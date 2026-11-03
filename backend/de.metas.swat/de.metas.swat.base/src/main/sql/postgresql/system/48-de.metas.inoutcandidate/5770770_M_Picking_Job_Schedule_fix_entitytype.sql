UPDATE ad_table
SET entitytype='de.metas.inoutcandidate'
WHERE ad_table_id = 542513 -- M_Picking_Job_Schedule
;

UPDATE ad_column
SET entitytype='de.metas.inoutcandidate'
WHERE ad_table_id = 542513 -- M_Picking_Job_Schedule
;

-- select ad_table_id, tablename, entitytype from ad_table where tablename in ('M_ShipmentSchedule', 'M_Picking_Job_Schedule');

