
DROP VIEW IF EXISTS ExternalSystem_Config_PInstance_Log_v;

CREATE OR REPLACE VIEW ExternalSystem_Config_PInstance_Log_v AS
SELECT pi.ad_process_id,
       pil.ad_pinstance_id                                                               AS ExternalSystem_Config_PInstance_Log_v_ID,
       pip_request.info                                                                  AS External_Request,
       pil.p_msg,
       pil.p_date,
       pi.ad_client_id,
       COALESCE(
               NULLIF(pi.record_id, '0'), /*Record_ID is not set then the process was called from AD_Scheduler */
               cast_to_numeric_or_null(pip_config.p_string)) AS ExternalSystem_Config_ID,
       pi.ad_org_id,
       pi.ad_pinstance_id,
       pi.isprocessing,
       pi.errormsg,
       now() as created,
       100 as createdby,
       now() as updated,
       100 as updatedby,
       'Y' as isactive
FROM ad_pinstance pi
         JOIN ad_pinstance_para pip_request ON pi.ad_pinstance_id = pip_request.ad_pinstance_id AND pip_request.parametername = 'External_Request'
         LEFT JOIN ad_pinstance_para pip_config ON pi.ad_pinstance_id = pip_config.ad_pinstance_id AND pip_config.parametername = 'ChildConfigId'
         JOIN ad_pinstance_log pil ON pil.ad_pinstance_id = pi.ad_pinstance_id
WHERE pi.AD_Table_ID = get_table_id('ExternalSystem_Config') /*AD_Table_ID is not set then the process was called from AD_Scheduler */
   OR pip_config.parametername = 'ChildConfigId'
;