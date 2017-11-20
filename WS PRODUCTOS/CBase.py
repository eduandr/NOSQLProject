import datetime
import hashlib
import psycopg2
import json
from CSql import *
from abc import ABCMeta, abstractmethod
from datetime import date, timedelta 
import os 


class CBase:
    __metaclass__ = ABCMeta

    def __init__(self):
        self.pcError   = None
        self.loSql     = None

    @abstractmethod  
    def mxEjecutarConsulta(self):
      pass

    def omLog(self,p_cRequest, p_cResponse):
       lcDir =  os.path.dirname(os.path.realpath(__file__))
       text_file = open(lcDir+"\log.txt", "a+")
       text_file.write(str(datetime.datetime.now()) + " Consulta a: " + str(self.__class__.__name__) + '\n')
       text_file.write("Solicitud: " + p_cRequest + '\n')
       text_file.write("Respuesta: "+p_cResponse + '\n')
       text_file.write('\n\n')
       text_file.close()    


    def omInitConsulta(self):
       llOk = self.mxValParam()
       if not llOk:
          return False
       # Conectar base de datos
       self.loSql = CSql()
       llOk = self.loSql.omConnect()
       if not llOk:
          self.pcError = self.loSql.pcError
          return False       
       # Inicia sesion
       llOk = self.mxEjecutarConsulta()
       self.loSql.omDisconnect()
       return llOk


    
   


