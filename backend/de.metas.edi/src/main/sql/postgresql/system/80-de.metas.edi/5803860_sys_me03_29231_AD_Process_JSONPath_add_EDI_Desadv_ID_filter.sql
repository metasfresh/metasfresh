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
-- AD_Process_Para_ID=5432070 (=543207*10) — pre-allocated.
INSERT INTO AD_Process_Para (
    AD_Client_ID, AD_Org_ID, AD_Process_Para_ID, AD_Process_ID, AD_Element_ID, AD_Reference_ID,
    ColumnName, Name, Description, Help,
    EntityType, FieldLength,
    IsActive, IsAutocomplete, IsCentrallyMaintained, IsEncrypted, IsMandatory, IsRange,
    SeqNo, Created, CreatedBy, Updated, UpdatedBy
) VALUES (
    0, 0, 5432070, 585473, 542691, 19,
    'EDI_Desadv_ID', 'DESADV',
    'EDI DESADV identifier — filters the export view to a single source DESADV for consolidated multi-source-order shipments.',
    'Required for the multi-source-order junction-driven export path. Each enqueued EDI_Desadv workpackage invokes this process once per linked M_InOut, scoping the PostgREST query to a single row.',
    'de.metas.esb.edi', 0,
    'Y', 'N', 'Y', 'N', 'Y', 'N',
    20, now() AT TIME ZONE 'UTC', 100, now() AT TIME ZONE 'UTC', 100
)
ON CONFLICT (AD_Process_Para_ID) DO NOTHING
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
