
drop view if exists AD_Window_ParentChildTableNames_v1;
create or replace view AD_Window_ParentChildTableNames_v1 as
select
AD_Window_ID
, WindowName
, ParentTableName
, Parent_Table_IsEnableRemoteCacheInvalidation
, ChildTableName
, Child_Table_IsEnableRemoteCacheInvalidation
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
	, (case 
	    when w.IsEnableRemoteCacheInvalidation='Y' OR pt.IsEnableRemoteCacheInvalidation='Y' then 'Y'
	    else 'N'
	end) as Parent_Table_IsEnableRemoteCacheInvalidation
	, ct.TableName as ChildTableName
	, ct.AD_Table_ID as Child_Table_ID
	, (case 
	   	when ct.AD_Table_ID IS NULL then NULL
	    when w.IsEnableRemoteCacheInvalidation='Y' OR ct.IsEnableRemoteCacheInvalidation='Y' then 'Y'
	    else 'N'
	end) as Child_Table_IsEnableRemoteCacheInvalidation
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

	from AD_Tab ptt/*parent-tab*/
		inner join AD_Table pt/*parent-table*/ on (pt.AD_Table_ID=ptt.AD_Table_ID)
		left join AD_Tab ctt/*child-tab*/ on (
			ctt.AD_Window_ID=ptt.AD_Window_ID
			and ctt.TabLevel=ptt.TabLevel+1
			and ctt.SeqNo > ptt.SeqNo
			and ctt.IsActive='Y'
		)
			left join AD_Table ct/*child-table*/ on (ct.AD_Table_ID=ctt.AD_Table_ID)
		inner join AD_Window w on (w.AD_Window_ID=ptt.AD_Window_ID)
	where true
		/* the parent tab needs to have the smallest SeqNo and TabLevel (but not neccesarly 10 resp. 0) */
		and ptt.SeqNo=(select min(siblings.SeqNo) from AD_Tab siblings where siblings.AD_Window_ID=w.AD_Window_ID and siblings.IsActive='Y') 
		and ptt.TabLevel=(select min(siblings.TabLevel) from AD_Tab siblings where siblings.AD_Window_ID=w.AD_Window_ID and siblings.IsActive='Y') 
		and ptt.IsActive='Y'
		and w.IsActive='Y'
		and pt.IsActive='Y'
		and coalesce(ct.IsActive,'Y')='Y'
		and exists (select 1 from AD_Menu m where m.AD_Window_ID=w.AD_Window_ID and m.IsActive='Y')
) t

/*
select * from AD_Window_ParentChildTableNames_v1 
where true
order by parentTableName, AD_Window_ID, childTableName;
*/
