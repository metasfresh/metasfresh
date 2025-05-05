-- 2023-06-23T13:12:39.344Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582467,0,'ProductAcctValue',TO_TIMESTAMP('2023-06-23 16:12:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Product Account Value','Product Account Value',TO_TIMESTAMP('2023-06-23 16:12:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-23T13:12:39.351Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582467 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: I_Inventory.ProductAcctValue
-- Column: I_Inventory.ProductAcctValue
-- 2023-06-23T13:12:55.297Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586849,582467,0,10,572,'ProductAcctValue',TO_TIMESTAMP('2023-06-23 16:12:55','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,250,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Product Account Value',0,0,TO_TIMESTAMP('2023-06-23 16:12:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-06-23T13:12:55.299Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586849 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-23T13:12:55.322Z
/* DDL */  select update_Column_Translation_From_AD_Element(582467) 
;

-- Column: I_Inventory.ProductAcctValue
-- Column: I_Inventory.ProductAcctValue
-- 2023-06-23T13:12:58.820Z
UPDATE AD_Column SET FieldLength=60,Updated=TO_TIMESTAMP('2023-06-23 16:12:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586849
;

-- 2023-06-23T13:13:06.438Z
/* DDL */ SELECT public.db_alter_table('I_Inventory','ALTER TABLE public.I_Inventory ADD COLUMN ProductAcctValue VARCHAR(60)')
;

-- 2023-06-23T13:14:00.490Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582468,0,'IsUpdateQtyBookedFromFactAcct',TO_TIMESTAMP('2023-06-23 16:14:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Update Qty Booked From FactAcct','Update Qty Booked From FactAcct',TO_TIMESTAMP('2023-06-23 16:14:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-23T13:14:00.492Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582468 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: I_Inventory.IsUpdateQtyBookedFromFactAcct
-- Column: I_Inventory.IsUpdateQtyBookedFromFactAcct
-- 2023-06-23T13:14:24.416Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586850,582468,0,20,572,'IsUpdateQtyBookedFromFactAcct',TO_TIMESTAMP('2023-06-23 16:14:24','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Update Qty Booked From FactAcct',0,0,TO_TIMESTAMP('2023-06-23 16:14:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-06-23T13:14:24.418Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586850 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-23T13:14:24.420Z
/* DDL */  select update_Column_Translation_From_AD_Element(582468) 
;

-- 2023-06-23T13:14:27.285Z
/* DDL */ SELECT public.db_alter_table('I_Inventory','ALTER TABLE public.I_Inventory ADD COLUMN IsUpdateQtyBookedFromFactAcct CHAR(1) DEFAULT ''N'' CHECK (IsUpdateQtyBookedFromFactAcct IN (''Y'',''N'')) NOT NULL')
;

-- Field: Import - Warenbestand -> Bestand -> Product Account Value
-- Column: I_Inventory.ProductAcctValue
-- Field: Import - Warenbestand(267,D) -> Bestand(481,D) -> Product Account Value
-- Column: I_Inventory.ProductAcctValue
-- 2023-06-23T13:16:07.756Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586849,716450,0,481,TO_TIMESTAMP('2023-06-23 16:16:07','YYYY-MM-DD HH24:MI:SS'),100,60,'D','Y','N','N','N','N','N','N','N','Product Account Value',TO_TIMESTAMP('2023-06-23 16:16:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-23T13:16:07.764Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=716450 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-23T13:16:07.770Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582467) 
;

-- 2023-06-23T13:16:07.789Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716450
;

-- 2023-06-23T13:16:07.796Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716450)
;

-- Field: Import - Warenbestand -> Bestand -> Update Qty Booked From FactAcct
-- Column: I_Inventory.IsUpdateQtyBookedFromFactAcct
-- Field: Import - Warenbestand(267,D) -> Bestand(481,D) -> Update Qty Booked From FactAcct
-- Column: I_Inventory.IsUpdateQtyBookedFromFactAcct
-- 2023-06-23T13:16:07.902Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586850,716451,0,481,TO_TIMESTAMP('2023-06-23 16:16:07','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Update Qty Booked From FactAcct',TO_TIMESTAMP('2023-06-23 16:16:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-06-23T13:16:07.904Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=716451 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-23T13:16:07.905Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582468) 
;

-- 2023-06-23T13:16:07.907Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716451
;

-- 2023-06-23T13:16:07.908Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716451)
;

-- UI Column: Import - Warenbestand(267,D) -> Bestand(481,D) -> main -> 10
-- UI Element Group: factacct
-- 2023-06-23T13:17:07.757Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542135,550800,TO_TIMESTAMP('2023-06-23 16:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','factacct',20,TO_TIMESTAMP('2023-06-23 16:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Warenbestand -> Bestand.Update Qty Booked From FactAcct
-- Column: I_Inventory.IsUpdateQtyBookedFromFactAcct
-- UI Element: Import - Warenbestand(267,D) -> Bestand(481,D) -> main -> 10 -> factacct.Update Qty Booked From FactAcct
-- Column: I_Inventory.IsUpdateQtyBookedFromFactAcct
-- 2023-06-23T13:17:25.637Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716451,0,481,550800,618085,'F',TO_TIMESTAMP('2023-06-23 16:17:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Update Qty Booked From FactAcct',10,0,0,TO_TIMESTAMP('2023-06-23 16:17:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import - Warenbestand -> Bestand.Product Account Value
-- Column: I_Inventory.ProductAcctValue
-- UI Element: Import - Warenbestand(267,D) -> Bestand(481,D) -> main -> 10 -> factacct.Product Account Value
-- Column: I_Inventory.ProductAcctValue
-- 2023-06-23T13:17:33.064Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716450,0,481,550800,618086,'F',TO_TIMESTAMP('2023-06-23 16:17:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Product Account Value',20,0,0,TO_TIMESTAMP('2023-06-23 16:17:32','YYYY-MM-DD HH24:MI:SS'),100)
;

