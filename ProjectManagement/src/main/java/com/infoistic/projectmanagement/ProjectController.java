package com.infoistic.projectmanagement;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.infoistic.dao.IAttachmentDao;
import com.infoistic.dao.IProjectDao;
import com.infoistic.domain.Attachment;
import com.infoistic.domain.Project;

@Controller
@RequestMapping("/project")
public class ProjectController {

	@Autowired
	IProjectDao projectDao;

	@Autowired
	IAttachmentDao attachmentDao;
	
	@RequestMapping("")
	public String index(Model model) {
		try {
			model.addAttribute("list", projectDao.get());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "project/index";
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String add(Model model, @RequestParam(required = false, defaultValue = "0") Integer Id) {
		String returnPath = "";
		try {
			Project project;
			if (Id > 0) {
				project = projectDao.getById(Id);
				returnPath = "project/update";
			} else {
				project = new Project();
				returnPath = "project/create";
			}
			model.addAttribute("project", project);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnPath;
	}

/*	@RequestMapping(value = "/saveContact", method = RequestMethod.POST)
	public String saveContact(Model model, @ModelAttribute Project project) {
		String returnPath = "";
		try {

			if (project.getId() > 0) {
				returnPath = "project/update";

				projectDao.Update(project);
			} else {
				returnPath = "project/create";

				projectDao.Add(project);
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("project", project);
			model.addAttribute("message", e.getMessage());
			return returnPath;
		}
		return "redirect:/project?message";
	}*/
	
	@RequestMapping(value = "/saveContact", method = RequestMethod.POST, consumes = {
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public String saveContact(@RequestParam(value = "file", required = false) MultipartFile[] file, Model model,
			@ModelAttribute Project project) {
		String returnPath="";
		try {
			List<Attachment> attachmentsNew = new ArrayList<Attachment>();
			Attachment attachment;
			Timestamp timestamp;
			
			if (project.getId() > 0) {
				returnPath="project/update";
				for (int i = 0; i < file.length; i++) {
					attachment = new Attachment();
					if (!file[i].isEmpty()) {
						String fileNameOrginal = file[i].getOriginalFilename();
						int fileExtentionIndex = fileNameOrginal.lastIndexOf(".");

						timestamp = new Timestamp(System.currentTimeMillis());
						attachment.setFilePath("" + timestamp.getTime() + "" + i + "."
								+ fileNameOrginal.substring(fileExtentionIndex + 1));
						attachment.setFileName(file[i].getOriginalFilename());
						attachment.setRefTable(Conversion.PROJECT_ATT);

						/*attachment.setCreatedAt(dd);
						attachment.setCreatedBy(user.getUserId());
						attachment.setCreatedForm(user.getLoginForm());*/

					}
					attachmentsNew.add(attachment);
				}
				List<Attachment> atts = projectDao.UpdatewithFile(project, attachmentsNew);
				for (int i = 0; i < file.length; i++) {
					if (!file[i].isEmpty()) {
						byte[] bytes = file[i].getBytes();
						Path path = Paths
								.get(Conversion.PROJECT_ATT_Path + "/" + attachmentsNew.get(i).getFilePath());
						Files.write(path, bytes);
					}
				}
				if (atts != null) {
					for (Attachment item : atts) {
						new File(Conversion.PROJECT_ATT_Path + "/" + item.getFilePath()).delete();
					}
				}
			} else {
				returnPath="project/create";

				for (int i = 0; i < file.length; i++) {
					attachment = new Attachment();
					if (!file[i].isEmpty()) {
						String fileNameOrginal = file[i].getOriginalFilename();
						int fileExtentionIndex = fileNameOrginal.lastIndexOf(".");

						timestamp = new Timestamp(System.currentTimeMillis());
						attachment.setFilePath("" + timestamp.getTime() + "" + i + "."
								+ fileNameOrginal.substring(fileExtentionIndex + 1));
						attachment.setFileName(file[i].getOriginalFilename());
						attachment.setRefTable(Conversion.PROJECT_ATT);
/*
						attachment.setCreatedAt(dd);
						attachment.setCreatedBy(user.getUserId());
						attachment.setCreatedForm(user.getLoginForm());*/

					}
					attachmentsNew.add(attachment);
				}
				project.setAttachments(attachmentsNew);

				projectDao.AddwithFile(project);
				for (int i = 0; i < file.length; i++) {
					if (!file[i].isEmpty()) {
						byte[] bytes = file[i].getBytes();
						Path path = Paths
								.get(Conversion.PROJECT_ATT_Path + "/" + attachmentsNew.get(i).getFilePath());
						Files.write(path, bytes);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("project", project);
			model.addAttribute("message", e.getMessage());
			return returnPath;
		}
		return "redirect:/project?message";
	}
	
	
	
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public String details(Model model, @RequestParam(required = false, defaultValue = "0") Integer Id) {
		try {
			model.addAttribute("project", projectDao.getById(Id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "project/details";
	}
	
	
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseBody
	public WsResponse delete(@RequestParam(required = false, defaultValue = "0") Integer Id) {
		try {
			projectDao.remove(Id);
			return WsResponse.createSuccessResponse("Data delete success", "");
		} catch (Exception e) {
			e.printStackTrace();
			return WsResponse.createErrorResponse("505", e.getMessage(), null);
		}
	}

	
}
