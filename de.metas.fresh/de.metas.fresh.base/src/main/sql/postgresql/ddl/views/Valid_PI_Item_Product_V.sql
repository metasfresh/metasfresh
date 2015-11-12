-- View: report.valid_pi_item_product_v

-- DROP VIEW report.valid_pi_item_product_v;

CREATE OR REPLACE VIEW report.valid_pi_item_product_v AS 

SELECT 
	ip.M_Product_ID, 
	ip.C_BPartner_ID, 
	ip.IsInfiniteCapacity, 
	ip.C_BPartner_ID IS NOT NULL AS hasPartner, 
	ip.M_HU_PI_Item_Product_ID,
	ip.M_HU_PI_Item_ID, 
	ip.Name,
	ip.Qty as QtyCUsPerTU
FROM 
	M_HU_PI_Item_Product ip
WHERE 
	ip.isactive = 'Y'::bpchar 
	AND ip.validfrom <= now() 
	AND (ip.validto >= now() OR ip.validto IS NULL)
;


COMMENT ON VIEW report.valid_pi_item_product_v IS 'This view returns a list of all active and currently valid Packing instructions.';

