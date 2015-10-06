DECLARE
   ins        VARCHAR2 (2000);
   sel        VARCHAR2 (2000);
   inssel     VARCHAR2 (4001);
   table_id   NUMBER;
BEGIN
   ins := RPAD (' ', 2000, ' ');
   sel := RPAD (' ', 2000, ' ');
   inssel := RPAD (' ', 4001, ' ');
   DBMS_OUTPUT.PUT_LINE ('Start');

   FOR t IN (SELECT ad_table_id,
                    SUBSTR (tablename, 1, LENGTH (tablename) - 4) tablename
               FROM AD_TABLE
              WHERE tablename LIKE '%_Trl' AND isactive = 'Y'
                    AND isview = 'N')
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

      DBMS_OUTPUT.PUT_LINE (inssel);
      EXECUTE IMMEDIATE inssel;
   END LOOP;

   DBMS_OUTPUT.PUT_LINE ('End');
   COMMIT;
END;
