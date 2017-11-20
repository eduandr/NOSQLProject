#import psycopg2
import datetime
import xml.etree.ElementTree as ET
import json
import collections
from collections import namedtuple
from pymongo import *
import pprint
from bson.json_util import dumps
import re

class CQueryProductos():
   pcParam = None
   lcIdCate = None
   
   def omConsultar(self):
       llOk = self.mxValParam()
       if not llOk:
          return False    
       # 
       llOk = self.mxConsultar()
       return llOk
      
   def omConsultarTodos(self):      
       # Inicia sesion
       llOk = self.mxConsultarTodos()
       return llOk       
  
   def mxValParam(self):
       if not self.pcParam.has_key('CIDCATE'):
          self.pcError = 'PARAMETRO DE CATEGORIA DE PRODUCTO [CIDCATE]'
          return False
       return True  
      
   def mxConsultar(self):
       self.lcIdCate = self.pcParam['CIDCATE']
       client = MongoClient()
       db = client.GAMESTORE
       if self.lcIdCate != '00':
          RS = db.productos.find({"CDESCAT.CIDCATE":self.lcIdCate}).sort("CITNAME", 1)
       else:
          RS = db.productos.find().limit(20).sort([("CDESCAT.CIDCATE", 1), ("CITNAME", 1)])
          #lcSql = "SELECT  nombreprod,precio,marca,stock,imagen,cdescri,cdescor FROM g01prod ORDER BY TMODIFI DESC LIMIT 20"
       if not RS:
          self.pcError = 'no hay productos'
          client.close()
          return False
       i = 1;
       llist = []
       for R in RS:
         lcDicc = {"CITNAME":str(R.get("CITNAME")) ,"CITPRIC":str(R.get("NPRECIO")),"CMARCA":str(R.get("CMARCA")),"CSTOCK":str(R.get("NSTOCK")),"CIMGURL":str(R.get("CIMGURL")),"CDESCRI":R.get("CDESCOR").encode('ascii','ignore'),"CITDESC":R.get("CDESCOR").encode('ascii','ignore')}
         lcDicc = {"prod"+str(i):lcDicc }
         llist.append(lcDicc)
         i +=1
       self.pcData = json.dumps(llist)
       print ("Productos retornados")
       client.close()
       return True

   def mxConsultarTodos(self):
       client = MongoClient()
       db = client.GAMESTORE
       RS = db.productos.find()
       if not RS:
          self.pcError = 'no hay productos'
          client.close()
          return False
       self.pcData = dumps(RS)
       client.close()
       print ("Productos retornados")
       return True
 

 
