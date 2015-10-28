
DROP VIEW IF EXISTS "de.metas.fresh".PP_Product_Bom_And_Component;

CREATE OR REPLACE VIEW "de.metas.fresh".PP_Product_Bom_And_Component AS
SELECT 
	-- bom (header) stuff
	b.PP_Product_Bom_ID, 
	
	-- the BOM-product, i.e. the "end-result"
	b.M_Product_ID AS b_M_Product_ID, b_p.Value as b_p_Value, b_p.Name as b_p_Name,
	
	--  the "end-result's" ASI; don't return null but 0 if there is no ASI, because we'll want to use '=' with it
	COALESCE(b.M_AttributeSetInstance_ID, 0)::numeric(10,0) AS b_M_AttributeSetInstance_ID,
	
	"de.metas.dimension".DIM_Get_GroupName('MRP_Product_Info_ASI_Values', GenerateHUStorageASIKey(b.M_AttributeSetInstance_ID)) AS b_M_AttributeSetGroupNames,
	
	-- bomline stuff, i.e. the components or packing material
	bl_p.M_Product_ID AS bl_M_Product_ID, bl_p.Value as bl_p_Value, bl_p.Name as bl_p_Name,
	bl.M_AttributeSetInstance_ID AS bl_M_AttributeSetInstance_ID,
	bl.QtyBatch, bl.QtyBom,
	bl_p.IsPurchased,
	
	-- factors to calculate the required qty of the package/component product (including UOM conversion!) for a given qty of the BOM product
	CASE
		WHEN bl.IsQtyPercentage='Y' 
		THEN bl.QtyBatch/100
		ELSE bl.QtyBom
	END AS bl_Factor_BOM,
	CASE
		WHEN bl.IsQtyPercentage='Y' 
		THEN COALESCE(conv_b_bl.MultiplyRate,1)
		ELSE 1
	END AS bl_Factor_b_To_bl,
	conv_bl_p.MultiplyRate AS bl_Factor_bl_To_Product

FROM PP_Product_Bom b
	JOIN M_Product b_p ON b_p.M_Product_ID=b.M_Product_ID
	JOIN PP_Product_BomLine bl ON bl.PP_Product_Bom_ID=b.PP_Product_Bom_ID
			JOIN M_Product bl_p ON bl_p.M_Product_ID=bl.M_Product_ID
	
	-- UOM conversion from the bom's UOM (e.g. Stk) to the UOM of the bom-line (e.g. Kg)
	LEFT JOIN C_UOM_Conversion_V conv_b_bl ON conv_b_bl.M_Product_ID=b.M_Product_ID
		AND conv_b_bl.C_UOM_From_ID=b.C_UOM_ID
		AND conv_b_bl.C_UOM_To_ID=bl.C_UOM_ID
		
	-- UOM conversion from the bom-line's UOM (e.g. mm) to the UOM of the bom-line's product (e.g. Rolle)
	JOIN C_UOM_Conversion_V conv_bl_p ON conv_bl_p.M_Product_ID=bl.M_Product_ID
		AND conv_bl_p.C_UOM_From_ID=COALESCE(bl.C_UOM_ID, bl_p.C_UOM_ID) -- cover the case that bomline-uom is empty
		AND conv_bl_p.C_UOM_To_ID=bl_p.C_UOM_ID
WHERE true
	AND b.IsActive='Y' AND bl.IsActive='Y' AND bl_p.IsActive='Y'
	AND bl.ComponentType IN ('CO','PK') -- only components and packing materials

--	AND b_p.Value='P000787'-- "AB Alicesalat 250g";"Alicesalat, endproduct", Stk
--	AND bl_p.Value='P000328' --Frisee, purchased, raw matrerial, kg
;

COMMENT ON VIEW "de.metas.fresh".PP_Product_Bom_And_Component IS 'task 08682: added this view in the attempt to make the view MRP_ProductInfo_Poor_Mans_MRP_V more "tidy". 
But note that the view is also used in the view M_Product_ID_M_AttributeSetInstance_ID_V.'
;
