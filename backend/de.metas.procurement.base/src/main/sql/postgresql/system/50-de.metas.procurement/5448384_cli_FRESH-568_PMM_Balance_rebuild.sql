create table backup.PMM_Balance_BKP20160802 as select * from PMM_Balance;

select de_metas_procurement.pmm_balance_rebuild();
