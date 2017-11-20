<?php 
   require_once "Libs/Smarty.class.php";
   require_once "Clases/CProducto.php";
   session_start();
   $loSmarty = new Smarty;
   if (@$_REQUEST['Boton'] == 'Grabar') {
      fxGrabar();
   }elseif (@$_REQUEST['Boton'] == 'Editar') {
      fxEditar();
   }elseif (@$_REQUEST['Boton'] == 'Eliminar') {
      fxEliminar();
   }elseif (@$_REQUEST['Boton'] == 'Actualizar') {
       fxActualizar();
   }else {
      fxInit();
   }
   function fxInit() {
      $lo = new CProducto();
      $llOk = $lo->omInitProducto();
      if (!$llOk) {
         fxHeader('index.php', $lo->pcError);
         return;
      }
       $_SESSION['paDatos'] = $lo->paDatos; 
       fxScreen();
   }

   function fxGrabar() {
      $laData = $_REQUEST['paData'];
      $_SESSION['paData'] = $laData;
      print_r($laData);
      $lo = new CProducto();
      $lo->paData = $laData;
      $llOk = $lo->omGrabarProducto();
      $_SESSION['paData'] = $lo->paData;
      fxAlert('GRABACION CONFORME');
      fxInit();
   }  

   function fxEliminar() {
      $lo = new CProducto();
      $lo->paIdPro = ['COBJID' => $_REQUEST['paIdPro']];
      $llOk = $lo->omEliminarProducto();
      $_SESSION['paData'] = $lo->paData;
      fxScreen();
   }
   
   function fxEditar() {
      $lo = new CProducto();
      $lo->paIdPro = ['COBJID' => $_REQUEST['paIdPro']];
      $llOk = $lo->omEditarProducto();
      $_SESSION['paData'] = $lo->paData;
      fxScreen();
   }
   
    function fxActualizar() {
      $laData = $_REQUEST['paData'];
      $_SESSION['paData'] = $laData;
      $lo = new CProducto();
      $lo->paData = $laData;
      $llOk = $lo->omActualizarProducto();
      $_SESSION['paData'] = $lo->paData;
      fxAlert('GRABACION CONFORME');
      fxInit();
   } 
   
   function fxScreen() {
      global $loSmarty;
      $loSmarty->assign('paIdPro', $_SESSION['paIdPro']);
      $loSmarty->assign('saDatos', $_SESSION['paDatos']);
      $loSmarty->assign('saData', $_SESSION['paData']);
      $loSmarty->display('Plantillas/index.tpl');    
   }  