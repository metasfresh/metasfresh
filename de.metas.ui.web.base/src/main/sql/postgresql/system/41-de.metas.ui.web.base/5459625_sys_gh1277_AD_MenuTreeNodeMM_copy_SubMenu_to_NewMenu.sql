-- create table backup.AD_TreeNodeMM_bkp20170202 as select * from AD_TreeNodeMM;

--
-- Remove current tree nodes
delete from AD_TreeNodeMM where AD_Tree_ID=1000039;

--
-- Recreate them by copying from Standard Menu tree -> Webui subtree
insert into AD_TreeNodeMM
(
	AD_Tree_ID
	, Node_ID
	, Parent_ID
	, SeqNo
	, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy
)
select
	1000039 as AD_Tree_ID -- Webui AD_Tree_ID
	, (case when Node_ID=1000007 then 0 else Node_ID end) as Node_ID
	, (case when Parent_ID=1000007 then 0 when Parent_ID=0 then null else Parent_ID end) as Parent_ID
	, SeqNo
	, 0 as AD_Client_ID, 0 as AD_Org_ID, now() as Created, 0 as CreatedBy, now() as Updated, 0 as UpdatedBy
	-- , t.Name, t.Path, t.IsCycle, t.Parent_ID as Parent_ID_Orig
from get_AD_TreeNodeMM_Paths(10, 1000007) t
where not IsCycle
order by path
;


--
-- Check results:
-- select * from get_AD_TreeNodeMM_Paths(1000039, null) t
-- order by path;

