-- Run mode: SWING_CLIENT

-- Reference: C_DocType DocBaseType
-- Value: APF
-- ValueName: APProFormaInvoice
-- 2026-03-25T14:54:01.045Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,183,544175,TO_TIMESTAMP('2026-03-25 14:54:00.886000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Proforma-Rechnung (Lieferant)',TO_TIMESTAMP('2026-03-25 14:54:00.886000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APF','APProFormaInvoice')
;

-- 2026-03-25T14:54:01.061Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544175 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: C_DocType DocBaseType -> APF_APProFormaInvoice
-- 2026-03-25T14:54:46.379Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Purchase Proforma Invoice',Updated=TO_TIMESTAMP('2026-03-25 14:54:46.379000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544175
;

-- 2026-03-25T14:54:46.381Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: C_DocType DocBaseType -> APF_APProFormaInvoice
-- 2026-03-25T14:54:46.886Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-03-25 14:54:46.886000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=544175
;

-- Reference Item: C_DocType DocBaseType -> APF_APProFormaInvoice
-- 2026-03-25T14:54:47.893Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-03-25 14:54:47.892000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544175
;

-- Run mode: WEBUI

-- 2026-03-25T15:09:27.821Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNo,Updated,UpdatedBy) VALUES (1000000,1000000,556590,TO_TIMESTAMP('2026-03-25 15:09:27.821000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1000000,100,'Purchase Proforma Invoice',1,'Y','N','Y','N','Purchase Proforma Invoice',1000000,TO_TIMESTAMP('2026-03-25 15:09:27.821000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-25T15:15:30.836Z
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,AD_PrintFormat_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocNoSequence_ID,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsExcludeFromCommision,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,1000000,1000015,541174,TO_TIMESTAMP('2026-03-25 15:15:30.832000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'APF',556590,1,'de.metas.swat',0,'N','N','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Proforma-Rechnung (Lieferant)','Proforma-Rechnung',TO_TIMESTAMP('2026-03-25 15:15:30.832000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-25T15:15:30.838Z
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_DocType_ID=541174 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2026-03-25T15:15:30.840Z
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541174 AND rol.IsManual='N')
;

-- 2026-03-25T15:15:47.203Z
UPDATE C_DocType_Trl SET Name='Purchase Proforma Invoice',Updated=TO_TIMESTAMP('2026-03-25 15:15:47.202000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541174
;

-- 2026-03-25T15:15:47.205Z
UPDATE C_DocType base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM C_DocType_Trl trl  WHERE trl.C_DocType_ID=base.C_DocType_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-03-25T15:15:54.215Z
UPDATE C_DocType_Trl SET PrintName='Proforma Invoice',Updated=TO_TIMESTAMP('2026-03-25 15:15:54.215000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541174
;

-- 2026-03-25T15:15:54.218Z
UPDATE C_DocType base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM C_DocType_Trl trl  WHERE trl.C_DocType_ID=base.C_DocType_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-03-25T15:15:57.076Z
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-03-25 15:15:57.076000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541174
;

-- 2026-03-25T15:15:59.561Z
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-03-25 15:15:59.561000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND C_DocType_ID=541174
;

-- 2026-03-25T15:16:01.562Z
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-03-25 15:16:01.562000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND C_DocType_ID=541174
;

-- 2026-03-25T15:37:09.164Z
UPDATE C_DocType SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2026-03-25 15:37:09.164000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE C_DocType_ID=541174
;

-- 2026-03-25T15:38:04.053Z
UPDATE AD_Sequence SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2026-03-25 15:38:04.053000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Sequence_ID=556590
;

-- old: Speditionsauftrag/Ladeliste
UPDATE ad_ref_list
SET valuename = 'ShipperTransportation',Updated=TO_TIMESTAMP('2026-03-25 15:38:04.053000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100
WHERE ad_ref_list_id = 540166
;

-- Name: C_DocType AR/AP normal Invoices and Credit Memos
-- 2026-03-26T10:31:36.154Z
UPDATE AD_Val_Rule SET Code='( C_DocType.DocBaseType IN (''API'',''APC'',''APF'') OR (C_DocType.DocBaseType IN (''ARC'',''ARI'') AND C_DocType.DocSubType IS NULL) /*only the default types*/ OR (C_DocType.DocBaseType=''ARC'' AND C_DocType.DocSubType = ''CS'') /*only the RMA-credit-memo*/ ) AND C_DocType.IsSOTrx=''@IsSOTrx@'' AND C_DocType.AD_Org_ID IN (@AD_Org_ID/-1@, 0)',Updated=TO_TIMESTAMP('2026-03-26 10:31:36.152000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540294
;
