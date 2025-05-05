-- 2023-06-09T16:02:58.926761855Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582419,0,'IsShowProductDetails',TO_TIMESTAMP('2023-06-09 16:02:58.924','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','Show Product Details','Show Product Details',TO_TIMESTAMP('2023-06-09 16:02:58.924','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-09T16:02:58.937615623Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582419 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-06-09T16:03:15.245639462Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2023-06-09 16:03:15.245','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582419
;

-- 2023-06-09T16:03:15.248668275Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582419,'de_DE') 
;

-- Element: IsShowProductDetails
-- 2023-06-09T16:03:34.421444660Z
UPDATE AD_Element_Trl SET Name='Produktinfos zeigen',Updated=TO_TIMESTAMP('2023-06-09 16:03:34.421','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582419 AND AD_Language='de_DE'
;

-- 2023-06-09T16:03:34.423340973Z
UPDATE AD_Element SET Name='Produktinfos zeigen' WHERE AD_Element_ID=582419
;

-- 2023-06-09T16:03:34.769360345Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582419,'de_DE') 
;

-- 2023-06-09T16:03:34.771821610Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582419,'de_DE') 
;

-- Element: IsShowProductDetails
-- 2023-06-09T16:03:35.487949415Z
UPDATE AD_Element_Trl SET PrintName='Produktinfos zeigen',Updated=TO_TIMESTAMP('2023-06-09 16:03:35.487','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582419 AND AD_Language='de_DE'
;

-- 2023-06-09T16:03:35.488686029Z
UPDATE AD_Element SET PrintName='Produktinfos zeigen' WHERE AD_Element_ID=582419
;

-- 2023-06-09T16:03:35.824983700Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582419,'de_DE') 
;

-- 2023-06-09T16:03:35.825495943Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582419,'de_DE') 
;

-- Element: IsShowProductDetails
-- 2023-06-09T16:03:35.842695106Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-06-09 16:03:35.842','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582419 AND AD_Language='de_DE'
;

-- 2023-06-09T16:03:35.864293797Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582419,'de_DE') 
;

-- 2023-06-09T16:03:35.865179311Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582419,'de_DE') 
;

-- Element: IsShowProductDetails
-- 2023-06-09T16:03:41.626333133Z
UPDATE AD_Element_Trl SET Name='Produktinfos zeigen',Updated=TO_TIMESTAMP('2023-06-09 16:03:41.626','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582419 AND AD_Language='de_CH'
;

-- 2023-06-09T16:03:41.627153855Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582419,'de_CH') 
;

-- Element: IsShowProductDetails
-- 2023-06-09T16:03:42.853790209Z
UPDATE AD_Element_Trl SET PrintName='Produktinfos zeigen',Updated=TO_TIMESTAMP('2023-06-09 16:03:42.853','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582419 AND AD_Language='de_CH'
;

-- 2023-06-09T16:03:42.854897050Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582419,'de_CH') 
;

-- Element: IsShowProductDetails
-- 2023-06-09T16:03:42.928127162Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-06-09 16:03:42.927','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582419 AND AD_Language='de_CH'
;

-- 2023-06-09T16:03:42.929279929Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582419,'de_CH') 
;

-- Process: Saldobilanz (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: IsShowProductDetails
-- 2023-06-09T16:04:01.503313785Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,582419,0,540588,542639,20,'IsShowProductDetails',TO_TIMESTAMP('2023-06-09 16:04:01.5','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.fresh',0,'Y','N','Y','N','N','N','Produktinfos zeigen',90,TO_TIMESTAMP('2023-06-09 16:04:01.5','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-09T16:04:01.503913452Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542639 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-06-09T16:04:01.504534089Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(582419) 
;

-- Process: Saldobilanz (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: IsShowProductDetails
-- 2023-06-09T16:04:09.576070104Z
UPDATE AD_Process_Para SET DefaultValue='N',Updated=TO_TIMESTAMP('2023-06-09 16:04:09.575','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542639
;

-- 2023-06-09T16:05:39.848452104Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582420,0,'IsShowActivityDetails',TO_TIMESTAMP('2023-06-09 16:05:39.846','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','Show Activity Details','Show Activity Details',TO_TIMESTAMP('2023-06-09 16:05:39.846','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-09T16:05:39.849506286Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582420 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-06-09T16:05:41.021095670Z
UPDATE AD_Element SET EntityType='de.metas.acct',Updated=TO_TIMESTAMP('2023-06-09 16:05:41.02','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582420
;

-- 2023-06-09T16:05:41.021892728Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582420,'de_DE') 
;

-- Element: IsShowActivityDetails
-- 2023-06-09T16:06:03.372928690Z
UPDATE AD_Element_Trl SET Name='Kostenstelle Infos anzeigen',Updated=TO_TIMESTAMP('2023-06-09 16:06:03.372','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582420 AND AD_Language='de_DE'
;

-- 2023-06-09T16:06:03.373639015Z
UPDATE AD_Element SET Name='Kostenstelle Infos anzeigen' WHERE AD_Element_ID=582420
;

-- 2023-06-09T16:06:03.640645246Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582420,'de_DE') 
;

-- 2023-06-09T16:06:03.641482139Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582420,'de_DE') 
;

-- Element: IsShowActivityDetails
-- 2023-06-09T16:06:04.971114336Z
UPDATE AD_Element_Trl SET PrintName='Kostenstelle Infos anzeigen',Updated=TO_TIMESTAMP('2023-06-09 16:06:04.971','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582420 AND AD_Language='de_DE'
;

-- 2023-06-09T16:06:04.971641617Z
UPDATE AD_Element SET PrintName='Kostenstelle Infos anzeigen' WHERE AD_Element_ID=582420
;

-- 2023-06-09T16:06:05.267813269Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582420,'de_DE') 
;

-- 2023-06-09T16:06:05.268613172Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582420,'de_DE') 
;

-- Element: IsShowActivityDetails
-- 2023-06-09T16:06:05.285864353Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-06-09 16:06:05.285','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582420 AND AD_Language='de_DE'
;

-- 2023-06-09T16:06:05.286654598Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582420,'de_DE') 
;

-- 2023-06-09T16:06:05.287284742Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582420,'de_DE') 
;

-- Element: IsShowActivityDetails
-- 2023-06-09T16:06:09.683938547Z
UPDATE AD_Element_Trl SET Name='Kostenstelle Infos anzeigen',Updated=TO_TIMESTAMP('2023-06-09 16:06:09.683','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582420 AND AD_Language='de_CH'
;

-- 2023-06-09T16:06:09.684987047Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582420,'de_CH') 
;

-- Element: IsShowActivityDetails
-- 2023-06-09T16:06:10.859860531Z
UPDATE AD_Element_Trl SET PrintName='Kostenstelle Infos anzeigen',Updated=TO_TIMESTAMP('2023-06-09 16:06:10.859','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582420 AND AD_Language='de_CH'
;

-- 2023-06-09T16:06:10.860974224Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582420,'de_CH') 
;

-- Element: IsShowActivityDetails
-- 2023-06-09T16:06:10.901733364Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-06-09 16:06:10.901','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582420 AND AD_Language='de_CH'
;

-- 2023-06-09T16:06:10.902862856Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582420,'de_CH') 
;

-- 2023-06-09T16:06:24.557921952Z
UPDATE AD_Element SET EntityType='de.metas.acct',Updated=TO_TIMESTAMP('2023-06-09 16:06:24.557','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582092
;

-- 2023-06-09T16:06:24.558663536Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582092,'de_DE') 
;

-- Process: Saldobilanz (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: IsShowProductDetails
-- 2023-06-09T16:06:32.378311509Z
UPDATE AD_Process_Para SET EntityType='de.metas.acct',Updated=TO_TIMESTAMP('2023-06-09 16:06:32.378','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542639
;

-- Process: Saldobilanz (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: IsShowActivityDetails
-- 2023-06-09T16:07:05.371454644Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,582420,0,540588,542640,20,'IsShowActivityDetails',TO_TIMESTAMP('2023-06-09 16:07:05.37','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.acct',0,'Y','N','Y','N','N','N','Kostenstelle Infos anzeigen',100,TO_TIMESTAMP('2023-06-09 16:07:05.37','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-09T16:07:05.372246953Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542640 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-06-09T16:07:05.373135844Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(582420) 
;

-- Process: Saldobilanz (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: IsShowActivityDetails
-- 2023-06-09T16:07:11.547582337Z
UPDATE AD_Process_Para SET DefaultValue='N',Updated=TO_TIMESTAMP('2023-06-09 16:07:11.547','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_Para_ID=542640
;
