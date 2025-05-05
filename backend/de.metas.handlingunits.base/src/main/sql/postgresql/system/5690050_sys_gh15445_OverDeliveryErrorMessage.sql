-- 2023-06-02T13:23:04.027Z
UPDATE AD_Message SET MsgText='Eine Überlieferung ist nicht erlaubt. ',Updated=TO_TIMESTAMP('2023-06-02 16:23:04.019','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=544647
;

-- 2023-06-02T13:24:55.646Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545274,0,TO_TIMESTAMP('2023-06-02 16:24:55.481','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.handlingunits','Y','Eine Überlieferung ist nicht erlaubt. ','E',TO_TIMESTAMP('2023-06-02 16:24:55.481','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.handlingunits.picking.candidate.commands.OverDeliveryNotAllowed')
;

-- 2023-06-02T13:24:55.650Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545274 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2023-06-02T13:25:55.401Z
UPDATE AD_Message_Trl SET MsgText='Over delivery is not allowed. Details: Shipment schedule qty to deliver: {0} | Qty intended to be delivered: {1}.',Updated=TO_TIMESTAMP('2023-06-02 16:25:55.399','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545274
;

-- 2023-06-02T13:34:43.422Z
UPDATE AD_Message_Trl SET MsgText='Eine Überlieferung mit der Menge {1} ist nicht erlaubt. Maximale Menge laut Lieferdispo: {0}.',Updated=TO_TIMESTAMP('2023-06-02 16:34:43.42','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545274
;

-- 2023-06-02T13:34:47.074Z
UPDATE AD_Message_Trl SET MsgText='Eine Überlieferung mit der Menge {1} ist nicht erlaubt. Maximale Menge laut Lieferdispo: {0}.',Updated=TO_TIMESTAMP('2023-06-02 16:34:47.072','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Message_ID=545274
;

-- 2023-06-02T13:35:01.907Z
UPDATE AD_Message_Trl SET MsgText='Eine Überlieferung mit der Menge {1} ist nicht erlaubt. Maximale Menge laut Lieferdispo: {0}.',Updated=TO_TIMESTAMP('2023-06-02 16:35:01.906','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545274
;

-- 2023-06-02T13:35:12.500Z
UPDATE AD_Message SET MsgText='Eine Überlieferung mit der Menge {1} ist nicht erlaubt. Maximale Menge laut Lieferdispo: {0}.',Updated=TO_TIMESTAMP('2023-06-02 16:35:12.499','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545274
;

