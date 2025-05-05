-- Name: M_InOut_Receipt_Source_Tablevalidation
-- 2023-07-05T16:11:16.742781200Z
INSERT INTO AD_Reference (AD_Client_ID, AD_Org_ID, AD_Reference_ID, Created, CreatedBy, EntityType, IsActive, IsOrderByValue, Name, Updated, UpdatedBy, ValidationType)
VALUES (0, 0, 541797, TO_TIMESTAMP('2023-07-05 18:11:16.628', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts', 'Y', 'N', 'M_InOut Receipt Source Tablevalidation', TO_TIMESTAMP('2023-07-05 18:11:16.628', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'T')
;

-- 2023-07-05T16:11:16.757369400Z
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
  AND t.AD_Reference_ID = 541797
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Reference_ID = t.AD_Reference_ID)
;

-- Table Validation - SQL not generated (not possible), because of missing ad_ref_table_id column
INSERT INTO ad_ref_table (ad_reference_id, ad_client_id, ad_org_id, created, createdby, updatedby, ad_table_id, ad_key, entitytype, ad_window_id)
VALUES (541797, 0, 0, TO_TIMESTAMP('2023-07-05 18:48:54.275', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 100, 319, 3521, 'de.metas.contracts', 184)
;

-- Reference: M_InOut_Receipt_Source_Tablevalidation
-- Table: M_InOut
-- Key: M_InOut.M_InOut_ID
-- 2023-07-05T16:48:54.276152300Z
UPDATE AD_Ref_Table
SET WhereClause='IsSoTrx = ''N''', Updated=TO_TIMESTAMP('2023-07-05 18:48:54.275', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Reference_ID = 541797
;

-- Name: ModCntr_Log target for M_InOut Receipt
-- 2023-07-05T17:41:10.042036800Z
INSERT INTO AD_Reference (AD_Client_ID, AD_Org_ID, AD_Reference_ID, Created, CreatedBy, EntityType, IsActive, IsOrderByValue, Name, Updated, UpdatedBy, ValidationType)
VALUES (0, 0, 541799, TO_TIMESTAMP('2023-07-05 19:41:09.895', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts', 'Y', 'N', 'ModCntr_Log target for M_InOut Receipt', TO_TIMESTAMP('2023-07-05 19:41:09.895', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'T')
;

-- 2023-07-05T17:41:10.044670600Z
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
  AND t.AD_Reference_ID = 541799
  AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Reference_ID = t.AD_Reference_ID)
;

-- Table Validation - SQL not generated (not possible), because of missing ad_ref_table_id column
INSERT INTO ad_ref_table (ad_reference_id, ad_client_id, ad_org_id, created, createdby, updatedby, ad_table_id, ad_key, entitytype, ad_window_id)
VALUES (541799, 0, 0, TO_TIMESTAMP('2023-07-05 19:41:09.895', 'YYYY-MM-DD HH24:MI:SS.US'), 0, 0, 542338, 586758, 'de.metas.contracts', 541711)
;

-- Reference: ModCntr_Log target for M_InOut Receipt
-- Table: ModCntr_Log
-- Key: ModCntr_Log.ModCntr_Log_ID
-- 2023-07-05T18:08:06.750274400Z
UPDATE AD_Ref_Table
SET WhereClause='EXISTS ( SELECT 1 from ModCntr_Log mcl JOIN M_InOutLine ml on mcl.record_id = ml.m_inoutline_id AND mcl.ad_table_id = (SELECT ad_table_id from ad_table where tablename = ''M_InOutLine'') where mcl.modcntr_log_id = ModCntr_Log.ModCntr_Log_ID AND ml.m_inout_id = @M_InOut_ID/-1@)', Updated=TO_TIMESTAMP('2023-07-05 20:08:06.749', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Reference_ID = 541799
;

-- 2023-07-05T18:15:14.426131700Z
INSERT INTO AD_RelationType (AD_Client_ID, AD_Org_ID, AD_Reference_Source_ID, AD_Reference_Target_ID, AD_RelationType_ID, Created, CreatedBy, EntityType, IsActive, IsTableRecordIdTarget, Name, Updated, UpdatedBy)
VALUES (0, 0, 541797, 541799, 540414, TO_TIMESTAMP('2023-07-05 20:15:14.313', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'de.metas.contracts', 'Y', 'N', 'M_InOut Receipt -> ModCntr_Log', TO_TIMESTAMP('2023-07-05 20:15:14.313', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

