-- Sets missing C_City.C_Country_ID from C_City.C_Region_ID.C_Country_ID


create table backup.C_City_BKP20161201 as select * from C_City;


drop table if exists TMP_C_City_ToUpdate;
create temporary table TMP_C_City_ToUpdate as
select r.C_Country_ID, c.C_City_ID, c.Name, c.C_Region_ID
from C_City c
inner join C_Region r on (r.C_Region_ID=c.C_Region_ID)
where c.C_Country_ID is null
;

-- select * from TMP_C_City_ToUpdate;

update C_City c set C_Country_ID=t.C_Country_ID
from TMP_C_City_ToUpdate t
where t.C_City_ID=c.C_City_ID;

