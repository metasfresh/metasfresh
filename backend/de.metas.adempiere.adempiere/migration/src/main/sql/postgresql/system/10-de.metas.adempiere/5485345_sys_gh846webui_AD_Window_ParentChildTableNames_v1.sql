drop view if exists AD_Window_ParentChildTableNames_v1;
create or replace view AD_Window_ParentChildTableNames_v1 as
select
AD_Window_ID
, WindowName
, ParentTableName
, ChildTableName
, ParentColumnName as ParentLinkColumnName
, ChildKeyColumnName
--
, (case
	when ColumnName is not null then ColumnName
	else (select c.ColumnName from AD_Column c where c.AD_Table_ID=t.Child_Table_ID and c.ColumnName=t.ParentColumnName and c.IsActive='Y')
end) as ChildLinkColumnName
--
from (
	select
	w.AD_Window_ID
	, w.Name as WindowName
	, pt.TableName as ParentTableName
	, pt.AD_Table_ID as Parent_Table_ID
	, ct.TableName as ChildTableName
	, ct.AD_Table_ID as Child_Table_ID

	-- ColumnName
	, (case
		when ctt.AD_Column_ID is not null then (select c.ColumnName from AD_Column c where c.AD_Column_ID=ctt.AD_Column_ID)
		else null
	end) as ColumnName

	-- ParentColumnName
	, (case
		when ctt.Parent_Column_ID is not null then (select c.ColumnName from AD_Column c where c.AD_Column_ID=ctt.Parent_Column_ID)
		else (select c.ColumnName from AD_Column c where c.AD_Table_ID=ptt.AD_Table_ID and c.IsKey='Y' and c.IsActive='Y')
	end) as ParentColumnName

	, (select c.ColumnName from AD_Column c where c.AD_Table_ID=ct.AD_Table_ID and c.IsKey='Y' and c.IsActive='Y') as ChildKeyColumnName

	from AD_Tab ptt
	inner join AD_Table pt on (pt.AD_Table_ID=ptt.AD_Table_ID)
	inner join AD_Tab ctt on (
		ctt.AD_Window_ID=ptt.AD_Window_ID
		and ctt.TabLevel=ptt.TabLevel+1
		and ctt.SeqNo > ptt.SeqNo
		and ctt.IsActive='Y'
	)
	inner join AD_Table ct on (ct.AD_Table_ID=ctt.AD_Table_ID)
	inner join AD_Window w on (w.AD_Window_ID=ptt.AD_Window_ID)
	where true
	and ptt.SeqNo=10 and ptt.TabLevel=0 and ptt.IsActive='Y'
	and w.IsActive='Y'
	and w.IsEnableRemoteCacheInvalidation='Y'
	and pt.IsActive='Y'
	and ct.IsActive='Y'
	and exists (select 1 from AD_Menu m where m.AD_Window_ID=w.AD_Window_ID and m.IsActive='Y')
) t
;



/*
select * from AD_Window_ParentChildTableNames_v1 
where true
order by parentTableName, AD_Window_ID, childTableName;
*/


