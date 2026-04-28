-- 2017-09-04T14:11:05.365
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Form_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,540070,0,540823,'N','N',TO_TIMESTAMP('2017-09-04 14:11:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','N','N','N','N','N','Y',0,'ReturnHUsFromCustomer','Y','Y','Java',TO_TIMESTAMP('2017-09-04 14:11:05','YYYY-MM-DD HH24:MI:SS'),100,'ReturnHUsFromCustomer')
;

-- 2017-09-04T14:11:05.384
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540823 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2017-09-04T14:12:47.317
-- URL zum Konzept
UPDATE AD_Column SET AD_Process_ID=540823,Updated=TO_TIMESTAMP('2017-09-04 14:12:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556818
;

-- 2017-09-04T14:12:50.570
-- URL zum Konzept
INSERT INTO t_alter_column values('m_inout','ReturnFromCustomer','CHAR(25)',null,null)
;

