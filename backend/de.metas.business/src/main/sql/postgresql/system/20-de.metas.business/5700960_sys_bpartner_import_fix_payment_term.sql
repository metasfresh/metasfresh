-- 2023-08-31T06:45:00.824058900Z
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 582673, 0, 'payment_term_po', TO_TIMESTAMP('2023-08-31 08:45:00.707', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'D', 'Y', 'Zahlungsbedingungen Lieferant', 'Zahlungsbedingungen Lieferant', TO_TIMESTAMP('2023-08-31 08:45:00.707', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- 2023-08-31T06:45:00.825121400Z
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Element_ID,
       t.CommitWarning,
       t.Description,
       t.Help,
       t.Name,
       t.PO_Description,
       t.PO_Help,
       t.PO_Name,
       t.PO_PrintName,
       t.PrintName,
       t.WEBUI_NameBrowse,
       t.WEBUI_NameNew,
       t.WEBUI_NameNewBreadcrumb,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Element t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 582673
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- Element: payment_term_po
-- 2023-08-31T06:45:05.368259300Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-08-31 08:45:05.368', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Element_ID = 582673
  AND AD_Language = 'de_CH'
;

-- 2023-08-31T06:45:05.370363300Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582673, 'de_CH')
;

-- Element: payment_term_po
-- 2023-08-31T06:45:06.220453500Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-08-31 08:45:06.22', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Element_ID = 582673
  AND AD_Language = 'de_DE'
;

-- 2023-08-31T06:45:06.222017500Z
/* DDL */

SELECT update_ad_element_on_ad_element_trl_update(582673, 'de_DE')
;

-- 2023-08-31T06:45:06.223054100Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582673, 'de_DE')
;

-- Element: payment_term_po
-- 2023-08-31T06:46:01.886337700Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Name='Payment term vendor', PrintName='Payment term vendor', Updated=TO_TIMESTAMP('2023-08-31 08:46:01.886', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Element_ID = 582673
  AND AD_Language = 'en_US'
;

-- 2023-08-31T06:46:01.888439300Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582673, 'en_US')
;

-- Column: I_BPartner.payment_term_po
-- 2023-08-31T06:48:13.535204400Z
UPDATE AD_Column
SET AD_Element_ID=582673, ColumnName='payment_term_po', Description=NULL, Help=NULL, IsExcludeFromZoomTargets='Y', Name='Zahlungsbedingungen Lieferant', Updated=TO_TIMESTAMP('2023-08-31 08:48:13.535', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Column_ID = 557904
;

-- 2023-08-31T06:48:13.539377100Z
UPDATE AD_Column_Trl trl
SET Name='Zahlungsbedingungen Lieferant'
WHERE AD_Column_ID = 557904
  AND AD_Language = 'de_DE'
;

-- 2023-08-31T06:48:13.541467900Z
UPDATE AD_Field
SET Name='Zahlungsbedingungen Lieferant', Description=NULL, Help=NULL
WHERE AD_Column_ID = 557904
;

-- 2023-08-31T06:48:13.546211500Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(582673)
;

-- 2023-08-31T06:48:16.714354400Z
/* DDL */

SELECT public.db_alter_table('I_BPartner', 'ALTER TABLE public.I_BPartner RENAME COLUMN paymentTerm TO payment_term_po')
;

-- 2023-08-31T07:02:40.764781600Z
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 582674, 0, 'payment_term_so', TO_TIMESTAMP('2023-08-31 09:02:40.649', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'D', 'Y', 'Zahlungsbedingungen Kunde', 'Zahlungsbedingungen Kunde', TO_TIMESTAMP('2023-08-31 09:02:40.649', 'YYYY-MM-DD HH24:MI:SS.US'), 100)
;

-- 2023-08-31T07:02:40.765835700Z
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Element_ID,
       t.CommitWarning,
       t.Description,
       t.Help,
       t.Name,
       t.PO_Description,
       t.PO_Help,
       t.PO_Name,
       t.PO_PrintName,
       t.PrintName,
       t.WEBUI_NameBrowse,
       t.WEBUI_NameNew,
       t.WEBUI_NameNewBreadcrumb,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Element t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 582674
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- Element: payment_term_so
-- 2023-08-31T07:02:50.504832500Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-08-31 09:02:50.504', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Element_ID = 582674
  AND AD_Language = 'de_CH'
;

-- 2023-08-31T07:02:50.506942200Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582674, 'de_CH')
;

-- Element: payment_term_so
-- 2023-08-31T07:02:51.312112200Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2023-08-31 09:02:51.312', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Element_ID = 582674
  AND AD_Language = 'de_DE'
;

-- 2023-08-31T07:02:51.314196600Z
/* DDL */

SELECT update_ad_element_on_ad_element_trl_update(582674, 'de_DE')
;

-- 2023-08-31T07:02:51.316292600Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582674, 'de_DE')
;

-- Element: payment_term_so
-- 2023-08-31T07:03:34.817597600Z
UPDATE AD_Element_Trl
SET IsTranslated='Y', Name='Payment term customer', PrintName='Payment term customer', Updated=TO_TIMESTAMP('2023-08-31 09:03:34.817', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Element_ID = 582674
  AND AD_Language = 'en_US'
;

-- 2023-08-31T07:03:34.819702100Z
/* DDL */

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582674, 'en_US')
;

-- Column: I_BPartner.payment_term_so
-- 2023-08-31T07:05:36.824722500Z
UPDATE AD_Column
SET AD_Element_ID=582674, ColumnName='payment_term_so', Description=NULL, Help=NULL, IsExcludeFromZoomTargets='Y', Name='Zahlungsbedingungen Kunde', Updated=TO_TIMESTAMP('2023-08-31 09:05:36.824', 'YYYY-MM-DD HH24:MI:SS.US'), UpdatedBy=100
WHERE AD_Column_ID = 564344
;

-- 2023-08-31T07:05:36.826862400Z
UPDATE AD_Column_Trl trl
SET Name='Zahlungsbedingungen Kunde'
WHERE AD_Column_ID = 564344
  AND AD_Language = 'de_DE'
;

-- 2023-08-31T07:05:36.828978900Z
UPDATE AD_Field
SET Name='Zahlungsbedingungen Kunde', Description=NULL, Help=NULL
WHERE AD_Column_ID = 564344
;

-- 2023-08-31T07:05:36.833129700Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(582674)
;

-- 2023-08-31T07:05:38.882482Z
/* DDL */

SELECT public.db_alter_table('I_BPartner', 'ALTER TABLE public.I_BPartner RENAME COLUMN paymentTermValue TO payment_term_so')
;

