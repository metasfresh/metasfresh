-- Table: S_HumanResourceTestGroup
-- 2023-05-02T08:07:02.519851900Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542326,'N',TO_TIMESTAMP('2023-05-02 11:07:01.446','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Test facility group','NP','L','S_HumanResourceTestGroup','DTI',TO_TIMESTAMP('2023-05-02 11:07:01.446','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-02T08:07:02.525869300Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542326 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-05-02T08:07:03.041748500Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556260,TO_TIMESTAMP('2023-05-02 11:07:02.927','YYYY-MM-DD HH24:MI:SS.US'),100,1000000,50000,'Table S_HumanResourceTestGroup',1,'Y','N','Y','Y','S_HumanResourceTestGroup','N',1000000,TO_TIMESTAMP('2023-05-02 11:07:02.927','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-02T08:07:03.062746800Z
CREATE SEQUENCE S_HUMANRESOURCETESTGROUP_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: S_HumanResourceTestGroup.AD_Client_ID
-- 2023-05-02T08:07:10.670595800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586498,102,0,19,542326,'AD_Client_ID',TO_TIMESTAMP('2023-05-02 11:07:10.522','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2023-05-02 11:07:10.522','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-02T08:07:10.675597Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586498 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-02T08:07:11.517640800Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: S_HumanResourceTestGroup.AD_Org_ID
-- 2023-05-02T08:07:12.755242Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586499,113,0,30,542326,'AD_Org_ID',TO_TIMESTAMP('2023-05-02 11:07:12.375','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2023-05-02 11:07:12.375','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-02T08:07:12.756349500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586499 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-02T08:07:13.363489600Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: S_HumanResourceTestGroup.Created
-- 2023-05-02T08:07:14.173719Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586500,245,0,16,542326,'Created',TO_TIMESTAMP('2023-05-02 11:07:13.873','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2023-05-02 11:07:13.873','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-02T08:07:14.175717600Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586500 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-02T08:07:14.748167200Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: S_HumanResourceTestGroup.CreatedBy
-- 2023-05-02T08:07:15.626205500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586501,246,0,18,110,542326,'CreatedBy',TO_TIMESTAMP('2023-05-02 11:07:15.284','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2023-05-02 11:07:15.284','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-02T08:07:15.628282800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586501 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-02T08:07:16.196726200Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: S_HumanResourceTestGroup.IsActive
-- 2023-05-02T08:07:16.986049400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586502,348,0,20,542326,'IsActive',TO_TIMESTAMP('2023-05-02 11:07:16.703','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2023-05-02 11:07:16.703','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-02T08:07:16.987047500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586502 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-02T08:07:17.583939200Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: S_HumanResourceTestGroup.Updated
-- 2023-05-02T08:07:18.416496100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586503,607,0,16,542326,'Updated',TO_TIMESTAMP('2023-05-02 11:07:18.129','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2023-05-02 11:07:18.129','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-02T08:07:18.418522200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586503 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-02T08:07:19.010718500Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: S_HumanResourceTestGroup.UpdatedBy
-- 2023-05-02T08:07:19.948746300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586504,608,0,18,110,542326,'UpdatedBy',TO_TIMESTAMP('2023-05-02 11:07:19.583','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2023-05-02 11:07:19.583','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-02T08:07:19.950745500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586504 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-02T08:07:20.712785100Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2023-05-02T08:07:21.964365Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582271,0,'S_HumanResourceTestGroup_ID',TO_TIMESTAMP('2023-05-02 11:07:21.874','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Test facility group','Test facility group',TO_TIMESTAMP('2023-05-02 11:07:21.874','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-02T08:07:21.966366700Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582271 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: S_HumanResourceTestGroup.S_HumanResourceTestGroup_ID
-- 2023-05-02T08:07:22.848941600Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586505,582271,0,13,542326,'S_HumanResourceTestGroup_ID',TO_TIMESTAMP('2023-05-02 11:07:21.871','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Test facility group',0,0,TO_TIMESTAMP('2023-05-02 11:07:21.871','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-02T08:07:22.850969Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586505 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-02T08:07:23.649865Z
/* DDL */  select update_Column_Translation_From_AD_Element(582271) 
;

-- Column: S_HumanResourceTestGroup.Name
-- 2023-05-02T08:08:54.884956500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586506,469,0,10,542326,'Name',TO_TIMESTAMP('2023-05-02 11:08:54.722','YYYY-MM-DD HH24:MI:SS.US'),100,'N','','D',0,40,'E','','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Name',0,1,TO_TIMESTAMP('2023-05-02 11:08:54.722','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-02T08:08:54.886955700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586506 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-02T08:08:55.446031600Z
/* DDL */  select update_Column_Translation_From_AD_Element(469) 
;

-- Column: S_HumanResourceTestGroup.Department
-- 2023-05-02T08:09:22.185588600Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586507,582261,0,10,542326,'Department',TO_TIMESTAMP('2023-05-02 11:09:22.045','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Abteilung',0,0,TO_TIMESTAMP('2023-05-02 11:09:22.045','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-02T08:09:22.189102300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586507 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-02T08:09:22.823060100Z
/* DDL */  select update_Column_Translation_From_AD_Element(582261) 
;

-- 2023-05-02T08:09:40.448351300Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582272,0,'GroupIdentifier',TO_TIMESTAMP('2023-05-02 11:09:40.289','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','LPG','LPG',TO_TIMESTAMP('2023-05-02 11:09:40.289','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-02T08:09:40.450354200Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582272 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: GroupIdentifier
-- 2023-05-02T08:10:06.362812900Z
UPDATE AD_Element_Trl SET Description='Gemittelte Wochenkapazität',Updated=TO_TIMESTAMP('2023-05-02 11:10:06.362','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582272 AND AD_Language='de_CH'
;

-- 2023-05-02T08:10:06.365815700Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582272,'de_CH') 
;

-- Element: GroupIdentifier
-- 2023-05-02T08:10:08.718450800Z
UPDATE AD_Element_Trl SET Description='Gemittelte Wochenkapazität',Updated=TO_TIMESTAMP('2023-05-02 11:10:08.718','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582272 AND AD_Language='de_DE'
;

-- 2023-05-02T08:10:08.719498100Z
UPDATE AD_Element SET Description='Gemittelte Wochenkapazität' WHERE AD_Element_ID=582272
;

-- 2023-05-02T08:10:09.099498600Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582272,'de_DE') 
;

-- 2023-05-02T08:10:09.100497300Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582272,'de_DE') 
;

-- Element: GroupIdentifier
-- 2023-05-02T08:10:12.977642100Z
UPDATE AD_Element_Trl SET Description='Gemittelte Wochenkapazität',Updated=TO_TIMESTAMP('2023-05-02 11:10:12.977','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582272 AND AD_Language='fr_CH'
;

-- 2023-05-02T08:10:12.979627900Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582272,'fr_CH') 
;

-- Element: GroupIdentifier
-- 2023-05-02T08:10:23.183406Z
UPDATE AD_Element_Trl SET Description='Average weekly capacity',Updated=TO_TIMESTAMP('2023-05-02 11:10:23.183','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582272 AND AD_Language='en_US'
;

-- 2023-05-02T08:10:23.185403900Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582272,'en_US') 
;

-- Column: S_HumanResourceTestGroup.GroupIdentifier
-- 2023-05-02T08:11:01.163513900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586508,582272,0,10,542326,'GroupIdentifier',TO_TIMESTAMP('2023-05-02 11:11:00.999','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Gemittelte Wochenkapazität','D',0,40,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'LPG',0,0,TO_TIMESTAMP('2023-05-02 11:11:00.999','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-02T08:11:01.167541200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586508 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-02T08:11:01.890243400Z
/* DDL */  select update_Column_Translation_From_AD_Element(582272) 
;

-- 2023-05-02T08:11:40.176459900Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582273,0,'CapacityInHours',TO_TIMESTAMP('2023-05-02 11:11:40.019','YYYY-MM-DD HH24:MI:SS.US'),100,'Personnel capacity hours per week','D','Y','Capacity in hours','Capacity in hours',TO_TIMESTAMP('2023-05-02 11:11:40.019','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-05-02T08:11:40.177533600Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582273 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: CapacityInHours
-- 2023-05-02T08:12:00.166772Z
UPDATE AD_Element_Trl SET Description='Personelle Kapazität Stunden pro Woche', Name='Kapazität in Stunden', PrintName='Kapazität in Stunden',Updated=TO_TIMESTAMP('2023-05-02 11:12:00.166','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582273 AND AD_Language='de_CH'
;

-- 2023-05-02T08:12:00.168809Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582273,'de_CH') 
;

-- Element: CapacityInHours
-- 2023-05-02T08:12:09.002596100Z
UPDATE AD_Element_Trl SET Description='Personelle Kapazität Stunden pro Woche', Name='Kapazität in Stunden', PrintName='Kapazität in Stunden',Updated=TO_TIMESTAMP('2023-05-02 11:12:09.002','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582273 AND AD_Language='de_DE'
;

-- 2023-05-02T08:12:09.003601Z
UPDATE AD_Element SET Description='Personelle Kapazität Stunden pro Woche', Name='Kapazität in Stunden', PrintName='Kapazität in Stunden' WHERE AD_Element_ID=582273
;

-- 2023-05-02T08:12:09.654591500Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582273,'de_DE') 
;

-- 2023-05-02T08:12:09.659315Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582273,'de_DE') 
;

-- Element: CapacityInHours
-- 2023-05-02T08:12:20.350647600Z
UPDATE AD_Element_Trl SET Description='Personelle Kapazität Stunden pro Woche', Name='Kapazität in Stunden', PrintName='Kapazität in Stunden',Updated=TO_TIMESTAMP('2023-05-02 11:12:20.35','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582273 AND AD_Language='fr_CH'
;

-- 2023-05-02T08:12:20.351648500Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582273,'fr_CH') 
;

-- Column: S_HumanResourceTestGroup.CapacityInHours
-- 2023-05-02T08:12:59.781788900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586509,582273,0,11,542326,'CapacityInHours',TO_TIMESTAMP('2023-05-02 11:12:59.624','YYYY-MM-DD HH24:MI:SS.US'),100,'N','0','Personelle Kapazität Stunden pro Woche','D',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Kapazität in Stunden',0,0,TO_TIMESTAMP('2023-05-02 11:12:59.624','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-02T08:12:59.783797500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586509 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-02T08:13:00.335017800Z
/* DDL */  select update_Column_Translation_From_AD_Element(582273) 
;

-- Column: S_HumanResourceTestGroup.Department
-- 2023-05-02T08:13:22.991141200Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-05-02 11:13:22.991','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586507
;

-- Column: S_HumanResourceTestGroup.Name
-- 2023-05-02T08:13:52.681486800Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-05-02 11:13:52.681','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586506
;

-- Column: S_HumanResourceTestGroup.GroupIdentifier
-- 2023-05-02T08:14:12.494411300Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-05-02 11:14:12.494','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586508
;

-- 2023-05-02T08:14:21.739611500Z
/* DDL */ CREATE TABLE public.S_HumanResourceTestGroup (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, CapacityInHours NUMERIC(10) DEFAULT 0 NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Department VARCHAR(255) NOT NULL, GroupIdentifier VARCHAR(40) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Name VARCHAR(40) NOT NULL, S_HumanResourceTestGroup_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT S_HumanResourceTestGroup_Key PRIMARY KEY (S_HumanResourceTestGroup_ID))
;



-- Column: S_HumanResourceTestGroup.S_Resource_ID
-- 2023-05-02T08:17:30.522381100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586510,1777,0,19,542326,'S_Resource_ID',TO_TIMESTAMP('2023-05-02 11:17:30.369','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Ressource','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Ressource',0,0,TO_TIMESTAMP('2023-05-02 11:17:30.369','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-05-02T08:17:30.883401700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586510 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-02T08:17:31.047887400Z
/* DDL */  select update_Column_Translation_From_AD_Element(1777)
;

-- 2023-05-02T08:17:35.138888700Z
/* DDL */ SELECT public.db_alter_table('S_HumanResourceTestGroup','ALTER TABLE public.S_HumanResourceTestGroup ADD COLUMN S_Resource_ID NUMERIC(10) NOT NULL')
;

-- 2023-05-02T08:17:35.150776100Z
ALTER TABLE S_HumanResourceTestGroup ADD CONSTRAINT SResource_SHumanResourceTestGroup FOREIGN KEY (S_Resource_ID) REFERENCES public.S_Resource DEFERRABLE INITIALLY DEFERRED
;



-- Column: S_HumanResourceTestGroup.GroupIdentifier
-- 2023-05-02T08:52:57.643220900Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=10,Updated=TO_TIMESTAMP('2023-05-02 11:52:57.643','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586508
;

-- 2023-05-02T08:52:59.695542900Z
INSERT INTO t_alter_column values('s_humanresourcetestgroup','GroupIdentifier','VARCHAR(40)',null,null)
;

-- Column: S_HumanResourceTestGroup.Name
-- 2023-05-02T08:53:15.312118700Z
UPDATE AD_Column SET IsIdentifier='Y',Updated=TO_TIMESTAMP('2023-05-02 11:53:15.312','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586506
;

-- Column: S_HumanResourceTestGroup.GroupIdentifier
-- 2023-05-02T08:53:23.577119200Z
UPDATE AD_Column SET SeqNo=0,Updated=TO_TIMESTAMP('2023-05-02 11:53:23.577','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586508
;

-- 2023-05-02T08:53:26.889276200Z
INSERT INTO t_alter_column values('s_humanresourcetestgroup','GroupIdentifier','VARCHAR(40)',null,null)
;

-- 2023-05-02T08:53:33.939286Z
INSERT INTO t_alter_column values('s_humanresourcetestgroup','Name','VARCHAR(40)',null,null)
;

-- Column: S_HumanResourceTestGroup.Department
-- 2023-05-02T08:53:52.076286400Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=2,Updated=TO_TIMESTAMP('2023-05-02 11:53:52.076','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586507
;

-- 2023-05-02T08:54:19.510006100Z
INSERT INTO t_alter_column values('s_humanresourcetestgroup','Department','VARCHAR(255)',null,null)
;

