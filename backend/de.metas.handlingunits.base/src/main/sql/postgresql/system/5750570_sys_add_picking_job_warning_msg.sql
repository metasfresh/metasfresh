-- Run mode: SWING_CLIENT

-- Value: de.metas.picking.workflow.handlers.activity_handlers.NOT_ALL_LINES_ARE_COMPLETED_WARNING
-- 2025-04-02T07:57:17.401Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545518,0,TO_TIMESTAMP('2025-04-02 07:57:17.158000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Nicht alle Positionen sind abgeschlossen. Möchten Sie dennoch fortfahren?','I',TO_TIMESTAMP('2025-04-02 07:57:17.158000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.picking.workflow.handlers.activity_handlers.NOT_ALL_LINES_ARE_COMPLETED_WARNING')
;

-- 2025-04-02T07:57:17.421Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545518 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.picking.workflow.handlers.activity_handlers.NOT_ALL_LINES_ARE_COMPLETED_WARNING
-- 2025-04-02T07:57:26.368Z
UPDATE AD_Message_Trl SET MsgText='Not all positions have been completed. Would you still like to continue?',Updated=TO_TIMESTAMP('2025-04-02 07:57:26.368000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545518
;

-- 2025-04-02T07:57:26.369Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.handlingunits.picking.job.service.commands.PICKING_ON_ALL_STEPS_ERROR_MSG
-- 2025-04-02T15:58:11.516Z
UPDATE AD_Message_Trl SET MsgText='Picking shall be DONE on all steps in order to complete the job. You can deactivate this in Mobile UI Picking Profile, by checking ''Allow completing partial picking jobs''.',Updated=TO_TIMESTAMP('2025-04-02 15:58:11.514000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545471
;

-- 2025-04-02T15:58:11.527Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.handlingunits.picking.job.service.commands.PICKING_ON_ALL_STEPS_ERROR_MSG
-- 2025-04-02T16:01:16.689Z
UPDATE AD_Message SET MsgText='Die Kommissionierung muss in allen Schritten abgeschlossen werden,  um den Auftrag abzuschließen. Sie können dieses Verhalten im Mobile UI Kommissionierprofil deaktivieren, indem Sie die Option ‚Teilweise Kommissionierung erlauben aktivieren.',Updated=TO_TIMESTAMP('2025-04-02 16:01:16.689000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545471
;

-- 2025-04-02T16:01:16.717Z
UPDATE AD_Message_Trl trl SET MsgText='Die Kommissionierung muss in allen Schritten abgeschlossen werden,  um den Auftrag abzuschließen. Sie können dieses Verhalten im Mobile UI Kommissionierprofil deaktivieren, indem Sie die Option ‚Teilweise Kommissionierung erlauben aktivieren.' WHERE AD_Message_ID=545471 AND AD_Language='de_DE'
;

-- Value: de.metas.handlingunits.picking.job.service.commands.PICKING_ON_ALL_STEPS_ERROR_MSG
-- 2025-04-02T16:01:27.534Z
UPDATE AD_Message_Trl SET MsgText='Die Kommissionierung muss in allen Schritten abgeschlossen werden,  um den Auftrag abzuschließen. Sie können dieses Verhalten im Mobile UI Kommissionierprofil deaktivieren, indem Sie die Option ‚Teilweise Kommissionierung erlauben aktivieren.',Updated=TO_TIMESTAMP('2025-04-02 16:01:27.533000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545471
;

-- 2025-04-02T16:01:27.535Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.handlingunits.picking.job.service.commands.PICKING_ON_ALL_STEPS_ERROR_MSG
-- 2025-04-02T16:01:30.460Z
UPDATE AD_Message_Trl SET MsgText='Die Kommissionierung muss in allen Schritten abgeschlossen werden,  um den Auftrag abzuschließen. Sie können dieses Verhalten im Mobile UI Kommissionierprofil deaktivieren, indem Sie die Option ‚Teilweise Kommissionierung erlauben aktivieren.',Updated=TO_TIMESTAMP('2025-04-02 16:01:30.460000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545471
;

-- 2025-04-02T16:01:30.461Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.handlingunits.picking.job.service.commands.NOTHING_HAS_BEEN_PICKED
-- 2025-04-02T16:33:21.146Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545519,0,TO_TIMESTAMP('2025-04-02 16:33:20.922000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Nichts wurde kommissioniert!','I',TO_TIMESTAMP('2025-04-02 16:33:20.922000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.handlingunits.picking.job.service.commands.NOTHING_HAS_BEEN_PICKED')
;

-- 2025-04-02T16:33:21.151Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545519 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.picking.job.service.commands.NOTHING_HAS_BEEN_PICKED
-- 2025-04-02T16:33:27.346Z
UPDATE AD_Message SET MsgType='E',Updated=TO_TIMESTAMP('2025-04-02 16:33:27.345000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545519
;

-- Value: de.metas.handlingunits.picking.job.service.commands.NOTHING_HAS_BEEN_PICKED
-- 2025-04-02T16:33:57.105Z
UPDATE AD_Message_Trl SET MsgText='Nothing was picked!',Updated=TO_TIMESTAMP('2025-04-02 16:33:57.105000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545519
;

-- 2025-04-02T16:33:57.105Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: de.metas.handlingunits.picking.job.service.commands.ALL_STEPS_SHALL_BE_PICKED_ERROR_MSG
-- 2025-04-02T16:44:01.125Z
DELETE FROM  AD_Message_Trl WHERE AD_Message_ID=545472
;

-- 2025-04-02T16:44:01.138Z
DELETE FROM AD_Message WHERE AD_Message_ID=545472
;

