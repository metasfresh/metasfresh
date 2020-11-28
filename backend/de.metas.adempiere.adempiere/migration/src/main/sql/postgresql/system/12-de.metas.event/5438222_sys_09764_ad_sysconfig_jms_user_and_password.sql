
delete from AD_SysConfig where name in ('de.metas.event.jms.User','de.metas.event.jms.Password'); --cleanup so we can reapply this SQL

-- 29.01.2016 16:59
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,540932,'S',TO_TIMESTAMP('2016-01-29 16:59:31','YYYY-MM-DD HH24:MI:SS'),100,'User name the is used then the event system connects to a JMS broker.','de.metas.event','Y','de.metas.event.jms.User',TO_TIMESTAMP('2016-01-29 16:59:31','YYYY-MM-DD HH24:MI:SS'),100,'smx')
;

-- 29.01.2016 17:00
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,540933,'S',TO_TIMESTAMP('2016-01-29 17:00:06','YYYY-MM-DD HH24:MI:SS'),100,'Password that is used then the event system connects to a JMS broker.','de.metas.event','Y','de.metas.event.jms.Password',TO_TIMESTAMP('2016-01-29 17:00:06','YYYY-MM-DD HH24:MI:SS'),100,'smx')
;

-- 29.01.2016 17:00
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='User name that is used then the event system connects to a JMS broker.',Updated=TO_TIMESTAMP('2016-01-29 17:00:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540932
;

