-- Column: S_Resource_Group.M_Product_Category_ID
-- 2022-05-27T10:11:20.173Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583198,453,0,30,542153,'M_Product_Category_ID',TO_TIMESTAMP('2022-05-27 13:11:19','YYYY-MM-DD HH24:MI:SS'),100,'N','Kategorie eines Produktes','D',0,10,'Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Produkt Kategorie',0,0,TO_TIMESTAMP('2022-05-27 13:11:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-27T10:11:20.176Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583198 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-27T10:11:20.180Z
/* DDL */  select update_Column_Translation_From_AD_Element(453) 
;

-- 2022-05-27T10:11:25.132Z
/* DDL */ SELECT public.db_alter_table('S_Resource_Group','ALTER TABLE public.S_Resource_Group ADD COLUMN M_Product_Category_ID NUMERIC(10) NOT NULL')
;

-- 2022-05-27T10:11:25.145Z
ALTER TABLE S_Resource_Group ADD CONSTRAINT MProductCategory_SResourceGroup FOREIGN KEY (M_Product_Category_ID) REFERENCES public.M_Product_Category DEFERRABLE INITIALLY DEFERRED
;

-- Table: S_Resource_Group
-- 2022-05-27T10:11:31.263Z
UPDATE AD_Table SET AD_Window_ID=541501,Updated=TO_TIMESTAMP('2022-05-27 13:11:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542153
;

-- Field: Resource Group -> Resource Group -> Produkt Kategorie
-- Column: S_Resource_Group.M_Product_Category_ID
-- 2022-05-27T10:11:57.703Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583198,696466,0,546255,TO_TIMESTAMP('2022-05-27 13:11:57','YYYY-MM-DD HH24:MI:SS'),100,'Kategorie eines Produktes',10,'D','Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','N','N','N','N','N','N','N','Produkt Kategorie',TO_TIMESTAMP('2022-05-27 13:11:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-27T10:11:57.706Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696466 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-27T10:11:57.711Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(453) 
;

-- 2022-05-27T10:11:57.761Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696466
;

-- 2022-05-27T10:11:57.767Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696466)
;

-- 2022-05-27T10:12:24.907Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545923,549098,TO_TIMESTAMP('2022-05-27 13:12:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','product',20,TO_TIMESTAMP('2022-05-27 13:12:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Resource Group -> Resource Group.Produkt Kategorie
-- Column: S_Resource_Group.M_Product_Category_ID
-- 2022-05-27T10:12:36.106Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,696466,0,546255,607609,549098,'F',TO_TIMESTAMP('2022-05-27 13:12:35','YYYY-MM-DD HH24:MI:SS'),100,'Kategorie eines Produktes','Identifiziert die Kategorie zu der ein Produkt gehört. Produktkategorien werden für Preisfindung und Auswahl verwendet.','Y','N','Y','N','N','Produkt Kategorie',10,0,0,TO_TIMESTAMP('2022-05-27 13:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: S_Resource_Group.DurationUnit
-- 2022-05-27T10:23:57.235Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583200,2321,0,17,299,542153,'DurationUnit',TO_TIMESTAMP('2022-05-27 13:23:57','YYYY-MM-DD HH24:MI:SS'),100,'N','Unit of Duration','D',0,1,'Unit to define the length of time for the execution','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Duration Unit',0,0,TO_TIMESTAMP('2022-05-27 13:23:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-27T10:23:57.237Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583200 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-27T10:23:57.238Z
/* DDL */  select update_Column_Translation_From_AD_Element(2321) 
;

-- 2022-05-27T10:24:02.271Z
/* DDL */ SELECT public.db_alter_table('S_Resource_Group','ALTER TABLE public.S_Resource_Group ADD COLUMN DurationUnit CHAR(1) NOT NULL')
;

-- Field: Resource Group -> Resource Group -> Duration Unit
-- Column: S_Resource_Group.DurationUnit
-- 2022-05-27T10:24:54.471Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583200,696467,0,546255,TO_TIMESTAMP('2022-05-27 13:24:54','YYYY-MM-DD HH24:MI:SS'),100,'Unit of Duration',1,'D','Unit to define the length of time for the execution','Y','N','N','N','N','N','N','N','Duration Unit',TO_TIMESTAMP('2022-05-27 13:24:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-27T10:24:54.472Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696467 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-27T10:24:54.473Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2321) 
;

-- 2022-05-27T10:24:54.482Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696467
;

-- 2022-05-27T10:24:54.482Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696467)
;

-- UI Element: Resource Group -> Resource Group.Duration Unit
-- Column: S_Resource_Group.DurationUnit
-- 2022-05-27T10:25:07.549Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,696467,0,546255,607610,549098,'F',TO_TIMESTAMP('2022-05-27 13:25:07','YYYY-MM-DD HH24:MI:SS'),100,'Unit of Duration','Unit to define the length of time for the execution','Y','N','Y','N','N','Duration Unit',20,0,0,TO_TIMESTAMP('2022-05-27 13:25:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: S_Resource_Group.DurationUnit
-- 2022-05-27T10:44:49.927Z
UPDATE AD_Column SET DefaultValue='h',Updated=TO_TIMESTAMP('2022-05-27 13:44:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583200
;

