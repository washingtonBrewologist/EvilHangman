# EvilHangman

In this version of hangman, The user will select a length for the words used during one round of game play. After selecting a word length and a max number of guesses allowed, The program will then pull out of the dictionary all of the words that are of the specified length. It will then use all of these words at the same time against the users guesses. As the user makes guesses, the program will update the pattern of words it's concidering and never actually picking a word untill there is only one possible word left in the set.

# Repo Contains:

HangmanMain.java  -> This is the client program. Contains main and is where you can specify which dictionary you wish to use.

Toggle : debug=false <-> debug=true; 
debug=true will display remaining wordset being considered by the computer
debug=false for a challenging round oh hangman!

HangmanManager.java -> This is the class file giving functionality to the client program. 

dictionary2.txt -> Used as a test file when debugging program. Only contains 9, 4-letter words.

dictionary.txt -> This is the official scrabble dictionary ( http://www.hasbro.com/scrabble/en_US/search.cfm ) 



Enjoy!
