

-- Table: C_Invoice_Adv_Search
-- 2024-09-20T13:22:41.229Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542435,'N',TO_TIMESTAMP('2024-09-20 16:22:41.068','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','Y','N','N','Y','N','N','Y','N','N',0,'Invoice Advanced Search','NP','L','C_Invoice_Adv_Search','DTI',TO_TIMESTAMP('2024-09-20 16:22:41.068','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:22:41.230Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542435 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-09-20T13:22:41.340Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556366,TO_TIMESTAMP('2024-09-20 16:22:41.238','YYYY-MM-DD HH24:MI:SS.US'),100,1000000,50000,'Table C_Invoice_Adv_Search',1,'Y','N','Y','Y','C_Invoice_Adv_Search','N',1000000,TO_TIMESTAMP('2024-09-20 16:22:41.238','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-20T13:22:41.355Z
CREATE SEQUENCE C_INVOICE_ADV_SEARCH_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: C_Invoice_Adv_Search.AD_Client_ID
-- 2024-09-20T13:23:01.389Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589032,102,0,30,542435,'AD_Client_ID',TO_TIMESTAMP('2024-09-20 16:23:01.269','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Mandant',0,0,TO_TIMESTAMP('2024-09-20 16:23:01.269','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:23:01.390Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589032 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:23:01.393Z
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- Column: C_Invoice_Adv_Search.Address1
-- 2024-09-20T13:23:02.149Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589033,156,0,10,542435,'Address1',TO_TIMESTAMP('2024-09-20 16:23:01.862','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Adresszeile 1 für diesen Standort','D',0,100,'"Adresszeile 1" gibt die Anschrift für diesen Standort an.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Straße und Nr.',0,0,TO_TIMESTAMP('2024-09-20 16:23:01.862','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:23:02.151Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589033 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:23:02.153Z
/* DDL */  select update_Column_Translation_From_AD_Element(156)
;

-- Column: C_Invoice_Adv_Search.AD_Org_ID
-- 2024-09-20T13:23:02.834Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589034,113,0,30,542435,'AD_Org_ID',TO_TIMESTAMP('2024-09-20 16:23:02.505','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Organisation',0,0,TO_TIMESTAMP('2024-09-20 16:23:02.505','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:23:02.836Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589034 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:23:02.838Z
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- Column: C_Invoice_Adv_Search.BPName
-- 2024-09-20T13:23:03.494Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589035,2510,0,10,542435,'BPName',TO_TIMESTAMP('2024-09-20 16:23:03.243','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Name des Sponsors.','D',0,100,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Name',0,0,TO_TIMESTAMP('2024-09-20 16:23:03.243','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:23:03.495Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589035 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:23:03.497Z
/* DDL */  select update_Column_Translation_From_AD_Element(2510)
;

-- Column: C_Invoice_Adv_Search.CalendarName
-- 2024-09-20T13:23:04.224Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589036,582789,0,10,542435,'CalendarName',TO_TIMESTAMP('2024-09-20 16:23:03.951','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,60,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Kalendername',0,0,TO_TIMESTAMP('2024-09-20 16:23:03.951','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:23:04.225Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589036 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:23:04.227Z
/* DDL */  select update_Column_Translation_From_AD_Element(582789)
;

-- Column: C_Invoice_Adv_Search.C_BPartner_ID
-- 2024-09-20T13:23:04.878Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589037,187,0,30,542435,'C_BPartner_ID',TO_TIMESTAMP('2024-09-20 16:23:04.611','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Bezeichnet einen Geschäftspartner','D',0,10,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Geschäftspartner',0,0,TO_TIMESTAMP('2024-09-20 16:23:04.611','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:23:04.879Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589037 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:23:04.881Z
/* DDL */  select update_Column_Translation_From_AD_Element(187)
;

-- Column: C_Invoice_Adv_Search.City
-- 2024-09-20T13:23:05.560Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589038,225,0,10,542435,'City',TO_TIMESTAMP('2024-09-20 16:23:05.29','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Name des Ortes','D',0,60,'Bezeichnet einen einzelnen Ort in diesem Land oder dieser Region.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Ort',0,0,TO_TIMESTAMP('2024-09-20 16:23:05.29','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:23:05.561Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589038 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:23:05.563Z
/* DDL */  select update_Column_Translation_From_AD_Element(225)
;

-- Column: C_Invoice_Adv_Search.Companyname
-- 2024-09-20T13:23:06.271Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589039,540648,0,10,542435,'Companyname',TO_TIMESTAMP('2024-09-20 16:23:06.001','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,100,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Firmenname',0,0,TO_TIMESTAMP('2024-09-20 16:23:06.001','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:23:06.272Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589039 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:23:06.274Z
/* DDL */  select update_Column_Translation_From_AD_Element(540648)
;

-- Column: C_Invoice_Adv_Search.Created
-- 2024-09-20T13:23:07.016Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589040,245,0,16,542435,'Created',TO_TIMESTAMP('2024-09-20 16:23:06.729','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,35,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-09-20 16:23:06.729','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:23:07.017Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589040 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:23:07.020Z
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- Column: C_Invoice_Adv_Search.CreatedBy
-- 2024-09-20T13:23:07.768Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589041,246,0,18,110,542435,'CreatedBy',TO_TIMESTAMP('2024-09-20 16:23:07.448','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-09-20 16:23:07.448','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:23:07.769Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589041 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:23:07.771Z
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- Column: C_Invoice_Adv_Search.Description
-- 2024-09-20T13:23:08.566Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589042,275,0,14,542435,'Description',TO_TIMESTAMP('2024-09-20 16:23:08.214','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,1024,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Beschreibung',0,0,TO_TIMESTAMP('2024-09-20 16:23:08.214','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:23:08.568Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589042 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:23:08.571Z
/* DDL */  select update_Column_Translation_From_AD_Element(275)
;

-- Column: C_Invoice_Adv_Search.DescriptionBottom
-- 2024-09-20T13:23:09.328Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589043,501680,0,14,542435,'DescriptionBottom',TO_TIMESTAMP('2024-09-20 16:23:09.048','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,2048,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Schlusstext',0,0,TO_TIMESTAMP('2024-09-20 16:23:09.048','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:23:09.330Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589043 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:23:09.333Z
/* DDL */  select update_Column_Translation_From_AD_Element(501680)
;

-- Column: C_Invoice_Adv_Search.DocTypeName
-- 2024-09-20T13:23:10.157Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589044,2098,0,10,542435,'DocTypeName',TO_TIMESTAMP('2024-09-20 16:23:09.836','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Name der Belegart','D',0,60,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Belegart-Bezeichnung',0,0,TO_TIMESTAMP('2024-09-20 16:23:09.836','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:23:10.159Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589044 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:23:10.162Z
/* DDL */  select update_Column_Translation_From_AD_Element(2098)
;

-- Column: C_Invoice_Adv_Search.DocumentNo
-- 2024-09-20T13:23:10.940Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589045,290,0,10,542435,'DocumentNo',TO_TIMESTAMP('2024-09-20 16:23:10.635','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Document sequence number of the document','D',0,30,'The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Nr.',0,0,TO_TIMESTAMP('2024-09-20 16:23:10.635','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:23:10.941Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589045 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:23:10.944Z
/* DDL */  select update_Column_Translation_From_AD_Element(290)
;

-- Column: C_Invoice_Adv_Search.ES_DocumentId
-- 2024-09-20T13:23:11.702Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589046,579541,0,14,542435,'ES_DocumentId',TO_TIMESTAMP('2024-09-20 16:23:11.386','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,2147483647,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Elasticsearch Document ID',0,0,TO_TIMESTAMP('2024-09-20 16:23:11.386','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:23:11.703Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589046 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:23:11.705Z
/* DDL */  select update_Column_Translation_From_AD_Element(579541)
;

-- Column: C_Invoice_Adv_Search.ExternalId
-- 2024-09-20T13:23:12.540Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589047,543939,0,10,542435,'ExternalId',TO_TIMESTAMP('2024-09-20 16:23:12.152','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Externe ID',0,0,TO_TIMESTAMP('2024-09-20 16:23:12.152','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:23:12.542Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589047 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:23:12.545Z
/* DDL */  select update_Column_Translation_From_AD_Element(543939)
;

-- Column: C_Invoice_Adv_Search.Firstname
-- 2024-09-20T13:23:13.284Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589048,540398,0,10,542435,'Firstname',TO_TIMESTAMP('2024-09-20 16:23:12.985','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Vorname','D',0,255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Vorname',0,0,TO_TIMESTAMP('2024-09-20 16:23:12.985','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:23:13.285Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589048 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:23:13.287Z
/* DDL */  select update_Column_Translation_From_AD_Element(540398)
;

-- Column: C_Invoice_Adv_Search.FiscalYear
-- 2024-09-20T13:23:13.990Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589049,3082,0,10,542435,'FiscalYear',TO_TIMESTAMP('2024-09-20 16:23:13.693','YYYY-MM-DD HH24:MI:SS.US'),100,'N','The Fiscal Year','D',0,20,'The Year identifies the accounting year for a calendar.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Jahr',0,0,TO_TIMESTAMP('2024-09-20 16:23:13.693','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:23:13.991Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589049 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:23:13.993Z
/* DDL */  select update_Column_Translation_From_AD_Element(3082)
;

-- Column: C_Invoice_Adv_Search.IsActive
-- 2024-09-20T13:23:14.770Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589050,348,0,20,542435,'IsActive',TO_TIMESTAMP('2024-09-20 16:23:14.414','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Aktiv',0,0,TO_TIMESTAMP('2024-09-20 16:23:14.414','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:23:14.772Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589050 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:23:14.775Z
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- Column: C_Invoice_Adv_Search.IsCompany
-- 2024-09-20T13:23:15.594Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589051,540400,0,20,542435,'IsCompany',TO_TIMESTAMP('2024-09-20 16:23:15.303','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,1,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Firma',0,0,TO_TIMESTAMP('2024-09-20 16:23:15.303','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:23:15.596Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589051 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:23:15.598Z
/* DDL */  select update_Column_Translation_From_AD_Element(540400)
;

-- Column: C_Invoice_Adv_Search.Lastname
-- 2024-09-20T13:23:16.326Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589052,540399,0,10,542435,'Lastname',TO_TIMESTAMP('2024-09-20 16:23:16.018','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,255,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Nachname',0,0,TO_TIMESTAMP('2024-09-20 16:23:16.018','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:23:16.327Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589052 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:23:16.329Z
/* DDL */  select update_Column_Translation_From_AD_Element(540399)
;

-- Column: C_Invoice_Adv_Search.POReference
-- 2024-09-20T13:23:17.256Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589053,952,0,10,542435,'POReference',TO_TIMESTAMP('2024-09-20 16:23:16.912','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Referenz-Nummer des Kunden','D',0,40,'The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Referenz',0,0,TO_TIMESTAMP('2024-09-20 16:23:16.912','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:23:17.257Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589053 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:23:17.259Z
/* DDL */  select update_Column_Translation_From_AD_Element(952)
;

-- Column: C_Invoice_Adv_Search.Postal
-- 2024-09-20T13:23:18.123Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589054,512,0,10,542435,'Postal',TO_TIMESTAMP('2024-09-20 16:23:17.777','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Postleitzahl','D',0,10,'"PLZ" bezeichnet die Postleitzahl für diese Adresse oder dieses Postfach.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','PLZ',0,0,TO_TIMESTAMP('2024-09-20 16:23:17.777','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:23:18.125Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589054 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:23:18.127Z
/* DDL */  select update_Column_Translation_From_AD_Element(512)
;

-- Column: C_Invoice_Adv_Search.Updated
-- 2024-09-20T13:23:18.939Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589055,607,0,16,542435,'Updated',TO_TIMESTAMP('2024-09-20 16:23:18.564','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,35,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-09-20 16:23:18.564','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:23:18.940Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589055 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:23:18.942Z
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- Column: C_Invoice_Adv_Search.UpdatedBy
-- 2024-09-20T13:23:19.786Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589056,608,0,18,110,542435,'UpdatedBy',TO_TIMESTAMP('2024-09-20 16:23:19.441','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-09-20 16:23:19.441','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:23:19.788Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589056 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:23:19.791Z
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- Column: C_Invoice_Adv_Search.Value
-- 2024-09-20T13:23:20.577Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589057,620,0,10,542435,'Value',TO_TIMESTAMP('2024-09-20 16:23:20.24','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein','D',0,40,'A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Suchschlüssel',0,0,TO_TIMESTAMP('2024-09-20 16:23:20.24','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:23:20.579Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589057 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:23:20.581Z
/* DDL */  select update_Column_Translation_From_AD_Element(620)
;

-- Column: C_Invoice_Adv_Search.WarehouseName
-- 2024-09-20T13:23:21.345Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589058,2280,0,10,542435,'WarehouseName',TO_TIMESTAMP('2024-09-20 16:23:21.037','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Lagerbezeichnung','D',0,60,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Lager',0,0,TO_TIMESTAMP('2024-09-20 16:23:21.037','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:23:21.347Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589058 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:23:21.349Z
/* DDL */  select update_Column_Translation_From_AD_Element(2280)
;

-- 2024-09-20T13:23:21.865Z
/* DDL */ CREATE TABLE public.C_Invoice_Adv_Search (AD_Client_ID NUMERIC(10), Address1 VARCHAR(100), AD_Org_ID NUMERIC(10), BPName VARCHAR(100), CalendarName VARCHAR(60), C_BPartner_ID NUMERIC(10), City VARCHAR(60), Companyname VARCHAR(100), Created TIMESTAMP WITH TIME ZONE, CreatedBy NUMERIC(10), Description VARCHAR(1024), DescriptionBottom VARCHAR(2048), DocTypeName VARCHAR(60), DocumentNo VARCHAR(30), ES_DocumentId TEXT, ExternalId VARCHAR(255), Firstname VARCHAR(255), FiscalYear VARCHAR(20), IsActive CHAR(1) CHECK (IsActive IN ('Y','N')), IsCompany CHAR(1) CHECK (IsCompany IN ('Y','N')), Lastname VARCHAR(255), POReference VARCHAR(40), Postal VARCHAR(10), Updated TIMESTAMP WITH TIME ZONE, UpdatedBy NUMERIC(10), Value VARCHAR(40), WarehouseName VARCHAR(60), CONSTRAINT CBPartner_CInvoiceAdvSearch FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED)
;

-- 2024-09-20T13:23:21.902Z
INSERT INTO t_alter_column values('c_invoice_adv_search','Address1','VARCHAR(100)',null,null)
;

-- 2024-09-20T13:23:21.917Z
INSERT INTO t_alter_column values('c_invoice_adv_search','AD_Org_ID','NUMERIC(10)',null,null)
;

-- 2024-09-20T13:23:21.930Z
INSERT INTO t_alter_column values('c_invoice_adv_search','BPName','VARCHAR(100)',null,null)
;

-- 2024-09-20T13:23:21.944Z
INSERT INTO t_alter_column values('c_invoice_adv_search','CalendarName','VARCHAR(60)',null,null)
;

-- 2024-09-20T13:23:21.958Z
INSERT INTO t_alter_column values('c_invoice_adv_search','C_BPartner_ID','NUMERIC(10)',null,null)
;

-- 2024-09-20T13:23:21.971Z
INSERT INTO t_alter_column values('c_invoice_adv_search','City','VARCHAR(60)',null,null)
;

-- 2024-09-20T13:23:21.986Z
INSERT INTO t_alter_column values('c_invoice_adv_search','Companyname','VARCHAR(100)',null,null)
;

-- 2024-09-20T13:23:22.001Z
INSERT INTO t_alter_column values('c_invoice_adv_search','Created','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2024-09-20T13:23:22.017Z
INSERT INTO t_alter_column values('c_invoice_adv_search','CreatedBy','NUMERIC(10)',null,null)
;

-- 2024-09-20T13:23:22.033Z
INSERT INTO t_alter_column values('c_invoice_adv_search','Description','VARCHAR(1024)',null,null)
;

-- 2024-09-20T13:23:22.047Z
INSERT INTO t_alter_column values('c_invoice_adv_search','DescriptionBottom','VARCHAR(2048)',null,null)
;

-- 2024-09-20T13:23:22.062Z
INSERT INTO t_alter_column values('c_invoice_adv_search','DocTypeName','VARCHAR(60)',null,null)
;

-- 2024-09-20T13:23:22.080Z
INSERT INTO t_alter_column values('c_invoice_adv_search','DocumentNo','VARCHAR(30)',null,null)
;

-- 2024-09-20T13:23:22.098Z
INSERT INTO t_alter_column values('c_invoice_adv_search','ES_DocumentId','TEXT',null,null)
;

-- 2024-09-20T13:23:22.115Z
INSERT INTO t_alter_column values('c_invoice_adv_search','ExternalId','VARCHAR(255)',null,null)
;

-- 2024-09-20T13:23:22.135Z
INSERT INTO t_alter_column values('c_invoice_adv_search','Firstname','VARCHAR(255)',null,null)
;

-- 2024-09-20T13:23:22.160Z
INSERT INTO t_alter_column values('c_invoice_adv_search','FiscalYear','VARCHAR(20)',null,null)
;

-- 2024-09-20T13:23:22.182Z
INSERT INTO t_alter_column values('c_invoice_adv_search','IsActive','CHAR(1)',null,null)
;

-- 2024-09-20T13:23:22.214Z
INSERT INTO t_alter_column values('c_invoice_adv_search','IsCompany','CHAR(1)',null,null)
;

-- 2024-09-20T13:23:22.241Z
INSERT INTO t_alter_column values('c_invoice_adv_search','Lastname','VARCHAR(255)',null,null)
;

-- 2024-09-20T13:23:22.258Z
INSERT INTO t_alter_column values('c_invoice_adv_search','POReference','VARCHAR(40)',null,null)
;

-- 2024-09-20T13:23:22.275Z
INSERT INTO t_alter_column values('c_invoice_adv_search','Postal','VARCHAR(10)',null,null)
;

-- 2024-09-20T13:23:22.293Z
INSERT INTO t_alter_column values('c_invoice_adv_search','Updated','TIMESTAMP WITH TIME ZONE',null,null)
;

-- 2024-09-20T13:23:22.310Z
INSERT INTO t_alter_column values('c_invoice_adv_search','UpdatedBy','NUMERIC(10)',null,null)
;

-- 2024-09-20T13:23:22.329Z
INSERT INTO t_alter_column values('c_invoice_adv_search','Value','VARCHAR(40)',null,null)
;

-- 2024-09-20T13:23:22.350Z
INSERT INTO t_alter_column values('c_invoice_adv_search','WarehouseName','VARCHAR(60)',null,null)
;



-- Column: C_Invoice_Adv_Search.AD_User_ID
-- 2024-09-20T13:36:33.298Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589100,138,0,30,542435,'AD_User_ID',TO_TIMESTAMP('2024-09-20 16:36:33.155','YYYY-MM-DD HH24:MI:SS.US'),100,'N','User within the system - Internal or Business Partner Contact','D',0,10,'The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Ansprechpartner',0,0,TO_TIMESTAMP('2024-09-20 16:36:33.155','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:36:33.300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589100 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:36:33.307Z
/* DDL */  select update_Column_Translation_From_AD_Element(138)
;

-- Column: C_Invoice_Adv_Search.C_BPartner_Location_ID
-- 2024-09-20T13:36:33.960Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589101,189,0,30,542435,'C_BPartner_Location_ID',TO_TIMESTAMP('2024-09-20 16:36:33.864','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Identifiziert die (Liefer-) Adresse des Geschäftspartners','D',0,10,'Identifiziert die Adresse des Geschäftspartners','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Standort',0,0,TO_TIMESTAMP('2024-09-20 16:36:33.864','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:36:33.961Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589101 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:36:33.963Z
/* DDL */  select update_Column_Translation_From_AD_Element(189)
;

-- Column: C_Invoice_Adv_Search.C_Calendar_ID
-- 2024-09-20T13:36:34.709Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589102,190,0,30,542435,'C_Calendar_ID',TO_TIMESTAMP('2024-09-20 16:36:34.416','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Bezeichnung des Buchführungs-Kalenders','D',0,10,'"Kalender" bezeichnet einen eindeutigen Kalender für die Buchhaltung. Es können mehrere Kalender verwendet werden. Z.B. können Sie einen Standardkalender definieren, der vom 1. Jan. bis zum 31. Dez. läuft und einen  fiskalischen, der vom 1. Jul. bis zum 30. Jun. läuft.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Kalender',0,0,TO_TIMESTAMP('2024-09-20 16:36:34.416','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:36:34.710Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589102 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:36:34.712Z
/* DDL */  select update_Column_Translation_From_AD_Element(190)
;

-- Column: C_Invoice_Adv_Search.C_DocType_ID
-- 2024-09-20T13:36:35.438Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589103,196,0,30,542435,'C_DocType_ID',TO_TIMESTAMP('2024-09-20 16:36:35.082','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Belegart oder Verarbeitungsvorgaben','D',0,10,'Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Belegart',0,0,TO_TIMESTAMP('2024-09-20 16:36:35.082','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:36:35.440Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589103 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:36:35.442Z
/* DDL */  select update_Column_Translation_From_AD_Element(196)
;

-- Column: C_Invoice_Adv_Search.C_Invoice_ID
-- 2024-09-20T13:36:36.096Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589104,1008,0,30,542435,'C_Invoice_ID',TO_TIMESTAMP('2024-09-20 16:36:35.817','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Invoice Identifier','D',0,10,'The Invoice Document.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Rechnung',0,0,TO_TIMESTAMP('2024-09-20 16:36:35.817','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:36:36.097Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589104 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:36:36.099Z
/* DDL */  select update_Column_Translation_From_AD_Element(1008)
;

-- Column: C_Invoice_Adv_Search.C_Year_ID
-- 2024-09-20T13:36:36.845Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589105,223,0,30,542435,'C_Year_ID',TO_TIMESTAMP('2024-09-20 16:36:36.749','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Kalenderjahr','D',0,10,'"Jahr" bezeichnet ein eindeutiges Jahr eines Kalenders.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Jahr',0,0,TO_TIMESTAMP('2024-09-20 16:36:36.749','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:36:36.846Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589105 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:36:36.848Z
/* DDL */  select update_Column_Translation_From_AD_Element(223)
;

-- Column: C_Invoice_Adv_Search.M_Warehouse_ID
-- 2024-09-20T13:36:37.617Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589106,459,0,30,542435,'M_Warehouse_ID',TO_TIMESTAMP('2024-09-20 16:36:37.495','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Lager oder Ort für Dienstleistung','D',0,10,'Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Lager',0,0,TO_TIMESTAMP('2024-09-20 16:36:37.495','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T13:36:37.618Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589106 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T13:36:37.620Z
/* DDL */  select update_Column_Translation_From_AD_Element(459)
;

-- 2024-09-20T13:36:38.236Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Adv_Search','ALTER TABLE public.C_Invoice_Adv_Search ADD COLUMN AD_User_ID NUMERIC(10)')
;

-- -- 2024-09-20T13:36:38.247Z
-- ALTER TABLE C_Invoice_Adv_Search ADD CONSTRAINT ADUser_CInvoiceAdvSearch FOREIGN KEY (AD_User_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
-- ;

-- 2024-09-20T13:36:38.268Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Adv_Search','ALTER TABLE public.C_Invoice_Adv_Search ADD COLUMN C_BPartner_Location_ID NUMERIC(10)')
;

-- -- 2024-09-20T13:36:38.314Z
-- ALTER TABLE C_Invoice_Adv_Search ADD CONSTRAINT CBPartnerLocation_CInvoiceAdvSearch FOREIGN KEY (C_BPartner_Location_ID) REFERENCES public.C_BPartner_Location DEFERRABLE INITIALLY DEFERRED
-- ;

-- 2024-09-20T13:36:38.335Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Adv_Search','ALTER TABLE public.C_Invoice_Adv_Search ADD COLUMN C_Calendar_ID NUMERIC(10)')
;

-- -- 2024-09-20T13:36:38.344Z
-- ALTER TABLE C_Invoice_Adv_Search ADD CONSTRAINT CCalendar_CInvoiceAdvSearch FOREIGN KEY (C_Calendar_ID) REFERENCES public.C_Calendar DEFERRABLE INITIALLY DEFERRED
-- ;

-- 2024-09-20T13:36:38.363Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Adv_Search','ALTER TABLE public.C_Invoice_Adv_Search ADD COLUMN C_DocType_ID NUMERIC(10)')
;

-- -- 2024-09-20T13:36:38.370Z
-- ALTER TABLE C_Invoice_Adv_Search ADD CONSTRAINT CDocType_CInvoiceAdvSearch FOREIGN KEY (C_DocType_ID) REFERENCES public.C_DocType DEFERRABLE INITIALLY DEFERRED
-- ;

-- 2024-09-20T13:36:38.391Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Adv_Search','ALTER TABLE public.C_Invoice_Adv_Search ADD COLUMN C_Invoice_ID NUMERIC(10)')
;

-- -- 2024-09-20T13:36:38.399Z
-- ALTER TABLE C_Invoice_Adv_Search ADD CONSTRAINT CInvoice_CInvoiceAdvSearch FOREIGN KEY (C_Invoice_ID) REFERENCES public.C_Invoice DEFERRABLE INITIALLY DEFERRED
-- ;

-- 2024-09-20T13:36:38.419Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Adv_Search','ALTER TABLE public.C_Invoice_Adv_Search ADD COLUMN C_Year_ID NUMERIC(10)')
;

-- -- 2024-09-20T13:36:38.428Z
-- ALTER TABLE C_Invoice_Adv_Search ADD CONSTRAINT CYear_CInvoiceAdvSearch FOREIGN KEY (C_Year_ID) REFERENCES public.C_Year DEFERRABLE INITIALLY DEFERRED
-- ;

-- 2024-09-20T13:36:38.447Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Adv_Search','ALTER TABLE public.C_Invoice_Adv_Search ADD COLUMN M_Warehouse_ID NUMERIC(10)')
;

-- -- 2024-09-20T13:36:38.455Z
-- ALTER TABLE C_Invoice_Adv_Search ADD CONSTRAINT MWarehouse_CInvoiceAdvSearch FOREIGN KEY (M_Warehouse_ID) REFERENCES public.M_Warehouse DEFERRABLE INITIALLY DEFERRED
-- ;



-- Column: C_Invoice_Adv_Search.BPartnerValue
-- 2024-09-20T14:25:02.095Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589120,2094,0,10,542435,'BPartnerValue',TO_TIMESTAMP('2024-09-20 17:25:01.933','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Key of the Business Partner','D',0,40,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Geschäftspartner-Schlüssel',0,0,TO_TIMESTAMP('2024-09-20 17:25:01.933','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-20T14:25:02.096Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589120 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T14:25:02.098Z
/* DDL */  select update_Column_Translation_From_AD_Element(2094)
;

-- 2024-09-20T14:25:02.724Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Adv_Search','ALTER TABLE public.C_Invoice_Adv_Search ADD COLUMN BPartnerValue VARCHAR(40)')
;

-- Column: C_Invoice_Adv_Search.Value
-- 2024-09-20T14:25:17.753Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=589057
;

-- 2024-09-20T14:25:17.757Z
DELETE FROM AD_Column WHERE AD_Column_ID=589057
;

SELECT public.db_alter_table('C_Invoice_Adv_Search','ALTER TABLE public.C_Invoice_Adv_Search DROP COLUMN Value')
;









drop table c_invoice_adv_search;





-- Run mode: SWING_CLIENT

-- Column: C_Invoice_Adv_Search.C_Year_ID
-- 2024-09-23T10:32:32.701Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:32:32.701','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589105
;

-- 2024-09-23T10:32:36.010Z
INSERT INTO t_alter_column values('c_invoice_adv_search','C_Year_ID','NUMERIC(10)',null,null)
;

-- Column: C_Invoice_Adv_Search.AD_Client_ID
-- 2024-09-23T10:36:12.811Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:12.811','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589032
;

-- Column: C_Invoice_Adv_Search.Address1
-- 2024-09-23T10:36:14.580Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:14.58','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589033
;

-- Column: C_Invoice_Adv_Search.AD_Org_ID
-- 2024-09-23T10:36:15.085Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:15.085','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589034
;

-- Column: C_Invoice_Adv_Search.AD_User_ID
-- 2024-09-23T10:36:15.918Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:15.918','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589100
;

-- Column: C_Invoice_Adv_Search.BPartnerValue
-- 2024-09-23T10:36:16.529Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:16.529','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589120
;

-- Column: C_Invoice_Adv_Search.BPName
-- 2024-09-23T10:36:17.050Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:17.05','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589035
;

-- Column: C_Invoice_Adv_Search.CalendarName
-- 2024-09-23T10:36:17.591Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:17.591','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589036
;

-- Column: C_Invoice_Adv_Search.C_BPartner_ID
-- 2024-09-23T10:36:18.164Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:18.163','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589037
;

-- Column: C_Invoice_Adv_Search.C_BPartner_Location_ID
-- 2024-09-23T10:36:18.744Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:18.744','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589101
;

-- Column: C_Invoice_Adv_Search.C_Calendar_ID
-- 2024-09-23T10:36:19.400Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:19.4','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589102
;

-- Column: C_Invoice_Adv_Search.C_DocType_ID
-- 2024-09-23T10:36:20.065Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:20.065','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589103
;

-- Column: C_Invoice_Adv_Search.C_Invoice_ID
-- 2024-09-23T10:36:20.747Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:20.747','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589104
;

-- Column: C_Invoice_Adv_Search.City
-- 2024-09-23T10:36:21.296Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:21.296','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589038
;

-- Column: C_Invoice_Adv_Search.Companyname
-- 2024-09-23T10:36:21.964Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:21.964','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589039
;

-- Column: C_Invoice_Adv_Search.Created
-- 2024-09-23T10:36:22.634Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:22.634','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589040
;

-- Column: C_Invoice_Adv_Search.CreatedBy
-- 2024-09-23T10:36:23.684Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:23.684','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589041
;

-- Column: C_Invoice_Adv_Search.Description
-- 2024-09-23T10:36:24.387Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:24.387','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589042
;

-- Column: C_Invoice_Adv_Search.DescriptionBottom
-- 2024-09-23T10:36:24.957Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:24.957','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589043
;

-- Column: C_Invoice_Adv_Search.DocTypeName
-- 2024-09-23T10:36:26.428Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:26.428','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589044
;

-- Column: C_Invoice_Adv_Search.WarehouseName
-- 2024-09-23T10:36:27.011Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:27.011','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589058
;

-- Column: C_Invoice_Adv_Search.UpdatedBy
-- 2024-09-23T10:36:27.621Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:27.621','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589056
;

-- Column: C_Invoice_Adv_Search.Updated
-- 2024-09-23T10:36:28.207Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:28.206','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589055
;

-- Column: C_Invoice_Adv_Search.Postal
-- 2024-09-23T10:36:28.836Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:28.836','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589054
;

-- Column: C_Invoice_Adv_Search.POReference
-- 2024-09-23T10:36:29.463Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:29.463','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589053
;

-- Column: C_Invoice_Adv_Search.M_Warehouse_ID
-- 2024-09-23T10:36:30.223Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:30.223','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589106
;

-- Column: C_Invoice_Adv_Search.Lastname
-- 2024-09-23T10:36:31.006Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:31.006','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589052
;

-- Column: C_Invoice_Adv_Search.IsCompany
-- 2024-09-23T10:36:31.648Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:31.647','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589051
;

-- Column: C_Invoice_Adv_Search.IsActive
-- 2024-09-23T10:36:32.503Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:32.503','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589050
;

-- Column: C_Invoice_Adv_Search.FiscalYear
-- 2024-09-23T10:36:33.150Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:33.15','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589049
;

-- Column: C_Invoice_Adv_Search.Firstname
-- 2024-09-23T10:36:33.756Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:33.756','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589048
;

-- Column: C_Invoice_Adv_Search.ExternalId
-- 2024-09-23T10:36:34.659Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:34.659','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589047
;

-- Column: C_Invoice_Adv_Search.ES_DocumentId
-- 2024-09-23T10:36:35.184Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:35.184','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589046
;

-- Column: C_Invoice_Adv_Search.DocumentNo
-- 2024-09-23T10:36:38.193Z
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2024-09-23 13:36:38.193','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589045
;

-- 2024-09-23T10:36:43.913Z
/* DDL */ CREATE TABLE public.C_Invoice_Adv_Search (AD_Client_ID NUMERIC(10), Address1 VARCHAR(100), AD_Org_ID NUMERIC(10), AD_User_ID NUMERIC(10), BPartnerValue VARCHAR(40), BPName VARCHAR(100), CalendarName VARCHAR(60), C_BPartner_ID NUMERIC(10), C_BPartner_Location_ID NUMERIC(10), C_Calendar_ID NUMERIC(10), C_DocType_ID NUMERIC(10), C_Invoice_ID NUMERIC(10), City VARCHAR(60), Companyname VARCHAR(100), Created TIMESTAMP WITH TIME ZONE, CreatedBy NUMERIC(10), C_Year_ID NUMERIC(10), Description VARCHAR(1024), DescriptionBottom VARCHAR(2048), DocTypeName VARCHAR(60), DocumentNo VARCHAR(30), ES_DocumentId TEXT, ExternalId VARCHAR(255), Firstname VARCHAR(255), FiscalYear VARCHAR(20), IsActive CHAR(1) CHECK (IsActive IN ('Y','N')), IsCompany CHAR(1) CHECK (IsCompany IN ('Y','N')), Lastname VARCHAR(255), M_Warehouse_ID NUMERIC(10), POReference VARCHAR(40), Postal VARCHAR(10), Updated TIMESTAMP WITH TIME ZONE, UpdatedBy NUMERIC(10), WarehouseName VARCHAR(60))
;



create index c_invoice_adv_search_c_bpartner_id_index
    on public.c_invoice_adv_search (c_bpartner_id);

create index c_invoice_adv_search_ad_user_id__index
    on public.c_invoice_adv_search (ad_user_id );

create index c_invoice_adv_search_c_bpartner_location_id_index
    on public.c_invoice_adv_search (c_bpartner_location_id);

create index c_invoice_adv_search_c_calendar_id_index
    on public.c_invoice_adv_search (c_calendar_id);

create index c_invoice_adv_search_c_doctype_id_index
    on public.c_invoice_adv_search (c_doctype_id);

create index c_invoice_adv_search_c_year_id_index
    on public.c_invoice_adv_search (c_year_id);

create index c_invoice_adv_search_m_warehouse_id_index
    on public.c_invoice_adv_search (m_warehouse_id);

