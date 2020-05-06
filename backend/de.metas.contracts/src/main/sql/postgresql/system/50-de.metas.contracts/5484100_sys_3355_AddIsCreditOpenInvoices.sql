-- 2018-01-29T18:12:09.220
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET SeqNo=15,Updated=TO_TIMESTAMP('2018-01-29 18:12:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=540001
;

-- 2018-01-29T18:12:17.396
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET SeqNo=10,Updated=TO_TIMESTAMP('2018-01-29 18:12:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541229
;

-- 2018-01-29T18:12:22.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET SeqNo=9,Updated=TO_TIMESTAMP('2018-01-29 18:12:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541232
;

-- 2018-01-29T18:13:45.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543838,0,'IsCreditOpenInvoices',TO_TIMESTAMP('2018-01-29 18:13:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.contracts','Y','isCreditOpenInvoices','isCreditOpenInvoices',TO_TIMESTAMP('2018-01-29 18:13:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-29T18:13:45.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543838 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-01-29T18:14:06.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,543838,0,540001,541262,20,'IsCreditOpenInvoices',TO_TIMESTAMP('2018-01-29 18:14:06','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.contracts',0,'Y','N','Y','N','N','N','isCreditOpenInvoices',25,TO_TIMESTAMP('2018-01-29 18:14:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-29T18:14:06.485
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Process_Para_ID=541262 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2018-01-29T18:14:23.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET SeqNo=8,Updated=TO_TIMESTAMP('2018-01-29 18:14:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541262
;

-- 2018-01-29T18:28:00.900
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Gutschrift für offene Rechnungen', PrintName='Gutschrift für offene Rechnungen',Updated=TO_TIMESTAMP('2018-01-29 18:28:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543838
;

-- 2018-01-29T18:28:00.904
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsCreditOpenInvoices', Name='Gutschrift für offene Rechnungen', Description=NULL, Help=NULL WHERE AD_Element_ID=543838
;

-- 2018-01-29T18:28:00.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsCreditOpenInvoices', Name='Gutschrift für offene Rechnungen', Description=NULL, Help=NULL, AD_Element_ID=543838 WHERE UPPER(ColumnName)='ISCREDITOPENINVOICES' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-01-29T18:28:00.918
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsCreditOpenInvoices', Name='Gutschrift für offene Rechnungen', Description=NULL, Help=NULL WHERE AD_Element_ID=543838 AND IsCentrallyMaintained='Y'
;

-- 2018-01-29T18:28:00.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Gutschrift für offene Rechnungen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543838) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543838)
;

-- 2018-01-29T18:28:00.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Gutschrift für offene Rechnungen', Name='Gutschrift für offene Rechnungen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543838)
;

-- 2018-01-29T18:28:14.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-01-29 18:28:14','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Credit Open Invoices',PrintName='Credit Open Invoices' WHERE AD_Element_ID=543838 AND AD_Language='en_US'
;

-- 2018-01-29T18:28:14.760
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543838,'en_US') 
;

-- 2018-01-29T18:36:10.408
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Name='Offene rechnungen gutschreiben', PrintName='Offene rechnungen gutschreiben',Updated=TO_TIMESTAMP('2018-01-29 18:36:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543838
;

-- 2018-01-29T18:36:10.409
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsCreditOpenInvoices', Name='Offene rechnungen gutschreiben', Description=NULL, Help=NULL WHERE AD_Element_ID=543838
;

-- 2018-01-29T18:36:10.419
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsCreditOpenInvoices', Name='Offene rechnungen gutschreiben', Description=NULL, Help=NULL, AD_Element_ID=543838 WHERE UPPER(ColumnName)='ISCREDITOPENINVOICES' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-01-29T18:36:10.420
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsCreditOpenInvoices', Name='Offene rechnungen gutschreiben', Description=NULL, Help=NULL WHERE AD_Element_ID=543838 AND IsCentrallyMaintained='Y'
;

-- 2018-01-29T18:36:10.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Offene rechnungen gutschreiben', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543838) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543838)
;

-- 2018-01-29T18:36:10.433
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Offene rechnungen gutschreiben', Name='Offene rechnungen gutschreiben' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543838)
;

