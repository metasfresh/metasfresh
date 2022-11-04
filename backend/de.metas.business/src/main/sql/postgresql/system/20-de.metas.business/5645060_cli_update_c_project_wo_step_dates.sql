UPDATE c_project_wo_step s
SET datestart=(SELECT min(r.assigndatefrom) FROM c_project_wo_resource r WHERE r.c_project_wo_step_id = s.c_project_wo_step_id)
WHERE s.datestart IS NULL
;

UPDATE c_project_wo_step s
SET dateend=(SELECT max(r.assigndateto) FROM c_project_wo_resource r WHERE r.c_project_wo_step_id = s.c_project_wo_step_id)
WHERE s.dateend IS NULL
;


-- select * from c_project_wo_step where (datestart is null or dateend is null);

