package com.cocorporation.minesweeper;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainMenueFragment extends Fragment {

	private Button mBombButton;
	private Button mBouncingBallButton;
	private static final String TAG = "MainMenueFragment";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Log.i(TAG, "Fragment created");
        //getActivity().setTitle(R.string.main_menu);
    }
	
    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_menue, parent, false);

        mBombButton = (Button)v.findViewById(R.id.button_menue_bomb);
        mBombButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getActivity().setTitle(R.string.menu_bomb);
				
				final FragmentTransaction ft = getFragmentManager().beginTransaction(); 
				ft.replace(R.id.fragmentContainer, new MenuBombFragment(), "NewFragmentTag"); 
				ft.addToBackStack("NewFragmentTag"); //faudrait que je verifie si ca marche bien
				ft.commit(); 
			}
		});
        
        mBouncingBallButton = (Button)v.findViewById(R.id.button_launch_bouncing_ball);
        mBouncingBallButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
                Intent i = new Intent(getActivity(), BouncingBallActivity.class);
                startActivity(i);
			}
		});
        	
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
        	getActivity().getActionBar().setDisplayHomeAsUpEnabled(false);
        }
        
        return v; 
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	System.out.println("onResume");
    	getActivity().setTitle(R.string.main_menu);
    }
    
    @Override
	public void onDetach(){
    	super.onDetach();
    	System.out.println("onDetach");
    }
    
    @Override
	public void onStart(){
    	super.onStart();
    	System.out.println("onStart");
    }
    
    @Override
	public void onPause(){
    	super.onPause();
    	System.out.println("onPause");
    }
    
    @Override
	public void onStop(){
    	super.onStop();
    	System.out.println("onStop");
    }
    
    @Override
	public void onDestroyView(){
    	super.onDestroyView();
    	System.out.println("onDestroyView");
    }
    
    @Override
	public void onDestroy(){
    	super.onDestroy();
    	System.out.println("onDestroy");
    }
    
    @Override
	public void onAttach(Activity activity) {
    	super.onAttach(activity);
    	System.out.println("onDetach");
    }
	
}
