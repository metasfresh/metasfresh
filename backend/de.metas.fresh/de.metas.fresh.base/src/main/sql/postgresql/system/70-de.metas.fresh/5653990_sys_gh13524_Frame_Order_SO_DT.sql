--
-- Script: /tmp/webui_migration_scripts_2022-08-29_2980774233665263819/5653550_migration_2022-08-29_postgresql.sql
-- User: metasfresh
-- OS user: root
--


-- 2022-08-29T10:21:49.766Z
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsExcludeFromCommision,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,1000000,541062,TO_TIMESTAMP('2022-08-29 12:21:49','YYYY-MM-DD HH24:MI:SS'),100,'SOO',1,'D',1000001,'N','N','Y','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Frame Order SO','Frame Order SO',TO_TIMESTAMP('2022-08-29 12:21:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-29T10:21:49.773Z
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_DocType_ID=541062 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2022-08-29T10:21:49.775Z
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541062 AND rol.IsManual='N')
;

-- 2022-08-29T10:21:53.292Z
UPDATE C_DocType SET DocumentCopies=0,Updated=TO_TIMESTAMP('2022-08-29 12:21:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541062
;

-- 2022-08-29T10:21:57.619Z
UPDATE C_DocType SET IsSOTrx='Y',Updated=TO_TIMESTAMP('2022-08-29 12:21:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541062
;

-- 2022-08-29T10:22:08.706Z
UPDATE C_DocType SET DocNoSequence_ID=545479,Updated=TO_TIMESTAMP('2022-08-29 12:22:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541062
;

-- 2022-08-29T10:22:14.069Z
UPDATE C_DocType SET AD_PrintFormat_ID=1000010,Updated=TO_TIMESTAMP('2022-08-29 12:22:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541062
;

-- 2022-08-29T10:22:20.776Z
UPDATE C_DocType SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2022-08-29 12:22:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541062
;

-- 2022-08-31T10:38:19.280Z
UPDATE C_DocType SET DocumentCopies=1,Updated=TO_TIMESTAMP('2022-08-31 12:38:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541062
;

-- 2022-08-31T10:38:35.734Z
UPDATE C_DocType SET DocSubType='FA',Updated=TO_TIMESTAMP('2022-08-31 12:38:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541062
;