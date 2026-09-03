-- gh#28565: Fix AD_Val_Rule date quoting for PromotionCode lookups
-- The @DateOrdered@ and @DateInvoiced@ context variables were not quoted,
-- causing SQL syntax errors like: ValidTo >= 2026-03-06 00:00:00
-- Correct pattern (used by all other val rules): >= ''@DateOrdered@''

-- Fix validation rule 540771 (C_PromotionCode for Order)
UPDATE AD_Val_Rule
SET Code    = 'C_PromotionCode.IsActive=''Y'' AND (C_PromotionCode.ValidTo IS NULL OR C_PromotionCode.ValidTo >= ''@DateOrdered@'')',
    Updated = TO_TIMESTAMP('2026-03-06 18:30', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy = 100
WHERE AD_Val_Rule_ID = 540771;

-- Fix validation rule 540772 (C_PromotionCode for Invoice)
UPDATE AD_Val_Rule
SET Code    = 'C_PromotionCode.IsActive=''Y'' AND (C_PromotionCode.ValidTo IS NULL OR C_PromotionCode.ValidTo >= ''@DateInvoiced@'')',
    Updated = TO_TIMESTAMP('2026-03-06 18:30', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy = 100
WHERE AD_Val_Rule_ID = 540772;
