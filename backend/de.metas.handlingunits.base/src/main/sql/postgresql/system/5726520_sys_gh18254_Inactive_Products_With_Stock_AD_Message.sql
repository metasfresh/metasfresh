-- Value: INACTIVE_PRODUCTS_WITH_STOCK
-- 2024-06-17T13:27:05.326Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545423,0,TO_TIMESTAMP('2024-06-17 16:27:05','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Dieses Produkt kann nicht deaktiviert werden, da noch Bestand davon im Lager vorhanden ist. Bitte den Lagerbestand über den Menüpunkt "Inventur" auf 0 setzen und erneut versuchen.','E',TO_TIMESTAMP('2024-06-17 16:27:05','YYYY-MM-DD HH24:MI:SS'),100,'INACTIVE_PRODUCTS_WITH_STOCK')
;

-- 2024-06-17T13:27:05.330Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545423 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: INACTIVE_PRODUCTS_WITH_STOCK
-- 2024-06-17T13:27:16.100Z
UPDATE AD_Message_Trl SET MsgText='This product cannot be deactivated as there is still stock of it in the warehouse. Please set the stock level to 0 via the menu option "Physical Inventory" and try again.',Updated=TO_TIMESTAMP('2024-06-17 16:27:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545423
;

