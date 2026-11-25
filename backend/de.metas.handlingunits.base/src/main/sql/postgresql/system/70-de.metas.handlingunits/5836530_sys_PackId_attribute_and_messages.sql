-- CU quantity entry: HU attribute PackId on the virtual packing item (version 101, CU level) and the five label / open-TU messages.
-- New:    M_Attribute PackId (string, read-only values, filled by the picking booking from the label's pack ID part).
--         M_HU_PI_Attribute on M_HU_PI_Version_ID 101 only (not on the template PI 100). Transfer strategy Skip (as HU_QRCode), propagation NONE.
--         AD_Message de.metas.handlingunits.picking.job.CU_LABEL_ALREADY_PICKED, CU_LABEL_NO_PACK_ID, CU_LABEL_FORMAT_MISMATCH, CU_LABEL_NO_FORMAT, CU_NO_OPEN_TU.
-- The pack ID part type of ScannableCodeFormatPartType is not created by this script.

-- M_Attribute PackId (name German base; M_Attribute has no _Trl table)
INSERT INTO M_Attribute (AD_Client_ID,AD_Org_ID,AttributeValueType,Created,CreatedBy,Description,IsActive,IsAlwaysUpdateable,IsAttrDocumentRelevant,IsHighVolume,IsInstanceAttribute,IsMandatory,IsPricingRelevant,IsPrintedInDocument,IsReadOnlyValues,IsStorageRelevant,IsTransferWhenNull,M_Attribute_ID,Name,Updated,UpdatedBy,Value,ValueMax,ValueMin) VALUES (0,0,'S',TO_TIMESTAMP('2026-11-18 23:10:00','YYYY-MM-DD HH24:MI:SS'),100,'Pack ID of the CU label, set when the CU is picked in CU quantity entry mode','Y','N','N','N','Y','N','N','N','Y','N','N',540215 /*From ID Server*/,'Packungs-ID',TO_TIMESTAMP('2026-11-18 23:10:00','YYYY-MM-DD HH24:MI:SS'),100,'PackId',0,0)
;

-- M_HU_PI_Attribute: only on the virtual packing item version 101 (CU level), never on the template PI 100
INSERT INTO M_HU_PI_Attribute (AD_Client_ID,AD_Org_ID,Created,CreatedBy,HU_TansferStrategy_JavaClass_ID,IsActive,IsDisplayed,IsInstanceAttribute,IsMandatory,IsOnlyIfInProductAttributeSet,IsReadOnly,M_Attribute_ID,M_HU_PI_Attribute_ID,M_HU_PI_Version_ID,PropagationType,SeqNo,Updated,UpdatedBy,UseInASI) VALUES (0,0,TO_TIMESTAMP('2026-11-18 23:10:01','YYYY-MM-DD HH24:MI:SS'),100,540026,'Y','Y','Y','N','N','Y',540215,540177 /*From ID Server*/,101,'NONE',9131,TO_TIMESTAMP('2026-11-18 23:10:01','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- AD_Message 1 of 5: CU_LABEL_ALREADY_PICKED
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,546015 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-18 23:10:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Diese Packung wurde in diesem Auftrag bereits kommissioniert (Packungs-ID {0}). Es wurde nichts gebucht.','E',TO_TIMESTAMP('2026-11-18 23:10:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.CU_LABEL_ALREADY_PICKED')
;
UPDATE AD_Message SET ErrorCode='CU_LABEL_ALREADY_PICKED', Updated=TO_TIMESTAMP('2026-11-18 23:10:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=546015
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=546015 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET MsgText='This CU was already picked in this job (pack ID {0}). Nothing was booked.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-18 23:10:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=546015
;
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-18 23:10:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=546015
;
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-18 23:10:07','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=546015
;

-- AD_Message 2 of 5: CU_LABEL_NO_PACK_ID
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,546016 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-18 23:10:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Das Etikett enthält keine Packungs-ID. Es wurde nichts gebucht.','E',TO_TIMESTAMP('2026-11-18 23:10:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.CU_LABEL_NO_PACK_ID')
;
UPDATE AD_Message SET ErrorCode='CU_LABEL_NO_PACK_ID', Updated=TO_TIMESTAMP('2026-11-18 23:10:09','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=546016
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=546016 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET MsgText='The label contains no pack ID. Nothing was booked.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-18 23:10:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=546016
;
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-18 23:10:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=546016
;
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-18 23:10:13','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=546016
;

-- AD_Message 3 of 5: CU_LABEL_FORMAT_MISMATCH
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,546017 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-18 23:10:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Das Etikett passt nicht zum Etikettenformat des Kommissionierprofils. Es wurde nichts gebucht.','E',TO_TIMESTAMP('2026-11-18 23:10:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.CU_LABEL_FORMAT_MISMATCH')
;
UPDATE AD_Message SET ErrorCode='CU_LABEL_FORMAT_MISMATCH', Updated=TO_TIMESTAMP('2026-11-18 23:10:15','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=546017
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=546017 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET MsgText='The label does not match the label format of the picking profile. Nothing was booked.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-18 23:10:17','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=546017
;
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-18 23:10:18','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=546017
;
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-18 23:10:19','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=546017
;

-- AD_Message 4 of 5: CU_LABEL_NO_FORMAT
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,546018 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-18 23:10:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kein Etikettenformat im Kommissionierprofil hinterlegt.','E',TO_TIMESTAMP('2026-11-18 23:10:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.CU_LABEL_NO_FORMAT')
;
UPDATE AD_Message SET ErrorCode='CU_LABEL_NO_FORMAT', Updated=TO_TIMESTAMP('2026-11-18 23:10:21','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=546018
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=546018 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET MsgText='No label format set on the picking profile.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-18 23:10:23','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=546018
;
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-18 23:10:24','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=546018
;
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-18 23:10:25','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=546018
;

-- AD_Message 5 of 5: CU_NO_OPEN_TU
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,546019 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-18 23:10:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Bitte öffnen Sie zuerst eine Kiste (GRAI scannen oder ''Weitere Kiste'').','E',TO_TIMESTAMP('2026-11-18 23:10:26','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits.picking.job.CU_NO_OPEN_TU')
;
UPDATE AD_Message SET ErrorCode='CU_NO_OPEN_TU', Updated=TO_TIMESTAMP('2026-11-18 23:10:27','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Message_ID=546019
;
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language,t.AD_Message_ID,t.MsgText,t.MsgTip,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=546019 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;
UPDATE AD_Message_Trl SET MsgText='Please open a TU first (scan a GRAI or ''Next TU'').', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-18 23:10:29','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=546019
;
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-18 23:10:30','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=546019
;
UPDATE AD_Message_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-18 23:10:31','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=546019
;
