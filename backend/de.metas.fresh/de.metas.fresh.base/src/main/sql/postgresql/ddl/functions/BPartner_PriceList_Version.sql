-- Function: report.bpartner_pricelist_version(numeric)

-- DROP FUNCTION report.bpartner_pricelist_version(numeric);

CREATE OR REPLACE FUNCTION report.bpartner_pricelist_version(c_bpartner_id numeric)
  RETURNS numeric AS
$BODY$
SELECT M_PriceList_Version_ID 
FROM Report.fresh_PriceList_Version_Val_Rule 
WHERE C_BPartner_ID= $1 AND ValidFrom <= now()
ORDER BY ValidFrom DESC, Name ASC 
LIMIT 1$BODY$
  LANGUAGE sql STABLE
  COST 100;
ALTER FUNCTION report.bpartner_pricelist_version(numeric) OWNER TO adempiere;
COMMENT ON FUNCTION report.bpartner_pricelist_version(numeric) IS 'Returns the latest, currently valid price list version within the pricing system of a given Partner (or its BP Group)';
