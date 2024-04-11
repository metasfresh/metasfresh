-- Run mode: SWING_CLIENT

-- Value: CreateFinalInvoice
-- Classname: de.metas.contracts.finalinvoice.process.C_ModularFinalInvoice
-- 2024-04-04T16:42:08.930Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585375,'Y','de.metas.contracts.finalinvoice.process.C_ModularFinalInvoice','N',TO_TIMESTAMP('2024-04-04 19:42:07.754','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Schlussrechnung erstellen','json','N','N','xls','Java',TO_TIMESTAMP('2024-04-04 19:42:07.754','YYYY-MM-DD HH24:MI:SS.US'),100,'CreateFinalInvoice')
;

-- 2024-04-04T16:42:08.938Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585375 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: CreateFinalInvoice(de.metas.contracts.finalinvoice.process.C_ModularFinalInvoice)
-- 2024-04-04T16:42:25.221Z
UPDATE AD_Process_Trl SET Name='Create final invoice',Updated=TO_TIMESTAMP('2024-04-04 19:42:25.221','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585375
;

-- Process: CreateFinalInvoice(de.metas.contracts.finalinvoice.process.C_ModularFinalInvoice)
-- Table: C_Flatrate_Term
-- EntityType: de.metas.contracts
-- 2024-04-04T16:42:59.972Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585375,540320,541473,TO_TIMESTAMP('2024-04-04 19:42:59.841','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',TO_TIMESTAMP('2024-04-04 19:42:59.841','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N')
;

-- Run mode: SWING_CLIENT

-- Field: Eingangsrechnung_OLD(183,D) -> Rechnungsposition(291,D) -> Produktname
-- Column: C_InvoiceLine.ProductName
-- 2024-04-05T11:53:03.477Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588113,727338,0,291,TO_TIMESTAMP('2024-04-05 14:53:03.218','YYYY-MM-DD HH24:MI:SS.US'),100,'Name des Produktes',255,'D','Y','N','N','N','N','N','N','N','Produktname',TO_TIMESTAMP('2024-04-05 14:53:03.218','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-05T11:53:03.488Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727338 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-05T11:53:03.531Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2659)
;

-- 2024-04-05T11:53:03.561Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727338
;

-- 2024-04-05T11:53:03.573Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727338)
;

-- Field: Eingangsrechnung_OLD(183,D) -> Rechnungsposition(291,D) -> Abrechnungsgruppe
-- Column: C_InvoiceLine.InvoicingGroup
-- 2024-04-05T11:53:12.332Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588114,727339,0,291,TO_TIMESTAMP('2024-04-05 14:53:12.184','YYYY-MM-DD HH24:MI:SS.US'),100,255,'D','Y','N','N','N','N','N','N','N','Abrechnungsgruppe',TO_TIMESTAMP('2024-04-05 14:53:12.184','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-05T11:53:12.335Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727339 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-05T11:53:12.338Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582427)
;

-- 2024-04-05T11:53:12.347Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727339
;

-- 2024-04-05T11:53:12.353Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727339)
;

-- UI Element: Eingangsrechnung_OLD(183,D) -> Rechnungsposition(291,D) -> main -> 10 -> default.Produktname
-- Column: C_InvoiceLine.ProductName
-- 2024-04-05T11:53:45.152Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,727338,0,291,624043,540219,'F',TO_TIMESTAMP('2024-04-05 14:53:44.934','YYYY-MM-DD HH24:MI:SS.US'),100,'Name des Produktes','Y','N','N','Y','N','N','N',0,'Produktname',150,0,0,TO_TIMESTAMP('2024-04-05 14:53:44.934','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Eingangsrechnung_OLD(183,D) -> Rechnungsposition(291,D) -> main -> 10 -> default.Abrechnungsgruppe
-- Column: C_InvoiceLine.InvoicingGroup
-- 2024-04-05T11:53:51.049Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,727339,0,291,624044,540219,'F',TO_TIMESTAMP('2024-04-05 14:53:50.924','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Abrechnungsgruppe',160,0,0,TO_TIMESTAMP('2024-04-05 14:53:50.924','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Run mode: SWING_CLIENT

-- 2024-04-05T13:54:10.009Z
INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,Classname,Created,CreatedBy,EntityType,InternalName,IsActive,Updated,UpdatedBy) VALUES (0,0,540107,'de.metas.contracts.finalinvoice.workpackage.FinalInvoiceWorkPackageProcessor',TO_TIMESTAMP('2024-04-05 16:54:09.866','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','FinalInvoiceWorkPackageProcessor','Y',TO_TIMESTAMP('2024-04-05 16:54:09.866','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Run mode: SWING_CLIENT

-- 2024-04-05T13:59:05.747Z
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy) VALUES (0,0,540077,TO_TIMESTAMP('2024-04-05 16:59:05.586','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',1000,'FinalInvoiceWorkPackageProcessor',1,TO_TIMESTAMP('2024-04-05 16:59:05.586','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-05T13:59:28.982Z
INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy) VALUES (0,0,540107,540120,540077,TO_TIMESTAMP('2024-04-05 16:59:28.852','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',TO_TIMESTAMP('2024-04-05 16:59:28.852','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Run mode: SWING_CLIENT

-- Reference: C_DocType DocBaseType
-- Value: FPI
-- ValueName: FinalPurchaseInvoice
-- 2024-04-09T10:18:18.194Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543655,183,TO_TIMESTAMP('2024-04-09 13:18:17.978','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Final Purchase Invoice',TO_TIMESTAMP('2024-04-09 13:18:17.978','YYYY-MM-DD HH24:MI:SS.US'),100,'FPI','FinalPurchaseInvoice')
;

-- 2024-04-09T10:18:18.211Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543655 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: C_DocType DocBaseType
-- Value: FCM
-- ValueName: FinalCreditMemo
-- 2024-04-09T10:18:58.311Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543656,183,TO_TIMESTAMP('2024-04-09 13:18:58.153','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Final Credit Memo',TO_TIMESTAMP('2024-04-09 13:18:58.153','YYYY-MM-DD HH24:MI:SS.US'),100,'FCM','FinalCreditMemo')
;

-- 2024-04-09T10:18:58.315Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543656 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Run mode: WEBUI

-- 2024-04-09T10:37:02.812Z
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsExcludeFromCommision,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,1000000,541119,TO_TIMESTAMP('2024-04-09 13:37:02.799','YYYY-MM-DD HH24:MI:SS.US'),100,'FPI',1,'de.metas.swat',1000006,'N','N','Y','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Final Invoice','Final Invoice',TO_TIMESTAMP('2024-04-09 13:37:02.799','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-09T10:37:02.849Z
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_DocType_ID=541119 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2024-04-09T10:37:02.856Z
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541119 AND rol.IsManual='N')
;

-- 2024-04-09T10:37:06.796Z
UPDATE C_DocType SET DocumentCopies=0,Updated=TO_TIMESTAMP('2024-04-09 13:37:06.796','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541119
;

-- 2024-04-09T10:37:11.435Z
UPDATE C_DocType SET DocNoSequence_ID=555719,Updated=TO_TIMESTAMP('2024-04-09 13:37:11.435','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541119
;

-- 2024-04-09T10:37:28.116Z
UPDATE C_DocType SET AD_PrintFormat_ID=1000012,Updated=TO_TIMESTAMP('2024-04-09 13:37:28.116','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541119
;

-- Run mode: WEBUI

-- 2024-04-09T10:39:12.005Z
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsExcludeFromCommision,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,1000000,541120,TO_TIMESTAMP('2024-04-09 13:39:12.001','YYYY-MM-DD HH24:MI:SS.US'),100,'FCM',1,'de.metas.swat',1000006,'N','N','Y','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Final Credit Memo','Final Credit Memo',TO_TIMESTAMP('2024-04-09 13:39:12.001','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-09T10:39:12.011Z
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_DocType_ID=541120 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2024-04-09T10:39:12.013Z
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541120 AND rol.IsManual='N')
;

-- 2024-04-09T10:39:14.735Z
UPDATE C_DocType SET DocumentCopies=0,Updated=TO_TIMESTAMP('2024-04-09 13:39:14.734','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541120
;

-- 2024-04-09T10:39:18.952Z
UPDATE C_DocType SET AD_PrintFormat_ID=1000015,Updated=TO_TIMESTAMP('2024-04-09 13:39:18.952','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541120
;

-- 2024-04-09T10:39:55.446Z
UPDATE C_DocType SET DocNoSequence_ID=555719,Updated=TO_TIMESTAMP('2024-04-09 13:39:55.446','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541120
;

-- Run mode: WEBUI

-- 2024-04-09T18:15:58.338Z
INSERT INTO C_AggregationItem (AD_Client_ID,AD_Column_ID,AD_Org_ID,C_Aggregation_ID,C_AggregationItem_ID,Created,CreatedBy,EntityType,IsActive,Type,Updated,UpdatedBy) VALUES (1000000,579362,1000000,1000000,540097,TO_TIMESTAMP('2024-04-09 21:15:58.29','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.invoicecandidate','Y','COL',TO_TIMESTAMP('2024-04-09 21:15:58.29','YYYY-MM-DD HH24:MI:SS.US'),100)
;

