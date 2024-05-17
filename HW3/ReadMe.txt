How to run the program (with the AutoAssociator)

load the coreesponding .stu file
click train (you need to load the file first)
TCont will continue from the current state with some occasional interruptions from the auto associator (15% chance seems to be the best)


What do the extra buttons do?
"TPr" will print "1" if the i'th class is at the time slot written in the "TimeSlot" field, "-1" otherwise
"Traub" will train the auto associator using the timetable files for the corresponding clash file (the file needs to be loaded with the load button)


Does the algorithm work?
Not really, the "TCont" button is a wild card, sometimes it gives a better state, while at other times it gives a better state
However, often times, it does give a better result than regularly continuing without using the trained set, but the difference is minor.
