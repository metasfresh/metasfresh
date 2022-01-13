-- api_request_audit
CREATE INDEX IF NOT EXISTS api_response_audit_api_audit_config_id
    ON api_request_audit USING btree (api_audit_config_id);

CREATE INDEX IF NOT EXISTS api_response_audit_api_ad_user_id
    ON api_request_audit USING btree (ad_user_id);

CREATE INDEX IF NOT EXISTS api_response_audit_remotehost
    ON api_request_audit USING btree (remotehost);

CREATE INDEX IF NOT EXISTS api_response_audit_iserroracknowleged
    ON api_request_audit USING btree (iserroracknowleged);

CREATE INDEX IF NOT EXISTS api_response_audit_time
    ON api_request_audit USING btree (time);

-- api_request_audit_log
CREATE INDEX IF NOT EXISTS api_response_audit_log_api_request_audit_id
    ON api_request_audit_log USING btree (api_request_audit_id);

CREATE INDEX IF NOT EXISTS api_response_audit_log_ad_issue_id
    ON api_request_audit_log USING btree (ad_issue_id);

-- api_response_audit
CREATE INDEX IF NOT EXISTS api_response_audit_api_request_audit_id
    ON api_response_audit USING btree (api_request_audit_id);

CREATE INDEX IF NOT EXISTS api_response_audit_time
    ON api_response_audit USING btree (time);