-- Rename the C_BPartner.EInvoice_BuyerReference field label from "Leitweg-ID" to
-- the general CII BT-10 "Buyer reference", to stop misleading customers outside
-- the German public sector into thinking a government-issued Leitweg-ID is required.
-- Any agreed reference between buyer and seller satisfies BR-DE-15 for XRechnung.
-- Adds explanatory Help text (previously empty in all languages).
--
-- AFFECTED RECORDS
-- =====================================================================
-- 1) AD_Element 584063 (ColumnName=EInvoice_BuyerReference)
-- -----------------------------------------------------------------
--   Lang  | Name / PrintName (old -> new)              | Help (old -> new)
--   ------+--------------------------------------------+-----------------------------
--   de_DE | Leitweg-ID -> Leitweg-ID / Kaeuferreferenz  | (empty) -> DE help text
--   de_CH | Leitweg-ID -> Leitweg-ID / Kaeuferreferenz  | (empty) -> DE help text
--   en_US | Buyer Reference -> Buyer Reference (kept)   | (empty) -> EN help text
--
-- 2) Propagates via update_TRL_Tables_On_AD_Element_TRL_Update to every AD_Field
--    whose column's element is 584063 (AD_Field.AD_Name_ID IS NULL for all of them,
--    so they inherit the column's element translations, not a per-field override):
--      AD_Field 754545 (AD_Tab_ID=220,    Window 123    Geschaeftspartner_OLD)
--      AD_Field 754548 (AD_Tab_ID=548036, Window 541887 Geschaeftspartner)
--      AD_Field 772995 (AD_Tab_ID=549020, Window 542087 Geschaeftspartner - Vertrieb)
--    All three windows are EntityType 'D' (metasfresh core).
--
-- NOT AFFECTED: en_GB/es_ES/fi_FI/fr_CH/fr_FR/it_IT/nl_NL/pl_PL/pt_PT translations
-- (left as the German fallback text, unchanged by this script).

-- 2026-07-11 10:00:00
UPDATE AD_Element_Trl
SET Name='Leitweg-ID / Käuferreferenz',
    PrintName='Leitweg-ID / Käuferreferenz',
    Help='Die Käuferreferenz (BT-10), die auf jeder XRechnung verpflichtend ist. Für Rechnungen an öffentliche Auftraggeber ist dies die von der empfangenden Behörde vergebene Leitweg-ID; für andere Empfänger die mit dem Käufer vereinbarte Referenz (z. B. Kunden- oder Bestellnummer).',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-07-11 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID=584063 AND AD_Language='de_DE'
;

-- 2026-07-11 10:00:01
UPDATE AD_Element_Trl
SET Name='Leitweg-ID / Käuferreferenz',
    PrintName='Leitweg-ID / Käuferreferenz',
    Help='Die Käuferreferenz (BT-10), die auf jeder XRechnung verpflichtend ist. Für Rechnungen an öffentliche Auftraggeber ist dies die von der empfangenden Behörde vergebene Leitweg-ID; für andere Empfänger die mit dem Käufer vereinbarte Referenz (z. B. Kunden- oder Bestellnummer).',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-07-11 10:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID=584063 AND AD_Language='de_CH'
;

-- 2026-07-11 10:00:02
UPDATE AD_Element_Trl
SET Name='Buyer Reference',
    PrintName='Buyer Reference',
    Help='The buyer reference (BT-10) required on every XRechnung. For invoices to German public authorities this is the Leitweg-ID assigned by the receiving authority; for other recipients, enter the reference agreed with the buyer (e.g. customer or order number).',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-07-11 10:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Element_ID=584063 AND AD_Language='en_US'
;

-- Propagate to AD_Element (base language), AD_Column/AD_Column_Trl and
-- AD_Field/AD_Field_Trl (fields 754545, 754548, 772995 - AD_Name_ID IS NULL on
-- all three, so they inherit from this column's element).
-- 2026-07-11 10:00:03
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584063,'de_DE')
;

-- 2026-07-11 10:00:04
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584063,'de_CH')
;

-- 2026-07-11 10:00:05
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584063,'en_US')
;
