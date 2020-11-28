
CREATE VIEW M_HU_Stock_Detail_V as
SELECT 
	hu.M_HU_ID, 
	hu.M_Locator_ID, 
	hu.C_BPartner_ID, 
	hu.HUStatus,
	hus.M_Product_ID, 
	hus.Qty, 
	hus.C_UOM_ID,
	hua.M_Attribute_ID,
	hua.Value as AttributeValue,
	
	hu.AD_client_ID,
	hu.AD_Org_ID,
	hu.Created,
	hu.Createdby,
	hu.IsActive,
	hu.Updated,
	hu.Updatedby,
	
	hus.M_HU_Storage_ID,
	hua.M_HU_Attribute_ID
	
FROM M_HU hu
	JOIN M_HU_Storage hus ON hus.M_HU_ID=hu.M_HU_ID AND hus.IsActive='Y'
	LEFT JOIN M_HU_Attribute hua ON hua.M_HU_ID=hu.M_HU_ID AND hua.IsActive='Y'
WHERE hu.IsActive='Y'
	AND hu.HuStatus NOT IN ('D', 'P', 'E') -- all "physical" states (i.e. not destroyed, planned, shipped); please keep in sync with IHUStatusBL
	AND hu.M_HU_Item_Parent_ID IS NULL -- only top level HUs
;

