-- Boni: the payment header gets the column "Zahlungsbonus" (PaymentBonusAmt): the bonus incl. VAT that the customer deducts when paying.
-- It reuses the existing element PaymentBonusAmt (585517). Mandatory, default 0, copied when a payment is copied.
--
-- IDs allocated from idserver.metas.de on 2026-10-12:
--   AD_MigrationScript 5829150
--   AD_Column 593733 (C_Payment.PaymentBonusAmt)

INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Version, EntityType,
                       ColumnName, AD_Table_ID, AD_Reference_ID, FieldLength, DefaultValue, IsMandatory, IsUpdateable, Callout,
                       AD_Element_ID, Name, Description, PersonalDataCategory, CloningStrategy)
VALUES (593733 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-12 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-12 12:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 0, 'D',
        'PaymentBonusAmt', 335, 12, 22, '0', 'Y', 'Y', 'org.compiere.model.CalloutPayment.amounts',
        585517, 'Zahlungsbonus', 'Bonus inkl. MwSt., den der Kunde bei der Zahlung abzieht. Er wird mit einer Zahlungsbonus-Gutschrift verbucht.', 'NP', 'XX')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 593733
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
UPDATE AD_Column_Trl
SET Name = 'Payment bonus', Description = 'Bonus incl. VAT that the customer deducts when paying. It is booked with a payment bonus credit memo.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-12 12:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 593733 AND AD_Language = 'en_US'
;
UPDATE AD_Column_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-12 12:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Column_ID = 593733 AND AD_Language IN ('de_DE', 'de_CH')
;

/* DDL */ SELECT public.db_alter_table('C_Payment', 'ALTER TABLE public.C_Payment ADD COLUMN PaymentBonusAmt NUMERIC DEFAULT 0 NOT NULL')
;
