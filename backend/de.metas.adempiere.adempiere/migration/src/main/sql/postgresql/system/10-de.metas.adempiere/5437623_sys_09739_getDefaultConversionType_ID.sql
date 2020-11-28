DROP FUNCTION IF EXISTS getDefaultConversionType_ID(p_client_id numeric, p_org_id numeric, p_convdate timestamp with time zone);

CREATE OR REPLACE FUNCTION getDefaultConversionType_ID(p_client_id numeric, p_org_id numeric, p_convdate timestamp with time zone)
RETURNS numeric AS
$BODY$
	-- NOTE to developer: keep in sync with org.adempiere.service.impl.CurrencyDAO.retrieveDefaultConversionType(Properties, int, int, Date)
	
	SELECT C_ConversionType_ID 
	FROM C_ConversionType_Default
	WHERE true
		AND AD_Client_ID IN (0, $1)
		AND AD_Org_ID IN (0, $2)
		AND ValidFrom <= TRUNC($3, 'DD')
	ORDER BY
		ValidFrom DESC
		, AD_Client_ID DESC
		, AD_Org_ID DESC
	LIMIT 1;
$BODY$
LANGUAGE SQL STABLE
;
