package com.natife.assotiation.initgame;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.natife.assotiation.R;

import java.util.List;
import java.util.Locale;

public class InitGameActivity extends AppCompatActivity implements InitGameContract.View {

    private RecyclerView recyclerPlayers;
    private ImageView back;
    private ImageView settings;
    private RelativeLayout btnAddPlayer;
    private RelativeLayout btnNext;
    private TextView textBtnNext;
    private PlayersAdapter adapterPlayers;
    private View viewRadioButton;
    private TextView textSelection;
    private InitGameContract.Presenter mPresenter;
    private OnItemVoiceIconListener onItemVoiceIconListener;
    private EditText nameForVoiceTemp;
    private final int VOICE_RECOGNIZER = 1000;
    private RadioButton radio_easy;
    private RadioButton radio_normal;
    private RadioButton radio_hard;
    private final static int LEVEL_EASE = 1;
    private final static int LEVEL_NORMAL = 2;
    private final static int LEVEL_HARD = 3;

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, InitGameActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Создаём Presenter и в аргументе передаём ему this - эта Activity расширяет интерфейс InitGameContract.View
        mPresenter = new InitGamePresenter(this);

        initView();

        recyclerPlayers.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapterPlayers = new PlayersAdapter(InitGameActivity.this, onItemVoiceIconListener);
        recyclerPlayers.setAdapter(adapterPlayers);

        ItemTouchHelper.Callback callback = new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int swipedPosition = viewHolder.getAdapterPosition();

                if (adapterPlayers.getItemCount() > 3) {
                    adapterPlayers.deleteFromListAdapter(swipedPosition);
                }
            }

            @Override
            public int getSwipeDirs(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder holder) {
                return recyclerView.getAdapter().getItemCount() > 3 ? super.getSwipeDirs(recyclerView, holder) : 0;
            }
        };
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerPlayers);

        mPresenter.initPlayerList();
    }//onCreate


    private void initView() {
        recyclerPlayers = findViewById(R.id.recyclerViewListPlayer);
        textSelection = findViewById(R.id.textSelection);
        back = findViewById(R.id.back);
        settings = findViewById(R.id.settings);
        btnAddPlayer = findViewById(R.id.buttonAddPlayer);
        btnNext = findViewById(R.id.buttonNext);
        textBtnNext = findViewById(R.id.textBtnNext);
        viewRadioButton = findViewById(R.id.viewRadioButton);
        radio_easy = findViewById(R.id.radio_easy);
        radio_normal = findViewById(R.id.radio_normal);
        radio_hard = findViewById(R.id.radio_hard);

        btnAddPlayer.setOnClickListener(view -> mPresenter.btnAddPlayerClicked());
        btnNext.setOnClickListener(view -> mPresenter.btnNextClicked(checkDifficultLevel()));
        back.setOnClickListener(view -> mPresenter.btnBackClicked());
        settings.setOnClickListener(view -> mPresenter.btnSettingsClicked());

        onItemVoiceIconListener = new OnItemVoiceIconListener() {
            @Override
            public void onItemVoiceIconClick(int position, EditText editText) {
                // call the voice dialing activity
                nameForVoiceTemp = editText;
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.getDefault());
                try {
                    startActivityForResult(intent, VOICE_RECOGNIZER);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(InitGameActivity.this,
                            getResources().getString(R.string.error_voice_not_support), Toast.LENGTH_SHORT).show();
                }
            }
        };
    }//initView

    private int checkDifficultLevel() {
        int levelDifficult = LEVEL_EASE;
        if (radio_easy.isChecked()) levelDifficult = LEVEL_EASE;
        if (radio_normal.isChecked()) levelDifficult = LEVEL_NORMAL;
        if (radio_hard.isChecked()) levelDifficult = LEVEL_HARD;
        return levelDifficult;
    }


    @Override
    public void showListPlayers(List<String> listName, List<Integer> listColor) {
        adapterPlayers.setData(listName, listColor);
    }

    @Override
    public Context contextActivity() {
        return this;
    }

    @Override
    public void showSettingsDialog() {
        DialogSettings dialogSettings = new DialogSettings();
        dialogSettings.show(getSupportFragmentManager(), "dialogSettings");
    }

    @Override
    public void changeScreen(boolean flagSetName) {
        recyclerPlayers.setVisibility(flagSetName ? View.GONE : View.VISIBLE);
        viewRadioButton.setVisibility(flagSetName ? View.VISIBLE : View.GONE);
        back.setVisibility(flagSetName ? View.VISIBLE : View.GONE);
        settings.setImageResource(flagSetName ? R.drawable.ic_settings_black_24dp : R.drawable.ic_error_outline_black_24dp);
        btnAddPlayer.setVisibility(flagSetName ? View.GONE : View.VISIBLE);
        textBtnNext.setText(flagSetName ? R.string.text_play : R.string.text_next);
        textSelection.setText(flagSetName ? R.string.text_selection_difficulty_level : R.string.text_selection_name);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case VOICE_RECOGNIZER: {
                // result of voice dialing
                if (resultCode == Activity.RESULT_OK && null != data) {
                    String yourResult = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);
                    nameForVoiceTemp.setText(yourResult);
                }
                break;
            }
        }
    }
}
