
--
-- the following processes had typos in their value and class name - it's C_Flatrate_Term, not C_FlatrateTerm. 
-- the wrong spelling can cause confusion and can cause C_Flatrate_Term related processes not to be found as easily.
--

-- 02.11.2016 13:00
-- URL zum Konzept
UPDATE AD_Process SET Classname='de.metas.flatrate.process.C_Flatrate_Term_Create_For_BPartners', Value='C_Flatrate_Term_Create_For_BPartners',Updated=TO_TIMESTAMP('2016-11-02 13:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540460
;

-- 02.11.2016 13:00
-- URL zum Konzept
UPDATE AD_Process SET Classname='de.metas.materialtracking.process.C_Flatrate_Term_Create_For_MaterialTracking', Value='C_Flatrate_Term_Create_For_MaterialTracking',Updated=TO_TIMESTAMP('2016-11-02 13:00:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540626
;

-- 02.11.2016 13:01
-- URL zum Konzept
UPDATE AD_Process SET Classname='de.metas.flatrate.process.C_Flatrate_Term_Create_From_OLCand', Value='C_Flatrate_Term_Create_From_OLCand',Updated=TO_TIMESTAMP('2016-11-02 13:01:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=540301
;

UPDATE AD_Process SET Classname='de.metas.flatrate.process.C_Flatrate_Term_Extend' ,Updated=TO_TIMESTAMP('2016-11-02 13:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ClassName like 'de.metas.flatrate.process.C_Flatrate%Term_Extend'
;
UPDATE AD_Process SET Value='C_Flatrate_Term_Extend' WHERE Value='C_Flatrate_Term_Extend';
;
UPDATE AD_Process SET Classname='de.metas.flatrate.process.C_Flatrate_Term_Prepare_Closing', Value='C_Flatrate_Term_Prepare_Closing',Updated=TO_TIMESTAMP('2016-11-02 13:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE ClassName='de.metas.flatrate.process.C_FlatrateTerm_Prepare_Closing'
;

