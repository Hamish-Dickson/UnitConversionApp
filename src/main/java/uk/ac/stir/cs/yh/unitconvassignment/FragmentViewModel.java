package uk.ac.stir.cs.yh.unitconvassignment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * View model implementation for communication between fragments.
 * Web tutorial used and adapted from: https://androidwave.com/fragment-communication-using-viewmodel
 */
public class FragmentViewModel extends ViewModel {
    //ViewModel class uses MutableLiveData to provide real time communication between fragments whenever the data is updated
    private MutableLiveData<String> unitFrom = new MutableLiveData<>();
    private MutableLiveData<String> unitTo = new MutableLiveData<>();

    /**
     * Sets the unit from and unit to
     *
     * @param unitFrom the unit being converted from
     * @param unitTo   the unit being converted to
     */
    public void setUnits(String unitFrom, String unitTo) {
        this.unitFrom.setValue(unitFrom);
        this.unitTo.setValue(unitTo);
    }

    /**
     * @return the unit from
     */
    public LiveData<String> getUnitFrom() {
        return unitFrom;
    }

    /**
     * @return the unit to
     */
    public LiveData<String> getUnitTo() {
        return unitTo;
    }
}
