DROP FUNCTION IF EXISTS fetchflatratetermhierarchy_byC_Flatrate_Term_id(numeric);
DROP FUNCTION IF EXISTS fetchInitialC_Flatrate_term_ID(numeric);
DROP FUNCTION IF EXISTS getInitialC_Flatrate_term_ID(numeric);
DROP FUNCTION IF EXISTS fetchflatratetermhierarchy_byc_flatrate_term_id(numeric);

DROP FUNCTION IF EXISTS de_metas_contracts.fetchflatratetermhierarchy_byC_Flatrate_Term_id(numeric);
DROP FUNCTION IF EXISTS de_metas_contracts.fetchInitialC_Flatrate_term_ID(numeric);
DROP FUNCTION IF EXISTS de_metas_contracts.getInitialC_Flatrate_term_ID(numeric);
DROP FUNCTION IF EXISTS de_metas_contracts.fetchflatratetermhierarchy_byc_flatrate_term_id(numeric);

CREATE SCHEMA de_metas_contracts;  
CREATE OR REPLACE FUNCTION de_metas_contracts.fetchInitialC_Flatrate_term_ID(p_C_Flatrate_term_ID numeric)
  RETURNS TABLE(C_Flatrate_term_ID numeric) AS
$BODY$
WITH RECURSIVE ancestor AS (
  SELECT ft.c_flatrate_term_id as AncestorId, 0 as distance
  FROM c_flatrate_term ft
  WHERE ft.c_flatrate_term_id = p_C_Flatrate_term_ID

   UNION  ALL

  SELECT ft2.c_flatrate_term_ID, a.distance + 1
  FROM ancestor a 
  JOIN c_flatrate_term ft2 on a.AncestorId = ft2.c_flatrateterm_next_id
)
select AncestorId
from ancestor
where distance = (select max(distance) from ancestor);
$BODY$
  LANGUAGE sql STABLE
  COST 100
  ROWS 1000;
comment on function de_metas_contracts.fetchInitialC_Flatrate_term_ID(numeric) is 'This function returns the most distant parent of the givent contract. The function looks recursivelly in back and returns the farthest flatrate term.
E.g. If we have next tree 1000281->1000282->1000283->1000284->1000285 . For 1000281 distance will be 0 and is the only result, which means that is the initial flatrate term. But for 1000283, will have 3 lines: 1000283 with distance 0, 
1000282 with distance 1 and 1000281 with distance 2. And the function choose the biggest distance, so the farthest flatrate term. So will return 1000281.'; 

 
CREATE OR REPLACE FUNCTION de_metas_contracts.fetchflatratetermhierarchy_byC_Flatrate_Term_id(IN p_c_flatrate_term_id numeric)
  RETURNS TABLE(initial_ft_id numeric, path numeric[]) AS
$BODY$
 WITH RECURSIVE node_graph AS (
   SELECT ft.c_flatrate_term_id as initial_ft_id, ft.c_flatrateterm_next_id as next_ft_id, ARRAY[ft.c_flatrate_term_id, ft.c_flatrateterm_next_id] AS path
   FROM c_flatrate_term ft 
     JOIN de_metas_contracts.fetchInitialC_Flatrate_term_ID(p_c_flatrate_term_id) as parent on parent.c_flatrate_term_id = ft.c_flatrate_term_id 

   UNION  ALL

    SELECT ng.initial_ft_id, ft3.c_flatrateterm_next_id as next_ft_id,
           (ng.path || ft3.c_flatrateterm_next_id )::numeric(10,0)[] as path
    FROM node_graph ng
    JOIN c_flatrate_term ft3 ON ng.next_ft_id = ft3.c_flatrate_term_id
   )
SELECT *
from
    (
        SELECT n1.initial_ft_id, n1.path
        from node_graph AS n1
        where true
        and not exists (select 1 from node_graph as n2 where n1.path <@ n2.path)
    ) as data
where true
order by initial_ft_id;
$BODY$
  LANGUAGE sql STABLE
  COST 100 
  ROWS 1000;
comment on function de_metas_contracts.fetchflatratetermhierarchy_byC_Flatrate_Term_id(numeric) is 'This function returns the hierachy chain of a contract. It starts from the initial contract (which is provided by function fetchInitialC_Flatrate_term_ID) and goes recursivelly building the inheritence tree and put it in an array. The function only looks foraward, based on the link current.c_flatrateterm_next_id = next.c_flatrate_term_id
E.g. We have next inheritence tree: 1000281->1000282->1000283->1000284->1000285. If we run for 1000281 the result is  1000281;{1000281,1000282,1000283,1000284,1000285,NULL}. If we run for 1000283 or 1000285, the result is 1000281;{1000281,1000282,1000283,1000284,1000285,NULL}. So same as before, since is the same inheritance tree. Regardless of the point in the tree from where you start, the inheritence tree must be all the time the same.'; 