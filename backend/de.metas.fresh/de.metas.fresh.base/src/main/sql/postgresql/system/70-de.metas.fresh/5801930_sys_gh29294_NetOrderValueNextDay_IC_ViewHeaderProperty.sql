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
-- 2026-05-13T14:30:14.960Z
UPDATE AD_Element_Trl SET Name='Netto Auftragswert', PrintName='Netto Auftragswert',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-13 14:30:14.960000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584855 AND AD_Language='de_CH'
;

-- 2026-05-13T14:30:14.962Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-13T14:30:15.315Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584855,'de_CH')
;

-- Element: NetOrderValueNextDay
-- 2026-05-13T14:31:21.736Z
UPDATE AD_Element_Trl SET Name='Netto Auftragswert', PrintName='Netto Auftragswert',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-13 14:31:21.736000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584855 AND AD_Language='de_DE'
;

-- 2026-05-13T14:31:21.737Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-13T14:31:22.286Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584855,'de_DE')
;

-- 2026-05-13T14:31:22.288Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584855,'de_DE')
;

-- Element: NetOrderValueNextDay
-- 2026-05-14T09:05:30.733Z
UPDATE AD_Element_Trl SET Name='Net Order Value Tomorrow', PrintName='Net Order Value Tomorrow',Updated=TO_TIMESTAMP('2026-05-14 09:05:30.733000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584855 AND AD_Language='en_US'
;

-- 2026-05-14T09:05:30.792Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-14T09:05:37.667Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584855,'en_US')
;

-- Element: NetOrderValueNextDay
-- 2026-05-14T09:05:55.117Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Nettoauftragswert morgen', PrintName='Nettoauftragswert morgen',Updated=TO_TIMESTAMP('2026-05-14 09:05:55.117000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584855 AND AD_Language='de_DE'
;

-- 2026-05-14T09:05:55.174Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-14T09:05:59.876Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584855,'de_DE')
;

-- 2026-05-14T09:05:59.936Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584855,'de_DE')
;
