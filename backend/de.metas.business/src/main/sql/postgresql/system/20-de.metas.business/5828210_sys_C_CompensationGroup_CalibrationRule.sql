-- Quantity calibration: rule table C_CompensationGroup_CalibrationRule.
-- A rule scales the component quantities of a compensation group (menu) for a customer or a
-- business partner group, optionally narrowed by component product, product category and menu schema.
-- The EntityType is the one of the sibling table C_CompensationGroup_Schema (generates into de.metas.order.model).

-- IDs allocated from idserver.metas.de:
--   AD_Table   542655
--   AD_Element 585521 (the table's own ID column), 585522 (GroupCompensationCalibrationFactor, shared with the order line column)
--   AD_Column  593714..593729 (16, in column order)
-- Reused: AD_Val_Rule 540779 (C_BPartner_Customer) and the shared standard / generic AD_Elements.

-- 1. Physical table
CREATE TABLE C_CompensationGroup_CalibrationRule
(
    C_CompensationGroup_CalibrationRule_ID NUMERIC(10)              NOT NULL,
    AD_Client_ID                           NUMERIC(10)              NOT NULL,
    AD_Org_ID                              NUMERIC(10)              NOT NULL,
    IsActive                               CHAR(1)                  NOT NULL DEFAULT 'Y',
    Created                                TIMESTAMP WITH TIME ZONE NOT NULL,
    CreatedBy                              NUMERIC(10)              NOT NULL,
    Updated                                TIMESTAMP WITH TIME ZONE NOT NULL,
    UpdatedBy                              NUMERIC(10)              NOT NULL,
    SeqNo                                  NUMERIC(10)              NOT NULL,
    C_BPartner_ID                          NUMERIC(10),
    C_BP_Group_ID                          NUMERIC(10),
    M_Product_ID                           NUMERIC(10),
    M_Product_Category_ID                  NUMERIC(10),
    C_CompensationGroup_Schema_ID          NUMERIC(10),
    GroupCompensationCalibrationFactor     NUMERIC                  NOT NULL,
    Description                            VARCHAR(255),
    CONSTRAINT C_CompensationGroup_CalibrationRule_key PRIMARY KEY (C_CompensationGroup_CalibrationRule_ID),
    CONSTRAINT C_CompensationGroup_CalibrationRule_IsActive_check CHECK (IsActive IN ('Y', 'N')),
    CONSTRAINT C_CompensationGroup_CalibrationRule_BPartnerOrGroup_check CHECK (C_BPartner_ID IS NOT NULL OR C_BP_Group_ID IS NOT NULL),
    CONSTRAINT C_CompensationGroup_CalibrationRule_Factor_check CHECK (GroupCompensationCalibrationFactor >= 0),
    CONSTRAINT CBPartner_CCompensationGroupCalibrationRule FOREIGN KEY (C_BPartner_ID) REFERENCES C_BPartner (C_BPartner_ID) DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT CBPGroup_CCompensationGroupCalibrationRule FOREIGN KEY (C_BP_Group_ID) REFERENCES C_BP_Group (C_BP_Group_ID) DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT MProduct_CCompensationGroupCalibrationRule FOREIGN KEY (M_Product_ID) REFERENCES M_Product (M_Product_ID) DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT MProductCategory_CCompensationGroupCalibrationRule FOREIGN KEY (M_Product_Category_ID) REFERENCES M_Product_Category (M_Product_Category_ID) DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT CCompensationGroupSchema_CCompensationGroupCalibrationRule FOREIGN KEY (C_CompensationGroup_Schema_ID) REFERENCES C_CompensationGroup_Schema (C_CompensationGroup_Schema_ID) DEFERRABLE INITIALLY DEFERRED
);

COMMENT ON TABLE C_CompensationGroup_CalibrationRule IS 'Calibration rules that scale the component quantities of compensation groups per customer / business partner group.';

-- 2. AD_Table (cloned from X_TableTemplate 540290; EntityType of the sibling C_CompensationGroup_Schema)
INSERT INTO AD_Table (AD_Table_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      Name, TableName, AccessLevel, IsView, IsSecurityEnabled, IsChangeLog, IsDeleteable,
                      IsHighVolume, LoadSeq, EntityType, ImportTable)
VALUES (542655 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'Kalibrierungsregel', 'C_CompensationGroup_CalibrationRule', '3', 'N', 'Y', 'Y', 'Y',
        'N', 0, 'de.metas.order', 'N');

-- 3. AD_Elements: the table's own ID column and the calibration factor (shared with the order line column)
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, Description, EntityType)
VALUES (585521 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'C_CompensationGroup_CalibrationRule_ID', 'Kalibrierungsregel', 'Kalibrierungsregel',
        'Regel, die die Komponentenmengen einer Kompensationsgruppe für einen Kunden oder eine Geschäftspartnergruppe skaliert.', 'de.metas.order'),
       (585522 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'GroupCompensationCalibrationFactor', 'Kalibrierfaktor', 'Kalibrierfaktor',
        'Faktor, mit dem die Menge einer Komponente der Kompensationsgruppe multipliziert wird. 0 lässt die Komponente weg.', 'de.metas.order');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated,
                            AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Element_ID IN (585521, 585522)
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl SET IsTranslated = 'Y', Name = 'Calibration rule', PrintName = 'Calibration rule',
    Description = 'Rule that scales the component quantities of a compensation group for a customer or a business partner group.',
    Updated = TO_TIMESTAMP('2026-10-06 10:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585521;

UPDATE AD_Element_Trl SET IsTranslated = 'Y', Name = 'Calibration factor', PrintName = 'Calibration factor',
    Description = 'Factor the quantity of a compensation group component is multiplied by. 0 leaves the component out.',
    Updated = TO_TIMESTAMP('2026-10-06 10:00:13', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585522;

UPDATE AD_Element_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-06 10:00:18', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Element_ID IN (585521, 585522);

-- 4. AD_Columns

INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Table_ID, AD_Element_ID, ColumnName, AD_Reference_ID, AD_Reference_Value_ID, AD_Val_Rule_ID, FieldLength,
                       IsKey, IsParent, IsMandatory, IsUpdateable, IsAlwaysUpdateable, IsIdentifier, SeqNo,
                       IsSelectionColumn, IsTranslated, IsEncrypted, IsAllowLogging,
                       DefaultValue, EntityType, PersonalDataCategory, Version)
VALUES
    (593714 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 10:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542655, 585521, 'C_CompensationGroup_CalibrationRule_ID', 13, NULL, NULL, 10,
     'Y', 'N', 'Y', 'N', 'N', 'N', NULL,
     'N', 'N', 'N', 'Y',
     NULL, 'de.metas.order', 'NP', 0),
    (593715 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 10:01:01', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 10:01:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542655, 102, 'AD_Client_ID', 19, NULL, NULL, 10,
     'N', 'N', 'Y', 'N', 'N', 'N', NULL,
     'N', 'N', 'N', 'Y',
     NULL, 'de.metas.order', 'NP', 0),
    (593716 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 10:01:02', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 10:01:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542655, 113, 'AD_Org_ID', 30, NULL, NULL, 10,
     'N', 'N', 'Y', 'Y', 'N', 'N', NULL,
     'Y', 'N', 'N', 'Y',
     NULL, 'de.metas.order', 'NP', 0),
    (593717 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 10:01:03', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 10:01:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542655, 348, 'IsActive', 20, NULL, NULL, 1,
     'N', 'N', 'Y', 'Y', 'N', 'N', NULL,
     'Y', 'N', 'N', 'Y',
     NULL, 'de.metas.order', 'NP', 0),
    (593718 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 10:01:04', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 10:01:04', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542655, 245, 'Created', 16, NULL, NULL, 29,
     'N', 'N', 'Y', 'N', 'N', 'N', NULL,
     'N', 'N', 'N', 'Y',
     NULL, 'de.metas.order', 'NP', 0),
    (593719 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 10:01:05', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 10:01:05', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542655, 246, 'CreatedBy', 18, 110, NULL, 10,
     'N', 'N', 'Y', 'N', 'N', 'N', NULL,
     'N', 'N', 'N', 'Y',
     NULL, 'de.metas.order', 'NP', 0),
    (593720 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 10:01:06', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 10:01:06', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542655, 607, 'Updated', 16, NULL, NULL, 29,
     'N', 'N', 'Y', 'N', 'N', 'N', NULL,
     'N', 'N', 'N', 'Y',
     NULL, 'de.metas.order', 'NP', 0),
    (593721 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 10:01:07', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 10:01:07', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542655, 608, 'UpdatedBy', 18, 110, NULL, 10,
     'N', 'N', 'Y', 'N', 'N', 'N', NULL,
     'N', 'N', 'N', 'Y',
     NULL, 'de.metas.order', 'NP', 0),
    (593722 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 10:01:08', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 10:01:08', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542655, 566, 'SeqNo', 11, NULL, NULL, 10,
     'N', 'N', 'Y', 'Y', 'N', 'Y', 10,
     'N', 'N', 'N', 'Y',
     '@SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM C_CompensationGroup_CalibrationRule', 'de.metas.order', 'NP', 0),
    (593723 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 10:01:09', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 10:01:09', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542655, 187, 'C_BPartner_ID', 30, NULL, 540779, 10,
     'N', 'N', 'N', 'Y', 'N', 'Y', 20,
     'Y', 'N', 'N', 'Y',
     NULL, 'de.metas.order', 'NP', 0),
    (593724 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 10:01:10', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 10:01:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542655, 1383, 'C_BP_Group_ID', 19, NULL, NULL, 10,
     'N', 'N', 'N', 'Y', 'N', 'Y', 30,
     'Y', 'N', 'N', 'Y',
     NULL, 'de.metas.order', 'NP', 0),
    (593725 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 10:01:11', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 10:01:11', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542655, 454, 'M_Product_ID', 30, NULL, NULL, 10,
     'N', 'N', 'N', 'Y', 'N', 'N', NULL,
     'Y', 'N', 'N', 'Y',
     NULL, 'de.metas.order', 'NP', 0),
    (593726 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 10:01:12', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 10:01:12', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542655, 453, 'M_Product_Category_ID', 19, NULL, NULL, 10,
     'N', 'N', 'N', 'Y', 'N', 'N', NULL,
     'Y', 'N', 'N', 'Y',
     NULL, 'de.metas.order', 'NP', 0),
    (593727 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 10:01:13', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 10:01:13', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542655, 543889, 'C_CompensationGroup_Schema_ID', 30, NULL, NULL, 10,
     'N', 'N', 'N', 'Y', 'N', 'N', NULL,
     'Y', 'N', 'N', 'Y',
     NULL, 'de.metas.order', 'NP', 0),
    (593728 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 10:01:14', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 10:01:14', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542655, 585522, 'GroupCompensationCalibrationFactor', 22, NULL, NULL, 10,
     'N', 'N', 'Y', 'Y', 'N', 'Y', 40,
     'N', 'N', 'N', 'Y',
     NULL, 'de.metas.order', 'NP', 0),
    (593729 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 10:01:15', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 10:01:15', 'YYYY-MM-DD HH24:MI:SS'), 100,
     542655, 275, 'Description', 10, NULL, NULL, 255,
     'N', 'N', 'N', 'Y', 'N', 'N', NULL,
     'N', 'N', 'N', 'Y',
     NULL, 'de.metas.order', 'NP', 0);

-- 5. AD_Column_Trl skeleton rows
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.ColumnName, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Table_ID = 542655
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- 6. Propagate element translations to the columns
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(x.AD_Element_ID)
FROM (VALUES (585521), (585522)) AS x(AD_Element_ID);
