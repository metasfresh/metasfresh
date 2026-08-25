-- ESR_ImportLine.ESR_Payment_Action (AD_Reference_ID=540386): repair the names + descriptions the
-- accountant actually reads in the ESR Import window.
--
-- WHY. Verified against two live instances: only 'P' was ever IsTranslated='Y'. Every other
-- AD_Ref_List_Trl row is IsTranslated='N', so the WebUI falls back to the base row -- which holds a
-- GERMAN Name and an ENGLISH Description. Net effect: German users read English descriptions and
-- English users read German names, in every language. On top of that:
--   * 'R' (Rueckbuchung) shows the Trl name "Summenzeile" in en_US and de_CH -- the label of 'C'.
--   * 'N' has "!!not used in fresh!!" leaking into the en_US/de_CH description.
--   * 'C' description reads "Contro line" (typo).
--   * 'B', 'T', 'U' have no description at all, in any language.
--   * 'A' is English in the base row, where the base language is German.
--   * 'P' says "The payment already exists. No action is required." That is now FALSE: since the ESR
--     duplicate-payment fix the line gets its OWN C_Payment and the accountant MUST pick an
--     overpayment action; the import cannot be completed otherwise.
--
-- ROOT CAUSE of the stale Trl rows, for future scripts: 5741590 seeded AD_Ref_List_Trl FROM the base
-- row and only THEN renamed the base row, so the Trl rows kept the pre-rename text forever. Always
-- set the base row first, seed second, then set each language explicitly (as below).
--
-- Descriptions state what metasfresh DOES vs what the user must do, because several actions only set
-- a flag (N, E) while one books a document (B) -- verified in de.metas.payment.esr.actionhandler.impl.
--
-- No model regeneration needed: no ValueName changes, and X_ESR_ImportLine already carries every
-- ESR_PAYMENT_ACTION_* constant incl. Unknown_Invoice.

-- 1. 'U' exists in core (5741590) but NOT on customer branches that predate it -- e.g.
--    long-lived customer branches, where ESRImportBL stores 'Unknown_Invoice' for SCOR lines and the
--    WebUI then shows the raw code. Idempotent, so this script is portable to those branches unchanged.
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName)
SELECT 0,0,540386,543782 /*From ID Server*/,TO_TIMESTAMP('2026-08-25 08:30:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.esr','Y','Systemfremde Rechnung',TO_TIMESTAMP('2026-08-25 08:30:00','YYYY-MM-DD HH24:MI:SS'),100,'U','Unknown_Invoice'
WHERE NOT EXISTS (SELECT 1 FROM AD_Ref_List WHERE AD_Ref_List_ID=543782)
;

-- 2. Base row = German (base language). Set Name AND Description for every action.
UPDATE AD_Ref_List rl SET Name=v.de_name, Description=v.de_desc,
       Updated=TO_TIMESTAMP('2026-08-25 08:30:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
FROM (VALUES
  (541079,'Zahlung mit aktueller Rechnung verrechnen','Zahlung der aktuellen Rechnung zuordnen.'),
  (540535,'Überzahlbetrag wurde zurückerstattet','Die Rückerstattung ist bereits erfolgt: metasfresh bucht dazu eine Auszahlung über den Überzahlbetrag und verrechnet sie mit der eingegangenen Zahlung. Die Banküberweisung selbst erfolgt ausserhalb von metasfresh.'),
  (540557,'Summenzeile','Kontrollzeile der Bankdatei mit der Summe der Einzelzahlungen. Keine Aktion nötig.'),
  (540538,'Betrag mahnen','Offenen Restbetrag der Rechnung offen lassen und in den Mahnlauf geben.'),
  (540556,'Ertrag kann nicht zugewiesen werden','Betrag bleibt unzugeordnet und wird von der automatischen Zuordnung ausgenommen.'),
  (540547,'Ist verbucht','Betrag stimmt mit der Rechnung überein und ist verbucht. Keine Aktion nötig.'),
  (540536,'Zahlung mit der nächsten Rechnung verrechnen','Betrag wird für die automatische Zuordnung freigegeben; jetzt wird noch nichts verrechnet. Die nächste passende Rechnung des Partners verbraucht ihn.'),
  (542738,'Zahlungsdublette','metasfresh konnte diesen Zahlungseingang nicht von einem bereits erfassten unterscheiden (gleicher Partner, gleicher Betrag). Der Betrag ist als eigene Zahlung verbucht und nicht zugeordnet; bitte prüfen und eine Aktion wählen.'),
  (541195,'Rückbuchung','Rückbuchung der Bank. Wird nicht automatisch verarbeitet, bitte manuell behandeln.'),
  (542188,'Skonto','Differenz zwischen Rechnungs- und Zahlbetrag als Skonto verbuchen.'),
  (543782,'Systemfremde Rechnung','Die Referenz gehört zu keiner Rechnung in metasfresh. Bitte manuell abklären.'),
  (540537,'Betrag abschreiben','Offenen Restbetrag der Rechnung abschreiben.')
) AS v(id, de_name, de_desc)
WHERE rl.AD_Ref_List_ID = v.id
;

-- 3. Seed any missing AD_Ref_List_Trl row for EVERY active system language (never per-language
--    VALUES -- a language such as fr_CH would otherwise get no row at all and fall back silently).
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID,Name,Description,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Ref_List_ID, t.Name, t.Description, 'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM   AD_Language l, AD_Ref_List t
WHERE  l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Reference_ID=540386
  AND  NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
--    NOTE: deliberately NO IsActive filter here. AD_Ref_List_Trl's PRIMARY KEY *is*
--    (AD_Ref_List_ID, AD_Language) -- the very key this guard matches -- so an IsActive='Y' filter
--    would hide a deactivated row and the INSERT would then violate the PK and abort the migration.
--    The general "filter IsActive on a NOT EXISTS guard" convention applies where the match key is a
--    NATURAL key distinct from a surrogate PK (a re-insert gets a fresh PK, so no collision); here it
--    is the PK itself, which inverts the outcome.
;

-- 4. Every NON-English row takes the corrected German base text. This must overwrite rows that
--    ALREADY exist, not just ones the seed created: on a real instance AD_Ref_List_Trl already has a
--    row per language (incl. fr_CH, en_GB, it_CH), so step 3 is a no-op for them and they would
--    otherwise keep exactly the German-name/English-description mix this script exists to remove.
--    IsTranslated='Y' only where the German text IS the final text (de_DE, de_CH); any other language
--    is honestly left 'N' -- correct German, still awaiting its own translation.
UPDATE AD_Ref_List_Trl t SET Name=rl.Name, Description=rl.Description,
       IsTranslated = CASE WHEN t.AD_Language IN ('de_DE','de_CH') THEN 'Y' ELSE 'N' END,
       Updated=TO_TIMESTAMP('2026-08-25 08:30:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
FROM   AD_Ref_List rl
WHERE  rl.AD_Ref_List_ID = t.AD_Ref_List_ID
  AND  rl.AD_Reference_ID = 540386
  AND  t.AD_Language <> 'en_US'
;

-- 5. English translations.
UPDATE AD_Ref_List_Trl t SET Name=v.en_name, Description=v.en_desc, IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-08-25 08:30:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
FROM (VALUES
  (541079,'Allocate payment with current invoice','Allocate the payment to the current invoice.'),
  (540535,'Overpayment was refunded','The refund has already been made: metasfresh books an outbound payment for the overpaid amount and allocates it against the incoming payment. The bank transfer itself happens outside metasfresh.'),
  (540557,'Control line','Control line of the bank file, holding the total of the individual payments. No action needed.'),
  (540538,'Keep for dunning','Leave the invoice''s remaining open amount open and pass it to the dunning run.'),
  (540556,'Income cannot be assigned','The amount stays unallocated and is excluded from automatic allocation.'),
  (540547,'Amount matches','The amount matches the invoice and is booked. No action needed.'),
  (540536,'Allocate payment with next invoice','The amount is released for automatic allocation; nothing is allocated yet. The partner''s next matching invoice consumes it.'),
  (542738,'Duplicate payment','metasfresh could not tell this incoming payment apart from one already recorded (same partner, same amount). The amount is booked as its own, unallocated payment; please review and choose an action.'),
  (541195,'Reverse booking','A reverse booking from the bank. Not processed automatically, please handle it manually.'),
  (542188,'Discount','Book the difference between invoice and paid amount as a discount.'),
  (543782,'Invoice not in metasfresh','The reference does not belong to any invoice in metasfresh. Please clarify manually.'),
  (540537,'Write off amount','Write off the invoice''s remaining open amount.')
) AS v(id, en_name, en_desc)
WHERE t.AD_Ref_List_ID = v.id AND t.AD_Language = 'en_US'
;
