-- select migrationscript_ignore('70-de.metas.handlingunits/5585180_cli_Materialentnahme_doctype.sql');

-- 2021-04-09T08:31:50.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,1000000,541012,TO_TIMESTAMP('2021-04-09 11:31:50','YYYY-MM-DD HH24:MI:SS'),100,'MMI',1,'de.metas.handlingunits',1000005,'N','N','Y','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','Materialentnahme','Materialentnahme',TO_TIMESTAMP('2021-04-09 11:31:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-09T08:31:50.973Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_DocType_ID=541012 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2021-04-09T08:31:50.977Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541012 AND rol.IsManual='N')
;

-- 2021-04-09T08:32:36.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocSubType='IUI',Updated=TO_TIMESTAMP('2021-04-09 11:32:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541012
;

-- 2021-04-09T08:33:12.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocNoSequence_ID=545347,Updated=TO_TIMESTAMP('2021-04-09 11:33:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541012
;

-- 2021-04-09T08:33:31.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocNoSequence_ID=1000,Updated=TO_TIMESTAMP('2021-04-09 11:33:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541012
;

-- 2021-04-09T08:33:57.486Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET AD_PrintFormat_ID=540062,Updated=TO_TIMESTAMP('2021-04-09 11:33:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541012
;

-- 2021-04-09T08:34:07.803Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2021-04-09 11:34:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541012
;

-- 2021-04-09T08:34:30.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocumentCopies=0,Updated=TO_TIMESTAMP('2021-04-09 11:34:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541012
;

