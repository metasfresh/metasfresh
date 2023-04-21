-- 2022-12-16T16:37:20.394Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,Help,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541701,TO_TIMESTAMP('2022-12-16 18:37:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.activecampaign','','Y','N','MKTG_Campaign_ID_Filter_Org',TO_TIMESTAMP('2022-12-16 18:37:20','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-12-16T16:37:20.394Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541701 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2022-12-16T16:38:14.052Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,559917,0,541701,540970,TO_TIMESTAMP('2022-12-16 18:38:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.base','Y','N','N',TO_TIMESTAMP('2022-12-16 18:38:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-16T16:38:19.024Z
UPDATE AD_Reference SET EntityType='de.metas.marketing.base',Updated=TO_TIMESTAMP('2022-12-16 18:38:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541701
;

-- 2022-12-16T16:39:26.360Z
UPDATE AD_Ref_Table SET AD_Key=559911,Updated=TO_TIMESTAMP('2022-12-16 18:39:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541701
;

-- 2022-12-16T16:40:10.442Z
UPDATE AD_Ref_Table SET WhereClause='MKTG_Campaign.AD_Org_ID =''@AD_Org_ID@''',Updated=TO_TIMESTAMP('2022-12-16 18:40:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541701
;

-- 2022-12-16T16:40:56.261Z
UPDATE AD_Process_Para SET AD_Reference_Value_ID=541701,Updated=TO_TIMESTAMP('2022-12-16 18:40:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541298
;

-- 2022-12-16T16:59:41.671Z
UPDATE AD_Ref_Table SET AD_Key=559917, WhereClause='MKTG_Campaign.AD_Org_ID =@AD_Org_ID@',Updated=TO_TIMESTAMP('2022-12-16 18:59:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541701
;
