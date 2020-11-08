UPDATE ad_wf_process
SET wf_initial_user_id=createdby
WHERE wf_initial_user_id IS NULL
;

