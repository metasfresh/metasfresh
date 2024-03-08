
update api_audit_config
set isforceprocessedasync =
        CASE
            WHEN isinvokerwaitsforresult = 'Y' THEN 'N'
            WHEN isinvokerwaitsforresult = 'N' THEN 'Y'
        END
;
