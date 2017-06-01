// modify these parameters accordingly!
var url = 'http://115.159.218.200:4000/map.html';
var     mapID           = 'map';
var     outputFilename  = 'map.png';

var resourceWait  = 30000,
    maxRenderWait = 30000;

var page          = require('webpage').create(),
    count         = 0,
    forcedRenderTimeout,
    renderTimeout;

// function to execute map clipping and saving when page has fully loaded
function doRender() {

    console.log("Page has fully loaded");       
    console.log('clipping map...')

    var clipRect = page.evaluate(function(mapID){
        console.log(mapID)
        return document.getElementById(mapID).getBoundingClientRect();
        }, mapID);

    page.clipRect = {
        top:    clipRect.top,
        left:   clipRect.left,
        width:  clipRect.width,
        height: clipRect.height
        };

    console.log('rendering image...')
    console.log('top, left, width, height:')
    console.log(clipRect.top)
    console.log(clipRect.left)
    console.log(clipRect.width)
    console.log(clipRect.height)

    page.render(outputFilename);
    phantom.exit();

}

page.onResourceRequested = function (req) {
    count += 1;
    console.log('> ' + req.id + ' - ' + req.url);
    clearTimeout(renderTimeout);
};

page.onResourceReceived = function (res) {
    if (!res.stage || res.stage === 'end') {
        count -= 1;
        console.log(res.id + ' ' + res.status + ' - ' + res.url);
        if (count === 0) {
            renderTimeout = setTimeout(doRender, resourceWait);
        }
    }
};

page.open(url, function (status) {

    if (status !== "success") {
        console.log('Unable to load url');
        phantom.exit();
    } else {
        forcedRenderTimeout = setTimeout(function () {
            console.log(count);
            doRender();
        }, maxRenderWait);
    }
});