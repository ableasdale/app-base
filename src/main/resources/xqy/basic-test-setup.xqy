xquery version "1.0-ml";

(: EXTREMELY rough quick set-up script for early spike unit test(s) :)

import module namespace info = "http://marklogic.com/appservices/infostudio" at "/MarkLogic/appservices/infostudio/info.xqy";

try {
    info:database-create("BasicTests")
} catch ($e) {
    xdmp:log( concat("Problem creating Database and Forests: " , $e/*:message/text()) )
}