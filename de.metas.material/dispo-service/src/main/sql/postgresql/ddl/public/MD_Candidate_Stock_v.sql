
CREATE VIEW public.MD_Candidate_Stock_v AS 
SELECT DISTINCT ON (M_Product_ID, StorageAttributesKey, M_Warehouse_ID)
	M_Product_ID,
	StorageAttributesKey,
	M_Warehouse_ID,
	DateProjected,
	Qty
FROM MD_Candidate WHERE IsActive='Y' /*phew, now we also need isactive in the index, right? or maybe just a whereclause*/
ORDER BY     
	M_Product_ID,
	StorageAttributesKey,
    M_Warehouse_ID,
    Dateprojected DESC
;
