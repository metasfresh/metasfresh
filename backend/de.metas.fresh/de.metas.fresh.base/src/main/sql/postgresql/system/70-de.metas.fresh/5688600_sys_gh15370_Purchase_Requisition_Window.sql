-- 2023-05-19T17:40:01.061691Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582352,0,'IsNewSupplier',TO_TIMESTAMP('2023-05-19 18:40:00.629','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Neuer Lieferant','Neuer Lieferant',TO_TIMESTAMP('2023-05-19 18:40:00.629','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-19T17:40:01.379737500Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582352 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsNewSupplier
-- 2023-05-19T17:40:19.149082400Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='New Supplier', PrintName='New Supplier',Updated=TO_TIMESTAMP('2023-05-19 18:40:19.148','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582352 AND AD_Language='de_DE'
;

-- 2023-05-19T17:40:19.150241400Z
UPDATE AD_Element SET Name='New Supplier', PrintName='New Supplier' WHERE AD_Element_ID=582352
;

-- 2023-05-19T17:40:19.456031100Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582352,'de_DE') 
;

-- 2023-05-19T17:40:19.461226800Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582352,'de_DE') 
;

-- Column: M_RequisitionLine.IsNewSupplier
-- 2023-05-19T17:40:31.752359600Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586676,582352,0,20,703,'IsNewSupplier',TO_TIMESTAMP('2023-05-19 18:40:31.464','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'New Supplier',0,0,TO_TIMESTAMP('2023-05-19 18:40:31.464','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-19T17:40:31.754972300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586676 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-19T17:40:32.179574Z
/* DDL */  select update_Column_Translation_From_AD_Element(582352) 
;

-- 2023-05-19T17:40:34.397730500Z
/* DDL */ SELECT public.db_alter_table('M_RequisitionLine','ALTER TABLE public.M_RequisitionLine ADD COLUMN IsNewSupplier CHAR(1) DEFAULT ''N'' CHECK (IsNewSupplier IN (''Y'',''N'')) NOT NULL')
;

-- Field: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> New Supplier
-- Column: M_RequisitionLine.IsNewSupplier
-- 2023-05-19T17:41:26.932515100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586676,715379,0,642,0,TO_TIMESTAMP('2023-05-19 18:41:26.593','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','New Supplier',0,140,0,1,1,TO_TIMESTAMP('2023-05-19 18:41:26.593','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-19T17:41:26.935680200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715379 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-19T17:41:26.937879100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582352) 
;

-- 2023-05-19T17:41:26.947187Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715379
;

-- 2023-05-19T17:41:26.948881300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715379)
;

-- Tab: Bestellanforderung(322,D) -> Bedarfs-Position(642,D)
-- UI Section: main
-- 2023-05-19T17:42:50.787297300Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,642,545584,TO_TIMESTAMP('2023-05-19 18:42:50.545','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-05-19 18:42:50.545','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2023-05-19T17:42:50.788911800Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545584 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main
-- UI Column: 10
-- 2023-05-19T17:42:57.201067900Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546826,545584,TO_TIMESTAMP('2023-05-19 18:42:56.941','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-05-19 18:42:56.941','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10
-- UI Element Group: main
-- 2023-05-19T17:44:00.101598600Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546826,550704,TO_TIMESTAMP('2023-05-19 18:43:59.775','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','main',10,'primary',TO_TIMESTAMP('2023-05-19 18:43:59.775','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.Zeile Nr.
-- Column: M_RequisitionLine.Line
-- 2023-05-19T17:44:15.547745400Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10042,0,642,550704,617472,'F',TO_TIMESTAMP('2023-05-19 18:44:15.289','YYYY-MM-DD HH24:MI:SS.US'),100,'Einzelne Zeile in dem Dokument','Indicates the unique line for a document.  It will also control the display order of the lines within a document.','Y','N','N','Y','N','N','N',0,'Zeile Nr.',10,0,0,TO_TIMESTAMP('2023-05-19 18:44:15.289','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.Geschäftspartner
-- Column: M_RequisitionLine.C_BPartner_ID
-- 2023-05-19T17:44:27.284135Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,56715,0,642,550704,617473,'F',TO_TIMESTAMP('2023-05-19 18:44:27.024','YYYY-MM-DD HH24:MI:SS.US'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','N','N',0,'Geschäftspartner',20,0,0,TO_TIMESTAMP('2023-05-19 18:44:27.024','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.Produkt
-- Column: M_RequisitionLine.M_Product_ID
-- 2023-05-19T17:44:37.639706700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10043,0,642,550704,617474,'F',TO_TIMESTAMP('2023-05-19 18:44:37.227','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','N','N',0,'Produkt',30,0,0,TO_TIMESTAMP('2023-05-19 18:44:37.227','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.Maßeinheit
-- Column: M_RequisitionLine.C_UOM_ID
-- 2023-05-19T17:44:48.828171500Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57388,0,642,550704,617475,'F',TO_TIMESTAMP('2023-05-19 18:44:48.567','YYYY-MM-DD HH24:MI:SS.US'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','Y','N','N','N',0,'Maßeinheit',40,0,0,TO_TIMESTAMP('2023-05-19 18:44:48.567','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.Menge
-- Column: M_RequisitionLine.Qty
-- 2023-05-19T17:44:57.056401300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10037,0,642,550704,617476,'F',TO_TIMESTAMP('2023-05-19 18:44:56.74','YYYY-MM-DD HH24:MI:SS.US'),100,'Menge','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','N','Y','N','N','N',0,'Menge',50,0,0,TO_TIMESTAMP('2023-05-19 18:44:56.74','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.Einzelpreis
-- Column: M_RequisitionLine.PriceActual
-- 2023-05-19T17:45:05.691670300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10034,0,642,550704,617477,'F',TO_TIMESTAMP('2023-05-19 18:45:05.396','YYYY-MM-DD HH24:MI:SS.US'),100,'Effektiver Preis','Der Einzelpreis oder Effektive Preis bezeichnet den Preis für das Produkt in Ausgangswährung.','Y','N','N','Y','N','N','N',0,'Einzelpreis',60,0,0,TO_TIMESTAMP('2023-05-19 18:45:05.396','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.Beschreibung
-- Column: M_RequisitionLine.Description
-- 2023-05-19T17:45:15.382374700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10036,0,642,550704,617478,'F',TO_TIMESTAMP('2023-05-19 18:45:15.091','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Beschreibung',70,0,0,TO_TIMESTAMP('2023-05-19 18:45:15.091','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.Zeilennetto
-- Column: M_RequisitionLine.LineNetAmt
-- 2023-05-19T17:45:24.370596100Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10035,0,642,550704,617479,'F',TO_TIMESTAMP('2023-05-19 18:45:24.082','YYYY-MM-DD HH24:MI:SS.US'),100,'Nettowert Zeile (Menge * Einzelpreis) ohne Fracht und Gebühren','Indicates the extended line amount based on the quantity and the actual price.  Any additional charges or freight are not included.  The Amount may or may not include tax.  If the price list is inclusive tax, the line amount is the same as the line total.','Y','N','N','Y','N','N','N',0,'Zeilennetto',80,0,0,TO_TIMESTAMP('2023-05-19 18:45:24.082','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.Bestellposition
-- Column: M_RequisitionLine.C_OrderLine_ID
-- 2023-05-19T17:45:33.381943300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,10196,0,642,550704,617480,'F',TO_TIMESTAMP('2023-05-19 18:45:33.058','YYYY-MM-DD HH24:MI:SS.US'),100,'Bestellposition','"Bestellposition" bezeichnet eine einzelne Position in einem Auftrag.','Y','N','N','Y','N','N','N',0,'Bestellposition',90,0,0,TO_TIMESTAMP('2023-05-19 18:45:33.058','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Element: IsNewSupplier
-- 2023-05-19T17:45:55.934120700Z
UPDATE AD_Element_Trl SET Name='Neuer Lieferant', PrintName='Neuer Lieferant',Updated=TO_TIMESTAMP('2023-05-19 18:45:55.933','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582352 AND AD_Language='de_DE'
;

-- 2023-05-19T17:45:55.935168100Z
UPDATE AD_Element SET Name='Neuer Lieferant', PrintName='Neuer Lieferant' WHERE AD_Element_ID=582352
;

-- 2023-05-19T17:45:56.263729Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582352,'de_DE') 
;

-- 2023-05-19T17:45:56.264775800Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582352,'de_DE') 
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.Neuer Lieferant
-- Column: M_RequisitionLine.IsNewSupplier
-- 2023-05-19T17:46:05.867691100Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715379,0,642,550704,617481,'F',TO_TIMESTAMP('2023-05-19 18:46:05.32','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Neuer Lieferant',100,0,0,TO_TIMESTAMP('2023-05-19 18:46:05.32','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-19T17:46:37.451023300Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582353,0,'NewSupplierName',TO_TIMESTAMP('2023-05-19 18:46:37.134','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Neuer Lieferant Name','Neuer Lieferant Name',TO_TIMESTAMP('2023-05-19 18:46:37.134','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-19T17:46:37.452622Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582353 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: NewSupplierName
-- 2023-05-19T17:46:52.611567900Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='New Supplier Name', PrintName='New Supplier Name',Updated=TO_TIMESTAMP('2023-05-19 18:46:52.611','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582353 AND AD_Language='en_US'
;

-- 2023-05-19T17:46:52.614264300Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582353,'en_US') 
;

-- Column: M_RequisitionLine.NewSupplierName
-- 2023-05-19T17:47:09.831911700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586677,582353,0,14,703,'NewSupplierName',TO_TIMESTAMP('2023-05-19 18:47:09.335','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,2000,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Neuer Lieferant Name',0,0,TO_TIMESTAMP('2023-05-19 18:47:09.335','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-19T17:47:09.834018800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586677 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-19T17:47:10.272274800Z
/* DDL */  select update_Column_Translation_From_AD_Element(582353) 
;

-- 2023-05-19T17:47:11.728156400Z
/* DDL */ SELECT public.db_alter_table('M_RequisitionLine','ALTER TABLE public.M_RequisitionLine ADD COLUMN NewSupplierName VARCHAR(2000)')
;

-- Field: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> Neuer Lieferant Name
-- Column: M_RequisitionLine.NewSupplierName
-- 2023-05-19T17:47:30.585563300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586677,715380,0,642,0,TO_TIMESTAMP('2023-05-19 18:47:30.329','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Neuer Lieferant Name',0,150,0,1,1,TO_TIMESTAMP('2023-05-19 18:47:30.329','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-19T17:47:30.587127200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715380 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-19T17:47:30.588171Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582353) 
;

-- 2023-05-19T17:47:30.591397100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715380
;

-- 2023-05-19T17:47:30.592443900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715380)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.Neuer Lieferant Name
-- Column: M_RequisitionLine.NewSupplierName
-- 2023-05-19T17:47:42.012040900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715380,0,642,550704,617482,'F',TO_TIMESTAMP('2023-05-19 18:47:41.763','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Neuer Lieferant Name',110,0,0,TO_TIMESTAMP('2023-05-19 18:47:41.763','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-19T17:48:55.987990200Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582354,0,'IsVendorApproved',TO_TIMESTAMP('2023-05-19 18:48:55.713','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Lieferant genehmigt','Lieferant genehmigt',TO_TIMESTAMP('2023-05-19 18:48:55.713','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-19T17:48:55.990125200Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582354 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsVendorApproved
-- 2023-05-19T17:49:21.961351100Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Supplier approved', PrintName='Supplier approved',Updated=TO_TIMESTAMP('2023-05-19 18:49:21.961','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582354 AND AD_Language='en_US'
;

-- 2023-05-19T17:49:21.963422300Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582354,'en_US') 
;

-- Column: M_RequisitionLine.IsVendorApproved
-- 2023-05-19T17:49:47.736517600Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586678,582354,0,20,703,'IsVendorApproved',TO_TIMESTAMP('2023-05-19 18:49:47.453','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Lieferant genehmigt',0,0,TO_TIMESTAMP('2023-05-19 18:49:47.453','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-19T17:49:47.738124300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586678 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-19T17:49:48.168178800Z
/* DDL */  select update_Column_Translation_From_AD_Element(582354) 
;

-- 2023-05-19T17:49:50.170745700Z
/* DDL */ SELECT public.db_alter_table('M_RequisitionLine','ALTER TABLE public.M_RequisitionLine ADD COLUMN IsVendorApproved CHAR(1) DEFAULT ''N'' CHECK (IsVendorApproved IN (''Y'',''N'')) NOT NULL')
;

-- Field: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> Lieferant genehmigt
-- Column: M_RequisitionLine.IsVendorApproved
-- 2023-05-19T17:50:05.316818900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586678,715381,0,642,0,TO_TIMESTAMP('2023-05-19 18:50:04.344','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Lieferant genehmigt',0,160,0,1,1,TO_TIMESTAMP('2023-05-19 18:50:04.344','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-19T17:50:05.317869900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715381 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-19T17:50:05.319998400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582354) 
;

-- 2023-05-19T17:50:05.323751Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715381
;

-- 2023-05-19T17:50:05.324804400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715381)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.Lieferant genehmigt
-- Column: M_RequisitionLine.IsVendorApproved
-- 2023-05-19T17:50:14.693538100Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715381,0,642,550704,617483,'F',TO_TIMESTAMP('2023-05-19 18:50:14.433','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Lieferant genehmigt',120,0,0,TO_TIMESTAMP('2023-05-19 18:50:14.433','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-19T17:53:47.237207900Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582355,0,'IsNewSupplierAddress',TO_TIMESTAMP('2023-05-19 18:53:46.966','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Adresse des neuen Lieferanten','Adresse des neuen Lieferanten',TO_TIMESTAMP('2023-05-19 18:53:46.966','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-19T17:53:47.238817600Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582355 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsNewSupplierAddress
-- 2023-05-19T17:54:00.480644800Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='New Supplier address', PrintName='New Supplier address',Updated=TO_TIMESTAMP('2023-05-19 18:54:00.48','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582355 AND AD_Language='en_US'
;

-- 2023-05-19T17:54:00.483356800Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582355,'en_US') 
;

-- Column: M_RequisitionLine.IsNewSupplierAddress
-- 2023-05-19T17:54:39.344369500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586679,582355,0,20,703,'IsNewSupplierAddress',TO_TIMESTAMP('2023-05-19 18:54:38.913','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Adresse des neuen Lieferanten',0,0,TO_TIMESTAMP('2023-05-19 18:54:38.913','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-19T17:54:39.345946400Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586679 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-19T17:54:39.798928100Z
/* DDL */  select update_Column_Translation_From_AD_Element(582355) 
;

-- 2023-05-19T17:54:40.902801100Z
/* DDL */ SELECT public.db_alter_table('M_RequisitionLine','ALTER TABLE public.M_RequisitionLine ADD COLUMN IsNewSupplierAddress CHAR(1) DEFAULT ''N'' CHECK (IsNewSupplierAddress IN (''Y'',''N'')) NOT NULL')
;

-- Field: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> Adresse des neuen Lieferanten
-- Column: M_RequisitionLine.IsNewSupplierAddress
-- 2023-05-19T17:55:00.392850900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586679,715382,0,642,0,TO_TIMESTAMP('2023-05-19 18:55:00.154','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Adresse des neuen Lieferanten',0,170,0,1,1,TO_TIMESTAMP('2023-05-19 18:55:00.154','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-19T17:55:00.394463500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715382 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-19T17:55:00.396055Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582355) 
;

-- 2023-05-19T17:55:00.398877700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715382
;

-- 2023-05-19T17:55:00.399387800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715382)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.Adresse des neuen Lieferanten
-- Column: M_RequisitionLine.IsNewSupplierAddress
-- 2023-05-19T18:00:45.565184100Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715382,0,642,550704,617484,'F',TO_TIMESTAMP('2023-05-19 19:00:45.267','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Adresse des neuen Lieferanten',130,0,0,TO_TIMESTAMP('2023-05-19 19:00:45.267','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: M_RequisitionLine.C_BPartner_Location_ID
-- 2023-05-19T18:02:10.577996Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586680,189,0,19,703,'C_BPartner_Location_ID',TO_TIMESTAMP('2023-05-19 19:02:10.318','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Identifiziert die (Liefer-) Adresse des Geschäftspartners','D',0,10,'Identifiziert die Adresse des Geschäftspartners','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Standort',0,0,TO_TIMESTAMP('2023-05-19 19:02:10.318','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-19T18:02:10.580138800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586680 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-19T18:02:11.010571100Z
/* DDL */  select update_Column_Translation_From_AD_Element(189) 
;

-- 2023-05-19T18:02:12.888874300Z
/* DDL */ SELECT public.db_alter_table('M_RequisitionLine','ALTER TABLE public.M_RequisitionLine ADD COLUMN C_BPartner_Location_ID NUMERIC(10)')
;

-- 2023-05-19T18:02:12.939952Z
ALTER TABLE M_RequisitionLine ADD CONSTRAINT CBPartnerLocation_MRequisitionLine FOREIGN KEY (C_BPartner_Location_ID) REFERENCES public.C_BPartner_Location DEFERRABLE INITIALLY DEFERRED
;

-- Field: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> Standort
-- Column: M_RequisitionLine.C_BPartner_Location_ID
-- 2023-05-19T18:02:45.918107700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586680,715383,0,642,0,TO_TIMESTAMP('2023-05-19 19:02:44.355','YYYY-MM-DD HH24:MI:SS.US'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',0,'D','Identifiziert die Adresse des Geschäftspartners',0,'Y','Y','Y','N','N','N','N','N','Standort',0,180,0,1,1,TO_TIMESTAMP('2023-05-19 19:02:44.355','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-19T18:02:45.919677500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715383 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-19T18:02:45.921256500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(189) 
;

-- 2023-05-19T18:02:45.945814600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715383
;

-- 2023-05-19T18:02:45.946893700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715383)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.Standort
-- Column: M_RequisitionLine.C_BPartner_Location_ID
-- 2023-05-19T18:03:14.117599200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715383,0,642,550704,617485,'F',TO_TIMESTAMP('2023-05-19 19:03:13.837','YYYY-MM-DD HH24:MI:SS.US'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners','Identifiziert die Adresse des Geschäftspartners','Y','N','N','Y','N','N','N',0,'Standort',140,0,0,TO_TIMESTAMP('2023-05-19 19:03:13.837','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-19T18:04:22.064550200Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582356,0,'NewSupplierAddress',TO_TIMESTAMP('2023-05-19 19:04:21.798','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Neue Lieferantenadresse Details','Neue Lieferantenadresse Details',TO_TIMESTAMP('2023-05-19 19:04:21.798','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-19T18:04:22.066117Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582356 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: NewSupplierAddress
-- 2023-05-19T18:04:34.642362700Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='New Supplier Address Details', PrintName='New Supplier Address Details',Updated=TO_TIMESTAMP('2023-05-19 19:04:34.642','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582356 AND AD_Language='en_US'
;

-- 2023-05-19T18:04:34.644510200Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582356,'en_US') 
;

-- Column: M_RequisitionLine.NewSupplierAddress
-- 2023-05-19T18:04:55.183014800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586681,582356,0,14,703,'NewSupplierAddress',TO_TIMESTAMP('2023-05-19 19:04:54.861','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Neue Lieferantenadresse Details',0,0,TO_TIMESTAMP('2023-05-19 19:04:54.861','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-19T18:04:55.184590100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586681 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-19T18:04:55.648175400Z
/* DDL */  select update_Column_Translation_From_AD_Element(582356) 
;

-- 2023-05-19T18:04:57.346800100Z
/* DDL */ SELECT public.db_alter_table('M_RequisitionLine','ALTER TABLE public.M_RequisitionLine ADD COLUMN NewSupplierAddress VARCHAR(2000)')
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.Standort
-- Column: M_RequisitionLine.C_BPartner_Location_ID
-- 2023-05-19T18:06:44.078386400Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715383,0,642,550704,617486,'F',TO_TIMESTAMP('2023-05-19 19:06:43.813','YYYY-MM-DD HH24:MI:SS.US'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners','Identifiziert die Adresse des Geschäftspartners','Y','N','N','Y','N','N','N',0,'Standort',150,0,0,TO_TIMESTAMP('2023-05-19 19:06:43.813','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.Standort
-- Column: M_RequisitionLine.C_BPartner_Location_ID
-- 2023-05-19T18:07:16.041280200Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=617486
;

-- Field: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> Neue Lieferantenadresse Details
-- Column: M_RequisitionLine.NewSupplierAddress
-- 2023-05-19T18:07:44.110054300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586681,715384,0,642,0,TO_TIMESTAMP('2023-05-19 19:07:43.827','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Neue Lieferantenadresse Details',0,190,0,1,1,TO_TIMESTAMP('2023-05-19 19:07:43.827','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-19T18:07:44.111113600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715384 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-19T18:07:44.112679400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582356) 
;

-- 2023-05-19T18:07:44.115401900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715384
;

-- 2023-05-19T18:07:44.115936700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715384)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.Neue Lieferantenadresse Details
-- Column: M_RequisitionLine.NewSupplierAddress
-- 2023-05-19T18:07:56.706904900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715384,0,642,550704,617487,'F',TO_TIMESTAMP('2023-05-19 19:07:56.31','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Neue Lieferantenadresse Details',150,0,0,TO_TIMESTAMP('2023-05-19 19:07:56.31','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Element: IsNewSupplierAddress
-- 2023-05-19T18:09:50.342907300Z
UPDATE AD_Element_Trl SET Name='Neue Lieferantenadresse', PrintName='Neue Lieferantenadresse',Updated=TO_TIMESTAMP('2023-05-19 19:09:50.342','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582355 AND AD_Language='de_DE'
;

-- 2023-05-19T18:09:50.343960500Z
UPDATE AD_Element SET Name='Neue Lieferantenadresse', PrintName='Neue Lieferantenadresse' WHERE AD_Element_ID=582355
;

-- 2023-05-19T18:09:50.629899100Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582355,'de_DE') 
;

-- 2023-05-19T18:09:50.631463300Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582355,'de_DE') 
;

-- Element: IsNewSupplierAddress
-- 2023-05-19T18:09:52.953663900Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-05-19 19:09:52.953','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582355 AND AD_Language='de_DE'
;

-- 2023-05-19T18:09:52.955235500Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582355,'de_DE') 
;

-- 2023-05-19T18:09:52.956370900Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582355,'de_DE') 
;

-- 2023-05-19T18:14:25.860098Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582357,0,'OrderChannel',TO_TIMESTAMP('2023-05-19 19:14:25.607','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Kanal der Bestellung','Kanal der Bestellung',TO_TIMESTAMP('2023-05-19 19:14:25.607','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-19T18:14:25.861699200Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582357 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Name: OrderChannel
-- 2023-05-19T18:17:34.483715900Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541736,TO_TIMESTAMP('2023-05-19 19:17:34.182','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','OrderChannel',TO_TIMESTAMP('2023-05-19 19:17:34.182','YYYY-MM-DD HH24:MI:SS.US'),100,'L')
;

-- 2023-05-19T18:17:34.485290200Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541736 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: OrderChannel
-- Value: P
-- ValueName: Purchasing
-- 2023-05-19T18:18:24.662124200Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541736,543470,TO_TIMESTAMP('2023-05-19 19:18:24.41','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Purchasing',TO_TIMESTAMP('2023-05-19 19:18:24.41','YYYY-MM-DD HH24:MI:SS.US'),100,'P','Purchasing')
;

-- 2023-05-19T18:18:24.663670700Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543470 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: OrderChannel -> P_Purchasing
-- 2023-05-19T18:18:38.363854300Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Einkaufen',Updated=TO_TIMESTAMP('2023-05-19 19:18:38.363','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543470
;

-- 2023-05-19T18:18:38.364897900Z
UPDATE AD_Ref_List SET Name='Einkaufen' WHERE AD_Ref_List_ID=543470
;

-- Reference: OrderChannel
-- Value: S
-- ValueName: Self
-- 2023-05-19T18:21:06.968579Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541736,543471,TO_TIMESTAMP('2023-05-19 19:21:06.721','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Self',TO_TIMESTAMP('2023-05-19 19:21:06.721','YYYY-MM-DD HH24:MI:SS.US'),100,'S','Self')
;

-- 2023-05-19T18:21:06.969638500Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543471 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: OrderChannel -> S_Self
-- 2023-05-19T18:21:19.312068100Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Selbst',Updated=TO_TIMESTAMP('2023-05-19 19:21:19.312','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543471
;

-- 2023-05-19T18:21:19.313114200Z
UPDATE AD_Ref_List SET Name='Selbst' WHERE AD_Ref_List_ID=543471
;

-- Column: M_RequisitionLine.OrderChannel
-- 2023-05-19T18:21:51.583957800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586682,582357,0,17,541736,703,'OrderChannel',TO_TIMESTAMP('2023-05-19 19:21:51.162','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Kanal der Bestellung',0,0,TO_TIMESTAMP('2023-05-19 19:21:51.162','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-19T18:21:51.586053800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586682 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-19T18:21:52.032007200Z
/* DDL */  select update_Column_Translation_From_AD_Element(582357) 
;

-- 2023-05-19T18:21:55.955322900Z
/* DDL */ SELECT public.db_alter_table('M_RequisitionLine','ALTER TABLE public.M_RequisitionLine ADD COLUMN OrderChannel CHAR(1)')
;

-- Field: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> Kanal der Bestellung
-- Column: M_RequisitionLine.OrderChannel
-- 2023-05-19T18:22:24.433479500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586682,715385,0,642,0,TO_TIMESTAMP('2023-05-19 19:22:24.107','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Kanal der Bestellung',0,200,0,1,1,TO_TIMESTAMP('2023-05-19 19:22:24.107','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-19T18:22:24.435074200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715385 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-19T18:22:24.436196300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582357) 
;

-- 2023-05-19T18:22:24.439424200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715385
;

-- 2023-05-19T18:22:24.440490700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715385)
;

-- 2023-05-19T18:22:44.868530600Z
INSERT INTO t_alter_column values('m_requisitionline','OrderChannel','CHAR(1)',null,null)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.Kanal der Bestellung
-- Column: M_RequisitionLine.OrderChannel
-- 2023-05-19T18:23:06.589512600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715385,0,642,550704,617488,'F',TO_TIMESTAMP('2023-05-19 19:23:06.339','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Kanal der Bestellung',160,0,0,TO_TIMESTAMP('2023-05-19 19:23:06.339','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: M_RequisitionLine.EMail
-- 2023-05-19T18:26:50.594981600Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586683,881,0,10,703,'EMail',TO_TIMESTAMP('2023-05-19 19:26:49.988','YYYY-MM-DD HH24:MI:SS.US'),100,'N','EMail-Adresse','D',0,60,'The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'eMail',0,0,TO_TIMESTAMP('2023-05-19 19:26:49.988','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-19T18:26:50.597096600Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586683 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-19T18:26:51.030700Z
/* DDL */  select update_Column_Translation_From_AD_Element(881) 
;

-- 2023-05-19T18:26:52.645807900Z
/* DDL */ SELECT public.db_alter_table('M_RequisitionLine','ALTER TABLE public.M_RequisitionLine ADD COLUMN EMail VARCHAR(60)')
;

-- Field: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> eMail
-- Column: M_RequisitionLine.EMail
-- 2023-05-19T18:27:18.684146100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586683,715386,0,642,0,TO_TIMESTAMP('2023-05-19 19:27:18.435','YYYY-MM-DD HH24:MI:SS.US'),100,'EMail-Adresse',0,'D','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.',0,'Y','Y','Y','N','N','N','N','N','eMail',0,210,0,1,1,TO_TIMESTAMP('2023-05-19 19:27:18.435','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-19T18:27:18.685187800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715386 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-19T18:27:18.686831Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(881) 
;

-- 2023-05-19T18:27:18.703751200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715386
;

-- 2023-05-19T18:27:18.704773700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715386)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.eMail
-- Column: M_RequisitionLine.EMail
-- 2023-05-19T18:27:43.416284700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715386,0,642,550704,617489,'F',TO_TIMESTAMP('2023-05-19 19:27:43.103','YYYY-MM-DD HH24:MI:SS.US'),100,'EMail-Adresse','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','N','N','Y','N','N','N',0,'eMail',170,0,0,TO_TIMESTAMP('2023-05-19 19:27:43.103','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: M_RequisitionLine.PaymentRule
-- 2023-05-19T18:29:20.269709500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586684,1143,0,17,195,703,'PaymentRule',TO_TIMESTAMP('2023-05-19 19:29:19.977','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Wie die Rechnung bezahlt wird','D',0,1,'Die Zahlungsweise zeigt die Art der Bezahlung der Rechnung an.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Zahlungsweise',0,0,TO_TIMESTAMP('2023-05-19 19:29:19.977','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-19T18:29:20.271294400Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586684 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-19T18:29:20.702208900Z
/* DDL */  select update_Column_Translation_From_AD_Element(1143) 
;

-- 2023-05-19T18:29:22.292881200Z
/* DDL */ SELECT public.db_alter_table('M_RequisitionLine','ALTER TABLE public.M_RequisitionLine ADD COLUMN PaymentRule CHAR(1)')
;

-- Field: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> Zahlungsweise
-- Column: M_RequisitionLine.PaymentRule
-- 2023-05-19T18:29:42.124981500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586684,715387,0,642,0,TO_TIMESTAMP('2023-05-19 19:29:41.416','YYYY-MM-DD HH24:MI:SS.US'),100,'Wie die Rechnung bezahlt wird',0,'D','Die Zahlungsweise zeigt die Art der Bezahlung der Rechnung an.',0,'Y','Y','Y','N','N','N','N','N','Zahlungsweise',0,220,0,1,1,TO_TIMESTAMP('2023-05-19 19:29:41.416','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-19T18:29:42.126564700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715387 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-19T18:29:42.127689500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1143) 
;

-- 2023-05-19T18:29:42.146635300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715387
;

-- 2023-05-19T18:29:42.147690900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715387)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarfs-Position(642,D) -> main -> 10 -> main.Zahlungsweise
-- Column: M_RequisitionLine.PaymentRule
-- 2023-05-19T18:29:52.110924200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715387,0,642,550704,617490,'F',TO_TIMESTAMP('2023-05-19 19:29:51.845','YYYY-MM-DD HH24:MI:SS.US'),100,'Wie die Rechnung bezahlt wird','Die Zahlungsweise zeigt die Art der Bezahlung der Rechnung an.','Y','N','N','Y','N','N','N',0,'Zahlungsweise',180,0,0,TO_TIMESTAMP('2023-05-19 19:29:51.845','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Bestellanforderung(322,D) -> Bedarf(641,D) -> main -> 20 -> pricing.Zahlungsweise
-- Column: M_Requisition.PaymentRule
-- 2023-05-19T18:31:00.871256900Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=617458
;

-- 2023-05-19T18:31:14.330342300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715366
;

-- Field: Bestellanforderung(322,D) -> Bedarf(641,D) -> Zahlungsweise
-- Column: M_Requisition.PaymentRule
-- 2023-05-19T18:31:14.334106900Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=715366
;

-- 2023-05-19T18:31:14.339470700Z
DELETE FROM AD_Field WHERE AD_Field_ID=715366
;

-- Column: M_Requisition.PaymentRule
-- 2023-05-19T18:31:38.504554200Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=586664
;

-- 2023-05-19T18:31:38.509229400Z
DELETE FROM AD_Column WHERE AD_Column_ID=586664
;

-- 2023-05-19T18:33:08.549596Z
INSERT INTO t_alter_column values('m_requisitionline','PaymentRule','CHAR(1)',null,null)
;

