-- 2021-06-02T12:01:03.949Z
-- #298 changing anz. stellen
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('4',0,0,0,541670,'N',TO_TIMESTAMP('2021-06-02 15:01:02','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Product Excluded from Flatrate Conditions','NP','L','M_Product_Exclude_FlatrateConditions','DTI',TO_TIMESTAMP('2021-06-02 15:01:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-02T12:01:04.081Z
-- #298 changing anz. stellen
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=541670 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2021-06-02T12:01:04.513Z
-- #298 changing anz. stellen
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555413,TO_TIMESTAMP('2021-06-02 15:01:04','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table M_Product_Exclude_FlatrateConditions',1,'Y','N','Y','Y','M_Product_Exclude_FlatrateConditions','N',1000000,TO_TIMESTAMP('2021-06-02 15:01:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-02T12:01:04.630Z
-- #298 changing anz. stellen
CREATE SEQUENCE M_PRODUCT_EXCLUDE_FLATRATECONDITIONS_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2021-06-02T12:02:31.130Z
-- #298 changing anz. stellen
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574147,543889,0,19,541670,'C_CompensationGroup_Schema_ID',TO_TIMESTAMP('2021-06-02 15:02:30','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.order',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Compensation Group Schema',0,0,TO_TIMESTAMP('2021-06-02 15:02:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-02T12:02:31.161Z
-- #298 changing anz. stellen
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574147 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-02T12:02:31.279Z
-- #298 changing anz. stellen
/* DDL */  select update_Column_Translation_From_AD_Element(543889) 
;

-- 2021-06-02T12:06:25.443Z
-- #298 changing anz. stellen
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574148,454,0,30,541670,'M_Product_ID',TO_TIMESTAMP('2021-06-02 15:06:24','YYYY-MM-DD HH24:MI:SS'),100,'N','Produkt, Leistung, Artikel','D',0,10,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Produkt',0,0,TO_TIMESTAMP('2021-06-02 15:06:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-02T12:06:25.474Z
-- #298 changing anz. stellen
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574148 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-02T12:06:25.543Z
-- #298 changing anz. stellen
/* DDL */  select update_Column_Translation_From_AD_Element(454) 
;

-- 2021-06-02T12:08:15.382Z
-- #298 changing anz. stellen
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574149,102,0,19,541670,'AD_Client_ID',TO_TIMESTAMP('2021-06-02 15:08:14','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','Mandant',0,TO_TIMESTAMP('2021-06-02 15:08:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-02T12:08:15.414Z
-- #298 changing anz. stellen
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574149 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-02T12:08:15.481Z
-- #298 changing anz. stellen
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- 2021-06-02T12:08:18.368Z
-- #298 changing anz. stellen
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574150,113,0,30,541670,'AD_Org_ID',TO_TIMESTAMP('2021-06-02 15:08:17','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','Y','N','Y','Y','N','N','Sektion',0,TO_TIMESTAMP('2021-06-02 15:08:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-02T12:08:18.401Z
-- #298 changing anz. stellen
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574150 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-02T12:08:18.466Z
-- #298 changing anz. stellen
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- 2021-06-02T12:08:20.684Z
-- #298 changing anz. stellen
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574151,245,0,16,541670,'Created',TO_TIMESTAMP('2021-06-02 15:08:20','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt',0,TO_TIMESTAMP('2021-06-02 15:08:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-02T12:08:20.717Z
-- #298 changing anz. stellen
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574151 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-02T12:08:20.785Z
-- #298 changing anz. stellen
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- 2021-06-02T12:08:22.840Z
-- #298 changing anz. stellen
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574152,246,0,18,110,541670,'CreatedBy',TO_TIMESTAMP('2021-06-02 15:08:22','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch',0,TO_TIMESTAMP('2021-06-02 15:08:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-02T12:08:22.919Z
-- #298 changing anz. stellen
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574152 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-02T12:08:22.987Z
-- #298 changing anz. stellen
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- 2021-06-02T12:08:27.103Z
-- #298 changing anz. stellen
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574153,348,0,20,541670,'IsActive',TO_TIMESTAMP('2021-06-02 15:08:26','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','Y','Aktiv',0,TO_TIMESTAMP('2021-06-02 15:08:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-02T12:08:27.136Z
-- #298 changing anz. stellen
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574153 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-02T12:08:27.238Z
-- #298 changing anz. stellen
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- 2021-06-02T12:08:33.097Z
-- #298 changing anz. stellen
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574154,607,0,16,541670,'Updated',TO_TIMESTAMP('2021-06-02 15:08:32','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert',0,TO_TIMESTAMP('2021-06-02 15:08:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-02T12:08:33.182Z
-- #298 changing anz. stellen
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574154 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-02T12:08:33.260Z
-- #298 changing anz. stellen
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- 2021-06-02T12:08:39.323Z
-- #298 changing anz. stellen
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574155,608,0,18,110,541670,'UpdatedBy',TO_TIMESTAMP('2021-06-02 15:08:38','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2021-06-02 15:08:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-02T12:08:39.357Z
-- #298 changing anz. stellen
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574155 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-02T12:08:39.420Z
-- #298 changing anz. stellen
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2021-06-02T12:08:42.739Z
-- #298 changing anz. stellen
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579275,0,'M_Product_Exclude_FlatrateConditions_ID',TO_TIMESTAMP('2021-06-02 15:08:42','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Product Excluded from Flatrate Conditions','Product Excluded from Flatrate Conditions',TO_TIMESTAMP('2021-06-02 15:08:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-02T12:08:42.772Z
-- #298 changing anz. stellen
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579275 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-06-02T12:08:45.081Z
-- #298 changing anz. stellen
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574156,579275,0,13,541670,'M_Product_Exclude_FlatrateConditions_ID',TO_TIMESTAMP('2021-06-02 15:08:42','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','N','N','N','N','N','Y','Y','N','N','Y','N','N','Product Excluded from Flatrate Conditions',0,TO_TIMESTAMP('2021-06-02 15:08:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-02T12:08:45.116Z
-- #298 changing anz. stellen
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574156 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-02T12:08:45.180Z
-- #298 changing anz. stellen
/* DDL */  select update_Column_Translation_From_AD_Element(579275) 
;

-- 2021-06-02T12:09:07.266Z
-- #298 changing anz. stellen
/* DDL */ CREATE TABLE public.M_Product_Exclude_FlatrateConditions (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_CompensationGroup_Schema_ID NUMERIC(10), Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_Product_Exclude_FlatrateConditions_ID NUMERIC(10) NOT NULL, M_Product_ID NUMERIC(10), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CCompensationGroupSchema_MProductExcludeFlatrateConditions FOREIGN KEY (C_CompensationGroup_Schema_ID) REFERENCES public.C_CompensationGroup_Schema DEFERRABLE INITIALLY DEFERRED, CONSTRAINT M_Product_Exclude_FlatrateConditions_Key PRIMARY KEY (M_Product_Exclude_FlatrateConditions_ID), CONSTRAINT MProduct_MProductExcludeFlatrateConditions FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED)
;

-- 2021-06-02T12:10:23.015Z
-- #298 changing anz. stellen
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,574147,579275,0,543968,541670,540415,'Y',TO_TIMESTAMP('2021-06-02 15:10:22','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','M_Product_Exclude_FlatrateConditions','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Product Excluded from Flatrate Conditions',559231,'N',30,1,TO_TIMESTAMP('2021-06-02 15:10:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-02T12:10:23.051Z
-- #298 changing anz. stellen
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=543968 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2021-06-02T12:10:23.085Z
-- #298 changing anz. stellen
/* DDL */  select update_tab_translation_from_ad_element(579275) 
;

-- 2021-06-02T12:10:23.140Z
-- #298 changing anz. stellen
/* DDL */ select AD_Element_Link_Create_Missing_Tab(543968)
;

-- 2021-06-02T12:11:02.139Z
-- #298 changing anz. stellen
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574147,646997,0,543968,TO_TIMESTAMP('2021-06-02 15:11:01','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Compensation Group Schema',TO_TIMESTAMP('2021-06-02 15:11:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-02T12:11:02.172Z
-- #298 changing anz. stellen
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646997 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-02T12:11:02.209Z
-- #298 changing anz. stellen
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543889) 
;

-- 2021-06-02T12:11:02.247Z
-- #298 changing anz. stellen
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646997
;

-- 2021-06-02T12:11:02.281Z
-- #298 changing anz. stellen
/* DDL */ select AD_Element_Link_Create_Missing_Field(646997)
;

-- 2021-06-02T12:11:02.762Z
-- #298 changing anz. stellen
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574148,646998,0,543968,TO_TIMESTAMP('2021-06-02 15:11:02','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',10,'D','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','N','N','N','N','N','Produkt',TO_TIMESTAMP('2021-06-02 15:11:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-02T12:11:02.795Z
-- #298 changing anz. stellen
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646998 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-02T12:11:02.828Z
-- #298 changing anz. stellen
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2021-06-02T12:11:03.056Z
-- #298 changing anz. stellen
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646998
;

-- 2021-06-02T12:11:03.088Z
-- #298 changing anz. stellen
/* DDL */ select AD_Element_Link_Create_Missing_Field(646998)
;

-- 2021-06-02T12:11:03.558Z
-- #298 changing anz. stellen
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574149,646999,0,543968,TO_TIMESTAMP('2021-06-02 15:11:03','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2021-06-02 15:11:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-02T12:11:03.590Z
-- #298 changing anz. stellen
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=646999 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-02T12:11:03.624Z
-- #298 changing anz. stellen
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2021-06-02T12:11:04.140Z
-- #298 changing anz. stellen
DELETE FROM AD_Element_Link WHERE AD_Field_ID=646999
;

-- 2021-06-02T12:11:04.171Z
-- #298 changing anz. stellen
/* DDL */ select AD_Element_Link_Create_Missing_Field(646999)
;

-- 2021-06-02T12:11:04.643Z
-- #298 changing anz. stellen
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574150,647000,0,543968,TO_TIMESTAMP('2021-06-02 15:11:04','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2021-06-02 15:11:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-02T12:11:04.675Z
-- #298 changing anz. stellen
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647000 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-02T12:11:04.707Z
-- #298 changing anz. stellen
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2021-06-02T12:11:04.926Z
-- #298 changing anz. stellen
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647000
;

-- 2021-06-02T12:11:04.977Z
-- #298 changing anz. stellen
/* DDL */ select AD_Element_Link_Create_Missing_Field(647000)
;

-- 2021-06-02T12:11:05.455Z
-- #298 changing anz. stellen
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574153,647001,0,543968,TO_TIMESTAMP('2021-06-02 15:11:05','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2021-06-02 15:11:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-02T12:11:05.492Z
-- #298 changing anz. stellen
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647001 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-02T12:11:05.526Z
-- #298 changing anz. stellen
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2021-06-02T12:11:05.847Z
-- #298 changing anz. stellen
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647001
;

-- 2021-06-02T12:11:05.879Z
-- #298 changing anz. stellen
/* DDL */ select AD_Element_Link_Create_Missing_Field(647001)
;

-- 2021-06-02T12:11:06.375Z
-- #298 changing anz. stellen
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574156,647002,0,543968,TO_TIMESTAMP('2021-06-02 15:11:05','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Product Excluded from Flatrate Conditions',TO_TIMESTAMP('2021-06-02 15:11:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-02T12:11:06.406Z
-- #298 changing anz. stellen
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647002 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-02T12:11:06.437Z
-- #298 changing anz. stellen
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579275) 
;

-- 2021-06-02T12:11:06.470Z
-- #298 changing anz. stellen
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647002
;

-- 2021-06-02T12:11:06.501Z
-- #298 changing anz. stellen
/* DDL */ select AD_Element_Link_Create_Missing_Field(647002)
;

-- 2021-06-02T12:12:01.572Z
-- #298 changing anz. stellen
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2021-06-02 15:12:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=646998
;

-- 2021-06-02T12:12:07.403Z
-- #298 changing anz. stellen
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2021-06-02 15:12:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=647001
;

-- 2021-06-02T12:12:11.887Z
-- #298 changing anz. stellen
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2021-06-02 15:12:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=647000
;

-- 2021-06-02T12:12:59.894Z
-- #298 changing anz. stellen
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,543968,543122,TO_TIMESTAMP('2021-06-02 15:12:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-06-02 15:12:59','YYYY-MM-DD HH24:MI:SS'),100,'Product Excluded from Flatrate Conditio')
;

-- 2021-06-02T12:12:59.925Z
-- #298 changing anz. stellen
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=543122 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2021-06-02T12:13:12.111Z
-- #298 changing anz. stellen
UPDATE AD_UI_Section SET UIStyle='primary',Updated=TO_TIMESTAMP('2021-06-02 15:13:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=543122
;

-- 2021-06-02T12:13:18.865Z
-- #298 changing anz. stellen
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,543928,543122,TO_TIMESTAMP('2021-06-02 15:13:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-06-02 15:13:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-02T12:13:55.193Z
-- #298 changing anz. stellen
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,543928,545928,TO_TIMESTAMP('2021-06-02 15:13:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,'primary',TO_TIMESTAMP('2021-06-02 15:13:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-02T12:14:16.194Z
-- #298 changing anz. stellen
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,646998,0,543968,545928,585389,'F',TO_TIMESTAMP('2021-06-02 15:14:15','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','N','N',0,'Produkt',10,0,0,TO_TIMESTAMP('2021-06-02 15:14:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-02T12:14:26.966Z
-- #298 changing anz. stellen
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,647001,0,543968,545928,585390,'F',TO_TIMESTAMP('2021-06-02 15:14:26','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',20,0,0,TO_TIMESTAMP('2021-06-02 15:14:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-02T12:14:42.875Z
-- #298 changing anz. stellen
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,647000,0,543968,545928,585391,'F',TO_TIMESTAMP('2021-06-02 15:14:42','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',30,0,0,TO_TIMESTAMP('2021-06-02 15:14:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-02T12:19:40.128Z
-- #298 changing anz. stellen
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2021-06-02 15:19:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585389
;

-- 2021-06-02T12:19:40.316Z
-- #298 changing anz. stellen
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-06-02 15:19:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585390
;

-- 2021-06-02T12:19:40.514Z
-- #298 changing anz. stellen
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-06-02 15:19:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585391
;

-- 2021-06-02T12:22:27.247Z
-- #298 changing anz. stellen
UPDATE AD_Table SET AccessLevel='3',Updated=TO_TIMESTAMP('2021-06-02 15:22:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541670
;

-- 2021-06-02T12:29:56.064Z
-- #298 changing anz. stellen
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-06-02 15:29:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574148
;

-- 2021-06-02T12:30:08.009Z
-- #298 changing anz. stellen
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-06-02 15:30:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574147
;

-- 2021-06-02T12:30:13.911Z
-- #298 changing anz. stellen
INSERT INTO t_alter_column values('m_product_exclude_flatrateconditions','C_CompensationGroup_Schema_ID','NUMERIC(10)',null,null)
;

-- 2021-06-02T12:30:13.949Z
-- #298 changing anz. stellen
INSERT INTO t_alter_column values('m_product_exclude_flatrateconditions','C_CompensationGroup_Schema_ID',null,'NOT NULL',null)
;

-- 2021-06-02T12:30:24.913Z
-- #298 changing anz. stellen
INSERT INTO t_alter_column values('m_product_exclude_flatrateconditions','M_Product_ID','NUMERIC(10)',null,null)
;

-- 2021-06-02T12:30:24.950Z
-- #298 changing anz. stellen
INSERT INTO t_alter_column values('m_product_exclude_flatrateconditions','M_Product_ID',null,'NOT NULL',null)
;

