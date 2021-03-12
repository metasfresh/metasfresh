-- 2020-07-16T15:08:29.572Z
-- URL zum Konzept
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,Description,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,541507,'N',TO_TIMESTAMP('2020-07-16 18:08:27','YYYY-MM-DD HH24:MI:SS'),100,'User''s Title','D','N','Y','N','N','Y','N','N','N','N','N',0,'Titel','NP','L','C_Title','DTI',TO_TIMESTAMP('2020-07-16 18:08:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-07-16T15:08:29.877Z
-- URL zum Konzept
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Table_ID=541507 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2020-07-16T13:34:45.207Z
-- URL zum Konzept
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555143,TO_TIMESTAMP('2020-07-16 16:34:44','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_Title',1,'Y','N','Y','Y','C_Title','N',1000000,TO_TIMESTAMP('2020-07-16 16:34:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-07-16T13:34:45.384Z
-- URL zum Konzept
CREATE SEQUENCE C_TITLE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2020-07-16T15:08:30.166Z
-- URL zum Konzept
ALTER SEQUENCE C_TITLE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 RESTART 1000000
;

-- 2020-07-16T15:09:24.770Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,570982,102,0,19,541507,'AD_Client_ID',TO_TIMESTAMP('2020-07-16 18:09:23','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','Mandant',0,TO_TIMESTAMP('2020-07-16 18:09:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-07-16T15:09:25.077Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570982 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-07-16T15:09:25.142Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- 2020-07-16T15:09:26.849Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,570983,113,0,30,541507,'AD_Org_ID',TO_TIMESTAMP('2020-07-16 18:09:26','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','Y','N','Y','Y','N','N','Organisation',0,TO_TIMESTAMP('2020-07-16 18:09:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-07-16T15:09:27.204Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570983 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-07-16T15:09:27.267Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- 2020-07-16T15:09:28.595Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,570984,245,0,16,541507,'Created',TO_TIMESTAMP('2020-07-16 18:09:27','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt',0,TO_TIMESTAMP('2020-07-16 18:09:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-07-16T15:09:28.887Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570984 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-07-16T15:09:28.944Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- 2020-07-16T15:09:30.389Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,570985,246,0,18,110,541507,'CreatedBy',TO_TIMESTAMP('2020-07-16 18:09:29','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch',0,TO_TIMESTAMP('2020-07-16 18:09:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-07-16T15:09:30.687Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570985 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-07-16T15:09:30.748Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- 2020-07-16T15:09:32.087Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,570986,348,0,20,541507,'IsActive',TO_TIMESTAMP('2020-07-16 18:09:31','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','Y','Aktiv',0,TO_TIMESTAMP('2020-07-16 18:09:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-07-16T15:09:32.385Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570986 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-07-16T15:09:32.443Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- 2020-07-16T15:09:33.785Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,570987,607,0,16,541507,'Updated',TO_TIMESTAMP('2020-07-16 18:09:33','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert',0,TO_TIMESTAMP('2020-07-16 18:09:33','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-07-16T15:09:34.087Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570987 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-07-16T15:09:34.145Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- 2020-07-16T15:09:35.720Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,570988,608,0,18,110,541507,'UpdatedBy',TO_TIMESTAMP('2020-07-16 18:09:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2020-07-16 18:09:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-07-16T15:09:36.014Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570988 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-07-16T15:09:36.074Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2020-07-16T15:09:37.486Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578005,0,'C_Title_ID',TO_TIMESTAMP('2020-07-16 18:09:36','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Titel','Titel',TO_TIMESTAMP('2020-07-16 18:09:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-07-16T15:09:37.783Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578005 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-07-16T15:09:38.593Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,570989,578005,0,13,541507,'C_Title_ID',TO_TIMESTAMP('2020-07-16 18:09:36','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','N','N','N','N','N','Y','Y','N','N','Y','N','N','Titel',0,TO_TIMESTAMP('2020-07-16 18:09:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-07-16T15:09:38.713Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570989 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-07-16T15:09:38.770Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(578005)
;

-- 2020-07-16T15:09:44.563Z
-- URL zum Konzept
/* DDL */ CREATE TABLE public.C_Title (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, C_Title_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT C_Title_Key PRIMARY KEY (C_Title_ID))
;

-- 2020-07-16T15:09:45.307Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2020-07-16T15:09:46.011Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2020-07-16T15:09:46.710Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title','CreatedBy','NUMERIC(10)',null,null)
;

-- 2020-07-16T15:09:47.429Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title','IsActive','CHAR(1)',null,null)
;

-- 2020-07-16T15:09:48.137Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2020-07-16T15:09:48.867Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2020-07-16T15:09:49.563Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title','C_Title_ID','NUMERIC(10)',null,null)
;

-- Backup AD_User table before alterations.
CREATE TABLE backup.BKP_AD_User_16072020 as select * from ad_user;
-- 2020-07-16T15:25:02.301Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,570990,469,0,10,541507,'Name',TO_TIMESTAMP('2020-07-16 18:25:01','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,100,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Name',0,1,TO_TIMESTAMP('2020-07-16 18:25:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-07-16T15:25:02.656Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570990 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-07-16T15:25:02.713Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(469)
;

-- 2020-07-17T07:16:40.999Z
-- URL zum Konzept
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,541508,'N',TO_TIMESTAMP('2020-07-17 10:16:37','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','Y','Y','N','N','N','N','N',0,'Titelübersetzung','NP','L','C_Title_Trl','DTI',TO_TIMESTAMP('2020-07-17 10:16:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-07-17T07:16:41.553Z
-- URL zum Konzept
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Table_ID=541508 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2020-07-17T07:16:42.181Z
-- URL zum Konzept
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555144,TO_TIMESTAMP('2020-07-17 10:16:41','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_Title_Trl',1,'Y','N','Y','Y','C_Title_Trl','N',1000000,TO_TIMESTAMP('2020-07-17 10:16:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-07-17T07:16:42.355Z
-- URL zum Konzept
CREATE SEQUENCE C_TITLE_TRL_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2020-07-17T07:31:25.147Z
-- URL zum Konzept
UPDATE AD_Table SET Description='User''s Title',Updated=TO_TIMESTAMP('2020-07-17 10:31:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541508
;

-- 2020-07-17T07:38:36.754Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,570991,102,0,19,541508,'AD_Client_ID',TO_TIMESTAMP('2020-07-17 10:38:35','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','Mandant',0,TO_TIMESTAMP('2020-07-17 10:38:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-07-17T07:38:37.119Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570991 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-07-17T07:38:37.218Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- 2020-07-17T07:38:38.861Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,570992,113,0,30,541508,'AD_Org_ID',TO_TIMESTAMP('2020-07-17 10:38:38','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','Y','N','Y','Y','N','N','Organisation',0,TO_TIMESTAMP('2020-07-17 10:38:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-07-17T07:38:39.166Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570992 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-07-17T07:38:39.224Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- 2020-07-17T07:38:40.618Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,570993,245,0,16,541508,'Created',TO_TIMESTAMP('2020-07-17 10:38:39','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt',0,TO_TIMESTAMP('2020-07-17 10:38:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-07-17T07:38:40.931Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570993 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-07-17T07:38:40.992Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- 2020-07-17T07:38:42.429Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,570994,246,0,18,110,541508,'CreatedBy',TO_TIMESTAMP('2020-07-17 10:38:41','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch',0,TO_TIMESTAMP('2020-07-17 10:38:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-07-17T07:38:42.740Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570994 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-07-17T07:38:42.800Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- 2020-07-17T07:38:44.261Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,570995,348,0,20,541508,'IsActive',TO_TIMESTAMP('2020-07-17 10:38:43','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','Y','Aktiv',0,TO_TIMESTAMP('2020-07-17 10:38:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-07-17T07:38:44.569Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570995 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-07-17T07:38:44.625Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- 2020-07-17T07:38:46.071Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,570996,607,0,16,541508,'Updated',TO_TIMESTAMP('2020-07-17 10:38:45','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert',0,TO_TIMESTAMP('2020-07-17 10:38:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-07-17T07:38:46.363Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570996 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-07-17T07:38:46.426Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- 2020-07-17T07:38:47.984Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,570997,608,0,18,110,541508,'UpdatedBy',TO_TIMESTAMP('2020-07-17 10:38:47','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2020-07-17 10:38:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-07-17T07:38:48.289Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570997 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-07-17T07:38:48.348Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2020-07-17T07:38:49.750Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578006,0,'C_Title_Trl_ID',TO_TIMESTAMP('2020-07-17 10:38:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Titelübersetzung','Titelübersetzung',TO_TIMESTAMP('2020-07-17 10:38:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-07-17T07:38:50.046Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578006 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-07-17T07:38:50.827Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,570998,578006,0,13,541508,'C_Title_Trl_ID',TO_TIMESTAMP('2020-07-17 10:38:49','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','N','N','N','N','N','Y','Y','N','N','Y','N','N','Titelübersetzung',0,TO_TIMESTAMP('2020-07-17 10:38:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-07-17T07:38:50.963Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570998 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-07-17T07:38:51.021Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(578006)
;

-- 2020-07-17T07:38:56.889Z
-- URL zum Konzept
/* DDL */ CREATE TABLE public.C_Title_Trl (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, C_Title_Trl_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT C_Title_Trl_Key PRIMARY KEY (C_Title_Trl_ID))
;

-- 2020-07-17T07:38:57.636Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title_trl','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2020-07-17T07:38:58.331Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title_trl','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2020-07-17T07:38:59.025Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title_trl','CreatedBy','NUMERIC(10)',null,null)
;

-- 2020-07-17T07:38:59.735Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title_trl','IsActive','CHAR(1)',null,null)
;

-- 2020-07-17T07:39:00.424Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title_trl','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2020-07-17T07:39:01.114Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title_trl','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2020-07-17T07:39:01.824Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title_trl','C_Title_Trl_ID','NUMERIC(10)',null,null)
;

-- 2020-07-17T07:49:29.673Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,570999,109,0,18,106,541508,'AD_Language',TO_TIMESTAMP('2020-07-17 10:49:28','YYYY-MM-DD HH24:MI:SS'),100,'N','Sprache für diesen Eintrag','D',0,10,'Definiert die Sprache für Anzeige und Aufbereitung','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N',0,'Sprache',0,0,TO_TIMESTAMP('2020-07-17 10:49:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-07-17T07:49:30.028Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=570999 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-07-17T07:49:30.085Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(109)
;

-- 2020-07-17T07:50:27.171Z
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=6, IsUpdateable='N',Updated=TO_TIMESTAMP('2020-07-17 10:50:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570999
;

-- 2020-07-17T07:50:43.777Z
-- URL zum Konzept
UPDATE AD_Column SET IsUpdateable='N', Version=1.000000000000,Updated=TO_TIMESTAMP('2020-07-17 10:50:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570999
;

-- 2020-07-17T08:04:47.251Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541163,TO_TIMESTAMP('2020-07-17 11:04:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Title',TO_TIMESTAMP('2020-07-17 11:04:46','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2020-07-17T08:04:47.602Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541163 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2020-07-17T08:05:41.507Z
-- URL zum Konzept
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,570990,570989,0,541163,541507,TO_TIMESTAMP('2020-07-17 11:05:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2020-07-17 11:05:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-07-17T08:06:05.664Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,571000,578005,0,30,541508,'C_Title_ID',TO_TIMESTAMP('2020-07-17 11:06:04','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,22,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N',0,'Titel',0,0,TO_TIMESTAMP('2020-07-17 11:06:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2020-07-17T08:06:05.866Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=571000 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-07-17T08:06:05.931Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(578005)
;

-- 2020-07-17T08:06:24.678Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_Value_ID=541163, IsUpdateable='N',Updated=TO_TIMESTAMP('2020-07-17 11:06:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=571000
;

-- 2020-07-17T08:06:59.804Z
-- URL zum Konzept
UPDATE AD_Column SET IsUpdateable='N', Version=1.000000000000,Updated=TO_TIMESTAMP('2020-07-17 11:06:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=571000
;

-- 2020-07-17T08:08:57.001Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,571001,469,0,10,541508,'Name',TO_TIMESTAMP('2020-07-17 11:08:56','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,100,'','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','Y','Y','N','Y','N','N','N','N','N','N',0,'Name',0,1,TO_TIMESTAMP('2020-07-17 11:08:56','YYYY-MM-DD HH24:MI:SS'),100,1.000000000000)
;

-- 2020-07-17T08:08:57.299Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=571001 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-07-17T08:08:57.358Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(469)
;

-- 2020-07-17T08:09:16.598Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_Title_Trl','ALTER TABLE public.C_Title_Trl ADD COLUMN Name VARCHAR(100) NOT NULL')
;

-- 2020-07-17T08:17:42.780Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=129, DefaultValue='@#AD_Client_ID@', FieldLength=22, Version=1.000000000000,Updated=TO_TIMESTAMP('2020-07-17 11:17:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570982
;

-- 2020-07-17T08:20:17.626Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=104, DefaultValue='@#AD_Org_ID@', FieldLength=22, Version=1.000000000000,Updated=TO_TIMESTAMP('2020-07-17 11:20:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570983
;

-- 2020-07-17T08:21:31.707Z
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=22, IsGenericZoomKeyColumn='Y', IsGenericZoomOrigin='Y', IsUpdateable='N', Version=1.000000000000,Updated=TO_TIMESTAMP('2020-07-17 11:21:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570989
;

-- 2020-07-17T08:22:03.669Z
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=7, Version=1.000000000000,Updated=TO_TIMESTAMP('2020-07-17 11:22:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570984
;

-- 2020-07-17T08:52:56.796Z
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=22, IsAllowLogging='Y', Version=1.000000000000,Updated=TO_TIMESTAMP('2020-07-17 11:52:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570985
;

-- 2020-07-17T08:53:31.981Z
-- URL zum Konzept
UPDATE AD_Column SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-07-17 11:53:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570990
;

-- 2020-07-17T08:54:45.587Z
-- URL zum Konzept
UPDATE AD_Column SET Version=1.000000000000,Updated=TO_TIMESTAMP('2020-07-17 11:54:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570990
;

-- 2020-07-17T08:55:20.737Z
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='Y', Version=1.000000000000,Updated=TO_TIMESTAMP('2020-07-17 11:55:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570986
;

-- 2020-07-17T08:56:03.507Z
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=7, Version=1.000000000000,Updated=TO_TIMESTAMP('2020-07-17 11:56:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570987
;

-- 2020-07-17T08:56:42.070Z
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=22, Version=1.000000000000,Updated=TO_TIMESTAMP('2020-07-17 11:56:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570988
;

-- 2020-07-17T08:57:01.671Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title','AD_Client_ID','NUMERIC(10)',null,null)
;

-- 2020-07-17T08:57:26.159Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2020-07-17T08:57:50.282Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2020-07-17T08:58:47.741Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title','CreatedBy','NUMERIC(10)',null,null)
;

-- 2020-07-17T08:59:25.344Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title','C_Title_ID','NUMERIC(10)',null,null)
;

-- 2020-07-17T08:59:55.482Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title','IsActive','CHAR(1)',null,'Y')
;

-- 2020-07-17T08:59:55.593Z
-- URL zum Konzept
UPDATE C_Title SET IsActive='Y' WHERE IsActive IS NULL
;

-- 2020-07-17T09:00:28.500Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_Title','ALTER TABLE public.C_Title ADD COLUMN Name VARCHAR(100)')
;

-- 2020-07-17T09:00:58.642Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2020-07-17T09:01:58.251Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2020-07-17T09:03:50.892Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=129, DefaultValue='@AD_Client_ID@', FieldLength=22, Version=1.000000000000,Updated=TO_TIMESTAMP('2020-07-17 12:03:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570991
;

-- 2020-07-17T09:04:43.690Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=104, DefaultValue='@AD_Org_ID@', FieldLength=22, Version=1.000000000000,Updated=TO_TIMESTAMP('2020-07-17 12:04:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570992
;

-- 2020-07-17T09:05:18.086Z
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=7, IsAllowLogging='Y', Version=1.000000000000,Updated=TO_TIMESTAMP('2020-07-17 12:05:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570993
;

-- 2020-07-17T09:05:47.456Z
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=22, IsAllowLogging='Y', Version=1.000000000000,Updated=TO_TIMESTAMP('2020-07-17 12:05:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570994
;

-- 2020-07-17T09:07:07.241Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=570998
;

-- 2020-07-17T09:07:07.590Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=570998
;

-- 2020-07-17T09:07:32.480Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title_trl','IsActive','CHAR(1)',null,null)
;

-- 2020-07-17T09:08:13.786Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_Title_Trl','ALTER TABLE public.C_Title_Trl ADD COLUMN C_Title_ID NUMERIC(10) NOT NULL')
;

-- 2020-07-17T09:08:13.853Z
-- URL zum Konzept
ALTER TABLE C_Title_Trl ADD CONSTRAINT CTitle_CTitleTrl FOREIGN KEY (C_Title_ID) REFERENCES public.C_Title DEFERRABLE INITIALLY DEFERRED
;

-- 2020-07-17T09:08:59.454Z
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='Y', Version=1.000000000000,Updated=TO_TIMESTAMP('2020-07-17 12:08:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570995
;

-- 2020-07-17T09:13:35.807Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,AD_Org_ID,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,Description,IsFacetFilter,MaxFacetsToFetch,FacetFilterSeqNo,AD_Element_ID) VALUES (20,'',1,1.000000000000,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2020-07-17 12:13:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N',0,'N','Y','N',TO_TIMESTAMP('2020-07-17 12:13:35','YYYY-MM-DD HH24:MI:SS'),100,'N','N',541508,'N','Das Selektionsfeld "Übersetzt" zeigt an, dass diese Spalte übersetzt ist',571002,'N','Y','N','N','N','N','N','N',0,'D','N','N','IsTranslated','N','Übersetzt','Diese Spalte ist übersetzt','N',0,0,420)
;

-- 2020-07-17T09:13:36.108Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=571002 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-07-17T09:13:36.170Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(420)
;

-- 2020-07-17T09:14:06.582Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_Title_Trl','ALTER TABLE public.C_Title_Trl ADD COLUMN IsTranslated CHAR(1) CHECK (IsTranslated IN (''Y'',''N'')) NOT NULL')
;

-- 2020-07-17T09:16:56.453Z
-- URL zum Konzept
UPDATE AD_Column SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-07-17 12:16:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=571001
;

-- 2020-07-17T09:19:28.426Z
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=7, Version=1.000000000000,Updated=TO_TIMESTAMP('2020-07-17 12:19:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570996
;

-- 2020-07-17T09:20:07.456Z
-- URL zum Konzept
UPDATE AD_Column SET Version=1.000000000000,Updated=TO_TIMESTAMP('2020-07-17 12:20:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570997
;

-- 2020-07-17T09:20:31.640Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title_trl','AD_Client_ID','NUMERIC(10)',null,null)
;

-- 2020-07-17T09:20:56.956Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_Title_Trl','ALTER TABLE public.C_Title_Trl ADD COLUMN AD_Language VARCHAR(6) NOT NULL')
;

-- 2020-07-17T09:20:57.024Z
-- URL zum Konzept
ALTER TABLE C_Title_Trl ADD CONSTRAINT ADLangu_CTitleTrl FOREIGN KEY (AD_Language) REFERENCES public.AD_Language DEFERRABLE INITIALLY DEFERRED
;

-- 2020-07-17T09:21:34.383Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title_trl','AD_Org_ID','NUMERIC(10)',null,null)
;

/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- 2020-07-17T09:22:13.004Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title_trl','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2020-07-17T09:22:43.128Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title_trl','CreatedBy','NUMERIC(10)',null,null)
;

-- 2020-07-17T09:25:01.293Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title_trl','C_Title_ID','NUMERIC(10)',null,null)
;

-- 2020-07-17T09:25:27.376Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title_trl','IsActive','CHAR(1)',null,'Y')
;

-- 2020-07-17T09:25:27.476Z
-- URL zum Konzept
UPDATE C_Title_Trl SET IsActive='Y' WHERE IsActive IS NULL
;

-- 2020-07-17T09:25:51.521Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title_trl','IsTranslated','CHAR(1)',null,null)
;

-- 2020-07-17T09:26:15.734Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title_trl','Name','VARCHAR(100)',null,null)
;

-- 2020-07-17T09:26:39.768Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title_trl','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2020-07-17T09:27:03.896Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_title_trl','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2020-07-17T09:27:03.896Z
-- URL zum Konzept
ALTER TABLE c_title_trl
    DROP COLUMN IF EXISTS c_title_trl_id
;
