package uk.ac.stir.cs.yh.unitconvassignment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

/**
 * Fragment implementation for page 1, or the unit selection page
 */
public class PageOneFragment extends Fragment {
    private FragmentViewModel viewModel; //view model for communication between fragments
    private Spinner categorySpinner, unitFromSpinner, unitToSpinner;
    private String unitFrom, unitTo, unitCategory = "distances";

    //Keys for use with persistent data
    private final String UNIT_FROM_KEY = "UNITFROM", UNIT_TO_KEY = "UNITTO", UNIT_CATEGORY_KEY = "UNITCATEGORY";
    //Values for use with persistent data
    private int unitCategoryInt, unitFromInt, unitToInt;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //lookup the view model at this stage
        viewModel = ViewModelProviders.of(requireActivity()).get(FragmentViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.page1_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //at this point in the lifecycle, we can set up our UI elements
        //but should not restore any saved data
        assignButtons(view);
        assignListeners();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {//if this is not a fresh load of the fragment
            //get the persisted data
            unitCategoryInt = savedInstanceState.getInt(UNIT_CATEGORY_KEY);
            unitFromInt = savedInstanceState.getInt(UNIT_FROM_KEY);
            unitToInt = savedInstanceState.getInt(UNIT_TO_KEY);

            //restore backend values
            setSpinners(unitCategoryInt);
            setUnits(unitFromInt, false);
            setUnits(unitToInt, true);

            //restore UI elements
            unitFromSpinner.setSelection(unitFromInt);
            unitToSpinner.setSelection(unitToInt);
        }
    }

    /**
     * method to instantiate the UI elements to reference XML file elements
     *
     * @param view the current View
     */
    private void assignButtons(View view) {
        categorySpinner = view.findViewById(R.id.categorySpinner);
        unitFromSpinner = view.findViewById(R.id.fromSpinner);
        unitToSpinner = view.findViewById(R.id.toSpinner);
    }

    /**
     * method to assign each UI element their required listener
     */
    private void assignListeners() {
        categorySpinner.setOnItemSelectedListener(categorySpinnerListener());
        unitFromSpinner.setOnItemSelectedListener(fromSpinnerListener());
        unitToSpinner.setOnItemSelectedListener(toSpinnerListener());
    }

    /**
     * sets the "To" and "From" spinners based on the selection within the "Category" spinner
     *
     * @param position the selection position within the category spinner
     */
    private void setSpinners(int position) {
        String[] categories = getResources().getStringArray(R.array.Categories);
        String category = categories[position];//category is the category at index position in the Categories array
        ArrayAdapter<String> spinnerArray;


        spinnerArray = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //both spinners can use the same adapter as they are displaying the same option set
        unitFromSpinner.setAdapter(spinnerArray);
        unitToSpinner.setAdapter(spinnerArray);

        //select the category based on the position array
        if (position == 0) {
            spinnerArray.addAll(getResources().getStringArray(R.array.Distances));
        }
        if (position == 1) {
            spinnerArray.addAll(getResources().getStringArray(R.array.Weights));
        }
        if (position == 2) {
            spinnerArray.addAll(getResources().getStringArray(R.array.Volumes));
        }
        if (position == 3) {
            spinnerArray.addAll(getResources().getStringArray(R.array.Currencies));
        }

        unitCategory = category;
        spinnerArray.notifyDataSetChanged();
    }

    /**
     * sets the units being converted from and to, using a boolean flag to determine which
     *
     * @param position the spinner position of the spinner that was selected
     * @param toOrFrom Whether the unit to or unit from is being changed. True = To, False = From
     */
    private void setUnits(int position, boolean toOrFrom) {
        String[] units;
        String selectedUnit;

        //Using a switch over the current category we can lookup the required String array and set the values accordingly
        switch (unitCategory) {
            case "Distance":
                units = getResources().getStringArray(R.array.Distances);
                break;
            case "Weight":
                units = getResources().getStringArray(R.array.Weights);
                break;
            case "Volumes":
                units = getResources().getStringArray(R.array.Volumes);
                break;
            case "Currency":
                units = getResources().getStringArray(R.array.Currencies);
                break;
            default://Should not occur
                units = getResources().getStringArray(R.array.Distances);
                break;
        }

        selectedUnit = units[position];//the unit selected is the unit in the array at the position selected from the spinner

        if (toOrFrom) { //if the unit to is being changed
            unitTo = selectedUnit;
        } else { //if the unit from is being changed
            unitFrom = selectedUnit;
        }

        //we then update the ViewModel with the new units, so fragment 2 updates its values
        viewModel.setUnits(unitFrom, unitTo);
    }

    /**
     * provides the required listener for the "from" spinner
     *
     * @return a listener that will update units based on selection
     */
    private AdapterView.OnItemSelectedListener fromSpinnerListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unitFromInt = position;
                setUnits(position, false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
    }

    /**
     * provides the required listener for the "to" spinner
     *
     * @return a listener that will update units based on selection
     */
    private AdapterView.OnItemSelectedListener toSpinnerListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unitToInt = position;
                setUnits(position, true);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    /**
     * provides a listener with required functionality for the "category" spinner
     *
     * @return the required listener
     */
    private AdapterView.OnItemSelectedListener categorySpinnerListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                unitCategoryInt = position;
                setSpinners(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    /**
     * Saves the state of the fragment on exit, to be used on resumption
     *
     * @param outState Bundle in which to place your saved state.
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        //store and save the positions of the currently selected spinner items
        outState.putInt(UNIT_CATEGORY_KEY, unitCategoryInt);
        outState.putInt(UNIT_FROM_KEY, unitFromInt);
        outState.putInt(UNIT_TO_KEY, unitToInt);
    }
}
