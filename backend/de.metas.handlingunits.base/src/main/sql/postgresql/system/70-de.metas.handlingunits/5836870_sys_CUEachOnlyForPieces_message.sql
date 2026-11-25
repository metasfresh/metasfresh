-- CU quantity entry: 'Scan each CU' is refused on a picking line whose unit (C_UOM.X12DE355) is not PCE (one scan = one CU cannot be a kg quantity).
-- New: AD_Message de.metas.handlingunits.picking.job.CU_EACH_ONLY_FOR_PIECES (+ AD_Message_Trl de_DE / en_US / de_CH)

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,546022 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-21 01:30:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','''Jede CU einzeln scannen'' funktioniert nur für Zeilen, die in Stück geführt werden. Bitte beim Administrator die CU-Mengenerfassung für diesen Kunden auf ''Manuell'' oder ''Einmal scannen, Menge eingeben'' umstellen lassen.','E',TO_TIMESTAMP('2026-11-21 01:30:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.CU_EACH_ONLY_FOR_PIECES')
;
UPDATE AD_Message SET ErrorCode='CU_EACH_ONLY_FOR_PIECES', Updated=TO_TIMESTAMP('2026-11-21 01:30:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=546022
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=546022 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET MsgText='''Scan each CU'' works only for lines counted in pieces. Please have your administrator set the CU quantity entry for this customer to ''Manual'' or ''Scan once, enter quantity''.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-21 01:30:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=546022
;
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-21 01:30:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=546022
;
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-21 01:30:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=546022
;
