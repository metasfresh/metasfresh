-- Boni: the payment header gets the link "Zahlungsbonus-Gutschrift" (PaymentBonus_CreditMemo_ID) to the credit memo that books the payment bonus.
-- Optional, read-only; not copied when a payment is copied.
--
-- IDs allocated from idserver.metas.de on 2026-10-12:
--   AD_MigrationScript 5829160
--   AD_Element 585536 (PaymentBonus_CreditMemo_ID)
--   AD_Column 593734 (C_Payment.PaymentBonus_CreditMemo_ID)

INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585536 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-12 12:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-12 12:10:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'PaymentBonus_CreditMemo_ID', 'D', 'Zahlungsbonus-Gutschrift', 'Zahlungsbonus-Gutschrift',
        'Gutschrift, mit der der Zahlungsbonus dieser Zahlung verbucht wurde.')
;
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585536
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name = 'Payment bonus credit memo', PrintName = 'Payment bonus credit memo',
    Description = 'Credit memo that booked the payment bonus of this payment.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-12 12:10:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585536 AND AD_Language = 'en_US'
;
UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-12 12:10:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585536 AND AD_Language IN ('de_DE', 'de_CH')
;

-- The column name does not end with the target table name, so the reference value (Table reference of C_Invoice, 336) names the target
INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Version, EntityType,
                       ColumnName, AD_Table_ID, AD_Reference_ID, AD_Reference_Value_ID, FieldLength, IsMandatory, IsUpdateable,
                       AD_Element_ID, Name, Description, PersonalDataCategory, CloningStrategy)
VALUES (593734 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-12 12:11:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-12 12:11:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 0, 'D',
        'PaymentBonus_CreditMemo_ID', 335, 30, 336, 10, 'N', 'N',
        585536, 'Zahlungsbonus-Gutschrift', 'Gutschrift, mit der der Zahlungsbonus dieser Zahlung verbucht wurde.', 'NP', 'SK')
;
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Column_ID = 593734
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585536)
;

/* DDL */ SELECT public.db_alter_table('C_Payment', 'ALTER TABLE public.C_Payment ADD COLUMN PaymentBonus_CreditMemo_ID NUMERIC(10)')
;
ALTER TABLE C_Payment ADD CONSTRAINT PaymentBonusCreditMemo_CPayment FOREIGN KEY (PaymentBonus_CreditMemo_ID) REFERENCES public.C_Invoice (C_Invoice_ID) DEFERRABLE INITIALLY DEFERRED
;
