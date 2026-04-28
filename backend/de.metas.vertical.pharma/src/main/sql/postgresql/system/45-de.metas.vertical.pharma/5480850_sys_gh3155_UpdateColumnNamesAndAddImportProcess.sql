-- 2017-12-18T16:26:45.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=543545, ColumnName='A00DARFO', Description=NULL, Help=NULL, Name='A00DARFO',Updated=TO_TIMESTAMP('2017-12-18 16:26:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558074
;

-- 2017-12-18T16:26:45.511
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='A00DARFO', Description=NULL, Help=NULL WHERE AD_Column_ID=558074 AND IsCentrallyMaintained='Y'
;

ALTER TABLE I_Product RENAME COLUMN M_DosageForm_Name TO A00DARFO;

-- 2017-12-18T16:27:52.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=543514
;

-- 2017-12-18T16:27:52.817
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=543514
;

-- 2017-12-18T16:29:08.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=543544, ColumnName='A00PGEINH', Description=NULL, EntityType='de.metas.vertical.pharma', Help=NULL, Name='A00PGEINH',Updated=TO_TIMESTAMP('2017-12-18 16:29:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558065
;

-- 2017-12-18T16:29:08.085
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='A00PGEINH', Description=NULL, Help=NULL WHERE AD_Column_ID=558065 AND IsCentrallyMaintained='Y'
;

ALTER TABLE I_Product RENAME COLUMN Package_UOM_Name TO A00PGEINH;

-- 2017-12-18T16:29:29.659
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=543512
;

-- 2017-12-18T16:29:29.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element WHERE AD_Element_ID=543512
;

-- 2017-12-18T16:29:56.769
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Package UOM Name',Updated=TO_TIMESTAMP('2017-12-18 16:29:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560698
;

-- 2017-12-18T16:30:21.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Dosage Form Name',Updated=TO_TIMESTAMP('2017-12-18 16:30:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560703
;

-- 2017-12-18T17:14:58.045
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsOneInstanceOnly,IsReport,IsServerProcess,IsUseBPartnerLanguage,LockWaitTimeout,Name,RefreshAllAfterExecution,ShowHelp,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,540903,'Y','org.adempiere.impexp.process.ImportPharmaProduct','N',TO_TIMESTAMP('2017-12-18 17:14:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y','N','N','N','N','N','N','Y',0,'Import Pharma Product','N','Y','Java',TO_TIMESTAMP('2017-12-18 17:14:57','YYYY-MM-DD HH24:MI:SS'),100,'ImportPharmaProduct')
;

-- 2017-12-18T17:14:58.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_ID=540903 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2017-12-18T17:15:24.269
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_QuickAction,WEBUI_QuickAction_Default) VALUES (0,0,540903,540880,TO_TIMESTAMP('2017-12-18 17:15:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.vertical.pharma','Y',TO_TIMESTAMP('2017-12-18 17:15:24','YYYY-MM-DD HH24:MI:SS'),100,'N','N')
;


-- 2017-12-18T17:40:26.665
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=540745,Updated=TO_TIMESTAMP('2017-12-18 17:40:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558279
;

-- 2017-12-18T17:46:27.826
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('i_pharma_product','I_IsImported','CHAR(1)',null,null)
;

ALTER TABLE i_pharma_product DROP CONSTRAINT i_pharma_product_i_isimported_check;


-- 2017-12-19T11:31:35.632
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.impexp.process.ImportPharmaProduct',Updated=TO_TIMESTAMP('2017-12-19 11:31:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540903
;



