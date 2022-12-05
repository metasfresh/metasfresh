-- 2022-08-23T12:43:49.077Z
UPDATE C_DocType SET Name='Call Order PO', PrintName='Call Order PO',Updated=TO_TIMESTAMP('2022-08-23 15:43:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541034
;

-- 2022-08-23T12:43:49.144Z
UPDATE C_DocType_Trl trl SET Description=NULL, DocumentNote=NULL, Name='Call Order PO', PrintName='Call Order PO'  WHERE C_DocType_ID=541034 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2022-08-23T12:44:56.724Z
UPDATE C_DocType_Trl SET IsTranslated='Y', Name='Abrufbestellung',Updated=TO_TIMESTAMP('2022-08-23 15:44:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_DocType_ID=541034
;

-- 2022-08-23T12:45:01.180Z
UPDATE C_DocType_Trl SET IsTranslated='Y', Name='Abrufbestellung',Updated=TO_TIMESTAMP('2022-08-23 15:45:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND C_DocType_ID=541034
;

-- 2022-08-23T12:48:02.429Z
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocNoSequence_ID,DocSubType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsExcludeFromCommision,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,0,541057,TO_TIMESTAMP('2022-08-23 15:48:02','YYYY-MM-DD HH24:MI:SS'),100,'POO',545465,'FA',1,'de.metas.contracts',1000000,'N','N','Y','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Frame Order PO','Frame Order PO',TO_TIMESTAMP('2022-08-23 15:48:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-23T12:48:02.529Z
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_DocType_ID=541057 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2022-08-23T12:48:02.562Z
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541057 AND rol.IsManual='N')
;

-- 2022-08-23T12:48:44.326Z
UPDATE C_DocType SET GL_Category_ID=1000001,Updated=TO_TIMESTAMP('2022-08-23 15:48:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541057
;

