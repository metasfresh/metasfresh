-- IDs allocated from idserver.metas.de on 2026-06-26:
--   AD_MigrationScript seq : 5809760
--   AD_Table            542621  (M_Product_PackagingMaterial)
--   AD_Sequence         556612  (table-ID sequence for M_Product_PackagingMaterial)
--   AD_Element          585052  (M_Product_PackagingMaterial_ID — PK label)
--   AD_Column           592884  (M_Product_PackagingMaterial_ID — PK)
--   AD_Column           592885  (AD_Client_ID)
--   AD_Column           592886  (AD_Org_ID)
--   AD_Column           592887  (IsActive)
--   AD_Column           592888  (Created)
--   AD_Column           592889  (CreatedBy)
--   AD_Column           592890  (Updated)
--   AD_Column           592891  (UpdatedBy)
--   AD_Column           592892  (M_Product_ID — FK/parent)
--   AD_Element          585053  (PackagingMaterialType)
--   AD_Column           592893  (PackagingMaterialType)
--   AD_Element          585054  (WeightInGram)
--   AD_Column           592894  (WeightInGram)
--   AD_Reference        542111  (PackagingMaterialType list reference)
--   AD_Ref_List         544283  (PPK)
--   AD_Ref_List         544284  (PS)
--   AD_Ref_List         544285  (PE)
--   AD_Ref_List         544286  (Metall)

-- Run mode: SWING_CLIENT

-- Table: M_Product_PackagingMaterial
-- Child table of M_Product; stores packaging material type and weight per product.
-- 2026-06-26T10:00:00.000Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('3',0,0,0,542621 /*From ID Server*/,'A','N',TO_TIMESTAMP('2026-06-26 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'A','D','N','Y','N','Y','Y','N','N','N','N','N',0,'Verpackungsmaterial','NP','L','M_Product_PackagingMaterial','DTI',TO_TIMESTAMP('2026-06-26 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,0,'A')
;

-- 2026-06-26T10:00:01.000Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542621 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2026-06-26T10:00:02.000Z
UPDATE AD_Table_Trl SET IsTranslated='Y', Name='Packaging material',Updated=TO_TIMESTAMP('2026-06-26 10:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=542621
;

-- Table-ID sequence for M_Product_PackagingMaterial (required for runtime PK allocation)
-- 2026-06-26T10:00:03.000Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNo,Updated,UpdatedBy) VALUES (0,0,556612 /*From ID Server*/,TO_TIMESTAMP('2026-06-26 10:00:03','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table M_Product_PackagingMaterial',1,'Y','N','Y','Y','M_Product_PackagingMaterial',1000000,TO_TIMESTAMP('2026-06-26 10:00:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-06-26T10:00:04.000Z
CREATE SEQUENCE M_PRODUCT_PACKAGINGMATERIAL_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000;

-- Column: M_Product_PackagingMaterial.AD_Client_ID
-- 2026-06-26T10:00:10.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592885 /*From ID Server*/,102,0,19,542621,'AD_Client_ID',TO_TIMESTAMP('2026-06-26 10:00:10','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant','NP',0,0,TO_TIMESTAMP('2026-06-26 10:00:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-26T10:00:11.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592885 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-26T10:00:12.000Z
/* DDL */ select update_Column_Translation_From_AD_Element(102)
;

-- Column: M_Product_PackagingMaterial.AD_Org_ID
-- 2026-06-26T10:00:20.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592886 /*From ID Server*/,113,0,30,542621,'AD_Org_ID',TO_TIMESTAMP('2026-06-26 10:00:20','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion','NP',0,0,TO_TIMESTAMP('2026-06-26 10:00:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-26T10:00:21.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592886 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-26T10:00:22.000Z
/* DDL */ select update_Column_Translation_From_AD_Element(113)
;

-- Column: M_Product_PackagingMaterial.IsActive
-- 2026-06-26T10:00:30.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592887 /*From ID Server*/,348,0,20,542621,'IsActive',TO_TIMESTAMP('2026-06-26 10:00:30','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv','NP',0,0,TO_TIMESTAMP('2026-06-26 10:00:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-26T10:00:31.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592887 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-26T10:00:32.000Z
/* DDL */ select update_Column_Translation_From_AD_Element(348)
;

-- Column: M_Product_PackagingMaterial.Created
-- 2026-06-26T10:00:40.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592888 /*From ID Server*/,245,0,16,542621,'Created',TO_TIMESTAMP('2026-06-26 10:00:40','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt','NP',0,0,TO_TIMESTAMP('2026-06-26 10:00:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-26T10:00:41.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592888 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-26T10:00:42.000Z
/* DDL */ select update_Column_Translation_From_AD_Element(245)
;

-- Column: M_Product_PackagingMaterial.CreatedBy
-- 2026-06-26T10:00:50.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592889 /*From ID Server*/,246,0,18,110,542621,'CreatedBy',TO_TIMESTAMP('2026-06-26 10:00:50','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch','NP',0,0,TO_TIMESTAMP('2026-06-26 10:00:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-26T10:00:51.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592889 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-26T10:00:52.000Z
/* DDL */ select update_Column_Translation_From_AD_Element(246)
;

-- Column: M_Product_PackagingMaterial.Updated
-- 2026-06-26T10:01:00.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592890 /*From ID Server*/,607,0,16,542621,'Updated',TO_TIMESTAMP('2026-06-26 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert','NP',0,0,TO_TIMESTAMP('2026-06-26 10:01:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-26T10:01:01.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592890 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-26T10:01:02.000Z
/* DDL */ select update_Column_Translation_From_AD_Element(607)
;

-- Column: M_Product_PackagingMaterial.UpdatedBy
-- 2026-06-26T10:01:10.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592891 /*From ID Server*/,608,0,18,110,542621,'UpdatedBy',TO_TIMESTAMP('2026-06-26 10:01:10','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch','NP',0,0,TO_TIMESTAMP('2026-06-26 10:01:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-26T10:01:11.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592891 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-26T10:01:12.000Z
/* DDL */ select update_Column_Translation_From_AD_Element(608)
;

-- Element and column for PK: M_Product_PackagingMaterial_ID
-- 2026-06-26T10:01:20.000Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585052 /*From ID Server*/,0,'M_Product_PackagingMaterial_ID',TO_TIMESTAMP('2026-06-26 10:01:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Verpackungsmaterial','Verpackungsmaterial',TO_TIMESTAMP('2026-06-26 10:01:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-06-26T10:01:21.000Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585052 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-06-26T10:01:22.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Packaging material', PrintName='Packaging material', Updated=TO_TIMESTAMP('2026-06-26 10:01:22','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=585052
;

-- 2026-06-26T10:01:23.000Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585052, 'en_US')
;

-- 2026-06-26T10:01:24.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Verpackungsmaterial', PrintName='Verpackungsmaterial', Updated=TO_TIMESTAMP('2026-06-26 10:01:24','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=585052
;

-- 2026-06-26T10:01:25.000Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585052, 'de_DE')
;

-- 2026-06-26T10:01:26.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Verpackungsmaterial', PrintName='Verpackungsmaterial', Updated=TO_TIMESTAMP('2026-06-26 10:01:26','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=585052
;

-- 2026-06-26T10:01:27.000Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585052, 'de_CH')
;

-- Column: M_Product_PackagingMaterial.M_Product_PackagingMaterial_ID
-- 2026-06-26T10:01:30.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592884 /*From ID Server*/,585052,0,13,542621,'M_Product_PackagingMaterial_ID',TO_TIMESTAMP('2026-06-26 10:01:30','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Verpackungsmaterial','NP',0,0,TO_TIMESTAMP('2026-06-26 10:01:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-26T10:01:31.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592884 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-26T10:01:32.000Z
/* DDL */ select update_Column_Translation_From_AD_Element(585052)
;

-- Column: M_Product_PackagingMaterial.M_Product_ID
-- Parent FK to M_Product.
-- 2026-06-26T10:01:40.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592892 /*From ID Server*/,454,0,19,542621,'XX','M_Product_ID',TO_TIMESTAMP('2026-06-26 10:01:40','YYYY-MM-DD HH24:MI:SS'),100,'N','Produkt, Leistung, Artikel','D',0,10,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','Y','N','N','N','N','N','N','N','Y','Y','N','N','N','Y','N','N','Produkt','NP',0,0,TO_TIMESTAMP('2026-06-26 10:01:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-26T10:01:41.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592892 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-26T10:01:42.000Z
/* DDL */ select update_Column_Translation_From_AD_Element(454)
;

-- AD_Reference for PackagingMaterialType list (ValidationType='L')
-- 2026-06-26T10:02:00.000Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,542111 /*From ID Server*/,TO_TIMESTAMP('2026-06-26 10:02:00','YYYY-MM-DD HH24:MI:SS'),100,'Packaging material type list for M_Product_PackagingMaterial','D','Y','N','Verpackungsmaterial-Typ',TO_TIMESTAMP('2026-06-26 10:02:00','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2026-06-26T10:02:01.000Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=542111 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2026-06-26T10:02:02.000Z
UPDATE AD_Reference_Trl SET IsTranslated='Y', Name='Packaging material type', Description='Packaging material type list for M_Product_PackagingMaterial', Updated=TO_TIMESTAMP('2026-06-26 10:02:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=542111
;

-- AD_Ref_List values for PackagingMaterialType (German in base Name column, English via _Trl).
-- 2026-06-26T10:02:10.000Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,544283 /*From ID Server*/,542111,TO_TIMESTAMP('2026-06-26 10:02:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Papier/Pappe/Karton',TO_TIMESTAMP('2026-06-26 10:02:10','YYYY-MM-DD HH24:MI:SS'),100,'PPK','PPK')
;

-- 2026-06-26T10:02:11.000Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544283 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2026-06-26T10:02:11.500Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Papier/Pappe/Karton', Updated=TO_TIMESTAMP('2026-06-26 10:02:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544283
;

-- 2026-06-26T10:02:12.000Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Paper/cardboard', Updated=TO_TIMESTAMP('2026-06-26 10:02:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544283
;

-- 2026-06-26T10:02:20.000Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,544284 /*From ID Server*/,542111,TO_TIMESTAMP('2026-06-26 10:02:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Polystyrol (PS)',TO_TIMESTAMP('2026-06-26 10:02:20','YYYY-MM-DD HH24:MI:SS'),100,'PS','PS')
;

-- 2026-06-26T10:02:21.000Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544284 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2026-06-26T10:02:21.500Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Polystyrol (PS)', Updated=TO_TIMESTAMP('2026-06-26 10:02:21','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544284
;

-- 2026-06-26T10:02:22.000Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Polystyrene (PS)', Updated=TO_TIMESTAMP('2026-06-26 10:02:22','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544284
;

-- 2026-06-26T10:02:30.000Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,544285 /*From ID Server*/,542111,TO_TIMESTAMP('2026-06-26 10:02:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Polyethylen (PE)',TO_TIMESTAMP('2026-06-26 10:02:30','YYYY-MM-DD HH24:MI:SS'),100,'PE','PE')
;

-- 2026-06-26T10:02:31.000Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544285 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2026-06-26T10:02:31.500Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Polyethylen (PE)', Updated=TO_TIMESTAMP('2026-06-26 10:02:31','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544285
;

-- 2026-06-26T10:02:32.000Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Polyethylene (PE)', Updated=TO_TIMESTAMP('2026-06-26 10:02:32','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544285
;

-- 2026-06-26T10:02:40.000Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,544286 /*From ID Server*/,542111,TO_TIMESTAMP('2026-06-26 10:02:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Metall',TO_TIMESTAMP('2026-06-26 10:02:40','YYYY-MM-DD HH24:MI:SS'),100,'Metall','Metall')
;

-- 2026-06-26T10:02:41.000Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=544286 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2026-06-26T10:02:41.500Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Metall', Updated=TO_TIMESTAMP('2026-06-26 10:02:41','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=544286
;

-- 2026-06-26T10:02:42.000Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Metal', Updated=TO_TIMESTAMP('2026-06-26 10:02:42','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=544286
;

-- Element and column for PackagingMaterialType
-- 2026-06-26T10:03:00.000Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585053 /*From ID Server*/,0,'PackagingMaterialType',TO_TIMESTAMP('2026-06-26 10:03:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Verpackungsmaterial-Typ','Verpackungsmaterial-Typ',TO_TIMESTAMP('2026-06-26 10:03:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-06-26T10:03:01.000Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585053 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-06-26T10:03:02.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Packaging material type', PrintName='Packaging material type', Updated=TO_TIMESTAMP('2026-06-26 10:03:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=585053
;

-- 2026-06-26T10:03:03.000Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585053, 'en_US')
;

-- 2026-06-26T10:03:04.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Verpackungsmaterial-Typ', PrintName='Verpackungsmaterial-Typ', Updated=TO_TIMESTAMP('2026-06-26 10:03:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=585053
;

-- 2026-06-26T10:03:05.000Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585053, 'de_DE')
;

-- 2026-06-26T10:03:06.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Verpackungsmaterial-Typ', PrintName='Verpackungsmaterial-Typ', Updated=TO_TIMESTAMP('2026-06-26 10:03:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=585053
;

-- 2026-06-26T10:03:07.000Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585053, 'de_CH')
;

-- Column: M_Product_PackagingMaterial.PackagingMaterialType
-- 2026-06-26T10:03:10.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592893 /*From ID Server*/,585053,0,17,542111,542621,'PackagingMaterialType',TO_TIMESTAMP('2026-06-26 10:03:10','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Verpackungsmaterial-Typ','NP',0,0,TO_TIMESTAMP('2026-06-26 10:03:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-26T10:03:11.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592893 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-26T10:03:12.000Z
/* DDL */ select update_Column_Translation_From_AD_Element(585053)
;

-- Element and column for WeightInGram
-- 2026-06-26T10:03:20.000Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585054 /*From ID Server*/,0,'WeightInGram',TO_TIMESTAMP('2026-06-26 10:03:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Gewicht (g)','Gewicht (g)',TO_TIMESTAMP('2026-06-26 10:03:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-06-26T10:03:21.000Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585054 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-06-26T10:03:22.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Weight (g)', PrintName='Weight (g)', Updated=TO_TIMESTAMP('2026-06-26 10:03:22','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=585054
;

-- 2026-06-26T10:03:23.000Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585054, 'en_US')
;

-- 2026-06-26T10:03:24.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Gewicht (g)', PrintName='Gewicht (g)', Updated=TO_TIMESTAMP('2026-06-26 10:03:24','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=585054
;

-- 2026-06-26T10:03:25.000Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585054, 'de_DE')
;

-- 2026-06-26T10:03:26.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Gewicht (g)', PrintName='Gewicht (g)', Updated=TO_TIMESTAMP('2026-06-26 10:03:26','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=585054
;

-- 2026-06-26T10:03:27.000Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585054, 'de_CH')
;

-- Column: M_Product_PackagingMaterial.WeightInGram
-- 2026-06-26T10:03:30.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592894 /*From ID Server*/,585054,0,12,542621,'WeightInGram',TO_TIMESTAMP('2026-06-26 10:03:30','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Gewicht (g)','NP',0,0,TO_TIMESTAMP('2026-06-26 10:03:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-26T10:03:31.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592894 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-06-26T10:03:32.000Z
/* DDL */ select update_Column_Translation_From_AD_Element(585054)
;

-- Physical table DDL
-- 2026-06-26T10:04:00.000Z
/* DDL */ CREATE TABLE public.M_Product_PackagingMaterial
(
    AD_Client_ID              NUMERIC(10)                  NOT NULL,
    AD_Org_ID                 NUMERIC(10)                  NOT NULL,
    Created                   TIMESTAMP WITH TIME ZONE     NOT NULL,
    CreatedBy                 NUMERIC(10)                  NOT NULL,
    IsActive                  CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL,
    M_Product_ID              NUMERIC(10)                  NOT NULL,
    M_Product_PackagingMaterial_ID NUMERIC(10)             NOT NULL,
    PackagingMaterialType     VARCHAR(10),
    Updated                   TIMESTAMP WITH TIME ZONE     NOT NULL,
    UpdatedBy                 NUMERIC(10)                  NOT NULL,
    WeightInGram              NUMERIC,
    CONSTRAINT MProduct_MProductPackagingMaterial FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED,
    CONSTRAINT M_Product_PackagingMaterial_Key PRIMARY KEY (M_Product_PackagingMaterial_ID)
)
;
