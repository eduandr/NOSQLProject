function f_NumFormat(e, d) {
   e.value = Number(e.value).toFixed(d);
   if (e.value == 'NaN') {
      e.value = Number(0).toFixed(d);
      alert('ERROR EN VALOR NUMERICO')
      }
   return;
}
﻿
function justNumbers(e) {
   var keynum = window.event ? window.event.keyCode : e.which;
   
   //alert(keynum);
   
//   if ((keynum == 8) || (keynum == 46) || (keynum == 12) || (keynum == 13)) {
   if ((keynum <= 13) || (keynum == 46)) {
      return true;
   }
   return /\d/.test(String.fromCharCode(keynum));
}

// Pasa a mayusculas
function cUpper(cObj) {
   cObj.value = cObj.value.toUpperCase();
}

//***************************************
//Funciones comunes a todos los problemas
//***************************************
function addEvent(elemento, nomevento, funcion, captura) {
   if (elemento.attachEvent) {
      elemento.attachEvent('on'+nomevento, funcion);
      return true;
   } else if (elemento.addEventListener) {
      elemento.addEventListener(nomevento, funcion, captura);
      return true;
   } else
      return false;
}

function crearXMLHttpRequest() {
   var xmlHttp = null;
   if (window.ActiveXObject) 
      xmlHttp  =  new ActiveXObject("Microsoft.XMLHTTP");
   else if (window.XMLHttpRequest) 
      xmlHttp  =  new XMLHttpRequest();
   return xmlHttp;
}

jQuery(function($){
      $.datepicker.regional['es'] = {
            closeText: 'Cerrar',
            prevText: '&#x3c;Ant',
            nextText: 'Sig&#x3e;',
            currentText: 'Hoy',
            monthNames: ['Enero','Febrero','Marzo','Abril','Mayo','Junio',
            'Julio','Agosto','Septiembre','Octubre','Noviembre','Diciembre'],
            monthNamesShort: ['Ene','Feb','Mar','Abr','May','Jun',
            'Jul','Ago','Sep','Oct','Nov','Dic'],
            dayNames: ['Domingo','Lunes','Martes','Mi&eacute;rcoles','Jueves','Viernes','S&aacute;bado'],
            dayNamesShort: ['Dom','Lun','Mar','Mi&eacute;','Juv','Vie','S&aacute;b'],
            dayNamesMin: ['Do','Lu','Ma','Mi','Ju','Vi','S&aacute;'],
            weekHeader: 'Sm',
            dateFormat: 'yy-mm-dd',
			//dateFormat: 'dd/mm/yy',
            firstDay: 1,
            isRTL: false,
            showMonthAfterYear: false,
            yearSuffix: ''};
      $.datepicker.setDefaults($.datepicker.regional['es']);
}); 

	  jQuery(document).ready(function() {
        jQuery('.input_num').keypress(function(tecla) {
           if(tecla.charCode < 48 || tecla.charCode > 57) return false;
              });
        });

function f_ValidarEmail(obj) {
   // creamos nuestra regla con expresiones regulares.
   var lcEmail = obj.value;
   //alert(lcEmail);
   var filter = /[\w-\.]{3,}@([\w-]{2,}\.)*([\w-]{2,}\.)[\w-]{2,4}/;
   // utilizamos test para comprobar si el parametro valor cumple la regla
   if (!filter.test(lcEmail)) {
      alert('ERROR AL INGRESAR EMAIL');
	  document.getElementById(valor).focus();
   }
}	
/*
function f_ValidarEmail(valor) {
   // creamos nuestra regla con expresiones regulares.
   var filter = /[\w-\.]{3,}@([\w-]{2,}\.)*([\w-]{2,}\.)[\w-]{2,4}/;
   // utilizamos test para comprobar si el parametro valor cumple la regla
   if (filter.test(valor)) {
      return true;
   } else {
      alert('ERROR AL INGRESAR EMAIL');
	  //document.getElementById("mytext").focus();
      return false;
   }
}*/		