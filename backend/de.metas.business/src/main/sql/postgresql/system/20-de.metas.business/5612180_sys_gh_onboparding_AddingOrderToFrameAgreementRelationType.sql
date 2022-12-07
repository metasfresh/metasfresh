-- 2021-11-04T12:28:17.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541468,TO_TIMESTAMP('2021-11-04 13:28:16','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_FrameAgreement_Order_ID -> C_Order_Target',TO_TIMESTAMP('2021-11-04 13:28:16','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-11-04T12:28:17.190Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541468 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-11-04T12:29:33.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,573002,0,541468,259,TO_TIMESTAMP('2021-11-04 13:29:33','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','N',TO_TIMESTAMP('2021-11-04 13:29:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-04T12:30:56.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Role_Source,Role_Target,Updated,UpdatedBy) VALUES (0,0,540666,541468,540318,TO_TIMESTAMP('2021-11-04 13:30:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','C_FrameAgreement_Order_ID -> C_Order_Target','','',TO_TIMESTAMP('2021-11-04 13:30:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-04T12:31:10.471Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET EntityType='D',Updated=TO_TIMESTAMP('2021-11-04 13:31:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540318
;

-- 2021-11-04T12:32:29.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET EntityType='D',Updated=TO_TIMESTAMP('2021-11-04 13:32:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541468
;


-- 2021-11-04T13:58:23.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541473,TO_TIMESTAMP('2021-11-04 14:58:22','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_FrameAgreement_Order_ID',TO_TIMESTAMP('2021-11-04 14:58:22','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-11-04T13:58:23.094Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541473 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-11-04T13:59:07.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,573002,0,541473,259,TO_TIMESTAMP('2021-11-04 14:59:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-11-04 14:59:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-04T13:59:47.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=541473,Updated=TO_TIMESTAMP('2021-11-04 14:59:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540318
;


-- 2021-11-04T14:24:12.588Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Key=2161,Updated=TO_TIMESTAMP('2021-11-04 15:24:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541468
;

-- 2021-11-04T14:26:25.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540100,Updated=TO_TIMESTAMP('2021-11-04 15:26:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540318
;

-- 2021-11-04T14:28:08.763Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=541468,Updated=TO_TIMESTAMP('2021-11-04 15:28:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540318
;

-- 2021-11-04T14:29:42.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Key=573002,Updated=TO_TIMESTAMP('2021-11-04 15:29:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541468
;

-- 2021-11-04T14:31:11.267Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540676,Updated=TO_TIMESTAMP('2021-11-04 15:31:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540318
;

-- 2021-11-04T14:34:51.013Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=541473, AD_Reference_Target_ID=540678,Updated=TO_TIMESTAMP('2021-11-04 15:34:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540318
;

-- 2021-11-04T14:37:11.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540666,Updated=TO_TIMESTAMP('2021-11-04 15:37:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540318
;

-- 2021-11-04T14:40:07.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541474,TO_TIMESTAMP('2021-11-04 15:40:07','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','C_Order_SO_Target_For_C_FrameAgreement_Order_ID',TO_TIMESTAMP('2021-11-04 15:40:07','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-11-04T14:40:07.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541474 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-11-04T14:40:12.739Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET EntityType='D',Updated=TO_TIMESTAMP('2021-11-04 15:40:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541474
;

-- 2021-11-04T14:40:46.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,2163,0,541474,259,TO_TIMESTAMP('2021-11-04 15:40:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-11-04 15:40:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-04T14:40:59.432Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Key=2161,Updated=TO_TIMESTAMP('2021-11-04 15:40:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541474
;

-- 2021-11-04T14:41:47.864Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='C_Order.C_Order_ID=@C_FrameAgreement_Order_ID@',Updated=TO_TIMESTAMP('2021-11-04 15:41:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541474
;

-- 2021-11-04T14:43:43.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=541474,Updated=TO_TIMESTAMP('2021-11-04 15:43:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540318
;

