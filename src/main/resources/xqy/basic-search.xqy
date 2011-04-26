xquery version "1.0-ml";

import module namespace search =
  "http://marklogic.com/appservices/search"
  at "/MarkLogic/appservices/search/search.xqy";

declare namespace meta = "http://www.springer.com/app/meta";

declare variable $term as xs:string external;
 
search:search($term)