-- 2023-07-31T16:50:08.714767300Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,337,540417,TO_TIMESTAMP('2023-07-31 17:50:08.264','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','M_InOut -> ModCntr_Log',TO_TIMESTAMP('2023-07-31 17:50:08.264','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Name: ModCntr_Log_For_M_InOut
-- 2023-07-31T16:50:42.304550500Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541812,TO_TIMESTAMP('2023-07-31 17:50:42.055','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','ModCntr_Log_For_M_InOut',TO_TIMESTAMP('2023-07-31 17:50:42.055','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2023-07-31T16:50:42.309266Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541812 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: ModCntr_Log_For_M_InOut
-- Table: ModCntr_Log
-- Key: ModCntr_Log.ModCntr_Log_ID
-- 2023-07-31T16:52:52.073472200Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,586758,0,541812,542338,TO_TIMESTAMP('2023-07-31 17:52:52.051','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N',TO_TIMESTAMP('2023-07-31 17:52:52.051','YYYY-MM-DD HH24:MI:SS.US'),100,'ModCntr_Log.AD_Table_ID= get_table_id(''M_InOutLine'') AND ModCntr_Log.Record_ID IN (SELECT iol.M_InOutLine_ID from M_InOutLine iol where iol.M_InOut_ID = @M_InOut_ID / -1@)')
;

-- 2023-07-31T16:53:03.460223Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=541812,Updated=TO_TIMESTAMP('2023-07-31 17:53:03.458','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540417
;

