
--
-- makes sure that the warehouses' C_BPartner_ID matches their C_BPartner_Locations' C_BPartner_ID
-- This was not the case on at least one older DB and went unnoticed until now 
--
CREATE table backup.m_warehouse_20210504 AS SELECT * FROM m_warehouse;

UPDATE m_warehouse wh
SET c_bpartner_id=bpl.c_bpartner_id, updatedby=99, updated='2021-05-04 12:32:31.623587 +02:00'
FROM c_bpartner_location bpl
WHERE bpl.c_bpartner_location_id = wh.c_bpartner_location_id
  AND wh.c_bpartner_id != bpl.c_bpartner_id;
