-- Reference: C_DocType SubType
-- Value: IVI
-- ValueName: InternalVendorInvoice
-- 2022-10-18T06:42:09.063Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543309,148,TO_TIMESTAMP('2022-10-18 09:42:08.8','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','InternalVendorInvoice',TO_TIMESTAMP('2022-10-18 09:42:08.8','YYYY-MM-DD HH24:MI:SS.US'),100,'IVI','InternalVendorInvoice')
;

-- 2022-10-18T06:42:09.066Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543309 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: C_DocType SubType -> IVI_InternalVendorInvoice
-- 2022-10-18T06:42:18.940Z
UPDATE AD_Ref_List_Trl SET Name='Interne Kreditorenrechnung',Updated=TO_TIMESTAMP('2022-10-18 09:42:18.94','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543309
;

-- Reference Item: C_DocType SubType -> IVI_InternalVendorInvoice
-- 2022-10-18T06:42:22.579Z
UPDATE AD_Ref_List_Trl SET Name='Interne Kreditorenrechnung',Updated=TO_TIMESTAMP('2022-10-18 09:42:22.579','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543309
;

-- Reference Item: C_DocType SubType -> IVI_InternalVendorInvoice
-- 2022-10-18T06:42:27.172Z
UPDATE AD_Ref_List_Trl SET Name='Internal Vendor Invoice',Updated=TO_TIMESTAMP('2022-10-18 09:42:27.171','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543309
;

-- Reference Item: C_DocType SubType -> IVI_InternalVendorInvoice
-- 2022-10-18T06:42:31.921Z
UPDATE AD_Ref_List_Trl SET Name='Interne Kreditorenrechnung',Updated=TO_TIMESTAMP('2022-10-18 09:42:31.921','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543309
;

-- Reference Item: C_DocType SubType -> IVI_InternalVendorInvoice
-- 2022-10-18T06:42:34.560Z
UPDATE AD_Ref_List_Trl SET Name='Interne Kreditorenrechnung',Updated=TO_TIMESTAMP('2022-10-18 09:42:34.56','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543309
;

-- Reference: C_DocType SubType
-- Value: IVI
-- ValueName: InternalVendorInvoice
-- 2022-10-18T06:43:16.727Z
UPDATE AD_Ref_List SET Name='Internal Vendor Invoice',Updated=TO_TIMESTAMP('2022-10-18 09:43:16.727','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543309
;

-- Name: C_DocSubType Compatible
-- 2022-10-18T06:45:26.734Z
UPDATE AD_Val_Rule SET Code='(''@DocBaseType@''=''ARI'' AND AD_Ref_List.ValueName IN (''AQ'', ''AP'', ''Healthcare_CH-EA'', ''Healthcare_CH-GM'', ''Healthcare_CH-KV'', ''Healthcare_CH-KT'', ''RD'')) OR (''@DocBaseType@''=''ARC'' AND AD_Ref_List.Value IN (''CQ'', ''CR'',''CS'', ''RI'', ''RC'')) OR (''@DocBaseType@''=''API'' AND (AD_Ref_List.Value IN (''QI'', ''VI'', ''II'', ''WH'', ''IVI'') OR AD_Ref_List.ValueName IN (''CommissionSettlement''))) OR (''@DocBaseType@''=''MOP'' AND AD_Ref_List.Value IN (''QI'', ''VI'')) OR (''@DocBaseType@'' = ''MMI'' AND AD_Ref_List.Value in (''MD'', ''VIY'')) OR (''@DocBaseType@''=''SDD'' AND AD_Ref_List.Value = ''NAR'') OR (''@DocBaseType@''=''SDC'' AND AD_Ref_List.Value = ''NAR'') OR (''@DocBaseType@''= ''CMB'' AND AD_Ref_List.VALUE IN (''CB'', ''BS'')) OR (''@DocBaseType@''=''POO'' AND AD_Ref_List.Value = ''MED'') OR (''@DocBaseType@'' NOT IN (''API'', ''ARI'', ''ARC'', ''MOP'') AND AD_Ref_List.Value NOT IN (''AQ'', ''AP'', ''CQ'', ''CR'', ''QI'', ''MED'')) /* fallback for the rest of the entries */',Updated=TO_TIMESTAMP('2022-10-18 09:45:26.734','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540219
;



-- 2022-10-18T06:47:07.247Z
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsExcludeFromCommision,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,1000000,541080,TO_TIMESTAMP('2022-10-18 09:47:07.237','YYYY-MM-DD HH24:MI:SS.US'),100,'API',1,'D',1000006,'N','N','Y','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Internal Vendor Invoice','Internal Vendor Invoice',TO_TIMESTAMP('2022-10-18 09:47:07.237','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-18T06:47:07.281Z
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_DocType_ID=541080 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2022-10-18T06:47:07.285Z
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541080 AND rol.IsManual='N')
;

-- 2022-10-18T06:47:15.720Z
UPDATE C_DocType SET DocSubType='IVI',Updated=TO_TIMESTAMP('2022-10-18 09:47:15.72','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541080
;

-- 2022-10-18T06:47:36.589Z
UPDATE C_DocType_Trl SET PrintName='Interne Kreditorenrechnung',Updated=TO_TIMESTAMP('2022-10-18 09:47:36.588','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_DocType_ID=541080
;

-- 2022-10-18T06:47:38.210Z
UPDATE C_DocType_Trl SET Name='Interne Kreditorenrechnung',Updated=TO_TIMESTAMP('2022-10-18 09:47:38.21','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_DocType_ID=541080
;

-- 2022-10-18T06:47:45.571Z
UPDATE C_DocType_Trl SET PrintName='Interne Kreditorenrechnung',Updated=TO_TIMESTAMP('2022-10-18 09:47:45.571','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND C_DocType_ID=541080
;

-- 2022-10-18T06:47:47.030Z
UPDATE C_DocType_Trl SET Name='Interne Kreditorenrechnung',Updated=TO_TIMESTAMP('2022-10-18 09:47:47.029','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND C_DocType_ID=541080
;

-- 2022-10-18T06:47:52.684Z
UPDATE C_DocType_Trl SET PrintName='Interne Kreditorenrechnung',Updated=TO_TIMESTAMP('2022-10-18 09:47:52.684','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND C_DocType_ID=541080
;

-- 2022-10-18T06:47:54.769Z
UPDATE C_DocType_Trl SET Name='Interne Kreditorenrechnung',Updated=TO_TIMESTAMP('2022-10-18 09:47:54.769','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND C_DocType_ID=541080
;

-- 2022-10-18T06:47:59.822Z
UPDATE C_DocType_Trl SET PrintName='Interne Kreditorenrechnung',Updated=TO_TIMESTAMP('2022-10-18 09:47:59.822','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND C_DocType_ID=541080
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



-- 2022-10-18T06:48:01.053Z
UPDATE C_DocType_Trl SET Name='Interne Kreditorenrechnung',Updated=TO_TIMESTAMP('2022-10-18 09:48:01.053','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND C_DocType_ID=541080
;

-- 2022-10-18T06:48:15.688Z
UPDATE C_DocType SET DocNoSequence_ID=555719,Updated=TO_TIMESTAMP('2022-10-18 09:48:15.688','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541080
;

-- 2022-10-18T07:01:50.518Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (1000000,1000000,556037,TO_TIMESTAMP('2022-10-18 10:01:50.513','YYYY-MM-DD HH24:MI:SS.US'),100,1000000,100,1,'Y','N','N','N','IVI Internal Vendor Invoice','N',1000000,TO_TIMESTAMP('2022-10-18 10:01:50.513','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-18T07:01:50.623Z
UPDATE AD_Sequence SET IsAutoSequence='Y',Updated=TO_TIMESTAMP('2022-10-18 10:01:50.623','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Sequence_ID=556037
;

-- 2022-10-18T07:02:00.821Z
UPDATE AD_Sequence SET Description='IVI Internal Vendor Invoice',Updated=TO_TIMESTAMP('2022-10-18 10:02:00.82','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Sequence_ID=556037
;

-- 2022-10-18T07:02:06.493Z
UPDATE AD_Sequence SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2022-10-18 10:02:06.493','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Sequence_ID=556037
;

-- 2022-10-18T07:02:25.129Z
UPDATE C_DocType SET DocNoSequence_ID=556037,Updated=TO_TIMESTAMP('2022-10-18 10:02:25.128','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541080
;



-- 2022-10-18T09:21:06.068Z
UPDATE C_DocType SET AD_PrintFormat_ID=1000012,Updated=TO_TIMESTAMP('2022-10-18 12:21:06.062','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541080
;

-- 2022-10-18T09:21:28.733Z
UPDATE C_DocType SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2022-10-18 12:21:28.733','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541080
;


