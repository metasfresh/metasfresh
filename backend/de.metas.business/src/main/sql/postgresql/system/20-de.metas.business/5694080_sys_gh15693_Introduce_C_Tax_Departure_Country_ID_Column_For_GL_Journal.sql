-- Column: SAP_GLJournal.C_Tax_Departure_Country_ID
-- 2023-07-03T16:48:45.227Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587022,582466,0,30,156,542275,'XX','C_Tax_Departure_Country_ID',TO_TIMESTAMP('2023-07-03 19:48:44','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.acct',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Tax Departure Country',0,0,TO_TIMESTAMP('2023-07-03 19:48:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-03T16:48:46.141Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587022 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-03T16:48:46.447Z
/* DDL */  select update_Column_Translation_From_AD_Element(582466) 
;

-- Column: SAP_GLJournal.C_Tax_Departure_Country_ID
-- 2023-07-03T16:49:13.662Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2023-07-03 19:49:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587022
;

-- 2023-07-03T16:49:17.063Z
/* DDL */ SELECT public.db_alter_table('SAP_GLJournal','ALTER TABLE public.SAP_GLJournal ADD COLUMN C_Tax_Departure_Country_ID NUMERIC(10)')
;

-- 2023-07-03T16:49:17.441Z
ALTER TABLE SAP_GLJournal ADD CONSTRAINT CTaxDepartureCountry_SAPGLJournal FOREIGN KEY (C_Tax_Departure_Country_ID) REFERENCES public.C_Country DEFERRABLE INITIALLY DEFERRED
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Steuerabgangsland
-- Column: SAP_GLJournal.C_Tax_Departure_Country_ID
-- 2023-07-04T08:12:56.484Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587022,716620,0,546730,0,TO_TIMESTAMP('2023-07-04 11:12:55','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','N','N','N','N','N','N','N','Steuerabgangsland',0,10,0,1,1,TO_TIMESTAMP('2023-07-04 11:12:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-04T08:12:56.484Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716620 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-04T08:12:56.514Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582466)
;

-- 2023-07-04T08:12:56.529Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716620
;

-- 2023-07-04T08:12:56.529Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716620)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 10 -> currency.Steuerabgangsland
-- Column: SAP_GLJournal.C_Tax_Departure_Country_ID
-- 2023-07-04T08:13:51.718Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716620,0,546730,618202,550189,'F',TO_TIMESTAMP('2023-07-04 11:13:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Steuerabgangsland',15,0,0,TO_TIMESTAMP('2023-07-04 11:13:51','YYYY-MM-DD HH24:MI:SS'),100)
;