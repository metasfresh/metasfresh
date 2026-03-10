-- Run mode: SWING_CLIENT

-- Element: null
-- 2024-09-19T10:37:41.985Z
UPDATE AD_Element_Trl SET Name='Computing Method', PrintName='Computing Method',Updated=TO_TIMESTAMP('2024-09-19 12:37:41.985','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582396 AND AD_Language='en_US'
;

-- 2024-09-19T10:37:41.986Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582396,'en_US')
;

-- Element: null
-- 2024-09-19T10:37:43.882Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-19 12:37:43.882','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582396 AND AD_Language='de_CH'
;

-- 2024-09-19T10:37:43.884Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582396,'de_CH')
;

-- Element: null
-- 2024-09-19T10:37:44.985Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-19 12:37:44.985','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582396 AND AD_Language='de_DE'
;

-- 2024-09-19T10:37:44.988Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582396,'de_DE')
;

-- 2024-09-19T10:37:44.989Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582396,'de_DE')
;

-- Element: null
-- 2024-09-19T10:45:20.441Z
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='',Updated=TO_TIMESTAMP('2024-09-19 12:45:20.441','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582396 AND AD_Language='en_US'
;

-- 2024-09-19T10:45:20.444Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582396,'en_US')
;

-- Column: ModCntr_Type.IsSOTrx
-- 2024-09-24T07:31:33.126Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589159,1106,0,20,542337,'IsSOTrx',TO_TIMESTAMP('2024-09-24 09:31:32.949','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','Dies ist eine Verkaufstransaktion','de.metas.contracts',0,1,'The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Verkaufstransaktion',0,0,TO_TIMESTAMP('2024-09-24 09:31:32.949','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-24T07:31:33.131Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589159 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-24T07:31:33.136Z
/* DDL */  select update_Column_Translation_From_AD_Element(1106)
;

-- 2024-09-24T07:31:35.303Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Type','ALTER TABLE public.ModCntr_Type ADD COLUMN IsSOTrx CHAR(1) DEFAULT ''N'' CHECK (IsSOTrx IN (''Y'',''N'')) NOT NULL')
;

-- Field: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> Verkaufstransaktion
-- Column: ModCntr_Type.IsSOTrx
-- 2024-09-24T11:42:01.389Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589159,731779,0,547011,TO_TIMESTAMP('2024-09-24 13:42:01.159','YYYY-MM-DD HH24:MI:SS.US'),100,'Dies ist eine Verkaufstransaktion',1,'de.metas.contracts','The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','N','N','N','N','N','N','Verkaufstransaktion',TO_TIMESTAMP('2024-09-24 13:42:01.159','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-24T11:42:01.394Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=731779 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-24T11:42:01.398Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1106)
;

-- 2024-09-24T11:42:01.447Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731779
;

-- 2024-09-24T11:42:01.449Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731779)
;

-- UI Element: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> main -> 20 -> flags.Verkaufstransaktion
-- Column: ModCntr_Type.IsSOTrx
-- 2024-09-24T11:43:43.961Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731779,0,547011,550770,626086,'F',TO_TIMESTAMP('2024-09-24 13:43:43.811','YYYY-MM-DD HH24:MI:SS.US'),100,'Dies ist eine Verkaufstransaktion','The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','N','Y','N','N','N',0,'Verkaufstransaktion',20,0,0,TO_TIMESTAMP('2024-09-24 13:43:43.811','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> Verkaufstransaktion
-- Column: ModCntr_Type.IsSOTrx
-- 2024-09-24T11:45:12.076Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2024-09-24 13:45:12.076','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=731779
;

-- Field: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> Verkaufstransaktion
-- Column: ModCntr_Type.IsSOTrx
-- 2024-09-24T11:45:59.899Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-09-24 13:45:59.899','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=731779
;

