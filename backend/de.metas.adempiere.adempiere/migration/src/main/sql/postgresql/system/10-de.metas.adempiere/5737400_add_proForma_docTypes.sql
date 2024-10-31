-- Run mode: SWING_CLIENT

-- Reference: C_DocType SubType
-- Value: PF
-- ValueName: ProFormaSO
-- 2024-10-21T06:27:49.658Z
UPDATE AD_Ref_List SET Name='ProForma',Updated=TO_TIMESTAMP('2024-10-21 08:27:49.658','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543560
;

-- Reference: C_DocType SubType
-- Value: PF
-- ValueName: ProFormaSO
-- 2024-10-21T06:27:49.658Z
UPDATE AD_Ref_List SET ValueName='ProForma',Updated=TO_TIMESTAMP('2024-10-21 08:27:49.658','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543560
;

-- 2024-10-21T06:27:49.660Z
UPDATE AD_Ref_List_Trl trl SET Name='ProForma' WHERE AD_Ref_List_ID=543560 AND AD_Language='de_DE'
;

-- Reference Item: C_DocType SubType -> PF_ProForma
-- 2024-10-21T06:28:06.346Z
UPDATE AD_Ref_List_Trl SET Name='ProForma',Updated=TO_TIMESTAMP('2024-10-21 08:28:06.345','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543560
;

-- Reference Item: C_DocType SubType -> PF_ProForma
-- 2024-10-21T06:28:09.127Z
UPDATE AD_Ref_List_Trl SET Name='ProForma',Updated=TO_TIMESTAMP('2024-10-21 08:28:09.127','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND AD_Ref_List_ID=543560
;

-- Reference Item: C_DocType SubType -> PF_ProForma
-- 2024-10-21T06:28:11.590Z
UPDATE AD_Ref_List_Trl SET Name='ProForma',Updated=TO_TIMESTAMP('2024-10-21 08:28:11.59','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543560
;

-- Reference Item: C_DocType SubType -> PF_ProForma
-- 2024-10-21T06:28:20.040Z
UPDATE AD_Ref_List_Trl SET Name='ProForma',Updated=TO_TIMESTAMP('2024-10-21 08:28:20.04','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543560
;

-- Run mode: WEBUI

-- 2024-10-16T16:48:06.197Z
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,AD_PrintFormat_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocNoSequence_ID,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsExcludeFromCommision,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,1000000,1000013,541128,TO_TIMESTAMP('2024-10-16 18:48:06.174','YYYY-MM-DD HH24:MI:SS.US'),100,'MMS',545461,1,'D',1000001,'N','N','Y','Y','Y','N','N','Y','N','N','N','N','N','N','N','Y','N','Proforma Lieferung','Proforma Lieferung',TO_TIMESTAMP('2024-10-16 18:48:06.174','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-10-16T16:48:06.247Z
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_DocType_ID=541128 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2024-10-16T16:48:06.251Z
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541128 AND rol.IsManual='N')
;

-- 2024-10-16T16:48:33.422Z
UPDATE C_DocType_Trl SET Name='Proforma Shipment',Updated=TO_TIMESTAMP('2024-10-16 18:48:33.422','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541128
;

-- 2024-10-16T16:48:36.373Z
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-16 18:48:36.373','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541128
;

-- 2024-10-16T16:48:38.539Z
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-16 18:48:38.539','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_DocType_ID=541128
;

-- 2024-10-16T16:48:40.266Z
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-16 18:48:40.266','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND C_DocType_ID=541128
;

-- 2024-10-16T16:58:50.308Z
UPDATE C_DocType SET C_DocTypeShipment_ID=541128,Updated=TO_TIMESTAMP('2024-10-16 18:58:50.307','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541113
;

-- 2024-10-21T06:35:26.206Z
UPDATE C_DocType SET DocSubType='PF',Updated=TO_TIMESTAMP('2024-10-21 08:35:26.189','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541128
;

-- 2024-10-30T17:12:09.288Z
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,AD_PrintFormat_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocNoSequence_ID,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsExcludeFromCommision,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,1000000,540136,541130,TO_TIMESTAMP('2024-10-30 18:12:09.274','YYYY-MM-DD HH24:MI:SS.US'),100,'SHN',556307,1,'de.metas.shippingnotification',0,'N','N','Y','Y','Y','N','N','Y','N','N','N','N','N','N','N','Y','N','Proforma Lieferavis','Proforma Lieferavis',TO_TIMESTAMP('2024-10-30 18:12:09.274','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-10-30T17:12:09.318Z
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_DocType_ID=541130 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2024-10-30T17:12:09.322Z
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541130 AND rol.IsManual='N')
;

-- 2024-10-30T17:12:38.128Z
UPDATE C_DocType_Trl SET Name='Proforma Shipping Notification',Updated=TO_TIMESTAMP('2024-10-30 18:12:38.128','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541130
;

-- 2024-10-30T17:12:40.868Z
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-30 18:12:40.868','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541130
;

-- 2024-10-30T17:12:43.273Z
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-30 18:12:43.272','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_DocType_ID=541130
;

-- 2024-10-30T17:12:44.973Z
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-30 18:12:44.973','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND C_DocType_ID=541130
;

-- 2024-10-30T17:12:44.973Z
UPDATE C_DocType SET DocSubType='PF',Updated=TO_TIMESTAMP('2024-10-30 18:12:44.973','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541130
;

-- 2024-10-30T17:19:09.091Z
UPDATE C_DocType SET IsDefault='Y',Updated=TO_TIMESTAMP('2024-10-30 18:19:09.091','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541109
;

-- 2024-10-30T17:19:09.881Z
UPDATE C_DocType SET IsSOTrx='Y',Updated=TO_TIMESTAMP('2024-10-30 18:19:09.881','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541109
;

-- Field: Belegart(135,D) -> Belegart(167,D) -> Doc Sub Type
-- Column: C_DocType.DocSubType
-- 2024-10-30T17:42:21.880Z
UPDATE AD_Field SET DisplayLogic='@DocBaseType@=''SOO'' | @DocBaseType@=''POO'' | @DocBaseType@=''ARI'' | @DocBaseType@=''ARC'' | @DocBaseType@=''MOP'' | @DocBaseType@=''MMR'' | @DocBaseType@=''MMS'' | @DocBaseType@=''API'' | @DocBaseType@=''MMI'' | @DocBaseType@ = ''APC'' | @DocBaseType@=''SDD'' | @DocBaseType@ = ''CMB'' | @DocBaseType@ = ''MST'' | @DocBaseType@ = ''SHN''',Updated=TO_TIMESTAMP('2024-10-30 18:42:21.88','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=2581
;

-- Name: C_Flatrate_Conditions_Modular_Settings
-- 2024-10-30T18:50:27.841Z
UPDATE AD_Val_Rule SET Code='(@C_DocTypeTarget_ID/-1@ NOT IN (541034,541062,541113,541112)) AND (C_Flatrate_Conditions.C_Flatrate_Conditions_ID IN ( (SELECT c.C_Flatrate_Conditions_ID FROM C_Flatrate_Conditions c INNER JOIN ModCntr_Settings mc ON @Harvesting_Year_ID/0@>0 AND mc.ModCntr_Settings_ID = c.ModCntr_Settings_ID AND mc.M_Raw_Product_ID = @M_Product_ID@ AND mc.C_Year_ID = @Harvesting_Year_ID/0@ AND mc.isSOTrx = ''@IsSOTrx@'' WHERE c.ModCntr_Settings_ID IS NOT NULL AND c.DocStatus = ''CO'' AND c.type_conditions <> ''InterimInvoice'' ) UNION ALL (SELECT C_Flatrate_Conditions_ID FROM C_Flatrate_Conditions WHERE @Harvesting_Year_ID/0@=0 AND type_conditions NOT IN (''InterimInvoice'', ''ModularContract'') AND DocStatus = ''CO'' ) ) )',Updated=TO_TIMESTAMP('2024-10-30 19:50:27.839','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540640
;

-- 2024-10-30T21:09:58.087Z
UPDATE C_DocType SET Name='Proforma Auftrag',Updated=TO_TIMESTAMP('2024-10-30 22:09:58.082','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541113
;

-- 2024-10-30T21:09:58.093Z
UPDATE C_DocType_Trl trl SET Name='Proforma Auftrag' WHERE C_DocType_ID=541113 AND (IsTranslated='N' OR AD_Language='de_DE')
;

-- 2024-10-30T21:09:58.264Z
INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541113)
;

-- 2024-10-30T21:10:00.589Z
UPDATE C_DocType SET PrintName='Proforma Auftrag',Updated=TO_TIMESTAMP('2024-10-30 22:10:00.589','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541113
;

-- 2024-10-30T21:10:00.590Z
UPDATE C_DocType_Trl trl SET PrintName='Proforma Auftrag' WHERE C_DocType_ID=541113 AND (IsTranslated='N' OR AD_Language='de_DE')
;

-- 2024-10-30T21:10:00.623Z
INSERT INTO ES_FTS_Index_Queue(ES_FTS_Config_ID, EventType, AD_Table_ID, Record_ID) VALUES (540003, 'U', 217, 541113)
;

-- 2024-10-30T21:10:08.704Z
UPDATE C_DocType_Trl SET Name='Proforma Auftrag',Updated=TO_TIMESTAMP('2024-10-30 22:10:08.704','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_DocType_ID=541113
;

-- 2024-10-30T21:10:29.791Z
UPDATE C_DocType_Trl SET Name='Proforma SO',Updated=TO_TIMESTAMP('2024-10-30 22:10:29.791','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541113
;

-- 2024-10-30T21:10:46.415Z
UPDATE C_DocType_Trl SET PrintName='Proforma Auftrag',Updated=TO_TIMESTAMP('2024-10-30 22:10:46.415','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_DocType_ID=541113
;

-- 2024-10-30T21:10:55.182Z
UPDATE C_DocType_Trl SET PrintName='Proforma SO',Updated=TO_TIMESTAMP('2024-10-30 22:10:55.182','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541113
;
