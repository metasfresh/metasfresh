
-- Value: deactivate_old_plv_productprices
-- Classname: de.metas.process.ExecuteUpdateSQL
-- 2026-02-18T16:06:36.876Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,Created,CreatedBy,Description,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585572,'Y','de.metas.process.ExecuteUpdateSQL',TO_TIMESTAMP('2026-02-18 16:06:36.875000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Purpose: Deactivates old Price List Versions (PLVs) and their associated
         Product Prices based on the validfrom date and optional filters.

CRITICAL SAFETY FEATURE: Only deactivates PLVs when a newer version exists
         for the same Price List. This prevents deactivating the latest/only
         PLV for a price list, ensuring pricing data continuity.

IMPORTANT: The "Valid Days Back" parameter is INCLUSIVE.
           Example: p_valid_days_back = 30 means:
           "Deactivate PLVs where validfrom is 30 days ago OR OLDER"
           Formula: validfrom <= NOW() - INTERVAL ''X days''','org.adempiere.pricing','Y','N','N','N','N','Y','N','N','N','Y','N','Y','Deactivate old price list version and product prices','json','N','N','xls','SQL',TO_TIMESTAMP('2026-02-18 16:06:36.875000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'deactivate_old_plv_productprices')
;

-- 2026-02-18T16:06:36.878Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585572 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;



-- Run mode: WEBUI

-- Process: deactivate_old_plv_productprices(de.metas.process.ExecuteUpdateSQL)
-- ParameterName: M_PricingSystem_ID
-- 2026-02-18T17:33:18.220Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,505135,0,585572,543122,30,'M_PricingSystem_ID',TO_TIMESTAMP('2026-02-18 17:33:18.212000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.','org.adempiere.pricing',0,'Welche Preisliste herangezogen wird, hängt in der Regel von der Lieferaddresse des jeweiligen Gschäftspartners ab.','Y','N','Y','N','N','N','Preissystem',10,TO_TIMESTAMP('2026-02-18 17:33:18.212000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-18T17:33:18.222Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543122 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: deactivate_old_plv_productprices(de.metas.process.ExecuteUpdateSQL)
-- ParameterName: M_PriceList_ID
-- 2026-02-18T17:36:03.332Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,449,0,585572,543123,30,'M_PriceList_ID',TO_TIMESTAMP('2026-02-18 17:36:03.331000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnung der Preisliste','org.adempiere.pricing',0,'Preislisten werden verwendet, um Preis, Spanne und Kosten einer ge- oder verkauften Ware zu bestimmen.','Y','N','Y','N','N','N','Preisliste',20,TO_TIMESTAMP('2026-02-18 17:36:03.331000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-18T17:36:03.333Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543123 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: deactivate_old_plv_productprices(de.metas.process.ExecuteUpdateSQL)
-- ParameterName: BasePriceList_ID
-- 2026-02-18T17:36:42.585Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1259,0,585572,543124,30,'BasePriceList_ID',TO_TIMESTAMP('2026-02-18 17:36:42.584000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Pricelist to be used, if product not found on this pricelist','org.adempiere.pricing',0,'The Base Price List identifies the default price list to be used if a product is not found on the selected price list','Y','N','Y','N','N','N','Basis Preisliste',30,TO_TIMESTAMP('2026-02-18 17:36:42.584000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-18T17:36:42.594Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543124 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: deactivate_old_plv_productprices(de.metas.process.ExecuteUpdateSQL)
-- ParameterName: BasePriceList_ID
-- 2026-02-18T17:37:14.818Z
UPDATE AD_Process_Para SET AD_Reference_Value_ID=166,Updated=TO_TIMESTAMP('2026-02-18 17:37:14.818000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543124
;

-- Process: deactivate_old_plv_productprices(de.metas.process.ExecuteUpdateSQL)
-- ParameterName: M_Pricelist_Version_Base_ID
-- 2026-02-18T17:42:18.975Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1228,0,585572,543125,30,'M_Pricelist_Version_Base_ID',TO_TIMESTAMP('2026-02-18 17:42:18.974000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Basis für Preiskalkulationen','org.adempiere.pricing',0,'Die Basis-Preislistenversion gibt die Preislistenversion an, die als Basis für Preiskalkulationen verwendet wird','Y','N','Y','N','N','N','Basis-Preislistenversion',40,TO_TIMESTAMP('2026-02-18 17:42:18.974000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-18T17:42:18.976Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543125 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: deactivate_old_plv_productprices(de.metas.process.ExecuteUpdateSQL)
-- ParameterName: M_Pricelist_Version_Base_ID
-- 2026-02-18T17:43:00.069Z
UPDATE AD_Process_Para SET AD_Reference_Value_ID=541033,Updated=TO_TIMESTAMP('2026-02-18 17:43:00.069000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543125
;

-- Process: deactivate_old_plv_productprices(de.metas.process.ExecuteUpdateSQL)
-- ParameterName: IsSOTrx
-- 2026-02-18T17:43:37.792Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,1106,0,585572,543126,20,'IsSOTrx',TO_TIMESTAMP('2026-02-18 17:43:37.791000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Dies ist eine Verkaufstransaktion','org.adempiere.pricing',0,'The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','Y','N','N','N','Verkaufstransaktion',50,TO_TIMESTAMP('2026-02-18 17:43:37.791000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-18T17:43:37.793Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543126 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2026-02-18T17:49:09.101Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584568,0,'ValidDaysBack',TO_TIMESTAMP('2026-02-18 17:49:09.091000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Days Back','Days Back',TO_TIMESTAMP('2026-02-18 17:49:09.091000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-18T17:49:09.102Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584568 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-02-18T17:49:16.523Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2026-02-18 17:49:16.523000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584568
;

-- 2026-02-18T17:49:16.552Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584568,'de_DE')
;

-- Process: deactivate_old_plv_productprices(de.metas.process.ExecuteUpdateSQL)
-- ParameterName: ValidDaysBack
-- 2026-02-18T17:49:46.076Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584568,0,585572,543128,11,'ValidDaysBack',TO_TIMESTAMP('2026-02-18 17:49:46.076000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'org.adempiere.pricing',0,'Y','N','Y','N','N','N','Days Back',60,TO_TIMESTAMP('2026-02-18 17:49:46.076000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-18T17:49:46.077Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543128 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: deactivate_old_plv_productprices(de.metas.process.ExecuteUpdateSQL)
-- ParameterName: ValidDaysBack
-- 2026-02-18T17:49:48.418Z
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2026-02-18 17:49:48.418000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543128
;

-- Process: deactivate_old_plv_productprices(de.metas.process.ExecuteUpdateSQL)
-- ParameterName: ValidDaysBack
-- 2026-02-18T17:49:55.126Z
UPDATE AD_Process_Para SET FieldLength=1,Updated=TO_TIMESTAMP('2026-02-18 17:49:55.126000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543128
;

-- 2026-02-18T17:51:21.240Z
UPDATE AD_Element SET Help='Minimum days for PLV deactivation (INCLUSIVE). PLVs with validfrom this many days ago or older are eligible for deactivation.
Example: 30 means PLVs from 30 days ago or earlier will be deactivated (only if a newer version exists).',Updated=TO_TIMESTAMP('2026-02-18 17:51:21.240000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584568
;

-- 2026-02-18T17:51:21.241Z
UPDATE AD_Element_Trl trl SET Help='Minimum days for PLV deactivation (INCLUSIVE). PLVs with validfrom this many days ago or older are eligible for deactivation.
Example: 30 means PLVs from 30 days ago or earlier will be deactivated (only if a newer version exists).' WHERE AD_Element_ID=584568 AND AD_Language='de_DE'
;

-- 2026-02-18T17:51:21.243Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584568,'de_DE')
;

-- Value: deactivate_old_plv_productprices
-- Classname: de.metas.process.ExecuteUpdateSQL
-- 2026-02-18T18:59:30.439Z
UPDATE AD_Process SET SQLStatement='SELECT deactivate_old_plv_productprices(p_m_pricingsystem_id => @M_PricingSystem_ID/NULL@,
                                        p_m_pricelist_id => @M_PriceList_ID/NULL@,
                                        p_basepricelist_id => @BasePriceList_ID/NULL@,
                                        p_base_plv_pricelist_id => @M_Pricelist_Version_Base_ID/NULL@,
                                        p_issotrx => @IsSOTrx/NULL@,
                                        p_valid_days_back => @ValidDaysBack/NULL@)
;',Updated=TO_TIMESTAMP('2026-02-18 18:59:30.439000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585572
;


-- Value: deactivate_old_plv_productprices
-- Classname: de.metas.process.ExecuteUpdateSQL
-- 2026-02-18T19:15:52.191Z
UPDATE AD_Process SET SQLStatement='SELECT deactivate_old_plv_productprices(p_m_pricingsystem_id => @M_PricingSystem_ID/NULL@, p_m_pricelist_id => @M_PriceList_ID/NULL@, p_basepricelist_id => @BasePriceList_ID/NULL@, p_base_plv_pricelist_id => @M_Pricelist_Version_Base_ID/NULL@, p_issotrx => @IsSOTrx/NULL@, p_valid_days_back => @ValidDaysBack/NULL@)
;',Updated=TO_TIMESTAMP('2026-02-18 19:15:52.191000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585572
;

-- Process: deactivate_old_plv_productprices(de.metas.process.ExecuteUpdateSQL)
-- ParameterName: IsSOTrx
-- 2026-02-18T19:18:41.585Z
UPDATE AD_Process_Para SET AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2026-02-18 19:18:41.585000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543126
;

-- Process: deactivate_old_plv_productprices(de.metas.process.ExecuteUpdateSQL)
-- ParameterName: IsSOTrx
-- 2026-02-18T19:18:44.385Z
UPDATE AD_Process_Para SET AD_Reference_Value_ID=319,Updated=TO_TIMESTAMP('2026-02-18 19:18:44.385000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543126
;



----------------------------- Add scheduler  -----------------------

-- 2026-02-18T17:54:17.874Z
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,CronPattern,EntityType,Frequency,IsActive,IsIgnoreProcessingTime,KeepLogDays,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (0,0,585572,0,550123,TO_TIMESTAMP('2026-02-18 17:54:17.855000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'05 00 * * *','D',0,'Y','N',7,'Deactivate old price list version and product prices','N','P','C','NEW',100,TO_TIMESTAMP('2026-02-18 17:54:17.855000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-18T17:54:35.482Z
INSERT INTO AD_Scheduler_Para (AD_Client_ID,AD_Org_ID,AD_Process_Para_ID,AD_Scheduler_ID,AD_Scheduler_Para_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,543128,550123,540053,TO_TIMESTAMP('2026-02-18 17:54:35.481000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',TO_TIMESTAMP('2026-02-18 17:54:35.481000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-18T17:54:52.424Z
UPDATE AD_Scheduler_Para SET ParameterDefault='28',Updated=TO_TIMESTAMP('2026-02-18 17:54:52.424000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Scheduler_Para_ID=540053
;

-- 2026-02-18T17:55:36.374Z
UPDATE AD_Scheduler SET IsActive='N',Updated=TO_TIMESTAMP('2026-02-18 17:55:36.374000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Scheduler_ID=550123
;

