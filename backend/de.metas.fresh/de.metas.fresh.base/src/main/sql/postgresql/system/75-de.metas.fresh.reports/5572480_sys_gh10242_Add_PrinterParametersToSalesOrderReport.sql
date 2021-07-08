-- 2020-11-17T07:57:58.314Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578551,0,TO_TIMESTAMP('2020-11-16 09:57:58','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','PRINTER_OPTS_IsPrintLogo','Print Logo',TO_TIMESTAMP('2020-11-16 09:57:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-16T07:57:58.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578551 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-11-16T07:58:07.571Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', PrintName='Logo drucken',Updated=TO_TIMESTAMP('2020-11-16 09:58:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578551 AND AD_Language='de_CH'
;

-- 2020-11-16T07:58:07.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578551,'de_CH') 
;

-- 2020-11-16T07:58:12.839Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', PrintName='Logo drucken',Updated=TO_TIMESTAMP('2020-11-16 09:58:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578551 AND AD_Language='de_DE'
;

-- 2020-11-16T07:58:12.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578551,'de_DE') 
;

-- 2020-11-16T07:58:12.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578551,'de_DE') 
;

-- 2020-11-16T07:58:12.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Logo drucken', Name='PRINTER_OPTS_IsPrintLogo' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578551)
;

-- 2020-11-16T07:58:36.374Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578552,0,TO_TIMESTAMP('2020-11-16 09:58:36','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','PRINTER_OPTS_IsPrintTotals','Print Totals',TO_TIMESTAMP('2020-11-16 09:58:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-16T07:58:36.378Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578552 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-11-16T07:58:41.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2020-11-16 09:58:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578552
;

-- 2020-11-16T07:58:50.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', PrintName='Gesamtsummen drucken',Updated=TO_TIMESTAMP('2020-11-16 09:58:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578552 AND AD_Language='de_DE'
;

-- 2020-11-16T07:58:50.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578552,'de_DE') 
;

-- 2020-11-16T07:58:50.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578552,'de_DE') 
;

-- 2020-11-16T07:58:50.741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Gesamtsummen drucken', Name='PRINTER_OPTS_IsPrintTotals' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578552)
;

-- 2020-11-16T07:59:04.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', PrintName='Gesamtsummen drucken',Updated=TO_TIMESTAMP('2020-11-16 09:59:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578552 AND AD_Language='de_CH'
;

-- 2020-11-16T07:59:04.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578552,'de_CH') 
;

-- 2020-11-16T07:59:10.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2020-11-16 09:59:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578551
;

-- 2020-11-16T07:59:26.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='PRINTER_OPTS_IsPrintLogo',Updated=TO_TIMESTAMP('2020-11-16 09:59:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578551
;

-- 2020-11-16T07:59:26.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PRINTER_OPTS_IsPrintLogo', Name='PRINTER_OPTS_IsPrintLogo', Description=NULL, Help=NULL WHERE AD_Element_ID=578551
;

-- 2020-11-16T07:59:26.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PRINTER_OPTS_IsPrintLogo', Name='PRINTER_OPTS_IsPrintLogo', Description=NULL, Help=NULL, AD_Element_ID=578551 WHERE UPPER(ColumnName)='PRINTER_OPTS_ISPRINTLOGO' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-11-16T07:59:26.230Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PRINTER_OPTS_IsPrintLogo', Name='PRINTER_OPTS_IsPrintLogo', Description=NULL, Help=NULL WHERE AD_Element_ID=578551 AND IsCentrallyMaintained='Y'
;

-- 2020-11-16T08:00:16.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,578551,0,110,541881,20,'PRINTER_OPTS_IsPrintLogo',TO_TIMESTAMP('2020-11-16 10:00:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','U',0,'Y','N','Y','N','Y','N','PRINTER_OPTS_IsPrintLogo',10,TO_TIMESTAMP('2020-11-16 10:00:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-16T08:00:16.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541881 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-11-16T08:00:33.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='PRINTER_OPTS_IsPrintTotals',Updated=TO_TIMESTAMP('2020-11-16 10:00:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578552
;

-- 2020-11-16T08:00:33.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='PRINTER_OPTS_IsPrintTotals', Name='PRINTER_OPTS_IsPrintTotals', Description=NULL, Help=NULL WHERE AD_Element_ID=578552
;

-- 2020-11-16T08:00:33.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PRINTER_OPTS_IsPrintTotals', Name='PRINTER_OPTS_IsPrintTotals', Description=NULL, Help=NULL, AD_Element_ID=578552 WHERE UPPER(ColumnName)='PRINTER_OPTS_ISPRINTTOTALS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-11-16T08:00:33.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='PRINTER_OPTS_IsPrintTotals', Name='PRINTER_OPTS_IsPrintTotals', Description=NULL, Help=NULL WHERE AD_Element_ID=578552 AND IsCentrallyMaintained='Y'
;

-- 2020-11-16T08:01:00.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,110,541882,20,'PRINTER_OPTS_IsPrintTotals',TO_TIMESTAMP('2020-11-16 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','U',1,'Y','N','Y','N','Y','N','PRINTER_OPTS_IsPrintTotals',20,TO_TIMESTAMP('2020-11-16 10:01:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-16T08:01:00.915Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=541882 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-11-16T08:01:06.934Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET FieldLength=1,Updated=TO_TIMESTAMP('2020-11-16 10:01:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541881
;

-- 2020-11-16T08:01:10.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2020-11-16 10:01:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541881
;

-- 2020-11-16T08:01:14.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2020-11-16 10:01:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541882
;

-- 2020-11-16T08:01:57.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Element_ID=578552,Updated=TO_TIMESTAMP('2020-11-16 10:01:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541882
;

-- 2020-11-16T08:02:11.606Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Gesamtbeträge drucken',Updated=TO_TIMESTAMP('2020-11-16 10:02:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578552 AND AD_Language='de_CH'
;

-- 2020-11-16T08:02:11.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578552,'de_CH') 
;

-- 2020-11-16T08:02:16.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Gesamtbeträge drucken',Updated=TO_TIMESTAMP('2020-11-16 10:02:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578552 AND AD_Language='de_DE'
;

-- 2020-11-16T08:02:16.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578552,'de_DE') 
;

-- 2020-11-16T08:02:16.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578552,'de_DE') 
;

-- 2020-11-16T08:02:16.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Gesamtbeträge drucken', Name='PRINTER_OPTS_IsPrintTotals' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578552)
;

-- 2020-11-16T08:02:28.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-11-16 10:02:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578552 AND AD_Language='en_US'
;

-- 2020-11-16T08:02:28.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578552,'en_US') 
;

-- 2020-11-16T08:02:40.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-11-16 10:02:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578551 AND AD_Language='en_US'
;

-- 2020-11-16T08:02:40.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578551,'en_US') 
;

