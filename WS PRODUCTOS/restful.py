#! /usr/bin/env python
from flask import Flask, jsonify, request,render_template
from CQueryProductos import *
from CBusqueda import *
from CMantenimiento import *
from CCompra import *
#from CDeudas import *
import json
import sys
from datetime import datetime
app = Flask(__name__)


@app.route('/cqueryOfertas1', methods=['GET'])
#Devuelve las Notas
def f_cqueryNotas():
    #st = '[{"prod1": {"CIMGURL":"http://media.ldlc.com/ld/products/00/01/69/25/LD0001692516_1.jpg","CITNAME":"Tarjeta de Video gtx 980 ti","CITDESC":"Tarjeta de video poderosa, para correr los ultimos juegos en 4K","CITPRIC":"1000.00$"}, {"prod2": "CIMGURL":"http://media.ldlc.com/ld/products/00/01/69/25/LD0001692516_1.jpg","CITNAME":"Tarjeta de Video gtx 980 ti","CITDESC":"Tarjeta de video poderosa, para correr los ultimos juegos en 4K","CITPRIC":"1000.00$"}]'
    item1 = '{"prod1":{"CIMGURL":"http://media.ldlc.com/ld/products/00/01/69/25/LD0001692516_1.jpg","CITNAME":"Tarjeta de Video GTX 980ti","CITDESC":"Tarjeta de video poderosa, para correr los ultimos juegos en 4K","CITPRIC":"1000.00$"}}'
    item2 = '{"prod2":{"CIMGURL":"https://ic.tweakimg.net/ext/i/2001106897.png","CITNAME":"Tarjeta de Video GTX 1070","CITDESC":"Tarjeta de video poderosa, para correr los ultimos juegos en 4K","CITPRIC":"900.00$"}}'
    print "-------"
    return "[" + item1 + "," + item2 + "]"


@app.route('/cqueryOfertas', methods=['POST'])
#Devuelve las Notas
def f_cqueryProductos():
    data = request.get_json(force=True)  
    lo = CQueryProductos()
    lo.pcParam = data
    llOk = lo.omConsultar()
    if llOk:
        return lo.pcData
    else:
        return lo.pcError
    
'''@app.route('/ccompra', methods=['POST'])
#Devuelve las Notas
def f_compra():
    data = request.get_json(force=True)
    print data
    return '{"OK":"OK"}'
'''

@app.route('/ccompra', methods=['POST'])
#Devuelve las Notas
def f_compra():
    data = request.get_json(force=True)  
    lo = CComprar()
    lo.pcParam = data
    llOk = lo.omComprar()
    if llOk:
        print "DATOS DE PAGO INSERTADOS:\n\n", data
        return lo.pcData
    else:
        return lo.pcError

@app.route('/cbusqueda', methods=['POST'])
#Devuelve las Notas
def f_BusquedaProductos():
    data = request.get_json(force=True)  
    lo = CBusqueda()
    lo.pcParam = data
    llOk = lo.omBuscar()
    if llOk:
        return lo.pcData
    else:
        return lo.pcError

@app.route('/ctodos', methods=['GET'])
#Devuelve las Notas
def f_todoslositems():
    lo = CQueryProductos()
    llOk = lo.omConsultarTodos()
    if llOk:
        return lo.pcData
    else:
        return lo.pcError

@app.route('/cinsertar', methods=['POST'])
def f_insertaproducto():
    data = request.get_json(force=True)  
    lo = CMantenimiento()
    lo.pcParam = data
    llOk = lo.omInsertar()
    if llOk:
        return lo.pcData
    else:
        return lo.pcError
    
@app.route('/cactualizar', methods=['POST'])
def f_actualizaproductos():
    data = request.get_json(force=True)  
    lo = CMantenimiento()
    lo.pcParam = data
    llOk = lo.omActualizar()
    if llOk:
        return lo.pcData
    else:
        return lo.pcError

@app.route('/celiminar', methods=['POST'])
def f_eliminaproductos():
    data = request.get_json(force=True)  
    lo = CMantenimiento()
    print data 
    lo.pcParam = data
    llOk = lo.omEliminar()
    if llOk:
        return lo.pcData
    else:
        return lo.pcError

@app.route('/cplaceorder', methods=['POST'])
def f_placeorder():
    data = request.get_json(force=True)  
    print data    

if __name__ == '__main__':
  #app.run(host='localhost', debug=True, port=8080)
  app.run(host='0.0.0.0', debug=True, port=8081)
  #app.run(host='192.168.0.3', debug=True, port=8080)
   


   

