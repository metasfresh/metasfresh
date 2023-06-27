DROP FUNCTION IF EXISTS getC_Project_IDs_UpStream(p_C_Project_ID numeric)
;

CREATE OR REPLACE FUNCTION getC_Project_IDs_UpStream(p_C_Project_ID numeric)
    RETURNS table
            (
                c_project_id numeric
            )
    LANGUAGE sql
    IMMUTABLE
AS
$$
WITH RECURSIVE project AS (SELECT c_project_id,
                                  C_Project_Parent_ID,
                                  ARRAY [c_project_id::integer] AS path,
                                  0::integer                    AS level,
                                  projectCategory
                           FROM c_project
                           WHERE c_project_id = getC_Project_IDs_UpStream.p_c_project_id
                           UNION ALL
                           SELECT parent.c_project_id,
                                  parent.C_Project_Parent_ID,
                                  child.path || parent.c_project_id::integer AS path,
                                  level + 1,
                                  parent.projectCategory
                           FROM project child
                                    INNER JOIN c_project parent ON parent.c_project_id = child.C_Project_Parent_ID
                           WHERE NOT (parent.c_project_id::integer = ANY (child.path)) -- avoid cycles
)
SELECT c_project_id
FROM project
ORDER BY level;
$$
;
