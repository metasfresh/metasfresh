
/*
Note: on at least one legacy-DB there are ancient ad_wf_process records with not-more existing createdBy-AD_Users
*/
UPDATE ad_wf_process p
SET wf_initial_user_id=u.ad_user_id
FROM AD_User u
WHERE TRUE
  and u.ad_user_ID=p.createdby
  and p.wf_initial_user_id IS NULL and u.ad_user_id is not null
;

UPDATE ad_wf_process p
SET wf_initial_user_id=99
WHERE p.wf_initial_user_id IS NULL
;
