xquery version "1.0-ml";
let $a := (10, 2000, 2)
return
<results><max>{ max($a) }</max><min>{ min($a) }</min></results>