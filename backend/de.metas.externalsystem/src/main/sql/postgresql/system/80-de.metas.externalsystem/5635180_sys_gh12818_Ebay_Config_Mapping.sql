-- 2022-04-13T16:24:27.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582768,449,0,30,541741,'M_PriceList_ID',TO_TIMESTAMP('2022-04-13 19:24:26','YYYY-MM-DD HH24:MI:SS'),100,'N','Bezeichnung der Preisliste','de.metas.externalsystem',0,10,'Preislisten werden verwendet, um Preis, Spanne und Kosten einer ge- oder verkauften Ware zu bestimmen.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Preisliste',0,0,TO_TIMESTAMP('2022-04-13 19:24:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-04-13T16:24:27.191Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582768 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-04-13T16:24:27.194Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(449) 
;

-- 2022-04-13T16:24:27.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Ebay','ALTER TABLE public.ExternalSystem_Config_Ebay ADD COLUMN M_PriceList_ID NUMERIC(10)')
;

-- 2022-04-13T16:24:27.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE ExternalSystem_Config_Ebay ADD CONSTRAINT MPriceList_ExternalSystemConfigEbay FOREIGN KEY (M_PriceList_ID) REFERENCES public.M_PriceList DEFERRABLE INITIALLY DEFERRED
;

-- 2022-04-13T16:31:28.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_Window_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,542124,541024,'N',TO_TIMESTAMP('2022-04-13 19:31:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','N','Y','N','N','N','N','N','N','N','N',0,'ExternalSystem_Config_Ebay_Mapping','NP','L','ExternalSystem_Config_Ebay_Mapping','DTI',TO_TIMESTAMP('2022-04-13 19:31:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-13T16:31:28.747Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542124 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2022-04-13T16:31:28.881Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555859,TO_TIMESTAMP('2022-04-13 19:31:28','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table ExternalSystem_Config_Ebay_Mapping',1,'Y','N','Y','Y','ExternalSystem_Config_Ebay_Mapping','N',1000000,TO_TIMESTAMP('2022-04-13 19:31:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-13T16:31:28.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE SEQUENCE EXTERNALSYSTEM_CONFIG_EBAY_MAPPING_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2022-04-13T16:33:04.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582769,566,0,11,542124,'SeqNo',TO_TIMESTAMP('2022-04-13 19:33:04','YYYY-MM-DD HH24:MI:SS'),100,'N','0','Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','de.metas.externalsystem',0,2,'"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Reihenfolge',0,0,TO_TIMESTAMP('2022-04-13 19:33:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-04-13T16:33:04.681Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582769 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-04-13T16:33:04.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(566) 
;

-- 2022-04-13T16:34:00.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582770,102,0,19,542124,'AD_Client_ID',TO_TIMESTAMP('2022-04-13 19:33:59','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','de.metas.externalsystem',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2022-04-13 19:33:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-04-13T16:34:00.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582770 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-04-13T16:34:00.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- 2022-04-13T16:34:01.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582771,113,0,30,542124,'AD_Org_ID',TO_TIMESTAMP('2022-04-13 19:34:01','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','de.metas.externalsystem',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2022-04-13 19:34:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-04-13T16:34:01.202Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582771 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-04-13T16:34:01.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- 2022-04-13T16:34:01.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582772,245,0,16,542124,'Created',TO_TIMESTAMP('2022-04-13 19:34:01','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.externalsystem',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2022-04-13 19:34:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-04-13T16:34:01.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582772 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-04-13T16:34:01.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- 2022-04-13T16:34:02.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582773,246,0,18,110,542124,'CreatedBy',TO_TIMESTAMP('2022-04-13 19:34:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.externalsystem',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2022-04-13 19:34:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-04-13T16:34:02.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582773 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-04-13T16:34:02.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- 2022-04-13T16:34:03.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582774,348,0,20,542124,'IsActive',TO_TIMESTAMP('2022-04-13 19:34:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','de.metas.externalsystem',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2022-04-13 19:34:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-04-13T16:34:03.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582774 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-04-13T16:34:03.052Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- 2022-04-13T16:34:03.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582775,607,0,16,542124,'Updated',TO_TIMESTAMP('2022-04-13 19:34:03','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.externalsystem',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2022-04-13 19:34:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-04-13T16:34:03.730Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582775 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-04-13T16:34:03.733Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- 2022-04-13T16:34:04.348Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582776,608,0,18,110,542124,'UpdatedBy',TO_TIMESTAMP('2022-04-13 19:34:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.externalsystem',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2022-04-13 19:34:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-04-13T16:34:04.350Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582776 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-04-13T16:34:04.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2022-04-13T16:34:04.921Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580774,0,'ExternalSystem_Config_Ebay_Mapping_ID',TO_TIMESTAMP('2022-04-13 19:34:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','ExternalSystem_Config_Ebay_Mapping','ExternalSystem_Config_Ebay_Mapping',TO_TIMESTAMP('2022-04-13 19:34:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-13T16:34:04.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580774 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-04-13T16:34:05.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582777,580774,0,13,542124,'ExternalSystem_Config_Ebay_Mapping_ID',TO_TIMESTAMP('2022-04-13 19:34:04','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','ExternalSystem_Config_Ebay_Mapping',0,0,TO_TIMESTAMP('2022-04-13 19:34:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-04-13T16:34:05.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582777 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-04-13T16:34:05.413Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(580774) 
;

-- Column: ExternalSystem_Config_Ebay_Mapping.ExternalSystem_Config_Ebay_ID
-- 2022-04-13T16:57:28.038Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582778,579444,0,19,542124,'ExternalSystem_Config_Ebay_ID',TO_TIMESTAMP('2022-04-13 19:57:27','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'eBay',0,0,TO_TIMESTAMP('2022-04-13 19:57:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-04-13T16:57:28.041Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582778 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-04-13T16:57:28.075Z
/* DDL */  select update_Column_Translation_From_AD_Element(579444) 
;

-- Column: ExternalSystem_Config_Ebay_Mapping.C_DocTypeOrder_ID
-- 2022-04-13T16:58:58.524Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582779,577366,0,30,170,542124,'C_DocTypeOrder_ID',TO_TIMESTAMP('2022-04-13 19:58:58','YYYY-MM-DD HH24:MI:SS'),100,'N','Document type used for the orders generated from this order candidate','de.metas.externalsystem',0,10,'The Document Type for Order indicates the document type that will be used when an order is generated from this order candidate.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Auftrags-Belegart',0,0,TO_TIMESTAMP('2022-04-13 19:58:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-04-13T16:58:58.525Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582779 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-04-13T16:58:58.528Z
/* DDL */  select update_Column_Translation_From_AD_Element(577366) 
;

-- Column: ExternalSystem_Config_Ebay_Mapping.PaymentRule
-- 2022-04-13T16:59:22.093Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582780,1143,0,10,542124,'PaymentRule',TO_TIMESTAMP('2022-04-13 19:59:21','YYYY-MM-DD HH24:MI:SS'),100,'N','Wie die Rechnung bezahlt wird','de.metas.externalsystem',0,500,'Die Zahlungsweise zeigt die Art der Bezahlung der Rechnung an.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Zahlungsweise',0,0,TO_TIMESTAMP('2022-04-13 19:59:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-04-13T16:59:22.095Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582780 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-04-13T16:59:22.098Z
/* DDL */  select update_Column_Translation_From_AD_Element(1143) 
;

-- 2022-04-13T17:01:41.622Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580775,0,'Ebay_Mapping_BPartnerIfExists',TO_TIMESTAMP('2022-04-13 20:01:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Partner if exists','Partner if exists',TO_TIMESTAMP('2022-04-13 20:01:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-13T17:01:41.624Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580775 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-04-13T17:02:28.814Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580776,0,'Ebay_Mapping_BPartnerIfNotExists',TO_TIMESTAMP('2022-04-13 20:02:28','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Partner if not exists','Partner if not exists',TO_TIMESTAMP('2022-04-13 20:02:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-13T17:02:28.815Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580776 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-04-13T17:02:54.945Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580777,0,'Ebay_Mapping_BPartnerLocation_IfExists',TO_TIMESTAMP('2022-04-13 20:02:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Partner location if exists','Partner location if exists',TO_TIMESTAMP('2022-04-13 20:02:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-13T17:02:54.946Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580777 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-04-13T17:03:03.509Z
UPDATE AD_Element SET ColumnName='Ebay_Mapping_BPartner_IfExists',Updated=TO_TIMESTAMP('2022-04-13 20:03:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580775
;

-- 2022-04-13T17:03:03.517Z
UPDATE AD_Column SET ColumnName='Ebay_Mapping_BPartner_IfExists', Name='Partner if exists', Description=NULL, Help=NULL WHERE AD_Element_ID=580775
;

-- 2022-04-13T17:03:03.518Z
UPDATE AD_Process_Para SET ColumnName='Ebay_Mapping_BPartner_IfExists', Name='Partner if exists', Description=NULL, Help=NULL, AD_Element_ID=580775 WHERE UPPER(ColumnName)='EBAY_MAPPING_BPARTNER_IFEXISTS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-04-13T17:03:03.520Z
UPDATE AD_Process_Para SET ColumnName='Ebay_Mapping_BPartner_IfExists', Name='Partner if exists', Description=NULL, Help=NULL WHERE AD_Element_ID=580775 AND IsCentrallyMaintained='Y'
;

-- 2022-04-13T17:03:12.433Z
UPDATE AD_Element SET ColumnName='Ebay_Mapping_BPartner_IfNotExists',Updated=TO_TIMESTAMP('2022-04-13 20:03:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580776
;

-- 2022-04-13T17:03:12.436Z
UPDATE AD_Column SET ColumnName='Ebay_Mapping_BPartner_IfNotExists', Name='Partner if not exists', Description=NULL, Help=NULL WHERE AD_Element_ID=580776
;

-- 2022-04-13T17:03:12.437Z
UPDATE AD_Process_Para SET ColumnName='Ebay_Mapping_BPartner_IfNotExists', Name='Partner if not exists', Description=NULL, Help=NULL, AD_Element_ID=580776 WHERE UPPER(ColumnName)='EBAY_MAPPING_BPARTNER_IFNOTEXISTS' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-04-13T17:03:12.438Z
UPDATE AD_Process_Para SET ColumnName='Ebay_Mapping_BPartner_IfNotExists', Name='Partner if not exists', Description=NULL, Help=NULL WHERE AD_Element_ID=580776 AND IsCentrallyMaintained='Y'
;

-- 2022-04-13T17:03:41.258Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580778,0,'Ebay_Mapping_BPartnerLocation_IfNotExists',TO_TIMESTAMP('2022-04-13 20:03:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','Partner location if not exists','Partner location if not exists',TO_TIMESTAMP('2022-04-13 20:03:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-13T17:03:41.259Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580778 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: ExternalSystem_Config_Ebay_Mapping.BPartner_IfExists
-- 2022-04-13T17:04:09.159Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582781,579145,0,10,542124,'BPartner_IfExists',TO_TIMESTAMP('2022-04-13 20:04:08','YYYY-MM-DD HH24:MI:SS'),100,'N','Legt fest, was passieren soll, wenn ein Shopware-Kunde schon als Geschäftspartner in metasfresh angelegt ist.','de.metas.externalsystem',0,500,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Wenn Gesch.-Partner ex.',0,0,TO_TIMESTAMP('2022-04-13 20:04:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-04-13T17:04:09.161Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582781 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-04-13T17:04:09.164Z
/* DDL */  select update_Column_Translation_From_AD_Element(579145) 
;

-- Column: ExternalSystem_Config_Ebay_Mapping.BPartner_IfNotExists
-- 2022-04-13T17:04:43.343Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582782,579146,0,10,542124,'BPartner_IfNotExists',TO_TIMESTAMP('2022-04-13 20:04:43','YYYY-MM-DD HH24:MI:SS'),100,'N','Legt fest, was passieren soll, wenn ein Shopware-Kunde noch nicht als Geschäftspartner in metasfresh angelegt ist.','de.metas.externalsystem',0,500,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Wenn Gesch.-Partner nicht ex.',0,0,TO_TIMESTAMP('2022-04-13 20:04:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-04-13T17:04:43.345Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582782 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-04-13T17:04:43.348Z
/* DDL */  select update_Column_Translation_From_AD_Element(579146) 
;

-- 2022-04-13T17:05:31.067Z
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=580775
;

-- 2022-04-13T17:05:31.073Z
DELETE FROM AD_Element WHERE AD_Element_ID=580775
;

-- 2022-04-13T17:05:33.851Z
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=580776
;

-- 2022-04-13T17:05:33.857Z
DELETE FROM AD_Element WHERE AD_Element_ID=580776
;

-- 2022-04-13T17:05:36.151Z
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=580777
;

-- 2022-04-13T17:05:36.157Z
DELETE FROM AD_Element WHERE AD_Element_ID=580777
;

-- 2022-04-13T17:05:38.614Z
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=580778
;

-- 2022-04-13T17:05:38.620Z
DELETE FROM AD_Element WHERE AD_Element_ID=580778
;

-- Column: ExternalSystem_Config_Ebay_Mapping.BPartnerLocation_IfExists
-- 2022-04-13T17:05:57.073Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582783,579147,0,10,542124,'BPartnerLocation_IfExists',TO_TIMESTAMP('2022-04-13 20:05:56','YYYY-MM-DD HH24:MI:SS'),100,'N','Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden schon in metasfresh angelegt ist.','de.metas.externalsystem',0,500,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Wenn Adresse ex.',0,0,TO_TIMESTAMP('2022-04-13 20:05:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-04-13T17:05:57.075Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582783 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-04-13T17:05:57.077Z
/* DDL */  select update_Column_Translation_From_AD_Element(579147) 
;

-- Column: ExternalSystem_Config_Ebay_Mapping.BPartnerLocation_IfNotExists
-- 2022-04-13T17:06:06.084Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582784,579148,0,10,542124,'BPartnerLocation_IfNotExists',TO_TIMESTAMP('2022-04-13 20:06:05','YYYY-MM-DD HH24:MI:SS'),100,'N','Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden noch nicht in metasfresh angelegt ist.','de.metas.externalsystem',0,500,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Wenn Adr. nicht ex.',0,0,TO_TIMESTAMP('2022-04-13 20:06:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-04-13T17:06:06.085Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582784 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-04-13T17:06:06.088Z
/* DDL */  select update_Column_Translation_From_AD_Element(579148) 
;

-- Column: ExternalSystem_Config_Ebay_Mapping.IsInvoiceEmailEnabled
-- 2022-04-13T17:06:16.973Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582785,543189,0,20,542124,'IsInvoiceEmailEnabled',TO_TIMESTAMP('2022-04-13 20:06:16','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.externalsystem',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Rechnung per eMail',0,0,TO_TIMESTAMP('2022-04-13 20:06:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-04-13T17:06:16.975Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582785 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-04-13T17:06:16.977Z
/* DDL */  select update_Column_Translation_From_AD_Element(543189) 
;

-- Column: ExternalSystem_Config_Ebay_Mapping.C_PaymentTerm_ID
-- 2022-04-13T17:06:36.985Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582786,204,0,30,542124,'C_PaymentTerm_ID',TO_TIMESTAMP('2022-04-13 20:06:36','YYYY-MM-DD HH24:MI:SS'),100,'N','Die Bedingungen für die Bezahlung dieses Vorgangs','de.metas.externalsystem',0,10,'Die Zahlungskondition bestimmt Fristen und Bedingungen für Zahlungen zu diesem Vorgang.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Zahlungsbedingung',0,0,TO_TIMESTAMP('2022-04-13 20:06:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-04-13T17:06:36.987Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582786 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-04-13T17:06:36.990Z
/* DDL */  select update_Column_Translation_From_AD_Element(204) 
;

-- 2022-04-13T17:07:13.491Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580779,0,'ebayCustomerGroup',TO_TIMESTAMP('2022-04-13 20:07:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','eBay Customer Group','eBay Customer Group',TO_TIMESTAMP('2022-04-13 20:07:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-13T17:07:13.493Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580779 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-04-13T17:07:37.513Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580780,0,'ebayPaymentMethod',TO_TIMESTAMP('2022-04-13 20:07:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','eBay Payment Method','eBay Payment Method',TO_TIMESTAMP('2022-04-13 20:07:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-13T17:07:37.515Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580780 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: ExternalSystem_Config_Ebay_Mapping.ebayCustomerGroup
-- 2022-04-13T17:07:51.388Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582787,580779,0,10,542124,'ebayCustomerGroup',TO_TIMESTAMP('2022-04-13 20:07:51','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,500,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'eBay Customer Group',0,0,TO_TIMESTAMP('2022-04-13 20:07:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-04-13T17:07:51.390Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582787 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-04-13T17:07:51.392Z
/* DDL */  select update_Column_Translation_From_AD_Element(580779) 
;

-- Column: ExternalSystem_Config_Ebay_Mapping.ebayPaymentMethod
-- 2022-04-13T17:08:00.603Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582788,580780,0,10,542124,'ebayPaymentMethod',TO_TIMESTAMP('2022-04-13 20:08:00','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,500,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'eBay Payment Method',0,0,TO_TIMESTAMP('2022-04-13 20:08:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-04-13T17:08:00.605Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582788 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-04-13T17:08:00.607Z
/* DDL */  select update_Column_Translation_From_AD_Element(580780) 
;

-- Column: ExternalSystem_Config_Ebay_Mapping.Description
-- 2022-04-13T17:08:18.387Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582789,275,0,10,542124,'Description',TO_TIMESTAMP('2022-04-13 20:08:18','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,1000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Beschreibung',0,0,TO_TIMESTAMP('2022-04-13 20:08:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-04-13T17:08:18.388Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582789 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-04-13T17:08:18.391Z
/* DDL */  select update_Column_Translation_From_AD_Element(275) 
;

-- Column: ExternalSystem_Config_Ebay_Mapping.BPartner_IfExists
-- 2022-04-13T17:08:44.313Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-04-13 20:08:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582781
;

-- Column: ExternalSystem_Config_Ebay_Mapping.BPartner_IfNotExists
-- 2022-04-13T17:08:50.039Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-04-13 20:08:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582782
;

-- Column: ExternalSystem_Config_Ebay_Mapping.BPartnerLocation_IfExists
-- 2022-04-13T17:08:53.086Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-04-13 20:08:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582783
;

-- Column: ExternalSystem_Config_Ebay_Mapping.BPartnerLocation_IfNotExists
-- 2022-04-13T17:09:01.234Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-04-13 20:09:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582784
;

-- 2022-04-14T08:46:52.990Z
UPDATE AD_Element SET ColumnName='EBayPaymentMethod',Updated=TO_TIMESTAMP('2022-04-14 11:46:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580780
;

-- 2022-04-14T08:46:52.996Z
UPDATE AD_Column SET ColumnName='EBayPaymentMethod', Name='eBay Payment Method', Description=NULL, Help=NULL WHERE AD_Element_ID=580780
;

-- 2022-04-14T08:46:52.997Z
UPDATE AD_Process_Para SET ColumnName='EBayPaymentMethod', Name='eBay Payment Method', Description=NULL, Help=NULL, AD_Element_ID=580780 WHERE UPPER(ColumnName)='EBAYPAYMENTMETHOD' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-04-14T08:46:52.999Z
UPDATE AD_Process_Para SET ColumnName='EBayPaymentMethod', Name='eBay Payment Method', Description=NULL, Help=NULL WHERE AD_Element_ID=580780 AND IsCentrallyMaintained='Y'
;

-- 2022-04-14T08:47:28.889Z
UPDATE AD_Element SET ColumnName='EBayCustomerGroup',Updated=TO_TIMESTAMP('2022-04-14 11:47:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580779
;

-- 2022-04-14T08:47:28.893Z
UPDATE AD_Column SET ColumnName='EBayCustomerGroup', Name='eBay Customer Group', Description=NULL, Help=NULL WHERE AD_Element_ID=580779
;

-- 2022-04-14T08:47:28.895Z
UPDATE AD_Process_Para SET ColumnName='EBayCustomerGroup', Name='eBay Customer Group', Description=NULL, Help=NULL, AD_Element_ID=580779 WHERE UPPER(ColumnName)='EBAYCUSTOMERGROUP' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-04-14T08:47:28.896Z
UPDATE AD_Process_Para SET ColumnName='EBayCustomerGroup', Name='eBay Customer Group', Description=NULL, Help=NULL WHERE AD_Element_ID=580779 AND IsCentrallyMaintained='Y'
;

-- 2022-04-14T08:48:52.169Z
/* DDL */ CREATE TABLE public.ExternalSystem_Config_Ebay_Mapping (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, BPartner_IfExists VARCHAR(500) NOT NULL, BPartner_IfNotExists VARCHAR(500) NOT NULL, BPartnerLocation_IfExists VARCHAR(500) NOT NULL, BPartnerLocation_IfNotExists VARCHAR(500) NOT NULL, C_DocTypeOrder_ID NUMERIC(10), C_PaymentTerm_ID NUMERIC(10), Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Description VARCHAR(1000), EBayCustomerGroup VARCHAR(500), EBayPaymentMethod VARCHAR(500), ExternalSystem_Config_Ebay_ID NUMERIC(10), ExternalSystem_Config_Ebay_Mapping_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, IsInvoiceEmailEnabled CHAR(1) DEFAULT 'N' CHECK (IsInvoiceEmailEnabled IN ('Y','N')) NOT NULL, PaymentRule VARCHAR(500), SeqNo NUMERIC(10) DEFAULT 0 NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CDocTypeOrder_ExternalSystemConfigEbayMapping FOREIGN KEY (C_DocTypeOrder_ID) REFERENCES public.C_DocType DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CPaymentTerm_ExternalSystemConfigEbayMapping FOREIGN KEY (C_PaymentTerm_ID) REFERENCES public.C_PaymentTerm DEFERRABLE INITIALLY DEFERRED, CONSTRAINT ExternalSystemConfigEbay_ExternalSystemConfigEbayMapping FOREIGN KEY (ExternalSystem_Config_Ebay_ID) REFERENCES public.ExternalSystem_Config_Ebay DEFERRABLE INITIALLY DEFERRED, CONSTRAINT ExternalSystem_Config_Ebay_Mapping_Key PRIMARY KEY (ExternalSystem_Config_Ebay_Mapping_ID))
;




-- Column: ExternalSystem_Config_Ebay_Mapping.BPartner_IfExists
-- 2022-04-14T10:10:50.830Z
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=541309,Updated=TO_TIMESTAMP('2022-04-14 13:10:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582781
;

-- 2022-04-14T10:10:51.622Z
INSERT INTO t_alter_column values('externalsystem_config_ebay_mapping','BPartner_IfExists','VARCHAR(500)',null,null)
;

-- Column: ExternalSystem_Config_Ebay_Mapping.BPartner_IfNotExists
-- 2022-04-14T10:11:36.675Z
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=541310,Updated=TO_TIMESTAMP('2022-04-14 13:11:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582782
;

-- 2022-04-14T10:11:37.223Z
INSERT INTO t_alter_column values('externalsystem_config_ebay_mapping','BPartner_IfNotExists','VARCHAR(500)',null,null)
;

-- Column: ExternalSystem_Config_Ebay_Mapping.BPartnerLocation_IfExists
-- 2022-04-14T10:12:18.521Z
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=541309,Updated=TO_TIMESTAMP('2022-04-14 13:12:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582783
;

-- 2022-04-14T10:12:19.069Z
INSERT INTO t_alter_column values('externalsystem_config_ebay_mapping','BPartnerLocation_IfExists','VARCHAR(500)',null,null)
;

-- Column: ExternalSystem_Config_Ebay_Mapping.BPartnerLocation_IfNotExists
-- 2022-04-14T10:12:30.919Z
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=541310,Updated=TO_TIMESTAMP('2022-04-14 13:12:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582784
;

-- 2022-04-14T10:12:31.513Z
INSERT INTO t_alter_column values('externalsystem_config_ebay_mapping','BPartnerLocation_IfNotExists','VARCHAR(500)',null,null)
;



-- Table: ExternalSystem_Config_Ebay_Mapping
-- 2022-04-14T10:36:10.670Z
UPDATE AD_Table SET IsDeleteable='Y',Updated=TO_TIMESTAMP('2022-04-14 13:36:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542124
;






-- Column: ExternalSystem_Config_Ebay_Mapping.BPartner_IfExists
-- 2022-04-14T10:37:53.349Z
UPDATE AD_Column SET DefaultValue='UPDATE_MERGE',Updated=TO_TIMESTAMP('2022-04-14 13:37:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582781
;

-- Column: ExternalSystem_Config_Ebay_Mapping.BPartnerLocation_IfExists
-- 2022-04-14T10:37:57.812Z
UPDATE AD_Column SET DefaultValue='UPDATE_MERGE',Updated=TO_TIMESTAMP('2022-04-14 13:37:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582783
;

-- Column: ExternalSystem_Config_Ebay_Mapping.BPartner_IfNotExists
-- 2022-04-14T10:37:59.451Z
UPDATE AD_Column SET DefaultValue='CREATE',Updated=TO_TIMESTAMP('2022-04-14 13:37:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582782
;

-- Column: ExternalSystem_Config_Ebay_Mapping.BPartnerLocation_IfNotExists
-- 2022-04-14T10:38:03.995Z
UPDATE AD_Column SET DefaultValue='CREATE',Updated=TO_TIMESTAMP('2022-04-14 13:38:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582784
;





-- Column: ExternalSystem_Config_Ebay_Mapping.PaymentRule
-- 2022-04-14T15:58:39.985634900Z
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=195, AD_Val_Rule_ID=52033,Updated=TO_TIMESTAMP('2022-04-14 18:58:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582780
;

-- 2022-04-14T15:58:41.246955700Z
INSERT INTO t_alter_column values('externalsystem_config_ebay_mapping','PaymentRule','VARCHAR(500)',null,null)
;




-- Column: ExternalSystem_Config_Ebay_Mapping.C_DocTypeOrder_ID
-- 2022-04-14T16:27:12.214276500Z
UPDATE AD_Column SET AD_Val_Rule_ID=207,Updated=TO_TIMESTAMP('2022-04-14 19:27:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582779
;

-- 2022-04-14T16:27:13.533354900Z
INSERT INTO t_alter_column values('externalsystem_config_ebay_mapping','C_DocTypeOrder_ID','NUMERIC(10)',null,null)
;




