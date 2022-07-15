
-- thx @teosarca
UPDATE c_project_wo_step s
SET datestart=(SELECT MIN(r.assigndatefrom) FROM c_project_wo_resource r WHERE r.c_project_wo_step_id = s.c_project_wo_step_id),
    dateend=(SELECT MAX(r.assigndateto) FROM c_project_wo_resource r WHERE r.c_project_wo_step_id = s.c_project_wo_step_id)
WHERE 1 = 1
;