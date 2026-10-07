-- Window 111 (Roles), main tab "Rolle" (AD_Tab 119): fix the placement of the role permission flag
-- IsAllowPasswordChangeForOthers (AD_Column 591277 / AD_Element 584086 "Passwortänderung für andere
-- erlauben" / AD_UI_Element 637723).
--
-- Defect: 637723 lived in the Advanced-Edit (Alt+E) group 540229 of the single-column section 540529,
-- yet it is the ONLY one of that group's 36 elements with IsAdvancedField='N'. Its 35 siblings are
-- 'Y' (correctly gated into the Alt+E modal); being the lone 'N', it was NOT pulled into the modal and
-- instead rendered inline as a stray full-width one-field block on the base form. A password-change
-- permission is a security-relevant setting a role administrator needs readily visible, so the fix is
-- to move it onto the base form's flags group rather than hide it in Alt+E.
--
-- Fix: move it into the main section's "flags" group 540242 (right column of section 540113) - the
-- tab's home for standalone Yes/No role permission toggles (IsActive, IsManual, IsAutoRoleLogin,
-- IsAccessAllOrgs, IsRoleAlwaysUseBetaFunctions, IsAttachmentDeletionAllowed) - as the next member at
-- SeqNo=70, directly after its closest semantic sibling IsAttachmentDeletionAllowed ("Darf Anhänge
-- löschen", SeqNo=60). IsAdvancedField stays 'N' (correct for a base-form group) and IsDisplayed is
-- already 'Y'. Recommendation validated by the window-designer render/rule-check of window 111.
UPDATE AD_UI_Element
SET AD_UI_ElementGroup_ID = 540242,
    SeqNo = 70,
    Updated = TO_TIMESTAMP('2026-09-25', 'YYYY-MM-DD'),
    UpdatedBy = 100
WHERE AD_UI_Element_ID = 637723
  AND AD_UI_ElementGroup_ID = 540229;
