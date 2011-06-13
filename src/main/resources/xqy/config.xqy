xquery version "1.0-ml" encoding "utf-8";

module namespace config="http://www.xmlmachines.com/app-base/config";

(: CONSTS - DON NOT CHECK THIS IN WITH "LIVE" SPRINGER VALUES! :)
declare variable $config:UNIT-TEST-DB-NAME as xs:string := "Unit-Tests";

declare variable $config:INDEXES as element(newIndexes) := 
<newIndexes>
  <newIndex names="ContentType, ItemTitle, PublicationTitle, Author, Id, Collection, Subject, Language" type="string" namespace="http://www.springer.com/app/meta" />
  <newIndex names="Date" type="date" namespace="http://www.springer.com/app/meta" />
</newIndexes>;