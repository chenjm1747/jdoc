// modify these parameters accordingly!
var url = 'http://115.159.218.200:4000/map.html';
var     mapID           = 'map';
var     outputFilename  = 'map.png';

var resourceWait  = 30000,
    maxRenderWait = 30000;

var page          = require('webpage').create(),
    count         = 0,
    exitTimeout;


function doExit() {
    console.log('Call '+url+' success, exit!');
    phantom.exit();
}    

page.onConsoleMessage = function(msg) {
    console.log(msg);
};

page.onResourceRequested = function (req) {
    count += 1;
    clearTimeout(exitTimeout);
};

page.onResourceReceived = function (res) {
    if (!res.stage || res.stage === 'end') {
        count -= 1;
        if (count === 0) {
            exitTimeout = setTimeout(doExit, resourceWait);
        }
    }
};

page.open(url, function (status) {
    if (status !== "success") {
        console.log('Unable to load url');
        phantom.exit();
    } else {
        console.log('Calling '+url+', please wait!');
    }
});