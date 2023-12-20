-- Value: GoodsIssueQuantityParameterError
-- 2023-02-28T13:19:15.141Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545247,0,TO_TIMESTAMP('2023-02-28 14:19:14','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','For this delivery rule the Quantity needs to be smaller or equal to Qty Total Open','E',TO_TIMESTAMP('2023-02-28 14:19:14','YYYY-MM-DD HH24:MI:SS'),100,'GoodsIssueQuantityParameterError')
;

-- 2023-02-28T13:19:15.143Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545247 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: GoodsIssueQuantityParameterError
-- 2023-02-28T13:25:24.655Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-02-28 14:25:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545247
;

-- Value: GoodsIssueQuantityParameterError
-- 2023-02-28T13:27:03.381Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Für diese Lieferregel muss die Anzahl kleiner oder gleich der gesamten offenen Menge sein.',Updated=TO_TIMESTAMP('2023-02-28 14:27:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545247
;

-- Value: GoodsIssueQuantityParameterError
-- 2023-02-28T13:27:23.249Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Für diese Lieferregel muss die Anzahl kleiner oder gleich der gesamten offenen Menge sein.',Updated=TO_TIMESTAMP('2023-02-28 14:27:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545247
;

-- 2023-02-28T13:27:23.250Z
UPDATE AD_Message SET MsgText='Für diese Lieferregel muss die Anzahl kleiner oder gleich der gesamten offenen Menge sein.' WHERE AD_Message_ID=545247
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: QtyTotalOpen
-- 2023-02-28T13:37:48.712Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,581682,0,585194,542566,29,'QtyTotalOpen',TO_TIMESTAMP('2023-02-28 14:37:48','YYYY-MM-DD HH24:MI:SS'),100,'D',0,'Y','N','Y','N','Y','N','Menge gesamt (offen)',25,TO_TIMESTAMP('2023-02-28 14:37:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-28T13:37:48.715Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=542566 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2023-02-28T13:37:48.744Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(581682) 
;

-- Process: M_Delivery_Planning_GenerateShipment(de.metas.deliveryplanning.webui.process.M_Delivery_Planning_GenerateShipment)
-- ParameterName: QtyTotalOpen
-- 2023-02-28T14:13:44.949Z
UPDATE AD_Process_Para SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2023-02-28 15:13:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542566
;

