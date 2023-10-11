-- Value: WEBUI_Missing_Base_PriceList_Version
-- 2023-10-10T15:48:20.932Z
<<<<<<< HEAD
<<<<<<< HEAD
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545352,0,TO_TIMESTAMP('2023-10-10 16:48:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Preisliste hat keine Basis Preisliste','I',TO_TIMESTAMP('2023-10-10 16:48:20','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_Missing_Base_PriceList_Version')
=======
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545352,0,TO_TIMESTAMP('2023-10-10 16:48:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','No Base Pricelist set for {0} Pricelist','I',TO_TIMESTAMP('2023-10-10 16:48:20','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_Missing_Base_PriceList_Version')
>>>>>>> a7c0827393a (Products Proposal modal update)
=======
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545352,0,TO_TIMESTAMP('2023-10-10 16:48:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Preisliste hat keine Basis Preisliste','I',TO_TIMESTAMP('2023-10-10 16:48:20','YYYY-MM-DD HH24:MI:SS'),100,'WEBUI_Missing_Base_PriceList_Version')
>>>>>>> 62861f561ce (Products Proposal modal update)
;

-- 2023-10-10T15:48:20.934Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545352 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: WEBUI_Missing_Base_PriceList_Version
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
-- 2023-10-10T18:06:53.914Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Pricelist has no Base Pricelist',Updated=TO_TIMESTAMP('2023-10-10 19:06:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545352
=======
-- 2023-10-10T15:48:50.220Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Keine Basispreisliste für {0} Preisliste festgelegt',Updated=TO_TIMESTAMP('2023-10-10 16:48:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545352
>>>>>>> a7c0827393a (Products Proposal modal update)
;

-- Value: WEBUI_Missing_Base_PriceList_Version
=======
>>>>>>> 62861f561ce (Products Proposal modal update)
-- 2023-10-10T18:05:56.608Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Preisliste hat keine Basis Preisliste',Updated=TO_TIMESTAMP('2023-10-10 19:05:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545352
;

-- Value: WEBUI_Missing_Base_PriceList_Version
-- 2023-10-10T18:06:47.440Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Preisliste hat keine Basis Preisliste',Updated=TO_TIMESTAMP('2023-10-10 19:06:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545352
;

-- Value: WEBUI_Missing_Base_PriceList_Version
=======
>>>>>>> 38925b2448f (Products Proposal modal update)
-- 2023-10-10T18:06:53.914Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Pricelist has no Base Pricelist',Updated=TO_TIMESTAMP('2023-10-10 19:06:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545352
;

