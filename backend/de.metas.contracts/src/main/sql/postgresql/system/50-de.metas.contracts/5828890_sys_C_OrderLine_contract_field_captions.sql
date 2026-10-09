-- Order line contract fields: clearer captions + descriptions in the sales order (window 143, tab "Auftragsposition")
-- and purchase order (window 181, tab "Bestellposition"), via dedicated elements on AD_Field.AD_Name_ID.
-- The column elements 541423 (C_Flatrate_Conditions_ID "Vertragsbedingungen") and 541447 (C_Flatrate_Term_ID
-- "Pauschale - Vertragsperiode") are shared by many windows and stay unchanged.
--
--   C_Flatrate_Conditions_ID: on order completion a contract with these conditions is created from the line
--     (subscription, call-off contract) -> "Vertragsbedingungen neuer Vertrag" / "Conditions for New Contract"
--     fields 540003 (143; replaces AD_Name_ID 1001993 "Abo Vertragsbedingungen", which had no other usage) and 548336 (181)
--   C_Flatrate_Term_ID: the call-off contract the line is called off from (price + remaining quantity from the contract)
--     -> "Abrufvertrag" / "Call-off Contract"
--     fields 680655 (143) and 691395 (181)
--
-- IDs allocated from idserver.metas.de on 2026-10-09:
--   AD_MigrationScript 5828890 (this script)
--   AD_Element 585529 (conditions caption), 585530 (call-off contract caption)

-- ============================================================================
-- 1) AD_Element "Vertragsbedingungen neuer Vertrag"
-- ============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585529 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-09 14:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-09 14:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        NULL, 'de.metas.contracts', 'Vertragsbedingungen neuer Vertrag', 'Vertragsbedingungen neuer Vertrag',
        'Beim Fertigstellen des Auftrags wird aus dieser Position ein Vertrag mit diesen Vertragsbedingungen erzeugt (z. B. Abo, Abrufvertrag).')
;
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585529
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name = 'Conditions for New Contract', PrintName = 'Conditions for New Contract',
    Description = 'When the order is completed, a contract with these conditions is created from this line (e.g. subscription, call-off contract).',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-09 14:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585529 AND AD_Language = 'en_US'
;
-- de_DE / de_CH: the German base text is final (no ß in it)
UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-09 14:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585529 AND AD_Language IN ('de_DE', 'de_CH')
;

-- ============================================================================
-- 2) AD_Element "Abrufvertrag"
-- ============================================================================
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585530 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-09 14:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-09 14:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100,
        NULL, 'de.metas.contracts', 'Abrufvertrag', 'Abrufvertrag',
        'Vertrag, aus dem diese Position abgerufen wird; Preis und Restmenge kommen aus dem Vertrag. Den Vertrag einer Kompensationsgruppe zeigt die Kompensationsgruppe.')
;
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585530
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name = 'Call-off Contract', PrintName = 'Call-off Contract',
    Description = 'Contract this line is called off from; price and remaining quantity come from the contract. The contract of a compensation group is shown on the compensation group.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-09 14:00:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585530 AND AD_Language = 'en_US'
;
UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-09 14:00:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585530 AND AD_Language IN ('de_DE', 'de_CH')
;

-- ============================================================================
-- 3) Order line fields: point AD_Name_ID at the new elements, propagate, rebuild element links
-- ============================================================================
UPDATE AD_Field
SET AD_Name_ID = 585529, Updated = TO_TIMESTAMP('2026-10-09 14:01:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID IN (540003 /* 143 Auftragsposition */, 548336 /* 181 Bestellposition */)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585529)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID IN (540003, 548336)
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(540003)
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(548336)
;

UPDATE AD_Field
SET AD_Name_ID = 585530, Updated = TO_TIMESTAMP('2026-10-09 14:01:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Field_ID IN (680655 /* 143 Auftragsposition */, 691395 /* 181 Bestellposition */)
;
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585530)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID IN (680655, 691395)
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(680655)
;
/* DDL */ select AD_Element_Link_Create_Missing_Field(691395)
;
