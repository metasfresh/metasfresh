DO
$$
    BEGIN

-- 2024-10-11T16:51:49.263Z
UPDATE AD_Reference_Trl SET Name='Quantity Not Picked Reject Reason',Updated=TO_TIMESTAMP('2024-10-11 19:51:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=541422
;

-- 2024-10-11T16:51:52.994Z
UPDATE AD_Reference_Trl SET Name='Nicht kommissionierte Menge Ablehnungsgrund',Updated=TO_TIMESTAMP('2024-10-11 19:51:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541422
;

-- 2024-10-11T16:51:55.759Z
UPDATE AD_Reference_Trl SET Name='Nicht kommissionierte Menge Ablehnungsgrund',Updated=TO_TIMESTAMP('2024-10-11 19:51:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=541422
;

-- 2024-10-11T16:51:58.176Z
UPDATE AD_Reference_Trl SET Name='Nicht kommissionierte Menge Ablehnungsgrund',Updated=TO_TIMESTAMP('2024-10-11 19:51:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Reference_ID=541422
;

-- Reference Item: QtyNotPicked RejectReason -> D_Damaged
-- 2024-10-11T16:52:19.489Z
UPDATE AD_Ref_List_Trl SET Name='Beschädigte',Updated=TO_TIMESTAMP('2024-10-11 19:52:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542905
;

-- Reference Item: QtyNotPicked RejectReason -> D_Damaged
-- 2024-10-11T16:52:23.119Z
UPDATE AD_Ref_List_Trl SET Name='Beschädigte',Updated=TO_TIMESTAMP('2024-10-11 19:52:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542905
;

-- Reference Item: QtyNotPicked RejectReason -> D_Damaged
-- 2024-10-11T16:52:25.449Z
UPDATE AD_Ref_List_Trl SET Name='Beschädigte',Updated=TO_TIMESTAMP('2024-10-11 19:52:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=542905
;

-- Reference Item: QtyNotPicked RejectReason -> N_NotFound
-- 2024-10-11T16:52:39.871Z
UPDATE AD_Ref_List_Trl SET Name='Nicht gefunden',Updated=TO_TIMESTAMP('2024-10-11 19:52:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542904
;

-- Reference Item: QtyNotPicked RejectReason -> N_NotFound
-- 2024-10-11T16:52:42.376Z
UPDATE AD_Ref_List_Trl SET Name='Nicht gefunden',Updated=TO_TIMESTAMP('2024-10-11 19:52:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542904
;

-- Reference Item: QtyNotPicked RejectReason -> N_NotFound
-- 2024-10-11T16:52:44.255Z
UPDATE AD_Ref_List_Trl SET Name='Nicht gefunden',Updated=TO_TIMESTAMP('2024-10-11 19:52:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=542904
;

-- Value: de.metas.handlingunits.picking.job.service.commands.ONE_PICKING_JOB_ERROR_MSG
-- 2024-10-12T13:52:23.511Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545446,0,TO_TIMESTAMP('2024-10-12 16:52:23','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Nur ein Kommissionierauftrag erwartet','E',TO_TIMESTAMP('2024-10-12 16:52:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.service.commands.ONE_PICKING_JOB_ERROR_MSG')
;

-- 2024-10-12T13:52:23.516Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545446 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.picking.job.service.commands.ONE_PICKING_JOB_ERROR_MSG
-- 2024-10-12T13:52:32.053Z
UPDATE AD_Message_Trl SET MsgText='Only one picking job expected',Updated=TO_TIMESTAMP('2024-10-12 16:52:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545446
;

-- Value: PickingJobCreateCommand.MORE_THAN_ONE_JOB_ERROR_MSG
-- 2024-10-12T13:54:06.043Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545447,0,TO_TIMESTAMP('2024-10-12 16:54:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Mehr als eine Stelle gefunden','E',TO_TIMESTAMP('2024-10-12 16:54:05','YYYY-MM-DD HH24:MI:SS'),100,'PickingJobCreateCommand.MORE_THAN_ONE_JOB_ERROR_MSG')
;

-- 2024-10-12T13:54:06.046Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545447 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: PickingJobCreateCommand.MORE_THAN_ONE_JOB_ERROR_MSG
-- 2024-10-12T13:54:11.767Z
UPDATE AD_Message_Trl SET MsgText='More than one job found',Updated=TO_TIMESTAMP('2024-10-12 16:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545447
;

-- Value: de.metas.handlingunits.picking.job.TU_CANNOT_BE_PICKED_ERROR_MSG
-- 2024-10-12T13:55:38.966Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545448,0,TO_TIMESTAMP('2024-10-12 16:55:38','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','TUs können nicht ausgewählt werden, wenn das TU-Auswahlziel eingestellt ist!','E',TO_TIMESTAMP('2024-10-12 16:55:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.TU_CANNOT_BE_PICKED_ERROR_MSG')
;

-- 2024-10-12T13:55:38.968Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545448 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.picking.job.TU_CANNOT_BE_PICKED_ERROR_MSG
-- 2024-10-12T13:55:57.275Z
UPDATE AD_Message_Trl SET MsgText='Cannot pick TUs when TU pick target is set!',Updated=TO_TIMESTAMP('2024-10-12 16:55:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545448
;

-- Value: de.metas.handlingunits.picking.job.PICKING_UNIT_NOT_SUPPORTED_ERROR_MSG
-- 2024-10-12T13:58:59.271Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545449,0,TO_TIMESTAMP('2024-10-12 16:58:59','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kommissioniereinheit nicht unterstützt !','E',TO_TIMESTAMP('2024-10-12 16:58:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.PICKING_UNIT_NOT_SUPPORTED_ERROR_MSG')
;

-- 2024-10-12T13:58:59.273Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545449 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.picking.job.PICKING_UNIT_NOT_SUPPORTED_ERROR_MSG
-- 2024-10-12T13:59:07.171Z
UPDATE AD_Message_Trl SET MsgText='Picking unit not supported !',Updated=TO_TIMESTAMP('2024-10-12 16:59:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545449
;

-- Value: de.metas.handlingunits.picking.job.NEGATIVE_CATCH_WEIGHT_ERROR_MSG
-- 2024-10-12T14:00:52.440Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545450,0,TO_TIMESTAMP('2024-10-12 17:00:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Das Fanggewicht muss positiv sein','E',TO_TIMESTAMP('2024-10-12 17:00:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.NEGATIVE_CATCH_WEIGHT_ERROR_MSG')
;

-- 2024-10-12T14:00:52.442Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545450 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.picking.job.NEGATIVE_CATCH_WEIGHT_ERROR_MSG
-- 2024-10-12T14:01:00.408Z
UPDATE AD_Message_Trl SET MsgText='Catch Weight shall be positive',Updated=TO_TIMESTAMP('2024-10-12 17:01:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545450
;

-- Value: de.metas.handlingunits.picking.job.QTY_REJECTED_ALTERNATIVES_ERROR_MSG
-- 2024-10-12T14:02:26.480Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545451,0,TO_TIMESTAMP('2024-10-12 17:02:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kann die abgelehnte Menge im Falle von Alternativen nicht berechnen','E',TO_TIMESTAMP('2024-10-12 17:02:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.QTY_REJECTED_ALTERNATIVES_ERROR_MSG')
;

-- 2024-10-12T14:02:26.481Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545451 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.picking.job.QTY_REJECTED_ALTERNATIVES_ERROR_MSG
-- 2024-10-12T14:02:33.981Z
UPDATE AD_Message_Trl SET MsgText='Cannot calculate Quantity Rejected in case of alternatives',Updated=TO_TIMESTAMP('2024-10-12 17:02:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545451
;

-- Value: de.metas.handlingunits.picking.job.L_M_QR_CODE_ERROR_MSG
-- 2024-10-12T14:04:08.034Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545452,0,TO_TIMESTAMP('2024-10-12 17:04:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','L+M QR-Code enthält keine externe Losnummer','E',TO_TIMESTAMP('2024-10-12 17:04:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.L_M_QR_CODE_ERROR_MSG')
;

-- 2024-10-12T14:04:08.036Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545452 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.picking.job.L_M_QR_CODE_ERROR_MSG
-- 2024-10-12T14:04:15.511Z
UPDATE AD_Message_Trl SET MsgText='L+M QR code does not contain external lot number',Updated=TO_TIMESTAMP('2024-10-12 17:04:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545452
;

-- Value: de.metas.handlingunits.picking.job.NO_QR_CODE_ERROR_MSG
-- 2024-10-12T14:06:39.411Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545453,0,TO_TIMESTAMP('2024-10-12 17:06:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','HU QR-Code muss bereitgestellt werden','E',TO_TIMESTAMP('2024-10-12 17:06:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.NO_QR_CODE_ERROR_MSG')
;

-- 2024-10-12T14:06:39.413Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545453 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.picking.job.NO_QR_CODE_ERROR_MSG
-- 2024-10-12T14:06:46.055Z
UPDATE AD_Message_Trl SET MsgText='HU QR code shall be provided',Updated=TO_TIMESTAMP('2024-10-12 17:06:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545453
;

-- Value: de.metas.handlingunits.picking.job.QR_CODE_EXTERNAL_LOT_ERROR_MSG
-- 2024-10-12T14:08:33.597Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545454,0,TO_TIMESTAMP('2024-10-12 17:08:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Keine HU in Verbindung mit externer Losnummer','E',TO_TIMESTAMP('2024-10-12 17:08:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.QR_CODE_EXTERNAL_LOT_ERROR_MSG')
;

-- 2024-10-12T14:08:33.598Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545454 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.picking.job.QR_CODE_EXTERNAL_LOT_ERROR_MSG
-- 2024-10-12T14:08:39.006Z
UPDATE AD_Message_Trl SET MsgText='No HU associated with external lot number',Updated=TO_TIMESTAMP('2024-10-12 17:08:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545454
;

-- Value: de.metas.handlingunits.picking.job.QR_CODE_NOT_SUPPORTED_ERROR_MSG
-- 2024-10-12T14:09:53.110Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545455,0,TO_TIMESTAMP('2024-10-12 17:09:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','HU QR-Code nicht unterstützt','E',TO_TIMESTAMP('2024-10-12 17:09:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.QR_CODE_NOT_SUPPORTED_ERROR_MSG')
;

-- 2024-10-12T14:09:53.111Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545455 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.picking.job.QR_CODE_NOT_SUPPORTED_ERROR_MSG
-- 2024-10-12T14:09:57.616Z
UPDATE AD_Message_Trl SET MsgText='HU QR code not supported',Updated=TO_TIMESTAMP('2024-10-12 17:09:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545455
;

-- Value: de.metas.ui.web.order.sales.hu.reservation.HUReservationDocumentFilterService.NO_QTY_RESERVED_ERROR_MSG
-- 2024-10-12T14:13:33.045Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545456,0,TO_TIMESTAMP('2024-10-12 17:13:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Für die Anfrage konnte keine Menge reserviert werden','E',TO_TIMESTAMP('2024-10-12 17:13:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web.order.sales.hu.reservation.HUReservationDocumentFilterService.NO_QTY_RESERVED_ERROR_MSG')
;

-- 2024-10-12T14:13:33.048Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545456 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.ui.web.order.sales.hu.reservation.HUReservationDocumentFilterService.NO_QTY_RESERVED_ERROR_MSG
-- 2024-10-12T14:13:38.508Z
UPDATE AD_Message_Trl SET MsgText='No quantity could be reserved for request',Updated=TO_TIMESTAMP('2024-10-12 17:13:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545456
;

-- Value: de.metas.ui.web.order.sales.hu.reservation.HUReservationDocumentFilterService.RESERVED_ERROR_MSG
-- 2024-10-12T14:15:14.862Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545457,0,TO_TIMESTAMP('2024-10-12 17:15:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Es kann nicht die gesamte angeforderte Menge reserviert werden','E',TO_TIMESTAMP('2024-10-12 17:15:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ui.web.order.sales.hu.reservation.HUReservationDocumentFilterService.RESERVED_ERROR_MSG')
;

-- 2024-10-12T14:15:14.867Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545457 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.ui.web.order.sales.hu.reservation.HUReservationDocumentFilterService.RESERVED_ERROR_MSG
-- 2024-10-12T14:15:20.681Z
UPDATE AD_Message_Trl SET MsgText='Cannot reserve the whole requested quantity',Updated=TO_TIMESTAMP('2024-10-12 17:15:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545457
;

-- Value: mobileui.picking.INVALID_QR_CODE_ERROR_MSG
-- 2024-10-12T14:16:53.309Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545458,0,TO_TIMESTAMP('2024-10-12 17:16:53','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Ungültiger QR-Code','E',TO_TIMESTAMP('2024-10-12 17:16:53','YYYY-MM-DD HH24:MI:SS'),100,'mobileui.picking.INVALID_QR_CODE_ERROR_MSG')
;

-- 2024-10-12T14:16:53.311Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545458 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: mobileui.picking.INVALID_QR_CODE_ERROR_MSG
-- 2024-10-12T14:17:00.205Z
UPDATE AD_Message_Trl SET MsgText='Invalid QR Code',Updated=TO_TIMESTAMP('2024-10-12 17:17:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545458
;

-- Value: de.metas.workflow.rest_api.model.NO_ACCESS_ERROR_MSG
-- 2024-10-12T14:18:47.861Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545459,0,TO_TIMESTAMP('2024-10-12 17:18:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Der Benutzer hat keinen Zugang','E',TO_TIMESTAMP('2024-10-12 17:18:47','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.workflow.rest_api.model.NO_ACCESS_ERROR_MSG')
;

-- 2024-10-12T14:18:47.862Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545459 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.workflow.rest_api.model.NO_ACCESS_ERROR_MSG
-- 2024-10-12T14:19:01.301Z
UPDATE AD_Message_Trl SET MsgText='User does not have access',Updated=TO_TIMESTAMP('2024-10-12 17:19:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545459
;

-- Value: de.metas.workflow.rest_api.model.NO_ACTIVITY_ERROR_MSG
-- 2024-10-12T14:21:13.441Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545460,0,TO_TIMESTAMP('2024-10-12 17:21:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Keine Aktivität für id gefunden','E',TO_TIMESTAMP('2024-10-12 17:21:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.workflow.rest_api.model.NO_ACTIVITY_ERROR_MSG')
;

-- 2024-10-12T14:21:13.442Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545460 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.workflow.rest_api.model.NO_ACTIVITY_ERROR_MSG
-- 2024-10-12T14:21:18.311Z
UPDATE AD_Message_Trl SET MsgText='No activity found for id',Updated=TO_TIMESTAMP('2024-10-12 17:21:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545460
;

-- Value: de.metas.handlingunits.picking.job.CANNOT_PACK_ERROR_MSG
-- 2024-10-12T14:26:46.588Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545461,0,TO_TIMESTAMP('2024-10-12 17:26:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kann nicht in HUs von {0} mit {1} packen, zu kommissionierende Menge={2}','E',TO_TIMESTAMP('2024-10-12 17:26:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.CANNOT_PACK_ERROR_MSG')
;

-- 2024-10-12T14:26:46.589Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545461 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.picking.job.CANNOT_PACK_ERROR_MSG
-- 2024-10-12T14:26:56.614Z
UPDATE AD_Message_Trl SET MsgText='Cannot pack to HUs from {0} using {1}, quantity to pick={2}',Updated=TO_TIMESTAMP('2024-10-12 17:26:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545461
;

-- Value: de.metas.workflow.rest_api.service.NOT_WORKFLOW_APP_ERROR_MSG
-- 2024-10-12T14:44:08.884Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545462,0,TO_TIMESTAMP('2024-10-12 17:44:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Anwendung {0} ist keine workflowbasierte Anwendung','E',TO_TIMESTAMP('2024-10-12 17:44:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.workflow.rest_api.service.NOT_WORKFLOW_APP_ERROR_MSG')
;

-- 2024-10-12T14:44:08.891Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545462 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.workflow.rest_api.service.NOT_WORKFLOW_APP_ERROR_MSG
-- 2024-10-12T14:44:15.193Z
UPDATE AD_Message_Trl SET MsgText='Application {0} is not a workflow based application',Updated=TO_TIMESTAMP('2024-10-12 17:44:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545462
;

-- Value: de.metas.handlingunits.picking.job.INVALID_NUMBER_QR_CODES_ERROR_MSG
-- 2024-10-12T14:47:30.824Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545463,0,TO_TIMESTAMP('2024-10-12 17:47:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Erwartet {0} QR-Codes, aber nur {1} erhalten','E',TO_TIMESTAMP('2024-10-12 17:47:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.INVALID_NUMBER_QR_CODES_ERROR_MSG')
;

-- 2024-10-12T14:47:30.826Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545463 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.picking.job.INVALID_NUMBER_QR_CODES_ERROR_MSG
-- 2024-10-12T14:47:37.951Z
UPDATE AD_Message_Trl SET MsgText='Expected {0} QR Codes but got only {1}',Updated=TO_TIMESTAMP('2024-10-12 17:47:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545463
;

-- Value: de.metas.handlingunits.picking.job.UNKNOWN_TARGET_LU_ERROR_MSG
-- 2024-10-12T14:49:31.871Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545464,0,TO_TIMESTAMP('2024-10-12 17:49:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Unbekanntes Ziel LU','E',TO_TIMESTAMP('2024-10-12 17:49:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.UNKNOWN_TARGET_LU_ERROR_MSG')
;

-- 2024-10-12T14:49:31.873Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545464 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.picking.job.UNKNOWN_TARGET_LU_ERROR_MSG
-- 2024-10-12T14:49:37.832Z
UPDATE AD_Message_Trl SET MsgText='Unknown target LU',Updated=TO_TIMESTAMP('2024-10-12 17:49:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545464
;

-- Value: de.metas.handlingunits.picking.job.NOT_ENOUGH_TUS_ERROR_MSG
-- 2024-10-12T14:51:07.299Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545465,0,TO_TIMESTAMP('2024-10-12 17:51:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Nicht genügend TUs gefunden','E',TO_TIMESTAMP('2024-10-12 17:51:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.NOT_ENOUGH_TUS_ERROR_MSG')
;

-- 2024-10-12T14:51:07.300Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545465 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.picking.job.NOT_ENOUGH_TUS_ERROR_MSG
-- 2024-10-12T14:51:14.738Z
UPDATE AD_Message_Trl SET MsgText='Not enough TUs found',Updated=TO_TIMESTAMP('2024-10-12 17:51:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545465
;

-- Value: de.metas.handlingunits.picking.job.CATCH_WEIGHT_LM_QR_CODE_ERROR_MSG
-- 2024-10-12T14:53:36.683Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545466,0,TO_TIMESTAMP('2024-10-12 17:53:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Catch Weight muss bei der Kommissionierung über LMQRCode vorhanden sein','E',TO_TIMESTAMP('2024-10-12 17:53:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.CATCH_WEIGHT_LM_QR_CODE_ERROR_MSG')
;

-- 2024-10-12T14:53:36.684Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545466 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.picking.job.CATCH_WEIGHT_LM_QR_CODE_ERROR_MSG
-- 2024-10-12T14:53:43.487Z
UPDATE AD_Message_Trl SET MsgText='Catch Weight must be present when picking via LMQRCode',Updated=TO_TIMESTAMP('2024-10-12 17:53:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545466
;

-- Value: de.metas.handlingunits.picking.job.CATCH_WEIGHT_MUST_MATCH_LM_QR_CODE_WEIGHT_ERROR_MSG
-- 2024-10-12T14:55:25.613Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545467,0,TO_TIMESTAMP('2024-10-12 17:55:25','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Das Fanggewicht muss mit dem Gewicht des LM-QR-Codes übereinstimmen.','E',TO_TIMESTAMP('2024-10-12 17:55:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.CATCH_WEIGHT_MUST_MATCH_LM_QR_CODE_WEIGHT_ERROR_MSG')
;

-- 2024-10-12T14:55:25.613Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545467 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.picking.job.CATCH_WEIGHT_MUST_MATCH_LM_QR_CODE_WEIGHT_ERROR_MSG
-- 2024-10-12T14:55:34.318Z
UPDATE AD_Message_Trl SET MsgText='Catch Weight must match the LM QR Code Weight',Updated=TO_TIMESTAMP('2024-10-12 17:55:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545467
;

-- Value: de.metas.handlingunits.picking.job.NO_QTY_ERROR_MSG
-- 2024-10-12T14:57:31.081Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545468,0,TO_TIMESTAMP('2024-10-12 17:57:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Keine Menge gefunden für {0}, {1}','E',TO_TIMESTAMP('2024-10-12 17:57:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.NO_QTY_ERROR_MSG')
;

-- 2024-10-12T14:57:31.087Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545468 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.picking.job.NO_QTY_ERROR_MSG
-- 2024-10-12T14:57:36.790Z
UPDATE AD_Message_Trl SET MsgText='No quantity found for {0}, {1}',Updated=TO_TIMESTAMP('2024-10-12 17:57:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545468
;

-- Value: de.metas.handlingunits.picking.job.model.PICKING_JOB_PROCESSED_ERROR_MSG
-- 2024-10-14T07:31:31.828Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545469,0,TO_TIMESTAMP('2024-10-14 10:31:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kommissionierauftrag wurde bereits bearbeitet','E',TO_TIMESTAMP('2024-10-14 10:31:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.model.PICKING_JOB_PROCESSED_ERROR_MSG')
;

-- 2024-10-14T07:31:31.832Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545469 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.picking.job.model.PICKING_JOB_PROCESSED_ERROR_MSG
-- 2024-10-14T07:31:41.231Z
UPDATE AD_Message_Trl SET MsgText='Picking Job was already processed',Updated=TO_TIMESTAMP('2024-10-14 10:31:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545469
;

-- Value: de.metas.handlingunits.picking.job.repository.PACKING_TO_GENERIC_PACKING_ERROR_MSG
-- 2024-10-14T08:13:15.268Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545470,0,TO_TIMESTAMP('2024-10-14 11:13:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Das Verpacken nach generischen Packvorschriften wird nicht unterstützt','E',TO_TIMESTAMP('2024-10-14 11:13:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.repository.PACKING_TO_GENERIC_PACKING_ERROR_MSG')
;

-- 2024-10-14T08:13:15.273Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545470 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.picking.job.repository.PACKING_TO_GENERIC_PACKING_ERROR_MSG
-- 2024-10-14T08:13:21.027Z
UPDATE AD_Message_Trl SET MsgText='Packing to generic packing instructions is not supported',Updated=TO_TIMESTAMP('2024-10-14 11:13:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545470
;

-- Value: de.metas.handlingunits.picking.job.service.commands.PICKING_ON_ALL_STEPS_ERROR_MSG
-- 2024-10-14T08:17:16.065Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545471,0,TO_TIMESTAMP('2024-10-14 11:17:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Die Kommissionierung muss auf allen Stufen durchgeführt werden, um den Auftrag abzuschließen.','E',TO_TIMESTAMP('2024-10-14 11:17:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.service.commands.PICKING_ON_ALL_STEPS_ERROR_MSG')
;

-- 2024-10-14T08:17:16.073Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545471 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.picking.job.service.commands.PICKING_ON_ALL_STEPS_ERROR_MSG
-- 2024-10-14T08:17:22.613Z
UPDATE AD_Message_Trl SET MsgText='Picking shall be DONE on all steps in order to complete the job',Updated=TO_TIMESTAMP('2024-10-14 11:17:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545471
;

-- Value: de.metas.handlingunits.picking.job.service.commands.ALL_STEPS_SHALL_BE_PICKED_ERROR_MSG
-- 2024-10-14T08:18:33.081Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545472,0,TO_TIMESTAMP('2024-10-14 11:18:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Alle Stufen müssen gepflückt werden','E',TO_TIMESTAMP('2024-10-14 11:18:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.service.commands.ALL_STEPS_SHALL_BE_PICKED_ERROR_MSG')
;

-- 2024-10-14T08:18:33.082Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545472 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.picking.job.service.commands.ALL_STEPS_SHALL_BE_PICKED_ERROR_MSG
-- 2024-10-14T08:18:38.819Z
UPDATE AD_Message_Trl SET MsgText='All steps shall be picked',Updated=TO_TIMESTAMP('2024-10-14 11:18:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545472
;

-- Value: de.metas.handlingunits.picking.job.model.JOB_ALREADY_ASSIGNED_ERROR_MSG
-- 2024-10-14T08:21:34.682Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545473,0,TO_TIMESTAMP('2024-10-14 11:21:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Der Auftrag wurde bereits vergeben','E',TO_TIMESTAMP('2024-10-14 11:21:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.model.JOB_ALREADY_ASSIGNED_ERROR_MSG')
;

-- 2024-10-14T08:21:34.684Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545473 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.picking.job.model.JOB_ALREADY_ASSIGNED_ERROR_MSG
-- 2024-10-14T08:21:41.316Z
UPDATE AD_Message_Trl SET MsgText='Job has been already assigned',Updated=TO_TIMESTAMP('2024-10-14 11:21:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545473
;

-- Value: de.metas.handlingunits.qrcodes.model.json.HUQRCodeJsonConverter
-- 2024-10-14T08:24:00.528Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545474,0,TO_TIMESTAMP('2024-10-14 11:24:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Ungültiger HU-QR-Code','E',TO_TIMESTAMP('2024-10-14 11:24:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.qrcodes.model.json.HUQRCodeJsonConverter')
;

-- 2024-10-14T08:24:00.529Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545474 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.qrcodes.model.json.HUQRCodeJsonConverter
-- 2024-10-14T08:24:08.736Z
UPDATE AD_Message_Trl SET MsgText='Invalid HU QR Code',Updated=TO_TIMESTAMP('2024-10-14 11:24:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545474
;

-- Value: de.metas.handlingunits.qrcodes.model.json.INVALID_QR_CODE_ERROR_MSG
-- 2024-10-14T08:25:24.129Z
UPDATE AD_Message SET Value='de.metas.handlingunits.qrcodes.model.json.INVALID_QR_CODE_ERROR_MSG',Updated=TO_TIMESTAMP('2024-10-14 11:25:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545474
;

-- Value: de.metas.handlingunits.qrcodes.model.json.INVALID_QR_VERSION_ERROR_MSG
-- 2024-10-14T08:26:07.342Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545475,0,TO_TIMESTAMP('2024-10-14 11:26:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Ungültige HU QR Code Version','E',TO_TIMESTAMP('2024-10-14 11:26:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.qrcodes.model.json.INVALID_QR_VERSION_ERROR_MSG')
;

-- 2024-10-14T08:26:07.343Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545475 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.qrcodes.model.json.INVALID_QR_VERSION_ERROR_MSG
-- 2024-10-14T08:26:13.088Z
UPDATE AD_Message_Trl SET MsgText='Invalid HU QR Code version',Updated=TO_TIMESTAMP('2024-10-14 11:26:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545475
;
    EXCEPTION
        WHEN unique_violation THEN
            RAISE NOTICE 'WorkpackageProcessor de.metas.salesorder.async.CompleteShipAndInvoiceWorkpackageProcessor already exists';
    END
$$
;
