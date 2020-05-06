-- 2019-08-15T11:24:54.006Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Datum Auftragseingang', PrintName='Datum Auftragseingang',Updated=TO_TIMESTAMP('2019-08-15 13:24:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1556 AND AD_Language='de_DE'
;

-- 2019-08-15T11:24:54.008Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1556,'de_DE') 
;

-- 2019-08-15T11:24:54.023Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(1556,'de_DE') 
;

-- 2019-08-15T11:24:54.026Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='DateContract', Name='Datum Auftragseingang', Description='Datum des Auftragseingangs', Help='The contract date is used to determine when the document becomes effective. This is usually the contract date.  The contract date is used in reports and report parameters.' WHERE AD_Element_ID=1556
;

-- 2019-08-15T11:24:54.028Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='DateContract', Name='Datum Auftragseingang', Description='Datum des Auftragseingangs', Help='The contract date is used to determine when the document becomes effective. This is usually the contract date.  The contract date is used in reports and report parameters.', AD_Element_ID=1556 WHERE UPPER(ColumnName)='DATECONTRACT' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-08-15T11:24:54.030Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='DateContract', Name='Datum Auftragseingang', Description='Datum des Auftragseingangs', Help='The contract date is used to determine when the document becomes effective. This is usually the contract date.  The contract date is used in reports and report parameters.' WHERE AD_Element_ID=1556 AND IsCentrallyMaintained='Y'
;

-- 2019-08-15T11:24:54.032Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Datum Auftragseingang', Description='Datum des Auftragseingangs', Help='The contract date is used to determine when the document becomes effective. This is usually the contract date.  The contract date is used in reports and report parameters.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1556) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1556)
;

-- 2019-08-15T11:24:54.055Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Datum Auftragseingang', Name='Datum Auftragseingang' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1556)
;

-- 2019-08-15T11:24:54.057Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Datum Auftragseingang', Description='Datum des Auftragseingangs', Help='The contract date is used to determine when the document becomes effective. This is usually the contract date.  The contract date is used in reports and report parameters.', CommitWarning = NULL WHERE AD_Element_ID = 1556
;

-- 2019-08-15T11:24:54.059Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Datum Auftragseingang', Description='Datum des Auftragseingangs', Help='The contract date is used to determine when the document becomes effective. This is usually the contract date.  The contract date is used in reports and report parameters.' WHERE AD_Element_ID = 1556
;

-- 2019-08-15T11:24:54.060Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Datum Auftragseingang', Description = 'Datum des Auftragseingangs', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1556
;

-- 2019-08-15T11:25:00.032Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-08-15 13:25:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1556 AND AD_Language='de_DE'
;

-- 2019-08-15T11:25:00.034Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1556,'de_DE') 
;

-- 2019-08-15T11:25:00.059Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(1556,'de_DE') 
;

-- 2019-08-15T11:25:16.126Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Date of Contract', PrintName='Date of Contract',Updated=TO_TIMESTAMP('2019-08-15 13:25:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1556 AND AD_Language='en_US'
;

-- 2019-08-15T11:25:16.128Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1556,'en_US') 
;

-- 2019-08-15T11:26:56.612Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Date planned finished', PrintName='Date planned finished',Updated=TO_TIMESTAMP('2019-08-15 13:26:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1557 AND AD_Language='en_US'
;

-- 2019-08-15T11:26:56.614Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1557,'en_US') 
;

-- 2019-08-15T12:27:06.087Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Request', PrintName='Request',Updated=TO_TIMESTAMP('2019-08-15 14:27:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=572582 AND AD_Language='en_US'
;

-- 2019-08-15T12:27:06.089Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(572582,'en_US') 
;

-- 2019-08-15T12:27:34.614Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Project Contact', PrintName='Project Contact',Updated=TO_TIMESTAMP('2019-08-15 14:27:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543957 AND AD_Language='en_US'
;

-- 2019-08-15T12:27:34.616Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543957,'en_US') 
;

-- 2019-08-15T12:27:54.663Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Files', PrintName='Files',Updated=TO_TIMESTAMP('2019-08-15 14:27:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=572726 AND AD_Language='en_US'
;

-- 2019-08-15T12:27:54.665Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(572726,'en_US') 
;

--Translating Status in en_US
UPDATE "public"."r_status_trl" SET "name" = 'Planning' WHERE "ad_language" LIKE 'en_US' ESCAPE '#' AND "r_status_id" = 540026 and "name" = 'Planning';
UPDATE "public"."r_status_trl" SET "name" = 'Archived' WHERE "ad_language" LIKE 'en_US' ESCAPE '#' AND "r_status_id" = 540030 and "name" = 'Archived';
UPDATE "public"."r_status_trl" SET "name" = 'Done' WHERE "ad_language" LIKE 'en_US' ESCAPE '#' AND "r_status_id" = 540029 and  "name" = 'Done';
UPDATE "public"."r_status_trl" SET "name" = 'Approval' WHERE "ad_language" LIKE 'en_US' ESCAPE '#' AND "r_status_id" = 540028  and  "name" ='Approval';
UPDATE "public"."r_status_trl" SET "name" = 'Execution' WHERE "ad_language" LIKE 'en_US' ESCAPE '#' AND "r_status_id" = 540027  and  "name" ='Execution';

--inheriting element translations to field translations which got lost when copying the window:

create schema if not exists fix;

create table fix.ad_field_trl_20190615 as
SELECT
  w.name as window_name,
  t.name as tab_name,
  f.ad_field_id,
  ftrl.name as ftrl_name,
       ftrl.description as ftr_desc,
       ftrl.help as ftrl_help,
    aet.name as aet_name,
       aet.description,
       aet.help

FROM ad_window w
  JOIN ad_tab t ON t.ad_window_id = w.ad_window_id
  JOIN ad_field f ON f.ad_tab_id = t.ad_tab_id
  join ad_field_trl ftrl ON f.ad_field_id = ftrl.ad_field_id
  join ad_column ac on f.ad_column_id = ac.ad_column_id
  join ad_element ae on ac.ad_element_id = ae.ad_element_id
  join ad_element_trl aet on ae.ad_element_id = aet.ad_element_id
  WHERE w.ad_window_id = 540668
  and aet.ad_language='en_US'
    and ftrl.ad_language='en_US'
  and f.isdisplayed='Y'
  and aet.name != ftrl.name
ORDER BY w.name;

update ad_field_trl as target
  set name =data.aet_name, description=data.description, help = data.help
  from (select * from fix.ad_field_trl_20190615) data
where target.ad_language='en_US' and target.ad_field_id=data.ad_field_id;
