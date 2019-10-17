-- 2019-07-04T13:35:45.061
UPDATE AD_Column SET AD_Reference_ID=36,Updated=TO_TIMESTAMP('2019-07-04 13:35:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=8805;
INSERT INTO t_alter_column values('ad_changelog','NewValue','TEXT',null,null);
UPDATE AD_Column SET AD_Reference_ID=36,Updated=TO_TIMESTAMP('2019-07-04 13:36:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=8815;
INSERT INTO t_alter_column values('ad_changelog','OldValue','TEXT',null,null);

