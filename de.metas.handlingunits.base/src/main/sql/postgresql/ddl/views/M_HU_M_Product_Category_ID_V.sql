
DROP VIEW IF EXISTS "de.metas.handlingunits".M_HU_M_Product_Category_ID_V;
CREATE OR REPLACE VIEW "de.metas.handlingunits".M_HU_M_Product_Category_ID_V AS
SELECT 
	hu.M_HU_ID, 
	CASE 
		WHEN array_length(array_agg(p.M_Product_Category_ID),1)!=1 THEN NULL 
		ELSE max(p.M_Product_Category_ID) 
	END AS M_Product_Category_ID
FROM M_HU hu
	INNER JOIN M_HU_Storage hus ON hu.M_HU_ID=hus.M_HU_ID
	INNER JOIN M_Product p ON p.M_Product_ID=hus.M_Product_ID
GROUP BY hu.M_HU_ID
;
COMMENT ON VIEW "de.metas.handlingunits".M_HU_M_Product_Category_ID_V IS 
	'task 08420: shows the M_Product_Category_ID of the products that are in the respectinve HU''s M_HU_Storage, or NULL if there are zero product categories or more than one. Supposed to be used for searching and filtering.';
	