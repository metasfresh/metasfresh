-- 2019-09-05T11:14:19.676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577038,0,'WarehouseLocatorIdentifier',TO_TIMESTAMP('2019-09-05 14:14:19','YYYY-MM-DD HH24:MI:SS'),100,'Text that contains identifier of earehosue and locator','D','Y','Warehouse Locator Identifier','Warehouse Locator Identifier',TO_TIMESTAMP('2019-09-05 14:14:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-05T11:14:19.692Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577038 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-09-05T11:14:35.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568639,577038,0,10,572,'WarehouseLocatorIdentifier',TO_TIMESTAMP('2019-09-05 14:14:35','YYYY-MM-DD HH24:MI:SS'),100,'N','Text that contains identifier of earehosue and locator','D',60,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Warehouse Locator Identifier',0,0,TO_TIMESTAMP('2019-09-05 14:14:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-05T11:14:35.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568639 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-05T11:14:35.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577038) 
;

-- 2019-09-05T11:14:38.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('I_Inventory','ALTER TABLE public.I_Inventory ADD COLUMN WarehouseLocatorIdentifier VARCHAR(60)')
;

-- 2019-09-06T13:33:10.380Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=1675, ColumnName='ProductValue', Description='Schlüssel des Produktes', Help=NULL, IsCalculated='N', Name='Produktschlüssel',Updated=TO_TIMESTAMP('2019-09-06 16:33:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=8824
;

-- 2019-09-06T13:33:10.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Produktschlüssel', Description='Schlüssel des Produktes', Help=NULL WHERE AD_Column_ID=8824
;

-- 2019-09-06T13:33:10.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(1675) 
;

/* DDL */ SELECT public.db_alter_table('I_Inventory','ALTER TABLE public.I_Inventory RENAME COLUMN value TO ProductValue')
;





-- 2019-09-06T14:04:31.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568639,583677,0,481,0,TO_TIMESTAMP('2019-09-06 17:04:31','YYYY-MM-DD HH24:MI:SS'),100,'Text that contains identifier of earehosue and locator',0,'D',0,'Y','Y','Y','N','N','N','N','N','Warehouse Locator Identifier',350,350,0,1,1,TO_TIMESTAMP('2019-09-06 17:04:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-06T14:04:31.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=583677 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-06T14:04:31.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577038) 
;

-- 2019-09-06T14:04:31.347Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=583677
;

-- 2019-09-06T14:04:31.349Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(583677)
;

-- 2019-09-06T14:05:00.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=90,Updated=TO_TIMESTAMP('2019-09-06 17:05:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583677
;

-- 2019-09-06T14:05:00.350Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=210,Updated=TO_TIMESTAMP('2019-09-06 17:05:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=8192
;

-- 2019-09-06T14:05:00.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=220,Updated=TO_TIMESTAMP('2019-09-06 17:05:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=6698
;

-- 2019-09-06T14:05:00.360Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=230,Updated=TO_TIMESTAMP('2019-09-06 17:05:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563456
;

-- 2019-09-06T14:05:00.365Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=240,Updated=TO_TIMESTAMP('2019-09-06 17:05:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=6711
;

-- 2019-09-06T14:05:00.369Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=250,Updated=TO_TIMESTAMP('2019-09-06 17:05:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=6703
;

-- 2019-09-06T14:05:00.372Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=260,Updated=TO_TIMESTAMP('2019-09-06 17:05:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563457
;

-- 2019-09-06T14:05:00.376Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=270,Updated=TO_TIMESTAMP('2019-09-06 17:05:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563459
;

-- 2019-09-06T14:05:00.379Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=280,Updated=TO_TIMESTAMP('2019-09-06 17:05:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563463
;

-- 2019-09-06T14:05:00.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=290,Updated=TO_TIMESTAMP('2019-09-06 17:05:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563460
;

-- 2019-09-06T14:05:00.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=300,Updated=TO_TIMESTAMP('2019-09-06 17:05:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563461
;

-- 2019-09-06T14:05:00.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=310,Updated=TO_TIMESTAMP('2019-09-06 17:05:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=6706
;

-- 2019-09-06T14:05:00.391Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=320,Updated=TO_TIMESTAMP('2019-09-06 17:05:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=6697
;

-- 2019-09-06T14:05:00.394Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=330,Updated=TO_TIMESTAMP('2019-09-06 17:05:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563458
;

-- 2019-09-06T14:05:00.397Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=340,Updated=TO_TIMESTAMP('2019-09-06 17:05:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583466
;

-- 2019-09-06T14:05:00.400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=350,Updated=TO_TIMESTAMP('2019-09-06 17:05:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=583467
;

-- 2019-09-06T14:05:14.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=120,Updated=TO_TIMESTAMP('2019-09-06 17:05:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=7064
;

-- 2019-09-06T14:05:14.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=130,Updated=TO_TIMESTAMP('2019-09-06 17:05:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=6699
;

-- 2019-09-06T14:05:14.340Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=140,Updated=TO_TIMESTAMP('2019-09-06 17:05:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=6709
;

-- 2019-09-06T14:05:14.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=150,Updated=TO_TIMESTAMP('2019-09-06 17:05:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=6702
;

-- 2019-09-06T14:05:14.347Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=160,Updated=TO_TIMESTAMP('2019-09-06 17:05:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=6707
;

-- 2019-09-06T14:05:14.350Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=170,Updated=TO_TIMESTAMP('2019-09-06 17:05:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563454
;






-- 2019-09-06T14:12:21.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Help='E.g. WH-LO-1-1-1-1',Updated=TO_TIMESTAMP('2019-09-06 17:12:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577038
;

-- 2019-09-06T14:12:21.323Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='WarehouseLocatorIdentifier', Name='Warehouse Locator Identifier', Description='Text that contains identifier of earehosue and locator', Help='E.g. WH-LO-1-1-1-1' WHERE AD_Element_ID=577038
;

-- 2019-09-06T14:12:21.325Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='WarehouseLocatorIdentifier', Name='Warehouse Locator Identifier', Description='Text that contains identifier of earehosue and locator', Help='E.g. WH-LO-1-1-1-1', AD_Element_ID=577038 WHERE UPPER(ColumnName)='WAREHOUSELOCATORIDENTIFIER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-09-06T14:12:21.330Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='WarehouseLocatorIdentifier', Name='Warehouse Locator Identifier', Description='Text that contains identifier of earehosue and locator', Help='E.g. WH-LO-1-1-1-1' WHERE AD_Element_ID=577038 AND IsCentrallyMaintained='Y'
;

-- 2019-09-06T14:12:21.331Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Warehouse Locator Identifier', Description='Text that contains identifier of earehosue and locator', Help='E.g. WH-LO-1-1-1-1' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577038) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577038)
;

-- 2019-09-06T14:12:21.342Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Warehouse Locator Identifier', Description='Text that contains identifier of earehosue and locator', Help='E.g. WH-LO-1-1-1-1', CommitWarning = NULL WHERE AD_Element_ID = 577038
;

-- 2019-09-06T14:12:21.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Warehouse Locator Identifier', Description='Text that contains identifier of earehosue and locator', Help='E.g. WH-LO-1-1-1-1' WHERE AD_Element_ID = 577038
;

-- 2019-09-06T14:12:21.346Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Warehouse Locator Identifier', Description = 'Text that contains identifier of earehosue and locator', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577038
;

-- 2019-09-06T14:12:59.850Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Text that contains identifier of warehouse. locator and dimensions',Updated=TO_TIMESTAMP('2019-09-06 17:12:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577038
;

-- 2019-09-06T14:12:59.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='WarehouseLocatorIdentifier', Name='Warehouse Locator Identifier', Description='Text that contains identifier of warehouse. locator and dimensions', Help='E.g. WH-LO-1-1-1-1' WHERE AD_Element_ID=577038
;

-- 2019-09-06T14:12:59.858Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='WarehouseLocatorIdentifier', Name='Warehouse Locator Identifier', Description='Text that contains identifier of warehouse. locator and dimensions', Help='E.g. WH-LO-1-1-1-1', AD_Element_ID=577038 WHERE UPPER(ColumnName)='WAREHOUSELOCATORIDENTIFIER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-09-06T14:12:59.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='WarehouseLocatorIdentifier', Name='Warehouse Locator Identifier', Description='Text that contains identifier of warehouse. locator and dimensions', Help='E.g. WH-LO-1-1-1-1' WHERE AD_Element_ID=577038 AND IsCentrallyMaintained='Y'
;

-- 2019-09-06T14:12:59.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Warehouse Locator Identifier', Description='Text that contains identifier of warehouse. locator and dimensions', Help='E.g. WH-LO-1-1-1-1' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577038) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577038)
;

-- 2019-09-06T14:12:59.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Warehouse Locator Identifier', Description='Text that contains identifier of warehouse. locator and dimensions', Help='E.g. WH-LO-1-1-1-1', CommitWarning = NULL WHERE AD_Element_ID = 577038
;

-- 2019-09-06T14:12:59.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Warehouse Locator Identifier', Description='Text that contains identifier of warehouse. locator and dimensions', Help='E.g. WH-LO-1-1-1-1' WHERE AD_Element_ID = 577038
;

-- 2019-09-06T14:12:59.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Warehouse Locator Identifier', Description = 'Text that contains identifier of warehouse. locator and dimensions', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577038
;

-- 2019-09-06T14:22:37.041Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Help='E.g. WH-LO-1-2-3-4
WH- warehouse value
LO - locator value
1 - X dimension
2 - Y dimension
3 - Z dimension
4 - X1 dimension',Updated=TO_TIMESTAMP('2019-09-06 17:22:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577038
;

-- 2019-09-06T14:22:37.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='WarehouseLocatorIdentifier', Name='Warehouse Locator Identifier', Description='Text that contains identifier of warehouse. locator and dimensions', Help='E.g. WH-LO-1-2-3-4
WH- warehouse value
LO - locator value
1 - X dimension
2 - Y dimension
3 - Z dimension
4 - X1 dimension' WHERE AD_Element_ID=577038
;

-- 2019-09-06T14:22:37.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='WarehouseLocatorIdentifier', Name='Warehouse Locator Identifier', Description='Text that contains identifier of warehouse. locator and dimensions', Help='E.g. WH-LO-1-2-3-4
WH- warehouse value
LO - locator value
1 - X dimension
2 - Y dimension
3 - Z dimension
4 - X1 dimension', AD_Element_ID=577038 WHERE UPPER(ColumnName)='WAREHOUSELOCATORIDENTIFIER' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-09-06T14:22:37.052Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='WarehouseLocatorIdentifier', Name='Warehouse Locator Identifier', Description='Text that contains identifier of warehouse. locator and dimensions', Help='E.g. WH-LO-1-2-3-4
WH- warehouse value
LO - locator value
1 - X dimension
2 - Y dimension
3 - Z dimension
4 - X1 dimension' WHERE AD_Element_ID=577038 AND IsCentrallyMaintained='Y'
;

-- 2019-09-06T14:22:37.053Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Warehouse Locator Identifier', Description='Text that contains identifier of warehouse. locator and dimensions', Help='E.g. WH-LO-1-2-3-4
WH- warehouse value
LO - locator value
1 - X dimension
2 - Y dimension
3 - Z dimension
4 - X1 dimension' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577038) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577038)
;

-- 2019-09-06T14:22:37.066Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Warehouse Locator Identifier', Description='Text that contains identifier of warehouse. locator and dimensions', Help='E.g. WH-LO-1-2-3-4
WH- warehouse value
LO - locator value
1 - X dimension
2 - Y dimension
3 - Z dimension
4 - X1 dimension', CommitWarning = NULL WHERE AD_Element_ID = 577038
;

-- 2019-09-06T14:22:37.068Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Warehouse Locator Identifier', Description='Text that contains identifier of warehouse. locator and dimensions', Help='E.g. WH-LO-1-2-3-4
WH- warehouse value
LO - locator value
1 - X dimension
2 - Y dimension
3 - Z dimension
4 - X1 dimension' WHERE AD_Element_ID = 577038
;

-- 2019-09-06T14:22:37.068Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Warehouse Locator Identifier', Description = 'Text that contains identifier of warehouse. locator and dimensions', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577038
;

-- 2019-09-06T15:09:40.101Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET Description='Can be:
	* The exact product value
	* The product id
	* Part of the product value, using this pattern val-%',Updated=TO_TIMESTAMP('2019-09-06 18:09:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=8824
;

-- 2019-09-06T15:09:40.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Produktschlüssel', Description='Can be:
	* The exact product value
	* The product id
	* Part of the product value, using this pattern val-%', Help=NULL WHERE AD_Column_ID=8824
;



-- 2019-09-06T15:14:41.852Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Help='Can be:
	* The exact product value
	* The product id
	* Part of the product value, using this pattern val-%',Updated=TO_TIMESTAMP('2019-09-06 18:14:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=6704
;



----------- update standard import invetory format



-- 2019-09-06T15:28:19.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_ImpFormat_Row SET AD_Column_ID=568639, Name='Warehouse Locator Identifier',Updated=TO_TIMESTAMP('2019-09-06 18:28:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=531106
;

