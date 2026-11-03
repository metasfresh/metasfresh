-- Migration script for gh#28487: Add ForeignSalesQty AD_Element for Package Licensing InOut Report

-- AD_Element: ForeignSalesQty (base language = de_DE => German)
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 584669 /*From ID Server*/, 0, 'ForeignSalesQty',
        TO_TIMESTAMP('2026-03-13 14:00', 'YYYY-MM-DD HH24:MI'), 100, 'D', 'Y',
        'Verkaufsmenge ins Ausland', 'Verkaufsmenge ins Ausland',
        TO_TIMESTAMP('2026-03-13 14:00', 'YYYY-MM-DD HH24:MI'), 100)
;

-- AD_Element_Trl for all active languages
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName, t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 584669
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- English translation
UPDATE AD_Element_Trl
SET IsTranslated  = 'Y',
    Name          = 'Foreign Sales Qty',
    PrintName     = 'Foreign Sales Qty',
    Updated       = TO_TIMESTAMP('2026-03-13 14:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy     = 100
WHERE AD_Element_ID = 584669
  AND AD_Language = 'en_US'
;

-- Propagate en_US to base element (base language is en_US in some setups)
UPDATE AD_Element base
SET Name      = trl.Name,
    PrintName = trl.PrintName,
    Updated   = trl.Updated,
    UpdatedBy = trl.UpdatedBy
FROM AD_Element_Trl trl
WHERE trl.AD_Element_ID = base.AD_Element_ID
  AND trl.AD_Language = 'en_US'
  AND trl.AD_Language = getBaseLanguage()
;

-- Propagate translations
/* DDL */
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584669, 'en_US')
;
