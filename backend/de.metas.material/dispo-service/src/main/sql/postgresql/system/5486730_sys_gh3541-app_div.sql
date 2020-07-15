--
-- order MD_Candidate records by Date DESC, SeqNo DESC, GroupId
--
-- 2018-02-24T20:48:36.108
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=NULL,Updated=TO_TIMESTAMP('2018-02-24 20:48:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563010
;

-- 2018-02-24T20:48:57.382
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=NULL,Updated=TO_TIMESTAMP('2018-02-24 20:48:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563011
;

-- 2018-02-24T20:49:03.405
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=-2.000000000000,Updated=TO_TIMESTAMP('2018-02-24 20:49:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558151
;

-- 2018-02-24T20:49:11.011
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=3.000000000000,Updated=TO_TIMESTAMP('2018-02-24 20:49:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558148
;

-- 2018-02-24T20:49:31.174
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET SortNo=-1.000000000000,Updated=TO_TIMESTAMP('2018-02-24 20:49:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=558126
;

--
-- create a supporting index for Qty_AvailableToPromise
--
CREATE INDEX IF NOT EXISTS md_candidate_md_candidate_parent_id
  ON public.md_candidate
  USING btree
  (md_candidate_parent_id)
  WHERE isactive = 'Y'::bpchar AND md_candidate_type::text = 'STOCK'::text;
COMMENT ON INDEX public.md_candidate_stock_v_perf
  IS 'This index has the purpose of supporting the sql column Qty_AvailableToPromise which selects the STOCK childrent of a given record.';
  
--
-- revise the MD_Candidate_Stock view
--

DROP VIEW IF EXISTS public.MD_Candidate_Stock_v;
CREATE OR REPLACE VIEW public.MD_Candidate_Stock_v AS 
SELECT
	main.M_Product_ID,
	main.M_Warehouse_ID,
	main.C_BPartner_ID,
	main.DateProjected,
	stockPerDate.StorageAttributesKey,
	stockPerDate.Qty
FROM 
	( /* "main": 
	    * get the different product, warehouse and dateProjected values (with the resp. largest/latest SeqNo)
		* the rest is then joined to this */
		SELECT DISTINCT ON (M_Product_ID, M_Warehouse_ID, C_BPartner_ID, DateProjected)
			M_Product_ID, M_Warehouse_ID, C_BPartner_ID, DateProjected, SeqNo
		FROM MD_Candidate 
		WHERE IsActive='Y' AND MD_Candidate_Type='STOCK'
		/* order in accordance to our index */
		ORDER BY M_Product_ID, M_Warehouse_ID, C_BPartner_ID, DateProjected DESC, SeqNo DESC	
	) main
	JOIN LATERAL 
	( /* "stockPerDate": for each combination from "main", join the latest stock records (with their different StorageAttributesKey) 
	     which are before/older than main */
		SELECT DISTINCT ON (M_Product_ID, M_Warehouse_ID, C_BPartner_ID, StorageAttributesKey)
			M_Product_ID, M_Warehouse_ID, C_BPartner_ID, StorageAttributesKey, 
			DateProjected, Qty
		FROM MD_Candidate innerCand
		WHERE /* these two conditions are in the whereclause of the index md_candidate_uc_stock */
			innerCand.IsActive='Y' AND innerCand.MD_Candidate_Type='STOCK'
			AND innerCand.M_Warehouse_ID = main.M_Warehouse_ID
			AND innerCand.M_Product_ID = main.M_Product_ID
			AND (innerCand.DateProjected < main.DateProjected
				OR (innerCand.DateProjected = main.DateProjected AND innerCand.SeqNo <= main.SeqNo))
		ORDER BY     
			M_Product_ID, M_Warehouse_ID, C_BPartner_ID, StorageAttributesKey, Dateprojected DESC, SeqNo DESC
	) stockPerDate ON true
;
COMMENT ON VIEW public.MD_Candidate_Stock_v 
IS 'For each distinct (DateProjected, M_Product_ID, M_Warehouse_ID) that occurs in any active STOCK MD_Candidate,
this view selects the different Qtys and StorageAttributesKeys for that date, product and warehouse.
Note that those Qtys and StorageAttributesKeys can be from MD_Candidate whose DateProjected is before the respective date, 
if they were not yet superseeded by more recent values';

DROP INDEX IF EXISTS md_candidate_stock_v_perf;
CREATE INDEX md_candidate_stock_v_perf
  ON public.md_candidate
  USING btree
  (M_Product_ID, M_Warehouse_ID, C_BPartner_ID, Dateprojected DESC, SeqNo DESC, storageattributeskey COLLATE pg_catalog."default", qty)
  WHERE isactive = 'Y'::bpchar AND md_candidate_type::text = 'STOCK'::text;
COMMENT ON INDEX public.md_candidate_stock_v_perf
  IS 'This index has the purpose of supporting the view MD_Candidate_Stock_v 
in finding the latest DateProjected for a given product-id, warehouse-id, partner-id and StorageAttributesKey-(like-)expression.

Note: the Qty column is in so that hopefully MD_Candidate_Stock_v can be done by the DBMS using index-only-scans.';

--
-- unrelated fix: make MD_Cockpit_DocumentDetail.QtyOrdered updatable
--
-- 2018-02-24T20:56:54.982
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='Y',Updated=TO_TIMESTAMP('2018-02-24 20:56:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558518
;

