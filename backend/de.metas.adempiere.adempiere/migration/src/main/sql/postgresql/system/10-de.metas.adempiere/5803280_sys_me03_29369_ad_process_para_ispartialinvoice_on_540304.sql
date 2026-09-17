-- me03 #29369: expose IsPartialInvoice as a process parameter on the async
-- "Auswahl Fakturieren" process (AD_Process_ID=540304,
-- C_Invoice_Candidate_EnqueueSelectionForInvoicing). Mirrors the existing
-- param on the synchronous C_Invoice_Candidate_Generate (540166, param 543194).
--
-- The Java side is already wired: IInvoicingParams.PARA_IsPartialInvoice and
-- IInvoiceHeader.getIsPartialInvoice() consume this param. Default 'Y'
-- (Partial) per REQUIREMENTS.md §3.4 — failing to mark Final only strands
-- prepay (visible, recoverable); failing to mark Partial silently consumes the
-- whole prepay on the first invoice (worse).
--
-- AD_Element 584794 (Teilrechnung) is reused — same lookup label, help, and
-- translations as the sync-process param. add_missing_translations() at the
-- bottom backfills _Trl rows for any languages that didn't have one yet.

INSERT INTO AD_Process_Para (
    AD_Process_Para_ID,
    AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, Description, Help,
    AD_Process_ID, SeqNo,
    AD_Reference_ID,
    ColumnName, IsCentrallyMaintained, FieldLength, IsMandatory, IsRange,
    DefaultValue,
    AD_Element_ID, EntityType,
    IsEncrypted, IsAutocomplete, ShowInactiveValues
) VALUES (
    543206 /*From ID Server*/,
    0, 0, 'Y',
    TO_TIMESTAMP('2026-05-19 18:30:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-05-19 18:30:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Teilrechnung',
    'Wenn aktiviert, ist diese Rechnung eine Teilrechnung.',
    'Bei einer Eingangsrechnung zu einem vorausbezahlten Auftrag bestimmt dieses Kennzeichen, wie der Anzahlungsbetrag zugeordnet wird: Teilrechnung (Y) → MIN(Wareneingang × LC%, verbleibende Anzahlung); Endrechnung (N) → verbleibende Anzahlung vollständig verbrauchen. Standard Y (Teilrechnung) auf Belegart-Ebene; pro Rechnung überschreibbar, solange der Belegstatus Entwurf (DR) oder In Bearbeitung (IP) ist.',
    540304, 56,
    20,
    'IsPartialInvoice', 'Y', 1, 'N', 'N',
    'Y',
    584794, 'de.metas.invoicecandidate',
    'N', 'N', 'N'
);

-- Seed _Trl rows for every active system language. Idempotent: NOT EXISTS guard
-- avoids duplicates if this script is re-applied or if add_missing_translations()
-- has run beforehand.
INSERT INTO AD_Process_Para_Trl (
    AD_Language, AD_Process_Para_ID,
    AD_Client_ID, AD_Org_ID, IsActive,
    Created, CreatedBy, Updated, UpdatedBy,
    Name, Description, Help,
    IsTranslated
)
SELECT
    l.AD_Language, p.AD_Process_Para_ID,
    p.AD_Client_ID, p.AD_Org_ID, p.IsActive,
    TO_TIMESTAMP('2026-05-19 18:30:01', 'YYYY-MM-DD HH24:MI:SS'), p.CreatedBy,
    TO_TIMESTAMP('2026-05-19 18:30:01', 'YYYY-MM-DD HH24:MI:SS'), p.UpdatedBy,
    p.Name, p.Description, p.Help,
    'N'
FROM AD_Language l
JOIN AD_Process_Para p ON p.AD_Process_Para_ID = 543206
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y'
  AND NOT EXISTS (
      SELECT 1 FROM AD_Process_Para_Trl t
      WHERE t.AD_Language = l.AD_Language
        AND t.AD_Process_Para_ID = p.AD_Process_Para_ID
  );

-- en_US override: English label/description/help (Partial invoice path).
UPDATE AD_Process_Para_Trl
SET Name = 'Partial invoice',
    Description = 'When enabled, the resulting invoice is flagged as a partial invoice.',
    Help = 'For a vendor invoice against a prepaid order this flag controls how the prepayment is allocated: Partial (Y) → MIN(receipt × LC%, remaining prepay); Final (N) → consumes all remaining prepay. Default Y (Partial) at doctype level; overridable per invoice while the document status is Draft (DR) or In Progress (IP).',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-05-19 18:30:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Language = 'en_US' AND AD_Process_Para_ID = 543206;

-- Mark de_DE / de_CH as actively translated (text is identical to the base).
UPDATE AD_Process_Para_Trl
SET IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-05-19 18:30:03', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Language IN ('de_DE', 'de_CH') AND AD_Process_Para_ID = 543206;

-- Note: we do NOT call add_missing_translations() here. The 12-language _Trl
-- batch above explicitly covers every active system language; calling the broad
-- cascade would only re-scan all 69 _Trl tables and (on seed DBs with stale
-- ad_window_trl rows) trip the deferred ad_window_trl_name_uc constraint —
-- failing this migration script for unrelated dirty data.
