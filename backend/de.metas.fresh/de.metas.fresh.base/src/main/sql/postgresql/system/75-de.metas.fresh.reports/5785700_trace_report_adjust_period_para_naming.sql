-- Run mode: SWING_CLIENT

-- 2026-01-28T09:39:27.469Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584470,0,'C_Period_St_ID',TO_TIMESTAMP('2026-01-28 09:39:27.288000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.fresh','Y','Startperiode','Startperiode',TO_TIMESTAMP('2026-01-28 09:39:27.288000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-28T09:39:27.480Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584470 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: C_Period_St_ID
-- 2026-01-28T09:39:45.923Z
UPDATE AD_Element_Trl SET IsTranslated='Y', PrintName='Start Period',Updated=TO_TIMESTAMP('2026-01-28 09:39:45.923000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584470 AND AD_Language='en_US'
;

-- 2026-01-28T09:39:45.925Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-28T09:39:46.446Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584470,'en_US')
;

-- Element: C_Period_St_ID
-- 2026-01-28T09:39:47.426Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-01-28 09:39:47.426000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584470 AND AD_Language='de_CH'
;

-- 2026-01-28T09:39:47.435Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584470,'de_CH')
;

-- Element: C_Period_St_ID
-- 2026-01-28T09:39:52.020Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-01-28 09:39:52.020000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584470 AND AD_Language='de_DE'
;

-- 2026-01-28T09:39:52.023Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584470,'de_DE')
;

-- 2026-01-28T09:39:52.025Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584470,'de_DE')
;

-- 2026-01-28T09:41:16.383Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584471,0,'C_Period_End_ID',TO_TIMESTAMP('2026-01-28 09:41:16.260000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.fresh','Y','Endperiode','Endperiode',TO_TIMESTAMP('2026-01-28 09:41:16.260000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-28T09:41:16.385Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584471 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: C_Period_End_ID
-- 2026-01-28T09:41:35.678Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='End Period', PrintName='End Period',Updated=TO_TIMESTAMP('2026-01-28 09:41:35.678000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584471 AND AD_Language='en_US'
;

-- 2026-01-28T09:41:35.680Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-28T09:41:35.903Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584471,'en_US')
;

-- Element: C_Period_End_ID
-- 2026-01-28T09:41:37.093Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-01-28 09:41:37.093000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584471 AND AD_Language='de_CH'
;

-- 2026-01-28T09:41:37.096Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584471,'de_CH')
;

-- Element: C_Period_End_ID
-- 2026-01-28T09:41:41.160Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-01-28 09:41:41.160000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584471 AND AD_Language='de_DE'
;

-- 2026-01-28T09:41:41.162Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584471,'de_DE')
;

-- 2026-01-28T09:41:41.164Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584471,'de_DE')
;

-- Element: C_Period_St_ID
-- 2026-01-28T09:41:55.356Z
UPDATE AD_Element_Trl SET Name='Start Period',Updated=TO_TIMESTAMP('2026-01-28 09:41:55.356000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584470 AND AD_Language='en_US'
;

-- 2026-01-28T09:41:55.357Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-28T09:41:55.651Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584470,'en_US')
;

-- Process: Sales Trace Excel (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: C_Period_St_ID
-- 2026-01-28T09:42:36.964Z
UPDATE AD_Process_Para SET AD_Element_ID=584470, Description=NULL, Help=NULL, Name='Startperiode',Updated=TO_TIMESTAMP('2026-01-28 09:42:36.964000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=540984
;

-- 2026-01-28T09:42:36.965Z
UPDATE AD_Process_Para_Trl trl SET Description=NULL,Help=NULL,Name='Startperiode' WHERE AD_Process_Para_ID=540984 AND AD_Language='de_DE'
;

-- Process: Sales Trace Excel (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: C_Period_End_ID
-- 2026-01-28T09:42:52.996Z
UPDATE AD_Process_Para SET AD_Element_ID=584471, Description=NULL, Help=NULL, Name='Endperiode',Updated=TO_TIMESTAMP('2026-01-28 09:42:52.996000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=540986
;

-- 2026-01-28T09:42:52.998Z
UPDATE AD_Process_Para_Trl trl SET Description=NULL,Help=NULL,Name='Endperiode' WHERE AD_Process_Para_ID=540986 AND AD_Language='de_DE'
;

