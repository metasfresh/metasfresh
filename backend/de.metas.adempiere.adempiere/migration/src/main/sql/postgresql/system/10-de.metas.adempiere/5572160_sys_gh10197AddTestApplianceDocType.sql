-- 2020-11-05T11:56:20.268Z
-- URL zum Konzept
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,AD_PrintFormat_ID,C_DocType_ID,C_DocTypeInvoice_ID,C_DocTypeShipment_ID,Created,CreatedBy,DocBaseType,DocNoSequence_ID,DocSubType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,0,1000010,541004,1000002,1000011,TO_TIMESTAMP('2020-11-05 13:56:18','YYYY-MM-DD HH24:MI:SS'),100,'SOO',545479,'SO',0,'de.metas.swat',1000001,'N','N','Y','Y','N','N','N','Y','Y','N','N','N','N','N','Y','N','Testgerät Auftrag','Testgerät Auftrag',TO_TIMESTAMP('2020-11-05 13:56:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-05T11:56:20.337Z
-- URL zum Konzept
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_DocType_ID=541004 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2020-11-05T11:56:20.406Z
-- URL zum Konzept
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541004 AND rol.IsManual='N')
;

-- 2020-11-05T11:56:44.297Z
-- URL zum Konzept
UPDATE C_DocType_Trl SET IsTranslated='Y', Name='Testappliance Order', PrintName='Testappliance Order',Updated=TO_TIMESTAMP('2020-11-05 13:56:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541004
;

