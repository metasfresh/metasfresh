--
-- Build a temporary table of duplicate PP_MRP records which are about same C_OrderLine.
drop table if exists PP_MRP_Duplicates;
create temporary table PP_MRP_Duplicates as
select t.*, mrp.PP_MRP_ID
from 
(
	select C_OrderLine_ID
	, max(PP_MRP_ID) as Last_PP_MRP_ID
	, count(*)
	from PP_MRP mrp
	where mrp.C_OrderLine_ID is not null
	and mrp.IsActive='Y'
	group by C_OrderLine_ID
	having count(*) > 1
) t
inner join PP_MRP mrp on (mrp.C_OrderLine_ID=t.C_OrderLine_ID and mrp.IsActive='Y')
;
--
create index on PP_MRP_Duplicates(C_OrderLine_ID);
create index on PP_MRP_Duplicates(Last_PP_MRP_ID);
create index on PP_MRP_Duplicates(PP_MRP_ID);
--
delete from PP_MRP_Duplicates where PP_MRP_ID=Last_PP_MRP_ID;

--
-- Show results
/*
select count(*) from PP_MRP_Duplicates;
select * from PP_MRP_Duplicates limit 1000;
*/


--
-- Delete duplicate records
delete from PP_MRP mrp
where exists (select 1 from PP_MRP_Duplicates d where d.PP_MRP_ID=mrp.PP_MRP_ID);

