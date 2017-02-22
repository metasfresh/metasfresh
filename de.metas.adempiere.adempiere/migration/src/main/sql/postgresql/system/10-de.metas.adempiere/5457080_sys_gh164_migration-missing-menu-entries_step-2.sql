--
-- Import AD_TreeNodeMM webui menu
delete from AD_TreeNodeMM where AD_Tree_ID=1000039;
insert into AD_TreeNodeMM
(
	AD_Tree_ID
	, Node_ID
	, Parent_ID
	, SeqNo
	, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy
)
select
	t.AD_Tree_ID
	, t.Node_ID
	, t.Parent_ID
	, t.SeqNo
	, 0 as AD_Client_ID, 0 as AD_Org_ID, now() as Created, 0 as CreatedBy, now() as Updated, 0 as UpdatedBy
from backup.WEBUI_AD_TreeNodeMM t;