-- https://drive.google.com/file/d/0BxR8srpWrnzvZmswWkhXZ0FHc1U/view

--
-- Backup
create table backup.AD_TreeNodeMM_BKP_BeforeImportWebUI as select * from AD_TreeNodeMM;
create table backup.AD_Menu_BKP_BeforeImportWebUI as select * from AD_Menu;
create table backup.AD_Menu_Trk_BKP_BeforeImportWebUI as select * from AD_Menu_Trl;

--
-- Expected input tables:
-- backup.WEBUI_AD_TreeNodeMM
-- backup.WEBUI_AD_Menu
-- backup.WEBUI_AD_Menu_Trl

--
-- Make sure we have the webui's AD_Tree entry
drop table if exists TMP_AD_Tree_ToImport;
create temporary table TMP_AD_Tree_ToImport as select ad_tree_id, ad_client_id, ad_org_id, created, createdby, updated, updatedby, isactive, name, description, treetype, isallnodes, processing, isdefault, ad_table_id from AD_Tree where 1=2;
INSERT INTO TMP_AD_Tree_ToImport (ad_tree_id, ad_client_id, ad_org_id, created, createdby, updated, updatedby, isactive, name, description, treetype, isallnodes, processing, isdefault, ad_table_id)
VALUES (1000039, 0, 0, '2016-09-21 18:06:52+02', 100, '2016-09-22 14:15:19+02', 100, 'Y', 'webUI Menu', 'webUI Menu', 'MM', 'N', 'N', 'N', 116);
--
insert into AD_Tree (ad_tree_id, ad_client_id, ad_org_id, created, createdby, updated, updatedby, isactive, name, description, treetype, isallnodes, processing, isdefault, ad_table_id)
select ad_tree_id, ad_client_id, ad_org_id, created, createdby, updated, updatedby, isactive, name, description, treetype, isallnodes, processing, isdefault, ad_table_id
from TMP_AD_Tree_ToImport t
where not exists (select 1 from AD_Tree z where z.AD_Tree_ID=t.AD_Tree_ID);

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
where not exists (select 1 from AD_Menu m where m.AD_Menu_ID=t.AD_Menu_ID);
--
insert into AD_Menu_Trl
(ad_menu_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, description, istranslated, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb)
select
ad_menu_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, description, istranslated, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb
from backup.WEBUI_AD_Menu_Trl t
where not exists (select 1 from AD_Menu_Trl m where m.AD_Menu_ID=t.AD_Menu_ID and m.AD_Language=t.AD_Language);

