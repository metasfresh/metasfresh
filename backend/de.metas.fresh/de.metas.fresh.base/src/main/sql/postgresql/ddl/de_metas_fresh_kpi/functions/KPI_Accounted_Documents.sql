

DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_Accounter_Document (timestamp with time zone);

CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Accounter_Document (timestamp with time zone)
RETURNS TABLE 
(
	Accounted_Until_Today numeric,
	Accounter_From_Today numeric
)
AS
$$

select x.before :: numeric, y.after :: numeric
from
(

SELECT COUNT (DISTINCT fa.Record_ID) as before
FROM Fact_Acct fa
WHERE 
	fa.DateAcct >= ( date_trunc('year', now()) ) AND
	fa.DateAcct < (date_trunc('day', now() + interval '1 day')) -- 430894


) x
,
(
SELECT COUNT (DISTINCT fa.Record_ID) as after
FROM Fact_Acct fa
WHERE 
	fa.DateAcct >= (date_trunc('day', now() + interval '1 day')) AND
	fa.DateAcct < (date_trunc('year', now() + interval '1 year')) -- 1030


)y

$$
LANGUAGE sql STABLE;
