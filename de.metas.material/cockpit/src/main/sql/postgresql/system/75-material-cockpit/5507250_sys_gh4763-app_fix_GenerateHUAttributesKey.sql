
CREATE OR REPLACE FUNCTION GenerateHUAttributesKey(huId numeric)
  RETURNS text AS
$BODY$
BEGIN
	RETURN (
		SELECT COALESCE(string_agg(sub.M_AttributeValue_ID::VARCHAR, '§&§'), '-1002'/*NONE*/)
		FROM (
			SELECT
				av.M_AttributeValue_ID AS M_AttributeValue_ID
			FROM M_HU hu
				INNER JOIN M_HU_Attribute hua ON hua.M_HU_ID=hu.M_HU_ID
				INNER JOIN M_Attribute a ON a.M_Attribute_ID=hua.M_Attribute_ID
				INNER JOIN M_AttributeValue av ON av.Value=hua.Value AND av.M_Attribute_ID=a.M_Attribute_ID
			WHERE hu.M_HU_ID = huId
				AND hua.IsActive='Y'
				AND av.IsActive='Y'
				AND a.IsActive='Y'
				AND a.IsStorageRelevant='Y'
			GROUP BY av.M_AttributeValue_ID
			ORDER BY av.M_AttributeValue_ID
		) sub
	);
END;
$BODY$
  LANGUAGE plpgsql STABLE
  COST 100;
COMMENT ON FUNCTION GenerateHUAttributesKey(numeric) IS
'This function is used to generate values for the MD_Stock.AttributesKey column when initializing or resetting the MD_Stock table. 
Please make sure it is in sync with the java implemention in AttributesKeys.createAttributesKeyFromASIStorageAttributes().
Note that we sort by M_AttributeValue_ID because that is what AttributesKey.ofAttributeValueIds() does.

Belongs to issue "Show onhand quantity in new WebUI MRP Product Info Window" https://github.com/metasfresh/metasfresh-webui-api/issues/762';

DROP VIEW IF EXISTS MD_Stock_From_HUs_V;
CREATE VIEW MD_Stock_From_HUs_V AS
SELECT 
	COALESCE(hu_agg.AD_Client_ID, s.AD_Client_ID) AS AD_Client_ID,
	COALESCE(hu_agg.AD_Org_ID, s.AD_Org_ID) AS AD_Org_ID,
	COALESCE(hu_agg.M_Warehouse_ID, s.M_Warehouse_ID) AS M_Warehouse_ID,
	COALESCE(hu_agg.M_Product_ID, s.M_Product_ID) AS M_Product_ID,
	COALESCE(hu_agg.C_UOM_ID, p.C_UOM_ID) AS C_UOM_ID,
	COALESCE(hu_agg.AttributesKey, s.AttributesKey) AS AttributesKey,
	COALESCE(hu_agg.QtyOnHand, 0) AS QtyOnHand,
	COALESCE(hu_agg.QtyOnHand, 0) - COALESCE(s.QtyOnHand, 0) AS QtyOnHandChange
FROM 
	MD_Stock s
	LEFT JOIN M_Product p ON p.M_Product_ID = s.M_Product_ID /*needed for its C_UOM_ID*/
	FULL OUTER JOIN 
	(
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
			and M_HU_Item_Parent_ID IS NULL

			/*please keep in sync with de.metas.handlingunits.IHUStatusBL.isPhysicalHU(I_M_HU)*/
			and hu.HuStatus NOT IN ('P'/*Planning*/,'D'/*Destroyed*/,'E'/*Shipped*/) 
		GROUP BY 
			hu.AD_Client_ID,
			hu.AD_Org_ID,
			l.M_Warehouse_ID,
			hus.M_Product_ID,
			hus.C_UOM_ID,
			GenerateHUAttributesKey(hu.m_hu_id)
	) hu_agg ON true
		AND hu_agg.AD_Client_ID = s.AD_Client_ID 
		AND hu_agg.AD_Org_ID=s.AD_Org_ID
		AND hu_agg.M_Warehouse_ID = s.M_Warehouse_ID
		AND hu_agg.M_Product_ID = s.M_Product_ID
		AND hu_agg.AttributesKey = s.AttributesKey
;
COMMENT ON VIEW MD_Stock_From_HUs_V IS 
'This view is used by the process MD_Stock_Reset_From_M_HUs to intitialize or reset the MD_stock table.
Note that due to the outer join, existing MD_Stock records that currently don''t have any HU-storage are also represented (with qty=0)
Belongs to issue "Show onhand quantity in new WebUI MRP Product Info Window" https://github.com/metasfresh/metasfresh-webui-api/issues/762';
