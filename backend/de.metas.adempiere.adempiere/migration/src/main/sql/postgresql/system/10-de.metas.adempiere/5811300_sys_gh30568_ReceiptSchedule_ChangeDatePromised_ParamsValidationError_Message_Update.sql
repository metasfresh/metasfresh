-- Run mode: SWING_CLIENT

-- Value: receiptschedule.ChangeDatePromised_OverrideAndPOReference.paramsValidationError
-- Include "Confirmed by Supplier" as a third valid option, now that
-- M_ReceiptSchedule_ChangeDatePromised_OverrideAndPOReference also updates IsConfirmedBySupplier.
UPDATE AD_Message
SET MsgText = 'At least one of "Date Promised Override", "PO Reference" or "Confirmed by Supplier" must be provided.'
WHERE Value = 'receiptschedule.ChangeDatePromised_OverrideAndPOReference.paramsValidationError'
;

UPDATE AD_Message_Trl
SET MsgText = 'Mindestens eines von „Lieferdatum (Überschreibung)“, „Bestellreferenz“ oder „Bestätigt durch Lieferant“ muss angegeben werden.'
WHERE AD_Language IN ('de_CH', 'de_DE')
AND AD_Message_ID = (SELECT AD_Message_ID FROM AD_Message WHERE Value = 'receiptschedule.ChangeDatePromised_OverrideAndPOReference.paramsValidationError')
;

UPDATE AD_Message base
SET MsgText = trl.MsgText, Updated = trl.Updated, UpdatedBy = trl.UpdatedBy
FROM AD_Message_Trl trl
WHERE trl.AD_Message_ID = base.AD_Message_ID
AND trl.AD_Language IN ('de_CH', 'de_DE')
AND trl.AD_Language = getBaseLanguage()
;