-- Sales order flag "PO reference is customer's order no.": the order's POReference is the customer's own order number (EDI RFF+ON), not ours (RFF+VN).
-- Stored only on C_Order; exported per line in the INVOIC / DESADV JSON. Set at order creation from EDI order candidates, reset when POReference changes (interceptor), editable by the user.
-- IsAlwaysUpdateable=Y: EDI orders are completed right after creation and the WebUI locks every field of a processed document unless the column is always-updateable (like C_Order.POReference).
-- IsCalculated=Y: the generic record copy resets it to its default (like C_Order.POReference).

-- AD_Element
INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, Description, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 585626 /*From ID Server*/, 0, 'IsPOReferenceBuyerOrderNo',
        TO_TIMESTAMP('2026-11-19 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'Die PO-Referenz ist die Bestellnummer des Kunden (EDI RFF+ON); sonst unsere Auftragsnummer (RFF+VN).',
        'D', 'Y', 'PO-Referenz ist Bestellnr. des Kunden', 'PO-Referenz ist Bestellnr. des Kunden',
        TO_TIMESTAMP('2026-11-19 15:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, PO_Name, PO_PrintName, PO_Description, PO_Help, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, t.Help, t.PO_Name, t.PO_PrintName, t.PO_Description, t.PO_Help, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Element_ID = 585626
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

UPDATE AD_Element_Trl
SET Name = 'PO reference is customer''s order no.', PrintName = 'PO reference is customer''s order no.',
    Description = 'The PO reference is the customer''s own order number (EDI RFF+ON); otherwise it is our order number (RFF+VN).',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-11-19 15:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585626 AND AD_Language = 'en_US'
;

-- de_DE / de_CH keep IsTranslated='N' (German base text copied by the Trl seed), per docs/REVIEW.md

-- AD_Column (C_Order = AD_Table 259, EntityType D, so no IsForceIncludeInGeneratedModel needed)
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DefaultValue, Description, EntityType, FieldLength, IsActive, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsEncrypted, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, Name, PersonalDataCategory, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 593856 /*From ID Server*/, 585626 /*From ID Server*/, 0, 20, 259, 'IsPOReferenceBuyerOrderNo',
        TO_TIMESTAMP('2026-11-19 15:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'N',
        'Die PO-Referenz ist die Bestellnummer des Kunden (EDI RFF+ON); sonst unsere Auftragsnummer (RFF+VN).',
        'D', 1, 'Y', 'Y', 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'Y', 'N', 'Y', 'N', 'PO-Referenz ist Bestellnr. des Kunden', 'NP', 0,
        TO_TIMESTAMP('2026-11-19 15:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 0)
;

INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 593856
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585626)
;

-- DDL: new column, existing rows get 'N'
ALTER TABLE C_Order ADD COLUMN IsPOReferenceBuyerOrderNo CHAR(1) DEFAULT 'N' NOT NULL CHECK (IsPOReferenceBuyerOrderNo IN ('Y', 'N'))
;
