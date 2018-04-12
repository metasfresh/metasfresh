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
comment on function de_metas_contracts.fetchflatratetermhierarchy_byC_Flatrate_Term_id(numeric) is 'This function returns the hierachy chain of a contract. It start from the initial contract (which is provided by function fetchInitialC_Flatrate_term_ID) and goes recursivelly building the inheritence tree and put it in an array. The function only look foraward, based on the link current.c_flatrateterm_next_id = next.c_flatrate_term_id
E.g. We have next inheritence tree: 1000281->1000282->1000283->1000284->1000285. The result if we run for 1000281 is  1000281;{1000281,1000282,1000283,1000284,1000285,NULL}. And the result if we run for 1000283 or 1000285  1000281;{1000281,1000282,1000283,1000284,1000285,NULL}';