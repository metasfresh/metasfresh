DROP VIEW IF EXISTS db_columns_fk$new
;

CREATE OR REPLACE VIEW db_columns_fk$new AS
SELECT v.TableName
     , v.ColumnName
     , v.table_ref                       AS Ref_TableName
     , v.TableName_EntityType
     , v.ColumnName_EntityType
     , v.AD_Reference_ID
     , v.AD_Reference_Name
     , v.AD_Reference_Value_ID
     --
     , v.DDL_NoForeignKey
     , v.IsView
     , v.IsVirtualColumn
     --
     , (CASE
            WHEN v.DDL_NoForeignKey = 'Y'
                THEN 'N'
            WHEN v.IsVirtualColumn = 'Y'
                THEN 'N'
            WHEN EXISTS(SELECT 1 FROM db_constraints c WHERE LOWER(c.tablename) = LOWER(v.tablename) AND LOWER(c.columnname) = LOWER(v.columnname) AND c.constraint_type IN ('p', 'f'))
                THEN 'N'
                ELSE 'Y'
        END)                             AS IsMissing
     --
     , 'ALTER TABLE ' || v.tablename || ' DROP CONSTRAINT IF EXISTS '
           || SUBSTR(REPLACE(SUBSTR(v.columnname, 1, LENGTH(v.columnname) - 3), '_', '') || '_' || REPLACE(v.tablename, '_', ''), 1, 70)
    || ';'                               AS sqltext_drop
     --
     , 'ALTER TABLE ' || v.tablename
           -- || ' ADD (CONSTRAINT '  /* for oracle */
           || ' ADD CONSTRAINT ' /* for postgresql */
           || SUBSTR(REPLACE(SUBSTR(v.columnname, 1, LENGTH(v.columnname) - 3), '_', '') || '_' || REPLACE(v.tablename, '_', ''), 1, 70)
           || ' FOREIGN KEY ('
           || v.columnname
           || ') REFERENCES '
           || 'public.' || v.table_ref /* gh #1379: prepend the name of the 'public' schema */
    || ' DEFERRABLE INITIALLY DEFERRED;' AS sqltext /* for postgresql */
     --
     , 'ALTER TABLE ' || v.tablename || ' DROP CONSTRAINT IF EXISTS '
           || SUBSTR(REPLACE(SUBSTR(v.columnname, 1, LENGTH(v.columnname) - 3), '_', '') || '_' || REPLACE(v.tablename, '_', ''), 1, 70)
           || ';'
           || 'ALTER TABLE ' || v.tablename || ' ADD CONSTRAINT ' /* for postgresql */
           || SUBSTR(REPLACE(SUBSTR(v.columnname, 1, LENGTH(v.columnname) - 3), '_', '') || '_' || REPLACE(v.tablename, '_', ''), 1, 70)
           || ' FOREIGN KEY ('
           || v.columnname
           || ') REFERENCES '
           || 'public.' || v.table_ref /* gh #1379: prepend the name of the 'public' schema */
    || ' ON DELETE CASCADE;'             AS sqltext_delete_cascade /* for postgresql */
     --
FROM (
         -- Table Direct or Search Table Direct
         SELECT t.tablename
              , c.columnname
              , r.NAME                                                             AS AD_Reference_Name
              , c.ad_reference_id
              , c.ad_reference_value_id
              , CAST(SUBSTR(columnname, 1, LENGTH(columnname) - 3) AS VARCHAR(80)) AS table_ref
              , t.EntityType                                                       AS TableName_EntityType
              , c.EntityType                                                       AS ColumnName_EntityType
              , c.DDL_NoForeignKey
              , (CASE WHEN LENGTH(TRIM(c.ColumnSql)) > 0 THEN 'Y' ELSE 'N' END)    AS IsVirtualColumn
              , t.IsView
         FROM AD_Table t,
              AD_Column c,
              AD_Reference r
         WHERE t.ad_table_id = c.ad_table_id
           -- AND TRIM(COALESCE(c.columnsql, '')) = '' -- consider virtual columns because we need the in views like ad_table_related_windows_v
           AND c.ad_reference_id = r.ad_reference_id
           AND (c.ad_reference_id IN (19 /*Table Direct*/)
             OR (c.ad_reference_id IN (30 /*Search*/)
                 AND c.ad_reference_value_id IS NULL
                    )
             )
           -- Table or Search
         UNION
         SELECT t.tablename
              , c.columnname
              , r.NAME
              , c.ad_reference_id
              , c.ad_reference_value_id
              , tr.tablename                                                    AS table_ref
              , t.EntityType                                                    AS TableName_EntityType
              , c.EntityType                                                    AS ColumnName_EntityType
              , c.DDL_NoForeignKey
              , (CASE WHEN LENGTH(TRIM(c.ColumnSql)) > 0 THEN 'Y' ELSE 'N' END) AS IsVirtualColumn
              , t.IsView
         FROM AD_Table t,
              AD_Column c,
              AD_Reference r,
              AD_Ref_Table rt,
              AD_Table tr
         WHERE t.ad_table_id = c.ad_table_id
           -- AND TRIM(COALESCE(c.columnsql, '')) = '' -- consider virtual columns because we need the in views like ad_table_related_windows_v
           AND c.ad_reference_id = r.ad_reference_id
           AND (c.ad_reference_id IN (18 /*Table*/)
             OR (c.ad_reference_id IN (30 /*Search*/)
                 AND c.ad_reference_value_id IS NOT NULL
                    )
             )
           AND c.ad_reference_value_id = rt.ad_reference_id
           AND rt.ad_table_id = tr.ad_table_id
           -- Other hardcoded types
           -- 25 - Account - C_ValidCombination
         UNION
         SELECT t.tablename
              , c.columnname
              , r.NAME
              , c.ad_reference_id
              , c.ad_reference_value_id
              , (CASE
                     WHEN c.AD_Reference_ID = 25 THEN 'C_ValidCombination'
                     WHEN c.AD_Reference_ID = 33 THEN 'S_ResourceAssignment'
                     WHEN c.AD_Reference_ID = 32 THEN 'AD_Image'
                     WHEN c.AD_Reference_ID = 21 THEN 'C_Location'
                     WHEN c.AD_Reference_ID = 31 THEN 'M_Locator'
                     WHEN c.AD_Reference_ID = 35 THEN 'M_AttributeSetInstance'
                 END)                                                           AS table_ref
              , t.EntityType                                                    AS TableName_EntityType
              , c.EntityType                                                    AS ColumnName_EntityType
              , c.DDL_NoForeignKey
              , (CASE WHEN LENGTH(TRIM(c.ColumnSql)) > 0 THEN 'Y' ELSE 'N' END) AS IsVirtualColumn
              , t.IsView
         FROM AD_Table t
                  INNER JOIN AD_Column c ON (c.AD_Table_ID = t.AD_Table_ID)
                  INNER JOIN AD_Reference r ON (r.AD_Reference_ID = c.AD_Reference_ID)
         WHERE t.IsView = 'N'
           AND c.AD_Reference_ID IN (
                                     25 -- Account - C_ValidCombination
             , 33 -- Assignment
             , 32 -- Image
             , 21 -- Location - C_Location_ID
             , 31 -- Locator - M_Locator_ID
             , 35 -- Product Attribute - M_AttributeSetInstance_ID
             )
     ) v
WHERE
  -- Exclude Standard Columns
    v.columnname NOT IN ('AD_Client_ID', 'AD_Org_ID', 'CreatedBy', 'UpdatedBy')
  -- Exclude Temporary Tables
  AND v.tablename NOT LIKE 'T|_%' ESCAPE '|'
    AND v.tablename NOT IN ('Test')
    -- Exclude views
    AND NOT EXISTS(SELECT 1 FROM pg_views WHERE viewname = LOWER(v.tablename))

-- Make sure table exists in database
-- gh #539: this view shall even work for non-existing tables because we want to use it to create a part of the table DDL when the ColumnSync process is executed
-- AND EXISTS (SELECT 1 FROM pg_tables WHERE tablename = LOWER (v.tablename))
;

SELECT db_alter_view(
               'db_columns_fk',
               (SELECT view_definition
                FROM information_schema.views
                WHERE views.table_name = 'db_columns_fk$new')
           )
;

DROP VIEW IF EXISTS db_columns_fk$new
;



COMMENT ON VIEW db_columns_fk IS 'Can be used to identify and fix missing FK constraints. Also used by org.compiere.model.MColumn.getConstraint()
See https://github.com/metasfresh/metasfresh/issues/539'
;

