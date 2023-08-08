-- Value: de.metas.order.model.interceptor.M_Product.MSG_PRODUCT_ALREADY_USED
-- 2023-06-07T00:23:36.163936919Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545275,0,TO_TIMESTAMP('2023-06-07 01:23:35.582','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','Die Maßeinheit kann nicht geändert werden, wenn bereits eine Konvertierung vorhanden ist','E',TO_TIMESTAMP('2023-06-07 01:23:35.582','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.order.model.interceptor.M_Product.MSG_PRODUCT_ALREADY_USED')
;

-- 2023-06-07T00:23:36.169362928Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545275 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.order.model.interceptor.M_Product.MSG_PRODUCT_ALREADY_USED
-- 2023-06-07T00:25:02.263337604Z
UPDATE AD_Message_Trl SET MsgText='The UOM can''t be changed once the product is used (Orders, Invoice,...)',Updated=TO_TIMESTAMP('2023-06-07 01:25:02.263','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545275
;

-- Value: de.metas.order.model.interceptor.M_Product.MSG_PRODUCT_ALREADY_USED
-- 2023-06-07T00:25:49.469122885Z
UPDATE AD_Message_Trl SET MsgText='Die Maßeinheit kann nicht mehr geändert werden, sobald das Produkt verwendet wird (Bestellungen, Rechnungen,...)',Updated=TO_TIMESTAMP('2023-06-07 01:25:49.468','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545275
;

-- Value: de.metas.order.model.interceptor.M_Product.MSG_PRODUCT_ALREADY_USED
-- 2023-06-07T00:25:59.389544939Z
UPDATE AD_Message_Trl SET MsgText='Die Maßeinheit kann nicht mehr geändert werden, sobald das Produkt verwendet wird (Bestellungen, Rechnungen,...)',Updated=TO_TIMESTAMP('2023-06-07 01:25:59.389','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545275
;

-- 2023-06-07T00:25:59.390537853Z
UPDATE AD_Message SET MsgText='Die Maßeinheit kann nicht mehr geändert werden, sobald das Produkt verwendet wird (Bestellungen, Rechnungen,...)' WHERE AD_Message_ID=545275
;

-- Value: de.metas.order.model.interceptor.M_Product.MSG_PRODUCT_ALREADY_USED
-- 2023-06-07T00:26:59.576718603Z
UPDATE AD_Message_Trl SET MsgText='L''UOM ne peut pas être modifié une fois que le produit est utilisé (commandes, factures,...).',Updated=TO_TIMESTAMP('2023-06-07 01:26:59.575','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545275
;

