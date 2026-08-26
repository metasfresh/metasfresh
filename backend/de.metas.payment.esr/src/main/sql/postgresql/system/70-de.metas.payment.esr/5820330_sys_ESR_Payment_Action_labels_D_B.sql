-- Re-word two ESR payment actions after a customer review of the labels shipped by 5819900.
--
-- 'D' (Keep for dunning): the label and description described only what happens to the REMAINDER
--   ("Betrag mahnen" / "Offenen Restbetrag ... in den Mahnlauf geben"), so the thing the accountant
--   was actually looking for -- that the incoming PARTIAL payment gets allocated to the invoice --
--   was invisible, and the action was not found when a debtor underpaid. The allocation is what the
--   action really does: the shared base handler allocates the payment and records the over/under
--   amount, and the dunning handler itself adds nothing on top. Whether the remainder is then
--   actually dunned depends on dunning configuration this action does not control, so the new text
--   deliberately promises only the allocation.
--
-- 'B' (Money was transferred back): both name and description asserted the refund had ALREADY
--   happened, but the action is what BOOKS the refund (it creates the outbound payment). Users hit
--   the case where the money had not yet been transferred and had no action to choose. Renamed to
--   the forward form and the description now states plainly that the bank transfer itself is manual.
--
-- Base row carries German (base language); en_US carries the English override.

-- 1. Base rows: German.
UPDATE AD_Ref_List rl SET Name=v.de_name, Description=v.de_desc,
       Updated=TO_TIMESTAMP('2026-08-26 14:10:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
FROM (VALUES
  (540538,'Teilzahlung zuordnen','Die eingegangene Teilzahlung wird der Rechnung zugeordnet.'),
  (540535,'Überzahlbetrag zurückerstatten','metasfresh bucht eine Auszahlung über den Überzahlbetrag und verrechnet sie mit der eingegangenen Zahlung. Die Banküberweisung selbst erfolgt ausserhalb von metasfresh und muss separat ausgelöst werden.')
) AS v(id, de_name, de_desc)
WHERE rl.AD_Ref_List_ID = v.id
;

-- 2. Push the German text into every non-en_US translation row.
--    IsTranslated='Y' only where the German text IS the final text (de_DE, de_CH); any other
--    language honestly stays 'N' -- correct German, still awaiting its own translation.
--    The IsTranslated guard protects a real translation somebody may have made since 5819900:
--    only de_DE/de_CH are overwritten unconditionally.
UPDATE AD_Ref_List_Trl t SET Name=rl.Name, Description=rl.Description,
       IsTranslated = CASE WHEN t.AD_Language IN ('de_DE','de_CH') THEN 'Y' ELSE 'N' END,
       Updated=TO_TIMESTAMP('2026-08-26 14:10:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
FROM   AD_Ref_List rl
WHERE  rl.AD_Ref_List_ID = t.AD_Ref_List_ID
  AND  rl.AD_Ref_List_ID IN (540538, 540535)
  AND  t.AD_Language <> 'en_US'
  AND  (t.AD_Language IN ('de_DE','de_CH') OR coalesce(t.IsTranslated,'N') <> 'Y')
;

-- 3. English override.
UPDATE AD_Ref_List_Trl t SET Name=v.en_name, Description=v.en_desc, IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-08-26 14:10:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
FROM (VALUES
  (540538,'Assign partial payment','The incoming partial payment is allocated to the invoice.'),
  (540535,'Refund the overpayment','metasfresh books an outbound payment for the overpaid amount and allocates it against the incoming payment. The bank transfer itself happens outside metasfresh and must be triggered separately.')
) AS v(id, en_name, en_desc)
WHERE t.AD_Ref_List_ID = v.id AND t.AD_Language = 'en_US'
;
