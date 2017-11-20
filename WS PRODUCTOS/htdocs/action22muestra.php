<?php

//API Url
echo "ejecutado";
$url = 'localhost:8081/ctodos';

//Initiate cURL.
$ch = curl_init($url);

//The JSON data.
$jsonData = array(
	//'CLIMIT' => '10'
	'CNRODNI' => '730418871',
    'CCODUSU' => '2013246521',
    'CPROYEC' => '2017-1',
    'CCLAVE' => '18877304',
	'CPASSWORD' => '18877304',
    'CTOKEN' => 'DIANA2',
	'CLIMIT' => '10',
	'CUNIACA' => '00'
    //'CTERMIP' => '73041887'
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