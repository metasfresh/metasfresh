DROP FUNCTION IF EXISTS getSysConfigAsNumeric ( IN name character varying, IN AD_Client_ID numeric, IN  AD_Org_ID numeric );
CREATE OR REPLACE FUNCTION getSysConfigAsNumeric ( IN name character varying, IN AD_Client_ID numeric, IN  AD_Org_ID numeric )
RETURNS NUMERIC
AS
$$
select value::numeric
from AD_SysConfig
where Name=$1
	and AD_Client_ID in (0, $2)
	and AD_Org_ID in (0, $3)
	and isActive = 'Y'
order by AD_Client_ID desc, AD_Org_ID desc
limit 1

$$
LANGUAGE sql STABLE	
;

