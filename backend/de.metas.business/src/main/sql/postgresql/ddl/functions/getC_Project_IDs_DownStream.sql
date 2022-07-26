DROP FUNCTION IF EXISTS getC_Project_IDs_DownStream(
    p_C_Project_ID  numeric,
    p_C_Project_IDs numeric[]
)
;


CREATE OR REPLACE FUNCTION getC_Project_IDs_DownStream(
    p_C_Project_ID  numeric = NULL,
    p_C_Project_IDs numeric[] = NULL
)
    RETURNS table
            (
                c_project_id        numeric,
                C_Project_Parent_ID numeric,
                start_project_id    NUMERIC,
                depth               integer,
                projectCategory     VARCHAR,
                isactive            char(1),
                c_bpartner_id       numeric
            )
    LANGUAGE sql
    IMMUTABLE
AS
$$
WITH RECURSIVE project AS (SELECT c_project_id                  AS start_project_id,
                                  C_Project_Parent_ID,
                                  c_project_id,
                                  ARRAY [c_project_id::integer] AS path,
                                  0::integer                    AS depth,
                                  projectCategory::varchar      AS projectcategory,
                                  isactive,
                                  c_bpartner_id
                           FROM c_project
                           WHERE (
                                         (p_c_project_id IS NOT NULL AND c_project_id = p_c_project_id)
                                         OR (p_c_project_ids IS NOT NULL AND c_project_id = ANY (p_c_project_ids))
                                     )
                           UNION ALL
                           SELECT parent.start_project_id,
                                  child.C_Project_Parent_ID,
                                  child.c_project_id,
                                  parent.path || child.c_project_id::integer AS path,
                                  parent.depth + 1,
                                  child.projectCategory,
                                  child.isactive,
                                  child.c_bpartner_id
                           FROM project parent
                                    INNER JOIN c_project child ON child.C_Project_Parent_ID = parent.c_project_id
                           WHERE NOT (child.c_project_id::integer = ANY (parent.path)) -- avoid cycles
)
SELECT c_project_id,
       C_Project_Parent_ID,
       start_project_id,
       depth,
       projectCategory,
       isactive,
       c_bpartner_id
FROM project
    -- ORDER BY level;
$$
;

