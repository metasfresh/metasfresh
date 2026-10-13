
-- The changes we are doing here allow an admin to add attributes to the M_ShipmentSchedule_AttributeConfig tab 

UPDATE AD_Table SET description=NULL, accesslevel='6', Updated='2026-04-15 16:11:00', UpdatedBy=100 WHERE tablename = 'M_IolCandHandler';
UPDATE M_IolCandHandler SET AD_Client_ID=1000000, Updated='2026-04-15 16:11:00', UpdatedBy=100 WHERE ad_client_id = 0;
