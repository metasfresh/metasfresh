
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544216,0,'c_project_role',TO_TIMESTAMP('2018-08-27 15:23:18','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Projektrolle','Projektrolle',TO_TIMESTAMP('2018-08-27 15:23:18','YYYY-MM-DD HH24:MI:SS'),100)
;
-- 2018-08-27T15:23:18.476
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544216 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-08-27T15:23:40.244
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='C_Project_Role_ID',Updated=TO_TIMESTAMP('2018-08-27 15:23:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544216
;

-- 2018-08-27T15:23:40.246
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_Project_Role_ID', Name='Projektrolle', Description=NULL, Help=NULL WHERE AD_Element_ID=544216
;

-- 2018-08-27T15:23:40.248
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Project_Role_ID', Name='Projektrolle', Description=NULL, Help=NULL, AD_Element_ID=544216 WHERE UPPER(ColumnName)='C_PROJECT_ROLE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-08-27T15:23:40.252
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Project_Role_ID', Name='Projektrolle', Description=NULL, Help=NULL WHERE AD_Element_ID=544216 AND IsCentrallyMaintained='Y'
;

-- 2018-08-27T15:23:44.725
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='C_Project_Role',Updated=TO_TIMESTAMP('2018-08-27 15:23:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=544216
;

-- 2018-08-27T15:23:44.728
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_Project_Role', Name='Projektrolle', Description=NULL, Help=NULL WHERE AD_Element_ID=544216
;

-- 2018-08-27T15:23:44.729
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Project_Role', Name='Projektrolle', Description=NULL, Help=NULL, AD_Element_ID=544216 WHERE UPPER(ColumnName)='C_PROJECT_ROLE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-08-27T15:23:44.732
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_Project_Role', Name='Projektrolle', Description=NULL, Help=NULL WHERE AD_Element_ID=544216 AND IsCentrallyMaintained='Y'
;

-- 2018-08-27T15:24:06.894
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540905,TO_TIMESTAMP('2018-08-27 15:24:06','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','c_project_role',TO_TIMESTAMP('2018-08-27 15:24:06','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2018-08-27T15:24:49.153
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,560808,544216,0,17,540905,540961,'C_Project_Role',TO_TIMESTAMP('2018-08-27 15:24:49','YYYY-MM-DD HH24:MI:SS'),100,'N','U',2,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Projektrolle',0,0,TO_TIMESTAMP('2018-08-27 15:24:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-08-27T15:24:49.154
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560808 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-08-27T15:25:05.248
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_Project_User','ALTER TABLE public.C_Project_User ADD COLUMN C_Project_Role VARCHAR(2)')
;



-- adding new field to tab project contact
-- 2019-07-29T11:57:07.922Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560808,582448,0,541832,TO_TIMESTAMP('2019-07-29 13:57:07','YYYY-MM-DD HH24:MI:SS'),100,2,'U','Y','N','N','N','N','N','N','N','Projektrolle',TO_TIMESTAMP('2019-07-29 13:57:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T11:57:07.925Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582448 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T11:57:07.998Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544216)
;

-- 2019-07-29T11:57:08.007Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582448
;

-- 2019-07-29T11:57:08.009Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582448)
;

-- 2019-07-29T11:58:40.033Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2019-07-29 13:58:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582448
;

-- 2019-07-29T11:59:03.839Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582448,0,541832,542698,560299,'F',TO_TIMESTAMP('2019-07-29 13:59:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Projektrolle',30,0,0,TO_TIMESTAMP('2019-07-29 13:59:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T11:59:09.735Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-07-29 13:59:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560299
;

-- 2019-07-29T11:59:38.767Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540905,541994,TO_TIMESTAMP('2019-07-29 13:59:38','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Project Manager',TO_TIMESTAMP('2019-07-29 13:59:38','YYYY-MM-DD HH24:MI:SS'),100,'PM','Project Manager')
;

-- 2019-07-29T11:59:38.768Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=541994 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2019-07-29T11:59:49.901Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540905,541995,TO_TIMESTAMP('2019-07-29 13:59:49','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Project Team Member',TO_TIMESTAMP('2019-07-29 13:59:49','YYYY-MM-DD HH24:MI:SS'),100,'PT','Project Team Member')
;

-- 2019-07-29T11:59:49.903Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Ref_List_ID=541995 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;
