-- Column: C_POS_Payment.POSPaymentProcessor
-- Column: C_POS_Payment.POSPaymentProcessor
-- 2024-10-09T14:52:17.967Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589286,583305,0,17,541896,542437,'XX','POSPaymentProcessor',TO_TIMESTAMP('2024-10-09 17:52:17','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',0,40,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Payment Processor',0,0,TO_TIMESTAMP('2024-10-09 17:52:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-09T14:52:17.972Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589286 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-09T14:52:18Z
/* DDL */  select update_Column_Translation_From_AD_Element(583305) 
;

-- 2024-10-09T14:52:22.691Z
/* DDL */ SELECT public.db_alter_table('C_POS_Payment','ALTER TABLE public.C_POS_Payment ADD COLUMN POSPaymentProcessor VARCHAR(40)')
;

-- Column: C_POS_Payment.SUMUP_Config_ID
-- Column: C_POS_Payment.SUMUP_Config_ID
-- 2024-10-09T14:52:52.298Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589287,583297,0,30,542437,'XX','SUMUP_Config_ID',TO_TIMESTAMP('2024-10-09 17:52:51','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sumup',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'SumUp Configuration',0,0,TO_TIMESTAMP('2024-10-09 17:52:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-09T14:52:52.300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589287 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-09T14:52:52.303Z
/* DDL */  select update_Column_Translation_From_AD_Element(583297) 
;

-- 2024-10-09T14:52:53.041Z
/* DDL */ SELECT public.db_alter_table('C_POS_Payment','ALTER TABLE public.C_POS_Payment ADD COLUMN SUMUP_Config_ID NUMERIC(10)')
;

-- 2024-10-09T14:52:53.047Z
ALTER TABLE C_POS_Payment ADD CONSTRAINT SUMUPConfig_CPOSPayment FOREIGN KEY (SUMUP_Config_ID) REFERENCES public.SUMUP_Config DEFERRABLE INITIALLY DEFERRED
;

-- 2024-10-09T14:54:05.735Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583316,0,'POSPaymentProcessing_TrxId',TO_TIMESTAMP('2024-10-09 17:54:05','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.pos','Y','Payment Processing Trx ID','Payment Processing Trx ID',TO_TIMESTAMP('2024-10-09 17:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-09T14:54:05.740Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583316 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_POS_Payment.POSPaymentProcessing_TrxId
-- Column: C_POS_Payment.POSPaymentProcessing_TrxId
-- 2024-10-09T14:54:18.653Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589288,583316,0,10,542437,'XX','POSPaymentProcessing_TrxId',TO_TIMESTAMP('2024-10-09 17:54:18','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.pos',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Payment Processing Trx ID',0,0,TO_TIMESTAMP('2024-10-09 17:54:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-09T14:54:18.655Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589288 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-09T14:54:18.660Z
/* DDL */  select update_Column_Translation_From_AD_Element(583316) 
;

-- 2024-10-09T14:54:19.243Z
/* DDL */ SELECT public.db_alter_table('C_POS_Payment','ALTER TABLE public.C_POS_Payment ADD COLUMN POSPaymentProcessing_TrxId VARCHAR(255)')
;

-- Field: POS Order -> POS Payment -> Payment Processor
-- Column: C_POS_Payment.POSPaymentProcessor
-- Field: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> Payment Processor
-- Column: C_POS_Payment.POSPaymentProcessor
-- 2024-10-09T14:55:32.211Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589286,731890,0,547593,TO_TIMESTAMP('2024-10-09 17:55:32','YYYY-MM-DD HH24:MI:SS'),100,40,'de.metas.pos','Y','N','N','N','N','N','N','N','Payment Processor',TO_TIMESTAMP('2024-10-09 17:55:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-09T14:55:32.216Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731890 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-09T14:55:32.220Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583305) 
;

-- 2024-10-09T14:55:32.234Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731890
;

-- 2024-10-09T14:55:32.240Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731890)
;

-- Field: POS Order -> POS Payment -> SumUp Configuration
-- Column: C_POS_Payment.SUMUP_Config_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> SumUp Configuration
-- Column: C_POS_Payment.SUMUP_Config_ID
-- 2024-10-09T14:55:32.366Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589287,731891,0,547593,TO_TIMESTAMP('2024-10-09 17:55:32','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.pos','Y','N','N','N','N','N','N','N','SumUp Configuration',TO_TIMESTAMP('2024-10-09 17:55:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-09T14:55:32.368Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731891 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-09T14:55:32.369Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583297) 
;

-- 2024-10-09T14:55:32.375Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731891
;

-- 2024-10-09T14:55:32.376Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731891)
;

-- Field: POS Order -> POS Payment -> Payment Processing Trx ID
-- Column: C_POS_Payment.POSPaymentProcessing_TrxId
-- Field: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> Payment Processing Trx ID
-- Column: C_POS_Payment.POSPaymentProcessing_TrxId
-- 2024-10-09T14:55:32.489Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589288,731892,0,547593,TO_TIMESTAMP('2024-10-09 17:55:32','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.pos','Y','N','N','N','N','N','N','N','Payment Processing Trx ID',TO_TIMESTAMP('2024-10-09 17:55:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-09T14:55:32.491Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731892 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-09T14:55:32.493Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583316) 
;

-- 2024-10-09T14:55:32.496Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731892
;

-- 2024-10-09T14:55:32.498Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731892)
;

-- UI Element: POS Order -> POS Payment.Payment Processor
-- Column: C_POS_Payment.POSPaymentProcessor
-- UI Element: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> main -> 20 -> status.Payment Processor
-- Column: C_POS_Payment.POSPaymentProcessor
-- 2024-10-09T14:56:30.238Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731890,0,547593,551964,626172,'F',TO_TIMESTAMP('2024-10-09 17:56:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Payment Processor',20,0,0,TO_TIMESTAMP('2024-10-09 17:56:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Payment.SumUp Configuration
-- Column: C_POS_Payment.SUMUP_Config_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> main -> 20 -> status.SumUp Configuration
-- Column: C_POS_Payment.SUMUP_Config_ID
-- 2024-10-09T14:56:46.982Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731891,0,547593,551964,626173,'F',TO_TIMESTAMP('2024-10-09 17:56:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','SumUp Configuration',30,0,0,TO_TIMESTAMP('2024-10-09 17:56:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Payment.Payment Processing Trx ID
-- Column: C_POS_Payment.POSPaymentProcessing_TrxId
-- UI Element: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> main -> 20 -> status.Payment Processing Trx ID
-- Column: C_POS_Payment.POSPaymentProcessing_TrxId
-- 2024-10-09T14:56:55.465Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731892,0,547593,551964,626174,'F',TO_TIMESTAMP('2024-10-09 17:56:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Payment Processing Trx ID',40,0,0,TO_TIMESTAMP('2024-10-09 17:56:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Payment.Zahlung
-- Column: C_POS_Payment.C_Payment_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> main -> 20 -> status.Zahlung
-- Column: C_POS_Payment.C_Payment_ID
-- 2024-10-09T14:57:42.626Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2024-10-09 17:57:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625769
;

-- Field: POS Order -> POS Payment -> Payment Processing Status
-- Column: C_POS_Payment.POSPaymentProcessingStatus
-- Field: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> Payment Processing Status
-- Column: C_POS_Payment.POSPaymentProcessingStatus
-- 2024-10-09T14:58:24.700Z
UPDATE AD_Field SET DisplayLogic='@POSPaymentProcessingStatus/@=''''',Updated=TO_TIMESTAMP('2024-10-09 17:58:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=731843
;

-- Field: POS Order -> POS Payment -> Payment Processing Status
-- Column: C_POS_Payment.POSPaymentProcessingStatus
-- Field: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> Payment Processing Status
-- Column: C_POS_Payment.POSPaymentProcessingStatus
-- 2024-10-09T14:58:28.660Z
UPDATE AD_Field SET DisplayLogic='@POSPaymentProcessingStatus@=''''',Updated=TO_TIMESTAMP('2024-10-09 17:58:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=731843
;

-- Field: POS Order -> POS Payment -> Payment Processing Status
-- Column: C_POS_Payment.POSPaymentProcessingStatus
-- Field: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> Payment Processing Status
-- Column: C_POS_Payment.POSPaymentProcessingStatus
-- 2024-10-09T14:58:33.699Z
UPDATE AD_Field SET DisplayLogic='@POSPaymentProcessingStatus@!''''',Updated=TO_TIMESTAMP('2024-10-09 17:58:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=731843
;

-- Field: POS Order -> POS Payment -> Payment Processing Status
-- Column: C_POS_Payment.POSPaymentProcessingStatus
-- Field: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> Payment Processing Status
-- Column: C_POS_Payment.POSPaymentProcessingStatus
-- 2024-10-09T14:58:42.073Z
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2024-10-09 17:58:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=731843
;

-- Field: POS Order -> POS Payment -> Payment Processor
-- Column: C_POS_Payment.POSPaymentProcessor
-- Field: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> Payment Processor
-- Column: C_POS_Payment.POSPaymentProcessor
-- 2024-10-09T14:58:49.256Z
UPDATE AD_Field SET DisplayLogic='@POSPaymentProcessor@!''''',Updated=TO_TIMESTAMP('2024-10-09 17:58:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=731890
;

-- Field: POS Order -> POS Payment -> SumUp Configuration
-- Column: C_POS_Payment.SUMUP_Config_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> SumUp Configuration
-- Column: C_POS_Payment.SUMUP_Config_ID
-- 2024-10-09T15:00:06.062Z
UPDATE AD_Field SET DisplayLogic='@SUMUP_Config_ID@>0',Updated=TO_TIMESTAMP('2024-10-09 18:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=731891
;

-- Field: POS Order -> POS Payment -> Payment Processing Trx ID
-- Column: C_POS_Payment.POSPaymentProcessing_TrxId
-- Field: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> Payment Processing Trx ID
-- Column: C_POS_Payment.POSPaymentProcessing_TrxId
-- 2024-10-09T15:47:05.330Z
UPDATE AD_Field SET DisplayLogic='@POSPaymentProcessing_TrxId@!''''',Updated=TO_TIMESTAMP('2024-10-09 18:47:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=731892
;

