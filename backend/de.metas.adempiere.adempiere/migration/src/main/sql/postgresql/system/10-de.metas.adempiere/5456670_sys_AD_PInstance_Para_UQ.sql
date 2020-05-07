/* Find and delete duplicate parameters (shall not happen)

drop table if exists TMP_PInstance_Para;
create temporary table TMP_PInstance_Para as
select AD_PInstance_ID, ParameterName
, count(*) as cnt
-- , min(Updated), max(Updated)
, min(AD_PInstance_Para_ID) as Min_AD_PInstance_Para_ID
from AD_PInstance_Para
group by AD_PInstance_ID, ParameterName
having count(*) > 1
;

delete from AD_PInstance_Para p
where exists(select 1 from TMP_PInstance_Para t where t.AD_PInstance_ID=p.AD_PInstance_ID and p.ParameterName=t.ParameterName and p.AD_PInstance_Para_ID<>t.Min_AD_PInstance_Para_ID)
;
*/

-- drop index if exists AD_PInstance_Para_UQ;
create unique index AD_PInstance_Para_UQ on AD_PInstance_Para(AD_PInstance_ID, ParameterName);
