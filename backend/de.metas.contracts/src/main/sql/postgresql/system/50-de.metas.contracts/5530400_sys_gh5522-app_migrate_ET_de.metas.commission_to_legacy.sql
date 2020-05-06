-- 2019-09-11T12:26:29Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_EntityType (AD_Client_ID,AD_EntityType_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,IsDisplayed,ModelPackage,Name,Processing,Updated,UpdatedBy) VALUES (0,540254,0,TO_TIMESTAMP('2019-09-11 14:26:28','YYYY-MM-DD HH24:MI:SS'),100,'metasfresh LEGACY commission','de.metas.commission_legacy','This entity type is used to flag the aprox. 10 years old "advanced commission" legacy records that still exist in the database','Y','N','','de.metas.commission_legacy','N',TO_TIMESTAMP('2019-09-11 14:26:28','YYYY-MM-DD HH24:MI:SS'),100)
;


/*
--The following updates are created with this SQL:
select --*, 
'UPDATE '||"FK_Table"||' SET '||"FK_Column"||'=''de.metas.commission_legacy''/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE '||"FK_Column"||'=''de.metas.commission''/*old ET name*/;'
from
(
SELECT conrelid::regclass AS "FK_Table"
      ,CASE WHEN pg_get_constraintdef(c.oid) LIKE 'FOREIGN KEY %' THEN substring(pg_get_constraintdef(c.oid), 14, position(')' in pg_get_constraintdef(c.oid))-14) END AS "FK_Column"
      ,CASE WHEN pg_get_constraintdef(c.oid) LIKE 'FOREIGN KEY %' THEN substring(pg_get_constraintdef(c.oid), position(' REFERENCES ' in pg_get_constraintdef(c.oid))+12, position('(' in substring(pg_get_constraintdef(c.oid), 14))-position(' REFERENCES ' in pg_get_constraintdef(c.oid))+1) END AS "PK_Table"
      ,CASE WHEN pg_get_constraintdef(c.oid) LIKE 'FOREIGN KEY %' THEN substring(pg_get_constraintdef(c.oid), position('(' in substring(pg_get_constraintdef(c.oid), 14))+14, position(')' in substring(pg_get_constraintdef(c.oid), position('(' in substring(pg_get_constraintdef(c.oid), 14))+14))-1) END AS "PK_Column"
FROM pg_constraint c
JOIN pg_namespace n ON n.oid = c.connamespace
WHERE contype IN ('f', 'p ')
	AND pg_get_constraintdef(c.oid) LIKE 'FOREIGN KEY %'
ORDER  BY pg_get_constraintdef(c.oid), conrelid::regclass::text, contype DESC
) foo
where "PK_Table"='ad_entitytype' AND "PK_Column"='entitytype' --AND "FK_Table" NOT IN ('c_uom','c_uom_conversion','c_uom_trl')
;
-- Many thanks to https://dba.stackexchange.com/a/156684/127433 and others
*/

UPDATE ad_column SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_element SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_field SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_fieldgroup SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_form SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_image SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_index_column SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_index_table SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_infocolumn SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_infowindow SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_menu SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_message SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_modelvalidator SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_process SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_process_para SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_reference SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_ref_list SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_ref_table SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_replicationstrategy SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_replicationtable SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_reportview SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_rule SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_sysconfig SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_tab SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_table SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_task SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_val_rule SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_wf_nextcondition SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_wf_node SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_wf_nodenext SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_wf_node_para SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_wf_responsible SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_window SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_workbench SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_workbenchwindow SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_workflow SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE pa_colorschema SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE pa_measurecalc SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE pp_order_nodenext SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE pp_wf_node_product SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_columncallout SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_field_contextmenu SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_inputdatasource SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_javaclass SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_javaclass_type SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_jaxrs_endpoint SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_migration SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_modification SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_notificationgroup SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_poprocessor SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_relationtype SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_scheduler SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_tab_callout SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_table_process SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_triggerui SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_triggerui_action SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_triggerui_criteria SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_val_rule_included SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE c_aggregation SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE c_aggregation_attribute SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE c_aggregationitem SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE c_doctype SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE c_ilcandhandler SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE c_pricingrule SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE c_queue_packageprocessor SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE c_referenceno_type SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE c_referenceno_type_table SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE exp_format SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE exp_formatline SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE imp_requesthandler SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE imp_requesthandlertype SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
UPDATE ad_infowindow_from SET entitytype='de.metas.commission_legacy'/*new ET name*/, Updated=now(), UpdatedBy=99 WHERE entitytype='de.metas.commission'/*old ET name*/;
