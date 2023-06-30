-- Column: C_Campaign_Price.PriceList
-- Column: C_Campaign_Price.PriceList
-- 2023-06-07T10:42:25.588Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586754,520,0,37,541174,'PriceList',TO_TIMESTAMP('2023-06-07 13:42:24','YYYY-MM-DD HH24:MI:SS'),100,'N','Auszeichnungspreis','D',0,22,'"Listenpreis" ist der offizielle Listenpreis in der Dokumentenwährung.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Auszeichnungspreis',0,0,TO_TIMESTAMP('2023-06-07 13:42:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-06-07T10:42:25.597Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586754 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-07T10:42:25.690Z
/* DDL */  select update_Column_Translation_From_AD_Element(520) 
;

-- 2023-06-07T10:42:52.337Z
/* DDL */ SELECT public.db_alter_table('C_Campaign_Price','ALTER TABLE public.C_Campaign_Price ADD COLUMN PriceList NUMERIC')
;

-- Field: Aktionspreise -> Aktionspreise -> Auszeichnungspreis
-- Column: C_Campaign_Price.PriceList
-- Field: Aktionspreise(540580,D) -> Aktionspreise(541551,D) -> Auszeichnungspreis
-- Column: C_Campaign_Price.PriceList
-- 2023-06-07T10:44:01.366Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586754,716277,0,541551,TO_TIMESTAMP('2023-06-07 13:44:01','YYYY-MM-DD HH24:MI:SS'),100,'Auszeichnungspreis',22,'D','"Listenpreis" ist der offizielle Listenpreis in der Dokumentenwährung.','Y','N','N','N','N','N','N','N','Auszeichnungspreis',TO_TIMESTAMP('2023-06-07 13:44:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-07T10:44:01.375Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=716277 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-07T10:44:01.390Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(520) 
;

-- 2023-06-07T10:44:01.435Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716277
;

-- 2023-06-07T10:44:01.448Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716277)
;

-- Field: Aktionspreise -> Aktionspreise -> Auszeichnungspreis
-- Column: C_Campaign_Price.PriceList
-- Field: Aktionspreise(540580,D) -> Aktionspreise(541551,D) -> Auszeichnungspreis
-- Column: C_Campaign_Price.PriceList
-- 2023-06-07T10:44:39.007Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-06-07 13:44:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716277
;

-- Field: Aktionspreise -> Aktionspreise -> Auszeichnungspreis
-- Column: C_Campaign_Price.PriceList
-- Field: Aktionspreise(540580,D) -> Aktionspreise(541551,D) -> Auszeichnungspreis
-- Column: C_Campaign_Price.PriceList
-- 2023-06-07T10:44:56.758Z
UPDATE AD_Field SET SeqNo=85,Updated=TO_TIMESTAMP('2023-06-07 13:44:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716277
;

