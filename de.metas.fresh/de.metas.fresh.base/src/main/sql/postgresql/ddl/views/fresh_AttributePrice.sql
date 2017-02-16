--DROP VIEW IF EXISTS rv_fresh_pricelist ;
--DROP VIEW IF EXISTS rv_fresh_pricelist_comparison;
--DROP VIEW IF EXISTS report.fresh_AttributePrice;

CREATE OR REPLACE VIEW report.fresh_AttributePrice AS 
SELECT 	
	pp.M_ProductPrice_ID, 
	ai.m_attributesetinstance_id,
	pp.PriceStd, 
	pp.IsActive, 
	pp.M_HU_PI_Item_Product_ID, 

	string_agg ( av.value , ', ' ORDER BY av.value ) AS Attributes,
	
	/** Create a column, that allows us to identify Attribute prices with the same AttributeSet 
	 *  That column will be filled with an empty string if all relevant data is null. this is important to prevent 
	 *  attribute prices to be compared with regular prices */
	(
		COALESCE( pp.M_HU_PI_Item_Product_ID || ' ', '' ) ||
		COALESCE( pp.isDefault || ' ', '' ) || 
		COALESCE( string_agg( ai.M_Attribute_ID::text ||' '|| ai.M_Attributevalue_ID::text, ',' ORDER BY ai.M_Attribute_ID) , '')
	) AS signature 
FROM 	
	M_ProductPrice pp
	--LEFT OUTER JOIN M_ProductPrice_Attribute_Line ppal ON ppa.M_ProductPrice_Attribute_ID = ppal.M_ProductPrice_Attribute_ID AND ppal.isActive = 'Y'
	LEFT OUTER JOIN M_AttributeInstance ai ON pp.m_attributesetinstance_id = ai.m_attributesetinstance_id AND ai.isActive = 'Y'
	LEFT OUTER JOIN (
		SELECT	av.isActive, av.M_Attributevalue_ID,
			CASE
				WHEN a.Value = '1000015' AND av.value = '01' THEN NULL -- ADR & Keine/Leer
				WHEN a.Value = '1000015' AND av.value IS NOT NULL THEN 'AdR' -- ADR
				WHEN a.Value = '1000001' THEN av.value -- Herkunft
				ELSE av.name 
			END as value
		FROM	M_Attributevalue av 
			LEFT OUTER JOIN M_Attribute a ON av.M_Attribute_ID = a.M_Attribute_ID
	) av ON ai.M_Attributevalue_ID = av.M_Attributevalue_ID AND av.IsActive = 'Y' AND av.value IS NOT NULL
WHERE 	
	pp.IsActive = 'Y'
GROUP BY 
	pp.M_ProductPrice_ID, pp.PriceStd, pp.IsActive, pp.M_HU_PI_Item_Product_ID
;


COMMENT ON VIEW report.fresh_AttributePrice IS 'This View is supposed to be used by the View RV_fresh_PriceList_Comparison and retrieves the Attribute price together with a rendered list of Attributes and a signature. Attribute prices of different Price list version but with the same attributes and packin instruction config can be matched and therefore compared with this.';

