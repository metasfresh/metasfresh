-- 2021-06-03T13:12:12.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,541673,'N',TO_TIMESTAMP('2021-06-03 16:12:12','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Invoice Auto allocation Restrictions','NP','L','C_BP_BankAccount_InvoiceAutoAllocateRule','DTI',TO_TIMESTAMP('2021-06-03 16:12:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-03T13:12:12.836Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=541673 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2021-06-03T13:12:12.974Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555416,TO_TIMESTAMP('2021-06-03 16:12:12','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_BP_BankAccount_InvoiceAutoAllocateRule',1,'Y','N','Y','Y','C_BP_BankAccount_InvoiceAutoAllocateRule','N',1000000,TO_TIMESTAMP('2021-06-03 16:12:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-03T13:12:12.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE SEQUENCE C_BP_BANKACCOUNT_INVOICEAUTOALLOCATERULE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2021-06-03T13:12:59.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574186,102,0,19,541673,'AD_Client_ID',TO_TIMESTAMP('2021-06-03 16:12:59','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','Mandant',0,TO_TIMESTAMP('2021-06-03 16:12:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-03T13:12:59.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574186 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-03T13:12:59.764Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- 2021-06-03T13:13:00.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574187,113,0,30,541673,'AD_Org_ID',TO_TIMESTAMP('2021-06-03 16:13:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','Y','N','Y','Y','N','N','Sektion',0,TO_TIMESTAMP('2021-06-03 16:13:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-03T13:13:00.507Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574187 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-03T13:13:00.511Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- 2021-06-03T13:13:01.206Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574188,245,0,16,541673,'Created',TO_TIMESTAMP('2021-06-03 16:13:01','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt',0,TO_TIMESTAMP('2021-06-03 16:13:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-03T13:13:01.210Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574188 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-03T13:13:01.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- 2021-06-03T13:13:01.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574189,246,0,18,110,541673,'CreatedBy',TO_TIMESTAMP('2021-06-03 16:13:01','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch',0,TO_TIMESTAMP('2021-06-03 16:13:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-03T13:13:01.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574189 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-03T13:13:01.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- 2021-06-03T13:13:02.384Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574190,348,0,20,541673,'IsActive',TO_TIMESTAMP('2021-06-03 16:13:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','Y','Aktiv',0,TO_TIMESTAMP('2021-06-03 16:13:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-03T13:13:02.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574190 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-03T13:13:02.390Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- 2021-06-03T13:13:02.959Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574191,607,0,16,541673,'Updated',TO_TIMESTAMP('2021-06-03 16:13:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert',0,TO_TIMESTAMP('2021-06-03 16:13:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-03T13:13:02.964Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574191 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-03T13:13:02.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- 2021-06-03T13:13:03.511Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574192,608,0,18,110,541673,'UpdatedBy',TO_TIMESTAMP('2021-06-03 16:13:03','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2021-06-03 16:13:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-03T13:13:03.515Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574192 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-03T13:13:03.521Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2021-06-03T13:13:04.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579286,0,'C_BP_BankAccount_InvoiceAutoAllocateRule_ID',TO_TIMESTAMP('2021-06-03 16:13:03','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Invoice Auto allocation Restrictions','Invoice Auto allocation Restrictions',TO_TIMESTAMP('2021-06-03 16:13:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-03T13:13:04.051Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579286 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-06-03T13:13:04.529Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574193,579286,0,13,541673,'C_BP_BankAccount_InvoiceAutoAllocateRule_ID',TO_TIMESTAMP('2021-06-03 16:13:03','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','N','N','N','N','N','Y','Y','N','N','Y','N','N','Invoice Auto allocation Restrictions',0,TO_TIMESTAMP('2021-06-03 16:13:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-03T13:13:04.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574193 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-03T13:13:04.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579286)
;

-- 2021-06-03T13:13:48.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574194,837,0,19,541673,'C_BP_BankAccount_ID',TO_TIMESTAMP('2021-06-03 16:13:48','YYYY-MM-DD HH24:MI:SS'),100,'N','Bankverbindung des Geschäftspartners','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Bankverbindung',0,0,TO_TIMESTAMP('2021-06-03 16:13:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-03T13:13:48.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574194 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-03T13:13:48.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(837)
;

-- 2021-06-03T13:14:00.764Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2021-06-03 16:14:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574194
;

-- 2021-06-03T13:16:43.588Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2021-06-03 16:16:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574194
;

-- 2021-06-03T13:28:39.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574196,196,0,19,541673,'C_DocType_ID',TO_TIMESTAMP('2021-06-03 16:28:39','YYYY-MM-DD HH24:MI:SS'),100,'N','Belegart oder Verarbeitungsvorgaben','D',0,10,'Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Belegart',0,0,TO_TIMESTAMP('2021-06-03 16:28:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-03T13:28:39.371Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574196 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-03T13:28:39.375Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(196)
;

-- 2021-06-03T13:49:37.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=1072, ColumnName='C_DocTypeInvoice_ID', Description='Document type used for invoices generated from this sales document', Help='The Document Type for Invoice indicates the document type that will be used when an invoice is generated from this sales document.  This field will display only when the base document type is Sales Order.', Name='Rechnungs-Belegart',Updated=TO_TIMESTAMP('2021-06-03 16:49:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574196
;

-- 2021-06-03T13:49:37.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Rechnungs-Belegart', Description='Document type used for invoices generated from this sales document', Help='The Document Type for Invoice indicates the document type that will be used when an invoice is generated from this sales document.  This field will display only when the base document type is Sales Order.' WHERE AD_Column_ID=574196
;

-- 2021-06-03T13:49:37.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(1072)
;

-- 2021-06-03T13:50:42.958Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=196, ColumnName='C_DocType_ID', Description='Belegart oder Verarbeitungsvorgaben', Help='Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.', Name='Belegart',Updated=TO_TIMESTAMP('2021-06-03 16:50:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574196
;

-- 2021-06-03T13:50:42.960Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Belegart', Description='Belegart oder Verarbeitungsvorgaben', Help='Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.' WHERE AD_Column_ID=574196
;

-- 2021-06-03T13:50:42.965Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(196)
;

-- 2021-06-03T13:50:46.893Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bp_bankaccount_invoiceautoallocaterule','C_DocType_ID','NUMERIC(10)',null,null)
;

-- 2021-06-03T13:50:57.756Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=574196
;

-- 2021-06-03T13:50:57.768Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=574196
;

-- 2021-06-03T13:52:27.831Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574197,1072,0,18,170,541673,'C_DocTypeInvoice_ID',TO_TIMESTAMP('2021-06-03 16:52:27','YYYY-MM-DD HH24:MI:SS'),100,'N','Document type used for invoices generated from this sales document','D',0,10,'The Document Type for Invoice indicates the document type that will be used when an invoice is generated from this sales document.  This field will display only when the base document type is Sales Order.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Rechnungs-Belegart',0,0,TO_TIMESTAMP('2021-06-03 16:52:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-03T13:52:27.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574197 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-03T13:52:27.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(1072)
;

-- 2021-06-03T13:52:31.527Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_BP_BankAccount_InvoiceAutoAllocateRule ADD CONSTRAINT CDocTypeInvoice_CBPBankAccountInvoiceAutoAllocateRule FOREIGN KEY (C_DocTypeInvoice_ID) REFERENCES public.C_DocType DEFERRABLE INITIALLY DEFERRED
;

-- 2021-06-03T14:03:45.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,579286,0,543994,541673,540337,'Y',TO_TIMESTAMP('2021-06-03 17:03:45','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','C_BP_BankAccount_InvoiceAutoAllocateRule','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Invoice Auto allocation Restrictions','N',40,1,TO_TIMESTAMP('2021-06-03 17:03:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-03T14:03:45.410Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=543994 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2021-06-03T14:03:45.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(579286)
;

-- 2021-06-03T14:03:45.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(543994)
;

-- 2021-06-03T14:04:25.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574186,647400,0,543994,TO_TIMESTAMP('2021-06-03 17:04:25','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2021-06-03 17:04:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-03T14:04:25.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647400 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-03T14:04:25.886Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2021-06-03T14:04:26.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647400
;

-- 2021-06-03T14:04:26.136Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(647400)
;

-- 2021-06-03T14:04:26.231Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574187,647401,0,543994,TO_TIMESTAMP('2021-06-03 17:04:26','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2021-06-03 17:04:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-03T14:04:26.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647401 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-03T14:04:26.234Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2021-06-03T14:04:26.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647401
;

-- 2021-06-03T14:04:26.513Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(647401)
;

-- 2021-06-03T14:04:26.616Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574190,647402,0,543994,TO_TIMESTAMP('2021-06-03 17:04:26','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2021-06-03 17:04:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-03T14:04:26.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647402 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-03T14:04:26.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2021-06-03T14:04:26.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647402
;

-- 2021-06-03T14:04:26.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(647402)
;

-- 2021-06-03T14:04:27Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574193,647403,0,543994,TO_TIMESTAMP('2021-06-03 17:04:26','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Invoice Auto allocation Restrictions',TO_TIMESTAMP('2021-06-03 17:04:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-03T14:04:27.001Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647403 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-03T14:04:27.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579286)
;

-- 2021-06-03T14:04:27.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647403
;

-- 2021-06-03T14:04:27.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(647403)
;

-- 2021-06-03T14:04:27.088Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574194,647404,0,543994,TO_TIMESTAMP('2021-06-03 17:04:27','YYYY-MM-DD HH24:MI:SS'),100,'Bankverbindung des Geschäftspartners',10,'D','Y','N','N','N','N','N','N','N','Bankverbindung',TO_TIMESTAMP('2021-06-03 17:04:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-03T14:04:27.092Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647404 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-03T14:04:27.096Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(837)
;

-- 2021-06-03T14:04:27.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647404
;

-- 2021-06-03T14:04:27.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(647404)
;

-- 2021-06-03T14:04:27.205Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574197,647405,0,543994,TO_TIMESTAMP('2021-06-03 17:04:27','YYYY-MM-DD HH24:MI:SS'),100,'Document type used for invoices generated from this sales document',10,'D','The Document Type for Invoice indicates the document type that will be used when an invoice is generated from this sales document.  This field will display only when the base document type is Sales Order.','Y','N','N','N','N','N','N','N','Rechnungs-Belegart',TO_TIMESTAMP('2021-06-03 17:04:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-03T14:04:27.209Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647405 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-03T14:04:27.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1072)
;

-- 2021-06-03T14:04:27.225Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647405
;

-- 2021-06-03T14:04:27.226Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(647405)
;

-- 2021-06-03T14:06:37.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET AD_Column_ID=574194, Parent_Column_ID=3094,Updated=TO_TIMESTAMP('2021-06-03 17:06:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=543994
;

-- 2021-06-03T14:08:11.837Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,543994,543148,TO_TIMESTAMP('2021-06-03 17:08:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-06-03 17:08:11','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2021-06-03T14:08:11.841Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=543148 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2021-06-03T14:08:15.281Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,543955,543148,TO_TIMESTAMP('2021-06-03 17:08:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-06-03 17:08:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-03T14:08:22.682Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543955,545965,TO_TIMESTAMP('2021-06-03 17:08:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,TO_TIMESTAMP('2021-06-03 17:08:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-03T14:09:03.686Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,647405,0,543994,585646,545965,'F',TO_TIMESTAMP('2021-06-03 17:09:03','YYYY-MM-DD HH24:MI:SS'),100,'Document type used for invoices generated from this sales document','The Document Type for Invoice indicates the document type that will be used when an invoice is generated from this sales document.  This field will display only when the base document type is Sales Order.','Y','N','N','Y','N','N','N',0,'Rechnungs-Belegart',10,0,0,TO_TIMESTAMP('2021-06-03 17:09:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-03T14:09:13.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2021-06-03 17:09:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585646
;

-- 2021-06-04T10:22:57.130Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Autom. Rechnungszuordnung', PrintName='Autom. Rechnungszuordnung',Updated=TO_TIMESTAMP('2021-06-04 13:22:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579286 AND AD_Language='de_CH'
;

-- 2021-06-04T10:22:57.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579286,'de_CH')
;

-- 2021-06-04T10:23:03.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Autom. Rechnungszuordnung', PrintName='Autom. Rechnungszuordnung',Updated=TO_TIMESTAMP('2021-06-04 13:23:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579286 AND AD_Language='de_DE'
;

-- 2021-06-04T10:23:03.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579286,'de_DE')
;

-- 2021-06-04T10:23:16.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Autom. Rechnungszuordnung', PrintName='Autom. Rechnungszuordnung',Updated=TO_TIMESTAMP('2021-06-04 13:23:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579286 AND AD_Language='nl_NL'
;

-- 2021-06-04T10:23:16.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579286,'nl_NL')
;

-- 2021-06-04T10:23:32.563Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Invoice auto allocation', PrintName='Invoice auto allocation',Updated=TO_TIMESTAMP('2021-06-04 13:23:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579286 AND AD_Language='en_US'
;

-- 2021-06-04T10:23:32.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579286,'en_US')
;

-- 2021-06-04T10:23:32.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579286,'en_US')
;

-- 2021-06-04T10:23:32.580Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_BP_BankAccount_InvoiceAutoAllocateRule_ID', Name='Invoice auto allocation', Description=NULL, Help=NULL WHERE AD_Element_ID=579286
;

-- 2021-06-04T10:23:32.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BP_BankAccount_InvoiceAutoAllocateRule_ID', Name='Invoice auto allocation', Description=NULL, Help=NULL, AD_Element_ID=579286 WHERE UPPER(ColumnName)='C_BP_BANKACCOUNT_INVOICEAUTOALLOCATERULE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-04T10:23:32.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BP_BankAccount_InvoiceAutoAllocateRule_ID', Name='Invoice auto allocation', Description=NULL, Help=NULL WHERE AD_Element_ID=579286 AND IsCentrallyMaintained='Y'
;

-- 2021-06-04T10:23:32.584Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Invoice auto allocation', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579286) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579286)
;

-- 2021-06-04T10:23:32.606Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Invoice auto allocation', Name='Invoice auto allocation' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579286)
;

-- 2021-06-04T10:23:32.607Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Invoice auto allocation', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579286
;

-- 2021-06-04T10:23:32.609Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Invoice auto allocation', Description=NULL, Help=NULL WHERE AD_Element_ID = 579286
;

-- 2021-06-04T10:23:32.609Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Invoice auto allocation', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579286
;

-- 2021-06-04T12:11:10.924Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=124,Updated=TO_TIMESTAMP('2021-06-04 15:11:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=574197
;

-- 2021-06-04T13:59:05.389Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Für die hier aufgeführten Rechnungbelegarten wird bei einer automatischen Zahlungszuordnung ausschließlich dieses Bankkonto verwendet.', Help='Für die hier aufgeführten Rechnungbelegarten wird bei einer automatischen Zahlungszuordnung ausschließlich dieses Bankkonto verwendet.',Updated=TO_TIMESTAMP('2021-06-04 16:59:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579286 AND AD_Language='de_CH'
;

-- 2021-06-04T13:59:05.393Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579286,'de_CH')
;

-- 2021-06-04T13:59:11.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Für die hier aufgeführten Rechnungbelegarten wird bei einer automatischen Zahlungszuordnung ausschließlich dieses Bankkonto verwendet.', Help='Für die hier aufgeführten Rechnungbelegarten wird bei einer automatischen Zahlungszuordnung ausschließlich dieses Bankkonto verwendet.',Updated=TO_TIMESTAMP('2021-06-04 16:59:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579286 AND AD_Language='de_DE'
;

-- 2021-06-04T13:59:11.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579286,'de_DE')
;

-- 2021-06-04T13:59:17.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Für die hier aufgeführten Rechnungbelegarten wird bei einer automatischen Zahlungszuordnung ausschließlich dieses Bankkonto verwendet.', Help='Für die hier aufgeführten Rechnungbelegarten wird bei einer automatischen Zahlungszuordnung ausschließlich dieses Bankkonto verwendet.',Updated=TO_TIMESTAMP('2021-06-04 16:59:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579286 AND AD_Language='nl_NL'
;

-- 2021-06-04T13:59:17.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579286,'nl_NL')
;

-- 2021-06-04T13:59:36.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Invoice Document Types defined here are restricted to this bank account when considering payment auto allocation.', Help='Invoice Document Types defined here are restricted to this bank account when considering payment auto allocation.',Updated=TO_TIMESTAMP('2021-06-04 16:59:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579286 AND AD_Language='en_US'
;

-- 2021-06-04T13:59:36.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579286,'en_US')
;

-- 2021-06-04T13:59:36.235Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(579286,'en_US')
;

-- 2021-06-04T13:59:36.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_BP_BankAccount_InvoiceAutoAllocateRule_ID', Name='Invoice auto allocation', Description='Invoice Document Types defined here are restricted to this bank account when considering payment auto allocation.', Help='Invoice Document Types defined here are restricted to this bank account when considering payment auto allocation.' WHERE AD_Element_ID=579286
;

-- 2021-06-04T13:59:36.237Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BP_BankAccount_InvoiceAutoAllocateRule_ID', Name='Invoice auto allocation', Description='Invoice Document Types defined here are restricted to this bank account when considering payment auto allocation.', Help='Invoice Document Types defined here are restricted to this bank account when considering payment auto allocation.', AD_Element_ID=579286 WHERE UPPER(ColumnName)='C_BP_BANKACCOUNT_INVOICEAUTOALLOCATERULE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-04T13:59:36.238Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BP_BankAccount_InvoiceAutoAllocateRule_ID', Name='Invoice auto allocation', Description='Invoice Document Types defined here are restricted to this bank account when considering payment auto allocation.', Help='Invoice Document Types defined here are restricted to this bank account when considering payment auto allocation.' WHERE AD_Element_ID=579286 AND IsCentrallyMaintained='Y'
;

-- 2021-06-04T13:59:36.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Invoice auto allocation', Description='Invoice Document Types defined here are restricted to this bank account when considering payment auto allocation.', Help='Invoice Document Types defined here are restricted to this bank account when considering payment auto allocation.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579286) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579286)
;

-- 2021-06-04T13:59:36.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Invoice auto allocation', Description='Invoice Document Types defined here are restricted to this bank account when considering payment auto allocation.', Help='Invoice Document Types defined here are restricted to this bank account when considering payment auto allocation.', CommitWarning = NULL WHERE AD_Element_ID = 579286
;

-- 2021-06-04T13:59:36.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Invoice auto allocation', Description='Invoice Document Types defined here are restricted to this bank account when considering payment auto allocation.', Help='Invoice Document Types defined here are restricted to this bank account when considering payment auto allocation.' WHERE AD_Element_ID = 579286
;

-- 2021-06-04T13:59:36.261Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Invoice auto allocation', Description = 'Invoice Document Types defined here are restricted to this bank account when considering payment auto allocation.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579286
;
