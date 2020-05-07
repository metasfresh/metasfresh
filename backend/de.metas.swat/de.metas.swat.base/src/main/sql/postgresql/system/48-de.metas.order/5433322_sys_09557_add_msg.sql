
-- 13.11.2015 13:45
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543703,0,TO_TIMESTAMP('2015-11-13 13:45:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order','Y','Partner-Produkt Datensatz nicht gefunden für Auftrag {0} - Zeile {1}','I',TO_TIMESTAMP('2015-11-13 13:45:27','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order.C_Order_CreatePOFromSOs.Missing_C_BPartner_Product_ID')
;

-- 13.11.2015 13:45
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543703 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 13.11.2015 13:47
-- URL zum Konzept
UPDATE AD_Message SET MsgText='Auftrag {0} - Zeile {1}: Partner-Produkt Datensatz nicht gefunden',Updated=TO_TIMESTAMP('2015-11-13 13:47:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=543703
;

-- 13.11.2015 13:47
-- URL zum Konzept
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=543703
;

-- 13.11.2015 13:51
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543704,0,TO_TIMESTAMP('2015-11-13 13:51:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order','Y','Auftrag {0} - Zeile {1}: Lieferant {2} - {3} entsprecht nicht dem Prozess-Parameter.','I',TO_TIMESTAMP('2015-11-13 13:51:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order.C_Order_CreatePOFromSOs.VendorMismatch')
;

-- 13.11.2015 13:51
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543704 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 13.11.2015 13:56
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543705,0,TO_TIMESTAMP('2015-11-13 13:56:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order','Y','Bestellung {0} erstellt.','I',TO_TIMESTAMP('2015-11-13 13:56:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.order.C_Order_CreatePOFromSOs.PurchaseOrderCreated')
;

-- 13.11.2015 13:56
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543705 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
