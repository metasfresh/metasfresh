-- gh#28565: Move Promotion Code fields to less prominent position in Advanced Edit
--
-- Design guide: Promotion codes are secondary business fields, not mandatory or "very important".
-- They should appear in the dedicated "advanced edit" UI element group, not in the "main" group
-- where they render prominently at the top of the Advanced Edit modal.
--
-- Changes:
-- 1. Sales Order (143): Move from "main" group (540005) to "advanced edit" group (540499), seqno 283/286
-- 2. Invoice (167): Move from "default" group (540022) to "advanced edit" group (541214), seqno 115/118
-- 3. Invoice Candidate (540092): Already in correct group (540056), fix seqno collision (1050->1055, 1060->1058)

-- ==========================================================================
-- 1. Sales Order (143) — Move promo codes from "main" (540005) to "advanced edit" (540499)
-- ==========================================================================

-- C_PromotionCode_ID (648483): move to advanced edit group, seqno 283 (after OfferValidDays=280, before Processed=290)
UPDATE AD_UI_Element
SET AD_UI_ElementGroup_ID = 540499,
    SeqNo = 283,
    Updated = TO_TIMESTAMP('2026-03-07', 'YYYY-MM-DD'),
    UpdatedBy = 100
WHERE AD_UI_Element_ID = 648483;

-- C_PromotionCode2_ID (648484): move to advanced edit group, seqno 286
UPDATE AD_UI_Element
SET AD_UI_ElementGroup_ID = 540499,
    SeqNo = 286,
    Updated = TO_TIMESTAMP('2026-03-07', 'YYYY-MM-DD'),
    UpdatedBy = 100
WHERE AD_UI_Element_ID = 648484;

-- ==========================================================================
-- 2. Invoice (167) — Move promo codes from "default" (540022) to "advanced edit" (541214)
-- ==========================================================================

-- C_PromotionCode_ID (648487): move to advanced edit group, seqno 115 (after C_Project_ID=110)
UPDATE AD_UI_Element
SET AD_UI_ElementGroup_ID = 541214,
    SeqNo = 115,
    Updated = TO_TIMESTAMP('2026-03-07', 'YYYY-MM-DD'),
    UpdatedBy = 100
WHERE AD_UI_Element_ID = 648487;

-- C_PromotionCode2_ID (648488): move to advanced edit group, seqno 118
UPDATE AD_UI_Element
SET AD_UI_ElementGroup_ID = 541214,
    SeqNo = 118,
    Updated = TO_TIMESTAMP('2026-03-07', 'YYYY-MM-DD'),
    UpdatedBy = 100
WHERE AD_UI_Element_ID = 648488;

-- ==========================================================================
-- 3. Invoice Candidate (540092) — Fix seqno collision (already in correct group 540056)
-- ==========================================================================

-- C_PromotionCode_ID (648485): fix seqno from 1050 to 1055 (1050 collides with C_Project_ID)
UPDATE AD_UI_Element
SET SeqNo = 1055,
    Updated = TO_TIMESTAMP('2026-03-07', 'YYYY-MM-DD'),
    UpdatedBy = 100
WHERE AD_UI_Element_ID = 648485;

-- C_PromotionCode2_ID (648486): fix seqno from 1060 to 1058
UPDATE AD_UI_Element
SET SeqNo = 1058,
    Updated = TO_TIMESTAMP('2026-03-07', 'YYYY-MM-DD'),
    UpdatedBy = 100
WHERE AD_UI_Element_ID = 648486;
