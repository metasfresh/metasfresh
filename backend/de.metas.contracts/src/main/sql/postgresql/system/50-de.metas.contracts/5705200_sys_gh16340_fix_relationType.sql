/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2023 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- Run mode: SWING_CLIENT

-- Reference: Logs for Shipping Notification
-- Table: ModCntr_Log
-- Key: ModCntr_Log.ModCntr_Log_ID
-- 2023-10-05T06:25:50.111Z
UPDATE AD_Ref_Table SET WhereClause='ModCntr_Log.AD_Table_ID = get_table_id(''M_Shipping_NotificationLine'') AND ModCntr_Log_Status.Record_ID IN  (SELECT M_Shipping_NotificationLine_ID        from M_Shipping_NotificationLine nline        where nline.M_Shipping_Notification_ID = @M_Shipping_Notification_ID / -1@)',Updated=TO_TIMESTAMP('2023-10-05 09:25:50.111','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541836
;

-- Reference: Logs for Shipping Notification
-- Table: ModCntr_Log
-- Key: ModCntr_Log.ModCntr_Log_ID
-- 2023-10-05T06:29:32.882Z
UPDATE AD_Ref_Table SET WhereClause='ModCntr_Log.AD_Table_ID = get_table_id(''M_Shipping_NotificationLine'') AND ModCntr_Log.Record_ID IN  (SELECT M_Shipping_NotificationLine_ID        from M_Shipping_NotificationLine nline        where nline.M_Shipping_Notification_ID = @M_Shipping_Notification_ID / -1@)',Updated=TO_TIMESTAMP('2023-10-05 09:29:32.882','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541836
;

-- Name: M_ShippingNotification -> Modular Logs
-- Source Reference: M_Shipping_Notification
-- Target Reference: Logs for Shipping Notification
-- 2023-10-05T06:35:52.661Z
UPDATE AD_RelationType SET IsTableRecordIdTarget='Y',Updated=TO_TIMESTAMP('2023-10-05 09:35:52.659','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540433
;

-- Name: M_ShippingNotification -> Modular Logs
-- Source Reference: M_Shipping_Notification
-- Target Reference: Logs for Shipping Notification
-- 2023-10-05T06:37:10.501Z
UPDATE AD_RelationType SET IsTableRecordIdTarget='N',Updated=TO_TIMESTAMP('2023-10-05 09:37:10.501','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540433
;

-- Column: M_Shipping_Notification.M_Shipping_Notification_ID
-- 2023-10-05T06:39:47.353Z
UPDATE AD_Column SET IsExcludeFromZoomTargets='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-10-05 09:39:47.353','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587380
;

-- Column: M_Shipping_Notification.M_Shipping_Notification_ID
-- 2023-10-05T06:41:22.743Z
UPDATE AD_Column SET IsGenericZoomOrigin='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-10-05 09:41:22.743','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587380
;

-- Column: M_Shipping_Notification.M_Shipping_Notification_ID
-- 2023-10-05T06:41:37.758Z
UPDATE AD_Column SET IsGenericZoomKeyColumn='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-10-05 09:41:37.758','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587380
;

