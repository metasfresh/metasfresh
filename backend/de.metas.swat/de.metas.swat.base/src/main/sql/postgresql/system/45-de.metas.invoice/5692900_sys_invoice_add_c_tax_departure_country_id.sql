-- 2023-06-22T13:18:26.667Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582466,0,'C_Tax_Departure_Country_ID',TO_TIMESTAMP('2023-06-22 15:18:26','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Tax Departure Country','Tax Departure Country',TO_TIMESTAMP('2023-06-22 15:18:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-22T13:18:26.676Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582466 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: C_Tax_Departure_Country_ID
-- 2023-06-22T13:18:50.718Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-06-22 15:18:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582466 AND AD_Language='en_US'
;

-- 2023-06-22T13:18:50.744Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582466,'en_US') 
;

-- Element: C_Tax_Departure_Country_ID
-- 2023-06-22T13:19:06.120Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Steuerabgangsland', PrintName='Steuerabgangsland',Updated=TO_TIMESTAMP('2023-06-22 15:19:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582466 AND AD_Language='de_CH'
;

-- 2023-06-22T13:19:06.123Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582466,'de_CH') 
;

-- Element: C_Tax_Departure_Country_ID
-- 2023-06-22T13:19:13.994Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Steuerabgangsland', PrintName='Steuerabgangsland',Updated=TO_TIMESTAMP('2023-06-22 15:19:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582466 AND AD_Language='de_DE'
;

-- 2023-06-22T13:19:13.996Z
UPDATE AD_Element SET Name='Steuerabgangsland', PrintName='Steuerabgangsland', Updated=TO_TIMESTAMP('2023-06-22 15:19:13','YYYY-MM-DD HH24:MI:SS') WHERE AD_Element_ID=582466
;

-- 2023-06-22T13:19:25.187Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582466,'de_DE') 
;

-- 2023-06-22T13:19:25.188Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582466,'de_DE') 
;

-- Column: C_Invoice.C_Tax_Departure_Country_ID
-- 2023-06-22T13:40:34.120Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586847,582466,0,18,156,318,505135,'XX','C_Tax_Departure_Country_ID',TO_TIMESTAMP('2023-06-22 15:40:33','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Steuerabgangsland',0,0,TO_TIMESTAMP('2023-06-22 15:40:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-06-22T13:40:34.123Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586847 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-22T13:40:34.574Z
/* DDL */  select update_Column_Translation_From_AD_Element(582466) 
;

-- Column: C_Invoice.C_Tax_Departure_Country_ID
-- 2023-06-23T14:01:38.807Z
UPDATE AD_Column SET AD_Reference_ID=30, AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2023-06-23 16:01:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586847
;

-- 2023-06-23T15:10:00.491Z
/* DDL */ SELECT public.db_alter_table('C_Invoice','ALTER TABLE public.C_Invoice ADD COLUMN C_Tax_Departure_Country_ID NUMERIC(10)')
;

-- 2023-06-23T15:10:01.774Z
ALTER TABLE C_Invoice ADD CONSTRAINT CTaxDepartureCountry_CInvoice FOREIGN KEY (C_Tax_Departure_Country_ID) REFERENCES public.C_Country DEFERRABLE INITIALLY DEFERRED
;

-- Field: Rechnung(167,D) -> Rechnung(263,D) -> Steuerabgangsland
-- Column: C_Invoice.C_Tax_Departure_Country_ID
-- 2023-06-23T06:35:13.281Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586847,716449,0,263,TO_TIMESTAMP('2023-06-23 08:35:13','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Steuerabgangsland',TO_TIMESTAMP('2023-06-23 08:35:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-23T06:35:13.285Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716449 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-23T06:35:13.292Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582466) 
;

-- 2023-06-23T06:35:13.310Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716449
;

-- 2023-06-23T06:35:13.317Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716449)
;

-- UI Element: Rechnung(167,D) -> Rechnung(263,D) -> main -> 10 -> rest.Steuerabgangsland
-- Column: C_Invoice.C_Tax_Departure_Country_ID
-- 2023-06-23T06:41:24.565Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716449,0,263,540029,618084,'F',TO_TIMESTAMP('2023-06-23 08:41:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Steuerabgangsland',16,0,0,TO_TIMESTAMP('2023-06-23 08:41:24','YYYY-MM-DD HH24:MI:SS'),100)
;

