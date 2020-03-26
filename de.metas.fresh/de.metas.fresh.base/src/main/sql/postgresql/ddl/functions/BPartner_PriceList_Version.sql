
-- Function: report.bpartner_pricelist_version(numeric)

-- DROP FUNCTION IF EXISTS report.bpartner_pricelist_version(numeric);


-- DROP FUNCTION IF EXISTS report.bpartner_pricelist_version(numeric, character);

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
COMMENT ON FUNCTION report.bpartner_pricelist_version(numeric, character) IS 'Returns the latest, currently valid price list version within the pricing system of a given Partner (or its BP Group). Based on the IsSOTrx parameter, only the sales/ purchase PLVs will be valid. If the IsSOTrx is left empty, both kind of PLVs are valid';

