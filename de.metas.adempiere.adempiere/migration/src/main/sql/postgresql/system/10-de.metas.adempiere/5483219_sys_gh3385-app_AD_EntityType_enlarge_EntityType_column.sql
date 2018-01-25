-- 2018-01-25T12:37:37.350
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=512, Help='The Entity Types "Dictionary", "Adempiere" and "Application" might be automatically synchronized and customizations deleted or overwritten.    For customizations, copy the entity and select "User"!',Updated=TO_TIMESTAMP('2018-01-25 12:37:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=15592
;

-- 2018-01-25T12:37:37.354
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Entitäts-Art', Description='Dictionary Entity Type; Determines ownership and synchronization', Help='The Entity Types "Dictionary", "Adempiere" and "Application" might be automatically synchronized and customizations deleted or overwritten.    For customizations, copy the entity and select "User"!' WHERE AD_Column_ID=15592
;

-- 2018-01-25T12:37:40.593
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('ad_entitytype','EntityType','VARCHAR(512)',null,null);
;

/*
I created the following statements with this SQL:

select 
	'INSERT INTO t_alter_column values('''||t.TableName||''','''||c.ColumnName||''',''VARCHAR(512)'',null,null);'
from AD_column c
	join AD_table t on t.AD_table_id=c.AD_table_ID
where c.AD_Element_ID=1682;
 */

INSERT INTO t_alter_column values('AD_Modification','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_ModelValidator','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_Table','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_Column','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_Index_Table','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_ColumnCallout','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_Index_Column','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_Table_Process','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_TriggerUI','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_TriggerUI_Action','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_TriggerUI_Criteria','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_POProcessor','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('EXP_Format','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('EXP_FormatLine','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('C_OLCandGenerator','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_Val_Rule_Included','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('C_DocType','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('C_PricingRule','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('C_ReferenceNo_Type_Table','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('C_ReferenceNo_Type','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_InputDataSource','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('C_ILCandHandler','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('IMP_RequestHandlerType','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('IMP_RequestHandler','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_Element','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_Process','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('C_Queue_PackageProcessor','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_Field_ContextMenu','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_Field','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_Tab_Callout','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_InfoWindow_From','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('C_AggregationItem','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('C_Aggregation','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('C_Aggregation_Attribute','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_Form','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_Window','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_Reference','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_Workbench','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_WorkbenchWindow','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_Ref_Table','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_Ref_List','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_Tab','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_Val_Rule','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_Message','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_Menu','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_Workflow','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_Task','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_WF_Node','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_Process_Para','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_ReplicationTable','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_ReplicationStrategy','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_FieldGroup','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_ReportView','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_WF_Node_Para','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_WF_Responsible','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_WF_NodeNext','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_Image','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_WF_NextCondition','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('PA_MeasureCalc','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('PA_ColorSchema','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_InfoWindow','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_InfoColumn','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_SysConfig','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('PP_WF_Node_Product','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('PP_Order_Node','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('PP_Order_NodeNext','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('PP_Order_Workflow','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_Rule','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_Migration','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_JAXRS_Endpoint','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_Scheduler','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_JavaClass_Type','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_RelationType','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_JavaClass','EntityType','VARCHAR(512)',null,null);
INSERT INTO t_alter_column values('AD_EntityType','EntityType','VARCHAR(512)',null,null);
