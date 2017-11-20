 <?php
// Conexion a Base de Datos
class CSql {
   public $pcError;
   protected $h;

   public function __construct() {
      $this->pcError = null;
   }

   public function omDisconnect() {
      $this->omExec("COMMIT;");
      pg_close($this->h);
   }

   public function omConnect() {
      //$lcConStr = "host=localhost dbname=UCSMOCRRII port=5432 user=postgres password=root";
      $lcConStr = "host=localhost dbname=UCSMOCRRII port=5432 user=postgres password=postgres";
      @$this->h = pg_connect($lcConStr)  or die("Can't connect to database".pg_last_error());;
      if (!$this->h) {
         $this->pcError = "No se pudo conectar a la base de datos";
         return false;
      }
      $this->omExec("BEGIN;");
      return true;
   }

   public function omExec($p_cSql) {
      $lcSql = substr(strtoupper(trim($p_cSql)), 0, 6);
      if ($lcSql === "SELECT") {
         $this->pnNumRow = 0;
         $RS = pg_query($this->h, $p_cSql);
         if (!($RS)) {
            $this->pcError = "Error al ejecutar comando SQL";
            return false;
         }
         $this->pnNumRow = pg_num_rows($RS);
         return $RS;
      } else {
         @$RS = pg_query($this->h, $p_cSql);
         if (pg_affected_rows($RS) == 0)
            if (!($RS)) {
               $this->pcError = "La operacion no afecto a ninguna fila";
               return false;
            }
         return true;
      }
   }

   public function fetch($RS) {
      return pg_fetch_row($RS);     
   }
   
   public function rollback() {
      $this->omExec("ROLLBACK;");
   }
}

/*class CSql1 extends CBase {
   public $pnNumRow;
   protected $h, $DB;

   public function __construct() {
      parent::__construct();
      $this->DB = 'OR';
   }

   public function omDisconnect() {
      if ($this->DB == 'PG') {
         pg_close($this->h);
      } else if ($this->DB == 'OR') {
         oci_commit($this->h);
         oci_close($this->h);
      }
   }

   public function omConnect($p_nDB = 1) {
      if ($this->DB == 'PG') {
         $lcConStr = "host=localhost dbname=PRail port=5432 user=postgres password=root";
         try {
            @$this->h = pg_connect($lcConStr);// or die ("Error de conexion. ". pg_last_error());
         } catch (Exception $e){   
            echo $e->getMessage();
         }
         if (!$this->h) {
            $this->pcError = "<DATA><ERROR>NO SE PUDO CONECTAR A LA BASE DE DATOS</ERROR></DATA>";
            return false;
         }
      } else if ($this->DB == 'OR') {
         if ($p_nDB == 1) {
            try {
               echo '<br>ORACLE 1<br>';
               @$this->h = oci_connect('System', 'oracle', 'LOCAL', 'UTF8');
               echo '<br>ORACLE 2<br>';
            } catch (Exception $e){   
               echo $e->getMessage();
            }
         } else {
            try {
               @$this->h = oci_connect('desa07', 'cn01', 'fxph7170', 'UTF8');
            } catch (Exception $e){   
               echo $e->getMessage();
            }
         }
         if (!$this->h) {
            $this->pcError = "<DATA><ERROR>NO SE PUDO CONECTAR A LA BASE DE DATOS</ERROR></DATA>";
            return false;
         }
      }
      return true;
   }

   public function omExec($p_cSql) {
      $lcSql = substr(strtoupper(trim($p_cSql)), 0, 6);
      if ($this->DB == 'PG') {
         if ($lcSql === "SELECT") {
            $this->pnNumRow = 0;
            $RS = pg_query($this->h, $p_cSql);
            if (!($RS)) {
               $this->pcError = "Error al ejecutar comando SQL";
               return false;
            }
            $this->pnNumRow = pg_num_rows($RS);
            return $RS;
         } else {
            @$RS = pg_query($this->h, $p_cSql);
            if (pg_affected_rows($RS) == 0)
               if (!($RS)) {
                  $this->pcError = "<DATA><ERROR>La operacion no afecto a ninguna fila</ERROR></DATA>";
                  return false;
               }
            return true;
         }
     } else if ($this->DB == 'OR') {
         if ($lcSql === "SELECT") {
            $RS = oci_parse($this->h, $p_cSql);
            oci_execute($RS);
            return $RS;
         } else {
            $llOk = true;
            try {
               $r = oci_parse($this->h, $p_cSql);
               $llOk = oci_execute($r, OCI_DEFAULT);
            } catch (Exception $e){ 
               $llOk = false;
            } 
            return $llOk;
         }
      }
   }
   
   public function fetch($RS) {
      if ($this->DB == 'PG') {
         return pg_fetch_row($RS);
     } else if ($this->DB == 'OR') {
         return oci_fetch_array($RS);
      }
   }

   public function rollback() {
       oci_rollback($this->h);
   }
}*/

?>
