-- 2018-04-04T17:12:56.101
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_NotificationGroup (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_NotificationGroup_ID,EntityType,Name,InternalName) 
VALUES (0,0,TO_TIMESTAMP('2018-04-04 17:12:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-04-04 17:12:55','YYYY-MM-DD HH24:MI:SS'),100,540001,'D','Inventory','de.metas.inventory.UserNotifications')
;

-- 2018-04-04T17:49:51.013
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_NotificationGroup SET Name='Material Inventory',Updated=TO_TIMESTAMP('2018-04-04 17:49:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_NotificationGroup_ID=540001
;

-- 2018-04-04T17:50:06.014
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_NotificationGroup (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_NotificationGroup_ID,EntityType,Name,InternalName) 
VALUES (0,0,TO_TIMESTAMP('2018-04-04 17:50:05','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-04-04 17:50:05','YYYY-MM-DD HH24:MI:SS'),100,540002,'D','Material Movement','de.metas.movement.UserNotifications')
;

-- 2018-04-04T17:55:34.013
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_NotificationGroup (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_NotificationGroup_ID,EntityType,Name,InternalName) 
VALUES (0,0,TO_TIMESTAMP('2018-04-04 17:55:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-04-04 17:55:33','YYYY-MM-DD HH24:MI:SS'),100,540003,'D','Material Shipment/Receipt/Returns','de.metas.inout.UserNotifications')
;

-- 2018-04-04T19:14:16.183
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_NotificationGroup (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_NotificationGroup_ID,EntityType,Name,InternalName) 
VALUES (0,0,TO_TIMESTAMP('2018-04-04 19:14:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-04-04 19:14:16','YYYY-MM-DD HH24:MI:SS'),100,540007,'D','BPartner - Credit Limit Approval','de.metas.bpartner.UserNotifications.CreditLimit')
;

-- 2018-04-04T19:20:42.306
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_NotificationGroup (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_NotificationGroup_ID,EntityType,Name,InternalName) 
VALUES (0,0,TO_TIMESTAMP('2018-04-04 19:20:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-04-04 19:20:42','YYYY-MM-DD HH24:MI:SS'),100,540009,'D','Orders (Sales/Purchase)','de.metas.order.UserNotifications')
;

-- 2018-04-05T12:31:06.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_NotificationGroup (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_NotificationGroup_ID,EntityType,Name,InternalName) VALUES (0,0,TO_TIMESTAMP('2018-04-05 12:31:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-04-05 12:31:06','YYYY-MM-DD HH24:MI:SS'),100,540010,'D','Document processing','de.metas.document.UserNotifications')
;

-- 2018-04-05T13:11:11.208
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_NotificationGroup (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy,AD_NotificationGroup_ID,EntityType,Name,InternalName) VALUES (0,0,TO_TIMESTAMP('2018-04-05 13:11:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2018-04-05 13:11:11','YYYY-MM-DD HH24:MI:SS'),100,540011,'D','Alerts','de.metas.alerts.UserNotifications')
;

