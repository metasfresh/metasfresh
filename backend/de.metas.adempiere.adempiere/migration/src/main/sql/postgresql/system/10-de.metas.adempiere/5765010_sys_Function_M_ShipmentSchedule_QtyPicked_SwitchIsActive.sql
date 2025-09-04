
DROP FUNCTION IF EXISTS M_ShipmentSchedule_QtyPicked_SwitchIsActive(p_M_ShipmentSchedule_QtyPicked_ID numeric)
;


CREATE OR REPLACE FUNCTION M_ShipmentSchedule_QtyPicked_SwitchIsActive(
    p_M_ShipmentSchedule_QtyPicked_ID numeric
)
   RETURNS void AS
$BODY$



BEGIN


UPDATE M_ShipmentSchedule_QtyPicked set IsActive = (CASE WHEN IsActive = 'Y' THEN 'N' ELSE 'Y' END) where M_ShipmentSchedule_QtyPicked_ID = p_M_ShipmentSchedule_QtyPicked_ID;




INSERT INTO m_shipmentschedule_recompute (m_shipmentschedule_id)
SELECT m_shipmentschedule_id
FROM M_ShipmentSchedule_QtyPicked where M_ShipmentSchedule_QtyPicked_ID = p_M_ShipmentSchedule_QtyPicked_ID
;





END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE
    COST 100
;

