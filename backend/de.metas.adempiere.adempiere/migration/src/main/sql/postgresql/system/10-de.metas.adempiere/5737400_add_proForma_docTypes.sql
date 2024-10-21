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
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,AD_PrintFormat_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocNoSequence_ID,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsExcludeFromCommision,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,1000000,1000013,541128,TO_TIMESTAMP('2024-10-16 18:48:06.174','YYYY-MM-DD HH24:MI:SS.US'),100,'MMS',545461,1,'D',1000001,'N','N','Y','Y','Y','N','N','Y','N','N','N','N','N','N','N','Y','N','ProForma Lieferung','ProForma Lieferung',TO_TIMESTAMP('2024-10-16 18:48:06.174','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-10-16T16:48:06.247Z
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_DocType_ID=541128 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2024-10-16T16:48:06.251Z
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541128 AND rol.IsManual='N')
;

-- 2024-10-16T16:48:33.422Z
UPDATE C_DocType_Trl SET Name='ProForma Shipment',Updated=TO_TIMESTAMP('2024-10-16 18:48:33.422','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541128
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


--TODO add proForma shipping notification docType