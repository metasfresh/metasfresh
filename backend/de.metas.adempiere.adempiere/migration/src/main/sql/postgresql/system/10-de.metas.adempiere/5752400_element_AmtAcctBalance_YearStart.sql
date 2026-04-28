-- Run mode: SWING_CLIENT
-- select migrationscript_ignore('10-de.metas.adempiere/5752400_element_AmtAcctBalance_YearStart.sql');

-- 2025-04-22T07:50:25.520Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583589,0,'AmtAcctBalance_YearStart',TO_TIMESTAMP('2025-04-22 07:50:25.092000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','D','','Y','Accounted Balance (Year Start)','Accounted Balance (Year Start)',TO_TIMESTAMP('2025-04-22 07:50:25.092000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-22T07:50:25.529Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583589 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: AmtAcctBalance
-- 2025-04-22T07:52:01.977Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Gebuchter Saldo',Updated=TO_TIMESTAMP('2025-04-22 07:52:01.977000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2649 AND AD_Language='de_CH'
;

-- 2025-04-22T07:52:01.979Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-22T07:52:02.609Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2649,'de_CH')
;

-- Element: AmtAcctBalance
-- 2025-04-22T07:52:07.580Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Gebuchter Saldo',Updated=TO_TIMESTAMP('2025-04-22 07:52:07.580000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=2649 AND AD_Language='de_DE'
;

-- 2025-04-22T07:52:07.582Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-22T07:52:09.309Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(2649,'de_DE')
;

-- 2025-04-22T07:52:09.311Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2649,'de_DE')
;

-- Element: AmtAcctBalance_YearStart
-- 2025-04-22T07:52:28.962Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Gebuchter Saldo (Jahresbeginn)', PrintName='Gebuchter Saldo (Jahresbeginn)',Updated=TO_TIMESTAMP('2025-04-22 07:52:28.962000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583589 AND AD_Language='de_DE'
;

-- 2025-04-22T07:52:28.964Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-22T07:52:29.689Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583589,'de_DE')
;

-- 2025-04-22T07:52:29.690Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583589,'de_DE')
;

-- Element: AmtAcctBalance_YearStart
-- 2025-04-22T07:52:31.452Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-04-22 07:52:31.451000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583589 AND AD_Language='en_US'
;

-- 2025-04-22T07:52:31.455Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583589,'en_US')
;

-- Element: AmtAcctBalance_YearStart
-- 2025-04-22T07:52:36.400Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Gebuchter Saldo (Jahresbeginn)', PrintName='Gebuchter Saldo (Jahresbeginn)',Updated=TO_TIMESTAMP('2025-04-22 07:52:36.400000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583589 AND AD_Language='de_CH'
;

-- 2025-04-22T07:52:36.401Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-22T07:52:36.801Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583589,'de_CH')
;

