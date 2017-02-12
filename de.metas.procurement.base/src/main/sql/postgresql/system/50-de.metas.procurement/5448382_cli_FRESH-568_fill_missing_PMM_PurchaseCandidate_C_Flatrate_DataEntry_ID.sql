--
--
-- Updates PMM_PurchaseCandidate.C_Flatrate_DataEntry_ID from PMM_QtyReport_Event.C_Flatrate_DataEntry_ID
--
--


--
-- Backup
create table backup.PMM_PurchaseCandidate_BKP20160802 as select * from PMM_PurchaseCandidate;

--
-- Join QtyReport_Event and Purchase Candidates
drop table if exists TMP_Event_And_PC;
create temporary table TMP_Event_And_PC as
select e.PMM_QtyReport_Event_ID, e.PMM_PurchaseCandidate_ID
, e.C_Flatrate_DataEntry_ID as Event_Flatrate_DataEntry_ID
, pc.C_Flatrate_DataEntry_ID as PC_Flatrate_DataEntry_ID
from PMM_QtyReport_Event e
inner join PMM_PurchaseCandidate pc on (pc.PMM_PurchaseCandidate_ID=e.PMM_PurchaseCandidate_ID)
where e.C_Flatrate_DataEntry_ID is distinct from pc.C_Flatrate_DataEntry_ID
;

--
-- Prepare purchase candidates to update
drop table if exists TMP_PurchaseCandidate_ToUpdate;
create temporary table TMP_PurchaseCandidate_ToUpdate as
select distinct PMM_PurchaseCandidate_ID, Event_Flatrate_DataEntry_ID, PC_Flatrate_DataEntry_ID
from TMP_Event_And_PC
;
create unique index on TMP_PurchaseCandidate_ToUpdate (PMM_PurchaseCandidate_ID);

--
-- Show results
/*
select * from TMP_PurchaseCandidate_ToUpdate;
*/


--
-- Update PMM_PurchaseCandidate.C_Flatrate_DataEntry_ID from Event
update PMM_PurchaseCandidate pc set C_Flatrate_DataEntry_ID=t.Event_Flatrate_DataEntry_ID
from TMP_PurchaseCandidate_ToUpdate t
where t.PMM_PurchaseCandidate_ID=pc.PMM_PurchaseCandidate_ID;

