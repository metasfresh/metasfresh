-- 2023-03-03T13:57:43.422Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582117,0,'IsEnforceCorrectionInvoice',TO_TIMESTAMP('2023-03-03 14:57:43','YYYY-MM-DD HH24:MI:SS'),100,'If active invoices that have this country as billto-location can''t be voided. Instead the process Generate Correction Invoice is available.','D','Y','Enforce Correction Invoice','Enforce Correction Invoice',TO_TIMESTAMP('2023-03-03 14:57:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-03T13:57:43.432Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582117 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsEnforceCorrectionInvoice
-- 2023-03-03T14:04:30.534Z
UPDATE AD_Element_Trl SET Description='Wenn aktiv können Rechnungen, die dieses Land als Rechnungsadresse haben, nicht storniert werden. Stattdessen steht der Prozess Generiere Korrekturrechnung zur Verfügung.', IsTranslated='Y', Name='Erzwinge Korrekturrechnung', PrintName='Erzwinge Korrekturrechnung',Updated=TO_TIMESTAMP('2023-03-03 15:04:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582117 AND AD_Language='de_CH'
;

-- 2023-03-03T14:04:30.577Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582117,'de_CH') 
;

-- Element: IsEnforceCorrectionInvoice
-- 2023-03-03T14:04:35.827Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-03-03 15:04:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582117 AND AD_Language='en_US'
;

-- 2023-03-03T14:04:35.829Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582117,'en_US') 
;

-- Element: IsEnforceCorrectionInvoice
-- 2023-03-03T14:05:53.726Z
UPDATE AD_Element_Trl SET Description='Wenn aktiv können Rechnungen, die dieses Land als Rechnungsadresse haben, nicht storniert werden. Stattdessen steht der Prozess Generiere Korrekturrechnung zur Verfügung.', IsTranslated='Y', Name='Erzwinge Korrekturrechnung', PrintName='Erzwinge Korrekturrechnung',Updated=TO_TIMESTAMP('2023-03-03 15:05:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582117 AND AD_Language='de_DE'
;

-- 2023-03-03T14:05:53.727Z
UPDATE AD_Element SET Description='Wenn aktiv können Rechnungen, die dieses Land als Rechnungsadresse haben, nicht storniert werden. Stattdessen steht der Prozess Generiere Korrekturrechnung zur Verfügung.', Name='Erzwinge Korrekturrechnung', PrintName='Erzwinge Korrekturrechnung' WHERE AD_Element_ID=582117
;

-- 2023-03-03T14:05:54.161Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582117,'de_DE') 
;

-- 2023-03-03T14:05:54.163Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582117,'de_DE') 
;

-- Column: C_Country.IsEnforceCorrectionInvoice
-- 2023-03-03T14:16:33.427Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586283,582117,0,20,170,'IsEnforceCorrectionInvoice',TO_TIMESTAMP('2023-03-03 15:16:33','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Wenn aktiv können Rechnungen, die dieses Land als Rechnungsadresse haben, nicht storniert werden. Stattdessen steht der Prozess Generiere Korrekturrechnung zur Verfügung.','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Erzwinge Korrekturrechnung',0,0,TO_TIMESTAMP('2023-03-03 15:16:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-03T14:16:33.431Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586283 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-03T14:16:33.435Z
/* DDL */  select update_Column_Translation_From_AD_Element(582117) 
;

-- 2023-03-03T14:16:34.572Z
/* DDL */ SELECT public.db_alter_table('C_Country','ALTER TABLE public.C_Country ADD COLUMN IsEnforceCorrectionInvoice CHAR(1) DEFAULT ''N'' CHECK (IsEnforceCorrectionInvoice IN (''Y'',''N'')) NOT NULL')
;

-- Field: Land, Region, Stadt(122,D) -> Land(135,D) -> NumCountryCode
-- Column: C_Country.NumCountryCode
-- 2023-03-03T14:20:58.706Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,542268,712776,0,135,TO_TIMESTAMP('2023-03-03 15:20:58','YYYY-MM-DD HH24:MI:SS'),100,'ISO-3166-NumCountryCode',14,'D','Y','N','N','N','N','N','N','N','NumCountryCode',TO_TIMESTAMP('2023-03-03 15:20:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-03T14:20:58.709Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712776 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-03T14:20:58.712Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540519) 
;

-- 2023-03-03T14:20:58.731Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712776
;

-- 2023-03-03T14:20:58.737Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712776)
;

-- Field: Land, Region, Stadt(122,D) -> Land(135,D) -> Erzwinge Korrekturrechnung
-- Column: C_Country.IsEnforceCorrectionInvoice
-- 2023-03-03T14:20:58.826Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586283,712777,0,135,TO_TIMESTAMP('2023-03-03 15:20:58','YYYY-MM-DD HH24:MI:SS'),100,'Wenn aktiv können Rechnungen, die dieses Land als Rechnungsadresse haben, nicht storniert werden. Stattdessen steht der Prozess Generiere Korrekturrechnung zur Verfügung.',1,'D','Y','N','N','N','N','N','N','N','Erzwinge Korrekturrechnung',TO_TIMESTAMP('2023-03-03 15:20:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-03T14:20:58.827Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712777 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-03T14:20:58.828Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582117) 
;

-- 2023-03-03T14:20:58.830Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712777
;

-- 2023-03-03T14:20:58.831Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712777)
;

-- UI Element: Land, Region, Stadt(122,D) -> Land(135,D) -> main -> 20 -> flags.Erzwinge Korrekturrechnung
-- Column: C_Country.IsEnforceCorrectionInvoice
-- 2023-03-03T14:30:01.842Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,UIStyle,Updated,UpdatedBy) VALUES (0,712777,0,135,540559,615972,'F',TO_TIMESTAMP('2023-03-03 15:30:01','YYYY-MM-DD HH24:MI:SS'),100,'Wenn aktiv können Rechnungen, die dieses Land als Rechnungsadresse haben, nicht storniert werden. Stattdessen steht der Prozess Generiere Korrekturrechnung zur Verfügung.','Y','N','N','Y','N','N','N',0,'Erzwinge Korrekturrechnung',40,0,0,'',TO_TIMESTAMP('2023-03-03 15:30:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-03T14:32:58.143Z
-- Country: Poland
UPDATE C_Country SET IsEnforceCorrectionInvoice='Y',Updated=TO_TIMESTAMP('2023-03-03 15:32:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Country_ID=280
;

