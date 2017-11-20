#import psycopg2
import datetime
import xml.etree.ElementTree as ET
import json
import collections
from collections import namedtuple
from pymongo import *
import pprint
from bson.json_util import dumps
from bson.objectid import ObjectId
import re

class CMantenimiento():
   lcObjectId = None 
   pcParam = None
   lcIdCate = None

   def omInsertar(self):
       llOk = self.mxValParamInsertar()
       if not llOk:
          return False     
       llOk = self.mxInsertar()
       return llOk
   
   def omActualizar(self):
       llOk = self.mxValParam()
       if not llOk:
          return False     
       llOk = self.mxActualizar()
       return llOk

   def omEliminar(self):
       llOk = self.mxValParam()
       if not llOk:
          return False     
       llOk = self.mxEliminar()
       return llOk    

   def mxValParamInsertar(self):
      return True
   
   def mxValParam(self):
       if not self.pcParam.has_key('COBJID'):
          self.pcError = 'PARAMETRO DE OBJECT ID [COBJID]'
          return False
       return True  

   def mxInsertar(self):
       client = MongoClient()
       db = client.GAMESTORE
       _id = db.productos.insert_one(self.pcParam).inserted_id
       if not _id:
          self.pcError = '{"ERROR":"SIN DATOS"}'
          client.close()
          return False
       else:
          self.pcData =  '{"OK":"'+str(_id)+'"}'
          client.close()
          return True
      
   def mxActualizar(self):
       self.lcObjectId = self.pcParam['COBJID']
       client = MongoClient()
       db = client.GAMESTORE
       laData = self.pcParam.pop ('COBJID')       
       RS = db.productos.update({'_id':ObjectId(self.lcObjectId)}, self.pcParam, True)
       if RS['nModified'] != 0:
            self.pcData = json.dumps({"OK":RS['nModified']})
            print ("Productos actualizados")
            client.close()
            return True
       else:
          self.pcError = json.dumps({"ERROR":"NO SE ACTUALIZARON ITEMS, SE INSERTO ITEM"})
          client.close()
          return False
       
   def mxEliminar(self):
       self.lcObjectId = self.pcParam['COBJID']
       client = MongoClient()
       db = client.GAMESTORE
       laData = self.pcParam.pop ('COBJID')       
       RS = db.productos.remove({'_id':ObjectId(self.lcObjectId)})
       if RS['n'] != 0:
          self.pcData = json.dumps({"OK":RS['n']})
          print ("Productos eliminados")
          client.close()
          return True
       else:
          self.pcError = json.dumps({"ERROR":"NO SE ELIMINARON ITEMS"})
          client.close()
          return False
 
