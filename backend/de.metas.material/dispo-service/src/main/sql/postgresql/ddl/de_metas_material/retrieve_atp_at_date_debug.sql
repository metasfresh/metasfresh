
DROP FUNCTION IF EXISTS de_metas_material.retrieve_atp_at_date_debug(timestamp with time zone);
CREATE FUNCTION de_metas_material.retrieve_atp_at_date_debug(IN p_date timestamp with time zone)
RETURNS TABLE(
	M_Product_ID numeric, 
	M_Warehouse_ID numeric, 
	C_BPartner_Customer_ID numeric, 
	StorageAttributesKey character varying, 
	SeqNo numeric,
	Qty numeric, 
	MD_Candidate_ID numeric,
	Dateprojected timestamp with time zone) AS
$BODY$
	SELECT DISTINCT ON (M_Product_ID, M_Warehouse_ID, C_BPartner_Customer_ID, StorageAttributesKey)
		M_Product_ID, M_Warehouse_ID, C_BPartner_Customer_ID, StorageAttributesKey, SeqNo, Qty, MD_Candidate_ID, Dateprojected
	FROM MD_Candidate 
	WHERE IsActive='Y' AND MD_Candidate_Type='STOCK' and DateProjected <= p_date and md_candidate_status <> 'simulated'
	ORDER BY M_Product_ID, M_Warehouse_ID, C_BPartner_Customer_ID, StorageAttributesKey, Dateprojected DESC, SeqNo DESC
$BODY$
  LANGUAGE sql STABLE;
COMMENT ON FUNCTION de_metas_material.retrieve_atp_at_date_debug(timestamp with time zone)
  IS 'Like de_metas_material.retrieve_atp_at_date, but returns additional columns that are not part of the index.
Therefore the performance is worse, but the function might be useful to find out what''s going on';
