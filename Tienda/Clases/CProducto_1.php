<?php
require_once "Clases/CBase.php";
require_once "Clases/CSql.php";

class CProducto extends CBase {
   public $paDatos,$paData, $paTitulo, $paDescri, $paProducto, $paIdPro;
   public function __construct() {
      parent::__construct();
      $this->paData= $this->paDatos=$this->paTitulo = $this->paProducto = $this->paDescri = $this->paIdPro=null;
   }
   public function omGrabarProducto() {
      echo "ejecutado";
      $url = 'localhost:8000/prueba';
      $ch = curl_init($url);
      $lcJson = json_encode($this->paData);
      curl_setopt($ch, CURLOPT_POST, 1);
      curl_setopt($ch, CURLOPT_POSTFIELDS, $lcJson);
      curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json')); 
      $result = curl_exec($ch);
      return true;
   }
   public function omInitProducto() {
        $url = 'localhost:8000/ctodos';
        $ch = curl_init($url);
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_URL,$url);
        $result = curl_exec($ch);
        curl_close($ch);
        $laData= json_decode($result);
        for($k=0; $k<count($laData);$k++) {
            $j=0;
            $nprecio = $laData[$k]->NPRECIO;
            $cdescor = $laData[$k]->CDESCOR;
            $cidprov = $laData[$k]->CIDPROV;
            $cdescat = json_decode($result,true)[$k]['CDESCAT'][$j]['CNOMCAT'];
            $cimgurl = $laData[$k]->CIMGURL;
            $nstock = $laData[$k]->NSTOCK;
            $cmarca = $laData[$k]->CMARCA;
            $cmodelo = $laData[$k]->CMODELO;
            $citname = $laData[$k]->CITNAME;
            $id = json_decode($result,true)[$k]['_id']['$oid'];
            $this->paDatos[] = [$laData[$k]->NPRECIO, $laData[$k]->CDESCOR,
                $laData[$k]->CIDPROV, json_decode($result,true)[$k]['CDESCAT'][$j]['CNOMCAT'],
                $laData[$k]->CIMGURL, $laData[$k]->NSTOCK, $laData[$k]->CMARCA,
                $laData[$k]->CMODELO, $laData[$k]->CITNAME, json_decode($result,true)[$k]['_id']['$oid']];
            $j++;
            
            }
            print_r($this->paDatos[0]);
      return true;
   }
}

            
//            echo $nprecio." ".$cdescor." ".$cidprov."O$% ".$cdescat." "
//                  .$cimgurl." ".$nstock." ".$cmarca." ".$cmodelo." "
//                  .$citname." ".$id;
//            echo "<br><br>";

