# PhoneticMatching
A samsung recommended project
Given two words either in Hindi or English, task is to determine whether two words are phonetically similar or not.

Input : Two words in Hindi/English
Output : 0 ---> if phonetically same
	 1 ---> if phonetically different
	
Languages constraint : C, C++, Java

Folder Structure:
TheSparklersNITK ---> Eclipse project(can be import directly).
src ---> Java classes
	- models ---> Traditional models like Cosine, JaroWinkler, NormalizedStringDistance, etc. to find initial threashold.
	- Transliteration ---> For converting English words to Hindi words.
	- ReadInputFile ---> to read input file containing three words in form (Word1	Word2	label).
	- WordDetector ---> Contains library to detect word(Hindi or English), and to check type of character(vowels, matras or consonants).
	- Model.java ---> Propsed model to create 3D-matrix of consonants->consonants mapping in 2D and third dimentsion of adding matras to it.
 Creating an update version for bixby.
