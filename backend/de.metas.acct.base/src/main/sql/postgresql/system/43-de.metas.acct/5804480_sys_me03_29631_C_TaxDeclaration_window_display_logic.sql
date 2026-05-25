-- Tax Declaration: DisplayLogic on the 4 new Correction-lifecycle fields (Iter 7).
-- Iter 7 of EPIC https://github.com/metasfresh/me03/issues/28717.
--
-- IsCorrection           — always visible (it's set by Create Correction process; user reads it on Original=N or Correction=Y).
-- Original_ID            — visible only for Corrections (NULL on Originals).
-- IsCorrectionNeeded     — always visible (drift detector writes it to Originals only).
-- CorrectionNeededReason — visible only when flagged ('@IsCorrectionNeeded/N@=Y').

UPDATE AD_Field
   SET DisplayLogic = '@IsCorrection/N@=Y'
 WHERE AD_Field_ID = 780480 /*From ID Server — C_TaxDeclaration_Original_ID*/;

UPDATE AD_Field
   SET DisplayLogic = '@IsCorrectionNeeded/N@=Y'
 WHERE AD_Field_ID = 780482 /*From ID Server — CorrectionNeededReason*/;
