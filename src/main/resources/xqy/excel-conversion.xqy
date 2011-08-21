xquery version "1.0-ml";

declare namespace sst="http://schemas.openxmlformats.org/spreadsheetml/2006/main";
declare namespace zip="xdmp:zip";

declare variable $zipfile as xs:string := "/home/casper/Downloads/Navigation_structure2.xlsx";

declare variable $shared-strings as document-node() :=
for $x in xdmp:zip-manifest(xdmp:document-get($zipfile))//zip:part/text()
 where contains($x, "sharedStrings.xml")
 return xdmp:zip-get(xdmp:document-get($zipfile), $x, <options xmlns="xdmp:zip-get">
          <format>xml</format>
        </options>);

declare function local:get-worksheets-from-zipfile($zipfile as xs:string){
 for $x in xdmp:zip-manifest(xdmp:document-get($zipfile))//zip:part/text()
 where contains($x, "worksheets/sheet1.xml")
 return xdmp:zip-get(xdmp:document-get($zipfile), $x, <options xmlns="xdmp:zip-get">
          <format>xml</format>
        </options>)
};

declare function local:get-shared-strings-from-zipfile($zipfile as xs:string){
 for $x in xdmp:zip-manifest(xdmp:document-get($zipfile))//zip:part/text()
 where contains($x, "sharedStrings.xml")
 return xdmp:zip-get(xdmp:document-get($zipfile), $x, <options xmlns="xdmp:zip-get">
          <format>xml</format>
        </options>)
};

declare function local:get-rels-from-zipfile($zipfile as xs:string){
 for $x in xdmp:zip-manifest(xdmp:document-get($zipfile))//zip:part/text()
 where contains($x, "workbook.xml.rels")
 return xdmp:zip-get(xdmp:document-get($zipfile), $x,
 <options xmlns="xdmp:zip-get">
   <format>xml</format>
 </options>)
};

declare function local:resolve-term-by-position($pos) {
  $shared-strings/sst:sst/sst:si[$pos]/sst:t/text()
};


(: local:get-shared-strings-from-zipfile("/home/casper/Downloads/Navigation_structure2.xlsx")/sst:sst/sst:si[1273]/sst:t/text(), :)
(: local:get-rels-from-zipfile("/home/casper/Downloads/Navigation_structure2.xlsx") :)

(for $row at $pos in local:get-worksheets-from-zipfile($zipfile)/sst:worksheet/sst:sheetData/sst:row
return
$row,
(:
element row {
  for $col in $row/sst:c
  return
  element col { 
    attribute cell {$col/@r},
    attribute origin-value {$col/sst:v/text()},
    local:resolve-term-by-position($col/sst:v/text() cast as xs:integer)
  }
},:)
$shared-strings)