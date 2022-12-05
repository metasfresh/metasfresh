DROP FUNCTION IF EXISTS getC_Project_IDs_UpStream(p_C_Project_ID numeric)
;

DROP FUNCTION IF EXISTS getC_Project_IDs_UpStream(
    p_C_Project_ID  numeric,
    p_C_Project_IDs numeric[]
)
;

CREATE OR REPLACE FUNCTION getC_Project_IDs_UpStream(
    p_C_Project_ID  numeric = NULL,
    p_C_Project_IDs numeric[] = NULL
)
    RETURNS table
            (
                c_project_id        numeric,
                C_Project_Parent_ID numeric,
                start_project_id    numeric,
                depth               integer,
                projectCategory     VARCHAR,
                isactive            char,
                c_bpartner_id       numeric
            )
    LANGUAGE sql
    IMMUTABLE
AS
$$
WITH RECURSIVE project AS (SELECT c_project_id                  AS start_project_id,
                                  c_project_id,
                                  C_Project_Parent_ID,
                                  ARRAY [c_project_id::integer] AS path,
                                  0::integer                    AS depth,
                                  projectCategory::varchar      AS projectcategory,
                                  isactive,
                                  c_bpartner_id
                           FROM c_project
                           WHERE (
                                         (p_C_Project_ID IS NOT NULL AND c_project_id = p_C_Project_ID)
                                         OR (p_C_Project_IDs IS NOT NULL AND c_project_id = ANY (p_C_Project_IDs))
                                     )
                           UNION ALL
                           SELECT child.start_project_id,
                                  parent.c_project_id,
                                  parent.C_Project_Parent_ID,
                                  child.path || parent.c_project_id::integer AS path,
                                  depth + 1,
                                  parent.projectCategory,
                                  parent.isactive,
                                  parent.c_bpartner_id
                           FROM project child
                                    INNER JOIN c_project parent ON parent.c_project_id = child.C_Project_Parent_ID
                           WHERE NOT (parent.c_project_id::integer = ANY (child.path)) -- avoid cycles
)
SELECT c_project_id,
       C_Project_Parent_ID,
       start_project_id,
       depth,
       projectCategory,
       isactive,
       c_bpartner_id
FROM project
$$
;

