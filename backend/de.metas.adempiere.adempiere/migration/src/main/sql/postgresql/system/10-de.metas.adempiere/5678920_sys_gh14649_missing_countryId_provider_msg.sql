-- Value: C_DocType_Sequence_countryId_provider_missing
-- 2023-02-22T13:35:25.536Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545241,0,TO_TIMESTAMP('2023-02-22 14:35:25','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','This Document Base Type doesn''t support country-based-Sequences, pls select country ''none''','I',TO_TIMESTAMP('2023-02-22 14:35:25','YYYY-MM-DD HH24:MI:SS'),100,'C_DocType_Sequence_countryId_provider_missing')
;

-- 2023-02-22T13:35:25.544Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545241 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: C_DocType_Sequence_countryId_provider_missing
-- 2023-02-22T14:01:41.133Z
UPDATE AD_Message SET MsgType='E',Updated=TO_TIMESTAMP('2023-02-22 15:01:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=545241
;


-- Value: C_DocType_Sequence_countryId_provider_missing
-- 2023-02-22T14:55:06.951Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-02-22 15:55:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545241
;

-- Value: C_DocType_Sequence_countryId_provider_missing
-- 2023-02-22T15:06:01.572Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Diese Belegart unterstützt keine Sequenzen auf Basis von Ländern. Bitte wählen Sie kein Land.',Updated=TO_TIMESTAMP('2023-02-22 16:06:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545241
;

-- Value: C_DocType_Sequence_countryId_provider_missing
-- 2023-02-22T15:06:07.797Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Diese Belegart unterstützt keine Sequenzen auf Basis von Ländern. Bitte wählen Sie kein Land.',Updated=TO_TIMESTAMP('2023-02-22 16:06:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545241
;

-- 2023-02-22T15:06:07.799Z
UPDATE AD_Message SET MsgText='Diese Belegart unterstützt keine Sequenzen auf Basis von Ländern. Bitte wählen Sie kein Land.' WHERE AD_Message_ID=545241
;

