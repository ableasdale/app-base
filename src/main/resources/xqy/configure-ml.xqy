xquery version "1.0-ml";

import module namespace info = "http://marklogic.com/appservices/infostudio" at "/MarkLogic/appservices/infostudio/info.xqy";
import module namespace dbtools = "http://www.xmlmachines.com/app-base/dbtools" at "lib-indexes.xqy";
import module namespace config = "http://www.xmlmachines.com/app-base/config" at "config.xqy";

declare default function namespace "http://www.w3.org/2005/xpath-functions";

(: 1. Create the database and forest :)
declare function local:setup(){
    (
        info:database-create($config:UNIT-TEST-DB-NAME),
        dbtools:addIndexes($config:UNIT-TEST-DB-NAME, $config:INDEXES)
     )
}; 





declare function local:teardown-stage-one(){
    info:database-delete($config:UNIT-TEST-DB-NAME)
};

local:setup()