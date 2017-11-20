# -*- coding: utf-8 -*-
import datetime
import xml.etree.ElementTree as ET
import json
import collections
from collections import namedtuple
from pymongo import *
import pprint
from bson.json_util import dumps
import re

class CBusqueda():
   pcParam = None
   lcBusq = None
  
   def omBuscar(self):
       llOk = self.mxValParam()
       if not llOk:
          return False     
       # 
       llOk = self.mxBuscar()
       return llOk
      
   def mxValParam(self):
       if not self.pcParam.has_key('CPARAM'):
          self.pcError = 'PARAMETRO DE BUSQUEDA[CPARAM]'
          return False
       return True  
      
   def mxBuscar(self):    
       self.lcBusq = self.pcParam['CPARAM']
       RS = []
       if self.lcBusq != "":
          client = MongoClient()
          db = client.GAMESTORE
          rgx = re.compile('.*'+self.lcBusq+'.*', re.IGNORECASE)
          RS = db.productos.find({'CITNAME': rgx}).distinct('CITNAME')
          RS = db.productos.find({"$or":[{'CITNAME': rgx},{'CMARCA': rgx},{'CMODELO': rgx}, {"CDESCAT.CNOMCAT": rgx}]})
       if not RS:
          self.pcError = 'no hay productos que mostrar' 
          return False
       i = 1;
       llist = []
       for R in RS:
         R = dict(R)
         lcDicc = {"CITNAME":str(R.get("CITNAME")) ,"CITPRIC":str(R.get("NPRECIO")),"CMARCA":str(R.get("CMARCA")),"CSTOCK":str(R.get("NSTOCK")),"CIMGURL":str(R.get("CIMGURL")),"CDESCRI":R.get("CDESCOR").encode('ascii','ignore'),"CITDESC":R.get("CDESCOR").encode('ascii','ignore')}
         lcDicc = {"prod"+str(i):lcDicc }
         #print "******",type (lcDicc),lcDicc, "******" 
         llist.append(lcDicc)
         i +=1
       self.pcData = json.dumps(llist)
       #print self.pcData
       print ("Productos retornados",len(llist))
       client.close()
       return True



