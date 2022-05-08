# CryptoTracker (Final Project CE3106)
This application aids in keeping  track of real-time prices of various currencies with visualisation and an alarm feature.  The Cryptocurrency analysis app uses the CoinMarketCap API to show you the timely prices and percentage change in the prices per hour, day, week. The application also provides a dynamic visualisation of the cryptocurrency prices per day, hour, minute using candlestick graphs using Gemini API. Additionally, the user can set an alert to receive a notification whenever the price of the chosen currency drops below the mentioned  value. 

[Download APK](https://drive.google.com/file/d/1D61o_f6T_yoUZQhKX5raYCdjKrU9mSo4/view)

<h4>Module wise Scope:</h4>  

<ul>
 
<li> 
<h4>Splash Screen:</h4>
When the user opens the app, a constant screen appears for a specific amount of time, showing the app icon and name. The MainActivity is visible after the specified time (in this case, 3 seconds).
Technical features: 
ImageView: Displays the app logo.
TextView: Displays the name of the application.
view.WindowManager: Interface that is used to talk to the window manager. It is used to set the splash screen to full screen and hide the action bar.
</li> 

<li>
<h4>Main Activity:</h4>
Users can scroll through the real-time prices of various cryptocurrencies and search for a cryptocurrency of their choice. 
Technical features: 
SearchBar: EditText implementing textChangeListener which filters the data being passed on to the Recycler View depending upon the text inputted.
Reload button: Calls the onReloadPressed function on clicking. This function uses intent to call the same activity. 
Buttons corresponding to Explicit Intents: See Trends and All alerts
Recycler View: 
Each Card implements currencyitem.xml layout and is defined by CurrencyViewHolder.
RecyclerView requests this view, and binds them to their data by calling methods in the RVAdapter.
Card data is an object of the CurrencyModal Class and receives data from the following fields of CoinMarketCap API: name, symbol, quote->usd->price, percent_change_1h, percent_change_24h and percent_change_7d. 
Main Activity implements the onResume and onPause methods along with the onCreate method. The activity reloads on the ONRESUME event and also every 15 minutes.
Every time the Main Activity runs, it compares the lower_limit price of the currencies in the database with their current prices and calls the sendNotification() function if the lower limit for that coin is crossed.
</li>
 
<li>
<h4>Notification Activity:</h4>
The application gives a notification when bitcoin price drops below the limit set by the user.
Technical components:
NotificationCompat.Builder: 
the class where we need to pass
a context of activity and 
a channel id as an argument while making an instance of the class.
</li>
 
<li>
<h4>Set Alert Activity:</h4>
Users can set a particular limit price for every bitcoin. The application will notify the user when the bitcoin price drops below the limit.
Technical features:
Enter Limit: Edit Text which takes care of input validation (allows only numeric values).
SetAlert Button: On clicking this button, data (currencyName and lower limit) is appended to the lowerLimit table using DBHandler.java. DBHandler uses SQLite to manage databases. 
</li>
 
<li>
 <h4>Visualisations Activity:</h4>
Visualization activity visualises the data received from the GeminiAPI in the form of a candlestick graph. Gemini API returns a list of the UNIX timestamp, open, close, high and low prices for each coin.
The graph is implemented using the MPCharts library from github.
</li>
 
<li>
 <h4>Alerts Activity:</h4>
The activity displays a list of all the alerts set by the user. Users can delete an alert by swiping left on the card.
Technical components:
Recycler View: 
Each Card implements alertitem.xml layout and is defined by AlertViewHolder.
RecyclerView requests this view, and binds them to their data by calling methods in the AlertAdapter.
Card data is an object of the AlertModal Class and receives data from the lowerLimit database using DBHandler.java

</li>
 
</ul>

![flowchart](https://github.com/samruddhideode/CryptoTracker/blob/master/image.JPG)











