package com.levitas.texttospeech;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    private TextToSpeech mTts;
    private static final int MY_DATA_CHECK_CODE = 1234;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = new Intent();
        i.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(i,MY_DATA_CHECK_CODE);
    }


    public void speakClicked(View v)
    {
// grab the contents of the text box.
        EditText editText = (EditText) findViewById(R.id.inputText);

        mTts.speak(editText.getText().toString(),
                TextToSpeech.QUEUE_FLUSH, // Drop all pending entries in the playback queue.
                null,
                null);
    }
    public void onInit(int i){
       // mTts.speak("Hello World",TextToSpeech.QUEUE_FLUSH,null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MY_DATA_CHECK_CODE)
        {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS)
            {
                // success, create the TTS instance
                mTts = new TextToSpeech(this, this);
            }
            else
            {
                // missing data, install it
                Intent installIntent = new Intent();
                installIntent.setAction(
                        TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }
    }

    @Override
    protected void onDestroy() {

        // Don't forget to shutdown!
        if (mTts != null)
        {
            mTts.stop();
            mTts.shutdown();
        }
        super.onDestroy();
    }
}
