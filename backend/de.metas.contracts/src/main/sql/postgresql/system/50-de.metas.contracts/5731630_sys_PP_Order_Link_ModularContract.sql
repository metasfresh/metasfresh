select backup_table('ad_reference');

select backup_table('ad_relationtype');


DELETE FROM ad_relationtype WHERE NAME = 'PP_Order -> ModCntr_Log';


DELETE from ad_reference where name = 'ModCntr_Log_Target_PP_Order';


-- Name: ModCntr_Log_Target_PP_Order
-- 2024-08-22T18:05:00.183Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) 
VALUES (0,0,541881,TO_TIMESTAMP('2024-08-22 18:05:00.036000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.contracts','Y','N','ModCntr_Log_Target_PP_Order',TO_TIMESTAMP('2024-08-22 18:05:00.036000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'T')
;

-- 2024-08-22T18:05:00.187Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541881 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: ModCntr_Log_Target_PP_Order
-- Table: ModCntr_Log
-- Key: ModCntr_Log.ModCntr_Log_ID
-- 2024-08-22T18:08:08.858Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause)
 VALUES (0,586758,0,541881,542338,TO_TIMESTAMP('2024-08-22 18:08:08.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.contracts','Y','N','N',TO_TIMESTAMP('2024-08-22 18:08:08.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EXISTS(SELECT 1 from ModCntr_Log log where log.ad_table_id = get_table_id(''pp_order'') AND log.Record_ID = @PP_Order_ID / -1@ AND log.modcntr_log_id = modcntr_log.modcntr_log_id)')
;

-- Name: PP_Order -> ModCntr_Log
-- Source Reference: PP_Order
-- Target Reference: ModCntr_Log_Target_PP_Order
-- 2024-08-22T18:08:25.857Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540503,541881,540440,TO_TIMESTAMP('2024-08-22 18:08:25.732000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.swat','Y','N','PP_Order -> ModCntr_Log',TO_TIMESTAMP('2024-08-22 18:08:25.732000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

