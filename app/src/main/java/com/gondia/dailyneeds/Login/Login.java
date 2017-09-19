package com.gondia.dailyneeds.Login;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gondia.dailyneeds.MainPage.MainActivity;
import com.gondia.dailyneeds.R;
import com.gondia.dailyneeds.helperClass.FontManager;
import com.gondia.dailyneeds.root.App;

import com.facebook.FacebookSdk;

import javax.inject.Inject;

public class Login extends AppCompatActivity implements LoginActivityMVP.View/*View.OnClickListener,GoogleApiClient.OnConnectionFailedListener */{

    @Inject
    LoginActivityMVP.Presenter presenter;


    private Button login,register;
    private EditText username,password;


    /*UserSharedPreference session;
    private ImageButton glogin;
    public static Boolean flagFB=false;

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    private LoginButton fb;



    private CallbackManager callbackManager;
    private GoogleApiClient mGoogleApiClient;
    private TextView mStatusTextView;
    private ProgressDialog mProgressDialog;
    private TextView skip;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((App) getApplication()).getComponent().inject(this);
        //session = new UserSharedPreference(getApplicationContext());

        login = (Button) findViewById(R.id.btlogin);
        username = (EditText) findViewById(R.id.etusername);
        password = (EditText) findViewById(R.id.etpassword);
        login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                presenter.loginButtonedClicked();
            }
        });
        //glogin = (ImageButton) findViewById(R.id.gplusloginbt);
        //glogin.setOnClickListener(this);
        //login.setOnClickListener(this);

        Typeface tf=Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");
        //TextView text=(TextView)findViewById(R.id.awesome);
       // text.setTypeface(tf);
        Typeface iconFont = FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(findViewById(R.id.awesome), iconFont);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.getCurrentUser();
    }

    @Override
    public String getFirstName() {
        return username.getText().toString();
    }

    @Override
    public String getLastName() {
        return password.getText().toString();
    }

    @Override
    public void showUserNotAvailable() {
        Toast.makeText(this,"Error,the user is not available",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInputError() {
        Toast.makeText(this,"username or password cannot be empty",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showUserSavedMessage() {
        Toast.makeText(this,"User saved",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setFirstName(String fname) {
        username.setText(fname);
    }

    @Override
    public void setLastName(String lname) {
        password.setText(lname);
    }

    @Override
    public void userSuccess() {
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }
        /*

        skip= (TextView) findViewById(R.id.skipToMain);
        skip.setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , this/* OnConnectionFailedListener )
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        callbackManager = CallbackManager.Factory.create();
        fb= (LoginButton) findViewById(R.id.fbbt);


        fb.setReadPermissions(Arrays.asList(
                "public_profile", "email"));
        fb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String accessToken = loginResult.getAccessToken().getToken();

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject jsonObject,
                                                    GraphResponse response) {

                                // Getting FB User Data
                                Bundle facebookData = getFacebookData(jsonObject,getApplicationContext());


                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,first_name,last_name,email,gender");
                request.setParameters(parameters);
                request.executeAsync();
                flagFB=true;
                //getUserDetails(loginResult);
                /*System.out.println("onSuccess "+loginResult);
                loginResult.getRecentlyGrantedPermissions();

                Intent mainLobby = new Intent(Login.this, MainActivity.class);

                startActivity(mainLobby);

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }*/


   /* @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btlogin:
                login();
                break;
            case R.id.gplusloginbt:
                signIn();
                break;
            case R.id.skipToMain:
                startActivity(new Intent(this,MainActivity.class));
                break;
        }
    }

    public void login(){
        String usernamee=username.getText().toString();
        String passworde=password.getText().toString();
        if(usernamee.length()>0 && passworde.length()>0){

            if(usernamee.equals("admin")&&passworde.equals("admin")){
                setSession(usernamee,passworde);
            }
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        // Toast.makeText(this,"in side sign in method",Toast.LENGTH_SHORT);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            // Toast.makeText(this,"on activity result method",Toast.LENGTH_SHORT);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            setSession(acct.getDisplayName(),acct.getEmail());
            //mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            // Toast.makeText(this,"Success",Toast.LENGTH_SHORT);

        } else {
            // Toast.makeText(this,"failed",Toast.LENGTH_SHORT);// Signed out, show unauthenticated UI.
            //updateUI(false);
        }
    }

    public void setSession(String name,String email){
        session.createUserLoginSession(name,email);
        Intent i=new Intent(this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }*/

}
