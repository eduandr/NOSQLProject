<?php

//API Url
echo "ejecutado";
$url = 'localhost:8081/cactualizar';

//Initiate cURL.
$ch = curl_init($url);

//The JSON data.
$jsonData = array(
	'COBJID' => '5a0cf1fb3a439ca8f12a16ca', // AQUI VA EL ID, SI NO EXISTE EL OID, LO INSERTA
    'NPRECIO'=> 1350.0, 
	'CDESCOR'=> "MECHANICAL 12 BUTTON THUMB GRID", 
	'CIDPROV'=> "0001783", 
	'CDESCAT'=> array("CIDCATE" => "C4", "CNOMCAT"=> "Accesorios"), // ESTE ES UN ARRAY DE CATEGORIA, TIENE QUE IR ASI SI O SI 
	'CIMGURL'=> "https://assets.razerzone.com/eeimages/products/13785/razer-naga-2014-right-03.png", 
	'NSTOCK'=> 3.0, 
	'CMARCA'=> "AMD", 
	'CMODELO'=> "RN1", 
	'CITNAME'=> "PERU :V"
);
//Encode the array into JSON.CNRODNI
$jsonDataEncoded = json_encode($jsonData);

//Tell cURL that we want to send a POST request.
curl_setopt($ch, CURLOPT_POST, 1);

//Attach our encoded JSON string to the POST fields.
curl_setopt($ch, CURLOPT_POSTFIELDS, $jsonDataEncoded);

//Set the content type to application/json
curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json')); 

//Execute the request
$result = curl_exec($ch);