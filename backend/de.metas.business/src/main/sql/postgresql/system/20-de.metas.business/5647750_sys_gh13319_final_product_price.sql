-- 2022-07-21T17:29:25.082Z
-- URL zum Konzept
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,Description,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,542190,'N',TO_TIMESTAMP('2022-07-21 17:29:24','YYYY-MM-DD HH24:MI:SS'),100,'Cost Revaluation','D','N','Y','N','N','Y','N','N','N','N','N',0,'Cost Revaluation','NP','L','M_CostRevaluation','DTI',TO_TIMESTAMP('2022-07-21 17:29:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T17:29:25.085Z
-- URL zum Konzept
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542190 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2022-07-21T17:29:25.149Z
-- URL zum Konzept
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555979,TO_TIMESTAMP('2022-07-21 17:29:25','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table M_CostRevaluation',1,'Y','N','Y','Y','M_CostRevaluation','N',1000000,TO_TIMESTAMP('2022-07-21 17:29:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T17:29:25.156Z
-- URL zum Konzept
CREATE SEQUENCE M_COSTREVALUATION_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2022-07-21T17:29:29.573Z
-- URL zum Konzept
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2022-07-21 17:29:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542190
;

-- 2022-07-21T17:29:41.404Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581160,0,'M_CostRevaluation_ID',TO_TIMESTAMP('2022-07-21 17:29:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Cost Revaluation','Cost Revaluation',TO_TIMESTAMP('2022-07-21 17:29:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T17:29:41.406Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581160 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-21T17:29:41.722Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,583740,581160,0,13,542190,'M_CostRevaluation_ID',TO_TIMESTAMP('2022-07-21 17:29:41','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','Cost Revaluation',TO_TIMESTAMP('2022-07-21 17:29:41','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-07-21T17:29:41.724Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583740 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:29:41.747Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(581160) 
;

-- 2022-07-21T17:29:42.040Z
-- URL zum Konzept
ALTER SEQUENCE M_COSTREVALUATION_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 RESTART 1000000
;



-- 2022-07-21T17:30:33.208Z
-- URL zum Konzept
/* DDL */ CREATE TABLE public.M_CostRevaluation (M_CostRevaluation_ID NUMERIC(10) NOT NULL, CONSTRAINT M_CostRevaluation_Key PRIMARY KEY (M_CostRevaluation_ID))
;

-- 2022-07-21T17:34:23.132Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583741,102,0,19,542190,'AD_Client_ID',TO_TIMESTAMP('2022-07-21 17:34:23','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Mandant',0,0,TO_TIMESTAMP('2022-07-21 17:34:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:34:23.134Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583741 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:34:23.136Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- 2022-07-21T17:34:23.859Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583742,113,0,30,542190,'AD_Org_ID',TO_TIMESTAMP('2022-07-21 17:34:23','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','N','N','N','Organisation',10,0,TO_TIMESTAMP('2022-07-21 17:34:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:34:23.861Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583742 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:34:23.862Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- 2022-07-21T17:34:24.267Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583743,126,0,19,542190,'AD_Table_ID',TO_TIMESTAMP('2022-07-21 17:34:24','YYYY-MM-DD HH24:MI:SS'),100,'N','Database Table information','D',0,10,'The Database Table provides the information of the table definition','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','DB-Tabelle',0,0,TO_TIMESTAMP('2022-07-21 17:34:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:34:24.268Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583743 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:34:24.269Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(126) 
;

-- 2022-07-21T17:34:24.630Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583744,2978,0,19,542190,'CM_Template_ID',TO_TIMESTAMP('2022-07-21 17:34:24','YYYY-MM-DD HH24:MI:SS'),100,'N','Template defines how content is displayed','D',0,10,'A template describes how content should get displayed, it contains layout and maybe also scripts on how to handle the content','Y','Y','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','Vorlage',0,0,TO_TIMESTAMP('2022-07-21 17:34:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:34:24.632Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583744 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:34:24.632Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(2978) 
;

-- 2022-07-21T17:34:24.973Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583745,245,0,16,542190,'Created',TO_TIMESTAMP('2022-07-21 17:34:24','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,7,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Erstellt',0,0,TO_TIMESTAMP('2022-07-21 17:34:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:34:24.974Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583745 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:34:24.975Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- 2022-07-21T17:34:25.435Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583746,246,0,18,110,542190,'CreatedBy',TO_TIMESTAMP('2022-07-21 17:34:25','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2022-07-21 17:34:25','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:34:25.436Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583746 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:34:25.437Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- 2022-07-21T17:34:25.834Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583747,275,0,10,542190,'Description',TO_TIMESTAMP('2022-07-21 17:34:25','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Beschreibung',0,0,TO_TIMESTAMP('2022-07-21 17:34:25','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:34:25.836Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583747 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:34:25.837Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(275) 
;

-- 2022-07-21T17:34:26.190Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583748,348,0,20,542190,'IsActive',TO_TIMESTAMP('2022-07-21 17:34:26','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Aktiv',0,0,TO_TIMESTAMP('2022-07-21 17:34:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:34:26.191Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583748 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:34:26.192Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- 2022-07-21T17:34:26.589Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583749,469,0,10,542190,'Name',TO_TIMESTAMP('2022-07-21 17:34:26','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,120,'E','','Y','Y','N','N','N','N','N','Y','N','Y','N','Y','N','N','N','N','Y','Name',10,1,TO_TIMESTAMP('2022-07-21 17:34:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:34:26.591Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583749 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:34:26.592Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(469) 
;

-- 2022-07-21T17:34:26.953Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583750,2642,0,14,542190,'OtherClause',TO_TIMESTAMP('2022-07-21 17:34:26','YYYY-MM-DD HH24:MI:SS'),100,'N','Other SQL Clause','D',0,2000,'Any other complete clause like GROUP BY, HAVING, ORDER BY, etc. after WHERE clause.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Other SQL Clause',0,0,TO_TIMESTAMP('2022-07-21 17:34:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:34:26.954Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583750 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:34:26.955Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(2642) 
;

-- 2022-07-21T17:34:27.260Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583751,607,0,16,542190,'Updated',TO_TIMESTAMP('2022-07-21 17:34:27','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,7,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2022-07-21 17:34:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:34:27.261Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583751 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:34:27.262Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- 2022-07-21T17:34:27.646Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583752,608,0,18,110,542190,'UpdatedBy',TO_TIMESTAMP('2022-07-21 17:34:27','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2022-07-21 17:34:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:34:27.647Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583752 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:34:27.648Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2022-07-21T17:34:28.067Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583753,630,0,14,542190,'WhereClause',TO_TIMESTAMP('2022-07-21 17:34:27','YYYY-MM-DD HH24:MI:SS'),100,'N','Fully qualified SQL WHERE clause','D',0,2000,'The Where Clause indicates the SQL WHERE clause to use for record selection. The WHERE clause is added to the query. Fully qualified means "tablename.columnname".','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Sql WHERE',0,0,TO_TIMESTAMP('2022-07-21 17:34:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:34:28.068Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583753 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:34:28.069Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(630) 
;

-- 2022-07-21T17:35:23.783Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=583753
;

-- 2022-07-21T17:35:23.787Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=583753
;

-- 2022-07-21T17:35:28.961Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=583750
;

-- 2022-07-21T17:35:28.963Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=583750
;

-- 2022-07-21T17:35:36.950Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=583744
;

-- 2022-07-21T17:35:36.953Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=583744
;

-- 2022-07-21T17:36:52.341Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583754,263,0,15,542190,'DateAcct',TO_TIMESTAMP('2022-07-21 17:36:52','YYYY-MM-DD HH24:MI:SS'),100,'N','Accounting Date','D',0,7,'The Accounting Date indicates the date to be used on the General Ledger account entries generated from this document. It is also used for any currency conversion.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Buchungsdatum',0,0,TO_TIMESTAMP('2022-07-21 17:36:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:36:52.342Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583754 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:36:52.343Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(263) 
;

-- 2022-07-21T17:37:30.171Z
-- URL zum Konzept
UPDATE AD_Table_Trl SET Name='Kosten Neubewertung',Updated=TO_TIMESTAMP('2022-07-21 17:37:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=542190
;

-- 2022-07-21T17:38:25.763Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581161,0,'EvaluationStartDate',TO_TIMESTAMP('2022-07-21 17:38:25','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Evaluation Start Date','Evaluation Start Date',TO_TIMESTAMP('2022-07-21 17:38:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T17:38:25.764Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581161 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-21T17:38:56.830Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Startdatum der Bewertung', PrintName='Startdatum der Bewertung',Updated=TO_TIMESTAMP('2022-07-21 17:38:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581161 AND AD_Language='de_DE'
;

-- 2022-07-21T17:38:56.834Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581161,'de_DE') 
;

-- 2022-07-21T17:38:56.856Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(581161,'de_DE') 
;

-- 2022-07-21T17:38:56.857Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='EvaluationStartDate', Name='Startdatum der Bewertung', Description=NULL, Help=NULL WHERE AD_Element_ID=581161
;

-- 2022-07-21T17:38:56.858Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='EvaluationStartDate', Name='Startdatum der Bewertung', Description=NULL, Help=NULL, AD_Element_ID=581161 WHERE UPPER(ColumnName)='EVALUATIONSTARTDATE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-07-21T17:38:56.858Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='EvaluationStartDate', Name='Startdatum der Bewertung', Description=NULL, Help=NULL WHERE AD_Element_ID=581161 AND IsCentrallyMaintained='Y'
;

-- 2022-07-21T17:38:56.859Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Startdatum der Bewertung', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581161) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581161)
;

-- 2022-07-21T17:38:56.872Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Startdatum der Bewertung', Name='Startdatum der Bewertung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581161)
;

-- 2022-07-21T17:38:56.873Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Startdatum der Bewertung', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581161
;

-- 2022-07-21T17:38:56.875Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Startdatum der Bewertung', Description=NULL, Help=NULL WHERE AD_Element_ID = 581161
;

-- 2022-07-21T17:38:56.875Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Startdatum der Bewertung', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581161
;

-- 2022-07-21T17:43:45.854Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583755,181,0,19,542190,'C_AcctSchema_ID',TO_TIMESTAMP('2022-07-21 17:43:45','YYYY-MM-DD HH24:MI:SS'),100,'N','Stammdaten für Buchhaltung','D',0,10,'Ein Kontenschema definiert eine Ausprägung von Stammdaten für die Buchhaltung wie verwendete Art der Kostenrechnung, Währung und Buchungsperiode.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Buchführungs-Schema',0,0,TO_TIMESTAMP('2022-07-21 17:43:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:43:45.855Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583755 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:43:45.856Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(181) 
;

-- 2022-07-21T17:44:50.671Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluation','ALTER TABLE public.M_CostRevaluation ADD COLUMN C_AcctSchema_ID NUMERIC(10)')
;

-- 2022-07-21T17:44:50.681Z
-- URL zum Konzept
ALTER TABLE M_CostRevaluation ADD CONSTRAINT CAcctSchema_MCostRevaluation FOREIGN KEY (C_AcctSchema_ID) REFERENCES public.C_AcctSchema DEFERRABLE INITIALLY DEFERRED
;

-- 2022-07-21T17:45:09.536Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluation','ALTER TABLE public.M_CostRevaluation ADD COLUMN Name VARCHAR(120) NOT NULL')
;

-- 2022-07-21T17:45:22.073Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluation','ALTER TABLE public.M_CostRevaluation ADD COLUMN AD_Client_ID NUMERIC(10) NOT NULL')
;

-- 2022-07-21T17:45:29.474Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluation','ALTER TABLE public.M_CostRevaluation ADD COLUMN AD_Org_ID NUMERIC(10) NOT NULL')
;

-- 2022-07-21T17:45:45.319Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=583743
;

-- 2022-07-21T17:45:45.321Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=583743
;

-- 2022-07-21T17:45:56.367Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluation','ALTER TABLE public.M_CostRevaluation ADD COLUMN Created TIMESTAMP WITH TIME ZONE NOT NULL')
;

-- 2022-07-21T17:46:01.780Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluation','ALTER TABLE public.M_CostRevaluation ADD COLUMN CreatedBy NUMERIC(10) NOT NULL')
;

-- 2022-07-21T17:46:06.735Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluation','ALTER TABLE public.M_CostRevaluation ADD COLUMN DateAcct TIMESTAMP WITHOUT TIME ZONE')
;

-- 2022-07-21T17:46:11.293Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluation','ALTER TABLE public.M_CostRevaluation ADD COLUMN Description VARCHAR(255)')
;

-- 2022-07-21T17:46:15.617Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluation','ALTER TABLE public.M_CostRevaluation ADD COLUMN IsActive CHAR(1) CHECK (IsActive IN (''Y'',''N'')) NOT NULL')
;

-- 2022-07-21T17:46:34.817Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=583749
;

-- 2022-07-21T17:46:34.818Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=583749
;

-- 2022-07-21T17:46:45.235Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=583747
;

-- 2022-07-21T17:46:45.236Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=583747
;

-- 2022-07-21T17:46:49.481Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluation','ALTER TABLE public.M_CostRevaluation ADD COLUMN UpdatedBy NUMERIC(10) NOT NULL')
;

-- 2022-07-21T17:46:55.948Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluation','ALTER TABLE public.M_CostRevaluation ADD COLUMN Updated TIMESTAMP WITH TIME ZONE NOT NULL')
;

-- 2022-07-21T17:47:26.752Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583756,287,0,28,135,542190,'DocAction',TO_TIMESTAMP('2022-07-21 17:47:26','YYYY-MM-DD HH24:MI:SS'),100,'N','CO','Der zukünftige Status des Belegs','D',0,2,'You find the current status in the Document Status field. The options are listed in a popup','Y','Y','N','N','Y','N','N','N','N','Y','N','N','N','N','N','N','Y','Belegverarbeitung',0,0,TO_TIMESTAMP('2022-07-21 17:47:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:47:26.753Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583756 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:47:26.754Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(287) 
;

-- 2022-07-21T17:47:27.087Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583757,289,0,17,131,542190,'DocStatus',TO_TIMESTAMP('2022-07-21 17:47:27','YYYY-MM-DD HH24:MI:SS'),100,'N','DR','The current status of the document','D',0,2,'The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','Y','N','N','Y','N','N','N','N','Y','N','N','N','N','N','N','Y','Belegstatus',0,0,TO_TIMESTAMP('2022-07-21 17:47:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:47:27.088Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583757 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:47:27.089Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(289) 
;

-- 2022-07-21T17:47:27.429Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583758,524,0,28,542190,'Processing',TO_TIMESTAMP('2022-07-21 17:47:27','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,1,'Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','Process Now',0,0,TO_TIMESTAMP('2022-07-21 17:47:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:47:27.430Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583758 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:47:27.430Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(524) 
;

-- 2022-07-21T17:47:27.743Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583759,1047,0,20,542190,'Processed',TO_TIMESTAMP('2022-07-21 17:47:27','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','D',0,1,'Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','Y','N','N','Y','N','N','N','N','Y','N','N','N','N','N','N','N','Verarbeitet',0,0,TO_TIMESTAMP('2022-07-21 17:47:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:47:27.744Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583759 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:47:27.745Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(1047) 
;


-- 2022-07-21T17:47:28.142Z
-- URL zum Konzept
INSERT INTO AD_Workflow (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_Workflow_ID,Author,Cost,Created,CreatedBy,DocumentNo,Duration,DurationUnit,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsValid,Name,PublishStatus,Updated,UpdatedBy,Value,Version,WaitingTime,WorkflowType,WorkingTime) VALUES ('1',0,0,542190,540117,'metasfresh ERP',0,TO_TIMESTAMP('2022-07-21 17:47:28','YYYY-MM-DD HH24:MI:SS'),100,'10000000',1,'D','D','Y','N','N','N','Process_M_CostRevaluation','R',TO_TIMESTAMP('2022-07-21 17:47:28','YYYY-MM-DD HH24:MI:SS'),100,'Process_M_CostRevaluation',0,0,'P',0)
;

-- 2022-07-21T17:47:28.143Z
-- URL zum Konzept
INSERT INTO AD_Workflow_Trl (AD_Language,AD_Workflow_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Workflow_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Workflow t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Workflow_ID=540117 AND NOT EXISTS (SELECT 1 FROM AD_Workflow_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Workflow_ID=t.AD_Workflow_ID)
;

-- 2022-07-21T17:47:28.272Z
-- URL zum Konzept
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,Description,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,JoinElement,Name,SplitElement,Updated,UpdatedBy,Value,WaitingTime,XPosition,YPosition) VALUES ('Z',0,0,540271,540117,0,TO_TIMESTAMP('2022-07-21 17:47:28','YYYY-MM-DD HH24:MI:SS'),100,'(Standard Node)',0,0,'D','Y','Y','X','(Start)','X',TO_TIMESTAMP('2022-07-21 17:47:28','YYYY-MM-DD HH24:MI:SS'),100,'(Start)',0,0,0)
;

-- 2022-07-21T17:47:28.276Z
-- URL zum Konzept
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_WF_Node_ID=540271 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2022-07-21T17:47:28.280Z
-- URL zum Konzept
UPDATE AD_Workflow SET AD_WF_Node_ID=540271, IsValid='Y',Updated=TO_TIMESTAMP('2022-07-21 17:47:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Workflow_ID=540117
;

-- 2022-07-21T17:47:28.283Z
-- URL zum Konzept
UPDATE AD_Workflow SET AD_WF_Node_ID=540271, IsValid='Y',Updated=TO_TIMESTAMP('2022-07-21 17:47:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Workflow_ID=540117
;

-- 2022-07-21T17:47:28.343Z
-- URL zum Konzept
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,Description,DocAction,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,JoinElement,Name,SplitElement,Updated,UpdatedBy,Value,WaitingTime,XPosition,YPosition) VALUES ('D',0,0,540272,540117,0,TO_TIMESTAMP('2022-07-21 17:47:28','YYYY-MM-DD HH24:MI:SS'),100,'(Standard Node)','--',0,0,'D','Y','Y','X','(DocAuto)','X',TO_TIMESTAMP('2022-07-21 17:47:28','YYYY-MM-DD HH24:MI:SS'),100,'(DocAuto)',0,0,0)
;

-- 2022-07-21T17:47:28.344Z
-- URL zum Konzept
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_WF_Node_ID=540272 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2022-07-21T17:47:28.399Z
-- URL zum Konzept
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,Description,DocAction,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,JoinElement,Name,SplitElement,Updated,UpdatedBy,Value,WaitingTime,XPosition,YPosition) VALUES ('D',0,0,540273,540117,0,TO_TIMESTAMP('2022-07-21 17:47:28','YYYY-MM-DD HH24:MI:SS'),100,'(Standard Node)','PR',0,0,'D','Y','Y','X','(DocPrepare)','X',TO_TIMESTAMP('2022-07-21 17:47:28','YYYY-MM-DD HH24:MI:SS'),100,'(DocPrepare)',0,0,0)
;

-- 2022-07-21T17:47:28.400Z
-- URL zum Konzept
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_WF_Node_ID=540273 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2022-07-21T17:47:28.447Z
-- URL zum Konzept
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,Description,DocAction,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,JoinElement,Name,SplitElement,Updated,UpdatedBy,Value,WaitingTime,XPosition,YPosition) VALUES ('D',0,0,540274,540117,0,TO_TIMESTAMP('2022-07-21 17:47:28','YYYY-MM-DD HH24:MI:SS'),100,'(Standard Node)','CO',0,0,'D','Y','Y','X','(DocComplete)','X',TO_TIMESTAMP('2022-07-21 17:47:28','YYYY-MM-DD HH24:MI:SS'),100,'(DocComplete)',0,0,0)
;

-- 2022-07-21T17:47:28.448Z
-- URL zum Konzept
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_WF_Node_ID=540274 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2022-07-21T17:47:28.532Z
-- URL zum Konzept
INSERT INTO AD_WF_NodeNext (AD_Client_ID,AD_Org_ID,AD_WF_Next_ID,AD_WF_Node_ID,AD_WF_NodeNext_ID,Created,CreatedBy,Description,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540273,540271,540182,TO_TIMESTAMP('2022-07-21 17:47:28','YYYY-MM-DD HH24:MI:SS'),100,'Standard Transition','D','Y',10,TO_TIMESTAMP('2022-07-21 17:47:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T17:47:28.535Z
-- URL zum Konzept
UPDATE AD_WF_NodeNext SET Description='(Standard Approval)', IsStdUserWorkflow='Y',Updated=TO_TIMESTAMP('2022-07-21 17:47:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_WF_NodeNext_ID=540182
;

-- 2022-07-21T17:47:28.588Z
-- URL zum Konzept
INSERT INTO AD_WF_NodeNext (AD_Client_ID,AD_Org_ID,AD_WF_Next_ID,AD_WF_Node_ID,AD_WF_NodeNext_ID,Created,CreatedBy,Description,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540272,540271,540183,TO_TIMESTAMP('2022-07-21 17:47:28','YYYY-MM-DD HH24:MI:SS'),100,'Standard Transition','D','Y',100,TO_TIMESTAMP('2022-07-21 17:47:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T17:47:28.626Z
-- URL zum Konzept
INSERT INTO AD_WF_NodeNext (AD_Client_ID,AD_Org_ID,AD_WF_Next_ID,AD_WF_Node_ID,AD_WF_NodeNext_ID,Created,CreatedBy,Description,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540274,540273,540184,TO_TIMESTAMP('2022-07-21 17:47:28','YYYY-MM-DD HH24:MI:SS'),100,'Standard Transition','D','Y',100,TO_TIMESTAMP('2022-07-21 17:47:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T17:47:28.674Z
-- URL zum Konzept
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Workflow_ID,Created,CreatedBy,EntityType,IsActive,IsReport,IsUseBPartnerLanguage,Name,Type,Updated,UpdatedBy,Value) VALUES ('1',0,0,585078,540117,TO_TIMESTAMP('2022-07-21 17:47:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Y','Process_M_CostRevaluation','Java',TO_TIMESTAMP('2022-07-21 17:47:28','YYYY-MM-DD HH24:MI:SS'),100,'Process_M_CostRevaluation')
;

-- 2022-07-21T17:47:28.683Z
-- URL zum Konzept
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=585078 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2022-07-21T17:47:28.720Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Process_ID=585078,Updated=TO_TIMESTAMP('2022-07-21 17:47:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583758
;

-- 2022-07-21T17:47:29.004Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Process_ID=585078,Updated=TO_TIMESTAMP('2022-07-21 17:47:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583756
;

-- 2022-07-21T17:49:35.227Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583760,290,0,10,542190,'DocumentNo',TO_TIMESTAMP('2022-07-21 17:49:35','YYYY-MM-DD HH24:MI:SS'),100,'N','Document sequence number of the document','D',0,40,'The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','Y',0,'Nr.',0,1,TO_TIMESTAMP('2022-07-21 17:49:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:49:35.228Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583760 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:49:35.229Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(290) 
;

-- 2022-07-21T17:52:06.572Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583761,581161,0,15,542190,'EvaluationStartDate',TO_TIMESTAMP('2022-07-21 17:52:06','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Startdatum der Bewertung',0,0,TO_TIMESTAMP('2022-07-21 17:52:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:52:06.573Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583761 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:52:06.574Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(581161) 
;

-- 2022-07-21T17:52:29.484Z
-- URL zum Konzept
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-07-21 17:52:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583761
;

-- 2022-07-21T17:53:06.657Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluation','ALTER TABLE public.M_CostRevaluation ADD COLUMN EvaluationStartDate TIMESTAMP WITHOUT TIME ZONE')
;

-- 2022-07-21T17:55:02.813Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-21 17:55:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581161 AND AD_Language='de_DE'
;

-- 2022-07-21T17:55:02.814Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581161,'de_DE') 
;

-- 2022-07-21T17:55:02.821Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(581161,'de_DE') 
;

-- 2022-07-21T17:56:34.798Z
-- URL zum Konzept
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,Description,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,542191,'N',TO_TIMESTAMP('2022-07-21 17:56:34','YYYY-MM-DD HH24:MI:SS'),100,'Cost Revaluation Line','D','N','Y','N','N','Y','N','Y','N','N','N',0,'Cost Revaluation Line','NP','L','M_CostRevaluationLine','DTI',TO_TIMESTAMP('2022-07-21 17:56:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T17:56:34.799Z
-- URL zum Konzept
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542191 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2022-07-21T17:56:34.842Z
-- URL zum Konzept
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555980,TO_TIMESTAMP('2022-07-21 17:56:34','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table M_CostRevaluationLine',1,'Y','N','Y','Y','M_CostRevaluationLine','N',1000000,TO_TIMESTAMP('2022-07-21 17:56:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T17:56:34.846Z
-- URL zum Konzept
CREATE SEQUENCE M_COSTREVALUATIONLINE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2022-07-21T17:57:23.598Z
-- URL zum Konzept
UPDATE AD_Table_Trl SET IsTranslated='Y', Name='Kosten Neubewertung Position',Updated=TO_TIMESTAMP('2022-07-21 17:57:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=542191
;

-- 2022-07-21T17:57:39.256Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581162,0,'M_CostRevaluationLine_ID',TO_TIMESTAMP('2022-07-21 17:57:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Cost Revaluation Line','Cost Revaluation Line',TO_TIMESTAMP('2022-07-21 17:57:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T17:57:39.257Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581162 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-21T17:57:39.555Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,583762,581162,0,13,542191,'M_CostRevaluationLine_ID',TO_TIMESTAMP('2022-07-21 17:57:39','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','Cost Revaluation Line',TO_TIMESTAMP('2022-07-21 17:57:39','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-07-21T17:57:39.557Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583762 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:57:39.558Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(581162) 
;

-- 2022-07-21T17:57:39.866Z
-- URL zum Konzept
ALTER SEQUENCE M_COSTREVALUATIONLINE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 RESTART 1000000
;


-- 2022-07-21T17:57:54.406Z
-- URL zum Konzept
/* DDL */ CREATE TABLE public.M_CostRevaluationLine (M_CostRevaluationLine_ID NUMERIC(10) NOT NULL, CONSTRAINT M_CostRevaluationLine_Key PRIMARY KEY (M_CostRevaluationLine_ID))
;

-- 2022-07-21T17:59:14.808Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583779,102,0,19,542191,'AD_Client_ID',TO_TIMESTAMP('2022-07-21 17:59:14','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Mandant',0,0,TO_TIMESTAMP('2022-07-21 17:59:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:59:14.810Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583779 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:59:14.812Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- 2022-07-21T17:59:15.270Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583780,113,0,30,542191,'AD_Org_ID',TO_TIMESTAMP('2022-07-21 17:59:15','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','N','N','N','Organisation',10,0,TO_TIMESTAMP('2022-07-21 17:59:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:59:15.271Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583780 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:59:15.272Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- 2022-07-21T17:59:15.692Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583781,126,0,19,542191,'AD_Table_ID',TO_TIMESTAMP('2022-07-21 17:59:15','YYYY-MM-DD HH24:MI:SS'),100,'N','Database Table information','D',0,10,'The Database Table provides the information of the table definition','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','DB-Tabelle',0,0,TO_TIMESTAMP('2022-07-21 17:59:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:59:15.693Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583781 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:59:15.694Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(126) 
;

-- 2022-07-21T17:59:16.010Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583782,2978,0,19,542191,'CM_Template_ID',TO_TIMESTAMP('2022-07-21 17:59:15','YYYY-MM-DD HH24:MI:SS'),100,'N','Template defines how content is displayed','D',0,10,'A template describes how content should get displayed, it contains layout and maybe also scripts on how to handle the content','Y','Y','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','Vorlage',0,0,TO_TIMESTAMP('2022-07-21 17:59:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:59:16.011Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583782 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:59:16.012Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(2978) 
;

-- 2022-07-21T17:59:16.315Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583783,245,0,16,542191,'Created',TO_TIMESTAMP('2022-07-21 17:59:16','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,7,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Erstellt',0,0,TO_TIMESTAMP('2022-07-21 17:59:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:59:16.316Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583783 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:59:16.317Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- 2022-07-21T17:59:16.784Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583784,246,0,18,110,542191,'CreatedBy',TO_TIMESTAMP('2022-07-21 17:59:16','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2022-07-21 17:59:16','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:59:16.785Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583784 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:59:16.786Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- 2022-07-21T17:59:17.160Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583785,275,0,10,542191,'Description',TO_TIMESTAMP('2022-07-21 17:59:17','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Beschreibung',0,0,TO_TIMESTAMP('2022-07-21 17:59:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:59:17.161Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583785 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:59:17.161Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(275) 
;

-- 2022-07-21T17:59:17.484Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583786,348,0,20,542191,'IsActive',TO_TIMESTAMP('2022-07-21 17:59:17','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Aktiv',0,0,TO_TIMESTAMP('2022-07-21 17:59:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:59:17.485Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583786 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:59:17.485Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- 2022-07-21T17:59:17.855Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583787,469,0,10,542191,'Name',TO_TIMESTAMP('2022-07-21 17:59:17','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,120,'E','','Y','Y','N','N','N','N','N','Y','N','Y','N','Y','N','N','N','N','Y','Name',10,1,TO_TIMESTAMP('2022-07-21 17:59:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:59:17.856Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583787 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:59:17.857Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(469) 
;

-- 2022-07-21T17:59:18.192Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583788,2642,0,14,542191,'OtherClause',TO_TIMESTAMP('2022-07-21 17:59:18','YYYY-MM-DD HH24:MI:SS'),100,'N','Other SQL Clause','D',0,2000,'Any other complete clause like GROUP BY, HAVING, ORDER BY, etc. after WHERE clause.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Other SQL Clause',0,0,TO_TIMESTAMP('2022-07-21 17:59:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:59:18.193Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583788 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:59:18.194Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(2642) 
;

-- 2022-07-21T17:59:18.529Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583789,607,0,16,542191,'Updated',TO_TIMESTAMP('2022-07-21 17:59:18','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,7,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2022-07-21 17:59:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:59:18.530Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583789 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:59:18.531Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- 2022-07-21T17:59:18.927Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583790,608,0,18,110,542191,'UpdatedBy',TO_TIMESTAMP('2022-07-21 17:59:18','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2022-07-21 17:59:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:59:18.928Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583790 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:59:18.929Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2022-07-21T17:59:19.301Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583791,630,0,14,542191,'WhereClause',TO_TIMESTAMP('2022-07-21 17:59:19','YYYY-MM-DD HH24:MI:SS'),100,'N','Fully qualified SQL WHERE clause','D',0,2000,'The Where Clause indicates the SQL WHERE clause to use for record selection. The WHERE clause is added to the query. Fully qualified means "tablename.columnname".','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Sql WHERE',0,0,TO_TIMESTAMP('2022-07-21 17:59:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T17:59:19.302Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583791 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T17:59:19.303Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(630) 
;

-- 2022-07-21T17:59:19.568Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluationLine','ALTER TABLE public.M_CostRevaluationLine ADD COLUMN AD_Client_ID NUMERIC(10) NOT NULL')
;

-- 2022-07-21T17:59:19.582Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluationLine','ALTER TABLE public.M_CostRevaluationLine ADD COLUMN AD_Org_ID NUMERIC(10) NOT NULL')
;

-- 2022-07-21T17:59:19.601Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluationLine','ALTER TABLE public.M_CostRevaluationLine ADD COLUMN AD_Table_ID NUMERIC(10) NOT NULL')
;

-- 2022-07-21T17:59:19.609Z
-- URL zum Konzept
ALTER TABLE M_CostRevaluationLine ADD CONSTRAINT ADTable_MCostRevaluationLine FOREIGN KEY (AD_Table_ID) REFERENCES public.AD_Table DEFERRABLE INITIALLY DEFERRED
;

-- 2022-07-21T17:59:19.620Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluationLine','ALTER TABLE public.M_CostRevaluationLine ADD COLUMN CM_Template_ID NUMERIC(10) NOT NULL')
;

-- 2022-07-21T17:59:19.625Z
-- URL zum Konzept
ALTER TABLE M_CostRevaluationLine ADD CONSTRAINT CMTemplate_MCostRevaluationLine FOREIGN KEY (CM_Template_ID) REFERENCES public.CM_Template DEFERRABLE INITIALLY DEFERRED
;

-- 2022-07-21T17:59:19.631Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluationLine','ALTER TABLE public.M_CostRevaluationLine ADD COLUMN Created TIMESTAMP WITH TIME ZONE NOT NULL')
;

-- 2022-07-21T17:59:19.642Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluationLine','ALTER TABLE public.M_CostRevaluationLine ADD COLUMN CreatedBy NUMERIC(10) NOT NULL')
;

-- 2022-07-21T17:59:19.650Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluationLine','ALTER TABLE public.M_CostRevaluationLine ADD COLUMN Description VARCHAR(255)')
;

-- 2022-07-21T17:59:19.658Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluationLine','ALTER TABLE public.M_CostRevaluationLine ADD COLUMN IsActive CHAR(1) CHECK (IsActive IN (''Y'',''N'')) NOT NULL')
;

-- 2022-07-21T17:59:19.667Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluationLine','ALTER TABLE public.M_CostRevaluationLine ADD COLUMN Name VARCHAR(120) NOT NULL')
;

-- 2022-07-21T17:59:19.675Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluationLine','ALTER TABLE public.M_CostRevaluationLine ADD COLUMN OtherClause VARCHAR(2000)')
;

-- 2022-07-21T17:59:19.695Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluationLine','ALTER TABLE public.M_CostRevaluationLine ADD COLUMN Updated TIMESTAMP WITH TIME ZONE NOT NULL')
;

-- 2022-07-21T17:59:19.705Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluationLine','ALTER TABLE public.M_CostRevaluationLine ADD COLUMN UpdatedBy NUMERIC(10) NOT NULL')
;

-- 2022-07-21T17:59:19.714Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluationLine','ALTER TABLE public.M_CostRevaluationLine ADD COLUMN WhereClause VARCHAR(2000)')
;

-- 2022-07-21T17:59:26.561Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=583791
;

-- 2022-07-21T17:59:26.562Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=583791
;

-- 2022-07-21T17:59:30.981Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=583788
;

-- 2022-07-21T17:59:30.984Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=583788
;

-- 2022-07-21T17:59:37.139Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=583787
;

-- 2022-07-21T17:59:37.140Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=583787
;

-- 2022-07-21T17:59:40.906Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=583785
;

-- 2022-07-21T17:59:40.906Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=583785
;

-- 2022-07-21T17:59:44.984Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=583782
;

-- 2022-07-21T17:59:44.985Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=583782
;

-- 2022-07-21T17:59:48.970Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=583781
;

-- 2022-07-21T17:59:48.971Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=583781
;

-- 2022-07-21T18:00:01.827Z
-- URL zum Konzept
INSERT INTO t_alter_column values('m_costrevaluationline','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2022-07-21T18:00:47.968Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583792,581160,0,19,542191,'M_CostRevaluation_ID',TO_TIMESTAMP('2022-07-21 18:00:47','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Cost Revaluation',0,0,TO_TIMESTAMP('2022-07-21 18:00:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T18:00:47.969Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583792 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T18:00:47.970Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(581160) 
;

-- 2022-07-21T18:02:10.091Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluationLine','ALTER TABLE public.M_CostRevaluationLine ADD COLUMN M_CostRevaluation_ID NUMERIC(10)')
;

-- 2022-07-21T18:02:10.098Z
-- URL zum Konzept
ALTER TABLE M_CostRevaluationLine ADD CONSTRAINT MCostRevaluation_MCostRevaluationLine FOREIGN KEY (M_CostRevaluation_ID) REFERENCES public.M_CostRevaluation DEFERRABLE INITIALLY DEFERRED
;

-- 2022-07-21T18:02:58.248Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583793,454,0,19,542191,'M_Product_ID',TO_TIMESTAMP('2022-07-21 18:02:58','YYYY-MM-DD HH24:MI:SS'),100,'N','Produkt, Leistung, Artikel','D',0,10,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Produkt',0,0,TO_TIMESTAMP('2022-07-21 18:02:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T18:02:58.249Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583793 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T18:02:58.250Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(454) 
;

-- 2022-07-21T18:03:35.789Z
-- URL zum Konzept
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-07-21 18:03:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583793
;

-- 2022-07-21T18:03:49.282Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluationLine','ALTER TABLE public.M_CostRevaluationLine ADD COLUMN M_Product_ID NUMERIC(10)')
;

-- 2022-07-21T18:03:49.286Z
-- URL zum Konzept
ALTER TABLE M_CostRevaluationLine ADD CONSTRAINT MProduct_MCostRevaluationLine FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;

-- 2022-07-21T18:04:35.214Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583794,1394,0,37,542191,'CurrentCostPrice',TO_TIMESTAMP('2022-07-21 18:04:35','YYYY-MM-DD HH24:MI:SS'),100,'N','Der gegenwärtig verwendete Kostenpreis','D',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Kostenpreis aktuell',0,0,TO_TIMESTAMP('2022-07-21 18:04:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T18:04:35.215Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583794 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T18:04:35.216Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(1394) 
;

-- 2022-07-21T18:04:57.739Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583795,2842,0,29,542191,'CurrentQty',TO_TIMESTAMP('2022-07-21 18:04:57','YYYY-MM-DD HH24:MI:SS'),100,'N','Menge aktuell','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Menge aktuell',0,0,TO_TIMESTAMP('2022-07-21 18:04:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T18:04:57.740Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583795 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T18:04:57.741Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(2842) 
;

-- 2022-07-21T18:05:54.404Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581163,0,'NewCostPrice',TO_TIMESTAMP('2022-07-21 18:05:54','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','New Cost Price','New Cost Price',TO_TIMESTAMP('2022-07-21 18:05:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:05:54.405Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581163 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-21T18:06:22.880Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Neuer Einstandspreis', PrintName='Neuer Einstandspreis',Updated=TO_TIMESTAMP('2022-07-21 18:06:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581163 AND AD_Language='de_DE'
;

-- 2022-07-21T18:06:22.881Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581163,'de_DE') 
;

-- 2022-07-21T18:06:22.899Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(581163,'de_DE') 
;

-- 2022-07-21T18:06:22.899Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='NewCostPrice', Name='Neuer Einstandspreis', Description=NULL, Help=NULL WHERE AD_Element_ID=581163
;

-- 2022-07-21T18:06:22.900Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='NewCostPrice', Name='Neuer Einstandspreis', Description=NULL, Help=NULL, AD_Element_ID=581163 WHERE UPPER(ColumnName)='NEWCOSTPRICE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-07-21T18:06:22.900Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='NewCostPrice', Name='Neuer Einstandspreis', Description=NULL, Help=NULL WHERE AD_Element_ID=581163 AND IsCentrallyMaintained='Y'
;

-- 2022-07-21T18:06:22.901Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Neuer Einstandspreis', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581163) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581163)
;

-- 2022-07-21T18:06:22.909Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Neuer Einstandspreis', Name='Neuer Einstandspreis' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=581163)
;

-- 2022-07-21T18:06:22.910Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Neuer Einstandspreis', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 581163
;

-- 2022-07-21T18:06:22.911Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Neuer Einstandspreis', Description=NULL, Help=NULL WHERE AD_Element_ID = 581163
;

-- 2022-07-21T18:06:22.912Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Neuer Einstandspreis', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581163
;

-- 2022-07-21T18:06:27.853Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-21 18:06:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581163 AND AD_Language='de_DE'
;

-- 2022-07-21T18:06:27.854Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581163,'de_DE') 
;

-- 2022-07-21T18:06:27.863Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(581163,'de_DE') 
;

-- 2022-07-21T18:06:55.925Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583796,581163,0,37,542191,'NewCostPrice',TO_TIMESTAMP('2022-07-21 18:06:55','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Neuer Einstandspreis',0,0,TO_TIMESTAMP('2022-07-21 18:06:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-07-21T18:06:55.926Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583796 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-07-21T18:06:55.928Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(581163) 
;

-- 2022-07-21T18:07:05.911Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluationLine','ALTER TABLE public.M_CostRevaluationLine ADD COLUMN NewCostPrice NUMERIC')
;

-- 2022-07-21T18:07:10.676Z
-- URL zum Konzept
INSERT INTO t_alter_column values('m_costrevaluationline','M_Product_ID','NUMERIC(10)',null,null)
;

-- 2022-07-21T18:07:14.842Z
-- URL zum Konzept
INSERT INTO t_alter_column values('m_costrevaluationline','M_CostRevaluationLine_ID','NUMERIC(10)',null,null)
;

-- 2022-07-21T18:12:09.268Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581164,0,TO_TIMESTAMP('2022-07-21 18:12:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kosten Neubewertung','Kosten Neubewertung',TO_TIMESTAMP('2022-07-21 18:12:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:12:09.269Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581164 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-21T18:12:40.813Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Cost Revaluation', PrintName='Cost Revaluation',Updated=TO_TIMESTAMP('2022-07-21 18:12:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581164 AND AD_Language='en_US'
;

-- 2022-07-21T18:12:40.814Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581164,'en_US') 
;

-- 2022-07-21T18:13:23.806Z
-- URL zum Konzept
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,581164,0,541568,TO_TIMESTAMP('2022-07-21 18:13:23','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Kosten Neubewertung','N',TO_TIMESTAMP('2022-07-21 18:13:23','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2022-07-21T18:13:23.807Z
-- URL zum Konzept
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541568 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2022-07-21T18:13:23.809Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(581164) 
;

-- 2022-07-21T18:13:23.814Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541568
;

-- 2022-07-21T18:13:23.816Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(541568)
;

-- 2022-07-21T18:13:28.610Z
-- URL zum Konzept
UPDATE AD_Window SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2022-07-21 18:13:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541568
;

-- 2022-07-21T18:15:05.333Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581160,0,546464,542190,541568,'Y',TO_TIMESTAMP('2022-07-21 18:15:05','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','M_CostRevaluation','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Cost Revaluation','N',10,0,TO_TIMESTAMP('2022-07-21 18:15:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:15:05.334Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546464 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-07-21T18:15:05.335Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(581160) 
;

-- 2022-07-21T18:15:05.336Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546464)
;

-- 2022-07-21T18:16:12.764Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583740,702140,0,546464,TO_TIMESTAMP('2022-07-21 18:16:12','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Cost Revaluation',TO_TIMESTAMP('2022-07-21 18:16:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:16:12.766Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702140 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-21T18:16:12.767Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581160) 
;

-- 2022-07-21T18:16:12.769Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702140
;

-- 2022-07-21T18:16:12.770Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(702140)
;

-- 2022-07-21T18:16:12.823Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583741,702141,0,546464,TO_TIMESTAMP('2022-07-21 18:16:12','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-07-21 18:16:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:16:12.824Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702141 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-21T18:16:12.825Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-07-21T18:16:12.957Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702141
;

-- 2022-07-21T18:16:12.957Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(702141)
;

-- 2022-07-21T18:16:13.021Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583742,702142,0,546464,TO_TIMESTAMP('2022-07-21 18:16:12','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','Organisation',TO_TIMESTAMP('2022-07-21 18:16:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:16:13.022Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702142 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-21T18:16:13.023Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-07-21T18:16:13.121Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702142
;

-- 2022-07-21T18:16:13.121Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(702142)
;

-- 2022-07-21T18:16:13.178Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583745,702143,0,546464,TO_TIMESTAMP('2022-07-21 18:16:13','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',7,'D','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','Y','N','N','N','N','N','Erstellt',TO_TIMESTAMP('2022-07-21 18:16:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:16:13.179Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702143 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-21T18:16:13.179Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2022-07-21T18:16:13.204Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702143
;

-- 2022-07-21T18:16:13.204Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(702143)
;

-- 2022-07-21T18:16:13.257Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583746,702144,0,546464,TO_TIMESTAMP('2022-07-21 18:16:13','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat',10,'D','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','Y','N','N','N','N','N','Erstellt durch',TO_TIMESTAMP('2022-07-21 18:16:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:16:13.258Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702144 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-21T18:16:13.259Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2022-07-21T18:16:13.296Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702144
;

-- 2022-07-21T18:16:13.297Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(702144)
;

-- 2022-07-21T18:16:13.355Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583748,702145,0,546464,TO_TIMESTAMP('2022-07-21 18:16:13','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-07-21 18:16:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:16:13.356Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702145 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-21T18:16:13.357Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-07-21T18:16:13.520Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702145
;

-- 2022-07-21T18:16:13.520Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(702145)
;

-- 2022-07-21T18:16:13.588Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583751,702146,0,546464,TO_TIMESTAMP('2022-07-21 18:16:13','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',7,'D','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','Y','N','N','N','N','N','Aktualisiert',TO_TIMESTAMP('2022-07-21 18:16:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:16:13.589Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702146 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-21T18:16:13.590Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607) 
;

-- 2022-07-21T18:16:13.618Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702146
;

-- 2022-07-21T18:16:13.618Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(702146)
;

-- 2022-07-21T18:16:13.669Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583752,702147,0,546464,TO_TIMESTAMP('2022-07-21 18:16:13','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'D','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','Y','N','N','N','N','N','Aktualisiert durch',TO_TIMESTAMP('2022-07-21 18:16:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:16:13.670Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702147 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-21T18:16:13.671Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608) 
;

-- 2022-07-21T18:16:13.705Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702147
;

-- 2022-07-21T18:16:13.705Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(702147)
;

-- 2022-07-21T18:16:13.751Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583754,702148,0,546464,TO_TIMESTAMP('2022-07-21 18:16:13','YYYY-MM-DD HH24:MI:SS'),100,'Accounting Date',7,'D','The Accounting Date indicates the date to be used on the General Ledger account entries generated from this document. It is also used for any currency conversion.','Y','Y','N','N','N','N','N','Buchungsdatum',TO_TIMESTAMP('2022-07-21 18:16:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:16:13.752Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702148 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-21T18:16:13.753Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(263) 
;

-- 2022-07-21T18:16:13.756Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702148
;

-- 2022-07-21T18:16:13.756Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(702148)
;

-- 2022-07-21T18:16:13.799Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583755,702149,0,546464,TO_TIMESTAMP('2022-07-21 18:16:13','YYYY-MM-DD HH24:MI:SS'),100,'Stammdaten für Buchhaltung',10,'D','Ein Kontenschema definiert eine Ausprägung von Stammdaten für die Buchhaltung wie verwendete Art der Kostenrechnung, Währung und Buchungsperiode.','Y','Y','N','N','N','N','N','Buchführungs-Schema',TO_TIMESTAMP('2022-07-21 18:16:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:16:13.799Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702149 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-21T18:16:13.800Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(181) 
;

-- 2022-07-21T18:16:13.809Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702149
;

-- 2022-07-21T18:16:13.809Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(702149)
;

-- 2022-07-21T18:16:13.851Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583756,702150,0,546464,TO_TIMESTAMP('2022-07-21 18:16:13','YYYY-MM-DD HH24:MI:SS'),100,'Der zukünftige Status des Belegs',2,'D','You find the current status in the Document Status field. The options are listed in a popup','Y','Y','N','N','N','N','N','Belegverarbeitung',TO_TIMESTAMP('2022-07-21 18:16:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:16:13.852Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702150 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-21T18:16:13.853Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(287) 
;

-- 2022-07-21T18:16:13.856Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702150
;

-- 2022-07-21T18:16:13.857Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(702150)
;

-- 2022-07-21T18:16:13.899Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583757,702151,0,546464,TO_TIMESTAMP('2022-07-21 18:16:13','YYYY-MM-DD HH24:MI:SS'),100,'The current status of the document',2,'D','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','Y','N','N','N','N','N','Belegstatus',TO_TIMESTAMP('2022-07-21 18:16:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:16:13.900Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702151 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-21T18:16:13.901Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(289) 
;

-- 2022-07-21T18:16:13.906Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702151
;

-- 2022-07-21T18:16:13.906Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(702151)
;

-- 2022-07-21T18:16:13.951Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583758,702152,0,546464,TO_TIMESTAMP('2022-07-21 18:16:13','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Process Now',TO_TIMESTAMP('2022-07-21 18:16:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:16:13.951Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702152 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-21T18:16:13.952Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(524) 
;

-- 2022-07-21T18:16:13.978Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702152
;

-- 2022-07-21T18:16:13.978Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(702152)
;

-- 2022-07-21T18:16:14.034Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583759,702153,0,546464,TO_TIMESTAMP('2022-07-21 18:16:13','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'D','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','Y','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2022-07-21 18:16:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:16:14.035Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702153 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-21T18:16:14.035Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047) 
;

-- 2022-07-21T18:16:14.040Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702153
;

-- 2022-07-21T18:16:14.040Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(702153)
;

-- 2022-07-21T18:16:14.084Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583760,702154,0,546464,TO_TIMESTAMP('2022-07-21 18:16:14','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document',40,'D','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','Y','N','N','N','N','N','Nr.',TO_TIMESTAMP('2022-07-21 18:16:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:16:14.084Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702154 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-21T18:16:14.085Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(290) 
;

-- 2022-07-21T18:16:14.088Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702154
;

-- 2022-07-21T18:16:14.088Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(702154)
;

-- 2022-07-21T18:16:14.132Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583761,702155,0,546464,TO_TIMESTAMP('2022-07-21 18:16:14','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','Y','N','N','N','N','N','Startdatum der Bewertung',TO_TIMESTAMP('2022-07-21 18:16:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:16:14.133Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702155 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-21T18:16:14.134Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581161) 
;

-- 2022-07-21T18:16:14.134Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702155
;

-- 2022-07-21T18:16:14.135Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(702155)
;

-- 2022-07-21T18:20:57.596Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-07-21 18:20:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=702140
;

-- 2022-07-21T18:23:12.560Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581165,0,TO_TIMESTAMP('2022-07-21 18:23:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kosten Neubewertung Position','Kosten Neubewertung Position',TO_TIMESTAMP('2022-07-21 18:23:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:23:12.560Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581165 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-07-21T18:23:23.075Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2022-07-21 18:23:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581165 AND AD_Language='de_DE'
;

-- 2022-07-21T18:23:23.076Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581165,'de_DE') 
;

-- 2022-07-21T18:23:23.083Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(581165,'de_DE') 
;

-- 2022-07-21T18:23:40.920Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Cost Revaluation Line', PrintName='Cost Revaluation Line',Updated=TO_TIMESTAMP('2022-07-21 18:23:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581165 AND AD_Language='en_US'
;

-- 2022-07-21T18:23:40.921Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581165,'en_US') 
;

-- 2022-07-21T18:24:13.019Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581162,0,546465,542191,541568,'Y',TO_TIMESTAMP('2022-07-21 18:24:12','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','M_CostRevaluationLine','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Cost Revaluation Line','N',20,0,TO_TIMESTAMP('2022-07-21 18:24:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:24:13.020Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546465 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-07-21T18:24:13.021Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(581162) 
;

-- 2022-07-21T18:24:13.024Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546465)
;

-- 2022-07-21T18:25:04.619Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583762,702156,0,546465,TO_TIMESTAMP('2022-07-21 18:25:04','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Cost Revaluation Line',TO_TIMESTAMP('2022-07-21 18:25:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:25:04.620Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702156 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-21T18:25:04.621Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581162) 
;

-- 2022-07-21T18:25:04.625Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702156
;

-- 2022-07-21T18:25:04.625Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(702156)
;

-- 2022-07-21T18:25:04.672Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583779,702157,0,546465,TO_TIMESTAMP('2022-07-21 18:25:04','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-07-21 18:25:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:25:04.673Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702157 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-21T18:25:04.674Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-07-21T18:25:04.792Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702157
;

-- 2022-07-21T18:25:04.792Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(702157)
;

-- 2022-07-21T18:25:04.886Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583780,702158,0,546465,TO_TIMESTAMP('2022-07-21 18:25:04','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','Organisation',TO_TIMESTAMP('2022-07-21 18:25:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:25:04.887Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702158 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-21T18:25:04.888Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-07-21T18:25:04.984Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702158
;

-- 2022-07-21T18:25:04.984Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(702158)
;

-- 2022-07-21T18:25:05.047Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583786,702159,0,546465,TO_TIMESTAMP('2022-07-21 18:25:04','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-07-21 18:25:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:25:05.048Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702159 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-21T18:25:05.049Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-07-21T18:25:05.145Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702159
;

-- 2022-07-21T18:25:05.145Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(702159)
;

-- 2022-07-21T18:25:05.199Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583792,702160,0,546465,TO_TIMESTAMP('2022-07-21 18:25:05','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Cost Revaluation',TO_TIMESTAMP('2022-07-21 18:25:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:25:05.199Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702160 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-21T18:25:05.200Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581160) 
;

-- 2022-07-21T18:25:05.201Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702160
;

-- 2022-07-21T18:25:05.201Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(702160)
;

-- 2022-07-21T18:25:05.250Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583793,702161,0,546465,TO_TIMESTAMP('2022-07-21 18:25:05','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',10,'D','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','Y','N','N','N','N','N','Produkt',TO_TIMESTAMP('2022-07-21 18:25:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:25:05.251Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702161 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-21T18:25:05.252Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2022-07-21T18:25:05.288Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702161
;

-- 2022-07-21T18:25:05.288Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(702161)
;

-- 2022-07-21T18:25:05.349Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583794,702162,0,546465,TO_TIMESTAMP('2022-07-21 18:25:05','YYYY-MM-DD HH24:MI:SS'),100,'Der gegenwärtig verwendete Kostenpreis',14,'D','Y','Y','N','N','N','N','N','Kostenpreis aktuell',TO_TIMESTAMP('2022-07-21 18:25:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:25:05.350Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702162 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-21T18:25:05.350Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1394) 
;

-- 2022-07-21T18:25:05.351Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702162
;

-- 2022-07-21T18:25:05.351Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(702162)
;

-- 2022-07-21T18:25:05.395Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583795,702163,0,546465,TO_TIMESTAMP('2022-07-21 18:25:05','YYYY-MM-DD HH24:MI:SS'),100,'Menge aktuell',10,'D','Y','Y','N','N','N','N','N','Menge aktuell',TO_TIMESTAMP('2022-07-21 18:25:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:25:05.396Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702163 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-21T18:25:05.396Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2842) 
;

-- 2022-07-21T18:25:05.397Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702163
;

-- 2022-07-21T18:25:05.397Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(702163)
;

-- 2022-07-21T18:25:05.437Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583796,702164,0,546465,TO_TIMESTAMP('2022-07-21 18:25:05','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','Y','N','N','N','N','N','Neuer Einstandspreis',TO_TIMESTAMP('2022-07-21 18:25:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:25:05.438Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=702164 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-07-21T18:25:05.438Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581163) 
;

-- 2022-07-21T18:25:05.438Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702164
;

-- 2022-07-21T18:25:05.438Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(702164)
;

-- 2022-07-21T18:26:20.891Z
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,581164,541977,0,541568,TO_TIMESTAMP('2022-07-21 18:26:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Cost Revaluation','Y','N','N','N','N','Kosten Neubewertung',TO_TIMESTAMP('2022-07-21 18:26:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:26:20.892Z
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541977 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2022-07-21T18:26:20.893Z
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541977, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541977)
;

-- 2022-07-21T18:26:20.897Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(581164) 
;

-- 2022-07-21T18:27:34.913Z
-- URL zum Konzept
UPDATE AD_Tab SET TabLevel=10,Updated=TO_TIMESTAMP('2022-07-21 18:27:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546465
;

-- 2022-07-21T18:27:40.803Z
-- URL zum Konzept
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2022-07-21 18:27:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546465
;

-- 2022-07-21T18:28:24.074Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546465,545103,TO_TIMESTAMP('2022-07-21 18:28:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-07-21 18:28:24','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-07-21T18:28:24.075Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545103 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-07-21T18:28:45.616Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546191,545103,TO_TIMESTAMP('2022-07-21 18:28:45','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-07-21 18:28:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:29:00.843Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546191,549559,TO_TIMESTAMP('2022-07-21 18:29:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2022-07-21 18:29:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:32:17.082Z
-- URL zum Konzept
UPDATE AD_Field SET IsActive='N',Updated=TO_TIMESTAMP('2022-07-21 18:32:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=702140
;

-- 2022-07-21T18:32:21.185Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=702140
;

-- 2022-07-21T18:32:21.185Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=702140
;

-- 2022-07-21T18:32:21.186Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=702140
;

-- 2022-07-21T18:32:46.739Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,702158,0,546465,549559,610528,'F',TO_TIMESTAMP('2022-07-21 18:32:46','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Organisation',10,0,0,TO_TIMESTAMP('2022-07-21 18:32:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:33:18.215Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,702159,0,546465,549559,610529,'F',TO_TIMESTAMP('2022-07-21 18:33:18','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',20,0,0,TO_TIMESTAMP('2022-07-21 18:33:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:36:26.763Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546464,545104,TO_TIMESTAMP('2022-07-21 18:36:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-07-21 18:36:26','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-07-21T18:36:26.764Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545104 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-07-21T18:36:31.715Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546192,545104,TO_TIMESTAMP('2022-07-21 18:36:31','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-07-21 18:36:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:36:46.635Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546192,549560,TO_TIMESTAMP('2022-07-21 18:36:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2022-07-21 18:36:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:37:19.933Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,702149,0,546464,549560,610530,'F',TO_TIMESTAMP('2022-07-21 18:37:19','YYYY-MM-DD HH24:MI:SS'),100,'Stammdaten für Buchhaltung','Ein Kontenschema definiert eine Ausprägung von Stammdaten für die Buchhaltung wie verwendete Art der Kostenrechnung, Währung und Buchungsperiode.','Y','N','N','Y','N','N','N',0,'Buchführungs-Schema',10,0,0,TO_TIMESTAMP('2022-07-21 18:37:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:37:31.975Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,702148,0,546464,549560,610531,'F',TO_TIMESTAMP('2022-07-21 18:37:31','YYYY-MM-DD HH24:MI:SS'),100,'Accounting Date','The Accounting Date indicates the date to be used on the General Ledger account entries generated from this document. It is also used for any currency conversion.','Y','N','N','Y','N','N','N',0,'Buchungsdatum',20,0,0,TO_TIMESTAMP('2022-07-21 18:37:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:37:49.684Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,702155,0,546464,549560,610532,'F',TO_TIMESTAMP('2022-07-21 18:37:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Startdatum der Bewertung',30,0,0,TO_TIMESTAMP('2022-07-21 18:37:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:38:44.395Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546193,545104,TO_TIMESTAMP('2022-07-21 18:38:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-07-21 18:38:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:38:59.582Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546193,549561,TO_TIMESTAMP('2022-07-21 18:38:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','status',10,TO_TIMESTAMP('2022-07-21 18:38:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:39:09.494Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,702145,0,546464,549561,610533,'F',TO_TIMESTAMP('2022-07-21 18:39:09','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2022-07-21 18:39:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:39:27.727Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,702154,0,546464,549561,610534,'F',TO_TIMESTAMP('2022-07-21 18:39:27','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','N','N','N',0,'Nr.',20,0,0,TO_TIMESTAMP('2022-07-21 18:39:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:39:50.460Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,702142,0,546464,549561,610535,'F',TO_TIMESTAMP('2022-07-21 18:39:50','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Organisation',30,0,0,TO_TIMESTAMP('2022-07-21 18:39:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:39:58.333Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,702141,0,546464,549561,610536,'F',TO_TIMESTAMP('2022-07-21 18:39:58','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',40,0,0,TO_TIMESTAMP('2022-07-21 18:39:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:40:44.160Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546194,545103,TO_TIMESTAMP('2022-07-21 18:40:44','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-07-21 18:40:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:40:52.979Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546194,549562,TO_TIMESTAMP('2022-07-21 18:40:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2022-07-21 18:40:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:41:06.146Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,702159,0,546465,549562,610537,'F',TO_TIMESTAMP('2022-07-21 18:41:06','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2022-07-21 18:41:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:41:20.874Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,702158,0,546465,549562,610538,'F',TO_TIMESTAMP('2022-07-21 18:41:20','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Organisation',20,0,0,TO_TIMESTAMP('2022-07-21 18:41:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:41:27.965Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,702157,0,546465,549562,610539,'F',TO_TIMESTAMP('2022-07-21 18:41:27','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',30,0,0,TO_TIMESTAMP('2022-07-21 18:41:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:45:29.690Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-07-21 18:45:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610532
;

-- 2022-07-21T18:45:29.692Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-07-21 18:45:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610534
;

-- 2022-07-21T18:45:29.694Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-07-21 18:45:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610531
;

-- 2022-07-21T18:45:29.697Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-07-21 18:45:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610530
;

-- 2022-07-21T18:45:29.699Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-07-21 18:45:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610533
;

-- 2022-07-21T18:45:29.701Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-07-21 18:45:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610535
;

-- 2022-07-21T18:45:29.703Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-07-21 18:45:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610536
;

-- 2022-07-21T18:46:27.919Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluation','ALTER TABLE public.M_CostRevaluation ADD COLUMN DocAction CHAR(2) DEFAULT ''CO'' NOT NULL')
;

-- 2022-07-21T18:46:36.309Z
-- URL zum Konzept
INSERT INTO t_alter_column values('m_costrevaluation','DateAcct','TIMESTAMP WITHOUT TIME ZONE',null,null)
;

-- 2022-07-21T18:46:57.851Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluation','ALTER TABLE public.M_CostRevaluation ADD COLUMN DocStatus VARCHAR(2) DEFAULT ''DR'' NOT NULL')
;

-- 2022-07-21T18:47:02.949Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluation','ALTER TABLE public.M_CostRevaluation ADD COLUMN DocumentNo VARCHAR(40)')
;

-- 2022-07-21T18:47:09.178Z
-- URL zum Konzept
INSERT INTO t_alter_column values('m_costrevaluation','IsActive','CHAR(1)',null,null)
;

-- 2022-07-21T18:47:14.744Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluation','ALTER TABLE public.M_CostRevaluation ADD COLUMN Processed CHAR(1) DEFAULT ''N'' CHECK (Processed IN (''Y'',''N'')) NOT NULL')
;

-- 2022-07-21T18:47:19.069Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluation','ALTER TABLE public.M_CostRevaluation ADD COLUMN Processing CHAR(1)')
;

-- 2022-07-21T18:47:25.912Z
-- URL zum Konzept
INSERT INTO t_alter_column values('m_costrevaluation','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2022-07-21T18:47:30.714Z
-- URL zum Konzept
INSERT INTO t_alter_column values('m_costrevaluation','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2022-07-21T18:47:35.975Z
-- URL zum Konzept
INSERT INTO t_alter_column values('m_costrevaluation','C_AcctSchema_ID','NUMERIC(10)',null,null)
;

-- 2022-07-21T18:47:40.208Z
-- URL zum Konzept
INSERT INTO t_alter_column values('m_costrevaluation','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2022-07-21T18:47:45.062Z
-- URL zum Konzept
INSERT INTO t_alter_column values('m_costrevaluation','AD_Client_ID','NUMERIC(10)',null,null)
;

-- 2022-07-21T18:48:17.271Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-07-21 18:48:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610534
;

-- 2022-07-21T18:48:17.272Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-07-21 18:48:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610532
;

-- 2022-07-21T18:49:15.582Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-07-21 18:49:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610536
;

-- 2022-07-21T18:51:44.786Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=610528
;

-- 2022-07-21T18:51:51.920Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=610529
;

-- 2022-07-21T18:53:17.313Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluationLine','ALTER TABLE public.M_CostRevaluationLine ADD COLUMN CurrentCostPrice NUMERIC')
;

-- 2022-07-21T18:53:24.219Z
-- URL zum Konzept
INSERT INTO t_alter_column values('m_costrevaluationline','NewCostPrice','NUMERIC',null,null)
;

-- 2022-07-21T18:53:29.445Z
-- URL zum Konzept
INSERT INTO t_alter_column values('m_costrevaluationline','M_Product_ID','NUMERIC(10)',null,null)
;

-- 2022-07-21T18:53:35.602Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_CostRevaluationLine','ALTER TABLE public.M_CostRevaluationLine ADD COLUMN CurrentQty NUMERIC')
;

-- 2022-07-21T18:54:55.697Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,702161,0,546465,549559,610540,'F',TO_TIMESTAMP('2022-07-21 18:54:55','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','N','N',0,'Produkt',10,0,0,TO_TIMESTAMP('2022-07-21 18:54:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:55:10.196Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,702162,0,546465,549559,610541,'F',TO_TIMESTAMP('2022-07-21 18:55:10','YYYY-MM-DD HH24:MI:SS'),100,'Der gegenwärtig verwendete Kostenpreis','Y','N','N','Y','N','N','N',0,'Kostenpreis aktuell',20,0,0,TO_TIMESTAMP('2022-07-21 18:55:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:55:20.709Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,702163,0,546465,549559,610542,'F',TO_TIMESTAMP('2022-07-21 18:55:20','YYYY-MM-DD HH24:MI:SS'),100,'Menge aktuell','Y','N','N','Y','N','N','N',0,'Menge aktuell',30,0,0,TO_TIMESTAMP('2022-07-21 18:55:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:55:35.349Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,702164,0,546465,549559,610543,'F',TO_TIMESTAMP('2022-07-21 18:55:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Neuer Einstandspreis',40,0,0,TO_TIMESTAMP('2022-07-21 18:55:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-07-21T18:56:28.308Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-07-21 18:56:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610540
;

-- 2022-07-21T18:56:28.311Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-07-21 18:56:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610543
;

-- 2022-07-21T18:56:28.313Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-07-21 18:56:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610541
;

-- 2022-07-21T18:56:28.316Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-07-21 18:56:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610542
;

-- 2022-07-21T18:56:28.318Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-07-21 18:56:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610537
;

-- 2022-07-21T18:56:28.320Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-07-21 18:56:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610538
;

-- 2022-07-21T18:58:17.778Z
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-07-21 18:58:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583755
;

-- 2022-07-21T18:58:34.621Z
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-07-21 18:58:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583761
;

-- 2022-07-21T18:59:13.297Z
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-07-21 18:59:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583793
;

-- 2022-07-21T18:59:25.565Z
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-07-21 18:59:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583796
;

-- 2022-07-21T19:03:13.874Z
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-07-21 19:03:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583760
;

-- 2022-07-21T19:03:26.283Z
-- URL zum Konzept
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-07-21 19:03:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583760
;

-- 2022-07-21T19:15:36.394Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=540272, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2022-07-21 19:15:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583793
;

-- 2022-07-21T19:16:12.861Z
-- URL zum Konzept
UPDATE AD_Column SET IsAutocomplete='Y',Updated=TO_TIMESTAMP('2022-07-21 19:16:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583793
;

-- 2022-07-21T19:18:51.507Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=5,Updated=TO_TIMESTAMP('2022-07-21 19:18:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610543
;

-- 2022-07-21T19:19:38.880Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2022-07-21 19:19:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=610543
;

