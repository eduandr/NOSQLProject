<!DOCTYPE html>
<html lang="en">
<head>
  <title>OCRRII</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script src="JS/java.js"></script>
  <link href="CSS/style.css" rel="stylesheet">
</head>
<body>
<div id="header"></div>
<form action="index.php" method="post" enctype="multipart/form-data">  
<div class="container" id="hola">    
  <h3>Registrar Productos</h3><br>
  <div class="row">
    <div class="col-sm-4">
        <div class="container">
                 <div class="form-group" >
                    <label class="control-label col-sm-2" for="codigo">ObjId:</label>
                    <div class="col-sm-10">
                       <input type="text" class="form-control" maxlength="250" name="paData[COBJID]"  value="{$saData['_id']['$oid']}" autofocus>
                    </div>
                 </div>
                 <div class="form-group">
                    <label class="control-label col-sm-2" for="codigo">Precio:</label>
                    <div class="col-sm-10">
                       <input type="text" class="form-control" maxlength="250" name="paData[NPRECIO]"  value="{$saData['NPRECIO']}" autofocus>
                    </div>
                 </div>
                 <br>
                 <div class="form-group">
                    <label class="control-label col-sm-2" for="mess">Descripción:</label>
                    <div class="col-sm-10">               
                       <input type="text" class="form-control" name="paData[CDESCOR]" value="{$saData['CDESCOR']}" maxlength="250">
                    </div>
                 </div>
                 <div class="form-group">
                    <label class="control-label col-sm-2" for="mess">Cod. Proveedor:</label>
                    <div class="col-sm-4">               
                       <input type="text" class="form-control" name="paData[CIDPROV]" value="{$saData['CIDPROV']}">
                    </div>
                 </div>
                 <br>
                 <div class="form-group">
                    <label class="control-label col-sm-2" for="mess">Categoria:</label>
                    <div class="col-sm-4">               
                        <select name="paData[CDESCAT]" style="width: 300px">
                            <option value = 'C4' selected >Accesorios</option>
                            <option value = 'C3' selected >Procesadores</option>
                            <option value = 'C2' selected >Motherboards</option>
                            <option value = 'C1' selected >Tarjetas de video</option>
                        </select>
                    </div>
                 </div>
                 <div class="form-group">
                    <label class="control-label col-sm-2" for="mess">Imagen URL:</label>
                    <div class="col-sm-4">               
                       <input type="text" class="form-control"  name="paData[CIMGURL]" value="{$saData['CIMGURL']}">
                    </div>
                 </div>
                 <div class="form-group">
                    <label class="control-label col-sm-2" for="mess">Stock:</label>
                    <div class="col-sm-4">               
                       <input type="text" class="form-control"  name="paData[NSTOCK]" value="{$saData['NSTOCK']}">
                    </div>
                 </div>
                 <div class="form-group">
                    <label class="control-label col-sm-2" for="mess">Marca:</label>
                    <div class="col-sm-4">               
                       <input type="text" class="form-control"  name="paData[CMARCA]" value="{$saData['CMARCA']}">
                    </div>
                 </div>
                 <div class="form-group">
                    <label class="control-label col-sm-2" for="mess">Modelo:</label>
                    <div class="col-sm-4">               
                       <input type="text" class="form-control"  name="paData[CMODELO]" value="{$saData['CMODELO']}">
                    </div>
                 </div>
                 <div class="form-group">
                    <label class="control-label col-sm-2" for="mess">Nombre:</label>
                    <div class="col-sm-4">               
                       <input type="text" class="form-control"  name="paData[CITNAME]" value="{$saData['CITNAME']}">
                    </div>
                 </div>
                </div>
               <div class="container">
                 <button type="submit" name="Boton" value="Grabar" class="btn btn-sucess">Grabar</button>
                 <button type="submit" name="Boton" value="Actualizar" class="btn btn-sucess">Actualizar</button>
              </div>
                </div>
    </div>
  </div>
</div><br><br>
      <div class="container " style="overflow:scroll; height:500px; width: 1100px; overflow:auto; border: 1px" > <!style="overflow:scroll; height:500px; width:1500px; overflow:auto; border: 1px">
      <table class="table table-bordered" style="font-size:10px">
         <th bgcolor="#F29818" >Id</th>
         <th bgcolor="#F29818" >Producto</th>
         <th bgcolor="#F29818" >Descripcion</th>
         <th bgcolor="#F29818" >Modelo</th>
         <th bgcolor="#F29818" >Marca</th>
         <th bgcolor="#F29818" >Categoria</th>
         <th bgcolor="#F29818" >Stock</th>
         <th bgcolor="#F29818" >Proveedor</th>
         <th bgcolor="#F29818" >Precio</th>
         <th bgcolor="#F29818" >Imagen</th>
         <th bgcolor="#F29818" align="center"><img src="CSS/Check.jpg" width="25" height="25"></th>
         {foreach from = $saDatos item = i}
            <tr>
            <td align="center">{$i[9]}</td>
            <td align="center">{$i[8]}</td>
            <td align="left">{$i[1]}</td>
            <td align="center">{$i[7]}</td>
            <td align="center">{$i[6]}</td>
            <td align="center">{$i[3]}</td>
            <td align="center">{$i[5]}</td>
            <td align="center">{$i[2]}</td>
            <td align="center">{$i[0]}</td>
            <td align="center"><img src="{$i[4]}" width="200" height="200"></td>
            <td align="center"><input type="radio" name="paIdPro" value="{$i[9]}"/></td>
            </tr>
         {/foreach}
      </table>
      </div>
      <button type="submit" name="Boton" value="Editar" class="btn btn-sucess">Editar</button>
      <button type="submit" name="Boton" value="Eliminar" class="btn btn-sucess">Eliminar</button>
</form>
</body>
</html>
