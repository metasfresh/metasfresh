
-- lazy-load a number of SQL columns that are just for display

-- C_Invoice_Candidate.Bill_BPartner_Name
-- 2018-06-04T10:27:28.002
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2018-06-04 10:27:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552287
;

--C_Order_BPartner
-- 2018-06-04T10:29:48.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2018-06-04 10:29:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551953
;

--IsToRecompute
-- 2018-06-04T10:31:09.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2018-06-04 10:31:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544924
;

--M_Product_Category_ID
-- 2018-06-04T10:31:45.227
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2018-06-04 10:31:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552289
;
--Processing
-- 2018-06-04T10:32:36.127
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2018-06-04 10:32:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552023
;

--ProductType
-- 2018-06-04T10:33:09.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2018-06-04 10:33:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552288
;

-- TotalOfOrder
-- 2018-06-04T10:34:23.146
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2018-06-04 10:34:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551284
;

--TotalOfOrderExcludingDiscount
-- 2018-06-04T10:34:41.334
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2018-06-04 10:34:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551763
;

CREATE INDEX IF NOT EXISTS c_flatrate_term_Bill_BPartner_ID
   ON public.c_flatrate_term (bill_bpartner_id ASC NULLS LAST);
COMMENT ON INDEX public.c_flatrate_term_bill_bpartner_id
  IS 'supposed to improve the speed of FlatrateDAO.retrieveTerms(I_C_Invoice_Candidate)';
