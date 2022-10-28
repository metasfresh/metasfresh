
-- Element: IsAlwaysUpdateable
-- 2022-10-22T05:58:04.304Z
UPDATE AD_Element_Trl SET Description='The column''s field is always updateable, even if the record is not active or processed',Updated=TO_TIMESTAMP('2022-10-22 07:58:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2468 AND AD_Language='de_CH'
;

-- 2022-10-22T05:58:04.308Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2468,'de_CH') 
;

-- Element: IsAlwaysUpdateable
-- 2022-10-22T05:58:09.640Z
UPDATE AD_Element_Trl SET Description='The column''s field is always updateable, even if the record is not active or processed',Updated=TO_TIMESTAMP('2022-10-22 07:58:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2468 AND AD_Language='en_US'
;

-- 2022-10-22T05:58:09.643Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2468,'en_US') 
;

-- Element: IsAlwaysUpdateable
-- 2022-10-22T05:58:14.488Z
UPDATE AD_Element_Trl SET Description='The column''s field is always updateable, even if the record is not active or processed',Updated=TO_TIMESTAMP('2022-10-22 07:58:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2468 AND AD_Language='nl_NL'
;

-- 2022-10-22T05:58:14.491Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2468,'nl_NL') 
;

-- Element: IsAlwaysUpdateable
-- 2022-10-22T05:58:18.226Z
UPDATE AD_Element_Trl SET Description='The column''s field is always updateable, even if the record is not active or processed',Updated=TO_TIMESTAMP('2022-10-22 07:58:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2468 AND AD_Language='de_DE'
;

-- 2022-10-22T05:58:18.230Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(2468,'de_DE') 
;

-- 2022-10-22T05:58:18.232Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2468,'de_DE') 
;

-- Column: AD_Field.IsAlwaysUpdateable
-- 2022-10-22T05:58:51.012Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584778,2468,0,20,107,'IsAlwaysUpdateable',TO_TIMESTAMP('2022-10-22 07:58:50','YYYY-MM-DD HH24:MI:SS'),100,'N','N','The column''s field is always updateable, even if the record is not active or processed','D',0,1,'If selected and if the winow / tab is not read only, you can always update the column.  This might be useful for comments, etc.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Always Updateable',0,0,TO_TIMESTAMP('2022-10-22 07:58:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-10-22T05:58:51.016Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584778 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-22T05:58:51.022Z
/* DDL */  select update_Column_Translation_From_AD_Element(2468) 
;

-- Column: AD_Field.IsAlwaysUpdateable
-- 2022-10-22T05:59:56.958Z
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=319,Updated=TO_TIMESTAMP('2022-10-22 07:59:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584778
;

-- Column: AD_Field.IsAlwaysUpdateable
-- 2022-10-22T06:00:02.662Z
UPDATE AD_Column SET DefaultValue='',Updated=TO_TIMESTAMP('2022-10-22 08:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584778
;

-- Column: AD_Field.IsAlwaysUpdateable
-- 2022-10-22T06:00:25.947Z
UPDATE AD_Column SET PersonalDataCategory='NP',Updated=TO_TIMESTAMP('2022-10-22 08:00:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584778
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Always Updateable
-- Column: AD_Field.IsAlwaysUpdateable
-- 2022-10-22T06:02:22.357Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584778,707817,0,107,0,TO_TIMESTAMP('2022-10-22 08:02:22','YYYY-MM-DD HH24:MI:SS'),100,'The column''s field is always updateable, even if the record is not active or processed',0,'D','If selected and if the winow / tab is not read only, you can always update the column.  This might be useful for comments, etc.',0,'Y','Y','N','N','N','N','N','N','Always Updateable',0,0,1,1,TO_TIMESTAMP('2022-10-22 08:02:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-10-22T06:02:22.360Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707817 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-22T06:02:22.363Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2468) 
;

-- 2022-10-22T06:02:22.371Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707817
;

-- 2022-10-22T06:02:22.373Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707817)
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Always Updateable
-- Column: AD_Field.IsAlwaysUpdateable
-- 2022-10-22T06:02:38.982Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=360,Updated=TO_TIMESTAMP('2022-10-22 08:02:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707817
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Feld
-- Column: AD_Field.AD_Field_ID
-- 2022-10-22T06:02:38.993Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=370,Updated=TO_TIMESTAMP('2022-10-22 08:02:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=125
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Standardwert-Logik
-- Column: AD_Field.DefaultValue
-- 2022-10-22T06:02:39.002Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=380,Updated=TO_TIMESTAMP('2022-10-22 08:02:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53280
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Color Logic
-- Column: AD_Field.ColorLogic
-- 2022-10-22T06:02:39.015Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=390,Updated=TO_TIMESTAMP('2022-10-22 08:02:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=542584
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Exclude from Zoom Targets
-- Column: AD_Field.IsExcludeFromZoomTargets
-- 2022-10-22T06:02:39.023Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=400,Updated=TO_TIMESTAMP('2022-10-22 08:02:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=643998
;

-- Field: Fenster Verwaltung(102,D) -> Feld(107,D) -> Always Updateable
-- Column: AD_Field.IsAlwaysUpdateable
-- 2022-10-22T06:02:42.470Z
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2022-10-22 08:02:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707817
;

