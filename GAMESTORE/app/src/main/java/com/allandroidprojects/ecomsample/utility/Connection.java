package com.allandroidprojects.ecomsample.utility;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

public class Connection extends Activity {

    private JSONTask mAuthTask = null;
    JSONObject acc;
    String ipAdr = "http://192.168.0.202:8081";

    public boolean isConnectedToServer(String url, int timeout) {
        return true;
    }

    public JSONArray oferQuery(String p_citdesc) throws JSONException, ExecutionException, InterruptedException {
            acc = new JSONObject();
            acc.put("CIDCATE", p_citdesc);
            //acc.put("CPASSWORD", p_password);
            String url =  ipAdr + "/cqueryOfertas";
            String res = null;
            res = new JSONTask().execute(url).get();
            if (res== null)
                return null;
            JSONArray obj = new JSONArray(res);
            return obj;
    }

    public JSONArray busqueda(String p_pcParam) throws JSONException, ExecutionException, InterruptedException {
        acc = new JSONObject();
        acc.put("CPARAM", p_pcParam);
        //acc.put("CPASSWORD", p_password);
        String url =  ipAdr + "/cbusqueda";
        String res = null;
        res = new JSONTask().execute(url).get();
        if (res== null)
            return null;
        JSONArray obj = new JSONArray(res);
        return obj;
    }
    public JSONObject compra(String p_cNombre, String p_cemail, String p_cdirec, String p_ctelef, String jsonArray, String paymentIntentSale) throws JSONException, ExecutionException, InterruptedException {
        acc = new JSONObject();
        acc.put("CNOMBRE", p_cNombre);
        acc.put("CEMAIL", p_cemail);
        acc.put("CDIRECC", p_cdirec);
        acc.put("CNUMTELF", p_ctelef);
        acc.put("ACOMPRAS", jsonArray);
        acc.put("NTOTAL", paymentIntentSale);
        String url =  ipAdr + "/ccompra";
        String res = null;
        res = new JSONTask().execute(url).get();
        if (res== null)
            return null;
        JSONObject obj = new JSONObject(res);
        return obj;
    }

    public class JSONTask extends AsyncTask<String,String,String> {
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
        protected String doInBackground(String... params) {
            {
                HttpURLConnection con = null;
                BufferedReader br = null;
                try {
                    URL url = new URL(params[0]);
                    con = (HttpURLConnection) url.openConnection();
                    con.setConnectTimeout(3000);
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    con.setRequestProperty("Accept", "application/json");
                    con.setRequestMethod("POST");
                    OutputStream os = con.getOutputStream();
                    os.write(acc.toString().getBytes("UTF-8"));
                    os.close();
                    StringBuilder sb = new StringBuilder();
                    int HttpResult = con.getResponseCode();
                    if (HttpResult == HttpURLConnection.HTTP_OK) {
                        br = new BufferedReader(
                                new InputStreamReader(con.getInputStream(), "UTF-8"));
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        br.close();
                    }
                    return sb.toString();
                } catch (java.net.SocketTimeoutException e) {
                    Log.i("ERROR","CONECTION TIEMOUT");
                    return null;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    if (con != null)
                        con.disconnect();
                    try {
                        if (br != null)
                            br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

       /* @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            textView.setText(result);
            /*if(result.length()<4) {
                textView.setText(result);
                Toast.makeText(MainActivity.this.getApplicationContext(),
                        "Ha Iniciado Sesión Correctamente", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(MainActivity.this.getApplicationContext(),
                        result, Toast.LENGTH_SHORT).show();
        }*/
    }
}
