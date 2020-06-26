package com.example.webviewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private EditText editText;
    private ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView=findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        editText=(EditText)findViewById(R.id.url);
        imageButton=(ImageButton)findViewById(R.id.search_btn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url=editText.getText().toString();
                webView.loadUrl(url);
            }
        });

        if(getIntent().getData()!=null){
            webView.loadUrl(getIntent().getData().toString());
        }
        else{
            Intent intent=getIntent();
            if(intent.getStringExtra("url")!=null){
                editText.setText(intent.getStringExtra("url"));
                webView.loadUrl(intent.getStringExtra("url"));
            }
            else {
                editText.setText("https://google.com");
                webView.loadUrl("http://google.com");
            }
            startService(new Intent(this,MyBackgroundService.class));
        }

    }


    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }
        else {
            super.onBackPressed();
        }
    }

    public void openURL(String string){
        webView.loadUrl(string);
    }
}