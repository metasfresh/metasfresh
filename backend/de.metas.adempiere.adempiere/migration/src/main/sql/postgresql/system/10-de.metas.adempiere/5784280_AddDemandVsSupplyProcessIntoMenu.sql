-- Run mode: SWING_CLIENT

-- 2026-01-16T12:08:08.146Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584409,0,TO_TIMESTAMP('2026-01-16 12:08:07.967000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Supply vs. Demand - Product Info (Excel)','Supply vs. Demand - Product Info (Excel)',TO_TIMESTAMP('2026-01-16 12:08:07.967000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-16T12:08:08.153Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584409 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2026-01-16T12:08:54.662Z
UPDATE AD_Element_Trl SET Description='Vergleichende Analyse des Lagerangebots (aktueller Bestand, Reserviert und Zulauf) gegenüber dem tatsächlichen Verkaufsbedarf (verkaufte Menge) für einen definierten Zeitraum. Enthält lieferantenspezifische Einkaufspreise und interne Einstandspreise zur Unterstützung fundierter Beschaffungsentscheidungen.', IsTranslated='Y', Name='Bestand vs. Bedarf - Produktinfo (Excel)', PrintName='Bestand vs. Bedarf - Produktinfo (Excel)',Updated=TO_TIMESTAMP('2026-01-16 12:08:54.662000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584409 AND AD_Language='de_CH'
;

-- 2026-01-16T12:08:54.663Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-16T12:08:54.881Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584409,'de_CH')
;

-- Element: null
-- 2026-01-16T12:09:08.886Z
UPDATE AD_Element_Trl SET Description='Vergleichende Analyse des Lagerangebots (aktueller Bestand, Reserviert und Zulauf) gegenüber dem tatsächlichen Verkaufsbedarf (verkaufte Menge) für einen definierten Zeitraum. Enthält lieferantenspezifische Einkaufspreise und interne Einstandspreise zur Unterstützung fundierter Beschaffungsentscheidungen.', IsTranslated='Y', Name='Bestand vs. Bedarf - Produktinfo (Excel)', PrintName='Bestand vs. Bedarf - Produktinfo (Excel)',Updated=TO_TIMESTAMP('2026-01-16 12:09:08.885000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584409 AND AD_Language='de_DE'
;

-- 2026-01-16T12:09:08.887Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-16T12:09:09.295Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584409,'de_DE')
;

-- 2026-01-16T12:09:09.296Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584409,'de_DE')
;

-- Element: null
-- 2026-01-16T12:09:40.354Z
UPDATE AD_Element_Trl SET Description='Comparative analysis of warehouse supply (Current Stock, Reserved, and Inbound) against actual sales demand (Qty Sold) for a defined period. Includes vendor-specific purchase prices and internal cost prices to support data-driven procurement decisions.', IsTranslated='Y',Updated=TO_TIMESTAMP('2026-01-16 12:09:40.353000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584409 AND AD_Language='en_US'
;

-- 2026-01-16T12:09:40.355Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-16T12:09:40.547Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584409,'en_US')
;

-- Name: Bestand vs. Bedarf - Produktinfo (Excel)
-- Action Type: P
-- Process: SupplyVsDemand_Product_Info(de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)
-- 2026-01-16T12:09:57.616Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,584409,542292,0,585558,TO_TIMESTAMP('2026-01-16 12:09:57.462000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Vergleichende Analyse des Lagerangebots (aktueller Bestand, Reserviert und Zulauf) gegenüber dem tatsächlichen Verkaufsbedarf (verkaufte Menge) für einen definierten Zeitraum. Enthält lieferantenspezifische Einkaufspreise und interne Einstandspreise zur Unterstützung fundierter Beschaffungsentscheidungen.','de.metas.fresh','SupplyVsDemand_Product_Info','Y','N','N','N','N','Bestand vs. Bedarf - Produktinfo (Excel)',TO_TIMESTAMP('2026-01-16 12:09:57.462000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-16T12:09:57.623Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542292 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2026-01-16T12:09:57.626Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542292, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542292)
;

-- 2026-01-16T12:09:57.635Z
/* DDL */  select update_menu_translation_from_ad_element(584409)
;

-- Reordering children of `Product Management`
-- Node name: `Replenishment (M_Material_Needs_Planner_V)`
-- 2026-01-16T12:10:05.839Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542202 AND AD_Tree_ID=10
;

-- Node name: `Product Data Capture (Jasper) (@PREFIX@de/metas/docs/label/dataentry/JSON_POC_Report.jasper)`
-- 2026-01-16T12:10:05.841Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541293 AND AD_Tree_ID=10
;

-- Node name: `Product (M_Product)`
-- 2026-01-16T12:10:05.842Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000034 AND AD_Tree_ID=10
;

-- Node name: `Customs Tariff (M_CustomsTariff)`
-- 2026-01-16T12:10:05.843Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541391 AND AD_Tree_ID=10
;

-- Node name: `Commodity Number (M_CommodityNumber)`
-- 2026-01-16T12:10:05.844Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541468 AND AD_Tree_ID=10
;

-- Node name: `Bill of Material Version (PP_Product_BOM)`
-- 2026-01-16T12:10:05.845Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000092 AND AD_Tree_ID=10
;

-- Node name: `Components of the BOM & Formula (PP_Product_BOMLine)`
-- 2026-01-16T12:10:05.846Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541376 AND AD_Tree_ID=10
;

-- Node name: `Discount Schema (M_DiscountSchema)`
-- 2026-01-16T12:10:05.848Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000093 AND AD_Tree_ID=10
;

-- Node name: `Discount Rows (M_DiscountSchemaBreak)`
-- 2026-01-16T12:10:05.848Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541230 AND AD_Tree_ID=10
;

-- Node name: `Lot control (M_Product_LotNumber_Quarantine)`
-- 2026-01-16T12:10:05.849Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541062 AND AD_Tree_ID=10
;

-- Node name: `BPartner Product statistics (C_BPartner_Product_Stats)`
-- 2026-01-16T12:10:05.850Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541194 AND AD_Tree_ID=10
;

-- Node name: `Hazard Symbol (M_HazardSymbol)`
-- 2026-01-16T12:10:05.851Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541870 AND AD_Tree_ID=10
;

-- Node name: `Product Certification (AD_Product_Certification_v)`
-- 2026-01-16T12:10:05.852Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541904 AND AD_Tree_ID=10
;

-- Node name: `Hazard Symbol Trl (M_HazardSymbol_Trl)`
-- 2026-01-16T12:10:05.853Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541871 AND AD_Tree_ID=10
;

-- Node name: `Download Partner Products (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2026-01-16T12:10:05.854Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541411 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2026-01-16T12:10:05.854Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000044 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2026-01-16T12:10:05.856Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000035 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2026-01-16T12:10:05.857Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000037 AND AD_Tree_ID=10
;

-- Node name: `Package-Licensing`
-- 2026-01-16T12:10:05.858Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542248 AND AD_Tree_ID=10
;

-- Node name: `Produkt Marktpläze (M_Product_Marketplace)`
-- 2026-01-16T12:10:05.859Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541864 AND AD_Tree_ID=10
;

-- Node name: `Bill of Material (PP_Product_BOMVersions)`
-- 2026-01-16T12:10:05.860Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541824 AND AD_Tree_ID=10
;

-- Node name: `Additives translation (M_Additive_Trl)`
-- 2026-01-16T12:10:05.860Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541873 AND AD_Tree_ID=10
;

-- Node name: `Waste Management`
-- 2026-01-16T12:10:05.861Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542270 AND AD_Tree_ID=10
;

-- Node name: `Food`
-- 2026-01-16T12:10:05.862Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542272 AND AD_Tree_ID=10
;

-- Node name: `Bestand vs. Bedarf - Produktinfo (Excel)`
-- 2026-01-16T12:10:05.863Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542292 AND AD_Tree_ID=10
;

-- Reordering children of `Product Management`
-- Node name: `Replenishment (M_Material_Needs_Planner_V)`
-- 2026-01-16T12:10:16.082Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542202 AND AD_Tree_ID=10
;

-- Node name: `Product Data Capture (Jasper) (@PREFIX@de/metas/docs/label/dataentry/JSON_POC_Report.jasper)`
-- 2026-01-16T12:10:16.083Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541293 AND AD_Tree_ID=10
;

-- Node name: `Product (M_Product)`
-- 2026-01-16T12:10:16.084Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000034 AND AD_Tree_ID=10
;

-- Node name: `Customs Tariff (M_CustomsTariff)`
-- 2026-01-16T12:10:16.085Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541391 AND AD_Tree_ID=10
;

-- Node name: `Commodity Number (M_CommodityNumber)`
-- 2026-01-16T12:10:16.086Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541468 AND AD_Tree_ID=10
;

-- Node name: `Bill of Material Version (PP_Product_BOM)`
-- 2026-01-16T12:10:16.087Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000092 AND AD_Tree_ID=10
;

-- Node name: `Components of the BOM & Formula (PP_Product_BOMLine)`
-- 2026-01-16T12:10:16.088Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541376 AND AD_Tree_ID=10
;

-- Node name: `Discount Schema (M_DiscountSchema)`
-- 2026-01-16T12:10:16.089Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000093 AND AD_Tree_ID=10
;

-- Node name: `Discount Rows (M_DiscountSchemaBreak)`
-- 2026-01-16T12:10:16.090Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541230 AND AD_Tree_ID=10
;

-- Node name: `Lot control (M_Product_LotNumber_Quarantine)`
-- 2026-01-16T12:10:16.091Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541062 AND AD_Tree_ID=10
;

-- Node name: `BPartner Product statistics (C_BPartner_Product_Stats)`
-- 2026-01-16T12:10:16.091Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541194 AND AD_Tree_ID=10
;

-- Node name: `Hazard Symbol (M_HazardSymbol)`
-- 2026-01-16T12:10:16.092Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541870 AND AD_Tree_ID=10
;

-- Node name: `Product Certification (AD_Product_Certification_v)`
-- 2026-01-16T12:10:16.093Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541904 AND AD_Tree_ID=10
;

-- Node name: `Hazard Symbol Trl (M_HazardSymbol_Trl)`
-- 2026-01-16T12:10:16.094Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541871 AND AD_Tree_ID=10
;

-- Node name: `Download Partner Products (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2026-01-16T12:10:16.095Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541411 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2026-01-16T12:10:16.096Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000044 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2026-01-16T12:10:16.096Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000035 AND AD_Tree_ID=10
;

-- Node name: `Sales`
-- 2026-01-16T12:10:16.097Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000010 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2026-01-16T12:10:16.099Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000037 AND AD_Tree_ID=10
;

-- Node name: `Package-Licensing`
-- 2026-01-16T12:10:16.099Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542248 AND AD_Tree_ID=10
;

-- Node name: `Produkt Marktpläze (M_Product_Marketplace)`
-- 2026-01-16T12:10:16.100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541864 AND AD_Tree_ID=10
;

-- Node name: `Bill of Material (PP_Product_BOMVersions)`
-- 2026-01-16T12:10:16.101Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541824 AND AD_Tree_ID=10
;

-- Node name: `Additives translation (M_Additive_Trl)`
-- 2026-01-16T12:10:16.102Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541873 AND AD_Tree_ID=10
;

-- Node name: `Waste Management`
-- 2026-01-16T12:10:16.103Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542270 AND AD_Tree_ID=10
;

-- Node name: `Food`
-- 2026-01-16T12:10:16.104Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542272 AND AD_Tree_ID=10
;

-- Node name: `Bestand vs. Bedarf - Produktinfo (Excel)`
-- 2026-01-16T12:10:16.104Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542292 AND AD_Tree_ID=10
;

-- Reordering children of `Reports`
-- Node name: `Bestand vs. Bedarf - Produktinfo (Excel)`
-- 2026-01-16T12:10:19.712Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000035, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542292 AND AD_Tree_ID=10
;


-- 2026-01-16T12:39:42.200Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584410,0,'QtySold',TO_TIMESTAMP('2026-01-16 12:39:42.063000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','QtySold','QtySold',TO_TIMESTAMP('2026-01-16 12:39:42.063000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-16T12:39:42.206Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584410 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: QtySold
-- 2026-01-16T12:40:04.242Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Verkaufsmenge', PrintName='Verkaufsmenge',Updated=TO_TIMESTAMP('2026-01-16 12:40:04.242000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584410 AND AD_Language='de_CH'
;

-- 2026-01-16T12:40:04.243Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-16T12:40:04.450Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584410,'de_CH')
;

-- Element: QtySold
-- 2026-01-16T12:40:09.202Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Verkaufsmenge', PrintName='Verkaufsmenge',Updated=TO_TIMESTAMP('2026-01-16 12:40:09.202000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584410 AND AD_Language='de_DE'
;

-- 2026-01-16T12:40:09.203Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-16T12:40:09.688Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584410,'de_DE')
;

-- 2026-01-16T12:40:09.689Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584410,'de_DE')
;

-- Element: QtySold
-- 2026-01-16T12:40:18.873Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Qty Sold', PrintName='Qty Sold',Updated=TO_TIMESTAMP('2026-01-16 12:40:18.873000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584410 AND AD_Language='en_US'
;

-- 2026-01-16T12:40:18.875Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-16T12:40:19.082Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584410,'en_US')
;

-- 2026-01-16T12:40:33.150Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584411,0,'QtyPurchased',TO_TIMESTAMP('2026-01-16 12:40:33.019000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','QtyPurchased','QtyPurchased',TO_TIMESTAMP('2026-01-16 12:40:33.019000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-16T12:40:33.152Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584411 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: QtyPurchased
-- 2026-01-16T12:40:42.474Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Qty Purchased', PrintName='Qty Purchased',Updated=TO_TIMESTAMP('2026-01-16 12:40:42.474000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584411 AND AD_Language='en_US'
;

-- 2026-01-16T12:40:42.475Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-16T12:40:42.659Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584411,'en_US')
;

-- Element: QtyPurchased
-- 2026-01-16T12:41:04.822Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Bestellmenge', PrintName='Bestellmenge',Updated=TO_TIMESTAMP('2026-01-16 12:41:04.822000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584411 AND AD_Language='de_CH'
;

-- 2026-01-16T12:41:04.823Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-16T12:41:05.017Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584411,'de_CH')
;

-- Element: QtyPurchased
-- 2026-01-16T12:41:11.826Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Bestellmenge', PrintName='Bestellmenge',Updated=TO_TIMESTAMP('2026-01-16 12:41:11.826000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584411 AND AD_Language='de_DE'
;

-- 2026-01-16T12:41:11.827Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-16T12:41:12.113Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584411,'de_DE')
;

-- 2026-01-16T12:41:12.114Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584411,'de_DE')
;

-- Element: QtyPurchased
-- 2026-01-16T13:14:57.948Z
UPDATE AD_Element_Trl SET Name='Im Zulauf', PrintName='Im Zulauf',Updated=TO_TIMESTAMP('2026-01-16 13:14:57.948000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584411 AND AD_Language='de_DE'
;

-- 2026-01-16T13:14:57.951Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-16T13:14:58.922Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584411,'de_DE')
;

-- 2026-01-16T13:14:58.925Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584411,'de_DE')
;

-- Element: QtyPurchased
-- 2026-01-16T13:15:19.513Z
UPDATE AD_Element_Trl SET Name='Im Zulauf', PrintName='Im Zulauf',Updated=TO_TIMESTAMP('2026-01-16 13:15:19.513000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584411 AND AD_Language='de_CH'
;

-- 2026-01-16T13:15:19.516Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-16T13:15:19.732Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584411,'de_CH')
;

-- Element: UOM
-- 2026-01-16T13:16:24.398Z
UPDATE AD_Element_Trl SET Name='Einheit', PrintName='Einheit',Updated=TO_TIMESTAMP('2026-01-16 13:16:24.398000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=577544 AND AD_Language='de_CH'
;

-- 2026-01-16T13:16:24.400Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-16T13:16:24.606Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577544,'de_CH')
;

-- Element: UOM
-- 2026-01-16T13:16:28.625Z
UPDATE AD_Element_Trl SET Name='Einheit', PrintName='Einheit',Updated=TO_TIMESTAMP('2026-01-16 13:16:28.625000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=577544 AND AD_Language='de_DE'
;

-- 2026-01-16T13:16:28.627Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-16T13:16:28.952Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(577544,'de_DE')
;

-- 2026-01-16T13:16:28.956Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577544,'de_DE')
;

-- Element: QtySold
-- 2026-01-16T13:18:13.840Z
UPDATE AD_Element_Trl SET Name='offener Auftrag', PrintName='offener Auftrag',Updated=TO_TIMESTAMP('2026-01-16 13:18:13.839000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584410 AND AD_Language='de_DE'
;

-- 2026-01-16T13:18:13.841Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-16T13:18:14.148Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584410,'de_DE')
;

-- 2026-01-16T13:18:14.149Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584410,'de_DE')
;

-- Element: QtySold
-- 2026-01-16T13:18:17.746Z
UPDATE AD_Element_Trl SET Name='offener Auftrag', PrintName='offener Auftrag',Updated=TO_TIMESTAMP('2026-01-16 13:18:17.746000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584410 AND AD_Language='de_CH'
;

-- 2026-01-16T13:18:17.747Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-16T13:18:17.941Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584410,'de_CH')
;

