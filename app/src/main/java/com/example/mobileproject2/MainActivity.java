package com.example.mobileproject2;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button currentSelectedButton;
    private List<Integer> playedSequence = new ArrayList<>();
    private static final String SequenceTxt = "sequence.txt";
    private Handler sequenceHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button additionalButton1 = findViewById(R.id.additional_button1);
        Button additionalButton2 = findViewById(R.id.additional_button2);

        additionalButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSequenceToFile();
            }
        });

        additionalButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSequenceFromFile();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(R.raw.do_stretched, button1);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(R.raw.re_stretched, button2);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(R.raw.mi_stretched, button3);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(R.raw.fa_stretched, button4);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(R.raw.sol_stretched, button5);
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(R.raw.la_stretched, button6);
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(R.raw.si_stretched, button7);
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound(R.raw.do_stretched_octave, button8);
            }
        });
    }

    private void playSound(int soundResourceId, Button button) {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, soundResourceId);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
        setButtonColorToGreen(button);
        playedSequence.add(soundResourceId);
    }

    private void setButtonColorToRed(Button button) {
        button.setBackgroundColor(Color.RED);
    }

    private void setButtonColorToGreen(Button button) {
        if (currentSelectedButton != null) {
            setButtonColorToRed(currentSelectedButton);
        }
        button.setBackgroundColor(Color.GREEN);
        currentSelectedButton = button;
    }
    private void saveSequenceToFile() {

        try (FileOutputStream fos = openFileOutput(SequenceTxt, MODE_PRIVATE)) {
            for (Integer soundResourceId : playedSequence) {
                String line = String.valueOf(soundResourceId) + "\n";
                fos.write(line.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Sequence saved to file");
    }
    private void playSequenceFromFile() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput(SequenceTxt)))) {
            String line;
            int delay = 0;
            while ((line = reader.readLine()) != null) {
                final int soundResourceId = Integer.parseInt(line);
                sequenceHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MediaPlayer mediaPlayer = MediaPlayer.create(MainActivity.this, soundResourceId);
                        mediaPlayer.start();
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                mp.release();
                            }
                        });
                    }
                }, delay);
                delay += 500;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
