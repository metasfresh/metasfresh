
-- #100 FRESH-435 EDI wrong handover location in Picking Terminal

DROP SCHEMA IF EXISTS FRESH_435;
CREATE SCHEMA FRESH_435;
COMMENT ON SCHEMA FRESH_435 IS 'Issue #100 FRESH-435: contains SQL workarounds to fix C_Orders, M_ShipmentSchedules and EDI_Desadvs, until the code fix is in place';

SET search_path TO FRESH_435,public;

---------------
-- Handover-Location
---------------
DROP VIEW IF EXISTS de_metas_ordercandidate.diag_handover CASCADE;
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
 * o_should_UseHandOver_Location: if the order has a handover location, then it shall have IsUseHandOver_Location=''Y''.
Important: the order might be reactivated and things might be changed manually. In that case the order might seem wrong according to this view.';
-- SELECT * FROM de_metas_ordercandidate.diag_handover WHERE true AND o_Created>='2016-06-20 12:30'

DROP VIEW IF EXISTS FRESH_435.C_Order_diag_handover_to_fix CASCADE;
CREATE VIEW FRESH_435.C_Order_diag_handover_to_fix AS
SELECT v.* 
FROM de_metas_ordercandidate.diag_handover v
	JOIN C_Order o ON o.C_Order_ID=v.o_C_Order_ID
WHERE true
	AND o_Created>='2016-06-20 12:30'
	AND (
		o.IsUseHandOver_Location!=v.o_should_UseHandOver_Location
		OR o.HandOver_Partner_ID!=v.olc_HandOver_Partner_Eff_ID 
		OR o.HandOver_Location_ID!=v.olc_HandOver_Location_Eff_ID
	);
COMMENT ON VIEW FRESH_435.C_Order_diag_handover_to_fix IS 'Issue #100 FRESH-435: selects de_metas_ordercandidate.diag_handover records whose orders are not OK, according to that view';
-- SELECT * FROM FRESH_435.C_Order_diag_handover_to_fix	

---------------
-- Dropship-Location
---------------
DROP VIEW IF EXISTS de_metas_ordercandidate.diag_dropship_v CASCADE;
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
 * o_should_be_dropship: if olc has a an explicit dropship location or partner, then this lcoation should also be explicitly 
Important: the order might be reactivated and things might be changed manually. In that case the order might seem wrong according to this view.';

DROP VIEW IF EXISTS FRESH_435.M_ShipmentSchedule_to_fix_location_general_v;
CREATE VIEW FRESH_435.M_ShipmentSchedule_to_fix_location_general_v AS
SELECT v.*,s.M_ShipmentSchedule_ID,s.C_BPArtner_Location_ID, s.C_BP_Location_Override_ID, s.DeliveryDate
FROM de_metas_ordercandidate.diag_dropship_v v
	JOIN M_ShipmentSchedule s ON s.C_OrderLine_ID=v.ol_C_OrderLine_ID
WHERE true
	AND o_Created>='2016-06-20 12:30'
	AND s.C_BPartner_Location_ID!=COALESCE(v.o_DropShip_Location_ID, v.o_C_BPartner_Location_ID);
COMMENT ON VIEW FRESH_435.M_ShipmentSchedule_to_fix_location_general_v IS 'Issue #100 FRESH-435: selects M_ShipmentSchedule whose C_BPartner_Location_ID differs from the C_Order''s (effective) DropShip_Location_ID';
--SELECT * FROM FRESH_435.M_ShipmentSchedule_to_fix_location_general_v;


drop VIEW IF EXISTS FRESH_435.C_Order_dropship_to_fix_v;
CREATE VIEW FRESH_435.C_Order_dropship_to_fix_v AS
SELECT v.*
FROM de_metas_ordercandidate.diag_dropship_v v
WHERE true
	AND v.o_Created>='2016-06-20 12:30'
	AND (
		v.o_DropShip_BPartner_ID_correct='N' OR
		v.o_DropShip_Location_ID_correct='N' OR
		v.o_should_be_dropship!=v.o_IsDropShip
	);
---------------
-- DESADV
---------------
DROP VIEW IF EXISTS "de.metas.edi".EDI_Desadv_diag_locations_v;
CREATE VIEW "de.metas.edi".EDI_Desadv_diag_locations_v AS
SELECT  d.EDI_Desadv_ID AS d_EDI_Desadv_ID,
	d.DocumentNo AS d_DocumentNo,
	d.POReference AS d_POReference,
	d.Created AS d_created,
	d.EDI_ExportStatus AS d_EDI_ExportStatus,
	d.C_BPartner_Location_ID AS d_C_BPartner_Location_ID,
	CASE WHEN o.IsDropShip='Y' THEN o.DropShip_Location_ID ELSE o.C_BPartner_Location_ID END AS d_C_BPartner_Location_ID_Should,
	d.HandOver_Location_ID AS d_HandOver_Location_ID,
	o.HandOver_Location_ID AS d_HandOver_Location_ID_Should
FROM EDI_Desadv d
	JOIN C_Order o ON o.EDI_Desadv_ID=d.EDI_Desadv_ID
WHERE true;

DROP VIEW IF EXISTS FRESH_435.EDI_Desadv_locations_to_fix_v;
CREATE VIEW FRESH_435.EDI_Desadv_locations_to_fix_v AS
SELECT * FROM "de.metas.edi".EDI_Desadv_diag_locations_v v
WHERE true
	AND d_created>='2016-06-21'
	AND d_EDI_ExportStatus!='S'
	AND ( 
		COALESCE(d_C_BPartner_Location_ID,0)!=COALESCE(d_C_BPartner_Location_ID_Should,0) OR
		COALESCE(d_HandOver_Location_ID,0)!=COALESCE(d_HandOver_Location_ID_Should,0)
	);
	
-- SELECT * FROM FRESH_435.EDI_Desadv_locations_to_fix_v	
	
-------------------------------
-- To run
------------------------------

CREATE OR REPLACE FUNCTION FRESH_435.workaround_fix_c_order_edi_desadv_and_m_Shipmentschedule()
  RETURNS VOID AS
 $BODY$
-- Dropship (Order and M_ShipmentSchedule)
------------
UPDATE C_Order o
SET 
	IsDropShip=v.o_should_be_dropship,
	DropShip_BPartner_ID=v.olc_DropShip_BPartner_Eff_ID, 
	DropShip_Location_ID=v.olc_DropShip_Location_Eff_ID,
	Updated=now(), UpdatedBy=99
FROM FRESH_435.C_Order_dropship_to_fix_v v
WHERE true
	AND o.C_Order_ID=v.o_C_Order_ID;

-- run in one TRX
INSERT INTO M_ShipmentSchedule_Recompute(M_ShipmentSchedule_ID) SELECT M_ShipmentSchedule_ID FROM FRESH_435.M_ShipmentSchedule_to_fix_location_general_v v;
UPDATE M_ShipmentSchedule s
SET C_BPartner_Location_ID=v.o_DropShip_Location_ID, Updated=now(), UpdatedBy=99
FROM FRESH_435.M_ShipmentSchedule_to_fix_location_general_v v
WHERE s.M_ShipmentSchedule_ID=v.M_ShipmentSchedule_ID;

-- HandOver (Order)
-------------------
UPDATE C_Order o
SET 
	IsUseHandOver_Location=v.o_should_UseHandOver_Location,
	HandOver_Partner_ID=v.olc_HandOver_Partner_Eff_ID, 
	HandOver_Location_ID=v.olc_HandOver_Location_Eff_ID,
	Updated=now(), UpdatedBy=99
FROM FRESH_435.C_Order_diag_handover_to_fix v
WHERE true
	AND o.C_Order_ID=v.o_C_Order_ID;


-- DESADV (lcoation and handover)	
----------------------------
UPDATE EDI_Desadv d
SET 
	C_BPartner_Location_ID=d_C_BPartner_Location_ID_Should,
	HandOver_Location_ID=d_HandOver_Location_ID_Should,
	Updated=now(), UpdatedBy=99
FROM FRESH_435.EDI_Desadv_locations_to_fix_v v
WHERE v.d_EDI_Desadv_ID=d.EDI_Desadv_ID;

$BODY$
  LANGUAGE sql VOLATILE;
  