--
--
-- Update PMM_QtyReport_Event.C_Flatrate_DataEntry_ID
--
--


--
-- Backup
create table backup.PMM_QtyReport_Event_BKP20160802 as select * from PMM_QtyReport_Event;

--
-- Prepare PMM_QtyReport_Event to be updated
drop table if exists TMP_QtyReport_Event_ToUpdate;
create temporary table TMP_QtyReport_Event_ToUpdate as
select
e.PMM_QtyReport_Event_ID
, e.Processed
, e.DatePromised
, e.IsError, e.ErrorMsg
, e.PMM_PurchaseCandidate_ID
, e.C_Flatrate_Term_ID
, e.C_Flatrate_DataEntry_ID
, (
	select C_Flatrate_DataEntry_ID
	from C_Flatrate_DataEntry
	where 
	C_Flatrate_Term_ID=e.C_Flatrate_Term_ID
	and C_Period_ID in (
		select p.C_Period_ID
		from C_Period p
		where p.StartDate <=e.DatePromised AND p.EndDate >=e.DatePromised
	)
	and Type='PC'
	and COALESCE( (select t.IsSimulation from C_Flatrate_Term t where t.C_Flatrate_Term_ID=C_Flatrate_DataEntry.C_Flatrate_Term_ID), 'N') = 'N'
	and (coalesce(FlatrateAmtPerUOM, 0) > 0 or coalesce(Qty_Planned, 0) > 0)
) as C_Flatrate_DataEntry_ID_New
from PMM_QtyReport_Event e
where
true
and C_Flatrate_Term_ID is not null and C_Flatrate_DataEntry_ID is null
;
--
delete from TMP_QtyReport_Event_ToUpdate where C_Flatrate_DataEntry_ID_New is null;
--
create index on TMP_QtyReport_Event_ToUpdate(PMM_QtyReport_Event_ID);

--
-- Show TMP_QtyReport_Event_ToUpdate
/*
select * from TMP_QtyReport_Event_ToUpdate;
*/

--
-- Update PMM_QtyReport_Event
update PMM_QtyReport_Event e set C_Flatrate_DataEntry_ID=t.C_Flatrate_DataEntry_ID_New
from TMP_QtyReport_Event_ToUpdate t
where t.PMM_QtyReport_Event_ID=e.PMM_QtyReport_Event_ID;
