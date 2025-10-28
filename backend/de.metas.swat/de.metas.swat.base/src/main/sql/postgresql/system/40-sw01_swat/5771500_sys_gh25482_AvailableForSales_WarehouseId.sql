-- Run mode: SWING_CLIENT

-- Column: MD_Available_For_Sales_QueryResult.M_Warehouse_ID
-- 2025-09-26T15:44:14.990Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591127,459,0,30,541340,'XX','M_Warehouse_ID',TO_TIMESTAMP('2025-09-26 15:44:14.672000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Lager oder Ort für Dienstleistung','de.metas.material.cockpit',0,10,'Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Lager',0,0,TO_TIMESTAMP('2025-09-26 15:44:14.672000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-26T15:44:15.006Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591127 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-26T15:44:15.037Z
/* DDL */  select update_Column_Translation_From_AD_Element(459)
;

-- Column: MD_Available_For_Sales.M_Warehouse_ID
-- 2025-09-26T15:48:04.336Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591128,459,0,30,542164,'XX','M_Warehouse_ID',TO_TIMESTAMP('2025-09-26 15:48:04.162000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Lager oder Ort für Dienstleistung','D',0,10,'Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lager',0,0,TO_TIMESTAMP('2025-09-26 15:48:04.162000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-26T15:48:04.337Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591128 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-26T15:48:04.339Z
/* DDL */  select update_Column_Translation_From_AD_Element(459)
;

-- 2025-09-26T15:48:05.508Z
/* DDL */ SELECT public.db_alter_table('MD_Available_For_Sales','ALTER TABLE public.MD_Available_For_Sales ADD COLUMN M_Warehouse_ID NUMERIC(10)')
;

-- 2025-09-26T15:48:05.687Z
ALTER TABLE MD_Available_For_Sales ADD CONSTRAINT MWarehouse_MDAvailableForSales FOREIGN KEY (M_Warehouse_ID) REFERENCES public.M_Warehouse DEFERRABLE INITIALLY DEFERRED
;

-- Field: Zum Verkauf verfügbar(541525,D) -> Zum Verkauf verfügbar(546323,D) -> Lager
-- Column: MD_Available_For_Sales.M_Warehouse_ID
-- 2025-09-26T15:55:10.266Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591128,754211,0,546323,TO_TIMESTAMP('2025-09-26 15:55:10.044000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lager oder Ort für Dienstleistung',10,'D','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','N','N','N','N','N','N','Lager',TO_TIMESTAMP('2025-09-26 15:55:10.044000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-26T15:55:10.270Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754211 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-26T15:55:10.274Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(459)
;

-- 2025-09-26T15:55:10.339Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754211
;

-- 2025-09-26T15:55:10.347Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754211)
;

-- UI Element: Zum Verkauf verfügbar(541525,D) -> Zum Verkauf verfügbar(546323,D) -> main -> 10 -> main.Lager
-- Column: MD_Available_For_Sales.M_Warehouse_ID
-- 2025-09-26T15:55:42.668Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,754211,0,546323,637304,549292,'F',TO_TIMESTAMP('2025-09-26 15:55:42.457000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lager oder Ort für Dienstleistung','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','N','Y','N','N','N',0,'Lager',60,0,0,TO_TIMESTAMP('2025-09-26 15:55:42.457000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Zum Verkauf verfügbar(541525,D) -> Zum Verkauf verfügbar(546323,D) -> main -> 10 -> main.StorageAttributesKey (technical)
-- Column: MD_Available_For_Sales.StorageAttributesKey
-- 2025-09-26T15:55:49.802Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-09-26 15:55:49.802000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=608994
;

-- UI Element: Zum Verkauf verfügbar(541525,D) -> Zum Verkauf verfügbar(546323,D) -> main -> 10 -> main.Lagerbestand
-- Column: MD_Available_For_Sales.QtyOnHandStock
-- 2025-09-26T15:55:49.807Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-09-26 15:55:49.807000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=608995
;

-- UI Element: Zum Verkauf verfügbar(541525,D) -> Zum Verkauf verfügbar(546323,D) -> main -> 10 -> main.QtyToBeShipped
-- Column: MD_Available_For_Sales.QtyToBeShipped
-- 2025-09-26T15:55:49.811Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-09-26 15:55:49.811000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=608996
;

-- UI Element: Zum Verkauf verfügbar(541525,D) -> Zum Verkauf verfügbar(546323,D) -> main -> 10 -> main.Lager
-- Column: MD_Available_For_Sales.M_Warehouse_ID
-- 2025-09-26T15:55:49.815Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-09-26 15:55:49.815000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637304
;

-- Column: MD_Available_For_Sales.M_Warehouse_ID
-- 2025-09-26T16:45:28.838Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2025-09-26 16:45:28.838000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591128
;

DELETE
FROM MD_Available_For_Sales
WHERE 1 = 1
;

DROP INDEX IF EXISTS Product_AttributesKey_OrgId_UniqueIndex
;

-- 2025-09-26T16:49:46.199Z
INSERT INTO t_alter_column values('md_available_for_sales','M_Warehouse_ID','NUMERIC(10)',null,null)
;

-- 2025-09-26T16:49:46.209Z
INSERT INTO t_alter_column values('md_available_for_sales','M_Warehouse_ID',null,'NOT NULL',null)
;

-- 2025-09-26T16:54:28.791Z
DELETE FROM AD_Index_Column WHERE AD_Index_Column_ID=541251
;

-- 2025-09-26T16:54:30.708Z
DELETE FROM AD_Index_Column WHERE AD_Index_Column_ID=541252
;

-- 2025-09-26T16:54:32.755Z
DELETE FROM AD_Index_Column WHERE AD_Index_Column_ID=541253
;

-- 2025-09-26T16:54:36.123Z
DELETE FROM  AD_Index_Table_Trl WHERE AD_Index_Table_ID=540701
;

-- 2025-09-26T16:54:36.125Z
DELETE FROM AD_Index_Table WHERE AD_Index_Table_ID=540701
;

-- 2025-09-26T16:54:59.700Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540826,0,542164,TO_TIMESTAMP('2025-09-26 16:54:59.530000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Y','Product_AttributesKey_OrgId_M_Warehouse_ID_UniqueIndex','N',TO_TIMESTAMP('2025-09-26 16:54:59.530000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-26T16:54:59.704Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Index_Table_ID=540826 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2025-09-26T16:55:13.343Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,583301,541467,540826,0,TO_TIMESTAMP('2025-09-26 16:55:13.183000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',10,TO_TIMESTAMP('2025-09-26 16:55:13.183000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-26T16:55:58.275Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,583302,541468,540826,0,TO_TIMESTAMP('2025-09-26 16:55:58.124000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',20,TO_TIMESTAMP('2025-09-26 16:55:58.124000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-26T16:56:06.659Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,583293,541469,540826,0,TO_TIMESTAMP('2025-09-26 16:56:06.499000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',30,TO_TIMESTAMP('2025-09-26 16:56:06.499000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-26T16:56:13.155Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,591128,541470,540826,0,TO_TIMESTAMP('2025-09-26 16:56:12.990000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',40,TO_TIMESTAMP('2025-09-26 16:56:12.990000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-26T16:56:16.694Z
CREATE UNIQUE INDEX Product_AttributesKey_OrgId_M_Warehouse_ID_UniqueIndex ON MD_Available_For_Sales (M_Product_ID,StorageAttributesKey,AD_Org_ID,M_Warehouse_ID)
;

-- Run mode: SWING_CLIENT

-- 2025-09-29T10:44:31.853Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584046,0,'IsQtyPerWarehouse',TO_TIMESTAMP('2025-09-29 10:44:31.578000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Menge pro Lager','Menge pro Lager',TO_TIMESTAMP('2025-09-29 10:44:31.578000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-29T10:44:31.869Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584046 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsQtyPerWarehouse
-- 2025-09-29T10:44:41.746Z
UPDATE AD_Element_Trl SET Name='Quantity per warehouse', PrintName='Quantity per warehouse',Updated=TO_TIMESTAMP('2025-09-29 10:44:41.746000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584046 AND AD_Language='en_US'
;

-- 2025-09-29T10:44:41.746Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-29T10:44:42.283Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584046,'en_US')
;

-- Column: MD_AvailableForSales_Config.IsQtyPerWarehouse
-- 2025-09-29T10:47:07.248Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591169,584046,0,20,541343,'XX','IsQtyPerWarehouse',TO_TIMESTAMP('2025-09-29 10:47:07.062000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','de.metas.material.cockpit',0,1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Menge pro Lager',0,0,TO_TIMESTAMP('2025-09-29 10:47:07.062000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-29T10:47:07.250Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591169 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-29T10:47:07.254Z
/* DDL */  select update_Column_Translation_From_AD_Element(584046)
;

-- 2025-09-29T10:47:08.256Z
/* DDL */ SELECT public.db_alter_table('MD_AvailableForSales_Config','ALTER TABLE public.MD_AvailableForSales_Config ADD COLUMN IsQtyPerWarehouse CHAR(1) DEFAULT ''N'' CHECK (IsQtyPerWarehouse IN (''Y'',''N'')) NOT NULL')
;

-- Field: Einstellungen zur verfügbaren Menge für den Verkauf(540614,de.metas.material.cockpit) -> Einstellungen(541694,de.metas.material.cockpit) -> Menge pro Lager
-- Column: MD_AvailableForSales_Config.IsQtyPerWarehouse
-- 2025-09-29T10:47:48.076Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591169,754212,0,541694,TO_TIMESTAMP('2025-09-29 10:47:47.539000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'de.metas.material.cockpit','Y','N','N','N','N','N','N','N','Menge pro Lager',TO_TIMESTAMP('2025-09-29 10:47:47.539000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-29T10:47:48.093Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754212 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-29T10:47:48.093Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584046)
;

-- 2025-09-29T10:47:48.124Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754212
;

-- 2025-09-29T10:47:48.147Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754212)
;

-- UI Element: Einstellungen zur verfügbaren Menge für den Verkauf(540614,de.metas.material.cockpit) -> Einstellungen(541694,de.metas.material.cockpit) -> main -> 20 -> misc_settings.Menge pro Lager
-- Column: MD_AvailableForSales_Config.IsQtyPerWarehouse
-- 2025-09-29T10:48:09.674Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,754212,0,541694,637305,542428,'F',TO_TIMESTAMP('2025-09-29 10:48:09.472000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Menge pro Lager',40,0,0,TO_TIMESTAMP('2025-09-29 10:48:09.472000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Einstellungen zur verfügbaren Menge für den Verkauf(540614,de.metas.material.cockpit) -> Einstellungen(541694,de.metas.material.cockpit) -> main -> 20 -> misc_settings.Menge pro Lager
-- Column: MD_AvailableForSales_Config.IsQtyPerWarehouse
-- 2025-09-29T10:48:25.100Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-09-29 10:48:25.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637305
;
