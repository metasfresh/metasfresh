-- Run mode: SWING_CLIENT

-- 2026-05-11T19:15:42.593Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584855,0,'NetOrderValueNextDay',TO_TIMESTAMP('2026-05-11 19:15:42.092000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Netto Auftragswert','Netto Auftragswert',TO_TIMESTAMP('2026-05-11 19:15:42.092000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-11T19:15:42.675Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584855 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: NetOrderValueNextDay
-- 2026-05-11T19:16:12.468Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Net Order Value', PrintName='Net Order Value',Updated=TO_TIMESTAMP('2026-05-11 19:16:12.468000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584855 AND AD_Language='en_US'
;

-- 2026-05-11T19:16:12.538Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-11T19:16:16.523Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584855,'en_US')
;

-- Element: NetOrderValueNextDay
-- 2026-05-13T13:07:55.710Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-13 13:07:55.710000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584855 AND AD_Language='de_CH'
;

-- 2026-05-13T13:07:55.734Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584855,'de_CH')
;

-- Element: NetOrderValueNextDay
-- 2026-05-13T13:08:13.530Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-13 13:08:13.530000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584855 AND AD_Language='de_DE'
;

-- 2026-05-13T13:08:13.533Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584855,'de_DE')
;
