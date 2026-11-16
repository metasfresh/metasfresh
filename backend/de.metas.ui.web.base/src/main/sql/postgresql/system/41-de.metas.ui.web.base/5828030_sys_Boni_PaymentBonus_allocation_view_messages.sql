-- Boni: messages of the payment bonus in the WebUI payment allocation view, and the caption of the column "Zahlungsbonus-Hinweis"
-- IDs allocated from the central ID server on 2026-10-06: AD_Message 545901..545905, AD_Element 585518 (PaymentBonusNote)

INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value, ErrorCode)
VALUES (0, 545901 /*From ID Server*/, 0, TO_TIMESTAMP('2026-10-06 13:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y',
        'Der Zahlungsbonus {0} ist größer als der offene Betrag {1} der Rechnung {2}.', 'E',
        TO_TIMESTAMP('2026-10-06 13:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'de.metas.ui.web.payment_allocation.PaymentBonusAboveOpenAmt', 'PAYMENT_BONUS_ABOVE_OPEN_AMT')
;

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l,
     AD_Message t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Message_ID = 545901
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

UPDATE AD_Message_Trl
SET MsgText='The payment bonus {0} is bigger than the open amount {1} of invoice {2}.', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-10-06 13:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545901
;

UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-06 13:00:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Message_ID = 545901
;

INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545902 /*From ID Server*/, 0, TO_TIMESTAMP('2026-10-06 13:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y',
        'Zahlungsbonus {0} ist größer als der offene Betrag {1} und wurde nicht vorbelegt.', 'I',
        TO_TIMESTAMP('2026-10-06 13:00:04', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'de.metas.ui.web.payment_allocation.PaymentBonusNotPrefilledAboveOpenAmt')
;

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l,
     AD_Message t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Message_ID = 545902
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

UPDATE AD_Message_Trl
SET MsgText='Payment bonus {0} is bigger than the open amount {1} and was not pre-filled.', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-10-06 13:00:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545902
;

UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-06 13:00:06', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Message_ID = 545902
;

INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545903 /*From ID Server*/, 0, TO_TIMESTAMP('2026-10-06 13:00:07', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y',
        'Zahlungsbonus konnte nicht berechnet werden: {0}', 'I',
        TO_TIMESTAMP('2026-10-06 13:00:07', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'de.metas.ui.web.payment_allocation.PaymentBonusNotComputed')
;

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l,
     AD_Message t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Message_ID = 545903
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

UPDATE AD_Message_Trl
SET MsgText='The payment bonus could not be computed: {0}', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-10-06 13:00:08', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545903
;

UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-06 13:00:09', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Message_ID = 545903
;

INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545904 /*From ID Server*/, 0, TO_TIMESTAMP('2026-10-06 13:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y',
        'Zahlungsbonus {0} ist mit der MwSt. nicht genau buchbar; angepasst auf {1}.', 'I',
        TO_TIMESTAMP('2026-10-06 13:00:10', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'de.metas.ui.web.payment_allocation.PaymentBonusAdjusted')
;

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l,
     AD_Message t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Message_ID = 545904
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

UPDATE AD_Message_Trl
SET MsgText='The payment bonus {0} cannot be booked exactly with the VAT; adjusted to {1}.', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-10-06 13:00:11', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545904
;

UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-06 13:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Message_ID = 545904
;

INSERT INTO AD_Message (AD_Client_ID, AD_Message_ID, AD_Org_ID, Created, CreatedBy, EntityType, IsActive, MsgText, MsgType, Updated, UpdatedBy, Value)
VALUES (0, 545905 /*From ID Server*/, 0, TO_TIMESTAMP('2026-10-06 13:00:13', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y',
        'Zahlungsbonus wird nur in der Rechnungswährung {0} abgezogen, nicht in {1}.', 'I',
        TO_TIMESTAMP('2026-10-06 13:00:13', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'de.metas.ui.web.payment_allocation.PaymentBonusOtherCurrency')
;

INSERT INTO AD_Message_Trl (AD_Language, AD_Message_ID, MsgText, MsgTip, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Message_ID, t.MsgText, t.MsgTip, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l,
     AD_Message t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Message_ID = 545905
  AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Message_ID = t.AD_Message_ID)
;

UPDATE AD_Message_Trl
SET MsgText='The payment bonus is only deducted in the invoice currency {0}, not in {1}.', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-10-06 13:00:14', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language = 'en_US' AND AD_Message_ID = 545905
;

UPDATE AD_Message_Trl
SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-06 13:00:15', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Message_ID = 545905
;

-- the caption of the column; like PaymentBonusAmt, an element without a table column
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585518 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-10-06 13:00:16', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-10-06 13:00:16', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'PaymentBonusNote', 'D', 'Zahlungsbonus-Hinweis', 'Zahlungsbonus-Hinweis',
        'Warum der Zahlungsbonus nicht der berechnete ist, z.B. weil er nicht berechnet werden konnte oder der eingegebene Betrag angepasst wurde.')
;
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, t.AD_Element_ID, t.Name, t.PrintName, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 585518
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;
UPDATE AD_Element_Trl
SET Name = 'Payment bonus note', PrintName = 'Payment bonus note',
    Description = 'Why the payment bonus is not the computed one, e.g. because it could not be computed, or because the entered amount was adjusted.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-06 13:00:17', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585518 AND AD_Language = 'en_US'
;
UPDATE AD_Element_Trl
SET IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-10-06 13:00:18', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE AD_Element_ID = 585518 AND AD_Language IN ('de_DE', 'de_CH')
;
