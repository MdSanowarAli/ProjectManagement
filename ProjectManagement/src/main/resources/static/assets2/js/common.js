function initFormSubmit(){
	$("form:not(.ajax)").on('submit', function(event) {
		//event.preventDefault();
		var validator = window[$(this).data("validator")];

		if (validator === undefined || validator()) {
			showWait();
			return true;
		}
		return false;
	});
}
function CheckRequired(selector){
    result = true;

    if (!selector) {
        selector = ".main-content"
    }

    $(selector).find("input.required, textarea.required").each(function(i, item){
        $(this).removeClass("error");
        MarkCross($(this).closest(".form-group").find("label"), false);
        if ($(item).val().trim() == "") {
            result = false;
            $(this).addClass("error");
            MarkCross($(this).closest(".form-group").find("label"), true);
        }
    });
    
    $(selector).find("select.required").each(function(i, item){
        $(this).removeClass("error");
        MarkCross($(this).closest(".form-group").find("label"), false);
        if ($(item).val().trim() == "" || $(item).val().trim() == "-1") {
            result = false;
            $(this).addClass("error");
            MarkCross($(this).closest(".form-group").find("label"), true);
        }
    });
    FocusError();
    return result;
}

function MarkCross(selector, invalid){
    $(selector).find("span").remove();

    if (invalid) {
        $(selector).append('<span>&nbsp;&nbsp;</span><span class="text-red fa fa-close"></span>');
    }
    else{
        $(selector).append('<span>&nbsp;&nbsp;</span><span class="text-green fa fa-check"></span>');
    }
}




function FocusError(){
    var firstErrorEl = $($(".error")[0]);
    firstErrorEl.focus();
    $().animate({
        scrollTop: (firstErrorEl.offset().top)
    },500);
}




function FocusElement(element){
    $().animate({
        scrollTop: ($(element).offset().top)
    },100);
    $(element).focus();
}


function InitErrorChange(){
    $(".required:not(.errorchange-linked)").on("change", function(){
        $(this).closest(".form-group").find("label").find("span").remove();
        if($(this).val()){
            $(this).closest(".form-group").find("label").append('<span>&nbsp;&nbsp;</span><span class="text-green fa fa-check"></span>');
        }
        else{
            $(this).closest(".form-group").find("label").append('<span>&nbsp;&nbsp;</span><span class="text-red fa fa-close"></span>');
        }
    }).addClass("errorchange-linked");
}

function ResetErrorChange(){
    $(".errorchange-linked").off("change").removeClass(".errorchange-linked");
}

function ResetInputs(selector){
    if (!selector) {
        selector = ".main-content"
    }
    $(selector).find("input[type='email']").val("");
    $(selector).find("input[type='text']").val("");
    $(selector).find("input[type='hidden']").val("");
    $(selector).find("input[type='number']").val("0");
    $(selector).find("input[type='checkbox']").removeProp("checked");
    $(selector).find("textarea").val("");
    $(selector).find("select").val("");
    $(selector).find(".control-label span").remove();
}
function initAjaxForms() {
	$("form.ajax:not(.ajax-linked)").on('submit', function(event) {
		event.preventDefault();
		var validator = window[$(this).data("validator")];

		if (validator === undefined || validator()) {
			var enctype = $(this).prop("enctype");

			if (!enctype || enctype == "application/x-www-form-urlencoded") {
				$.ajax({
					type : $(this).prop('method'),
					url : $(this).prop('action'),
					data : $(this).serialize(),
					success : window[$(this).data("callback")]
				});
			} else {
				$.ajax({
					type : $(this).prop('method'),
					encType : enctype,
					contentType : false,
					processData : false,
					url : $(this).prop('action'),
					data : new FormData($(this)[0]),
					dataType : 'json',
					success : window[$(this).data("callback")]
				});
			}

		}
		;
		return false;
	}).addClass("ajax-linked");
}

function initAjaxLinks() {
	$("a.ajax:not(.ajax-linked), button.ajax:not(.ajax-linked)").on('click',
			function(event) {
				event.preventDefault();
				var url = "";
				if ($(this).attr('href')) {
					url = $(this).attr('href');
				} else {
					url = $(this).data('href');
				}

				var container = $(this).data('container');
				var callback = $(this).data('callback');

				if (container) {
					$(container).load(url, function() {
						if (callback) {
							window[callback]();
						}
					});
				} else if (callback) {
					$.ajax({
						url : url,
						success : window[callback]
					});
				} else {
					console.error("Define data-container and/or data-callback")
				}
				return false;

			}).addClass("ajax-linked");
}

function loadModal(id, title, url, callback) {
	if ($("#" + id).length == 0) {
		var html = '';
		html += '<div class="modal fade draggable-modal ui-draggable" id="'
				+ id
				+ '" tabindex="-1" role="basic" aria-hidden="true" style="left: 40px; top: 102px;">';
		html += '    <div class="modal-dialog">';
		html += '        <div class="modal-content">';
		html += '            <div class="modal-header ui-draggable-handle">';
		html += '                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>';
		html += '                <h4 class="modal-title"></h4>';
		html += '            </div>';
		html += '            <div class="modal-body">Loading...</div>';
		html += '            <div class="modal-footer">';
		/*
		 * html+= ' <button type="button" class="btn dark btn-outline"
		 * data-dismiss="modal">Close</button>' ; html+= ' <button
		 * type="button" class="btn green">Save changes</button>' ;
		 */
		html += '            </div>';
		html += '        </div>';
		html += '    </div>';
		html += '</div>';
		$('body').append(html);
	} else {
		$("#" + id).find('.modal-body').html('Loading...');
	}

	$("#" + id).find('.modal-body').load(url, function() {
		$("#" + id).find(" .modal-title").text(title);
		$("#" + id).find(" .modal-footer").html($(".hidden-footer").html());
		$(".hidden-footer").remove();
		$("#" + id).modal();
		if (callback) {
			callback();
		}
	});
}

function showModal(id, title, body) {
	if ($("#" + id).length == 0) {
		var html = '';
		html += '<div class="modal fade draggable-modal ui-draggable" id="'
				+ id
				+ '" tabindex="-1" role="basic" aria-hidden="true" style="left: 40px; top: 102px;">';
		html += '    <div class="modal-dialog">';
		html += '        <div class="modal-content">';
		html += '            <div class="modal-header ui-draggable-handle">';
		html += '                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>';
		html += '                <h4 class="modal-title"></h4>';
		html += '            </div>';
		html += '            <div class="modal-body"></div>';
		html += '            <div class="modal-footer">';
		html += '                <button type="button" class="btn dark btn-outline" data-dismiss="modal">Close</button>';
		html += '                <button type="button" class="btn green">Save changes</button>';
		html += '            </div>';
		html += '        </div>';
		html += '    </div>';
		html += '</div>';
		$('body').append(html);
	}

	$("#" + id).find(" .modal-title").text(title);
	$("#" + id).find('.modal-body').html(body);
	$("#" + id).modal();
}

function closeModal(id) {
	$("#" + id).modal('hide');
	$("#" + id).find('.modal-body').html('Loading...');
}

function deleteMessage(message, isSuccess) {
	if (isSuccess) {
		$(".delete_message")
				.html(
						'<p style="color: green; font-size: 18px;">' + message
								+ '</p>');
	} else {
		$(".delete_message").html(
				'<p style="color: red; font-size: 18px;">' + message + '</p>');
	}
	$(".delete_message").show();
	$(".defaltHeader").hide();
	setTimeout(function() {
		$(".delete_message").hide("slow");
		$(".defaltHeader").show();
		//$(".delete_message").html('');
		
	}, 2000);
}

function showSuccessMsg(title, msg) {
	swal({
		title : title,
		text : msg,
		type : "success",
		confirmButtonColor : "#007AFF"
	});
}

function showErrorMsg(title, msg) {
	swal({
		title : title,
		text : msg,
		type : "error",
		confirmButtonColor : "#007AFF"
	});
}

function showConfirmMsg(title, msg, btnText, callback) {
	swal({
		title : title,
		text : msg,
		type : "warning",
		showCancelButton : true,
		// confirmButtonColor: "#007AFF ",
		confirmButtonText : btnText,
		closeOnConfirm : true
	}, callback);
}

function formatDate(inputDate, format) {
	var m_names = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
			"Aug", "Sep", "Oct", "Nov", "Dec");

	var d = new Date(inputDate);
	var curr_date = PadZero(d.getDate());
	var curr_month = d.getMonth();
	var curr_year = d.getFullYear();
	if (format == "dd-mm-yyyy") {
		return (curr_date + "-" + PadZero(curr_month + 1) + "-" + curr_year);
	} else if (format == "dd-mmm-yyyy") {
		return (curr_date + "-" + m_names[curr_month] + "-" + curr_year);
	} else if (format == "yyyy-mm-dd") {
		return (curr_year + "-" + PadZero(curr_month + 1) + "-" + curr_date);
	} else if (format == "yyyy-mmm-dd") {
		return (curr_year + "-" + m_names[curr_month] + "-" + curr_date);
	} else {
		console.log("Invalid Data Format");
	}

}
function deleteByAjax(url, token, callback) {
	$.ajax({
		url : url,
		beforeSend : function(xhr) {
			xhr.setRequestHeader('X-Csrf-Token', token)
		},
		type : 'DELETE',
		cache : false,
		success : callback
	});
	return false;
}
var waitWin = null;
function showWait() {
	if (!document.getElementById("wait-content")) {
		console.log('%c Wait...',
				'color: red; font-weight: bold;font-size:30px;');
	}

	clearTimeout(waitWin);
	document.getElementById("wait-content").style.display = '';

	var canvas = document.getElementById('waitCanvas');
	var context = canvas.getContext('2d');
	var n = 0;
	var start = -(Math.PI / 2);
	var w = context.canvas.width;
	var h = context.canvas.height;
	var cx = context.canvas.width / 2;
	var cy = context.canvas.height / 2;
	var diff = 0;
	var bgColor = "#8f8";
	var fgColor = "#88f";
	var text = "Please wait..";
	var drawWait = function() {
		diff = (n / 100) * Math.PI * 2;
		context.clearRect(0, 0, w, h);
		context.lineWidth = h * 0.1;
		context.beginPath();
		context.arc(cx, cy, h * 0.4, 0, 2 * Math.PI, false);
		context.strokeStyle = bgColor;
		context.stroke();
		context.textAlign = 'center';
		context.font = '10pt Verdana';
		context.beginPath();
		context.arc(cx, cy, h * 0.4, start, diff + start, false);
		context.strokeStyle = fgColor;
		context.stroke();
		context.fillStyle = "#fff";
		context.fillText(text, cx + 2, cy + 6);
		if (n >= 100) {
			n = 0;
			var temp = bgColor;
			bgColor = fgColor;
			fgColor = temp;
			start += 0.1;
		}

		n += 2;

	}

	waitWin = setInterval(drawWait, 50);
}

function closeWait() {
	clearTimeout(waitWin);
	document.getElementById("wait-content").style.display = 'none';
}
function numberWithCommas(x) {
	try {
		return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	} catch (error) {
		return 0;
	}
}

function chart(val, chartvalue, title) {
	AmCharts
			.makeChart(
					"chart_Top" + val,
					{
						"type" : "serial",
						"theme" : "light",
						"dataProvider" : chartvalue,
						"valueAxes" : [ {
							"axisAlpha" : 0,
							"dashLength" : 4,
							"position" : "left"
						} ],
						"graphs" : [ {
							"bulletSize" : 14,
							"customBullet" : "../images/star.png",
							"customBulletField" : "customBullet",
							"valueField" : "postRank",
							"balloonText" : "<div style='margin:10px; text-align:left;'><span style='font-size:13px'>[[category]]</span><br><span style='font-size:18px'>Post Rank:[[value]]</span>",
						} ],
						"marginTop" : 20,
						"marginRight" : 70,
						"marginLeft" : 60,
						"marginBottom" : 20,
						"chartCursor" : {
							"graphBulletSize" : 1.5,
							"zoomable" : true,
							"valueZoomable" : true,
							"cursorAlpha" : 0,
							"valueLineEnabled" : true,
							"valueLineBalloonEnabled" : true,
							"valueLineAlpha" : 0.2
						},
						"autoMargins" : false,
						// "dataDateFormat": "YYYY-MM-DD",
						"categoryField" : "UpdateTime",
						"valueScrollbar" : {
							"offset" : 30
						},
						"categoryAxis" : {
							"axisAlpha" : 0,
							"gridAlpha" : 0,
							"inside" : true,
							"tickLength" : 0,
							"title" : title
						},
						"export" : {
							"enabled" : true
						}

					});
}

function getDate() {
	var date = new Date();
	var day = date.getDate();
	var month = date.getMonth() + 1;
	var year = date.getFullYear();
	day = day < 10 ? "0" + day : day;
	month = month < 10 ? "0" + month : month;
	return day + "-" + month + "-" + year;
}

$(".requiredDate").on('change', function() {
	var val = $(this).val();
	if (val == "") {

	}
});

function loadClinic(clinicIds){
	$("#_clinicId").typeahead({
		source : function(query, result) {
			$.ajax({
				url : "/api/clinic",
				data : {
					'query' : query
				},
				dataType : "json",
				type : "GET",
				success : function(data) {
					result($.map(data, function(item) {
						clinicIds[item.clinicCode] = item.id;
						return item.clinicCode;
					}));
				}
			});
		},
		updater : function(e) {
			$('#clinicId').val(clinicIds[e]);
			return e;
		}
	});
}
function loadADA(adaIds){
	$("#_adaId").typeahead({
		source : function(query, result) {
			$.ajax({
				url : "/api/ada",
				data : {
					'query' : query
				},
				dataType : "json",
				type : "GET",
				success : function(data) {
					result($.map(data, function(item) {
						adaIds[item.adaCode] = item.id;
						return item.adaCode;
					}));
				}
			});
		},
		updater : function(e) {
			$('#adaId').val(adaIds[e]);
			return e;
		}
	});
}
function loadPatient(patientIds){
	$("#_patientId").typeahead({
		source : function(query, result) {
			$.ajax({
				url : "/api/patient",
				data : {
					'query' : query
				},
				dataType : "json",
				type : "GET",
				success : function(data) {
					result($.map(data, function(item) {
						patientIds[item.patientId] = item.id;
						return item.patientId;
					}));
				}
			});
		},
		updater : function(e) {
			$('#patientId').val(patientIds[e]);
			return e;
		}
	});
}
function loadInsurance(insuranceIds){
	$("#_insuranceId").typeahead({
		source : function(query, result) {
			$.ajax({
				url : "/api/insurance",
				data : {
					'query' : query
				},
				dataType : "json",
				type : "GET",
				success : function(data) {
					result($.map(data, function(item) {
						insuranceIds[item.carrierName] = item.id;
						return item.carrierName;
					}));
				}
			});
		},
		updater : function(e) {
			$('#insuranceId').val(insuranceIds[e]);
			return e;
		}
	});
}
$(".amount").on('focusout', function() {
	var val = $(this).val();
	if (isNaN(val) || val <= 0) {
		$(this).val('0.00');
	}
});