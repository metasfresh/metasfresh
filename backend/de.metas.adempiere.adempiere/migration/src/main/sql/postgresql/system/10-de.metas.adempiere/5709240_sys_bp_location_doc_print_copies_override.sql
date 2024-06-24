-- 2023-10-31T12:51:54.986Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582783,0,'DocumentCopies_Override',TO_TIMESTAMP('2023-10-31 13:51:54','YYYY-MM-DD HH24:MI:SS'),100,'Überschreibt die Anzahl der Kopien, die in der Belegart gesetzt sind. (Wert 0 wird ignoriert)','D','Y','Kopien','Kopien',TO_TIMESTAMP('2023-10-31 13:51:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-31T12:51:55.009Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582783 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: DocumentCopies_Override
-- 2023-10-31T12:52:12.147Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-10-31 13:52:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582783 AND AD_Language='de_CH'
;

-- 2023-10-31T12:52:12.187Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582783,'de_CH') 
;

-- Element: DocumentCopies_Override
-- 2023-10-31T12:52:28.180Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-10-31 13:52:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582783 AND AD_Language='de_DE'
;

-- 2023-10-31T12:52:28.182Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582783,'de_DE') 
;

-- 2023-10-31T12:52:28.185Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582783,'de_DE') 
;

-- Element: DocumentCopies_Override
-- 2023-10-31T12:55:21.576Z
UPDATE AD_Element_Trl SET Description='Overwrites the number of copies set in the document type. (Value 0 is ignored)', IsTranslated='Y', Name='Copies', PrintName='Copies',Updated=TO_TIMESTAMP('2023-10-31 13:55:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582783 AND AD_Language='en_US'
;

-- 2023-10-31T12:55:21.579Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582783,'en_US') 
;

-- Column: C_BP_PrintFormat.C_BPartner_Location_ID
-- Column: C_BP_PrintFormat.C_BPartner_Location_ID
-- 2023-10-31T13:01:21.020Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MandatoryLogic,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587622,189,0,19,540638,'C_BPartner_Location_ID',TO_TIMESTAMP('2023-10-31 14:01:20','YYYY-MM-DD HH24:MI:SS'),100,'N','Identifiziert die (Liefer-) Adresse des Geschäftspartners','D',0,10,'Identifiziert die Adresse des Geschäftspartners','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','@DocumentCopies_Override@!0',0,'Standort',0,0,TO_TIMESTAMP('2023-10-31 14:01:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-31T13:01:21.031Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587622 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-31T13:01:21.042Z
/* DDL */  select update_Column_Translation_From_AD_Element(189) 
;

-- 2023-10-31T13:01:40.426Z
/* DDL */ SELECT public.db_alter_table('C_BP_PrintFormat','ALTER TABLE public.C_BP_PrintFormat ADD COLUMN C_BPartner_Location_ID NUMERIC(10)')
;

-- 2023-10-31T13:01:40.461Z
ALTER TABLE C_BP_PrintFormat ADD CONSTRAINT CBPartnerLocation_CBPPrintFormat FOREIGN KEY (C_BPartner_Location_ID) REFERENCES public.C_BPartner_Location DEFERRABLE INITIALLY DEFERRED
;

-- Name: C_BPartner_Loc
-- 2023-11-02T07:32:10.468Z
UPDATE AD_Val_Rule SET Code='C_BPartner_Location.C_BPartner_ID=@C_BPartner_ID@ AND C_BPartner_Location.IsActive=''Y''',Updated=TO_TIMESTAMP('2023-11-02 08:32:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=131
;

-- Column: C_BP_PrintFormat.C_BPartner_Location_ID
-- Column: C_BP_PrintFormat.C_BPartner_Location_ID
-- 2023-11-02T07:32:43.702Z
UPDATE AD_Column SET AD_Reference_ID=30, AD_Val_Rule_ID=131,Updated=TO_TIMESTAMP('2023-11-02 08:32:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587622
;

-- Column: C_BP_PrintFormat.C_BPartner_Location_ID
-- Column: C_BP_PrintFormat.C_BPartner_Location_ID
-- 2023-11-02T09:30:28.622Z
UPDATE AD_Column SET MandatoryLogic='',Updated=TO_TIMESTAMP('2023-11-02 10:30:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587622
;

-- Column: C_BP_PrintFormat.DocumentCopies_Override
-- Column: C_BP_PrintFormat.DocumentCopies_Override
-- 2023-10-31T13:03:01.784Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MandatoryLogic,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587623,582783,0,22,540638,'DocumentCopies_Override',TO_TIMESTAMP('2023-10-31 14:03:01','YYYY-MM-DD HH24:MI:SS'),100,'N','Überschreibt die Anzahl der Kopien, die in der Belegart gesetzt sind. (Wert 0 wird ignoriert)','D',0,2,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','@C_BPartner_Location_ID@!0',0,'Kopien',0,0,TO_TIMESTAMP('2023-10-31 14:03:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-31T13:03:01.787Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587623 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-31T13:03:01.793Z
/* DDL */  select update_Column_Translation_From_AD_Element(582783) 
;

-- Column: C_BP_PrintFormat.DocumentCopies_Override
-- Column: C_BP_PrintFormat.DocumentCopies_Override
-- 2023-11-02T11:50:47.626Z
UPDATE AD_Column SET AD_Reference_ID=11, DefaultValue='0', IsMandatory='Y',Updated=TO_TIMESTAMP('2023-11-02 12:50:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587623
;

-- 2023-10-31T13:03:22.798Z
/* DDL */ SELECT public.db_alter_table('C_BP_PrintFormat','ALTER TABLE public.C_BP_PrintFormat ADD COLUMN DocumentCopies_Override NUMERIC(10) DEFAULT 0 NOT NULL')
;

-- Field: Geschäftspartner -> Druck Format -> Standort
-- Column: C_BP_PrintFormat.C_BPartner_Location_ID
-- Field: Geschäftspartner(123,D) -> Druck Format(540653,D) -> Standort
-- Column: C_BP_PrintFormat.C_BPartner_Location_ID
-- 2023-10-31T13:15:53.322Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587622,721736,0,540653,TO_TIMESTAMP('2023-10-31 14:15:53','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',10,'D','Identifiziert die Adresse des Geschäftspartners','Y','N','N','N','N','N','N','N','Standort',TO_TIMESTAMP('2023-10-31 14:15:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-31T13:15:53.330Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721736 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-31T13:15:53.338Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(189) 
;

-- 2023-10-31T13:15:53.471Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721736
;

-- 2023-10-31T13:15:53.486Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721736)
;

-- Field: Geschäftspartner -> Druck Format -> Kopien
-- Column: C_BP_PrintFormat.DocumentCopies_Override
-- Field: Geschäftspartner(123,D) -> Druck Format(540653,D) -> Kopien
-- Column: C_BP_PrintFormat.DocumentCopies_Override
-- 2023-10-31T13:17:34.942Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587623,721737,0,540653,TO_TIMESTAMP('2023-10-31 14:17:34','YYYY-MM-DD HH24:MI:SS'),100,'Überschreibt die Anzahl der Kopien, die in der Belegart gesetzt sind. (Wert 0 wird ignoriert)',2,'D','Y','N','N','N','N','N','N','N','Kopien',TO_TIMESTAMP('2023-10-31 14:17:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-31T13:17:34.945Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721737 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-31T13:17:34.948Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582783) 
;

-- 2023-10-31T13:17:34.953Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721737
;

-- 2023-10-31T13:17:34.955Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721737)
;

-- UI Element: Geschäftspartner -> Druck Format.Standort
-- Column: C_BP_PrintFormat.C_BPartner_Location_ID
-- UI Element: Geschäftspartner(123,D) -> Druck Format(540653,D) -> main -> 10 -> default.Standort
-- Column: C_BP_PrintFormat.C_BPartner_Location_ID
-- 2023-10-31T13:21:37.850Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721736,0,540653,1000037,621199,'F',TO_TIMESTAMP('2023-10-31 14:21:37','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners','Identifiziert die Adresse des Geschäftspartners','Y','N','N','Y','N','N','N',0,'Standort',42,0,0,TO_TIMESTAMP('2023-10-31 14:21:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Geschäftspartner -> Druck Format.Kopien
-- Column: C_BP_PrintFormat.DocumentCopies_Override
-- UI Element: Geschäftspartner(123,D) -> Druck Format(540653,D) -> main -> 10 -> default.Kopien
-- Column: C_BP_PrintFormat.DocumentCopies_Override
-- 2023-10-31T13:21:57.829Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721737,0,540653,1000037,621200,'F',TO_TIMESTAMP('2023-10-31 14:21:57','YYYY-MM-DD HH24:MI:SS'),100,'Überschreibt die Anzahl der Kopien, die in der Belegart gesetzt sind. (Wert 0 wird ignoriert)','Y','N','N','Y','N','N','N',0,'Kopien',43,0,0,TO_TIMESTAMP('2023-10-31 14:21:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Geschäftspartner -> Druck Format.Standort
-- Column: C_BP_PrintFormat.C_BPartner_Location_ID
-- UI Element: Geschäftspartner(123,D) -> Druck Format(540653,D) -> main -> 10 -> default.Standort
-- Column: C_BP_PrintFormat.C_BPartner_Location_ID
-- 2023-10-31T13:22:28.573Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-10-31 14:22:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621199
;

-- UI Element: Geschäftspartner -> Druck Format.Kopien
-- Column: C_BP_PrintFormat.DocumentCopies_Override
-- UI Element: Geschäftspartner(123,D) -> Druck Format(540653,D) -> main -> 10 -> default.Kopien
-- Column: C_BP_PrintFormat.DocumentCopies_Override
-- 2023-10-31T13:22:28.582Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-10-31 14:22:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621200
;

-- UI Element: Geschäftspartner -> Druck Format.AD_Zebra_Config_ID
-- Column: C_BP_PrintFormat.AD_Zebra_Config_ID
-- UI Element: Geschäftspartner(123,D) -> Druck Format(540653,D) -> main -> 10 -> default.AD_Zebra_Config_ID
-- Column: C_BP_PrintFormat.AD_Zebra_Config_ID
-- 2023-10-31T13:22:28.590Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-10-31 14:22:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=578119
;

-- UI Element: Geschäftspartner -> Druck Format.Aktiv
-- Column: C_BP_PrintFormat.IsActive
-- UI Element: Geschäftspartner(123,D) -> Druck Format(540653,D) -> main -> 10 -> default.Aktiv
-- Column: C_BP_PrintFormat.IsActive
-- 2023-10-31T13:22:28.597Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-10-31 14:22:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000354
;

-- UI Element: Geschäftspartner -> Druck Format.Sektion
-- Column: C_BP_PrintFormat.AD_Org_ID
-- UI Element: Geschäftspartner(123,D) -> Druck Format(540653,D) -> main -> 10 -> default.Sektion
-- Column: C_BP_PrintFormat.AD_Org_ID
-- 2023-10-31T13:22:28.607Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-10-31 14:22:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546568
;

-- Column: C_BP_PrintFormat.SeqNo
-- Column: C_BP_PrintFormat.SeqNo
-- 2023-11-02T11:03:13.510Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587626,566,0,11,540638,'SeqNo',TO_TIMESTAMP('2023-11-02 12:03:13','YYYY-MM-DD HH24:MI:SS'),100,'N','0','Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','D',0,22,'"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Reihenfolge',0,0,TO_TIMESTAMP('2023-11-02 12:03:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-11-02T11:03:13.530Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587626 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-11-02T11:03:13.581Z
/* DDL */  select update_Column_Translation_From_AD_Element(566)
;

-- 2023-11-02T11:12:26.571Z
/* DDL */ SELECT public.db_alter_table('C_BP_PrintFormat','ALTER TABLE public.C_BP_PrintFormat ADD COLUMN SeqNo NUMERIC(10) DEFAULT 0 NOT NULL')
;

-- Field: Geschäftspartner -> Druck Format -> Reihenfolge
-- Column: C_BP_PrintFormat.SeqNo
-- Field: Geschäftspartner(123,D) -> Druck Format(540653,D) -> Reihenfolge
-- Column: C_BP_PrintFormat.SeqNo
-- 2023-11-02T14:22:22.464Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587626,721746,0,540653,0,TO_TIMESTAMP('2023-11-02 15:22:22','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',0,'U','"Reihenfolge" bestimmt die Reihenfolge der Einträge',0,'Y','Y','Y','N','N','N','N','N','Reihenfolge',0,60,0,1,1,TO_TIMESTAMP('2023-11-02 15:22:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-02T14:22:22.474Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721746 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-02T14:22:22.480Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(566)
;

-- 2023-11-02T14:22:22.635Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721746
;

-- 2023-11-02T14:22:22.646Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721746)
;

-- Field: Geschäftspartner -> Druck Format -> Reihenfolge
-- Column: C_BP_PrintFormat.SeqNo
-- Field: Geschäftspartner(123,D) -> Druck Format(540653,D) -> Reihenfolge
-- Column: C_BP_PrintFormat.SeqNo
-- 2023-11-02T14:24:38.613Z
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2023-11-02 15:24:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=721746
;

-- UI Element: Geschäftspartner -> Druck Format.Reihenfolge
-- Column: C_BP_PrintFormat.SeqNo
-- UI Element: Geschäftspartner(123,D) -> Druck Format(540653,D) -> main -> 10 -> default.Reihenfolge
-- Column: C_BP_PrintFormat.SeqNo
-- 2023-11-02T14:25:04.805Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721746,0,540653,1000037,621206,'F',TO_TIMESTAMP('2023-11-02 15:25:04','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','Y','N','N','N',0,'Reihenfolge',70,0,0,TO_TIMESTAMP('2023-11-02 15:25:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Geschäftspartner -> Druck Format.Reihenfolge
-- Column: C_BP_PrintFormat.SeqNo
-- UI Element: Geschäftspartner(123,D) -> Druck Format(540653,D) -> main -> 10 -> default.Reihenfolge
-- Column: C_BP_PrintFormat.SeqNo
-- 2023-11-02T14:25:59.022Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-11-02 15:25:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621206
;

-- UI Element: Geschäftspartner -> Druck Format.Belegart
-- Column: C_BP_PrintFormat.C_DocType_ID
-- UI Element: Geschäftspartner(123,D) -> Druck Format(540653,D) -> main -> 10 -> default.Belegart
-- Column: C_BP_PrintFormat.C_DocType_ID
-- 2023-11-02T14:25:59.031Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-11-02 15:25:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000351
;

-- UI Element: Geschäftspartner -> Druck Format.DB-Tabelle
-- Column: C_BP_PrintFormat.AD_Table_ID
-- UI Element: Geschäftspartner(123,D) -> Druck Format(540653,D) -> main -> 10 -> default.DB-Tabelle
-- Column: C_BP_PrintFormat.AD_Table_ID
-- 2023-11-02T14:25:59.039Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-11-02 15:25:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000352
;

-- UI Element: Geschäftspartner -> Druck Format.Druck - Format
-- Column: C_BP_PrintFormat.AD_PrintFormat_ID
-- UI Element: Geschäftspartner(123,D) -> Druck Format(540653,D) -> main -> 10 -> default.Druck - Format
-- Column: C_BP_PrintFormat.AD_PrintFormat_ID
-- 2023-11-02T14:25:59.046Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-11-02 15:25:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000353
;

-- UI Element: Geschäftspartner -> Druck Format.Standort
-- Column: C_BP_PrintFormat.C_BPartner_Location_ID
-- UI Element: Geschäftspartner(123,D) -> Druck Format(540653,D) -> main -> 10 -> default.Standort
-- Column: C_BP_PrintFormat.C_BPartner_Location_ID
-- 2023-11-02T14:25:59.053Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-11-02 15:25:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621199
;

-- UI Element: Geschäftspartner -> Druck Format.Kopien
-- Column: C_BP_PrintFormat.DocumentCopies_Override
-- UI Element: Geschäftspartner(123,D) -> Druck Format(540653,D) -> main -> 10 -> default.Kopien
-- Column: C_BP_PrintFormat.DocumentCopies_Override
-- 2023-11-02T14:25:59.059Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-11-02 15:25:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621200
;

-- UI Element: Geschäftspartner -> Druck Format.AD_Zebra_Config_ID
-- Column: C_BP_PrintFormat.AD_Zebra_Config_ID
-- UI Element: Geschäftspartner(123,D) -> Druck Format(540653,D) -> main -> 10 -> default.AD_Zebra_Config_ID
-- Column: C_BP_PrintFormat.AD_Zebra_Config_ID
-- 2023-11-02T14:25:59.079Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-11-02 15:25:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=578119
;

-- UI Element: Geschäftspartner -> Druck Format.Aktiv
-- Column: C_BP_PrintFormat.IsActive
-- UI Element: Geschäftspartner(123,D) -> Druck Format(540653,D) -> main -> 10 -> default.Aktiv
-- Column: C_BP_PrintFormat.IsActive
-- 2023-11-02T14:25:59.086Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-11-02 15:25:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000354
;

-- UI Element: Geschäftspartner -> Druck Format.Sektion
-- Column: C_BP_PrintFormat.AD_Org_ID
-- UI Element: Geschäftspartner(123,D) -> Druck Format(540653,D) -> main -> 10 -> default.Sektion
-- Column: C_BP_PrintFormat.AD_Org_ID
-- 2023-11-02T14:25:59.094Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-11-02 15:25:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546568
;

-- 2023-11-03T15:46:06.993Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,Updated,UpdatedBy) VALUES (0,582790,0,TO_TIMESTAMP('2023-11-03 16:46:06','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners. Kann nur als Kriterium für die Anzahl von Kopien genutzt werden.','CUST','Identifiziert die Adresse des Geschäftspartners','Y','Standort','Identifiziert die (Auslieferungs-) Adresse des Geschäftspartners','Identifiziert die Adresse des Geschäftspartners','Standort','Standort','Standort',TO_TIMESTAMP('2023-11-03 16:46:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-03T15:46:07.011Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582790 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-11-03T15:46:40.151Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-11-03 16:46:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582790 AND AD_Language='de_CH'
;

-- 2023-11-03T15:46:40.205Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582790,'de_CH')
;

-- Element: null
-- 2023-11-03T15:46:43.152Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-11-03 16:46:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582790 AND AD_Language='de_DE'
;

-- 2023-11-03T15:46:43.154Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582790,'de_DE')
;

-- 2023-11-03T15:46:43.155Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582790,'de_DE')
;

-- Element: null
-- 2023-11-03T15:48:38.455Z
UPDATE AD_Element_Trl SET Description='Identifies the (delivery) address of the business partner. Can only be used as a criterion for the number of copies.', Help='', Name='Location', PO_Description='', PO_Help='', PO_Name='', PO_PrintName='', PrintName='Location',Updated=TO_TIMESTAMP('2023-11-03 16:48:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582790 AND AD_Language='en_US'
;

-- 2023-11-03T15:48:38.470Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582790,'en_US')
;

-- Field: Geschäftspartner -> Druck Format -> Standort
-- Column: C_BP_PrintFormat.C_BPartner_Location_ID
-- Field: Geschäftspartner(123,D) -> Druck Format(540653,D) -> Standort
-- Column: C_BP_PrintFormat.C_BPartner_Location_ID
-- 2023-11-03T15:49:59.660Z
UPDATE AD_Field SET AD_Name_ID=582790, Description='Identifiziert die (Liefer-) Adresse des Geschäftspartners. Kann nur als Kriterium für die Anzahl von Kopien genutzt werden.', Help='Identifiziert die Adresse des Geschäftspartners', Name='Standort',Updated=TO_TIMESTAMP('2023-11-03 16:49:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=721736
;

-- 2023-11-03T15:49:59.663Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582790)
;

-- 2023-11-03T15:49:59.680Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721736
;

-- 2023-11-03T15:49:59.687Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721736)
;

-- Field: Geschäftspartner -> Druck Format -> Reihenfolge
-- Column: C_BP_PrintFormat.SeqNo
-- Field: Geschäftspartner(123,D) -> Druck Format(540653,D) -> Reihenfolge
-- Column: C_BP_PrintFormat.SeqNo
-- 2023-11-03T16:08:46.778Z
UPDATE AD_Field SET SortNo=1.000000000000,Updated=TO_TIMESTAMP('2023-11-03 17:08:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=721746
;

-- Field: Geschäftspartner -> Druck Format -> Belegart
-- Column: C_BP_PrintFormat.C_DocType_ID
-- Field: Geschäftspartner(123,D) -> Druck Format(540653,D) -> Belegart
-- Column: C_BP_PrintFormat.C_DocType_ID
-- 2023-11-03T16:10:29.837Z
UPDATE AD_Field SET SortNo=0,Updated=TO_TIMESTAMP('2023-11-03 17:10:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555269
;

