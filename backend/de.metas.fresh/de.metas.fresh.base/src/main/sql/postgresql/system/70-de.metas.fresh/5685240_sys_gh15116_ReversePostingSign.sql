--------------------------------------------------------
--------------------------------------------------------- 

-- 1. Drop the existing constraint
ALTER TABLE sap_gljournal
    DROP CONSTRAINT reversal_sapgljournal;

-- 2. Create a new constraint with the updated reference
ALTER TABLE sap_gljournal
    ADD CONSTRAINT reversal_sapgljournal
        FOREIGN KEY (reversal_id) REFERENCES sap_gljournal (sap_gljournal_id)
            DEFERRABLE INITIALLY DEFERRED;
            
---Name: SAP_GLJournal_Source
-- 2023-04-18T15:34:09.184Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541728,TO_TIMESTAMP('2023-04-18 16:34:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','SAP_GLJournal_Source',TO_TIMESTAMP('2023-04-18 16:34:08','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-04-18T15:34:09.278Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541728 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: SAP_GLJournal_Source
-- Table: SAP_GLJournal
-- Key: SAP_GLJournal.SAP_GLJournal_ID
-- 2023-04-18T15:37:03.471Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,585351,0,541728,542275,NULL,TO_TIMESTAMP('2023-04-18 16:37:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2023-04-18 16:37:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: SAP_GLJournal_Reversal
-- 2023-04-18T15:41:00.782Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541729,TO_TIMESTAMP('2023-04-18 16:40:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','SAP_GLJournal_Reversal',TO_TIMESTAMP('2023-04-18 16:40:55','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-04-18T15:41:00.962Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541729 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: SAP_GLJournal_Reversal
-- Table: SAP_GLJournal
-- Key: SAP_GLJournal.SAP_GLJournal_ID
-- 2023-04-18T15:45:43.923Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,585351,0,541729,542275,NULL,TO_TIMESTAMP('2023-04-18 16:45:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2023-04-18 16:45:43','YYYY-MM-DD HH24:MI:SS'),100,'SAP_GLJournal.Reversal_ID =@SAP_GLJournal_ID/-1@')
;

-- 2023-04-18T16:29:03.087Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541700,541729,540383,TO_TIMESTAMP('2023-04-18 17:29:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','SAP_GLJournal -> SAP_GLJournal_Reversal',TO_TIMESTAMP('2023-04-18 17:29:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Reference: SAP_GLJournal_Source
-- Table: SAP_GLJournal
-- Key: SAP_GLJournal.SAP_GLJournal_ID
-- 2023-04-19T14:40:45.722Z
UPDATE AD_Ref_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2023-04-19 15:40:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541728
;

-- Reference: SAP_GLJournal_Reversal
-- Table: SAP_GLJournal
-- Key: SAP_GLJournal.SAP_GLJournal_ID
-- 2023-04-19T14:41:35.359Z
UPDATE AD_Ref_Table SET WhereClause='SAP_GLJournal.Reversal_ID =@SAP_GLJournal_ID/-1@',Updated=TO_TIMESTAMP('2023-04-19 15:41:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541729
;

-- Reference: SAP_GLJournal_Reversal
-- Table: SAP_GLJournal
-- Key: SAP_GLJournal.SAP_GLJournal_ID
-- 2023-04-19T14:41:44.794Z
UPDATE AD_Ref_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2023-04-19 15:41:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541729
;

-- Table: SAP_GLJournal
-- 2023-04-19T14:43:02.113Z
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2023-04-19 15:43:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542275
;

-- Column: SAP_GLJournal.SAP_GLJournal_ID
-- 2023-04-19T14:45:42.689Z
UPDATE AD_Column SET IsExcludeFromZoomTargets='N', IsGenericZoomKeyColumn='Y', IsGenericZoomOrigin='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-04-19 15:45:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585351
;

-- Column: SAP_GLJournal.Reversal_ID
-- 2023-04-19T14:46:14.368Z
UPDATE AD_Column SET IsExcludeFromZoomTargets='N', IsGenericZoomOrigin='Y',Updated=TO_TIMESTAMP('2023-04-19 15:46:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585361
;

-- Window: GL Journal (SAP) (original), InternalName=sapGLJournal
-- 2023-04-19T14:48:53.060Z
UPDATE AD_Window SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2023-04-19 15:48:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541656
;

-- Reference: SAP_GLJournal_Reversal
-- Table: SAP_GLJournal
-- Key: SAP_GLJournal.SAP_GLJournal_ID
-- 2023-04-19T14:51:18.454Z
UPDATE AD_Ref_Table SET AD_Window_ID=541656,Updated=TO_TIMESTAMP('2023-04-19 15:51:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541729
;

-- Reference: SAP_GLJournal_Source
-- Table: SAP_GLJournal
-- Key: SAP_GLJournal.SAP_GLJournal_ID
-- 2023-04-19T14:51:40.434Z
UPDATE AD_Ref_Table SET AD_Window_ID=541656,Updated=TO_TIMESTAMP('2023-04-19 15:51:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541728
;

