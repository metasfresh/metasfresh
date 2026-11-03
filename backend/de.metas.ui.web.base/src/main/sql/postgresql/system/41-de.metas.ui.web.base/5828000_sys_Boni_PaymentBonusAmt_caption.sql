-- Boni: caption of the column "Zahlungsbonus" in the invoices of the WebUI payment allocation view.
-- The column shows the bonus that the customer deducts when paying the invoice (incl. VAT); it is pre-filled and editable.
-- Like the service fee column (element ServiceFeeAmt), the caption is an element without a table column.
--
-- IDs allocated from idserver.metas.de on 2026-10-06:
--   AD_Element 585517 (PaymentBonusAmt)

INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585517 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'PaymentBonusAmt', 'D', 'Zahlungsbonus', 'Zahlungsbonus',
        'Bonus inkl. MwSt., den der Kunde bei der Zahlung abzieht. Er wird mit einer Zahlungsbonus-Gutschrift verbucht.')
;
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585517
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name = 'Payment bonus', PrintName = 'Payment bonus',
    Description = 'Bonus incl. VAT that the customer deducts when paying. It is booked with a payment bonus credit memo.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-06 12:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585517 AND AD_Language = 'en_US'
;
UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-06 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585517 AND AD_Language IN ('de_DE', 'de_CH')
;
