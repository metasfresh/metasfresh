

DROP FUNCTION IF EXISTS de_metas_fresh_kpi.KPI_Open_Sessions_Today_Function(date);

CREATE OR REPLACE FUNCTION de_metas_fresh_kpi.KPI_Open_Sessions_Today_Function (IN CurrentDate date) 
RETURNS TABLE 
(
	UserName character varying,
	IP character varying,
	LoginTime timestamp with time zone
)
AS
$$



SELECT

	LoginUserName AS UserName, Remote_Addr AS IP , Created AS LoginTime
	
FROM AD_Session s

WHERE 
	s.loginDate = $1 AND s.Processed = 'N' AND LoginUserName IS NOT NULL
	
	
ORDER BY Created
	
$$
LANGUAGE sql STABLE;