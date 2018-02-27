DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Source_of_supply ( IN Record_ID numeric, IN AD_Language Character Varying (6) );


CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Source_of_supply ( IN Record_ID numeric, IN AD_Language Character Varying (6) )
RETURNS TABlE 
(
	org_addressline text,
	adressblock character varying(360),
	bp_value character varying(40),
	documentno character varying(30),
	movementdate date,
	product_name character varying(255),
	product_value character varying(40),
	lotno text,
	BestBeforeDate text,
	sub_producer character varying(60),
	total_qty numeric
)
AS
$$
SELECT 
(
		SELECT
			trim(
				COALESCE ( org_bp.name || ' | ', '' ) ||
				COALESCE ( loc.address1 || ' | ', '' ) ||
				COALESCE ( loc.postal || ' ', '' ) ||
				COALESCE ( loc.city, '' )
			)as org_addressline
		FROM
			c_bpartner org_bp
			JOIN c_bpartner_location org_bpl 	ON org_bp.c_bpartner_id	= org_bpl.c_bpartner_id AND org_bpl.isActive = 'Y'
			JOIN c_location loc	 		ON org_bpl.c_location_id	= loc.c_location_id AND loc.isActive = 'Y'
		WHERE
			org_bp.ad_orgbp_id = io.ad_org_id AND org_bp.isActive = 'Y'
			and org_bpl.isbillto = 'Y'
		LIMIT 1
	)as org_addressline,
	
	io.BPartnerAddress as adressblock,
	bp.value as bp_value,
	io.documentno,
	io.movementdate::date,
	COALESCE(pt.name, p.name) as product_name, 
	p.value as product_value,
	att.lotno,
	att.BestBeforeDate,
	sub_bp.name as sub_producer,
	sum(iol.qtyentered) as total_qty
	
	
FROM M_InOut io

INNER JOIN C_BPartner bp ON io.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
INNER JOIN M_InOutLine iol ON io.M_InOut_ID = iol.M_InOut_ID AND iol.isActive = 'Y'
INNER JOIN M_Product p ON iol.M_Product_ID = p.M_Product_ID AND p.isActive = 'Y'
LEFT JOIN M_Product_trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.isActive = 'Y' AND pt.ad_language=$2
LEFT JOIN LATERAL(
	SELECT 
		String_agg(ai.value, '') FILTER (WHERE a.Value = 'Lot-Nummer') AS lotno,
		String_agg(to_char(ai.valuedate, 'DD.MM.YYYY'), '') FILTER (WHERE a.Value = 'HU_BestBeforeDate') AS BestBeforeDate,
		String_agg(ai.valuenumber::character varying, '') FILTER (WHERE a.Value = 'SubProducerBPartner') AS SubProducerBPartner
		
	FROM m_attributesetinstance asi
	JOIN M_AttributeInstance ai ON ai.M_AttributeSetInstance_ID = asi.M_AttributeSetInstance_ID
	JOIN M_Attribute a ON ai.m_attribute_id = a.m_attribute_id
	WHERE iol.m_attributesetinstance_id = asi.m_attributesetinstance_id


) att ON TRUE



LEFT JOIN C_BPartner sub_bp ON att.SubProducerBPartner::numeric = sub_bp.c_bpartner_id AND sub_bp.isActive = 'Y'


WHERE io.m_inout_id = $1

group by org_addressline,
	io.BPartnerAddress,
	bp.value,
	io.documentno,
	io.movementdate,
	p.name, pt.name, 
	p.value,
	att.lotno,
	att.BestBeforeDate,
	sub_bp.name
	

$$
LANGUAGE sql STABLE
;