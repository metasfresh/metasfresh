-- IDs allocated from idserver.metas.de on 2026-09-03:
--   AD_MigrationScript    5822080 (this script)
--   AD_NotificationGroup  540026 (de.metas.invoicecandidate.UserNotifications.InvoicingErrors)
--
-- The event-bus topic de.metas.invoicecandidate.UserNotifications.InvoicingErrors was shipped
-- (InvoiceUserNotificationsProducer.EVENTBUS_TOPIC_Error, registered in ConfigValidator.onAfterInit)
-- WITHOUT its AD_NotificationGroup row. InternalName must equal the topic name, because
-- UserNotificationsConfig.getGroupByName() matches on it exactly and otherwise falls back to
-- `defaults` -- i.e. to AD_User.NotificationType, which is NOT NULL DEFAULT 'E' (EMail only), so
-- a user on the default gets no entry in the notification bell at all.
--
-- Without this row the topic is also invisible in "Mein Profil" -> Notifications, in the role
-- admin window's Notification Groups tab, and to AD_NotificationGroup_CC -- so nobody can opt in
-- or out of it, and no CC recipient can be configured.
--
-- Name is deliberately "problems" rather than "errors": Event_InvoicingError (enqueued, then failed
-- during the async invoicing) rides this topic, while the enqueuer's skip report goes into the process
-- summary instead. One subscription therefore covers "my Create Invoices run had a problem".

-- 2026-09-03T00:10:07
INSERT INTO AD_NotificationGroup (AD_Client_ID,AD_NotificationGroup_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,Name,Updated,UpdatedBy)
VALUES (0,540026 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-03 00:10:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoicecandidate','de.metas.invoicecandidate.UserNotifications.InvoicingErrors','Y','Billing - Invoicing problems',TO_TIMESTAMP('2026-09-03 00:10:07','YYYY-MM-DD HH24:MI:SS'),100)
;
