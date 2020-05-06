-- 2019-03-04T12:56:21.131
-- #298 changing anz. stellen
UPDATE AD_Column SET FieldLength=30000,Updated=TO_TIMESTAMP('2019-03-04 12:56:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=542497
;

-- 2019-03-04T12:56:24.103
-- #298 changing anz. stellen
INSERT INTO t_alter_column values('ad_process','SQLStatement','TEXT',null,null)
;

