-- 2022-03-15T11:07:36.343Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsExcludeFromCommision,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,1000000,541034,TO_TIMESTAMP('2022-03-15 13:07:36','YYYY-MM-DD HH24:MI:SS'),100,'POO',1,'de.metas.contracts',0,'N','N','Y','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Abrufbestellung','Abrufbestellung',TO_TIMESTAMP('2022-03-15 13:07:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-03-15T11:07:36.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_DocType_ID=541034 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2022-03-15T11:07:36.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541034 AND rol.IsManual='N')
;

-- 2022-03-15T11:07:45.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2022-03-15 13:07:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541034
;

-- 2022-03-15T11:08:49.412Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocSubType='CAO',Updated=TO_TIMESTAMP('2022-03-15 13:08:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541034
;

-- 2022-03-16T13:49:18.720Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (1000000,1000000,555854,TO_TIMESTAMP('2022-03-16 15:49:18','YYYY-MM-DD HH24:MI:SS'),100,1000000,100,1,'Y','N','N','N','Call Order POTrx','N',1000000,TO_TIMESTAMP('2022-03-16 15:49:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-03-16T13:49:21.494Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET IsAutoSequence='Y',Updated=TO_TIMESTAMP('2022-03-16 15:49:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555854
;

-- 2022-03-16T13:49:41.396Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2022-03-16 15:49:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555854
;

-- 2022-03-16T13:49:51.602Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET Description='Call Order POTrx',Updated=TO_TIMESTAMP('2022-03-16 15:49:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=555854
;

-- 2022-03-16T13:52:06.363Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET DocNoSequence_ID=555854,Updated=TO_TIMESTAMP('2022-03-16 15:52:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541034
;


/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


-- 2022-03-17T10:42:40.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET Name='Abrufbestellung',Updated=TO_TIMESTAMP('2022-03-17 12:42:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541034
;

-- 2022-03-17T10:42:40.393Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl trl SET Description=NULL, DocumentNote=NULL, Name='Abrufbestellung', PrintName='Call-off order'  WHERE C_DocType_ID=541034 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2022-03-17T10:42:42.694Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType SET PrintName='Abrufbestellung',Updated=TO_TIMESTAMP('2022-03-17 12:42:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541034
;

-- 2022-03-17T10:42:42.696Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl trl SET Description=NULL, DocumentNote=NULL, Name='Abrufbestellung', PrintName='Abrufbestellung'  WHERE C_DocType_ID=541034 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2022-03-17T10:43:03.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl SET PrintName='Call-off order',Updated=TO_TIMESTAMP('2022-03-17 12:43:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541034
;

-- 2022-03-17T10:43:04.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl SET Name='Call-off order',Updated=TO_TIMESTAMP('2022-03-17 12:43:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541034
;

