-- nShift Lieferweg: make carrier/Lieferweg selectable on the C_Order header.
-- Mirrors the existing M_ShipmentSchedule carrier model (5773340_added_CarrierAdvice_tables.sql):
--   * two header columns on C_Order: Carrier_Product_ID + Carrier_Goods_Type_ID (Table reference to
--     Carrier_Product / Carrier_Goods_Type), reusing the existing carrier AD_Elements 584116 / 584112.
--   * a bridge table C_Order_Carrier_Service (many services per order), mirroring the
--     M_ShipmentSchedule_Carrier_Service bridge (nullable C_Order_ID + Carrier_Service_ID FKs + audit cols).
--   * two NEW SQL AD_Val_Rules constraining goods type / service to the SELECTED carrier product's
--     allocations (Carrier_Product_GoodsType_Alloc / Carrier_Product_Service_Alloc), keyed by
--     @Carrier_Product_ID@ — set column-level via AD_Column.AD_Val_Rule_ID so every field inherits them.
--     New rules (not the shared 540750/540757, which are reused by M_ShipmentSchedule and the advise process).
--
-- IDs allocated from idserver.metas.de on 2026-07-23:
--   AD_MigrationScript 5815660
--   AD_Table   542628 (C_Order_Carrier_Service)
--   AD_Sequence 556613 (C_Order_Carrier_Service)
--   AD_Element 585125 (C_Order_Carrier_Service_ID)
--   AD_Val_Rule 540793 (Carrier_Goods_Type_ID_for_Carrier_Product_ID)
--   AD_Val_Rule 540794 (Carrier_Service_ID_for_Carrier_Product_ID)
--   AD_Column  592978..592989 (bridge cols + 2 C_Order cols)

-- ---------------------------------------------------------------------------------------------------
-- 1) Bridge table C_Order_Carrier_Service  (mirror of M_ShipmentSchedule_Carrier_Service, AD_Table 542544)
-- ---------------------------------------------------------------------------------------------------

INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('3',0,0,0,542628 /*From ID Server*/,'A','N',TO_TIMESTAMP('2026-07-23 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'A','D','N','Y','N','Y','Y','N','N','N','N','N',0,'Lieferweg-Service-Zuweisung','NP','L','C_Order_Carrier_Service','DTI',TO_TIMESTAMP('2026-07-23 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,0,'A')
;

INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542628 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNo,Updated,UpdatedBy) VALUES (0,0,556613 /*From ID Server*/,TO_TIMESTAMP('2026-07-23 10:00:01','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_Order_Carrier_Service',1,'Y','N','Y','Y','C_Order_Carrier_Service',1000000,TO_TIMESTAMP('2026-07-23 10:00:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_Order_Carrier_Service.AD_Client_ID
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592978 /*From ID Server*/,102,0,19,542628,'AD_Client_ID',TO_TIMESTAMP('2026-07-23 10:00:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant','NP',0,0,TO_TIMESTAMP('2026-07-23 10:00:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592978 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */ select update_Column_Translation_From_AD_Element(102)
;

-- Column: C_Order_Carrier_Service.AD_Org_ID
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592979 /*From ID Server*/,113,0,30,542628,'AD_Org_ID',TO_TIMESTAMP('2026-07-23 10:00:03','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion','NP',10,0,TO_TIMESTAMP('2026-07-23 10:00:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592979 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */ select update_Column_Translation_From_AD_Element(113)
;

-- Column: C_Order_Carrier_Service.Created
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592980 /*From ID Server*/,245,0,16,542628,'Created',TO_TIMESTAMP('2026-07-23 10:00:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt','NP',0,0,TO_TIMESTAMP('2026-07-23 10:00:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592980 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */ select update_Column_Translation_From_AD_Element(245)
;

-- Column: C_Order_Carrier_Service.CreatedBy
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592981 /*From ID Server*/,246,0,18,110,542628,'CreatedBy',TO_TIMESTAMP('2026-07-23 10:00:05','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch','NP',0,0,TO_TIMESTAMP('2026-07-23 10:00:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592981 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */ select update_Column_Translation_From_AD_Element(246)
;

-- Column: C_Order_Carrier_Service.IsActive
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592982 /*From ID Server*/,348,0,20,542628,'IsActive',TO_TIMESTAMP('2026-07-23 10:00:06','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv','NP',0,0,TO_TIMESTAMP('2026-07-23 10:00:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592982 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */ select update_Column_Translation_From_AD_Element(348)
;

-- Column: C_Order_Carrier_Service.Updated
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592983 /*From ID Server*/,607,0,16,542628,'Updated',TO_TIMESTAMP('2026-07-23 10:00:07','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert','NP',0,0,TO_TIMESTAMP('2026-07-23 10:00:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592983 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */ select update_Column_Translation_From_AD_Element(607)
;

-- Column: C_Order_Carrier_Service.UpdatedBy
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592984 /*From ID Server*/,608,0,18,110,542628,'UpdatedBy',TO_TIMESTAMP('2026-07-23 10:00:08','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch','NP',0,0,TO_TIMESTAMP('2026-07-23 10:00:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592984 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */ select update_Column_Translation_From_AD_Element(608)
;

-- Element: C_Order_Carrier_Service_ID (new key element for the bridge table)
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585125 /*From ID Server*/,0,'C_Order_Carrier_Service_ID',TO_TIMESTAMP('2026-07-23 10:00:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Lieferweg-Service-Zuweisung','Lieferweg-Service-Zuweisung',TO_TIMESTAMP('2026-07-23 10:00:09','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585125 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
-- en_US override for the new element
UPDATE AD_Element_Trl SET Name='Carrier Service Assignment', PrintName='Carrier Service Assignment', IsTranslated='Y',Updated=TO_TIMESTAMP('2026-07-23 10:00:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585125 AND AD_Language='en_US'
;
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl WHERE trl.AD_Element_ID=base.AD_Element_ID AND base.AD_Element_ID=585125 AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585125,'en_US')
;

-- Column: C_Order_Carrier_Service.C_Order_Carrier_Service_ID (key)
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592985 /*From ID Server*/,585125,0,13,542628,'C_Order_Carrier_Service_ID',TO_TIMESTAMP('2026-07-23 10:00:21','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Lieferweg-Service-Zuweisung','NP',0,0,TO_TIMESTAMP('2026-07-23 10:00:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592985 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */ select update_Column_Translation_From_AD_Element(585125)
;

-- Column: C_Order_Carrier_Service.C_Order_ID  (FK to C_Order, nullable — mirror of the shipment-schedule bridge)
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592986 /*From ID Server*/,558,0,19,542628,'XX','C_Order_ID',TO_TIMESTAMP('2026-07-23 10:00:22','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Auftrag','NP',0,0,TO_TIMESTAMP('2026-07-23 10:00:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592986 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */ select update_Column_Translation_From_AD_Element(558)
;

-- Column: C_Order_Carrier_Service.Carrier_Service_ID  (FK to Carrier_Service, nullable) — services lookup, val rule 540794
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592987 /*From ID Server*/,584113,0,19,542628,540794,'XX','Carrier_Service_ID',TO_TIMESTAMP('2026-07-23 10:00:23','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lieferweg-Servicekatalog','NP',0,0,TO_TIMESTAMP('2026-07-23 10:00:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592987 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */ select update_Column_Translation_From_AD_Element(584113)
;

-- Physical bridge table (mirror of M_ShipmentSchedule_Carrier_Service DDL). Native PK sequence auto-created by dba_seq_check_native().
/* DDL */ CREATE TABLE public.C_Order_Carrier_Service (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_Order_Carrier_Service_ID NUMERIC(10) NOT NULL, C_Order_ID NUMERIC(10), Carrier_Service_ID NUMERIC(10), Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT C_Order_Carrier_Service_Key PRIMARY KEY (C_Order_Carrier_Service_ID), CONSTRAINT COrder_COrderCarrierService FOREIGN KEY (C_Order_ID) REFERENCES public.C_Order DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CarrierService_COrderCarrierService FOREIGN KEY (Carrier_Service_ID) REFERENCES public.Carrier_Service DEFERRABLE INITIALLY DEFERRED)
;

-- ---------------------------------------------------------------------------------------------------
-- 2) New SQL AD_Val_Rules — constrain goods type / service to the SELECTED carrier product's allocations.
--    Keyed by @Carrier_Product_ID@ (the C_Order header's own Carrier_Product_ID). Mirror of the
--    allocation-based rules 540750 / 540757 used on M_ShipmentSchedule, but new + C_Order-scoped so the
--    shared shipment-schedule rules are untouched.
-- ---------------------------------------------------------------------------------------------------

-- Name: Carrier_Goods_Type_ID_for_Carrier_Product_ID
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540793 /*From ID Server*/,'Carrier_Goods_Type_ID IN (SELECT cgt.Carrier_Goods_Type_ID FROM Carrier_Goods_Type cgt JOIN Carrier_Product_GoodsType_Alloc a ON a.Carrier_Goods_Type_ID = cgt.Carrier_Goods_Type_ID WHERE a.IsActive=''Y'' AND a.Carrier_Product_ID = @Carrier_Product_ID/-1@)',TO_TIMESTAMP('2026-07-23 10:00:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Carrier_Goods_Type_ID_for_Carrier_Product_ID','S',TO_TIMESTAMP('2026-07-23 10:00:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: Carrier_Service_ID_for_Carrier_Product_ID
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540794 /*From ID Server*/,'Carrier_Service_ID IN (SELECT cs.Carrier_Service_ID FROM Carrier_Service cs JOIN Carrier_Product_Service_Alloc a ON a.Carrier_Service_ID = cs.Carrier_Service_ID WHERE a.IsActive=''Y'' AND a.Carrier_Product_ID = @Carrier_Product_ID/-1@)',TO_TIMESTAMP('2026-07-23 10:00:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Carrier_Service_ID_for_Carrier_Product_ID','S',TO_TIMESTAMP('2026-07-23 10:00:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- ---------------------------------------------------------------------------------------------------
-- 3) C_Order header carrier columns (mirror of M_ShipmentSchedule.Carrier_Product_ID / Carrier_Goods_Type_ID)
--    C_Order is a core table (EntityType 'D'); these columns match that EntityType so GenerateModel includes them.
-- ---------------------------------------------------------------------------------------------------

-- Column: C_Order.Carrier_Product_ID  (Table reference to Carrier_Product, val rule 540751 — products of the shipper)
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592988 /*From ID Server*/,584116,0,19,259,540751,'XX','Carrier_Product_ID',TO_TIMESTAMP('2026-07-23 10:00:32','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lieferweg-Produkt','NP',0,0,TO_TIMESTAMP('2026-07-23 10:00:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592988 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */ select update_Column_Translation_From_AD_Element(584116)
;

-- Column: C_Order.Carrier_Goods_Type_ID  (Table reference to Carrier_Goods_Type, val rule 540793 — goods types of the selected product)
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592989 /*From ID Server*/,584112,0,19,259,540793,'XX','Carrier_Goods_Type_ID',TO_TIMESTAMP('2026-07-23 10:00:33','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Materialzuordnung je Lieferweg','NP',0,0,TO_TIMESTAMP('2026-07-23 10:00:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592989 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
/* DDL */ select update_Column_Translation_From_AD_Element(584112)
;

-- Physical C_Order columns + FK constraints
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN Carrier_Product_ID NUMERIC(10)')
;
ALTER TABLE C_Order ADD CONSTRAINT CarrierProduct_COrder FOREIGN KEY (Carrier_Product_ID) REFERENCES public.Carrier_Product DEFERRABLE INITIALLY DEFERRED
;
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN Carrier_Goods_Type_ID NUMERIC(10)')
;
ALTER TABLE C_Order ADD CONSTRAINT CarrierGoodsType_COrder FOREIGN KEY (Carrier_Goods_Type_ID) REFERENCES public.Carrier_Goods_Type DEFERRABLE INITIALLY DEFERRED
;
