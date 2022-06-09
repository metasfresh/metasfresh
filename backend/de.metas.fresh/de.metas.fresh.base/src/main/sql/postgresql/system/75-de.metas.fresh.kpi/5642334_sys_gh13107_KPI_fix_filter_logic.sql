-- Increase staleTime to 1 minute, fix details filter criteria
-- 2022-06-08T07:03:48.080Z
UPDATE WEBUI_KPI SET KPI_AllowedStaledTimeInSec=60, SQL_Details_WhereClause='isSoTrx = ''Y'' AND DateOrdered <= NOW()::DATE AND DateOrdered >= NOW()::DATE AND DocStatus IN (''CO'', ''CL'') AND IsActive = ''Y'' AND ad_org_id = @AD_Org_ID / 0@ AND ad_client_id = @AD_Client_ID / -1@',Updated=TO_TIMESTAMP('2022-06-08 10:03:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=540036
;

-- 2022-06-08T07:04:18.327Z
UPDATE WEBUI_KPI SET KPI_AllowedStaledTimeInSec=60, SQL_Details_WhereClause='isSoTrx = ''Y'' AND DateOrdered <= NOW()::DATE AND  DateOrdered >= (NOW() - (DATE_PART(''dow'', NOW()) - 1) * ''1 day''::interval)::date AND DocStatus IN (''CO'', ''CL'')  AND IsActive = ''Y'' AND ad_org_id = @AD_Org_ID / 0@ AND ad_client_id = @AD_Client_ID / -1@',Updated=TO_TIMESTAMP('2022-06-08 10:04:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=540037
;

-- 2022-06-08T07:04:30.115Z
UPDATE WEBUI_KPI SET KPI_AllowedStaledTimeInSec=60, SQL_Details_WhereClause='isSoTrx = ''N'' AND DateOrdered <= NOW()::DATE AND  DateOrdered >= NOW()::DATE AND DocStatus IN (''CO'', ''CL'') AND IsActive = ''Y'' AND ad_org_id = @AD_Org_ID / 0@ AND ad_client_id = @AD_Client_ID / -1@',Updated=TO_TIMESTAMP('2022-06-08 10:04:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=540038
;

-- 2022-06-08T07:04:41.926Z
UPDATE WEBUI_KPI SET KPI_AllowedStaledTimeInSec=60, SQL_Details_WhereClause='isSoTrx = ''N'' AND DateOrdered <= NOW()::DATE AND  DateOrdered >= (NOW() - (DATE_PART(''dow'', NOW()) - 1) * ''1 day''::interval)::date AND DocStatus IN (''CO'', ''CL'') AND IsActive = ''Y'' AND ad_org_id = @AD_Org_ID / 0@ AND ad_client_id = @AD_Client_ID / -1@',Updated=TO_TIMESTAMP('2022-06-08 10:04:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE WEBUI_KPI_ID=540040
;
