package svin.beerbowl.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import svin.beerbowl.R;
import svin.beerbowl.utilities.NetworkSingleton;

/**
 * A login screen that offers login via name/password.
 */
public class LoginActivity extends AppCompatActivity {

    private Boolean isRequestPending = false;
    // UI references.
    private EditText mLoginNameView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mLoginNameView = (EditText) findViewById(R.id.loginName);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mLoginNameSignInButton = (Button) findViewById(R.id.login_name_sign_in_button);
        mLoginNameSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);


    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid name, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (isRequestPending) {
            return;
        }

        // Reset errors.
        mLoginNameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String loginName = mLoginNameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid login name.
        if (TextUtils.isEmpty(loginName)) {
            mLoginNameView.setError(getString(R.string.error_field_required));
            focusView = mLoginNameView;
            cancel = true;
        } else if (!isLoginNameValid(loginName)) {
            mLoginNameView.setError(getString(R.string.error_invalid_login_name));
            focusView = mLoginNameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            // TODO remove this when internet comes back

            startActivity(new Intent(LoginActivity.this, HomeScreenActivity.class));
            finish();

            //showProgress(true);
            //sendLoginRequest(loginName, password);
        }
    }

    /**
     * Sends an authentication request to the server.
     * If the info is correct the user is logged in, and this activity is finished.
     * If the info is incorrect the response from the server is displayed.
     * @param loginName Name used for log in.
     * @param password Password used for log in.
     */

    private void sendLoginRequest(final String loginName, final String password) {

        isRequestPending = true;

        // TODO use listener
        // TODO encrypt before sending
        // TODO use tokens
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, NetworkSingleton.getAuth(), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        showProgress(false);
                        isRequestPending = false;

                        Intent mIntent = new Intent(LoginActivity.this, HomeScreenActivity.class);
                        mIntent.putExtra("username", ((EditText) findViewById(R.id.loginName)).getText().toString());
                        mIntent.putExtra("password", ((EditText) findViewById(R.id.password)).getText().toString());

                        startActivity(new Intent(LoginActivity.this, HomeScreenActivity.class));
                        finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Define actions for onErrorResponse
                        showProgress(false);
                        isRequestPending = false;

                        if (error.networkResponse != null) {
                            switch (error.networkResponse.statusCode) {
                                case 401: {
                                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                                    break;
                                }
                                default: {
                                    mPasswordView.setError(getString(R.string.server_login_error));
                                    break;
                                }
                            }
                        }
                        mPasswordView.requestFocus();
                        VolleyLog.e("Error:" + error.getMessage());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("username", loginName);
                params.put("password", password);

                return params;
            }
        };

        NetworkSingleton.getInstance(getApplicationContext()).AddToRequestQueue(jsObjRequest);

    }

    /**
     * The logic for testing loginName before sending.
     * @param loginName Input loginName
     * @return True if loginName obeys the logic, else false.
     */
    private boolean isLoginNameValid(String loginName) {
        return !loginName.isEmpty();
    }

    /**
     * The logic for testing password before sending.
     * @param password Input password.
     * @return returns True if password obeys the logic, else false.
     */
    private boolean isPasswordValid(String password) {
        return password.length() > 3;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}

