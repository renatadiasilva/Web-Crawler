			
// Copyright 2006-2015 ClickTale Ltd., US Patent Pending
// PID: 49
// Generated on: 6/1/2015 4:12:34 PM (UTC 6/1/2015 9:12:34 PM)

(function(){var e=!0,g=!1,j=this;var k,l,n,o;function p(){return j.navigator?j.navigator.userAgent:null}o=n=l=k=g;var q;if(q=p()){var r=j.navigator;k=0==q.indexOf("Opera");l=!k&&-1!=q.indexOf("MSIE");n=!k&&-1!=q.indexOf("WebKit");o=!k&&!n&&"Gecko"==r.product}var s=k,u=l,v=o,w=n,x;
a:{var y="",z;if(s&&j.opera)var A=j.opera.version,y="function"==typeof A?A():A;else if(v?z=/rv\:([^\);]+)(\)|;)/:u?z=/MSIE\s+([^\);]+)(\)|;)/:w&&(z=/WebKit\/(\S+)/),z)var B=z.exec(p()),y=B?B[1]:"";if(u){var C,D=j.document;C=D?D.documentMode:void 0;if(C>parseFloat(y)){x=""+C;break a}}x=y}var E={};
function F(a){var b;if(!(b=E[a])){b=0;for(var c=(""+x).replace(/^[\s\xa0]+|[\s\xa0]+$/g,"").split("."),d=(""+a).replace(/^[\s\xa0]+|[\s\xa0]+$/g,"").split("."),h=Math.max(c.length,d.length),m=0;0==b&&m<h;m++){var t=c[m]||"",O=d[m]||"",P=RegExp("(\\d*)(\\D*)","g"),Q=RegExp("(\\d*)(\\D*)","g");do{var i=P.exec(t)||["","",""],f=Q.exec(O)||["","",""];if(0==i[0].length&&0==f[0].length)break;b=((0==i[1].length?0:parseInt(i[1],10))<(0==f[1].length?0:parseInt(f[1],10))?-1:(0==i[1].length?0:parseInt(i[1],10))>
(0==f[1].length?0:parseInt(f[1],10))?1:0)||((0==i[2].length)<(0==f[2].length)?-1:(0==i[2].length)>(0==f[2].length)?1:0)||(i[2]<f[2]?-1:i[2]>f[2]?1:0)}while(0==b)}b=E[a]=0<=b}return b}var G={};function H(){G[9]||(G[9]=u&&!!document.documentMode&&9<=document.documentMode)};!u||H();!u||H();u&&F("8");!w||F("528");v&&F("1.9b")||u&&F("8")||s&&F("9.5")||w&&F("528");!v||F("8");function I(a,b,c,d,h){a&&b&&("undefined"==typeof c&&(c=1E3),"undefined"==typeof d&&(d=20),0>--d?"function"===typeof h&&h():b()?a():setTimeout(function(){I(a,b,c,d,h)},c))};function J(a){function b(){c||(c=e,a())}var c=g;if("complete"===document.readyState||"interactive"===document.readyState)b();else{if(document.addEventListener)document.addEventListener("DOMContentLoaded",b,g);else if(document.attachEvent){try{var d=null!=window.frameElement}catch(h){}if(document.documentElement.doScroll&&!d){var m=function(){if(!c)try{document.documentElement.doScroll("left"),b()}catch(a){setTimeout(m,10)}};m()}document.attachEvent("onreadystatechange",function(){"complete"===document.readyState&&
b()})}if(window.addEventListener)window.addEventListener("load",b,g);else if(window.attachEvent)window.attachEvent("onload",b);else{var t=window.onload;window.onload=function(){t&&t();b()}}}};function K(a){"function"===typeof ClickTaleExec&&ClickTaleExec(a)}function L(a,b){"function"===typeof ClickTaleEvent&&(b?L.b[a]!==e&&(L.b[a]=e,ClickTaleEvent(a)):ClickTaleEvent(a))}L.b={};function N(a,b){"function"===typeof window.ClickTaleRegisterElementAction&&ClickTaleRegisterElementAction(a,b)}window.ClickTaleDetectAgent&&window.ClickTaleDetectAgent()&&window.ClickTaleDetectAgent();var R=L;window.ct_readCookie=function(a){for(var a=a+"=",b=document.cookie.split(";"),c=0;c<b.length;c++){for(var d=b[c];" "==d.charAt(0);)d=d.substring(1,d.length);if(0==d.indexOf(a))return d.substring(a.length,d.length)}return null};
window.pathOverride=function(){if(window.jQuery&&0<jQuery("body > div#cnn_hdr").length){var a=jQuery("body > div#cnn_hdr").index("body>div");window.ClickTaleSettings&&(window.ClickTaleSettings.ElementPathRewriter=function(b,c){jQuery(b).index("body>div");0<jQuery(b).parent("body").length&&c[0].p>=a&&(c[0].p-=a);return c})}};
function S(){if(!window.ClickTaleFirstPCCGo){window.ClickTaleFirstPCCGo=e;window.htmlArrived=function(){if(window.jQuery&&window.cnnPageName&&"CNN Home Page"==window.cnnPageName&&0<jQuery("body > div#cnn_hdr").length&&0<jQuery("#cnn_mOvrlycntr").length||window.jQuery&&0<jQuery('meta[content="article"]').length&&0<jQuery("body > div#cnn_hdr").length&&0<jQuery("#cnn_mOvrlycntr").length)if(R("version 5"),-1<document.location.href.toLowerCase().indexOf("cnn.com")){window.cnnPageName&&"CNN Home Page"==
window.cnnPageName&&R("Home page");var b=0,a=setInterval(function(){0<jQuery('meta[content="article"]').length&&0<jQuery("body > div#cnn_mOvrlycntr").length&&0<jQuery("body > div#cnn_hdr").length?(R("Article Page"),clearInterval(a)):100<b&&clearInterval(a);b+=1},300),c=0,d=setInterval(function(){0<jQuery(".no-border.no-pad-right:eq(1)>a").length&&"Log out"==jQuery(".no-border.no-pad-right:eq(1)>a").text()?(R("Login User",e),clearInterval(d)):100<c&&clearInterval(d);c+=1},300),h=0,i=setInterval(function(){0<
jQuery(".cnnvideo_playcontainer.cnnvideo_click_standard").length?(jQuery(document).on("mousedown",".cnnvideo_playcontainer.cnnvideo_click_standard",function(b){var a=jQuery(".cnnvideo_playcontainer.cnnvideo_click_standard").index(this);if(0<jQuery(".cnnvideo_playcontainer.cnnvideo_click_standard:eq("+a+")").closest("a").length){var a=jQuery(".cnnvideo_playcontainer.cnnvideo_click_standard:eq("+a+")").closest("a")[0],c={},d;for(d in b)c[d]=b[d];c.target=a;c.srcElement=a;N("mouseover",c);N("click",
c)}}),clearInterval(i)):100<h&&clearInterval(i);h+=1},300);jQuery("#us-menu>li").on("mousedown",function(b){var a=jQuery(this).index();if("a"!=b.target.nodeName.toLowerCase()&&0<jQuery("#us-menu>li").eq(a).find("a").length){var a=jQuery("#us-menu>li").eq(a).find("a")[0],c={},d;for(d in b)c[d]=b[d];c.target=a;c.srcElement=a;N("mouseover",c);N("click",c)}});setTimeout(function(){jQuery(".cnn_mtpmorebtn").one("mousedown",function(){R("More Clicked")});jQuery(document).on("input",".ftr-search-tfield>input",
function(){var a=jQuery(".ftr-search-tfield>input").index(this),b=jQuery(".ftr-search-tfield>input").eq(a).val();K("jQuery('.ftr-search-tfield>input:eq("+a+")').val('"+b+"');");R("Interacted With Search",e)})},3E3);window.bufferMovie=function(){jQuery("#cnnCVP1").on("onContentPause",function(){R("Stop Video",e);f=g});jQuery("#cnnCVP1").on("onContentBegin",function(){f=e})};var f=g;window.startIntervalMovie=function(){var a=10,b=setInterval(function(){f==e&&R("Video "+a+" seconds");(f==g||110<a)&&
clearInterval(b);a+=10},1E4)};var M=g;jQuery(document).on("mousedown",".cnnvideo_playcontainer.cnnvideo_click_standard",function(){M=f=e;R("Start Video Pressed On Play",e)});jQuery("#cnnCVP1").on("onContentBegin",function(){f=e;setTimeout(function(){if(0<jQuery(".cnn_mtt1img").length){var a=jQuery(".cnn_mtt1img").html().replace(/"/g,"'").replace(/(\r\n|\n|\r)/gm," ").replace(/<script.*?<\/script>/gi,"<script><\/script>");K("jQuery('.cnn_mtt1img').html(\""+a+'");')}},100);M==g&&R("Start Video Onload",
e)});jQuery("#cnnCVP1").one("onContentBuffering",function(){f=e;window.startIntervalMovie&&window.startIntervalMovie();window.bufferMovie&&window.bufferMovie()})}};"undefined"!=typeof window.CNN&&"contentModel"in window.CNN&&"article"==window.CNN.contentModel.pageType&&R("Article template");var a=0,b=setInterval(function(){0<jQuery('.pg-rail-tall__head div[itemprop="video"]').length&&(0<jQuery("#large-media_0 object").length&&(R("autoplay video"),clearInterval(b)),0<jQuery("#large-media_0--thumbnail>img").length&&
(R("no autoplay video"),clearInterval(b)),a+=1);30<a&&clearInterval(b)},300);if(jQuery("body").hasClass("pg-homepage")&&jQuery("h2.banner-text>strong").length){var c=jQuery("h2.banner-text>strong").text();R("us home page - "+c)}var d=0,h=setInterval(function(){window.jQuery&&0<jQuery("body > div#cnn_hdr").length&&0<jQuery("#cnn_mOvrlycntr").length?(clearInterval(h),window.htmlArrived&&window.htmlArrived()):100<d&&clearInterval(h);d+=1},300)}}
(function(a){function b(){2==++window.okToStartOn2&&a()}window.okToStartOn2=0;J(function(){b()});if("undefined"!==typeof window.ClickTaleIsRecording&&window.ClickTaleIsRecording()===e)b();else{var c=window.ClickTaleOnRecording||function(){};window.ClickTaleOnRecording=function(){b();return c.apply(this,arguments)}}})(function(){var a=0,b=setInterval(function(){window.jQuery&&window.cnnPageName&&"CNN Home Page"==window.cnnPageName&&0<jQuery("body > div#cnn_hdr").length&&0<jQuery("#cnn_mOvrlycntr").length||
window.jQuery&&0<jQuery('meta[content="article"]').length&&0<jQuery("body > div#cnn_hdr").length&&0<jQuery("#cnn_mOvrlycntr").length?(clearInterval(b),window.ZoeFlag||(window.ZoeFlag=e,window.pathOverride&&window.pathOverride())):100<a&&clearInterval(b);a+=1},300);I(S,function(){return window.jQuery&&"function"===typeof jQuery.fn.on?e:g},250,40)});})();


