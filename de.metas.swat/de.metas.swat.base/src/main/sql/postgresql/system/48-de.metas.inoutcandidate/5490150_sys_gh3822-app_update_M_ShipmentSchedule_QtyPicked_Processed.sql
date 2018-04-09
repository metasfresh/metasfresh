
UPDATE M_ShipmentSchedule_QtyPicked
SET Processed='Y', Updated=now(), UpdatedBy=99
WHERE M_ShipmentSchedule_QtyPicked_ID IN
(
	SELECT a.M_ShipmentSchedule_QtyPicked_ID
	FROM M_ShipmentSchedule_QtyPicked a
		JOIN M_ShipmentSchedule s ON s.M_ShipmentSchedule_ID=a.M_ShipmentSchedule_ID
		JOIN M_InOutLine iol ON iol.M_InoutLine_ID=a.M_InoutLine_ID
			JOIN M_InOut io ON io.M_InOut_ID=iol.M_InOut_ID
	-- note: update from io.Processed (not iol.Processed), because that's what the java code also does
	WHERE io.Processed='Y' AND a.Processed='N' 
		AND (
			s.Processed='N' 
			OR 
			s.Created >= (now() - interval '1 week') 
		)
);
