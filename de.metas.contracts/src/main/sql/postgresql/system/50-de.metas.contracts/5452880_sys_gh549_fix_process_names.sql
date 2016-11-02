
--
-- the following 3 processes had typos in their value and class name- It's C_Flatrate_Term, not C_FlatrateTerm. 
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

