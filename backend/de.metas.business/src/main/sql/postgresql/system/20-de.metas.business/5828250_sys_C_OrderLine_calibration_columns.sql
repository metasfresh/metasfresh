-- Quantity calibration: three nullable columns on C_OrderLine that record how a compensation group component quantity was scaled.
-- The columns are set by the system only (IsUpdateable='N') and are copied as they are when an order is copied (CloningStrategy 'DC').

-- IDs allocated from idserver.metas.de:
--   AD_Element 585523 (GroupCompensationQtyEnteredUncalibrated); 585522 (GroupCompensationCalibrationFactor) was created together with the rule table
--   AD_Column  593730, 593731, 593732
-- Reused: AD_Element 585521 (C_CompensationGroup_CalibrationRule_ID).

-- 1. Physical columns (new columns, so plain ADD COLUMN) and FK to the rule table; no ON DELETE action
ALTER TABLE C_OrderLine ADD COLUMN GroupCompensationCalibrationFactor NUMERIC;
ALTER TABLE C_OrderLine ADD COLUMN GroupCompensationQtyEnteredUncalibrated NUMERIC;
ALTER TABLE C_OrderLine ADD COLUMN C_CompensationGroup_CalibrationRule_ID NUMERIC(10);
ALTER TABLE C_OrderLine ADD CONSTRAINT CCompensationGroupCalibrationRule_COrderLine FOREIGN KEY (C_CompensationGroup_CalibrationRule_ID)
    REFERENCES C_CompensationGroup_CalibrationRule (C_CompensationGroup_CalibrationRule_ID) DEFERRABLE INITIALLY DEFERRED;

-- 2. New AD_Element for the uncalibrated quantity
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, Description, EntityType)
VALUES (585523 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 11:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'GroupCompensationQtyEnteredUncalibrated', 'Menge unkalibriert', 'Menge unkalibriert',
        'Menge der Komponente der Kompensationsgruppe vor der Anwendung des Kalibrierfaktors.', 'de.metas.order');

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated,
                            AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Element_ID = 585523
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

UPDATE AD_Element_Trl SET IsTranslated = 'Y', Name = 'Uncalibrated quantity', PrintName = 'Uncalibrated quantity',
    Description = 'Quantity of the compensation group component before the calibration factor was applied.',
    Updated = TO_TIMESTAMP('2026-10-06 11:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Element_ID = 585523;

UPDATE AD_Element_Trl SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-06 11:00:18', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Element_ID = 585523;

-- 3. AD_Columns on C_OrderLine (AD_Table 260)
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Table_ID, AD_Element_ID, ColumnName, AD_Reference_ID, AD_Reference_Value_ID, AD_Val_Rule_ID, FieldLength,
                       IsKey, IsParent, IsMandatory, IsUpdateable, IsAlwaysUpdateable, IsIdentifier, SeqNo,
                       IsSelectionColumn, IsTranslated, IsEncrypted, IsAllowLogging, DDL_NoForeignKey, CloningStrategy,
                       DefaultValue, EntityType, PersonalDataCategory, Version)
VALUES
    (593730 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 11:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 11:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
     260, 585522, 'GroupCompensationCalibrationFactor', 22, NULL, NULL, 10,
     'N', 'N', 'N', 'N', 'N', 'N', NULL,
     'N', 'N', 'N', 'Y', 'Y', 'DC',
     NULL, 'de.metas.order', 'NP', 0),
    (593731 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 11:01:01', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 11:01:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
     260, 585523, 'GroupCompensationQtyEnteredUncalibrated', 29, NULL, NULL, 10,
     'N', 'N', 'N', 'N', 'N', 'N', NULL,
     'N', 'N', 'N', 'Y', 'Y', 'DC',
     NULL, 'de.metas.order', 'NP', 0),
    (593732 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 11:01:02', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 11:01:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
     260, 585521, 'C_CompensationGroup_CalibrationRule_ID', 30, NULL, NULL, 10,
     'N', 'N', 'N', 'N', 'N', 'N', NULL,
     'N', 'N', 'N', 'Y', 'N', 'DC',
     NULL, 'de.metas.order', 'NP', 0);

-- 4. AD_Column_Trl skeleton rows
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.ColumnName, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID IN (593730, 593731, 593732)
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

-- 5. Propagate element translations to the columns
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(x.AD_Element_ID)
FROM (VALUES (585521), (585522), (585523)) AS x(AD_Element_ID);
