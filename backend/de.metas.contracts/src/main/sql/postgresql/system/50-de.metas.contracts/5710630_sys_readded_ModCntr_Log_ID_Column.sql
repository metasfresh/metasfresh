-- Run mode: SWING_CLIENT

-- Column: ModCntr_Log.ModCntr_Module_ID
-- 2023-11-15T12:46:50.876Z
UPDATE AD_Column SET IsIdentifier='Y',Updated=TO_TIMESTAMP('2023-11-15 14:46:50.876','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587468
;

-- Column: ModCntr_Log.ModCntr_Module_ID
-- 2023-11-15T12:47:29.432Z
UPDATE AD_Column SET AD_Column_ID=587468, AD_Element_ID=582426, AD_Reference_ID=30, AD_Reference_Value_ID=541830, ColumnName='ModCntr_Module_ID', Created=TO_TIMESTAMP('2023-09-12 21:31:53.613','YYYY-MM-DD HH24:MI:SS.US'), FieldLength=10, IsIdentifier='Y', IsMandatory='N', Name='Bausteine', SeqNo=1, Updated=TO_TIMESTAMP('2023-11-15 14:46:50.876','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587468
;

-- Column: ModCntr_Log.ModCntr_Log_DocumentType
-- 2023-11-15T12:47:29.714Z
UPDATE AD_Column SET SeqNo=2,Updated=TO_TIMESTAMP('2023-11-15 14:47:29.714','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586851
;

-- Column: ModCntr_Log.ModCntr_Log_DocumentType
-- 2023-11-15T12:47:37.090Z
UPDATE AD_Column SET IsIdentifier='Y',Updated=TO_TIMESTAMP('2023-11-15 14:47:37.09','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586851
;

-- Column: I_ModCntr_Log.ModCntr_Log_ID
-- 2023-11-15T12:47:47.461Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587647,582413,0,19,541775,542372,'ModCntr_Log_ID',TO_TIMESTAMP('2023-11-15 14:47:47.279','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Contract Module Log',0,0,TO_TIMESTAMP('2023-11-15 14:47:47.279','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-11-15T12:47:47.467Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587647 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-11-15T12:47:47.506Z
/* DDL */  select update_Column_Translation_From_AD_Element(582413)
;

-- 2023-11-15T12:47:53.598Z
/* DDL */ SELECT public.db_alter_table('I_ModCntr_Log','ALTER TABLE public.I_ModCntr_Log ADD COLUMN ModCntr_Log_ID NUMERIC(10)')
;

-- 2023-11-15T12:47:53.605Z
ALTER TABLE I_ModCntr_Log ADD CONSTRAINT ModCntrLog_IModCntrLog FOREIGN KEY (ModCntr_Log_ID) REFERENCES public.ModCntr_Log DEFERRABLE INITIALLY DEFERRED
;

-- Element: null
-- 2023-11-15T12:49:39.926Z
UPDATE AD_Element_Trl SET Name='Import Modular Contract Log', PrintName='Import Modular Contract Log',Updated=TO_TIMESTAMP('2023-11-15 14:49:39.926','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582737 AND AD_Language='en_US'
;

-- 2023-11-15T12:49:39.929Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582737,'en_US')
;

-- Element: null
-- 2023-11-15T12:49:44.873Z
UPDATE AD_Element_Trl SET Name='Import Vertragsbaustein Log', PrintName='Import Vertragsbaustein Log',Updated=TO_TIMESTAMP('2023-11-15 14:49:44.873','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582737 AND AD_Language='fr_CH'
;

-- 2023-11-15T12:49:44.875Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582737,'fr_CH')
;

-- Field: Import Vertragsbaustein Log(541737,de.metas.contracts) -> Import Contract Module Logs(547233,de.metas.contracts) -> Contract Module Log
-- Column: I_ModCntr_Log.ModCntr_Log_ID
-- 2023-11-15T12:50:15.993Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587647,721856,0,547233,0,TO_TIMESTAMP('2023-11-15 14:50:15.825','YYYY-MM-DD HH24:MI:SS.US'),100,0,'de.metas.contracts',0,'Y','Y','Y','N','N','N','N','N','N','Contract Module Log',0,210,0,1,1,TO_TIMESTAMP('2023-11-15 14:50:15.825','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-15T12:50:15.994Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721856 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-15T12:50:15.996Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582413)
;

-- 2023-11-15T12:50:16.008Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721856
;

-- 2023-11-15T12:50:16.010Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721856)
;

-- 2023-11-15T12:53:10.222Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,587647,540092,541986,0,TO_TIMESTAMP('2023-11-15 14:53:10.22','YYYY-MM-DD HH24:MI:SS.US'),100,'N','.','N','Y','ModularContractLogID',10,TO_TIMESTAMP('2023-11-15 14:53:10.22','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-15T12:53:15.161Z
UPDATE AD_ImpFormat_Row SET StartNo=1,Updated=TO_TIMESTAMP('2023-11-15 14:53:15.161','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541986
;

-- 2023-11-15T12:53:22.697Z
UPDATE AD_ImpFormat_Row SET EndNo=0,Updated=TO_TIMESTAMP('2023-11-15 14:53:22.697','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541986
;

-- Column: I_ModCntr_Log.AD_Table_ID
-- 2023-11-15T13:16:24.831Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587648,126,0,19,542372,'AD_Table_ID',TO_TIMESTAMP('2023-11-15 15:16:24.668','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Database Table information','de.metas.contracts',0,10,'The Database Table provides the information of the table definition','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'DB-Tabelle',0,0,TO_TIMESTAMP('2023-11-15 15:16:24.668','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-11-15T13:16:24.839Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587648 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-11-15T13:16:24.874Z
/* DDL */  select update_Column_Translation_From_AD_Element(126)
;

-- Column: I_ModCntr_Log.Record_ID
-- 2023-11-15T13:20:43.357Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587649,538,0,28,542372,'Record_ID',TO_TIMESTAMP('2023-11-15 15:20:43.201','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Direct internal record ID','de.metas.contracts',0,10,'The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Datensatz-ID',0,0,TO_TIMESTAMP('2023-11-15 15:20:43.201','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-11-15T13:20:43.358Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587649 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-11-15T13:20:43.361Z
/* DDL */  select update_Column_Translation_From_AD_Element(538)
;

-- 2023-11-15T13:20:55.445Z
/* DDL */ SELECT public.db_alter_table('I_ModCntr_Log','ALTER TABLE public.I_ModCntr_Log ADD COLUMN Record_ID NUMERIC(10)')
;

-- 2023-11-15T13:21:05.845Z
/* DDL */ SELECT public.db_alter_table('I_ModCntr_Log','ALTER TABLE public.I_ModCntr_Log ADD COLUMN AD_Table_ID NUMERIC(10)')
;

-- 2023-11-15T13:21:05.851Z
ALTER TABLE I_ModCntr_Log ADD CONSTRAINT ADTable_IModCntrLog FOREIGN KEY (AD_Table_ID) REFERENCES public.AD_Table DEFERRABLE INITIALLY DEFERRED
;


-- 2023-11-15T13:22:53.355Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,587648,540092,541987,0,TO_TIMESTAMP('2023-11-15 15:22:53.354','YYYY-MM-DD HH24:MI:SS.US'),100,'N','.','N','Y','TableId',20,TO_TIMESTAMP('2023-11-15 15:22:53.354','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-15T13:22:56.801Z
UPDATE AD_ImpFormat_Row SET StartNo=20,Updated=TO_TIMESTAMP('2023-11-15 15:22:56.801','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541987
;

-- 2023-11-15T13:22:58.973Z
UPDATE AD_ImpFormat_Row SET StartNo=2,Updated=TO_TIMESTAMP('2023-11-15 15:22:58.973','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541987
;

-- 2023-11-15T13:23:03.156Z
UPDATE AD_ImpFormat_Row SET EndNo=0,Updated=TO_TIMESTAMP('2023-11-15 15:23:03.156','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541987
;

-- 2023-11-15T13:23:20.208Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,587649,540092,541988,0,TO_TIMESTAMP('2023-11-15 15:23:20.207','YYYY-MM-DD HH24:MI:SS.US'),100,'N','.','N','Y','Record_ID',30,TO_TIMESTAMP('2023-11-15 15:23:20.207','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-15T13:23:24.212Z
UPDATE AD_ImpFormat_Row SET StartNo=3,Updated=TO_TIMESTAMP('2023-11-15 15:23:24.212','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541988
;

-- 2023-11-15T13:23:29.424Z
UPDATE AD_ImpFormat_Row SET EndNo=0,Updated=TO_TIMESTAMP('2023-11-15 15:23:29.424','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541988
;
