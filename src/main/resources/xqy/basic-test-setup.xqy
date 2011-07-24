xquery version "1.0-ml";

(: EXTREMELY rough quick set-up script for early spike unit test(s) :)

import module namespace info = "http://marklogic.com/appservices/infostudio" at "/MarkLogic/appservices/infostudio/info.xqy";
import module namespace admin = "http://marklogic.com/xdmp/admin" at "/MarkLogic/admin.xqy";

declare variable $db as xs:string := "AppBaseTests";        
declare variable $config := admin:get-configuration();

(try {
    info:database-create($db)
} catch ($e) {
    xdmp:log( concat("Problem creating Database and Forests: " , $e/*:message/text()) )
}, 

let $config := admin:database-set-uri-lexicon($config, xdmp:database($db), fn:true())
let $config := admin:database-set-collection-lexicon($config, xdmp:database($db), fn:true())
return
admin:save-configuration($config))