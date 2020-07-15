-- 2017-04-12T18:32:10.833
-- URL zum Konzept
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,Description,DocBaseType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,0,540948,TO_TIMESTAMP('2017-04-12 18:32:10','YYYY-MM-DD HH24:MI:SS'),100,'Disposal','MMI',0,'de.metas.swat',1000005,'N','N','Y','Y','N','N','N','Y','N','N','N','N','N','N','N','Entsorgung','Inventur',TO_TIMESTAMP('2017-04-12 18:32:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-04-12T18:32:10.837
-- URL zum Konzept
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.C_DocType_ID, t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_DocType t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.C_DocType_ID=540948 AND NOT EXISTS (SELECT * FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2017-04-12T18:32:10.843
-- URL zum Konzept
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=540948 AND rol.IsManual='N')
;

-- 2017-04-12T18:32:31.456
-- URL zum Konzept
UPDATE C_DocType SET PrintName='Entsorgung',Updated=TO_TIMESTAMP('2017-04-12 18:32:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540948
;

-- 2017-04-12T18:32:31.460
-- URL zum Konzept
UPDATE C_DocType_Trl SET IsTranslated='N' WHERE C_DocType_ID=540948
;

-- 2017-04-18T16:40:19.168
-- URL zum Konzept
UPDATE C_DocType SET DocSubType='MD',Updated=TO_TIMESTAMP('2017-04-18 16:40:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540948
;

