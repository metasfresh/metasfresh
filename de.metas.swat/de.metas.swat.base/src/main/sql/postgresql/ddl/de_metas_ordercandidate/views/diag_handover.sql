CREATE OR REPLACE VIEW de_metas_ordercandidate.diag_handover AS
SELECT 
	olc.C_OLCand_ID AS olc_C_OLCand_ID,
	olc.POReference AS olc_POReference,
	ol.C_OrderLine_ID AS ol_C_OrderLine_ID,
	o.Created AS o_Created,
	o.Updated AS o_Updated,
	o.C_Order_ID AS o_C_Order_ID, 
	o.POReference AS o_POReference, 
	o.C_BPartner_ID AS o_C_BPartner_ID,
	o_bp.Value, o_bp.Name,
	
	o.HandOver_Partner_ID AS o_HandOver_Partner_ID, 
	COALESCE(olc.HandOver_Partner_Override_ID, olc.HandOver_Partner_ID) AS olc_HandOver_Partner_Eff_ID, 
	CASE WHEN COALESCE(olc.HandOver_Partner_Override_ID, olc.HandOver_Partner_ID, 0) = COALESCE(o.HandOver_Partner_ID, 0) THEN 'Y' ELSE 'N' END AS o_HandOver_Partner_ID_correct,

	o.C_BPartner_Location_ID AS o_C_BPartner_Location_ID,
	o.HandOver_Location_ID AS o_HandOver_Location_ID,
	COALESCE(olc.HandOver_Location_Override_ID, olc.HandOver_Location_ID) AS olc_HandOver_Location_Eff_ID,
	CASE WHEN COALESCE(olc.HandOver_Location_Override_ID, olc.HandOver_Location_ID, 0)= COALESCE(o.HandOver_Location_ID, 0) THEN 'Y' ELSE 'N' END AS o_HandOver_Location_ID_correct,
	
	o.IsUseHandOver_Location AS o_IsUseHandOver_Location,
	CASE WHEN COALESCE(olc.HandOver_Location_Override_ID, olc.HandOver_Location_ID, 0)>0 THEN 'Y' ELSE 'N' END AS o_should_UseHandOver_Location -- this is according to the old logic; handover-partner plays no role in it
FROM C_OLCand olc
	JOIN C_Order_Line_Alloc ola ON olc.C_OLCand_ID=ola.C_OLCand_ID
	JOIN C_OrderLine ol ON ol.C_OrderLine_ID=ola.C_OrderLine_ID
	JOIN C_Order o ON o.C_Order_ID=ol.C_Order_ID
	JOIN C_BPartner o_bp ON o_bp.C_BPartner_ID=o.C_BPArtner_ID
WHERE true;
COMMENT ON VIEW de_metas_ordercandidate.diag_handover IS 'Issue #100 FRESH-435: can be used to show if C_ORder''s handover references are OK according to our current logic:
 * o_HandOver_Partner_ID_correct: if a handOver partner is set in the C_OLCand, then it shall also be set in the C_Order, even if it does not differ from the C_Order''s C_BPartner_ID
 * o_HandOver_Location_ID_correct: similar to partner, if a handover location is set in the olc, then it shall also be set in o, even if it does not differ from o''s C_BPartner_Location_ID
 * o_should_UseHandOver_Location: if the olc has a handover location, then it shall have IsUseHandOver_Location=''Y''.
Important: the order might be reactivated and things might be changed manually. In that case the order might seem wrong according to this view.';

