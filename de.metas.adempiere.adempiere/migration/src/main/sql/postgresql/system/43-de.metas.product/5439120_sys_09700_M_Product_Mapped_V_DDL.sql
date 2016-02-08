

DROP VIEW If EXISTS M_Product_Mapped_V;
CREATE VIEW M_Product_Mapped_V AS
SELECT 
	p.AD_Client_ID, 
	p.AD_Org_ID,
	p.Created,
	p.CreatedBy,
	p.IsActive,
	p.Updated,
	p.UpdatedBy,
	p.M_Product_ID,
	p.M_Product_ID AS M_Product_Mapped_V_ID,
	m.M_Product_Mapping_ID
FROM M_Product_Mapping m
	JOIN M_Product p ON p.M_Product_Mapping_ID=m.M_Product_Mapping_ID
WHERE true
	AND m.IsActive='Y';
