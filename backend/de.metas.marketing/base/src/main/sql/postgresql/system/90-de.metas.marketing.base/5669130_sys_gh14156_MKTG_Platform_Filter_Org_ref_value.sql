-- 2022-12-16T17:26:13.665Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541702,TO_TIMESTAMP('2022-12-16 19:26:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.base','Y','N','MKTG_Platform_Filter_Org',TO_TIMESTAMP('2022-12-16 19:26:13','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-12-16T17:26:13.665Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541702 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2022-12-16T17:27:04.334Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,560081,0,541702,540979,TO_TIMESTAMP('2022-12-16 19:27:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.marketing.base','Y','N','N',TO_TIMESTAMP('2022-12-16 19:27:04','YYYY-MM-DD HH24:MI:SS'),100,'MKTG_Platform.AD_Org_ID=@AD_Org_ID@')
;

-- Column: MKTG_Campaign.MKTG_Platform_ID
-- 2022-12-16T17:28:15.273Z
UPDATE AD_Column SET AD_Reference_Value_ID=541702, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2022-12-16 19:28:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560098
;

-- 2022-12-16T17:28:18.157Z
INSERT INTO t_alter_column values('mktg_campaign','MKTG_Platform_ID','NUMERIC(10)',null,null)
;

