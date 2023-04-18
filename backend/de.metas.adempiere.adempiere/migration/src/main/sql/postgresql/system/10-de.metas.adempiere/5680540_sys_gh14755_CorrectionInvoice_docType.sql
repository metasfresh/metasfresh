-- Reference: C_DocType SubType
-- Value: CI
-- ValueName: CorrectionInvoice
-- 2023-03-06T13:08:01.267Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,148,543416,TO_TIMESTAMP('2023-03-06 14:08:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','NB - Correction Invoice',TO_TIMESTAMP('2023-03-06 14:08:01','YYYY-MM-DD HH24:MI:SS'),100,'CI','CorrectionInvoice')
;

-- 2023-03-06T13:08:01.269Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543416 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: C_DocType SubType -> CI_CorrectionInvoice
-- 2023-03-06T13:08:46.393Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='NB - Korrekturrechnung',Updated=TO_TIMESTAMP('2023-03-06 14:08:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543416
;

-- Reference Item: C_DocType SubType -> CI_CorrectionInvoice
-- 2023-03-06T13:08:50.376Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-03-06 14:08:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543416
;

-- Reference Item: C_DocType SubType -> CI_CorrectionInvoice
-- 2023-03-06T13:09:03.501Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='NB - Korrekturrechnung',Updated=TO_TIMESTAMP('2023-03-06 14:09:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543416
;

-- 2023-03-06T13:09:03.502Z
UPDATE AD_Ref_List SET Name='NB - Korrekturrechnung' WHERE AD_Ref_List_ID=543416
;

-- Name: C_DocSubType Compatible
-- 2023-03-06T13:52:50.748Z
UPDATE AD_Val_Rule SET Code='(''@DocBaseType@''=''ARI'' AND AD_Ref_List.ValueName IN (''AQ'', ''AP'', ''Healthcare_CH-EA'', ''Healthcare_CH-GM'', ''Healthcare_CH-KV'', ''Healthcare_CH-KT'', ''RD'', ''LS'', ''CorrectionInvoice'')) OR (''@DocBaseType@''=''ARC'' AND AD_Ref_List.Value IN (''CQ'', ''CR'',''CS'', ''RI'', ''RC'')) OR (''@DocBaseType@''=''API'' AND (AD_Ref_List.Value IN (''QI'', ''VI'', ''SI'') OR AD_Ref_List.ValueName IN (''CommissionSettlement''))) OR (''@DocBaseType@''=''MOP'' AND AD_Ref_List.Value IN (''QI'', ''VI'')) OR (''@DocBaseType@'' = ''MMI'' AND AD_Ref_List.Value in (''MD'', ''VIY'', ''ISD'', ''IOD'')) OR (''@DocBaseType@''=''SDD'' AND AD_Ref_List.Value = ''NAR'') OR (''@DocBaseType@''=''SDC'' AND AD_Ref_List.Value = ''NAR'') OR (''@DocBaseType@''= ''CMB'' AND AD_Ref_List.VALUE IN (''CB'', ''BS'')) OR (''@DocBaseType@''=''POO'' AND AD_Ref_List.Value = ''MED'') OR (''@DocBaseType@'' NOT IN (''API'', ''ARI'', ''ARC'', ''MOP'') AND AD_Ref_List.Value NOT IN (''AQ'', ''AP'', ''CQ'', ''CR'', ''QI'', ''MED'')) /* fallback for the rest of the entries */',Updated=TO_TIMESTAMP('2023-03-06 14:52:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540219
;

-- 2023-03-06T14:45:55.519Z
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,AD_PrintFormat_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocNoSequence_ID,DocSubType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsExcludeFromCommision,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,0,1000015,541100,TO_TIMESTAMP('2023-03-06 15:45:55','YYYY-MM-DD HH24:MI:SS'),100,'ARI',545456,'CI',0,'de.metas.swat',1000003,'N','N','Y','Y','N','N','N','Y','N','Y','N','N','N','N','N','Y','N','Korrekturrechnung','Korrekturrechnung',TO_TIMESTAMP('2023-03-06 15:45:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-06T14:45:55.532Z
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_DocType_ID=541100 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2023-03-06T14:45:55.534Z
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,1000000,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541100 AND rol.IsManual='N')
;

-- 2023-03-06T14:48:06.152Z
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-03-06 15:48:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_DocType_ID=541100
;

-- 2023-03-06T14:48:09.190Z
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-03-06 15:48:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND C_DocType_ID=541100
;

-- 2023-03-06T14:48:28.619Z
UPDATE C_DocType_Trl SET IsTranslated='Y', Name='Correction Invoice', PrintName='Correction Invoice',Updated=TO_TIMESTAMP('2023-03-06 15:48:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541100
;

