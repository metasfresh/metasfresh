-- DROP VIEW IF EXISTS db_constraints;
CREATE OR REPLACE VIEW db_constraints AS 
SELECT
	co.contype AS constraint_type
	, co.conname AS constraint_name
	, ns.nspname AS schemaname
	, cl.relname AS tablename
	, a.attname AS columnname
	, nsf.nspname AS fschemaname
	, clf.relname AS ftablename
	, af.attname AS fcolumnname
FROM pg_constraint co
JOIN pg_class cl ON cl.oid = co.conrelid
JOIN pg_namespace ns ON ns.oid = cl.relnamespace
LEFT JOIN pg_attribute a ON a.attrelid = co.conrelid AND ARRAY[a.attnum] <@ co.conkey
LEFT JOIN pg_class clf ON clf.oid = co.confrelid
LEFT JOIN pg_namespace nsf ON nsf.oid = clf.relnamespace
LEFT JOIN pg_attribute af ON af.attrelid = co.confrelid AND ARRAY[af.attnum] <@ co.confkey
WHERE (co.contype = ANY (ARRAY['p'::"char", 'f'::"char"]))
;


