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

class CComprar():
   pcParam = None
   lcIdCate = None
   
   def omComprar(self):
       llOk = self.mxValParam()
       if not llOk:
          return False    
       # 
       llOk = self.mxComprar()
       return llOk

  
   def mxValParam(self):
       return True  
      
   def mxComprar(self):
       client = MongoClient()
       db = client.GAMESTORE
       _id = db.compras.insert_one(self.pcParam).inserted_id
       if not _id:
          self.pcError = '{"ERROR":"SIN DATOS"}'
          client.close()
          return False
       else:
          self.pcData =  '{"OK":"'+str(_id)+'"}'
          client.close()
          return True


 

 
