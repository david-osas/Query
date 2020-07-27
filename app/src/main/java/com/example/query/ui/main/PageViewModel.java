package com.example.query.ui.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.query.MainActivity;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            switch (input){
                case 1:
                    return "https://en.wikipedia.org/wiki/Special:Search?search="+ MainActivity.queryText +"&go=Go&ns0=1";
                case 2:
                    return "https://www.geeksforgeeks.org/search/?q="+MainActivity.queryText;
                case 3:
                    return "https://www.youtube.com/results?q="+MainActivity.queryText;
                case 4:
                    return "https://stackoverflow.com/search?q="+MainActivity.queryText;
                case 5:
                    return "https://www.quora.com/search?q="+MainActivity.queryText;
                case 6:
                    return "https://www.reddit.com/search/?q="+MainActivity.queryText+"&type=link";
                case 7:
                    return "https://www.programiz.com/search/"+MainActivity.queryText;
                default:
                    return "";
            }
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }
}