
DROP FUNCTION IF EXISTS report.fresh_ADR_umsatzliste_bpartner_report
	(
		IN Base_Period_Start date,
		IN Base_Period_End date, 
	
		IN issotrx character varying,
		IN C_BPartner_ID numeric, 
		
		IN M_AttributeSetInstance_ID numeric,
		
		IN AD_Language character varying
	);
	
DROP FUNCTION IF EXISTS report.fresh_ADR_umsatzliste_bpartner_report
	(
		IN Base_Period_Start date,
		IN Base_Period_End date, 
	
		IN issotrx character varying,
		IN C_BPartner_ID numeric, 
		
		IN M_AttributeSetInstance_ID numeric,
		
		IN AD_Language character varying,
		IN AD_Org_ID numeric
	);
DROP TABLE IF EXISTS report.fresh_ADR_umsatzliste_bpartner_report;

CREATE TABLE report.fresh_ADR_umsatzliste_bpartner_report
(
	productname character varying(250),
	sameperiodsum numeric,
	productcategory character varying(250),
	bpartnername character varying(250),
	attributes character varying(250),
	
	startdate character varying(250),
	enddate character varying(250),
	ad_org_id numeric
	
);


CREATE FUNCTION report.fresh_ADR_umsatzliste_bpartner_report
	(
		IN Base_Period_Start date,
		IN Base_Period_End date, 
		IN issotrx character varying,
		IN C_BPartner_ID numeric, 
		IN M_AttributeSetInstance_ID numeric,
		IN AD_Language character varying,
		IN AD_Org_ID numeric
	) 
	RETURNS SETOF report.fresh_ADR_umsatzliste_bpartner_report AS
$BODY$
SELECT
	
	 COALESCE(pt.name, p.name) as productname
	 ,sum (um.sameperiodsum) as sameperiodsum
	 ,bpp.productcategory 
	 ,COALESCE ((SELECT name FROM C_BPartner WHERE C_BPartner_ID = $4), 'alle' ) AS bpartnername
	 ,COALESCE ((SELECT String_Agg(ai_value, ', ' ORDER BY ai_Value) FROM Report.fresh_Attributes WHERE M_AttributeSetInstance_ID = $5), 'alle') AS attributes
	 ,to_char($1, 'DD.MM.YYYY') AS Base_Period_Start
	 ,to_char($2, 'DD.MM.YYYY') AS Base_Period_End
	 ,um.ad_org_id 
	 FROM report.fresh_umsatzliste_bpartner_report(
			$1,
			$2,
			 null, --$P{Comp_Period_Start},
			 null, --$P{Comp_Period_End},
			$3, --$P{IsSOTrx},
			$4, --$P{C_BPartner_ID},
			 null, --$P{C_Activity_ID},
			 null, --$P{M_Product_ID},
			 null, --$P{M_Product_Category_ID},
			$5, --$P{M_AttributeSetInstance_ID}
			$7 -- AD_Org_ID
			) um 
	join m_product p on p.name = um.p_name AND p.isActive = 'Y' AND p.ad_org_id = $7
	LEFT OUTER JOIN M_Product_Trl pt ON p.M_Product_ID = pt.M_Product_ID AND pt.AD_Language = $6 AND pt.isActive = 'Y'
	left join C_BPartner_Product bpp  ON p.M_Product_ID = bpp.M_Product_ID and bpp.c_bpartner_id = $4 AND bpp.isActive = 'Y'
	group by  COALESCE(pt.name, p.name), bpp.productcategory,um.ad_org_id 
	
$BODY$
LANGUAGE sql STABLE;

