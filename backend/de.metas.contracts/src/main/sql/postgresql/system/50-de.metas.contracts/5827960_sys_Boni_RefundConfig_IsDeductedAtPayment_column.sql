-- Boni: C_Flatrate_RefundConfig.IsDeductedAtPayment
-- If 'Y', the customer deducts the bonus when paying an invoice ("bei Zahlung"): the refund engine does not create refund
-- invoice candidates for the conditions; instead the payment allocation books the deduction as a payment bonus credit memo.
-- No window / tab / field changes in this script.
--
-- IDs allocated from idserver.metas.de on 2026-10-06:
--   AD_Element 585516 (IsDeductedAtPayment)
--   AD_Column  593711 (C_Flatrate_RefundConfig.IsDeductedAtPayment)

INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585516 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'IsDeductedAtPayment', 'de.metas.contracts', 'Abzug bei Zahlung', 'Abzug bei Zahlung',
        'Wenn aktiv, zieht der Kunde den Bonus bei der Zahlung ab. Es wird keine Rückvergütung abgerechnet, sondern die Zahlungszuordnung erstellt eine Zahlungsbonus-Gutschrift.')
;
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585516
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name = 'Deducted at payment', PrintName = 'Deducted at payment',
    Description = 'If enabled, the customer deducts the bonus when paying. No refund is invoiced, the payment allocation creates a payment bonus credit memo instead.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-06 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585516 AND AD_Language = 'en_US'
;
UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-06 10:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585516 AND AD_Language IN ('de_DE', 'de_CH')
;

INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       Version, EntityType, ColumnName, AD_Table_ID, AD_Element_ID, AD_Reference_ID, AD_Reference_Value_ID,
                       FieldLength, Name, Description,
                       IsMandatory, IsUpdateable, IsAlwaysUpdateable, DefaultValue,
                       IsKey, IsParent, IsTranslated, IsIdentifier, IsEncrypted, IsSelectionColumn,
                       IsAllowLogging, PersonalDataCategory)
VALUES (593711 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 10:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 10:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100,
        0, 'de.metas.contracts', 'IsDeductedAtPayment', (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_Flatrate_RefundConfig'), 585516, 20, NULL,
        1, 'Abzug bei Zahlung',
        'Wenn aktiv, zieht der Kunde den Bonus bei der Zahlung ab. Es wird keine Rückvergütung abgerechnet, sondern die Zahlungszuordnung erstellt eine Zahlungsbonus-Gutschrift.',
        'Y', 'Y', 'N', 'N',
        'N', 'N', 'N', 'N', 'N', 'N',
        'Y', 'NP')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT et.AD_Language, c.AD_Column_ID, et.Name, et.IsTranslated, c.AD_Client_ID, c.AD_Org_ID, c.Created, c.CreatedBy, c.Updated, c.UpdatedBy
FROM AD_Column c
         JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE c.AD_Column_ID = 593711
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = et.AD_Language AND tt.AD_Column_ID = c.AD_Column_ID)
;

SELECT public.db_alter_table('C_Flatrate_RefundConfig', 'ALTER TABLE public.C_Flatrate_RefundConfig ADD COLUMN IsDeductedAtPayment CHAR(1) DEFAULT ''N'' NOT NULL')
;
ALTER TABLE C_Flatrate_RefundConfig
    ADD CONSTRAINT IsDeductedAtPayment_Check CHECK (IsDeductedAtPayment IN ('Y', 'N'))
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585516)
;
