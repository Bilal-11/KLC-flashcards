# KLC flashcards

In my journey to learning Japanese, I am using [Kodansha's Kanji Learner Course (KLC)](https://www.amazon.com/Kodansha-Kanji-Learners-Course-Step/dp/1568365268) for learning Kanji (Chinese characters used  in Japanese. I used 3 flashcard apps (including Anki) to do my Kanji reviews. However, I found those apps lacking in one way or the other for this Kanji learning purpose. So, I decided to make my own flashcard app for Kanji!

This is a small application where I have hardcoded the 2300 Kanji and their meaning into the application (from the CSV source file I have). When the user opens the app, they are met with this screen:

<img src="https://imgur.com/jO9nUAh.jpg" alt="Query Screen">

The user can then specify the start point and end point of the Kanji revision (values between 1 and 2300) that correspond to the numbers associated with each Kanji entry in the Kodansha KLC book (below, left). Then the quiz screen shows up where the user can review their Kanji (below, right).

<img src="https://imgur.com/y2ot0mn.jpg" alt="Query Screen"> <img src="https://imgur.com/hgKFmIV.jpg" alt="Quiz Screen">

The `Next` button takes the user the next Kanji, `Back` button to the previous Kanji and `Reset` button to the query screen where the range of Kanji can be entered again.

The meaning of the Kanji is initially hidden, revealed when the `Next` button is clicked.
