
CREATE VIEW de_metas_material_dispo.MD_Candidate_Latest_v AS 
SELECT DISTINCT ON (M_Product_ID, StorageattributesKey, M_Warehouse_ID)
	M_Product_ID,
	StorageattributesKey,
	M_Warehouse_ID,
	DateProjected,
	Qty
FROM MD_Candidate
ORDER BY     
	M_Product_ID,
	StorageattributesKey,
    M_Warehouse_ID,
    Dateprojected DESC
;
