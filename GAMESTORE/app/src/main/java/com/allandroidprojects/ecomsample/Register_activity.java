package com.allandroidprojects.ecomsample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.allandroidprojects.ecomsample.utility.ImageUrlUtils;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalItem;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by eduxh on 30/06/2017.
 */

public class Register_activity extends AppCompatActivity {
        private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;

        private static final String CONFIG_CLIENT_ID = "AYtcsHbbQLjDzd1Ci3vFZr6ncSCq53Mch4cSxswcGZJNK6XNC_g4j-WgPgG8NhhOQ-drm7bWCvT_aq1C";

        private static final int REQUEST_CODE_PAYMENT = 1;
        private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;

        private static PayPalConfiguration config = new PayPalConfiguration()
                .environment(CONFIG_ENVIRONMENT).clientId(CONFIG_CLIENT_ID)
                .merchantName("GameStore").merchantPrivacyPolicyUri(
                        Uri.parse("https://www.example.com/privacy"))
                .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));

        PayPalPayment thingToBuy;

      public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
          findViewById(R.id.btn_continue).setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  // Create a basic PayPalPayment.
                  ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
                  final ArrayList<Product> cartlistImageUri =imageUrlUtils.getCartListImageUri();
                  PayPalItem[] ppis = new PayPalItem[cartlistImageUri.size()];
                  PayPalItem ppi1;
                  for (int i = 0; i<cartlistImageUri.size(); i++)
                  {
                      ppi1 = new PayPalItem(cartlistImageUri.get(i).getName(), 1, new BigDecimal(Float.parseFloat(cartlistImageUri.get(i).getPrice())), "USD", "Pid1");
                      ppis[i] = ppi1;
                      Toast.makeText(getBaseContext(),cartlistImageUri.get(i).getName(),Toast.LENGTH_LONG).show();
                  }
                  PayPalPaymentDetails pd = new PayPalPaymentDetails(
                          PayPalItem.getItemTotal(ppis), // price
                          new BigDecimal(0.0), // shipment costs
                          new BigDecimal(0) // taxes
                  );

                  thingToBuy = new PayPalPayment(new BigDecimal(String.valueOf(PayPalItem.getItemTotal(ppis))), "PEN", "Total a pagar:", PayPalPayment.PAYMENT_INTENT_SALE);
                  thingToBuy.items(ppis);
                  thingToBuy.paymentDetails(pd);

                  Intent intent = new Intent(Register_activity.this,PaymentActivity.class);

                  intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
                  startActivityForResult(intent, REQUEST_CODE_PAYMENT);
              }
          });

      }

    public void onFuturePaymentPressed(View pressed) {
        Intent intent = new Intent(Register_activity.this,PayPalFuturePaymentActivity.class);
        startActivityForResult(intent, REQUEST_CODE_FUTURE_PAYMENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        System.out.println(confirm.toJSONObject().toString(4));
                        System.out.println(confirm.getPayment().toJSONObject().toString(4));

                        Toast.makeText(getApplicationContext(), "Order placed",Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                System.out.println("The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                System.out.println("An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        } else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth = data.getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.i("FuturePaymentExample", auth.toJSONObject().toString(4));

                        String authorization_code = auth.getAuthorizationCode();
                        Log.i("FuturePaymentExample", authorization_code);

                        sendAuthorizationToServer(auth);
                        Toast.makeText(getApplicationContext(),	"Future Payment code received from PayPal",	Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        Log.e("FuturePaymentExample","an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("FuturePaymentExample", "The user canceled.");
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("FuturePaymentExample",
                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
            }
        }
    }

    private void sendAuthorizationToServer(PayPalAuthorization authorization) {

    }

    public void onFuturePaymentPurchasePressed(View pressed) {
// Get the Application Correlation ID from the SDK
        String correlationId = PayPalConfiguration.getApplicationCorrelationId(this);

        Log.i("FuturePaymentExample", "Application Correlation ID: "+ correlationId);

// TODO: Send correlationId and transaction details to your server for
// processing with
// PayPal...
        Toast.makeText(getApplicationContext(),	"App Correlation ID received from SDK", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
// Stop service when done
        //stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}
