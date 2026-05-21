-- me03#29231 — extend the PostgREST JSONPath of M_InOut_EDI_Export_JSON to filter by BOTH
-- m_inout_id AND edi_desadv_id, and add the new mandatory EDI_Desadv_ID process parameter.
--
-- Background: a consolidated multi-source-order shipment links to N source DESADVs via the
-- edi_desadv_m_inout junction. The export view (M_InOut_Export_EDI_DESADV_JSON_V) now emits one
-- row per (m_inout_id, edi_desadv_id) pair. Without the EDI_Desadv_ID filter the PostgREST call
-- would return N rows and trip expectSingleResult(true) in PostgRESTProcessExecutor.
-- The EDIWorkpackageProcessor enqueues one workpackage per (desadv, inout) pair and supplies
-- the EDI_Desadv_ID parameter for each call (wired in PLAN_ARCH T7).

-- AD_Process M_InOut_EDI_Export_JSON: AD_Process_ID=585473
UPDATE AD_Process
SET JSONPath = 'm_inout_export_edi_desadv_json_v?select=embedded_json->metasfresh_DESADV&m_inout_id=eq.@M_InOut_ID/0@&edi_desadv_id=eq.@EDI_Desadv_ID/0@',
    Updated = now() AT TIME ZONE 'UTC',
    UpdatedBy = 100
WHERE AD_Process_ID = 585473
  AND value = 'M_InOut_EDI_Export_JSON'
;

-- Add the EDI_Desadv_ID parameter. AD_Element_ID=542691 (existing EDI_Desadv_ID element, Name='DESADV').
-- AD_Reference_ID=19 (Table Direct) — matches the M_InOut_ID parameter style; PostgREST resolves the
-- ID at runtime regardless. IsMandatory='Y' enforces the new contract from M_InOut_EDI_Export_JSON.java.
-- AD_Process_Para_ID=5432070 was fetched from the central ID server
-- (PROJECT=metas TABLE=AD_Process_Para returned 543207; ×10 = 5432070 per the metasfresh-db skill).
INSERT INTO AD_Process_Para (
    AD_Client_ID, AD_Org_ID, AD_Process_Para_ID /*From ID Server*/, AD_Process_ID, AD_Element_ID, AD_Reference_ID,
    ColumnName, Name, Description, Help,
    EntityType, FieldLength,
    IsActive, IsAutocomplete, IsCentrallyMaintained, IsEncrypted, IsMandatory, IsRange,
    SeqNo, Created, CreatedBy, Updated, UpdatedBy
) VALUES (
    0, 0, 5432070 /*From ID Server*/, 585473, 542691, 19,
    'EDI_Desadv_ID', 'DESADV',
    'EDI DESADV identifier — filters the export view to a single source DESADV for consolidated multi-source-order shipments.',
    'Required for the multi-source-order junction-driven export path. Each enqueued EDI_Desadv workpackage invokes this process once per linked M_InOut, scoping the PostgREST query to a single row.',
    'de.metas.esb.edi', 0,
    'Y', 'N', 'Y', 'N', 'Y', 'N',
    20, now() AT TIME ZONE 'UTC', 100, now() AT TIME ZONE 'UTC', 100
)
ON CONFLICT (AD_Process_Para_ID) DO NOTHING
;

-- DefaultValue safety-net (mirrors the M_InOut_ID param set by 5755950). The /0@ suffix in the
-- JSONPath above is the primary safe-fail for substitution; this DefaultValue is the framework-
-- side fallback when the AD-process caller doesn't supply a value. IsMandatory='Y' should reject
-- those calls before substitution, but the layered fallback keeps the worst case to "empty
-- result set" rather than a NullPointer / crash. Code-review T6 / HIGH #3.
UPDATE AD_Process_Para
SET DefaultValue = '@EDI_Desadv_ID/0@',
    Updated = now() AT TIME ZONE 'UTC',
    UpdatedBy = 100
WHERE AD_Process_Para_ID = 5432070
;

-- Translate the new parameter into all installed languages (centrally maintained → AD_Element_Trl drives the actual text).
INSERT INTO AD_Process_Para_Trl (
    AD_Language, AD_Process_Para_ID, Description, Help, Name, IsTranslated,
    AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive
)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description, t.Help, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Process_Para_ID = 5432070
  AND NOT EXISTS (
      SELECT 1 FROM AD_Process_Para_Trl tt
      WHERE tt.AD_Language = l.AD_Language
        AND tt.AD_Process_Para_ID = t.AD_Process_Para_ID
  )
;

-- Propagate the AD_Element_Trl values (Name / Description / Help) into the newly-inserted
-- AD_Process_Para_Trl rows for each installed language. Without this, the skeleton _Trl rows
-- above carry the base-language strings copied from AD_Process_Para — wrong for non-base
-- languages. Idempotent: the function is a no-op if there's nothing to update.
-- See metasfresh-application-dictionary skill, section "Translation propagation". Code-review T6 / HIGH #4.
DO $$
DECLARE
    lang_rec RECORD;
BEGIN
    FOR lang_rec IN
        SELECT AD_Language
        FROM AD_Language
        WHERE IsActive = 'Y'
          AND (IsSystemLanguage = 'Y' OR IsBaseLanguage = 'Y')
    LOOP
        PERFORM update_TRL_Tables_On_AD_Element_TRL_Update(542691, lang_rec.AD_Language);
    END LOOP;
END $$
;
