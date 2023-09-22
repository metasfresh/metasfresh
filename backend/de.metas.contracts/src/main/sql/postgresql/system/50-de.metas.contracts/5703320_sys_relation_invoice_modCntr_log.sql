-- Name: ModCntr_Log target for C_Invoice
-- 2023-09-19T08:57:55.599Z
INSERT INTO AD_Reference (AD_Client_ID, AD_Org_ID, AD_Reference_ID, Created, CreatedBy, EntityType, IsActive, IsOrderByValue, Name, Updated, UpdatedBy, ValidationType)
VALUES (0, 0, 541835, TO_TIMESTAMP('2023-09-19 10:57:55.314', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts', 'Y', 'N', 'ModCntr_Log target for C_Invoice', TO_TIMESTAMP('2023-09-19 10:57:55.314', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'T')
;

-- 2023-09-19T08:57:55.601Z
INSERT INTO AD_Reference_Trl (AD_Language, AD_Reference_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Reference_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Reference t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Reference_ID = 541835
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Reference_ID = t.AD_Reference_ID)
;

-- Table Validation - SQL not generated (not possible), because of missing ad_ref_table_id column
INSERT INTO ad_ref_table (ad_reference_id, ad_client_id, ad_org_id, created, createdby, updatedby, ad_table_id, ad_key, entitytype, ad_window_id)
VALUES (541835, 0, 0, TO_TIMESTAMP('2023-09-18 19:41:09.895', 'YYYY-MM-DD HH24:MI:SS.US'), 0, 0, 542338, 586758, 'de.metas.contracts', 541711)
;

-- Reference: ModCntr_Log target for C_Invoice
-- Table: ModCntr_Log
-- Key: ModCntr_Log.ModCntr_Log_ID
-- 2023-09-19T18:08:06.750274400Z
UPDATE AD_Ref_Table
SET WhereClause='EXISTS ( SELECT 1 from ModCntr_Log mcl JOIN C_InvoiceLine il on mcl.record_id = il.C_InvoiceLine_ID AND mcl.ad_table_id = (SELECT ad_table_id from ad_table where tablename = ''C_InvoiceLine'') where mcl.ModCntr_Log_ID = ModCntr_Log.ModCntr_Log_ID AND il.C_Invoice_ID = @C_Invoice_ID/-1@)', Updated=TO_TIMESTAMP('2023-09-19 20:08:06.749', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Reference_ID = 541835
;

-- 2023-09-19T09:12:03.245Z
INSERT INTO AD_RelationType (AD_Client_ID, AD_Org_ID, AD_Reference_Source_ID, AD_RelationType_ID, Created, CreatedBy, EntityType, IsActive, IsTableRecordIdTarget, Name, Updated, UpdatedBy)
VALUES (0, 0, 336, 540431, TO_TIMESTAMP('2023-09-19 11:12:03.145', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.swat', 'Y', 'N', 'C_Invoice -> ModCntr_Log', TO_TIMESTAMP('2023-09-19 11:12:03.145', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- 2023-09-19T09:12:20.941Z
UPDATE AD_RelationType
SET AD_Reference_Target_ID=541835, Updated=TO_TIMESTAMP('2023-09-18 11:12:20.94', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_RelationType_ID = 540431
;
