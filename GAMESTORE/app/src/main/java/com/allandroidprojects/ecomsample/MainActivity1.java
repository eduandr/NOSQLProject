package com.allandroidprojects.ecomsample;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.allandroidprojects.ecomsample.utility.Connection;
import com.allandroidprojects.ecomsample.utility.ImageUrlUtils;
import com.allandroidprojects.ecomsample.utility.MyDialogFragment3;
import com.google.gson.Gson;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalItem;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalPaymentDetails;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

public class MainActivity1 extends AppCompatActivity {

// private static final String TAG = "paymentdemoblog";
	/**
	 * - Set to PaymentActivity.ENVIRONMENT_PRODUCTION to move real money.
	 *
	 * - Set to PaymentActivity.ENVIRONMENT_SANDBOX to use your test credentials
	 * from https://developer.paypal.com
	 *
	 * - Set to PayPalConfiguration.ENVIRONMENT_NO_NETWORK to kick the tires
	 * without communicating to PayPal's servers.
	 */
// private static final String CONFIG_ENVIRONMENT =
// PayPalConfiguration.ENVIRONMENT_NO_NETWORK;
	private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;

	// note that these credentials will differ between live & sandbox
// environments.
	private static final String CONFIG_CLIENT_ID = "AYtcsHbbQLjDzd1Ci3vFZr6ncSCq53Mch4cSxswcGZJNK6XNC_g4j-WgPgG8NhhOQ-drm7bWCvT_aq1C";

	private static final int REQUEST_CODE_PAYMENT = 1;
	private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;

	private static PayPalConfiguration config = new PayPalConfiguration()
			.environment(CONFIG_ENVIRONMENT).clientId(CONFIG_CLIENT_ID)
// The following are only used in PayPalFuturePaymentActivity.
			.merchantName("Hipster Store").merchantPrivacyPolicyUri(
					Uri.parse("https://www.example.com/privacy"))
			.merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));

	PayPalPayment thingToBuy;
    public final static boolean isValidEmail(String emal) {
        if (emal.indexOf("@")> 0 && emal.indexOf(".com")> 0)
            return false;
        if (emal.isEmpty())
            return true;
        return true;
    }
    public boolean valParam ()
    {
        TextView input_name = (TextView) findViewById(R.id.input_name);
        TextView input_email = (TextView) findViewById(R.id.input_email);
        TextView input_direction = (TextView) findViewById(R.id.input_direction);
        TextView input_phone = (TextView) findViewById(R.id.input_name);
        if (input_name.getText().toString().matches("") ||input_direction.getText().toString().matches("") ||input_phone.getText().toString().matches("")  || isValidEmail(input_email.getText().toString()))
        {
            MyDialogFragment3 myDialogFragment3 = new MyDialogFragment3("Error", "Debes llenar correctamente todos los campos", (Activity) MainActivity1.this);
            myDialogFragment3.show(getSupportFragmentManager(), "MyDialogFragmentTag");
            return false;
        }
        else
            return true;
    }
	ArrayList<Product> cartlistImageUri;
	PayPalItem[] ppis;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main1);

		Intent intent = new Intent(this, PayPalService.class);
		intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
		startService(intent);
		findViewById(R.id.order).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
                if (valParam()){
                    ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
                    cartlistImageUri =imageUrlUtils.getCartListImageUri();
                    ppis = new PayPalItem[cartlistImageUri.size()];
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
                    thingToBuy = new PayPalPayment(new BigDecimal(String.valueOf(PayPalItem.getItemTotal(ppis))), "USD", "Total a pagar:", PayPalPayment.PAYMENT_INTENT_SALE);
                    thingToBuy.items(ppis);
                    thingToBuy.paymentDetails(pd);

                    Intent intent = new Intent(MainActivity1.this,PaymentActivity.class);

                    intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
                    startActivityForResult(intent, REQUEST_CODE_PAYMENT);
                }
            }
    });

	}

	public void onFuturePaymentPressed(View pressed) {
		Intent intent = new Intent(MainActivity1.this,PayPalFuturePaymentActivity.class);
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
                        TextView input_name = (TextView) findViewById(R.id.input_name);
                        TextView input_email = (TextView) findViewById(R.id.input_email);
                        TextView input_direction = (TextView) findViewById(R.id.input_direction);
                        TextView input_phone = (TextView) findViewById(R.id.input_name);
                        Connection con = new Connection();
						Gson gson = new Gson();
						String json = gson.toJson(cartlistImageUri);
						con.compra(input_name.getText().toString(),input_email.getText().toString(),input_direction.getText().toString(),input_phone.getText().toString(),json, String.valueOf(PayPalItem.getItemTotal(ppis)));
						Toast.makeText(getApplicationContext(), "Order placed",Toast.LENGTH_LONG).show();

					} catch (JSONException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
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
		stopService(new Intent(this, PayPalService.class));
		super.onDestroy();
	}
}