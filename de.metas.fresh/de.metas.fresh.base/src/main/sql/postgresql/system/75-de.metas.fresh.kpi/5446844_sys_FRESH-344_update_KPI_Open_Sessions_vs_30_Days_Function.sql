DROP FUNCTION IF EXISTS KPI_Open_Sessions_vs_30_Days_Function (IN CurrentDate date);
DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_Open_Sessions_vs_30_Days_Function (IN CurrentDate date);

CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Open_Sessions_vs_30_Days_Function (IN CurrentDate date) 
RETURNS TABLE 
(
	CurrentOpenSessions bigint,
	AVGPOpenSessions numeric
)
AS
$$


SELECT



x.CurrentOpenSessions, SUM(y.Sessions) / 30 AS AVGPOpenSessions
FROM 

(

SELECT COUNT(s.AD_Session_ID) AS CurrentOpenSessions
FROM AD_Session s
WHERE s.loginDate = $1 AND s.Processed = 'N'
) AS x,
(

SELECT COUNT(s.AD_Session_ID) AS Sessions
FROM AD_Session s

WHERE s.LoginDate >=  ((date ($1) - interval '1 day') - interval '29 days')
	AND s.LoginDate <$1 AND s.Processed = 'N'

GROUP BY s.LoginDate

ORDER BY s.LoginDate

)y


GROUP BY x.CurrentOpenSessions


$$
LANGUAGE sql STABLE;

