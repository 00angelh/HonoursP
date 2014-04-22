package cumaps;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.example.testapp.R;
import pathfind.*;

import adapter.TabMenu;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.text.Spanned;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends  FragmentActivity implements
ActionBar.TabListener {

	
	private ViewPager viewPager;
    private TabMenu mAdapter;
    private ActionBar actionBar;
    private NfcAdapter mNfcAdapter;
    private String nfcStatus;
    public static final String MIME_TEXT_PLAIN = "text/plain";
	public static final String TAG = "NfcDemo";
	
	
    
    // Tab titles
    private String[] tabs = { "Home", "Map","Places","NFC"};
    
	int changed = 0;
	Bitmap tempBMP; //Contains the drawing image
	File outputMap;  //Output file location
	int swiped =0; 
	private ProgressDialog pd; //Variable that's used for displaying a loading icon while images generate
	List<Map<String, String>> settingList = new ArrayList<Map<String,String>>(); //For the settings list menu
	List<Map<String,String>> locationsList = new ArrayList<Map<String,String>>();
	SimpleAdapter simpleAdpt; //Used for the list menu
	Handler mHideHandler;
	Runnable mHideRunnable;
	int root; //Contains the integer for the view of current view
	CarletonMap map;
	int lastBuildingSelected;
	String menuType;
	Node sourceLocation; 
	String srcLocation;
	String destLocation;
	int newScrollX,newScrollY; //Contain the position of the webview
	int zoomPercent; //Contains the zoom amount for webview
	WebView mapV;
	float zoomPercentX; //Contains the zoom amount for webview
	float zoomPercentY; //Contains the zoom amount for webview
	boolean nfcScanned = false;
	int tabSelected;
	String currentNFCSource;
	String currentNFCDestination;
	boolean streetView = true; //If it's true, the map screen displays the general view, otherwise the satellite
	String mapUrl;
	private Spinner srcSpinner, destSpinner;
	View popupSpinners;
	
	public void findNFCPath(View view){
		srcLocation = (String) ((TextView)findViewById(R.id.nfc_source_text)).getText();
		destLocation = (String) ((TextView)findViewById(R.id.nfc_destination_text)).getText();
		
		
		
		Toast.makeText(getBaseContext(),"Path set from " + srcLocation + " to " + destLocation + ", go to Map tab to generate the visual path.", Toast.LENGTH_LONG).show();
        
	}
	
	public void setSourceFromPage(View view){
		TextView buildingCode = (TextView)this.findViewById(R.id.building_text);
		srcLocation = (String) buildingCode.getText();
		Toast.makeText(getBaseContext(),"Source set to: " + srcLocation + ", go to Map tab to generate the visual path.", Toast.LENGTH_LONG).show();
	}
	
	public void setDestinationFromPage(View view){
		TextView buildingCode = (TextView)this.findViewById(R.id.building_text);
		destLocation = (String) buildingCode.getText();
		Toast.makeText(getBaseContext(),"Destination set to: " + destLocation + ", go to Map tab to generate the visual path.", Toast.LENGTH_LONG).show();
	}
	
	//USED TO INTERCEPT BACK BUTTON
	@Override
	public void onBackPressed() {
		setTheCurrentView(R.layout.main_screen);
	}

	//If a user presses the back button, it'll take them to the main screen
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	setTheCurrentView(R.layout.main_screen);
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	//Displays the page containg the building info of the item selected
	public void viewBuildingInfo(View view,Node location){
	
		setContentView(R.layout.building_info);
		TextView buildingText = (TextView)findViewById(R.id.building_text);
		buildingText.setText(location.getCode());
		ImageView imgv = (ImageView)findViewById(R.id.building_image);  
		imgv.setImageBitmap(getImageFromCode(location.getCode()));
		TextView buildingDescription = (TextView)findViewById(R.id.description);
		//buildingDescription.setText(location.getDesc());
		
		//Loads the description from the strings.xml file
		buildingDescription.setText(getDescriptionFromCode(location.getCode()));
	}
	
	//Displays the page containg the building info of the item selected from the NFC page
	public void viewBuildingInfoDestination(View view){
		
		TextView destText = (TextView) findViewById(R.id.nfc_destination_text);
		Node  location = map.findNodeWithCode((String)destText.getText());
		viewBuildingInfo(view,location);	
		tabSelected = 2;
	}
	
	//Displays the page containg the building info of the item selected from the NFC page
	public void viewBuildingInfoSource(View view){
		
		TextView srcText = (TextView) findViewById(R.id.nfc_source_text);
		Node  location = map.findNodeWithCode((String)srcText.getText());
		viewBuildingInfo(view,location);
		tabSelected = 2;
	}

	//MAKES IT SO ITS FULLSCREEN AND BARS NEVER REAPPEAR
	private void hideSystemUi() {
		getWindow().getDecorView().setSystemUiVisibility(

		View.SYSTEM_UI_FLAG_LAYOUT_STABLE

		| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

		| View.SYSTEM_UI_FLAG_FULLSCREEN

		| View.SYSTEM_UI_FLAG_IMMERSIVE);

	}
	
	//Shows the ui 
	private void showSystemUi(){
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
	}


	//Initializes the settings list !!((NO LONGER USED))
	private void initList() {
		//Initializing setting menu list
		settingList.add(createSettingsList("setting", "About"));
		settingList.add(createSettingsList("setting", "Return"));
		settingList.add(createSettingsList("setting", "back"));

	}

	//Creates a hash map for the lists used for displaying list view
	private LinkedHashMap<String, String> createSettingsList(String key, String name) {
		LinkedHashMap<String, String> settingsList = new LinkedHashMap<String, String>();
		settingsList.put(key, name);
		return settingsList;
	}
	
	private HashMap<String,String> createLocationList(String name){
		HashMap<String,String> locationList = new HashMap<String,String>();
		locationList.put("building", name);
		return locationList;
	}
	
	private void initLocationList(){
		Iterator<String> keySetIterator = map.getBuildingList().keySet().iterator();

		while(keySetIterator.hasNext()){
		  String key = keySetIterator.next();
		  locationsList.add(createLocationList(key));
		}

	}
	
	
	//Method used to keep the current view when switching views
	public void setTheCurrentView(int id){
		root = id;
		setContentView(id);
		
		//This is done to stop crashing on start up since the viewpager isn't initialized yet before this call
		if (id == R.layout.activity_main){
			tabSelected = 0;
		}else{
			tabSelected = viewPager.getCurrentItem();
		}
		
		
	}
	
	//Used for switching between location and settings list views, settings list no longer used
	public void setMenuType(String type){
		
		
		menuType = type;
		
		if (type == "location"){
			//For location menu
	        simpleAdpt = new SimpleAdapter(this, locationsList, android.R.layout.simple_list_item_1, new String[] {"building"}, new int[] {android.R.id.text1});
		}else if (type == "setting"){
			 //For settings menu
			simpleAdpt = new SimpleAdapter(this, settingList, android.R.layout.simple_list_item_1, new String[] {"setting"}, new int[] {android.R.id.text1});
			hideSystemUi();
		}
	}
	
	public void showSettingsList(View view){
		this.setTheCurrentView(R.layout.about_page);
	}
	public void showLocationList(View view){
		setMenuType("location");
		displayScrollingMenu(view);
	}
	
	//Method to switch to a scrolling menu
	public void displayScrollingMenu(View view){
  

		
		setContentView(R.layout.list_menu);
        ListView lv = (ListView) findViewById(R.id.listView);

       
        
        lv.setAdapter(simpleAdpt);
       
        // React to user clicks on item
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

             public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
                                     long id) {
                     
             
                 // We know the View is a TextView so we can cast it
                 TextView clickedView = (TextView) view;
                 
                 
                 if (clickedView.getText() == "back"){
                	 showSystemUi(); //Makes the UI reappear
                	 setTheCurrentView(root);
                	 
                	 
                 }else if (menuType == "location"){
                	 

                	 String buildingInfo  = map.getBuildingList().get(clickedView.getText()).getCode();
                	 
                	 viewBuildingInfo(view, map.findNodeWithCode(map.getBuildingList().get(clickedView.getText()).getCode()));
                	 
                 }
             }
        });
      
        //Scrolls to position
        lv.smoothScrollByOffset(200);
        
        
        //For getting the UI to rehide after 2 seconds
        mHideHandler = new Handler();
        mHideRunnable = new Runnable() {

        @Override
        public void run() {

        }

        };
        
        //A listener that checks to see if bars re-appeared, and if they do it re-hides it after 2 seconds
        //No longer being used
        getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(

        		new View.OnSystemUiVisibilityChangeListener() {

        		@Override
        		public void onSystemUiVisibilityChange(int visibility) {

        		if (visibility == 0) {

        		mHideHandler.postDelayed(mHideRunnable, 2000);

        		}

        		}

        		});
        
       
        


	}

	 
	//Initializes everything when the app is launched
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheCurrentView(R.layout.activity_main);
        
        map = new CarletonMap();
        
        mapUrl = "file:///android_asset/mapv2.html";
        
        
        
        menuType = "setting";
        
        //TEMP
        this.srcLocation = "UC";
        this.destLocation = "UC";
        
        newScrollX=0;
        newScrollY=0;
        zoomPercent =100;
        zoomPercentX=1f;
        zoomPercentY=1f;
        tabSelected = 0;
        
        
        root=R.layout.activity_main;
        lastBuildingSelected = 20;
        
     // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabMenu(getSupportFragmentManager());
      
        
        initList();//Creates the settings menu list
        this.initLocationList();
        
        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);        
 
        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }
        
        //Sets a listener for the action bar
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
        	 
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
         
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
         
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        
        
        
        //Initializes the nfc adapter
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
    	
		if (mNfcAdapter == null) {
			Toast.makeText(this, "This device doesn't support NFC.", Toast.LENGTH_LONG).show();
			return;

		}

		
		if (!mNfcAdapter.isEnabled()) {
			nfcStatus = "NFC is Disabled";		
		} else {
			nfcStatus ="NFC is Enabled";
		}
		
		checkIntent(getIntent());


		
    }

    //Used for a context menu, no longer being used
    @Override  
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
    	super.onCreateContextMenu(menu, v, menuInfo);  
	     menu.setHeaderTitle("kururu");  
	     menu.add(0, v.getId(), 0, "View info");  
	     menu.add(0, v.getId(), 0, "Find Path");  
    }  
    
    //For context menu, no longer being used
    @Override  
    public boolean onContextItemSelected(MenuItem item) {  
    	return true;  
    }  
    
   
    //Used to check item selected from action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.launch_test:
            	displaySearch();
                return true;
            case R.id.about:
            	
            	showSettingsList(findViewById(root));
                 
            	return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    
    //No longer being used
    public void addSpinnerItems(){
    
		
		
    }

    //For option menu inflation
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
  
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);

    }
    
    
    //Quits the application
    public void quitApp(View view){
    	System.exit(0); 
    }
    
    //Displays the main screen
    public void mainReturn(View view){
    	setTheCurrentView(R.layout.activity_main);
    }

    
    //Gets web view details and sets them 
    public void setWebViewDetails(){
    	
    	WebView webView = (WebView)findViewById(R.id.webView1);
    	newScrollX = webView.getScrollX();
  	    newScrollY = webView.getScrollY();
  	    zoomPercent = (int) (webView.getScale() *100);;
    }
    
    //Loads the map
    public void loadMap(View view){
    	WebView webView = (WebView)findViewById(R.id.webView1);
    	
    	//Gets the current webview position so that we can put it back after loading the new page
    	setWebViewDetails();
    	
    	webView.getSettings().setBuiltInZoomControls(true);
    	webView.getSettings().setUseWideViewPort(true);
  	   

  	    webView.setWebViewClient(getPositionedClient());
  	    webView.getSettings().setLoadWithOverviewMode(false);
  	    webView.clearCache(true);
  	    webView.loadUrl(mapUrl);
    }
    
    
    //Switches between the two map view types
    public void switchMapView(View view){
    	if (streetView){
    		streetView = false;
    		this.zoomPercentX = mapV.getScaleX();
    		zoomPercentY = mapV.getScaleY();
    		mapUrl = "file:///android_asset/mapv1.html";
    	}else{
    		streetView = true;
    		mapUrl = "file:///android_asset/mapv2.html";
    		
    	}
    	loadMap(view);
    }
    
    
    //Generates the path and draws it
    public void generatePath(View view){
    	WebView webView = (WebView)findViewById(R.id.webView1);
    	webView.getSettings().setBuiltInZoomControls(true);
    	webView.getSettings().setUseWideViewPort(true);
	    webView.getSettings().setLoadWithOverviewMode(false);
  	   

  	    AsyncTask<Void, Void, Void> mapLoader = new AsyncTask<Void, Void, Void>() {
  		 
          @Override
          protected Void doInBackground(Void... params) {
   

        	 //Sets up the bitmap that will be drawn on
           	BitmapFactory.Options options = new BitmapFactory.Options();
           	options.inPurgeable = true;
           	options.inDither = true;
           	options.inScaled = false;
           	options.inMutable = true;
           	options.inPreferQualityOverSpeed = false;
           	options.inPreferredConfig = Bitmap.Config.ARGB_4444;
           	tempBMP =  BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.drawon), null,options );
         	    
           	//Create a canvas for the bitmap
       	  	Canvas mCanvas = new Canvas(tempBMP);
       	  	
       	  	
       	  	//Sets up the paint style to be used for drawing lines
       	  	Paint routePaintStyle = new Paint();
       	  	routePaintStyle.setColor(Color.BLACK);
       	  	routePaintStyle.setStyle(Paint.Style.STROKE);

       	  
       	  	//Gets the path to be drawn
       	  	List<Node> paths = map.getPathWithCode(srcLocation, destLocation);
         	int prevx = paths.get(0).getX();
         	int prevy = paths.get(0).getY();
         	
         	//Used for setting the map after done loading
         	sourceLocation = paths.get(0);
         	
         	//Draws the path
         	for (Node v : paths)
       		{
         		
         	  	routePaintStyle.setStrokeWidth(8);
    	       	routePaintStyle.setARGB(255, 241, 255, 115);
    	       	routePaintStyle.setXfermode(null);
    	       	mCanvas.drawLine(prevx,prevy,v.getX(),v.getY(),routePaintStyle);
    	       	
    	       	
    	       	routePaintStyle.setStrokeWidth(4);
    	      	routePaintStyle.setARGB(255, 255, 20, 147);
    	       	mCanvas.drawLine(prevx,prevy,v.getX(),v.getY(),routePaintStyle);
    	       	
    	       	
         		prevx = v.getX();
         		prevy = v.getY();
         		System.out.println("\nx: "+v.getX() + "  y: " + v.getY());
       		}
         	
         	
         	//draws circle at source
         	routePaintStyle.setStrokeWidth(6);
         	routePaintStyle.setARGB(255, 241, 255, 115);
	       	routePaintStyle.setXfermode(null);
	     	mCanvas.drawCircle(sourceLocation.getX(), sourceLocation.getY(), 4, routePaintStyle);
	     	
	    	routePaintStyle.setStrokeWidth(4);
	      	routePaintStyle.setARGB(255, 255, 0, 0);
	    	mCanvas.drawCircle(sourceLocation.getX(), sourceLocation.getY(), 3, routePaintStyle);
         	
         	
         	//draws circle at destination
         	routePaintStyle.setStrokeWidth(6);
	       	routePaintStyle.setARGB(255, 102, 0, 102);
	       	routePaintStyle.setXfermode(null);
	     	mCanvas.drawCircle(prevx, prevy, 4, routePaintStyle);
	     	
	    	routePaintStyle.setStrokeWidth(4);
	      	routePaintStyle.setARGB(255, 255, 0, 0);
	    	mCanvas.drawCircle(prevx, prevy, 3, routePaintStyle);

       	  
	    	//Saves the new bitmap to external storage so it can be used by the HTML map page
	    	String baseDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CUMAPS/images" + File.separator;
       	  	File mkdirs = new File(baseDir);
       		mkdirs.mkdirs();
       		String fileName = "overlay.png";
       		String total = (baseDir  + fileName);
       		File f = new File(total);

       		try {
       			FileOutputStream out = new FileOutputStream(f);
       			tempBMP.compress(Bitmap.CompressFormat.PNG, 100, out);
       			out.close();
       		} catch (FileNotFoundException e) {
       			//Do nothing
       		} catch (IOException e) {
       			//Do nothing
       		}
         	    
              return null;
          }
          
          boolean scrolled = false;
          
          
          //Runs after the above has completed
          @Override
          protected void onPostExecute(Void result) {
              pd.dismiss();
              WebView webView = (WebView)findViewById(R.id.webView1);
              mapV = webView;
              newScrollX = (int) (sourceLocation.getX()*1.82);
              newScrollY = (int) (sourceLocation.getY()*1.92);
               
               
               
              //Creates a webview client that goes to the source position on load
               
               webView.setWebViewClient(getPositionedClient());
              	webView.clearCache(true);
              	webView.loadUrl(mapUrl);
             
              webView.setWebViewClient(new WebViewClient() {
            	  
            	    @Override
            	    public void onPageFinished(WebView view, String url) {
            	        super.onPageFinished(view, url);
            	        Handler handle = new Handler();
            	        if (!scrolled) {
            	            handle.postDelayed(new Runnable() {
            	                @Override
            	                public void run() {
            	                    mapV.scrollBy(newScrollX,newScrollY );
            	                }
            	            }, 250);
            	            scrolled = true;
            	        }
            	    }
            	    
            	    
            	});

        	  
        	  
        	  
          }
      };
      
      //Shows the loading bar
      pd = ProgressDialog.show(this, "Please Wait...", "Generating Map Path...", true, true);
      
      //Executes the background thread for generating a map
      mapLoader.execute((Void[])null);

    }
    
    //Gets a webviewclient that is scrolled to the last position before switching map types
    public  WebViewClient getPositionedClient(){
    	
    	return new WebViewClient() {
      	  
    	    @Override
    	    public void onPageFinished(WebView view, String url) {
    	        super.onPageFinished(view, url);
    	        Handler handle = new Handler();
    	        boolean scrolled = false;
    	        if (!scrolled) {
    	            handle.postDelayed(new Runnable() {
    	                @Override
    	                public void run() {

    	                	mapV.loadUrl("javascript:(function() { " + "document.getElementsByTagName('body')[0].style.zoom='" + (zoomPercentX) + "'; })()");
    	                    mapV.scrollBy(newScrollX,newScrollY );
    	                }
    	            }, 250);
    	            scrolled = true;
    	        }
    	    }
    	    
    	    
    	};
    }
    
    //Loads the default map page
    public void loadMapPage(){
    	
    	WebView webView = (WebView)findViewById(R.id.webView1);
    	webView.getSettings().setBuiltInZoomControls(true);
    	webView.getSettings().setUseWideViewPort(true);
    	webView.getSettings().setLoadWithOverviewMode(true);
    	webView.clearCache(true);
    	webView.loadUrl(mapUrl);
    	
    }
    
    
    //No longer being used
    public void displayTabs(View view){

    }
 
    //Displays the search menu
    public void displaySearch(){
    	try {
        	LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);  
        	View popupView = layoutInflater.inflate(R.layout.about_screen, null); 
        	final PopupWindow popupWindow = new PopupWindow(popupView,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);  
        	popupSpinners = popupView;
        	
        	popupWindow.setWindowLayoutMode(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        	
        	 Button btnDismiss = (Button)popupView.findViewById(R.id.dismiss);
             btnDismiss.setOnClickListener(new Button.OnClickListener(){

            	 //Dismisses the popup
	         @Override
	         public void onClick(View v) {
	          popupWindow.dismiss();
	         }});
             
             Button setPath = (Button)popupView.findViewById(R.id.setPathButton);
             setPath.setOnClickListener(new Button.OnClickListener(){

            //Sets the path then dismisses
	         @Override
	         public void onClick(View v) {
	        	 
	        srcSpinner = (Spinner) (popupSpinners.findViewById(R.id.srcSpinner));
	        srcLocation = srcSpinner.getSelectedItem().toString().intern();
	        destSpinner = (Spinner)(popupSpinners.findViewById(R.id.destSpinner));
	        destLocation = destSpinner.getSelectedItem().toString().intern();
	        
	         Toast.makeText(getBaseContext(),"Path set from " + srcLocation + " to " + destLocation + ", go to Map tab to generate the visual path.", Toast.LENGTH_LONG).show();
	        
	          popupWindow.dismiss();
	         }});
        
            TextView popup = (TextView)findViewById(R.id.popupLabel);
            popupWindow.showAsDropDown(popup, -150,-700);
            
            
    	 } catch (Exception e){
    		 
    	 }
    	
    }
    
    
    //Switches to the map page
    public void switchMap(View view){
    	setTheCurrentView(R.layout.map_screen);
    	viewPager.setCurrentItem(1);
    	
    }
    
    
    //Displays the location list page
    public void displayListOfLocations(View view){
    	viewPager.setCurrentItem(2);
    	this.setMenuType("location");
    	this.displayScrollingMenu(view);
    }

    //Returns from the building info page to the previous page
    public void returnFromInfoPage(View view){
    	if (tabSelected == 2){
    		this.setTheCurrentView(root);
    		tabSelected = 1;
    		setNFCPage();
    	}else{
    		this.displayScrollingMenu(view);
    	}
    }

    
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		//Do nothing
	}

	
	//Used for the action bar tab menu to switch between the tabs
	//...No longer fully using fragments
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		
		//Remove below for it to return to proper swipe stuff
		if (tab.getPosition()==0 && swiped ==1){

			setTheCurrentView(R.layout.main_screen);
		}else if (tab.getPosition()==1){
			setTheCurrentView(R.layout.map_screen);
			loadMapPage();
	
			
			
			
			WebView webView = (WebView)findViewById(R.id.webView1);
            mapV = webView;
			
		}else if (tab.getPosition()==2){
			this.setMenuType("location");
			this.displayScrollingMenu(this.findViewById(root));
		}else if (tab.getPosition()==3){
			
			//Displays one of two pages depending on whether or not NFC is enabled
			if (mNfcAdapter == null){
				setTheCurrentView(R.layout.nfc_not_enabled);
				TextView defaultMsg = (TextView)findViewById(R.id.default_nfc);
				defaultMsg.setText("Your device does not support NFC");
			}else if (mNfcAdapter.isEnabled()){
				if (!nfcScanned){
					setTheCurrentView(R.layout.nfc_not_enabled);
				}else{
					setTheCurrentView(R.layout.nfc_popup);
					setNFCPage();
				}
					
			}else{
				setTheCurrentView(R.layout.nfc_not_enabled);
				TextView defaultMsg = (TextView)findViewById(R.id.default_nfc);
				defaultMsg.setText("Your device does not support NFC or it is not enabled");
			}
			
		}
		swiped=1;
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		//Do nothing
	}
	
	//Resumes the foreground dispatch to stop errors
	@Override
	protected void onResume() {
		super.onResume();
		if (!(mNfcAdapter == null)){
			setUpDispatch(this, mNfcAdapter);
		}
		
	}
	
	//Prevents an error since dispatch needs to be stopped before pausing
	@Override
	protected void onPause() {
		stopForegroundDispatch(this, mNfcAdapter);
		super.onPause();
	}
	
	//Method used to handle new intents from android
	@Override
	protected void onNewIntent(Intent intent) { 
		checkIntent(intent);
	}
	
	//Sets up a foreground dispatch for NFC
	public static void setUpDispatch(final Activity activity, NfcAdapter nfcAdapter) {
		final Intent androidIntent = new Intent(activity.getApplicationContext(), activity.getClass());
		androidIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

		final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, androidIntent, 0);

		IntentFilter[] intentFilters = new IntentFilter[1];
		String[][] techList = new String[][]{};

		//Makes the intent filter
		intentFilters[0] = new IntentFilter();
		intentFilters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
		intentFilters[0].addCategory(Intent.CATEGORY_DEFAULT);
		
		try {
			intentFilters[0].addDataType(MIME_TEXT_PLAIN);
		} catch (MalformedMimeTypeException e) {
			//Do nothing
		}
		
		nfcAdapter.enableForegroundDispatch(activity, pendingIntent, intentFilters, techList);
	}
	public static void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
		adapter.disableForegroundDispatch(activity);
	}
	
	
	//A private class used for reading NFC tag information
	private class NdefReaderTask extends AsyncTask<Tag, Void, String> {

		
		
		//Parses the information contained in the tag in the background thread
		@Override
		protected String doInBackground(Tag... params) {
			
			Tag tag = params[0];
			
			Ndef ndef = Ndef.get(tag);
			if (ndef == null) {
				// NDEF is not supported by this Tag. 
				return null;
			}

			NdefMessage ndefMessage = ndef.getCachedNdefMessage();
			NdefRecord[] records = ndefMessage.getRecords();
			
			for (NdefRecord ndefRecord : records) {
				if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
					try {
						return parseTag(ndefRecord);
					} catch (UnsupportedEncodingException e) {
						//Encoding type not supported
					}
				}
			}

			return null;
		}
		
		//Returns the text in the tag 
		private String parseTag(NdefRecord tag) throws UnsupportedEncodingException {


			byte[] information = tag.getPayload();

			// Get the  Encoding format
			String encodingFormat = ((information[0] & 128) == 0) ? "UTF-8" : "UTF-16";

			//Calculates offset and byte count
			int offset = (information[0] & 0063)  +1;
			int byteCount = information.length - (information[0] & 0063) -1;
			
			// Get the Text
			String text = new String(information, offset, byteCount, encodingFormat);
			
			return text;

		}
		
		//What the program does when an NFC tag is read and the background thread has finished parsing
		@Override
		protected void onPostExecute(String result) {
			if (result != null) {
				
				nfcStatus = result; 
				nfcScanned = true;
				
				//Test message to show what's in the tag
				Toast msg = Toast.makeText(getBaseContext(),"NFC: " + result, Toast.LENGTH_LONG);
				msg.show();
    
			     //Gets the source and destination if the tag contains a "~" symbol
			    if (result.contains("~")) {
			    	
			    	//Gets the location and target from the tag
			    	String srceLocation = result.substring(0, 2);
			    	String destiLocation = result.substring(2, 4);
			    	
			    	 
			    	 
			    	//Switches the view to the nfc popup since the tag has been scanned
			    	viewPager.setCurrentItem(3);
			    	setTheCurrentView(R.layout.nfc_popup);
			    	 

			    	//This corrects the NFC text read in so that it's in the proper character code format needed for comparison
			    	srceLocation = srceLocation.intern();
			    	destiLocation = destiLocation.intern();
			    	 
			    	//Sets the application to know what was scanned
			    	currentNFCSource = srceLocation;
			    	currentNFCDestination = destiLocation;
			    	 
			    	//Sets the information on the NFC page
			    	setNFCPage();
		
			    } else {
			    	System.out.println("Doesn't contain ~");
			    }
				
			}
		}
	}
	
	//Sets the current page to the NFC page
	public void setNFCPage(){
		((TextView)(findViewById(R.id.nfc_source_text))).setText(currentNFCSource);
   	 	((TextView)(findViewById(R.id.nfc_destination_text))).setText(currentNFCDestination);
   	 	
	}
	
	
	//Checks the intent delivered from android for the NFC functionality
	private void checkIntent(Intent androidIntent) {
		
		//Gets the intent action
    	String action = androidIntent.getAction();
    	
    	
    	//Checks to see what kind of intent it is
		if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
			
			
			String type = androidIntent.getType();
			if (MIME_TEXT_PLAIN.equals(type)) {

				Tag tag = androidIntent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
				new NdefReaderTask().execute(tag);
				
			} else {
				//Do nothing
			}
		} else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
			
			
			Tag tag = androidIntent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			String[] techList = tag.getTechList();
			String searchedTech = Ndef.class.getName();
			
			for (String tech : techList) {
				if (searchedTech.equals(tech)) {
					new NdefReaderTask().execute(tag);
					break;
				}
			}
		}
	}

	
	//Loads the image from for the building info page
	public Bitmap getImageFromCode(String code){
		 if (code == "AA"){
			 return BitmapFactory.decodeResource(getResources(),R.drawable.aa);
		 }else if (code == "AP"){
			 return BitmapFactory.decodeResource(getResources(),R.drawable.ap);
		 }else if (code == "CC"){
			 return BitmapFactory.decodeResource(getResources(),R.drawable.cc);
		 }else if (code == "DT"){
			 return BitmapFactory.decodeResource(getResources(),R.drawable.dt);
		 }else if (code == "HP"){
			 return BitmapFactory.decodeResource(getResources(),R.drawable.hp);
		 }else if (code == "LA"){
			 return BitmapFactory.decodeResource(getResources(),R.drawable.la);
		 }else if (code == "LS"){
			 return BitmapFactory.decodeResource(getResources(),R.drawable.ls);
		 }else if (code == "MC"){
			 return BitmapFactory.decodeResource(getResources(),R.drawable.mc);
		 }else if (code == "ME"){
			 return BitmapFactory.decodeResource(getResources(),R.drawable.me);
		 }else if (code == "ML"){
			 return BitmapFactory.decodeResource(getResources(),R.drawable.ml);
		 }else if (code == "NW"){
			 return BitmapFactory.decodeResource(getResources(),R.drawable.nw);
		 }else if (code == "PH"){
			 return BitmapFactory.decodeResource(getResources(),R.drawable.ph);
		 }else if (code == "RO"){
			 return BitmapFactory.decodeResource(getResources(),R.drawable.ro);
		 }else if (code == "SA"){
			 return BitmapFactory.decodeResource(getResources(),R.drawable.sa);
		 }else if (code == "SC"){
			 return BitmapFactory.decodeResource(getResources(),R.drawable.sc);
		 }else if (code == "SP"){
			 return BitmapFactory.decodeResource(getResources(),R.drawable.sp);
		 }else if (code == "SR"){
			 return BitmapFactory.decodeResource(getResources(),R.drawable.sr);
		 }else if (code == "TB"){
			 return BitmapFactory.decodeResource(getResources(),R.drawable.tb);
		 }else if (code == "TT"){
			 return BitmapFactory.decodeResource(getResources(),R.drawable.tt);
		 }else{
			 return BitmapFactory.decodeResource(getResources(),R.drawable.no_image);
		 }
	}
	
	//Loads the description from the strings.xml file for the building information page
	public Spanned getDescriptionFromCode(String code){
		
		
		
		if (code == "UC"){
			return Html.fromHtml(getString(R.string.UC_html));
		}else if (code =="AA"){
			return Html.fromHtml(getString(R.string.aa_html));
		}else if (code =="AP"){
			return Html.fromHtml(getString(R.string.ap_html));
		}else if (code =="IH"){
			return Html.fromHtml(getString(R.string.ih_html));
		}else if (code =="TT"){
			return Html.fromHtml(getString(R.string.tt_html));
		}else if (code =="CC"){
			return Html.fromHtml(getString(R.string.cc_html));
		}else if (code =="DT"){
			return Html.fromHtml(getString(R.string.dt_html));
		}else if (code =="FH"){
			return Html.fromHtml(getString(R.string.fh_html));
		}else if (code =="GH"){
			return Html.fromHtml(getString(R.string.gh_html));
		}else if (code =="GR"){
			return Html.fromHtml(getString(R.string.gr_html));
		}else if (code =="HP"){
			return Html.fromHtml(getString(R.string.hp_html));
		}else if (code =="LH"){
			return Html.fromHtml(getString(R.string.lh_html));
		}else if (code =="LS"){
			return Html.fromHtml(getString(R.string.ls_html));
		}else if (code =="LA"){
			return Html.fromHtml(getString(R.string.la_html));
		}else if (code =="ME"){
			return Html.fromHtml(getString(R.string.me_html));
		}else if (code =="ML"){
			return Html.fromHtml(getString(R.string.ml_html));
		}else if (code =="MC"){
			return Html.fromHtml(getString(R.string.mc_html));
		}else if (code =="NB"){
			return Html.fromHtml(getString(R.string.nb_html));
		}else if (code =="NW"){
			return Html.fromHtml(getString(R.string.nw_html));
		}else if (code =="PA"){
			return Html.fromHtml(getString(R.string.pa_html));
		}else if (code =="RH"){
			return Html.fromHtml(getString(R.string.rh_html));
		}else if (code =="CO"){
			return Html.fromHtml(getString(R.string.co_html));
		}else if (code =="RO"){
			return Html.fromHtml(getString(R.string.ro_html));
		}else if (code =="SP"){
			return Html.fromHtml(getString(R.string.sp_html));
		}else if (code =="SR"){
			return Html.fromHtml(getString(R.string.sr_html));
		}else if (code =="SA"){
			return Html.fromHtml(getString(R.string.sa_html));
		}else if (code =="SC"){
			return Html.fromHtml(getString(R.string.sc_html));
		}else if (code =="SD"){
			return Html.fromHtml(getString(R.string.sd_html));
		}else if (code =="TB"){
			return Html.fromHtml(getString(R.string.tb_html));
		}else{
			return Html.fromHtml(getString(R.string.default_html));
		}

	}
	
	
}
