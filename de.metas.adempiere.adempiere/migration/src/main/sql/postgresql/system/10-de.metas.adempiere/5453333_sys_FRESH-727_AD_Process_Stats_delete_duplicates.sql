drop table if exists AD_Process_Stats_Duplicates;
create temporary table AD_Process_Stats_Duplicates as
select AD_Process_ID, min(AD_Process_Stats_ID) first_AD_Process_Stats_ID, count(1) as count
from AD_Process_Stats
group by AD_Process_ID, AD_Client_ID
having count(1) > 1
;

drop table if exists AD_Process_Stats_ToDelete;
create temporary table AD_Process_Stats_ToDelete as
select s.*
from AD_Process_Stats_Duplicates d
inner join AD_Process_Stats s on (s.AD_Process_ID=d.AD_Process_ID and s.AD_Process_Stats_ID<>d.first_AD_Process_Stats_ID)
;

-- select * from AD_Process_Stats_ToDelete;

delete from AD_Process_Stats s where exists (select 1 from AD_Process_Stats_ToDelete d where d.AD_Process_Stats_ID=s.AD_Process_Stats_ID);

