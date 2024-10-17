-- 2024-08-29T12:00:43.793Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583236,0,'IsAllowSkippingRejectedReason',TO_TIMESTAMP('2024-08-29 15:00:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Allow picking with no rejected qty reason','Allow picking with no rejected qty reason',TO_TIMESTAMP('2024-08-29 15:00:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-08-29T12:00:43.798Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583236 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: MobileUI_UserProfile_Picking.IsAllowSkippingRejectedReason
-- Column: MobileUI_UserProfile_Picking.IsAllowSkippingRejectedReason
-- 2024-08-29T12:01:00.662Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588938,583236,0,20,542373,'XX','IsAllowSkippingRejectedReason',TO_TIMESTAMP('2024-08-29 15:01:00','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Allow picking with no rejected qty reason',0,0,TO_TIMESTAMP('2024-08-29 15:01:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-08-29T12:01:00.675Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588938 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-29T12:01:00.688Z
/* DDL */  select update_Column_Translation_From_AD_Element(583236) 
;

-- 2024-08-29T12:01:02.943Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking','ALTER TABLE public.MobileUI_UserProfile_Picking ADD COLUMN IsAllowSkippingRejectedReason CHAR(1) DEFAULT ''N'' CHECK (IsAllowSkippingRejectedReason IN (''Y'',''N'')) NOT NULL')
;

-- Element: IsConsiderSalesOrderCapacity
-- 2024-08-29T12:09:17.293Z
UPDATE AD_Element_Trl SET Name='Kundenauftragskapazität berücksichtigen', PrintName='Kundenauftragskapazität berücksichtigen',Updated=TO_TIMESTAMP('2024-08-29 15:09:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583235 AND AD_Language='de_CH'
;

-- 2024-08-29T12:09:17.296Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583235,'de_CH') 
;

-- Element: IsConsiderSalesOrderCapacity
-- 2024-08-29T12:09:21.129Z
UPDATE AD_Element_Trl SET Name='Kundenauftragskapazität berücksichtigen', PrintName='Kundenauftragskapazität berücksichtigen',Updated=TO_TIMESTAMP('2024-08-29 15:09:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583235 AND AD_Language='de_DE'
;

-- 2024-08-29T12:09:21.131Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583235,'de_DE') 
;

-- 2024-08-29T12:09:21.136Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583235,'de_DE') 
;

-- Element: IsConsiderSalesOrderCapacity
-- 2024-08-29T12:09:50.099Z
UPDATE AD_Element_Trl SET Name='Kundenauftragskapazität berücksichtigen', PrintName='Kundenauftragskapazität berücksichtigen',Updated=TO_TIMESTAMP('2024-08-29 15:09:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583235 AND AD_Language='fr_CH'
;

-- 2024-08-29T12:09:50.101Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583235,'fr_CH') 
;

-- Element: IsCatchWeightTUPickingEnabled
-- 2024-08-29T12:11:27.919Z
UPDATE AD_Element_Trl SET Name='Erlauben Sie das Pflücken von TU mit Fanggewicht', PrintName='Erlauben Sie das Pflücken von TU mit Fanggewicht',Updated=TO_TIMESTAMP('2024-08-29 15:11:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583234 AND AD_Language='de_CH'
;

-- 2024-08-29T12:11:27.921Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583234,'de_CH') 
;

-- Element: IsCatchWeightTUPickingEnabled
-- 2024-08-29T12:11:31.485Z
UPDATE AD_Element_Trl SET Name='Erlauben Sie das Pflücken von TU mit Fanggewicht', PrintName='Erlauben Sie das Pflücken von TU mit Fanggewicht',Updated=TO_TIMESTAMP('2024-08-29 15:11:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583234 AND AD_Language='de_DE'
;

-- 2024-08-29T12:11:31.486Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583234,'de_DE') 
;

-- 2024-08-29T12:11:31.488Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583234,'de_DE') 
;

-- Element: IsCatchWeightTUPickingEnabled
-- 2024-08-29T12:11:42.906Z
UPDATE AD_Element_Trl SET Name='Erlauben Sie das Pflücken von TU mit Fanggewicht', PrintName='Erlauben Sie das Pflücken von TU mit Fanggewicht',Updated=TO_TIMESTAMP('2024-08-29 15:11:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583234 AND AD_Language='fr_CH'
;

-- 2024-08-29T12:11:42.908Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583234,'fr_CH') 
;

-- Element: IsAllowSkippingRejectedReason
-- 2024-08-29T12:13:46.447Z
UPDATE AD_Element_Trl SET Name='Kommissionierung ohne Grund für zurückgewiesene Menge zulassen', PrintName='Kommissionierung ohne Grund für zurückgewiesene Menge zulassen',Updated=TO_TIMESTAMP('2024-08-29 15:13:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583236 AND AD_Language='de_CH'
;

-- 2024-08-29T12:13:46.450Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583236,'de_CH') 
;

-- Element: IsAllowSkippingRejectedReason
-- 2024-08-29T12:14:07.507Z
UPDATE AD_Element_Trl SET Name='Kommissionierung ohne Grund für zurückgewiesene Menge', PrintName='Kommissionierung ohne Grund für zurückgewiesene Menge',Updated=TO_TIMESTAMP('2024-08-29 15:14:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583236 AND AD_Language='de_DE'
;

-- 2024-08-29T12:14:07.508Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583236,'de_DE') 
;

-- 2024-08-29T12:14:07.513Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583236,'de_DE') 
;

-- Element: IsAllowSkippingRejectedReason
-- 2024-08-29T12:14:15.205Z
UPDATE AD_Element_Trl SET Name='Kommissionierung ohne Grund für zurückgewiesene Menge', PrintName='Kommissionierung ohne Grund für zurückgewiesene Menge',Updated=TO_TIMESTAMP('2024-08-29 15:14:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583236 AND AD_Language='de_CH'
;

-- 2024-08-29T12:14:15.207Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583236,'de_CH') 
;

-- Element: IsAllowSkippingRejectedReason
-- 2024-08-29T12:14:29.418Z
UPDATE AD_Element_Trl SET Name='Kommissionierung ohne Grund für zurückgewiesene Menge', PrintName='Kommissionierung ohne Grund für zurückgewiesene Menge',Updated=TO_TIMESTAMP('2024-08-29 15:14:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583236 AND AD_Language='fr_CH'
;

-- 2024-08-29T12:14:29.438Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583236,'fr_CH') 
;

-- Field: Mobile UI Kommissionierprofil -> Mobile UI Kommissionierprofil -> Erlauben Sie das Pflücken von TU mit Fanggewicht
-- Column: MobileUI_UserProfile_Picking.IsCatchWeightTUPickingEnabled
-- Field: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> Erlauben Sie das Pflücken von TU mit Fanggewicht
-- Column: MobileUI_UserProfile_Picking.IsCatchWeightTUPickingEnabled
-- 2024-08-29T12:15:56.975Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588936,729842,0,547258,TO_TIMESTAMP('2024-08-29 15:15:56','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Erlauben Sie das Pflücken von TU mit Fanggewicht',TO_TIMESTAMP('2024-08-29 15:15:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-08-29T12:15:56.977Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729842 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-29T12:15:56.982Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583234) 
;

-- 2024-08-29T12:15:57.009Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729842
;

-- 2024-08-29T12:15:57.017Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729842)
;

-- Field: Mobile UI Kommissionierprofil -> Mobile UI Kommissionierprofil -> Kommissionierung ohne Grund für zurückgewiesene Menge
-- Column: MobileUI_UserProfile_Picking.IsAllowSkippingRejectedReason
-- Field: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> Kommissionierung ohne Grund für zurückgewiesene Menge
-- Column: MobileUI_UserProfile_Picking.IsAllowSkippingRejectedReason
-- 2024-08-29T12:16:09.014Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588938,729843,0,547258,TO_TIMESTAMP('2024-08-29 15:16:08','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Kommissionierung ohne Grund für zurückgewiesene Menge',TO_TIMESTAMP('2024-08-29 15:16:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-08-29T12:16:09.016Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729843 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-29T12:16:09.018Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583236) 
;

-- 2024-08-29T12:16:09.022Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729843
;

-- 2024-08-29T12:16:09.026Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729843)
;

-- Field: Mobile UI Kommissionierprofil -> Mobile UI Kommissionierprofil -> Kundenauftragskapazität berücksichtigen
-- Column: MobileUI_UserProfile_Picking.IsConsiderSalesOrderCapacity
-- Field: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> Kundenauftragskapazität berücksichtigen
-- Column: MobileUI_UserProfile_Picking.IsConsiderSalesOrderCapacity
-- 2024-08-29T12:16:20.750Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588937,729844,0,547258,TO_TIMESTAMP('2024-08-29 15:16:20','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Kundenauftragskapazität berücksichtigen',TO_TIMESTAMP('2024-08-29 15:16:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-08-29T12:16:20.753Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729844 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-29T12:16:20.755Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583235) 
;

-- 2024-08-29T12:16:20.763Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729844
;

-- 2024-08-29T12:16:20.771Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729844)
;

-- UI Element: Mobile UI Kommissionierprofil -> Mobile UI Kommissionierprofil.Erlauben Sie das Pflücken von TU mit Fanggewicht
-- Column: MobileUI_UserProfile_Picking.IsCatchWeightTUPickingEnabled
-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 20 -> flags.Erlauben Sie das Pflücken von TU mit Fanggewicht
-- Column: MobileUI_UserProfile_Picking.IsCatchWeightTUPickingEnabled
-- 2024-08-29T12:16:57.273Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,729842,0,547258,625301,551252,'F',TO_TIMESTAMP('2024-08-29 15:16:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Erlauben Sie das Pflücken von TU mit Fanggewicht',60,0,0,TO_TIMESTAMP('2024-08-29 15:16:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Mobile UI Kommissionierprofil -> Mobile UI Kommissionierprofil.Kundenauftragskapazität berücksichtigen
-- Column: MobileUI_UserProfile_Picking.IsConsiderSalesOrderCapacity
-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 20 -> flags.Kundenauftragskapazität berücksichtigen
-- Column: MobileUI_UserProfile_Picking.IsConsiderSalesOrderCapacity
-- 2024-08-29T12:17:10.096Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,729844,0,547258,625302,551252,'F',TO_TIMESTAMP('2024-08-29 15:17:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Kundenauftragskapazität berücksichtigen',70,0,0,TO_TIMESTAMP('2024-08-29 15:17:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Mobile UI Kommissionierprofil -> Mobile UI Kommissionierprofil.Kundenauftragskapazität berücksichtigen
-- Column: MobileUI_UserProfile_Picking.IsConsiderSalesOrderCapacity
-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 20 -> flags.Kundenauftragskapazität berücksichtigen
-- Column: MobileUI_UserProfile_Picking.IsConsiderSalesOrderCapacity
-- 2024-08-29T12:17:19.860Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,729844,0,547258,625303,551252,'F',TO_TIMESTAMP('2024-08-29 15:17:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Kundenauftragskapazität berücksichtigen',80,0,0,TO_TIMESTAMP('2024-08-29 15:17:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Mobile UI Kommissionierprofil -> Mobile UI Kommissionierprofil.Kommissionierung ohne Grund für zurückgewiesene Menge
-- Column: MobileUI_UserProfile_Picking.IsAllowSkippingRejectedReason
-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 20 -> flags.Kommissionierung ohne Grund für zurückgewiesene Menge
-- Column: MobileUI_UserProfile_Picking.IsAllowSkippingRejectedReason
-- 2024-08-29T12:18:34.292Z
UPDATE AD_UI_Element SET AD_Field_ID=729843, Name='Kommissionierung ohne Grund für zurückgewiesene Menge',Updated=TO_TIMESTAMP('2024-08-29 15:18:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625303
;

