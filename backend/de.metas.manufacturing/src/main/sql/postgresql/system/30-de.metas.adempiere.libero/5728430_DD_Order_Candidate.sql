-- Table: DD_Order_Candidate
-- Table: DD_Order_Candidate
-- 2024-07-11T07:05:45.184Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('3',0,0,0,542424,'A','N',TO_TIMESTAMP('2024-07-11 10:05:44','YYYY-MM-DD HH24:MI:SS'),100,'A','EE01','N','Y','N','N','Y','N','N','Y','N','N',0,'Distribution Order Candidate','NP','L','DD_Order_Candidate','DTI',TO_TIMESTAMP('2024-07-11 10:05:44','YYYY-MM-DD HH24:MI:SS'),100,0,'A')
;

-- 2024-07-11T07:05:45.187Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542424 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-07-11T07:05:45.314Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556354,TO_TIMESTAMP('2024-07-11 10:05:45','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table DD_Order_Candidate',1,'Y','N','Y','Y','DD_Order_Candidate','N',1000000,TO_TIMESTAMP('2024-07-11 10:05:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T07:05:45.330Z
CREATE SEQUENCE DD_ORDER_CANDIDATE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: DD_Order_Candidate.AD_Client_ID
-- Column: DD_Order_Candidate.AD_Client_ID
-- 2024-07-11T07:05:59.638Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588716,102,0,19,542424,'AD_Client_ID',TO_TIMESTAMP('2024-07-11 10:05:59','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','EE01',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-07-11 10:05:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:05:59.642Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588716 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:05:59.675Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: DD_Order_Candidate.AD_Org_ID
-- Column: DD_Order_Candidate.AD_Org_ID
-- 2024-07-11T07:06:00.475Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588717,113,0,30,542424,'AD_Org_ID',TO_TIMESTAMP('2024-07-11 10:06:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','EE01',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2024-07-11 10:06:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:06:00.477Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588717 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:00.479Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: DD_Order_Candidate.Created
-- Column: DD_Order_Candidate.Created
-- 2024-07-11T07:06:01.114Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588718,245,0,16,542424,'Created',TO_TIMESTAMP('2024-07-11 10:06:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','EE01',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-07-11 10:06:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:06:01.116Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588718 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:01.118Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: DD_Order_Candidate.CreatedBy
-- Column: DD_Order_Candidate.CreatedBy
-- 2024-07-11T07:06:01.726Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588719,246,0,18,110,542424,'CreatedBy',TO_TIMESTAMP('2024-07-11 10:06:01','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','EE01',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-07-11 10:06:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:06:01.728Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588719 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:01.731Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: DD_Order_Candidate.IsActive
-- Column: DD_Order_Candidate.IsActive
-- 2024-07-11T07:06:02.325Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588720,348,0,20,542424,'IsActive',TO_TIMESTAMP('2024-07-11 10:06:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','EE01',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-07-11 10:06:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:06:02.328Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588720 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:02.332Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: DD_Order_Candidate.Updated
-- Column: DD_Order_Candidate.Updated
-- 2024-07-11T07:06:02.903Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588721,607,0,16,542424,'Updated',TO_TIMESTAMP('2024-07-11 10:06:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','EE01',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-07-11 10:06:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:06:02.905Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588721 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:02.908Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: DD_Order_Candidate.UpdatedBy
-- Column: DD_Order_Candidate.UpdatedBy
-- 2024-07-11T07:06:03.561Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588722,608,0,18,110,542424,'UpdatedBy',TO_TIMESTAMP('2024-07-11 10:06:03','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','EE01',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-07-11 10:06:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:06:03.563Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588722 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:03.565Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2024-07-11T07:06:04.078Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583179,0,'DD_Order_Candidate_ID',TO_TIMESTAMP('2024-07-11 10:06:03','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Distribution Order Candidate','Distribution Order Candidate',TO_TIMESTAMP('2024-07-11 10:06:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T07:06:04.082Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583179 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: DD_Order_Candidate.DD_Order_Candidate_ID
-- Column: DD_Order_Candidate.DD_Order_Candidate_ID
-- 2024-07-11T07:06:04.627Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588723,583179,0,13,542424,'DD_Order_Candidate_ID',TO_TIMESTAMP('2024-07-11 10:06:03','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Distribution Order Candidate',0,0,TO_TIMESTAMP('2024-07-11 10:06:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:06:04.628Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588723 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:04.631Z
/* DDL */  select update_Column_Translation_From_AD_Element(583179) 
;

-- Column: DD_Order_Candidate.AD_OrgTrx_ID
-- Column: DD_Order_Candidate.AD_OrgTrx_ID
-- 2024-07-11T07:06:17.601Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588724,112,0,18,130,542424,'AD_OrgTrx_ID',TO_TIMESTAMP('2024-07-11 10:06:17','YYYY-MM-DD HH24:MI:SS'),100,'N','Durchführende oder auslösende Organisation','EE01',0,22,'Die Organisation, die diese Transaktion durchführt oder auslöst (für eine andere Organisation). Die besitzende Organisation muss nicht die durchführende Organisation sein. Dies kann bei zentralisierten Dienstleistungen oder Vorfällen zwischen Organisationen der Fall sein.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Buchende Organisation',0,0,TO_TIMESTAMP('2024-07-11 10:06:17','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:17.603Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588724 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:17.606Z
/* DDL */  select update_Column_Translation_From_AD_Element(112) 
;

-- Column: DD_Order_Candidate.AD_User_ID
-- Column: DD_Order_Candidate.AD_User_ID
-- 2024-07-11T07:06:18.196Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588725,138,0,19,542424,123,'AD_User_ID',TO_TIMESTAMP('2024-07-11 10:06:17','YYYY-MM-DD HH24:MI:SS'),100,'N','-1','User within the system - Internal or Business Partner Contact','EE01',0,22,'The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Ansprechpartner',0,0,TO_TIMESTAMP('2024-07-11 10:06:17','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:18.198Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588725 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:18.202Z
/* DDL */  select update_Column_Translation_From_AD_Element(138) 
;

-- Column: DD_Order_Candidate.AD_User_Responsible_ID
-- Column: DD_Order_Candidate.AD_User_Responsible_ID
-- 2024-07-11T07:06:18.766Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588726,542007,0,30,110,542424,'AD_User_Responsible_ID',TO_TIMESTAMP('2024-07-11 10:06:18','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','Verantwortlicher Benutzer',0,0,TO_TIMESTAMP('2024-07-11 10:06:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:06:18.769Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588726 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:18.772Z
/* DDL */  select update_Column_Translation_From_AD_Element(542007) 
;

-- Column: DD_Order_Candidate.C_Activity_ID
-- Column: DD_Order_Candidate.C_Activity_ID
-- 2024-07-11T07:06:19.319Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588727,1005,0,19,542424,'C_Activity_ID',TO_TIMESTAMP('2024-07-11 10:06:19','YYYY-MM-DD HH24:MI:SS'),100,'N','Kostenstelle','EE01',0,22,'Erfassung der zugehörigen Kostenstelle','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Kostenstelle',0,0,TO_TIMESTAMP('2024-07-11 10:06:19','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:19.321Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588727 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:19.324Z
/* DDL */  select update_Column_Translation_From_AD_Element(1005) 
;

-- Column: DD_Order_Candidate.C_BPartner_ID
-- Column: DD_Order_Candidate.C_BPartner_ID
-- 2024-07-11T07:06:19.919Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588728,187,0,30,542424,230,'C_BPartner_ID',TO_TIMESTAMP('2024-07-11 10:06:19','YYYY-MM-DD HH24:MI:SS'),100,'N','Bezeichnet einen Geschäftspartner','EE01',0,22,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Geschäftspartner',0,0,TO_TIMESTAMP('2024-07-11 10:06:19','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:19.921Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588728 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:19.924Z
/* DDL */  select update_Column_Translation_From_AD_Element(187) 
;

-- Column: DD_Order_Candidate.C_BPartner_Location_ID
-- Column: DD_Order_Candidate.C_BPartner_Location_ID
-- 2024-07-11T07:06:20.495Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588729,189,0,19,542424,167,'C_BPartner_Location_ID',TO_TIMESTAMP('2024-07-11 10:06:20','YYYY-MM-DD HH24:MI:SS'),100,'N','Identifiziert die (Liefer-) Adresse des Geschäftspartners','EE01',0,22,'Identifiziert die Adresse des Geschäftspartners','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Standort',0,0,TO_TIMESTAMP('2024-07-11 10:06:20','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:20.496Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588729 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:20.500Z
/* DDL */  select update_Column_Translation_From_AD_Element(189) 
;

-- Column: DD_Order_Candidate.C_Campaign_ID
-- Column: DD_Order_Candidate.C_Campaign_ID
-- 2024-07-11T07:06:21.073Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588730,550,0,19,542424,'C_Campaign_ID',TO_TIMESTAMP('2024-07-11 10:06:20','YYYY-MM-DD HH24:MI:SS'),100,'N','Marketing Campaign','EE01',0,22,'The Campaign defines a unique marketing program.  Projects can be associated with a pre defined Marketing Campaign.  You can then report based on a specific Campaign.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Werbemassnahme',0,0,TO_TIMESTAMP('2024-07-11 10:06:20','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:21.074Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588730 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:21.076Z
/* DDL */  select update_Column_Translation_From_AD_Element(550) 
;

-- Column: DD_Order_Candidate.C_Charge_ID
-- Column: DD_Order_Candidate.C_Charge_ID
-- 2024-07-11T07:06:21.669Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588731,968,0,18,200,542424,'C_Charge_ID',TO_TIMESTAMP('2024-07-11 10:06:21','YYYY-MM-DD HH24:MI:SS'),100,'N','Zusätzliche Kosten','EE01',0,22,'Kosten gibt die Art der zusätzlichen Kosten an (Abwicklung, Fracht, Bankgebühren, ...)','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Kosten',0,0,TO_TIMESTAMP('2024-07-11 10:06:21','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:21.670Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588731 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:21.674Z
/* DDL */  select update_Column_Translation_From_AD_Element(968) 
;

-- Column: DD_Order_Candidate.C_DocType_ID
-- Column: DD_Order_Candidate.C_DocType_ID
-- 2024-07-11T07:06:22.288Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588732,196,0,19,542424,52004,'C_DocType_ID',TO_TIMESTAMP('2024-07-11 10:06:22','YYYY-MM-DD HH24:MI:SS'),100,'N','Belegart oder Verarbeitungsvorgaben','EE01',0,22,'Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Belegart',0,0,TO_TIMESTAMP('2024-07-11 10:06:22','YYYY-MM-DD HH24:MI:SS'),100,1.000000000000)
;

-- 2024-07-11T07:06:22.291Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588732 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:22.294Z
/* DDL */  select update_Column_Translation_From_AD_Element(196) 
;

-- Column: DD_Order_Candidate.ChargeAmt
-- Column: DD_Order_Candidate.ChargeAmt
-- 2024-07-11T07:06:22.889Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588733,849,0,12,542424,'ChargeAmt',TO_TIMESTAMP('2024-07-11 10:06:22','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,22,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Gebühr',0,0,TO_TIMESTAMP('2024-07-11 10:06:22','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:22.890Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588733 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:22.893Z
/* DDL */  select update_Column_Translation_From_AD_Element(849) 
;

-- Column: DD_Order_Candidate.C_Invoice_ID
-- Column: DD_Order_Candidate.C_Invoice_ID
-- 2024-07-11T07:06:23.459Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588734,1008,0,30,542424,'C_Invoice_ID',TO_TIMESTAMP('2024-07-11 10:06:23','YYYY-MM-DD HH24:MI:SS'),100,'N','Invoice Identifier','EE01',0,22,'The Invoice Document.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','Rechnung',0,0,TO_TIMESTAMP('2024-07-11 10:06:23','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:23.462Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588734 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:23.466Z
/* DDL */  select update_Column_Translation_From_AD_Element(1008) 
;

-- Column: DD_Order_Candidate.C_Order_ID
-- Column: DD_Order_Candidate.C_Order_ID
-- 2024-07-11T07:06:24.192Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588735,558,0,30,542424,'C_Order_ID',TO_TIMESTAMP('2024-07-11 10:06:23','YYYY-MM-DD HH24:MI:SS'),100,'N','Auftrag','EE01',0,22,'The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','Auftrag',0,0,TO_TIMESTAMP('2024-07-11 10:06:23','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:24.194Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588735 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:24.197Z
/* DDL */  select update_Column_Translation_From_AD_Element(558) 
;

-- Column: DD_Order_Candidate.C_Project_ID
-- Column: DD_Order_Candidate.C_Project_ID
-- 2024-07-11T07:06:24.772Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588736,208,0,19,542424,227,'C_Project_ID',TO_TIMESTAMP('2024-07-11 10:06:24','YYYY-MM-DD HH24:MI:SS'),100,'N','Financial Project','EE01',0,22,'A Project allows you to track and control internal or external activities.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Projekt',0,0,TO_TIMESTAMP('2024-07-11 10:06:24','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:24.773Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588736 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:24.776Z
/* DDL */  select update_Column_Translation_From_AD_Element(208) 
;

-- Column: DD_Order_Candidate.CreateConfirm
-- Column: DD_Order_Candidate.CreateConfirm
-- 2024-07-11T07:06:25.372Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588737,2520,0,281,28,542424,'CreateConfirm',TO_TIMESTAMP('2024-07-11 10:06:25','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,1,'Y','Y','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Create Confirm',0,0,TO_TIMESTAMP('2024-07-11 10:06:25','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:25.373Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588737 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:25.376Z
/* DDL */  select update_Column_Translation_From_AD_Element(2520) 
;

-- Column: DD_Order_Candidate.CreateFrom
-- Column: DD_Order_Candidate.CreateFrom
-- 2024-07-11T07:06:25.965Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588738,1490,0,28,542424,'CreateFrom',TO_TIMESTAMP('2024-07-11 10:06:25','YYYY-MM-DD HH24:MI:SS'),100,'N','Prozess, der die Position(en) aus einem bestehenden Beleg kopiert','EE01',0,1,'Startet einen Prozess, der die Position(en) zu diesem Belegkopf durch Kopie aus einem bestehenden Beleg anlegt.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Position(en) kopieren von',0,0,TO_TIMESTAMP('2024-07-11 10:06:25','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:25.966Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588738 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:25.968Z
/* DDL */  select update_Column_Translation_From_AD_Element(1490) 
;

-- Column: DD_Order_Candidate.CreatePackage
-- Column: DD_Order_Candidate.CreatePackage
-- 2024-07-11T07:06:26.547Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588739,2525,0,28,542424,'CreatePackage',TO_TIMESTAMP('2024-07-11 10:06:26','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,1,'N','Y','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Packstück erstellen',0,0,TO_TIMESTAMP('2024-07-11 10:06:26','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:26.549Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588739 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:26.551Z
/* DDL */  select update_Column_Translation_From_AD_Element(2525) 
;

-- Column: DD_Order_Candidate.DateOrdered
-- Column: DD_Order_Candidate.DateOrdered
-- 2024-07-11T07:06:27.156Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588740,268,0,15,542424,'DateOrdered',TO_TIMESTAMP('2024-07-11 10:06:26','YYYY-MM-DD HH24:MI:SS'),100,'N','@#Date@','Datum des Auftrags','EE01',0,7,'B','Bezeichnet das Datum, an dem die Ware bestellt wurde.','Y','Y','N','N','Y','N','N','N','N','Y','N','Y','N','N','Y','N','N','Auftragsdatum',20,0,TO_TIMESTAMP('2024-07-11 10:06:26','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:27.158Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588740 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:27.164Z
/* DDL */  select update_Column_Translation_From_AD_Element(268) 
;

-- Column: DD_Order_Candidate.DatePrinted
-- Column: DD_Order_Candidate.DatePrinted
-- 2024-07-11T07:06:27.754Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588741,1091,0,15,542424,'DatePrinted',TO_TIMESTAMP('2024-07-11 10:06:27','YYYY-MM-DD HH24:MI:SS'),100,'N','Date the document was printed.','EE01',0,7,'Indicates the Date that a document was printed.','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Y','N','Y','Date printed',0,0,TO_TIMESTAMP('2024-07-11 10:06:27','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:27.756Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588741 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:27.760Z
/* DDL */  select update_Column_Translation_From_AD_Element(1091) 
;

-- Column: DD_Order_Candidate.DatePromised
-- Column: DD_Order_Candidate.DatePromised
-- 2024-07-11T07:06:28.366Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588742,269,0,15,542424,'DatePromised',TO_TIMESTAMP('2024-07-11 10:06:28','YYYY-MM-DD HH24:MI:SS'),100,'N','@#Date@','Zugesagter Termin für diesen Auftrag','EE01',0,10,'Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.','Y','Y','N','N','Y','N','N','N','N','Y','N','N','N','N','Y','N','Y','Zugesagter Termin',0,0,TO_TIMESTAMP('2024-07-11 10:06:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:06:28.369Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588742 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:28.373Z
/* DDL */  select update_Column_Translation_From_AD_Element(269) 
;

-- Column: DD_Order_Candidate.DateReceived
-- Column: DD_Order_Candidate.DateReceived
-- 2024-07-11T07:06:29.178Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588743,1324,0,15,542424,'DateReceived',TO_TIMESTAMP('2024-07-11 10:06:28','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, zu dem ein Produkt empfangen wurde','EE01',0,7,'"Eingangsdatum" bezeichnet das Datum, zu dem das Produkt empfangen wurde','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Y','N','Y','Eingangsdatum',0,0,TO_TIMESTAMP('2024-07-11 10:06:28','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:29.180Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588743 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:29.183Z
/* DDL */  select update_Column_Translation_From_AD_Element(1324) 
;

-- Column: DD_Order_Candidate.DeliveryRule
-- Column: DD_Order_Candidate.DeliveryRule
-- 2024-07-11T07:06:29.928Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588744,555,0,17,151,542424,'DeliveryRule',TO_TIMESTAMP('2024-07-11 10:06:29','YYYY-MM-DD HH24:MI:SS'),100,'N','A','Definiert die zeitliche Steuerung von Lieferungen','EE01',0,1,'The Delivery Rule indicates when an order should be delivered. For example should the order be delivered when the entire order is complete, when a line is complete or as the products become available.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Lieferart',0,0,TO_TIMESTAMP('2024-07-11 10:06:29','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:29.931Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588744 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:29.934Z
/* DDL */  select update_Column_Translation_From_AD_Element(555) 
;

-- Column: DD_Order_Candidate.DeliveryViaRule
-- Column: DD_Order_Candidate.DeliveryViaRule
-- 2024-07-11T07:06:30.728Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588745,274,0,17,152,542424,'DeliveryViaRule',TO_TIMESTAMP('2024-07-11 10:06:30','YYYY-MM-DD HH24:MI:SS'),100,'N','P','Wie der Auftrag geliefert wird','EE01',0,2,'"Lieferung durch" gibt an, auf welche Weise die Produkte geliefert werden sollen. Beispiel: wird abgeholt oder geliefert?','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Lieferung',0,0,TO_TIMESTAMP('2024-07-11 10:06:30','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:30.730Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588745 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:30.734Z
/* DDL */  select update_Column_Translation_From_AD_Element(274) 
;

-- Column: DD_Order_Candidate.Description
-- Column: DD_Order_Candidate.Description
-- 2024-07-11T07:06:31.585Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588746,275,0,14,542424,'Description',TO_TIMESTAMP('2024-07-11 10:06:31','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,255,'Y','Y','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Beschreibung',0,0,TO_TIMESTAMP('2024-07-11 10:06:31','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:31.587Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588746 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:31.590Z
/* DDL */  select update_Column_Translation_From_AD_Element(275) 
;

-- Column: DD_Order_Candidate.DocAction
-- Column: DD_Order_Candidate.DocAction
-- 2024-07-11T07:06:32.559Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588747,287,0,53042,28,135,542424,'DocAction',TO_TIMESTAMP('2024-07-11 10:06:32','YYYY-MM-DD HH24:MI:SS'),100,'N','CO','Der zukünftige Status des Belegs','EE01',0,2,'You find the current status in the Document Status field. The options are listed in a popup','Y','Y','N','N','Y','N','N','N','N','Y','N','N','N','N','Y','N','Y','Belegverarbeitung',0,0,TO_TIMESTAMP('2024-07-11 10:06:32','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:32.561Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588747 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:32.564Z
/* DDL */  select update_Column_Translation_From_AD_Element(287) 
;

-- Column: DD_Order_Candidate.DocStatus
-- Column: DD_Order_Candidate.DocStatus
-- 2024-07-11T07:06:33.514Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588748,289,0,17,131,542424,'DocStatus',TO_TIMESTAMP('2024-07-11 10:06:33','YYYY-MM-DD HH24:MI:SS'),100,'N','DR','The current status of the document','EE01',0,2,'E','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','Y','N','N','Y','N','N','N','N','Y','N','Y','N','N','Y','N','Y','Belegstatus',30,0,TO_TIMESTAMP('2024-07-11 10:06:33','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:33.516Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588748 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:33.520Z
/* DDL */  select update_Column_Translation_From_AD_Element(289) 
;

-- Column: DD_Order_Candidate.DocumentNo
-- Column: DD_Order_Candidate.DocumentNo
-- 2024-07-11T07:06:34.172Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588749,290,0,10,542424,'DocumentNo',TO_TIMESTAMP('2024-07-11 10:06:33','YYYY-MM-DD HH24:MI:SS'),100,'N','Document sequence number of the document','EE01',0,30,'E','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','Y','N','N','Y','N','N','Y','N','Y','N','Y','N','N','Y','N','N','Nr.',10,1,TO_TIMESTAMP('2024-07-11 10:06:33','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:34.174Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588749 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:34.177Z
/* DDL */  select update_Column_Translation_From_AD_Element(290) 
;

-- Column: DD_Order_Candidate.FreightAmt
-- Column: DD_Order_Candidate.FreightAmt
-- 2024-07-11T07:06:34.820Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588750,306,0,12,542424,'FreightAmt',TO_TIMESTAMP('2024-07-11 10:06:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Frachtbetrag','EE01',0,22,'"Frachtbetrag" gibt den Betrag für Fracht in diesem Beleg an.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Frachtbetrag',0,0,TO_TIMESTAMP('2024-07-11 10:06:34','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:34.821Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588750 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:34.824Z
/* DDL */  select update_Column_Translation_From_AD_Element(306) 
;

-- Column: DD_Order_Candidate.FreightCostRule
-- Column: DD_Order_Candidate.FreightCostRule
-- 2024-07-11T07:06:35.448Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588751,1007,0,17,153,542424,'FreightCostRule',TO_TIMESTAMP('2024-07-11 10:06:35','YYYY-MM-DD HH24:MI:SS'),100,'N','I','Methode zur Berechnung von Frachtkosten','EE01',0,1,'"Frachtkostenberechnung" gibt an, auf welche Weise die Kosten für Fracht berechnet werden.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Frachtkostenberechnung',0,0,TO_TIMESTAMP('2024-07-11 10:06:35','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:35.450Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588751 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:35.452Z
/* DDL */  select update_Column_Translation_From_AD_Element(1007) 
;

-- Column: DD_Order_Candidate.GenerateTo
-- Column: DD_Order_Candidate.GenerateTo
-- 2024-07-11T07:06:36.134Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588752,1491,0,154,28,542424,'GenerateTo',TO_TIMESTAMP('2024-07-11 10:06:35','YYYY-MM-DD HH24:MI:SS'),100,'N','Generate To','EE01',0,1,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Generate To',0,0,TO_TIMESTAMP('2024-07-11 10:06:35','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:36.135Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588752 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:36.138Z
/* DDL */  select update_Column_Translation_From_AD_Element(1491) 
;

-- Column: DD_Order_Candidate.IsApproved
-- Column: DD_Order_Candidate.IsApproved
-- 2024-07-11T07:06:36.789Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588753,351,0,20,542424,'IsApproved',TO_TIMESTAMP('2024-07-11 10:06:36','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Zeigt an, ob dieser Beleg eine Freigabe braucht','EE01',0,1,'Das Selektionsfeld "Freigabe" zeigt an, dass dieser Beleg eine Freigabe braucht, bevor er verarbeitet werden kann','Y','Y','N','N','Y','N','N','N','N','Y','N','N','N','N','Y','N','Y','Freigegeben',0,0,TO_TIMESTAMP('2024-07-11 10:06:36','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:36.790Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588753 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:36.794Z
/* DDL */  select update_Column_Translation_From_AD_Element(351) 
;

-- Column: DD_Order_Candidate.IsDelivered
-- Column: DD_Order_Candidate.IsDelivered
-- 2024-07-11T07:06:37.427Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588754,367,0,20,542424,'IsDelivered',TO_TIMESTAMP('2024-07-11 10:06:37','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,1,'Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Y','N','Y','Zugestellt',0,0,TO_TIMESTAMP('2024-07-11 10:06:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:06:37.428Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588754 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:37.431Z
/* DDL */  select update_Column_Translation_From_AD_Element(367) 
;

-- Column: DD_Order_Candidate.IsDropShip
-- Column: DD_Order_Candidate.IsDropShip
-- 2024-07-11T07:06:38.064Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588755,2466,0,20,542424,'IsDropShip',TO_TIMESTAMP('2024-07-11 10:06:37','YYYY-MM-DD HH24:MI:SS'),100,'N','Beim Streckengeschäft wird die Ware direkt vom Lieferanten zum Kunden geliefert','EE01',0,1,'Drop Shipments do not cause any Inventory reservations or movements as the Shipment is from the Vendor''s inventory. The Shipment of the Vendor to the Customer must be confirmed.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Abweichende Lieferadresse',0,0,TO_TIMESTAMP('2024-07-11 10:06:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:06:38.066Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588755 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:38.068Z
/* DDL */  select update_Column_Translation_From_AD_Element(2466) 
;

-- Column: DD_Order_Candidate.IsInDispute
-- Column: DD_Order_Candidate.IsInDispute
-- 2024-07-11T07:06:38.713Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588756,2543,0,20,542424,'IsInDispute',TO_TIMESTAMP('2024-07-11 10:06:38','YYYY-MM-DD HH24:MI:SS'),100,'N','N','','EE01',0,1,'','Y','Y','Y','N','Y','N','N','N','N','Y','N','N','N','N','Y','N','Y','Strittig',0,0,TO_TIMESTAMP('2024-07-11 10:06:38','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:38.715Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588756 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:38.717Z
/* DDL */  select update_Column_Translation_From_AD_Element(2543) 
;

-- Column: DD_Order_Candidate.IsInTransit
-- Column: DD_Order_Candidate.IsInTransit
-- 2024-07-11T07:06:39.365Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588757,2397,0,20,542424,'IsInTransit',TO_TIMESTAMP('2024-07-11 10:06:39','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Movement is in transit','EE01',0,1,'Material Movement is in transit - shipped, but not received.
The transaction is completed, if confirmed.','Y','Y','N','N','Y','N','N','N','N','Y','N','N','N','N','Y','N','Y','In Transit',0,0,TO_TIMESTAMP('2024-07-11 10:06:39','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:39.366Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588757 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:39.369Z
/* DDL */  select update_Column_Translation_From_AD_Element(2397) 
;

-- Column: DD_Order_Candidate.IsPrinted
-- Column: DD_Order_Candidate.IsPrinted
-- 2024-07-11T07:06:39.998Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588758,399,0,20,542424,'IsPrinted',TO_TIMESTAMP('2024-07-11 10:06:39','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Indicates if this document / line is printed','EE01',0,1,'The Printed checkbox indicates if this document or line will included when printing.','Y','Y','N','N','Y','N','N','N','N','Y','N','N','N','N','Y','N','Y','andrucken',0,0,TO_TIMESTAMP('2024-07-11 10:06:39','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:40Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588758 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:40.002Z
/* DDL */  select update_Column_Translation_From_AD_Element(399) 
;

-- Column: DD_Order_Candidate.IsSelected
-- Column: DD_Order_Candidate.IsSelected
-- 2024-07-11T07:06:40.611Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588759,1321,0,20,542424,'IsSelected',TO_TIMESTAMP('2024-07-11 10:06:40','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,1,'Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Y','N','Y','Selektiert',0,0,TO_TIMESTAMP('2024-07-11 10:06:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:06:40.612Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588759 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:40.614Z
/* DDL */  select update_Column_Translation_From_AD_Element(1321) 
;

-- Column: DD_Order_Candidate.IsSimulated
-- Column: DD_Order_Candidate.IsSimulated
-- 2024-07-11T07:06:41.211Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588760,580611,0,20,542424,'IsSimulated',TO_TIMESTAMP('2024-07-11 10:06:40','YYYY-MM-DD HH24:MI:SS'),100,'N','N','EE01',0,1,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Simulated',0,0,TO_TIMESTAMP('2024-07-11 10:06:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:06:41.212Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588760 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:41.216Z
/* DDL */  select update_Column_Translation_From_AD_Element(580611) 
;

-- Column: DD_Order_Candidate.IsSOTrx
-- Column: DD_Order_Candidate.IsSOTrx
-- 2024-07-11T07:06:41.824Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588761,1106,0,20,542424,'IsSOTrx',TO_TIMESTAMP('2024-07-11 10:06:41','YYYY-MM-DD HH24:MI:SS'),100,'N','@IsSOTrx@','Dies ist eine Verkaufstransaktion','EE01',0,1,'The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Verkaufstransaktion',0,0,TO_TIMESTAMP('2024-07-11 10:06:41','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:41.826Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588761 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:41.828Z
/* DDL */  select update_Column_Translation_From_AD_Element(1106) 
;

-- Column: DD_Order_Candidate.MRP_AllowCleanup
-- Column: DD_Order_Candidate.MRP_AllowCleanup
-- 2024-07-11T07:06:42.472Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588762,542623,0,20,542424,'MRP_AllowCleanup',TO_TIMESTAMP('2024-07-11 10:06:42','YYYY-MM-DD HH24:MI:SS'),100,'N','N','MRP is allowed to remove this document','EE01',0,1,'Y','Y','N','N','Y','N','N','N','N','Y','N','N','N','N','N','N','Y','MRP Allow Cleanup',0,0,TO_TIMESTAMP('2024-07-11 10:06:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:06:42.475Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588762 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:42.478Z
/* DDL */  select update_Column_Translation_From_AD_Element(542623) 
;

-- Column: DD_Order_Candidate.MRP_Generated
-- Column: DD_Order_Candidate.MRP_Generated
-- 2024-07-11T07:06:43.117Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588763,542622,0,20,542424,'MRP_Generated',TO_TIMESTAMP('2024-07-11 10:06:42','YYYY-MM-DD HH24:MI:SS'),100,'N','N','This document was generated by MRP','EE01',0,1,'Y','Y','N','N','Y','N','N','N','N','Y','N','N','N','N','N','N','Y','MRP Generated Document',0,0,TO_TIMESTAMP('2024-07-11 10:06:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:06:43.118Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588763 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:43.121Z
/* DDL */  select update_Column_Translation_From_AD_Element(542622) 
;

-- Column: DD_Order_Candidate.MRP_ToDelete
-- Column: DD_Order_Candidate.MRP_ToDelete
-- 2024-07-11T07:06:43.813Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588764,542701,0,20,542424,'MRP_ToDelete',TO_TIMESTAMP('2024-07-11 10:06:43','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Indicates if this document is scheduled to be deleted by MRP','EE01',0,1,'Y','Y','N','N','Y','N','N','N','N','Y','N','N','N','N','N','N','Y','To be deleted (MRP)',0,0,TO_TIMESTAMP('2024-07-11 10:06:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:06:43.816Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588764 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:43.819Z
/* DDL */  select update_Column_Translation_From_AD_Element(542701) 
;

-- Column: DD_Order_Candidate.M_Shipper_ID
-- Column: DD_Order_Candidate.M_Shipper_ID
-- 2024-07-11T07:06:44.568Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588765,455,0,19,542424,'M_Shipper_ID',TO_TIMESTAMP('2024-07-11 10:06:44','YYYY-MM-DD HH24:MI:SS'),100,'N','Methode oder Art der Warenlieferung','EE01',0,22,'"Lieferweg" bezeichnet die Art der Warenlieferung.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Lieferweg',0,0,TO_TIMESTAMP('2024-07-11 10:06:44','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:44.570Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588765 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:44.575Z
/* DDL */  select update_Column_Translation_From_AD_Element(455) 
;

-- Column: DD_Order_Candidate.M_Warehouse_From_ID
-- Column: DD_Order_Candidate.M_Warehouse_From_ID
-- 2024-07-11T07:06:45.607Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588766,577736,0,30,540420,542424,'M_Warehouse_From_ID',TO_TIMESTAMP('2024-07-11 10:06:45','YYYY-MM-DD HH24:MI:SS'),100,'N','','EE01',0,10,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Lager ab',0,0,TO_TIMESTAMP('2024-07-11 10:06:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:06:45.609Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588766 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:45.613Z
/* DDL */  select update_Column_Translation_From_AD_Element(577736) 
;

-- Column: DD_Order_Candidate.M_Warehouse_ID
-- Column: DD_Order_Candidate.M_Warehouse_ID
-- 2024-07-11T07:06:46.608Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588767,459,0,19,542424,52024,'M_Warehouse_ID',TO_TIMESTAMP('2024-07-11 10:06:46','YYYY-MM-DD HH24:MI:SS'),100,'N','Lager oder Ort für Dienstleistung','EE01',0,22,'E','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Lager',40,0,TO_TIMESTAMP('2024-07-11 10:06:46','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:46.611Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588767 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:46.614Z
/* DDL */  select update_Column_Translation_From_AD_Element(459) 
;

-- Column: DD_Order_Candidate.M_Warehouse_To_ID
-- Column: DD_Order_Candidate.M_Warehouse_To_ID
-- 2024-07-11T07:06:47.528Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,ReadOnlyLogic,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588768,577737,0,30,540420,542424,'M_Warehouse_To_ID',TO_TIMESTAMP('2024-07-11 10:06:47','YYYY-MM-DD HH24:MI:SS'),100,'N','','EE01',0,10,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Lager an','',0,0,TO_TIMESTAMP('2024-07-11 10:06:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:06:47.531Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588768 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:47.534Z
/* DDL */  select update_Column_Translation_From_AD_Element(577737) 
;

-- Column: DD_Order_Candidate.NoPackages
-- Column: DD_Order_Candidate.NoPackages
-- 2024-07-11T07:06:48.406Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588769,2113,0,11,542424,'NoPackages',TO_TIMESTAMP('2024-07-11 10:06:48','YYYY-MM-DD HH24:MI:SS'),100,'N','Anzahl der Handling Units die versendet werden','EE01',0,22,'N','Y','Y','N','Y','N','N','N','N','N','N','N','N','N','Y','N','Y','Anzahl Handling Units',0,0,TO_TIMESTAMP('2024-07-11 10:06:48','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:48.408Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588769 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:48.411Z
/* DDL */  select update_Column_Translation_From_AD_Element(2113) 
;

-- Column: DD_Order_Candidate.PickDate
-- Column: DD_Order_Candidate.PickDate
-- 2024-07-11T07:06:49.080Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588770,2117,0,16,542424,'PickDate',TO_TIMESTAMP('2024-07-11 10:06:48','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum/Zeit der Kommissionierung für die Lieferung','EE01',0,7,'Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Y','N','Y','Kommissionierdatum',0,0,TO_TIMESTAMP('2024-07-11 10:06:48','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:49.082Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588770 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:49.085Z
/* DDL */  select update_Column_Translation_From_AD_Element(2117) 
;

-- Column: DD_Order_Candidate.POReference
-- Column: DD_Order_Candidate.POReference
-- 2024-07-11T07:06:49.839Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588771,952,0,10,542424,'POReference',TO_TIMESTAMP('2024-07-11 10:06:49','YYYY-MM-DD HH24:MI:SS'),100,'N','Referenz-Nummer des Kunden','EE01',0,40,'E','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','Y','N','N','N','N','N','N','N','N','N','Y','N','N','Y','N','Y','Referenz',50,0,TO_TIMESTAMP('2024-07-11 10:06:49','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:49.842Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588771 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:49.844Z
/* DDL */  select update_Column_Translation_From_AD_Element(952) 
;

-- Column: DD_Order_Candidate.Posted
-- Column: DD_Order_Candidate.Posted
-- 2024-07-11T07:06:50.509Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588772,1308,0,17,234,542424,'Posted',TO_TIMESTAMP('2024-07-11 10:06:50','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Buchungsstatus','EE01',0,1,'Zeigt den Verbuchungsstatus der Hauptbuchpositionen an.','Y','Y','N','N','Y','N','N','N','N','Y','N','N','N','N','Y','N','Y','Buchungsstatus',0,0,TO_TIMESTAMP('2024-07-11 10:06:50','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:50.511Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588772 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:50.513Z
/* DDL */  select update_Column_Translation_From_AD_Element(1308) 
;

-- Column: DD_Order_Candidate.PostingError_Issue_ID
-- Column: DD_Order_Candidate.PostingError_Issue_ID
-- 2024-07-11T07:06:51.166Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588773,577755,0,30,540991,542424,'PostingError_Issue_ID',TO_TIMESTAMP('2024-07-11 10:06:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','EE01',0,10,'Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','Verbuchungsfehler',0,0,TO_TIMESTAMP('2024-07-11 10:06:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:06:51.168Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588773 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:51.170Z
/* DDL */  select update_Column_Translation_From_AD_Element(577755) 
;

-- Column: DD_Order_Candidate.PP_Plant_ID
-- Column: DD_Order_Candidate.PP_Plant_ID
-- 2024-07-11T07:06:51.834Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588774,542433,0,30,53320,542424,52002,'PP_Plant_ID',TO_TIMESTAMP('2024-07-11 10:06:51','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Produktionsstätte',0,0,TO_TIMESTAMP('2024-07-11 10:06:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:06:51.836Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588774 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:51.838Z
/* DDL */  select update_Column_Translation_From_AD_Element(542433) 
;

-- Column: DD_Order_Candidate.PP_Product_Planning_ID
-- Column: DD_Order_Candidate.PP_Product_Planning_ID
-- 2024-07-11T07:06:52.468Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588775,53268,0,30,542424,'PP_Product_Planning_ID',TO_TIMESTAMP('2024-07-11 10:06:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','EE01',0,10,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Product Planning',0,0,TO_TIMESTAMP('2024-07-11 10:06:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:06:52.469Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588775 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:52.472Z
/* DDL */  select update_Column_Translation_From_AD_Element(53268) 
;

-- Column: DD_Order_Candidate.PriorityRule
-- Column: DD_Order_Candidate.PriorityRule
-- 2024-07-11T07:06:53.132Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588776,522,0,17,154,542424,'PriorityRule',TO_TIMESTAMP('2024-07-11 10:06:52','YYYY-MM-DD HH24:MI:SS'),100,'N','5','Priority of a document','EE01',0,1,'The Priority indicates the importance (high, medium, low) of this document','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Priorität',0,0,TO_TIMESTAMP('2024-07-11 10:06:52','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:53.135Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588776 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:53.139Z
/* DDL */  select update_Column_Translation_From_AD_Element(522) 
;

-- Column: DD_Order_Candidate.Processed
-- Column: DD_Order_Candidate.Processed
-- 2024-07-11T07:06:53.766Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588777,1047,0,20,542424,'Processed',TO_TIMESTAMP('2024-07-11 10:06:53','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','EE01',0,1,'Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','Y','N','N','Y','N','N','N','N','Y','N','N','N','N','Y','N','Y','Verarbeitet',0,0,TO_TIMESTAMP('2024-07-11 10:06:53','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:53.769Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588777 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:53.772Z
/* DDL */  select update_Column_Translation_From_AD_Element(1047) 
;

-- Column: DD_Order_Candidate.Processing
-- Column: DD_Order_Candidate.Processing
-- 2024-07-11T07:06:54.439Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588778,524,0,53042,28,542424,'Processing',TO_TIMESTAMP('2024-07-11 10:06:54','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,1,'Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Y','N','Y','Process Now',0,0,TO_TIMESTAMP('2024-07-11 10:06:54','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:54.440Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588778 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:54.443Z
/* DDL */  select update_Column_Translation_From_AD_Element(524) 
;

-- Column: DD_Order_Candidate.Ref_Order_ID
-- Column: DD_Order_Candidate.Ref_Order_ID
-- 2024-07-11T07:06:55.114Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588779,2431,0,18,53631,542424,'Ref_Order_ID',TO_TIMESTAMP('2024-07-11 10:06:54','YYYY-MM-DD HH24:MI:SS'),100,'N','Reference to corresponding Sales/Purchase Order','EE01',0,10,'Reference of the Sales Order Line to the corresponding Purchase Order Line or vice versa.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Referenced Order',0,0,TO_TIMESTAMP('2024-07-11 10:06:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:06:55.116Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588779 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:55.119Z
/* DDL */  select update_Column_Translation_From_AD_Element(2431) 
;

-- Column: DD_Order_Candidate.SalesRep_ID
-- Column: DD_Order_Candidate.SalesRep_ID
-- 2024-07-11T07:06:55.788Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588780,1063,0,18,286,542424,'SalesRep_ID',TO_TIMESTAMP('2024-07-11 10:06:55','YYYY-MM-DD HH24:MI:SS'),100,'N','@#AD_User_ID@','','EE01',0,1,'','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Kundenbetreuer',0,0,TO_TIMESTAMP('2024-07-11 10:06:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:06:55.790Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588780 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:55.793Z
/* DDL */  select update_Column_Translation_From_AD_Element(1063) 
;

-- Column: DD_Order_Candidate.SendEMail
-- Column: DD_Order_Candidate.SendEMail
-- 2024-07-11T07:06:56.432Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588781,1978,0,20,542424,'SendEMail',TO_TIMESTAMP('2024-07-11 10:06:56','YYYY-MM-DD HH24:MI:SS'),100,'N','Enable sending Document EMail','EE01',0,1,'Send emails with document attached (e.g. Invoice, Delivery Note, etc.)','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','E-Mail senden',0,0,TO_TIMESTAMP('2024-07-11 10:06:56','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:56.434Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588781 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:56.436Z
/* DDL */  select update_Column_Translation_From_AD_Element(1978) 
;

-- Column: DD_Order_Candidate.ShipDate
-- Column: DD_Order_Candidate.ShipDate
-- 2024-07-11T07:06:57.091Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588782,2123,0,16,542424,'ShipDate',TO_TIMESTAMP('2024-07-11 10:06:56','YYYY-MM-DD HH24:MI:SS'),100,'N','Shipment Date/Time','EE01',0,7,'Actual Date/Time of Shipment (pick up)','N','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Y','N','Y','Lieferdatum',0,0,TO_TIMESTAMP('2024-07-11 10:06:56','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:57.093Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588782 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:57.095Z
/* DDL */  select update_Column_Translation_From_AD_Element(2123) 
;

-- Column: DD_Order_Candidate.User1_ID
-- Column: DD_Order_Candidate.User1_ID
-- 2024-07-11T07:06:57.759Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588783,613,0,18,134,542424,'User1_ID',TO_TIMESTAMP('2024-07-11 10:06:57','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzerdefiniertes Element Nr. 1','EE01',0,22,'Das Nutzerdefinierte Element zeigt die optionalen Elementwerte an, die für diese Kontenkombination definiert sind.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Nutzer 1',0,0,TO_TIMESTAMP('2024-07-11 10:06:57','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:57.762Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588783 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:57.765Z
/* DDL */  select update_Column_Translation_From_AD_Element(613) 
;

-- Column: DD_Order_Candidate.User2_ID
-- Column: DD_Order_Candidate.User2_ID
-- 2024-07-11T07:06:58.444Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588784,614,0,18,137,542424,'User2_ID',TO_TIMESTAMP('2024-07-11 10:06:58','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzerdefiniertes Element Nr. 2','EE01',0,22,'Das Nutzerdefinierte Element zeigt die optionalen Elementwerte an, die für diese Kontenkombination definiert sind.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Nutzer 2',0,0,TO_TIMESTAMP('2024-07-11 10:06:58','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:06:58.446Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588784 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:58.448Z
/* DDL */  select update_Column_Translation_From_AD_Element(614) 
;

-- Column: DD_Order_Candidate.Volume
-- Column: DD_Order_Candidate.Volume
-- 2024-07-11T07:06:59.072Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588785,627,0,22,542424,'Volume',TO_TIMESTAMP('2024-07-11 10:06:58','YYYY-MM-DD HH24:MI:SS'),100,'N','Volumen eines Produktes','EE01',0,22,'Gibt das Volumen eines Produktes in der Volumen-Mengeneinheit des Mandanten an.','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Y','N','Y','Volumen',0,0,TO_TIMESTAMP('2024-07-11 10:06:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:06:59.075Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588785 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:59.078Z
/* DDL */  select update_Column_Translation_From_AD_Element(627) 
;

-- Column: DD_Order_Candidate.Weight
-- Column: DD_Order_Candidate.Weight
-- 2024-07-11T07:06:59.771Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588786,629,0,22,542424,'Weight',TO_TIMESTAMP('2024-07-11 10:06:59','YYYY-MM-DD HH24:MI:SS'),100,'N','Gewicht eines Produktes','EE01',0,22,'The Weight indicates the weight  of the product in the Weight UOM of the Client','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Y','N','Y','Gewicht',0,0,TO_TIMESTAMP('2024-07-11 10:06:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:06:59.773Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588786 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:06:59.775Z
/* DDL */  select update_Column_Translation_From_AD_Element(629) 
;

-- Column: DD_Order_Candidate.CreatePackage
-- Column: DD_Order_Candidate.CreatePackage
-- 2024-07-11T07:07:46.442Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588739
;

-- 2024-07-11T07:07:46.454Z
DELETE FROM AD_Column WHERE AD_Column_ID=588739
;

-- Column: DD_Order_Candidate.CreateFrom
-- Column: DD_Order_Candidate.CreateFrom
-- 2024-07-11T07:07:51.069Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588738
;

-- 2024-07-11T07:07:51.073Z
DELETE FROM AD_Column WHERE AD_Column_ID=588738
;

-- Column: DD_Order_Candidate.DatePrinted
-- Column: DD_Order_Candidate.DatePrinted
-- 2024-07-11T07:07:56.818Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588741
;

-- 2024-07-11T07:07:56.823Z
DELETE FROM AD_Column WHERE AD_Column_ID=588741
;

-- Column: DD_Order_Candidate.DateReceived
-- Column: DD_Order_Candidate.DateReceived
-- 2024-07-11T07:08:01.163Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588743
;

-- 2024-07-11T07:08:01.168Z
DELETE FROM AD_Column WHERE AD_Column_ID=588743
;

-- Column: DD_Order_Candidate.DocAction
-- Column: DD_Order_Candidate.DocAction
-- 2024-07-11T07:08:11.946Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588747
;

-- 2024-07-11T07:08:11.950Z
DELETE FROM AD_Column WHERE AD_Column_ID=588747
;

-- Column: DD_Order_Candidate.DocStatus
-- Column: DD_Order_Candidate.DocStatus
-- 2024-07-11T07:08:16.042Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588748
;

-- 2024-07-11T07:08:16.046Z
DELETE FROM AD_Column WHERE AD_Column_ID=588748
;

-- Column: DD_Order_Candidate.DocumentNo
-- Column: DD_Order_Candidate.DocumentNo
-- 2024-07-11T07:08:20.113Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588749
;

-- 2024-07-11T07:08:20.116Z
DELETE FROM AD_Column WHERE AD_Column_ID=588749
;

-- Column: DD_Order_Candidate.GenerateTo
-- Column: DD_Order_Candidate.GenerateTo
-- 2024-07-11T07:08:24.959Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588752
;

-- 2024-07-11T07:08:24.963Z
DELETE FROM AD_Column WHERE AD_Column_ID=588752
;

-- Column: DD_Order_Candidate.IsApproved
-- Column: DD_Order_Candidate.IsApproved
-- 2024-07-11T07:08:29.077Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588753
;

-- 2024-07-11T07:08:29.082Z
DELETE FROM AD_Column WHERE AD_Column_ID=588753
;

-- Column: DD_Order_Candidate.IsDelivered
-- Column: DD_Order_Candidate.IsDelivered
-- 2024-07-11T07:08:32.017Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588754
;

-- 2024-07-11T07:08:32.022Z
DELETE FROM AD_Column WHERE AD_Column_ID=588754
;

-- Column: DD_Order_Candidate.IsInTransit
-- Column: DD_Order_Candidate.IsInTransit
-- 2024-07-11T07:08:37.663Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588757
;

-- 2024-07-11T07:08:37.666Z
DELETE FROM AD_Column WHERE AD_Column_ID=588757
;

-- Column: DD_Order_Candidate.IsPrinted
-- Column: DD_Order_Candidate.IsPrinted
-- 2024-07-11T07:08:40.047Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588758
;

-- 2024-07-11T07:08:40.052Z
DELETE FROM AD_Column WHERE AD_Column_ID=588758
;

-- Column: DD_Order_Candidate.IsSelected
-- Column: DD_Order_Candidate.IsSelected
-- 2024-07-11T07:08:45.390Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588759
;

-- 2024-07-11T07:08:45.394Z
DELETE FROM AD_Column WHERE AD_Column_ID=588759
;

-- Column: DD_Order_Candidate.IsInDispute
-- Column: DD_Order_Candidate.IsInDispute
-- 2024-07-11T07:08:48.855Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588756
;

-- 2024-07-11T07:08:48.859Z
DELETE FROM AD_Column WHERE AD_Column_ID=588756
;

-- Column: DD_Order_Candidate.IsSimulated
-- Column: DD_Order_Candidate.IsSimulated
-- 2024-07-11T07:08:53.253Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588760
;

-- 2024-07-11T07:08:53.260Z
DELETE FROM AD_Column WHERE AD_Column_ID=588760
;

-- Column: DD_Order_Candidate.IsSOTrx
-- Column: DD_Order_Candidate.IsSOTrx
-- 2024-07-11T07:08:58.484Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588761
;

-- 2024-07-11T07:08:58.487Z
DELETE FROM AD_Column WHERE AD_Column_ID=588761
;

-- Column: DD_Order_Candidate.MRP_AllowCleanup
-- Column: DD_Order_Candidate.MRP_AllowCleanup
-- 2024-07-11T07:09:00.701Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588762
;

-- 2024-07-11T07:09:00.704Z
DELETE FROM AD_Column WHERE AD_Column_ID=588762
;

-- Column: DD_Order_Candidate.MRP_Generated
-- Column: DD_Order_Candidate.MRP_Generated
-- 2024-07-11T07:09:02.752Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588763
;

-- 2024-07-11T07:09:02.755Z
DELETE FROM AD_Column WHERE AD_Column_ID=588763
;

-- Column: DD_Order_Candidate.MRP_ToDelete
-- Column: DD_Order_Candidate.MRP_ToDelete
-- 2024-07-11T07:09:05.118Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588764
;

-- 2024-07-11T07:09:05.122Z
DELETE FROM AD_Column WHERE AD_Column_ID=588764
;

-- Column: DD_Order_Candidate.PostingError_Issue_ID
-- Column: DD_Order_Candidate.PostingError_Issue_ID
-- 2024-07-11T07:09:15.222Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588773
;

-- 2024-07-11T07:09:15.225Z
DELETE FROM AD_Column WHERE AD_Column_ID=588773
;

-- Column: DD_Order_Candidate.SendEMail
-- Column: DD_Order_Candidate.SendEMail
-- 2024-07-11T07:09:22.193Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588781
;

-- 2024-07-11T07:09:22.197Z
DELETE FROM AD_Column WHERE AD_Column_ID=588781
;

-- Column: DD_Order_Candidate.ShipDate
-- Column: DD_Order_Candidate.ShipDate
-- 2024-07-11T07:09:24.861Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588782
;

-- 2024-07-11T07:09:24.865Z
DELETE FROM AD_Column WHERE AD_Column_ID=588782
;

-- Column: DD_Order_Candidate.User1_ID
-- Column: DD_Order_Candidate.User1_ID
-- 2024-07-11T07:09:30.685Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588783
;

-- 2024-07-11T07:09:30.690Z
DELETE FROM AD_Column WHERE AD_Column_ID=588783
;

-- Column: DD_Order_Candidate.User2_ID
-- Column: DD_Order_Candidate.User2_ID
-- 2024-07-11T07:09:32.708Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588784
;

-- 2024-07-11T07:09:32.713Z
DELETE FROM AD_Column WHERE AD_Column_ID=588784
;

-- Column: DD_Order_Candidate.C_Charge_ID
-- Column: DD_Order_Candidate.C_Charge_ID
-- 2024-07-11T07:09:51.196Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588731
;

-- 2024-07-11T07:09:51.199Z
DELETE FROM AD_Column WHERE AD_Column_ID=588731
;

-- Column: DD_Order_Candidate.ChargeAmt
-- Column: DD_Order_Candidate.ChargeAmt
-- 2024-07-11T07:09:54.926Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588733
;

-- 2024-07-11T07:09:54.930Z
DELETE FROM AD_Column WHERE AD_Column_ID=588733
;

-- Column: DD_Order_Candidate.C_Invoice_ID
-- Column: DD_Order_Candidate.C_Invoice_ID
-- 2024-07-11T07:09:58.019Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588734
;

-- 2024-07-11T07:09:58.023Z
DELETE FROM AD_Column WHERE AD_Column_ID=588734
;

-- Column: DD_Order_Candidate.CreateConfirm
-- Column: DD_Order_Candidate.CreateConfirm
-- 2024-07-11T07:10:05.616Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588737
;

-- 2024-07-11T07:10:05.620Z
DELETE FROM AD_Column WHERE AD_Column_ID=588737
;

-- Column: DD_Order_Candidate.M_WarehouseTo_ID
-- Column SQL (old): null
-- Column: DD_Order_Candidate.M_WarehouseTo_ID
-- 2024-07-11T07:13:16.125Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588808,543474,0,19,542424,'M_WarehouseTo_ID','(select l.M_Warehouse_ID from M_Locator l where l.M_Locator_ID = DD_OrderLine.M_LocatorTo_ID)',TO_TIMESTAMP('2024-07-11 10:13:15','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'E','Y','Y','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Lager Nach',20,0,TO_TIMESTAMP('2024-07-11 10:13:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:13:16.128Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588808 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:13:16.133Z
/* DDL */  select update_Column_Translation_From_AD_Element(543474) 
;

-- Column: DD_Order_Candidate.PickedQty
-- Column: DD_Order_Candidate.PickedQty
-- 2024-07-11T07:13:16.694Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588809,2422,0,29,542424,'PickedQty',TO_TIMESTAMP('2024-07-11 10:13:16','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,22,'Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Y','N','Y','Picked Quantity',0,0,TO_TIMESTAMP('2024-07-11 10:13:16','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:13:16.696Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588809 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:13:16.698Z
/* DDL */  select update_Column_Translation_From_AD_Element(2422) 
;

-- Column: DD_Order_Candidate.PP_Plant_From_ID
-- Column: DD_Order_Candidate.PP_Plant_From_ID
-- 2024-07-11T07:13:17.245Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588810,542485,0,30,53320,542424,52002,'PP_Plant_From_ID',TO_TIMESTAMP('2024-07-11 10:13:17','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Produktionsstätte ab',0,0,TO_TIMESTAMP('2024-07-11 10:13:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:13:17.246Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588810 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:13:17.248Z
/* DDL */  select update_Column_Translation_From_AD_Element(542485) 
;

-- Column: DD_Order_Candidate.QtyDelivered
-- Column: DD_Order_Candidate.QtyDelivered
-- 2024-07-11T07:13:17.750Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588811,528,0,29,542424,'QtyDelivered',TO_TIMESTAMP('2024-07-11 10:13:17','YYYY-MM-DD HH24:MI:SS'),100,'N','Gelieferte Menge','EE01',0,10,'Die "Gelieferte Menge" bezeichnet die Menge einer Ware, die geliefert wurde.','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Y','N','Y','Gelieferte Menge',0,0,TO_TIMESTAMP('2024-07-11 10:13:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:13:17.751Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588811 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:13:17.753Z
/* DDL */  select update_Column_Translation_From_AD_Element(528) 
;

-- Column: DD_Order_Candidate.QtyEntered
-- Column: DD_Order_Candidate.QtyEntered
-- 2024-07-11T07:13:18.302Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588812,2589,0,29,542424,'QtyEntered',TO_TIMESTAMP('2024-07-11 10:13:18','YYYY-MM-DD HH24:MI:SS'),100,'N','1','Die Eingegebene Menge basiert auf der gewählten Mengeneinheit','EE01',0,22,'Die Eingegebene Menge wird in die Basismenge zur Produkt-Mengeneinheit umgewandelt','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Menge',0,0,TO_TIMESTAMP('2024-07-11 10:13:18','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:13:18.304Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588812 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:13:18.306Z
/* DDL */  select update_Column_Translation_From_AD_Element(2589) 
;

-- Column: DD_Order_Candidate.QtyEnteredTU
-- Column: DD_Order_Candidate.QtyEnteredTU
-- 2024-07-11T07:13:18.992Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588813,542397,0,29,542424,'QtyEnteredTU',TO_TIMESTAMP('2024-07-11 10:13:18','YYYY-MM-DD HH24:MI:SS'),100,'N','1','EE01',0,22,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Menge TU',0,0,TO_TIMESTAMP('2024-07-11 10:13:18','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:13:18.993Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588813 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:13:18.997Z
/* DDL */  select update_Column_Translation_From_AD_Element(542397) 
;

-- Column: DD_Order_Candidate.QtyInTransit
-- Column: DD_Order_Candidate.QtyInTransit
-- 2024-07-11T07:13:19.518Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588814,53312,0,29,542424,'QtyInTransit',TO_TIMESTAMP('2024-07-11 10:13:19','YYYY-MM-DD HH24:MI:SS'),100,'N','0','EE01',0,22,'Y','Y','N','N','Y','N','N','N','N','Y','N','N','N','N','Y','N','Y','Qty In Transit',0,2,TO_TIMESTAMP('2024-07-11 10:13:19','YYYY-MM-DD HH24:MI:SS'),100,1.000000000000)
;

-- 2024-07-11T07:13:19.519Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588814 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:13:19.522Z
/* DDL */  select update_Column_Translation_From_AD_Element(53312) 
;

-- Column: DD_Order_Candidate.QtyOrdered
-- Column: DD_Order_Candidate.QtyOrdered
-- 2024-07-11T07:13:20.057Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588815,531,0,29,542424,'QtyOrdered',TO_TIMESTAMP('2024-07-11 10:13:19','YYYY-MM-DD HH24:MI:SS'),100,'N','1','Bestellt/ Beauftragt','EE01',0,22,'Die "Bestellte Menge" bezeichnet die Menge einer Ware, die bestellt wurde.','Y','Y','N','N','N','N','N','Y','N','Y','N','N','N','N','Y','N','Y','Bestellt/ Beauftragt',0,2,TO_TIMESTAMP('2024-07-11 10:13:19','YYYY-MM-DD HH24:MI:SS'),100,1.000000000000)
;

-- 2024-07-11T07:13:20.058Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588815 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:13:20.060Z
/* DDL */  select update_Column_Translation_From_AD_Element(531) 
;

-- Column: DD_Order_Candidate.QtyReserved
-- Column: DD_Order_Candidate.QtyReserved
-- 2024-07-11T07:13:20.582Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588816,532,0,29,542424,'QtyReserved',TO_TIMESTAMP('2024-07-11 10:13:20','YYYY-MM-DD HH24:MI:SS'),100,'N','Reservierte Menge','EE01',0,10,'Die "Reservierte Menge" bezeichnet die Menge einer Ware, die zur Zeit reserviert ist.','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Y','N','Y','Reservierte Menge',0,0,TO_TIMESTAMP('2024-07-11 10:13:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:13:20.584Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588816 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:13:20.586Z
/* DDL */  select update_Column_Translation_From_AD_Element(532) 
;

-- Column: DD_Order_Candidate.ScrappedQty
-- Column: DD_Order_Candidate.ScrappedQty
-- 2024-07-11T07:13:21.125Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588817,2435,0,29,542424,'ScrappedQty',TO_TIMESTAMP('2024-07-11 10:13:20','YYYY-MM-DD HH24:MI:SS'),100,'N','0','Durch QA verworfene Menge','EE01',0,22,'Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Verworfene Menge',0,0,TO_TIMESTAMP('2024-07-11 10:13:20','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:13:21.127Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588817 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:13:21.130Z
/* DDL */  select update_Column_Translation_From_AD_Element(2435) 
;

-- Column: DD_Order_Candidate.TargetQty
-- Column: DD_Order_Candidate.TargetQty
-- 2024-07-11T07:13:21.658Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588818,2436,0,29,542424,'TargetQty',TO_TIMESTAMP('2024-07-11 10:13:21','YYYY-MM-DD HH24:MI:SS'),100,'N','Zielmenge der Warenbewegung','EE01',0,22,'Die Menge, die empfangen worden sein sollte','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Zielmenge',0,0,TO_TIMESTAMP('2024-07-11 10:13:21','YYYY-MM-DD HH24:MI:SS'),100,1)
;

-- 2024-07-11T07:13:21.659Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588818 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:13:21.661Z
/* DDL */  select update_Column_Translation_From_AD_Element(2436) 
;

-- Column: DD_Order_Candidate.User1_ID
-- Column: DD_Order_Candidate.User1_ID
-- 2024-07-11T07:13:22.216Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588819,613,0,18,134,542424,'User1_ID',TO_TIMESTAMP('2024-07-11 10:13:22','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzerdefiniertes Element Nr. 1','EE01',0,10,'Das Nutzerdefinierte Element zeigt die optionalen Elementwerte an, die für diese Kontenkombination definiert sind.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Nutzer 1',0,0,TO_TIMESTAMP('2024-07-11 10:13:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:13:22.218Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588819 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:13:22.220Z
/* DDL */  select update_Column_Translation_From_AD_Element(613) 
;

-- Column: DD_Order_Candidate.User2_ID
-- Column: DD_Order_Candidate.User2_ID
-- 2024-07-11T07:13:22.764Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588820,614,0,18,137,542424,'User2_ID',TO_TIMESTAMP('2024-07-11 10:13:22','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzerdefiniertes Element Nr. 2','EE01',0,10,'Das Nutzerdefinierte Element zeigt die optionalen Elementwerte an, die für diese Kontenkombination definiert sind.','Y','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','Nutzer 2',0,0,TO_TIMESTAMP('2024-07-11 10:13:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:13:22.766Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588820 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:13:22.768Z
/* DDL */  select update_Column_Translation_From_AD_Element(614) 
;

-- Column: DD_Order_Candidate.M_WarehouseTo_ID
-- Column: DD_Order_Candidate.M_WarehouseTo_ID
-- 2024-07-11T07:13:57.237Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588808
;

-- 2024-07-11T07:13:57.242Z
DELETE FROM AD_Column WHERE AD_Column_ID=588808
;

-- Column: DD_Order_Candidate.QtyDelivered
-- Column: DD_Order_Candidate.QtyDelivered
-- 2024-07-11T07:14:18.357Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588811
;

-- 2024-07-11T07:14:18.361Z
DELETE FROM AD_Column WHERE AD_Column_ID=588811
;

-- Column: DD_Order_Candidate.QtyReserved
-- Column: DD_Order_Candidate.QtyReserved
-- 2024-07-11T07:14:32.888Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588816
;

-- 2024-07-11T07:14:32.892Z
DELETE FROM AD_Column WHERE AD_Column_ID=588816
;

-- Column: DD_Order_Candidate.ScrappedQty
-- Column: DD_Order_Candidate.ScrappedQty
-- 2024-07-11T07:14:38.913Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588817
;

-- 2024-07-11T07:14:38.917Z
DELETE FROM AD_Column WHERE AD_Column_ID=588817
;

-- Column: DD_Order_Candidate.TargetQty
-- Column: DD_Order_Candidate.TargetQty
-- 2024-07-11T07:14:40.752Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588818
;

-- 2024-07-11T07:14:40.756Z
DELETE FROM AD_Column WHERE AD_Column_ID=588818
;

-- Column: DD_Order_Candidate.User1_ID
-- Column: DD_Order_Candidate.User1_ID
-- 2024-07-11T07:14:45.257Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588819
;

-- 2024-07-11T07:14:45.261Z
DELETE FROM AD_Column WHERE AD_Column_ID=588819
;

-- Column: DD_Order_Candidate.User2_ID
-- Column: DD_Order_Candidate.User2_ID
-- 2024-07-11T07:14:48.024Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588820
;

-- 2024-07-11T07:14:48.028Z
DELETE FROM AD_Column WHERE AD_Column_ID=588820
;

-- Column: DD_Order_Candidate.PickedQty
-- Column: DD_Order_Candidate.PickedQty
-- 2024-07-11T07:15:57.988Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588809
;

-- 2024-07-11T07:15:57.991Z
DELETE FROM AD_Column WHERE AD_Column_ID=588809
;

-- Column: DD_Order_Candidate.Posted
-- Column: DD_Order_Candidate.Posted
-- 2024-07-11T07:16:04.324Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588772
;

-- 2024-07-11T07:16:04.327Z
DELETE FROM AD_Column WHERE AD_Column_ID=588772
;

-- Column: DD_Order_Candidate.FreightAmt
-- Column: DD_Order_Candidate.FreightAmt
-- 2024-07-11T07:16:28.672Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588750
;

-- 2024-07-11T07:16:28.676Z
DELETE FROM AD_Column WHERE AD_Column_ID=588750
;

-- Column: DD_Order_Candidate.FreightCostRule
-- Column: DD_Order_Candidate.FreightCostRule
-- 2024-07-11T07:16:30.713Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588751
;

-- 2024-07-11T07:16:30.717Z
DELETE FROM AD_Column WHERE AD_Column_ID=588751
;

-- Column: DD_Order_Candidate.NoPackages
-- Column: DD_Order_Candidate.NoPackages
-- 2024-07-11T07:16:46.900Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588769
;

-- 2024-07-11T07:16:46.904Z
DELETE FROM AD_Column WHERE AD_Column_ID=588769
;

-- Column: DD_Order_Candidate.QtyInTransit
-- Column: DD_Order_Candidate.QtyInTransit
-- 2024-07-11T07:16:59.437Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588814
;

-- 2024-07-11T07:16:59.442Z
DELETE FROM AD_Column WHERE AD_Column_ID=588814
;

-- Column: DD_Order_Candidate.Ref_Order_ID
-- Column: DD_Order_Candidate.Ref_Order_ID
-- 2024-07-11T07:17:19.813Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588779
;

-- 2024-07-11T07:17:19.816Z
DELETE FROM AD_Column WHERE AD_Column_ID=588779
;

-- Column: DD_Order_Candidate.SalesRep_ID
-- Column: DD_Order_Candidate.SalesRep_ID
-- 2024-07-11T07:17:23.233Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588780
;

-- 2024-07-11T07:17:23.237Z
DELETE FROM AD_Column WHERE AD_Column_ID=588780
;

-- Column: DD_Order_Candidate.C_Campaign_ID
-- Column: DD_Order_Candidate.C_Campaign_ID
-- 2024-07-11T07:17:48.476Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588730
;

-- 2024-07-11T07:17:48.480Z
DELETE FROM AD_Column WHERE AD_Column_ID=588730
;

-- Column: DD_Order_Candidate.C_Activity_ID
-- Column: DD_Order_Candidate.C_Activity_ID
-- 2024-07-11T07:17:53.797Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588727
;

-- 2024-07-11T07:17:53.801Z
DELETE FROM AD_Column WHERE AD_Column_ID=588727
;

-- Column: DD_Order_Candidate.C_Project_ID
-- Column: DD_Order_Candidate.C_Project_ID
-- 2024-07-11T07:18:02.165Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588736
;

-- 2024-07-11T07:18:02.170Z
DELETE FROM AD_Column WHERE AD_Column_ID=588736
;

-- Column: DD_Order_Candidate.DD_NetworkDistribution_ID
-- Column: DD_Order_Candidate.DD_NetworkDistribution_ID
-- 2024-07-11T07:19:07.431Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588821,53340,0,30,542424,'XX','DD_NetworkDistribution_ID',TO_TIMESTAMP('2024-07-11 10:19:07','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Network Distribution',0,0,TO_TIMESTAMP('2024-07-11 10:19:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:19:07.432Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588821 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:19:07.435Z
/* DDL */  select update_Column_Translation_From_AD_Element(53340) 
;

-- Name: DD_NetworkDistributionLine of DD_NetworkDistribution_ID
-- 2024-07-11T07:20:48.112Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540681,'DD_NetworkDistributionLine.DD_NetworkDistribution_ID=@DD_NetworkDistribution_ID@',TO_TIMESTAMP('2024-07-11 10:20:47','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','DD_NetworkDistributionLine of DD_NetworkDistribution_ID','S',TO_TIMESTAMP('2024-07-11 10:20:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: DD_Order_Candidate.DD_NetworkDistributionLine_ID
-- Column: DD_Order_Candidate.DD_NetworkDistributionLine_ID
-- 2024-07-11T07:20:58.589Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588822,53341,0,30,542424,540681,'XX','DD_NetworkDistributionLine_ID',TO_TIMESTAMP('2024-07-11 10:20:58','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Network Distribution Line',0,0,TO_TIMESTAMP('2024-07-11 10:20:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:20:58.591Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588822 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:20:58.594Z
/* DDL */  select update_Column_Translation_From_AD_Element(53341) 
;

-- Column: DD_Order_Candidate.C_UOM_ID
-- Column: DD_Order_Candidate.C_UOM_ID
-- 2024-07-11T07:21:37.853Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588823,215,0,30,542424,'XX','C_UOM_ID',TO_TIMESTAMP('2024-07-11 10:21:37','YYYY-MM-DD HH24:MI:SS'),100,'N','Maßeinheit','EE01',0,10,'Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Maßeinheit',0,0,TO_TIMESTAMP('2024-07-11 10:21:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:21:37.856Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588823 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:21:37.859Z
/* DDL */  select update_Column_Translation_From_AD_Element(215) 
;

-- Column: DD_Order_Candidate.M_Locator_ID
-- Column: DD_Order_Candidate.M_Locator_ID
-- 2024-07-11T07:22:27.287Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588824,448,0,30,542424,127,'XX','M_Locator_ID',TO_TIMESTAMP('2024-07-11 10:22:27','YYYY-MM-DD HH24:MI:SS'),100,'N','Lagerort im Lager','EE01',0,10,'"Lagerort" bezeichnet, wo im Lager ein Produkt aufzufinden ist.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lagerort',0,0,TO_TIMESTAMP('2024-07-11 10:22:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:22:27.290Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588824 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:22:27.293Z
/* DDL */  select update_Column_Translation_From_AD_Element(448) 
;

-- Name: M_Locator of M_WarehouseTo_ID
-- 2024-07-11T07:23:25.753Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540682,'M_Locator.M_Warehouse_ID=@M_WarehouseTo_ID@',TO_TIMESTAMP('2024-07-11 10:23:25','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','M_Locator of M_WarehouseTo_ID','S',TO_TIMESTAMP('2024-07-11 10:23:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: DD_Order_Candidate.M_LocatorTo_ID
-- Column: DD_Order_Candidate.M_LocatorTo_ID
-- 2024-07-11T07:23:40.265Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588825,1029,0,30,542424,540682,'XX','M_LocatorTo_ID',TO_TIMESTAMP('2024-07-11 10:23:39','YYYY-MM-DD HH24:MI:SS'),100,'N','Lagerort, an den Bestand bewegt wird','EE01',0,10,'"Lagerort An" bezeichnet den Lagerort, auf den ein Warenbestand bewegt wird.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lagerort An',0,0,TO_TIMESTAMP('2024-07-11 10:23:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:23:40.267Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588825 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:23:40.269Z
/* DDL */  select update_Column_Translation_From_AD_Element(1029) 
;

-- Column: DD_Order_Candidate.M_WarehouseTo_ID
-- Column: DD_Order_Candidate.M_WarehouseTo_ID
-- 2024-07-11T07:24:07.004Z
UPDATE AD_Column SET AD_Element_ID=543474, ColumnName='M_WarehouseTo_ID', Description=NULL, Help=NULL, IsMandatory='Y', Name='Lager Nach',Updated=TO_TIMESTAMP('2024-07-11 10:24:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588768
;

-- 2024-07-11T07:24:07.020Z
UPDATE AD_Field SET Name='Lager Nach', Description=NULL, Help=NULL WHERE AD_Column_ID=588768
;

-- 2024-07-11T07:24:07.032Z
/* DDL */  select update_Column_Translation_From_AD_Element(543474) 
;

-- Column: DD_Order_Candidate.M_Warehouse_ID
-- Column: DD_Order_Candidate.M_Warehouse_ID
-- 2024-07-11T07:24:49.934Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588767
;

-- 2024-07-11T07:24:49.940Z
DELETE FROM AD_Column WHERE AD_Column_ID=588767
;

-- 2024-07-11T07:27:06.290Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583180,0,'M_LocatorFrom_ID',TO_TIMESTAMP('2024-07-11 10:27:06','YYYY-MM-DD HH24:MI:SS'),100,'','D','','Y','Von Lagerort','Von Lagerort',TO_TIMESTAMP('2024-07-11 10:27:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T07:27:06.292Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583180 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Name: M_Locator of M_Warehouse_From_ID
-- 2024-07-11T07:28:24.564Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540683,'M_Locator.M_Warehouse_ID=@M_Warehouse_From_ID@',TO_TIMESTAMP('2024-07-11 10:28:24','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','M_Locator of M_Warehouse_From_ID','S',TO_TIMESTAMP('2024-07-11 10:28:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: M_Locator of M_Warehouse_From_ID
-- 2024-07-11T07:28:31.067Z
UPDATE AD_Val_Rule SET EntityType='D',Updated=TO_TIMESTAMP('2024-07-11 10:28:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540683
;

-- Column: DD_Order_Candidate.M_LocatorFrom_ID
-- Column: DD_Order_Candidate.M_LocatorFrom_ID
-- 2024-07-11T07:29:22.323Z
UPDATE AD_Column SET AD_Element_ID=583180, AD_Val_Rule_ID=540683, ColumnName='M_LocatorFrom_ID', Description='', Help='', Name='Von Lagerort',Updated=TO_TIMESTAMP('2024-07-11 10:29:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588824
;

-- 2024-07-11T07:29:22.326Z
UPDATE AD_Field SET Name='Von Lagerort', Description='', Help='' WHERE AD_Column_ID=588824
;

-- 2024-07-11T07:29:22.328Z
/* DDL */  select update_Column_Translation_From_AD_Element(583180) 
;

-- Name: M_Locator of M_Warehouse_To_ID
-- 2024-07-11T07:29:35.150Z
UPDATE AD_Val_Rule SET Code='M_Locator.M_Warehouse_ID=@M_Warehouse_To_ID@', Name='M_Locator of M_Warehouse_To_ID',Updated=TO_TIMESTAMP('2024-07-11 10:29:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540682
;

-- Column: DD_Order_Candidate.M_Product_ID
-- Column: DD_Order_Candidate.M_Product_ID
-- 2024-07-11T07:30:12.261Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588826,454,0,30,542424,'XX','M_Product_ID',TO_TIMESTAMP('2024-07-11 10:30:12','YYYY-MM-DD HH24:MI:SS'),100,'N','Produkt, Leistung, Artikel','EE01',0,10,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Produkt',0,0,TO_TIMESTAMP('2024-07-11 10:30:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:30:12.263Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588826 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:30:12.266Z
/* DDL */  select update_Column_Translation_From_AD_Element(454) 
;

-- Column: DD_Order_Candidate.M_Warehouse_From_ID
-- Column: DD_Order_Candidate.M_Warehouse_From_ID
-- 2024-07-11T07:30:23.681Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-07-11 10:30:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588766
;

-- Column: DD_Order_Candidate.PickDate
-- Column: DD_Order_Candidate.PickDate
-- 2024-07-11T07:30:31.208Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588770
;

-- 2024-07-11T07:30:31.211Z
DELETE FROM AD_Column WHERE AD_Column_ID=588770
;

-- Column: DD_Order_Candidate.POReference
-- Column: DD_Order_Candidate.POReference
-- 2024-07-11T07:30:38.060Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588771
;

-- 2024-07-11T07:30:38.064Z
DELETE FROM AD_Column WHERE AD_Column_ID=588771
;

-- 2024-07-11T07:31:32.279Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583181,0,'PP_Plant_To_ID',TO_TIMESTAMP('2024-07-11 10:31:32','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Produktionsstätte','Produktionsstätte',TO_TIMESTAMP('2024-07-11 10:31:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T07:31:32.281Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583181 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: PP_Plant_To_ID
-- 2024-07-11T07:31:57.231Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Produktionsstätte von', PrintName='Produktionsstätte von',Updated=TO_TIMESTAMP('2024-07-11 10:31:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583181 AND AD_Language='de_CH'
;

-- 2024-07-11T07:31:57.233Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583181,'de_CH') 
;

-- Element: PP_Plant_To_ID
-- 2024-07-11T07:32:45.593Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Plant To', PrintName='Plant To',Updated=TO_TIMESTAMP('2024-07-11 10:32:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583181 AND AD_Language='en_US'
;

-- 2024-07-11T07:32:45.596Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583181,'en_US') 
;

-- Element: PP_Plant_To_ID
-- 2024-07-11T07:32:52.390Z
UPDATE AD_Element_Trl SET Name='Produktionsstätte zu', PrintName='Produktionsstätte zu',Updated=TO_TIMESTAMP('2024-07-11 10:32:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583181 AND AD_Language='de_CH'
;

-- 2024-07-11T07:32:52.392Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583181,'de_CH') 
;

-- Element: PP_Plant_To_ID
-- 2024-07-11T07:32:57.486Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Produktionsstätte zu', PrintName='Produktionsstätte zu',Updated=TO_TIMESTAMP('2024-07-11 10:32:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583181 AND AD_Language='de_DE'
;

-- 2024-07-11T07:32:57.489Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583181,'de_DE') 
;

-- 2024-07-11T07:32:57.490Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583181,'de_DE') 
;

-- Column: DD_Order_Candidate.PP_Plant_To_ID
-- Column: DD_Order_Candidate.PP_Plant_To_ID
-- 2024-07-11T07:33:26.548Z
UPDATE AD_Column SET AD_Element_ID=583181, ColumnName='PP_Plant_To_ID', Description=NULL, Help=NULL, Name='Produktionsstätte zu',Updated=TO_TIMESTAMP('2024-07-11 10:33:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588774
;

-- 2024-07-11T07:33:26.549Z
UPDATE AD_Field SET Name='Produktionsstätte zu', Description=NULL, Help=NULL WHERE AD_Column_ID=588774
;

-- 2024-07-11T07:33:26.551Z
/* DDL */  select update_Column_Translation_From_AD_Element(583181) 
;

-- Column: DD_Order_Candidate.Processing
-- Column: DD_Order_Candidate.Processing
-- 2024-07-11T07:33:38.183Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588778
;

-- 2024-07-11T07:33:38.187Z
DELETE FROM AD_Column WHERE AD_Column_ID=588778
;

-- Column: DD_Order_Candidate.M_HU_PI_Item_Product_ID
-- Column: DD_Order_Candidate.M_HU_PI_Item_Product_ID
-- 2024-07-11T07:34:12.810Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Val_Rule_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588827,542132,0,30,542424,540299,'XX','M_HU_PI_Item_Product_ID',TO_TIMESTAMP('2024-07-11 10:34:12','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Packvorschrift',0,0,TO_TIMESTAMP('2024-07-11 10:34:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T07:34:12.812Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588827 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T07:34:12.815Z
/* DDL */  select update_Column_Translation_From_AD_Element(542132) 
;

-- 2024-07-11T09:06:06.800Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583182,0,'Forward_PP_Order_ID',TO_TIMESTAMP('2024-07-11 12:06:06','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Forward Manufacturing Order','Forward Manufacturing Order',TO_TIMESTAMP('2024-07-11 12:06:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T09:06:06.804Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583182 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-07-11T09:06:50.307Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583183,0,'Forward_PP_Order_BOMLine_ID',TO_TIMESTAMP('2024-07-11 12:06:50','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','Forward Manufacturing Order BOM Line','Forward Manufacturing Order BOM Line',TO_TIMESTAMP('2024-07-11 12:06:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T09:06:50.310Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583183 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: DD_Order_Candidate.Forward_PP_Order_ID
-- Column: DD_Order_Candidate.Forward_PP_Order_ID
-- 2024-07-11T09:07:12.459Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588828,583182,0,30,540503,542424,'XX','Forward_PP_Order_ID',TO_TIMESTAMP('2024-07-11 12:07:12','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Forward Manufacturing Order',0,0,TO_TIMESTAMP('2024-07-11 12:07:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T09:07:12.462Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588828 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T09:07:12.465Z
/* DDL */  select update_Column_Translation_From_AD_Element(583182) 
;

-- Column: DD_Order_Candidate.Forward_PP_Order_BOMLine_ID
-- Column: DD_Order_Candidate.Forward_PP_Order_BOMLine_ID
-- 2024-07-11T09:07:51.878Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588829,583183,0,30,541079,542424,'XX','Forward_PP_Order_BOMLine_ID',TO_TIMESTAMP('2024-07-11 12:07:51','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Forward Manufacturing Order BOM Line',0,0,TO_TIMESTAMP('2024-07-11 12:07:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-11T09:07:51.879Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588829 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-11T09:07:51.882Z
/* DDL */  select update_Column_Translation_From_AD_Element(583183) 
;

-- Column: DD_Order_Candidate.Volume
-- Column: DD_Order_Candidate.Volume
-- 2024-07-11T09:08:01.009Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588785
;

-- 2024-07-11T09:08:01.014Z
DELETE FROM AD_Column WHERE AD_Column_ID=588785
;

-- Column: DD_Order_Candidate.Weight
-- Column: DD_Order_Candidate.Weight
-- 2024-07-11T09:08:03.020Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588786
;

-- 2024-07-11T09:08:03.024Z
DELETE FROM AD_Column WHERE AD_Column_ID=588786
;

-- Column: DD_Order_Candidate.IsDropShip
-- Column: DD_Order_Candidate.IsDropShip
-- 2024-07-11T09:08:13.103Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588755
;

-- 2024-07-11T09:08:13.111Z
DELETE FROM AD_Column WHERE AD_Column_ID=588755
;

-- Column: DD_Order_Candidate.C_BPartner_ID
-- Column: DD_Order_Candidate.C_BPartner_ID
-- 2024-07-11T09:08:21.587Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588728
;

-- 2024-07-11T09:08:21.591Z
DELETE FROM AD_Column WHERE AD_Column_ID=588728
;

-- Column: DD_Order_Candidate.C_BPartner_Location_ID
-- Column: DD_Order_Candidate.C_BPartner_Location_ID
-- 2024-07-11T09:08:24.172Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588729
;

-- 2024-07-11T09:08:24.178Z
DELETE FROM AD_Column WHERE AD_Column_ID=588729
;

-- Column: DD_Order_Candidate.C_Order_ID
-- Column: DD_Order_Candidate.C_Order_ID
-- 2024-07-11T09:08:30.166Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588735
;

-- 2024-07-11T09:08:30.169Z
DELETE FROM AD_Column WHERE AD_Column_ID=588735
;

-- Column: DD_Order_Candidate.AD_User_ID
-- Column: DD_Order_Candidate.AD_User_ID
-- 2024-07-11T09:08:47.834Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588725
;

-- 2024-07-11T09:08:47.838Z
DELETE FROM AD_Column WHERE AD_Column_ID=588725
;

-- Column: DD_Order_Candidate.AD_User_Responsible_ID
-- Column: DD_Order_Candidate.AD_User_Responsible_ID
-- 2024-07-11T09:08:50.541Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588726
;

-- 2024-07-11T09:08:50.546Z
DELETE FROM AD_Column WHERE AD_Column_ID=588726
;

-- Column: DD_Order_Candidate.AD_OrgTrx_ID
-- Column: DD_Order_Candidate.AD_OrgTrx_ID
-- 2024-07-11T09:08:55.417Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588724
;

-- 2024-07-11T09:08:55.422Z
DELETE FROM AD_Column WHERE AD_Column_ID=588724
;

-- Column: DD_Order_Candidate.DeliveryRule
-- Column: DD_Order_Candidate.DeliveryRule
-- 2024-07-11T09:09:04.253Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588744
;

-- 2024-07-11T09:09:04.259Z
DELETE FROM AD_Column WHERE AD_Column_ID=588744
;

-- Column: DD_Order_Candidate.DeliveryViaRule
-- Column: DD_Order_Candidate.DeliveryViaRule
-- 2024-07-11T09:09:06.808Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588745
;

-- 2024-07-11T09:09:06.813Z
DELETE FROM AD_Column WHERE AD_Column_ID=588745
;

-- Column: DD_Order_Candidate.Description
-- Column: DD_Order_Candidate.Description
-- 2024-07-11T09:09:11.802Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588746
;

-- 2024-07-11T09:09:11.806Z
DELETE FROM AD_Column WHERE AD_Column_ID=588746
;

-- Column: DD_Order_Candidate.M_Shipper_ID
-- Column: DD_Order_Candidate.M_Shipper_ID
-- 2024-07-11T09:09:17.255Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588765
;

-- 2024-07-11T09:09:17.259Z
DELETE FROM AD_Column WHERE AD_Column_ID=588765
;

-- Column: DD_Order_Candidate.PriorityRule
-- Column: DD_Order_Candidate.PriorityRule
-- 2024-07-11T09:09:23.723Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588776
;

-- 2024-07-11T09:09:23.727Z
DELETE FROM AD_Column WHERE AD_Column_ID=588776
;

-- Column: DD_Order_Candidate.C_DocType_ID
-- Column: DD_Order_Candidate.C_DocType_ID
-- 2024-07-11T09:09:39.929Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588732
;

-- 2024-07-11T09:09:39.933Z
DELETE FROM AD_Column WHERE AD_Column_ID=588732
;

-- Window: Distribution Order Candidate, InternalName=null
-- Window: Distribution Order Candidate, InternalName=null
-- 2024-07-11T09:12:46.887Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,583179,0,541807,TO_TIMESTAMP('2024-07-11 12:12:46','YYYY-MM-DD HH24:MI:SS'),100,'EE01','Y','N','N','N','N','N','N','Y','Distribution Order Candidate','N',TO_TIMESTAMP('2024-07-11 12:12:46','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2024-07-11T09:12:46.891Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541807 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2024-07-11T09:12:46.897Z
/* DDL */  select update_window_translation_from_ad_element(583179) 
;

-- 2024-07-11T09:12:46.913Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541807
;

-- 2024-07-11T09:12:46.919Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541807)
;

-- Tab: Distribution Order Candidate -> Distribution Order Candidate
-- Table: DD_Order_Candidate
-- Tab: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate
-- Table: DD_Order_Candidate
-- 2024-07-11T09:13:05.224Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583179,0,547559,542424,541807,'Y',TO_TIMESTAMP('2024-07-11 12:13:05','YYYY-MM-DD HH24:MI:SS'),100,'EE01','N','N','A','DD_Order_Candidate','Y','N','Y','Y','N','N','N','N','Y','N','N','N','Y','Y','N','N','N',0,'Distribution Order Candidate','N',10,0,TO_TIMESTAMP('2024-07-11 12:13:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T09:13:05.230Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547559 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-07-11T09:13:05.233Z
/* DDL */  select update_tab_translation_from_ad_element(583179) 
;

-- 2024-07-11T09:13:05.238Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547559)
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Mandant
-- Column: DD_Order_Candidate.AD_Client_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Mandant
-- Column: DD_Order_Candidate.AD_Client_ID
-- 2024-07-11T09:13:11.824Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588716,729067,0,547559,TO_TIMESTAMP('2024-07-11 12:13:11','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'EE01','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-07-11 12:13:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T09:13:11.828Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729067 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-11T09:13:11.832Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-07-11T09:13:13.816Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729067
;

-- 2024-07-11T09:13:13.818Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729067)
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Sektion
-- Column: DD_Order_Candidate.AD_Org_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Sektion
-- Column: DD_Order_Candidate.AD_Org_ID
-- 2024-07-11T09:13:13.936Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588717,729068,0,547559,TO_TIMESTAMP('2024-07-11 12:13:13','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'EE01','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2024-07-11 12:13:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T09:13:13.937Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729068 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-11T09:13:13.938Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-07-11T09:13:14.474Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729068
;

-- 2024-07-11T09:13:14.476Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729068)
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Aktiv
-- Column: DD_Order_Candidate.IsActive
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Aktiv
-- Column: DD_Order_Candidate.IsActive
-- 2024-07-11T09:13:14.583Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588720,729069,0,547559,TO_TIMESTAMP('2024-07-11 12:13:14','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'EE01','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-07-11 12:13:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T09:13:14.584Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729069 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-11T09:13:14.586Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-07-11T09:13:14.701Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729069
;

-- 2024-07-11T09:13:14.703Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729069)
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Distribution Order Candidate
-- Column: DD_Order_Candidate.DD_Order_Candidate_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Distribution Order Candidate
-- Column: DD_Order_Candidate.DD_Order_Candidate_ID
-- 2024-07-11T09:13:14.806Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588723,729070,0,547559,TO_TIMESTAMP('2024-07-11 12:13:14','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Distribution Order Candidate',TO_TIMESTAMP('2024-07-11 12:13:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T09:13:14.808Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729070 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-11T09:13:14.809Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583179) 
;

-- 2024-07-11T09:13:14.811Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729070
;

-- 2024-07-11T09:13:14.812Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729070)
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Auftragsdatum
-- Column: DD_Order_Candidate.DateOrdered
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Auftragsdatum
-- Column: DD_Order_Candidate.DateOrdered
-- 2024-07-11T09:13:14.919Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588740,729071,0,547559,TO_TIMESTAMP('2024-07-11 12:13:14','YYYY-MM-DD HH24:MI:SS'),100,'Datum des Auftrags',7,'EE01','Bezeichnet das Datum, an dem die Ware bestellt wurde.','Y','N','N','N','N','N','N','N','Auftragsdatum',TO_TIMESTAMP('2024-07-11 12:13:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T09:13:14.921Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729071 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-11T09:13:14.923Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(268) 
;

-- 2024-07-11T09:13:14.933Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729071
;

-- 2024-07-11T09:13:14.934Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729071)
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Zugesagter Termin
-- Column: DD_Order_Candidate.DatePromised
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Zugesagter Termin
-- Column: DD_Order_Candidate.DatePromised
-- 2024-07-11T09:13:15.039Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588742,729072,0,547559,TO_TIMESTAMP('2024-07-11 12:13:14','YYYY-MM-DD HH24:MI:SS'),100,'Zugesagter Termin für diesen Auftrag',10,'EE01','Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.','Y','N','N','N','N','N','N','N','Zugesagter Termin',TO_TIMESTAMP('2024-07-11 12:13:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T09:13:15.040Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729072 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-11T09:13:15.042Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(269) 
;

-- 2024-07-11T09:13:15.052Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729072
;

-- 2024-07-11T09:13:15.053Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729072)
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Lager ab
-- Column: DD_Order_Candidate.M_Warehouse_From_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Lager ab
-- Column: DD_Order_Candidate.M_Warehouse_From_ID
-- 2024-07-11T09:13:15.166Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588766,729073,0,547559,TO_TIMESTAMP('2024-07-11 12:13:15','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Lager ab',TO_TIMESTAMP('2024-07-11 12:13:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T09:13:15.167Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729073 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-11T09:13:15.168Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577736) 
;

-- 2024-07-11T09:13:15.172Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729073
;

-- 2024-07-11T09:13:15.173Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729073)
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Lager Nach
-- Column: DD_Order_Candidate.M_WarehouseTo_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Lager Nach
-- Column: DD_Order_Candidate.M_WarehouseTo_ID
-- 2024-07-11T09:13:15.276Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588768,729074,0,547559,TO_TIMESTAMP('2024-07-11 12:13:15','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Lager Nach',TO_TIMESTAMP('2024-07-11 12:13:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T09:13:15.278Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729074 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-11T09:13:15.279Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543474) 
;

-- 2024-07-11T09:13:15.283Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729074
;

-- 2024-07-11T09:13:15.283Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729074)
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Produktionsstätte zu
-- Column: DD_Order_Candidate.PP_Plant_To_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Produktionsstätte zu
-- Column: DD_Order_Candidate.PP_Plant_To_ID
-- 2024-07-11T09:13:15.378Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588774,729075,0,547559,TO_TIMESTAMP('2024-07-11 12:13:15','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Produktionsstätte zu',TO_TIMESTAMP('2024-07-11 12:13:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T09:13:15.379Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729075 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-11T09:13:15.381Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583181) 
;

-- 2024-07-11T09:13:15.384Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729075
;

-- 2024-07-11T09:13:15.385Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729075)
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Product Planning
-- Column: DD_Order_Candidate.PP_Product_Planning_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Product Planning
-- Column: DD_Order_Candidate.PP_Product_Planning_ID
-- 2024-07-11T09:13:15.489Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588775,729076,0,547559,TO_TIMESTAMP('2024-07-11 12:13:15','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Product Planning',TO_TIMESTAMP('2024-07-11 12:13:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T09:13:15.491Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729076 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-11T09:13:15.492Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53268) 
;

-- 2024-07-11T09:13:15.499Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729076
;

-- 2024-07-11T09:13:15.500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729076)
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Verarbeitet
-- Column: DD_Order_Candidate.Processed
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Verarbeitet
-- Column: DD_Order_Candidate.Processed
-- 2024-07-11T09:13:15.604Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588777,729077,0,547559,TO_TIMESTAMP('2024-07-11 12:13:15','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'EE01','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2024-07-11 12:13:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T09:13:15.606Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729077 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-11T09:13:15.607Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047) 
;

-- 2024-07-11T09:13:15.627Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729077
;

-- 2024-07-11T09:13:15.628Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729077)
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Produktionsstätte ab
-- Column: DD_Order_Candidate.PP_Plant_From_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Produktionsstätte ab
-- Column: DD_Order_Candidate.PP_Plant_From_ID
-- 2024-07-11T09:13:15.724Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588810,729078,0,547559,TO_TIMESTAMP('2024-07-11 12:13:15','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Produktionsstätte ab',TO_TIMESTAMP('2024-07-11 12:13:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T09:13:15.726Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729078 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-11T09:13:15.728Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542485) 
;

-- 2024-07-11T09:13:15.731Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729078
;

-- 2024-07-11T09:13:15.732Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729078)
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Menge
-- Column: DD_Order_Candidate.QtyEntered
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Menge
-- Column: DD_Order_Candidate.QtyEntered
-- 2024-07-11T09:13:15.832Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588812,729079,0,547559,TO_TIMESTAMP('2024-07-11 12:13:15','YYYY-MM-DD HH24:MI:SS'),100,'Die Eingegebene Menge basiert auf der gewählten Mengeneinheit',22,'EE01','Die Eingegebene Menge wird in die Basismenge zur Produkt-Mengeneinheit umgewandelt','Y','N','N','N','N','N','N','N','Menge',TO_TIMESTAMP('2024-07-11 12:13:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T09:13:15.834Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729079 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-11T09:13:15.836Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2589) 
;

-- 2024-07-11T09:13:15.851Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729079
;

-- 2024-07-11T09:13:15.852Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729079)
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Menge TU
-- Column: DD_Order_Candidate.QtyEnteredTU
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Menge TU
-- Column: DD_Order_Candidate.QtyEnteredTU
-- 2024-07-11T09:13:15.965Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588813,729080,0,547559,TO_TIMESTAMP('2024-07-11 12:13:15','YYYY-MM-DD HH24:MI:SS'),100,22,'EE01','Y','N','N','N','N','N','N','N','Menge TU',TO_TIMESTAMP('2024-07-11 12:13:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T09:13:15.968Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729080 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-11T09:13:15.970Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542397) 
;

-- 2024-07-11T09:13:15.974Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729080
;

-- 2024-07-11T09:13:15.975Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729080)
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Bestellt/ Beauftragt
-- Column: DD_Order_Candidate.QtyOrdered
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Bestellt/ Beauftragt
-- Column: DD_Order_Candidate.QtyOrdered
-- 2024-07-11T09:13:16.078Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588815,729081,0,547559,TO_TIMESTAMP('2024-07-11 12:13:15','YYYY-MM-DD HH24:MI:SS'),100,'Bestellt/ Beauftragt',22,'EE01','Die "Bestellte Menge" bezeichnet die Menge einer Ware, die bestellt wurde.','Y','N','N','N','N','N','N','N','Bestellt/ Beauftragt',TO_TIMESTAMP('2024-07-11 12:13:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T09:13:16.081Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729081 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-11T09:13:16.082Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(531) 
;

-- 2024-07-11T09:13:16.087Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729081
;

-- 2024-07-11T09:13:16.088Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729081)
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Network Distribution
-- Column: DD_Order_Candidate.DD_NetworkDistribution_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Network Distribution
-- Column: DD_Order_Candidate.DD_NetworkDistribution_ID
-- 2024-07-11T09:13:16.184Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588821,729082,0,547559,TO_TIMESTAMP('2024-07-11 12:13:16','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Network Distribution',TO_TIMESTAMP('2024-07-11 12:13:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T09:13:16.185Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729082 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-11T09:13:16.187Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53340) 
;

-- 2024-07-11T09:13:16.189Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729082
;

-- 2024-07-11T09:13:16.190Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729082)
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Network Distribution Line
-- Column: DD_Order_Candidate.DD_NetworkDistributionLine_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Network Distribution Line
-- Column: DD_Order_Candidate.DD_NetworkDistributionLine_ID
-- 2024-07-11T09:13:16.288Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588822,729083,0,547559,TO_TIMESTAMP('2024-07-11 12:13:16','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Network Distribution Line',TO_TIMESTAMP('2024-07-11 12:13:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T09:13:16.289Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729083 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-11T09:13:16.290Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53341) 
;

-- 2024-07-11T09:13:16.293Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729083
;

-- 2024-07-11T09:13:16.294Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729083)
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Maßeinheit
-- Column: DD_Order_Candidate.C_UOM_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Maßeinheit
-- Column: DD_Order_Candidate.C_UOM_ID
-- 2024-07-11T09:13:16.393Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588823,729084,0,547559,TO_TIMESTAMP('2024-07-11 12:13:16','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit',10,'EE01','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','N','N','N','N','N','Maßeinheit',TO_TIMESTAMP('2024-07-11 12:13:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T09:13:16.395Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729084 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-11T09:13:16.397Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215) 
;

-- 2024-07-11T09:13:16.480Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729084
;

-- 2024-07-11T09:13:16.481Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729084)
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Von Lagerort
-- Column: DD_Order_Candidate.M_LocatorFrom_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Von Lagerort
-- Column: DD_Order_Candidate.M_LocatorFrom_ID
-- 2024-07-11T09:13:16.584Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588824,729085,0,547559,TO_TIMESTAMP('2024-07-11 12:13:16','YYYY-MM-DD HH24:MI:SS'),100,'',10,'EE01','','Y','N','N','N','N','N','N','N','Von Lagerort',TO_TIMESTAMP('2024-07-11 12:13:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T09:13:16.585Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729085 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-11T09:13:16.586Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583180) 
;

-- 2024-07-11T09:13:16.589Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729085
;

-- 2024-07-11T09:13:16.589Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729085)
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Lagerort An
-- Column: DD_Order_Candidate.M_LocatorTo_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Lagerort An
-- Column: DD_Order_Candidate.M_LocatorTo_ID
-- 2024-07-11T09:13:16.677Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588825,729086,0,547559,TO_TIMESTAMP('2024-07-11 12:13:16','YYYY-MM-DD HH24:MI:SS'),100,'Lagerort, an den Bestand bewegt wird',10,'EE01','"Lagerort An" bezeichnet den Lagerort, auf den ein Warenbestand bewegt wird.','Y','N','N','N','N','N','N','N','Lagerort An',TO_TIMESTAMP('2024-07-11 12:13:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T09:13:16.679Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729086 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-11T09:13:16.680Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1029) 
;

-- 2024-07-11T09:13:16.683Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729086
;

-- 2024-07-11T09:13:16.684Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729086)
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Produkt
-- Column: DD_Order_Candidate.M_Product_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Produkt
-- Column: DD_Order_Candidate.M_Product_ID
-- 2024-07-11T09:13:16.787Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588826,729087,0,547559,TO_TIMESTAMP('2024-07-11 12:13:16','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',10,'EE01','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','N','N','N','N','N','Produkt',TO_TIMESTAMP('2024-07-11 12:13:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T09:13:16.788Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729087 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-11T09:13:16.789Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2024-07-11T09:13:16.998Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729087
;

-- 2024-07-11T09:13:16.999Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729087)
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Packvorschrift
-- Column: DD_Order_Candidate.M_HU_PI_Item_Product_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Packvorschrift
-- Column: DD_Order_Candidate.M_HU_PI_Item_Product_ID
-- 2024-07-11T09:13:17.114Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588827,729088,0,547559,TO_TIMESTAMP('2024-07-11 12:13:17','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Packvorschrift',TO_TIMESTAMP('2024-07-11 12:13:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T09:13:17.115Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729088 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-11T09:13:17.117Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542132) 
;

-- 2024-07-11T09:13:17.124Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729088
;

-- 2024-07-11T09:13:17.125Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729088)
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Forward Manufacturing Order
-- Column: DD_Order_Candidate.Forward_PP_Order_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Forward Manufacturing Order
-- Column: DD_Order_Candidate.Forward_PP_Order_ID
-- 2024-07-11T09:13:17.221Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588828,729089,0,547559,TO_TIMESTAMP('2024-07-11 12:13:17','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Forward Manufacturing Order',TO_TIMESTAMP('2024-07-11 12:13:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T09:13:17.222Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729089 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-11T09:13:17.224Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583182) 
;

-- 2024-07-11T09:13:17.228Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729089
;

-- 2024-07-11T09:13:17.229Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729089)
;

-- Field: Distribution Order Candidate -> Distribution Order Candidate -> Forward Manufacturing Order BOM Line
-- Column: DD_Order_Candidate.Forward_PP_Order_BOMLine_ID
-- Field: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> Forward Manufacturing Order BOM Line
-- Column: DD_Order_Candidate.Forward_PP_Order_BOMLine_ID
-- 2024-07-11T09:13:17.337Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588829,729090,0,547559,TO_TIMESTAMP('2024-07-11 12:13:17','YYYY-MM-DD HH24:MI:SS'),100,10,'EE01','Y','N','N','N','N','N','N','N','Forward Manufacturing Order BOM Line',TO_TIMESTAMP('2024-07-11 12:13:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T09:13:17.339Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729090 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-11T09:13:17.340Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583183) 
;

-- 2024-07-11T09:13:17.342Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729090
;

-- 2024-07-11T09:13:17.343Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729090)
;

-- Tab: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01)
-- UI Section: main
-- 2024-07-11T09:13:33.316Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547559,546143,TO_TIMESTAMP('2024-07-11 12:13:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-07-11 12:13:33','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2024-07-11T09:13:33.319Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=546143 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main
-- UI Column: 10
-- 2024-07-11T09:13:33.469Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547506,546143,TO_TIMESTAMP('2024-07-11 12:13:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-07-11 12:13:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main
-- UI Column: 20
-- 2024-07-11T09:13:33.582Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547507,546143,TO_TIMESTAMP('2024-07-11 12:13:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2024-07-11 12:13:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 10
-- UI Element Group: default
-- 2024-07-11T09:13:33.714Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547506,551860,TO_TIMESTAMP('2024-07-11 12:13:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2024-07-11 12:13:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Lager ab
-- Column: DD_Order_Candidate.M_Warehouse_From_ID
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 10 -> default.Lager ab
-- Column: DD_Order_Candidate.M_Warehouse_From_ID
-- 2024-07-11T09:15:52.993Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729073,0,547559,551860,624984,'F',TO_TIMESTAMP('2024-07-11 12:15:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lager ab',10,0,0,TO_TIMESTAMP('2024-07-11 12:15:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Von Lagerort
-- Column: DD_Order_Candidate.M_LocatorFrom_ID
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 10 -> default.Von Lagerort
-- Column: DD_Order_Candidate.M_LocatorFrom_ID
-- 2024-07-11T09:16:15.616Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729085,0,547559,551860,624985,'F',TO_TIMESTAMP('2024-07-11 12:16:15','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Von Lagerort',20,0,0,TO_TIMESTAMP('2024-07-11 12:16:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Produktionsstätte ab
-- Column: DD_Order_Candidate.PP_Plant_From_ID
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 10 -> default.Produktionsstätte ab
-- Column: DD_Order_Candidate.PP_Plant_From_ID
-- 2024-07-11T09:16:29.957Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729078,0,547559,551860,624986,'F',TO_TIMESTAMP('2024-07-11 12:16:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Produktionsstätte ab',30,0,0,TO_TIMESTAMP('2024-07-11 12:16:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Lager Nach
-- Column: DD_Order_Candidate.M_WarehouseTo_ID
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 10 -> default.Lager Nach
-- Column: DD_Order_Candidate.M_WarehouseTo_ID
-- 2024-07-11T09:16:48.444Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729074,0,547559,551860,624987,'F',TO_TIMESTAMP('2024-07-11 12:16:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lager Nach',40,0,0,TO_TIMESTAMP('2024-07-11 12:16:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Lagerort An
-- Column: DD_Order_Candidate.M_LocatorTo_ID
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 10 -> default.Lagerort An
-- Column: DD_Order_Candidate.M_LocatorTo_ID
-- 2024-07-11T09:17:02.705Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729086,0,547559,551860,624988,'F',TO_TIMESTAMP('2024-07-11 12:17:02','YYYY-MM-DD HH24:MI:SS'),100,'Lagerort, an den Bestand bewegt wird','"Lagerort An" bezeichnet den Lagerort, auf den ein Warenbestand bewegt wird.','Y','N','Y','N','N','Lagerort An',50,0,0,TO_TIMESTAMP('2024-07-11 12:17:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Produktionsstätte zu
-- Column: DD_Order_Candidate.PP_Plant_To_ID
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 10 -> default.Produktionsstätte zu
-- Column: DD_Order_Candidate.PP_Plant_To_ID
-- 2024-07-11T09:17:13.034Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729075,0,547559,551860,624989,'F',TO_TIMESTAMP('2024-07-11 12:17:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Produktionsstätte zu',60,0,0,TO_TIMESTAMP('2024-07-11 12:17:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 10
-- UI Element Group: product&qty
-- 2024-07-11T09:17:35.800Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547506,551861,TO_TIMESTAMP('2024-07-11 12:17:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','product&qty',20,TO_TIMESTAMP('2024-07-11 12:17:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Produkt
-- Column: DD_Order_Candidate.M_Product_ID
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 10 -> product&qty.Produkt
-- Column: DD_Order_Candidate.M_Product_ID
-- 2024-07-11T09:17:58.135Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729087,0,547559,551861,624990,'F',TO_TIMESTAMP('2024-07-11 12:17:57','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','Produkt',10,0,0,TO_TIMESTAMP('2024-07-11 12:17:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Maßeinheit
-- Column: DD_Order_Candidate.C_UOM_ID
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 10 -> product&qty.Maßeinheit
-- Column: DD_Order_Candidate.C_UOM_ID
-- 2024-07-11T09:18:14.355Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729084,0,547559,551861,624991,'F',TO_TIMESTAMP('2024-07-11 12:18:14','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','N','Maßeinheit',20,0,0,TO_TIMESTAMP('2024-07-11 12:18:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Menge TU
-- Column: DD_Order_Candidate.QtyEnteredTU
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 10 -> product&qty.Menge TU
-- Column: DD_Order_Candidate.QtyEnteredTU
-- 2024-07-11T09:18:33.591Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729080,0,547559,551861,624992,'F',TO_TIMESTAMP('2024-07-11 12:18:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Menge TU',30,0,0,TO_TIMESTAMP('2024-07-11 12:18:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Menge
-- Column: DD_Order_Candidate.QtyEntered
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 10 -> product&qty.Menge
-- Column: DD_Order_Candidate.QtyEntered
-- 2024-07-11T09:18:51.190Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729079,0,547559,551861,624993,'F',TO_TIMESTAMP('2024-07-11 12:18:51','YYYY-MM-DD HH24:MI:SS'),100,'Die Eingegebene Menge basiert auf der gewählten Mengeneinheit','Die Eingegebene Menge wird in die Basismenge zur Produkt-Mengeneinheit umgewandelt','Y','N','Y','N','N','Menge',40,0,0,TO_TIMESTAMP('2024-07-11 12:18:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Bestellt/ Beauftragt
-- Column: DD_Order_Candidate.QtyOrdered
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 10 -> product&qty.Bestellt/ Beauftragt
-- Column: DD_Order_Candidate.QtyOrdered
-- 2024-07-11T09:19:10.815Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729081,0,547559,551861,624994,'F',TO_TIMESTAMP('2024-07-11 12:19:10','YYYY-MM-DD HH24:MI:SS'),100,'Bestellt/ Beauftragt','Die "Bestellte Menge" bezeichnet die Menge einer Ware, die bestellt wurde.','Y','N','Y','N','N','Bestellt/ Beauftragt',50,0,0,TO_TIMESTAMP('2024-07-11 12:19:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 10
-- UI Element Group: dates
-- 2024-07-11T09:19:24.440Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547506,551862,TO_TIMESTAMP('2024-07-11 12:19:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','dates',30,TO_TIMESTAMP('2024-07-11 12:19:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Auftragsdatum
-- Column: DD_Order_Candidate.DateOrdered
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 10 -> dates.Auftragsdatum
-- Column: DD_Order_Candidate.DateOrdered
-- 2024-07-11T09:19:52.286Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729071,0,547559,551862,624995,'F',TO_TIMESTAMP('2024-07-11 12:19:52','YYYY-MM-DD HH24:MI:SS'),100,'Datum des Auftrags','Bezeichnet das Datum, an dem die Ware bestellt wurde.','Y','N','Y','N','N','Auftragsdatum',10,0,0,TO_TIMESTAMP('2024-07-11 12:19:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Zugesagter Termin
-- Column: DD_Order_Candidate.DatePromised
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 10 -> dates.Zugesagter Termin
-- Column: DD_Order_Candidate.DatePromised
-- 2024-07-11T09:20:04.472Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729072,0,547559,551862,624996,'F',TO_TIMESTAMP('2024-07-11 12:20:04','YYYY-MM-DD HH24:MI:SS'),100,'Zugesagter Termin für diesen Auftrag','Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.','Y','N','Y','N','N','Zugesagter Termin',20,0,0,TO_TIMESTAMP('2024-07-11 12:20:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 20
-- UI Element Group: flags
-- 2024-07-11T09:20:19.323Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547507,551863,TO_TIMESTAMP('2024-07-11 12:20:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2024-07-11 12:20:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Verarbeitet
-- Column: DD_Order_Candidate.Processed
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 20 -> flags.Verarbeitet
-- Column: DD_Order_Candidate.Processed
-- 2024-07-11T09:20:40.288Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729077,0,547559,551863,624997,'F',TO_TIMESTAMP('2024-07-11 12:20:40','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','Y','N','N','Verarbeitet',10,0,0,TO_TIMESTAMP('2024-07-11 12:20:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 20
-- UI Element Group: planning
-- 2024-07-11T09:20:48.434Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547507,551864,TO_TIMESTAMP('2024-07-11 12:20:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','planning',20,TO_TIMESTAMP('2024-07-11 12:20:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Product Planning
-- Column: DD_Order_Candidate.PP_Product_Planning_ID
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 20 -> planning.Product Planning
-- Column: DD_Order_Candidate.PP_Product_Planning_ID
-- 2024-07-11T09:21:12.642Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729076,0,547559,551864,624998,'F',TO_TIMESTAMP('2024-07-11 12:21:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Product Planning',10,0,0,TO_TIMESTAMP('2024-07-11 12:21:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Network Distribution
-- Column: DD_Order_Candidate.DD_NetworkDistribution_ID
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 20 -> planning.Network Distribution
-- Column: DD_Order_Candidate.DD_NetworkDistribution_ID
-- 2024-07-11T09:21:18.758Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729082,0,547559,551864,624999,'F',TO_TIMESTAMP('2024-07-11 12:21:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Network Distribution',20,0,0,TO_TIMESTAMP('2024-07-11 12:21:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Network Distribution Line
-- Column: DD_Order_Candidate.DD_NetworkDistributionLine_ID
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 20 -> planning.Network Distribution Line
-- Column: DD_Order_Candidate.DD_NetworkDistributionLine_ID
-- 2024-07-11T09:21:26.677Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729083,0,547559,551864,625000,'F',TO_TIMESTAMP('2024-07-11 12:21:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Network Distribution Line',30,0,0,TO_TIMESTAMP('2024-07-11 12:21:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 20
-- UI Element Group: forward document
-- 2024-07-11T09:21:39.223Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547507,551865,TO_TIMESTAMP('2024-07-11 12:21:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','forward document',30,TO_TIMESTAMP('2024-07-11 12:21:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Forward Manufacturing Order
-- Column: DD_Order_Candidate.Forward_PP_Order_ID
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 20 -> forward document.Forward Manufacturing Order
-- Column: DD_Order_Candidate.Forward_PP_Order_ID
-- 2024-07-11T09:21:53.447Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729089,0,547559,551865,625001,'F',TO_TIMESTAMP('2024-07-11 12:21:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Forward Manufacturing Order',10,0,0,TO_TIMESTAMP('2024-07-11 12:21:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Forward Manufacturing Order BOM Line
-- Column: DD_Order_Candidate.Forward_PP_Order_BOMLine_ID
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 20 -> forward document.Forward Manufacturing Order BOM Line
-- Column: DD_Order_Candidate.Forward_PP_Order_BOMLine_ID
-- 2024-07-11T09:21:59.631Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729090,0,547559,551865,625002,'F',TO_TIMESTAMP('2024-07-11 12:21:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Forward Manufacturing Order BOM Line',20,0,0,TO_TIMESTAMP('2024-07-11 12:21:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 20
-- UI Element Group: org&client
-- 2024-07-11T09:22:12.949Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547507,551866,TO_TIMESTAMP('2024-07-11 12:22:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','org&client',40,TO_TIMESTAMP('2024-07-11 12:22:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Sektion
-- Column: DD_Order_Candidate.AD_Org_ID
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 20 -> org&client.Sektion
-- Column: DD_Order_Candidate.AD_Org_ID
-- 2024-07-11T09:22:22.724Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729068,0,547559,551866,625003,'F',TO_TIMESTAMP('2024-07-11 12:22:22','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2024-07-11 12:22:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Distribution Order Candidate -> Distribution Order Candidate.Mandant
-- Column: DD_Order_Candidate.AD_Client_ID
-- UI Element: Distribution Order Candidate(541807,EE01) -> Distribution Order Candidate(547559,EE01) -> main -> 20 -> org&client.Mandant
-- Column: DD_Order_Candidate.AD_Client_ID
-- 2024-07-11T09:22:29.891Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729067,0,547559,551866,625004,'F',TO_TIMESTAMP('2024-07-11 12:22:28','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2024-07-11 12:22:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T09:24:08.200Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,729088,0,541813,624990,TO_TIMESTAMP('2024-07-11 12:24:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,'widget',TO_TIMESTAMP('2024-07-11 12:24:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Window: Distribution Order Candidate, InternalName=ddOrderCandidate
-- Window: Distribution Order Candidate, InternalName=ddOrderCandidate
-- 2024-07-11T09:25:25.084Z
UPDATE AD_Window SET InternalName='ddOrderCandidate',Updated=TO_TIMESTAMP('2024-07-11 12:25:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541807
;

-- Name: Distribution Order Candidate
-- Action Type: W
-- Window: Distribution Order Candidate(541807,EE01)
-- 2024-07-11T09:25:38.360Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,583179,542164,0,541807,TO_TIMESTAMP('2024-07-11 12:25:38','YYYY-MM-DD HH24:MI:SS'),100,'EE01','ddOrderCandidate','Y','N','N','N','N','Distribution Order Candidate',TO_TIMESTAMP('2024-07-11 12:25:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-11T09:25:38.363Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542164 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2024-07-11T09:25:38.367Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542164, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542164)
;

-- 2024-07-11T09:25:38.378Z
/* DDL */  select update_menu_translation_from_ad_element(583179) 
;

-- Reordering children of `Logistics`
-- Node name: `Tour (M_Tour)`
-- 2024-07-11T09:25:38.974Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- Node name: `Tourversion (M_TourVersion)`
-- 2024-07-11T09:25:38.985Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- Node name: `Delivery Days (M_DeliveryDay)`
-- 2024-07-11T09:25:38.986Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- Node name: `Distribution Order (DD_Order)`
-- 2024-07-11T09:25:38.987Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- Node name: `Distributions Editor (DD_OrderLine)`
-- 2024-07-11T09:25:38.988Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction (M_HU_PI)`
-- 2024-07-11T09:25:38.989Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction Version (M_HU_PI_Version)`
-- 2024-07-11T09:25:38.989Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- Node name: `CU-TU Allocation (M_HU_PI_Item_Product)`
-- 2024-07-11T09:25:38.990Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541375 AND AD_Tree_ID=10
;

-- Node name: `Packing Material (M_HU_PackingMaterial)`
-- 2024-07-11T09:25:38.991Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit (M_HU)`
-- 2024-07-11T09:25:38.991Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- Node name: `Packaging code (M_HU_PackagingCode)`
-- 2024-07-11T09:25:38.992Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541384 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Transaction (M_HU_Trx_Line)`
-- 2024-07-11T09:25:38.993Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Trace (M_HU_Trace)`
-- 2024-07-11T09:25:38.994Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- Node name: `Transport Disposition (M_Tour_Instance)`
-- 2024-07-11T09:25:38.995Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- Node name: `Transport Delivery (M_DeliveryDay_Alloc)`
-- 2024-07-11T09:25:38.995Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- Node name: `Material Transactions (M_Transaction)`
-- 2024-07-11T09:25:38.996Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- Node name: `Transportation Order (M_ShipperTransportation)`
-- 2024-07-11T09:25:38.996Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- Node name: `Package (M_Package)`
-- 2024-07-11T09:25:38.997Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- Node name: `Internal Use (M_Inventory)`
-- 2024-07-11T09:25:38.998Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- Node name: `GO! Delivery Orders (GO_DeliveryOrder)`
-- 2024-07-11T09:25:38.999Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- Node name: `Der Kurier Delivery Orders (DerKurier_DeliveryOrder)`
-- 2024-07-11T09:25:38.999Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541083 AND AD_Tree_ID=10
;

-- Node name: `DHL Delivery Order (DHL_ShipmentOrder)`
-- 2024-07-11T09:25:39Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541388 AND AD_Tree_ID=10
;

-- Node name: `DPD Delivery Order (DPD_StoreOrder)`
-- 2024-07-11T09:25:39.001Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541394 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2024-07-11T09:25:39.001Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2024-07-11T09:25:39.002Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2024-07-11T09:25:39.003Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- Node name: `HU Reservierung (M_HU_Reservation)`
-- 2024-07-11T09:25:39.003Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541117 AND AD_Tree_ID=10
;

-- Node name: `Service Handling Units (M_HU)`
-- 2024-07-11T09:25:39.004Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541572 AND AD_Tree_ID=10
;

-- Node name: `HU QR Code (M_HU_QRCode)`
-- 2024-07-11T09:25:39.005Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541905 AND AD_Tree_ID=10
;

-- Node name: `Generate new HU QR Codes (de.metas.handlingunits.process.GenerateHUQRCodes)`
-- 2024-07-11T09:25:39.006Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542152 AND AD_Tree_ID=10
;

-- Node name: `Distribution Order Candidate`
-- 2024-07-11T09:25:39.007Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542164 AND AD_Tree_ID=10
;

-- Column: DD_Order_Candidate.M_LocatorFrom_ID
-- Column: DD_Order_Candidate.M_LocatorFrom_ID
-- 2024-07-11T09:26:46.784Z
UPDATE AD_Column SET AD_Reference_Value_ID=191, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2024-07-11 12:26:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588824
;

-- Column: DD_Order_Candidate.M_LocatorTo_ID
-- Column: DD_Order_Candidate.M_LocatorTo_ID
-- 2024-07-11T09:26:51.409Z
UPDATE AD_Column SET AD_Reference_Value_ID=191, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2024-07-11 12:26:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588825
;

