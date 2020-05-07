DROP FUNCTION IF EXISTS getC_AcctSchema_ID(numeric,numeric);
CREATE OR REPLACE FUNCTION getC_AcctSchema_ID(p_AD_Client_ID numeric, p_AD_OrgOnly_ID numeric)
  RETURNS numeric AS
$BODY$
DECLARE 
	v_C_AcctSchema_ID numeric;
BEGIN
	SELECT s.C_AcctSchema_ID
	INTO v_C_AcctSchema_ID
	FROM C_AcctSchema s
		LEFT JOIN AD_ClientInfo ci ON ci.C_AcctSchema1_ID=s.C_AcctSchema_ID
	WHERE true
		AND s.IsActive='Y' -- only active C_AcctSchemas
		AND COALESCE(ci.IsActive,'Y')='Y' /* if there is any AD_ClientInfo, it shall be active*/
		 
		AND (ci.AD_Client_ID = p_AD_Client_ID 
			OR (ci.AD_Client_ID IS NULL AND s.AD_Client_ID = p_AD_Client_ID)) -- if there is no AD_ClientInfo, fall back to C_AcctSchema.AD_Client_ID
		AND (s.AD_OrgOnly_ID = p_AD_OrgOnly_ID OR s.AD_OrgOnly_ID IS NULL)
	ORDER BY
		s.AD_OrgOnly_ID DESC NULLS LAST, -- matching AD_OrgOnly_ID first
		ci.AD_Client_ID DESC NULLS LAST  -- the one referenced by AD_ClientInfo first
	LIMIT 1;
	
	IF (v_C_AcctSchema_ID is null) THEN
		v_C_AcctSchema_ID := -1;
	END IF;
	RETURN v_C_AcctSchema_ID;
END;$BODY$
  LANGUAGE plpgsql STABLE
  COST 100;
  
ALTER FUNCTION getC_AcctSchema_ID(numeric, numeric)
  OWNER TO adempiere;

COMMENT ON FUNCTION getC_AcctSchema_ID(numeric, numeric) IS '
 This function returns the C_AcctSchema_ID of the record with the given AD_Client_ID and AD_OrgOnly_ID.
 Inactive records are ignored. If no C_AcctSchema record with the given AD_OrgOnly_ID exists, then it falls back and returns the record which is set in AD_ClientInfo. 
 IMPORTANT: Make sure that the C_AcctSchema referenced in AD_ClientInfo has AD_OrgOnly_ID=NULL (I think anything else wouldn''t make sense anyways)
 
 If no C_AcctSchema record is found at all, then the function returns -1
 
 See http://dewiki908/mediawiki/index.php/fresh_07391_Error_trying_to_create_an_invoice_when_you_have_more_then_one_accounting_schema';

