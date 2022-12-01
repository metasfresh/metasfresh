-- 2021-08-27T09:47:06.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542855,541255,TO_TIMESTAMP('2021-08-27 12:47:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','RabbitMQ REST API',TO_TIMESTAMP('2021-08-27 12:47:06','YYYY-MM-DD HH24:MI:SS'),100,'RabbitMQ','RabbitMQ REST API')
;

-- 2021-08-27T09:47:06.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542855 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-09-01T11:33:42.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_Window_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,541803,541024,'N',TO_TIMESTAMP('2021-09-01 14:33:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','N','Y','N','N','N','N','N','N','N','N',0,'ExternalSystem_Config_RabbitMQ_HTTP','NP','L','ExternalSystem_Config_RabbitMQ_HTTP','DTI',TO_TIMESTAMP('2021-09-01 14:33:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T11:33:42.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=541803 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2021-09-01T11:33:42.558Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555513,TO_TIMESTAMP('2021-09-01 14:33:42','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table ExternalSystem_Config_RabbitMQ_HTTP',1,'Y','N','Y','Y','ExternalSystem_Config_RabbitMQ_HTTP','N',1000000,TO_TIMESTAMP('2021-09-01 14:33:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T11:33:42.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE SEQUENCE EXTERNALSYSTEM_CONFIG_RABBITMQ_HTTP_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2021-09-01T11:52:19.984Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575923,102,0,19,541803,'AD_Client_ID',TO_TIMESTAMP('2021-09-01 14:52:19','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','de.metas.externalsystem',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','Mandant',0,TO_TIMESTAMP('2021-09-01 14:52:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T11:52:19.987Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575923 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T11:52:20.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- 2021-09-01T11:52:20.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575924,113,0,30,541803,'AD_Org_ID',TO_TIMESTAMP('2021-09-01 14:52:20','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','de.metas.externalsystem',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','Y','N','Y','Y','N','N','Sektion',0,TO_TIMESTAMP('2021-09-01 14:52:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T11:52:20.990Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575924 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T11:52:20.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- 2021-09-01T11:52:21.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575925,245,0,16,541803,'Created',TO_TIMESTAMP('2021-09-01 14:52:21','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.externalsystem',29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt',0,TO_TIMESTAMP('2021-09-01 14:52:21','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T11:52:21.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575925 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T11:52:21.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- 2021-09-01T11:52:22.205Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575926,246,0,18,110,541803,'CreatedBy',TO_TIMESTAMP('2021-09-01 14:52:22','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.externalsystem',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch',0,TO_TIMESTAMP('2021-09-01 14:52:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T11:52:22.207Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575926 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T11:52:22.211Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- 2021-09-01T11:52:22.769Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575927,348,0,20,541803,'IsActive',TO_TIMESTAMP('2021-09-01 14:52:22','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','de.metas.externalsystem',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','Y','Aktiv',0,TO_TIMESTAMP('2021-09-01 14:52:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T11:52:22.771Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575927 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T11:52:22.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- 2021-09-01T11:52:23.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575928,607,0,16,541803,'Updated',TO_TIMESTAMP('2021-09-01 14:52:23','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.externalsystem',29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert',0,TO_TIMESTAMP('2021-09-01 14:52:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T11:52:23.355Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575928 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T11:52:23.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- 2021-09-01T11:52:23.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575929,608,0,18,110,541803,'UpdatedBy',TO_TIMESTAMP('2021-09-01 14:52:23','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.externalsystem',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2021-09-01 14:52:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T11:52:23.842Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575929 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T11:52:23.845Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2021-09-01T11:52:24.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579644,0,'ExternalSystem_Config_RabbitMQ_HTTP_ID',TO_TIMESTAMP('2021-09-01 14:52:24','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','ExternalSystem_Config_RabbitMQ_HTTP','ExternalSystem_Config_RabbitMQ_HTTP',TO_TIMESTAMP('2021-09-01 14:52:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T11:52:24.316Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579644 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-09-01T11:52:24.713Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575930,579644,0,13,541803,'ExternalSystem_Config_RabbitMQ_HTTP_ID',TO_TIMESTAMP('2021-09-01 14:52:24','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',10,'Y','N','N','N','N','N','Y','Y','N','N','Y','N','N','ExternalSystem_Config_RabbitMQ_HTTP',0,TO_TIMESTAMP('2021-09-01 14:52:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T11:52:24.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575930 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T11:52:24.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579644) 
;

-- 2021-09-01T11:52:25.210Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.ExternalSystem_Config_RabbitMQ_HTTP (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, ExternalSystem_Config_RabbitMQ_HTTP_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT ExternalSystem_Config_RabbitMQ_HTTP_Key PRIMARY KEY (ExternalSystem_Config_RabbitMQ_HTTP_ID))
;

-- 2021-09-01T11:52:25.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('externalsystem_config_rabbitmq_http','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2021-09-01T11:52:25.251Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('externalsystem_config_rabbitmq_http','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2021-09-01T11:52:25.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('externalsystem_config_rabbitmq_http','CreatedBy','NUMERIC(10)',null,null)
;

-- 2021-09-01T11:52:25.269Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('externalsystem_config_rabbitmq_http','IsActive','CHAR(1)',null,null)
;

-- 2021-09-01T11:52:25.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('externalsystem_config_rabbitmq_http','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2021-09-01T11:52:25.293Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('externalsystem_config_rabbitmq_http','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2021-09-01T11:52:25.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('externalsystem_config_rabbitmq_http','ExternalSystem_Config_RabbitMQ_HTTP_ID','NUMERIC(10)',null,null)
;

-- 2021-09-01T12:15:03.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='RabbitMQ REST API Konfig', PrintName='RabbitMQ REST API Konfig',Updated=TO_TIMESTAMP('2021-09-01 15:15:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579644 AND AD_Language='de_CH'
;

-- 2021-09-01T12:15:03.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579644,'de_CH') 
;

-- 2021-09-01T12:15:06.756Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='RabbitMQ REST API Konfig', PrintName='RabbitMQ REST API Konfig',Updated=TO_TIMESTAMP('2021-09-01 15:15:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579644 AND AD_Language='de_DE'
;

-- 2021-09-01T12:15:06.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579644,'de_DE') 
;

-- 2021-09-01T12:15:06.767Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579644,'de_DE') 
;

-- 2021-09-01T12:15:06.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='ExternalSystem_Config_RabbitMQ_HTTP_ID', Name='RabbitMQ REST API Konfig', Description=NULL, Help=NULL WHERE AD_Element_ID=579644
;

-- 2021-09-01T12:15:06.770Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalSystem_Config_RabbitMQ_HTTP_ID', Name='RabbitMQ REST API Konfig', Description=NULL, Help=NULL, AD_Element_ID=579644 WHERE UPPER(ColumnName)='EXTERNALSYSTEM_CONFIG_RABBITMQ_HTTP_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-09-01T12:15:06.771Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='ExternalSystem_Config_RabbitMQ_HTTP_ID', Name='RabbitMQ REST API Konfig', Description=NULL, Help=NULL WHERE AD_Element_ID=579644 AND IsCentrallyMaintained='Y'
;

-- 2021-09-01T12:15:06.772Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='RabbitMQ REST API Konfig', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579644) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579644)
;

-- 2021-09-01T12:15:06.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='RabbitMQ REST API Konfig', Name='RabbitMQ REST API Konfig' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579644)
;

-- 2021-09-01T12:15:06.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='RabbitMQ REST API Konfig', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579644
;

-- 2021-09-01T12:15:06.794Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='RabbitMQ REST API Konfig', Description=NULL, Help=NULL WHERE AD_Element_ID = 579644
;

-- 2021-09-01T12:15:06.795Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'RabbitMQ REST API Konfig', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579644
;

-- 2021-09-01T12:15:13.612Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='RabbitMQ REST API Config', PrintName='RabbitMQ REST API Config',Updated=TO_TIMESTAMP('2021-09-01 15:15:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579644 AND AD_Language='en_US'
;

-- 2021-09-01T12:15:13.613Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579644,'en_US') 
;

-- 2021-09-01T12:15:20.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='RabbitMQ REST API Konfig', PrintName='RabbitMQ REST API Konfig',Updated=TO_TIMESTAMP('2021-09-01 15:15:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579644 AND AD_Language='nl_NL'
;

-- 2021-09-01T12:15:20.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579644,'nl_NL') 
;

-- 2021-09-01T12:18:12.661Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579645,0,'RemoteURL',TO_TIMESTAMP('2021-09-01 15:18:12','YYYY-MM-DD HH24:MI:SS'),100,'URL of the RabbitMQ HTTP API server. metasfresh will append the path "/messages/publish" to the given URL','D','Y','Remote-URL','Remote-URL',TO_TIMESTAMP('2021-09-01 15:18:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T12:18:12.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579645 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-09-01T12:18:28.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='URL des RabbitMQ HTTP API Servers. metasfresh hängt an diese URL noch den Pfad "/messages/publish" an.',Updated=TO_TIMESTAMP('2021-09-01 15:18:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579645 AND AD_Language='de_CH'
;

-- 2021-09-01T12:18:28.220Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579645,'de_CH') 
;

-- 2021-09-01T12:18:32.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='URL des RabbitMQ HTTP API Servers. metasfresh hängt an diese URL noch den Pfad "/messages/publish" an.',Updated=TO_TIMESTAMP('2021-09-01 15:18:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579645 AND AD_Language='de_DE'
;

-- 2021-09-01T12:18:32.676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579645,'de_DE') 
;

-- 2021-09-01T12:18:32.683Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579645,'de_DE') 
;

-- 2021-09-01T12:18:32.684Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='RemoteURL', Name='Remote-URL', Description='URL des RabbitMQ HTTP API Servers. metasfresh hängt an diese URL noch den Pfad "/messages/publish" an.', Help=NULL WHERE AD_Element_ID=579645
;

-- 2021-09-01T12:18:32.684Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RemoteURL', Name='Remote-URL', Description='URL des RabbitMQ HTTP API Servers. metasfresh hängt an diese URL noch den Pfad "/messages/publish" an.', Help=NULL, AD_Element_ID=579645 WHERE UPPER(ColumnName)='REMOTEURL' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-09-01T12:18:32.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='RemoteURL', Name='Remote-URL', Description='URL des RabbitMQ HTTP API Servers. metasfresh hängt an diese URL noch den Pfad "/messages/publish" an.', Help=NULL WHERE AD_Element_ID=579645 AND IsCentrallyMaintained='Y'
;

-- 2021-09-01T12:18:32.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Remote-URL', Description='URL des RabbitMQ HTTP API Servers. metasfresh hängt an diese URL noch den Pfad "/messages/publish" an.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579645) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579645)
;

-- 2021-09-01T12:18:32.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Remote-URL', Description='URL des RabbitMQ HTTP API Servers. metasfresh hängt an diese URL noch den Pfad "/messages/publish" an.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579645
;

-- 2021-09-01T12:18:32.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Remote-URL', Description='URL des RabbitMQ HTTP API Servers. metasfresh hängt an diese URL noch den Pfad "/messages/publish" an.', Help=NULL WHERE AD_Element_ID = 579645
;

-- 2021-09-01T12:18:32.700Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Remote-URL', Description = 'URL des RabbitMQ HTTP API Servers. metasfresh hängt an diese URL noch den Pfad "/messages/publish" an.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579645
;

-- 2021-09-01T12:18:44.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='URL des RabbitMQ HTTP API Servers. metasfresh hängt an diese URL noch den Pfad "/messages/publish" an.',Updated=TO_TIMESTAMP('2021-09-01 15:18:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579645 AND AD_Language='nl_NL'
;

-- 2021-09-01T12:18:44.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579645,'nl_NL') 
;

-- 2021-09-01T12:20:01.015Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575931,579645,0,10,541803,'RemoteURL',TO_TIMESTAMP('2021-09-01 15:20:00','YYYY-MM-DD HH24:MI:SS'),100,'N','URL des RabbitMQ HTTP API Servers. metasfresh hängt an diese URL noch den Pfad "/messages/publish" an.','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Remote-URL','NP',0,0,TO_TIMESTAMP('2021-09-01 15:20:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T12:20:01.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575931 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T12:20:01.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579645) 
;

-- 2021-09-01T12:21:12.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579648,0,'Routing_Key',TO_TIMESTAMP('2021-09-01 15:21:12','YYYY-MM-DD HH24:MI:SS'),100,'Messages are send to the default exchange with the given "routing_key". We assume that RabbitMQ contains a queue with the routing_key''s name.','D','Y','Routing-Key','Routing-Key',TO_TIMESTAMP('2021-09-01 15:21:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T12:21:12.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579648 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-09-01T12:21:20.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Nachrichten werden mit dem gegebenen rounting_key an den Standard-Exchange gesendet. RabbitMQ leitet diese Nachrichten dann an eine Queue mit dem routing_key-Namen weiter.',Updated=TO_TIMESTAMP('2021-09-01 15:21:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579648 AND AD_Language='de_CH'
;

-- 2021-09-01T12:21:20.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579648,'de_CH') 
;

-- 2021-09-01T12:21:23.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Nachrichten werden mit dem gegebenen rounting_key an den Standard-Exchange gesendet. RabbitMQ leitet diese Nachrichten dann an eine Queue mit dem routing_key-Namen weiter.',Updated=TO_TIMESTAMP('2021-09-01 15:21:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579648 AND AD_Language='de_DE'
;

-- 2021-09-01T12:21:23.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579648,'de_DE') 
;

-- 2021-09-01T12:21:23.657Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579648,'de_DE') 
;

-- 2021-09-01T12:21:23.658Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Routing_Key', Name='Routing-Key', Description='Nachrichten werden mit dem gegebenen rounting_key an den Standard-Exchange gesendet. RabbitMQ leitet diese Nachrichten dann an eine Queue mit dem routing_key-Namen weiter.', Help=NULL WHERE AD_Element_ID=579648
;

-- 2021-09-01T12:21:23.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Routing_Key', Name='Routing-Key', Description='Nachrichten werden mit dem gegebenen rounting_key an den Standard-Exchange gesendet. RabbitMQ leitet diese Nachrichten dann an eine Queue mit dem routing_key-Namen weiter.', Help=NULL, AD_Element_ID=579648 WHERE UPPER(ColumnName)='ROUTING_KEY' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-09-01T12:21:23.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Routing_Key', Name='Routing-Key', Description='Nachrichten werden mit dem gegebenen rounting_key an den Standard-Exchange gesendet. RabbitMQ leitet diese Nachrichten dann an eine Queue mit dem routing_key-Namen weiter.', Help=NULL WHERE AD_Element_ID=579648 AND IsCentrallyMaintained='Y'
;

-- 2021-09-01T12:21:23.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Routing-Key', Description='Nachrichten werden mit dem gegebenen rounting_key an den Standard-Exchange gesendet. RabbitMQ leitet diese Nachrichten dann an eine Queue mit dem routing_key-Namen weiter.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579648) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579648)
;

-- 2021-09-01T12:21:23.671Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Routing-Key', Description='Nachrichten werden mit dem gegebenen rounting_key an den Standard-Exchange gesendet. RabbitMQ leitet diese Nachrichten dann an eine Queue mit dem routing_key-Namen weiter.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579648
;

-- 2021-09-01T12:21:23.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Routing-Key', Description='Nachrichten werden mit dem gegebenen rounting_key an den Standard-Exchange gesendet. RabbitMQ leitet diese Nachrichten dann an eine Queue mit dem routing_key-Namen weiter.', Help=NULL WHERE AD_Element_ID = 579648
;

-- 2021-09-01T12:21:23.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Routing-Key', Description = 'Nachrichten werden mit dem gegebenen rounting_key an den Standard-Exchange gesendet. RabbitMQ leitet diese Nachrichten dann an eine Queue mit dem routing_key-Namen weiter.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579648
;

-- 2021-09-01T12:21:26.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Nachrichten werden mit dem gegebenen rounting_key an den Standard-Exchange gesendet. RabbitMQ leitet diese Nachrichten dann an eine Queue mit dem routing_key-Namen weiter.',Updated=TO_TIMESTAMP('2021-09-01 15:21:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579648 AND AD_Language='nl_NL'
;

-- 2021-09-01T12:21:26.644Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579648,'nl_NL') 
;

-- 2021-09-01T12:21:59.070Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575932,579648,0,10,541803,'Routing_Key',TO_TIMESTAMP('2021-09-01 15:21:58','YYYY-MM-DD HH24:MI:SS'),100,'N','Nachrichten werden mit dem gegebenen rounting_key an den Standard-Exchange gesendet. RabbitMQ leitet diese Nachrichten dann an eine Queue mit dem routing_key-Namen weiter.','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Routing-Key',0,0,TO_TIMESTAMP('2021-09-01 15:21:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T12:21:59.071Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575932 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T12:21:59.073Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579648) 
;

-- 2021-09-01T12:22:00.322Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_RabbitMQ_HTTP','ALTER TABLE public.ExternalSystem_Config_RabbitMQ_HTTP ADD COLUMN Routing_Key VARCHAR(255) NOT NULL')
;

-- 2021-09-01T12:22:06.787Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_RabbitMQ_HTTP','ALTER TABLE public.ExternalSystem_Config_RabbitMQ_HTTP ADD COLUMN RemoteURL VARCHAR(255) NOT NULL')
;

-- 2021-09-01T12:22:52.533Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=40,Updated=TO_TIMESTAMP('2021-09-01 15:22:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575931
;

-- 2021-09-01T12:22:54.829Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('externalsystem_config_rabbitmq_http','RemoteURL','VARCHAR(255)',null,null)
;

-- 2021-09-01T12:24:11.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579649,0,'IsSyncBPartnersToRabbitMQ',TO_TIMESTAMP('2021-09-01 15:24:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','IsSyncBPartnersToRabbitMQ','IsSyncBPartnersToRabbitMQ',TO_TIMESTAMP('2021-09-01 15:24:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T12:24:11.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579649 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-09-01T12:24:28.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, dann können ausgewählte Geschäftspartner mit einer Aktion im Geschäftspartner-Fenster initial zu RabbitMQ gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.', Name='Geschäftspartner senden', PrintName='Geschäftspartner senden',Updated=TO_TIMESTAMP('2021-09-01 15:24:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579649 AND AD_Language='de_CH'
;

-- 2021-09-01T12:24:28.739Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579649,'de_CH') 
;

-- 2021-09-01T12:24:37.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, dann können ausgewählte Geschäftspartner mit einer Aktion im Geschäftspartner-Fenster initial zu RabbitMQ gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.', Name='Geschäftspartner senden', PrintName='Geschäftspartner senden',Updated=TO_TIMESTAMP('2021-09-01 15:24:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579649 AND AD_Language='de_DE'
;

-- 2021-09-01T12:24:37.491Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579649,'de_DE') 
;

-- 2021-09-01T12:24:37.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579649,'de_DE') 
;

-- 2021-09-01T12:24:37.496Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsSyncBPartnersToRabbitMQ', Name='Geschäftspartner senden', Description='Wenn angehakt, dann können ausgewählte Geschäftspartner mit einer Aktion im Geschäftspartner-Fenster initial zu RabbitMQ gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.', Help=NULL WHERE AD_Element_ID=579649
;

-- 2021-09-01T12:24:37.496Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSyncBPartnersToRabbitMQ', Name='Geschäftspartner senden', Description='Wenn angehakt, dann können ausgewählte Geschäftspartner mit einer Aktion im Geschäftspartner-Fenster initial zu RabbitMQ gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.', Help=NULL, AD_Element_ID=579649 WHERE UPPER(ColumnName)='ISSYNCBPARTNERSTORABBITMQ' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-09-01T12:24:37.497Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSyncBPartnersToRabbitMQ', Name='Geschäftspartner senden', Description='Wenn angehakt, dann können ausgewählte Geschäftspartner mit einer Aktion im Geschäftspartner-Fenster initial zu RabbitMQ gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.', Help=NULL WHERE AD_Element_ID=579649 AND IsCentrallyMaintained='Y'
;

-- 2021-09-01T12:24:37.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Geschäftspartner senden', Description='Wenn angehakt, dann können ausgewählte Geschäftspartner mit einer Aktion im Geschäftspartner-Fenster initial zu RabbitMQ gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579649) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579649)
;

-- 2021-09-01T12:24:37.517Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Geschäftspartner senden', Name='Geschäftspartner senden' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579649)
;

-- 2021-09-01T12:24:37.518Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Geschäftspartner senden', Description='Wenn angehakt, dann können ausgewählte Geschäftspartner mit einer Aktion im Geschäftspartner-Fenster initial zu RabbitMQ gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579649
;

-- 2021-09-01T12:24:37.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Geschäftspartner senden', Description='Wenn angehakt, dann können ausgewählte Geschäftspartner mit einer Aktion im Geschäftspartner-Fenster initial zu RabbitMQ gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.', Help=NULL WHERE AD_Element_ID = 579649
;

-- 2021-09-01T12:24:37.520Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Geschäftspartner senden', Description = 'Wenn angehakt, dann können ausgewählte Geschäftspartner mit einer Aktion im Geschäftspartner-Fenster initial zu RabbitMQ gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579649
;

-- 2021-09-01T12:24:44.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='If checked, then business selected partners can be initially send to RabbitMQ via an action in the business partner window. Once initially send, they will from there onwards be automatically send whenever changed in metasfresh.',Updated=TO_TIMESTAMP('2021-09-01 15:24:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579649 AND AD_Language='en_US'
;

-- 2021-09-01T12:24:44.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579649,'en_US') 
;

-- 2021-09-01T12:24:56.498Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn angehakt, dann können ausgewählte Geschäftspartner mit einer Aktion im Geschäftspartner-Fenster initial zu RabbitMQ gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.', Name='Geschäftspartner senden', PrintName='Geschäftspartner senden',Updated=TO_TIMESTAMP('2021-09-01 15:24:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579649 AND AD_Language='nl_NL'
;

-- 2021-09-01T12:24:56.499Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579649,'nl_NL') 
;

-- 2021-09-01T12:25:28.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575933,579649,0,20,541803,'IsSyncBPartnersToRabbitMQ',TO_TIMESTAMP('2021-09-01 15:25:27','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','Wenn angehakt, dann können ausgewählte Geschäftspartner mit einer Aktion im Geschäftspartner-Fenster initial zu RabbitMQ gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.','de.metas.externalsystem',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Geschäftspartner senden',0,0,TO_TIMESTAMP('2021-09-01 15:25:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T12:25:28.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575933 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T12:25:28.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579649) 
;

-- 2021-09-01T12:25:32.065Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_RabbitMQ_HTTP','ALTER TABLE public.ExternalSystem_Config_RabbitMQ_HTTP ADD COLUMN IsSyncBPartnersToRabbitMQ CHAR(1) DEFAULT ''Y'' CHECK (IsSyncBPartnersToRabbitMQ IN (''Y'',''N'')) NOT NULL')
;

-- 2021-09-01T12:26:59.180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575935,578788,0,10,541803,'ExternalSystemValue',TO_TIMESTAMP('2021-09-01 15:26:59','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Suchschlüssel','NP',0,0,TO_TIMESTAMP('2021-09-01 15:26:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T12:26:59.182Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575935 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T12:26:59.183Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578788) 
;

-- 2021-09-01T12:27:00.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_RabbitMQ_HTTP','ALTER TABLE public.ExternalSystem_Config_RabbitMQ_HTTP ADD COLUMN ExternalSystemValue VARCHAR(255) NOT NULL')
;

-- 2021-09-01T12:30:52.999Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540631,0,541803,TO_TIMESTAMP('2021-09-01 15:30:52','YYYY-MM-DD HH24:MI:SS'),100,'Unique constraint on parent config id','D','Y','Y','IDX S_ExternalSystemRabbitMQ_unique_parent_id','N',TO_TIMESTAMP('2021-09-01 15:30:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T12:30:53Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540631 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-09-01T12:32:51.667Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575936,578728,0,19,541803,'ExternalSystem_Config_ID',TO_TIMESTAMP('2021-09-01 15:32:51','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'External System Config',0,0,TO_TIMESTAMP('2021-09-01 15:32:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T12:32:51.668Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575936 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T12:32:51.671Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578728) 
;

-- 2021-09-01T12:32:54.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_RabbitMQ_HTTP','ALTER TABLE public.ExternalSystem_Config_RabbitMQ_HTTP ADD COLUMN ExternalSystem_Config_ID NUMERIC(10) NOT NULL')
;

-- 2021-09-01T12:32:54.469Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE ExternalSystem_Config_RabbitMQ_HTTP ADD CONSTRAINT ExternalSystemConfig_ExternalSystemConfigRabbitMQHTTP FOREIGN KEY (ExternalSystem_Config_ID) REFERENCES public.ExternalSystem_Config DEFERRABLE INITIALLY DEFERRED
;

-- 2021-09-01T12:33:05.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2021-09-01 15:33:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575936
;

-- 2021-09-01T12:33:07.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('externalsystem_config_rabbitmq_http','ExternalSystem_Config_ID','NUMERIC(10)',null,null)
;

-- 2021-09-01T12:33:37.271Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,575936,541149,540631,0,TO_TIMESTAMP('2021-09-01 15:33:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2021-09-01 15:33:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T12:34:21.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540632,0,541803,TO_TIMESTAMP('2021-09-01 15:34:21','YYYY-MM-DD HH24:MI:SS'),100,'Unique index external system rabbit value','de.metas.externalsystem','Y','Y','IDX S_ExternalSystemRabbitMQ_unique_value','N',TO_TIMESTAMP('2021-09-01 15:34:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T12:34:21.761Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540632 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-09-01T12:34:41.080Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,575935,541150,540632,0,TO_TIMESTAMP('2021-09-01 15:34:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y',10,TO_TIMESTAMP('2021-09-01 15:34:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T12:39:03.295Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,575936,579644,0,544410,541803,541024,'Y',TO_TIMESTAMP('2021-09-01 15:39:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','N','N','ExternalSystem_Config_RabbitMQ_HTTP','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'RabbitMQ REST API Konfig','N',50,1,TO_TIMESTAMP('2021-09-01 15:39:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T12:39:03.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=544410 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2021-09-01T12:39:03.302Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(579644) 
;

-- 2021-09-01T12:39:03.315Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(544410)
;

-- 2021-09-01T12:39:20.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET SeqNo=35,Updated=TO_TIMESTAMP('2021-09-01 15:39:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544410
;

-- 2021-09-01T12:39:35.666Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575923,656727,0,544410,TO_TIMESTAMP('2021-09-01 15:39:35','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.externalsystem','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2021-09-01 15:39:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T12:39:35.668Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656727 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T12:39:35.671Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2021-09-01T12:39:35.980Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656727
;

-- 2021-09-01T12:39:35.981Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656727)
;

-- 2021-09-01T12:39:36.106Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575924,656728,0,544410,TO_TIMESTAMP('2021-09-01 15:39:35','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.externalsystem','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2021-09-01 15:39:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T12:39:36.108Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656728 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T12:39:36.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2021-09-01T12:39:36.302Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656728
;

-- 2021-09-01T12:39:36.302Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656728)
;

-- 2021-09-01T12:39:36.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575927,656729,0,544410,TO_TIMESTAMP('2021-09-01 15:39:36','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.externalsystem','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2021-09-01 15:39:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T12:39:36.407Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656729 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T12:39:36.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2021-09-01T12:39:36.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656729
;

-- 2021-09-01T12:39:36.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656729)
;

-- 2021-09-01T12:39:36.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575930,656730,0,544410,TO_TIMESTAMP('2021-09-01 15:39:36','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','RabbitMQ REST API Konfig',TO_TIMESTAMP('2021-09-01 15:39:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T12:39:36.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656730 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T12:39:36.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579644) 
;

-- 2021-09-01T12:39:36.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656730
;

-- 2021-09-01T12:39:36.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656730)
;

-- 2021-09-01T12:39:36.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575931,656731,0,544410,TO_TIMESTAMP('2021-09-01 15:39:36','YYYY-MM-DD HH24:MI:SS'),100,'URL des RabbitMQ HTTP API Servers. metasfresh hängt an diese URL noch den Pfad "/messages/publish" an.',255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Remote-URL',TO_TIMESTAMP('2021-09-01 15:39:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T12:39:36.906Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656731 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T12:39:36.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579645) 
;

-- 2021-09-01T12:39:36.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656731
;

-- 2021-09-01T12:39:36.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656731)
;

-- 2021-09-01T12:39:36.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575932,656732,0,544410,TO_TIMESTAMP('2021-09-01 15:39:36','YYYY-MM-DD HH24:MI:SS'),100,'Nachrichten werden mit dem gegebenen rounting_key an den Standard-Exchange gesendet. RabbitMQ leitet diese Nachrichten dann an eine Queue mit dem routing_key-Namen weiter.',255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Routing-Key',TO_TIMESTAMP('2021-09-01 15:39:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T12:39:36.999Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656732 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T12:39:37.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579648) 
;

-- 2021-09-01T12:39:37.005Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656732
;

-- 2021-09-01T12:39:37.005Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656732)
;

-- 2021-09-01T12:39:37.100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575933,656733,0,544410,TO_TIMESTAMP('2021-09-01 15:39:37','YYYY-MM-DD HH24:MI:SS'),100,'Wenn angehakt, dann können ausgewählte Geschäftspartner mit einer Aktion im Geschäftspartner-Fenster initial zu RabbitMQ gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.',1,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Geschäftspartner senden',TO_TIMESTAMP('2021-09-01 15:39:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T12:39:37.102Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656733 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T12:39:37.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579649) 
;

-- 2021-09-01T12:39:37.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656733
;

-- 2021-09-01T12:39:37.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656733)
;

-- 2021-09-01T12:39:37.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575935,656734,0,544410,TO_TIMESTAMP('2021-09-01 15:39:37','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Suchschlüssel',TO_TIMESTAMP('2021-09-01 15:39:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T12:39:37.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656734 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T12:39:37.202Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578788) 
;

-- 2021-09-01T12:39:37.203Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656734
;

-- 2021-09-01T12:39:37.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656734)
;

-- 2021-09-01T12:39:37.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575936,656735,0,544410,TO_TIMESTAMP('2021-09-01 15:39:37','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','External System Config',TO_TIMESTAMP('2021-09-01 15:39:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T12:39:37.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656735 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T12:39:37.298Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578728) 
;

-- 2021-09-01T12:39:37.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656735
;

-- 2021-09-01T12:39:37.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656735)
;

-- 2021-09-01T13:18:40.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,544410,543493,TO_TIMESTAMP('2021-09-01 16:18:40','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-09-01 16:18:40','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2021-09-01T13:18:40.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=543493 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2021-09-01T13:18:58.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,544336,543493,TO_TIMESTAMP('2021-09-01 16:18:58','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-09-01 16:18:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T13:19:00.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,544337,543493,TO_TIMESTAMP('2021-09-01 16:18:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2021-09-01 16:18:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T13:19:15.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,544336,546532,TO_TIMESTAMP('2021-09-01 16:19:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2021-09-01 16:19:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T13:19:48.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,656734,0,544410,589824,546532,'F',TO_TIMESTAMP('2021-09-01 16:19:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Suchschlüssel',10,0,0,TO_TIMESTAMP('2021-09-01 16:19:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T13:20:48.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2021-09-01 16:20:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=589824
;

-- 2021-09-01T13:22:13.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,656731,0,544410,589825,546532,'F',TO_TIMESTAMP('2021-09-01 16:22:13','YYYY-MM-DD HH24:MI:SS'),100,'URL des RabbitMQ HTTP API Servers. metasfresh hängt an diese URL noch den Pfad "/messages/publish" an.','Y','N','N','Y','N','N','N',0,'Remote-URL',20,0,0,TO_TIMESTAMP('2021-09-01 16:22:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T13:24:28.189Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,656732,0,544410,589826,546532,'F',TO_TIMESTAMP('2021-09-01 16:24:28','YYYY-MM-DD HH24:MI:SS'),100,'Nachrichten werden mit dem gegebenen rounting_key an den Standard-Exchange gesendet. RabbitMQ leitet diese Nachrichten dann an eine Queue mit dem routing_key-Namen weiter.','Y','N','N','Y','N','N','N',0,'Routing-Key',30,0,0,TO_TIMESTAMP('2021-09-01 16:24:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T13:24:41.455Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,656733,0,544410,589827,546532,'F',TO_TIMESTAMP('2021-09-01 16:24:41','YYYY-MM-DD HH24:MI:SS'),100,'Wenn angehakt, dann können ausgewählte Geschäftspartner mit einer Aktion im Geschäftspartner-Fenster initial zu RabbitMQ gesendet werden. Nachdem sie einmal gesendet wurden, werden sie bei Änderungen automatisch erneut gesendet.','Y','N','N','Y','N','N','N',0,'Geschäftspartner senden',40,0,0,TO_TIMESTAMP('2021-09-01 16:24:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T13:24:56.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,544337,546533,TO_TIMESTAMP('2021-09-01 16:24:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','flag',10,TO_TIMESTAMP('2021-09-01 16:24:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T13:25:01.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,544337,546534,TO_TIMESTAMP('2021-09-01 16:25:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2021-09-01 16:25:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T13:25:18.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,656729,0,544410,589828,546533,'F',TO_TIMESTAMP('2021-09-01 16:25:18','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2021-09-01 16:25:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T13:25:35.948Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,656728,0,544410,589829,546534,'F',TO_TIMESTAMP('2021-09-01 16:25:35','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2021-09-01 16:25:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T13:25:44.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,656727,0,544410,589830,546534,'F',TO_TIMESTAMP('2021-09-01 16:25:44','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2021-09-01 16:25:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T13:26:32.177Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2021-09-01 16:26:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=589824
;

-- 2021-09-01T13:26:32.180Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-09-01 16:26:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=589825
;

-- 2021-09-01T13:26:32.182Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-09-01 16:26:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=589826
;

-- 2021-09-01T13:26:32.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-09-01 16:26:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=589827
;

-- 2021-09-01T13:45:23.183Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,541804,'N',TO_TIMESTAMP('2021-09-01 16:45:23','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Data_Export_Audit','NP','L','Data_Export_Audit','DTI',TO_TIMESTAMP('2021-09-01 16:45:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T13:45:23.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=541804 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2021-09-01T13:45:23.314Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555514,TO_TIMESTAMP('2021-09-01 16:45:23','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table Data_Export_Audit',1,'Y','N','Y','Y','Data_Export_Audit','N',1000000,TO_TIMESTAMP('2021-09-01 16:45:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T13:45:23.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE SEQUENCE DATA_EXPORT_AUDIT_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2021-09-01T14:01:34.575Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575938,102,0,19,541804,'AD_Client_ID',TO_TIMESTAMP('2021-09-01 17:01:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','Mandant',0,TO_TIMESTAMP('2021-09-01 17:01:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T14:01:34.579Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575938 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T14:01:34.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- 2021-09-01T14:01:35.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575939,113,0,30,541804,'AD_Org_ID',TO_TIMESTAMP('2021-09-01 17:01:35','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','Y','N','Y','Y','N','N','Sektion',0,TO_TIMESTAMP('2021-09-01 17:01:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T14:01:35.339Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575939 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T14:01:35.342Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- 2021-09-01T14:01:35.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575940,245,0,16,541804,'Created',TO_TIMESTAMP('2021-09-01 17:01:35','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt',0,TO_TIMESTAMP('2021-09-01 17:01:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T14:01:35.933Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575940 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T14:01:35.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- 2021-09-01T14:01:36.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575941,246,0,18,110,541804,'CreatedBy',TO_TIMESTAMP('2021-09-01 17:01:36','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch',0,TO_TIMESTAMP('2021-09-01 17:01:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T14:01:36.511Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575941 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T14:01:36.514Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- 2021-09-01T14:01:37.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575942,348,0,20,541804,'IsActive',TO_TIMESTAMP('2021-09-01 17:01:36','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','Y','Aktiv',0,TO_TIMESTAMP('2021-09-01 17:01:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T14:01:37.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575942 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T14:01:37.088Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- 2021-09-01T14:01:37.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575943,607,0,16,541804,'Updated',TO_TIMESTAMP('2021-09-01 17:01:37','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert',0,TO_TIMESTAMP('2021-09-01 17:01:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T14:01:37.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575943 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T14:01:37.678Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- 2021-09-01T14:01:38.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575944,608,0,18,110,541804,'UpdatedBy',TO_TIMESTAMP('2021-09-01 17:01:38','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2021-09-01 17:01:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T14:01:38.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575944 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T14:01:38.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2021-09-01T14:01:38.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579650,0,'Data_Export_Audit_ID',TO_TIMESTAMP('2021-09-01 17:01:38','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Data_Export_Audit','Data_Export_Audit',TO_TIMESTAMP('2021-09-01 17:01:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T14:01:38.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579650 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-09-01T14:01:39.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575945,579650,0,13,541804,'Data_Export_Audit_ID',TO_TIMESTAMP('2021-09-01 17:01:38','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','N','N','N','N','N','Y','Y','N','N','Y','N','N','Data_Export_Audit',0,TO_TIMESTAMP('2021-09-01 17:01:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T14:01:39.053Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575945 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T14:01:39.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579650) 
;

-- 2021-09-01T14:01:39.554Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.Data_Export_Audit (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Data_Export_Audit_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT Data_Export_Audit_Key PRIMARY KEY (Data_Export_Audit_ID))
;

-- 2021-09-01T14:01:39.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('data_export_audit','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2021-09-01T14:01:39.597Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('data_export_audit','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2021-09-01T14:01:39.606Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('data_export_audit','CreatedBy','NUMERIC(10)',null,null)
;

-- 2021-09-01T14:01:39.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('data_export_audit','IsActive','CHAR(1)',null,null)
;

-- 2021-09-01T14:01:39.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('data_export_audit','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2021-09-01T14:01:39.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('data_export_audit','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2021-09-01T14:01:39.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('data_export_audit','Data_Export_Audit_ID','NUMERIC(10)',null,null)
;

-- 2021-09-01T14:04:23.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575946,126,0,19,541804,'AD_Table_ID',TO_TIMESTAMP('2021-09-01 17:04:23','YYYY-MM-DD HH24:MI:SS'),100,'N','Database Table information','D',0,10,'The Database Table provides the information of the table definition','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'DB-Tabelle',0,0,TO_TIMESTAMP('2021-09-01 17:04:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T14:04:23.192Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575946 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T14:04:23.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(126) 
;

-- 2021-09-01T14:04:25.774Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('Data_Export_Audit','ALTER TABLE public.Data_Export_Audit ADD COLUMN AD_Table_ID NUMERIC(10) NOT NULL')
;

-- 2021-09-01T14:04:25.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE Data_Export_Audit ADD CONSTRAINT ADTable_DataExportAudit FOREIGN KEY (AD_Table_ID) REFERENCES public.AD_Table DEFERRABLE INITIALLY DEFERRED
;

-- 2021-09-01T14:06:30.489Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575947,538,0,28,541804,'Record_ID',TO_TIMESTAMP('2021-09-01 17:06:30','YYYY-MM-DD HH24:MI:SS'),100,'N','Direct internal record ID','D',0,10,'The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Datensatz-ID',0,0,TO_TIMESTAMP('2021-09-01 17:06:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T14:06:30.492Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575947 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T14:06:30.497Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(538) 
;

-- 2021-09-01T14:06:32.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('Data_Export_Audit','ALTER TABLE public.Data_Export_Audit ADD COLUMN Record_ID NUMERIC(10) NOT NULL')
;

-- 2021-09-01T14:07:21.016Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579651,0,'Data_Export_Audit_Parent_ID',TO_TIMESTAMP('2021-09-01 17:07:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Data_Export_Audit_Parent_ID','Data_Export_Audit_Parent_ID',TO_TIMESTAMP('2021-09-01 17:07:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T14:07:21.019Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579651 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-09-01T14:10:18.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541388,TO_TIMESTAMP('2021-09-01 17:10:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Data_Export_Audit',TO_TIMESTAMP('2021-09-01 17:10:18','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-09-01T14:10:18.248Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541388 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-09-01T14:10:47.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,575945,0,541388,541804,TO_TIMESTAMP('2021-09-01 17:10:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-09-01 17:10:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T14:19:07.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2021-09-01 17:19:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575945
;

-- 2021-09-01T14:19:32.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575948,579651,0,30,541388,541804,'Data_Export_Audit_Parent_ID',TO_TIMESTAMP('2021-09-01 17:19:31','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Data_Export_Audit_Parent_ID',0,0,TO_TIMESTAMP('2021-09-01 17:19:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T14:19:32.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575948 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T14:19:32.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579651) 
;

-- 2021-09-01T14:19:37.657Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('Data_Export_Audit','ALTER TABLE public.Data_Export_Audit ADD COLUMN Data_Export_Audit_Parent_ID NUMERIC(10)')
;

-- 2021-09-01T14:19:37.665Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE Data_Export_Audit ADD CONSTRAINT DataExportAuditParent_DataExportAudit FOREIGN KEY (Data_Export_Audit_Parent_ID) REFERENCES public.Data_Export_Audit DEFERRABLE INITIALLY DEFERRED
;

-- 2021-09-01T14:26:12.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540633,0,541804,TO_TIMESTAMP('2021-09-01 17:26:12','YYYY-MM-DD HH24:MI:SS'),100,'Unique constraint on ad_table_id','D','Y','Y','IDX_DataExportAudit_unique_table_id','N',TO_TIMESTAMP('2021-09-01 17:26:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T14:26:12.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540633 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-09-01T14:26:35.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,575946,541151,540633,0,TO_TIMESTAMP('2021-09-01 17:26:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2021-09-01 17:26:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T14:27:32.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540634,0,541804,TO_TIMESTAMP('2021-09-01 17:27:32','YYYY-MM-DD HH24:MI:SS'),100,'Unique constraint on record id','D','Y','Y','IDX_DataExportAudit_unique_record_id','N',TO_TIMESTAMP('2021-09-01 17:27:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T14:27:32.639Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540634 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-09-01T14:27:49.209Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,575947,541152,540634,0,TO_TIMESTAMP('2021-09-01 17:27:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2021-09-01 17:27:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T14:28:30.741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540635,0,541804,TO_TIMESTAMP('2021-09-01 17:28:30','YYYY-MM-DD HH24:MI:SS'),100,'Unique constraint on parent data export audit id','D','Y','Y','IDX_DataExportAudit_unique_parent_id','N',TO_TIMESTAMP('2021-09-01 17:28:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T14:28:30.743Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540635 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-09-01T14:28:52.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,575945,541153,540635,0,TO_TIMESTAMP('2021-09-01 17:28:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2021-09-01 17:28:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T14:43:48.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,541805,'N',TO_TIMESTAMP('2021-09-01 17:43:48','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Data_Export_Audit_Log','NP','L','Data_Export_Audit_Log','DTI',TO_TIMESTAMP('2021-09-01 17:43:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T14:43:48.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=541805 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2021-09-01T14:43:48.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555515,TO_TIMESTAMP('2021-09-01 17:43:48','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table Data_Export_Audit_Log',1,'Y','N','Y','Y','Data_Export_Audit_Log','N',1000000,TO_TIMESTAMP('2021-09-01 17:43:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T14:43:48.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE SEQUENCE DATA_EXPORT_AUDIT_LOG_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2021-09-01T14:46:25.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575949,102,0,19,541805,'AD_Client_ID',TO_TIMESTAMP('2021-09-01 17:46:25','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','Mandant',0,TO_TIMESTAMP('2021-09-01 17:46:25','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T14:46:25.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575949 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T14:46:25.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- 2021-09-01T14:46:26.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575950,113,0,30,541805,'AD_Org_ID',TO_TIMESTAMP('2021-09-01 17:46:26','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','Y','N','Y','Y','N','N','Sektion',0,TO_TIMESTAMP('2021-09-01 17:46:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T14:46:26.227Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575950 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T14:46:26.230Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- 2021-09-01T14:46:26.892Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575951,245,0,16,541805,'Created',TO_TIMESTAMP('2021-09-01 17:46:26','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt',0,TO_TIMESTAMP('2021-09-01 17:46:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T14:46:26.894Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575951 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T14:46:26.897Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- 2021-09-01T14:46:27.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575952,246,0,18,110,541805,'CreatedBy',TO_TIMESTAMP('2021-09-01 17:46:27','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch',0,TO_TIMESTAMP('2021-09-01 17:46:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T14:46:27.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575952 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T14:46:27.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- 2021-09-01T14:46:28.028Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575953,348,0,20,541805,'IsActive',TO_TIMESTAMP('2021-09-01 17:46:27','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','Y','Aktiv',0,TO_TIMESTAMP('2021-09-01 17:46:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T14:46:28.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575953 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T14:46:28.034Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- 2021-09-01T14:46:28.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575954,607,0,16,541805,'Updated',TO_TIMESTAMP('2021-09-01 17:46:28','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert',0,TO_TIMESTAMP('2021-09-01 17:46:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T14:46:28.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575954 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T14:46:28.613Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- 2021-09-01T14:46:29.094Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575955,608,0,18,110,541805,'UpdatedBy',TO_TIMESTAMP('2021-09-01 17:46:29','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2021-09-01 17:46:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T14:46:29.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575955 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T14:46:29.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2021-09-01T14:46:29.564Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579652,0,'Data_Export_Audit_Log_ID',TO_TIMESTAMP('2021-09-01 17:46:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Data_Export_Audit_Log','Data_Export_Audit_Log',TO_TIMESTAMP('2021-09-01 17:46:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T14:46:29.566Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579652 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-09-01T14:46:29.984Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575956,579652,0,13,541805,'Data_Export_Audit_Log_ID',TO_TIMESTAMP('2021-09-01 17:46:29','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','N','N','N','N','N','Y','Y','N','N','Y','N','N','Data_Export_Audit_Log',0,TO_TIMESTAMP('2021-09-01 17:46:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T14:46:29.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575956 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T14:46:29.990Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579652) 
;

-- 2021-09-01T14:46:30.460Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.Data_Export_Audit_Log (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Data_Export_Audit_Log_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT Data_Export_Audit_Log_Key PRIMARY KEY (Data_Export_Audit_Log_ID))
;

-- 2021-09-01T14:46:30.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('data_export_audit_log','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2021-09-01T14:46:30.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('data_export_audit_log','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2021-09-01T14:46:30.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('data_export_audit_log','CreatedBy','NUMERIC(10)',null,null)
;

-- 2021-09-01T14:46:30.514Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('data_export_audit_log','IsActive','CHAR(1)',null,null)
;

-- 2021-09-01T14:46:30.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('data_export_audit_log','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2021-09-01T14:46:30.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('data_export_audit_log','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2021-09-01T14:46:30.545Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('data_export_audit_log','Data_Export_Audit_Log_ID','NUMERIC(10)',null,null)
;

-- 2021-09-01T14:47:24.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575957,579650,0,19,541805,'Data_Export_Audit_ID',TO_TIMESTAMP('2021-09-01 17:47:24','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Data_Export_Audit',0,0,TO_TIMESTAMP('2021-09-01 17:47:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T14:47:24.179Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575957 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T14:47:24.185Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579650) 
;

-- 2021-09-01T14:47:24.758Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('Data_Export_Audit_Log','ALTER TABLE public.Data_Export_Audit_Log ADD COLUMN Data_Export_Audit_ID NUMERIC(10) NOT NULL')
;

-- 2021-09-01T14:47:24.765Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE Data_Export_Audit_Log ADD CONSTRAINT DataExportAudit_DataExportAuditLog FOREIGN KEY (Data_Export_Audit_ID) REFERENCES public.Data_Export_Audit DEFERRABLE INITIALLY DEFERRED
;

-- 2021-09-01T14:48:36.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2021-09-01 17:48:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575957
;

-- 2021-09-01T14:50:50.836Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540636,0,541805,TO_TIMESTAMP('2021-09-01 17:50:50','YYYY-MM-DD HH24:MI:SS'),100,'Unique constraint on data export audit id','D','Y','Y','IDX_DataExportAudit_unique_id','N',TO_TIMESTAMP('2021-09-01 17:50:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T14:50:50.839Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540636 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2021-09-01T14:51:02.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,575957,541154,540636,0,TO_TIMESTAMP('2021-09-01 17:51:02','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2021-09-01 17:51:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T14:53:13.312Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579653,0,'Data_Export_Action',TO_TIMESTAMP('2021-09-01 17:53:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Data_Export_Action','Data_Export_Action',TO_TIMESTAMP('2021-09-01 17:53:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T14:53:13.316Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579653 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-09-01T14:54:13.983Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541389,TO_TIMESTAMP('2021-09-01 17:54:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Data_Export_Actions',TO_TIMESTAMP('2021-09-01 17:54:13','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2021-09-01T14:54:13.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541389 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-09-01T14:54:30.951Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542859,541389,TO_TIMESTAMP('2021-09-01 17:54:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Exported-Standalone',TO_TIMESTAMP('2021-09-01 17:54:30','YYYY-MM-DD HH24:MI:SS'),100,'Exported-Standalone','Exported-Standalone')
;

-- 2021-09-01T14:54:30.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542859 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-09-01T14:54:40.570Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542860,541389,TO_TIMESTAMP('2021-09-01 17:54:40','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Exported-AlongWithParent',TO_TIMESTAMP('2021-09-01 17:54:40','YYYY-MM-DD HH24:MI:SS'),100,'Exported-AlongWithParent','Exported-AlongWithParent')
;

-- 2021-09-01T14:54:40.571Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542860 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-09-01T14:54:50.783Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542861,541389,TO_TIMESTAMP('2021-09-01 17:54:50','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','AssignedToParent',TO_TIMESTAMP('2021-09-01 17:54:50','YYYY-MM-DD HH24:MI:SS'),100,'AssignedToParent','AssignedToParent')
;

-- 2021-09-01T14:54:50.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542861 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-09-01T14:55:00.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542862,541389,TO_TIMESTAMP('2021-09-01 17:55:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Deleted',TO_TIMESTAMP('2021-09-01 17:55:00','YYYY-MM-DD HH24:MI:SS'),100,'Deleted','Deleted')
;

-- 2021-09-01T14:55:00.407Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542862 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-09-01T14:55:59.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575958,579653,0,17,541389,541805,'Data_Export_Action',TO_TIMESTAMP('2021-09-01 17:55:59','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Data_Export_Action',0,0,TO_TIMESTAMP('2021-09-01 17:55:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T14:55:59.218Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575958 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T14:55:59.220Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579653) 
;

-- 2021-09-01T14:55:59.723Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('Data_Export_Audit_Log','ALTER TABLE public.Data_Export_Audit_Log ADD COLUMN Data_Export_Action VARCHAR(255) NOT NULL')
;

-- 2021-09-01T14:56:55.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575959,578728,0,19,541805,'ExternalSystem_Config_ID',TO_TIMESTAMP('2021-09-01 17:56:55','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'External System Config',0,0,TO_TIMESTAMP('2021-09-01 17:56:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T14:56:55.787Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575959 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T14:56:55.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(578728) 
;

-- 2021-09-01T14:56:56.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('Data_Export_Audit_Log','ALTER TABLE public.Data_Export_Audit_Log ADD COLUMN ExternalSystem_Config_ID NUMERIC(10)')
;

-- 2021-09-01T14:56:56.333Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE Data_Export_Audit_Log ADD CONSTRAINT ExternalSystemConfig_DataExportAuditLog FOREIGN KEY (ExternalSystem_Config_ID) REFERENCES public.ExternalSystem_Config DEFERRABLE INITIALLY DEFERRED
;

-- 2021-09-01T14:57:12.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575960,114,0,19,541805,'AD_PInstance_ID',TO_TIMESTAMP('2021-09-01 17:57:12','YYYY-MM-DD HH24:MI:SS'),100,'N','Instanz eines Prozesses','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Prozess-Instanz',0,0,TO_TIMESTAMP('2021-09-01 17:57:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-09-01T14:57:12.455Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575960 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-09-01T14:57:12.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(114) 
;

-- 2021-09-01T14:57:13.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('Data_Export_Audit_Log','ALTER TABLE public.Data_Export_Audit_Log ADD COLUMN AD_PInstance_ID NUMERIC(10)')
;

-- 2021-09-01T14:57:13.025Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE Data_Export_Audit_Log ADD CONSTRAINT ADPInstance_DataExportAuditLog FOREIGN KEY (AD_PInstance_ID) REFERENCES public.AD_PInstance DEFERRABLE INITIALLY DEFERRED
;

-- 2021-09-01T15:22:09.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541252,541388,540300,TO_TIMESTAMP('2021-09-01 18:22:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_BPartner => Data_Export_Audit',TO_TIMESTAMP('2021-09-01 18:22:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:26:53.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,579650,0,541216,TO_TIMESTAMP('2021-09-01 18:26:53','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Data_Export_Audit','N',TO_TIMESTAMP('2021-09-01 18:26:53','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2021-09-01T15:26:53.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541216 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2021-09-01T15:26:53.824Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_window_translation_from_ad_element(579650) 
;

-- 2021-09-01T15:26:53.850Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541216
;

-- 2021-09-01T15:26:53.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Window(541216)
;

-- 2021-09-01T15:27:44.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,579650,0,544411,541804,541216,'Y',TO_TIMESTAMP('2021-09-01 18:27:44','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','Data_Export_Audit','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Data_Export_Audit','N',10,0,TO_TIMESTAMP('2021-09-01 18:27:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:27:44.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=544411 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2021-09-01T15:27:44.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(579650) 
;

-- 2021-09-01T15:27:44.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(544411)
;

-- 2021-09-01T15:29:45.432Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579654,0,TO_TIMESTAMP('2021-09-01 18:29:45','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Exported Data Audit','Exported Data Audit',TO_TIMESTAMP('2021-09-01 18:29:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:29:45.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579654 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-09-01T15:29:56.471Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Exportierte Daten Revision', PrintName='Exportierte Daten Revision',Updated=TO_TIMESTAMP('2021-09-01 18:29:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579654 AND AD_Language='de_CH'
;

-- 2021-09-01T15:29:56.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579654,'de_CH') 
;

-- 2021-09-01T15:29:59.721Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Exportierte Daten Revision', PrintName='Exportierte Daten Revision',Updated=TO_TIMESTAMP('2021-09-01 18:29:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579654 AND AD_Language='de_DE'
;

-- 2021-09-01T15:29:59.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579654,'de_DE') 
;

-- 2021-09-01T15:29:59.735Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579654,'de_DE') 
;

-- 2021-09-01T15:29:59.735Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Exportierte Daten Revision', Description=NULL, Help=NULL WHERE AD_Element_ID=579654
;

-- 2021-09-01T15:29:59.736Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Exportierte Daten Revision', Description=NULL, Help=NULL WHERE AD_Element_ID=579654 AND IsCentrallyMaintained='Y'
;

-- 2021-09-01T15:29:59.736Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Exportierte Daten Revision', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579654) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579654)
;

-- 2021-09-01T15:29:59.753Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Exportierte Daten Revision', Name='Exportierte Daten Revision' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579654)
;

-- 2021-09-01T15:29:59.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Exportierte Daten Revision', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579654
;

-- 2021-09-01T15:29:59.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Exportierte Daten Revision', Description=NULL, Help=NULL WHERE AD_Element_ID = 579654
;

-- 2021-09-01T15:29:59.756Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Exportierte Daten Revision', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579654
;

-- 2021-09-01T15:30:02.850Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Exportierte Daten Revision', PrintName='Exportierte Daten Revision',Updated=TO_TIMESTAMP('2021-09-01 18:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579654 AND AD_Language='nl_NL'
;

-- 2021-09-01T15:30:02.851Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579654,'nl_NL') 
;

-- 2021-09-01T15:30:15.621Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET AD_Element_ID=579654, Description=NULL, Help=NULL, Name='Exportierte Daten Revision',Updated=TO_TIMESTAMP('2021-09-01 18:30:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541216
;

-- 2021-09-01T15:30:15.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_window_translation_from_ad_element(579654) 
;

-- 2021-09-01T15:30:15.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541216
;

-- 2021-09-01T15:30:15.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Window(541216)
;

-- 2021-09-01T15:30:42.318Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575938,656739,0,544411,TO_TIMESTAMP('2021-09-01 18:30:42','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2021-09-01 18:30:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:30:42.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656739 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T15:30:42.322Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2021-09-01T15:30:42.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656739
;

-- 2021-09-01T15:30:42.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656739)
;

-- 2021-09-01T15:30:42.721Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575939,656740,0,544411,TO_TIMESTAMP('2021-09-01 18:30:42','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2021-09-01 18:30:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:30:42.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656740 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T15:30:42.723Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2021-09-01T15:30:42.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656740
;

-- 2021-09-01T15:30:42.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656740)
;

-- 2021-09-01T15:30:43.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575942,656741,0,544411,TO_TIMESTAMP('2021-09-01 18:30:42','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2021-09-01 18:30:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:30:43.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656741 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T15:30:43.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2021-09-01T15:30:43.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656741
;

-- 2021-09-01T15:30:43.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656741)
;

-- 2021-09-01T15:30:43.449Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575945,656742,0,544411,TO_TIMESTAMP('2021-09-01 18:30:43','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Data_Export_Audit',TO_TIMESTAMP('2021-09-01 18:30:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:30:43.450Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656742 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T15:30:43.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579650) 
;

-- 2021-09-01T15:30:43.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656742
;

-- 2021-09-01T15:30:43.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656742)
;

-- 2021-09-01T15:30:43.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575946,656743,0,544411,TO_TIMESTAMP('2021-09-01 18:30:43','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information',10,'D','The Database Table provides the information of the table definition','Y','N','N','N','N','N','N','N','DB-Tabelle',TO_TIMESTAMP('2021-09-01 18:30:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:30:43.554Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656743 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T15:30:43.555Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(126) 
;

-- 2021-09-01T15:30:43.585Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656743
;

-- 2021-09-01T15:30:43.586Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656743)
;

-- 2021-09-01T15:30:43.674Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575947,656744,0,544411,TO_TIMESTAMP('2021-09-01 18:30:43','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID',10,'D','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','N','N','N','N','N','N','Datensatz-ID',TO_TIMESTAMP('2021-09-01 18:30:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:30:43.676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656744 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T15:30:43.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(538) 
;

-- 2021-09-01T15:30:43.697Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656744
;

-- 2021-09-01T15:30:43.698Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656744)
;

-- 2021-09-01T15:30:43.796Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575948,656745,0,544411,TO_TIMESTAMP('2021-09-01 18:30:43','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Data_Export_Audit_Parent_ID',TO_TIMESTAMP('2021-09-01 18:30:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:30:43.798Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656745 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T15:30:43.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579651) 
;

-- 2021-09-01T15:30:43.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656745
;

-- 2021-09-01T15:30:43.802Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656745)
;

-- 2021-09-01T15:33:01.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,579652,0,544412,541805,541216,'Y',TO_TIMESTAMP('2021-09-01 18:33:01','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','Data_Export_Audit_Log','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Data_Export_Audit_Log','N',20,1,TO_TIMESTAMP('2021-09-01 18:33:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:33:01.938Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=544412 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2021-09-01T15:33:01.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(579652) 
;

-- 2021-09-01T15:33:01.945Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(544412)
;

-- 2021-09-01T15:33:30.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575949,656746,0,544412,TO_TIMESTAMP('2021-09-01 18:33:30','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2021-09-01 18:33:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:33:30.504Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656746 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T15:33:30.505Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2021-09-01T15:33:30.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656746
;

-- 2021-09-01T15:33:30.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656746)
;

-- 2021-09-01T15:33:30.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575950,656747,0,544412,TO_TIMESTAMP('2021-09-01 18:33:30','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2021-09-01 18:33:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:33:30.791Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656747 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T15:33:30.793Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2021-09-01T15:33:30.944Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656747
;

-- 2021-09-01T15:33:30.944Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656747)
;

-- 2021-09-01T15:33:31.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575953,656748,0,544412,TO_TIMESTAMP('2021-09-01 18:33:30','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2021-09-01 18:33:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:33:31.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656748 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T15:33:31.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2021-09-01T15:33:31.375Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656748
;

-- 2021-09-01T15:33:31.376Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656748)
;

-- 2021-09-01T15:33:31.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575956,656749,0,544412,TO_TIMESTAMP('2021-09-01 18:33:31','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Data_Export_Audit_Log',TO_TIMESTAMP('2021-09-01 18:33:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:33:31.508Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656749 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T15:33:31.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579652) 
;

-- 2021-09-01T15:33:31.513Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656749
;

-- 2021-09-01T15:33:31.513Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656749)
;

-- 2021-09-01T15:33:31.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575957,656750,0,544412,TO_TIMESTAMP('2021-09-01 18:33:31','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Data_Export_Audit',TO_TIMESTAMP('2021-09-01 18:33:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:33:31.609Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656750 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T15:33:31.611Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579650) 
;

-- 2021-09-01T15:33:31.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656750
;

-- 2021-09-01T15:33:31.616Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656750)
;

-- 2021-09-01T15:33:31.706Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575958,656751,0,544412,TO_TIMESTAMP('2021-09-01 18:33:31','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','Data_Export_Action',TO_TIMESTAMP('2021-09-01 18:33:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:33:31.707Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656751 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T15:33:31.709Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579653) 
;

-- 2021-09-01T15:33:31.713Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656751
;

-- 2021-09-01T15:33:31.713Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656751)
;

-- 2021-09-01T15:33:31.809Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575959,656752,0,544412,TO_TIMESTAMP('2021-09-01 18:33:31','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','External System Config',TO_TIMESTAMP('2021-09-01 18:33:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:33:31.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656752 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T15:33:31.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578728) 
;

-- 2021-09-01T15:33:31.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656752
;

-- 2021-09-01T15:33:31.815Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656752)
;

-- 2021-09-01T15:33:31.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575960,656753,0,544412,TO_TIMESTAMP('2021-09-01 18:33:31','YYYY-MM-DD HH24:MI:SS'),100,'Instanz eines Prozesses',10,'D','Y','N','N','N','N','N','N','N','Prozess-Instanz',TO_TIMESTAMP('2021-09-01 18:33:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:33:31.901Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656753 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T15:33:31.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(114) 
;

-- 2021-09-01T15:33:31.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656753
;

-- 2021-09-01T15:33:31.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656753)
;

-- 2021-09-01T15:39:43.491Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,544411,543494,TO_TIMESTAMP('2021-09-01 18:39:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-09-01 18:39:43','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2021-09-01T15:39:43.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=543494 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2021-09-01T15:39:49.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,544338,543494,TO_TIMESTAMP('2021-09-01 18:39:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-09-01 18:39:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:39:50.440Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,544339,543494,TO_TIMESTAMP('2021-09-01 18:39:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2021-09-01 18:39:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:40:02.471Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,544339,546535,TO_TIMESTAMP('2021-09-01 18:40:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','details',10,TO_TIMESTAMP('2021-09-01 18:40:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:40:54.602Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,656742,0,544411,589834,546535,'F',TO_TIMESTAMP('2021-09-01 18:40:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Data_Export_Audit',10,0,0,TO_TIMESTAMP('2021-09-01 18:40:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:41:28.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,656743,0,544411,589835,546535,'F',TO_TIMESTAMP('2021-09-01 18:41:28','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information','The Database Table provides the information of the table definition','Y','N','N','Y','N','N','N',0,'DB-Tabelle',20,0,0,TO_TIMESTAMP('2021-09-01 18:41:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:41:37.792Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,656744,0,544411,589836,546535,'F',TO_TIMESTAMP('2021-09-01 18:41:37','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','N','Y','N','N','N',0,'Datensatz-ID',30,0,0,TO_TIMESTAMP('2021-09-01 18:41:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:42:06.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,656745,0,544411,589837,546535,'F',TO_TIMESTAMP('2021-09-01 18:42:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Data_Export_Audit_Parent_ID',40,0,0,TO_TIMESTAMP('2021-09-01 18:42:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:44:56.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Column SET SeqNo=21,Updated=TO_TIMESTAMP('2021-09-01 18:44:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Column_ID=544338
;

-- 2021-09-01T15:45:02.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Column SET SeqNo=10,Updated=TO_TIMESTAMP('2021-09-01 18:45:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Column_ID=544339
;

-- 2021-09-01T15:45:08.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Column SET SeqNo=20,Updated=TO_TIMESTAMP('2021-09-01 18:45:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Column_ID=544338
;

-- 2021-09-01T15:45:27.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,544338,546536,TO_TIMESTAMP('2021-09-01 18:45:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',10,TO_TIMESTAMP('2021-09-01 18:45:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:45:56.809Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,656740,0,544411,589838,546536,'F',TO_TIMESTAMP('2021-09-01 18:45:56','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2021-09-01 18:45:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:46:05.062Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,656739,0,544411,589839,546536,'F',TO_TIMESTAMP('2021-09-01 18:46:04','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2021-09-01 18:46:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:48:35.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,544412,543495,TO_TIMESTAMP('2021-09-01 18:48:35','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-09-01 18:48:35','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2021-09-01T15:48:35.387Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=543495 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2021-09-01T15:48:40.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,544340,543495,TO_TIMESTAMP('2021-09-01 18:48:40','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-09-01 18:48:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:48:48.030Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,544340,546537,TO_TIMESTAMP('2021-09-01 18:48:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2021-09-01 18:48:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:49:09.023Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,656749,0,544412,589840,546537,'F',TO_TIMESTAMP('2021-09-01 18:49:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Data_Export_Audit_Log',10,0,0,TO_TIMESTAMP('2021-09-01 18:49:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:49:20.834Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,656750,0,544412,589841,546537,'F',TO_TIMESTAMP('2021-09-01 18:49:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Data_Export_Audit',20,0,0,TO_TIMESTAMP('2021-09-01 18:49:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:49:30.730Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,656751,0,544412,589842,546537,'F',TO_TIMESTAMP('2021-09-01 18:49:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Data_Export_Action',30,0,0,TO_TIMESTAMP('2021-09-01 18:49:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:49:39.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,656752,0,544412,589843,546537,'F',TO_TIMESTAMP('2021-09-01 18:49:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'External System Config',40,0,0,TO_TIMESTAMP('2021-09-01 18:49:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:49:48.248Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,656753,0,544412,589844,546537,'F',TO_TIMESTAMP('2021-09-01 18:49:48','YYYY-MM-DD HH24:MI:SS'),100,'Instanz eines Prozesses','Y','N','N','Y','N','N','N',0,'Prozess-Instanz',50,0,0,TO_TIMESTAMP('2021-09-01 18:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:51:08.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2021-09-01 18:51:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=589834
;

-- 2021-09-01T15:51:08.101Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-09-01 18:51:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=589837
;

-- 2021-09-01T15:51:08.103Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-09-01 18:51:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=589835
;

-- 2021-09-01T15:51:08.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-09-01 18:51:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=589836
;

-- 2021-09-01T15:52:34.207Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2021-09-01 18:52:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=589840
;

-- 2021-09-01T15:52:34.212Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-09-01 18:52:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=589841
;

-- 2021-09-01T15:52:34.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-09-01 18:52:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=589843
;

-- 2021-09-01T15:52:34.220Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-09-01 18:52:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=589842
;

-- 2021-09-01T15:52:34.224Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2021-09-01 18:52:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=589844
;

-- 2021-09-01T15:52:56.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,656746,0,544412,589845,546537,'F',TO_TIMESTAMP('2021-09-01 18:52:55','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',60,0,0,TO_TIMESTAMP('2021-09-01 18:52:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T15:53:05.007Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,656747,0,544412,589846,546537,'F',TO_TIMESTAMP('2021-09-01 18:53:04','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',70,0,0,TO_TIMESTAMP('2021-09-01 18:53:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T16:09:09.690Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579655,0,TO_TIMESTAMP('2021-09-01 19:09:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Exported Data Audit Log','Exported Data Audit Log',TO_TIMESTAMP('2021-09-01 19:09:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T16:09:09.692Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579655 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-09-01T16:09:22.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Exportierte Daten Revision Log', PrintName='Exportierte Daten Revision Log',Updated=TO_TIMESTAMP('2021-09-01 19:09:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579655 AND AD_Language='de_CH'
;

-- 2021-09-01T16:09:22.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579655,'de_CH') 
;

-- 2021-09-01T16:09:24.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Exportierte Daten Revision Log', PrintName='Exportierte Daten Revision Log',Updated=TO_TIMESTAMP('2021-09-01 19:09:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579655 AND AD_Language='de_DE'
;

-- 2021-09-01T16:09:24.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579655,'de_DE') 
;

-- 2021-09-01T16:09:24.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579655,'de_DE') 
;

-- 2021-09-01T16:09:24.526Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Exportierte Daten Revision Log', Description=NULL, Help=NULL WHERE AD_Element_ID=579655
;

-- 2021-09-01T16:09:24.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Exportierte Daten Revision Log', Description=NULL, Help=NULL WHERE AD_Element_ID=579655 AND IsCentrallyMaintained='Y'
;

-- 2021-09-01T16:09:24.528Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Exportierte Daten Revision Log', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579655) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579655)
;

-- 2021-09-01T16:09:24.547Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Exportierte Daten Revision Log', Name='Exportierte Daten Revision Log' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579655)
;

-- 2021-09-01T16:09:24.548Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Exportierte Daten Revision Log', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579655
;

-- 2021-09-01T16:09:24.549Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Exportierte Daten Revision Log', Description=NULL, Help=NULL WHERE AD_Element_ID = 579655
;

-- 2021-09-01T16:09:24.550Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Exportierte Daten Revision Log', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579655
;

-- 2021-09-01T16:09:27.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Exportierte Daten Revision Log', PrintName='Exportierte Daten Revision Log',Updated=TO_TIMESTAMP('2021-09-01 19:09:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579655 AND AD_Language='nl_NL'
;

-- 2021-09-01T16:09:27.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579655,'nl_NL') 
;

-- 2021-09-01T16:09:44.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,579655,0,541217,TO_TIMESTAMP('2021-09-01 19:09:44','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','N','N','N','Y','Exportierte Daten Revision Log','N',TO_TIMESTAMP('2021-09-01 19:09:44','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2021-09-01T16:09:44.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541217 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2021-09-01T16:09:44.719Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_window_translation_from_ad_element(579655) 
;

-- 2021-09-01T16:09:44.721Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541217
;

-- 2021-09-01T16:09:44.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Window(541217)
;

-- 2021-09-01T16:11:16.761Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,579652,0,544413,541805,541217,'Y',TO_TIMESTAMP('2021-09-01 19:11:16','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','Data_Export_Audit_Log','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Data_Export_Audit_Log','N',10,0,TO_TIMESTAMP('2021-09-01 19:11:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T16:11:16.763Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=544413 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2021-09-01T16:11:16.764Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(579652) 
;

-- 2021-09-01T16:11:16.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(544413)
;

-- 2021-09-01T16:11:52.769Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-09-01 19:11:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544413
;

-- 2021-09-01T16:11:58.633Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575949,656754,0,544413,TO_TIMESTAMP('2021-09-01 19:11:58','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2021-09-01 19:11:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T16:11:58.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656754 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T16:11:58.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2021-09-01T16:11:58.945Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656754
;

-- 2021-09-01T16:11:58.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656754)
;

-- 2021-09-01T16:11:59.043Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575950,656755,0,544413,TO_TIMESTAMP('2021-09-01 19:11:58','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2021-09-01 19:11:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T16:11:59.045Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656755 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T16:11:59.047Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2021-09-01T16:11:59.215Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656755
;

-- 2021-09-01T16:11:59.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656755)
;

-- 2021-09-01T16:11:59.325Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575953,656756,0,544413,TO_TIMESTAMP('2021-09-01 19:11:59','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2021-09-01 19:11:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T16:11:59.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656756 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T16:11:59.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2021-09-01T16:11:59.623Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656756
;

-- 2021-09-01T16:11:59.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656756)
;

-- 2021-09-01T16:11:59.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575956,656757,0,544413,TO_TIMESTAMP('2021-09-01 19:11:59','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Data_Export_Audit_Log',TO_TIMESTAMP('2021-09-01 19:11:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T16:11:59.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656757 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T16:11:59.720Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579652) 
;

-- 2021-09-01T16:11:59.723Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656757
;

-- 2021-09-01T16:11:59.723Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656757)
;

-- 2021-09-01T16:11:59.818Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575957,656758,0,544413,TO_TIMESTAMP('2021-09-01 19:11:59','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Data_Export_Audit',TO_TIMESTAMP('2021-09-01 19:11:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T16:11:59.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656758 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T16:11:59.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579650) 
;

-- 2021-09-01T16:11:59.826Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656758
;

-- 2021-09-01T16:11:59.826Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656758)
;

-- 2021-09-01T16:11:59.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575958,656759,0,544413,TO_TIMESTAMP('2021-09-01 19:11:59','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','N','N','N','N','N','N','N','Data_Export_Action',TO_TIMESTAMP('2021-09-01 19:11:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T16:11:59.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656759 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T16:11:59.928Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579653) 
;

-- 2021-09-01T16:11:59.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656759
;

-- 2021-09-01T16:11:59.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656759)
;

-- 2021-09-01T16:12:00.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575959,656760,0,544413,TO_TIMESTAMP('2021-09-01 19:11:59','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','External System Config',TO_TIMESTAMP('2021-09-01 19:11:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T16:12:00.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656760 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T16:12:00.028Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578728) 
;

-- 2021-09-01T16:12:00.029Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656760
;

-- 2021-09-01T16:12:00.029Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656760)
;

-- 2021-09-01T16:12:00.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,575960,656761,0,544413,TO_TIMESTAMP('2021-09-01 19:12:00','YYYY-MM-DD HH24:MI:SS'),100,'Instanz eines Prozesses',10,'D','Y','N','N','N','N','N','N','N','Prozess-Instanz',TO_TIMESTAMP('2021-09-01 19:12:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T16:12:00.117Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=656761 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-01T16:12:00.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(114) 
;

-- 2021-09-01T16:12:00.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=656761
;

-- 2021-09-01T16:12:00.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(656761)
;

-- 2021-09-01T16:12:10.352Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,544413,543496,TO_TIMESTAMP('2021-09-01 19:12:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-09-01 19:12:10','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2021-09-01T16:12:10.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=543496 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2021-09-01T16:12:14.268Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,544341,543496,TO_TIMESTAMP('2021-09-01 19:12:14','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-09-01 19:12:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T16:12:19.747Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,544341,546538,TO_TIMESTAMP('2021-09-01 19:12:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2021-09-01 19:12:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T16:12:35.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,656757,0,544413,589847,546538,'F',TO_TIMESTAMP('2021-09-01 19:12:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Data_Export_Audit_Log',10,0,0,TO_TIMESTAMP('2021-09-01 19:12:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T16:12:45.547Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,656758,0,544413,589848,546538,'F',TO_TIMESTAMP('2021-09-01 19:12:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Data_Export_Audit',20,0,0,TO_TIMESTAMP('2021-09-01 19:12:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T16:13:18.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,656760,0,544413,589849,546538,'F',TO_TIMESTAMP('2021-09-01 19:13:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'External System Config',30,0,0,TO_TIMESTAMP('2021-09-01 19:13:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T16:13:28.591Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,656759,0,544413,589850,546538,'F',TO_TIMESTAMP('2021-09-01 19:13:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Data_Export_Action',40,0,0,TO_TIMESTAMP('2021-09-01 19:13:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T16:13:37.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,656761,0,544413,589851,546538,'F',TO_TIMESTAMP('2021-09-01 19:13:37','YYYY-MM-DD HH24:MI:SS'),100,'Instanz eines Prozesses','Y','N','N','Y','N','N','N',0,'Prozess-Instanz',50,0,0,TO_TIMESTAMP('2021-09-01 19:13:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T16:13:52.507Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2021-09-01 19:13:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=589847
;

-- 2021-09-01T16:13:52.511Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-09-01 19:13:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=589848
;

-- 2021-09-01T16:13:52.514Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-09-01 19:13:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=589849
;

-- 2021-09-01T16:13:52.518Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-09-01 19:13:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=589850
;

-- 2021-09-01T16:13:52.521Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2021-09-01 19:13:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=589851
;

-- 2021-09-01T16:16:04.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Eltern-Datensatz zugeordnet',Updated=TO_TIMESTAMP('2021-09-01 19:16:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542861
;

-- 2021-09-01T16:16:06.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Eltern-Datensatz zugeordnet',Updated=TO_TIMESTAMP('2021-09-01 19:16:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=542861
;

-- 2021-09-01T16:16:08.748Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Eltern-Datensatz zugeordnet',Updated=TO_TIMESTAMP('2021-09-01 19:16:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542861
;

-- 2021-09-01T16:16:22.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Gelöscht',Updated=TO_TIMESTAMP('2021-09-01 19:16:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542862
;

-- 2021-09-01T16:16:24.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Gelöscht',Updated=TO_TIMESTAMP('2021-09-01 19:16:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=542862
;

-- 2021-09-01T16:16:26.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Gelöscht',Updated=TO_TIMESTAMP('2021-09-01 19:16:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542862
;

-- 2021-09-01T16:16:56.714Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Assigned to parent record',Updated=TO_TIMESTAMP('2021-09-01 19:16:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542861
;

-- 2021-09-01T16:17:13.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Mit Eltern-Datensatz exportiert',Updated=TO_TIMESTAMP('2021-09-01 19:17:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542860
;

-- 2021-09-01T16:17:19.524Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Exported with parent record',Updated=TO_TIMESTAMP('2021-09-01 19:17:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542860
;

-- 2021-09-01T16:17:25.115Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Mit Eltern-Datensatz exportiert',Updated=TO_TIMESTAMP('2021-09-01 19:17:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=542860
;

-- 2021-09-01T16:17:27.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Mit Eltern-Datensatz exportiert',Updated=TO_TIMESTAMP('2021-09-01 19:17:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542860
;

-- 2021-09-01T16:17:46.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Einzeln exportiert',Updated=TO_TIMESTAMP('2021-09-01 19:17:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=542859
;

-- 2021-09-01T16:17:54.097Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Exported Standalone',Updated=TO_TIMESTAMP('2021-09-01 19:17:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=542859
;

-- 2021-09-01T16:17:58.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Einzeln exportiert',Updated=TO_TIMESTAMP('2021-09-01 19:17:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=542859
;

-- 2021-09-01T16:18:02.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List_Trl SET Name='Einzeln exportiert',Updated=TO_TIMESTAMP('2021-09-01 19:18:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=542859
;

-- 2021-09-01T17:09:34.202Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541390,TO_TIMESTAMP('2021-09-01 20:09:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Data_Export_Audit_Target',TO_TIMESTAMP('2021-09-01 20:09:34','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-09-01T17:09:34.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541390 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-09-01T17:18:51.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,575945,0,541390,541804,TO_TIMESTAMP('2021-09-01 20:18:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-09-01 20:18:51','YYYY-MM-DD HH24:MI:SS'),100,'exists(select 1               from data_export_audit export               where export.ad_table_id = get_table_id(''C_BPartner'')                 and export.record_id = @C_BPartner_ID@     )')
;

-- 2021-09-01T17:22:19.859Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=541390,Updated=TO_TIMESTAMP('2021-09-01 20:22:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540300
;

-- 2021-09-01T17:25:18.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Window_ID=541216,Updated=TO_TIMESTAMP('2021-09-01 20:25:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541390
;

-- 2021-09-01T17:28:54.794Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Log', PrintName='Log',Updated=TO_TIMESTAMP('2021-09-01 20:28:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579652 AND AD_Language='de_CH'
;

-- 2021-09-01T17:28:54.826Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579652,'de_CH') 
;

-- 2021-09-01T17:28:57.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Log', PrintName='Log',Updated=TO_TIMESTAMP('2021-09-01 20:28:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579652 AND AD_Language='de_DE'
;

-- 2021-09-01T17:28:57.449Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579652,'de_DE') 
;

-- 2021-09-01T17:28:57.464Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579652,'de_DE') 
;

-- 2021-09-01T17:28:57.465Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Data_Export_Audit_Log_ID', Name='Log', Description=NULL, Help=NULL WHERE AD_Element_ID=579652
;

-- 2021-09-01T17:28:57.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Data_Export_Audit_Log_ID', Name='Log', Description=NULL, Help=NULL, AD_Element_ID=579652 WHERE UPPER(ColumnName)='DATA_EXPORT_AUDIT_LOG_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-09-01T17:28:57.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Data_Export_Audit_Log_ID', Name='Log', Description=NULL, Help=NULL WHERE AD_Element_ID=579652 AND IsCentrallyMaintained='Y'
;

-- 2021-09-01T17:28:57.468Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Log', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579652) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579652)
;

-- 2021-09-01T17:28:57.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Log', Name='Log' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579652)
;

-- 2021-09-01T17:28:57.488Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Log', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579652
;

-- 2021-09-01T17:28:57.489Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Log', Description=NULL, Help=NULL WHERE AD_Element_ID = 579652
;

-- 2021-09-01T17:28:57.489Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Log', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579652
;

-- 2021-09-01T17:29:00.803Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Log', PrintName='Log',Updated=TO_TIMESTAMP('2021-09-01 20:29:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579652 AND AD_Language='en_US'
;

-- 2021-09-01T17:29:00.803Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579652,'en_US') 
;

-- 2021-09-01T17:29:03.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Log', PrintName='Log',Updated=TO_TIMESTAMP('2021-09-01 20:29:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579652 AND AD_Language='nl_NL'
;

-- 2021-09-01T17:29:03.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579652,'nl_NL') 
;

-- 2021-09-01T17:31:13.582Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=589834
;

-- 2021-09-01T17:32:04.938Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,656742,0,544411,589852,546535,'F',TO_TIMESTAMP('2021-09-01 20:32:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','N',0,'Data_Export_Audit',50,0,0,TO_TIMESTAMP('2021-09-01 20:32:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-01T17:32:11.075Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2021-09-01 20:32:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=589852
;

-- 2021-09-01T17:33:34.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Exported Data Audit', PrintName='Exported Data Audit',Updated=TO_TIMESTAMP('2021-09-01 20:33:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579650 AND AD_Language='de_CH'
;

-- 2021-09-01T17:33:34.602Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579650,'de_CH') 
;

-- 2021-09-01T17:33:43.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Exportierte Daten Revision', PrintName='Exportierte Daten Revision',Updated=TO_TIMESTAMP('2021-09-01 20:33:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579650 AND AD_Language='de_CH'
;

-- 2021-09-01T17:33:43.128Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579650,'de_CH') 
;

-- 2021-09-01T17:33:45.974Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Exportierte Daten Revision', PrintName='Exportierte Daten Revision',Updated=TO_TIMESTAMP('2021-09-01 20:33:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579650 AND AD_Language='de_DE'
;

-- 2021-09-01T17:33:45.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579650,'de_DE') 
;

-- 2021-09-01T17:33:45.981Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579650,'de_DE') 
;

-- 2021-09-01T17:33:45.982Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Data_Export_Audit_ID', Name='Exportierte Daten Revision', Description=NULL, Help=NULL WHERE AD_Element_ID=579650
;

-- 2021-09-01T17:33:45.982Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Data_Export_Audit_ID', Name='Exportierte Daten Revision', Description=NULL, Help=NULL, AD_Element_ID=579650 WHERE UPPER(ColumnName)='DATA_EXPORT_AUDIT_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-09-01T17:33:45.983Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Data_Export_Audit_ID', Name='Exportierte Daten Revision', Description=NULL, Help=NULL WHERE AD_Element_ID=579650 AND IsCentrallyMaintained='Y'
;

-- 2021-09-01T17:33:45.983Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Exportierte Daten Revision', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579650) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579650)
;

-- 2021-09-01T17:33:45.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Exportierte Daten Revision', Name='Exportierte Daten Revision' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579650)
;

-- 2021-09-01T17:33:45.994Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Exportierte Daten Revision', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579650
;

-- 2021-09-01T17:33:45.995Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Exportierte Daten Revision', Description=NULL, Help=NULL WHERE AD_Element_ID = 579650
;

-- 2021-09-01T17:33:45.996Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Exportierte Daten Revision', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579650
;

-- 2021-09-01T17:33:53.030Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Exported Data Audit', PrintName='Exported Data Audit',Updated=TO_TIMESTAMP('2021-09-01 20:33:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579650 AND AD_Language='en_US'
;

-- 2021-09-01T17:33:53.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579650,'en_US') 
;

-- 2021-09-01T17:33:59.005Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Exportierte Daten Revision', PrintName='Exportierte Daten Revision',Updated=TO_TIMESTAMP('2021-09-01 20:33:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579650 AND AD_Language='nl_NL'
;

-- 2021-09-01T17:33:59.006Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579650,'nl_NL') 
;

-- 2021-09-01T17:35:46.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Action', PrintName='Action',Updated=TO_TIMESTAMP('2021-09-01 20:35:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579653 AND AD_Language='de_CH'
;

-- 2021-09-01T17:35:46.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579653,'de_CH') 
;

-- 2021-09-01T17:35:52.982Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Action', PrintName='Action',Updated=TO_TIMESTAMP('2021-09-01 20:35:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579653 AND AD_Language='de_DE'
;

-- 2021-09-01T17:35:52.982Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579653,'de_DE') 
;

-- 2021-09-01T17:35:52.982Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579653,'de_DE') 
;

-- 2021-09-01T17:35:52.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='Data_Export_Action', Name='Action', Description=NULL, Help=NULL WHERE AD_Element_ID=579653
;

-- 2021-09-01T17:35:52.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Data_Export_Action', Name='Action', Description=NULL, Help=NULL, AD_Element_ID=579653 WHERE UPPER(ColumnName)='DATA_EXPORT_ACTION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-09-01T17:35:52.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='Data_Export_Action', Name='Action', Description=NULL, Help=NULL WHERE AD_Element_ID=579653 AND IsCentrallyMaintained='Y'
;

-- 2021-09-01T17:35:52.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Action', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579653) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579653)
;

-- 2021-09-01T17:35:52.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Action', Name='Action' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579653)
;

-- 2021-09-01T17:35:53.014Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Action', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579653
;

-- 2021-09-01T17:35:53.014Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Action', Description=NULL, Help=NULL WHERE AD_Element_ID = 579653
;

-- 2021-09-01T17:35:53.014Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Action', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579653
;

-- 2021-09-01T17:35:55.292Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Action', PrintName='Action',Updated=TO_TIMESTAMP('2021-09-01 20:35:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579653 AND AD_Language='en_US'
;

-- 2021-09-01T17:35:55.292Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579653,'en_US') 
;

-- 2021-09-01T17:35:57.747Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Action', PrintName='Action',Updated=TO_TIMESTAMP('2021-09-01 20:35:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579653 AND AD_Language='nl_NL'
;

-- 2021-09-01T17:35:57.747Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579653,'nl_NL') 
;

-- 2021-09-01T17:39:36.018Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Column_ID=575957, Parent_Column_ID=575945,Updated=TO_TIMESTAMP('2021-09-01 20:39:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544412
;

-- 2021-09-01T17:39:42.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2021-09-01 20:39:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544412
;

-- 2021-09-01T17:42:03.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2021-09-01 20:42:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544411
;

-- 2021-09-02T08:53:00.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,579654,541752,0,541216,TO_TIMESTAMP('2021-09-02 11:53:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Exportierte Daten Revision','Y','N','N','N','N','Exportierte Daten Revision',TO_TIMESTAMP('2021-09-02 11:53:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-02T08:53:00.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541752 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2021-09-02T08:53:00.747Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541752, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541752)
;

-- 2021-09-02T08:53:00.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_menu_translation_from_ad_element(579654) 
;

-- 2021-09-02T08:53:09.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541725 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541702 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541585 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541600 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541481 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541134 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541474 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541111 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.128Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541416 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.128Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541142 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540892 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.129Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540969 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540914 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540915 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541539 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541216 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541263 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.134Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540898 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541476 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541563 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540895 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541599 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540911 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540427 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540438 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540912 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540913 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541478 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541540 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540894 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540917 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540982 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541414 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540919 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540983 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53101 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541080 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541273 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.147Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=363 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541079 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541108 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541053 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.149Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541052 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541513 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541233 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53103 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541389 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541041 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.153Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541104 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.153Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541109 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541162 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540901 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541282 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541339 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.157Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541326 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541417 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541374 AND AD_Tree_ID=10
;

-- 2021-09-02T08:53:09.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541752 AND AD_Tree_ID=10
;

-- 2021-09-02T08:54:39.109Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET InternalName='541216',Updated=TO_TIMESTAMP('2021-09-02 11:54:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541752
;

-- 2021-09-02T10:07:45.288Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-09-02 13:07:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544411
;

-- 2021-09-03T09:20:29.450Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,Type,Updated,UpdatedBy,Value) VALUES ('3',0,0,584901,'Y','de.metas.externalsystem.rabbitmq.C_BPartner_SyncTo_RabbitMQ_HTTP','N',TO_TIMESTAMP('2021-09-03 12:20:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','N','Y','N','N','N','Y','Y',0,'Send to RabbitMQ','json','N','Y','xls','Java',TO_TIMESTAMP('2021-09-03 12:20:28','YYYY-MM-DD HH24:MI:SS'),100,'Send to RabbitMQ')
;

-- 2021-09-03T09:20:29.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_ID=584901 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- 2021-09-03T09:20:47.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Sendet die ausgewählten Geschäftspartner an die gewählte RabbitMQ REST API config. Nachdem in Geschäftspartner einmal gesendet wurde, wird er bei Änderungen automatisch erneut gesendet, sofern die entsprechende Checkbox in der Externes-System-Konfiguartion anghakt ist.', Name='An RabbitMQ senden',Updated=TO_TIMESTAMP('2021-09-03 12:20:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=584901
;

-- 2021-09-03T09:20:56.251Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Description='Sendet die ausgewählten Geschäftspartner an die gewählte RabbitMQ REST API config. Nachdem in Geschäftspartner einmal gesendet wurde, wird er bei Änderungen automatisch erneut gesendet, sofern die entsprechende Checkbox in der Externes-System-Konfiguartion anghakt ist.', Help=NULL, Name='An RabbitMQ senden',Updated=TO_TIMESTAMP('2021-09-03 12:20:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584901
;

-- 2021-09-03T09:20:56.247Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Sendet die ausgewählten Geschäftspartner an die gewählte RabbitMQ REST API config. Nachdem in Geschäftspartner einmal gesendet wurde, wird er bei Änderungen automatisch erneut gesendet, sofern die entsprechende Checkbox in der Externes-System-Konfiguartion anghakt ist.', Name='An RabbitMQ senden',Updated=TO_TIMESTAMP('2021-09-03 12:20:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=584901
;

-- 2021-09-03T09:21:02.273Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Sends the selected business partners to the corresponding RabbitMQ REST API config. Once a business partner was send to an external system, it will be automatically resend when changed, as long as the corresponding checkbox is ticked in the external system config',Updated=TO_TIMESTAMP('2021-09-03 12:21:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=584901
;

-- 2021-09-03T09:21:15.743Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Trl SET Description='Sendet die ausgewählten Geschäftspartner an die gewählte RabbitMQ REST API config. Nachdem in Geschäftspartner einmal gesendet wurde, wird er bei Änderungen automatisch erneut gesendet, sofern die entsprechende Checkbox in der Externes-System-Konfiguartion anghakt ist.', Name='An RabbitMQ senden',Updated=TO_TIMESTAMP('2021-09-03 12:21:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Process_ID=584901
;

-- 2021-09-03T10:18:19.430Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,579644,0,584901,542085,19,'ExternalSystem_Config_RabbitMQ_HTTP_ID',TO_TIMESTAMP('2021-09-03 13:18:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem',7,'Y','N','Y','N','Y','N','RabbitMQ REST API Konfig',10,TO_TIMESTAMP('2021-09-03 13:18:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-03T10:18:19.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Process_Para_ID=542085 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2021-09-03T10:18:27.845Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET EntityType='de.metas.externalsystem',Updated=TO_TIMESTAMP('2021-09-03 13:18:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584901
;

-- 2021-09-03T10:18:43.280Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET ShowHelp='N',Updated=TO_TIMESTAMP('2021-09-03 13:18:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584901
;

-- 2021-09-03T10:19:14.026Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,584901,291,540992,TO_TIMESTAMP('2021-09-03 13:19:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y',TO_TIMESTAMP('2021-09-03 13:19:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N')
;

-- 2021-09-03T10:21:46.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsIdentifier='Y',Updated=TO_TIMESTAMP('2021-09-03 13:21:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575935
;

-- 2021-09-06T13:14:18.072Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.externalsystem.rabbitmqhttp.C_BPartner_SyncTo_RabbitMQ_HTTP',Updated=TO_TIMESTAMP('2021-09-06 16:14:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584901
;

-- 2021-09-08T16:25:05.339140800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET Classname='de.metas.externalsystem.rabbitmqhttp.process.C_BPartner_SyncTo_RabbitMQ_HTTP',Updated=TO_TIMESTAMP('2021-09-08 19:25:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584901
;

-- 2021-09-08T16:40:30.939992Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541399,TO_TIMESTAMP('2021-09-08 19:40:30','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','ccc',TO_TIMESTAMP('2021-09-08 19:40:30','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-09-08T16:40:30.943994600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541399 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-09-08T16:41:43.815133900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,575930,0,541399,541803,TO_TIMESTAMP('2021-09-08 19:41:43','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','N','N',TO_TIMESTAMP('2021-09-08 19:41:43','YYYY-MM-DD HH24:MI:SS'),100,'isActive=''Y''')
;

-- 2021-09-08T16:42:16.225606600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET EntityType='de.metas.externalsystem', Name='IsActive_RabbitMQ_Config',Updated=TO_TIMESTAMP('2021-09-08 19:42:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541399
;

-- 2021-09-08T16:44:53.837502500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=541399
;

-- 2021-09-08T16:44:53.841494400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=541399
;

-- 2021-09-08T16:45:25.754114500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541400,TO_TIMESTAMP('2021-09-08 19:45:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','N','IsActive_RabbitMQ_Config',TO_TIMESTAMP('2021-09-08 19:45:25','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-09-08T16:45:25.756077200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541400 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-09-08T16:46:00.350220800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,575930,0,541400,541803,TO_TIMESTAMP('2021-09-08 19:46:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','N','N',TO_TIMESTAMP('2021-09-08 19:46:00','YYYY-MM-DD HH24:MI:SS'),100,'isActive=''Y''')
;

-- 2021-09-08T16:46:16.785346500Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET AD_Reference_ID=18, AD_Reference_Value_ID=541400,Updated=TO_TIMESTAMP('2021-09-08 19:46:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542085
;

-- 2021-09-08T16:47:50.118642200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Key=575927,Updated=TO_TIMESTAMP('2021-09-08 19:47:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541400
;

-- 2021-09-08T16:51:15.911695400Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Key=575930, WhereClause='IsActive=''Y''',Updated=TO_TIMESTAMP('2021-09-08 19:51:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541400
;

-- 2021-09-08T16:54:21.699615600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Key=572724, AD_Table_ID=541576,Updated=TO_TIMESTAMP('2021-09-08 19:54:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541400
;

-- 2021-09-08T16:58:57.385404900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='IsActive=''Y'' AND Type=''RabbitMQ''',Updated=TO_TIMESTAMP('2021-09-08 19:58:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541400
;

-- 2021-09-09T09:21:47.770444100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Key=575930, AD_Table_ID=541803, WhereClause='IsActive=''Y'' AND (SELECT isActive from externalsystem_config parent where parent.externalsystem_config_id = ExternalSystem_Config_RabbitMQ_HTTP.externalsystem_config_id ) = ''Y''',Updated=TO_TIMESTAMP('2021-09-09 12:21:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541400
;

-- 2021-09-09T09:26:16.526868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='IsActive=''Y',Updated=TO_TIMESTAMP('2021-09-09 12:26:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541400
;

-- 2021-09-09T09:49:59.026610600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='IsActive=''Y''',Updated=TO_TIMESTAMP('2021-09-09 12:49:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541400
;