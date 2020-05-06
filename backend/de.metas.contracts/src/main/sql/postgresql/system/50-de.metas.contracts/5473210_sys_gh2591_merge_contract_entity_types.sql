/*

I figured out all the tables to update via

select R.TABLE_NAME,
'UPDATE ' || R.TABLE_NAME || ' SET EntityType=''de.metas.contracts'' WHERE EntityType=''de.metas.contracts.subscription'';'
from INFORMATION_SCHEMA.CONSTRAINT_COLUMN_USAGE u
inner join INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS FK
    on U.CONSTRAINT_CATALOG = FK.UNIQUE_CONSTRAINT_CATALOG
    and U.CONSTRAINT_SCHEMA = FK.UNIQUE_CONSTRAINT_SCHEMA
    and U.CONSTRAINT_NAME = FK.UNIQUE_CONSTRAINT_NAME
inner join INFORMATION_SCHEMA.KEY_COLUMN_USAGE R
    ON R.CONSTRAINT_CATALOG = FK.CONSTRAINT_CATALOG
    AND R.CONSTRAINT_SCHEMA = FK.CONSTRAINT_SCHEMA
    AND R.CONSTRAINT_NAME = FK.CONSTRAINT_NAME
WHERE U.COLUMN_NAME = 'entitytype'
--  AND U.TABLE_CATALOG = 'b'
  AND U.TABLE_SCHEMA = 'public'
  AND U.TABLE_NAME = 'ad_entitytype'

Thanks to https://stackoverflow.com/a/5347136/1012103
  
 */

-- 2017-09-28T16:41:14.161
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_EntityType (AD_Client_ID,AD_EntityType_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,IsDisplayed,ModelPackage,Name,Processing,Updated,UpdatedBy) VALUES (0,540214,0,TO_TIMESTAMP('2017-09-28 16:41:13','YYYY-MM-DD HH24:MI:SS'),100,'','de.metas.contracts','Y','Y','de.metas.contracts.model','de.metas.contracts','N',TO_TIMESTAMP('2017-09-28 16:41:13','YYYY-MM-DD HH24:MI:SS'),100)
;



UPDATE ad_table SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE c_queue_packageprocessor SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_message SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE c_doctype SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_sysconfig SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_scheduler SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_process SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_column SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_columncallout SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_element SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_field SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_field_contextmenu SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_fieldgroup SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_tab SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_val_rule SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_form SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_image SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_index_column SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_index_table SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_infocolumn SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_infowindow SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_infowindow_from SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_inputdatasource SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_javaclass SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_javaclass_type SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_jaxrs_endpoint SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_menu SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_migration SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_modelvalidator SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_modification SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_poprocessor SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_process_para SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_ref_list SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_ref_table SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_reference SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_relationtype SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_replicationstrategy SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_replicationtable SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_reportview SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_rule SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_tab_callout SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_table_process SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_task SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_triggerui SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_triggerui_action SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_triggerui_criteria SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_val_rule_included SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_wf_nextcondition SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_wf_node SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_wf_node_para SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_wf_nodenext SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_wf_responsible SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_window SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_workbench SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_workbenchwindow SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE ad_workflow SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE c_aggregation SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE c_aggregation_attribute SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE c_aggregationitem SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE c_ilcandhandler SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE c_olcandgenerator SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE c_pricingrule SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE c_referenceno_type SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE c_referenceno_type_table SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE exp_format SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE exp_formatline SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE imp_requesthandler SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE imp_requesthandlertype SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE pa_colorschema SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE pa_measurecalc SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE pp_order_node SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE pp_order_nodenext SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE pp_order_workflow SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';
UPDATE pp_wf_node_product SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.flatrate';

DELETE FROM AD_EntityType WHERE Name='de.metas.flatrate';

UPDATE ad_table SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE c_queue_packageprocessor SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_message SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE c_doctype SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_sysconfig SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_scheduler SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_process SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_column SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_columncallout SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_element SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_field SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_field_contextmenu SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_fieldgroup SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_tab SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_val_rule SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_form SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_image SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_index_column SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_index_table SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_infocolumn SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_infowindow SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_infowindow_from SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_inputdatasource SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_javaclass SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_javaclass_type SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_jaxrs_endpoint SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_menu SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_migration SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_modelvalidator SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_modification SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_poprocessor SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_process_para SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_ref_list SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_ref_table SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_reference SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_relationtype SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_replicationstrategy SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_replicationtable SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_reportview SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_rule SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_tab_callout SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_table_process SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_task SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_triggerui SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_triggerui_action SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_triggerui_criteria SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_val_rule_included SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_wf_nextcondition SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_wf_node SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_wf_node_para SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_wf_nodenext SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_wf_responsible SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_window SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_workbench SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_workbenchwindow SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE ad_workflow SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE c_aggregation SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE c_aggregation_attribute SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE c_aggregationitem SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE c_ilcandhandler SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE c_olcandgenerator SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE c_pricingrule SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE c_referenceno_type SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE c_referenceno_type_table SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE exp_format SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE exp_formatline SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE imp_requesthandler SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE imp_requesthandlertype SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE pa_colorschema SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE pa_measurecalc SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE pp_order_node SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE pp_order_nodenext SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE pp_order_workflow SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';
UPDATE pp_wf_node_product SET EntityType='de.metas.contracts' WHERE EntityType='de.metas.contracts.subscription';

DELETE FROM AD_EntityType WHERE Name='de.metas.contracts.subscription';