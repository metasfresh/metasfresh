-- Run mode: SWING_CLIENT

-- Column: M_ShipmentSchedule.QtyOrdered_Override
-- 2025-09-25T11:21:25.838Z
UPDATE AD_Column SET ReadOnlyLogic='@ExportStatus/''X''@ = ''EXPORTED'' | @ExportStatus/''X''@ = ''EXPORTED_AND_FORWARDED'' | @ExportStatus/''X''@ = ''EXPORTED_FORWARD_ERROR'' | @IsScheduledForPicking/N@=Y',Updated=TO_TIMESTAMP('2025-09-25 11:21:25.838000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=551639
;

-- Column: M_ShipmentSchedule.QtyToDeliverCatch_Override
-- 2025-09-25T11:21:36.188Z
UPDATE AD_Column SET ReadOnlyLogic='@ExportStatus/''X''@ = ''EXPORTED'' | @ExportStatus/''X''@ = ''EXPORTED_AND_FORWARDED'' | @ExportStatus/''X''@ = ''EXPORTED_FORWARD_ERROR'' | @IsScheduledForPicking/N@=Y',Updated=TO_TIMESTAMP('2025-09-25 11:21:36.188000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=568497
;

-- Column: M_ShipmentSchedule.QtyToDeliver_Override
-- 2025-09-25T11:21:44.259Z
UPDATE AD_Column SET ReadOnlyLogic='@ExportStatus/''X''@ = ''EXPORTED'' | @ExportStatus/''X''@ = ''EXPORTED_AND_FORWARDED'' | @ExportStatus/''X''@ = ''EXPORTED_FORWARD_ERROR'' | @IsScheduledForPicking/N@=Y',Updated=TO_TIMESTAMP('2025-09-25 11:21:44.258000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=500238
;

-- Column: M_ShipmentSchedule.QtyToDeliver_OverrideFulfilled
-- 2025-09-25T11:21:46.146Z
UPDATE AD_Column SET ReadOnlyLogic='@ExportStatus/''X''@ = ''EXPORTED'' | @ExportStatus/''X''@ = ''EXPORTED_AND_FORWARDED'' | @ExportStatus/''X''@ = ''EXPORTED_FORWARD_ERROR'' | @IsScheduledForPicking/N@=Y',Updated=TO_TIMESTAMP('2025-09-25 11:21:46.145000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=544881
;

-- Column: M_ShipmentSchedule.QtyTU_Override
-- 2025-09-25T11:21:50.969Z
UPDATE AD_Column SET ReadOnlyLogic='@ExportStatus/''X''@ = ''EXPORTED'' | @ExportStatus/''X''@ = ''EXPORTED_AND_FORWARDED'' | @ExportStatus/''X''@ = ''EXPORTED_FORWARD_ERROR'' | @IsScheduledForPicking/N@=Y',Updated=TO_TIMESTAMP('2025-09-25 11:21:50.969000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=551616
;

-- Value: salesorder.shipmentschedule.cannotReactivateBecauseScheduledForPicking
-- 2025-09-25T11:32:42.992Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545584,0,TO_TIMESTAMP('2025-09-25 11:32:42.798000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Der Beleg kann nicht geändert werden, weil die Lieferdispo-Datensätze bereits zur Kommissionierung vorgemerkt sind.','E',TO_TIMESTAMP('2025-09-25 11:32:42.798000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'salesorder.shipmentschedule.cannotReactivateBecauseScheduledForPicking')
;

-- 2025-09-25T11:32:42.996Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545584 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: salesorder.shipmentschedule.cannotReactivateBecauseScheduledForPicking
-- 2025-09-25T11:33:35.189Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-09-25 11:33:35.189000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545584
;

-- Value: salesorder.shipmentschedule.cannotReactivateBecauseScheduledForPicking
-- 2025-09-25T11:33:41.002Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-09-25 11:33:41.002000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545584
;

-- Value: salesorder.shipmentschedule.cannotReactivateBecauseScheduledForPicking
-- 2025-09-25T11:34:21.312Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Changing the document is not allowed because shipment schedules are already scheduled for picking.',Updated=TO_TIMESTAMP('2025-09-25 11:34:21.312000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545584
;

-- 2025-09-25T11:34:21.313Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

