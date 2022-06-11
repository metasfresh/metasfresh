-- Table: S_Resource_Group_Assignment
-- 2022-05-24T07:55:41.276Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,542155,'N',TO_TIMESTAMP('2022-05-24 10:55:41','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','Y','N','N',0,'Resource Group Assignment','NP','L','S_Resource_Group_Assignment','DTI',TO_TIMESTAMP('2022-05-24 10:55:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-24T07:55:41.280Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542155 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2022-05-24T07:55:41.379Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555894,TO_TIMESTAMP('2022-05-24 10:55:41','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table S_Resource_Group_Assignment',1,'Y','N','Y','Y','S_Resource_Group_Assignment','N',1000000,TO_TIMESTAMP('2022-05-24 10:55:41','YYYY-MM-DD HH24:MI:SS'),100)
;


-- Column: S_Resource_Group_Assignment.AD_Client_ID
-- 2022-05-24T07:55:53.868Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583177,102,0,19,542155,129,'AD_Client_ID',TO_TIMESTAMP('2022-05-24 10:55:53','YYYY-MM-DD HH24:MI:SS'),100,'N','@AD_Client_ID@','Mandant für diese Installation.','D',0,22,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Mandant',0,0,TO_TIMESTAMP('2022-05-24 10:55:53','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-05-24T07:55:53.871Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583177 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-24T07:55:53.875Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: S_Resource_Group_Assignment.AD_Org_ID
-- 2022-05-24T07:55:54.565Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583178,113,0,30,542155,104,'AD_Org_ID',TO_TIMESTAMP('2022-05-24 10:55:54','YYYY-MM-DD HH24:MI:SS'),100,'N','@AD_Org_ID@','Organisatorische Einheit des Mandanten','D',0,22,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','N','N','N','Organisation',10,0,TO_TIMESTAMP('2022-05-24 10:55:54','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-05-24T07:55:54.566Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583178 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-24T07:55:54.567Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: S_Resource_Group_Assignment.AssignDateFrom
-- 2022-05-24T07:55:55.024Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583179,1754,0,16,542155,'AssignDateFrom',TO_TIMESTAMP('2022-05-24 10:55:54','YYYY-MM-DD HH24:MI:SS'),100,'N','Ressource zuordnen ab','D',0,7,'Beginn Zuordnung','Y','Y','N','N','N','N','N','Y','N','Y','N','N','N','N','N','N','N','Zuordnung von',0,2,TO_TIMESTAMP('2022-05-24 10:55:54','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-05-24T07:55:55.025Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583179 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-24T07:55:55.026Z
/* DDL */  select update_Column_Translation_From_AD_Element(1754) 
;

-- Column: S_Resource_Group_Assignment.AssignDateTo
-- 2022-05-24T07:55:55.443Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583180,1755,0,16,542155,'AssignDateTo',TO_TIMESTAMP('2022-05-24 10:55:55','YYYY-MM-DD HH24:MI:SS'),100,'N','Ressource zuordnen bis','D',0,7,'Zuordnung endet','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Zuordnung bis',0,0,TO_TIMESTAMP('2022-05-24 10:55:55','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-05-24T07:55:55.444Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583180 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-24T07:55:55.446Z
/* DDL */  select update_Column_Translation_From_AD_Element(1755) 
;

-- Column: S_Resource_Group_Assignment.Created
-- 2022-05-24T07:55:55.852Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583181,245,0,16,542155,'Created',TO_TIMESTAMP('2022-05-24 10:55:55','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,7,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Erstellt',0,0,TO_TIMESTAMP('2022-05-24 10:55:55','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-05-24T07:55:55.853Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583181 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-24T07:55:55.856Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: S_Resource_Group_Assignment.CreatedBy
-- 2022-05-24T07:55:56.315Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583182,246,0,18,110,542155,'CreatedBy',TO_TIMESTAMP('2022-05-24 10:55:56','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,22,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2022-05-24 10:55:56','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-05-24T07:55:56.317Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583182 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-24T07:55:56.319Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: S_Resource_Group_Assignment.Description
-- 2022-05-24T07:55:56.768Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583183,275,0,10,542155,'Description',TO_TIMESTAMP('2022-05-24 10:55:56','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Beschreibung',0,0,TO_TIMESTAMP('2022-05-24 10:55:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-24T07:55:56.770Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583183 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-24T07:55:56.772Z
/* DDL */  select update_Column_Translation_From_AD_Element(275) 
;

-- Column: S_Resource_Group_Assignment.IsActive
-- 2022-05-24T07:55:57.185Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583184,348,0,20,542155,'IsActive',TO_TIMESTAMP('2022-05-24 10:55:57','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Aktiv',0,0,TO_TIMESTAMP('2022-05-24 10:55:57','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-05-24T07:55:57.187Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583184 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-24T07:55:57.189Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: S_Resource_Group_Assignment.IsAllDay
-- 2022-05-24T07:55:57.634Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583185,580861,0,20,542155,'IsAllDay',TO_TIMESTAMP('2022-05-24 10:55:57','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','All day',0,0,TO_TIMESTAMP('2022-05-24 10:55:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-24T07:55:57.635Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583185 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-24T07:55:57.637Z
/* DDL */  select update_Column_Translation_From_AD_Element(580861) 
;

-- Column: S_Resource_Group_Assignment.IsConfirmed
-- 2022-05-24T07:55:58.006Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583186,1763,0,20,542155,'IsConfirmed',TO_TIMESTAMP('2022-05-24 10:55:57','YYYY-MM-DD HH24:MI:SS'),100,'N','Zuordnung ist bestätigt','D',0,1,'Zuordnung der Ressource ist bestätigt','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','bestätigt',0,0,TO_TIMESTAMP('2022-05-24 10:55:57','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-05-24T07:55:58.007Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583186 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-24T07:55:58.009Z
/* DDL */  select update_Column_Translation_From_AD_Element(1763) 
;

-- Column: S_Resource_Group_Assignment.Name
-- 2022-05-24T07:55:58.399Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583187,469,0,10,542155,'Name',TO_TIMESTAMP('2022-05-24 10:55:58','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,60,'E','','Y','Y','N','N','N','N','N','Y','N','Y','N','Y','N','N','N','N','Y','Name',10,3,TO_TIMESTAMP('2022-05-24 10:55:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-24T07:55:58.401Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583187 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-24T07:55:58.403Z
/* DDL */  select update_Column_Translation_From_AD_Element(469) 
;

-- Column: S_Resource_Group_Assignment.Qty
-- 2022-05-24T07:55:58.815Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583188,526,0,29,542155,'Qty',TO_TIMESTAMP('2022-05-24 10:55:58','YYYY-MM-DD HH24:MI:SS'),100,'N','Menge','D',0,22,'Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Menge',0,0,TO_TIMESTAMP('2022-05-24 10:55:58','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-05-24T07:55:58.816Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583188 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-24T07:55:58.817Z
/* DDL */  select update_Column_Translation_From_AD_Element(526) 
;

-- Column: S_Resource_Group_Assignment.S_Resource_ID
-- 2022-05-24T07:55:59.226Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583189,1777,0,19,542155,'S_Resource_ID',TO_TIMESTAMP('2022-05-24 10:55:59','YYYY-MM-DD HH24:MI:SS'),100,'N','Ressource','D',0,22,'Y','Y','N','N','N','N','N','Y','N','Y','Y','N','N','N','N','N','N','Ressource',0,1,TO_TIMESTAMP('2022-05-24 10:55:59','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-05-24T07:55:59.227Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583189 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-24T07:55:59.229Z
/* DDL */  select update_Column_Translation_From_AD_Element(1777) 
;

-- 2022-05-24T07:55:59.602Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580935,0,'S_Resource_Group_Assignment_ID',TO_TIMESTAMP('2022-05-24 10:55:59','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Resource Group Assignment','Resource Group Assignment',TO_TIMESTAMP('2022-05-24 10:55:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-24T07:55:59.605Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580935 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: S_Resource_Group_Assignment.S_Resource_Group_Assignment_ID
-- 2022-05-24T07:55:59.962Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583190,580935,0,13,542155,'S_Resource_Group_Assignment_ID',TO_TIMESTAMP('2022-05-24 10:55:59','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,22,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','Resource Group Assignment',0,0,TO_TIMESTAMP('2022-05-24 10:55:59','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-05-24T07:55:59.964Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583190 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-24T07:55:59.966Z
/* DDL */  select update_Column_Translation_From_AD_Element(580935) 
;

-- Column: S_Resource_Group_Assignment.Updated
-- 2022-05-24T07:56:00.400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583191,607,0,16,542155,'Updated',TO_TIMESTAMP('2022-05-24 10:56:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,7,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2022-05-24 10:56:00','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-05-24T07:56:00.402Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583191 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-24T07:56:00.404Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: S_Resource_Group_Assignment.UpdatedBy
-- 2022-05-24T07:56:00.855Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583192,608,0,18,110,542155,'UpdatedBy',TO_TIMESTAMP('2022-05-24 10:56:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,22,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2022-05-24 10:56:00','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-05-24T07:56:00.857Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583192 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-24T07:56:00.859Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- Table: S_Resource_Group_Assignment
-- 2022-05-24T07:56:04.875Z
UPDATE AD_Table SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2022-05-24 10:56:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542155
;

-- Column: S_Resource_Group_Assignment.Qty
-- 2022-05-24T07:56:14.473Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=583188
;

-- 2022-05-24T07:56:14.481Z
DELETE FROM AD_Column WHERE AD_Column_ID=583188
;

-- Column: S_Resource_Group_Assignment.AssignDateTo
-- 2022-05-24T07:56:24.786Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2022-05-24 10:56:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583180
;

-- Column: S_Resource_Group_Assignment.IsConfirmed
-- 2022-05-24T07:56:51.383Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=583186
;

-- 2022-05-24T07:56:51.390Z
DELETE FROM AD_Column WHERE AD_Column_ID=583186
;

-- Column: S_Resource_Group_Assignment.S_Resource_Group_ID
-- 2022-05-24T07:57:47.835Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,583193,580932,0,30,542155,'S_Resource_Group_ID',TO_TIMESTAMP('2022-05-24 10:57:47','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N',0,'Resource Group',0,0,TO_TIMESTAMP('2022-05-24 10:57:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-05-24T07:57:47.838Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=583193 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-05-24T07:57:47.842Z
/* DDL */  select update_Column_Translation_From_AD_Element(580932) 
;

-- Column: S_Resource_Group_Assignment.S_Resource_Group_ID
-- 2022-05-24T07:57:54.745Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-05-24 10:57:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583193
;

-- Column: S_Resource_Group_Assignment.S_Resource_ID
-- 2022-05-24T07:58:04.297Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=583189
;

-- 2022-05-24T07:58:04.298Z
DELETE FROM AD_Column WHERE AD_Column_ID=583189
;

-- Column: S_Resource_Group_Assignment.S_Resource_Group_ID
-- 2022-05-24T07:58:11.742Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=1,Updated=TO_TIMESTAMP('2022-05-24 10:58:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583193
;

-- Column: S_Resource_Group_Assignment.Name
-- 2022-05-24T08:00:10.225Z
UPDATE AD_Column SET SeqNo=4,Updated=TO_TIMESTAMP('2022-05-24 11:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583187
;

-- Column: S_Resource_Group_Assignment.AssignDateTo
-- 2022-05-24T08:00:25.431Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=3,Updated=TO_TIMESTAMP('2022-05-24 11:00:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583180
;

-- Column: S_Resource_Group_Assignment.AssignDateFrom
-- 2022-05-24T08:00:44.552Z
UPDATE AD_Column SET FilterOperator='B', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-05-24 11:00:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583179
;

-- Column: S_Resource_Group_Assignment.AssignDateTo
-- 2022-05-24T08:00:54.036Z
UPDATE AD_Column SET FilterOperator='B', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-05-24 11:00:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583180
;

-- Column: S_Resource_Group_Assignment.IsAllDay
-- 2022-05-24T08:01:10.954Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-05-24 11:01:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583185
;

-- Column: S_Resource_Group_Assignment.S_Resource_Group_Assignment_ID
-- 2022-05-24T08:01:21.401Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2022-05-24 11:01:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583190
;

-- Column: S_Resource_Group_Assignment.S_Resource_Group_Assignment_ID
-- 2022-05-24T08:01:27.777Z
UPDATE AD_Column SET IsSelectionColumn='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2022-05-24 11:01:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583190
;


-- Table: S_Resource_Group_Assignment
-- 2022-05-24T08:02:55.471Z
UPDATE AD_Table SET AD_Window_ID=541501,Updated=TO_TIMESTAMP('2022-05-24 11:02:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542155
;

-- Tab: Resource Group -> Resource Group Assignment
-- Table: S_Resource_Group_Assignment
-- 2022-05-24T08:03:08.389Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,580935,0,546256,542155,541501,'Y',TO_TIMESTAMP('2022-05-24 11:03:08','YYYY-MM-DD HH24:MI:SS'),100,'U','N','N','A','S_Resource_Group_Assignment','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Resource Group Assignment','N',20,0,TO_TIMESTAMP('2022-05-24 11:03:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-24T08:03:08.390Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546256 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-05-24T08:03:08.394Z
/* DDL */  select update_tab_translation_from_ad_element(580935) 
;

-- 2022-05-24T08:03:08.398Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546256)
;

-- Tab: Resource Group -> Resource Group Assignment
-- Table: S_Resource_Group_Assignment
-- 2022-05-24T08:03:10.971Z
UPDATE AD_Tab SET EntityType='D',Updated=TO_TIMESTAMP('2022-05-24 11:03:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546256
;

-- Tab: Resource Group -> Resource Group Assignment
-- Table: S_Resource_Group_Assignment
-- 2022-05-24T08:03:38.638Z
UPDATE AD_Tab SET AD_Column_ID=583193, Parent_Column_ID=583170, TabLevel=1,Updated=TO_TIMESTAMP('2022-05-24 11:03:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546256
;

-- Field: Resource Group -> Resource Group Assignment -> Mandant
-- Column: S_Resource_Group_Assignment.AD_Client_ID
-- 2022-05-24T08:03:49.680Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583177,696445,0,546256,TO_TIMESTAMP('2022-05-24 11:03:49','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',22,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-05-24 11:03:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-24T08:03:49.682Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696445 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-24T08:03:49.684Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-05-24T08:03:50.091Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696445
;

-- 2022-05-24T08:03:50.094Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696445)
;

-- Field: Resource Group -> Resource Group Assignment -> Organisation
-- Column: S_Resource_Group_Assignment.AD_Org_ID
-- 2022-05-24T08:03:50.200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583178,696446,0,546256,TO_TIMESTAMP('2022-05-24 11:03:50','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',22,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2022-05-24 11:03:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-24T08:03:50.201Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696446 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-24T08:03:50.201Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-05-24T08:03:50.381Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696446
;

-- 2022-05-24T08:03:50.381Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696446)
;

-- Field: Resource Group -> Resource Group Assignment -> Zuordnung von
-- Column: S_Resource_Group_Assignment.AssignDateFrom
-- 2022-05-24T08:03:50.484Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583179,696447,0,546256,TO_TIMESTAMP('2022-05-24 11:03:50','YYYY-MM-DD HH24:MI:SS'),100,'Ressource zuordnen ab',7,'D','Beginn Zuordnung','Y','N','N','N','N','N','N','N','Zuordnung von',TO_TIMESTAMP('2022-05-24 11:03:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-24T08:03:50.485Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696447 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-24T08:03:50.486Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1754) 
;

-- 2022-05-24T08:03:50.488Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696447
;

-- 2022-05-24T08:03:50.488Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696447)
;

-- Field: Resource Group -> Resource Group Assignment -> Zuordnung bis
-- Column: S_Resource_Group_Assignment.AssignDateTo
-- 2022-05-24T08:03:50.587Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583180,696448,0,546256,TO_TIMESTAMP('2022-05-24 11:03:50','YYYY-MM-DD HH24:MI:SS'),100,'Ressource zuordnen bis',7,'D','Zuordnung endet','Y','N','N','N','N','N','N','N','Zuordnung bis',TO_TIMESTAMP('2022-05-24 11:03:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-24T08:03:50.588Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696448 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-24T08:03:50.588Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1755) 
;

-- 2022-05-24T08:03:50.591Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696448
;

-- 2022-05-24T08:03:50.591Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696448)
;

-- Field: Resource Group -> Resource Group Assignment -> Beschreibung
-- Column: S_Resource_Group_Assignment.Description
-- 2022-05-24T08:03:50.677Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583183,696449,0,546256,TO_TIMESTAMP('2022-05-24 11:03:50','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2022-05-24 11:03:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-24T08:03:50.678Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696449 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-24T08:03:50.679Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2022-05-24T08:03:50.746Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696449
;

-- 2022-05-24T08:03:50.746Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696449)
;

-- Field: Resource Group -> Resource Group Assignment -> Aktiv
-- Column: S_Resource_Group_Assignment.IsActive
-- 2022-05-24T08:03:50.845Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583184,696450,0,546256,TO_TIMESTAMP('2022-05-24 11:03:50','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-05-24 11:03:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-24T08:03:50.845Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696450 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-24T08:03:50.846Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-05-24T08:03:51.215Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696450
;

-- 2022-05-24T08:03:51.216Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696450)
;

-- Field: Resource Group -> Resource Group Assignment -> All day
-- Column: S_Resource_Group_Assignment.IsAllDay
-- 2022-05-24T08:03:51.322Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583185,696451,0,546256,TO_TIMESTAMP('2022-05-24 11:03:51','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','All day',TO_TIMESTAMP('2022-05-24 11:03:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-24T08:03:51.323Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696451 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-24T08:03:51.324Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580861) 
;

-- 2022-05-24T08:03:51.324Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696451
;

-- 2022-05-24T08:03:51.324Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696451)
;

-- Field: Resource Group -> Resource Group Assignment -> Name
-- Column: S_Resource_Group_Assignment.Name
-- 2022-05-24T08:03:51.411Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583187,696452,0,546256,TO_TIMESTAMP('2022-05-24 11:03:51','YYYY-MM-DD HH24:MI:SS'),100,'',60,'D','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2022-05-24 11:03:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-24T08:03:51.411Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696452 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-24T08:03:51.412Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2022-05-24T08:03:51.462Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696452
;

-- 2022-05-24T08:03:51.462Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696452)
;

-- Field: Resource Group -> Resource Group Assignment -> Resource Group Assignment
-- Column: S_Resource_Group_Assignment.S_Resource_Group_Assignment_ID
-- 2022-05-24T08:03:51.565Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583190,696453,0,546256,TO_TIMESTAMP('2022-05-24 11:03:51','YYYY-MM-DD HH24:MI:SS'),100,22,'D','Y','N','N','N','N','N','N','N','Resource Group Assignment',TO_TIMESTAMP('2022-05-24 11:03:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-24T08:03:51.566Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696453 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-24T08:03:51.568Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580935) 
;

-- 2022-05-24T08:03:51.568Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696453
;

-- 2022-05-24T08:03:51.568Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696453)
;

-- Field: Resource Group -> Resource Group Assignment -> Resource Group
-- Column: S_Resource_Group_Assignment.S_Resource_Group_ID
-- 2022-05-24T08:03:51.674Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583193,696454,0,546256,TO_TIMESTAMP('2022-05-24 11:03:51','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Resource Group',TO_TIMESTAMP('2022-05-24 11:03:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-24T08:03:51.674Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696454 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-24T08:03:51.675Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580932) 
;

-- 2022-05-24T08:03:51.676Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696454
;

-- 2022-05-24T08:03:51.676Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696454)
;

-- Field: Resource Group -> Resource Group Assignment -> Zuordnung von
-- Column: S_Resource_Group_Assignment.AssignDateFrom
-- 2022-05-24T08:04:25.273Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2022-05-24 11:04:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=696447
;

-- Field: Resource Group -> Resource Group Assignment -> Zuordnung bis
-- Column: S_Resource_Group_Assignment.AssignDateTo
-- 2022-05-24T08:04:25.275Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2022-05-24 11:04:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=696448
;

-- Field: Resource Group -> Resource Group Assignment -> All day
-- Column: S_Resource_Group_Assignment.IsAllDay
-- 2022-05-24T08:04:25.277Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2022-05-24 11:04:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=696451
;

-- Field: Resource Group -> Resource Group Assignment -> Name
-- Column: S_Resource_Group_Assignment.Name
-- 2022-05-24T08:04:25.280Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2022-05-24 11:04:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=696452
;

-- Field: Resource Group -> Resource Group Assignment -> Beschreibung
-- Column: S_Resource_Group_Assignment.Description
-- 2022-05-24T08:04:25.282Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=50,Updated=TO_TIMESTAMP('2022-05-24 11:04:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=696449
;

-- 2022-05-24T08:04:57.215Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546255,544899,TO_TIMESTAMP('2022-05-24 11:04:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-05-24 11:04:57','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-05-24T08:04:57.217Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=544899 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-05-24T08:04:57.347Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,545920,544899,TO_TIMESTAMP('2022-05-24 11:04:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-05-24 11:04:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-24T08:04:57.449Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,545921,544899,TO_TIMESTAMP('2022-05-24 11:04:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-05-24 11:04:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-24T08:04:57.588Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,545920,549094,TO_TIMESTAMP('2022-05-24 11:04:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-05-24 11:04:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-24T08:04:57.688Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546256,544900,TO_TIMESTAMP('2022-05-24 11:04:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-05-24 11:04:57','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-05-24T08:04:57.689Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=544900 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-05-24T08:04:57.774Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,545922,544900,TO_TIMESTAMP('2022-05-24 11:04:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-05-24 11:04:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-24T08:04:57.870Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,545922,549095,TO_TIMESTAMP('2022-05-24 11:04:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-05-24 11:04:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Resource Group -> Resource Group -> Mandant
-- Column: S_Resource_Group.AD_Client_ID
-- 2022-05-24T08:06:40.843Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583163,696455,0,546255,TO_TIMESTAMP('2022-05-24 11:06:40','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-05-24 11:06:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-24T08:06:40.844Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696455 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-24T08:06:40.846Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-05-24T08:06:40.944Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696455
;

-- 2022-05-24T08:06:40.945Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696455)
;

-- Field: Resource Group -> Resource Group -> Organisation
-- Column: S_Resource_Group.AD_Org_ID
-- 2022-05-24T08:06:41.039Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583164,696456,0,546255,TO_TIMESTAMP('2022-05-24 11:06:40','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2022-05-24 11:06:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-24T08:06:41.039Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696456 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-24T08:06:41.040Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-05-24T08:06:41.117Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696456
;

-- 2022-05-24T08:06:41.118Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696456)
;

-- Field: Resource Group -> Resource Group -> Aktiv
-- Column: S_Resource_Group.IsActive
-- 2022-05-24T08:06:41.219Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583167,696457,0,546255,TO_TIMESTAMP('2022-05-24 11:06:41','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-05-24 11:06:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-24T08:06:41.220Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696457 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-24T08:06:41.220Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-05-24T08:06:41.343Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696457
;

-- 2022-05-24T08:06:41.343Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696457)
;

-- Field: Resource Group -> Resource Group -> Resource Group
-- Column: S_Resource_Group.S_Resource_Group_ID
-- 2022-05-24T08:06:41.450Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583170,696458,0,546255,TO_TIMESTAMP('2022-05-24 11:06:41','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Resource Group',TO_TIMESTAMP('2022-05-24 11:06:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-24T08:06:41.451Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696458 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-24T08:06:41.452Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580932) 
;

-- 2022-05-24T08:06:41.454Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696458
;

-- 2022-05-24T08:06:41.454Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696458)
;

-- Field: Resource Group -> Resource Group -> Name
-- Column: S_Resource_Group.Name
-- 2022-05-24T08:06:41.544Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583172,696459,0,546255,TO_TIMESTAMP('2022-05-24 11:06:41','YYYY-MM-DD HH24:MI:SS'),100,'',255,'D','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2022-05-24 11:06:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-24T08:06:41.544Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696459 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-24T08:06:41.545Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2022-05-24T08:06:41.580Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696459
;

-- 2022-05-24T08:06:41.581Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696459)
;

-- Field: Resource Group -> Resource Group -> Beschreibung
-- Column: S_Resource_Group.Description
-- 2022-05-24T08:06:41.672Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583173,696460,0,546255,TO_TIMESTAMP('2022-05-24 11:06:41','YYYY-MM-DD HH24:MI:SS'),100,2000,'D','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2022-05-24 11:06:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-24T08:06:41.673Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=696460 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-24T08:06:41.674Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2022-05-24T08:06:41.716Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=696460
;

-- 2022-05-24T08:06:41.716Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(696460)
;

-- 2022-05-24T08:07:23.368Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=549094
;

-- 2022-05-24T08:07:32.982Z
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=545920
;

-- 2022-05-24T08:07:32.997Z
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=545921
;

-- 2022-05-24T08:07:37.806Z
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=544899
;

-- 2022-05-24T08:07:37.824Z
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=544899
;

-- 2022-05-24T08:07:55.647Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=549095
;

-- 2022-05-24T08:07:55.648Z
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=545922
;

-- 2022-05-24T08:07:55.649Z
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=544900
;

-- 2022-05-24T08:07:55.650Z
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=544900
;

-- Field: Resource Group -> Resource Group -> Name
-- Column: S_Resource_Group.Name
-- 2022-05-24T08:08:29.582Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2022-05-24 11:08:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=696459
;

-- Field: Resource Group -> Resource Group -> Beschreibung
-- Column: S_Resource_Group.Description
-- 2022-05-24T08:08:29.584Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2022-05-24 11:08:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=696460
;

-- Field: Resource Group -> Resource Group -> Aktiv
-- Column: S_Resource_Group.IsActive
-- 2022-05-24T08:08:29.586Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2022-05-24 11:08:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=696457
;

-- Field: Resource Group -> Resource Group -> Organisation
-- Column: S_Resource_Group.AD_Org_ID
-- 2022-05-24T08:08:37.926Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2022-05-24 11:08:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=696456
;

-- Field: Resource Group -> Resource Group -> Mandant
-- Column: S_Resource_Group.AD_Client_ID
-- 2022-05-24T08:08:45.743Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-05-24 11:08:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=696455
;

-- Field: Resource Group -> Resource Group -> Organisation
-- Column: S_Resource_Group.AD_Org_ID
-- 2022-05-24T08:08:45.745Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-05-24 11:08:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=696456
;

-- Field: Resource Group -> Resource Group -> Aktiv
-- Column: S_Resource_Group.IsActive
-- 2022-05-24T08:08:45.746Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-05-24 11:08:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=696457
;

-- Field: Resource Group -> Resource Group -> Resource Group
-- Column: S_Resource_Group.S_Resource_Group_ID
-- 2022-05-24T08:08:45.747Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-05-24 11:08:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=696458
;

-- Field: Resource Group -> Resource Group -> Name
-- Column: S_Resource_Group.Name
-- 2022-05-24T08:08:45.748Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-05-24 11:08:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=696459
;

-- Field: Resource Group -> Resource Group -> Beschreibung
-- Column: S_Resource_Group.Description
-- 2022-05-24T08:08:45.749Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-05-24 11:08:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=696460
;

-- Field: Resource Group -> Resource Group Assignment -> Mandant
-- Column: S_Resource_Group_Assignment.AD_Client_ID
-- 2022-05-24T08:09:01.839Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-05-24 11:09:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=696445
;

-- Field: Resource Group -> Resource Group Assignment -> Organisation
-- Column: S_Resource_Group_Assignment.AD_Org_ID
-- 2022-05-24T08:09:01.842Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-05-24 11:09:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=696446
;

-- Field: Resource Group -> Resource Group Assignment -> Zuordnung von
-- Column: S_Resource_Group_Assignment.AssignDateFrom
-- 2022-05-24T08:09:01.843Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-05-24 11:09:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=696447
;

-- Field: Resource Group -> Resource Group Assignment -> Zuordnung bis
-- Column: S_Resource_Group_Assignment.AssignDateTo
-- 2022-05-24T08:09:01.845Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-05-24 11:09:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=696448
;

-- Field: Resource Group -> Resource Group Assignment -> Beschreibung
-- Column: S_Resource_Group_Assignment.Description
-- 2022-05-24T08:09:01.846Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-05-24 11:09:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=696449
;

-- Field: Resource Group -> Resource Group Assignment -> Aktiv
-- Column: S_Resource_Group_Assignment.IsActive
-- 2022-05-24T08:09:01.847Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-05-24 11:09:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=696450
;

-- Field: Resource Group -> Resource Group Assignment -> All day
-- Column: S_Resource_Group_Assignment.IsAllDay
-- 2022-05-24T08:09:01.849Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-05-24 11:09:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=696451
;

-- Field: Resource Group -> Resource Group Assignment -> Name
-- Column: S_Resource_Group_Assignment.Name
-- 2022-05-24T08:09:01.850Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-05-24 11:09:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=696452
;

-- Field: Resource Group -> Resource Group Assignment -> Resource Group Assignment
-- Column: S_Resource_Group_Assignment.S_Resource_Group_Assignment_ID
-- 2022-05-24T08:09:01.851Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-05-24 11:09:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=696453
;

-- Field: Resource Group -> Resource Group Assignment -> Resource Group
-- Column: S_Resource_Group_Assignment.S_Resource_Group_ID
-- 2022-05-24T08:09:01.853Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2022-05-24 11:09:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=696454
;

-- 2022-05-24T08:09:19.912Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546255,544901,TO_TIMESTAMP('2022-05-24 11:09:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-05-24 11:09:19','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-05-24T08:09:19.912Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=544901 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-05-24T08:09:20.003Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,545923,544901,TO_TIMESTAMP('2022-05-24 11:09:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-05-24 11:09:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-24T08:09:20.092Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,545924,544901,TO_TIMESTAMP('2022-05-24 11:09:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-05-24 11:09:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-24T08:09:20.205Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,545923,549096,TO_TIMESTAMP('2022-05-24 11:09:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-05-24 11:09:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Resource Group -> Resource Group.Name
-- Column: S_Resource_Group.Name
-- 2022-05-24T08:09:20.307Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,696459,0,546255,607597,549096,'F',TO_TIMESTAMP('2022-05-24 11:09:20','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','Y','Name',10,0,10,TO_TIMESTAMP('2022-05-24 11:09:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Resource Group -> Resource Group.Beschreibung
-- Column: S_Resource_Group.Description
-- 2022-05-24T08:09:20.405Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,696460,0,546255,607598,549096,'F',TO_TIMESTAMP('2022-05-24 11:09:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','Y','Beschreibung',20,0,20,TO_TIMESTAMP('2022-05-24 11:09:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Resource Group -> Resource Group.Aktiv
-- Column: S_Resource_Group.IsActive
-- 2022-05-24T08:09:20.496Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,696457,0,546255,607599,549096,'F',TO_TIMESTAMP('2022-05-24 11:09:20','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','Y','Aktiv',30,0,30,TO_TIMESTAMP('2022-05-24 11:09:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Resource Group -> Resource Group.Organisation
-- Column: S_Resource_Group.AD_Org_ID
-- 2022-05-24T08:09:20.596Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,696456,0,546255,607600,549096,'F',TO_TIMESTAMP('2022-05-24 11:09:20','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','Y','Organisation',40,0,40,TO_TIMESTAMP('2022-05-24 11:09:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-24T08:09:20.701Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546256,544902,TO_TIMESTAMP('2022-05-24 11:09:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-05-24 11:09:20','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-05-24T08:09:20.702Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=544902 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-05-24T08:09:20.800Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,545925,544902,TO_TIMESTAMP('2022-05-24 11:09:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-05-24 11:09:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-24T08:09:20.892Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,545925,549097,TO_TIMESTAMP('2022-05-24 11:09:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-05-24 11:09:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Resource Group -> Resource Group Assignment.Zuordnung von
-- Column: S_Resource_Group_Assignment.AssignDateFrom
-- 2022-05-24T08:09:20.985Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,696447,0,546256,607601,549097,'F',TO_TIMESTAMP('2022-05-24 11:09:20','YYYY-MM-DD HH24:MI:SS'),100,'Ressource zuordnen ab','Beginn Zuordnung','Y','N','N','N','Y','Zuordnung von',0,0,10,TO_TIMESTAMP('2022-05-24 11:09:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Resource Group -> Resource Group Assignment.Zuordnung bis
-- Column: S_Resource_Group_Assignment.AssignDateTo
-- 2022-05-24T08:09:21.081Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,696448,0,546256,607602,549097,'F',TO_TIMESTAMP('2022-05-24 11:09:20','YYYY-MM-DD HH24:MI:SS'),100,'Ressource zuordnen bis','Zuordnung endet','Y','N','N','N','Y','Zuordnung bis',0,0,20,TO_TIMESTAMP('2022-05-24 11:09:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Resource Group -> Resource Group Assignment.All day
-- Column: S_Resource_Group_Assignment.IsAllDay
-- 2022-05-24T08:09:21.175Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,696451,0,546256,607603,549097,'F',TO_TIMESTAMP('2022-05-24 11:09:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','All day',0,0,30,TO_TIMESTAMP('2022-05-24 11:09:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Resource Group -> Resource Group Assignment.Name
-- Column: S_Resource_Group_Assignment.Name
-- 2022-05-24T08:09:21.278Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,696452,0,546256,607604,549097,'F',TO_TIMESTAMP('2022-05-24 11:09:21','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','N','N','Y','Name',0,0,40,TO_TIMESTAMP('2022-05-24 11:09:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Resource Group -> Resource Group Assignment.Beschreibung
-- Column: S_Resource_Group_Assignment.Description
-- 2022-05-24T08:09:21.375Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,696449,0,546256,607605,549097,'F',TO_TIMESTAMP('2022-05-24 11:09:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','Beschreibung',0,0,50,TO_TIMESTAMP('2022-05-24 11:09:21','YYYY-MM-DD HH24:MI:SS'),100)
;

