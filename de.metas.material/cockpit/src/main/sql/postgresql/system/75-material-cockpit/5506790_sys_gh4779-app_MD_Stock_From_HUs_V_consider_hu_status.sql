

DROP VIEW IF EXISTS MD_Stock_From_HUs_V;
CREATE VIEW MD_Stock_From_HUs_V AS
SELECT 
	hu.AD_Client_ID,
	hu.AD_Org_ID,
	l.M_Warehouse_ID,
	hus.M_Product_ID,
	hus.C_UOM_ID,
	GenerateHUAttributesKey(hu.m_hu_id) as AttributesKey,
	SUM(hus.Qty) as QtyOnHand
FROM m_hu hu
	JOIN M_HU_Storage hus ON hus.M_HU_ID = hu.M_HU_ID
	JOIN M_Locator l ON l.M_Locator_ID=hu.M_Locator_ID
WHERE hu.isactive='Y'
	and hu.M_HU_Item_Parent_ID IS NULL
	
	/*please keep in sync with de.metas.handlingunits.IHUStatusBL.isPhysicalHU(I_M_HU)*/
	and hu.HuStatus NOT IN ('P'/*Planning*/,'D'/*Destroyed*/,'E'/*Shipped*/) 
GROUP BY 
	hu.AD_Client_ID,
	hu.AD_Org_ID,
	l.M_Warehouse_ID,
	hus.M_Product_ID,
	hus.C_UOM_ID,
	GenerateHUAttributesKey(hu.m_hu_id)
;
COMMENT ON VIEW MD_Stock_From_HUs_V IS 
'This view is used by the process MD_Stock_Reset_From_M_HUs to intitialize or reset the MD_stock table

Belongs to issue "Show onhand quantity in new WebUI MRP Product Info Window" https://github.com/metasfresh/metasfresh-webui-api/issues/762';

