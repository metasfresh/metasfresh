-- 2018-09-19T14:52:04.977
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType (CreatedBy,IsDocNoControlled,AD_Client_ID,IsActive,Created,DocNoSequence_ID,GL_Category_ID,PrintName,HasCharges,HasProforma,DocumentCopies,IsSOTrx,IsDefaultCounterDoc,IsPickQAConfirm,IsShipConfirm,IsInTransit,IsSplitWhenDifference,IsCreateCounter,IsIndexed,IsDefault,UpdatedBy,Updated,C_DocType_ID,DocBaseType,AD_Org_ID,Name,IsCopyDescriptionToDocument,EntityType,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete) VALUES (100,'Y',1000000,'Y',TO_TIMESTAMP('2018-09-19 14:52:04','YYYY-MM-DD HH24:MI:SS'),553567,1000000,'Mahnung','N','N',1,'N','N','N','N','N','N','Y','N','N',100,TO_TIMESTAMP('2018-09-19 14:52:04','YYYY-MM-DD HH24:MI:SS'),540965,'DUN',0,'Mahnung','Y','de.metas.dunning','N','N')
;

-- 2018-09-19T14:52:04.988
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, PrintName,Name,Description,DocumentNote, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.C_DocType_ID, t.PrintName,t.Name,t.Description,t.DocumentNote, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_DocType t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.C_DocType_ID=540965 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2018-09-19T14:52:04.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=540965 AND rol.IsManual='N')
;

