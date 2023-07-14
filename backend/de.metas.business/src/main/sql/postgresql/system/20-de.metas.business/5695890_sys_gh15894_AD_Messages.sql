-- Value: ONLY_ONE_PRODUCT_WAREHOUSE_ASSIGNMENT
-- 2023-07-14T11:57:12.422Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545289,0,TO_TIMESTAMP('2023-07-14 14:57:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Einem Produkt darf nur ein Lager zugeordnet werden.','E',TO_TIMESTAMP('2023-07-14 14:57:12','YYYY-MM-DD HH24:MI:SS'),100,'ONLY_ONE_PRODUCT_WAREHOUSE_ASSIGNMENT')
;

-- 2023-07-14T11:57:12.425Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545289 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: ONLY_ONE_PRODUCT_WAREHOUSE_ASSIGNMENT
-- 2023-07-14T11:57:21.273Z
UPDATE AD_Message_Trl SET MsgText='A product can only be assigned one warehouse.',Updated=TO_TIMESTAMP('2023-07-14 14:57:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545289
;

-- 2023-07-14T11:57:21.275Z
UPDATE AD_Message SET MsgText='A product can only be assigned one warehouse.', Updated=TO_TIMESTAMP('2023-07-14 14:57:21','YYYY-MM-DD HH24:MI:SS') WHERE AD_Message_ID=545289
;

-- Value: ORDER_DIFFERENT_WAREHOUSE
-- 2023-07-14T11:58:54.461Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545290,0,TO_TIMESTAMP('2023-07-14 14:58:54','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Die dem Auftrag/der Bestellung und dem Produkt zugeordneten Lager stimmen nicht überein.','E',TO_TIMESTAMP('2023-07-14 14:58:54','YYYY-MM-DD HH24:MI:SS'),100,'ORDER_DIFFERENT_WAREHOUSE')
;

-- 2023-07-14T11:58:54.462Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545290 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: ORDER_DIFFERENT_WAREHOUSE
-- 2023-07-14T11:59:10.835Z
UPDATE AD_Message_Trl SET MsgText='The warehouses assigned to both the sales/purchase order and the product do not match.',Updated=TO_TIMESTAMP('2023-07-14 14:59:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545290
;

-- 2023-07-14T11:59:10.837Z
UPDATE AD_Message SET MsgText='The warehouses assigned to both the sales/purchase order and the product do not match.', Updated=TO_TIMESTAMP('2023-07-14 14:59:10','YYYY-MM-DD HH24:MI:SS') WHERE AD_Message_ID=545290
;

