-- Replace the all-rows UNIQUE constraint on EDI_Desadv_M_InOut(edi_desadv_id, m_inout_id)
-- with a partial UNIQUE INDEX restricted to active rows.
--
-- assignDesadvToInOut uses addOnlyActiveRecordsFilter() for its existence check, so the
-- all-rows constraint incorrectly blocks a new active row when a soft-deleted (IsActive='N')
-- historical row already exists for the same pair.
-- The partial index expresses the actual invariant: at most one ACTIVE junction row
-- per (EDI_Desadv_ID, M_InOut_ID) pair. Inactive rows are unconstrained.

ALTER TABLE public.edi_desadv_m_inout DROP CONSTRAINT IF EXISTS edi_desadv_m_inout_un;

CREATE UNIQUE INDEX IF NOT EXISTS edi_desadv_m_inout_active_uc_idx
	ON public.edi_desadv_m_inout(edi_desadv_id, m_inout_id)
	WHERE isactive = 'Y'
;
