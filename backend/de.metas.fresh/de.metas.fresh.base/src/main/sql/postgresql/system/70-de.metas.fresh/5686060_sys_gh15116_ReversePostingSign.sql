-- Name: SAP_GLJournal_Origin
-- 2023-04-26T13:10:09.369Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541731,TO_TIMESTAMP('2023-04-26 14:10:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','SAP_GLJournal_Origin',TO_TIMESTAMP('2023-04-26 14:10:09','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-04-26T13:10:09.371Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541731 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: SAP_GLJournal_Origin
-- Table: SAP_GLJournal
-- Key: SAP_GLJournal.Reversal_ID
-- 2023-04-26T13:12:57.029Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,585361,0,541731,542275,TO_TIMESTAMP('2023-04-26 14:12:57','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','N',TO_TIMESTAMP('2023-04-26 14:12:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Reference: SAP_GLJournal_Origin
-- Table: SAP_GLJournal
-- Key: SAP_GLJournal.SAP_GLJournal_ID
-- 2023-04-26T13:18:27.016Z
UPDATE AD_Ref_Table SET AD_Key=585351, WhereClause='SAP_GLJournal.SAP_GLJournal_ID =@Reversal_ID/-1@',Updated=TO_TIMESTAMP('2023-04-26 14:18:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541731
;

-- 2023-04-26T13:18:46.923Z
UPDATE AD_RelationType SET AD_Reference_Source_ID=541728, AD_Reference_Target_ID=541731,Updated=TO_TIMESTAMP('2023-04-26 14:18:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540383
;

