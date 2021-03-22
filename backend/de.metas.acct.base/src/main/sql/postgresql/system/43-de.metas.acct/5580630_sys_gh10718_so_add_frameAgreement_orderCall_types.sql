-- 2021-02-25T08:22:03.264Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,148,542288,TO_TIMESTAMP('2021-02-25 10:22:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Rahmenvertrag',TO_TIMESTAMP('2021-02-25 10:22:02','YYYY-MM-DD HH24:MI:SS'),100,'FA','Frame Agrement')
;

-- 2021-02-25T08:22:03.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542288 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-02-25T08:22:27.479Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,148,542289,TO_TIMESTAMP('2021-02-25 10:22:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Abrufbestätigung',TO_TIMESTAMP('2021-02-25 10:22:27','YYYY-MM-DD HH24:MI:SS'),100,'OC','Order Call')
;

-- 2021-02-25T08:22:27.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542289 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-02-25T08:22:43.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Order Call',Updated=TO_TIMESTAMP('2021-02-25 10:22:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542289
;

-- 2021-02-25T08:23:04.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Frame Agreement',Updated=TO_TIMESTAMP('2021-02-25 10:23:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542288
;

INSERT INTO public.ad_sequence (ad_sequence_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, description, isautosequence, incrementno, startno, currentnext, currentnextsys, isaudited, istableid, prefix, startnewyear) VALUES (555253, 1000000, 0, 'Y', '2021-02-25 08:30:34.000000', 100, '2021-02-25 08:30:34.000000', 100, 'Frame Agreement', 'SO Frame Agreement seq', 'Y', 1, 1000000, 10001, 100, 'N', 'N', 'F', 'N')
;

-- 2021-02-25T08:47:35.355Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,AD_PrintFormat_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocNoSequence_ID,DocSubType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,0,1000010,541010,TO_TIMESTAMP('2021-02-25 10:47:35','YYYY-MM-DD HH24:MI:SS'),100,'SOO',555253,'FA',0,'D',1000001,'N','N','N','Y','N','N','N','Y','Y','N','N','N','N','N','Y','N','Rahmenvertrag','Rahmenvertrag',TO_TIMESTAMP('2021-02-25 10:47:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-25T08:47:35.358Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_DocType_ID=541010 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2021-02-25T08:47:35.360Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541010 AND rol.IsManual='N')
;

-- 2021-02-25T08:47:48.194Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl SET IsTranslated='Y', Name='Frame Agreement', PrintName='Frame Agreement',Updated=TO_TIMESTAMP('2021-02-25 10:47:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541010
;

-- 2021-02-25T08:49:01.626Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,AD_PrintFormat_ID,C_DocType_ID,C_DocTypeInvoice_ID,C_DocTypeShipment_ID,Created,CreatedBy,DocBaseType,DocNoSequence_ID,DocSubType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,0,1000010,541011,1000002,1000011,TO_TIMESTAMP('2021-02-25 10:49:01','YYYY-MM-DD HH24:MI:SS'),100,'SOO',545479,'OC',0,'D',1000001,'N','N','N','Y','N','N','N','Y','Y','N','N','N','N','N','Y','N','Abrufbestätigung','Abrufbestätigung',TO_TIMESTAMP('2021-02-25 10:49:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-25T08:49:01.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_DocType_ID=541011 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2021-02-25T08:49:01.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541011 AND rol.IsManual='N')
;

-- 2021-02-25T08:49:14.969Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_DocType_Trl SET Name='Order Call', PrintName='Order Call',Updated=TO_TIMESTAMP('2021-02-25 10:49:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541011
;

-- 2021-02-25T09:14:15.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578783,0,'C_FrameAgreement_Order_ID',TO_TIMESTAMP('2021-02-25 11:14:14','YYYY-MM-DD HH24:MI:SS'),100,'Reference to corresponding FrameAgreement Order','D','Reference of the Sales Order to the corresponding FrameAgreement','Y','Frame Agreement Order','Frame Agreement Order',TO_TIMESTAMP('2021-02-25 11:14:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-25T09:14:15.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578783 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-02-25T09:24:30.063Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MandatoryLogic,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573002,578783,0,30,290,259,147,'C_FrameAgreement_Order_ID',TO_TIMESTAMP('2021-02-25 11:24:29','YYYY-MM-DD HH24:MI:SS'),100,'N','Reference to corresponding FrameAgreement Order','D',0,10,'Reference of the Sales Order to the corresponding FrameAgreement','Y','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','@C_DocType_ID/1@=541010',0,'Frame Agreement Order',0,0,TO_TIMESTAMP('2021-02-25 11:24:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-25T09:24:30.065Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573002 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-25T09:24:30.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578783) 
;

-- 2021-02-25T09:26:54.850Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573003,942,0,29,260,'Order_Min',TO_TIMESTAMP('2021-02-25 11:26:54','YYYY-MM-DD HH24:MI:SS'),100,'N','Mindestbestellmenge in Mengeneinheit','D',0,14,'"Mindestbestellmenge" gibt die kleinste Menge für dieses Produkt an, die bestellt werden kann.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Mindestbestellmenge',0,0,TO_TIMESTAMP('2021-02-25 11:26:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-25T09:26:54.852Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573003 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-25T09:26:54.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(942) 
;

-- 2021-02-25T09:33:10.979Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN C_FrameAgreement_Order_ID NUMERIC(10)')
;

-- 2021-02-25T09:33:12.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Order ADD CONSTRAINT CFrameAgreementOrder_COrder FOREIGN KEY (C_FrameAgreement_Order_ID) REFERENCES public.C_Order DEFERRABLE INITIALLY DEFERRED
;

-- 2021-02-25T09:33:43.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN Order_Min NUMERIC')
;

-- 2021-02-25T09:40:56.847Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541264,TO_TIMESTAMP('2021-02-25 11:40:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Order->C_FrameAgreement_Order_ID',TO_TIMESTAMP('2021-02-25 11:40:56','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-02-25T09:40:56.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541264 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-02-25T09:41:47.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,573002,0,541264,259,TO_TIMESTAMP('2021-02-25 11:41:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-02-25 11:41:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-25T09:50:34.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='C_Order_ID =  @C_FrameAgreement_Order_ID@',Updated=TO_TIMESTAMP('2021-02-25 11:50:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541264
;

-- 2021-02-25T09:54:57.250Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541265,TO_TIMESTAMP('2021-02-25 11:54:57','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_FrameAgreement_Order_ID->C_Order',TO_TIMESTAMP('2021-02-25 11:54:57','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-02-25T09:54:57.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541265 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-02-25T09:56:26.372Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,573002,0,541265,259,TO_TIMESTAMP('2021-02-25 11:56:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-02-25 11:56:26','YYYY-MM-DD HH24:MI:SS'),100,'C_Order_ID =  @C_FrameAgreement_Order_ID@')
;

-- 2021-02-25T09:56:57.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Key=2161, WhereClause='@C_Order_ID@ = C_FrameAgreement_Order_ID',Updated=TO_TIMESTAMP('2021-02-25 11:56:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541264
;

-- 2021-02-25T09:58:19.535Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Role_Source,Role_Target,Updated,UpdatedBy) VALUES (0,0,541264,541265,540278,TO_TIMESTAMP('2021-02-25 11:58:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','C_Order(SO) <-> C_FrameAgreement_Order_ID','Order','Order',TO_TIMESTAMP('2021-02-25 11:58:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-25T10:05:39.804Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Rahmenvertrag ',Updated=TO_TIMESTAMP('2021-02-25 12:05:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578783 AND AD_Language='de_DE'
;

-- 2021-02-25T10:05:39.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578783,'de_DE') 
;

-- 2021-02-25T10:05:39.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578783,'de_DE') 
;

-- 2021-02-25T10:05:39.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Rahmenvertrag', Name='Frame Agreement Order' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578783)
;

-- 2021-02-25T10:05:42.505Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Rahmenvertrag ',Updated=TO_TIMESTAMP('2021-02-25 12:05:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578783 AND AD_Language='de_CH'
;

-- 2021-02-25T10:05:42.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578783,'de_CH') 
;

-- 2021-02-25T10:05:43.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-02-25 12:05:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578783 AND AD_Language='en_US'
;

-- 2021-02-25T10:05:43.587Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578783,'en_US') 
;

-- 2021-02-25T10:05:47.763Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Rahmenvertrag',Updated=TO_TIMESTAMP('2021-02-25 12:05:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578783 AND AD_Language='nl_NL'
;

-- 2021-02-25T10:05:47.764Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578783,'nl_NL') 
;

-- 2021-02-25T10:05:50.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Rahmenvertrag',Updated=TO_TIMESTAMP('2021-02-25 12:05:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578783 AND AD_Language='de_DE'
;

-- 2021-02-25T10:05:50.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578783,'de_DE') 
;

-- 2021-02-25T10:05:50.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578783,'de_DE') 
;

-- 2021-02-25T10:05:50.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Rahmenvertrag', Name='Frame Agreement Order' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578783)
;

-- 2021-02-25T10:05:53.736Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Rahmenvertrag',Updated=TO_TIMESTAMP('2021-02-25 12:05:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578783 AND AD_Language='de_CH'
;

-- 2021-02-25T10:05:53.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578783,'de_CH') 
;

-- 2021-02-25T10:08:14.064Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Rahmenvertrag Referenz',Updated=TO_TIMESTAMP('2021-02-25 12:08:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578783 AND AD_Language='de_CH'
;

-- 2021-02-25T10:08:14.067Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578783,'de_CH') 
;

-- 2021-02-25T10:08:15.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Rahmenvertrag Referenz',Updated=TO_TIMESTAMP('2021-02-25 12:08:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578783 AND AD_Language='de_DE'
;

-- 2021-02-25T10:08:15.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578783,'de_DE') 
;

-- 2021-02-25T10:08:15.222Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578783,'de_DE') 
;

-- 2021-02-25T10:08:15.224Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_FrameAgreement_Order_ID', Name='Frame Agreement Order', Description='Rahmenvertrag Referenz', Help='Reference of the Sales Order to the corresponding FrameAgreement' WHERE AD_Element_ID=578783
;

-- 2021-02-25T10:08:15.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_FrameAgreement_Order_ID', Name='Frame Agreement Order', Description='Rahmenvertrag Referenz', Help='Reference of the Sales Order to the corresponding FrameAgreement', AD_Element_ID=578783 WHERE UPPER(ColumnName)='C_FRAMEAGREEMENT_ORDER_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-25T10:08:15.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_FrameAgreement_Order_ID', Name='Frame Agreement Order', Description='Rahmenvertrag Referenz', Help='Reference of the Sales Order to the corresponding FrameAgreement' WHERE AD_Element_ID=578783 AND IsCentrallyMaintained='Y'
;

-- 2021-02-25T10:08:15.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Frame Agreement Order', Description='Rahmenvertrag Referenz', Help='Reference of the Sales Order to the corresponding FrameAgreement' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578783) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578783)
;

-- 2021-02-25T10:08:15.242Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Frame Agreement Order', Description='Rahmenvertrag Referenz', Help='Reference of the Sales Order to the corresponding FrameAgreement', CommitWarning = NULL WHERE AD_Element_ID = 578783
;

-- 2021-02-25T10:08:17.191Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Rahmenvertrag Referenz',Updated=TO_TIMESTAMP('2021-02-25 12:08:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578783 AND AD_Language='nl_NL'
;

-- 2021-02-25T10:08:17.193Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578783,'nl_NL') 
;

-- 2021-02-25T10:08:28.566Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Rahmenvertrag Referenz',Updated=TO_TIMESTAMP('2021-02-25 12:08:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578783 AND AD_Language='de_CH'
;

-- 2021-02-25T10:08:28.567Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578783,'de_CH') 
;

-- 2021-02-25T10:08:29.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Rahmenvertrag Referenz',Updated=TO_TIMESTAMP('2021-02-25 12:08:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578783 AND AD_Language='de_DE'
;

-- 2021-02-25T10:08:29.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578783,'de_DE') 
;

-- 2021-02-25T10:08:29.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(578783,'de_DE') 
;

-- 2021-02-25T10:08:29.435Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_FrameAgreement_Order_ID', Name='Frame Agreement Order', Description='Rahmenvertrag Referenz', Help='Rahmenvertrag Referenz' WHERE AD_Element_ID=578783
;

-- 2021-02-25T10:08:29.437Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_FrameAgreement_Order_ID', Name='Frame Agreement Order', Description='Rahmenvertrag Referenz', Help='Rahmenvertrag Referenz', AD_Element_ID=578783 WHERE UPPER(ColumnName)='C_FRAMEAGREEMENT_ORDER_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-25T10:08:29.439Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_FrameAgreement_Order_ID', Name='Frame Agreement Order', Description='Rahmenvertrag Referenz', Help='Rahmenvertrag Referenz' WHERE AD_Element_ID=578783 AND IsCentrallyMaintained='Y'
;

-- 2021-02-25T10:08:29.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Frame Agreement Order', Description='Rahmenvertrag Referenz', Help='Rahmenvertrag Referenz' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578783) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578783)
;

-- 2021-02-25T10:08:29.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Frame Agreement Order', Description='Rahmenvertrag Referenz', Help='Rahmenvertrag Referenz', CommitWarning = NULL WHERE AD_Element_ID = 578783
;

-- 2021-02-25T10:08:30.908Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Help='Rahmenvertrag Referenz',Updated=TO_TIMESTAMP('2021-02-25 12:08:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578783 AND AD_Language='nl_NL'
;

-- 2021-02-25T10:08:30.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578783,'nl_NL') 
;

-- 2021-02-25T10:09:21.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,ColumnDisplayLength,IncludedTabHeight,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,Name,Description,AD_Org_ID,EntityType) VALUES (186,'Y',0,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-02-25 12:09:21','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,TO_TIMESTAMP('2021-02-25 12:09:21','YYYY-MM-DD HH24:MI:SS'),100,'Rahmenvertrag Referenz',632795,'Y',730,730,1,1,573002,'Frame Agreement Order','Rahmenvertrag Referenz',0,'D')
;

-- 2021-02-25T10:09:21.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=632795 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-02-25T10:09:21.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578783) 
;

-- 2021-02-25T10:09:21.836Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=632795
;

-- 2021-02-25T10:09:21.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(632795)
;

-- 2021-02-25T10:12:15.325Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,ColumnDisplayLength,IncludedTabHeight,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,Name,Description,AD_Org_ID,EntityType) VALUES (187,'Y',0,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2021-02-25 12:12:15','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,TO_TIMESTAMP('2021-02-25 12:12:15','YYYY-MM-DD HH24:MI:SS'),100,'"Mindestbestellmenge" gibt die kleinste Menge für dieses Produkt an, die bestellt werden kann.',632798,'Y',350,340,1,1,573003,'Mindestbestellmenge','Mindestbestellmenge in Mengeneinheit',0,'D')
;

-- 2021-02-25T10:12:15.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=632798 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-02-25T10:12:15.330Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(942) 
;

-- 2021-02-25T10:12:15.335Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=632798
;

-- 2021-02-25T10:12:15.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(632798)
;
-- 2021-02-25T11:06:42.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541266,TO_TIMESTAMP('2021-02-25 13:06:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Order_SO_Target_For_C_FrameAgreement',TO_TIMESTAMP('2021-02-25 13:06:41','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-02-25T11:06:42.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541266 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-02-25T11:07:36.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,2161,0,541266,259,TO_TIMESTAMP('2021-02-25 13:07:36','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','N',TO_TIMESTAMP('2021-02-25 13:07:36','YYYY-MM-DD HH24:MI:SS'),100,'exists ( select 1 from C_Order o where o.c_frameagreement_order_id=@C_Order_ID/-1@ and C_Order.C_Order_ID = o.C_Order_ID)')
;

-- 2021-02-25T11:09:37.475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=541266,Updated=TO_TIMESTAMP('2021-02-25 13:09:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573002
;

-- 2021-02-25T11:14:02.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from C_Order o where o.C_DocType_ID=541010 and C_Order.C_Order_ID = o.C_Order_ID and @C_BPartner_ID/-1@ = o.C_BPartner_ID)',Updated=TO_TIMESTAMP('2021-02-25 13:14:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541184
;

-- 2021-02-25T11:17:54.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from C_Order o where o.C_DocType_ID=541010 and C_Order.C_Order_ID = o.C_Order_ID and @C_BPartner_ID/-1@=o.C_BPartner_ID)',Updated=TO_TIMESTAMP('2021-02-25 13:17:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541184
;

-- 2021-02-25T11:21:40.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from C_Order o where o.C_DocType_ID=541010 and C_Order.C_Order_ID = o.C_Order_ID and @C_BPartner_ID/-1@=o.C_BPartner_ID)',Updated=TO_TIMESTAMP('2021-02-25 13:21:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541266
;

-- 2021-02-25T11:57:19.088Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET EntityType='D',Updated=TO_TIMESTAMP('2021-02-25 13:57:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541266
;

-- 2021-02-25T11:57:58.839Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='@C_Order_ID/-1@ = C_FrameAgreement_Order_ID',Updated=TO_TIMESTAMP('2021-02-25 13:57:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541264
;

-- 2021-02-25T11:58:08.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='C_Order_ID =  @C_FrameAgreement_Order_ID/-1@',Updated=TO_TIMESTAMP('2021-02-25 13:58:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541265
;

-- 2021-02-25T12:03:12.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from C_Order o where o.C_Order_ID =  @C_FrameAgreement_Order_ID/-1@ and C_Order.C_Order_ID = o.C_Order_ID)',Updated=TO_TIMESTAMP('2021-02-25 14:03:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541265
;

-- 2021-02-25T12:03:48.442Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from C_Order o where C_FrameAgreement_Order_ID =  @C_Order_ID/-1@ and C_Order.C_Order_ID = o.C_Order_ID)',Updated=TO_TIMESTAMP('2021-02-25 14:03:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541264
;

-- 2021-02-25T12:38:01.339Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-02-25 14:38:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573002
;

-- 2021-02-25T12:38:53.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2021-02-25 14:38:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573002
;

-- 2021-02-25T12:39:02.290Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@C_DocType_ID/-1@=541011',Updated=TO_TIMESTAMP('2021-02-25 14:39:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573002
;

-- 2021-02-25T12:39:28.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsCalculated='N',Updated=TO_TIMESTAMP('2021-02-25 14:39:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573002
;

-- 2021-02-25T12:57:25.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@C_DocType_ID@=541010',Updated=TO_TIMESTAMP('2021-02-25 14:57:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=632795
;

-- 2021-02-25T13:18:35.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from C_Order o where o.C_DocType_ID=541010 and C_Order.C_Order_ID = o.C_Order_ID and @C_BPartner_ID/-1@=o.C_BPartner_ID and o.processed=''Y'')',Updated=TO_TIMESTAMP('2021-02-25 15:18:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541266
;

-- 2021-02-25T14:15:29.350Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Role_Source='Order_Current', Role_Target='Order_hist',Updated=TO_TIMESTAMP('2021-02-25 16:15:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540278
;

-- 2021-02-25T14:19:43.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Role_Source='', Role_Target='',Updated=TO_TIMESTAMP('2021-02-25 16:19:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540278
;

-- 2021-02-25T14:24:03.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540666,Updated=TO_TIMESTAMP('2021-02-25 16:24:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540278
;

-- 2021-02-25T14:25:14.449Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Ref_Table WHERE AD_Reference_ID=541264
;

-- 2021-02-25T14:25:20.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=541264
;

-- 2021-02-25T14:25:20.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=541264
;

-- 2021-02-25T14:25:54.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='C_Order->C_FrameAgreement_Order_ID_Target',Updated=TO_TIMESTAMP('2021-02-25 16:25:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541265
;

-- 2021-02-25T14:27:15.849Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='exists ( select 1 from C_Order o where o.C_FrameAgreement_Order_ID =  @C_Order_ID/-1@ and C_Order.C_Order_ID = o.C_Order_ID)',Updated=TO_TIMESTAMP('2021-02-25 16:27:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541265
;

-- 2021-02-25T14:37:48.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2021-02-25 16:37:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573002
;

-- 2021-02-25T14:42:21.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@C_DocType_ID/0@=541010',Updated=TO_TIMESTAMP('2021-02-25 16:42:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573002
;

-- 2021-02-25T14:45:27.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@OrderType/XX@=OC',Updated=TO_TIMESTAMP('2021-02-25 16:45:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=632795
;

-- 2021-02-25T14:46:39.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@OrderType/XX@=OC',Updated=TO_TIMESTAMP('2021-02-25 16:46:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573002
;

-- 2021-02-25T14:46:48.873Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@C_DocType_ID/0@=541010',Updated=TO_TIMESTAMP('2021-02-25 16:46:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573002
;

-- 2021-02-25T15:07:13.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Name='C_Order(SO) -> C_Order(FrameAgreement)',Updated=TO_TIMESTAMP('2021-02-25 17:07:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540278
;

