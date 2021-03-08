DROP VIEW IF EXISTS ExternalSystem_Config_PInstance_Log_v
;

CREATE OR REPLACE VIEW ExternalSystem_Config_PInstance_Log_v AS
SELECT (i.ad_issue_id * 10) + 2                            AS ExternalSystem_Config_PInstance_Log_v_ID,
       pi.ad_process_id,
       pip_request.info                                      AS External_Request,
       null                                                  as p_msg,
       i.created                                             as p_date,
       pi.ad_client_id,
       COALESCE(
               NULLIF(pi.record_id, '0'), /*Record_ID is not set then the process was called from AD_Scheduler */
               cast_to_numeric_or_null(pip_config.p_string)) AS ExternalSystem_Config_ID,
       pi.ad_org_id,
       pi.ad_pinstance_id,
       pi.isprocessing,
       pi.errormsg,
       i.ad_issue_id                                         as ad_issue_id,
       i.issuesummary                                        as issuesummary,
       'Error'                                               as type,
       now()                                                 as created,
       100                                                   as createdby,
       now()                                                 as updated,
       100                                                   as updatedby,
       'Y'                                                   as isactive
from ad_pinstance pi
         JOIN ad_pinstance_para pip_request ON pi.ad_pinstance_id = pip_request.ad_pinstance_id AND pip_request.parametername = 'External_Request'
         JOIN ad_issue i on i.ad_pinstance_id = pi.ad_pinstance_id
         LEFT JOIN ad_pinstance_para pip_config ON pi.ad_pinstance_id = pip_config.ad_pinstance_id AND pip_config.parametername = 'ChildConfigId'
WHERE pi.AD_Table_ID = get_table_id('ExternalSystem_Config') /*AD_Table_ID is not set then the process was called from AD_Scheduler */
   OR pip_config.parametername = 'ChildConfigId'

UNION
SELECT (pil.ad_pinstance_log_id * 10) + 1                    AS ExternalSystem_Config_PInstance_Log_v_ID,
       pinstance.ad_process_id,
       pip_request.info                                      AS External_Request,
       pil.p_msg,
       pil.p_date,
       pinstance.ad_client_id,
       COALESCE(
               NULLIF(pinstance.record_id, '0'), /*Record_ID is not set then the process was called from AD_Scheduler */
               cast_to_numeric_or_null(pip_config.p_string)) AS ExternalSystem_Config_ID,
       pinstance.ad_org_id,
       pinstance.ad_pinstance_id,
       pinstance.isprocessing,
       pinstance.errormsg,
       null                                                  as ad_issue_id,
       null                                                  as issuesummary,
       'Info'                                                as type,
       now()                                                 as created,
       100                                                   as createdby,
       now()                                                 as updated,
       100                                                   as updatedby,
       'Y'                                                   as isactive

from ad_pinstance pinstance
         JOIN ad_pinstance_para pip_request ON pinstance.ad_pinstance_id = pip_request.ad_pinstance_id AND pip_request.parametername = 'External_Request'
         JOIN ad_pinstance_log pil ON pil.ad_pinstance_id = pinstance.ad_pinstance_id
         LEFT JOIN ad_pinstance_para pip_config ON pinstance.ad_pinstance_id = pip_config.ad_pinstance_id AND pip_config.parametername = 'ChildConfigId'
WHERE pinstance.AD_Table_ID = get_table_id('ExternalSystem_Config') /*AD_Table_ID is not set then the process was called from AD_Scheduler */
   OR pip_config.parametername = 'ChildConfigId'
;
