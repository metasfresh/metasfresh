-- Run mode: SWING_CLIENT

-- Name: C_DocSubType Compatible
-- 2024-10-01T08:52:17.082Z
UPDATE AD_Val_Rule SET Code='(''@DocBaseType@''=''ARI'' AND AD_Ref_List.ValueName IN (''AQ'', ''AP'', ''Healthcare_CH-EA'', ''Healthcare_CH-GM'', ''Healthcare_CH-KV'', ''Healthcare_CH-KT'', ''RD'', ''LS'', ''CorrectionInvoice'', ''Final Invoice'')) OR (''@DocBaseType@''=''ARC'' AND AD_Ref_List.Value IN (''CQ'', ''CR'',''CS'', ''RI'', ''RC'', ''FCM'')) OR (''@DocBaseType@''=''API'' AND (AD_Ref_List.Value IN (''QI'', ''VI'', ''SI'', ''FI'', ''DS'') OR AD_Ref_List.ValueName IN (''CommissionSettlement''))) OR (''@DocBaseType@''=''MOP'' AND AD_Ref_List.Value IN (''QI'', ''VI'')) OR (''@DocBaseType@'' = ''MMI'' AND AD_Ref_List.Value in (''MD'', ''VIY'', ''ISD'', ''IOD'')) OR (''@DocBaseType@''=''SDD'' AND AD_Ref_List.Value = ''NAR'') OR (''@DocBaseType@''=''SDC'' AND AD_Ref_List.Value = ''NAR'') OR (''@DocBaseType@''= ''CMB'' AND AD_Ref_List.VALUE IN (''CB'', ''BS'')) OR (''@DocBaseType@''=''POO'' AND AD_Ref_List.Value = ''MED'') OR (''@DocBaseType@'' NOT IN (''API'', ''ARI'', ''ARC'', ''MOP'') AND AD_Ref_List.Value NOT IN (''AQ'', ''AP'', ''CQ'', ''CR'', ''QI'', ''MED'')) /* fallback for the rest of the entries */',Updated=TO_TIMESTAMP('2024-10-01 10:52:17.079','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540219
;

-- Run mode: WEBUI

-- 2024-10-01T08:28:31.287Z
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsExcludeFromCommision,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,1000000,541126,TO_TIMESTAMP('2024-10-01 10:28:31.281','YYYY-MM-DD HH24:MI:SS.US'),100,'ARI',1,'de.metas.contracts',1000003,'N','N','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Debitoren Schlussabrechnung','Schlussabrechnung',TO_TIMESTAMP('2024-10-01 10:28:31.281','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-10-01T08:28:31.376Z
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_DocType_ID=541126 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2024-10-01T08:28:31.384Z
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541126 AND rol.IsManual='N')
;

-- 2024-10-01T08:28:39.138Z
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-01 10:28:39.138','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_DocType_ID=541126
;

-- 2024-10-01T08:28:41.051Z
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-01 10:28:41.051','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND C_DocType_ID=541126
;

-- 2024-10-01T08:29:05.360Z
UPDATE C_DocType_Trl SET Name='Debtors Final Invoice',Updated=TO_TIMESTAMP('2024-10-01 10:29:05.36','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541126
;

-- 2024-10-01T08:29:13.985Z
UPDATE C_DocType_Trl SET PrintName='Final Invoice',Updated=TO_TIMESTAMP('2024-10-01 10:29:13.985','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541126
;

-- 2024-10-01T08:29:20.611Z
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-01 10:29:20.611','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541126
;

-- 2024-10-01T08:29:26.802Z
UPDATE C_DocType SET DocNoSequence_ID=545456,Updated=TO_TIMESTAMP('2024-10-01 10:29:26.802','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541126
;

-- 2024-10-01T08:29:35.834Z
UPDATE C_DocType SET IsSOTrx='Y',Updated=TO_TIMESTAMP('2024-10-01 10:29:35.834','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541126
;

-- 2024-10-01T08:30:38.143Z
UPDATE C_DocType SET AD_PrintFormat_ID=1000015,Updated=TO_TIMESTAMP('2024-10-01 10:30:38.143','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541126
;

-- 2024-10-01T08:53:06.031Z
UPDATE C_DocType SET DocSubType='FI',Updated=TO_TIMESTAMP('2024-10-01 10:53:06.031','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541126
;

-- 2024-10-01T08:55:02.847Z
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsExcludeFromCommision,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,1000000,541127,TO_TIMESTAMP('2024-10-01 10:55:02.835','YYYY-MM-DD HH24:MI:SS.US'),100,'ARI',1,'de.metas.contracts',1000003,'N','N','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Debitoren Schlussabrechnung Gutschrift','Schlussabrechnung Gutschrift',TO_TIMESTAMP('2024-10-01 10:55:02.835','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-10-01T08:55:02.866Z
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_DocType_ID=541127 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2024-10-01T08:55:02.868Z
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541127 AND rol.IsManual='N')
;

-- 2024-10-01T08:55:11.683Z
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-01 10:55:11.682','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_DocType_ID=541127
;

-- 2024-10-01T08:55:13.924Z
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-01 10:55:13.924','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND C_DocType_ID=541127
;

-- 2024-10-01T08:55:32.206Z
UPDATE C_DocType_Trl SET Name='Debtors Final Credit Memo',Updated=TO_TIMESTAMP('2024-10-01 10:55:32.206','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541127
;

-- 2024-10-01T08:55:45.106Z
UPDATE C_DocType_Trl SET PrintName='Final Credit Memo',Updated=TO_TIMESTAMP('2024-10-01 10:55:45.106','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541127
;

-- 2024-10-01T08:58:14.522Z
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-01 10:58:14.521','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541127
;

-- 2024-10-01T08:57:17.675Z
UPDATE C_DocType SET AD_PrintFormat_ID=1000015,Updated=TO_TIMESTAMP('2024-10-01 10:57:17.675','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541127
;

-- 2024-10-01T08:57:22.915Z
UPDATE C_DocType SET IsSOTrx='Y',Updated=TO_TIMESTAMP('2024-10-01 10:57:22.915','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541127
;

-- 2024-10-01T08:57:44.395Z
UPDATE C_DocType SET DocBaseType='ARC',Updated=TO_TIMESTAMP('2024-10-01 10:57:44.395','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541127
;

-- 2024-10-01T08:57:50.287Z
UPDATE C_DocType SET DocSubType='FCM',Updated=TO_TIMESTAMP('2024-10-01 10:57:50.287','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541127
;

-- 2024-10-01T09:02:55.938Z
UPDATE C_DocType SET DocNoSequence_ID=545458,Updated=TO_TIMESTAMP('2024-10-01 11:02:55.938','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541127
;

-- 2024-10-01T08:59:50.156Z
INSERT INTO C_DocType_Invoicing_Pool (AD_Client_ID,AD_Org_ID,C_DocType_Invoicing_Pool_ID,Created,CreatedBy,IsActive,IsCreditedInvoiceReinvoicable,IsOnDistinctICTypes,IsSOTrx,Name,Negative_Amt_C_DocType_ID,Positive_Amt_C_DocType_ID,Updated,UpdatedBy) VALUES (1000000,1000000,540007,TO_TIMESTAMP('2024-10-01 10:59:50.146','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Y','Y','Y','Sales Final Invoice',541127,541126,TO_TIMESTAMP('2024-10-01 10:59:50.146','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-10-01T09:02:34.895Z
UPDATE C_DocType SET C_DocType_Invoicing_Pool_ID=540007,Updated=TO_TIMESTAMP('2024-10-01 11:02:34.895','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541126
;

-- 2024-10-01T09:02:39.810Z
UPDATE C_DocType SET C_DocType_Invoicing_Pool_ID=540007,Updated=TO_TIMESTAMP('2024-10-01 11:02:39.809','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541127
;

