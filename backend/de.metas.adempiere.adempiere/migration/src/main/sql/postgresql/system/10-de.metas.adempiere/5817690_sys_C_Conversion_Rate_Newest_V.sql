-- Source DDL: backend/de.metas.adempiere.adempiere/migration/src/main/sql/postgresql/ddl/public/views/C_Conversion_Rate_Newest_V.sql
-- Migration-script id 5817690 allocated from idserver.metas.de (TABLE=AD_MigrationScript).
--
-- Newest conversion rate per (client, org, from, to, type) combo, via a ROW_NUMBER() window (newest_rn=1
-- is the most recently valid rate per combo). Lets the newest-rates read return only one row per combo
-- from the DB instead of loading all matching rows and reducing newest-per-combo in Java (OOME risk).
-- Applied via db_alter_view so any dependent objects are handled; a $new temp view keeps the SQL readable.

DROP VIEW IF EXISTS C_Conversion_Rate_Newest_V$new;

CREATE OR REPLACE VIEW C_Conversion_Rate_Newest_V$new AS
SELECT cr.C_Conversion_Rate_ID AS C_Conversion_Rate_Newest_V_ID,
       cr.C_Conversion_Rate_ID,
       cr.AD_Client_ID,
       cr.AD_Org_ID,
       cr.C_Currency_ID,
       cr.C_Currency_ID_To,
       cr.C_ConversionType_ID,
       cr.ValidFrom,
       cr.ValidTo,
       cr.MultiplyRate,
       cr.DivideRate,
       cr.IsActive,
       cr.Created,
       cr.CreatedBy,
       cr.Updated,
       cr.UpdatedBy,
       ROW_NUMBER() OVER (
           PARTITION BY cr.AD_Client_ID, cr.AD_Org_ID, cr.C_Currency_ID, cr.C_Currency_ID_To, cr.C_ConversionType_ID
           ORDER BY cr.ValidFrom DESC, cr.C_Conversion_Rate_ID DESC
       ) AS newest_rn
FROM C_Conversion_Rate cr;

SELECT db_alter_view(
    'C_Conversion_Rate_Newest_V',
    (SELECT view_definition
     FROM information_schema.views
     WHERE lower(views.table_name) = lower('C_Conversion_Rate_Newest_V$new'))
);

DROP VIEW IF EXISTS C_Conversion_Rate_Newest_V$new;
