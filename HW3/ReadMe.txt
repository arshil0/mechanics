made by Arshil Aragelian!

How to run the program (with the AutoAssociator)

load the corresponding .stu file
click train (you need to load the file first)
use "TStart" or "TCont" to run the start and continue functions using the trained model (45% chance seems to be the best chance for interrupts)


What do the extra buttons do?
"TPr" will print "1" if the i'th class is at the time slot written in the "TimeSlot" field, "-1" otherwise
"Train" will train the auto associator using the timetable files for the corresponding clash file (the file needs to be loaded with the load button)


How does training work and how does it affect the algorithm?

when you hit the train button, it opens the corresponding timeslots file, and trains the autoassociator with different states
This data is simply the status of each class on a specific time slot, using the "TPr" button.
As I am writing this, I realized that I completely forgot to include the weights matrix from the Autoassociator class in anything, but I have no idea how I could use it
I would love some advise!
as we hit "TStart" or "TCont", it's like the regular "Start" or "Cont" button, with a 45% chance of getting interrupted and trying to converge a random neuron to a random good state
if the moved neuron results in more clashes, revert the change.
The reason I decided to make it like this is because there's a better chance of getting better states (with less clashes)
these good states are all written in the corresponding timeslots file for each set of classes and students.


Does the trained algorithm actually work?

Not really, the "TCont" button is a wild card, sometimes it gives a better state, while at other times it gives a worse state
However, often times, it does give a better result than regularly continuing without using the trained set, but the difference is minor.
