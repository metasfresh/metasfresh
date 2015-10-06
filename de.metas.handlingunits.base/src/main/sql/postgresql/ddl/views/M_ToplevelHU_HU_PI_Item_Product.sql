
DROP VIEW IF EXISTS "de.metas.handlingunits".M_ToplevelHU_HU_PI_Item_Product;
CREATE OR REPLACE VIEW "de.metas.handlingunits".M_ToplevelHU_HU_PI_Item_Product
AS
SELECT  
	isTopLevel, M_HU_ID, M_product_ID, Name,
	SUM( Qty ) AS Qty
FROM
	(
	SELECT
		p_hu.M_HU_ID, 
		p_hu.M_HU_Item_Parent_ID IS NULL isTopLevel,
		COALESCE( c_hu_i_s.M_Product_ID, p_hu_i_s.M_Product_ID ) AS M_Product_ID, 
		COALESCE( c_hu_piip.Name, p_hu_piip.Name ) AS Name,
		COALESCE( c_hu_i_s.Qty, p_hu_i_s.Qty, 0 ) AS Qty
	FROM
		-- Parent HU
		M_HU p_hu
		LEFT OUTER JOIN M_HU_Item p_hu_i ON p_hu_i.M_HU_ID = p_hu.M_HU_ID
		LEFT OUTER JOIN M_HU_Item_Storage p_hu_i_s ON p_hu_i.M_HU_Item_ID = p_hu_i_s.M_HU_Item_ID
		LEFT OUTER JOIN M_HU_PI_Item p_hu_pii ON p_hu_pii.M_HU_PI_Item_ID = p_hu_i.M_HU_PI_Item_ID
		LEFT OUTER JOIN M_HU_PI_Item_Product p_hu_piip ON p_hu_piip.M_HU_PI_Item_Product_ID = p_hu.M_HU_PI_Item_Product_ID 
		-- Child HU
		LEFT OUTER JOIN M_HU c_hu ON c_hu.M_HU_Item_Parent_ID = p_hu_i.M_HU_Item_ID
		LEFT OUTER JOIN M_HU_Item c_hu_i ON c_hu_i.M_HU_ID = c_hu.M_HU_ID
		LEFT OUTER JOIN M_HU_Item_Storage c_hu_i_s ON c_hu_i_s.M_HU_Item_ID = c_hu_i.M_HU_Item_ID
		LEFT OUTER JOIN M_HU_PI_Item c_hu_pii ON c_hu_pii.M_HU_PI_Item_ID = c_hu_i.M_HU_PI_Item_ID
		LEFT OUTER JOIN M_HU_PI_Item_Product c_hu_piip ON c_hu_piip.M_HU_PI_Item_Product_ID = c_hu.M_HU_PI_Item_Product_ID 
	WHERE 
		true 
		-- Filter handling units without a matching M_HU_PI_Item_Product
		AND COALESCE( c_hu_piip.name, p_hu_piip.name, ''::character varying )::text <> ''::text 
		-- Filter handling units without M_HU_Item_Storage
		AND COALESCE( c_hu_i_s.M_HU_Item_Storage_ID, p_hu_i_s.M_HU_Item_Storage_ID, 0::numeric ) <> 0::numeric
		-- Select only those that have Children - to filter out VHUs
		AND EXISTS( SELECT 0 FROM M_HU WHERE M_HU_Item_Parent_ID = p_hu_i.M_HU_Item_ID )
	) data
GROUP BY
	M_HU_ID, M_Product_ID, Name, IsTopLevel
;

COMMENT ON VIEW "de.metas.handlingunits".M_ToplevelHU_HU_PI_Item_Product IS '
*task 07761 Finish Wareneingangsetikett Packvorschrift (107763499838)
*Note that we don''t distinguish between LU and TU, but between parent-HU and child-HU; that''s because in the case of paloxe, there is no LU.
*Also note that we don''t use LUTU_config. Instead we see thuis HU-Items there are and which ones have HU_PI_Item_Product that matches the HU_Storage''s product
';
