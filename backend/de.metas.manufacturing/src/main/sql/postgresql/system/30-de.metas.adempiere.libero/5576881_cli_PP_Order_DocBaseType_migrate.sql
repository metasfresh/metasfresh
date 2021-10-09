UPDATE pp_order mo
SET docbasetype=dt.docbasetype
FROM c_doctype dt
WHERE mo.c_doctype_id IS NOT NULL
  AND mo.docbasetype IS NULL
  AND mo.c_doctype_id = dt.c_doctype_id
;

