

INSERT INTO M_AttributeInstance
	(
		m_attributesetinstance_id ,
		m_attribute_id ,
		ad_client_id ,
		ad_org_id,
		
		isactive,
		created,
		createdby,
		updated,
		updatedby,
		
		m_attributevalue_id ,
		value ,
		valuenumber ,
		valuedate 
	)


SELECT
	
		y.IL_ASI_ID,
		y.m_attribute_id ,
		y.ad_client_id ,
		y.ad_org_id,
		
		y.isactive,
		y.created,
		y.createdby,
		y.updated,
		y.updatedby,
		
		y.m_attributevalue_id ,
		y.value ,
		y.valuenumber ,
		y.valuedate 
	

	FROM

(
SELECT
		DISTINCT il.M_AttributeSetInstance_ID AS IL_ASI_ID,
		x.C_Orderline_ID,
		x.m_attributesetinstance_id ,
		x.m_attribute_id ,
		il.ad_client_id ,
		il.ad_org_id ,
		
		'Y' AS IsActive,
		now() AS Created,
		99 AS CreatedBy,
		now() AS Updated,
		99 AS UpdatedBy,
		
		x.m_attributevalue_id ,
		x.value ,
		0 AS valuenumber ,
		null:: timestamp without time zone AS valuedate

FROM 
	C_InvoiceLine il
	
	JOIN
	(	SELECT	
			ol.C_Orderline_ID,
			asi.M_AttributeSetInstance_ID,
			a.M_Attribute_ID,
			asi.AD_Client_ID,
			asi.AD_Org_ID,
			
			aval.M_AttributeValue_ID,
			aval.Value

		
		FROM	
		C_OrderLine ol
		JOIN C_Order o on ol.C_Order_ID = o.c_order_ID
		JOIN C_BPartner bp on o.C_BPartner_ID = bp.C_BPartner_ID
		JOIN M_Product p on ol.M_Product_ID = p.M_Product_ID
		JOIN M_Product_Category pc on p.M_Product_Category_ID = pc.M_Product_Category_ID
		JOIN M_AttributeSet aset ON  (p.M_AttributeSet_ID= aset.M_AttributeSet_ID OR pc.M_AttributeSet_ID= aset.M_AttributeSet_ID)
		JOIN M_AttributeUse au ON au.M_AttributeSet_ID= aset.M_AttributeSet_ID
		JOIN M_Attribute a ON a.M_Attribute_ID = au.M_Attribute_ID
		JOIN M_AttributeValue aval ON a.M_Attribute_ID = aval.M_Attribute_ID AND bp.Fresh_AdRVendorRegion = aval.Value


		JOIN M_AttributeSetInstance asi on ol.M_AttributeSetInstance_ID = asi.M_AttributeSetInstance_ID
	
		WHERE 
			a.M_Attribute_ID = (SELECT Value FROM  AD_SysConfig  where Name = 'de.metas.fresh.ADRAttribute'):: numeric
			AND o.IsSoTrx = 'N' 
			AND bp.isADRVendor = 'Y' 
	) x
	
	ON il.C_Orderline_ID = x.C_Orderline_ID
	
WHERE (NOT EXISTS (SELECT 1 FROM M_AttributeInstance ai  WHERE ai.M_Attribute_ID = x.M_Attribute_ID AND ai.M_AttributeSetInstance_ID = x.M_AttributeSetInstance_ID))


)y

WHERE (NOT EXISTS (SELECT 1 FROM M_AttributeInstance ai  WHERE ai.M_Attribute_ID = y.M_Attribute_ID AND ai.M_AttributeSetInstance_ID = y.IL_ASI_ID))