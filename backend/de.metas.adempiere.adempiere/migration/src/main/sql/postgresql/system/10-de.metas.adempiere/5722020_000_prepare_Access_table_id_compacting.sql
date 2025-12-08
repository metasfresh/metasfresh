--
-- Backup access tables that we will change
select backup_table('AD_Window_Access');
select backup_table('AD_Process_Access');
select backup_table('AD_Form_Access');
select backup_table('AD_Workflow_Access');
select backup_table('AD_Task_Access');
select backup_table('AD_Document_Action_Access');


--
-- Remove all generated access records in order to have an efficient IDs compacting
delete from ad_window_access where ad_role_id in (select ad_role_id from ad_role where isactive='Y' and ismanual='N');
delete from ad_process_access where ad_role_id in (select ad_role_id from ad_role where isactive='Y' and ismanual='N');
delete from ad_form_access where ad_role_id in (select ad_role_id from ad_role where isactive='Y' and ismanual='N');
delete from ad_workflow_access where ad_role_id in (select ad_role_id from ad_role where isactive='Y' and ismanual='N');
delete from ad_task_access where ad_role_id in (select ad_role_id from ad_role where isactive='Y' and ismanual='N');
delete from ad_document_action_access where ad_role_id in (select ad_role_id from ad_role where isactive='Y' and ismanual='N');

