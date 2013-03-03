#ITM569 Android API Project
- - - 

**Vesion: 1.0**

The intention of this project is to build an Android mobile 
application that uses a public API to consume and display data. 
This application uses the City of Chicago's data API which is 
provided by Socrata and uses their soda format.

I used the data resource from the City of Chicago that lists 
crimes reported in the last calendar year, excluding murder. 
The City data is located at the following link.

[Chicago One Year Crime Database](https://data.cityofchicago.org/Public-Safety/Crimes-One-year-prior-to-present/x2n5-8w5q)

The application allows you to search the database based on your 
current GPS coordinates. It gives you the options to limit the date 
range and distance from the location. On devices that provide the 
Google APIs and a service for the Geocoder class, you will have the 
option to input an address to search based on that address. Once 
you get the list or crimes that fit your search parameters, you 
can select one and it will show you all the details in that report.

##Features

+ Search for local Chicago Crime Reports based on location
+ Choose distance radius to search within
+ Choose from various amounts of time to search
+ If your device provides a Geolocation service you can search 
by address
+ The full details of the record in the database can be viewed
+ Launch activity provides a menu option for setting default 
GPS coordinates for testing when the emulator is having issues. 
These coordinates represent the Illinois Institute of Technology 
Tower.   
    
##Android Support

The application is targeted to Android 4.2 but has also been tested 
in Android 2.3.3. Android 2.3.3 is the minimum SDK version the 
application will run on. I was only able to test the application 
in the emulator since I don't have an actual device.

####Emulator Issues:####

One problem I noticed in the emulators is issues with GPS. Sometimes 
I wasn't able to get the emulator to respond to the send GPS commands 
through DDMS or through telnet. This was especially a problem in 
the 2.3.3 emulator. I provided a menu item on the main launch activity 
to set a default value for the GPS coordinates for testing when the 
emulator GPS is having issues.

Another issue I ran into in the 2.3.3 emulator was with the Geocoder 
class. Although my 2.3.3 emulator has the Google API's I was still 
having problems with it. The emulator would report that it is 
available when you call isPresent(), but the emulator would still 
throw an IOException saying the service was unavailable. I looked 
around the Internet and found this is a problem in the emulator but 
should work in a real device.

##Version History

1.0 - Mar 2, 2013 - Version turned in to Professor

##Sources

[Chicago One Year Crime Database](https://data.cityofchicago.org/Public-Safety/Crimes-One-year-prior-to-present/x2n5-8w5q)

[Socrata Soda API](http://dev.socrata.com/)


