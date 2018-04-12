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