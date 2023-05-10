-- Table: M_HU_UniqueAttribute
-- 2022-10-31T15:00:40.481Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,542252,'N',TO_TIMESTAMP('2022-10-31 17:00:40.247','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.handlingunits','N','Y','N','N','Y','N','N','N','N','N',0,'Handling Unit Unique Attributes','NP','L','M_HU_UniqueAttribute','DTI',TO_TIMESTAMP('2022-10-31 17:00:40.247','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-31T15:00:40.483Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542252 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2022-10-31T15:00:40.597Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556044,TO_TIMESTAMP('2022-10-31 17:00:40.503','YYYY-MM-DD HH24:MI:SS.US'),100,1000000,50000,'Table M_HU_UniqueAttribute',1,'Y','N','Y','Y','M_HU_UniqueAttribute','N',1000000,TO_TIMESTAMP('2022-10-31 17:00:40.503','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-31T15:00:40.607Z
CREATE SEQUENCE M_HU_UNIQUEATTRIBUTE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: M_HU_UniqueAttribute.AD_Client_ID
-- 2022-10-31T15:00:47.300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584847,102,0,19,542252,'AD_Client_ID',TO_TIMESTAMP('2022-10-31 17:00:46.83','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Mandant für diese Installation.','de.metas.handlingunits',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2022-10-31 17:00:46.83','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2022-10-31T15:00:47.301Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584847 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-31T15:00:47.328Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: M_HU_UniqueAttribute.AD_Org_ID
-- 2022-10-31T15:00:48.639Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584848,113,0,30,542252,'AD_Org_ID',TO_TIMESTAMP('2022-10-31 17:00:48.016','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Organisatorische Einheit des Mandanten','de.metas.handlingunits',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2022-10-31 17:00:48.016','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2022-10-31T15:00:48.641Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584848 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-31T15:00:48.643Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: M_HU_UniqueAttribute.Created
-- 2022-10-31T15:00:49.198Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584849,245,0,16,542252,'Created',TO_TIMESTAMP('2022-10-31 17:00:49.103','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.handlingunits',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2022-10-31 17:00:49.103','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2022-10-31T15:00:49.200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584849 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-31T15:00:49.203Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: M_HU_UniqueAttribute.CreatedBy
-- 2022-10-31T15:00:49.680Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584850,246,0,18,110,542252,'CreatedBy',TO_TIMESTAMP('2022-10-31 17:00:49.578','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.handlingunits',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2022-10-31 17:00:49.578','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2022-10-31T15:00:49.682Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584850 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-31T15:00:49.685Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: M_HU_UniqueAttribute.IsActive
-- 2022-10-31T15:00:50.210Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584851,348,0,20,542252,'IsActive',TO_TIMESTAMP('2022-10-31 17:00:50.117','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Der Eintrag ist im System aktiv','de.metas.handlingunits',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2022-10-31 17:00:50.117','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2022-10-31T15:00:50.212Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584851 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-31T15:00:50.214Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: M_HU_UniqueAttribute.Updated
-- 2022-10-31T15:00:50.681Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584852,607,0,16,542252,'Updated',TO_TIMESTAMP('2022-10-31 17:00:50.589','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.handlingunits',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2022-10-31 17:00:50.589','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2022-10-31T15:00:50.683Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584852 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-31T15:00:50.685Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: M_HU_UniqueAttribute.UpdatedBy
-- 2022-10-31T15:00:51.219Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584853,608,0,18,110,542252,'UpdatedBy',TO_TIMESTAMP('2022-10-31 17:00:51.13','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.handlingunits',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2022-10-31 17:00:51.13','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2022-10-31T15:00:51.220Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584853 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-31T15:00:51.224Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2022-10-31T15:00:51.676Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581628,0,'M_HU_UniqueAttribute_ID',TO_TIMESTAMP('2022-10-31 17:00:51.588','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.handlingunits','Y','Handling Unit Unique Attributes','Handling Unit Unique Attributes',TO_TIMESTAMP('2022-10-31 17:00:51.588','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-31T15:00:51.678Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581628 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_HU_UniqueAttribute.M_HU_UniqueAttribute_ID
-- 2022-10-31T15:00:52.093Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584854,581628,0,13,542252,'M_HU_UniqueAttribute_ID',TO_TIMESTAMP('2022-10-31 17:00:51.586','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.handlingunits',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Handling Unit Unique Attributes',0,0,TO_TIMESTAMP('2022-10-31 17:00:51.586','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2022-10-31T15:00:52.095Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584854 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-31T15:00:52.098Z
/* DDL */  select update_Column_Translation_From_AD_Element(581628) 
;

-- Column: M_HU_UniqueAttribute.M_Product_ID
-- 2022-10-31T15:01:20.853Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584855,454,0,30,542252,'M_Product_ID',TO_TIMESTAMP('2022-10-31 17:01:20.718','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Produkt, Leistung, Artikel','de.metas.handlingunits',0,10,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Produkt',0,0,TO_TIMESTAMP('2022-10-31 17:01:20.718','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2022-10-31T15:01:20.854Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584855 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-31T15:01:20.857Z
/* DDL */  select update_Column_Translation_From_AD_Element(454) 
;

-- Column: M_HU_UniqueAttribute.M_Attribute_ID
-- 2022-10-31T15:01:47.720Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584856,2015,0,30,542252,'M_Attribute_ID',TO_TIMESTAMP('2022-10-31 17:01:47.578','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Produkt-Merkmal','de.metas.handlingunits',0,10,'Product Attribute like Color, Size','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Merkmal',0,0,TO_TIMESTAMP('2022-10-31 17:01:47.578','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2022-10-31T15:01:47.721Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584856 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-31T15:01:47.724Z
/* DDL */  select update_Column_Translation_From_AD_Element(2015) 
;

-- Column: M_HU_UniqueAttribute.M_HU_ID
-- 2022-10-31T15:02:01.634Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584857,542140,0,30,542252,'M_HU_ID',TO_TIMESTAMP('2022-10-31 17:02:01.477','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Handling Unit',0,0,TO_TIMESTAMP('2022-10-31 17:02:01.477','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2022-10-31T15:02:01.635Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584857 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-31T15:02:01.638Z
/* DDL */  select update_Column_Translation_From_AD_Element(542140) 
;

-- Column: M_HU_UniqueAttribute.Value
-- 2022-10-31T15:02:33.583Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584858,620,0,10,542252,'Value',TO_TIMESTAMP('2022-10-31 17:02:33.457','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','de.metas.handlingunits',0,250,'A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y',0,'Suchschlüssel',0,0,TO_TIMESTAMP('2022-10-31 17:02:33.457','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2022-10-31T15:02:33.585Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584858 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-31T15:02:33.587Z
/* DDL */  select update_Column_Translation_From_AD_Element(620) 
;

-- Column: M_HU_UniqueAttribute.M_HU_PI_Attribute_ID
-- 2022-10-31T15:02:53.283Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584859,542131,0,30,542252,'M_HU_PI_Attribute_ID',TO_TIMESTAMP('2022-10-31 17:02:53.123','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Handling Units Packing Instructions Attribute',0,0,TO_TIMESTAMP('2022-10-31 17:02:53.123','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2022-10-31T15:02:53.285Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584859 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-31T15:02:53.288Z
/* DDL */  select update_Column_Translation_From_AD_Element(542131) 
;

-- Column: M_HU_UniqueAttribute.M_HU_Attribute_ID
-- 2022-10-31T15:03:08.057Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584860,542128,0,30,542252,'M_HU_Attribute_ID',TO_TIMESTAMP('2022-10-31 17:03:07.84','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Handling Unit Attribute',0,0,TO_TIMESTAMP('2022-10-31 17:03:07.84','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2022-10-31T15:03:08.058Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584860 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-10-31T15:03:08.061Z
/* DDL */  select update_Column_Translation_From_AD_Element(542128) 
;

-- 2022-10-31T15:03:13.935Z
/* DDL */ CREATE TABLE public.M_HU_UniqueAttribute (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_Attribute_ID NUMERIC(10), M_HU_Attribute_ID NUMERIC(10), M_HU_ID NUMERIC(10), M_HU_PI_Attribute_ID NUMERIC(10), M_HU_UniqueAttribute_ID NUMERIC(10) NOT NULL, M_Product_ID NUMERIC(10), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, Value VARCHAR(250), CONSTRAINT MAttribute_MHUUniqueAttribute FOREIGN KEY (M_Attribute_ID) REFERENCES public.M_Attribute DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MHUAttribute_MHUUniqueAttribute FOREIGN KEY (M_HU_Attribute_ID) REFERENCES public.M_HU_Attribute DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MHU_MHUUniqueAttribute FOREIGN KEY (M_HU_ID) REFERENCES public.M_HU DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MHUPIAttribute_MHUUniqueAttribute FOREIGN KEY (M_HU_PI_Attribute_ID) REFERENCES public.M_HU_PI_Attribute DEFERRABLE INITIALLY DEFERRED, CONSTRAINT M_HU_UniqueAttribute_Key PRIMARY KEY (M_HU_UniqueAttribute_ID), CONSTRAINT MProduct_MHUUniqueAttribute FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED)
;

-- 2022-10-31T15:04:08.630Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540711,0,542252,TO_TIMESTAMP('2022-10-31 17:04:08.499','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','Y','M_HU_UniqueAttribute_Unique_Product_Attribute_And_Value','N',TO_TIMESTAMP('2022-10-31 17:04:08.499','YYYY-MM-DD HH24:MI:SS.US'),100,'IaActive = ''Y''')
;

-- 2022-10-31T15:04:08.632Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540711 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2022-10-31T15:04:33.917Z
UPDATE AD_Index_Table SET BeforeChangeWarning='An active, picked or issued handling unit with the same attribute value already exists.', EntityType='de.metas.handlingunits',Updated=TO_TIMESTAMP('2022-10-31 17:04:33.915','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Index_Table_ID=540711
;

-- 2022-10-31T15:05:27.692Z
UPDATE AD_Index_Table SET BeforeChangeWarning='An active, picked or issued handling unit with the same product, attribute and attribute value already exists.',Updated=TO_TIMESTAMP('2022-10-31 17:05:27.689','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Index_Table_ID=540711
;

-- 2022-10-31T15:05:42.975Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,584855,541277,540711,0,TO_TIMESTAMP('2022-10-31 17:05:42.842','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.handlingunits','Y',10,TO_TIMESTAMP('2022-10-31 17:05:42.842','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-31T15:05:53.118Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,584856,541278,540711,0,TO_TIMESTAMP('2022-10-31 17:05:52.968','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.handlingunits','Y',20,TO_TIMESTAMP('2022-10-31 17:05:52.968','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-31T15:07:42.046Z
UPDATE AD_Index_Table SET WhereClause='IaActive = ''Y'' AND Value IS NOT NULL',Updated=TO_TIMESTAMP('2022-10-31 17:07:42.044','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Index_Table_ID=540711
;

-- 2022-10-31T15:07:56.931Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,584858,541279,540711,0,TO_TIMESTAMP('2022-10-31 17:07:56.775','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.handlingunits','Y',30,TO_TIMESTAMP('2022-10-31 17:07:56.775','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-31T15:08:09.591Z
UPDATE AD_Index_Table SET WhereClause='IsActive = ''Y'' AND Value IS NOT NULL',Updated=TO_TIMESTAMP('2022-10-31 17:08:09.589','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Index_Table_ID=540711
;

-- 2022-10-31T15:08:11.331Z
CREATE UNIQUE INDEX M_HU_UniqueAttribute_Unique_Product_Attribute_And_Value ON M_HU_UniqueAttribute (M_Product_ID,M_Attribute_ID,Value) WHERE IsActive = 'Y' AND Value IS NOT NULL
;

-- Window: Handling Unit Unique Attributes, InternalName=null
-- 2022-10-31T15:39:05.453Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,581628,0,541626,TO_TIMESTAMP('2022-10-31 17:39:05.262','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.handlingunits','Y','N','N','N','N','N','N','Y','Handling Unit Unique Attributes','N',TO_TIMESTAMP('2022-10-31 17:39:05.262','YYYY-MM-DD HH24:MI:SS.US'),100,'T',0,0,100)
;

-- 2022-10-31T15:39:05.456Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541626 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2022-10-31T15:39:05.461Z
/* DDL */  select update_window_translation_from_ad_element(581628) 
;

-- 2022-10-31T15:39:05.481Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541626
;

-- 2022-10-31T15:39:05.483Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541626)
;

-- Tab: Handling Unit Unique Attributes -> Handling Unit Unique Attributes
-- Table: M_HU_UniqueAttribute
-- 2022-10-31T15:39:29.101Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581628,0,546663,542252,541626,'Y',TO_TIMESTAMP('2022-10-31 17:39:28.958','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.handlingunits','N','N','A','M_HU_UniqueAttribute','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Handling Unit Unique Attributes','N',10,0,TO_TIMESTAMP('2022-10-31 17:39:28.958','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-31T15:39:29.103Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546663 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-10-31T15:39:29.105Z
/* DDL */  select update_tab_translation_from_ad_element(581628) 
;

-- 2022-10-31T15:39:29.107Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546663)
;

-- Field: Handling Unit Unique Attributes -> Handling Unit Unique Attributes -> Mandant
-- Column: M_HU_UniqueAttribute.AD_Client_ID
-- 2022-10-31T15:40:44.503Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584847,707916,0,546663,TO_TIMESTAMP('2022-10-31 17:40:44.346','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'de.metas.handlingunits','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-10-31 17:40:44.346','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-31T15:40:44.504Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707916 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-31T15:40:44.506Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-10-31T15:40:44.593Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707916
;

-- 2022-10-31T15:40:44.595Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707916)
;

-- Field: Handling Unit Unique Attributes -> Handling Unit Unique Attributes -> Sektion
-- Column: M_HU_UniqueAttribute.AD_Org_ID
-- 2022-10-31T15:40:44.699Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584848,707917,0,546663,TO_TIMESTAMP('2022-10-31 17:40:44.598','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.handlingunits','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','Sektion',TO_TIMESTAMP('2022-10-31 17:40:44.598','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-31T15:40:44.701Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707917 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-31T15:40:44.702Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-10-31T15:40:44.774Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707917
;

-- 2022-10-31T15:40:44.775Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707917)
;

-- Field: Handling Unit Unique Attributes -> Handling Unit Unique Attributes -> Erstellt
-- Column: M_HU_UniqueAttribute.Created
-- 2022-10-31T15:40:44.909Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584849,707918,0,546663,TO_TIMESTAMP('2022-10-31 17:40:44.778','YYYY-MM-DD HH24:MI:SS.US'),100,'Datum, an dem dieser Eintrag erstellt wurde',29,'de.metas.handlingunits','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','Y','N','N','N','N','N','Erstellt',TO_TIMESTAMP('2022-10-31 17:40:44.778','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-31T15:40:44.910Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707918 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-31T15:40:44.911Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2022-10-31T15:40:44.933Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707918
;

-- 2022-10-31T15:40:44.934Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707918)
;

-- Field: Handling Unit Unique Attributes -> Handling Unit Unique Attributes -> Erstellt durch
-- Column: M_HU_UniqueAttribute.CreatedBy
-- 2022-10-31T15:40:45.030Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584850,707919,0,546663,TO_TIMESTAMP('2022-10-31 17:40:44.937','YYYY-MM-DD HH24:MI:SS.US'),100,'Nutzer, der diesen Eintrag erstellt hat',10,'de.metas.handlingunits','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','Y','N','N','N','N','N','Erstellt durch',TO_TIMESTAMP('2022-10-31 17:40:44.937','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-31T15:40:45.031Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707919 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-31T15:40:45.033Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2022-10-31T15:40:45.049Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707919
;

-- 2022-10-31T15:40:45.051Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707919)
;

-- Field: Handling Unit Unique Attributes -> Handling Unit Unique Attributes -> Aktiv
-- Column: M_HU_UniqueAttribute.IsActive
-- 2022-10-31T15:40:45.159Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584851,707920,0,546663,TO_TIMESTAMP('2022-10-31 17:40:45.054','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'de.metas.handlingunits','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-10-31 17:40:45.054','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-31T15:40:45.160Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707920 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-31T15:40:45.162Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-10-31T15:40:45.235Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707920
;

-- 2022-10-31T15:40:45.236Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707920)
;

-- Field: Handling Unit Unique Attributes -> Handling Unit Unique Attributes -> Aktualisiert
-- Column: M_HU_UniqueAttribute.Updated
-- 2022-10-31T15:40:45.348Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584852,707921,0,546663,TO_TIMESTAMP('2022-10-31 17:40:45.239','YYYY-MM-DD HH24:MI:SS.US'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'de.metas.handlingunits','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','Y','N','N','N','N','N','Aktualisiert',TO_TIMESTAMP('2022-10-31 17:40:45.239','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-31T15:40:45.350Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707921 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-31T15:40:45.351Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607) 
;

-- 2022-10-31T15:40:45.375Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707921
;

-- 2022-10-31T15:40:45.376Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707921)
;

-- Field: Handling Unit Unique Attributes -> Handling Unit Unique Attributes -> Aktualisiert durch
-- Column: M_HU_UniqueAttribute.UpdatedBy
-- 2022-10-31T15:40:45.479Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584853,707922,0,546663,TO_TIMESTAMP('2022-10-31 17:40:45.379','YYYY-MM-DD HH24:MI:SS.US'),100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'de.metas.handlingunits','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','Y','N','N','N','N','N','Aktualisiert durch',TO_TIMESTAMP('2022-10-31 17:40:45.379','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-31T15:40:45.480Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707922 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-31T15:40:45.481Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608) 
;

-- 2022-10-31T15:40:45.503Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707922
;

-- 2022-10-31T15:40:45.505Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707922)
;

-- Field: Handling Unit Unique Attributes -> Handling Unit Unique Attributes -> Handling Unit Unique Attributes
-- Column: M_HU_UniqueAttribute.M_HU_UniqueAttribute_ID
-- 2022-10-31T15:40:45.604Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584854,707923,0,546663,TO_TIMESTAMP('2022-10-31 17:40:45.507','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','Handling Unit Unique Attributes',TO_TIMESTAMP('2022-10-31 17:40:45.507','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-31T15:40:45.605Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707923 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-31T15:40:45.607Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581628) 
;

-- 2022-10-31T15:40:45.608Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707923
;

-- 2022-10-31T15:40:45.609Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707923)
;

-- Field: Handling Unit Unique Attributes -> Handling Unit Unique Attributes -> Produkt
-- Column: M_HU_UniqueAttribute.M_Product_ID
-- 2022-10-31T15:40:45.707Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584855,707924,0,546663,TO_TIMESTAMP('2022-10-31 17:40:45.612','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt, Leistung, Artikel',10,'de.metas.handlingunits','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','Y','N','N','N','N','N','Produkt',TO_TIMESTAMP('2022-10-31 17:40:45.612','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-31T15:40:45.708Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707924 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-31T15:40:45.710Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2022-10-31T15:40:45.729Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707924
;

-- 2022-10-31T15:40:45.731Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707924)
;

-- Field: Handling Unit Unique Attributes -> Handling Unit Unique Attributes -> Merkmal
-- Column: M_HU_UniqueAttribute.M_Attribute_ID
-- 2022-10-31T15:40:45.833Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584856,707925,0,546663,TO_TIMESTAMP('2022-10-31 17:40:45.734','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt-Merkmal',10,'de.metas.handlingunits','Product Attribute like Color, Size','Y','Y','N','N','N','N','N','Merkmal',TO_TIMESTAMP('2022-10-31 17:40:45.734','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-31T15:40:45.835Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707925 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-31T15:40:45.837Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2015) 
;

-- 2022-10-31T15:40:45.839Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707925
;

-- 2022-10-31T15:40:45.840Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707925)
;

-- Field: Handling Unit Unique Attributes -> Handling Unit Unique Attributes -> Handling Unit
-- Column: M_HU_UniqueAttribute.M_HU_ID
-- 2022-10-31T15:40:45.938Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584857,707926,0,546663,TO_TIMESTAMP('2022-10-31 17:40:45.842','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.handlingunits','Y','Y','N','N','N','N','N','Handling Unit',TO_TIMESTAMP('2022-10-31 17:40:45.842','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-31T15:40:45.939Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707926 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-31T15:40:45.940Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542140) 
;

-- 2022-10-31T15:40:45.943Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707926
;

-- 2022-10-31T15:40:45.943Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707926)
;

-- Field: Handling Unit Unique Attributes -> Handling Unit Unique Attributes -> Suchschlüssel
-- Column: M_HU_UniqueAttribute.Value
-- 2022-10-31T15:40:46.037Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584858,707927,0,546663,TO_TIMESTAMP('2022-10-31 17:40:45.946','YYYY-MM-DD HH24:MI:SS.US'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein',250,'de.metas.handlingunits','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','Y','N','N','N','N','N','Suchschlüssel',TO_TIMESTAMP('2022-10-31 17:40:45.946','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-31T15:40:46.038Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707927 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-31T15:40:46.040Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(620) 
;

-- 2022-10-31T15:40:46.053Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707927
;

-- 2022-10-31T15:40:46.054Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707927)
;

-- Field: Handling Unit Unique Attributes -> Handling Unit Unique Attributes -> Handling Units Packing Instructions Attribute
-- Column: M_HU_UniqueAttribute.M_HU_PI_Attribute_ID
-- 2022-10-31T15:40:46.161Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584859,707928,0,546663,TO_TIMESTAMP('2022-10-31 17:40:46.056','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.handlingunits','Y','Y','N','N','N','N','N','Handling Units Packing Instructions Attribute',TO_TIMESTAMP('2022-10-31 17:40:46.056','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-31T15:40:46.162Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707928 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-31T15:40:46.163Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542131) 
;

-- 2022-10-31T15:40:46.165Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707928
;

-- 2022-10-31T15:40:46.166Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707928)
;

-- Field: Handling Unit Unique Attributes -> Handling Unit Unique Attributes -> Handling Unit Attribute
-- Column: M_HU_UniqueAttribute.M_HU_Attribute_ID
-- 2022-10-31T15:40:46.264Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584860,707929,0,546663,TO_TIMESTAMP('2022-10-31 17:40:46.168','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.handlingunits','Y','Y','N','N','N','N','N','Handling Unit Attribute',TO_TIMESTAMP('2022-10-31 17:40:46.168','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-31T15:40:46.266Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707929 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-10-31T15:40:46.267Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542128) 
;

-- 2022-10-31T15:40:46.268Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707929
;

-- 2022-10-31T15:40:46.269Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707929)
;

-- 2022-10-31T15:42:03.875Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546663,545287,TO_TIMESTAMP('2022-10-31 17:42:03.742','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2022-10-31 17:42:03.742','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2022-10-31T15:42:03.877Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545287 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-10-31T15:42:04Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546432,545287,TO_TIMESTAMP('2022-10-31 17:42:03.913','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2022-10-31 17:42:03.913','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-31T15:42:04.094Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546433,545287,TO_TIMESTAMP('2022-10-31 17:42:04.003','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',20,TO_TIMESTAMP('2022-10-31 17:42:04.003','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-31T15:42:04.230Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546432,549989,TO_TIMESTAMP('2022-10-31 17:42:04.148','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-10-31 17:42:04.148','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Mandant
-- Column: M_HU_UniqueAttribute.AD_Client_ID
-- 2022-10-31T15:42:04.370Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707916,0,546663,549989,613350,'F',TO_TIMESTAMP('2022-10-31 17:42:04.275','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',10,10,0,TO_TIMESTAMP('2022-10-31 17:42:04.275','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Sektion
-- Column: M_HU_UniqueAttribute.AD_Org_ID
-- 2022-10-31T15:42:04.454Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707917,0,546663,549989,613351,'F',TO_TIMESTAMP('2022-10-31 17:42:04.373','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',20,20,0,TO_TIMESTAMP('2022-10-31 17:42:04.373','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Erstellt
-- Column: M_HU_UniqueAttribute.Created
-- 2022-10-31T15:42:04.574Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707918,0,546663,549989,613352,'F',TO_TIMESTAMP('2022-10-31 17:42:04.458','YYYY-MM-DD HH24:MI:SS.US'),100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','Y','Y','N','Erstellt',30,30,0,TO_TIMESTAMP('2022-10-31 17:42:04.458','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Erstellt durch
-- Column: M_HU_UniqueAttribute.CreatedBy
-- 2022-10-31T15:42:04.659Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707919,0,546663,549989,613353,'F',TO_TIMESTAMP('2022-10-31 17:42:04.579','YYYY-MM-DD HH24:MI:SS.US'),100,'Nutzer, der diesen Eintrag erstellt hat','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','Y','Y','N','Erstellt durch',40,40,0,TO_TIMESTAMP('2022-10-31 17:42:04.579','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Aktiv
-- Column: M_HU_UniqueAttribute.IsActive
-- 2022-10-31T15:42:04.747Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707920,0,546663,549989,613354,'F',TO_TIMESTAMP('2022-10-31 17:42:04.663','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','Y','N','Aktiv',50,50,0,TO_TIMESTAMP('2022-10-31 17:42:04.663','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Aktualisiert
-- Column: M_HU_UniqueAttribute.Updated
-- 2022-10-31T15:42:04.838Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707921,0,546663,549989,613355,'F',TO_TIMESTAMP('2022-10-31 17:42:04.75','YYYY-MM-DD HH24:MI:SS.US'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','Y','Y','N','Aktualisiert',60,60,0,TO_TIMESTAMP('2022-10-31 17:42:04.75','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Aktualisiert durch
-- Column: M_HU_UniqueAttribute.UpdatedBy
-- 2022-10-31T15:42:04.919Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707922,0,546663,549989,613356,'F',TO_TIMESTAMP('2022-10-31 17:42:04.841','YYYY-MM-DD HH24:MI:SS.US'),100,'Nutzer, der diesen Eintrag aktualisiert hat','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','Y','Y','N','Aktualisiert durch',70,70,0,TO_TIMESTAMP('2022-10-31 17:42:04.841','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Produkt
-- Column: M_HU_UniqueAttribute.M_Product_ID
-- 2022-10-31T15:42:05.005Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707924,0,546663,549989,613357,'F',TO_TIMESTAMP('2022-10-31 17:42:04.922','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','Y','N','Produkt',80,80,0,TO_TIMESTAMP('2022-10-31 17:42:04.922','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Merkmal
-- Column: M_HU_UniqueAttribute.M_Attribute_ID
-- 2022-10-31T15:42:05.098Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707925,0,546663,549989,613358,'F',TO_TIMESTAMP('2022-10-31 17:42:05.009','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt-Merkmal','Product Attribute like Color, Size','Y','N','Y','Y','N','Merkmal',90,90,0,TO_TIMESTAMP('2022-10-31 17:42:05.009','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Handling Unit
-- Column: M_HU_UniqueAttribute.M_HU_ID
-- 2022-10-31T15:42:05.198Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707926,0,546663,549989,613359,'F',TO_TIMESTAMP('2022-10-31 17:42:05.101','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','Y','N','Handling Unit',100,100,0,TO_TIMESTAMP('2022-10-31 17:42:05.101','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Suchschlüssel
-- Column: M_HU_UniqueAttribute.Value
-- 2022-10-31T15:42:05.284Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707927,0,546663,549989,613360,'F',TO_TIMESTAMP('2022-10-31 17:42:05.202','YYYY-MM-DD HH24:MI:SS.US'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','Y','N','Suchschlüssel',110,110,0,TO_TIMESTAMP('2022-10-31 17:42:05.202','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Handling Units Packing Instructions Attribute
-- Column: M_HU_UniqueAttribute.M_HU_PI_Attribute_ID
-- 2022-10-31T15:42:05.373Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707928,0,546663,549989,613361,'F',TO_TIMESTAMP('2022-10-31 17:42:05.287','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','Y','N','Handling Units Packing Instructions Attribute',120,120,0,TO_TIMESTAMP('2022-10-31 17:42:05.287','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Handling Unit Attribute
-- Column: M_HU_UniqueAttribute.M_HU_Attribute_ID
-- 2022-10-31T15:42:05.465Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,707929,0,546663,549989,613362,'F',TO_TIMESTAMP('2022-10-31 17:42:05.376','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','Y','N','Handling Unit Attribute',130,130,0,TO_TIMESTAMP('2022-10-31 17:42:05.376','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-31T15:42:46.217Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546433,549990,TO_TIMESTAMP('2022-10-31 17:42:46.086','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','flags',10,TO_TIMESTAMP('2022-10-31 17:42:46.086','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-31T15:42:53.660Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546433,549991,TO_TIMESTAMP('2022-10-31 17:42:53.534','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','other',20,TO_TIMESTAMP('2022-10-31 17:42:53.534','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-31T15:42:56.981Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546433,549992,TO_TIMESTAMP('2022-10-31 17:42:56.88','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','org',30,TO_TIMESTAMP('2022-10-31 17:42:56.88','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Sektion
-- Column: M_HU_UniqueAttribute.AD_Org_ID
-- 2022-10-31T15:48:15.434Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=549992, SeqNo=10,Updated=TO_TIMESTAMP('2022-10-31 17:48:15.434','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613351
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Mandant
-- Column: M_HU_UniqueAttribute.AD_Client_ID
-- 2022-10-31T15:58:25.171Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=549992, SeqNo=20,Updated=TO_TIMESTAMP('2022-10-31 17:58:25.171','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613350
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Aktiv
-- Column: M_HU_UniqueAttribute.IsActive
-- 2022-10-31T15:58:36.151Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=549990, SeqNo=10,Updated=TO_TIMESTAMP('2022-10-31 17:58:36.151','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613354
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Erstellt
-- Column: M_HU_UniqueAttribute.Created
-- 2022-10-31T15:58:44.355Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2022-10-31 17:58:44.355','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613352
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Erstellt durch
-- Column: M_HU_UniqueAttribute.CreatedBy
-- 2022-10-31T15:58:45.192Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2022-10-31 17:58:45.192','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613353
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Aktualisiert
-- Column: M_HU_UniqueAttribute.Updated
-- 2022-10-31T15:58:46.764Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2022-10-31 17:58:46.764','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613355
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Aktualisiert durch
-- Column: M_HU_UniqueAttribute.UpdatedBy
-- 2022-10-31T15:58:55.903Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2022-10-31 17:58:55.902','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613356
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Suchschlüssel
-- Column: M_HU_UniqueAttribute.Value
-- 2022-10-31T15:59:10.498Z
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2022-10-31 17:59:10.497','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613360
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Handling Unit
-- Column: M_HU_UniqueAttribute.M_HU_ID
-- 2022-10-31T15:59:16.631Z
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2022-10-31 17:59:16.631','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613359
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Mandant
-- Column: M_HU_UniqueAttribute.AD_Client_ID
-- 2022-10-31T15:59:43.499Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-10-31 17:59:43.499','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613350
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Erstellt
-- Column: M_HU_UniqueAttribute.Created
-- 2022-10-31T15:59:43.514Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-10-31 17:59:43.513','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613352
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Erstellt durch
-- Column: M_HU_UniqueAttribute.CreatedBy
-- 2022-10-31T15:59:43.520Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-10-31 17:59:43.519','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613353
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Aktiv
-- Column: M_HU_UniqueAttribute.IsActive
-- 2022-10-31T15:59:43.525Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-10-31 17:59:43.525','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613354
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Aktualisiert
-- Column: M_HU_UniqueAttribute.Updated
-- 2022-10-31T15:59:43.531Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-10-31 17:59:43.531','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613355
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Aktualisiert durch
-- Column: M_HU_UniqueAttribute.UpdatedBy
-- 2022-10-31T15:59:43.538Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-10-31 17:59:43.538','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613356
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Produkt
-- Column: M_HU_UniqueAttribute.M_Product_ID
-- 2022-10-31T15:59:43.543Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-10-31 17:59:43.543','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613357
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Merkmal
-- Column: M_HU_UniqueAttribute.M_Attribute_ID
-- 2022-10-31T15:59:43.549Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-10-31 17:59:43.549','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613358
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Handling Unit
-- Column: M_HU_UniqueAttribute.M_HU_ID
-- 2022-10-31T15:59:43.555Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-10-31 17:59:43.555','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613359
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Suchschlüssel
-- Column: M_HU_UniqueAttribute.Value
-- 2022-10-31T15:59:43.560Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-10-31 17:59:43.56','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613360
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Handling Units Packing Instructions Attribute
-- Column: M_HU_UniqueAttribute.M_HU_PI_Attribute_ID
-- 2022-10-31T15:59:43.566Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-10-31 17:59:43.566','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613361
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Handling Unit Attribute
-- Column: M_HU_UniqueAttribute.M_HU_Attribute_ID
-- 2022-10-31T15:59:43.571Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-10-31 17:59:43.571','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613362
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Sektion
-- Column: M_HU_UniqueAttribute.AD_Org_ID
-- 2022-10-31T15:59:43.578Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-10-31 17:59:43.578','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613351
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Suchschlüssel
-- Column: M_HU_UniqueAttribute.Value
-- 2022-10-31T15:59:50.787Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-10-31 17:59:50.787','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613360
;

-- UI Element: Handling Unit Unique Attributes -> Handling Unit Unique Attributes.Handling Unit
-- Column: M_HU_UniqueAttribute.M_HU_ID
-- 2022-10-31T15:59:50.796Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-10-31 17:59:50.796','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=613359
;

-- Column: M_HU_UniqueAttribute.M_Attribute_ID
-- 2022-10-31T16:00:19.649Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-10-31 18:00:19.647','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=584856
;

-- Column: M_HU_UniqueAttribute.M_HU_ID
-- 2022-10-31T16:00:33.697Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-10-31 18:00:33.695','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=584857
;

-- Column: M_HU_UniqueAttribute.M_Product_ID
-- 2022-10-31T16:00:51.319Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-10-31 18:00:51.317','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=584855
;

-- Column: M_HU_UniqueAttribute.Value
-- 2022-10-31T16:01:05.288Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-10-31 18:01:05.286','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=584858
;

-- Column: M_HU_UniqueAttribute.M_Product_ID
-- 2022-10-31T16:01:16.784Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2022-10-31 18:01:16.784','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=584855
;

-- Column: M_HU_UniqueAttribute.M_Attribute_ID
-- 2022-10-31T16:01:17.146Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2022-10-31 18:01:17.146','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=584856
;

-- Column: M_HU_UniqueAttribute.Value
-- 2022-10-31T16:01:17.554Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2022-10-31 18:01:17.554','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=584858
;

-- Column: M_HU_UniqueAttribute.M_HU_ID
-- 2022-10-31T16:01:17.910Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2022-10-31 18:01:17.91','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=584857
;

-- Column: M_HU_UniqueAttribute.AD_Org_ID
-- 2022-10-31T16:01:18.335Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2022-10-31 18:01:18.335','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=584848
;




-- 2022-10-31T20:00:03.159Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,581628,542018,0,541626,TO_TIMESTAMP('2022-10-31 22:00:02.994','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.handlingunits','M_HU_UniqueAttribute','Y','N','N','N','N','Handling Unit Unique Attributes',TO_TIMESTAMP('2022-10-31 22:00:02.994','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-10-31T20:00:03.160Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542018 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2022-10-31T20:00:03.162Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542018, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542018)
;

-- 2022-10-31T20:00:03.171Z
/* DDL */  select update_menu_translation_from_ad_element(581628) 
;

-- Reordering children of `Logistics`
-- Node name: `Tour (M_Tour)`
-- 2022-10-31T20:00:03.753Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- Node name: `Tourversion (M_TourVersion)`
-- 2022-10-31T20:00:03.755Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- Node name: `Delivery Days (M_DeliveryDay)`
-- 2022-10-31T20:00:03.756Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- Node name: `Distribution Order (DD_Order)`
-- 2022-10-31T20:00:03.757Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- Node name: `Distributions Editor (DD_OrderLine)`
-- 2022-10-31T20:00:03.757Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction (M_HU_PI)`
-- 2022-10-31T20:00:03.758Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction Version (M_HU_PI_Version)`
-- 2022-10-31T20:00:03.759Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- Node name: `CU-TU Allocation (M_HU_PI_Item_Product)`
-- 2022-10-31T20:00:03.760Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541375 AND AD_Tree_ID=10
;

-- Node name: `Packing Material (M_HU_PackingMaterial)`
-- 2022-10-31T20:00:03.761Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit (M_HU)`
-- 2022-10-31T20:00:03.762Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- Node name: `Packaging code (M_HU_PackagingCode)`
-- 2022-10-31T20:00:03.762Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541384 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Transaction (M_HU_Trx_Line)`
-- 2022-10-31T20:00:03.763Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Trace (M_HU_Trace)`
-- 2022-10-31T20:00:03.764Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- Node name: `Transport Disposition (M_Tour_Instance)`
-- 2022-10-31T20:00:03.765Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- Node name: `Transport Delivery (M_DeliveryDay_Alloc)`
-- 2022-10-31T20:00:03.766Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- Node name: `Material Transactions (M_Transaction)`
-- 2022-10-31T20:00:03.767Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- Node name: `Transportation Order (M_ShipperTransportation)`
-- 2022-10-31T20:00:03.768Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- Node name: `Package (M_Package)`
-- 2022-10-31T20:00:03.769Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- Node name: `Internal Use (M_Inventory)`
-- 2022-10-31T20:00:03.770Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- Node name: `GO! Delivery Orders (GO_DeliveryOrder)`
-- 2022-10-31T20:00:03.771Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- Node name: `Der Kurier Delivery Orders (DerKurier_DeliveryOrder)`
-- 2022-10-31T20:00:03.771Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541083 AND AD_Tree_ID=10
;

-- Node name: `DHL Delivery Order (DHL_ShipmentOrder)`
-- 2022-10-31T20:00:03.772Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541388 AND AD_Tree_ID=10
;

-- Node name: `DPD Delivery Order (DPD_StoreOrder)`
-- 2022-10-31T20:00:03.773Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541394 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2022-10-31T20:00:03.774Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2022-10-31T20:00:03.775Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2022-10-31T20:00:03.775Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- Node name: `HU Reservierung (M_HU_Reservation)`
-- 2022-10-31T20:00:03.776Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541117 AND AD_Tree_ID=10
;

-- Node name: `Service Handling Units (M_HU)`
-- 2022-10-31T20:00:03.777Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541572 AND AD_Tree_ID=10
;

-- Node name: `HU QR Code (M_HU_QRCode)`
-- 2022-10-31T20:00:03.778Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541905 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Unique Attributes`
-- 2022-10-31T20:00:03.779Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542018 AND AD_Tree_ID=10
;

-- Reordering children of `Logistics`
-- Node name: `Tour (M_Tour)`
-- 2022-10-31T20:01:34.068Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- Node name: `Tourversion (M_TourVersion)`
-- 2022-10-31T20:01:34.069Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- Node name: `Delivery Days (M_DeliveryDay)`
-- 2022-10-31T20:01:34.070Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- Node name: `Distribution Order (DD_Order)`
-- 2022-10-31T20:01:34.071Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- Node name: `Distributions Editor (DD_OrderLine)`
-- 2022-10-31T20:01:34.072Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction (M_HU_PI)`
-- 2022-10-31T20:01:34.073Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction Version (M_HU_PI_Version)`
-- 2022-10-31T20:01:34.073Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- Node name: `CU-TU Allocation (M_HU_PI_Item_Product)`
-- 2022-10-31T20:01:34.075Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541375 AND AD_Tree_ID=10
;

-- Node name: `Packing Material (M_HU_PackingMaterial)`
-- 2022-10-31T20:01:34.076Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit (M_HU)`
-- 2022-10-31T20:01:34.076Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- Node name: `Packaging code (M_HU_PackagingCode)`
-- 2022-10-31T20:01:34.077Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541384 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Transaction (M_HU_Trx_Line)`
-- 2022-10-31T20:01:34.078Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Trace (M_HU_Trace)`
-- 2022-10-31T20:01:34.079Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Unique Attributes`
-- 2022-10-31T20:01:34.080Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542018 AND AD_Tree_ID=10
;

-- Node name: `Transport Disposition (M_Tour_Instance)`
-- 2022-10-31T20:01:34.081Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- Node name: `Transport Delivery (M_DeliveryDay_Alloc)`
-- 2022-10-31T20:01:34.082Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- Node name: `Material Transactions (M_Transaction)`
-- 2022-10-31T20:01:34.082Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- Node name: `Transportation Order (M_ShipperTransportation)`
-- 2022-10-31T20:01:34.083Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- Node name: `Package (M_Package)`
-- 2022-10-31T20:01:34.084Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- Node name: `Internal Use (M_Inventory)`
-- 2022-10-31T20:01:34.085Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- Node name: `GO! Delivery Orders (GO_DeliveryOrder)`
-- 2022-10-31T20:01:34.086Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- Node name: `Der Kurier Delivery Orders (DerKurier_DeliveryOrder)`
-- 2022-10-31T20:01:34.087Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541083 AND AD_Tree_ID=10
;

-- Node name: `DHL Delivery Order (DHL_ShipmentOrder)`
-- 2022-10-31T20:01:34.087Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541388 AND AD_Tree_ID=10
;

-- Node name: `DPD Delivery Order (DPD_StoreOrder)`
-- 2022-10-31T20:01:34.088Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541394 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2022-10-31T20:01:34.089Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2022-10-31T20:01:34.090Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2022-10-31T20:01:34.090Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- Node name: `HU Reservierung (M_HU_Reservation)`
-- 2022-10-31T20:01:34.091Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541117 AND AD_Tree_ID=10
;

-- Node name: `Service Handling Units (M_HU)`
-- 2022-10-31T20:01:34.093Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541572 AND AD_Tree_ID=10
;

-- Node name: `HU QR Code (M_HU_QRCode)`
-- 2022-10-31T20:01:34.093Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541905 AND AD_Tree_ID=10
;

-- Tab: Handling Unit Unique Attributes -> Handling Unit Unique Attributes
-- Table: M_HU_UniqueAttribute
-- 2022-11-02T16:01:25.109Z
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-11-02 18:01:25.109','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=546663
;



