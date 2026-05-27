UPDATE AD_Field
   SET DisplayLogic = '@IsCorrection/N@=Y'
 WHERE AD_Field_ID = 780480 /*From ID Server*/;

UPDATE AD_Field
   SET DisplayLogic = '@IsCorrectionNeeded/N@=Y'
 WHERE AD_Field_ID = 780482 /*From ID Server*/;
