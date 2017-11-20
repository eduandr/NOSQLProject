<?php
require_once "Clases/CBase.php";
require_once "Clases/CSql.php";

class CProducto extends CBase {
   public $paDatos,$paData, $paTitulo, $paDescri, $paProducto, $paIdPro, $laCategorias;
   public function __construct() {
      parent::__construct();
      $this->paData= $this->paDatos=$this->paTitulo = $this->paProducto = $this->paDescri = $this->paIdPro=null;
	  $this->laCategorias = array (
		'C1'=> 'Tarjetas de video', 
		'C2'=> 'Procesadores', 
		'C3'=> 'Motherboards',
		'C4'=> 'Accesorios'
   );
   }
   

   public function omInitProducto() {
        $url = 'localhost:8081/ctodos';
        $ch = curl_init($url);
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_URL,$url);
        $result = curl_exec($ch);
        curl_close($ch);
        $laData= json_decode($result);
        for($k=0; $k<count($laData);$k++) {
            $j=0;
            $this->paDatos[] = [$laData[$k]->NPRECIO, $laData[$k]->CDESCOR,
                $laData[$k]->CIDPROV, json_decode($result,true)[$k]['CDESCAT'][$j]['CNOMCAT'],
                $laData[$k]->CIMGURL, $laData[$k]->NSTOCK, $laData[$k]->CMARCA,
                $laData[$k]->CMODELO, $laData[$k]->CITNAME, json_decode($result,true)[$k]['_id']['$oid']];
            $j++;
            }
      return true;
   }
   public function omGrabarProducto() {
        $url = 'localhost:8081/cinsertar';
        $ch = curl_init($url);
		$jsonData = array(
			'NPRECIO'=> $this->paData['NPRECIO'], 
			'CDESCOR'=> $this->paData['CDESCOR'], 
			'CIDPROV'=> $this->paData['CIDPROV'], 
			'CDESCAT'=> array(array("CIDCATE" => $this->paData['CDESCAT'] , "CNOMCAT"=> $this->laCategorias [$this->paData['CDESCAT']])),
			'CIMGURL'=> $this->paData['CIMGURL'], 
			'NSTOCK'=> $this->paData['NSTOCK'], 
			'CMARCA'=> $this->paData['CMARCA'], 
			'CMODELO'=> $this->paData['CMODELO'], 
			'CITNAME'=> $this->paData['CITNAME']
		);
		$jsonDataEncoded = json_encode($jsonData);
		curl_setopt($ch, CURLOPT_POST, 1);
                curl_setopt($ch, CURLOPT_POSTFIELDS, $jsonDataEncoded);
		curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json')); 
		$result = curl_exec($ch);
        return true;
   }
   
   protected function mxInvocateProducto(){
        $url = 'localhost:8081/ctodos';
        $ch = curl_init($url);
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_URL,$url);
        $result = curl_exec($ch);
        $laData= json_decode($result);
        curl_close($ch);
        for($k=0; $k<count($laData);$k++) {
               if($this->paIdPro['COBJID'] ==  json_decode($result,true)[$k]['_id']['$oid']){
                    $this->paProducto = json_decode($result,true)[$k];
                    $this->paTitulo = json_decode($result,true)[$k]['_id']['$oid'];
                }
        }   
   }

   public function omEliminarProducto()
   {
        $this->mxInvocateProducto();
        $url = 'localhost:8081/celiminar';
        $ch = curl_init($url);
        $jsonData = array(
            'COBJID' => $this->paIdPro['COBJID'] 

        );    
        $jsonDataEncoded = json_encode($jsonData,JSON_UNESCAPED_SLASHES);
        curl_setopt($ch, CURLOPT_POST, 1);
        curl_setopt($ch, CURLOPT_POSTFIELDS, $jsonDataEncoded);
        curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json')); 
        $result = curl_exec($ch);      
        curl_close($ch);
   }

   public function omEditarProducto()
   {
        $this->mxInvocateProducto();
        $laFila = 0;
        $la = $this->paIdPro['COBJID'];
        $this->paData[] = [$this->paProducto['NPRECIO'] => $laFila[0], $this->paProducto['CDESCOR'] => $laFila[1],$this->paProducto['CIDPROV'] => $laFila[2],
            $this->paProducto['CDESCAT'] => $laFila[3],$this->paProducto['CIMGURL'] => $laFila[4],$this->paProducto['NSTOCK'] => $laFila[5],
            $this->paProducto['CMARCA'] => $laFila[6],$this->paProducto['CMODELO'] => $laFila[7],$this->paProducto['CITNAME'] => $laFila[8],
            $this->paIdPro => $laFila[9]];
        
        $this->paData = $this->paProducto; 
    }
   
   public function omActualizarProducto()
   {
       $url = 'localhost:8081/cactualizar';
       $ch = curl_init($url);
		$jsonData = array(
                        'COBJID' => $this->paData['COBJID'], 
			'NPRECIO'=> $this->paData['NPRECIO'], 
			'CDESCOR'=> $this->paData['CDESCOR'], 
			'CIDPROV'=> $this->paData['CIDPROV'], 
			'CDESCAT'=> array(array("CIDCATE" => $this->paData['CDESCAT'] , "CNOMCAT"=> $this->laCategorias [$this->paData['CDESCAT']])),
			'CIMGURL'=> $this->paData['CIMGURL'], 
			'NSTOCK'=> $this->paData['NSTOCK'], 
			'CMARCA'=> $this->paData['CMARCA'], 
			'CMODELO'=> $this->paData['CMODELO'], 
			'CITNAME'=> $this->paData['CITNAME']
		);
		$jsonDataEncoded = json_encode($jsonData);
		curl_setopt($ch, CURLOPT_POST, 1);
                curl_setopt($ch, CURLOPT_POSTFIELDS, $jsonDataEncoded);
		curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json')); 
		$result = curl_exec($ch);
        return true;
   }
}