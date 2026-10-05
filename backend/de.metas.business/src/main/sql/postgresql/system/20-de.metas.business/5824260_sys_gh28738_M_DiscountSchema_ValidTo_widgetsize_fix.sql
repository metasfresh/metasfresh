-- Fix-up for M_DiscountSchema.ValidTo field (Task 1 of issue #28738)
-- Addresses: AD_UI_Element.WidgetSize inconsistent with sibling ValidFrom; AD_Field.SeqNoGrid unset
-- (code-review finding: window-designer render + code-reviewer pass)

-- ============================================================================
-- Step 1: Match WidgetSize to the sibling ValidFrom field (both plain Date fields, same group)
-- ============================================================================
UPDATE AD_UI_Element
SET WidgetSize = 'S',
    Updated = TO_TIMESTAMP('2026-09-13 22:00:00', 'YYYY-MM-DD HH24:MI:SS')
WHERE AD_UI_Element_ID = 654742;

-- ============================================================================
-- Step 2: Set AD_Field.SeqNoGrid for consistency with sibling fields on the same tab
-- ============================================================================
UPDATE AD_Field
SET SeqNoGrid = 0,
    Updated = TO_TIMESTAMP('2026-09-13 22:00:01', 'YYYY-MM-DD HH24:MI:SS')
WHERE AD_Field_ID = 784974;
