-- Table: AD_UserDefaults_Attribute3
-- 2023-05-23T09:19:59.606Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('7',0,0,0,542336,'N',TO_TIMESTAMP('2023-05-23 12:19:59','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'User Defaults - Attribute 3','NP','L','AD_UserDefaults_Attribute3','DTI',TO_TIMESTAMP('2023-05-23 12:19:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-23T09:19:59.610Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542336 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-05-23T09:19:59.761Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556270,TO_TIMESTAMP('2023-05-23 12:19:59','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table AD_UserDefaults_Attribute3',1,'Y','N','Y','Y','AD_UserDefaults_Attribute3','N',1000000,TO_TIMESTAMP('2023-05-23 12:19:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-23T09:19:59.775Z
CREATE SEQUENCE AD_USERDEFAULTS_ATTRIBUTE3_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: AD_UserDefaults_Attribute3.AD_Client_ID
-- 2023-05-23T09:20:49.878Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586688,102,0,19,542336,'AD_Client_ID',TO_TIMESTAMP('2023-05-23 12:20:49','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2023-05-23 12:20:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-05-23T09:20:49.880Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586688 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-23T09:20:49.885Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: AD_UserDefaults_Attribute3.AD_Org_ID
-- 2023-05-23T09:20:51.465Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586689,113,0,30,542336,'AD_Org_ID',TO_TIMESTAMP('2023-05-23 12:20:51','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2023-05-23 12:20:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-05-23T09:20:51.468Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586689 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-23T09:20:51.471Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: AD_UserDefaults_Attribute3.Created
-- 2023-05-23T09:20:52.103Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586690,245,0,16,542336,'Created',TO_TIMESTAMP('2023-05-23 12:20:52','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2023-05-23 12:20:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-05-23T09:20:52.106Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586690 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-23T09:20:52.110Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: AD_UserDefaults_Attribute3.CreatedBy
-- 2023-05-23T09:20:52.644Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586691,246,0,18,110,542336,'CreatedBy',TO_TIMESTAMP('2023-05-23 12:20:52','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2023-05-23 12:20:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-05-23T09:20:52.646Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586691 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-23T09:20:52.652Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: AD_UserDefaults_Attribute3.IsActive
-- 2023-05-23T09:20:53.177Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586692,348,0,20,542336,'IsActive',TO_TIMESTAMP('2023-05-23 12:20:53','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2023-05-23 12:20:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-05-23T09:20:53.179Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586692 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-23T09:20:53.182Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: AD_UserDefaults_Attribute3.Updated
-- 2023-05-23T09:20:53.719Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586693,607,0,16,542336,'Updated',TO_TIMESTAMP('2023-05-23 12:20:53','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2023-05-23 12:20:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-05-23T09:20:53.722Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586693 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-23T09:20:53.726Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: AD_UserDefaults_Attribute3.UpdatedBy
-- 2023-05-23T09:20:54.281Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586694,608,0,18,110,542336,'UpdatedBy',TO_TIMESTAMP('2023-05-23 12:20:54','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2023-05-23 12:20:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-05-23T09:20:54.283Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586694 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-23T09:20:54.286Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2023-05-23T09:20:54.828Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582362,0,'AD_UserDefaults_Attribute3_ID',TO_TIMESTAMP('2023-05-23 12:20:54','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','User Defaults - Attribute 3','User Defaults - Attribute 3',TO_TIMESTAMP('2023-05-23 12:20:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-23T09:20:54.831Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582362 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_UserDefaults_Attribute3.AD_UserDefaults_Attribute3_ID
-- 2023-05-23T09:20:55.263Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586695,582362,0,13,542336,'AD_UserDefaults_Attribute3_ID',TO_TIMESTAMP('2023-05-23 12:20:54','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','User Defaults - Attribute 3',0,0,TO_TIMESTAMP('2023-05-23 12:20:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-05-23T09:20:55.264Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586695 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-23T09:20:55.267Z
/* DDL */  select update_Column_Translation_From_AD_Element(582362) 
;

-- Column: AD_UserDefaults_Attribute3.AD_User_ID
-- 2023-05-23T09:21:30.871Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586696,138,0,19,542336,'AD_User_ID',TO_TIMESTAMP('2023-05-23 12:21:30','YYYY-MM-DD HH24:MI:SS'),100,'N','User within the system - Internal or Business Partner Contact','D',0,10,'The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'Ansprechpartner',0,0,TO_TIMESTAMP('2023-05-23 12:21:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-05-23T09:21:30.873Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586696 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-23T09:21:30.875Z
/* DDL */  select update_Column_Translation_From_AD_Element(138) 
;

-- 2023-05-23T09:21:31.547Z
/* DDL */ CREATE TABLE public.AD_UserDefaults_Attribute3 (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, AD_UserDefaults_Attribute3_ID NUMERIC(10) NOT NULL, AD_User_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT AD_UserDefaults_Attribute3_Key PRIMARY KEY (AD_UserDefaults_Attribute3_ID), CONSTRAINT ADUser_ADUserDefaultsAttribute3 FOREIGN KEY (AD_User_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED)
;

-- Column: AD_UserDefaults_Attribute3.Attributes3
-- 2023-05-23T09:22:42.928Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586697,579337,0,17,541334,542336,'Attributes3',TO_TIMESTAMP('2023-05-23 12:22:42','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,40,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Merkmal3',0,0,TO_TIMESTAMP('2023-05-23 12:22:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-05-23T09:22:42.930Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586697 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-23T09:22:42.935Z
/* DDL */  select update_Column_Translation_From_AD_Element(579337) 
;

-- 2023-05-23T09:22:47.922Z
/* DDL */ SELECT public.db_alter_table('AD_UserDefaults_Attribute3','ALTER TABLE public.AD_UserDefaults_Attribute3 ADD COLUMN Attributes3 VARCHAR(40) NOT NULL')
;

-- Tab: Mein Profil -> User Defaults - Attribute 3
-- Table: AD_UserDefaults_Attribute3
-- 2023-05-23T09:24:41.992Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,586696,582362,0,546981,542336,53100,'Y',TO_TIMESTAMP('2023-05-23 12:24:41','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','AD_UserDefaults_Attribute3','Y','N','Y','Y','N','N','N','Y','Y','Y','N','N','N','Y','Y','N','N','N',0,'User Defaults - Attribute 3',212,'N',40,1,TO_TIMESTAMP('2023-05-23 12:24:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-23T09:24:41.996Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546981 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-05-23T09:24:42Z
/* DDL */  select update_tab_translation_from_ad_element(582362) 
;

-- 2023-05-23T09:24:42.004Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546981)
;

-- Field: Mein Profil -> User Defaults - Attribute 3 -> Mandant
-- Column: AD_UserDefaults_Attribute3.AD_Client_ID
-- 2023-05-23T09:24:59.182Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586688,715494,0,546981,TO_TIMESTAMP('2023-05-23 12:24:59','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-05-23 12:24:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-23T09:24:59.185Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=715494 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-23T09:24:59.188Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-05-23T09:25:00.369Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715494
;

-- 2023-05-23T09:25:00.370Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715494)
;

-- Field: Mein Profil -> User Defaults - Attribute 3 -> Sektion
-- Column: AD_UserDefaults_Attribute3.AD_Org_ID
-- 2023-05-23T09:25:00.487Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586689,715495,0,546981,TO_TIMESTAMP('2023-05-23 12:25:00','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2023-05-23 12:25:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-23T09:25:00.489Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=715495 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-23T09:25:00.491Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-05-23T09:25:01.019Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715495
;

-- 2023-05-23T09:25:01.020Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715495)
;

-- Field: Mein Profil -> User Defaults - Attribute 3 -> Aktiv
-- Column: AD_UserDefaults_Attribute3.IsActive
-- 2023-05-23T09:25:01.140Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586692,715496,0,546981,TO_TIMESTAMP('2023-05-23 12:25:01','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-05-23 12:25:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-23T09:25:01.142Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=715496 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-23T09:25:01.144Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-05-23T09:25:01.530Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715496
;

-- 2023-05-23T09:25:01.532Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715496)
;

-- Field: Mein Profil -> User Defaults - Attribute 3 -> User Defaults - Attribute 3
-- Column: AD_UserDefaults_Attribute3.AD_UserDefaults_Attribute3_ID
-- 2023-05-23T09:25:01.650Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586695,715497,0,546981,TO_TIMESTAMP('2023-05-23 12:25:01','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','User Defaults - Attribute 3',TO_TIMESTAMP('2023-05-23 12:25:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-23T09:25:01.651Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=715497 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-23T09:25:01.653Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582362) 
;

-- 2023-05-23T09:25:01.657Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715497
;

-- 2023-05-23T09:25:01.658Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715497)
;

-- Field: Mein Profil -> User Defaults - Attribute 3 -> Ansprechpartner
-- Column: AD_UserDefaults_Attribute3.AD_User_ID
-- 2023-05-23T09:25:01.770Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586696,715498,0,546981,TO_TIMESTAMP('2023-05-23 12:25:01','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact',10,'D','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','N','N','N','N','N','Ansprechpartner',TO_TIMESTAMP('2023-05-23 12:25:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-23T09:25:01.772Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=715498 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-23T09:25:01.774Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(138) 
;

-- 2023-05-23T09:25:01.803Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715498
;

-- 2023-05-23T09:25:01.804Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715498)
;

-- Field: Mein Profil -> User Defaults - Attribute 3 -> Merkmal3
-- Column: AD_UserDefaults_Attribute3.Attributes3
-- 2023-05-23T09:25:01.906Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586697,715499,0,546981,TO_TIMESTAMP('2023-05-23 12:25:01','YYYY-MM-DD HH24:MI:SS'),100,40,'D','Y','N','N','N','N','N','N','N','Merkmal3',TO_TIMESTAMP('2023-05-23 12:25:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-23T09:25:01.907Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=715499 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-23T09:25:01.909Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579337) 
;

-- 2023-05-23T09:25:01.920Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715499
;

-- 2023-05-23T09:25:01.921Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715499)
;

-- Field: Mein Profil -> User Defaults - Attribute 3 -> Merkmal3
-- Column: AD_UserDefaults_Attribute3.Attributes3
-- 2023-05-23T09:25:37.597Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2023-05-23 12:25:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=715499
;

-- Field: Mein Profil -> User Defaults - Attribute 3 -> Merkmal3
-- Column: AD_UserDefaults_Attribute3.Attributes3
-- 2023-05-23T09:25:42.538Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-05-23 12:25:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=715499
;

-- UI Element: Mein Profil -> User Contact.Merkmal 3
-- 2023-05-23T09:27:25.700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Labels_Selector_Field_ID,Labels_Tab_ID,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,0,53282,550717,617543,'L',TO_TIMESTAMP('2023-05-23 12:27:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',715499,546981,0,'Merkmal 3',20,0,0,TO_TIMESTAMP('2023-05-23 12:27:25','YYYY-MM-DD HH24:MI:SS'),100)
;

