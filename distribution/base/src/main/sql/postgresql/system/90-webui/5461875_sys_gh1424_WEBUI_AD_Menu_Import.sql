--
-- Backup
create table backup.AD_TreeNodeMM_BKP_BeforeImportWebUI_5461871_2 as select * from AD_TreeNodeMM;
create table backup.AD_Menu_BKP_BeforeImportWebUI_5461871_2 as select * from AD_Menu;
create table backup.AD_Menu_Trk_BKP_BeforeImportWebUI_5461871_2 as select * from AD_Menu_Trl;

--
-- Expected input tables:
-- backup.WEBUI_AD_TreeNodeMM
-- backup.WEBUI_AD_Menu
-- backup.WEBUI_AD_Menu_Trl

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

--
--
-- Import AD_Menu for webui
--
--

--
-- Update existing entries for AD_Menu and AD_Menu_Trl
update AD_Menu m set
	InternalName=t.InternalName
	, Name=t.Name
	, Description=t.Description
	, IsCreateNew=t.IsCreateNew
	, WEBUI_NameBrowse=t.WEBUI_NameBrowse
	, WEBUI_NameNew=t.WEBUI_NameNew
	, WEBUI_NameNewBreadcrumb=t.WEBUI_NameNewBreadcrumb
from backup.WEBUI_AD_Menu t
where t.AD_Menu_ID=m.AD_Menu_ID;
--
update AD_Menu_Trl m set
	Name=t.Name
	, Description=t.Description
	, WEBUI_NameBrowse=t.WEBUI_NameBrowse
	, WEBUI_NameNew=t.WEBUI_NameNew
	, WEBUI_NameNewBreadcrumb=t.WEBUI_NameNewBreadcrumb
	, IsTranslated=t.IsTranslated
from backup.WEBUI_AD_Menu_Trl t
where t.AD_Menu_ID=m.AD_Menu_ID and t.AD_Language=m.AD_Language;


--
-- Insert missing entries for AD_Menu and AD_Menu_Trl
insert into AD_Menu
(ad_menu_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, name, updatedby, description, issummary, issotrx, isreadonly, action, ad_window_id, ad_workflow_id, ad_task_id, ad_process_id, ad_form_id, ad_workbench_id, entitytype, internalname, iscreatenew, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb)
select
ad_menu_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, name, updatedby, description, issummary, issotrx, isreadonly, action, ad_window_id, ad_workflow_id, ad_task_id, ad_process_id, ad_form_id, ad_workbench_id, entitytype, internalname, iscreatenew, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb
from backup.WEBUI_AD_Menu t
where
	not exists (select 1 from AD_Menu m where m.AD_Menu_ID=t.AD_Menu_ID)
	and (t.AD_Form_ID is null or exists(select 1 from AD_Form z where z.AD_Form_ID=t.AD_Form_ID))
;
--
insert into AD_Menu_Trl
(ad_menu_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, description, istranslated, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb)
select
ad_menu_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, description, istranslated, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb
from backup.WEBUI_AD_Menu_Trl t
where
	not exists (select 1 from AD_Menu_Trl m where m.AD_Menu_ID=t.AD_Menu_ID and m.AD_Language=t.AD_Language)
	and exists (select 1 from AD_Menu z where z.AD_Menu_ID=t.AD_Menu_ID)
;



--
-- Delete orphan trees of webui AD_Tree_ID
delete from AD_TreeNodeMM n
where 
n.AD_Tree_ID=1000039
and n.Node_ID in (
	select t.Node_ID
	from get_AD_TreeNodeMM_Paths(1000039, null) t
	where not IsCycle and t.Root_ID <> 0
	-- order by Path
);

--
-- Remove the Node_ID=1000007 (the webui subtree in AD_Tree_ID=10),
-- and move it's direct children to Parent_ID=0
delete from AD_TreeNodeMM where AD_Tree_ID=1000039 and node_id=1000007;
update AD_TreeNodeMM set Parent_ID=0 where AD_Tree_ID=1000039 and Parent_ID=1000007;


-- Check the webui menu
/*
select * from get_AD_TreeNodeMM_Paths(1000039, null) order by Path;
*/

