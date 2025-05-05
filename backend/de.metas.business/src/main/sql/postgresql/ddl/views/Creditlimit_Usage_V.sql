DROP VIEW IF EXISTS creditlimit_usage_v
;

CREATE VIEW creditlimit_usage_v AS
SELECT *
FROM get_credit_limit_usage(NULL)
;
