drop index if exists AD_Window_Access_UQ;
drop index if exists AD_Process_Access_UQ;
drop index if exists AD_Form_Access_UQ;
drop index if exists AD_Task_Access_UQ;
drop index if exists AD_Workflow_Access_UQ;
drop index if exists AD_Document_Action_Access_UQ;
drop index if exists AD_Role_OrgAccess_UQ;

create unique index if not exists AD_Window_Access_UQ on AD_Window_Access (AD_Role_ID, AD_Window_ID);
create unique index if not exists AD_Process_Access_UQ on AD_Process_Access (AD_Role_ID, AD_Process_ID);
create unique index if not exists AD_Form_Access_UQ on AD_Form_Access (AD_Role_ID, AD_Form_ID);
create unique index if not exists AD_Task_Access_UQ on AD_Task_Access (AD_Role_ID, AD_Task_ID);
create unique index if not exists AD_Workflow_Access_UQ on AD_Workflow_Access (AD_Role_ID, AD_Workflow_ID);
create unique index if not exists AD_Document_Action_Access_UQ on AD_Document_Action_Access (AD_Role_ID, C_DocType_ID, AD_Ref_List_ID);
create unique index if not exists AD_Role_OrgAccess_UQ on AD_Role_OrgAccess (AD_Role_ID, AD_Org_ID);

