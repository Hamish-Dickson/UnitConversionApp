package uk.ac.stir.cs.yh.unitconvassignment;

import android.os.Bundle;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.text.DecimalFormat;

public class PageTwoFragment extends Fragment {
    private FragmentViewModel viewModel;
    private Button calcButton0, calcButton1, calcButton2, calcButton3, calcButton4,
            calcButton5, calcButton6, calcButton7, calcButton8, calcButton9,
            calcButtonC, calcButtonEnter;
    private EditText inputField, resultField;
    private String unitFrom, unitTo;
    private TextView lblConvertFrom, lblConvertTo;
    private FeedReaderDbHelper dbHelper;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //here we can initialise the ViewModel and database helper
        viewModel = ViewModelProviders.of(requireActivity()).get(FragmentViewModel.class);
        dbHelper = new FeedReaderDbHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.page2_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //at this point in the android lifecycle, we can set up our UI elements
        assignFields(view);
        assignListeners();
    }

    /**
     * appends a number to the end of the input field
     *
     * @param i the number to be added
     */
    private void input(int i) {
        //appends the text using a StringBuilder
        inputField.setText(new StringBuilder().append(inputField.getText().toString()).append(i).toString());
    }

    /**
     * clears the user input field and results field
     */
    private void clearInput() {
        inputField.setText(null);
        resultField.setText(null);
    }

    /**
     * converts the number in the input field to the desired unit and displays the output
     */
    private void convert() {
        double ratio = getRatio();

        //formatting in this format allows a user friendly and consumable number.
        // 7 decimal places provides enough information but not too much as to be unreadable
        DecimalFormat df = new DecimalFormat("#,###.#######");
        df.setGroupingSize(3); //this means that every 3 numbers will be separated using a comma ','

        if (inputField.getText().toString().equals("")) {//prevents exception when no input
            resultField.setText(getResources().getString(R.string.lblNoValue));
        } else {
            try {
                //concise inline conversion that formats using the decimal format and appends the unit to the end of the number. i.e "7.5 Kilometres"
                resultField.setText(new StringBuilder().append(df.format(ratio * Integer.valueOf(inputField.getText().toString()))).append(" ").append(unitTo).toString());
            } catch (Exception e) {
                //sometimes the provided number becomes too large to calculate
                resultField.setText(getResources().getString(R.string.errTooBig));
            }
        }
    }

    /**
     * gets the ratio from the database
     *
     * @return ratio of unit from to unit to
     */
    private double getRatio() {
        double ratio = 0;
        if (unitFrom.equals(unitTo)) ratio = 1;//if the same unit is selected for from and to, the ratio is 1
        else ratio = dbHelper.getRatio(dbHelper.getWritableDatabase(), unitFrom, unitTo); //query the database for the ratio
        return ratio;
    }

    /**
     * initialises the variables within the code to the relevant XML entities
     *
     * @param view the view
     */
    private void assignFields(View view) {
        calcButton0 = view.findViewById(R.id.calcButton0);
        calcButton1 = view.findViewById(R.id.calcButton1);
        calcButton2 = view.findViewById(R.id.calcButton2);
        calcButton3 = view.findViewById(R.id.calcButton3);
        calcButton4 = view.findViewById(R.id.calcButton4);
        calcButton5 = view.findViewById(R.id.calcButton5);
        calcButton6 = view.findViewById(R.id.calcButton6);
        calcButton7 = view.findViewById(R.id.calcButton7);
        calcButton8 = view.findViewById(R.id.calcButton8);
        calcButton9 = view.findViewById(R.id.calcButton9);
        calcButtonC = view.findViewById(R.id.calcButtonC);
        calcButtonEnter = view.findViewById(R.id.calcButtonEnter);
        inputField = view.findViewById(R.id.fieldInput);
        resultField = view.findViewById(R.id.fieldResult);
        lblConvertFrom = view.findViewById(R.id.lblConvertFrom);
        lblConvertTo = view.findViewById(R.id.lblConvertTo);
    }

    /**
     * assigns each UI element the relevant listener
     */
    private void assignListeners() {
        calcButton0.setOnClickListener(btn0Listener());
        calcButton1.setOnClickListener(btn1Listener());
        calcButton2.setOnClickListener(btn2Listener());
        calcButton3.setOnClickListener(btn3Listener());
        calcButton4.setOnClickListener(btn4Listener());
        calcButton5.setOnClickListener(btn5Listener());
        calcButton6.setOnClickListener(btn6Listener());
        calcButton7.setOnClickListener(btn7Listener());
        calcButton8.setOnClickListener(btn8Listener());
        calcButton9.setOnClickListener(btn9Listener());
        calcButtonC.setOnClickListener(btnCListener());
        calcButtonEnter.setOnClickListener(btnEnterListener());

        //add listeners for the ViewModel
        viewModel.getUnitFrom().observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                unitFrom = s;
                lblConvertFrom.setText(s);
            }
        });
        viewModel.getUnitTo().observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                unitTo = s;
                lblConvertTo.setText(s);
            }
        });
    }

    //these button listeners are all mostly the same, will just provide JavaDoc for one

    /**
     * button listener
     *
     * @return the required listener
     */
    private View.OnClickListener btn0Listener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Haptic feedback provides useful feedback to the user as a prompt that a key has been pressed
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                input(0);
            }
        };
    }

    private View.OnClickListener btn1Listener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Haptic feedback provides useful feedback to the user as a prompt that a key has been pressed
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                input(1);
            }
        };
    }

    private View.OnClickListener btn2Listener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Haptic feedback provides useful feedback to the user as a prompt that a key has been pressed
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                input(2);
            }
        };
    }

    private View.OnClickListener btn3Listener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Haptic feedback provides useful feedback to the user as a prompt that a key has been pressed
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                input(3);
            }
        };
    }

    private View.OnClickListener btn4Listener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Haptic feedback provides useful feedback to the user as a prompt that a key has been pressed
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                input(4);
            }
        };
    }

    private View.OnClickListener btn5Listener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Haptic feedback provides useful feedback to the user as a prompt that a key has been pressed
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                input(5);
            }
        };
    }

    private View.OnClickListener btn6Listener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Haptic feedback provides useful feedback to the user as a prompt that a key has been pressed
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                input(6);
            }
        };
    }

    private View.OnClickListener btn7Listener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Haptic feedback provides useful feedback to the user as a prompt that a key has been pressed
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                input(7);
            }
        };
    }

    private View.OnClickListener btn8Listener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Haptic feedback provides useful feedback to the user as a prompt that a key has been pressed
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                input(8);
            }
        };
    }

    private View.OnClickListener btn9Listener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Haptic feedback provides useful feedback to the user as a prompt that a key has been pressed
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                input(9);
            }
        };
    }

    private View.OnClickListener btnCListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Haptic feedback provides useful feedback to the user as a prompt that a key has been pressed
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                clearInput();
            }
        };
    }

    private View.OnClickListener btnEnterListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Haptic feedback provides useful feedback to the user as a prompt that a key has been pressed
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                convert();
            }
        };
    }
}
