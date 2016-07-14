DROP VIEW IF EXISTS db_columns_fk;
DROP VIEW IF EXISTS db_constraints;


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


CREATE OR REPLACE VIEW db_columns_fk AS
SELECT
	v.TableName
	, v.ColumnName
	, v.table_ref as Ref_TableName
	, v.TableName_EntityType, v.ColumnName_EntityType
	, v.AD_Reference_ID, v.AD_Reference_Name, v.AD_Reference_Value_ID
	--
	, v.DDL_NoForeignKey
	, v.IsView
	--
	, (CASE
			WHEN v.DDL_NoForeignKey='Y'
				THEN 'N'
			WHEN EXISTS (SELECT 1 FROM db_constraints c WHERE lower(c.tablename)=LOWER(v.tablename) AND lower(c.columnname)=LOWER(v.columnname) AND c.constraint_type IN ('p','f'))
				THEN 'N'
			ELSE 'Y'
		END) AS IsMissing
	--
	, 'ALTER TABLE ' || v.tablename || ' DROP CONSTRAINT IF EXISTS '
		|| SUBSTR (REPLACE(SUBSTR (v.columnname, 1, LENGTH (v.columnname) - 3), '_', '') || '_' || REPLACE (v.tablename, '_', ''), 1, 30)
		|| ';' as sqltext_drop 
	--
	, 'ALTER TABLE ' || v.tablename
		-- || ' ADD (CONSTRAINT '  /* for oracle */
		|| ' ADD CONSTRAINT '  /* for postgresql */
		|| SUBSTR (REPLACE(SUBSTR (v.columnname, 1, LENGTH (v.columnname) - 3), '_', '') || '_' || REPLACE (v.tablename, '_', ''), 1, 30)
		|| ' FOREIGN KEY ('
		|| v.columnname
		|| ') REFERENCES '
		|| v.table_ref
		-- || ');'||CHR(10) AS cmd  /* for oracle */
		|| ' DEFERRABLE INITIALLY DEFERRED;'||chr(10) AS sqltext  /* for postgresql */
	--
	, 'ALTER TABLE ' || v.tablename || ' DROP CONSTRAINT IF EXISTS '
		|| SUBSTR (REPLACE(SUBSTR (v.columnname, 1, LENGTH (v.columnname) - 3), '_', '') || '_' || REPLACE (v.tablename, '_', ''), 1, 30)
		|| ';' 
		|| 'ALTER TABLE '|| v.tablename|| ' ADD CONSTRAINT '  /* for postgresql */
		|| SUBSTR (REPLACE(SUBSTR (v.columnname, 1, LENGTH (v.columnname) - 3), '_', '') || '_' || REPLACE (v.tablename, '_', ''), 1, 30)
		|| ' FOREIGN KEY ('
		|| v.columnname
		|| ') REFERENCES '
		|| v.table_ref
		-- || ');'||CHR(10) AS cmd  /* for oracle */
		|| ' ON DELETE CASCADE;' AS sqltext_delete_cascade  /* for postgresql */
	--
FROM
(
-- Table Direct or Search Table Direct
		SELECT t.tablename, c.columnname
			, r.NAME as AD_Reference_Name, c.ad_reference_id, c.ad_reference_value_id
			, CAST (SUBSTR (columnname, 1, LENGTH (columnname) - 3) AS VARCHAR(40)) AS table_ref
			, t.EntityType as TableName_EntityType
			, c.EntityType as ColumnName_EntityType
			, c.DDL_NoForeignKey
			, t.IsView
            FROM AD_Table t, AD_Column c, AD_Reference r
           WHERE t.ad_table_id = c.ad_table_id
             -- AND t.tablename LIKE 'ASP|_%' ESCAPE '|'
             AND c.columnsql IS NULL
             AND c.ad_reference_id = r.ad_reference_id
             AND (   c.ad_reference_id IN (19 /*Table Direct*/)
                  OR (    c.ad_reference_id IN (30 /*Search*/)
                      AND c.ad_reference_value_id IS NULL
                     )
                 )
-- Table or Search
		UNION
		SELECT t.tablename, c.columnname
				, r.NAME, c.ad_reference_id,
				c.ad_reference_value_id
				, tr.tablename as table_ref
				, t.EntityType as TableName_EntityType
				, c.EntityType as ColumnName_EntityType
				, c.DDL_NoForeignKey
				, t.IsView
            FROM AD_Table t,
                 AD_Column c,
                 AD_Reference r,
                 AD_Ref_Table rt,
                 AD_Table tr
           WHERE t.ad_table_id = c.ad_table_id
             -- AND t.tablename LIKE 'ASP|_%' ESCAPE '|'
             AND c.columnsql IS NULL
             AND c.ad_reference_id = r.ad_reference_id
             AND (   c.ad_reference_id IN (18 /*Table*/)
                  OR (    c.ad_reference_id IN (30 /*Search*/)
                      AND c.ad_reference_value_id IS NOT NULL
                     )
                 )
             AND c.ad_reference_value_id = rt.ad_reference_id
             AND rt.ad_table_id = tr.ad_table_id
-- Other hardcoded types
-- 25 - Account - C_ValidCombination
		UNION
		SELECT
			t.tablename, c.columnname
			, r.NAME, c.ad_reference_id, c.ad_reference_value_id
			, (CASE WHEN c.AD_Reference_ID=25 THEN 'C_ValidCombination'
				WHEN c.AD_Reference_ID=33 THEN 'S_ResourceAssignment'
				WHEN c.AD_Reference_ID=32 THEN 'AD_Image'
				WHEN c.AD_Reference_ID=21 THEN 'C_Location'
				WHEN c.AD_Reference_ID=31 THEN 'M_Locator'
				WHEN c.AD_Reference_ID=35 THEN 'M_AttributeSetInstance'
				ELSE NULL END) as table_ref
			, t.EntityType as TableName_EntityType
			, c.EntityType as ColumnName_EntityType
			, c.DDL_NoForeignKey
			, t.IsView
		FROM AD_Table t
		INNER JOIN AD_Column c ON (c.AD_Table_ID=t.AD_Table_ID)
		INNER JOIN AD_Reference r ON (r.AD_Reference_ID=c.AD_Reference_ID)
		WHERE t.IsView='N'
		AND c.AD_Reference_ID IN (
			25 -- Account - C_ValidCombination
			, 33 -- Assignment
			, 32 -- Image
			, 21 -- Location - C_Location_ID
			, 31 -- Locator - M_Locator_ID
			, 35 -- Product Attribute - M_AttributeSetInstance_ID
		)
) v
WHERE 1=1
	-- Exclude Standard Columns
	AND v.columnname NOT IN ('AD_Client_ID', 'AD_Org_ID', 'CreatedBy', 'UpdatedBy')
	-- Exclude Temporary Tables
	AND v.tablename NOT LIKE 'T|_%' ESCAPE '|'
	AND v.tablename NOT IN ('Test')
	-- Exclude views
	AND NOT EXISTS (SELECT 1 FROM pg_views WHERE viewname = LOWER (v.tablename))
	-- Make sure table exists in database
	AND EXISTS (SELECT 1 FROM pg_tables WHERE tablename = LOWER (v.tablename))
    -- exclude already existing foreign keys
	/*
	AND NOT EXISTS (
		SELECT 1
		FROM db_constraints c
		WHERE
		c.tablename=LOWER(v.tablename) AND c.columnname=LOWER(v.columnname)
		AND c.constraint_type IN ('p','f')
	)
	*/
-- ORDER BY v.tablename, v.table_ref, v.columnname
;

