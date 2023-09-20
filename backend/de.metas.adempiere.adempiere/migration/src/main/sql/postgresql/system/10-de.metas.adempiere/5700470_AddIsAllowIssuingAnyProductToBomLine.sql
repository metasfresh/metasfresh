-- 2023-08-29T11:43:50.320Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582665,0,'IsAllowIssuingAnyProduct',TO_TIMESTAMP('2023-08-29 14:43:50','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Allow Issuing Any Product','Allow Issuing Any Product',TO_TIMESTAMP('2023-08-29 14:43:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-08-29T11:43:50.322Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582665 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsAllowIssuingAnyProduct
-- 2023-08-29T11:45:49.450Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Erlauben Sie die Ausgabe jedes Produkts', PrintName='Erlauben Sie die Ausgabe jedes Produkts',Updated=TO_TIMESTAMP('2023-08-29 14:45:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582665 AND AD_Language='de_CH'
;

-- 2023-08-29T11:45:49.472Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582665,'de_CH') 
;

-- Element: IsAllowIssuingAnyProduct
-- 2023-08-29T11:45:53.275Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Erlauben Sie die Ausgabe jedes Produkts', PrintName='Erlauben Sie die Ausgabe jedes Produkts',Updated=TO_TIMESTAMP('2023-08-29 14:45:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582665 AND AD_Language='de_DE'
;

-- 2023-08-29T11:45:53.277Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582665,'de_DE') 
;

-- 2023-08-29T11:45:53.279Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582665,'de_DE') 
;

-- Column: PP_Order_BOMLine.IsAllowIssuingAnyProduct
-- Column: PP_Order_BOMLine.IsAllowIssuingAnyProduct
-- 2023-08-29T11:46:05.389Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587343,582665,0,20,53025,'IsAllowIssuingAnyProduct',TO_TIMESTAMP('2023-08-29 14:46:05','YYYY-MM-DD HH24:MI:SS'),100,'N','N','EE01',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'Erlauben Sie die Ausgabe jedes Produkts',0,0,TO_TIMESTAMP('2023-08-29 14:46:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-08-29T11:46:05.390Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587343 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-29T11:46:05.395Z
/* DDL */  select update_Column_Translation_From_AD_Element(582665) 
;

-- 2023-08-29T11:46:08.953Z
/* DDL */ SELECT public.db_alter_table('PP_Order_BOMLine','ALTER TABLE public.PP_Order_BOMLine ADD COLUMN IsAllowIssuingAnyProduct CHAR(1) DEFAULT ''N'' CHECK (IsAllowIssuingAnyProduct IN (''Y'',''N'')) NOT NULL')
;

-- Field: Produktionsauftrag -> Komponenten -> Erlauben Sie die Ausgabe jedes Produkts
-- Column: PP_Order_BOMLine.IsAllowIssuingAnyProduct
-- Field: Produktionsauftrag(53009,EE01) -> Komponenten(53039,EE01) -> Erlauben Sie die Ausgabe jedes Produkts
-- Column: PP_Order_BOMLine.IsAllowIssuingAnyProduct
-- 2023-08-29T11:54:45.660Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587343,720300,0,53039,0,TO_TIMESTAMP('2023-08-29 14:54:45','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Erlauben Sie die Ausgabe jedes Produkts',0,420,0,1,1,TO_TIMESTAMP('2023-08-29 14:54:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-08-29T11:54:45.662Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=720300 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-29T11:54:45.664Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582665) 
;

-- 2023-08-29T11:54:45.673Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720300
;

-- 2023-08-29T11:54:45.675Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720300)
;

-- UI Element: Produktionsauftrag -> Komponenten.Erlauben Sie die Ausgabe jedes Produkts
-- Column: PP_Order_BOMLine.IsAllowIssuingAnyProduct
-- UI Element: Produktionsauftrag(53009,EE01) -> Komponenten(53039,EE01) -> main -> 10 -> default.Erlauben Sie die Ausgabe jedes Produkts
-- Column: PP_Order_BOMLine.IsAllowIssuingAnyProduct
-- 2023-08-29T11:56:02.736Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720300,0,53039,540244,620373,'F',TO_TIMESTAMP('2023-08-29 14:56:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Erlauben Sie die Ausgabe jedes Produkts',410,0,0,TO_TIMESTAMP('2023-08-29 14:56:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produktionsauftrag -> Komponenten.Erlauben Sie die Ausgabe jedes Produkts
-- Column: PP_Order_BOMLine.IsAllowIssuingAnyProduct
-- UI Element: Produktionsauftrag(53009,EE01) -> Komponenten(53039,EE01) -> main -> 10 -> default.Erlauben Sie die Ausgabe jedes Produkts
-- Column: PP_Order_BOMLine.IsAllowIssuingAnyProduct
-- 2023-08-29T11:56:20.943Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2023-08-29 14:56:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=620373
;

-- UI Element: Produktionsauftrag -> Komponenten.Aktiv
-- Column: PP_Order_BOMLine.IsActive
-- UI Element: Produktionsauftrag(53009,EE01) -> Komponenten(53039,EE01) -> main -> 10 -> default.Aktiv
-- Column: PP_Order_BOMLine.IsActive
-- 2023-08-29T11:56:20.952Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2023-08-29 14:56:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542875
;

-- Column: PP_Product_BOMLine.IsAllowIssuingAnyProduct
-- Column: PP_Product_BOMLine.IsAllowIssuingAnyProduct
-- 2023-08-29T13:25:00.707Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587344,582665,0,20,53019,'IsAllowIssuingAnyProduct',TO_TIMESTAMP('2023-08-29 16:24:59','YYYY-MM-DD HH24:MI:SS'),100,'N','N','EE01',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Erlauben Sie die Ausgabe jedes Produkts',0,0,TO_TIMESTAMP('2023-08-29 16:24:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-08-29T13:25:00.709Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587344 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-29T13:25:00.713Z
/* DDL */  select update_Column_Translation_From_AD_Element(582665) 
;

-- 2023-08-29T13:25:02.327Z
/* DDL */ SELECT public.db_alter_table('PP_Product_BOMLine','ALTER TABLE public.PP_Product_BOMLine ADD COLUMN IsAllowIssuingAnyProduct CHAR(1) DEFAULT ''N'' CHECK (IsAllowIssuingAnyProduct IN (''Y'',''N'')) NOT NULL')
;

-- Element: IsAllowIssuingAnyProduct
-- 2023-08-29T13:26:37.684Z
UPDATE AD_Element_Trl SET Name='Jedes Produkt zuteilbar', PrintName='Jedes Produkt zuteilbar',Updated=TO_TIMESTAMP('2023-08-29 16:26:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582665 AND AD_Language='de_CH'
;

-- 2023-08-29T13:26:37.687Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582665,'de_CH') 
;

-- Element: IsAllowIssuingAnyProduct
-- 2023-08-29T13:26:41.417Z
UPDATE AD_Element_Trl SET Name='Jedes Produkt zuteilbar', PrintName='Jedes Produkt zuteilbar',Updated=TO_TIMESTAMP('2023-08-29 16:26:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582665 AND AD_Language='de_DE'
;

-- 2023-08-29T13:26:41.422Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582665,'de_DE') 
;

-- 2023-08-29T13:26:41.425Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582665,'de_DE') 
;

-- Element: IsAllowIssuingAnyProduct
-- 2023-08-29T13:26:52.228Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Issue any product', PrintName='Issue any product',Updated=TO_TIMESTAMP('2023-08-29 16:26:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582665 AND AD_Language='en_US'
;

-- 2023-08-29T13:26:52.230Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582665,'en_US') 
;

-- Field: Stücklistenkonfiguration Version -> Stücklistenbestandteile -> Jedes Produkt zuteilbar
-- Column: PP_Product_BOMLine.IsAllowIssuingAnyProduct
-- Field: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> Jedes Produkt zuteilbar
-- Column: PP_Product_BOMLine.IsAllowIssuingAnyProduct
-- 2023-08-29T13:27:40.894Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587344,720301,0,53029,0,TO_TIMESTAMP('2023-08-29 16:27:40','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Jedes Produkt zuteilbar',0,210,0,1,1,TO_TIMESTAMP('2023-08-29 16:27:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-08-29T13:27:40.895Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=720301 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-29T13:27:40.896Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582665) 
;

-- 2023-08-29T13:27:40.899Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720301
;

-- 2023-08-29T13:27:40.900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720301)
;

-- UI Element: Stücklistenkonfiguration Version -> Stücklistenbestandteile.Jedes Produkt zuteilbar
-- Column: PP_Product_BOMLine.IsAllowIssuingAnyProduct
-- UI Element: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> default.Jedes Produkt zuteilbar
-- Column: PP_Product_BOMLine.IsAllowIssuingAnyProduct
-- 2023-08-29T13:28:46.413Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720301,0,53029,540444,620374,'F',TO_TIMESTAMP('2023-08-29 16:28:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Jedes Produkt zuteilbar',175,0,0,TO_TIMESTAMP('2023-08-29 16:28:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Stücklistenkonfiguration Version -> Stücklistenbestandteile.Jedes Produkt zuteilbar
-- Column: PP_Product_BOMLine.IsAllowIssuingAnyProduct
-- UI Element: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> default.Jedes Produkt zuteilbar
-- Column: PP_Product_BOMLine.IsAllowIssuingAnyProduct
-- 2023-08-29T13:28:56.327Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2023-08-29 16:28:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=620374
;

-- UI Element: Stücklistenkonfiguration Version -> Stücklistenbestandteile.Aktiv
-- Column: PP_Product_BOMLine.IsActive
-- UI Element: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> default.Aktiv
-- Column: PP_Product_BOMLine.IsActive
-- 2023-08-29T13:28:56.335Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2023-08-29 16:28:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544405
;

-- UI Element: Stücklistenkonfiguration Version -> Stücklistenbestandteile.Sektion
-- Column: PP_Product_BOMLine.AD_Org_ID
-- UI Element: Stücklistenkonfiguration Version(53006,EE01) -> Stücklistenbestandteile(53029,EE01) -> main -> 10 -> default.Sektion
-- Column: PP_Product_BOMLine.AD_Org_ID
-- 2023-08-29T13:28:56.343Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2023-08-29 16:28:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547353
;

-- 2023-08-29T13:29:25.190Z
INSERT INTO t_alter_column values('pp_order_bomline','IsAllowIssuingAnyProduct','CHAR(1)',null,'N')
;

-- 2023-08-29T13:29:25.402Z
UPDATE PP_Order_BOMLine SET IsAllowIssuingAnyProduct='N' WHERE IsAllowIssuingAnyProduct IS NULL
;

