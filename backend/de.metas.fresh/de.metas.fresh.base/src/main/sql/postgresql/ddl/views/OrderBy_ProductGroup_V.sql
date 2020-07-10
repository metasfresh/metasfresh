
DROP VIEW IF EXISTS "de.metas.fresh".OrderBy_ProductGroup_V;
CREATE OR REPLACE VIEW "de.metas.fresh".OrderBy_ProductGroup_V AS
SELECT 
	p.M_Product_ID, 
	p.Name AS OrderBy_ProductName,
	CASE 
		WHEN pc.Name ilike 'Früchte' THEN '02_Fruits'
		WHEN pc.Name ilike 'Kartoffeln' THEN '03_Potatos'
		ELSE '01_Rest'
	END AS OrderBy_ProductGroup
FROM M_Product p
	JOIN M_Product_Category pc ON pc.M_Product_Category_ID=p.M_Product_Category_ID
;
COMMENT ON VIEW "de.metas.fresh".OrderBy_ProductGroup_V IS 'see task 08924'
;
