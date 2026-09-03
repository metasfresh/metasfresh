-- IDs allocated from idserver.metas.de on 2026-09-03:
--   AD_MigrationScript    5822080 (this script)
--   AD_NotificationGroup  540026 (de.metas.invoicecandidate.UserNotifications.InvoicingErrors)
--
-- The topic (InvoiceUserNotificationsProducer.EVENTBUS_TOPIC_Error) shipped without its group row.
-- InternalName MUST equal the topic name -- UserNotificationsConfig.getGroupByName() matches exactly and
-- otherwise falls back to AD_User.NotificationType, and the topic stays invisible in the subscription tabs.

-- 2026-09-03T00:10:07
INSERT INTO AD_NotificationGroup (AD_Client_ID,AD_NotificationGroup_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,Name,Updated,UpdatedBy)
VALUES (0,540026 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-03 00:10:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoicecandidate','de.metas.invoicecandidate.UserNotifications.InvoicingErrors','Y','Billing - Invoicing problems',TO_TIMESTAMP('2026-09-03 00:10:07','YYYY-MM-DD HH24:MI:SS'),100)
;
