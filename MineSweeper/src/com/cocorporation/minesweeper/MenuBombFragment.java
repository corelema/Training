package com.cocorporation.minesweeper;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MenuBombFragment extends Fragment {
	private EditText mNumberOfMines;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setHasOptionsMenu(true);
        
    }
	
    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_menue_bomb, parent, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        	getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        
        mNumberOfMines = (EditText)v.findViewById(R.id.editTextMines);
        mNumberOfMines.setText(String.valueOf(ConfigSingleton.getInstance().getNbOfMines()));
        System.out.println("number of mines = " + String.valueOf(ConfigSingleton.getInstance().getNbOfMines()));
        mNumberOfMines.setOnKeyListener(new View.OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
		        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
		                (keyCode == KeyEvent.KEYCODE_ENTER)) {
		        	ConfigSingleton.getInstance().setNbOfMines(Integer.parseInt(mNumberOfMines.getText().toString()));
	        		System.out.println("New number of mines = " + mNumberOfMines.getText().toString());
	        		return true;
	            }
		            return false;
			}
		});
        
        return v; 
    }
    
    @TargetApi(11)
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
		switch (item.getItemId()){
		case android.R.id.home:
			Toast.makeText(getActivity().getApplicationContext(), "Coucou, ca marche", Toast.LENGTH_SHORT).show();
		    
			getFragmentManager().popBackStack();
		    
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
    	
    }
}
