-- 2026-05-28T00:00:00.000Z
-- gh29558: reposition IsWithoutCharge next to the other checkboxes on the
-- C_CompensationGroup_Schema_TemplateLine tab (was at SeqNo/SeqNoGrid=70,
-- after IsActive; should be between IsHideWhenPrinting (48) and
-- C_Flatrate_Conditions_ID (50)).
UPDATE AD_Field SET SeqNo=49,SeqNoGrid=49,Updated=TO_TIMESTAMP('2026-05-28 00:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=780495
;
