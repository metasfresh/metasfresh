-- C_POS_Payment: UNKNOWN processing status, card checkout columns and cashier messages for a till card sale whose result is unknown.
-- IDs allocated from the central ID server.

INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,544398 /*From ID Server*/,541897,TO_TIMESTAMP('2026-11-10 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Unbekannt',TO_TIMESTAMP('2026-11-10 09:00:01','YYYY-MM-DD HH24:MI:SS'),100,'UNKNOWN','UNKNOWN')
;

INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Ref_List_ID=544398 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;


UPDATE AD_Ref_List_Trl SET Name='Unknown',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=544398 AND AD_Language='en_US'
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=544398 AND AD_Language='de_DE'
;

UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=544398 AND AD_Language='de_CH'
;


INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585614 /*From ID Server*/,0,'CardCheckoutStartedAt',TO_TIMESTAMP('2026-11-10 09:00:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Kartenzahlung gestartet am','Kartenzahlung gestartet am',TO_TIMESTAMP('2026-11-10 09:00:06','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID,Name,PrintName,Description,Help,PO_Name,PO_PrintName,PO_Description,PO_Help, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID,t.Name,t.PrintName,t.Description,t.Help,t.PO_Name,t.PO_PrintName,t.PO_Description,t.PO_Help, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=585614 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;


UPDATE AD_Element_Trl SET Name='Card checkout started at',PrintName='Card checkout started at',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585614 AND AD_Language='en_US'
;

UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585614 AND AD_Language='de_DE'
;

UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585614 AND AD_Language='de_CH'
;


INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsEncrypted,IsExcludeFromZoomTargets,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593826 /*From ID Server*/,585614,0,16,542437,'CardCheckoutStartedAt',TO_TIMESTAMP('2026-11-10 09:00:10','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',7,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','N','Kartenzahlung gestartet am','NP',0,TO_TIMESTAMP('2026-11-10 09:00:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=593826 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


UPDATE AD_Column_Trl SET Name='Card checkout started at',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=593826 AND AD_Language='en_US'
;

UPDATE AD_Column_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=593826 AND AD_Language='de_DE'
;

UPDATE AD_Column_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=593826 AND AD_Language='de_CH'
;


INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585615 /*From ID Server*/,0,'SUMUP_ClientTransactionID',TO_TIMESTAMP('2026-11-10 09:00:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','SumUp-Client-Transaktions-ID','SumUp-Client-Transaktions-ID',TO_TIMESTAMP('2026-11-10 09:00:16','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID,Name,PrintName,Description,Help,PO_Name,PO_PrintName,PO_Description,PO_Help, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID,t.Name,t.PrintName,t.Description,t.Help,t.PO_Name,t.PO_PrintName,t.PO_Description,t.PO_Help, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=585615 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;


UPDATE AD_Element_Trl SET Name='SumUp client transaction ID',PrintName='SumUp client transaction ID',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585615 AND AD_Language='en_US'
;

UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585615 AND AD_Language='de_DE'
;

UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585615 AND AD_Language='de_CH'
;


INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsEncrypted,IsExcludeFromZoomTargets,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593827 /*From ID Server*/,585615,0,10,542437,'SUMUP_ClientTransactionID',TO_TIMESTAMP('2026-11-10 09:00:20','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','N','SumUp-Client-Transaktions-ID','NP',0,TO_TIMESTAMP('2026-11-10 09:00:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=593827 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


UPDATE AD_Column_Trl SET Name='SumUp client transaction ID',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=593827 AND AD_Language='en_US'
;

UPDATE AD_Column_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=593827 AND AD_Language='de_DE'
;

UPDATE AD_Column_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=593827 AND AD_Language='de_CH'
;


INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585616 /*From ID Server*/,0,'ReleasedSUMUPClientTransactionID',TO_TIMESTAMP('2026-11-10 09:00:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Freigegebene SumUp-Client-Transaktions-ID','Freigegebene SumUp-Client-Transaktions-ID',TO_TIMESTAMP('2026-11-10 09:00:26','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID,Name,PrintName,Description,Help,PO_Name,PO_PrintName,PO_Description,PO_Help, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID,t.Name,t.PrintName,t.Description,t.Help,t.PO_Name,t.PO_PrintName,t.PO_Description,t.PO_Help, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=585616 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;


UPDATE AD_Element_Trl SET Name='Released SumUp client transaction ID',PrintName='Released SumUp client transaction ID',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585616 AND AD_Language='en_US'
;

UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585616 AND AD_Language='de_DE'
;

UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585616 AND AD_Language='de_CH'
;


INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsEncrypted,IsExcludeFromZoomTargets,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593828 /*From ID Server*/,585616,0,10,542437,'ReleasedSUMUPClientTransactionID',TO_TIMESTAMP('2026-11-10 09:00:30','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','N','Freigegebene SumUp-Client-Transaktions-ID','NP',0,TO_TIMESTAMP('2026-11-10 09:00:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=593828 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


UPDATE AD_Column_Trl SET Name='Released SumUp client transaction ID',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=593828 AND AD_Language='en_US'
;

UPDATE AD_Column_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=593828 AND AD_Language='de_DE'
;

UPDATE AD_Column_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=593828 AND AD_Language='de_CH'
;


INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585617 /*From ID Server*/,0,'CardLookupFailingSince',TO_TIMESTAMP('2026-11-10 09:00:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','SumUp-Abfrage fehlgeschlagen seit','SumUp-Abfrage fehlgeschlagen seit',TO_TIMESTAMP('2026-11-10 09:00:36','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID,Name,PrintName,Description,Help,PO_Name,PO_PrintName,PO_Description,PO_Help, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID,t.Name,t.PrintName,t.Description,t.Help,t.PO_Name,t.PO_PrintName,t.PO_Description,t.PO_Help, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=585617 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;


UPDATE AD_Element_Trl SET Name='SumUp lookup failing since',PrintName='SumUp lookup failing since',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585617 AND AD_Language='en_US'
;

UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585617 AND AD_Language='de_DE'
;

UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585617 AND AD_Language='de_CH'
;


INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsEncrypted,IsExcludeFromZoomTargets,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593829 /*From ID Server*/,585617,0,16,542437,'CardLookupFailingSince',TO_TIMESTAMP('2026-11-10 09:00:40','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',7,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','N','SumUp-Abfrage fehlgeschlagen seit','NP',0,TO_TIMESTAMP('2026-11-10 09:00:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=593829 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;


UPDATE AD_Column_Trl SET Name='SumUp lookup failing since',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=593829 AND AD_Language='en_US'
;

UPDATE AD_Column_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=593829 AND AD_Language='de_DE'
;

UPDATE AD_Column_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=593829 AND AD_Language='de_CH'
;


ALTER TABLE C_POS_Payment ADD COLUMN CardCheckoutStartedAt TIMESTAMP WITH TIME ZONE, ADD COLUMN SUMUP_ClientTransactionID VARCHAR(255), ADD COLUMN ReleasedSUMUPClientTransactionID VARCHAR(255), ADD COLUMN CardLookupFailingSince TIMESTAMP WITH TIME ZONE
;

INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,546005 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-10 09:00:45','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Ergebnis der Kartenzahlung unbekannt — bitte Kartenterminal prüfen','E',TO_TIMESTAMP('2026-11-10 09:00:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.POSPayment.CardPaymentResultUnknown')
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID,t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=546005 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;


UPDATE AD_Message_Trl SET MsgText='Card payment result unknown — check the card reader',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=546005 AND AD_Language='en_US'
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=546005 AND AD_Language='de_DE'
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=546005 AND AD_Language='de_CH'
;


INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,546006 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-10 09:00:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Freigeben ist nur bei unbekanntem Ergebnis möglich','E',TO_TIMESTAMP('2026-11-10 09:00:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.POSPayment.ReleaseOnlyIfResultUnknown')
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID,t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=546006 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;


UPDATE AD_Message_Trl SET MsgText='Release is possible only when the result is unknown',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=546006 AND AD_Language='en_US'
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=546006 AND AD_Language='de_DE'
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=546006 AND AD_Language='de_CH'
;


INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,546007 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-10 09:00:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Freigabe erst nach Ablauf der Wartezeit möglich','E',TO_TIMESTAMP('2026-11-10 09:00:56','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.POSPayment.ReleaseNotYetAllowed')
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID,t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=546007 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;


UPDATE AD_Message_Trl SET MsgText='Release is possible only after the waiting time',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=546007 AND AD_Language='en_US'
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=546007 AND AD_Language='de_DE'
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:00:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=546007 AND AD_Language='de_CH'
;


INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,546008 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-10 09:01:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','SumUp hat die Zahlung genehmigt; Freigabe nicht möglich','E',TO_TIMESTAMP('2026-11-10 09:01:01','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.POSPayment.ReleaseRefusedApproved')
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID,t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=546008 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;


UPDATE AD_Message_Trl SET MsgText='SumUp approved the payment; it cannot be released',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:01:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=546008 AND AD_Language='en_US'
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:01:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=546008 AND AD_Language='de_DE'
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:01:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=546008 AND AD_Language='de_CH'
;


INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,546009 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-10 09:01:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Kartenzahlung vom Kassierer als nicht belastet freigegeben','E',TO_TIMESTAMP('2026-11-10 09:01:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.POSPayment.ReleasedAsNotCharged')
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID,t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=546009 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;


UPDATE AD_Message_Trl SET MsgText='Card payment released by the cashier as not charged',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:01:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=546009 AND AD_Language='en_US'
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:01:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=546009 AND AD_Language='de_DE'
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:01:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=546009 AND AD_Language='de_CH'
;


INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,546010 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-10 09:01:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Ergebnis der Kartenzahlung konnte nicht gespeichert werden (SumUp-Transaktion: {0}) — bitte im SumUp-Dashboard prüfen','E',TO_TIMESTAMP('2026-11-10 09:01:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.POSPayment.CardResultNotStored')
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID,t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=546010 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;


UPDATE AD_Message_Trl SET MsgText='The card payment result could not be stored (SumUp transaction: {0}) — please check in the SumUp dashboard',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:01:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=546010 AND AD_Language='en_US'
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:01:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=546010 AND AD_Language='de_DE'
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:01:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=546010 AND AD_Language='de_CH'
;


INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,546011 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-10 09:01:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','SumUp-Abfrage der Kartenzahlung fehlgeschlagen','E',TO_TIMESTAMP('2026-11-10 09:01:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.POSPayment.SumUpLookupFailed')
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID,t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=546011 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;


UPDATE AD_Message_Trl SET MsgText='SumUp lookup of the card payment failed',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:01:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=546011 AND AD_Language='en_US'
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:01:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=546011 AND AD_Language='de_DE'
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:01:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=546011 AND AD_Language='de_CH'
;


INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value)
VALUES (0,546012 /*From ID Server*/,0,TO_TIMESTAMP('2026-11-10 09:01:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Späte SumUp-Genehmigung einer gelöschten oder freigegebenen Kartenzahlung — nicht gebucht, bitte prüfen','E',TO_TIMESTAMP('2026-11-10 09:01:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos.POSPayment.LateApprovalNotBooked')
;

INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID,MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID,t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Message_ID=546012 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;


UPDATE AD_Message_Trl SET MsgText='Late SumUp approval of a deleted or released card payment — not booked, please check',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:01:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=546012 AND AD_Language='en_US'
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:01:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=546012 AND AD_Language='de_DE'
;

UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-11-10 09:01:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=546012 AND AD_Language='de_CH'
;

