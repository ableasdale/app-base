xquery version "1.0-ml";

module namespace dbtools = "http://www.xmlmachines.com/app-base/dbtools";

import module namespace admin = "http://marklogic.com/xdmp/admin" at "/MarkLogic/admin.xqy";

declare default function namespace "http://www.w3.org/2005/xpath-functions";

declare function dbtools:addRangeIndex($config as element(configuration), $dbName as xs:string, 
  $index as element(index)){

   let $ret := try {
        admin:database-add-range-element-index(
            $config, 
            xdmp:database($dbName), 
            admin:database-range-element-index(
                $index/@type, 
                $index/@namespace,
                $index/@name, 
                $index/@collation,
                $index/@usePosition 
            )
        )
    } catch ($e) {
         $e/*:message/text()
    }
    return $ret
};

declare function dbtools:unrollIndexes($newIndexes as element(newIndexes))
  as element(index)* {

    let $res := for $index in $newIndexes/newIndex
      let $names := tokenize($index/@names, ",")
      let $type := $index/@type
      let $usePosition := if($index/@usePosition) then xs:boolean($index/@usePosition)
        else false()
      let $namespace := if($index/@namespace) then $index/@namespace else ""
      let $collation := if($index/@collation) then $index/@collation 
        else if($type eq "string") then "http://marklogic.com/collation/codepoint"
        else "http://marklogic.com/collation"
      return for $iname in $names
        let $name := normalize-space($iname)
        return <index name="{$name}" type="{$type}" usePosition="{$usePosition}" namespace="{$namespace}"
      collation="{$collation}"/>    
    return $res
};


declare function dbtools:addIndexes($dbName as xs:string, $newIndexes as element(newIndexes)) {
  
  let $config := admin:get-configuration()
  let $indexes := dbtools:unrollIndexes($newIndexes)
  let $results := dbtools:processIndexes($config, $dbName, $indexes)
  let $updatedConfig := $results[1]
  let $statuses := subsequence($results, 2, count($results))
  let $dummy := admin:save-configuration($updatedConfig)
  return element results {$statuses}

};

declare function dbtools:processIndexes($config as element(configuration), $dbName as xs:string, $indexes as element(index)*) {
  let $idxCount := count($indexes)
  return if($idxCount eq 1) then 
      let $index := $indexes[1]
      let $c := dbtools:addRangeIndex($config, $dbName, $index)
      return if($c instance of element(configuration)) then
    ($c, element addedIndex{$index/@name})
      else 
        ($config, element indexFailed{$index/@name, $c}) 
  else 
      let $index := $indexes[1]
      let $r := dbtools:addRangeIndex($config, $dbName, $index)
      let $updatedConfig := if($r[1] instance of element(configuration)) then $r[1] else $config
      let $resultMessage := if($r[1] instance of element(configuration)) then
    element addedIndex {$index/@name}
      else
        element indexFailed {$index/@name, $r[1]}
      let $ret := dbtools:processIndexes($updatedConfig, $dbName, subsequence($indexes, 2,  count($indexes)))
      return ($ret[1], ($resultMessage, subsequence($ret, 2, count($ret))))
};

(: example usage:

let $toAdd := <newIndexes>
  <newIndex names="uri, name, location" type="string"/>
  <newIndex names="version" type="int" usePosition="true"/>
</newIndexes>

return local:addIndexes("Test-DB", $toAdd)

newIndex can have the following attributes:
 <newIndex names="one, two" type="string" collation="http://something" namespace="test" usePosition="true"/>
 these correspond to the $localname as xs:string,$scalar-type as xs:string,$collation as xs:string,$namespace as xs:string,
$range-value-positions as xs:boolean arguments of the admin:database-range-element-index function

:)