-- Table: SUMUP_Config
-- Table: SUMUP_Config
-- 2024-10-03T17:08:18.011Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('2',0,0,0,542440,'A','N',TO_TIMESTAMP('2024-10-03 20:08:17','YYYY-MM-DD HH24:MI:SS'),100,'A','de.metas.payment.sumup','N','Y','N','Y','Y','N','N','N','N','N',0,'SumUp Configuration','NP','L','SUMUP_Config','DTI',TO_TIMESTAMP('2024-10-03 20:08:17','YYYY-MM-DD HH24:MI:SS'),100,0,'A')
;

-- 2024-10-03T17:08:18.015Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542440 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-10-03T17:08:18.154Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556371,TO_TIMESTAMP('2024-10-03 20:08:18','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table SUMUP_Config',1,'Y','N','Y','Y','SUMUP_Config','N',1000000,TO_TIMESTAMP('2024-10-03 20:08:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T17:08:18.166Z
CREATE SEQUENCE SUMUP_CONFIG_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: SUMUP_Config.AD_Client_ID
-- Column: SUMUP_Config.AD_Client_ID
-- 2024-10-03T17:08:25.541Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589181,102,0,19,542440,'AD_Client_ID',TO_TIMESTAMP('2024-10-03 20:08:25','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','de.metas.payment.sumup',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-10-03 20:08:25','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T17:08:25.546Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589181 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T17:08:25.581Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: SUMUP_Config.AD_Org_ID
-- Column: SUMUP_Config.AD_Org_ID
-- 2024-10-03T17:08:26.339Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589182,113,0,30,542440,'AD_Org_ID',TO_TIMESTAMP('2024-10-03 20:08:26','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','de.metas.payment.sumup',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2024-10-03 20:08:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T17:08:26.341Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589182 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T17:08:26.344Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: SUMUP_Config.Created
-- Column: SUMUP_Config.Created
-- 2024-10-03T17:08:26.896Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589183,245,0,16,542440,'Created',TO_TIMESTAMP('2024-10-03 20:08:26','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.payment.sumup',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-10-03 20:08:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T17:08:26.898Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589183 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T17:08:26.901Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: SUMUP_Config.CreatedBy
-- Column: SUMUP_Config.CreatedBy
-- 2024-10-03T17:08:27.461Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589184,246,0,18,110,542440,'CreatedBy',TO_TIMESTAMP('2024-10-03 20:08:27','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.payment.sumup',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-10-03 20:08:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T17:08:27.464Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589184 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T17:08:27.468Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: SUMUP_Config.IsActive
-- Column: SUMUP_Config.IsActive
-- 2024-10-03T17:08:28.073Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589185,348,0,20,542440,'IsActive',TO_TIMESTAMP('2024-10-03 20:08:27','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','de.metas.payment.sumup',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-10-03 20:08:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T17:08:28.075Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589185 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T17:08:28.077Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: SUMUP_Config.Updated
-- Column: SUMUP_Config.Updated
-- 2024-10-03T17:08:28.685Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589186,607,0,16,542440,'Updated',TO_TIMESTAMP('2024-10-03 20:08:28','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.payment.sumup',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-10-03 20:08:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T17:08:28.687Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589186 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T17:08:28.690Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: SUMUP_Config.UpdatedBy
-- Column: SUMUP_Config.UpdatedBy
-- 2024-10-03T17:08:29.322Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589187,608,0,18,110,542440,'UpdatedBy',TO_TIMESTAMP('2024-10-03 20:08:29','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.payment.sumup',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-10-03 20:08:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T17:08:29.324Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589187 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T17:08:29.327Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2024-10-03T17:08:29.811Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583297,0,'SUMUP_Config_ID',TO_TIMESTAMP('2024-10-03 20:08:29','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','Y','SumUp Configuration','SumUp Configuration',TO_TIMESTAMP('2024-10-03 20:08:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T17:08:29.816Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583297 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: SUMUP_Config.SUMUP_Config_ID
-- Column: SUMUP_Config.SUMUP_Config_ID
-- 2024-10-03T17:08:30.387Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589188,583297,0,13,542440,'SUMUP_Config_ID',TO_TIMESTAMP('2024-10-03 20:08:29','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sumup',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','SumUp Configuration',0,0,TO_TIMESTAMP('2024-10-03 20:08:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T17:08:30.389Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589188 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T17:08:30.391Z
/* DDL */  select update_Column_Translation_From_AD_Element(583297) 
;

-- Column: SUMUP_Config.ApiKey
-- Column: SUMUP_Config.ApiKey
-- 2024-10-03T17:08:53.714Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589189,543990,0,10,542440,'XX','ApiKey',TO_TIMESTAMP('2024-10-03 20:08:53','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sumup',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'API-Key',0,0,TO_TIMESTAMP('2024-10-03 20:08:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T17:08:53.716Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589189 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T17:08:53.720Z
/* DDL */  select update_Column_Translation_From_AD_Element(543990) 
;

-- 2024-10-03T17:11:59.515Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583298,0,'SUMUP_merchant_code',TO_TIMESTAMP('2024-10-03 20:11:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','Y','Merchant Code','Merchant Code',TO_TIMESTAMP('2024-10-03 20:11:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T17:11:59.518Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583298 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: SUMUP_Config.SUMUP_merchant_code
-- Column: SUMUP_Config.SUMUP_merchant_code
-- 2024-10-03T17:12:11.971Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589190,583298,0,10,542440,'XX','SUMUP_merchant_code',TO_TIMESTAMP('2024-10-03 20:12:11','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sumup',0,40,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Merchant Code',0,0,TO_TIMESTAMP('2024-10-03 20:12:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T17:12:11.973Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589190 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T17:12:11.976Z
/* DDL */  select update_Column_Translation_From_AD_Element(583298) 
;

-- 2024-10-03T17:12:59.490Z
/* DDL */ CREATE TABLE public.SUMUP_Config (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, ApiKey VARCHAR(255) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, SUMUP_Config_ID NUMERIC(10) NOT NULL, SUMUP_merchant_code VARCHAR(40) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT SUMUP_Config_Key PRIMARY KEY (SUMUP_Config_ID))
;

-- Window: SumUp Configuration, InternalName=null
-- Window: SumUp Configuration, InternalName=null
-- 2024-10-03T17:13:33.943Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,583297,0,541823,TO_TIMESTAMP('2024-10-03 20:13:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','Y','N','N','N','N','N','N','Y','SumUp Configuration','N',TO_TIMESTAMP('2024-10-03 20:13:33','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2024-10-03T17:13:33.946Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541823 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2024-10-03T17:13:33.950Z
/* DDL */  select update_window_translation_from_ad_element(583297) 
;

-- 2024-10-03T17:13:33.960Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541823
;

-- 2024-10-03T17:13:33.965Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541823)
;

-- Tab: SumUp Configuration -> SumUp Configuration
-- Table: SUMUP_Config
-- Tab: SumUp Configuration(541823,de.metas.payment.sumup) -> SumUp Configuration
-- Table: SUMUP_Config
-- 2024-10-03T17:13:53.120Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583297,0,547614,542440,541823,'Y',TO_TIMESTAMP('2024-10-03 20:13:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','N','N','A','SUMUP_Config','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'SumUp Configuration','N',10,0,TO_TIMESTAMP('2024-10-03 20:13:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T17:13:53.124Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547614 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-10-03T17:13:53.127Z
/* DDL */  select update_tab_translation_from_ad_element(583297) 
;

-- 2024-10-03T17:13:53.132Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547614)
;

-- Field: SumUp Configuration -> SumUp Configuration -> Mandant
-- Column: SUMUP_Config.AD_Client_ID
-- Field: SumUp Configuration(541823,de.metas.payment.sumup) -> SumUp Configuration(547614,de.metas.payment.sumup) -> Mandant
-- Column: SUMUP_Config.AD_Client_ID
-- 2024-10-03T17:14:01.138Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589181,731799,0,547614,TO_TIMESTAMP('2024-10-03 20:13:59','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.payment.sumup','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-10-03 20:13:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T17:14:01.141Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731799 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-03T17:14:01.145Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-10-03T17:14:01.727Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731799
;

-- 2024-10-03T17:14:01.730Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731799)
;

-- Field: SumUp Configuration -> SumUp Configuration -> Organisation
-- Column: SUMUP_Config.AD_Org_ID
-- Field: SumUp Configuration(541823,de.metas.payment.sumup) -> SumUp Configuration(547614,de.metas.payment.sumup) -> Organisation
-- Column: SUMUP_Config.AD_Org_ID
-- 2024-10-03T17:14:01.844Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589182,731800,0,547614,TO_TIMESTAMP('2024-10-03 20:14:01','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.payment.sumup','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2024-10-03 20:14:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T17:14:01.845Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731800 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-03T17:14:01.847Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-10-03T17:14:01.965Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731800
;

-- 2024-10-03T17:14:01.967Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731800)
;

-- Field: SumUp Configuration -> SumUp Configuration -> Aktiv
-- Column: SUMUP_Config.IsActive
-- Field: SumUp Configuration(541823,de.metas.payment.sumup) -> SumUp Configuration(547614,de.metas.payment.sumup) -> Aktiv
-- Column: SUMUP_Config.IsActive
-- 2024-10-03T17:14:02.086Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589185,731801,0,547614,TO_TIMESTAMP('2024-10-03 20:14:01','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.payment.sumup','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-10-03 20:14:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T17:14:02.089Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731801 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-03T17:14:02.091Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-10-03T17:14:02.223Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731801
;

-- 2024-10-03T17:14:02.224Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731801)
;

-- Field: SumUp Configuration -> SumUp Configuration -> SumUp Configuration
-- Column: SUMUP_Config.SUMUP_Config_ID
-- Field: SumUp Configuration(541823,de.metas.payment.sumup) -> SumUp Configuration(547614,de.metas.payment.sumup) -> SumUp Configuration
-- Column: SUMUP_Config.SUMUP_Config_ID
-- 2024-10-03T17:14:02.340Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589188,731802,0,547614,TO_TIMESTAMP('2024-10-03 20:14:02','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','SumUp Configuration',TO_TIMESTAMP('2024-10-03 20:14:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T17:14:02.342Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731802 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-03T17:14:02.344Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583297) 
;

-- 2024-10-03T17:14:02.348Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731802
;

-- 2024-10-03T17:14:02.349Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731802)
;

-- Field: SumUp Configuration -> SumUp Configuration -> API-Key
-- Column: SUMUP_Config.ApiKey
-- Field: SumUp Configuration(541823,de.metas.payment.sumup) -> SumUp Configuration(547614,de.metas.payment.sumup) -> API-Key
-- Column: SUMUP_Config.ApiKey
-- 2024-10-03T17:14:02.457Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589189,731803,0,547614,TO_TIMESTAMP('2024-10-03 20:14:02','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','API-Key',TO_TIMESTAMP('2024-10-03 20:14:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T17:14:02.458Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731803 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-03T17:14:02.460Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543990) 
;

-- 2024-10-03T17:14:02.464Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731803
;

-- 2024-10-03T17:14:02.465Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731803)
;

-- Field: SumUp Configuration -> SumUp Configuration -> Merchant Code
-- Column: SUMUP_Config.SUMUP_merchant_code
-- Field: SumUp Configuration(541823,de.metas.payment.sumup) -> SumUp Configuration(547614,de.metas.payment.sumup) -> Merchant Code
-- Column: SUMUP_Config.SUMUP_merchant_code
-- 2024-10-03T17:14:02.581Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589190,731804,0,547614,TO_TIMESTAMP('2024-10-03 20:14:02','YYYY-MM-DD HH24:MI:SS'),100,40,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','Merchant Code',TO_TIMESTAMP('2024-10-03 20:14:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T17:14:02.583Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731804 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-03T17:14:02.586Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583298) 
;

-- 2024-10-03T17:14:02.591Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731804
;

-- 2024-10-03T17:14:02.592Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731804)
;

-- Tab: SumUp Configuration(541823,de.metas.payment.sumup) -> SumUp Configuration(547614,de.metas.payment.sumup)
-- UI Section: main
-- 2024-10-03T17:14:42.409Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547614,546202,TO_TIMESTAMP('2024-10-03 20:14:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-10-03 20:14:42','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2024-10-03T17:14:42.412Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546202 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: SumUp Configuration(541823,de.metas.payment.sumup) -> SumUp Configuration(547614,de.metas.payment.sumup) -> main
-- UI Column: 10
-- 2024-10-03T17:14:42.567Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547583,546202,TO_TIMESTAMP('2024-10-03 20:14:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-10-03 20:14:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: SumUp Configuration(541823,de.metas.payment.sumup) -> SumUp Configuration(547614,de.metas.payment.sumup) -> main
-- UI Column: 20
-- 2024-10-03T17:14:42.684Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547584,546202,TO_TIMESTAMP('2024-10-03 20:14:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2024-10-03 20:14:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: SumUp Configuration(541823,de.metas.payment.sumup) -> SumUp Configuration(547614,de.metas.payment.sumup) -> main -> 10
-- UI Element Group: default
-- 2024-10-03T17:14:42.864Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547583,552022,TO_TIMESTAMP('2024-10-03 20:14:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2024-10-03 20:14:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp Configuration -> SumUp Configuration.API-Key
-- Column: SUMUP_Config.ApiKey
-- UI Element: SumUp Configuration(541823,de.metas.payment.sumup) -> SumUp Configuration(547614,de.metas.payment.sumup) -> main -> 10 -> default.API-Key
-- Column: SUMUP_Config.ApiKey
-- 2024-10-03T17:15:18.454Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731803,0,547614,552022,626114,'F',TO_TIMESTAMP('2024-10-03 20:15:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','API-Key',10,0,0,TO_TIMESTAMP('2024-10-03 20:15:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp Configuration -> SumUp Configuration.Merchant Code
-- Column: SUMUP_Config.SUMUP_merchant_code
-- UI Element: SumUp Configuration(541823,de.metas.payment.sumup) -> SumUp Configuration(547614,de.metas.payment.sumup) -> main -> 10 -> default.Merchant Code
-- Column: SUMUP_Config.SUMUP_merchant_code
-- 2024-10-03T17:15:24.727Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731804,0,547614,552022,626115,'F',TO_TIMESTAMP('2024-10-03 20:15:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Merchant Code',20,0,0,TO_TIMESTAMP('2024-10-03 20:15:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: SumUp Configuration(541823,de.metas.payment.sumup) -> SumUp Configuration(547614,de.metas.payment.sumup) -> main -> 20
-- UI Element Group: flags
-- 2024-10-03T17:15:36.177Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547584,552023,TO_TIMESTAMP('2024-10-03 20:15:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2024-10-03 20:15:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp Configuration -> SumUp Configuration.Aktiv
-- Column: SUMUP_Config.IsActive
-- UI Element: SumUp Configuration(541823,de.metas.payment.sumup) -> SumUp Configuration(547614,de.metas.payment.sumup) -> main -> 20 -> flags.Aktiv
-- Column: SUMUP_Config.IsActive
-- 2024-10-03T17:15:45.018Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731801,0,547614,552023,626116,'F',TO_TIMESTAMP('2024-10-03 20:15:44','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2024-10-03 20:15:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: SumUp Configuration(541823,de.metas.payment.sumup) -> SumUp Configuration(547614,de.metas.payment.sumup) -> main -> 20
-- UI Element Group: org & client
-- 2024-10-03T17:16:03.262Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547584,552024,TO_TIMESTAMP('2024-10-03 20:16:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','org & client',20,TO_TIMESTAMP('2024-10-03 20:16:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp Configuration -> SumUp Configuration.Organisation
-- Column: SUMUP_Config.AD_Org_ID
-- UI Element: SumUp Configuration(541823,de.metas.payment.sumup) -> SumUp Configuration(547614,de.metas.payment.sumup) -> main -> 20 -> org & client.Organisation
-- Column: SUMUP_Config.AD_Org_ID
-- 2024-10-03T17:16:15.803Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731800,0,547614,552024,626117,'F',TO_TIMESTAMP('2024-10-03 20:16:14','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Organisation',10,0,0,TO_TIMESTAMP('2024-10-03 20:16:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp Configuration -> SumUp Configuration.Mandant
-- Column: SUMUP_Config.AD_Client_ID
-- UI Element: SumUp Configuration(541823,de.metas.payment.sumup) -> SumUp Configuration(547614,de.metas.payment.sumup) -> main -> 20 -> org & client.Mandant
-- Column: SUMUP_Config.AD_Client_ID
-- 2024-10-03T17:16:23.336Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731799,0,547614,552024,626118,'F',TO_TIMESTAMP('2024-10-03 20:16:23','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2024-10-03 20:16:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp Configuration -> SumUp Configuration.Merchant Code
-- Column: SUMUP_Config.SUMUP_merchant_code
-- UI Element: SumUp Configuration(541823,de.metas.payment.sumup) -> SumUp Configuration(547614,de.metas.payment.sumup) -> main -> 10 -> default.Merchant Code
-- Column: SUMUP_Config.SUMUP_merchant_code
-- 2024-10-03T17:16:56.460Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-10-03 20:16:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626115
;

-- 2024-10-03T17:18:59.359Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583299,0,TO_TIMESTAMP('2024-10-03 20:18:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','Y','SumUp','SumUp',TO_TIMESTAMP('2024-10-03 20:18:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T17:18:59.363Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583299 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Window: SumUp Configuration, InternalName=sumUpConfig
-- Window: SumUp Configuration, InternalName=sumUpConfig
-- 2024-10-03T17:19:15.730Z
UPDATE AD_Window SET InternalName='sumUpConfig',Updated=TO_TIMESTAMP('2024-10-03 20:19:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541823
;

-- Name: SumUp
-- Action Type: null
-- 2024-10-03T17:19:55.837Z
INSERT INTO AD_Menu (AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,583299,542175,0,TO_TIMESTAMP('2024-10-03 20:19:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','sumUp','Y','N','N','N','Y','SumUp',TO_TIMESTAMP('2024-10-03 20:19:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T17:19:55.840Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542175 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2024-10-03T17:19:55.848Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542175, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542175)
;

-- 2024-10-03T17:19:55.857Z
/* DDL */  select update_menu_translation_from_ad_element(583299) 
;

-- Reordering children of `PayPal`
-- Node name: `PayPal Configuration (PayPal_Config)`
-- 2024-10-03T17:20:04.022Z
UPDATE AD_TreeNodeMM SET Parent_ID=541304, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541305 AND AD_Tree_ID=10
;

-- Node name: `PayPal Log (PayPal_Log)`
-- 2024-10-03T17:20:04.032Z
UPDATE AD_TreeNodeMM SET Parent_ID=541304, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541306 AND AD_Tree_ID=10
;

-- Node name: `PayPal Order (PayPal_Order)`
-- 2024-10-03T17:20:04.033Z
UPDATE AD_TreeNodeMM SET Parent_ID=541304, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541307 AND AD_Tree_ID=10
;

-- Node name: `SumUp`
-- 2024-10-03T17:20:04.034Z
UPDATE AD_TreeNodeMM SET Parent_ID=541304, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542175 AND AD_Tree_ID=10
;

-- Reordering children of `Finance`
-- Node name: `Remittance Advice (REMADV) (C_RemittanceAdvice)`
-- 2024-10-03T17:20:09.638Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541584 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (GL_Journal)`
-- 2024-10-03T17:20:09.639Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- Node name: `Bank Account (C_BP_BankAccount)`
-- 2024-10-03T17:20:09.642Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- Node name: `Import Bank Statement (I_BankStatement)`
-- 2024-10-03T17:20:09.643Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541297 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (C_BankStatement)`
-- 2024-10-03T17:20:09.644Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal (C_BankStatement)`
-- 2024-10-03T17:20:09.645Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- Node name: `Bankstatement Reference`
-- 2024-10-03T17:20:09.645Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- Node name: `Payment (C_Payment)`
-- 2024-10-03T17:20:09.646Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- Node name: `Manual Payment Allocations (C_AllocationHdr)`
-- 2024-10-03T17:20:09.647Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection (C_PaySelection)`
-- 2024-10-03T17:20:09.648Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation (C_Payment_Reservation)`
-- 2024-10-03T17:20:09.649Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation Capture (C_Payment_Reservation_Capture)`
-- 2024-10-03T17:20:09.649Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- Node name: `Dunning (C_DunningDoc)`
-- 2024-10-03T17:20:09.650Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- Node name: `Dunning Candidates (C_Dunning_Candidate)`
-- 2024-10-03T17:20:09.651Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- Node name: `Accounting Transactions (Fact_Acct_Transactions_View)`
-- 2024-10-03T17:20:09.652Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- Node name: `ESR Payment Import (ESR_Import)`
-- 2024-10-03T17:20:09.654Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- Node name: `Account Combination (C_ValidCombination)`
-- 2024-10-03T17:20:09.655Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- Node name: `Chart of Accounts (C_Element)`
-- 2024-10-03T17:20:09.655Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- Node name: `Element values (C_ElementValue)`
-- 2024-10-03T17:20:09.656Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541405 AND AD_Tree_ID=10
;

-- Node name: `Productcosts (M_Product)`
-- 2024-10-03T17:20:09.657Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- Node name: `Current Product Costs (M_Cost)`
-- 2024-10-03T17:20:09.658Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541455 AND AD_Tree_ID=10
;

-- Node name: `Products Without Initial Cost (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2024-10-03T17:20:09.658Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541710 AND AD_Tree_ID=10
;

-- Node name: `Products With Booked Quantity (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2024-10-03T17:20:09.659Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541717 AND AD_Tree_ID=10
;

-- Node name: `Cost Detail (M_CostDetail)`
-- 2024-10-03T17:20:09.660Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541454 AND AD_Tree_ID=10
;

-- Node name: `Costcenter Documents (Fact_Acct_ActivityChangeRequest)`
-- 2024-10-03T17:20:09.660Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- Node name: `Cost Center (C_Activity)`
-- 2024-10-03T17:20:09.661Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- Node name: `Referenz No (C_ReferenceNo)`
-- 2024-10-03T17:20:09.662Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541015 AND AD_Tree_ID=10
;

-- Node name: `Referenz No Type (C_ReferenceNo_Type)`
-- 2024-10-03T17:20:09.663Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541016 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export (DATEV_Export)`
-- 2024-10-03T17:20:09.664Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541042 AND AD_Tree_ID=10
;

-- Node name: `Matched Invoices (M_MatchInv)`
-- 2024-10-03T17:20:09.664Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=315 AND AD_Tree_ID=10
;

-- Node name: `UnPosted Documents (RV_UnPosted)`
-- 2024-10-03T17:20:09.665Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541368 AND AD_Tree_ID=10
;

-- Node name: `Import Datev Payment (I_Datev_Payment)`
-- 2024-10-03T17:20:09.666Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541120 AND AD_Tree_ID=10
;

-- Node name: `Enqueue all not posted documents (de.metas.acct.process.Documents_EnqueueNotPosted)`
-- 2024-10-03T17:20:09.666Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541125 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2024-10-03T17:20:09.667Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- Node name: `PayPal`
-- 2024-10-03T17:20:09.667Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541304 AND AD_Tree_ID=10
;

-- Node name: `SumUp`
-- 2024-10-03T17:20:09.668Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542175 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2024-10-03T17:20:09.669Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2024-10-03T17:20:09.670Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- Name: SumUp Configuration
-- Action Type: W
-- Window: SumUp Configuration(541823,de.metas.payment.sumup)
-- 2024-10-03T17:20:22.884Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,583297,542176,0,541823,TO_TIMESTAMP('2024-10-03 20:20:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','sumUpConfig','Y','N','N','N','N','SumUp Configuration',TO_TIMESTAMP('2024-10-03 20:20:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T17:20:22.886Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542176 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2024-10-03T17:20:22.888Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542176, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542176)
;

-- 2024-10-03T17:20:22.890Z
/* DDL */  select update_menu_translation_from_ad_element(583297) 
;

-- Reordering children of `SumUp`
-- Node name: `SumUp Configuration`
-- 2024-10-03T17:20:31.102Z
UPDATE AD_TreeNodeMM SET Parent_ID=542175, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542176 AND AD_Tree_ID=10
;

-- Table: SUMUP_CardReader
-- Table: SUMUP_CardReader
-- 2024-10-03T17:21:57.200Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('2',0,0,0,542441,'A','N',TO_TIMESTAMP('2024-10-03 20:21:57','YYYY-MM-DD HH24:MI:SS'),100,'A','de.metas.payment.sumup','N','Y','N','N','Y','N','N','N','N','N',0,'Card Reader','NP','L','SUMUP_CardReader','DTI',TO_TIMESTAMP('2024-10-03 20:21:57','YYYY-MM-DD HH24:MI:SS'),100,0,'A')
;

-- 2024-10-03T17:21:57.202Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542441 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-10-03T17:21:57.309Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556372,TO_TIMESTAMP('2024-10-03 20:21:57','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table SUMUP_CardReader',1,'Y','N','Y','Y','SUMUP_CardReader','N',1000000,TO_TIMESTAMP('2024-10-03 20:21:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T17:21:57.319Z
CREATE SEQUENCE SUMUP_CARDREADER_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: SUMUP_CardReader.AD_Client_ID
-- Column: SUMUP_CardReader.AD_Client_ID
-- 2024-10-03T17:22:00.630Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589191,102,0,19,542441,'AD_Client_ID',TO_TIMESTAMP('2024-10-03 20:22:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','de.metas.payment.sumup',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-10-03 20:22:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T17:22:00.634Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589191 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T17:22:00.641Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: SUMUP_CardReader.AD_Org_ID
-- Column: SUMUP_CardReader.AD_Org_ID
-- 2024-10-03T17:22:01.290Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589192,113,0,30,542441,'AD_Org_ID',TO_TIMESTAMP('2024-10-03 20:22:01','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','de.metas.payment.sumup',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2024-10-03 20:22:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T17:22:01.294Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589192 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T17:22:01.297Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: SUMUP_CardReader.Created
-- Column: SUMUP_CardReader.Created
-- 2024-10-03T17:22:01.882Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589193,245,0,16,542441,'Created',TO_TIMESTAMP('2024-10-03 20:22:01','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.payment.sumup',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-10-03 20:22:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T17:22:01.885Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589193 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T17:22:01.889Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: SUMUP_CardReader.CreatedBy
-- Column: SUMUP_CardReader.CreatedBy
-- 2024-10-03T17:22:02.477Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589194,246,0,18,110,542441,'CreatedBy',TO_TIMESTAMP('2024-10-03 20:22:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.payment.sumup',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-10-03 20:22:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T17:22:02.478Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589194 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T17:22:02.481Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: SUMUP_CardReader.IsActive
-- Column: SUMUP_CardReader.IsActive
-- 2024-10-03T17:22:03.068Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589195,348,0,20,542441,'IsActive',TO_TIMESTAMP('2024-10-03 20:22:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','de.metas.payment.sumup',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-10-03 20:22:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T17:22:03.070Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589195 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T17:22:03.072Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: SUMUP_CardReader.Updated
-- Column: SUMUP_CardReader.Updated
-- 2024-10-03T17:22:03.646Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589196,607,0,16,542441,'Updated',TO_TIMESTAMP('2024-10-03 20:22:03','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.payment.sumup',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-10-03 20:22:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T17:22:03.648Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589196 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T17:22:03.652Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: SUMUP_CardReader.UpdatedBy
-- Column: SUMUP_CardReader.UpdatedBy
-- 2024-10-03T17:22:04.417Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589197,608,0,18,110,542441,'UpdatedBy',TO_TIMESTAMP('2024-10-03 20:22:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.payment.sumup',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-10-03 20:22:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T17:22:04.419Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589197 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T17:22:04.421Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2024-10-03T17:22:04.875Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583300,0,'SUMUP_CardReader_ID',TO_TIMESTAMP('2024-10-03 20:22:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','Y','Card Reader','Card Reader',TO_TIMESTAMP('2024-10-03 20:22:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T17:22:04.877Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583300 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: SUMUP_CardReader.SUMUP_CardReader_ID
-- Column: SUMUP_CardReader.SUMUP_CardReader_ID
-- 2024-10-03T17:22:05.407Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589198,583300,0,13,542441,'SUMUP_CardReader_ID',TO_TIMESTAMP('2024-10-03 20:22:04','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sumup',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Card Reader',0,0,TO_TIMESTAMP('2024-10-03 20:22:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T17:22:05.410Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589198 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T17:22:05.412Z
/* DDL */  select update_Column_Translation_From_AD_Element(583300) 
;

-- Column: SUMUP_CardReader.Name
-- Column: SUMUP_CardReader.Name
-- 2024-10-03T17:23:12.806Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589199,469,0,10,542441,'XX','Name',TO_TIMESTAMP('2024-10-03 20:23:12','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.payment.sumup',0,255,'E','','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Y','N','N','Y','N','N','N','N','N','Y','N',0,'Name',0,1,TO_TIMESTAMP('2024-10-03 20:23:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T17:23:12.808Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589199 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T17:23:12.811Z
/* DDL */  select update_Column_Translation_From_AD_Element(469) 
;

-- Column: SUMUP_CardReader.ExternalId
-- Column: SUMUP_CardReader.ExternalId
-- 2024-10-03T17:23:45.423Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589200,543939,0,10,542441,'XX','ExternalId',TO_TIMESTAMP('2024-10-03 20:23:45','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sumup',0,255,'Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Externe ID',0,0,TO_TIMESTAMP('2024-10-03 20:23:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T17:23:45.425Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589200 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T17:23:45.428Z
/* DDL */  select update_Column_Translation_From_AD_Element(543939) 
;

-- Column: SUMUP_Config.SUMUP_merchant_code
-- Column: SUMUP_Config.SUMUP_merchant_code
-- 2024-10-03T17:24:59.711Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=1,Updated=TO_TIMESTAMP('2024-10-03 20:24:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589190
;

-- Column: SUMUP_CardReader.SUMUP_Config_ID
-- Column: SUMUP_CardReader.SUMUP_Config_ID
-- 2024-10-03T17:25:05.122Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589201,583297,0,30,542441,'XX','SUMUP_Config_ID',TO_TIMESTAMP('2024-10-03 20:25:04','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sumup',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'SumUp Configuration',0,0,TO_TIMESTAMP('2024-10-03 20:25:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T17:25:05.124Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589201 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T17:25:05.126Z
/* DDL */  select update_Column_Translation_From_AD_Element(583297) 
;

-- 2024-10-03T17:25:13.100Z
/* DDL */ CREATE TABLE public.SUMUP_CardReader (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, ExternalId VARCHAR(255) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Name VARCHAR(255) NOT NULL, SUMUP_CardReader_ID NUMERIC(10) NOT NULL, SUMUP_Config_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT SUMUP_CardReader_Key PRIMARY KEY (SUMUP_CardReader_ID), CONSTRAINT SUMUPConfig_SUMUPCardReader FOREIGN KEY (SUMUP_Config_ID) REFERENCES public.SUMUP_Config DEFERRABLE INITIALLY DEFERRED)
;

-- Tab: SumUp Configuration -> Card Reader
-- Table: SUMUP_CardReader
-- Tab: SumUp Configuration(541823,de.metas.payment.sumup) -> Card Reader
-- Table: SUMUP_CardReader
-- 2024-10-03T17:26:12.591Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,589201,583300,0,547615,542441,541823,'Y',TO_TIMESTAMP('2024-10-03 20:26:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','N','N','A','SUMUP_CardReader','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Card Reader',589188,'N',20,1,TO_TIMESTAMP('2024-10-03 20:26:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T17:26:12.594Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547615 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-10-03T17:26:12.597Z
/* DDL */  select update_tab_translation_from_ad_element(583300) 
;

-- 2024-10-03T17:26:12.606Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547615)
;

-- Field: SumUp Configuration -> Card Reader -> Mandant
-- Column: SUMUP_CardReader.AD_Client_ID
-- Field: SumUp Configuration(541823,de.metas.payment.sumup) -> Card Reader(547615,de.metas.payment.sumup) -> Mandant
-- Column: SUMUP_CardReader.AD_Client_ID
-- 2024-10-03T17:26:33.500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589191,731805,0,547615,TO_TIMESTAMP('2024-10-03 20:26:33','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.payment.sumup','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-10-03 20:26:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T17:26:33.502Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731805 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-03T17:26:33.505Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-10-03T17:26:33.886Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731805
;

-- 2024-10-03T17:26:33.887Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731805)
;

-- Field: SumUp Configuration -> Card Reader -> Organisation
-- Column: SUMUP_CardReader.AD_Org_ID
-- Field: SumUp Configuration(541823,de.metas.payment.sumup) -> Card Reader(547615,de.metas.payment.sumup) -> Organisation
-- Column: SUMUP_CardReader.AD_Org_ID
-- 2024-10-03T17:26:33.995Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589192,731806,0,547615,TO_TIMESTAMP('2024-10-03 20:26:33','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.payment.sumup','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2024-10-03 20:26:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T17:26:33.997Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731806 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-03T17:26:34.001Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-10-03T17:26:34.087Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731806
;

-- 2024-10-03T17:26:34.088Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731806)
;

-- Field: SumUp Configuration -> Card Reader -> Aktiv
-- Column: SUMUP_CardReader.IsActive
-- Field: SumUp Configuration(541823,de.metas.payment.sumup) -> Card Reader(547615,de.metas.payment.sumup) -> Aktiv
-- Column: SUMUP_CardReader.IsActive
-- 2024-10-03T17:26:34.204Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589195,731807,0,547615,TO_TIMESTAMP('2024-10-03 20:26:34','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.payment.sumup','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-10-03 20:26:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T17:26:34.206Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731807 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-03T17:26:34.208Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-10-03T17:26:34.304Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731807
;

-- 2024-10-03T17:26:34.306Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731807)
;

-- Field: SumUp Configuration -> Card Reader -> Card Reader
-- Column: SUMUP_CardReader.SUMUP_CardReader_ID
-- Field: SumUp Configuration(541823,de.metas.payment.sumup) -> Card Reader(547615,de.metas.payment.sumup) -> Card Reader
-- Column: SUMUP_CardReader.SUMUP_CardReader_ID
-- 2024-10-03T17:26:34.427Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589198,731808,0,547615,TO_TIMESTAMP('2024-10-03 20:26:34','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','Card Reader',TO_TIMESTAMP('2024-10-03 20:26:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T17:26:34.428Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731808 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-03T17:26:34.430Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583300) 
;

-- 2024-10-03T17:26:34.433Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731808
;

-- 2024-10-03T17:26:34.433Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731808)
;

-- Field: SumUp Configuration -> Card Reader -> Name
-- Column: SUMUP_CardReader.Name
-- Field: SumUp Configuration(541823,de.metas.payment.sumup) -> Card Reader(547615,de.metas.payment.sumup) -> Name
-- Column: SUMUP_CardReader.Name
-- 2024-10-03T17:26:34.538Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589199,731809,0,547615,TO_TIMESTAMP('2024-10-03 20:26:34','YYYY-MM-DD HH24:MI:SS'),100,'',255,'de.metas.payment.sumup','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2024-10-03 20:26:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T17:26:34.539Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731809 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-03T17:26:34.540Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2024-10-03T17:26:34.624Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731809
;

-- 2024-10-03T17:26:34.624Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731809)
;

-- Field: SumUp Configuration -> Card Reader -> Externe ID
-- Column: SUMUP_CardReader.ExternalId
-- Field: SumUp Configuration(541823,de.metas.payment.sumup) -> Card Reader(547615,de.metas.payment.sumup) -> Externe ID
-- Column: SUMUP_CardReader.ExternalId
-- 2024-10-03T17:26:34.732Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589200,731810,0,547615,TO_TIMESTAMP('2024-10-03 20:26:34','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','Externe ID',TO_TIMESTAMP('2024-10-03 20:26:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T17:26:34.734Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731810 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-03T17:26:34.736Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543939) 
;

-- 2024-10-03T17:26:34.743Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731810
;

-- 2024-10-03T17:26:34.744Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731810)
;

-- Field: SumUp Configuration -> Card Reader -> SumUp Configuration
-- Column: SUMUP_CardReader.SUMUP_Config_ID
-- Field: SumUp Configuration(541823,de.metas.payment.sumup) -> Card Reader(547615,de.metas.payment.sumup) -> SumUp Configuration
-- Column: SUMUP_CardReader.SUMUP_Config_ID
-- 2024-10-03T17:26:34.858Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589201,731811,0,547615,TO_TIMESTAMP('2024-10-03 20:26:34','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','SumUp Configuration',TO_TIMESTAMP('2024-10-03 20:26:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T17:26:34.860Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731811 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-03T17:26:34.861Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583297) 
;

-- 2024-10-03T17:26:34.863Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731811
;

-- 2024-10-03T17:26:34.864Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731811)
;

-- Tab: SumUp Configuration(541823,de.metas.payment.sumup) -> Card Reader(547615,de.metas.payment.sumup)
-- UI Section: main
-- 2024-10-03T17:26:51.118Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547615,546203,TO_TIMESTAMP('2024-10-03 20:26:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-10-03 20:26:50','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2024-10-03T17:26:51.120Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546203 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: SumUp Configuration(541823,de.metas.payment.sumup) -> Card Reader(547615,de.metas.payment.sumup) -> main
-- UI Column: 10
-- 2024-10-03T17:26:55.564Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547585,546203,TO_TIMESTAMP('2024-10-03 20:26:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-10-03 20:26:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: SumUp Configuration(541823,de.metas.payment.sumup) -> Card Reader(547615,de.metas.payment.sumup) -> main
-- UI Column: 20
-- 2024-10-03T17:26:57.459Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547586,546203,TO_TIMESTAMP('2024-10-03 20:26:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2024-10-03 20:26:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: SumUp Configuration(541823,de.metas.payment.sumup) -> Card Reader(547615,de.metas.payment.sumup) -> main -> 10
-- UI Element Group: primary
-- 2024-10-03T17:27:11.986Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547585,552025,TO_TIMESTAMP('2024-10-03 20:27:11','YYYY-MM-DD HH24:MI:SS'),100,'Y','primary',10,'primary',TO_TIMESTAMP('2024-10-03 20:27:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp Configuration -> Card Reader.Name
-- Column: SUMUP_CardReader.Name
-- UI Element: SumUp Configuration(541823,de.metas.payment.sumup) -> Card Reader(547615,de.metas.payment.sumup) -> main -> 10 -> primary.Name
-- Column: SUMUP_CardReader.Name
-- 2024-10-03T17:27:30.941Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731809,0,547615,552025,626119,'F',TO_TIMESTAMP('2024-10-03 20:27:30','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Name',10,0,0,TO_TIMESTAMP('2024-10-03 20:27:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp Configuration -> Card Reader.Externe ID
-- Column: SUMUP_CardReader.ExternalId
-- UI Element: SumUp Configuration(541823,de.metas.payment.sumup) -> Card Reader(547615,de.metas.payment.sumup) -> main -> 10 -> primary.Externe ID
-- Column: SUMUP_CardReader.ExternalId
-- 2024-10-03T17:27:39.377Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731810,0,547615,552025,626120,'F',TO_TIMESTAMP('2024-10-03 20:27:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Externe ID',20,0,0,TO_TIMESTAMP('2024-10-03 20:27:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: SumUp Configuration(541823,de.metas.payment.sumup) -> Card Reader(547615,de.metas.payment.sumup) -> main -> 20
-- UI Element Group: flags
-- 2024-10-03T17:27:50.275Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547586,552026,TO_TIMESTAMP('2024-10-03 20:27:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2024-10-03 20:27:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp Configuration -> Card Reader.Aktiv
-- Column: SUMUP_CardReader.IsActive
-- UI Element: SumUp Configuration(541823,de.metas.payment.sumup) -> Card Reader(547615,de.metas.payment.sumup) -> main -> 20 -> flags.Aktiv
-- Column: SUMUP_CardReader.IsActive
-- 2024-10-03T17:27:59.146Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731807,0,547615,552026,626121,'F',TO_TIMESTAMP('2024-10-03 20:27:58','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2024-10-03 20:27:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: SumUp Configuration -> Card Reader.Name
-- Column: SUMUP_CardReader.Name
-- UI Element: SumUp Configuration(541823,de.metas.payment.sumup) -> Card Reader(547615,de.metas.payment.sumup) -> main -> 10 -> primary.Name
-- Column: SUMUP_CardReader.Name
-- 2024-10-03T17:28:07.962Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-10-03 20:28:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626119
;

-- UI Element: SumUp Configuration -> Card Reader.Externe ID
-- Column: SUMUP_CardReader.ExternalId
-- UI Element: SumUp Configuration(541823,de.metas.payment.sumup) -> Card Reader(547615,de.metas.payment.sumup) -> main -> 10 -> primary.Externe ID
-- Column: SUMUP_CardReader.ExternalId
-- 2024-10-03T17:28:07.969Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-10-03 20:28:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626120
;

-- UI Element: SumUp Configuration -> Card Reader.Aktiv
-- Column: SUMUP_CardReader.IsActive
-- UI Element: SumUp Configuration(541823,de.metas.payment.sumup) -> Card Reader(547615,de.metas.payment.sumup) -> main -> 20 -> flags.Aktiv
-- Column: SUMUP_CardReader.IsActive
-- 2024-10-03T17:28:07.976Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-10-03 20:28:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626121
;

-- Column: SUMUP_Config.SUMUP_CardReader_ID
-- Column: SUMUP_Config.SUMUP_CardReader_ID
-- 2024-10-03T17:47:41.961Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589202,583300,0,30,542440,'XX','SUMUP_CardReader_ID',TO_TIMESTAMP('2024-10-03 20:47:41','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sumup',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Card Reader',0,0,TO_TIMESTAMP('2024-10-03 20:47:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-03T17:47:41.964Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589202 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-03T17:47:41.969Z
/* DDL */  select update_Column_Translation_From_AD_Element(583300) 
;

-- 2024-10-03T17:47:44.908Z
/* DDL */ SELECT public.db_alter_table('SUMUP_Config','ALTER TABLE public.SUMUP_Config ADD COLUMN SUMUP_CardReader_ID NUMERIC(10)')
;

-- 2024-10-03T17:47:44.921Z
ALTER TABLE SUMUP_Config ADD CONSTRAINT SUMUPCardReader_SUMUPConfig FOREIGN KEY (SUMUP_CardReader_ID) REFERENCES public.SUMUP_CardReader DEFERRABLE INITIALLY DEFERRED
;

-- Field: SumUp Configuration -> SumUp Configuration -> Card Reader
-- Column: SUMUP_Config.SUMUP_CardReader_ID
-- Field: SumUp Configuration(541823,de.metas.payment.sumup) -> SumUp Configuration(547614,de.metas.payment.sumup) -> Card Reader
-- Column: SUMUP_Config.SUMUP_CardReader_ID
-- 2024-10-03T17:47:57.512Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589202,731812,0,547614,TO_TIMESTAMP('2024-10-03 20:47:57','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.payment.sumup','Y','N','N','N','N','N','N','N','Card Reader',TO_TIMESTAMP('2024-10-03 20:47:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-03T17:47:57.515Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731812 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-03T17:47:57.517Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583300) 
;

-- 2024-10-03T17:47:57.523Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731812
;

-- 2024-10-03T17:47:57.524Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731812)
;

-- UI Element: SumUp Configuration -> SumUp Configuration.Card Reader
-- Column: SUMUP_Config.SUMUP_CardReader_ID
-- UI Element: SumUp Configuration(541823,de.metas.payment.sumup) -> SumUp Configuration(547614,de.metas.payment.sumup) -> main -> 10 -> default.Card Reader
-- Column: SUMUP_Config.SUMUP_CardReader_ID
-- 2024-10-03T17:48:17.942Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731812,0,547614,552022,626122,'F',TO_TIMESTAMP('2024-10-03 20:48:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Card Reader',30,0,0,TO_TIMESTAMP('2024-10-03 20:48:16','YYYY-MM-DD HH24:MI:SS'),100)
;

