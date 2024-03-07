-- Run mode: SWING_CLIENT

-- Table: PostFinance_Customer_Registration_Message
-- 2024-02-07T20:23:53.268Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542391,'N',TO_TIMESTAMP('2024-02-07 22:23:52.245','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'PostFinance Customer Registration Message','NP','L','PostFinance_Customer_Registration_Message','DTI',TO_TIMESTAMP('2024-02-07 22:23:52.245','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-02-07T20:23:53.284Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542391 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-02-07T20:23:53.404Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556327,TO_TIMESTAMP('2024-02-07 22:23:53.31','YYYY-MM-DD HH24:MI:SS.US'),100,1000000,50000,'Table PostFinance_Customer_Registration_Message',1,'Y','N','Y','Y','PostFinance_Customer_Registration_Message','N',1000000,TO_TIMESTAMP('2024-02-07 22:23:53.31','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-02-07T20:23:53.428Z
CREATE SEQUENCE POSTFINANCE_CUSTOMER_REGISTRATION_MESSAGE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: PostFinance_Customer_Registration_Message.AD_Client_ID
-- 2024-02-07T20:24:03.891Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587889,102,0,19,542391,'AD_Client_ID',TO_TIMESTAMP('2024-02-07 22:24:03.716','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-02-07 22:24:03.716','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-02-07T20:24:03.894Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587889 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-07T20:24:03.927Z
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- Column: PostFinance_Customer_Registration_Message.AD_Org_ID
-- 2024-02-07T20:24:04.814Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587890,113,0,30,542391,'AD_Org_ID',TO_TIMESTAMP('2024-02-07 22:24:04.487','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2024-02-07 22:24:04.487','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-02-07T20:24:04.816Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587890 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-07T20:24:04.818Z
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- Column: PostFinance_Customer_Registration_Message.Created
-- 2024-02-07T20:24:05.606Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587891,245,0,16,542391,'Created',TO_TIMESTAMP('2024-02-07 22:24:05.335','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-02-07 22:24:05.335','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-02-07T20:24:05.608Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587891 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-07T20:24:05.611Z
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- Column: PostFinance_Customer_Registration_Message.CreatedBy
-- 2024-02-07T20:24:06.654Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587892,246,0,18,110,542391,'CreatedBy',TO_TIMESTAMP('2024-02-07 22:24:06.268','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-02-07 22:24:06.268','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-02-07T20:24:06.656Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587892 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-07T20:24:06.658Z
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- Column: PostFinance_Customer_Registration_Message.IsActive
-- 2024-02-07T20:24:07.639Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587893,348,0,20,542391,'IsActive',TO_TIMESTAMP('2024-02-07 22:24:07.347','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-02-07 22:24:07.347','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-02-07T20:24:07.640Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587893 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-07T20:24:07.643Z
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- Column: PostFinance_Customer_Registration_Message.Updated
-- 2024-02-07T20:24:08.775Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587894,607,0,16,542391,'Updated',TO_TIMESTAMP('2024-02-07 22:24:08.441','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-02-07 22:24:08.441','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-02-07T20:24:08.777Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587894 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-07T20:24:08.780Z
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- Column: PostFinance_Customer_Registration_Message.UpdatedBy
-- 2024-02-07T20:24:10.115Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587895,608,0,18,110,542391,'UpdatedBy',TO_TIMESTAMP('2024-02-07 22:24:09.724','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-02-07 22:24:09.724','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-02-07T20:24:10.118Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587895 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-07T20:24:10.121Z
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2024-02-07T20:24:10.954Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582955,0,'PostFinance_Customer_Registration_Message_ID',TO_TIMESTAMP('2024-02-07 22:24:10.862','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','PostFinance Customer Registration Message','PostFinance Customer Registration Message',TO_TIMESTAMP('2024-02-07 22:24:10.862','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-02-07T20:24:10.957Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582955 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: PostFinance_Customer_Registration_Message.PostFinance_Customer_Registration_Message_ID
-- 2024-02-07T20:24:12.036Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587896,582955,0,13,542391,'PostFinance_Customer_Registration_Message_ID',TO_TIMESTAMP('2024-02-07 22:24:10.86','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','PostFinance Customer Registration Message',0,0,TO_TIMESTAMP('2024-02-07 22:24:10.86','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-02-07T20:24:12.037Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587896 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-07T20:24:12.039Z
/* DDL */  select update_Column_Translation_From_AD_Element(582955)
;

-- 2024-02-07T20:24:12.802Z
/* DDL */ CREATE TABLE public.PostFinance_Customer_Registration_Message (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, PostFinance_Customer_Registration_Message_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT PostFinance_Customer_Registration_Message_Key PRIMARY KEY (PostFinance_Customer_Registration_Message_ID))
;

-- 2024-02-07T20:24:12.841Z
INSERT INTO t_alter_column values('postfinance_customer_registration_message','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2024-02-07T20:24:12.860Z
INSERT INTO t_alter_column values('postfinance_customer_registration_message','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2024-02-07T20:24:12.872Z
INSERT INTO t_alter_column values('postfinance_customer_registration_message','CreatedBy','NUMERIC(10)',null,null)
;

-- 2024-02-07T20:24:12.882Z
INSERT INTO t_alter_column values('postfinance_customer_registration_message','IsActive','CHAR(1)',null,null)
;

-- 2024-02-07T20:24:12.918Z
INSERT INTO t_alter_column values('postfinance_customer_registration_message','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2024-02-07T20:24:12.934Z
INSERT INTO t_alter_column values('postfinance_customer_registration_message','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2024-02-07T20:24:12.951Z
INSERT INTO t_alter_column values('postfinance_customer_registration_message','PostFinance_Customer_Registration_Message_ID','NUMERIC(10)',null,null)
;

-- Column: PostFinance_Customer_Registration_Message.Processed
-- 2024-02-07T20:39:57.219Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587897,1047,0,20,542391,'Processed',TO_TIMESTAMP('2024-02-07 22:39:57.046','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','D',0,1,'E','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','Y','N','N','N','N','N','Y','N',0,'Verarbeitet',0,0,TO_TIMESTAMP('2024-02-07 22:39:57.046','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-02-07T20:39:57.223Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587897 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-07T20:39:57.227Z
/* DDL */  select update_Column_Translation_From_AD_Element(1047)
;

-- 2024-02-07T20:39:59.801Z
/* DDL */ SELECT public.db_alter_table('PostFinance_Customer_Registration_Message','ALTER TABLE public.PostFinance_Customer_Registration_Message ADD COLUMN Processed CHAR(1) DEFAULT ''N'' CHECK (Processed IN (''Y'',''N'')) NOT NULL')
;

-- Column: PostFinance_Customer_Registration_Message.C_BPartner_ID
-- 2024-02-07T20:42:58.143Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587898,187,0,30,542391,'C_BPartner_ID',TO_TIMESTAMP('2024-02-07 22:42:57.98','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Bezeichnet einen Geschäftspartner','D',0,22,'E','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Geschäftspartner',0,0,TO_TIMESTAMP('2024-02-07 22:42:57.98','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-02-07T20:42:58.147Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587898 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-07T20:42:58.152Z
/* DDL */  select update_Column_Translation_From_AD_Element(187)
;

-- 2024-02-07T20:43:00.122Z
/* DDL */ SELECT public.db_alter_table('PostFinance_Customer_Registration_Message','ALTER TABLE public.PostFinance_Customer_Registration_Message ADD COLUMN C_BPartner_ID NUMERIC(10)')
;

-- 2024-02-07T20:43:00.141Z
ALTER TABLE PostFinance_Customer_Registration_Message ADD CONSTRAINT CBPartner_PostFinanceCustomerRegistrationMessage FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- Run mode: SWING_CLIENT

-- Column: PostFinance_Customer_Registration_Message.PostFinance_Receiver_eBillId
-- 2024-02-08T09:01:31.932Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587900,582911,0,10,542391,'PostFinance_Receiver_eBillId',TO_TIMESTAMP('2024-02-08 11:01:31.675','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'eBill-ID des Kunden',0,0,TO_TIMESTAMP('2024-02-08 11:01:31.675','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-02-08T09:01:31.950Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587900 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-08T09:01:31.986Z
/* DDL */  select update_Column_Translation_From_AD_Element(582911)
;

-- 2024-02-08T09:01:34.245Z
/* DDL */ SELECT public.db_alter_table('PostFinance_Customer_Registration_Message','ALTER TABLE public.PostFinance_Customer_Registration_Message ADD COLUMN PostFinance_Receiver_eBillId VARCHAR(255) NOT NULL')
;

-- Run mode: SWING_CLIENT

-- Name: PostFinance Subscription Type
-- 2024-02-08T09:05:11.548Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541852,TO_TIMESTAMP('2024-02-08 11:05:11.396','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','PostFinance Subscription Type',TO_TIMESTAMP('2024-02-08 11:05:11.396','YYYY-MM-DD HH24:MI:SS.US'),100,'L')
;

-- 2024-02-08T09:05:11.552Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541852 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: PostFinance Subscription Type
-- Value: 1
-- ValueName: Registration
-- 2024-02-08T09:05:53.787Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543627,541852,TO_TIMESTAMP('2024-02-08 11:05:53.621','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Registration',TO_TIMESTAMP('2024-02-08 11:05:53.621','YYYY-MM-DD HH24:MI:SS.US'),100,'1','Registration')
;

-- 2024-02-08T09:05:53.790Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543627 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: PostFinance Subscription Type
-- Value: 2
-- ValueName: DirectRegistration
-- 2024-02-08T09:06:17.498Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543628,541852,TO_TIMESTAMP('2024-02-08 11:06:17.23','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','DirectRegistration',TO_TIMESTAMP('2024-02-08 11:06:17.23','YYYY-MM-DD HH24:MI:SS.US'),100,'2','DirectRegistration')
;

-- 2024-02-08T09:06:17.501Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543628 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: PostFinance Subscription Type
-- Value: 3
-- ValueName: Deregistration
-- 2024-02-08T09:06:29.004Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543629,541852,TO_TIMESTAMP('2024-02-08 11:06:28.875','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Deregistration',TO_TIMESTAMP('2024-02-08 11:06:28.875','YYYY-MM-DD HH24:MI:SS.US'),100,'3','Deregistration')
;

-- 2024-02-08T09:06:29.006Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543629 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2024-02-08T09:07:24.448Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582956,0,'SubscriptionType',TO_TIMESTAMP('2024-02-08 11:07:24.305','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Subscription Type','Subscription Type',TO_TIMESTAMP('2024-02-08 11:07:24.305','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-02-08T09:07:24.454Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582956 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: PostFinance_Customer_Registration_Message.SubscriptionType
-- 2024-02-08T09:08:32.973Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587901,582956,0,17,541852,542391,'SubscriptionType',TO_TIMESTAMP('2024-02-08 11:08:32.817','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Subscription Type',0,0,TO_TIMESTAMP('2024-02-08 11:08:32.817','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-02-08T09:08:32.975Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587901 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-08T09:08:32.978Z
/* DDL */  select update_Column_Translation_From_AD_Element(582956)
;

-- 2024-02-08T09:08:34.906Z
/* DDL */ SELECT public.db_alter_table('PostFinance_Customer_Registration_Message','ALTER TABLE public.PostFinance_Customer_Registration_Message ADD COLUMN SubscriptionType CHAR(1) NOT NULL')
;

-- 2024-02-08T09:09:41.038Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582957,0,'QRCode',TO_TIMESTAMP('2024-02-08 11:09:40.909','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','QR Code','QR Code',TO_TIMESTAMP('2024-02-08 11:09:40.909','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-02-08T09:09:41.041Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582957 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: PostFinance_Customer_Registration_Message.QRCode
-- 2024-02-08T09:09:58.097Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587902,582957,0,10,542391,'QRCode',TO_TIMESTAMP('2024-02-08 11:09:57.927','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'QR Code',0,0,TO_TIMESTAMP('2024-02-08 11:09:57.927','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-02-08T09:09:58.101Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587902 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-08T09:09:58.107Z
/* DDL */  select update_Column_Translation_From_AD_Element(582957)
;

-- 2024-02-08T09:10:00.606Z
/* DDL */ SELECT public.db_alter_table('PostFinance_Customer_Registration_Message','ALTER TABLE public.PostFinance_Customer_Registration_Message ADD COLUMN QRCode VARCHAR(255)')
;

-- 2024-02-08T09:10:46.423Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582958,0,'AdditionalData',TO_TIMESTAMP('2024-02-08 11:10:46.268','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Additional Data','Additional Data',TO_TIMESTAMP('2024-02-08 11:10:46.268','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-02-08T09:10:46.426Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582958 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: PostFinance_Customer_Registration_Message.AdditionalData
-- 2024-02-08T09:11:20.523Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587903,582958,0,10,542391,'AdditionalData',TO_TIMESTAMP('2024-02-08 11:11:20.333','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Additional Data',0,0,TO_TIMESTAMP('2024-02-08 11:11:20.333','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-02-08T09:11:20.525Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587903 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-08T09:11:20.529Z
/* DDL */  select update_Column_Translation_From_AD_Element(582958)
;

-- 2024-02-08T09:11:21.793Z
/* DDL */ SELECT public.db_alter_table('PostFinance_Customer_Registration_Message','ALTER TABLE public.PostFinance_Customer_Registration_Message ADD COLUMN AdditionalData VARCHAR(2000)')
;

-- Run mode: SWING_CLIENT

-- Window: PostFinance Customer Registration Message, InternalName=null
-- 2024-02-08T09:12:52.768Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,582955,0,541769,TO_TIMESTAMP('2024-02-08 11:12:52.622','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','Y','N','N','N','Y','PostFinance Customer Registration Message','N',TO_TIMESTAMP('2024-02-08 11:12:52.622','YYYY-MM-DD HH24:MI:SS.US'),100,'M',0,0,100)
;

-- 2024-02-08T09:12:52.771Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541769 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2024-02-08T09:12:52.774Z
/* DDL */  select update_window_translation_from_ad_element(582955)
;

-- 2024-02-08T09:12:52.785Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541769
;

-- 2024-02-08T09:12:52.791Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541769)
;

-- Tab: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message
-- Table: PostFinance_Customer_Registration_Message
-- 2024-02-08T09:13:45.643Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582955,0,547410,542391,541769,'Y',TO_TIMESTAMP('2024-02-08 11:13:45.493','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','N','A','PostFinance_Customer_Registration_Message','Y','N','Y','Y','N','N','N','Y','Y','Y','N','N','N','Y','Y','N','N','N',0,'PostFinance Customer Registration Message','N',10,0,TO_TIMESTAMP('2024-02-08 11:13:45.493','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-02-08T09:13:45.647Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547410 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-02-08T09:13:45.651Z
/* DDL */  select update_tab_translation_from_ad_element(582955)
;

-- 2024-02-08T09:13:45.659Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547410)
;

-- Field: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D) -> Mandant
-- Column: PostFinance_Customer_Registration_Message.AD_Client_ID
-- 2024-02-08T09:13:51.949Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587889,725152,0,547410,TO_TIMESTAMP('2024-02-08 11:13:51.792','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-02-08 11:13:51.792','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-02-08T09:13:51.953Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=725152 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-08T09:13:51.956Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2024-02-08T09:13:52.344Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725152
;

-- 2024-02-08T09:13:52.345Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725152)
;

-- Field: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D) -> Organisation
-- Column: PostFinance_Customer_Registration_Message.AD_Org_ID
-- 2024-02-08T09:13:52.466Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587890,725153,0,547410,TO_TIMESTAMP('2024-02-08 11:13:52.35','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2024-02-08 11:13:52.35','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-02-08T09:13:52.468Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=725153 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-08T09:13:52.470Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2024-02-08T09:13:52.654Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725153
;

-- 2024-02-08T09:13:52.655Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725153)
;

-- Field: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D) -> Aktiv
-- Column: PostFinance_Customer_Registration_Message.IsActive
-- 2024-02-08T09:13:52.779Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587893,725154,0,547410,TO_TIMESTAMP('2024-02-08 11:13:52.658','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-02-08 11:13:52.658','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-02-08T09:13:52.780Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=725154 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-08T09:13:52.781Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2024-02-08T09:13:52.955Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725154
;

-- 2024-02-08T09:13:52.956Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725154)
;

-- Field: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D) -> PostFinance Customer Registration Message
-- Column: PostFinance_Customer_Registration_Message.PostFinance_Customer_Registration_Message_ID
-- 2024-02-08T09:13:53.067Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587896,725155,0,547410,TO_TIMESTAMP('2024-02-08 11:13:52.959','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','PostFinance Customer Registration Message',TO_TIMESTAMP('2024-02-08 11:13:52.959','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-02-08T09:13:53.069Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=725155 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-08T09:13:53.070Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582955)
;

-- 2024-02-08T09:13:53.074Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725155
;

-- 2024-02-08T09:13:53.075Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725155)
;

-- Field: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D) -> Verarbeitet
-- Column: PostFinance_Customer_Registration_Message.Processed
-- 2024-02-08T09:13:53.256Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587897,725156,0,547410,TO_TIMESTAMP('2024-02-08 11:13:53.077','YYYY-MM-DD HH24:MI:SS.US'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'D','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2024-02-08 11:13:53.077','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-02-08T09:13:53.256Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=725156 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-08T09:13:53.257Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047)
;

-- 2024-02-08T09:13:53.314Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725156
;

-- 2024-02-08T09:13:53.315Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725156)
;

-- Field: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D) -> Geschäftspartner
-- Column: PostFinance_Customer_Registration_Message.C_BPartner_ID
-- 2024-02-08T09:13:53.476Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587898,725157,0,547410,TO_TIMESTAMP('2024-02-08 11:13:53.322','YYYY-MM-DD HH24:MI:SS.US'),100,'Bezeichnet einen Geschäftspartner',22,'D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2024-02-08 11:13:53.322','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-02-08T09:13:53.477Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=725157 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-08T09:13:53.479Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187)
;

-- 2024-02-08T09:13:53.522Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725157
;

-- 2024-02-08T09:13:53.522Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725157)
;

-- Field: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D) -> eBill-ID des Kunden
-- Column: PostFinance_Customer_Registration_Message.PostFinance_Receiver_eBillId
-- 2024-02-08T09:13:53.618Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587900,725158,0,547410,TO_TIMESTAMP('2024-02-08 11:13:53.526','YYYY-MM-DD HH24:MI:SS.US'),100,255,'D','Y','N','N','N','N','N','N','N','eBill-ID des Kunden',TO_TIMESTAMP('2024-02-08 11:13:53.526','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-02-08T09:13:53.619Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=725158 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-08T09:13:53.621Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582911)
;

-- 2024-02-08T09:13:53.624Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725158
;

-- 2024-02-08T09:13:53.625Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725158)
;

-- Field: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D) -> Subscription Type
-- Column: PostFinance_Customer_Registration_Message.SubscriptionType
-- 2024-02-08T09:13:53.725Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587901,725159,0,547410,TO_TIMESTAMP('2024-02-08 11:13:53.627','YYYY-MM-DD HH24:MI:SS.US'),100,1,'D','Y','N','N','N','N','N','N','N','Subscription Type',TO_TIMESTAMP('2024-02-08 11:13:53.627','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-02-08T09:13:53.726Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=725159 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-08T09:13:53.728Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582956)
;

-- 2024-02-08T09:13:53.731Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725159
;

-- 2024-02-08T09:13:53.732Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725159)
;

-- Field: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D) -> QR Code
-- Column: PostFinance_Customer_Registration_Message.QRCode
-- 2024-02-08T09:13:53.835Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587902,725160,0,547410,TO_TIMESTAMP('2024-02-08 11:13:53.735','YYYY-MM-DD HH24:MI:SS.US'),100,255,'D','Y','N','N','N','N','N','N','N','QR Code',TO_TIMESTAMP('2024-02-08 11:13:53.735','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-02-08T09:13:53.836Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=725160 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-08T09:13:53.838Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582957)
;

-- 2024-02-08T09:13:53.843Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725160
;

-- 2024-02-08T09:13:53.843Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725160)
;

-- Field: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D) -> Additional Data
-- Column: PostFinance_Customer_Registration_Message.AdditionalData
-- 2024-02-08T09:13:53.944Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587903,725161,0,547410,TO_TIMESTAMP('2024-02-08 11:13:53.845','YYYY-MM-DD HH24:MI:SS.US'),100,2000,'D','Y','N','N','N','N','N','N','N','Additional Data',TO_TIMESTAMP('2024-02-08 11:13:53.845','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-02-08T09:13:53.945Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=725161 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-08T09:13:53.946Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582958)
;

-- 2024-02-08T09:13:53.949Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725161
;

-- 2024-02-08T09:13:53.949Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725161)
;

-- Tab: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D)
-- UI Section: main
-- 2024-02-08T09:16:23.451Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547410,545987,TO_TIMESTAMP('2024-02-08 11:16:23.21','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2024-02-08 11:16:23.21','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2024-02-08T09:16:23.454Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545987 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D) -> main
-- UI Column: 10
-- 2024-02-08T09:16:34.052Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547303,545987,TO_TIMESTAMP('2024-02-08 11:16:33.906','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2024-02-08 11:16:33.906','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D) -> main -> 10
-- UI Element Group: default
-- 2024-02-08T09:16:48.588Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547303,551531,TO_TIMESTAMP('2024-02-08 11:16:48.438','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','default',10,'primary',TO_TIMESTAMP('2024-02-08 11:16:48.438','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D) -> main -> 10 -> default.Geschäftspartner
-- Column: PostFinance_Customer_Registration_Message.C_BPartner_ID
-- 2024-02-08T09:17:11.716Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,725157,0,547410,622916,551531,'F',TO_TIMESTAMP('2024-02-08 11:17:11.465','YYYY-MM-DD HH24:MI:SS.US'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','N','N',0,'Geschäftspartner',10,0,0,TO_TIMESTAMP('2024-02-08 11:17:11.465','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D) -> main -> 10 -> default.eBill-ID des Kunden
-- Column: PostFinance_Customer_Registration_Message.PostFinance_Receiver_eBillId
-- 2024-02-08T09:17:20.536Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,725158,0,547410,622917,551531,'F',TO_TIMESTAMP('2024-02-08 11:17:20.43','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'eBill-ID des Kunden',20,0,0,TO_TIMESTAMP('2024-02-08 11:17:20.43','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D) -> main -> 10 -> default.Subscription Type
-- Column: PostFinance_Customer_Registration_Message.SubscriptionType
-- 2024-02-08T09:17:35.808Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,725159,0,547410,622918,551531,'F',TO_TIMESTAMP('2024-02-08 11:17:35.662','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Subscription Type',30,0,0,TO_TIMESTAMP('2024-02-08 11:17:35.662','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D) -> main -> 10 -> default.QR Code
-- Column: PostFinance_Customer_Registration_Message.QRCode
-- 2024-02-08T09:18:25.072Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,725160,0,547410,622919,551531,'F',TO_TIMESTAMP('2024-02-08 11:18:24.926','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'QR Code',40,0,0,TO_TIMESTAMP('2024-02-08 11:18:24.926','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D) -> main -> 10
-- UI Element Group: info
-- 2024-02-08T09:18:31.522Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547303,551532,TO_TIMESTAMP('2024-02-08 11:18:31.388','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','info',20,TO_TIMESTAMP('2024-02-08 11:18:31.388','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D) -> main -> 10 -> info.Additional Data
-- Column: PostFinance_Customer_Registration_Message.AdditionalData
-- 2024-02-08T09:18:45.468Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,725161,0,547410,622920,551532,'F',TO_TIMESTAMP('2024-02-08 11:18:45.344','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Additional Data',10,0,0,TO_TIMESTAMP('2024-02-08 11:18:45.344','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Section: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D) -> main
-- UI Column: 20
-- 2024-02-08T09:18:57.649Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547304,545987,TO_TIMESTAMP('2024-02-08 11:18:57.49','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',20,TO_TIMESTAMP('2024-02-08 11:18:57.49','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D) -> main -> 20
-- UI Element Group: flags
-- 2024-02-08T09:19:03.555Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547304,551533,TO_TIMESTAMP('2024-02-08 11:19:03.412','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','flags',10,TO_TIMESTAMP('2024-02-08 11:19:03.412','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D) -> main -> 20 -> flags.Verarbeitet
-- Column: PostFinance_Customer_Registration_Message.Processed
-- 2024-02-08T09:19:21.269Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,725156,0,547410,622921,551533,'F',TO_TIMESTAMP('2024-02-08 11:19:21.159','YYYY-MM-DD HH24:MI:SS.US'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','Y','N','N','N',0,'Verarbeitet',10,0,0,TO_TIMESTAMP('2024-02-08 11:19:21.159','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D) -> main -> 20
-- UI Element Group: org
-- 2024-02-08T09:19:47.177Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547304,551534,TO_TIMESTAMP('2024-02-08 11:19:47.023','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','org',20,TO_TIMESTAMP('2024-02-08 11:19:47.023','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D) -> main -> 20 -> org.Organisation
-- Column: PostFinance_Customer_Registration_Message.AD_Org_ID
-- 2024-02-08T09:20:07.980Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,725153,0,547410,622922,551534,'F',TO_TIMESTAMP('2024-02-08 11:20:07.861','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Organisation',10,0,0,TO_TIMESTAMP('2024-02-08 11:20:07.861','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D) -> main -> 20 -> org.Mandant
-- Column: PostFinance_Customer_Registration_Message.AD_Client_ID
-- 2024-02-08T09:20:14.357Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,725152,0,547410,622923,551534,'F',TO_TIMESTAMP('2024-02-08 11:20:14.22','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2024-02-08 11:20:14.22','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D) -> main -> 10 -> default.Geschäftspartner
-- Column: PostFinance_Customer_Registration_Message.C_BPartner_ID
-- 2024-02-08T09:20:55.653Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-02-08 11:20:55.653','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=622916
;

-- UI Element: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D) -> main -> 10 -> default.eBill-ID des Kunden
-- Column: PostFinance_Customer_Registration_Message.PostFinance_Receiver_eBillId
-- 2024-02-08T09:20:55.661Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-02-08 11:20:55.661','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=622917
;

-- UI Element: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D) -> main -> 10 -> default.Subscription Type
-- Column: PostFinance_Customer_Registration_Message.SubscriptionType
-- 2024-02-08T09:20:55.667Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-02-08 11:20:55.667','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=622918
;

-- UI Element: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D) -> main -> 10 -> default.QR Code
-- Column: PostFinance_Customer_Registration_Message.QRCode
-- 2024-02-08T09:20:55.673Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-02-08 11:20:55.673','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=622919
;

-- UI Element: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D) -> main -> 10 -> info.Additional Data
-- Column: PostFinance_Customer_Registration_Message.AdditionalData
-- 2024-02-08T09:20:55.679Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-02-08 11:20:55.679','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=622920
;

-- UI Element: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D) -> main -> 20 -> flags.Verarbeitet
-- Column: PostFinance_Customer_Registration_Message.Processed
-- 2024-02-08T09:20:55.684Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-02-08 11:20:55.684','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=622921
;

-- UI Element: PostFinance Customer Registration Message(541769,D) -> PostFinance Customer Registration Message(547410,D) -> main -> 20 -> org.Organisation
-- Column: PostFinance_Customer_Registration_Message.AD_Org_ID
-- 2024-02-08T09:20:55.689Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-02-08 11:20:55.689','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=622922
;

-- Run mode: SWING_CLIENT

-- Value: DownloadPostFinanceCustomerRegistrationMessage
-- Classname: de.metas.postfinance.customerregistration.process.DownloadPostFinanceCustomerRegistrationMessage
-- 2024-02-13T20:07:52.296Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585355,'Y','de.metas.postfinance.customerregistration.process.DownloadPostFinanceCustomerRegistrationMessage','N',TO_TIMESTAMP('2024-02-13 22:07:52.009','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'PostFinance-Registrierungsnachrichten abrufen','json','N','N','xls','Java',TO_TIMESTAMP('2024-02-13 22:07:52.009','YYYY-MM-DD HH24:MI:SS.US'),100,'DownloadPostFinanceCustomerRegistrationMessage')
;

-- 2024-02-13T20:07:52.321Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585355 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: DownloadPostFinanceCustomerRegistrationMessage(de.metas.postfinance.customerregistration.process.DownloadPostFinanceCustomerRegistrationMessage)
-- Table: PostFinance_Customer_Registration_Message
-- EntityType: D
-- 2024-02-13T20:08:58.488Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585355,542391,541460,TO_TIMESTAMP('2024-02-13 22:08:58.317','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y',TO_TIMESTAMP('2024-02-13 22:08:58.317','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','Y','N')
;

-- Process: DownloadPostFinanceCustomerRegistrationMessage(de.metas.postfinance.customerregistration.process.DownloadPostFinanceCustomerRegistrationMessage)
-- 2024-02-13T20:09:15.548Z
UPDATE AD_Process_Trl SET Name='Download PostFinance Customer Registration Message',Updated=TO_TIMESTAMP('2024-02-13 22:09:15.548','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585355
;

-- Run mode: SWING_CLIENT

-- Process: DownloadPostFinanceCustomerRegistrationMessage(de.metas.postfinance.customerregistration.process.DownloadPostFinanceCustomerRegistrationMessage)
-- Table: PostFinance_Customer_Registration_Message
-- Window: PostFinance Customer Registration Message(541769,D)
-- EntityType: D
-- 2024-02-14T12:50:48.435Z
UPDATE AD_Table_Process SET AD_Window_ID=541769,Updated=TO_TIMESTAMP('2024-02-14 14:50:48.434','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_Process_ID=541460
;

-- Element: PostFinance_Customer_Registration_Message_ID
-- 2024-02-14T12:52:43.249Z
UPDATE AD_Element_Trl SET Name='PostFinance Kunden-Registrierungsnachricht', PrintName='PostFinance Kunden-Registrierungsnachricht',Updated=TO_TIMESTAMP('2024-02-14 14:52:43.249','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582955 AND AD_Language='de_CH'
;

-- 2024-02-14T12:52:43.283Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582955,'de_CH')
;

-- Element: PostFinance_Customer_Registration_Message_ID
-- 2024-02-14T12:52:45.942Z
UPDATE AD_Element_Trl SET Name='PostFinance Kunden-Registrierungsnachricht', PrintName='PostFinance Kunden-Registrierungsnachricht',Updated=TO_TIMESTAMP('2024-02-14 14:52:45.942','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582955 AND AD_Language='de_DE'
;

-- 2024-02-14T12:52:45.944Z
UPDATE AD_Element SET Name='PostFinance Kunden-Registrierungsnachricht', PrintName='PostFinance Kunden-Registrierungsnachricht' WHERE AD_Element_ID=582955
;

-- 2024-02-14T12:52:46.530Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582955,'de_DE')
;

-- 2024-02-14T12:52:46.536Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582955,'de_DE')
;

-- Element: PostFinance_Customer_Registration_Message_ID
-- 2024-02-14T12:52:49.865Z
UPDATE AD_Element_Trl SET Name='PostFinance Kunden-Registrierungsnachricht', PrintName='PostFinance Kunden-Registrierungsnachricht',Updated=TO_TIMESTAMP('2024-02-14 14:52:49.865','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582955 AND AD_Language='fr_CH'
;

-- 2024-02-14T12:52:49.869Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582955,'fr_CH')
;

-- Element: PostFinance_Customer_Registration_Message_ID
-- 2024-02-14T12:52:52.481Z
UPDATE AD_Element_Trl SET Name='PostFinance Kunden-Registrierungsnachricht', PrintName='PostFinance Kunden-Registrierungsnachricht',Updated=TO_TIMESTAMP('2024-02-14 14:52:52.481','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582955 AND AD_Language='it_IT'
;

-- 2024-02-14T12:52:52.482Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582955,'it_IT')
;

-- Name: PostFinance Kunden-Registrierungsnachricht
-- Action Type: W
-- Window: PostFinance Kunden-Registrierungsnachricht(541769,D)
-- 2024-02-14T12:53:51.774Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,582955,542138,0,541769,TO_TIMESTAMP('2024-02-14 14:53:51.644','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Post Finance Customer Registration Message','Y','N','N','N','N','PostFinance Kunden-Registrierungsnachricht',TO_TIMESTAMP('2024-02-14 14:53:51.644','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-02-14T12:53:51.787Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542138 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2024-02-14T12:53:51.791Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542138, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542138)
;

-- 2024-02-14T12:53:51.817Z
/* DDL */  select update_menu_translation_from_ad_element(582955)
;

-- Reordering children of `Shipment`
-- Node name: `Shipment (M_InOut)`
-- 2024-02-14T12:53:52.451Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000087 AND AD_Tree_ID=10
;

-- Node name: `Shipping Notification (M_Shipping_Notification)`
-- 2024-02-14T12:53:52.453Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542113 AND AD_Tree_ID=10
;

-- Node name: `Shipment Restrictions (M_Shipment_Constraint)`
-- 2024-02-14T12:53:52.454Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540970 AND AD_Tree_ID=10
;

-- Node name: `Shipment Disposition (M_ShipmentSchedule)`
-- 2024-02-14T12:53:52.455Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540728 AND AD_Tree_ID=10
;

-- Node name: `Shipment disposition export revision (M_ShipmentSchedule_ExportAudit_Item)`
-- 2024-02-14T12:53:52.456Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541483 AND AD_Tree_ID=10
;

-- Node name: `Empties Return (M_InOut)`
-- 2024-02-14T12:53:52.458Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540783 AND AD_Tree_ID=10
;

-- Node name: `Shipment Declaration Config (M_Shipment_Declaration_Config)`
-- 2024-02-14T12:53:52.459Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541251 AND AD_Tree_ID=10
;

-- Node name: `Shipment Declaration (M_Shipment_Declaration)`
-- 2024-02-14T12:53:52.460Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541250 AND AD_Tree_ID=10
;

-- Node name: `Customer Return (M_InOut)`
-- 2024-02-14T12:53:52.461Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540841 AND AD_Tree_ID=10
;

-- Node name: `Service/Repair Customer Return (M_InOut)`
-- 2024-02-14T12:53:52.462Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53243 AND AD_Tree_ID=10
;

-- Node name: `Picking Terminal (M_Packageable_V)`
-- 2024-02-14T12:53:52.463Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540868 AND AD_Tree_ID=10
;

-- Node name: `Picking Terminal (v2)`
-- 2024-02-14T12:53:52.463Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541138 AND AD_Tree_ID=10
;

-- Node name: `Picking Tray Clearing (Prototype)`
-- 2024-02-14T12:53:52.465Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540971 AND AD_Tree_ID=10
;

-- Node name: `Picking Tray (M_PickingSlot)`
-- 2024-02-14T12:53:52.465Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540949 AND AD_Tree_ID=10
;

-- Node name: `Picking Slot Trx (M_PickingSlot_Trx)`
-- 2024-02-14T12:53:52.466Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540950 AND AD_Tree_ID=10
;

-- Node name: `EDI DESADV (EDI_Desadv)`
-- 2024-02-14T12:53:52.467Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540976 AND AD_Tree_ID=10
;

-- Node name: `Picking candidate (M_Picking_Candidate)`
-- 2024-02-14T12:53:52.468Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541395 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2024-02-14T12:53:52.468Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000060 AND AD_Tree_ID=10
;

-- Node name: `EDI_Desadv_Pack (EDI_Desadv_Pack)`
-- 2024-02-14T12:53:52.469Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541406 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2024-02-14T12:53:52.470Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000068 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2024-02-14T12:53:52.471Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000079 AND AD_Tree_ID=10
;

-- Node name: `Picking`
-- 2024-02-14T12:53:52.471Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541856 AND AD_Tree_ID=10
;

-- Node name: `PostFinance Kunden-Registrierungsnachricht`
-- 2024-02-14T12:53:52.472Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542138 AND AD_Tree_ID=10
;

-- Reordering children of `Menu`
-- Node name: `PostFinance Kunden-Registrierungsnachricht`
-- 2024-02-14T12:53:59.990Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542138 AND AD_Tree_ID=10
;

-- Node name: `webUI`
-- 2024-02-14T12:53:59.991Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000007 AND AD_Tree_ID=10
;

-- Node name: `Application Dictionary`
-- 2024-02-14T12:53:59.991Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541085 AND AD_Tree_ID=10
;

-- Node name: `Übersetzung`
-- 2024-02-14T12:53:59.992Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540861 AND AD_Tree_ID=10
;

-- Node name: `Handling Units`
-- 2024-02-14T12:53:59.993Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540478 AND AD_Tree_ID=10
;

-- Node name: `Application Dictionary`
-- 2024-02-14T12:53:59.993Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=153 AND AD_Tree_ID=10
;

-- Node name: `System Admin`
-- 2024-02-14T12:53:59.994Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=218 AND AD_Tree_ID=10
;

-- Node name: `Contract Management`
-- 2024-02-14T12:53:59.994Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540281 AND AD_Tree_ID=10
;

-- Node name: `Partner Relations`
-- 2024-02-14T12:53:59.995Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=263 AND AD_Tree_ID=10
;

-- Node name: `Quote-to-Invoice`
-- 2024-02-14T12:53:59.996Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=166 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Management`
-- 2024-02-14T12:53:59.996Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53014 AND AD_Tree_ID=10
;

-- Node name: `Requisition-to-Invoice`
-- 2024-02-14T12:53:59.997Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=203 AND AD_Tree_ID=10
;

-- Node name: `DPD`
-- 2024-02-14T12:53:59.997Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540092 AND AD_Tree_ID=10
;

-- Node name: `Materialsaldo`
-- 2024-02-14T12:53:59.998Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540627 AND AD_Tree_ID=10
;

-- Node name: `Returns`
-- 2024-02-14T12:53:59.999Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53242 AND AD_Tree_ID=10
;

-- Node name: `Open Items`
-- 2024-02-14T12:53:59.999Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=236 AND AD_Tree_ID=10
;

-- Node name: `Material Management`
-- 2024-02-14T12:54:00Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=183 AND AD_Tree_ID=10
;

-- Node name: `Project Management`
-- 2024-02-14T12:54:00.001Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=160 AND AD_Tree_ID=10
;

-- Node name: `Performance Analysis`
-- 2024-02-14T12:54:00.002Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=278 AND AD_Tree_ID=10
;

-- Node name: `Assets`
-- 2024-02-14T12:54:00.002Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=345 AND AD_Tree_ID=10
;

-- Node name: `Call Center`
-- 2024-02-14T12:54:00.003Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540016 AND AD_Tree_ID=10
;

-- Node name: `Berichte`
-- 2024-02-14T12:54:00.004Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540613 AND AD_Tree_ID=10
;

-- Node name: `Human Resource & Payroll`
-- 2024-02-14T12:54:00.005Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53108 AND AD_Tree_ID=10
;

-- Node name: `EDI Definition (C_BP_EDI)`
-- 2024-02-14T12:54:00.005Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=518 AND AD_Tree_ID=10
;

-- Node name: `EDI Transaction`
-- 2024-02-14T12:54:00.006Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=519 AND AD_Tree_ID=10
;

-- Node name: `Berichte Materialwirtschaft`
-- 2024-02-14T12:54:00.007Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000004 AND AD_Tree_ID=10
;

-- Node name: `Einstellungen Verkauf`
-- 2024-02-14T12:54:00.007Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000001 AND AD_Tree_ID=10
;

-- Node name: `Berichte Verkauf`
-- 2024-02-14T12:54:00.008Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000000 AND AD_Tree_ID=10
;

-- Node name: `Berichte Geschäftspartner`
-- 2024-02-14T12:54:00.009Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000002 AND AD_Tree_ID=10
;

-- Node name: `Cockpit`
-- 2024-02-14T12:54:00.009Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540029 AND AD_Tree_ID=10
;

-- Node name: `Packstück (M_Package)`
-- 2024-02-14T12:54:00.010Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540028 AND AD_Tree_ID=10
;

-- Node name: `Lieferanten Abrufauftrag (C_OrderLine)`
-- 2024-02-14T12:54:00.010Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540030 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal (@PREFIX@de/metas/reports/kassenbuch/report.jasper)`
-- 2024-02-14T12:54:00.011Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540031 AND AD_Tree_ID=10
;

-- Node name: `Nachlieferung (M_SubsequentDelivery_V)`
-- 2024-02-14T12:54:00.011Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540034 AND AD_Tree_ID=10
;

-- Node name: `Verpackung (M_PackagingContainer)`
-- 2024-02-14T12:54:00.012Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540024 AND AD_Tree_ID=10
;

-- Node name: `Abolieferplan aktualisieren (de.metas.contracts.subscription.process.C_SubscriptionProgress_Evaluate)`
-- 2024-02-14T12:54:00.012Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540015 AND AD_Tree_ID=10
;

-- Node name: `Umsatz pro Kunde (@PREFIX@de/metas/reports/umsatzprokunde/report.jasper)`
-- 2024-02-14T12:54:00.013Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540066 AND AD_Tree_ID=10
;

-- Node name: `Sponsoren Anlegen (de.metas.commission.process.CreateSponsors)`
-- 2024-02-14T12:54:00.014Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540043 AND AD_Tree_ID=10
;

-- Node name: `Arbeitszeit (@PREFIX@de/metas/reports/arbeitszeit/report.jasper)`
-- 2024-02-14T12:54:00.015Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540048 AND AD_Tree_ID=10
;

-- Node name: `Check Tree and Reset Sponsor Depths (de.metas.commission.process.CheckTreeResetDepths)`
-- 2024-02-14T12:54:00.015Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540052 AND AD_Tree_ID=10
;

-- Node name: `Bankeinzug (C_DirectDebit)`
-- 2024-02-14T12:54:00.016Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540058 AND AD_Tree_ID=10
;

-- Node name: `Spezial`
-- 2024-02-14T12:54:00.017Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540077 AND AD_Tree_ID=10
;

-- Node name: `Belege`
-- 2024-02-14T12:54:00.018Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540076 AND AD_Tree_ID=10
;

-- Node name: `Steuer`
-- 2024-02-14T12:54:00.018Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540075 AND AD_Tree_ID=10
;

-- Node name: `Währung`
-- 2024-02-14T12:54:00.019Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540074 AND AD_Tree_ID=10
;

-- Node name: `Hauptbuch`
-- 2024-02-14T12:54:00.019Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540073 AND AD_Tree_ID=10
;

-- Node name: `Speditionsauftrag (@PREFIX@de/metas/docs/sales/shippingorder/report.jasper)`
-- 2024-02-14T12:54:00.020Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540062 AND AD_Tree_ID=10
;

-- Node name: `Steueranmeldung (@PREFIX@de/metas/reports/taxregistration/report.jasper)`
-- 2024-02-14T12:54:00.020Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540070 AND AD_Tree_ID=10
;

-- Node name: `Wiederkehrende Zahlungen (C_RecurrentPayment)`
-- 2024-02-14T12:54:00.021Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540068 AND AD_Tree_ID=10
;

-- Node name: `Verkaufte Artikel (@PREFIX@de/metas/reports/soldproducts/report.jasper)`
-- 2024-02-14T12:54:00.022Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540065 AND AD_Tree_ID=10
;

-- Node name: `Versand (@PREFIX@de/metas/reports/versand/report.jasper)`
-- 2024-02-14T12:54:00.022Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540067 AND AD_Tree_ID=10
;

-- Node name: `Provision_LEGACY`
-- 2024-02-14T12:54:00.023Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540083 AND AD_Tree_ID=10
;

-- Node name: `Vertriebspartnerpunkte (@PREFIX@de/metas/reports/vertriebspartnerpunktzahl/report.jasper)`
-- 2024-02-14T12:54:00.023Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540136 AND AD_Tree_ID=10
;

-- Node name: `C_BPartner Convert Memo (de.metas.adempiere.process.ConvertBPartnerMemo)`
-- 2024-02-14T12:54:00.024Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540106 AND AD_Tree_ID=10
;

-- Node name: `Ladeliste (Jasper) (@PREFIX@de/metas/docs/sales/shippingorder/report.jasper)`
-- 2024-02-14T12:54:00.024Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540090 AND AD_Tree_ID=10
;

-- Node name: `Wiederkenhrende Zahlungs-Rechnungen erzeugen (de.metas.banking.process.C_RecurrentPaymentCreateInvoice)`
-- 2024-02-14T12:54:00.025Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540109 AND AD_Tree_ID=10
;

-- Node name: `Daten-Bereinigung (de.metas.adempiere.process.SweepTable)`
-- 2024-02-14T12:54:00.026Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540119 AND AD_Tree_ID=10
;

-- Node name: `Geschäftspartner importieren (org.compiere.process.ImportBPartner)`
-- 2024-02-14T12:54:00.027Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540120 AND AD_Tree_ID=10
;

-- Node name: `Update C_BPartner.IsSalesRep (de.metas.process.ExecuteUpdateSQL)`
-- 2024-02-14T12:54:00.028Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540103 AND AD_Tree_ID=10
;

-- Node name: `E/A`
-- 2024-02-14T12:54:00.028Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540098 AND AD_Tree_ID=10
;

-- Node name: `Sponsor-Statistik aktualisieren (de.metas.commission.process.UpdateSponsorStats)`
-- 2024-02-14T12:54:00.029Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540105 AND AD_Tree_ID=10
;

-- Node name: `Downline Navigator (de.metas.commision.form.zk.WSponsorBrowse)`
-- 2024-02-14T12:54:00.030Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540139 AND AD_Tree_ID=10
;

-- Node name: `B2B Adressen und Bankverbindung ändern (de.metas.commision.form.zk.WB2BAddressAccount)`
-- 2024-02-14T12:54:00.031Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540187 AND AD_Tree_ID=10
;

-- Node name: `B2B Auftrag erfassen (de.metas.commision.form.zk.WB2BOrder)`
-- 2024-02-14T12:54:00.031Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540184 AND AD_Tree_ID=10
;

-- Node name: `UserAccountLock`
-- 2024-02-14T12:54:00.032Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540147 AND AD_Tree_ID=10
;

-- Node name: `B2B Bestellübersicht (de.metas.commision.form.zk.WB2BOrderHistory)`
-- 2024-02-14T12:54:00.033Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540185 AND AD_Tree_ID=10
;

-- Node name: `VP-Ränge (@PREFIX@de/metas/reports/vertriebspartnerraenge/report.jasper)`
-- 2024-02-14T12:54:00.033Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540167 AND AD_Tree_ID=10
;

-- Node name: `User lock expire (de.metas.user.process.AD_User_ExpireLocks)`
-- 2024-02-14T12:54:00.034Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540148 AND AD_Tree_ID=10
;

-- Node name: `Orders Overview (de.metas.adempiere.form.swing.OrderOverview)`
-- 2024-02-14T12:54:00.035Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540228 AND AD_Tree_ID=10
;

-- Node name: `Kommissionier Terminal (de.metas.picking.terminal.form.swing.PickingTerminal)`
-- 2024-02-14T12:54:00.036Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540225 AND AD_Tree_ID=10
;

-- Node name: `Tour (M_Tour)`
-- 2024-02-14T12:54:00.036Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540269 AND AD_Tree_ID=10
;

-- Node name: `UI Trigger (AD_TriggerUI)`
-- 2024-02-14T12:54:00.038Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540246 AND AD_Tree_ID=10
;

-- Node name: `Auftragskandidaten verarbeiten (de.metas.ordercandidate.process.C_OLCandEnqueueForSalesOrderCreation)`
-- 2024-02-14T12:54:00.038Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540261 AND AD_Tree_ID=10
;

-- Node name: `ESR Zahlungsimport (ESR_Import)`
-- 2024-02-14T12:54:00.038Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540404 AND AD_Tree_ID=10
;

-- Node name: `Liefertage (M_DeliveryDay)`
-- 2024-02-14T12:54:00.039Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540270 AND AD_Tree_ID=10
;

-- Node name: `Create product costs (de.metas.adempiere.process.CreateProductCosts)`
-- 2024-02-14T12:54:00.039Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540252 AND AD_Tree_ID=10
;

-- Node name: `Document Management`
-- 2024-02-14T12:54:00.040Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540400 AND AD_Tree_ID=10
;

-- Node name: `Update Addresses (de.metas.adempiere.process.UpdateAddresses)`
-- 2024-02-14T12:54:00.040Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540253 AND AD_Tree_ID=10
;

-- Node name: `Massendruck`
-- 2024-02-14T12:54:00.041Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540443 AND AD_Tree_ID=10
;

-- Node name: `Abrechnung MwSt.-Korrektur (C_Invoice_VAT_Corr_Candidates_v1)`
-- 2024-02-14T12:54:00.042Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=79, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540238 AND AD_Tree_ID=10
;

-- Node name: `Picking Slot (M_PickingSlot)`
-- 2024-02-14T12:54:00.042Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=80, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540512 AND AD_Tree_ID=10
;

-- Node name: `Picking Slot Trx (M_PickingSlot_Trx)`
-- 2024-02-14T12:54:00.043Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=81, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540544 AND AD_Tree_ID=10
;

-- Node name: `Picking Vorbereitung Liste (Jasper) (@PREFIX@de/metas/reports/pickingpreparation/report.jasper)`
-- 2024-02-14T12:54:00.044Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=82, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540517 AND AD_Tree_ID=10
;

-- Node name: `Parzelle (C_Allotment)`
-- 2024-02-14T12:54:00.044Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=83, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540518 AND AD_Tree_ID=10
;

-- Node name: `Export Format (EXP_Format)`
-- 2024-02-14T12:54:00.045Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=84, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540440 AND AD_Tree_ID=10
;

-- Node name: `Transportdisposition (M_Tour_Instance)`
-- 2024-02-14T12:54:00.046Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=85, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540559 AND AD_Tree_ID=10
;

-- Node name: `Belegzeile-Sortierung (C_DocLine_Sort)`
-- 2024-02-14T12:54:00.047Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=86, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540608 AND AD_Tree_ID=10
;

-- Node name: `Zählbestand Einkauf (fresh) (Fresh_QtyOnHand)`
-- 2024-02-14T12:54:00.048Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=87, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540587 AND AD_Tree_ID=10
;

-- Node name: `Batch (C_Async_Batch)`
-- 2024-02-14T12:54:00.048Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=88, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540570 AND AD_Tree_ID=10
;

-- Node name: `Batch Type (C_Async_Batch_Type)`
-- 2024-02-14T12:54:00.049Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=89, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540576 AND AD_Tree_ID=10
;

-- Node name: `Transparenz zur Status ESR Import in Bankauszug (x_esr_import_in_c_bankstatement_v)`
-- 2024-02-14T12:54:00.049Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=90, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540656 AND AD_Tree_ID=10
;

-- Node name: `Offene Zahlung - Skonto Zuordnung (de.metas.payment.process.C_Payment_MassWriteOff)`
-- 2024-02-14T12:54:00.050Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=91, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540649 AND AD_Tree_ID=10
;

-- Node name: `Gebinde`
-- 2024-02-14T12:54:00.051Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=92, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000005 AND AD_Tree_ID=10
;

-- Node name: `Parzelle`
-- 2024-02-14T12:54:00.052Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=93, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540886 AND AD_Tree_ID=10
;

-- Node name: `AD_Table_CreateFromInputFile (org.adempiere.ad.table.process.AD_Table_CreateFromInputFile)`
-- 2024-02-14T12:54:00.052Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=94, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540997 AND AD_Tree_ID=10
;

-- Node name: `Shipment restrictions (M_Shipment_Constraint)`
-- 2024-02-14T12:54:00.053Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=95, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540967 AND AD_Tree_ID=10
;

-- Node name: `Board Configuration (WEBUI_Board)`
-- 2024-02-14T12:54:00.053Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=96, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540987 AND AD_Tree_ID=10
;

-- Node name: `Test facility group (S_HumanResourceTestGroup)`
-- 2024-02-14T12:54:00.054Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=97, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542082 AND AD_Tree_ID=10
;

-- Node name: `Request (R_Request)`
-- 2024-02-14T12:54:00.054Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=98, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000023 AND AD_Tree_ID=10
;

-- Node name: `Create Membership Contracts (de.metas.contracts.order.process.C_Order_CreateForAllMembers)`
-- 2024-02-14T12:54:00.055Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=99, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541832 AND AD_Tree_ID=10
;

-- Reordering children of `Finance`
-- Node name: `PostFinance Kunden-Registrierungsnachricht`
-- 2024-02-14T13:01:29.004Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542138 AND AD_Tree_ID=10
;

-- Node name: `Open Items (Excel) (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2024-02-14T13:01:29.013Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542075 AND AD_Tree_ID=10
;

-- Node name: `Remittance Advice (REMADV) (C_RemittanceAdvice)`
-- 2024-02-14T13:01:29.014Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541584 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (GL_Journal)`
-- 2024-02-14T13:01:29.014Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (SAP) (SAP_GLJournal)`
-- 2024-02-14T13:01:29.016Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542031 AND AD_Tree_ID=10
;

-- Node name: `Bank Account (C_BP_BankAccount)`
-- 2024-02-14T13:01:29.017Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- Node name: `Import Bank Statement (I_BankStatement)`
-- 2024-02-14T13:01:29.018Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541297 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (C_BankStatement)`
-- 2024-02-14T13:01:29.019Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal (C_BankStatement)`
-- 2024-02-14T13:01:29.020Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- Node name: `Bankstatement Reference`
-- 2024-02-14T13:01:29.020Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- Node name: `Payment (C_Payment)`
-- 2024-02-14T13:01:29.021Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- Node name: `Manual Payment Allocations (C_AllocationHdr)`
-- 2024-02-14T13:01:29.022Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection (C_PaySelection)`
-- 2024-02-14T13:01:29.023Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation (C_Payment_Reservation)`
-- 2024-02-14T13:01:29.023Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation Capture (C_Payment_Reservation_Capture)`
-- 2024-02-14T13:01:29.024Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- Node name: `Dunning (C_DunningDoc)`
-- 2024-02-14T13:01:29.025Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- Node name: `Dunning Candidates (C_Dunning_Candidate)`
-- 2024-02-14T13:01:29.026Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- Node name: `Accounting Transactions (Fact_Acct_Transactions_View)`
-- 2024-02-14T13:01:29.027Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- Node name: `ESR Payment Import (ESR_Import)`
-- 2024-02-14T13:01:29.028Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- Node name: `Account Combination (C_ValidCombination)`
-- 2024-02-14T13:01:29.028Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- Node name: `Chart of Accounts (C_Element)`
-- 2024-02-14T13:01:29.029Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- Node name: `Element values (C_ElementValue)`
-- 2024-02-14T13:01:29.030Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541405 AND AD_Tree_ID=10
;

-- Node name: `Productcosts (M_Product)`
-- 2024-02-14T13:01:29.031Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- Node name: `Current Product Costs (M_Cost)`
-- 2024-02-14T13:01:29.031Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541455 AND AD_Tree_ID=10
;

-- Node name: `Cost Element (M_CostElement)`
-- 2024-02-14T13:01:29.032Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542067 AND AD_Tree_ID=10
;

-- Node name: `Products Without Initial Cost (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2024-02-14T13:01:29.033Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541710 AND AD_Tree_ID=10
;

-- Node name: `Products With Booked Quantity (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2024-02-14T13:01:29.033Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541717 AND AD_Tree_ID=10
;

-- Node name: `Cost Type (C_Cost_Type)`
-- 2024-02-14T13:01:29.034Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542052 AND AD_Tree_ID=10
;

-- Node name: `Cost Detail (M_CostDetail)`
-- 2024-02-14T13:01:29.035Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541454 AND AD_Tree_ID=10
;

-- Node name: `Costcenter Documents (Fact_Acct_ActivityChangeRequest)`
-- 2024-02-14T13:01:29.036Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- Node name: `Cost Center (C_Activity)`
-- 2024-02-14T13:01:29.037Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- Node name: `Referenz No (C_ReferenceNo)`
-- 2024-02-14T13:01:29.037Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541015 AND AD_Tree_ID=10
;

-- Node name: `Referenz No Type (C_ReferenceNo_Type)`
-- 2024-02-14T13:01:29.038Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541016 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export (DATEV_Export)`
-- 2024-02-14T13:01:29.039Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541042 AND AD_Tree_ID=10
;

-- Node name: `Cost Revaluation (M_CostRevaluation)`
-- 2024-02-14T13:01:29.040Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541977 AND AD_Tree_ID=10
;

-- Node name: `Matched Invoices (M_MatchInv)`
-- 2024-02-14T13:01:29.040Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=315 AND AD_Tree_ID=10
;

-- Node name: `UnPosted Documents (RV_UnPosted)`
-- 2024-02-14T13:01:29.041Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541368 AND AD_Tree_ID=10
;

-- Node name: `Import Datev Payment (I_Datev_Payment)`
-- 2024-02-14T13:01:29.042Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541120 AND AD_Tree_ID=10
;

-- Node name: `Enqueue all not posted documents (de.metas.acct.process.Documents_EnqueueNotPosted)`
-- 2024-02-14T13:01:29.043Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541125 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2024-02-14T13:01:29.044Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- Node name: `PayPal`
-- 2024-02-14T13:01:29.045Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541304 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2024-02-14T13:01:29.046Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2024-02-14T13:01:29.047Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement Import-File (C_BankStatement_Import_File)`
-- 2024-02-14T13:01:29.048Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542014 AND AD_Tree_ID=10
;

-- Node name: `Invoice Accounting Overrides (C_Invoice_Acct)`
-- 2024-02-14T13:01:29.049Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542034 AND AD_Tree_ID=10
;

-- Node name: `Foreign Exchange Contract (C_ForeignExchangeContract)`
-- 2024-02-14T13:01:29.049Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542042 AND AD_Tree_ID=10
;

-- Run mode: SWING_CLIENT

-- Value: ProcessPostFinanceCustomerRegistrationMessage
-- Classname: de.metas.postfinance.customerregistration.process.ProcessPostFinanceCustomerRegistrationMessage
-- 2024-02-14T17:31:14.015Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,585356,'Y','de.metas.postfinance.customerregistration.process.ProcessPostFinanceCustomerRegistrationMessage','N',TO_TIMESTAMP('2024-02-14 19:31:13.692','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Manuelles Verarbeiten ausgewählter Datensätze','json','N','N','xls','Java',TO_TIMESTAMP('2024-02-14 19:31:13.692','YYYY-MM-DD HH24:MI:SS.US'),100,'ProcessPostFinanceCustomerRegistrationMessage')
;

-- 2024-02-14T17:31:14.034Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585356 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: ProcessPostFinanceCustomerRegistrationMessage(de.metas.postfinance.customerregistration.process.ProcessPostFinanceCustomerRegistrationMessage)
-- Table: PostFinance_Customer_Registration_Message
-- Window: PostFinance Kunden-Registrierungsnachricht(541769,D)
-- EntityType: D
-- 2024-02-14T17:32:24.674Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585356,542391,541461,541769,TO_TIMESTAMP('2024-02-14 19:32:24.502','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y',TO_TIMESTAMP('2024-02-14 19:32:24.502','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','Y','N')
;

/*
 * #%L
 * de.metas.postfinance
 * %%
 * Copyright (C) 2024 metas GmbH
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

-- Process: ProcessPostFinanceCustomerRegistrationMessage(de.metas.postfinance.customerregistration.process.ProcessPostFinanceCustomerRegistrationMessage)
-- 2024-02-14T17:32:41.699Z
UPDATE AD_Process_Trl SET Name='Manually process selected records',Updated=TO_TIMESTAMP('2024-02-14 19:32:41.699','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585356
;
