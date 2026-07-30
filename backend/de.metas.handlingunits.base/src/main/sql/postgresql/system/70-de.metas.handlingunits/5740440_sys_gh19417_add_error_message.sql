-- Value: de.metas.handlingunits.picking.job.model.MISSING_PICKING_SLOT_ID_ERROR_MSG
-- 2024-11-25T05:28:39.163Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545484,0,TO_TIMESTAMP('2024-11-25 07:28:38','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Bitte scannen Sie zuerst einen Kommissionierschlitz.','E',TO_TIMESTAMP('2024-11-25 07:28:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.model.MISSING_PICKING_SLOT_ID_ERROR_MSG')
;

-- 2024-11-25T05:28:39.168Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545484 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.handlingunits.picking.job.model.MISSING_PICKING_SLOT_ID_ERROR_MSG
-- 2024-11-25T05:28:54.674Z
UPDATE AD_Message_Trl SET MsgText='Please scan a picking slot first.',Updated=TO_TIMESTAMP('2024-11-25 07:28:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545484
;

