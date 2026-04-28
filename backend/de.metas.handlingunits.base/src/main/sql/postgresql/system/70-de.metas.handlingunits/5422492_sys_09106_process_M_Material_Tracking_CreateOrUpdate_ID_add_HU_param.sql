
-- 17.07.2015 16:27:15
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,DisplayLogic,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,Updated,UpdatedBy) VALUES (0,0,540589,540723,20,'M_Material_Tracking_CreateOrUpdate_ID.CHANGE_HUs',TO_TIMESTAMP('2015-07-17 16:27:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','''N''','de.metas.handlingunits',0,'Y','N','Y','N','Y','N','Handlingunits aktualiseren','''N''',30,TO_TIMESTAMP('2015-07-17 16:27:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 17.07.2015 16:27:15
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=540723 AND NOT EXISTS (SELECT * FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 20.07.2015 08:12
-- URL zum Konzept
-- Neve display
UPDATE AD_Process_Para SET DisplayLogic='''N''=''Y''', ReadOnlyLogic=NULL,Updated=TO_TIMESTAMP('2015-07-20 08:12:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540723
;
