-- me03#29231 — replace the all-rows UNIQUE constraint on EDI_Desadv_M_InOut(edi_desadv_id, m_inout_id)
-- with a partial UNIQUE INDEX restricted to active rows.
--
-- Per PR #24042 review comment on EDIDesadvInOutRepository:48 — the application-side
-- assignDesadvToInOut method uses addOnlyActiveRecordsFilter() for its existence check;
-- the original strict constraint (from 5803540) does not accommodate a soft-deleted
-- (IsActive='N') historical row coexisting with a new active row for the same pair.
-- The partial index expresses the actual invariant: at most one ACTIVE junction row
-- per (EDI_Desadv_ID, M_InOut_ID) pair. Inactive rows are unconstrained, so reactivation
-- / soft-delete workflows are safe.

ALTER TABLE public.edi_desadv_m_inout DROP CONSTRAINT IF EXISTS edi_desadv_m_inout_un;

CREATE UNIQUE INDEX IF NOT EXISTS edi_desadv_m_inout_active_uc_idx
	ON public.edi_desadv_m_inout(edi_desadv_id, m_inout_id)
	WHERE isactive = 'Y'
;
