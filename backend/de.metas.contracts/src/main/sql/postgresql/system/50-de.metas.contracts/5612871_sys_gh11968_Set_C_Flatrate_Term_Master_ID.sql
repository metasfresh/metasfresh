CREATE TABLE backup.c_flatrate_term_gh11968 AS
SELECT *
FROM c_flatrate_term
;


drop table if exists c_flatrate_term_temp;
create temporary table c_flatrate_term_temp AS
WITH RECURSIVE node_graph AS (
    SELECT ft.c_flatrate_term_id AS initial_ft_id, ft.c_flatrateterm_next_id AS next_ft_id, ARRAY [ft.c_flatrate_term_id, ft.c_flatrateterm_next_id] AS path
    FROM (SELECT * FROM c_flatrate_term ORDER BY c_flatrate_term_id) AS ft

    UNION ALL

    SELECT ng.initial_ft_id,
           y.c_flatrateterm_next_id                                AS next_ft_id,
           (ng.path || y.c_flatrateterm_next_id)::numeric(10, 0)[] AS path
    FROM node_graph ng,
         c_flatrate_term AS y
    WHERE ng.next_ft_id = y.c_flatrate_term_id
)
SELECT *
FROM node_graph
WHERE next_ft_id IS NULL
;

CREATE INDEX Index_c_flatrate_term_temp ON c_flatrate_term_temp(initial_ft_id);


drop table if exists tmp_master_terms;
create TEMPORARY  table tmp_master_terms as
SELECT
    ft.c_flatrate_term_id as master_flatrateterm_id,
    ft.c_flatrateterm_next_id
FROM c_flatrate_term ft
WHERE NOT EXISTS(SELECT
                 FROM c_flatrate_term z
                 WHERE z.c_flatrateterm_next_id = ft.c_flatrate_term_id);


with records AS
         (
             SELECT master_flatrateterm_id, t.path
             FROM c_flatrate_term_temp t
                      JOIN tmp_master_terms m ON t.initial_ft_id = m.master_flatrateterm_id
             ORDER BY master_flatrateterm_id
         )
update c_flatrate_term set c_flatrate_term_master_id= records.master_flatrateterm_id
from records
where c_flatrate_term.c_flatrate_term_ID = ANY(path);


