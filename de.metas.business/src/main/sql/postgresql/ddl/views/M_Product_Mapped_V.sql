
DROP VIEW If EXISTS M_Product_Mapped_V;
CREATE VIEW M_Product_Mapped_V AS
SELECT 
DISTINCT
	p.M_Product_Mapping_ID,
	p2.M_Product_ID,
	p2.M_Product_ID AS M_Product_Mapped_V_ID,
	p2.AD_Client_ID, 
	p2.AD_Org_ID,
	p2.Created,
	p2.CreatedBy,
	p2.IsActive,
	p2.Updated,
	p2.UpdatedBy
FROM 
	M_Product p 
	JOIN M_Product p2 ON p2.M_Product_Mapping_ID=p.M_Product_Mapping_ID
	
	/* only joining M_Product_Mapping because we want to exclude inactive mappings */
	JOIN M_Product_Mapping m ON m.M_Product_Mapping_ID=p.M_Product_Mapping_ID 
WHERE true
	AND m.IsActive='Y';
