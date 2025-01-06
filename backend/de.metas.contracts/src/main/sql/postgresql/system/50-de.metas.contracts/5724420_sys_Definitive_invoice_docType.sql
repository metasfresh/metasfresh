-- Run mode: SWING_CLIENT

-- Reference: C_DocType SubType
-- Value: DP
-- ValueName: DownPayment
-- 2024-05-27T10:23:57.184Z
UPDATE AD_Ref_List SET Name='Akonto',Updated=TO_TIMESTAMP('2024-05-27 13:23:57.183','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=540856
;

-- 2024-05-27T10:23:57.191Z
UPDATE AD_Ref_List_Trl trl SET Name='Akonto' WHERE AD_Ref_List_ID=540856 AND AD_Language='de_DE'
;

-- Reference: C_DocType SubType
-- Value: DS
-- ValueName: Definitive Schlussabrechnung
-- 2024-05-27T10:30:12.016Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,148,543690,TO_TIMESTAMP('2024-05-27 13:30:11.863','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','Definitive Schlussabrechnung',TO_TIMESTAMP('2024-05-27 13:30:11.863','YYYY-MM-DD HH24:MI:SS.US'),100,'DS','Definitive Invoice')
;

-- 2024-05-27T10:30:12.020Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543690 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Name: C_DocSubType Compatible
-- 2024-05-27T10:31:37.093Z
UPDATE AD_Val_Rule SET Code='(''@DocBaseType@''=''ARI'' AND AD_Ref_List.ValueName IN (''AQ'', ''AP'', ''Healthcare_CH-EA'', ''Healthcare_CH-GM'', ''Healthcare_CH-KV'', ''Healthcare_CH-KT'', ''RD'', ''LS'', ''CorrectionInvoice'')) OR (''@DocBaseType@''=''ARC'' AND AD_Ref_List.Value IN (''CQ'', ''CR'',''CS'', ''RI'', ''RC'')) OR (''@DocBaseType@''=''API'' AND (AD_Ref_List.Value IN (''QI'', ''VI'', ''SI'', ''FI'', ''DS'') OR AD_Ref_List.ValueName IN (''CommissionSettlement''))) OR (''@DocBaseType@''=''MOP'' AND AD_Ref_List.Value IN (''QI'', ''VI'')) OR (''@DocBaseType@'' = ''MMI'' AND AD_Ref_List.Value in (''MD'', ''VIY'', ''ISD'', ''IOD'')) OR (''@DocBaseType@''=''SDD'' AND AD_Ref_List.Value = ''NAR'') OR (''@DocBaseType@''=''SDC'' AND AD_Ref_List.Value = ''NAR'') OR (''@DocBaseType@''= ''CMB'' AND AD_Ref_List.VALUE IN (''CB'', ''BS'')) OR (''@DocBaseType@''=''POO'' AND AD_Ref_List.Value = ''MED'') OR (''@DocBaseType@'' NOT IN (''API'', ''ARI'', ''ARC'', ''MOP'') AND AD_Ref_List.Value NOT IN (''AQ'', ''AP'', ''CQ'', ''CR'', ''QI'', ''MED'')) /* fallback for the rest of the entries */',Updated=TO_TIMESTAMP('2024-05-27 13:31:37.092','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540219
;

-- 2024-05-27T10:35:23.390Z
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocNoSequence_ID,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsExcludeFromCommision,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,1000000,541123,TO_TIMESTAMP('2024-05-27 13:35:23.379','YYYY-MM-DD HH24:MI:SS.US'),100,'APC',555719,1,'de.metas.contracts',1000006,'N','N','Y','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Definitive Schlusszahlung Gutschrift','Definitive Schlusszahlung Gutschrift',TO_TIMESTAMP('2024-05-27 13:35:23.379','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-27T10:35:23.439Z
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_DocType_ID=541123 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2024-05-27T10:35:23.442Z
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541123 AND rol.IsManual='N')
;

-- 2024-05-27T10:36:29.025Z
UPDATE C_DocType SET DocSubType='DS',Updated=TO_TIMESTAMP('2024-05-27 13:36:29.025','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541123
;

-- 2024-05-27T10:37:23.425Z
UPDATE C_DocType SET AD_PrintFormat_ID=1000015,Updated=TO_TIMESTAMP('2024-05-27 13:37:23.425','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541123
;

-- 2024-05-27T10:37:54.127Z
UPDATE C_DocType SET AD_PrintFormat_ID=1000012,Updated=TO_TIMESTAMP('2024-05-27 13:37:54.127','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541123
;

-- 2024-05-29T13:33:13.978Z
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocNoSequence_ID,DocSubType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsExcludeFromCommision,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,1000000,541124,TO_TIMESTAMP('2024-05-29 15:33:13.974','YYYY-MM-DD HH24:MI:SS.US'),100,'API',555719,'DS',1,'de.metas.contracts',1000006,'N','N','Y','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Definitive Schlusszahlung','Definitive Schlusszahlung',TO_TIMESTAMP('2024-05-29 15:33:13.974','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-29T13:33:13.981Z
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_DocType_ID=541124 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2024-05-29T13:33:13.982Z
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541124 AND rol.IsManual='N')
;

-- 2024-05-29T13:33:45.531Z
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-29 15:33:45.531','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_DocType_ID=541124
;

-- 2024-05-29T13:33:48.779Z
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-29 15:33:48.779','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND C_DocType_ID=541124
;

-- 2024-05-29T13:33:56.093Z
UPDATE C_DocType_Trl SET Name='Definitive Invoice',Updated=TO_TIMESTAMP('2024-05-29 15:33:56.093','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541124
;

-- 2024-05-29T13:34:02.005Z
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-05-29 15:34:02.005','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541124
;



-- 2024-05-27T10:38:42.239Z
INSERT INTO C_DocType_Invoicing_Pool (AD_Client_ID,AD_Org_ID,C_DocType_Invoicing_Pool_ID,Created,CreatedBy,IsActive,IsOnDistinctICTypes,IsSOTrx,Name,Negative_Amt_C_DocType_ID,Positive_Amt_C_DocType_ID,Updated,UpdatedBy) VALUES (1000000,1000000,540003,TO_TIMESTAMP('2024-05-27 13:38:42.231','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Y','N','Definitive Invoice',541123,541124,TO_TIMESTAMP('2024-05-27 13:38:42.231','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-27T10:38:50.269Z
UPDATE C_DocType SET C_DocType_Invoicing_Pool_ID=540003,Updated=TO_TIMESTAMP('2024-05-27 13:38:50.269','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541124
;

-- 2024-05-27T10:38:56.179Z
UPDATE C_DocType SET C_DocType_Invoicing_Pool_ID=540003,Updated=TO_TIMESTAMP('2024-05-27 13:38:56.179','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541123
;

