-- Run mode: SWING_CLIENT



-- 2025-07-12T09:44:17.535Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583810,0,'PrintFormat_DocumentCopies_Override',TO_TIMESTAMP('2025-07-12 09:44:17.168000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Überschreibt die Anzahl der Kopien, die in der Belegart gesetzt sind. (Wert 0 wird ignoriert)','D','Y','Druck Format - Kopien','Druck Format - Kopien',TO_TIMESTAMP('2025-07-12 09:44:17.168000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-12T09:44:17.541Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583810 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: PrintFormat_DocumentCopies_Override
-- 2025-07-12T09:46:36.731Z
UPDATE AD_Element_Trl SET Description='Overwrites the number of copies set in the document type. (Value 0 is ignored)', IsTranslated='Y', Name='Print Format - Copies', PrintName='Print Format - Copies',Updated=TO_TIMESTAMP('2025-07-12 09:46:36.731000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583810 AND AD_Language='en_US'
;

-- 2025-07-12T09:46:36.732Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-12T09:46:36.916Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583810,'en_US')
;

-- Element: PrintFormat_DocumentCopies_Override
-- 2025-07-12T09:46:37.549Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-07-12 09:46:37.549000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583810 AND AD_Language='de_CH'
;

-- 2025-07-12T09:46:37.551Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583810,'de_CH')
;

-- Column: I_BPartner.PrintFormat_DocumentCopies_Override
-- 2025-07-12T09:56:41.629Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590535,583810,0,11,533,'XX','PrintFormat_DocumentCopies_Override',TO_TIMESTAMP('2025-07-12 09:56:41.517000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','0','Überschreibt die Anzahl der Kopien, die in der Belegart gesetzt sind. (Wert 0 wird ignoriert)','D',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Druck Format - Kopien',0,0,TO_TIMESTAMP('2025-07-12 09:56:41.517000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-07-12T09:56:41.630Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590535 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-12T09:56:41.632Z
/* DDL */  select update_Column_Translation_From_AD_Element(583810)
;

-- 2025-07-12T09:56:43.851Z
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN PrintFormat_DocumentCopies_Override NUMERIC(10) DEFAULT 0 NOT NULL')
;

-- Field: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> Druck Format - Kopien
-- Column: I_BPartner.PrintFormat_DocumentCopies_Override
-- 2025-07-12T09:57:13.726Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590535,750259,0,441,TO_TIMESTAMP('2025-07-12 09:57:13.588000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Überschreibt die Anzahl der Kopien, die in der Belegart gesetzt sind. (Wert 0 wird ignoriert)',14,'D','Y','N','N','N','N','N','N','N','Druck Format - Kopien',TO_TIMESTAMP('2025-07-12 09:57:13.588000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-12T09:57:13.729Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=750259 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-12T09:57:13.732Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583810)
;

-- 2025-07-12T09:57:13.741Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=750259
;

-- 2025-07-12T09:57:13.744Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(750259)
;

-- UI Element: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> advanced edit -> 10 -> advanced edit.Druck - Format
-- Column: I_BPartner.AD_PrintFormat_ID
-- 2025-07-12T09:59:24.406Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,564454,0,441,541262,635279,'F',TO_TIMESTAMP('2025-07-12 09:59:24.273000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Data Print Format','Das Druckformat legt fest, wie die Daten für den Druck aufbereitet werden.','Y','Y','N','Y','N','N','N',0,'Druck - Format',480,0,0,TO_TIMESTAMP('2025-07-12 09:59:24.273000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> advanced edit -> 10 -> advanced edit.Druck Format - Kopien
-- Column: I_BPartner.PrintFormat_DocumentCopies_Override
-- 2025-07-12T09:59:38.232Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,750259,0,441,541262,635280,'F',TO_TIMESTAMP('2025-07-12 09:59:37.946000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Überschreibt die Anzahl der Kopien, die in der Belegart gesetzt sind. (Wert 0 wird ignoriert)','Y','N','N','Y','N','N','N',0,'Druck Format - Kopien',490,0,0,TO_TIMESTAMP('2025-07-12 09:59:37.946000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Element: PrintFormat_DocumentCopies_Override
-- 2025-07-12T10:43:06.070Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-07-12 10:43:06.067000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583810 AND AD_Language='de_DE'
;

-- 2025-07-12T10:43:06.073Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583810,'de_DE')
;

-- 2025-07-12T10:43:06.075Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583810,'de_DE')
;

-- 2025-07-12T11:49:40.093Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583812,0,'PrintFormat_DocBaseType',TO_TIMESTAMP('2025-07-12 11:49:39.982000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','D','Y','Druck Format - Dokument Basis Typ','Druck Format - Dokument Basis Typ',TO_TIMESTAMP('2025-07-12 11:49:39.982000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-12T11:49:40.096Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583812 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: PrintFormat_DocBaseType
-- 2025-07-12T11:50:14.243Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Print Format - Document Base Type', PrintName='Print Format - Document Base Type',Updated=TO_TIMESTAMP('2025-07-12 11:50:14.243000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583812 AND AD_Language='en_US'
;

-- 2025-07-12T11:50:14.245Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-12T11:50:14.505Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583812,'en_US')
;

-- Element: PrintFormat_DocBaseType
-- 2025-07-12T11:50:15.835Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-07-12 11:50:15.835000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583812 AND AD_Language='de_CH'
;

-- 2025-07-12T11:50:15.836Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583812,'de_CH')
;

-- Element: PrintFormat_DocBaseType
-- 2025-07-12T11:50:17.771Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-07-12 11:50:17.771000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583812 AND AD_Language='de_DE'
;

-- 2025-07-12T11:50:17.773Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583812,'de_DE')
;

-- 2025-07-12T11:50:17.775Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583812,'de_DE')
;

-- Column: I_BPartner.PrintFormat_DocBaseType
-- 2025-07-12T11:53:27.665Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590537,583812,0,17,183,533,'XX','PrintFormat_DocBaseType',TO_TIMESTAMP('2025-07-12 11:53:27.543000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','D',0,3,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Druck Format - Dokument Basis Typ',0,0,TO_TIMESTAMP('2025-07-12 11:53:27.543000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-07-12T11:53:27.667Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590537 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-12T11:53:27.671Z
/* DDL */  select update_Column_Translation_From_AD_Element(583812)
;

-- 2025-07-12T14:41:52.875Z
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN PrintFormat_DocBaseType VARCHAR(3)')
;

-- Field: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> Druck Format - Dokument Basis Typ
-- Column: I_BPartner.PrintFormat_DocBaseType
-- 2025-07-12T11:53:43.755Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590537,750261,0,441,TO_TIMESTAMP('2025-07-12 11:53:43.634000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',3,'D','Y','N','N','N','N','N','N','N','Druck Format - Dokument Basis Typ',TO_TIMESTAMP('2025-07-12 11:53:43.634000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-12T11:53:43.758Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=750261 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-12T11:53:43.760Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583812)
;

-- 2025-07-12T11:53:43.764Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=750261
;

-- 2025-07-12T11:53:43.766Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(750261)
;

-- UI Element: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> advanced edit -> 10 -> advanced edit.Druck Format - Dokument Basis Typ
-- Column: I_BPartner.PrintFormat_DocBaseType
-- 2025-07-12T11:54:32.752Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,750261,0,441,541262,635282,'F',TO_TIMESTAMP('2025-07-12 11:54:32.488000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Druck Format - Dokument Basis Typ',510,0,0,TO_TIMESTAMP('2025-07-12 11:54:32.488000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> advanced edit -> 10 -> advanced edit.Druck Format - Kopien
-- Column: I_BPartner.PrintFormat_DocumentCopies_Override
-- 2025-07-12T11:54:43.517Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2025-07-12 11:54:43.517000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=635280
;
-- 2025-07-12T13:32:22.621Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583813,0,'IsSetPrintFormat_C_BPartner_Location',TO_TIMESTAMP('2025-07-12 13:32:22.459000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Print Format Standort setzen','Print Format Standort setzen',TO_TIMESTAMP('2025-07-12 13:32:22.459000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-12T13:32:22.624Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583813 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsSetPrintFormat_C_BPartner_Location
-- 2025-07-12T13:33:01.060Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Set Print Format location', PrintName='Set Print Format location',Updated=TO_TIMESTAMP('2025-07-12 13:33:01.060000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583813 AND AD_Language='en_US'
;

-- 2025-07-12T13:33:01.061Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-12T13:33:01.252Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583813,'en_US')
;

-- Element: IsSetPrintFormat_C_BPartner_Location
-- 2025-07-12T13:33:15.893Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Druck Format Standort setzen', PrintName='Druck Format Standort setzen',Updated=TO_TIMESTAMP('2025-07-12 13:33:15.893000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583813 AND AD_Language='de_CH'
;

-- 2025-07-12T13:33:15.894Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-12T13:33:16.157Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583813,'de_CH')
;

-- Element: IsSetPrintFormat_C_BPartner_Location
-- 2025-07-12T13:33:24.243Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Druck Format Standort setzen', PrintName='Druck Format Standort setzen',Updated=TO_TIMESTAMP('2025-07-12 13:33:24.243000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583813 AND AD_Language='de_DE'
;

-- 2025-07-12T13:33:24.244Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-07-12T13:33:24.784Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583813,'de_DE')
;

-- 2025-07-12T13:33:24.785Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583813,'de_DE')
;

-- Column: I_BPartner.IsSetPrintFormat_C_BPartner_Location
-- 2025-07-12T13:34:14.472Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590538,583813,0,20,533,'XX','IsSetPrintFormat_C_BPartner_Location',TO_TIMESTAMP('2025-07-12 13:34:14.360000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Druck Format Standort setzen',0,0,TO_TIMESTAMP('2025-07-12 13:34:14.360000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-07-12T13:34:14.477Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590538 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-12T13:34:14.483Z
/* DDL */  select update_Column_Translation_From_AD_Element(583813)
;

-- 2025-07-12T13:34:17.025Z
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN IsSetPrintFormat_C_BPartner_Location CHAR(1) DEFAULT ''N'' CHECK (IsSetPrintFormat_C_BPartner_Location IN (''Y'',''N'')) NOT NULL')
;

-- Field: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> Druck Format Standort setzen
-- Column: I_BPartner.IsSetPrintFormat_C_BPartner_Location
-- 2025-07-12T13:34:30.723Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590538,750262,0,441,TO_TIMESTAMP('2025-07-12 13:34:30.604000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Druck Format Standort setzen',TO_TIMESTAMP('2025-07-12 13:34:30.604000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-12T13:34:30.724Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=750262 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- 2025-07-12T13:34:30.725Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583813)
;

-- 2025-07-12T13:34:30.729Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=750262
;

-- 2025-07-12T13:34:30.730Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(750262)
;

-- UI Element: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> advanced edit -> 10 -> advanced edit.Druck Format Standort setzen
-- Column: I_BPartner.IsSetPrintFormat_C_BPartner_Location
-- 2025-07-12T13:35:07.027Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,750262,0,441,541262,635283,'F',TO_TIMESTAMP('2025-07-12 13:35:06.922000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Druck Format Standort setzen',520,0,0,TO_TIMESTAMP('2025-07-12 13:35:06.922000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

