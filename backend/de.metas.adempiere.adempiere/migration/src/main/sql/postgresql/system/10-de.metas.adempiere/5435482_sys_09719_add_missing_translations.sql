-- Function: add_missing_translations()

-- DROP FUNCTION add_missing_translations();

CREATE OR REPLACE FUNCTION add_missing_translations()
  RETURNS void AS
$BODY$
DECLARE
   ins        VARCHAR (2000);
   sel        VARCHAR (2000);
   inssel     VARCHAR (4001);
   table_id   NUMERIC;
   t	      RECORD;
   c	      RECORD;
   v_InsertCount integer;
BEGIN

   FOR t IN (SELECT ad_table_id,
                   SUBSTR (tablename, 1, LENGTH (tablename) - 4) as tablename
               FROM AD_TABLE
              WHERE tablename LIKE '%_Trl' AND isactive = 'Y' AND isview = 'N'
			  ORDER BY TableName)
   LOOP
      ins :=
            'INSERT INTO '
         || t.tablename
         || '_TRL ('
         || 'ad_language,ad_client_id,ad_org_id,created,createdby,updated,updatedby,isactive,istranslated,'
         || t.tablename
         || '_id';
      sel :=
            'SELECT l.ad_language,t.ad_client_id,t.ad_org_id,t.created,t.createdby,t.updated,t.updatedby,t.isactive,''N'' as istranslated,'
         || t.tablename
         || '_id';

      SELECT ad_table_id
        INTO table_id
        FROM AD_TABLE
       WHERE tablename = t.tablename;

      FOR c IN (SELECT   col.columnname
                    FROM AD_COLUMN col INNER JOIN AD_TABLE tab
                         ON (col.ad_table_id = tab.ad_table_id)
                   WHERE col.ad_table_id = table_id
                     AND col.istranslated = 'Y'
                     AND col.isactive = 'Y'
                ORDER BY 1)
      LOOP
         ins := TRIM (ins) || ',' || TRIM (c.columnname);
         sel := TRIM (sel) || ',t.' || TRIM (c.columnname);
      END LOOP;

      ins := TRIM (ins) || ')';
      sel :=
            TRIM (sel)
         || ' from '
         || t.tablename
         || ' t, ad_language l WHERE l.issystemlanguage=''Y'' AND NOT EXISTS (SELECT 1 FROM '
         || t.tablename
         || '_TRL b WHERE b.'
         || t.tablename
         || '_id=t.'
         || t.tablename
         || '_id AND b.AD_LANGUAGE=l.AD_LANGUAGE)';
      inssel := TRIM (ins) || ' ' || TRIM (sel);

      EXECUTE inssel;
	  GET DIAGNOSTICS v_InsertCount = ROW_COUNT;
	  raise notice 'Inserted % rows into %_Trl', v_InsertCount, t.tablename;
   END LOOP;

END;
$BODY$
LANGUAGE plpgsql VOLATILE
COST 100;
