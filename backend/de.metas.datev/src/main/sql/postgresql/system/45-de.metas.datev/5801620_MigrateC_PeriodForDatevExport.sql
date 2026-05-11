select backup_table('datev_export');


UPDATE datev_export
SET C_Period_ID = report.Get_Period((SELECT C_Calendar_ID FROM AD_ClientInfo ci WHERE ci.ad_client_id = datev_export.ad_client_id), COALESCE(DateAcctFrom, DateAcctto,datev_export.created)::date)
WHERE C_Period_ID IS NULL
;

-- 2026-05-11T07:57:50.473Z
INSERT INTO t_alter_column values('datev_export','C_Period_ID','NUMERIC(10)',null,null)
;

-- 2026-05-11T07:57:50.477Z
INSERT INTO t_alter_column values('datev_export','C_Period_ID',null,'NOT NULL',null)
;


