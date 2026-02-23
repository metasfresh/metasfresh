-- Migration: Add new AD_Column entries for M_MaterialCockpit_Base_V columns
-- Part of: Material Cockpit V2 (Increment 1) -- me03#27512, se203#252
-- Table: QtyDemand_QtySupply_V (AD_Table_ID=542218)
--
-- New columns: SupplyType, C_BPartner_Vendor_ID, DatePromised, QtyOnHand, QtyTU, QtyLU, WeightNet
-- Note: AD_Column_IDs and AD_Element_IDs below need to be verified/allocated from the ID server before merge.


-- AD_Element: SupplyType
-- 2026-02-23
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType,
                        IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 584200, 0, 'SupplyType',
        TO_TIMESTAMP('2026-02-23 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D',
        'Y', 'Supply Type', 'Supply Type',
        TO_TIMESTAMP('2026-02-23 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description,
                            PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew,
                            WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby,
                            Updated, UpdatedBy, IsActive)
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
  AND t.AD_Element_ID = 584200
  AND NOT EXISTS (SELECT 1
                  FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language
                    AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- Update German translation for SupplyType
UPDATE AD_Element_Trl
SET Name      = 'Versorgungstyp',
    PrintName = 'Versorgungstyp'
WHERE AD_Element_ID = 584200
  AND AD_Language = 'de_DE'
;

-- AD_Column: QtyDemand_QtySupply_V.SupplyType
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID,
                       ColumnName, Created, CreatedBy, EntityType, FieldLength,
                       IsActive, IsAllowLogging, IsAlwaysUpdateable, IsEncrypted, IsIdentifier, IsKey,
                       IsMandatory, IsParent, IsSelectionColumn, IsTranslated, IsUpdateable, Name,
                       Updated, UpdatedBy, Version)
VALUES (0, 591500, 584200, 0, 10, 542218,
        'SupplyType',
        TO_TIMESTAMP('2026-02-23 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 2,
        'Y', 'Y', 'N', 'N', 'N', 'N',
        'N', 'N', 'Y', 'N', 'N', 'Supply Type',
        TO_TIMESTAMP('2026-02-23 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 0)
;

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created,
                           Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby,
       t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l,
     AD_Column t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 591500
  AND NOT EXISTS (SELECT 1
                  FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language
                    AND tt.AD_Column_ID = t.AD_Column_ID)
;

SELECT update_Column_Translation_From_AD_Element(584200);


-- AD_Column: QtyDemand_QtySupply_V.C_BPartner_Vendor_ID
-- Reuse existing AD_Element for C_BPartner_ID (Element_ID=187) with different column name
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID,
                       ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FieldLength,
                       IsActive, IsAllowLogging, IsAlwaysUpdateable, IsEncrypted, IsIdentifier, IsKey,
                       IsMandatory, IsParent, IsSelectionColumn, IsTranslated, IsUpdateable, Name,
                       Updated, UpdatedBy, Version)
VALUES (0, 591501, 187, 0, 30, 542218,
        'C_BPartner_Vendor_ID',
        TO_TIMESTAMP('2026-02-23 12:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Y', 'D', 10,
        'Y', 'Y', 'N', 'N', 'N', 'N',
        'N', 'N', 'N', 'N', 'N', 'Vendor',
        TO_TIMESTAMP('2026-02-23 12:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 0)
;

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created,
                           Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby,
       t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l,
     AD_Column t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 591501
  AND NOT EXISTS (SELECT 1
                  FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language
                    AND tt.AD_Column_ID = t.AD_Column_ID)
;


-- AD_Column: QtyDemand_QtySupply_V.DatePromised
-- Reuse existing AD_Element for DatePromised (Element_ID=269)
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID,
                       ColumnName, Created, CreatedBy, EntityType, FieldLength,
                       IsActive, IsAllowLogging, IsAlwaysUpdateable, IsEncrypted, IsIdentifier, IsKey,
                       IsMandatory, IsParent, IsSelectionColumn, IsTranslated, IsUpdateable, Name,
                       Updated, UpdatedBy, Version)
VALUES (0, 591502, 269, 0, 16, 542218,
        'DatePromised',
        TO_TIMESTAMP('2026-02-23 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 7,
        'Y', 'Y', 'N', 'N', 'N', 'N',
        'N', 'N', 'N', 'N', 'N', 'Date Promised',
        TO_TIMESTAMP('2026-02-23 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 0)
;

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created,
                           Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby,
       t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l,
     AD_Column t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 591502
  AND NOT EXISTS (SELECT 1
                  FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language
                    AND tt.AD_Column_ID = t.AD_Column_ID)
;

SELECT update_Column_Translation_From_AD_Element(269);


-- AD_Element: QtyOnHand (reuse existing Element_ID=530)
-- AD_Column: QtyDemand_QtySupply_V.QtyOnHand
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID,
                       ColumnName, Created, CreatedBy, EntityType, FieldLength,
                       IsActive, IsAllowLogging, IsAlwaysUpdateable, IsEncrypted, IsIdentifier, IsKey,
                       IsMandatory, IsParent, IsSelectionColumn, IsTranslated, IsUpdateable, Name,
                       Updated, UpdatedBy, Version)
VALUES (0, 591503, 530, 0, 29, 542218,
        'QtyOnHand',
        TO_TIMESTAMP('2026-02-23 12:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 10,
        'Y', 'Y', 'N', 'N', 'N', 'N',
        'N', 'N', 'N', 'N', 'N', 'Qty On Hand',
        TO_TIMESTAMP('2026-02-23 12:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100, 0)
;

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created,
                           Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby,
       t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l,
     AD_Column t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 591503
  AND NOT EXISTS (SELECT 1
                  FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language
                    AND tt.AD_Column_ID = t.AD_Column_ID)
;

SELECT update_Column_Translation_From_AD_Element(530);


-- AD_Element: QtyTU
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType,
                        IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 584201, 0, 'QtyTU',
        TO_TIMESTAMP('2026-02-23 12:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D',
        'Y', 'Qty TU', 'Qty TU',
        TO_TIMESTAMP('2026-02-23 12:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description,
                            PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew,
                            WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby,
                            Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning, t.Description, t.Help, t.Name, t.PO_Description,
       t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName, t.WEBUI_NameBrowse, t.WEBUI_NameNew,
       t.WEBUI_NameNewBreadcrumb, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby,
       t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 584201
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET Name = 'Menge TU', PrintName = 'Menge TU' WHERE AD_Element_ID = 584201 AND AD_Language = 'de_DE';

-- AD_Column: QtyDemand_QtySupply_V.QtyTU
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID,
                       ColumnName, Created, CreatedBy, EntityType, FieldLength,
                       IsActive, IsAllowLogging, IsAlwaysUpdateable, IsEncrypted, IsIdentifier, IsKey,
                       IsMandatory, IsParent, IsSelectionColumn, IsTranslated, IsUpdateable, Name,
                       Updated, UpdatedBy, Version)
VALUES (0, 591504, 584201, 0, 29, 542218,
        'QtyTU',
        TO_TIMESTAMP('2026-02-23 12:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 10,
        'Y', 'Y', 'N', 'N', 'N', 'N',
        'N', 'N', 'N', 'N', 'N', 'Qty TU',
        TO_TIMESTAMP('2026-02-23 12:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100, 0)
;

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created,
                           Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby,
       t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 591504
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

SELECT update_Column_Translation_From_AD_Element(584201);


-- AD_Element: QtyLU
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType,
                        IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 584202, 0, 'QtyLU',
        TO_TIMESTAMP('2026-02-23 12:00:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D',
        'Y', 'Qty LU', 'Qty LU',
        TO_TIMESTAMP('2026-02-23 12:00:05', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description,
                            PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew,
                            WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby,
                            Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning, t.Description, t.Help, t.Name, t.PO_Description,
       t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName, t.WEBUI_NameBrowse, t.WEBUI_NameNew,
       t.WEBUI_NameNewBreadcrumb, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby,
       t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 584202
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET Name = 'Menge LU', PrintName = 'Menge LU' WHERE AD_Element_ID = 584202 AND AD_Language = 'de_DE';

-- AD_Column: QtyDemand_QtySupply_V.QtyLU
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID,
                       ColumnName, Created, CreatedBy, EntityType, FieldLength,
                       IsActive, IsAllowLogging, IsAlwaysUpdateable, IsEncrypted, IsIdentifier, IsKey,
                       IsMandatory, IsParent, IsSelectionColumn, IsTranslated, IsUpdateable, Name,
                       Updated, UpdatedBy, Version)
VALUES (0, 591505, 584202, 0, 29, 542218,
        'QtyLU',
        TO_TIMESTAMP('2026-02-23 12:00:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 10,
        'Y', 'Y', 'N', 'N', 'N', 'N',
        'N', 'N', 'N', 'N', 'N', 'Qty LU',
        TO_TIMESTAMP('2026-02-23 12:00:05', 'YYYY-MM-DD HH24:MI:SS'), 100, 0)
;

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created,
                           Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby,
       t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 591505
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

SELECT update_Column_Translation_From_AD_Element(584202);


-- AD_Column: QtyDemand_QtySupply_V.WeightNet
-- Reuse existing AD_Element for Weight (Element_ID=629)
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID,
                       ColumnName, Created, CreatedBy, EntityType, FieldLength,
                       IsActive, IsAllowLogging, IsAlwaysUpdateable, IsEncrypted, IsIdentifier, IsKey,
                       IsMandatory, IsParent, IsSelectionColumn, IsTranslated, IsUpdateable, Name,
                       Updated, UpdatedBy, Version)
VALUES (0, 591506, 629, 0, 29, 542218,
        'WeightNet',
        TO_TIMESTAMP('2026-02-23 12:00:06', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 10,
        'Y', 'Y', 'N', 'N', 'N', 'N',
        'N', 'N', 'N', 'N', 'N', 'Net Weight',
        TO_TIMESTAMP('2026-02-23 12:00:06', 'YYYY-MM-DD HH24:MI:SS'), 100, 0)
;

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created,
                           Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby,
       t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 591506
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

SELECT update_Column_Translation_From_AD_Element(629);
