-- Column: I_BPartner.IsManuallyCreated
-- 2023-06-19T13:42:39.546810600Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586822,582439,0,20,533,'IsManuallyCreated',TO_TIMESTAMP('2023-06-19 16:42:39.349','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Manuell erstellt',0,0,TO_TIMESTAMP('2023-06-19 16:42:39.349','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-19T13:42:40.263528800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586822 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-19T13:42:40.607273900Z
/* DDL */  select update_Column_Translation_From_AD_Element(582439) 
;

-- 2023-06-19T13:42:45.948713100Z
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN IsManuallyCreated CHAR(1) DEFAULT ''N'' CHECK (IsManuallyCreated IN (''Y'',''N'')) NOT NULL')
;

-- Column: I_BPartner.ISO_Code
-- 2023-06-19T13:44:37.004936900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586823,328,0,10,533,'ISO_Code',TO_TIMESTAMP('2023-06-19 16:44:36.851','YYYY-MM-DD HH24:MI:SS.US'),100,'N','','Dreibuchstabiger ISO 4217 Code für die Währung','D',0,3,'Für Details - http://www.unece.org/cefact/recommendations/rec09/rec09_ecetrd203.pdf','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'ISO Währungscode',0,0,TO_TIMESTAMP('2023-06-19 16:44:36.851','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-19T13:44:37.007938200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586823 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-19T13:44:37.692461100Z
/* DDL */  select update_Column_Translation_From_AD_Element(328) 
;

-- 2023-06-19T13:44:39.681462700Z
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN ISO_Code VARCHAR(3)')
;

-- Field: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> Manuell erstellt
-- Column: I_BPartner.IsManuallyCreated
-- 2023-06-19T13:46:31.856070900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586822,716373,0,441,TO_TIMESTAMP('2023-06-19 16:46:31.72','YYYY-MM-DD HH24:MI:SS.US'),100,1,'D','Y','N','N','N','N','N','N','N','Manuell erstellt',TO_TIMESTAMP('2023-06-19 16:46:31.72','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-19T13:46:31.861075900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716373 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-19T13:46:31.865075500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582439) 
;

-- 2023-06-19T13:46:31.871070400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716373
;

-- 2023-06-19T13:46:31.878074200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716373)
;

-- Field: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> ISO Währungscode
-- Column: I_BPartner.ISO_Code
-- 2023-06-19T13:46:44.581931400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586823,716374,0,441,TO_TIMESTAMP('2023-06-19 16:46:44.422','YYYY-MM-DD HH24:MI:SS.US'),100,'Dreibuchstabiger ISO 4217 Code für die Währung',3,'D','Für Details - http://www.unece.org/cefact/recommendations/rec09/rec09_ecetrd203.pdf','Y','N','N','N','N','N','N','N','ISO Währungscode',TO_TIMESTAMP('2023-06-19 16:46:44.422','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-19T13:46:44.582941900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716374 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-19T13:46:44.583931200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(328) 
;

-- 2023-06-19T13:46:44.590932200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716374
;

-- 2023-06-19T13:46:44.590932200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716374)
;

-- UI Element: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> main -> 20 -> flags.Manuell erstellt
-- Column: I_BPartner.IsManuallyCreated
-- 2023-06-19T13:47:07.934920200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716373,0,441,618013,541265,'F',TO_TIMESTAMP('2023-06-19 16:47:07.785','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Manuell erstellt',60,0,0,TO_TIMESTAMP('2023-06-19 16:47:07.785','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> main -> 20 -> div.ISO Währungscode
-- Column: I_BPartner.ISO_Code
-- 2023-06-19T13:48:31.894099600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716374,0,441,618014,541267,'F',TO_TIMESTAMP('2023-06-19 16:48:31.756','YYYY-MM-DD HH24:MI:SS.US'),100,'Dreibuchstabiger ISO 4217 Code für die Währung','Für Details - http://www.unece.org/cefact/recommendations/rec09/rec09_ecetrd203.pdf','Y','N','N','Y','N','N','N',0,'ISO Währungscode',36,0,0,TO_TIMESTAMP('2023-06-19 16:48:31.756','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: I_BPartner.IsManuallyCreated
-- 2023-06-19T13:49:32.088050Z
UPDATE AD_Column SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2023-06-19 16:49:32.088','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586822
;

