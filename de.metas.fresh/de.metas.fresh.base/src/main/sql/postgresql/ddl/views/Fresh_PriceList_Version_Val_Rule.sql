-- View: report.fresh_pricelist_version_val_rule

-- DROP VIEW report.fresh_pricelist_version_val_rule;

CREATE OR REPLACE VIEW report.fresh_Pricelist_Version_Val_Rule AS 
SELECT 
	plv.m_pricelist_version_id, ps.c_bpartner_id, plv.validfrom, plv.name, ps.IsSOTrx
FROM 
	( 
		SELECT bp.C_BPartner_ID, COALESCE( bp.M_PricingSystem_ID, bpg.M_PricingSystem_ID ) AS M_PricingSystem_ID, 'Y':: character as IsSOTrx
		FROM C_BPartner bp LEFT OUTER JOIN C_BP_Group bpg ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID
	UNION	
		SELECT bp.C_BPartner_ID, COALESCE( bp.PO_PricingSystem_ID, bpg.PO_PricingSystem_ID ) AS M_PricingSystem_ID, 'N'::character as IsSOTrx
		FROM C_BPartner bp LEFT OUTER JOIN C_BP_Group bpg ON bp.C_BP_Group_ID = bpg.C_BP_Group_ID
	) ps
	LEFT OUTER JOIN M_PriceList pl ON ps.M_PricingSystem_ID = pl.M_PricingSystem_ID
	LEFT OUTER JOIN M_PriceList_Version plv ON pl.M_PriceList_ID = plv.M_PriceList_ID
WHERE 
	plv.IsActive = 'Y'::bpchar;


COMMENT ON VIEW report.fresh_Pricelist_Version_Val_Rule IS 'Returns all currently valid C_PriceList_Version_IDs for all C_BPartner_ID along with the valid-from-date and price list version name';





-- Function: report.bpartner_pricelist_version(numeric)

 DROP FUNCTION IF EXISTS report.bpartner_pricelist_version(numeric);


 DROP FUNCTION IF EXISTS report.bpartner_pricelist_version(numeric, character);

CREATE OR REPLACE FUNCTION report.bpartner_pricelist_version(c_bpartner_id numeric, p_IsSOTrx character default null)
  RETURNS numeric AS
$BODY$
SELECT M_PriceList_Version_ID
FROM Report.fresh_PriceList_Version_Val_Rule plv
WHERE C_BPartner_ID= $1 AND ValidFrom <= now()
AND (p_IsSOTrx IS NULL OR p_IsSOTrx = IsSOTrx)
ORDER BY ValidFrom DESC, Name ASC
LIMIT 1$BODY$
  LANGUAGE sql STABLE
  COST 100;
COMMENT ON FUNCTION report.bpartner_pricelist_version(numeric, character) IS 'Returns the latest, currently valid price list version within the pricing system of a given Partner (or its BP Group)';
