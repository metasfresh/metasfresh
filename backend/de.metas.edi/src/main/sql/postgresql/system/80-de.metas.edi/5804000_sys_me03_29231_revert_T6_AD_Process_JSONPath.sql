-- me03#29231 — revert the T6 AD_Process JSONPath / EDI_Desadv_ID mandatory parameter for
-- M_InOut_EDI_Export_JSON (AD_Process_ID=585473). See PLAN_ARRAY_MODE.md §6.1.
--
-- Background — array-mode pivot:
-- The previous design (migration 5803860, code-review T6 / HIGH #2) required every call to the
-- M_InOut_EDI_Export_JSON process to supply both M_InOut_ID and EDI_Desadv_ID, scoping the export
-- view to one (m_inout_id, edi_desadv_id) row so expectSingleResult(true) held. That broke five
-- pre-existing direct REST callers that only knew the M_InOut_ID, and forced the EDIWorkpackageProcessor
-- to enqueue one workpackage per (desadv, inout) pair.
--
-- ARRAY-MODE Phase 1 relaxes this: the M_InOut JSON-export now expects an *array* of DESADVs
-- (one element per EDI_Desadv_M_InOut junction row) via the new shouldExpectSingleResult()=false
-- hook on M_InOut_EDI_Export_JSON. The downstream Camel route iterates over the array and
-- dispatches one EDIFACT message per element. With this contract the EDI_Desadv_ID parameter is
-- no longer needed at the AD_Process level.
--
-- This migration:
--   1. Drops the &edi_desadv_id=eq.@EDI_Desadv_ID/0@ filter from the JSONPath.
--   2. Deactivates the AD_Process_Para row for EDI_Desadv_ID (we keep the ID server allocation —
--      5432070 — so we never re-use it, but the parameter is no longer published to callers).
--   3. Deactivates the matching AD_Process_Para_Trl rows.
--
-- Idempotent: if the rows don't exist (fresh DB / migration 5803860 not applied), the UPDATEs are
-- no-ops. No DELETE is used; deactivation preserves audit history and the ID server allocation.

-- 1) Revert JSONPath: filter only by m_inout_id; the array shape is the contract now.
UPDATE AD_Process
SET JSONPath = 'm_inout_export_edi_desadv_json_v?select=embedded_json->metasfresh_DESADV&m_inout_id=eq.@M_InOut_ID/0@',
    Updated = now() AT TIME ZONE 'UTC',
    UpdatedBy = 100
WHERE AD_Process_ID = 585473
;

-- 2) Deactivate the EDI_Desadv_ID parameter (preserve the ID server allocation 5432070).
UPDATE AD_Process_Para
SET IsActive = 'N',
    Updated = now() AT TIME ZONE 'UTC',
    UpdatedBy = 100
WHERE AD_Process_Para_ID = 5432070
;

-- 3) Deactivate translations too.
UPDATE AD_Process_Para_Trl
SET IsActive = 'N',
    Updated = now() AT TIME ZONE 'UTC',
    UpdatedBy = 100
WHERE AD_Process_Para_ID = 5432070
;
