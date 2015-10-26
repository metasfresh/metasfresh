
DROP FUNCTION IF EXISTS  "de.metas.fresh".X_MRP_ProductInfo_Detail_Update_Poor_Mans_MRP(date, numeric, numeric);
CREATE OR REPLACE FUNCTION "de.metas.fresh".X_MRP_ProductInfo_Detail_Update_Poor_Mans_MRP(IN DateFrom date, IN M_Product_ID numeric, IN M_AttributesetInstance_ID numeric) 
	RETURNS VOID AS
$BODY$

	UPDATE X_MRP_ProductInfo_Detail_MV 
	SET fresh_qtymrp=0
	WHERE DateGeneral::date = $1 AND M_Product_ID = $2 AND ASIKey = GenerateHUStorageASIKey($3,'')
	;

	UPDATE X_MRP_ProductInfo_Detail_MV mv
	SET 
		fresh_qtymrp = CEIL(Poor_Mans_MRP_Purchase_Qty),
		IsFallback='N'
	FROM (
		-- to get our data,
		-- join the "end-product" mv line with the poor man's MRP record's end-product (b_m_Product_ID) column, and multiply the "end-product" mv's QtyOrdered_Sale_OnDate with the BOM-line's factor
		-- that way way get the Qtys needed for the raw materials
		SELECT 
			COALESCE(v_exact.bl_M_Product_ID, v_fallback.bl_M_Product_ID) as bl_M_Product_ID, 
			GenerateHUStorageASIKey(COALESCE(v_exact.b_M_AttributeSetInstance_ID, v_fallback.b_M_AttributeSetInstance_ID),'') as ASIKey, 
			mv2.DateGeneral,
			SUM(mv2.QtyOrdered_Sale_OnDate * COALESCE(v_exact.Factor_Recursive, v_fallback.Factor_Recursive)) as Poor_Mans_MRP_Purchase_Qty
			
		FROM X_MRP_ProductInfo_Detail_MV mv2
			-- join the "end-product" mv line with the poor man's MRP record's end-product (b_m_Product_ID)
			LEFT JOIN "de.metas.fresh".MRP_ProductInfo_Poor_Mans_MRP_V($2, $3) v_exact 
				ON mv2.M_Product_ID=v_exact.b_m_Product_ID
					AND mv2.ASIKey = GenerateHUStorageASIKey(v_exact.b_M_AttributeSetInstance_ID,'') 
					AND v_exact.IsPurchased='Y'

			LEFT JOIN "de.metas.fresh".MRP_ProductInfo_Poor_Mans_MRP_V($2, $3) v_fallback 
				ON mv2.M_Product_ID=v_fallback.b_m_Product_ID
					AND v_exact.bl_m_Product_ID IS NULL -- it is important to only join v_fallback, if there was no v_exact to be found. Otherwise we would join one v_fallback for each bomline-product 
					AND GenerateHUStorageASIKey(v_fallback.b_M_AttributeSetInstance_ID,'')=''
					AND v_fallback.IsPurchased='Y'

		WHERE true
			AND COALESCE(v_exact.IsPurchased,v_fallback.IsPurchased)='Y'
			AND mv2.IsFallback='N' -- we can exclude endproducts of fallback records, because they don't have a reserved qty, so they don't contribute to any raw material's Poor_Mans_MRP_Purchase_Qty
			AND mv2.DateGeneral::date = $1 AND mv2.M_Product_ID = $2 AND mv2.ASIKey = GenerateHUStorageASIKey($3,'')
		GROUP BY 
			COALESCE(v_exact.bl_M_Product_ID, v_fallback.bl_M_Product_ID), 
			GenerateHUStorageASIKey(COALESCE(v_exact.b_M_AttributeSetInstance_ID, v_fallback.b_M_AttributeSetInstance_ID),''), 
			mv2.DateGeneral
	) data
	WHERE true
		AND data.bl_M_Product_ID = mv.M_Product_ID -- note that we join the bom*line*product, because that's the one whose line we want to update
		AND data.ASIKey = mv.ASIKey
		AND data.DateGeneral=mv.DateGeneral 
		-- AND mv.IsFallback='N' there is no reason not to update records that were supplemented by X_MRP_ProductInfo_Detail_Fallback_V
	;
$BODY$
LANGUAGE sql VOLATILE;
