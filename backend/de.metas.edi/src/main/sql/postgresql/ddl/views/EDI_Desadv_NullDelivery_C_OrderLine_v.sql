
DROP VIEW IF EXISTS EDI_Desadv_NullDelivery_C_OrderLine_v;
CREATE OR REPLACE VIEW EDI_Desadv_NullDelivery_C_OrderLine_v AS
SELECT DISTINCT
	d.EDI_Desadv_ID
--	, ol.C_OrderLine_ID, ol.Line, ol.QtyDelivered  -- for debugging
	, ol_null.C_OrderLine_ID
--	, ol_null.Line, ol_null.QtyDelivered  -- for debugging
FROM EDI_Desadv d
	JOIN EDI_DesadvLine dl ON d.EDI_Desadv_ID = dl.EDI_Desadv_ID
	JOIN C_OrderLine ol ON ol.C_OrderLine_ID=dl.C_OrderLine_ID
	JOIN C_OrderLine ol_null ON true
		AND ol_null.C_Order_ID=ol.C_Order_ID
		AND ol_null.C_OrderLine_ID!=ol.C_OrderLine_ID
		AND ol.IsPackagingMaterial='N'
WHERE TRUE
	AND d.IsActive='Y'
	AND dl.IsActive='Y'
	AND ol.IsActive='Y'
	AND ol_null.IsActive='Y'
	AND NOT EXISTS (
		select 1 
		from EDI_DesadvLine dlOther
		where dlOther.C_OrderLine_ID=ol_null.C_OrderLine_ID
	);



COMMENT ON VIEW adempiere.EDI_Desadv_NullDelivery_C_OrderLine_v
  IS 'task 08456: selects those order lines which have *no* desadvLine, but which have a "sibling" in this desadv';
