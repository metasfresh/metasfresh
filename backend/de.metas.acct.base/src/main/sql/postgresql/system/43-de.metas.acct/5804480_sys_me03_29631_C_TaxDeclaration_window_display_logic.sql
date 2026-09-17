UPDATE AD_Field
   SET DisplayLogic = '@IsCorrection/N@=Y',
       Updated=TO_TIMESTAMP('2026-05-26 00:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Field_ID = 780480 /*From ID Server*/;

UPDATE AD_Field
   SET DisplayLogic = '@IsCorrectionNeeded/N@=Y',
       Updated=TO_TIMESTAMP('2026-05-26 00:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Field_ID = 780482 /*From ID Server*/;
