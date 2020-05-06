

-- 2018-05-24T13:39:40.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (0,0,540956,TO_TIMESTAMP('2018-05-24 13:39:40','YYYY-MM-DD HH24:MI:SS'),100,'APC',1,'de.metas.invoice',0,'N','N','Y','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','Rückvergütungsrechnung','Rückvergütungsrechnung',TO_TIMESTAMP('2018-05-24 13:39:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-24T13:39:40.394
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_DocType t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.C_DocType_ID=540956 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2018-05-24T13:40:18.725
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET IsCreateCounter='N',Updated=TO_TIMESTAMP('2018-05-24 13:40:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540956
;

-- 2018-05-24T14:29:39.013
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,0,540957,TO_TIMESTAMP('2018-05-24 14:29:38','YYYY-MM-DD HH24:MI:SS'),100,'APC',1,'de.metas.invoice',1000000,'N','N','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Rückvergütungsrechnung','Rückvergütungsrechnung',TO_TIMESTAMP('2018-05-24 14:29:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-24T14:29:39.016
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_DocType t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.C_DocType_ID=540957 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2018-05-24T14:29:39.021
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=540957 AND rol.IsManual='N')
;

-- 2018-05-24T14:29:40.571
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET IsDocNoControlled='Y',Updated=TO_TIMESTAMP('2018-05-24 14:29:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540957
;

-- 2018-05-24T14:36:27.818
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocSubType='RI',Updated=TO_TIMESTAMP('2018-05-24 14:36:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540957
;

-- 2018-05-24T14:37:00.667
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocSubType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,0,540958,TO_TIMESTAMP('2018-05-24 14:37:00','YYYY-MM-DD HH24:MI:SS'),100,'APC','RC',1,'de.metas.invoice',1000000,'N','N','Y','Y','N','N','N','Y','N','N','N','N','N','N','N','N','Rückvergütungsgutschrift','Rückvergütungsgutschrift',TO_TIMESTAMP('2018-05-24 14:37:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-24T14:37:00.669
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_DocType t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.C_DocType_ID=540958 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2018-05-24T14:37:00.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=540958 AND rol.IsManual='N')
;

-- 2018-05-24T14:37:17.157
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-24 14:37:17','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Refund Creditmemo' WHERE C_DocType_ID=540958 AND AD_Language='en_US'
;

-- 2018-05-24T14:37:33.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-24 14:37:33','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Refund Invoice',PrintName='Refund Invoice' WHERE C_DocType_ID=540957 AND AD_Language='en_US'
;

-- 2018-05-24T14:37:42.276
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-24 14:37:42','YYYY-MM-DD HH24:MI:SS'),PrintName='Refund Creditmemo' WHERE C_DocType_ID=540958 AND AD_Language='en_US'
;

-- 2018-05-24T15:26:18.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,0,540960,TO_TIMESTAMP('2018-05-24 15:26:18','YYYY-MM-DD HH24:MI:SS'),100,'ARC',1,'de.metas.invoice',1000000,'N','N','Y','Y','N','N','N','Y','N','N','N','N','N','N','Y','N','Rückvergütungsrechnung Kunde','Rückvergütungsrechnung Kunde',TO_TIMESTAMP('2018-05-24 15:26:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-24T15:26:18.849
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_DocType t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.C_DocType_ID=540960 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2018-05-24T15:26:18.851
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=540960 AND rol.IsManual='N')
;

-- 2018-05-24T15:26:45.430
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-24 15:26:45','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Refund Invoice Customer',PrintName='Refund Invoice Customer' WHERE C_DocType_ID=540960 AND AD_Language='en_US'
;

-- 2018-05-24T15:27:24.054
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-24 15:27:24','YYYY-MM-DD HH24:MI:SS'),PrintName='Refund Invoice' WHERE C_DocType_ID=540960 AND AD_Language='en_US'
;

-- 2018-05-24T15:27:29.762
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET PrintName='Rückvergütungsrechnung',Updated=TO_TIMESTAMP('2018-05-24 15:27:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540960
;

-- 2018-05-24T15:30:24.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET Name='Rückvergütungsgutschrift Lieferant',Updated=TO_TIMESTAMP('2018-05-24 15:30:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540958
;

-- 2018-05-24T15:30:31.660
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET Name='Rückvergütungsrechnung Lieferant',Updated=TO_TIMESTAMP('2018-05-24 15:30:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540957
;

-- 2018-05-24T15:31:00.962
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,0,540961,TO_TIMESTAMP('2018-05-24 15:31:00','YYYY-MM-DD HH24:MI:SS'),100,'ARC',1,'de.metas.invoice',1000000,'N','N','Y','Y','N','N','N','Y','N','N','N','N','N','N','N','N','Rückvergütungsgutschrift Kunde','Rückvergütungsgutschrift',TO_TIMESTAMP('2018-05-24 15:31:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-05-24T15:31:00.963
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_DocType t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.C_DocType_ID=540961 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2018-05-24T15:31:00.964
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=540961 AND rol.IsManual='N')
;

-- 2018-05-24T16:00:26.102
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET IsSOTrx='Y',Updated=TO_TIMESTAMP('2018-05-24 16:00:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540961
;

-- 2018-05-24T16:00:38.639
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocSubType='RC',Updated=TO_TIMESTAMP('2018-05-24 16:00:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540961
;

-- 2018-05-24T16:00:47.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocSubType='RC',Updated=TO_TIMESTAMP('2018-05-24 16:00:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540960
;

-- 2018-05-24T16:01:18.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-24 16:01:18','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Refund Creditmemo Customer',PrintName='Refund Creditmemo' WHERE C_DocType_ID=540961 AND AD_Language='en_US'
;

-- 2018-05-24T16:01:38.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-24 16:01:38','YYYY-MM-DD HH24:MI:SS'),Name='Refund Invoice Vendor' WHERE C_DocType_ID=540957 AND AD_Language='en_US'
;

-- 2018-05-24T16:01:50.356
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-05-24 16:01:50','YYYY-MM-DD HH24:MI:SS'),Name='Refund Creditmemo Vendor' WHERE C_DocType_ID=540958 AND AD_Language='en_US'
;

-- 2018-05-24T16:54:35.873
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocSubType='RI',Updated=TO_TIMESTAMP('2018-05-24 16:54:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=540960
;

