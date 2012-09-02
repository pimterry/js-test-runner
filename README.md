Js-Test-Runner
============

A command-line tool for running JavaScript unit test suites, aiming to make quickly running JavaScript tests as easy as testing is in any other language.

If you have some JavaScript that you want to be tested automatically and headlessly, either locally and in CI, this'll make life lovely. If you have JavaScript 
that's complex enough to encounter serious browser compatibility issues, if you're trying to test node.js code, or if you just really like managing more test runner 
scripts than tests (somewhat tongue-in-cheek: there are arguments for this), you might want to look into [Selenium](http://seleniumhq.org/), 
[Mocha](http://dailyjs.com/2011/12/08/mocha/) or [PhantomJS](http://phantomjs.org/), respectively.

Input support:
--------------

Js-Test-Runner fully supports only [QUnit](http://qunitjs.com/) HTML tests currently. This is expected to expand to include [Jasmine](http://pivotal.github.com/jasmine/) tests  
too in the medium/short-term (issue [#7](https://github.com/pimterry/js-test-runner/issues/7)).

Output support:
---------------

Currently Js-Test-Runner supports output in JUnit xml format. This is expected to be extended into a visual command-line runner for manual testing without a browser 
eventually (see issue [#8](https://github.com/pimterry/js-test-runner/issues/8)).

Installation:
-------------

[Download](https://github.com/pimterry/js-test-runner/downloads) one of the binary packages for the project and install it. Js-Test-Runner will be added to your 
path as `js-test-runner`. If none of the platform-specific binaries is appropriate, the jar-with-dependencies binary can be used directly and run as 
`java -jar js-test-runner-0.1-full.jar [arguments]`. 

Usage:
------

Usage instructions can be found by running the application without arguments. In practice, to run a test suite you can run `js-test-runner test-file.html`.
Optionally you can also name an output file with `-o ./test-results.xml`. By default, output will be placed in a file named 'output.xml' in the working directory.