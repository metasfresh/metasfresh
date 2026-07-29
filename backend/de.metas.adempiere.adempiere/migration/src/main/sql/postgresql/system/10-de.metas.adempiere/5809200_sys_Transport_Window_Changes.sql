-- 2026-06-22T14:12:02.394Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585039,0,'ProductNos',TO_TIMESTAMP('2026-06-22 14:12:01.024000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Produktnummer','Produktnummer',TO_TIMESTAMP('2026-06-22 14:12:01.024000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-22T14:12:02.445Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585039 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: ProductNos
-- 2026-06-22T14:14:00.954Z
UPDATE AD_Element_Trl SET Description='Kommagetrennte Liste aller unterschiedlichen Artikelnummern des zugrunde liegenden Belegs: der Lieferschein-Positionen, falls das Packstück einen Lieferschein referenziert, sonst der Auftrags-Positionen.', Help='Kommagetrennte Liste aller unterschiedlichen Artikelnummern des zugrunde liegenden Belegs: der Lieferschein-Positionen, falls das Packstück einen Lieferschein referenziert, sonst der Auftrags-Positionen.', IsTranslated='Y',Updated=TO_TIMESTAMP('2026-06-22 14:14:00.954000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585039 AND AD_Language='de_CH'
;

-- 2026-06-22T14:14:01.005Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-06-22T14:14:04.120Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585039,'de_CH')
;

-- Element: ProductNos
-- 2026-06-22T14:14:16.996Z
UPDATE AD_Element_Trl SET Description='Kommagetrennte Liste aller unterschiedlichen Artikelnummern des zugrunde liegenden Belegs: der Lieferschein-Positionen, falls das Packstück einen Lieferschein referenziert, sonst der Auftrags-Positionen.', Help='Kommagetrennte Liste aller unterschiedlichen Artikelnummern des zugrunde liegenden Belegs: der Lieferschein-Positionen, falls das Packstück einen Lieferschein referenziert, sonst der Auftrags-Positionen.',Updated=TO_TIMESTAMP('2026-06-22 14:14:16.995000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585039 AND AD_Language='de_DE'
;

-- 2026-06-22T14:14:17.045Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-06-22T14:14:20.231Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(585039,'de_DE')
;

-- 2026-06-22T14:14:20.281Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585039,'de_DE')
;

-- Element: ProductNos
-- 2026-06-22T14:14:53.997Z
UPDATE AD_Element_Trl SET Description='Comma-separated list of all distinct article numbers (product value) of the underlying document: the shipment lines if this package references a shipment, otherwise the order lines.', Help='Comma-separated list of all distinct article numbers (product value) of the underlying document: the shipment lines if this package references a shipment, otherwise the order lines.',Updated=TO_TIMESTAMP('2026-06-22 14:14:53.997000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585039 AND AD_Language='en_US'
;

-- 2026-06-22T14:14:54.048Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-06-22T14:14:57.015Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585039,'en_US')
;

-- 2026-06-22T14:15:38.603Z
UPDATE AD_Element SET ColumnName='TransportProductNos',Updated=TO_TIMESTAMP('2026-06-22 14:15:38.603000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585039
;

-- 2026-06-22T14:15:38.652Z
UPDATE AD_Column SET ColumnName='TransportProductNos' WHERE AD_Element_ID=585039
;

-- 2026-06-22T14:15:38.702Z
UPDATE AD_Process_Para SET ColumnName='TransportProductNos' WHERE AD_Element_ID=585039
;

-- 2026-06-22T14:15:38.902Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585039,'de_DE')
;

-- 2026-06-22T14:16:39.691Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585040,0,'TransportQtys',TO_TIMESTAMP('2026-06-22 14:16:39.342000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gesamtmenge (Summe der erfassten Menge) des zugrunde liegenden Belegs; nur befüllt, wenn der Beleg genau einen Artikel enthält. Bei mehreren Artikeln bleibt die Spalte leer.','D','Gesamtmenge (Summe der erfassten Menge) des zugrunde liegenden Belegs; nur befüllt, wenn der Beleg genau einen Artikel enthält. Bei mehreren Artikeln bleibt die Spalte leer.','Y','Menge','Menge',TO_TIMESTAMP('2026-06-22 14:16:39.342000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-22T14:16:39.742Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585040 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: TransportQtys
-- 2026-06-22T14:17:54.604Z
UPDATE AD_Element_Trl SET Description='Total quantity (sum of entered qty) of the underlying document, filled only when it contains exactly one distinct product. Left empty when more than one product is present, since a single quantity would be ambiguous.', Help='Total quantity (sum of entered qty) of the underlying document, filled only when it contains exactly one distinct product. Left empty when more than one product is present, since a single quantity would be ambiguous.',Updated=TO_TIMESTAMP('2026-06-22 14:17:54.604000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585040 AND AD_Language='en_US'
;

-- 2026-06-22T14:17:54.654Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-06-22T14:17:57.605Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585040,'en_US')
;

-- 2026-06-22T14:28:50.606Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585041,0,'TransportUOMs',TO_TIMESTAMP('2026-06-22 14:28:50.276000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit zur Menge. Nur befüllt, wenn der Beleg genau einen Artikel enthält; sonst leer.','D','Maßeinheit zur Menge. Nur befüllt, wenn der Beleg genau einen Artikel enthält; sonst leer.','Y','Maßeinheit','Maßeinheit',TO_TIMESTAMP('2026-06-22 14:28:50.276000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-22T14:28:50.657Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585041 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: TransportUOMs
-- 2026-06-22T14:29:38.905Z
UPDATE AD_Element_Trl SET Description='Unit of measure for the value shown in Qty. Filled only when the underlying document contains exactly one distinct product; empty otherwise.', Help='Unit of measure for the value shown in Qty. Filled only when the underlying document contains exactly one distinct product; empty otherwise.',Updated=TO_TIMESTAMP('2026-06-22 14:29:38.905000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585041 AND AD_Language='en_US'
;

-- 2026-06-22T14:29:38.956Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-06-22T14:29:42.564Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585041,'en_US')
;

-- Column: M_ShippingPackage.TransportProductNos
-- 2026-06-22T14:30:39.698Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592872,585039,0,10,540031,'XX','TransportProductNos','(case when @JoinTableNameOrAliasIncludingDot@M_InOut_ID is not null then (select string_agg(distinct p.Value, '', '' order by p.Value) from m_inoutline iol join m_product p on p.M_Product_ID = iol.M_Product_ID where iol.M_InOut_ID = @JoinTableNameOrAliasIncludingDot@M_InOut_ID and iol.IsActive = ''Y'' and iol.IsPackagingMaterial = ''N'' and iol.M_Product_ID is not null) when @JoinTableNameOrAliasIncludingDot@C_Order_ID is not null then (select string_agg(distinct p.Value, '', '' order by p.Value) from c_orderline ol join m_product p on p.M_Product_ID = ol.M_Product_ID where ol.C_Order_ID = @JoinTableNameOrAliasIncludingDot@C_Order_ID and ol.IsActive = ''Y'' and ol.IsPackagingMaterial = ''N'' and ol.M_Product_ID is not null) end)',TO_TIMESTAMP('2026-06-22 14:30:39.269000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Kommagetrennte Liste aller unterschiedlichen Artikelnummern des zugrunde liegenden Belegs: der Lieferschein-Positionen, falls das Packstück einen Lieferschein referenziert, sonst der Auftrags-Positionen.','METAS_SHIPPING',0,30,'Kommagetrennte Liste aller unterschiedlichen Artikelnummern des zugrunde liegenden Belegs: der Lieferschein-Positionen, falls das Packstück einen Lieferschein referenziert, sonst der Auftrags-Positionen.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N',0,'Produktnummer',0,0,TO_TIMESTAMP('2026-06-22 14:30:39.269000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-06-22T14:30:39.750Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592872 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-22T14:30:39.852Z
/* DDL */  select update_Column_Translation_From_AD_Element(585039)
;

-- Column: M_ShippingPackage.TransportQtys
-- 2026-06-22T14:31:27.154Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592873,585040,0,29,540031,'XX','TransportQtys','(case when @JoinTableNameOrAliasIncludingDot@M_InOut_ID is not null then (select case when count(distinct iol.M_Product_ID) = 1 then sum(iol.QtyEntered) end from m_inoutline iol where iol.M_InOut_ID = @JoinTableNameOrAliasIncludingDot@M_InOut_ID and iol.IsActive = ''Y'' and iol.IsPackagingMaterial = ''N'' and iol.M_Product_ID is not null) when @JoinTableNameOrAliasIncludingDot@C_Order_ID is not null then (select case when count(distinct ol.M_Product_ID) = 1 then sum(ol.QtyEntered) end from c_orderline ol where ol.C_Order_ID = @JoinTableNameOrAliasIncludingDot@C_Order_ID and ol.IsActive = ''Y'' and ol.IsPackagingMaterial = ''N'' and ol.M_Product_ID is not null) end)',TO_TIMESTAMP('2026-06-22 14:31:26.744000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Gesamtmenge (Summe der erfassten Menge) des zugrunde liegenden Belegs; nur befüllt, wenn der Beleg genau einen Artikel enthält. Bei mehreren Artikeln bleibt die Spalte leer.','METAS_SHIPPING',0,10,'Gesamtmenge (Summe der erfassten Menge) des zugrunde liegenden Belegs; nur befüllt, wenn der Beleg genau einen Artikel enthält. Bei mehreren Artikeln bleibt die Spalte leer.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N',0,'Menge',0,0,TO_TIMESTAMP('2026-06-22 14:31:26.744000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-06-22T14:31:27.204Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592873 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-22T14:31:28.071Z
/* DDL */  select update_Column_Translation_From_AD_Element(585040)
;

-- 2026-06-22T14:33:18.108Z
UPDATE AD_Element SET ColumnName='TransportUOM_ID',Updated=TO_TIMESTAMP('2026-06-22 14:33:18.108000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585041
;

-- 2026-06-22T14:33:18.158Z
UPDATE AD_Column SET ColumnName='TransportUOM_ID' WHERE AD_Element_ID=585041
;

-- 2026-06-22T14:33:18.207Z
UPDATE AD_Process_Para SET ColumnName='TransportUOM_ID' WHERE AD_Element_ID=585041
;

-- 2026-06-22T14:33:18.400Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585041,'de_DE')
;

-- Column: M_ShippingPackage.TransportUOM_ID
-- 2026-06-22T14:34:16.843Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592874,585041,0,19,540031,'XX','TransportUOM_ID',TO_TIMESTAMP('2026-06-22 14:34:16.247000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Maßeinheit zur Menge. Nur befüllt, wenn der Beleg genau einen Artikel enthält; sonst leer.','METAS_SHIPPING',0,10,'Maßeinheit zur Menge. Nur befüllt, wenn der Beleg genau einen Artikel enthält; sonst leer.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Maßeinheit',0,0,TO_TIMESTAMP('2026-06-22 14:34:16.247000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-06-22T14:34:16.893Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592874 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-22T14:34:17.279Z
/* DDL */  select update_Column_Translation_From_AD_Element(585041)
;

-- Column: M_ShippingPackage.TransportUOM_ID
-- 2026-06-22T14:35:26.002Z
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=541960, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2026-06-22 14:35:26.002000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592874
;

-- Column: M_ShippingPackage.TransportUOM_ID
-- 2026-06-22T14:38:28.973Z
UPDATE AD_Column SET ColumnSQL='(case when @JoinTableNameOrAliasIncludingDot@M_InOut_ID is not null then (select case when count(distinct iol.M_Product_ID) = 1 then max(iol.C_UOM_ID) end from m_inoutline iol where iol.M_InOut_ID = @JoinTableNameOrAliasIncludingDot@M_InOut_ID and iol.IsActive = ''Y'' and iol.IsPackagingMaterial = ''N'' and iol.M_Product_ID is not null) when @JoinTableNameOrAliasIncludingDot@C_Order_ID is not null then (select case when count(distinct ol.M_Product_ID) = 1 then max(ol.C_UOM_ID) end from c_orderline ol where ol.C_Order_ID = @JoinTableNameOrAliasIncludingDot@C_Order_ID and ol.IsActive = ''Y'' and ol.IsPackagingMaterial = ''N'' and ol.M_Product_ID is not null) end)', IsLazyLoading='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2026-06-22 14:38:28.972000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592874
;

-- Field: Transport Auftrag(540020,METAS_SHIPPING) -> Versandpackung(540097,METAS_SHIPPING) -> Auftragsposition
-- Column: M_ShippingPackage.C_OrderLine_ID
-- 2026-06-22T14:39:25.389Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590587,781233,0,540097,TO_TIMESTAMP('2026-06-22 14:39:24.914000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftragsposition',10,'METAS_SHIPPING','"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.','Y','N','N','N','N','N','N','N','Auftragsposition',TO_TIMESTAMP('2026-06-22 14:39:24.914000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-22T14:39:25.440Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781233 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-06-22T14:39:25.491Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(561)
;

-- 2026-06-22T14:39:25.582Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781233
;

-- 2026-06-22T14:39:25.636Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(781233)
;

-- Field: Transport Auftrag(540020,METAS_SHIPPING) -> Versandpackung(540097,METAS_SHIPPING) -> Produktnummer
-- Column: M_ShippingPackage.TransportProductNos
-- 2026-06-22T14:39:26.172Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592872,781234,0,540097,TO_TIMESTAMP('2026-06-22 14:39:25.745000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Kommagetrennte Liste aller unterschiedlichen Artikelnummern des zugrunde liegenden Belegs: der Lieferschein-Positionen, falls das Packstück einen Lieferschein referenziert, sonst der Auftrags-Positionen.',30,'METAS_SHIPPING','Kommagetrennte Liste aller unterschiedlichen Artikelnummern des zugrunde liegenden Belegs: der Lieferschein-Positionen, falls das Packstück einen Lieferschein referenziert, sonst der Auftrags-Positionen.','Y','N','N','N','N','N','N','N','Produktnummer',TO_TIMESTAMP('2026-06-22 14:39:25.745000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-22T14:39:26.221Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781234 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-06-22T14:39:26.273Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(585039)
;

-- 2026-06-22T14:39:26.326Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781234
;

-- 2026-06-22T14:39:26.376Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(781234)
;

-- Field: Transport Auftrag(540020,METAS_SHIPPING) -> Versandpackung(540097,METAS_SHIPPING) -> Menge
-- Column: M_ShippingPackage.TransportQtys
-- 2026-06-22T14:39:26.911Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592873,781235,0,540097,TO_TIMESTAMP('2026-06-22 14:39:26.478000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gesamtmenge (Summe der erfassten Menge) des zugrunde liegenden Belegs; nur befüllt, wenn der Beleg genau einen Artikel enthält. Bei mehreren Artikeln bleibt die Spalte leer.',10,'METAS_SHIPPING','Gesamtmenge (Summe der erfassten Menge) des zugrunde liegenden Belegs; nur befüllt, wenn der Beleg genau einen Artikel enthält. Bei mehreren Artikeln bleibt die Spalte leer.','Y','N','N','N','N','N','N','N','Menge',TO_TIMESTAMP('2026-06-22 14:39:26.478000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-22T14:39:26.962Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781235 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-06-22T14:39:27.015Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(585040)
;

-- 2026-06-22T14:39:27.069Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781235
;

-- 2026-06-22T14:39:27.119Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(781235)
;

-- Field: Transport Auftrag(540020,METAS_SHIPPING) -> Versandpackung(540097,METAS_SHIPPING) -> Maßeinheit
-- Column: M_ShippingPackage.TransportUOM_ID
-- 2026-06-22T14:39:27.648Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592874,781236,0,540097,TO_TIMESTAMP('2026-06-22 14:39:27.218000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit zur Menge. Nur befüllt, wenn der Beleg genau einen Artikel enthält; sonst leer.',10,'METAS_SHIPPING','Maßeinheit zur Menge. Nur befüllt, wenn der Beleg genau einen Artikel enthält; sonst leer.','Y','N','N','N','N','N','N','N','Maßeinheit',TO_TIMESTAMP('2026-06-22 14:39:27.218000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-22T14:39:27.700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781236 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-06-22T14:39:27.752Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(585041)
;

-- 2026-06-22T14:39:27.807Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781236
;

-- 2026-06-22T14:39:27.856Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(781236)
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Versandpackung(540097,METAS_SHIPPING) -> main -> 10 -> default.Produktnummer
-- Column: M_ShippingPackage.TransportProductNos
-- 2026-06-22T14:44:21.875Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,781234,0,540097,540666,652352,'F',TO_TIMESTAMP('2026-06-22 14:44:21.487000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Kommagetrennte Liste aller unterschiedlichen Artikelnummern des zugrunde liegenden Belegs: der Lieferschein-Positionen, falls das Packstück einen Lieferschein referenziert, sonst der Auftrags-Positionen.','Kommagetrennte Liste aller unterschiedlichen Artikelnummern des zugrunde liegenden Belegs: der Lieferschein-Positionen, falls das Packstück einen Lieferschein referenziert, sonst der Auftrags-Positionen.','Y','N','N','Y','N','N','N',0,'Produktnummer',13,0,0,TO_TIMESTAMP('2026-06-22 14:44:21.487000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Versandpackung(540097,METAS_SHIPPING) -> main -> 10 -> default.Menge
-- Column: M_ShippingPackage.TransportQtys
-- 2026-06-22T14:48:54.360Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,781235,0,540097,540666,652353,'F',TO_TIMESTAMP('2026-06-22 14:48:53.977000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gesamtmenge (Summe der erfassten Menge) des zugrunde liegenden Belegs; nur befüllt, wenn der Beleg genau einen Artikel enthält. Bei mehreren Artikeln bleibt die Spalte leer.','Gesamtmenge (Summe der erfassten Menge) des zugrunde liegenden Belegs; nur befüllt, wenn der Beleg genau einen Artikel enthält. Bei mehreren Artikeln bleibt die Spalte leer.','Y','N','N','Y','N','N','N',0,'Menge',14,0,0,TO_TIMESTAMP('2026-06-22 14:48:53.977000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Versandpackung(540097,METAS_SHIPPING) -> main -> 10 -> default.Maßeinheit
-- Column: M_ShippingPackage.TransportUOM_ID
-- 2026-06-22T14:49:26.195Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,781236,0,540097,540666,652354,'F',TO_TIMESTAMP('2026-06-22 14:49:25.837000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit zur Menge. Nur befüllt, wenn der Beleg genau einen Artikel enthält; sonst leer.','Maßeinheit zur Menge. Nur befüllt, wenn der Beleg genau einen Artikel enthält; sonst leer.','Y','N','N','Y','N','N','N',0,'Maßeinheit',15,0,0,TO_TIMESTAMP('2026-06-22 14:49:25.837000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Versandpackung(540097,METAS_SHIPPING) -> main -> 10 -> default.Produktnummer
-- Column: M_ShippingPackage.TransportProductNos
-- 2026-06-22T14:49:52.483Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2026-06-22 14:49:52.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652352
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Versandpackung(540097,METAS_SHIPPING) -> main -> 10 -> default.Menge
-- Column: M_ShippingPackage.TransportQtys
-- 2026-06-22T14:49:52.782Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-06-22 14:49:52.782000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652353
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Versandpackung(540097,METAS_SHIPPING) -> main -> 10 -> default.Maßeinheit
-- Column: M_ShippingPackage.TransportUOM_ID
-- 2026-06-22T14:49:53.083Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-06-22 14:49:53.083000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652354
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Versandpackung(540097,METAS_SHIPPING) -> main -> 10 -> default.Abholung
-- Column: M_ShippingPackage.IsToBeFetched
-- 2026-06-22T14:49:53.383Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-06-22 14:49:53.383000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=565326
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Versandpackung(540097,METAS_SHIPPING) -> main -> 10 -> default.Auftrag
-- Column: M_ShippingPackage.C_Order_ID
-- 2026-06-22T14:49:53.682Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-06-22 14:49:53.682000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545696
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Versandpackung(540097,METAS_SHIPPING) -> main -> 10 -> default.Lieferung/Wareneingang
-- Column: M_ShippingPackage.M_InOut_ID
-- 2026-06-22T14:49:53.981Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-06-22 14:49:53.981000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545691
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Versandpackung(540097,METAS_SHIPPING) -> main -> 10 -> default.Kunde
-- Column: M_ShippingPackage.C_BPartner_ID
-- 2026-06-22T14:49:54.281Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-06-22 14:49:54.281000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545693
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Versandpackung(540097,METAS_SHIPPING) -> main -> 10 -> default.LU Anzahl
-- Column: M_ShippingPackage.QtyLU
-- 2026-06-22T14:49:54.582Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-06-22 14:49:54.581000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627380
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Versandpackung(540097,METAS_SHIPPING) -> main -> 10 -> default.TU Anzahl
-- Column: M_ShippingPackage.QtyTU
-- 2026-06-22T14:49:54.883Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-06-22 14:49:54.882000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627381
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Versandpackung(540097,METAS_SHIPPING) -> main -> 10 -> default.Gewicht Packstücke
-- Column: M_ShippingPackage.PackageWeight
-- 2026-06-22T14:49:55.184Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-06-22 14:49:55.183000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545694
;

-- UI Element: Transport Auftrag(540020,METAS_SHIPPING) -> Versandpackung(540097,METAS_SHIPPING) -> main -> 10 -> default.Notiz
-- Column: M_ShippingPackage.Note
-- 2026-06-22T14:49:55.484Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-06-22 14:49:55.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=545697
;

-- AD_SQLColumn_SourceTableColumn: cache invalidation for the three virtual columns on M_ShippingPackage (540031).
-- The columns read from M_InOutLine / C_OrderLine (and M_Product for TransportProductNos); none of those has a
-- direct FK to M_ShippingPackage (the link is indirect via M_InOut_ID / C_Order_ID), so all use the SQL fetch
-- method 'S'. Without these, WebUI grids show stale values until manual reload.
-- IDs from idserver.metas.de: 540210-540216.

-- TransportProductNos (592872) <- M_InOutLine
INSERT INTO AD_SQLColumn_SourceTableColumn
	(AD_SQLColumn_SourceTableColumn_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
	 AD_Table_ID, AD_Column_ID, Source_Table_ID, Sql_GetTargetRecordIdBySourceRecordId, FetchTargetRecordsMethod)
SELECT 540210, 0, 0, 'Y',
	TO_TIMESTAMP('2026-06-22 15:00:00','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-06-22 15:00:00','YYYY-MM-DD HH24:MI:SS'), 100,
	540031, 592872,
	(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_InOutLine'),
	'select distinct sp.M_ShippingPackage_ID from m_shippingpackage sp, m_inoutline iol where iol.M_InOutLine_ID = @Record_ID@ and sp.M_InOut_ID = iol.M_InOut_ID',
	'S'
WHERE NOT EXISTS (SELECT 1 FROM AD_SQLColumn_SourceTableColumn WHERE AD_Column_ID=592872 AND Source_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_InOutLine'))
;

-- TransportProductNos (592872) <- C_OrderLine
INSERT INTO AD_SQLColumn_SourceTableColumn
	(AD_SQLColumn_SourceTableColumn_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
	 AD_Table_ID, AD_Column_ID, Source_Table_ID, Sql_GetTargetRecordIdBySourceRecordId, FetchTargetRecordsMethod)
SELECT 540211, 0, 0, 'Y',
	TO_TIMESTAMP('2026-06-22 15:00:01','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-06-22 15:00:01','YYYY-MM-DD HH24:MI:SS'), 100,
	540031, 592872,
	(SELECT AD_Table_ID FROM AD_Table WHERE TableName='C_OrderLine'),
	'select distinct sp.M_ShippingPackage_ID from m_shippingpackage sp, c_orderline ol where ol.C_OrderLine_ID = @Record_ID@ and sp.C_Order_ID = ol.C_Order_ID',
	'S'
WHERE NOT EXISTS (SELECT 1 FROM AD_SQLColumn_SourceTableColumn WHERE AD_Column_ID=592872 AND Source_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='C_OrderLine'))
;

-- TransportProductNos (592872) <- M_Product (reads p.Value)
INSERT INTO AD_SQLColumn_SourceTableColumn
	(AD_SQLColumn_SourceTableColumn_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
	 AD_Table_ID, AD_Column_ID, Source_Table_ID, Sql_GetTargetRecordIdBySourceRecordId, FetchTargetRecordsMethod)
SELECT 540212, 0, 0, 'Y',
	TO_TIMESTAMP('2026-06-22 15:00:02','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-06-22 15:00:02','YYYY-MM-DD HH24:MI:SS'), 100,
	540031, 592872,
	(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_Product'),
	'select distinct sp.M_ShippingPackage_ID from m_shippingpackage sp where sp.M_InOut_ID in (select iol.M_InOut_ID from m_inoutline iol where iol.M_Product_ID = @Record_ID@) or sp.C_Order_ID in (select ol.C_Order_ID from c_orderline ol where ol.M_Product_ID = @Record_ID@)',
	'S'
WHERE NOT EXISTS (SELECT 1 FROM AD_SQLColumn_SourceTableColumn WHERE AD_Column_ID=592872 AND Source_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_Product'))
;

-- TransportQtys (592873) <- M_InOutLine
INSERT INTO AD_SQLColumn_SourceTableColumn
	(AD_SQLColumn_SourceTableColumn_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
	 AD_Table_ID, AD_Column_ID, Source_Table_ID, Sql_GetTargetRecordIdBySourceRecordId, FetchTargetRecordsMethod)
SELECT 540213, 0, 0, 'Y',
	TO_TIMESTAMP('2026-06-22 15:00:03','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-06-22 15:00:03','YYYY-MM-DD HH24:MI:SS'), 100,
	540031, 592873,
	(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_InOutLine'),
	'select distinct sp.M_ShippingPackage_ID from m_shippingpackage sp, m_inoutline iol where iol.M_InOutLine_ID = @Record_ID@ and sp.M_InOut_ID = iol.M_InOut_ID',
	'S'
WHERE NOT EXISTS (SELECT 1 FROM AD_SQLColumn_SourceTableColumn WHERE AD_Column_ID=592873 AND Source_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_InOutLine'))
;

-- TransportQtys (592873) <- C_OrderLine
INSERT INTO AD_SQLColumn_SourceTableColumn
	(AD_SQLColumn_SourceTableColumn_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
	 AD_Table_ID, AD_Column_ID, Source_Table_ID, Sql_GetTargetRecordIdBySourceRecordId, FetchTargetRecordsMethod)
SELECT 540214, 0, 0, 'Y',
	TO_TIMESTAMP('2026-06-22 15:00:04','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-06-22 15:00:04','YYYY-MM-DD HH24:MI:SS'), 100,
	540031, 592873,
	(SELECT AD_Table_ID FROM AD_Table WHERE TableName='C_OrderLine'),
	'select distinct sp.M_ShippingPackage_ID from m_shippingpackage sp, c_orderline ol where ol.C_OrderLine_ID = @Record_ID@ and sp.C_Order_ID = ol.C_Order_ID',
	'S'
WHERE NOT EXISTS (SELECT 1 FROM AD_SQLColumn_SourceTableColumn WHERE AD_Column_ID=592873 AND Source_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='C_OrderLine'))
;

-- TransportUOM_ID (592874) <- M_InOutLine
INSERT INTO AD_SQLColumn_SourceTableColumn
	(AD_SQLColumn_SourceTableColumn_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
	 AD_Table_ID, AD_Column_ID, Source_Table_ID, Sql_GetTargetRecordIdBySourceRecordId, FetchTargetRecordsMethod)
SELECT 540215, 0, 0, 'Y',
	TO_TIMESTAMP('2026-06-22 15:00:05','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-06-22 15:00:05','YYYY-MM-DD HH24:MI:SS'), 100,
	540031, 592874,
	(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_InOutLine'),
	'select distinct sp.M_ShippingPackage_ID from m_shippingpackage sp, m_inoutline iol where iol.M_InOutLine_ID = @Record_ID@ and sp.M_InOut_ID = iol.M_InOut_ID',
	'S'
WHERE NOT EXISTS (SELECT 1 FROM AD_SQLColumn_SourceTableColumn WHERE AD_Column_ID=592874 AND Source_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_InOutLine'))
;

-- TransportUOM_ID (592874) <- C_OrderLine
INSERT INTO AD_SQLColumn_SourceTableColumn
	(AD_SQLColumn_SourceTableColumn_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
	 AD_Table_ID, AD_Column_ID, Source_Table_ID, Sql_GetTargetRecordIdBySourceRecordId, FetchTargetRecordsMethod)
SELECT 540216, 0, 0, 'Y',
	TO_TIMESTAMP('2026-06-22 15:00:06','YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-06-22 15:00:06','YYYY-MM-DD HH24:MI:SS'), 100,
	540031, 592874,
	(SELECT AD_Table_ID FROM AD_Table WHERE TableName='C_OrderLine'),
	'select distinct sp.M_ShippingPackage_ID from m_shippingpackage sp, c_orderline ol where ol.C_OrderLine_ID = @Record_ID@ and sp.C_Order_ID = ol.C_Order_ID',
	'S'
WHERE NOT EXISTS (SELECT 1 FROM AD_SQLColumn_SourceTableColumn WHERE AD_Column_ID=592874 AND Source_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='C_OrderLine'))
;

