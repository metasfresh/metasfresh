-- Table: C_BankStatement_Import_File
-- 2022-10-25T15:11:14.621Z
UPDATE AD_Table SET TechnicalNote='Currently this is about importing camt53 files.
In future we might import other files from here.
The reason to have this dedicated table is that one import file might amount to multiple bank statements',Updated=TO_TIMESTAMP('2022-10-25 17:11:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542246
;

-- Column: C_BankStatement_Import_File.FileName
-- 2022-10-25T15:13:26.785Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=10,Updated=TO_TIMESTAMP('2022-10-25 17:13:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584725
;

-- Column: C_BankStatement_Import_File.Imported
-- 2022-10-25T15:13:42.084Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=20,Updated=TO_TIMESTAMP('2022-10-25 17:13:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584726
;

-- Column: C_BankStatement_Import_File.FileName
-- 2022-10-25T15:13:52.313Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-10-25 17:13:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584725
;

-- Column: C_BankStatement_Import_File.Imported
-- 2022-10-25T15:14:05.288Z
UPDATE AD_Column SET FilterOperator='B', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-10-25 17:14:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584726
;

-- Column: C_BankStatement_Import_File.C_BankStatement_Import_File_ID
-- 2022-10-25T15:14:18.163Z
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N', SeqNo=30,Updated=TO_TIMESTAMP('2022-10-25 17:14:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584724
;

-- Column: C_BankStatement.C_BankStatement_Import_File_ID
-- 2022-10-25T15:14:55.309Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584812,581559,0,30,392,'C_BankStatement_Import_File_ID',TO_TIMESTAMP('2022-10-25 17:14:55','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Kontoauszug Import-Datei',0,0,TO_TIMESTAMP('2022-10-25 17:14:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-25T15:14:55.312Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584812 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-25T15:14:55.347Z
/* DDL */  select update_Column_Translation_From_AD_Element(581559) 
;

-- Field: Bankauszug(194,D) -> Bankauszug(328,D) -> Kontoauszug Import-Datei
-- Column: C_BankStatement.C_BankStatement_Import_File_ID
-- 2022-10-25T15:15:40.176Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584812,707862,0,328,0,TO_TIMESTAMP('2022-10-25 17:15:40','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Kontoauszug Import-Datei',0,210,0,1,1,TO_TIMESTAMP('2022-10-25 17:15:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-25T15:15:40.180Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707862 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-25T15:15:40.183Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581559) 
;

-- 2022-10-25T15:15:40.204Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707862
;

-- 2022-10-25T15:15:40.206Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707862)
;

-- Field: Bankauszug(194,D) -> Bankauszug(328,D) -> Kontoauszug Import-Datei
-- Column: C_BankStatement.C_BankStatement_Import_File_ID
-- 2022-10-25T15:16:04.678Z
UPDATE AD_Field SET DisplayLogic='@C_BankStatement_Import_File_ID/0@>0',Updated=TO_TIMESTAMP('2022-10-25 17:16:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707862
;

-- UI Element: Bankauszug(194,D) -> Bankauszug(328,D) -> main -> 10 -> default.Kontoauszug Import-Datei
-- Column: C_BankStatement.C_BankStatement_Import_File_ID
-- 2022-10-25T15:16:44.237Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707862,0,328,540279,613304,'F',TO_TIMESTAMP('2022-10-25 17:16:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Kontoauszug Import-Datei',40,0,0,TO_TIMESTAMP('2022-10-25 17:16:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Bankauszug(194,D) -> Bankauszug(328,D) -> Kontoauszug Import-Datei
-- Column: C_BankStatement.C_BankStatement_Import_File_ID
-- 2022-10-25T15:16:54.618Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-10-25 17:16:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707862
;

-- Column: C_BankStatement.C_BankStatement_Import_File_ID
-- 2022-10-25T15:17:25.319Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-10-25 17:17:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584812
;

-- Column: C_BankStatement.C_BankStatement_Import_File_ID
-- 2022-10-25T15:24:35.792Z
UPDATE AD_Column SET EntityType='de.metas.banking',Updated=TO_TIMESTAMP('2022-10-25 17:24:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584812
;

-- Table: C_BankStatement_Import_File
-- 2022-10-25T15:25:48.899Z
UPDATE AD_Table SET EntityType='de.metas.banking',Updated=TO_TIMESTAMP('2022-10-25 17:25:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542246
;

-- 2022-10-25T18:40:09.035Z
/* DDL */ SELECT public.db_alter_table('C_BankStatement','ALTER TABLE public.C_BankStatement ADD COLUMN C_BankStatement_Import_File_ID NUMERIC(10)')
;

-- 2022-10-25T18:40:09.301Z
ALTER TABLE C_BankStatement ADD CONSTRAINT CBankStatementImportFile_CBankStatement FOREIGN KEY (C_BankStatement_Import_File_ID) REFERENCES public.C_BankStatement_Import_File DEFERRABLE INITIALLY DEFERRED
;

