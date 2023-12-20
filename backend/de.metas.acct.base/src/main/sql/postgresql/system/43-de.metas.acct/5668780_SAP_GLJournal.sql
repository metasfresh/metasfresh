-- Table: SAP_GLJournal
-- 2022-12-15T13:23:04.546Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('1',0,0,0,542275,'N',TO_TIMESTAMP('2022-12-15 15:23:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','N','Y','N','N','Y','N','N','N','N','N',0,'GL Journal (SAP)','NP','L','SAP_GLJournal','DTI',TO_TIMESTAMP('2022-12-15 15:23:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T13:23:04.549Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542275 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2022-12-15T13:23:04.659Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556067,TO_TIMESTAMP('2022-12-15 15:23:04','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table SAP_GLJournal',1,'Y','N','Y','Y','SAP_GLJournal','N',1000000,TO_TIMESTAMP('2022-12-15 15:23:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:23:04.668Z
CREATE SEQUENCE SAP_GLJOURNAL_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: SAP_GLJournal.AD_Client_ID
-- 2022-12-15T13:23:21.007Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585331,102,0,19,542275,129,'AD_Client_ID',TO_TIMESTAMP('2022-12-15 15:23:20','YYYY-MM-DD HH24:MI:SS'),100,'N','@AD_Client_ID@','Client/Tenant for this installation.','de.metas.acct',0,22,'A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Client',0,0,TO_TIMESTAMP('2022-12-15 15:23:20','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:21.009Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585331 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:21.038Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: SAP_GLJournal.AD_Org_ID
-- 2022-12-15T13:23:21.916Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585332,113,0,30,542275,130,'AD_Org_ID',TO_TIMESTAMP('2022-12-15 15:23:21','YYYY-MM-DD HH24:MI:SS'),100,'N','@AD_Org_ID@','Organisational entity within client','de.metas.acct',0,22,'E','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','N','N','N','Organisation',60,0,TO_TIMESTAMP('2022-12-15 15:23:21','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:21.918Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585332 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:21.920Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: SAP_GLJournal.C_AcctSchema_ID
-- 2022-12-15T13:23:22.604Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585333,181,0,19,542275,'C_AcctSchema_ID',TO_TIMESTAMP('2022-12-15 15:23:22','YYYY-MM-DD HH24:MI:SS'),100,'N','@$C_AcctSchema_ID@','Rules for accounting','de.metas.acct',0,22,'An Accounting Schema defines the rules used in accounting such as costing method, currency and calendar','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Accounting Schema',0,0,TO_TIMESTAMP('2022-12-15 15:23:22','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:22.606Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585333 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:22.609Z
/* DDL */  select update_Column_Translation_From_AD_Element(181) 
;

-- Column: SAP_GLJournal.C_ConversionType_ID
-- 2022-12-15T13:23:23.176Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585334,2278,0,19,542275,'C_ConversionType_ID',TO_TIMESTAMP('2022-12-15 15:23:22','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.acct',0,22,'','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Conversiontype',0,0,TO_TIMESTAMP('2022-12-15 15:23:22','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:23.179Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585334 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:23.185Z
/* DDL */  select update_Column_Translation_From_AD_Element(2278) 
;

-- Column: SAP_GLJournal.C_Currency_ID
-- 2022-12-15T13:23:23.754Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585335,193,0,19,542275,'C_Currency_ID',TO_TIMESTAMP('2022-12-15 15:23:23','YYYY-MM-DD HH24:MI:SS'),100,'N','@C_Currency_ID@','The Currency for this record','de.metas.acct',0,22,'Indicates the Currency to be used when processing or reporting on this record','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Currency',0,0,TO_TIMESTAMP('2022-12-15 15:23:23','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:23.755Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585335 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:23.758Z
/* DDL */  select update_Column_Translation_From_AD_Element(193) 
;

-- Column: SAP_GLJournal.C_DocType_ID
-- 2022-12-15T13:23:24.428Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585336,196,0,19,542275,102,'C_DocType_ID',TO_TIMESTAMP('2022-12-15 15:23:24','YYYY-MM-DD HH24:MI:SS'),100,'N','','Document type or rules','de.metas.acct',0,22,'The Document Type determines document sequence and processing rules','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Document Type',0,0,TO_TIMESTAMP('2022-12-15 15:23:24','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:24.430Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585336 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:24.432Z
/* DDL */  select update_Column_Translation_From_AD_Element(196) 
;

-- Column: SAP_GLJournal.ControlAmt
-- 2022-12-15T13:23:25.004Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585337,233,0,12,542275,'ControlAmt',TO_TIMESTAMP('2022-12-15 15:23:24','YYYY-MM-DD HH24:MI:SS'),100,'N','If not zero, the Debit amount of the document must be equal this amount','de.metas.acct',0,22,'If the control amount is zero, no check is performed.
Otherwise the total Debit amount must be equal to the control amount, before the document is processed.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Control Amount',0,0,TO_TIMESTAMP('2022-12-15 15:23:24','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:25.006Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585337 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:25.008Z
/* DDL */  select update_Column_Translation_From_AD_Element(233) 
;

-- Column: SAP_GLJournal.C_Period_ID
-- 2022-12-15T13:23:25.774Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585338,206,0,18,275,542275,215,'C_Period_ID',TO_TIMESTAMP('2022-12-15 15:23:25','YYYY-MM-DD HH24:MI:SS'),100,'N','@C_Period_ID@','Period of the Calendar','de.metas.acct',0,22,'E','The Period indicates an exclusive range of dates for a calendar.','N','Y','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','Period',50,0,TO_TIMESTAMP('2022-12-15 15:23:25','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:25.775Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585338 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:25.777Z
/* DDL */  select update_Column_Translation_From_AD_Element(206) 
;

-- Column: SAP_GLJournal.Created
-- 2022-12-15T13:23:26.379Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585339,245,0,16,542275,'Created',TO_TIMESTAMP('2022-12-15 15:23:26','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was created','de.metas.acct',0,7,'The Created field indicates the date that this record was created.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Created',0,0,TO_TIMESTAMP('2022-12-15 15:23:26','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:26.381Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585339 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:26.385Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: SAP_GLJournal.CreatedBy
-- 2022-12-15T13:23:27.267Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585340,246,0,18,110,542275,'CreatedBy',TO_TIMESTAMP('2022-12-15 15:23:27','YYYY-MM-DD HH24:MI:SS'),100,'N','User who created this records','de.metas.acct',0,22,'The Created By field indicates the user who created this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Created By',0,0,TO_TIMESTAMP('2022-12-15 15:23:27','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:27.269Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585340 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:27.272Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: SAP_GLJournal.CurrencyRate
-- 2022-12-15T13:23:27.913Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585341,253,0,22,542275,'CurrencyRate',TO_TIMESTAMP('2022-12-15 15:23:27','YYYY-MM-DD HH24:MI:SS'),100,'N','1','','de.metas.acct',0,22,'','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Currency Rate',0,0,TO_TIMESTAMP('2022-12-15 15:23:27','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:27.915Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585341 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:27.918Z
/* DDL */  select update_Column_Translation_From_AD_Element(253) 
;

-- Column: SAP_GLJournal.DateAcct
-- 2022-12-15T13:23:28.566Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585342,263,0,15,542275,'DateAcct',TO_TIMESTAMP('2022-12-15 15:23:28','YYYY-MM-DD HH24:MI:SS'),100,'N','@DateAcct@','Accounting Date','de.metas.acct',0,7,'B','The Accounting Date indicates the date to be used on the General Ledger account entries generated from this document. It is also used for any currency conversion.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','N','N','Y','Accounting Date',40,0,TO_TIMESTAMP('2022-12-15 15:23:28','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:28.568Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585342 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:28.571Z
/* DDL */  select update_Column_Translation_From_AD_Element(263) 
;

-- Column: SAP_GLJournal.DateDoc
-- 2022-12-15T13:23:29.221Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585343,265,0,15,542275,'DateDoc',TO_TIMESTAMP('2022-12-15 15:23:28','YYYY-MM-DD HH24:MI:SS'),100,'N','@DateDoc@','','de.metas.acct',0,7,'B','The Document Date indicates the date the document was generated.  It may or may not be the same as the accounting date.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','N','N','Y','Document Date',10,0,TO_TIMESTAMP('2022-12-15 15:23:28','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:29.223Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585343 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:29.225Z
/* DDL */  select update_Column_Translation_From_AD_Element(265) 
;

-- Column: SAP_GLJournal.Description
-- 2022-12-15T13:23:29.801Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585344,275,0,10,542275,'Description',TO_TIMESTAMP('2022-12-15 15:23:29','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.acct',0,255,'','Y','Y','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Description',0,0,TO_TIMESTAMP('2022-12-15 15:23:29','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:29.802Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585344 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:29.805Z
/* DDL */  select update_Column_Translation_From_AD_Element(275) 
;

-- Column: SAP_GLJournal.DocAction
-- 2022-12-15T13:23:30.439Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585345,287,0,169,28,135,542275,'DocAction',TO_TIMESTAMP('2022-12-15 15:23:30','YYYY-MM-DD HH24:MI:SS'),100,'N','CO','de.metas.acct',0,2,'Y','Y','N','N','Y','N','N','N','N','Y','N','N','N','N','N','N','Y','Process Batch',0,0,TO_TIMESTAMP('2022-12-15 15:23:30','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:30.440Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585345 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:30.442Z
/* DDL */  select update_Column_Translation_From_AD_Element(287) 
;

-- Column: SAP_GLJournal.DocStatus
-- 2022-12-15T13:23:31.182Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585346,289,0,17,131,542275,'DocStatus',TO_TIMESTAMP('2022-12-15 15:23:30','YYYY-MM-DD HH24:MI:SS'),100,'N','DR','','de.metas.acct',0,2,'','Y','Y','N','N','Y','N','N','N','N','Y','N','N','N','N','N','N','Y','Status',0,0,TO_TIMESTAMP('2022-12-15 15:23:30','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:31.183Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585346 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:31.187Z
/* DDL */  select update_Column_Translation_From_AD_Element(289) 
;

-- Column: SAP_GLJournal.DocumentNo
-- 2022-12-15T13:23:32.073Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585347,290,0,10,542275,'DocumentNo',TO_TIMESTAMP('2022-12-15 15:23:31','YYYY-MM-DD HH24:MI:SS'),100,'N','Document sequence number of the document','de.metas.acct',0,30,'E','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','Y','N','N','Y','N','N','Y','N','Y','N','Y','N','N','N','N','N','Document No',20,1,TO_TIMESTAMP('2022-12-15 15:23:31','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:32.075Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585347 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:32.078Z
/* DDL */  select update_Column_Translation_From_AD_Element(290) 
;

-- Column: SAP_GLJournal.GL_Budget_ID
-- 2022-12-15T13:23:32.763Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585348,308,0,19,542275,'GL_Budget_ID',TO_TIMESTAMP('2022-12-15 15:23:32','YYYY-MM-DD HH24:MI:SS'),100,'N','General Ledger Budget','de.metas.acct',0,22,'The General Ledger Budget identifies a user defined budget.  These can be used in reporting as a comparison against your actual amounts.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Budget',0,0,TO_TIMESTAMP('2022-12-15 15:23:32','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:32.765Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585348 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:32.767Z
/* DDL */  select update_Column_Translation_From_AD_Element(308) 
;

-- Column: SAP_GLJournal.GL_Category_ID
-- 2022-12-15T13:23:33.450Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585349,309,0,19,542275,118,'GL_Category_ID',TO_TIMESTAMP('2022-12-15 15:23:33','YYYY-MM-DD HH24:MI:SS'),100,'N','@GL_Category_ID@','General Ledger Category','de.metas.acct',0,22,'The General Ledger Category is an optional, user defined method of grouping journal lines.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','GL Category',0,0,TO_TIMESTAMP('2022-12-15 15:23:33','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:33.452Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585349 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:33.454Z
/* DDL */  select update_Column_Translation_From_AD_Element(309) 
;

-- Column: SAP_GLJournal.GL_JournalBatch_ID
-- 2022-12-15T13:23:34.061Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585350,313,0,19,542275,'GL_JournalBatch_ID',TO_TIMESTAMP('2022-12-15 15:23:33','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.acct',0,22,'','Y','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Journal Run',0,0,TO_TIMESTAMP('2022-12-15 15:23:33','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:34.063Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585350 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:34.065Z
/* DDL */  select update_Column_Translation_From_AD_Element(313) 
;

-- 2022-12-15T13:23:34.498Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581870,0,'SAP_GLJournal_ID',TO_TIMESTAMP('2022-12-15 15:23:34','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','GL Journal (SAP)','GL Journal (SAP)',TO_TIMESTAMP('2022-12-15 15:23:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:23:34.500Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581870 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: SAP_GLJournal.SAP_GLJournal_ID
-- 2022-12-15T13:23:35.073Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585351,581870,0,13,542275,'SAP_GLJournal_ID',TO_TIMESTAMP('2022-12-15 15:23:34','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.acct',0,22,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','GL Journal (SAP)',0,0,TO_TIMESTAMP('2022-12-15 15:23:34','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:35.075Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585351 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:35.078Z
/* DDL */  select update_Column_Translation_From_AD_Element(581870) 
;

-- Column: SAP_GLJournal.IsActive
-- 2022-12-15T13:23:35.693Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585352,348,0,20,542275,'IsActive',TO_TIMESTAMP('2022-12-15 15:23:35','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','The record is active in the system','de.metas.acct',0,1,'There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Active',0,0,TO_TIMESTAMP('2022-12-15 15:23:35','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:35.695Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585352 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:35.698Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: SAP_GLJournal.IsApproved
-- 2022-12-15T13:23:36.389Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585353,351,0,20,542275,'IsApproved',TO_TIMESTAMP('2022-12-15 15:23:36','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','Indicates if this document requires approval','de.metas.acct',0,1,'The Approved checkbox indicates if this document requires approval before it can be processed.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Approved',0,0,TO_TIMESTAMP('2022-12-15 15:23:36','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:36.390Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585353 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:36.393Z
/* DDL */  select update_Column_Translation_From_AD_Element(351) 
;

-- Column: SAP_GLJournal.IsPrinted
-- 2022-12-15T13:23:36.990Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585354,399,0,20,542275,'IsPrinted',TO_TIMESTAMP('2022-12-15 15:23:36','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Indicates if this document / line is printed','de.metas.acct',0,1,'The Printed checkbox indicates if this document or line will included when printing.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Printed',0,0,TO_TIMESTAMP('2022-12-15 15:23:36','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:36.992Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585354 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:36.995Z
/* DDL */  select update_Column_Translation_From_AD_Element(399) 
;

-- Column: SAP_GLJournal.M_SectionCode_ID
-- 2022-12-15T13:23:37.641Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585355,581238,0,30,542275,'M_SectionCode_ID',TO_TIMESTAMP('2022-12-15 15:23:37','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.acct',0,10,'E','Y','Y','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','Section Code',0,0,TO_TIMESTAMP('2022-12-15 15:23:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T13:23:37.643Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585355 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:37.645Z
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;

-- Column: SAP_GLJournal.Posted
-- 2022-12-15T13:23:38.305Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585356,1308,0,17,234,542275,'Posted',TO_TIMESTAMP('2022-12-15 15:23:38','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Posting status','de.metas.acct',0,1,'The ''Posting Status'' field indicates the status of the General Ledger accounting lines.','Y','Y','N','N','Y','N','N','N','N','Y','N','N','N','N','N','N','N','Posting status',0,0,TO_TIMESTAMP('2022-12-15 15:23:38','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:38.306Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585356 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:38.309Z
/* DDL */  select update_Column_Translation_From_AD_Element(1308) 
;

-- Column: SAP_GLJournal.PostingError_Issue_ID
-- 2022-12-15T13:23:38.918Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585357,577755,0,30,540991,542275,'PostingError_Issue_ID',TO_TIMESTAMP('2022-12-15 15:23:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','de.metas.acct',0,10,'Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','Posting Error',0,0,TO_TIMESTAMP('2022-12-15 15:23:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T13:23:38.920Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585357 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:38.922Z
/* DDL */  select update_Column_Translation_From_AD_Element(577755) 
;

-- Column: SAP_GLJournal.PostingType
-- 2022-12-15T13:23:39.561Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585358,514,0,17,125,542275,'PostingType',TO_TIMESTAMP('2022-12-15 15:23:39','YYYY-MM-DD HH24:MI:SS'),100,'N','@PostingType@','','de.metas.acct',0,1,'E','','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','N','N','Y','Posting Type',30,0,TO_TIMESTAMP('2022-12-15 15:23:39','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:39.562Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585358 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:39.565Z
/* DDL */  select update_Column_Translation_From_AD_Element(514) 
;

-- Column: SAP_GLJournal.Processed
-- 2022-12-15T13:23:40.236Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585359,1047,0,20,542275,'Processed',TO_TIMESTAMP('2022-12-15 15:23:39','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.acct',0,1,'','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','Processed',0,0,TO_TIMESTAMP('2022-12-15 15:23:39','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:40.237Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585359 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:40.241Z
/* DDL */  select update_Column_Translation_From_AD_Element(1047) 
;

-- Column: SAP_GLJournal.Processing
-- 2022-12-15T13:23:40.944Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585360,524,0,28,542275,'Processing',TO_TIMESTAMP('2022-12-15 15:23:40','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.acct',0,1,'Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','Process Now',0,0,TO_TIMESTAMP('2022-12-15 15:23:40','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:40.946Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585360 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:40.948Z
/* DDL */  select update_Column_Translation_From_AD_Element(524) 
;

-- Column: SAP_GLJournal.Reversal_ID
-- 2022-12-15T13:23:41.664Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585361,53457,0,18,53250,542275,'Reversal_ID',TO_TIMESTAMP('2022-12-15 15:23:41','YYYY-MM-DD HH24:MI:SS'),100,'N','ID of document reversal','de.metas.acct',0,22,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Reversal ID',0,0,TO_TIMESTAMP('2022-12-15 15:23:41','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-15T13:23:41.665Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585361 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:41.667Z
/* DDL */  select update_Column_Translation_From_AD_Element(53457) 
;

-- Column: SAP_GLJournal.TotalCr
-- 2022-12-15T13:23:42.266Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585362,596,0,12,542275,'TotalCr',TO_TIMESTAMP('2022-12-15 15:23:42','YYYY-MM-DD HH24:MI:SS'),100,'N','0','Total Credit in document currency','de.metas.acct',0,22,'The Total Credit indicates the total credit amount for a journal or journal batch in the source currency','Y','Y','N','N','Y','N','N','N','N','Y','N','N','N','N','N','N','N','Total Credit',0,0,TO_TIMESTAMP('2022-12-15 15:23:42','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:42.268Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585362 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:42.270Z
/* DDL */  select update_Column_Translation_From_AD_Element(596) 
;

-- Column: SAP_GLJournal.TotalDr
-- 2022-12-15T13:23:42.864Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585363,597,0,12,542275,'TotalDr',TO_TIMESTAMP('2022-12-15 15:23:42','YYYY-MM-DD HH24:MI:SS'),100,'N','0','Total debit in document currency','de.metas.acct',0,22,'The Total Debit indicates the total debit amount for a journal or journal batch in the source currency','Y','Y','N','N','Y','N','N','N','N','Y','N','N','N','N','N','N','N','Total Debit',0,0,TO_TIMESTAMP('2022-12-15 15:23:42','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:42.865Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585363 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:42.867Z
/* DDL */  select update_Column_Translation_From_AD_Element(597) 
;

-- Column: SAP_GLJournal.Updated
-- 2022-12-15T13:23:43.481Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585364,607,0,16,542275,'Updated',TO_TIMESTAMP('2022-12-15 15:23:43','YYYY-MM-DD HH24:MI:SS'),100,'N','Date this record was updated','de.metas.acct',0,7,'The Updated field indicates the date that this record was updated.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Updated',0,0,TO_TIMESTAMP('2022-12-15 15:23:43','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:43.482Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585364 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:43.484Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: SAP_GLJournal.UpdatedBy
-- 2022-12-15T13:23:44.153Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585365,608,0,18,110,542275,'UpdatedBy',TO_TIMESTAMP('2022-12-15 15:23:43','YYYY-MM-DD HH24:MI:SS'),100,'N','User who updated this records','de.metas.acct',0,22,'The Updated By field indicates the user who updated this record.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Updated By',0,0,TO_TIMESTAMP('2022-12-15 15:23:43','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2022-12-15T13:23:44.155Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585365 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T13:23:44.157Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- Window: GL Journal (SAP), InternalName=null
-- 2022-12-15T13:26:52.998Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,581870,0,541656,TO_TIMESTAMP('2022-12-15 15:26:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','Y','N','N','N','N','N','N','Y','GL Journal (SAP)','N',TO_TIMESTAMP('2022-12-15 15:26:52','YYYY-MM-DD HH24:MI:SS'),100,'T',0,0,100)
;

-- 2022-12-15T13:26:52.999Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541656 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2022-12-15T13:26:53.002Z
/* DDL */  select update_window_translation_from_ad_element(581870) 
;

-- 2022-12-15T13:26:53.012Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541656
;

-- 2022-12-15T13:26:53.013Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541656)
;

-- Tab: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)
-- Table: SAP_GLJournal
-- 2022-12-15T13:27:12.141Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581870,0,546730,542275,541656,'Y',TO_TIMESTAMP('2022-12-15 15:27:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','N','N','A','SAP_GLJournal','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'GL Journal (SAP)','N',10,0,TO_TIMESTAMP('2022-12-15 15:27:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:27:12.143Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546730 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-12-15T13:27:12.145Z
/* DDL */  select update_tab_translation_from_ad_element(581870) 
;

-- 2022-12-15T13:27:12.152Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546730)
;

-- Table: SAP_GLJournal
-- 2022-12-15T13:27:30.849Z
UPDATE AD_Table SET AD_Window_ID=541656,Updated=TO_TIMESTAMP('2022-12-15 15:27:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542275
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Client
-- Column: SAP_GLJournal.AD_Client_ID
-- 2022-12-15T13:28:39.089Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585331,709970,0,546730,TO_TIMESTAMP('2022-12-15 15:28:38','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',22,'de.metas.acct','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','N','N','N','Y','N','Client',TO_TIMESTAMP('2022-12-15 15:28:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:28:39.091Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=709970 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T13:28:39.092Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-12-15T13:28:39.327Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709970
;

-- 2022-12-15T13:28:39.329Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709970)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Organisation
-- Column: SAP_GLJournal.AD_Org_ID
-- 2022-12-15T13:28:39.431Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585332,709971,0,546730,TO_TIMESTAMP('2022-12-15 15:28:39','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client',22,'de.metas.acct','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2022-12-15 15:28:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:28:39.432Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=709971 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T13:28:39.433Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-12-15T13:28:39.578Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709971
;

-- 2022-12-15T13:28:39.580Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709971)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Accounting Schema
-- Column: SAP_GLJournal.C_AcctSchema_ID
-- 2022-12-15T13:28:39.694Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585333,709972,0,546730,TO_TIMESTAMP('2022-12-15 15:28:39','YYYY-MM-DD HH24:MI:SS'),100,'Rules for accounting',22,'de.metas.acct','An Accounting Schema defines the rules used in accounting such as costing method, currency and calendar','Y','N','N','N','N','N','N','N','Accounting Schema',TO_TIMESTAMP('2022-12-15 15:28:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:28:39.695Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=709972 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T13:28:39.697Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(181) 
;

-- 2022-12-15T13:28:39.705Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709972
;

-- 2022-12-15T13:28:39.706Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709972)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Conversiontype
-- Column: SAP_GLJournal.C_ConversionType_ID
-- 2022-12-15T13:28:39.814Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585334,709973,0,546730,TO_TIMESTAMP('2022-12-15 15:28:39','YYYY-MM-DD HH24:MI:SS'),100,'',22,'de.metas.acct','','Y','N','N','N','N','N','N','N','Conversiontype',TO_TIMESTAMP('2022-12-15 15:28:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:28:39.816Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=709973 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T13:28:39.817Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2278) 
;

-- 2022-12-15T13:28:39.820Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709973
;

-- 2022-12-15T13:28:39.821Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709973)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Currency
-- Column: SAP_GLJournal.C_Currency_ID
-- 2022-12-15T13:28:39.911Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585335,709974,0,546730,TO_TIMESTAMP('2022-12-15 15:28:39','YYYY-MM-DD HH24:MI:SS'),100,'The Currency for this record',22,'de.metas.acct','Indicates the Currency to be used when processing or reporting on this record','Y','N','N','N','N','N','N','N','Currency',TO_TIMESTAMP('2022-12-15 15:28:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:28:39.913Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=709974 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T13:28:39.914Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193) 
;

-- 2022-12-15T13:28:39.928Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709974
;

-- 2022-12-15T13:28:39.931Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709974)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Document Type
-- Column: SAP_GLJournal.C_DocType_ID
-- 2022-12-15T13:28:40.024Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585336,709975,0,546730,TO_TIMESTAMP('2022-12-15 15:28:39','YYYY-MM-DD HH24:MI:SS'),100,'Document type or rules',22,'de.metas.acct','The Document Type determines document sequence and processing rules','Y','N','N','N','N','N','N','N','Document Type',TO_TIMESTAMP('2022-12-15 15:28:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:28:40.026Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=709975 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T13:28:40.027Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(196) 
;

-- 2022-12-15T13:28:40.042Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709975
;

-- 2022-12-15T13:28:40.043Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709975)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Control Amount
-- Column: SAP_GLJournal.ControlAmt
-- 2022-12-15T13:28:40.155Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585337,709976,0,546730,TO_TIMESTAMP('2022-12-15 15:28:40','YYYY-MM-DD HH24:MI:SS'),100,'If not zero, the Debit amount of the document must be equal this amount',22,'de.metas.acct','If the control amount is zero, no check is performed.
Otherwise the total Debit amount must be equal to the control amount, before the document is processed.','Y','N','N','N','N','N','N','N','Control Amount',TO_TIMESTAMP('2022-12-15 15:28:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:28:40.157Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=709976 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T13:28:40.159Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(233) 
;

-- 2022-12-15T13:28:40.166Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709976
;

-- 2022-12-15T13:28:40.167Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709976)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Currency Rate
-- Column: SAP_GLJournal.CurrencyRate
-- 2022-12-15T13:28:40.270Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585341,709977,0,546730,TO_TIMESTAMP('2022-12-15 15:28:40','YYYY-MM-DD HH24:MI:SS'),100,'',22,'de.metas.acct','','Y','N','N','N','N','N','N','N','Currency Rate',TO_TIMESTAMP('2022-12-15 15:28:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:28:40.272Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=709977 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T13:28:40.273Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(253) 
;

-- 2022-12-15T13:28:40.277Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709977
;

-- 2022-12-15T13:28:40.278Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709977)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Accounting Date
-- Column: SAP_GLJournal.DateAcct
-- 2022-12-15T13:28:40.398Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585342,709978,0,546730,TO_TIMESTAMP('2022-12-15 15:28:40','YYYY-MM-DD HH24:MI:SS'),100,'Accounting Date',7,'de.metas.acct','The Accounting Date indicates the date to be used on the General Ledger account entries generated from this document. It is also used for any currency conversion.','Y','N','N','N','N','N','N','N','Accounting Date',TO_TIMESTAMP('2022-12-15 15:28:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:28:40.399Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=709978 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T13:28:40.401Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(263) 
;

-- 2022-12-15T13:28:40.406Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709978
;

-- 2022-12-15T13:28:40.406Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709978)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Document Date
-- Column: SAP_GLJournal.DateDoc
-- 2022-12-15T13:28:40.510Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585343,709979,0,546730,TO_TIMESTAMP('2022-12-15 15:28:40','YYYY-MM-DD HH24:MI:SS'),100,'',7,'de.metas.acct','The Document Date indicates the date the document was generated.  It may or may not be the same as the accounting date.','Y','N','N','N','N','N','N','N','Document Date',TO_TIMESTAMP('2022-12-15 15:28:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:28:40.511Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=709979 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T13:28:40.513Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(265) 
;

-- 2022-12-15T13:28:40.517Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709979
;

-- 2022-12-15T13:28:40.518Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709979)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Description
-- Column: SAP_GLJournal.Description
-- 2022-12-15T13:28:40.617Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585344,709980,0,546730,TO_TIMESTAMP('2022-12-15 15:28:40','YYYY-MM-DD HH24:MI:SS'),100,'',255,'de.metas.acct','','Y','N','N','N','N','N','N','N','Description',TO_TIMESTAMP('2022-12-15 15:28:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:28:40.619Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=709980 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T13:28:40.621Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2022-12-15T13:28:40.666Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709980
;

-- 2022-12-15T13:28:40.668Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709980)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Process Batch
-- Column: SAP_GLJournal.DocAction
-- 2022-12-15T13:28:40.772Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585345,709981,0,546730,TO_TIMESTAMP('2022-12-15 15:28:40','YYYY-MM-DD HH24:MI:SS'),100,2,'de.metas.acct','Y','N','N','N','N','N','N','N','Process Batch',TO_TIMESTAMP('2022-12-15 15:28:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:28:40.774Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=709981 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T13:28:40.775Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(287) 
;

-- 2022-12-15T13:28:40.780Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709981
;

-- 2022-12-15T13:28:40.781Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709981)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Status
-- Column: SAP_GLJournal.DocStatus
-- 2022-12-15T13:28:40.886Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585346,709982,0,546730,TO_TIMESTAMP('2022-12-15 15:28:40','YYYY-MM-DD HH24:MI:SS'),100,'',2,'de.metas.acct','','Y','N','N','N','N','N','N','N','Status',TO_TIMESTAMP('2022-12-15 15:28:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:28:40.887Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=709982 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T13:28:40.889Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(289) 
;

-- 2022-12-15T13:28:40.895Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709982
;

-- 2022-12-15T13:28:40.895Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709982)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Document No
-- Column: SAP_GLJournal.DocumentNo
-- 2022-12-15T13:28:40.998Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585347,709983,0,546730,TO_TIMESTAMP('2022-12-15 15:28:40','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document',30,'de.metas.acct','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','N','N','N','N','N','Document No',TO_TIMESTAMP('2022-12-15 15:28:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:28:41Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=709983 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T13:28:41.001Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(290) 
;

-- 2022-12-15T13:28:41.005Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709983
;

-- 2022-12-15T13:28:41.006Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709983)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Budget
-- Column: SAP_GLJournal.GL_Budget_ID
-- 2022-12-15T13:28:41.103Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585348,709984,0,546730,TO_TIMESTAMP('2022-12-15 15:28:41','YYYY-MM-DD HH24:MI:SS'),100,'General Ledger Budget',22,'de.metas.acct','The General Ledger Budget identifies a user defined budget.  These can be used in reporting as a comparison against your actual amounts.','Y','N','N','N','N','N','N','N','Budget',TO_TIMESTAMP('2022-12-15 15:28:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:28:41.104Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=709984 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T13:28:41.106Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(308) 
;

-- 2022-12-15T13:28:41.113Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709984
;

-- 2022-12-15T13:28:41.115Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709984)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> GL Category
-- Column: SAP_GLJournal.GL_Category_ID
-- 2022-12-15T13:28:41.210Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585349,709985,0,546730,TO_TIMESTAMP('2022-12-15 15:28:41','YYYY-MM-DD HH24:MI:SS'),100,'General Ledger Category',22,'de.metas.acct','The General Ledger Category is an optional, user defined method of grouping journal lines.','Y','N','N','N','N','N','N','N','GL Category',TO_TIMESTAMP('2022-12-15 15:28:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:28:41.211Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=709985 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T13:28:41.212Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(309) 
;

-- 2022-12-15T13:28:41.216Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709985
;

-- 2022-12-15T13:28:41.217Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709985)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Journal Run
-- Column: SAP_GLJournal.GL_JournalBatch_ID
-- 2022-12-15T13:28:41.315Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585350,709986,0,546730,TO_TIMESTAMP('2022-12-15 15:28:41','YYYY-MM-DD HH24:MI:SS'),100,'',22,'de.metas.acct','','Y','N','N','N','N','N','N','N','Journal Run',TO_TIMESTAMP('2022-12-15 15:28:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:28:41.316Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=709986 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T13:28:41.318Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(313) 
;

-- 2022-12-15T13:28:41.323Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709986
;

-- 2022-12-15T13:28:41.324Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709986)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> GL Journal (SAP)
-- Column: SAP_GLJournal.SAP_GLJournal_ID
-- 2022-12-15T13:28:41.434Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585351,709987,0,546730,TO_TIMESTAMP('2022-12-15 15:28:41','YYYY-MM-DD HH24:MI:SS'),100,22,'de.metas.acct','Y','N','N','N','N','N','N','N','GL Journal (SAP)',TO_TIMESTAMP('2022-12-15 15:28:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:28:41.436Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=709987 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T13:28:41.437Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581870) 
;

-- 2022-12-15T13:28:41.440Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709987
;

-- 2022-12-15T13:28:41.441Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709987)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Active
-- Column: SAP_GLJournal.IsActive
-- 2022-12-15T13:28:41.551Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585352,709988,0,546730,TO_TIMESTAMP('2022-12-15 15:28:41','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',1,'de.metas.acct','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','N','N','N','N','N','Active',TO_TIMESTAMP('2022-12-15 15:28:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:28:41.553Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=709988 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T13:28:41.554Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-12-15T13:28:41.720Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709988
;

-- 2022-12-15T13:28:41.722Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709988)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Approved
-- Column: SAP_GLJournal.IsApproved
-- 2022-12-15T13:28:41.843Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585353,709989,0,546730,TO_TIMESTAMP('2022-12-15 15:28:41','YYYY-MM-DD HH24:MI:SS'),100,'Indicates if this document requires approval',1,'de.metas.acct','The Approved checkbox indicates if this document requires approval before it can be processed.','Y','N','N','N','N','N','N','N','Approved',TO_TIMESTAMP('2022-12-15 15:28:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:28:41.844Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=709989 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T13:28:41.846Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(351) 
;

-- 2022-12-15T13:28:41.853Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709989
;

-- 2022-12-15T13:28:41.854Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709989)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Printed
-- Column: SAP_GLJournal.IsPrinted
-- 2022-12-15T13:28:41.953Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585354,709990,0,546730,TO_TIMESTAMP('2022-12-15 15:28:41','YYYY-MM-DD HH24:MI:SS'),100,'Indicates if this document / line is printed',1,'de.metas.acct','The Printed checkbox indicates if this document or line will included when printing.','Y','N','N','N','N','N','N','N','Printed',TO_TIMESTAMP('2022-12-15 15:28:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:28:41.955Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=709990 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T13:28:41.956Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(399) 
;

-- 2022-12-15T13:28:41.964Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709990
;

-- 2022-12-15T13:28:41.964Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709990)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Section Code
-- Column: SAP_GLJournal.M_SectionCode_ID
-- 2022-12-15T13:28:42.067Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585355,709991,0,546730,TO_TIMESTAMP('2022-12-15 15:28:41','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.acct','Y','N','N','N','N','N','N','N','Section Code',TO_TIMESTAMP('2022-12-15 15:28:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:28:42.069Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=709991 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T13:28:42.070Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2022-12-15T13:28:42.074Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709991
;

-- 2022-12-15T13:28:42.075Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709991)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Posting status
-- Column: SAP_GLJournal.Posted
-- 2022-12-15T13:28:42.177Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585356,709992,0,546730,TO_TIMESTAMP('2022-12-15 15:28:42','YYYY-MM-DD HH24:MI:SS'),100,'Posting status',1,'de.metas.acct','The ''Posting Status'' field indicates the status of the General Ledger accounting lines.','Y','N','N','N','N','N','N','N','Posting status',TO_TIMESTAMP('2022-12-15 15:28:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:28:42.179Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=709992 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T13:28:42.180Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1308) 
;

-- 2022-12-15T13:28:42.188Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709992
;

-- 2022-12-15T13:28:42.189Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709992)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Posting Error
-- Column: SAP_GLJournal.PostingError_Issue_ID
-- 2022-12-15T13:28:42.287Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585357,709993,0,546730,TO_TIMESTAMP('2022-12-15 15:28:42','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.acct','Y','N','N','N','N','N','N','N','Posting Error',TO_TIMESTAMP('2022-12-15 15:28:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:28:42.288Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=709993 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T13:28:42.290Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577755) 
;

-- 2022-12-15T13:28:42.296Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709993
;

-- 2022-12-15T13:28:42.297Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709993)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Posting Type
-- Column: SAP_GLJournal.PostingType
-- 2022-12-15T13:28:42.397Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585358,709994,0,546730,TO_TIMESTAMP('2022-12-15 15:28:42','YYYY-MM-DD HH24:MI:SS'),100,'',1,'de.metas.acct','','Y','N','N','N','N','N','N','N','Posting Type',TO_TIMESTAMP('2022-12-15 15:28:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:28:42.399Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=709994 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T13:28:42.400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(514) 
;

-- 2022-12-15T13:28:42.403Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709994
;

-- 2022-12-15T13:28:42.404Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709994)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Processed
-- Column: SAP_GLJournal.Processed
-- 2022-12-15T13:28:42.510Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585359,709995,0,546730,TO_TIMESTAMP('2022-12-15 15:28:42','YYYY-MM-DD HH24:MI:SS'),100,'',1,'de.metas.acct','','Y','N','N','N','N','N','N','N','Processed',TO_TIMESTAMP('2022-12-15 15:28:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:28:42.512Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=709995 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T13:28:42.513Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047) 
;

-- 2022-12-15T13:28:42.519Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709995
;

-- 2022-12-15T13:28:42.520Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709995)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Process Now
-- Column: SAP_GLJournal.Processing
-- 2022-12-15T13:28:42.612Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585360,709996,0,546730,TO_TIMESTAMP('2022-12-15 15:28:42','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.acct','Y','N','N','N','N','N','N','N','Process Now',TO_TIMESTAMP('2022-12-15 15:28:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:28:42.613Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=709996 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T13:28:42.615Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(524) 
;

-- 2022-12-15T13:28:42.634Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709996
;

-- 2022-12-15T13:28:42.636Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709996)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Reversal ID
-- Column: SAP_GLJournal.Reversal_ID
-- 2022-12-15T13:28:42.733Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585361,709997,0,546730,TO_TIMESTAMP('2022-12-15 15:28:42','YYYY-MM-DD HH24:MI:SS'),100,'ID of document reversal',22,'de.metas.acct','Y','N','N','N','N','N','N','N','Reversal ID',TO_TIMESTAMP('2022-12-15 15:28:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:28:42.735Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=709997 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T13:28:42.736Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53457) 
;

-- 2022-12-15T13:28:42.741Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709997
;

-- 2022-12-15T13:28:42.741Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709997)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Total Credit
-- Column: SAP_GLJournal.TotalCr
-- 2022-12-15T13:28:42.845Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585362,709998,0,546730,TO_TIMESTAMP('2022-12-15 15:28:42','YYYY-MM-DD HH24:MI:SS'),100,'Total Credit in document currency',22,'de.metas.acct','The Total Credit indicates the total credit amount for a journal or journal batch in the source currency','Y','N','N','N','N','N','N','N','Total Credit',TO_TIMESTAMP('2022-12-15 15:28:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:28:42.846Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=709998 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T13:28:42.848Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(596) 
;

-- 2022-12-15T13:28:42.851Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709998
;

-- 2022-12-15T13:28:42.852Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709998)
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Total Debit
-- Column: SAP_GLJournal.TotalDr
-- 2022-12-15T13:28:42.957Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585363,709999,0,546730,TO_TIMESTAMP('2022-12-15 15:28:42','YYYY-MM-DD HH24:MI:SS'),100,'Total debit in document currency',22,'de.metas.acct','The Total Debit indicates the total debit amount for a journal or journal batch in the source currency','Y','N','N','N','N','N','N','N','Total Debit',TO_TIMESTAMP('2022-12-15 15:28:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T13:28:42.959Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=709999 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T13:28:42.960Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(597) 
;

-- 2022-12-15T13:28:42.963Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709999
;

-- 2022-12-15T13:28:42.964Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709999)
;

-- Tab: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct)
-- UI Section: main
-- 2022-12-15T13:31:19.466Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546730,545358,TO_TIMESTAMP('2022-12-15 15:31:19','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-12-15 15:31:19','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-12-15T13:31:19.468Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545358 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main
-- UI Column: 10
-- 2022-12-15T13:31:23.041Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546535,545358,TO_TIMESTAMP('2022-12-15 15:31:22','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-12-15 15:31:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main
-- UI Column: 20
-- 2022-12-15T13:31:24.227Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546536,545358,TO_TIMESTAMP('2022-12-15 15:31:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-12-15 15:31:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 10
-- UI Element Group: primary
-- 2022-12-15T13:31:37.711Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546535,550183,TO_TIMESTAMP('2022-12-15 15:31:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','primary',10,TO_TIMESTAMP('2022-12-15 15:31:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 10 -> primary.Document No
-- Column: SAP_GLJournal.DocumentNo
-- 2022-12-15T13:31:55.898Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,709983,0,546730,550183,614521,'F',TO_TIMESTAMP('2022-12-15 15:31:55','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','N','N','Document No',10,0,0,TO_TIMESTAMP('2022-12-15 15:31:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 10 -> primary.Description
-- Column: SAP_GLJournal.Description
-- 2022-12-15T13:32:53.598Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,709980,0,546730,550183,614522,'F',TO_TIMESTAMP('2022-12-15 15:32:53','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Description',20,0,0,TO_TIMESTAMP('2022-12-15 15:32:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 10 -> primary.GL Category
-- Column: SAP_GLJournal.GL_Category_ID
-- 2022-12-15T13:33:08.540Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,709985,0,546730,550183,614523,'F',TO_TIMESTAMP('2022-12-15 15:33:08','YYYY-MM-DD HH24:MI:SS'),100,'General Ledger Category','The General Ledger Category is an optional, user defined method of grouping journal lines.','Y','N','Y','N','N','GL Category',30,0,0,TO_TIMESTAMP('2022-12-15 15:33:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 10 -> primary.Posting Type
-- Column: SAP_GLJournal.PostingType
-- 2022-12-15T13:33:17.908Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,709994,0,546730,550183,614524,'F',TO_TIMESTAMP('2022-12-15 15:33:17','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Posting Type',40,0,0,TO_TIMESTAMP('2022-12-15 15:33:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 10 -> primary.Currency
-- Column: SAP_GLJournal.C_Currency_ID
-- 2022-12-15T13:33:30.730Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,709974,0,546730,550183,614525,'F',TO_TIMESTAMP('2022-12-15 15:33:30','YYYY-MM-DD HH24:MI:SS'),100,'The Currency for this record','Indicates the Currency to be used when processing or reporting on this record','Y','N','Y','N','N','Currency',50,0,0,TO_TIMESTAMP('2022-12-15 15:33:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 10 -> primary.Currency Rate
-- Column: SAP_GLJournal.CurrencyRate
-- 2022-12-15T13:33:42.592Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,709977,0,546730,550183,614526,'F',TO_TIMESTAMP('2022-12-15 15:33:39','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Currency Rate',60,0,0,TO_TIMESTAMP('2022-12-15 15:33:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 10
-- UI Element Group: control amts
-- 2022-12-15T13:33:56.021Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546535,550184,TO_TIMESTAMP('2022-12-15 15:33:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','control amts',20,TO_TIMESTAMP('2022-12-15 15:33:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 10 -> control amts.Total Debit
-- Column: SAP_GLJournal.TotalDr
-- 2022-12-15T13:34:19.717Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,709999,0,546730,550184,614527,'F',TO_TIMESTAMP('2022-12-15 15:34:19','YYYY-MM-DD HH24:MI:SS'),100,'Total debit in document currency','The Total Debit indicates the total debit amount for a journal or journal batch in the source currency','Y','N','Y','N','N','Total Debit',10,0,0,TO_TIMESTAMP('2022-12-15 15:34:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 10 -> control amts.Total Credit
-- Column: SAP_GLJournal.TotalCr
-- 2022-12-15T13:34:29.678Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,709998,0,546730,550184,614528,'F',TO_TIMESTAMP('2022-12-15 15:34:29','YYYY-MM-DD HH24:MI:SS'),100,'Total Credit in document currency','The Total Credit indicates the total credit amount for a journal or journal batch in the source currency','Y','N','Y','N','N','Total Credit',20,0,0,TO_TIMESTAMP('2022-12-15 15:34:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 20
-- UI Element Group: doc type & date
-- 2022-12-15T13:34:51.896Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546536,550185,TO_TIMESTAMP('2022-12-15 15:34:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','doc type & date',10,TO_TIMESTAMP('2022-12-15 15:34:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 20 -> doc type & date.Document Type
-- Column: SAP_GLJournal.C_DocType_ID
-- 2022-12-15T13:35:15.834Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,709975,0,546730,550185,614529,'F',TO_TIMESTAMP('2022-12-15 15:35:15','YYYY-MM-DD HH24:MI:SS'),100,'Document type or rules','The Document Type determines document sequence and processing rules','Y','N','Y','N','N','Document Type',10,0,0,TO_TIMESTAMP('2022-12-15 15:35:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 20 -> doc type & date.Document No
-- Column: SAP_GLJournal.DocumentNo
-- 2022-12-15T13:37:25.796Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550185, IsDisplayed='Y', SeqNo=20,Updated=TO_TIMESTAMP('2022-12-15 15:37:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614521
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 20 -> doc type & date.Document Date
-- Column: SAP_GLJournal.DateDoc
-- 2022-12-15T13:37:51.770Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,709979,0,546730,550185,614530,'F',TO_TIMESTAMP('2022-12-15 15:37:51','YYYY-MM-DD HH24:MI:SS'),100,'','The Document Date indicates the date the document was generated.  It may or may not be the same as the accounting date.','Y','N','Y','N','N','Document Date',30,0,0,TO_TIMESTAMP('2022-12-15 15:37:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 20 -> doc type & date.Accounting Date
-- Column: SAP_GLJournal.DateAcct
-- 2022-12-15T13:38:00.867Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,709978,0,546730,550185,614531,'F',TO_TIMESTAMP('2022-12-15 15:38:00','YYYY-MM-DD HH24:MI:SS'),100,'Accounting Date','The Accounting Date indicates the date to be used on the General Ledger account entries generated from this document. It is also used for any currency conversion.','Y','N','Y','N','N','Accounting Date',40,0,0,TO_TIMESTAMP('2022-12-15 15:38:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 20
-- UI Element Group: approval
-- 2022-12-15T13:38:22.852Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546536,550186,TO_TIMESTAMP('2022-12-15 15:38:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','approval',20,TO_TIMESTAMP('2022-12-15 15:38:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 20
-- UI Element Group: doc status
-- 2022-12-15T13:38:48.184Z
UPDATE AD_UI_ElementGroup SET Name='doc status',Updated=TO_TIMESTAMP('2022-12-15 15:38:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550186
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 20 -> doc status.Approved
-- Column: SAP_GLJournal.IsApproved
-- 2022-12-15T13:39:22.310Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,709989,0,546730,550186,614532,'F',TO_TIMESTAMP('2022-12-15 15:39:22','YYYY-MM-DD HH24:MI:SS'),100,'Indicates if this document requires approval','The Approved checkbox indicates if this document requires approval before it can be processed.','Y','N','Y','N','N','Approved',10,0,0,TO_TIMESTAMP('2022-12-15 15:39:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 20 -> doc status.Status
-- Column: SAP_GLJournal.DocStatus
-- 2022-12-15T13:39:38.122Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,709982,0,546730,550186,614533,'F',TO_TIMESTAMP('2022-12-15 15:39:37','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Status',20,0,0,TO_TIMESTAMP('2022-12-15 15:39:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 20
-- UI Element Group: posting
-- 2022-12-15T13:40:05.291Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546536,550187,TO_TIMESTAMP('2022-12-15 15:40:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','posting',30,TO_TIMESTAMP('2022-12-15 15:40:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 20 -> posting.Posting status
-- Column: SAP_GLJournal.Posted
-- 2022-12-15T13:40:17.127Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,709992,0,546730,550187,614534,'F',TO_TIMESTAMP('2022-12-15 15:40:16','YYYY-MM-DD HH24:MI:SS'),100,'Posting status','The ''Posting Status'' field indicates the status of the General Ledger accounting lines.','Y','N','Y','N','N','Posting status',10,0,0,TO_TIMESTAMP('2022-12-15 15:40:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 20 -> posting.Posting Error
-- Column: SAP_GLJournal.PostingError_Issue_ID
-- 2022-12-15T13:40:26.455Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,709993,0,546730,550187,614535,'F',TO_TIMESTAMP('2022-12-15 15:40:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Posting Error',20,0,0,TO_TIMESTAMP('2022-12-15 15:40:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 20
-- UI Element Group: org
-- 2022-12-15T13:40:35.838Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546536,550188,TO_TIMESTAMP('2022-12-15 15:40:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',40,TO_TIMESTAMP('2022-12-15 15:40:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 20 -> org.Section Code
-- Column: SAP_GLJournal.M_SectionCode_ID
-- 2022-12-15T13:40:51.844Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,709991,0,546730,550188,614536,'F',TO_TIMESTAMP('2022-12-15 15:40:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Section Code',10,0,0,TO_TIMESTAMP('2022-12-15 15:40:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 20 -> org.Organisation
-- Column: SAP_GLJournal.AD_Org_ID
-- 2022-12-15T13:40:58.203Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,709971,0,546730,550188,614537,'F',TO_TIMESTAMP('2022-12-15 15:40:58','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','Y','N','N','Organisation',20,0,0,TO_TIMESTAMP('2022-12-15 15:40:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 20 -> org.Client
-- Column: SAP_GLJournal.AD_Client_ID
-- 2022-12-15T13:41:07.942Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,709970,0,546730,550188,614538,'F',TO_TIMESTAMP('2022-12-15 15:41:07','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','Y','N','N','Client',30,0,0,TO_TIMESTAMP('2022-12-15 15:41:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: SAP_GLJournal.C_Period_ID
-- 2022-12-15T13:41:48.167Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585338
;

-- 2022-12-15T13:41:48.171Z
DELETE FROM AD_Column WHERE AD_Column_ID=585338
;

-- 2022-12-15T13:42:12.759Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709984
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Budget
-- Column: SAP_GLJournal.GL_Budget_ID
-- 2022-12-15T13:42:12.762Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=709984
;

-- 2022-12-15T13:42:12.768Z
DELETE FROM AD_Field WHERE AD_Field_ID=709984
;

-- Column: SAP_GLJournal.GL_Budget_ID
-- 2022-12-15T13:42:12.774Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585348
;

-- 2022-12-15T13:42:12.777Z
DELETE FROM AD_Column WHERE AD_Column_ID=585348
;

-- 2022-12-15T13:42:32.700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709986
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Journal Run
-- Column: SAP_GLJournal.GL_JournalBatch_ID
-- 2022-12-15T13:42:32.703Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=709986
;

-- 2022-12-15T13:42:32.709Z
DELETE FROM AD_Field WHERE AD_Field_ID=709986
;

-- Column: SAP_GLJournal.GL_JournalBatch_ID
-- 2022-12-15T13:42:32.715Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585350
;

-- 2022-12-15T13:42:32.718Z
DELETE FROM AD_Column WHERE AD_Column_ID=585350
;

-- 2022-12-15T13:42:48.586Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709990
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Printed
-- Column: SAP_GLJournal.IsPrinted
-- 2022-12-15T13:42:48.589Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=709990
;

-- 2022-12-15T13:42:48.593Z
DELETE FROM AD_Field WHERE AD_Field_ID=709990
;

-- Column: SAP_GLJournal.IsPrinted
-- 2022-12-15T13:42:48.602Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585354
;

-- 2022-12-15T13:42:48.605Z
DELETE FROM AD_Column WHERE AD_Column_ID=585354
;

-- 2022-12-15T13:43:22.674Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709976
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Control Amount
-- Column: SAP_GLJournal.ControlAmt
-- 2022-12-15T13:43:22.676Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=709976
;

-- 2022-12-15T13:43:22.681Z
DELETE FROM AD_Field WHERE AD_Field_ID=709976
;

-- Column: SAP_GLJournal.ControlAmt
-- 2022-12-15T13:43:22.688Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585337
;

-- 2022-12-15T13:43:22.692Z
DELETE FROM AD_Column WHERE AD_Column_ID=585337
;

-- UI Column: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 10
-- UI Element Group: currency
-- 2022-12-15T13:44:24.993Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546535,550189,TO_TIMESTAMP('2022-12-15 15:44:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','currency',20,TO_TIMESTAMP('2022-12-15 15:44:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 10
-- UI Element Group: control amts
-- 2022-12-15T13:44:29.036Z
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2022-12-15 15:44:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550184
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 10 -> currency.Currency
-- Column: SAP_GLJournal.C_Currency_ID
-- 2022-12-15T13:53:03.686Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550189, IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2022-12-15 15:53:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614525
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 10 -> currency.Conversiontype
-- Column: SAP_GLJournal.C_ConversionType_ID
-- 2022-12-15T13:53:34.333Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,709973,0,546730,550189,614539,'F',TO_TIMESTAMP('2022-12-15 15:53:34','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Conversiontype',20,0,0,TO_TIMESTAMP('2022-12-15 15:53:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 10 -> currency.Currency Rate
-- Column: SAP_GLJournal.CurrencyRate
-- 2022-12-15T13:53:50.640Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550189, IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2022-12-15 15:53:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614526
;

-- UI Column: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 10
-- UI Element Group: control amts
-- 2022-12-15T13:54:13.274Z
UPDATE AD_UI_ElementGroup SET SeqNo=40,Updated=TO_TIMESTAMP('2022-12-15 15:54:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550184
;

-- UI Column: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 10
-- UI Element Group: accounting schema, posting type
-- 2022-12-15T13:54:29.075Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546535,550190,TO_TIMESTAMP('2022-12-15 15:54:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','accounting schema, posting type',30,TO_TIMESTAMP('2022-12-15 15:54:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 10 -> accounting schema, posting type.Posting Type
-- Column: SAP_GLJournal.PostingType
-- 2022-12-15T13:54:44.821Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550190, IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2022-12-15 15:54:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614524
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 10 -> accounting schema, posting type.Accounting Schema
-- Column: SAP_GLJournal.C_AcctSchema_ID
-- 2022-12-15T13:54:53.499Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,709972,0,546730,550190,614540,'F',TO_TIMESTAMP('2022-12-15 15:54:53','YYYY-MM-DD HH24:MI:SS'),100,'Rules for accounting','An Accounting Schema defines the rules used in accounting such as costing method, currency and calendar','Y','N','Y','N','N','Accounting Schema',20,0,0,TO_TIMESTAMP('2022-12-15 15:54:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> main -> 10 -> accounting schema, posting type.GL Category
-- Column: SAP_GLJournal.GL_Category_ID
-- 2022-12-15T13:56:43.760Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550190, IsDisplayed='Y', SeqNo=30,Updated=TO_TIMESTAMP('2022-12-15 15:56:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614523
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Posting status
-- Column: SAP_GLJournal.Posted
-- 2022-12-15T14:04:19.222Z
UPDATE AD_Field SET DisplayLogic='@Processed/N@=''Y''',Updated=TO_TIMESTAMP('2022-12-15 16:04:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=709992
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal (SAP)(546730,de.metas.acct) -> Posting Error
-- Column: SAP_GLJournal.PostingError_Issue_ID
-- 2022-12-15T14:04:37.369Z
UPDATE AD_Field SET DisplayLogic='@PostingError_Issue_ID/0@>0',Updated=TO_TIMESTAMP('2022-12-15 16:04:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=709993
;

-- Window: GL Journal (SAP), InternalName=sapGLJournal
-- 2022-12-15T14:06:55.251Z
UPDATE AD_Window SET InternalName='sapGLJournal',Updated=TO_TIMESTAMP('2022-12-15 16:06:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541656
;

-- Name: GL Journal (SAP)
-- Action Type: W
-- Window: GL Journal (SAP)(541656,de.metas.acct)
-- 2022-12-15T14:07:02.903Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,581870,542031,0,541656,TO_TIMESTAMP('2022-12-15 16:07:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.acct','sapGLJournal','Y','N','N','N','N','GL Journal (SAP)',TO_TIMESTAMP('2022-12-15 16:07:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-15T14:07:02.904Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542031 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2022-12-15T14:07:02.905Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542031, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542031)
;

-- 2022-12-15T14:07:02.908Z
/* DDL */  select update_menu_translation_from_ad_element(581870) 
;

-- Reordering children of `Performance Analysis`
-- Node name: `Accounting Rules`
-- 2022-12-15T14:07:03.493Z
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=164 AND AD_Tree_ID=10
;

-- Node name: `Financial Reporting`
-- 2022-12-15T14:07:03.494Z
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=280 AND AD_Tree_ID=10
;

-- Node name: `Performance Measurement`
-- 2022-12-15T14:07:03.495Z
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=158 AND AD_Tree_ID=10
;

-- Node name: `Costing`
-- 2022-12-15T14:07:03.496Z
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=522 AND AD_Tree_ID=10
;

-- Node name: `GL Journal Batch (GL_JournalBatch)`
-- 2022-12-15T14:07:03.496Z
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=118 AND AD_Tree_ID=10
;

-- Node name: `Accounting Fact Details (Fact_Acct_Transactions_View)`
-- 2022-12-15T14:07:03.498Z
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=169 AND AD_Tree_ID=10
;

-- Node name: `Accounting Fact Details`
-- 2022-12-15T14:07:03.498Z
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=433 AND AD_Tree_ID=10
;

-- Node name: `Accounting Fact Balances (Fact_Acct_Balance)`
-- 2022-12-15T14:07:03.499Z
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=352 AND AD_Tree_ID=10
;

-- Node name: `Accounting Fact Daily`
-- 2022-12-15T14:07:03.500Z
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=434 AND AD_Tree_ID=10
;

-- Node name: `Accounting Fact Period`
-- 2022-12-15T14:07:03.501Z
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=435 AND AD_Tree_ID=10
;

-- Node name: `Kostenstellen Belege (Fact_Acct_ActivityChangeRequest)`
-- 2022-12-15T14:07:03.502Z
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540677 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (SAP)`
-- 2022-12-15T14:07:03.502Z
UPDATE AD_TreeNodeMM SET Parent_ID=278, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542031 AND AD_Tree_ID=10
;

-- Reordering children of `Finance`
-- Node name: `Remittance Advice (REMADV) (C_RemittanceAdvice)`
-- 2022-12-15T14:07:17.204Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541584 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (GL_Journal)`
-- 2022-12-15T14:07:17.204Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540905 AND AD_Tree_ID=10
;

-- Node name: `GL Journal (SAP)`
-- 2022-12-15T14:07:17.205Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542031 AND AD_Tree_ID=10
;

-- Node name: `Bank Account (C_BP_BankAccount)`
-- 2022-12-15T14:07:17.206Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540814 AND AD_Tree_ID=10
;

-- Node name: `Import Bank Statement (I_BankStatement)`
-- 2022-12-15T14:07:17.207Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541297 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement (C_BankStatement)`
-- 2022-12-15T14:07:17.208Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540803 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal (C_BankStatement)`
-- 2022-12-15T14:07:17.209Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541377 AND AD_Tree_ID=10
;

-- Node name: `Bankstatement Reference`
-- 2022-12-15T14:07:17.210Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540904 AND AD_Tree_ID=10
;

-- Node name: `Payment (C_Payment)`
-- 2022-12-15T14:07:17.210Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540749 AND AD_Tree_ID=10
;

-- Node name: `Manual Payment Allocations (C_AllocationHdr)`
-- 2022-12-15T14:07:17.211Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540779 AND AD_Tree_ID=10
;

-- Node name: `Payment Selection (C_PaySelection)`
-- 2022-12-15T14:07:17.212Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540910 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation (C_Payment_Reservation)`
-- 2022-12-15T14:07:17.213Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541308 AND AD_Tree_ID=10
;

-- Node name: `Payment Reservation Capture (C_Payment_Reservation_Capture)`
-- 2022-12-15T14:07:17.213Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541313 AND AD_Tree_ID=10
;

-- Node name: `Dunning (C_DunningDoc)`
-- 2022-12-15T14:07:17.214Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540758 AND AD_Tree_ID=10
;

-- Node name: `Dunning Candidates (C_Dunning_Candidate)`
-- 2022-12-15T14:07:17.215Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540759 AND AD_Tree_ID=10
;

-- Node name: `Accounting Transactions (Fact_Acct_Transactions_View)`
-- 2022-12-15T14:07:17.215Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540806 AND AD_Tree_ID=10
;

-- Node name: `ESR Payment Import (ESR_Import)`
-- 2022-12-15T14:07:17.216Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540891 AND AD_Tree_ID=10
;

-- Node name: `Account Combination (C_ValidCombination)`
-- 2022-12-15T14:07:17.217Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540896 AND AD_Tree_ID=10
;

-- Node name: `Chart of Accounts (C_Element)`
-- 2022-12-15T14:07:17.217Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540903 AND AD_Tree_ID=10
;

-- Node name: `Element values (C_ElementValue)`
-- 2022-12-15T14:07:17.218Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541405 AND AD_Tree_ID=10
;

-- Node name: `Productcosts (M_Product)`
-- 2022-12-15T14:07:17.219Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540906 AND AD_Tree_ID=10
;

-- Node name: `Current Product Costs (M_Cost)`
-- 2022-12-15T14:07:17.220Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541455 AND AD_Tree_ID=10
;

-- Node name: `Products Without Initial Cost (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2022-12-15T14:07:17.221Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541710 AND AD_Tree_ID=10
;

-- Node name: `Products With Booked Quantity (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2022-12-15T14:07:17.221Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541717 AND AD_Tree_ID=10
;

-- Node name: `Cost Detail (M_CostDetail)`
-- 2022-12-15T14:07:17.222Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541454 AND AD_Tree_ID=10
;

-- Node name: `Costcenter Documents (Fact_Acct_ActivityChangeRequest)`
-- 2022-12-15T14:07:17.223Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540907 AND AD_Tree_ID=10
;

-- Node name: `Cost Center (C_Activity)`
-- 2022-12-15T14:07:17.223Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540908 AND AD_Tree_ID=10
;

-- Node name: `Referenz No (C_ReferenceNo)`
-- 2022-12-15T14:07:17.224Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541015 AND AD_Tree_ID=10
;

-- Node name: `Referenz No Type (C_ReferenceNo_Type)`
-- 2022-12-15T14:07:17.225Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541016 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export (DATEV_Export)`
-- 2022-12-15T14:07:17.225Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541042 AND AD_Tree_ID=10
;

-- Node name: `Matched Invoices (M_MatchInv)`
-- 2022-12-15T14:07:17.226Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=315 AND AD_Tree_ID=10
;

-- Node name: `UnPosted Documents (RV_UnPosted)`
-- 2022-12-15T14:07:17.227Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541368 AND AD_Tree_ID=10
;

-- Node name: `Import Datev Payment (I_Datev_Payment)`
-- 2022-12-15T14:07:17.228Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541120 AND AD_Tree_ID=10
;

-- Node name: `Enqueue all not posted documents (de.metas.acct.process.Documents_EnqueueNotPosted)`
-- 2022-12-15T14:07:17.228Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541125 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2022-12-15T14:07:17.229Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000056 AND AD_Tree_ID=10
;

-- Node name: `PayPal`
-- 2022-12-15T14:07:17.230Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541304 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2022-12-15T14:07:17.230Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000064 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2022-12-15T14:07:17.232Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000072 AND AD_Tree_ID=10
;

-- Node name: `Bank Statement Import-File (C_BankStatement_Import_File)`
-- 2022-12-15T14:07:17.232Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000015, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542014 AND AD_Tree_ID=10
;

-- 2022-12-15T14:08:19.338Z
/* DDL */ CREATE TABLE public.SAP_GLJournal (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_AcctSchema_ID NUMERIC(10) NOT NULL, C_ConversionType_ID NUMERIC(10) NOT NULL, C_Currency_ID NUMERIC(10) NOT NULL, C_DocType_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, CurrencyRate NUMERIC DEFAULT 1 NOT NULL, DateAcct TIMESTAMP WITHOUT TIME ZONE NOT NULL, DateDoc TIMESTAMP WITHOUT TIME ZONE NOT NULL, Description VARCHAR(255) NOT NULL, DocAction CHAR(2) DEFAULT 'CO' NOT NULL, DocStatus VARCHAR(2) DEFAULT 'DR' NOT NULL, DocumentNo VARCHAR(30) NOT NULL, GL_Category_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) DEFAULT 'Y' CHECK (IsActive IN ('Y','N')) NOT NULL, IsApproved CHAR(1) DEFAULT 'Y' CHECK (IsApproved IN ('Y','N')) NOT NULL, M_SectionCode_ID NUMERIC(10), Posted CHAR(1) DEFAULT 'N' NOT NULL, PostingError_Issue_ID NUMERIC(10), PostingType CHAR(1) NOT NULL, Processed CHAR(1) CHECK (Processed IN ('Y','N')), Processing CHAR(1), Reversal_ID NUMERIC(10), SAP_GLJournal_ID NUMERIC(10) NOT NULL, TotalCr NUMERIC DEFAULT 0 NOT NULL, TotalDr NUMERIC DEFAULT 0 NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CAcctSchema_SAPGLJournal FOREIGN KEY (C_AcctSchema_ID) REFERENCES public.C_AcctSchema DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CConversionType_SAPGLJournal FOREIGN KEY (C_ConversionType_ID) REFERENCES public.C_ConversionType DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CCurrency_SAPGLJournal FOREIGN KEY (C_Currency_ID) REFERENCES public.C_Currency DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CDocType_SAPGLJournal FOREIGN KEY (C_DocType_ID) REFERENCES public.C_DocType DEFERRABLE INITIALLY DEFERRED, CONSTRAINT GLCategory_SAPGLJournal FOREIGN KEY (GL_Category_ID) REFERENCES public.GL_Category DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MSectionCode_SAPGLJournal FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED, CONSTRAINT Reversal_SAPGLJournal FOREIGN KEY (Reversal_ID) REFERENCES public.GL_Journal DEFERRABLE INITIALLY DEFERRED, CONSTRAINT SAP_GLJournal_Key PRIMARY KEY (SAP_GLJournal_ID))
;

