
CREATE OR REPLACE VIEW de_metas_ordercandidate.diag_dropship_v AS
SELECT 
	olc.C_OLCand_ID AS olc_C_OLCand_ID,
	olc.POReference AS olc_POReference,
	ol.C_OrderLine_ID AS ol_C_OrderLine_ID,
	o.Created AS o_Created,
	o.Updated AS o_Updated,
	o.C_Order_ID AS o_C_Order_ID, 
	o.POReference AS o_POReference, 
	o.C_BPartner_ID AS o_C_BPartner_ID,
	o.C_BPartner_Location_ID AS o_C_BPartner_Location_ID,
	o.DropShip_BPartner_ID AS o_DropShip_BPartner_ID, 
	o_bp.Value, o_bp.Name,
	COALESCE(olc.DropShip_BPartner_Override_ID, olc.DropShip_BPartner_ID) AS olc_DropShip_BPartner_Eff_ID,  

	CASE WHEN COALESCE(olc.DropShip_BPartner_Override_ID, olc.DropShip_BPartner_ID, 0)= COALESCE(o.DropShip_BPartner_ID, 0) THEN 'Y' ELSE 'N' END AS o_DropShip_BPartner_ID_correct,
	o.DropShip_Location_ID AS o_DropShip_Location_ID,
	COALESCE(olc.DropShip_Location_Override_ID, olc.DropShip_Location_ID) AS olc_DropShip_Location_Eff_ID,

	CASE WHEN COALESCE(olc.DropShip_Location_Override_ID, olc.DropShip_Location_ID, 0)= COALESCE(o.DropShip_Location_ID,0) THEN 'Y' ELSE 'N' END AS o_DropShip_Location_ID_correct,
	o.IsDropShip AS o_IsDropShip,

	CASE WHEN COALESCE(olc.DropShip_Location_Override_ID, olc.DropShip_Location_ID, 0)>0 OR COALESCE(olc.DropShip_BPartner_Override_ID, olc.DropShip_BPartner_ID, 0)>0 THEN 'Y' ELSE 'N' END AS o_should_be_dropship

FROM C_OLCand olc
	JOIN C_Order_Line_Alloc ola ON olc.C_OLCand_ID=ola.C_OLCand_ID
	JOIN C_OrderLine ol ON ol.C_OrderLine_ID=ola.C_OrderLine_ID
	JOIN C_Order o ON o.C_Order_ID=ol.C_Order_ID
	JOIN C_BPartner o_bp ON o_bp.C_BPartner_ID=o.C_BPArtner_ID
WHERE true;
COMMENT ON VIEW de_metas_ordercandidate.diag_dropship_v IS 'Issue #100 FRESH-435: select C_Orders with their C_OLCands and checks if the C_Orders'' dropship IDs are consistent with the C_OlCands'' IDs, according to our current logic:
 * o_DropShip_BPartner_ID_correct: if a dropship partner is set in the olc, then it shall also be set in o, even if it does not differ from o''s C_BPartner_ID
 * o_DropShip_Location_ID_correct: similar to partner, if a dropship location is set in the olc, then it shall also be set in o, even if it does not differ from o''s C_BPartner_Location_ID
 * o_should_be_dropship: if olc has a an explicit dropship location or partner, then the order should be flagged accordingly
Important: the order might be reactivated and things might be changed manually. In that case the order might seem wrong according to this view.';
