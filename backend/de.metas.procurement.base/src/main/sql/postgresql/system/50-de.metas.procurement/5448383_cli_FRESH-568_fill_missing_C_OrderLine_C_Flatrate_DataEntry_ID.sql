--
--
-- Update C_OrderLine.C_Flatrate_DataEntry_ID from PMM_PurchaseCandidate.C_Flatrate_DataEntry_ID
--
--

--
-- Backup
create table backup.C_OrderLine_MFProcurement_BKP20160802 as select * from C_OrderLine; -- where MFProcurement='Y';

drop table if exists TMP_OrderLine_ToUpdate;
create temporary table TMP_OrderLine_ToUpdate as
select distinct
	ol.C_OrderLine_ID
	, ol.C_Flatrate_DataEntry_ID as OL_Flatrate_DataEntry_ID
	, pc.C_Flatrate_DataEntry_ID as PC_Flatrate_DataEntry_ID
from PMM_PurchaseCandidate_OrderLine pco
inner join PMM_PurchaseCandidate pc on (pc.PMM_PurchaseCandidate_ID=pco.PMM_PurchaseCandidate_ID)
inner join C_OrderLine ol on (ol.C_OrderLine_ID=pco.C_OrderLine_ID)
where ol.C_Flatrate_DataEntry_ID is distinct from pc.C_Flatrate_DataEntry_ID
;
create unique index on TMP_OrderLine_ToUpdate (C_OrderLine_ID);


--
-- Show results
/*
select * from TMP_OrderLine_ToUpdate;
*/

--
-- Update C_OrderLine.C_Flatrate_DataEntry_ID from PMM_PurchaseCandidate
update C_OrderLine ol set C_Flatrate_DataEntry_ID=t.PC_Flatrate_DataEntry_ID
from TMP_OrderLine_ToUpdate t
where t.C_OrderLine_ID=ol.C_OrderLine_ID;
