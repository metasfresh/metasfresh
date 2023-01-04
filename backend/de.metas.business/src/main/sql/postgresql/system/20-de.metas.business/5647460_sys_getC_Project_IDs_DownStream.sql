DROP FUNCTION IF EXISTS getC_Project_IDs_DownStream(p_C_Project_ID numeric)
;

CREATE OR REPLACE FUNCTION getC_Project_IDs_DownStream(p_C_Project_ID numeric)
    RETURNS table
            (
                c_project_id        numeric,
                C_Project_Parent_ID numeric
            )
    LANGUAGE sql
    IMMUTABLE
AS
$$
WITH RECURSIVE project AS (SELECT C_Project_Parent_ID,
                                  c_project_id,
                                  ARRAY [c_project_id::integer] AS path,
                                  0                             AS level,
                                  projectCategory
                           FROM c_project
                           WHERE c_project_id = getC_Project_IDs_DownStream.p_c_project_id
                           UNION ALL
                           SELECT child.C_Project_Parent_ID,
                                  child.c_project_id,
                                  parent.path || child.c_project_id::integer AS path,
                                  parent.level + 1,
                                  child.projectCategory
                           FROM project parent
                                    INNER JOIN c_project child ON child.C_Project_Parent_ID = parent.c_project_id
                           WHERE NOT (child.c_project_id::integer = ANY (parent.path)) -- avoid cycles
)
SELECT c_project_id, C_Project_Parent_ID
FROM project
ORDER BY level;
$$
;

