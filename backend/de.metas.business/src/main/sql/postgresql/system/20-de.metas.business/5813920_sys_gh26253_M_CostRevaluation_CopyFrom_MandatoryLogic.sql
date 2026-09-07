-- gh26253 - Cost Revaluation: make CopyFrom_M_CostElement_ID conditionally mandatory in the UI.
--
-- The source cost element is required ONLY when RevaluationSource = 'CopyFromCostElement'
-- (it is null/irrelevant for the default 'Calculated' source). Modelled with MandatoryLogic
-- rather than a blanket IsMandatory so the field is required exactly in the state that needs it.
-- This is the UI guard; it COMPLEMENTS (does not replace) the server-side enforcement in
-- CostRevaluationService.createDetailsForCopyFromCostElement (which throws when the FK is unset).
--
-- Metadata-only UPDATE on the existing AD_Column 592962 (allocated in 5813790_..._RevaluationSource.sql);
-- MandatoryLogic is not reflected in the generated I_/X_ model, so no model regeneration is needed.

UPDATE AD_Column
SET MandatoryLogic = '@RevaluationSource@=''CopyFromCostElement''',
    Updated = TO_TIMESTAMP('2026-07-14 14:02:00','YYYY-MM-DD HH24:MI:SS')
WHERE AD_Column_ID = 592962   -- M_CostRevaluation.CopyFrom_M_CostElement_ID
;
