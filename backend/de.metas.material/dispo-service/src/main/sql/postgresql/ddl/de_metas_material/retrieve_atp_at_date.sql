
DROP FUNCTION IF EXISTS de_metas_material.retrieve_atp_at_date(timestamp with time zone);
CREATE FUNCTION de_metas_material.retrieve_atp_at_date(IN p_date timestamp with time zone)
RETURNS TABLE(
	M_Product_ID numeric, 
	M_Warehouse_ID numeric, 
	C_BPartner_Customer_ID numeric, 
	StorageAttributesKey character varying, 
	DateProjected timestamp with time zone,
	SeqNo numeric,
	Qty numeric) AS
$BODY$
	SELECT DISTINCT ON (M_Product_ID, M_Warehouse_ID, C_BPartner_Customer_ID, StorageAttributesKey)
		M_Product_ID, M_Warehouse_ID, C_BPartner_Customer_ID, StorageAttributesKey, DateProjected, SeqNo, Qty
	FROM MD_Candidate 
	WHERE IsActive='Y' AND MD_Candidate_Type='STOCK' and DateProjected <= p_date and md_candidate_status <> 'simulated'
	ORDER BY M_Product_ID, M_Warehouse_ID, C_BPartner_Customer_ID, StorageAttributesKey, Dateprojected DESC, SeqNo DESC
$BODY$
  LANGUAGE sql STABLE;
COMMENT ON FUNCTION de_metas_material.retrieve_atp_at_date(timestamp with time zone)
  IS 'Note that the Qtys can be from MD_Candidates whose DateProjected is before the given p_date, if they were not yet superseeded by more recent values.
  Please keep this function in sync with the AD_Table named MD_Candidate_ATP_QueryResult and also with the function retrieve_atp_at_date_debug';
  
DROP INDEX IF EXISTS md_candidate_stock_v_perf;
CREATE INDEX md_candidate_stock_v_perf
  ON public.md_candidate
  USING btree
  (M_Product_ID, M_Warehouse_ID, C_BPartner_Customer_ID, StorageAttributesKey, Dateprojected DESC, SeqNo DESC, Qty)
  WHERE isactive = 'Y' AND md_candidate_type = 'STOCK';
COMMENT ON INDEX public.md_candidate_stock_v_perf
  IS 'This index has the purpose of supporting the function de_metas_material.retrieve_atp_at_date 
in finding the latest DateProjected for a given product-id, warehouse-id, partner-id and StorageAttributesKey-(like-)expression.

Note: the Qty column is in so that hopefully all this can be done by the DBMS using index-only-scans.';
