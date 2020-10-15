#PROBLEM STATEMENT:

##Background

This kata simulates a babysitter working and getting paid for one night.  The rules are pretty straight forward:

The babysitter 
* starts no earlier than 5:00PM
* leaves no later than 4:00AM
* gets paid $12/hour from start-time to bedtime
* gets paid $8/hour from bedtime to midnight
* gets paid $16/hour from midnight to end of job
* gets paid for full hours (no fractional hours)

As a babysitter, in order to get paid for 1 night of work, I want to calculate my nightly charge

##Approach
This is a simple algorithm with the following components:
* Checking if input time is in-between the appropriate ranges, particularly for the start and end times. Bedtime is assumed to be a particular hour that is not subject to the user
* Calculating the differences in hours among the three periods
* Multiply the time blocks with the appropriate payrates and add to get the final result


