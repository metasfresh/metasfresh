-- 2021-06-04T09:24:29.660Z
-- URL zum Konzept
UPDATE AD_Process_Para SET SeqNo=50,Updated=TO_TIMESTAMP('2021-06-04 12:24:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542016
;

-- 2021-06-04T09:24:33.085Z
-- URL zum Konzept
UPDATE AD_Process_Para SET SeqNo=40,Updated=TO_TIMESTAMP('2021-06-04 12:24:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542015
;

-- 2021-06-04T09:25:03.059Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,837,0,584833,542021,30,'C_BP_BankAccount_ID',TO_TIMESTAMP('2021-06-04 12:25:02','YYYY-MM-DD HH24:MI:SS'),100,'Bankverbindung des Geschäftspartners','D',0,'Y','N','Y','N','N','N','Bankverbindung',30,TO_TIMESTAMP('2021-06-04 12:25:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-04T09:25:03.100Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542021 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-06-04T09:43:44.818Z
-- URL zum Konzept
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-06-04 12:43:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542021
;

-- 2021-06-04T13:02:09.027Z
-- URL zum Konzept
UPDATE AD_Process_Para SET IsAutocomplete='Y',Updated=TO_TIMESTAMP('2021-06-04 16:02:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542021
;

