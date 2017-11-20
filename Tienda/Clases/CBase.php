<?php
require_once "class/PHPExcel.php";
require_once 'class/RTFTable.php';
//------------------------------------------------------
// Clase Base
//------------------------------------------------------
class CBase {
   public $pcError;

   function __construct() {
      $this->pcError = null;
   }
}
//------------------------------------------------------
// Clase para fechas
//------------------------------------------------------
class CDate extends CBase {
   public $date;
   public $days;

   public function valDate($p_dFecha) {
      $laFecha = explode('-', $p_dFecha);
      $llOk = checkdate((int)$laFecha[1], (int)$laFecha[2], (int)$laFecha[0]); 
      if (!$llOk) {
         $this->pcError = 'FORMATO DE FECHA INVALIDA';
      }
      return $llOk;
   }
   public function add($p_dFecha, $p_nDias) {
      $llOk = $this->valDate($p_dFecha);
      if (!$llOk) {
         return false;
      }
      if (!is_int($p_nDias)) {
         $this->pcError = 'PARAMETRO DE DIAS ES INVALIDO';
         return false;
      } elseif ($p_nDias >= 0) {
         $lcDias = ' + '.$p_nDias.' days';
      } else {
         $p_nDias = $p_nDias * (-1);
         $lcDias = ' - '.$p_nDias.' days';
      }
      $this->date = date('Y-m-d', strtotime($p_dFecha.$lcDias));
      return true;
   }
   public function diff($p_dFecha1, $p_dFecha2) {
      $llOk = $this->valDate($p_dFecha1);
      if (!$llOk) {
         return false;
      }
      $llOk = $this->valDate($p_dFecha2);
      if (!$llOk) {
         return false;
      }
      $this->days = (strtotime($p_dFecha1) - strtotime($p_dFecha2)) / 86400;
      $this->days = floor($this->days);
	  return true;
   }
   public function dateText($p_dDate) {
      $llOk = $this->valDate($p_dDate);
      if (!$llOk) {
         return 'Error: '.$p_dDate;
      }
      $laDays = array('Domingo', 'Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado');
      $laMonths = array('Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre');
      $laDate = explode('-', $p_dDate);
      $ldDate = mktime(0, 0, 0, $laDate[1], $laDate[2], $laDate[0]);
      return $laDays[date('w', $ldDate)].', '.date('d', $ldDate).' '.$laMonths[date('m', $ldDate) - 1].' de '.date('Y', $ldDate);
   }
}
class CXls extends CBase {
   public $pcData = "", $pcFile, $pcFilXls;
   protected $loXls, $lo, $lcFilXls;
   public function __construct() {
      parent::__construct();
      $this->loXls = new PHPExcel();
      $this->lo = PHPExcel_IOFactory::createReader('Excel2007');      
   }
   public function openXls($p_cFilXls) {
      $this->loXls = $this->lo->load('./Xls/'.$p_cFilXls.'.xlsx');      
      $this->lcFilXls = './Files/R'.rand().'.xlsx';      
      $this->pcFilXls = $this->lcFilXls;
   }
   public function sendXls($p_nSheet, $p_cCol, $p_nRow, $p_xValue) {      
      $this->loXls->setActiveSheetIndex($p_nSheet)->setCellValue($p_cCol.$p_nRow, $p_xValue);            
      return;
   }
   public function closeXls() {    
      $lo = PHPExcel_IOFactory::createWriter($this->loXls, 'Excel2007');                        
      $lo->save($this->lcFilXls);           
   }
   public function cellColor($cells, $color) {
      $this->loXls->getActiveSheet()->getStyle($cells)->getFill()->applyFromArray(array(
         'type' => PHPExcel_Style_Fill::FILL_SOLID, 'startcolor' => array('rgb' => $color)));
   }
   public function cellColor1($Sheet, $cells, $color) {
      $this->loXls->getActiveSheet($Sheet)->getStyle($cells)->getFill()->applyFromArray(array(
         'type' => PHPExcel_Style_Fill::FILL_SOLID, 'startcolor' => array('rgb' => $color)));
   }
   public function setActiveSheet($p_nSheet) {
      $this->loXls->setActiveSheetIndex($p_nSheet);
   }
   public function getValue($p_nSheet, $p_cCol, $p_nRow) {
      $lcCell = $p_cCol.$p_nRow;
      $lxValue = $this->loXls->getActiveSheet(1)->getCell($lcCell)->getValue();
      return $lxValue;
   }
   public function openXlsIO($p_cFilXls, $p_cPrefij) {
      $this->loXls = $this->lo->load('./Xls/'.$p_cFilXls.'.xlsx');
      $lcFile = $p_cPrefij.rand();
      $this->pcFile = './IO/'.$lcFile.'.xlsx';
   }
   public function closeXlsIO() {
      $lo = PHPExcel_IOFactory::createWriter($this->loXls, 'Excel2007');                        
      $lo->save($this->pcFile);
   }
   public function getColor() {
      $lxValue = $this->loXls->getActiveSheet()->getStyle('D2')->getFill()->getStartColor()->getRGB();
      return $lxValue;
   }
}
function fxAlert($p_Message) {
   echo "<script type=\"text/javascript\">";
   echo "alert('$p_Message')";
   echo "</script>";  
}
function fxValEmail($p_cEmail) {
      if (!filter_var($p_cEmail, FILTER_VALIDATE_EMAIL)) {
         return false;
      }
      return true;
}
function fxHeader($p_cLocation, $p_cMensaje = '') {
   if (empty($p_cMensaje)) {
      $lcScript = "window.location='$p_cLocation';";
   } else {
      $lcScript = "alert('$p_cMensaje');window.location='$p_cLocation';";
   }
   echo '<script>'.$lcScript.'</script>';
}
function right($lcCadena, $count) {
   return substr($lcCadena, ($count * -1));
}
function left($lcCadena, $count) {
   return substr($lcCadena, 0, $count);
}
function fxNumber($p_nNumero, $p_nLength, $p_nDecimal) {
   $lcNumero = number_format($p_nNumero, $p_nDecimal);
   $lcCadena = str_repeat(' ', $p_nLength).$lcNumero;
   return right($lcCadena, $p_nLength);
}
function fxString($p_cCadena, $p_nLength) {
   $i = substr_count($p_cCadena, 'Ñ');
   $lcCadena = $p_cCadena.str_repeat(' ', $p_nLength);
   $lcCadena = substr($lcCadena, 0, $p_nLength + $i);
   return $lcCadena;
}
function fxInitSession() {
   if (!(isset($_SESSION["GCNOMBRE"]) and isset($_SESSION["GCUNIACA"]) and isset($_SESSION["GCCODUSU"]))) {
      return false;
   }
   return true;
}
function fxSubstrCount($p_cString) {
   $i = substr_count($p_cString, 'Á');
   $i += substr_count($p_cString, 'É');
   $i += substr_count($p_cString, 'Í');
   $i += substr_count($p_cString, 'Ó');
   $i += substr_count($p_cString, 'Ú');
   return $i;
}

function fxTraceo($p_cTexto, $p_nFlag = 1) {
   $lcTipo = ($p_nFlag == 0) ? 'w' : 'a';
   $loFile = fopen('fpm.txt', $lcTipo);
   fputs($loFile, $p_cTexto);
   fclose($loFile);
}
class CRtf extends CBase {
    public $pcFile, $paArray, $pcFilRet, $pcCodUsu, $paAArray, $pcFileName;
    protected $lcFolXls, $lcFolSal, $lcFilRet, $lcFilInp, $lcTodo, $lp;

    function __construct () {
       parent::__construct();
       $this->paArray = null;
       $this->lcFolXls = './Xls/';
       $this->lcFolSal = './Files/';
    }

    public function omInit() {
        $lcFile1 = $this->lcFolXls.$this->pcFile.'.rtf';
        if (!is_file($lcFile1)) {
           $this->pcError = 'ARCHIVO DE ORIGEN NO EXISTE ['.$lcFile1.']';
           return false;
        }
        $this->pcFilRet = $this->lcFolSal.'R'.rand().'.doc';
        $this->pcFilRet = 'R'.rand().'.doc';
        $this->lp = fopen($this->pcFilRet, 'w');
        // Lee archivo formato
        $laTexto = file($lcFile1);
        $lnSize = sizeof($laTexto);
        $this->lcTodo = '';
        for ($i = 0; $i < $lnSize; $i++) {error_reporting(0);//eliminar notificaciones  
            $this->lcTodo .=  $lcTodo.$laTexto[$i];
        }
        return true;
    }

    public function omInicializar() {
       $lcFile1 = $this->lcFolXls.$this->pcFile.'.rtf';
       if (empty($this->pcCodUsu)) {
          $this->pcError = 'CODIGO DE USUARIO NO DEFINIDO';
          return false;
       } elseif (!is_file($lcFile1)) {
          $this->pcError = 'ARCHIVO DE ORIGEN NO EXISTE';
          return false;
       }
       if (empty($this->pcFilRet)) {
          $this->pcFilRet = $this->lcFolSal.$this->pcFile.'_'.$this->pcCodUsu.'.doc';
       }
       $this->lp = fopen($this->pcFilRet, 'w');
       // Lee archivo formato
       $laTexto = file($lcFile1);
       $lnSize = sizeof($laTexto);
       $this->lcTodo = '';
       for ($i = 0;$i < $lnSize; $i++) {
           $this->lcTodo .= $lcTodo.$laTexto[$i];
       }
       return true;
    }

    protected function mxTerminar() {
       fputs($this->lp, $this->lcTodo);
       fclose($this->lp);
       return true;
    }

    public function omGenerar($p_lClose = false) {
       if (!(is_array($this->paArray) and count($this->paArray) > 0)) {
          fclose($this->lp);
          $this->pcError = 'ARREGLO DE DATOS NO DEFINIDO';
          return false;
       }
       // Reemplazo de variables
       foreach ($this->paArray as $lcValor1 => $lcValor2) {
          $lcValor2 = utf8_decode($lcValor2);
          $this->lcTodo = str_replace($lcValor1, $lcValor2, $this->lcTodo);
       }
       if ($p_lClose) {
          $this->mxTerminar();
       }
       return true;
    }

    public function omGenerarArray($p_lClose = false) {
       if (!(is_array($this->paAArray) and count($this->paAArray) > 0)) {
          fclose($this->lp);
          $this->pcError = 'ARREGLO DE DATOS NO DEFINIDO';
          return false;
       }
       foreach ($this->paAArray as $lcValor1 => $lcValor2) {
            $loTabla = new RTFTable($lcValor2[0], $lcValor2[1]);
            if ($lcValor2[3]=='') {
               $loTabla->SetWideColsTable(round(10500/$lcValor2[0]));
            }else {
               for ($k = 0;$k < count($lcValor2[3]);$k++) {
                   $loTabla->SetWideColTable($k,$lcValor2[3][$k]);
               }
            }
            //Llenar Tabla cn arreglo pos:2
            for ($i = 0;$i < count($lcValor2[2]);$i++) {
                for ($j = 0;$j < count($lcValor2[2][0]);$j++) {
                    $lcValor2[2][$i][$j] = utf8_decode($lcValor2[2][$i][$j]);
                    if ($j ==0 ) {
                       //Centrado
                       $loTabla->SetElementCell($i,$j,'\\qc '.$lcValor2[2][$i][$j]);
                    }else
                       $loTabla->SetElementCell($i,$j,' '.$lcValor2[2][$i][$j]);
                }
            }
            $this->lcTodo = str_replace($lcValor1,$loTabla->GetTable() ,$this->lcTodo);
       }
       if ($p_lClose) {
          $this->mxTerminar();
       }
       return true;
    }
   
    protected function mxLeerArchivo() {
       if (!is_file($this->pcFile)) {
          $this->pcError = '<DATA><ERROR>ARCHIVO DE ORIGEN NO EXISTE</ERROR></DATA>';
          return false;
       }
       $laTexto = file($this->pcFile);
       $lnSize = sizeof($laTexto);
       $lcTodo = '';
       for ($i = 0;$i < $lnSize;$i++) {
           $lcTodo = $lcTodo.$laTexto[$i];
       }      
       return $lcTodo;
    }

    public function omProcesar() {
        $this->lcFilRet = $this->lcFolSal.$this->pcFile.'_'.$this->pcCodUsu.'.rtf';//-- DEFINIMOS EL NOMBRE DEL NUEVO FICHERO
        $this->pcFile = $this->lcFolXls.$this->pcFile.'.rtf';
        if ($lcTexto = $this->mxLeerArchivo()) {
            $lp = fopen($this->lcFilRet, 'w');
            if (is_array($this->paArray) and count($this->paArray) > 0) {
                foreach($this->paArray as $lcValor1 =>$lcValor2) {//-- REEMPLAZAMOS LAS VARIABLES
                   $lcValor2 = utf8_decode($lcValor2);
                   $lcTexto = str_replace($lcValor1, $lcValor2 ,$lcTexto);
                }
            }
            fputs($lp, $lcTexto);
            fclose($lp);
            header ('Content-Disposition: attachment;filename = '.$this->lcFilRet.'\n\n');
            header ('Content-Type: application/octet-stream');
            readfile($this->lcFilRet);
        }
    }
}
?>
