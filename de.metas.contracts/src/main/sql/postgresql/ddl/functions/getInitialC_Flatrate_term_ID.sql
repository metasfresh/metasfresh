CREATE OR REPLACE FUNCTION de_metas_contracts.getInitialC_Flatrate_term_ID(p_C_Flatrate_term_ID numeric)
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