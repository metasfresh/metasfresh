-- thx @teosarca
UPDATE c_project_wo_step s
SET datestart=
        COALESCE(
                (SELECT MIN(r.assigndatefrom) FROM c_project_wo_resource r WHERE r.c_project_wo_step_id = s.c_project_wo_step_id),
                '2000-01-01'),
    dateend=
        COALESCE((SELECT MAX(r.assigndateto) FROM c_project_wo_resource r WHERE r.c_project_wo_step_id = s.c_project_wo_step_id),
                 '9999-12-31')
WHERE 1 = 1
;