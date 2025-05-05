-- Table: I_DeliveryPlanning
-- 2023-02-02T13:29:47.353Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542290,'N',TO_TIMESTAMP('2023-02-02 15:29:47','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Delivery Planning','NP','L','I_DeliveryPlanning','DTI',TO_TIMESTAMP('2023-02-02 15:29:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-02T13:29:47.356Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542290 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-02-02T13:29:47.465Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556203,TO_TIMESTAMP('2023-02-02 15:29:47','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table I_DeliveryPlanning',1,'Y','N','Y','Y','I_DeliveryPlanning','N',1000000,TO_TIMESTAMP('2023-02-02 15:29:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T13:29:47.473Z
CREATE SEQUENCE I_DELIVERYPLANNING_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2023-02-02T13:29:58.455Z
UPDATE AD_Table_Trl SET Name='Lieferplanung Import',Updated=TO_TIMESTAMP('2023-02-02 15:29:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=542290
;

-- 2023-02-02T13:30:01.306Z
UPDATE AD_Table_Trl SET Name='Lieferplanung Import',Updated=TO_TIMESTAMP('2023-02-02 15:30:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Table_ID=542290
;

-- 2023-02-02T13:30:02.417Z
UPDATE AD_Table_Trl SET Name='Lieferplanung Import',Updated=TO_TIMESTAMP('2023-02-02 15:30:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=542290
;

-- 2023-02-02T13:30:02.418Z
UPDATE AD_Table SET Name='Lieferplanung Import' WHERE AD_Table_ID=542290
;

-- 2023-02-02T13:30:09.752Z
UPDATE AD_Table_Trl SET Name='Lieferplanung Import',Updated=TO_TIMESTAMP('2023-02-02 15:30:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Table_ID=542290
;

-- Column: I_DeliveryPlanning.AD_Client_ID
-- 2023-02-02T13:31:08.078Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585742,102,0,19,542290,'AD_Client_ID',TO_TIMESTAMP('2023-02-02 15:31:07','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2023-02-02 15:31:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-02T13:31:08.080Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585742 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-02T13:31:08.108Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: I_DeliveryPlanning.AD_Org_ID
-- 2023-02-02T13:31:08.790Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585743,113,0,30,542290,'AD_Org_ID',TO_TIMESTAMP('2023-02-02 15:31:08','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2023-02-02 15:31:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-02T13:31:08.792Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585743 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-02T13:31:08.794Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- 2023-02-02T13:31:09.276Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581998,0,'I_DeliveryPlanning_ID',TO_TIMESTAMP('2023-02-02 15:31:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Lieferplanung Import','Lieferplanung Import',TO_TIMESTAMP('2023-02-02 15:31:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T13:31:09.278Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581998 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: I_DeliveryPlanning.I_DeliveryPlanning_ID
-- 2023-02-02T13:31:09.718Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585744,581998,0,13,542290,'I_DeliveryPlanning_ID',TO_TIMESTAMP('2023-02-02 15:31:09','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','Y','Y','Y','N','N','N','N','Y','N','N','Lieferplanung Import',0,30,TO_TIMESTAMP('2023-02-02 15:31:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-02T13:31:09.719Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585744 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-02T13:31:09.721Z
/* DDL */  select update_Column_Translation_From_AD_Element(581998) 
;

-- Column: I_DeliveryPlanning.Created
-- 2023-02-02T13:31:10.174Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585745,245,0,16,542290,'Created',TO_TIMESTAMP('2023-02-02 15:31:09','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2023-02-02 15:31:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-02T13:31:10.175Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585745 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-02T13:31:10.177Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: I_DeliveryPlanning.CreatedBy
-- 2023-02-02T13:31:10.734Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585746,246,0,18,110,542290,'CreatedBy',TO_TIMESTAMP('2023-02-02 15:31:10','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2023-02-02 15:31:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-02T13:31:10.736Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585746 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-02T13:31:10.737Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: I_DeliveryPlanning.FileName
-- 2023-02-02T13:31:11.295Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585747,2295,0,10,542290,'FileName',TO_TIMESTAMP('2023-02-02 15:31:11','YYYY-MM-DD HH24:MI:SS'),100,'N','Name of the local file or URL','D',0,1024,'E','Name of a file in the local directory space - or URL (file://.., http://.., ftp://..)','Y','Y','N','N','N','N','N','Y','N','N','N','Y','N','N','N','N','Y','Dateiname',0,10,TO_TIMESTAMP('2023-02-02 15:31:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-02T13:31:11.296Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585747 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-02T13:31:11.297Z
/* DDL */  select update_Column_Translation_From_AD_Element(2295) 
;

-- Column: I_DeliveryPlanning.Imported
-- 2023-02-02T13:31:11.808Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585748,581560,0,16,542290,'Imported',TO_TIMESTAMP('2023-02-02 15:31:11','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'B','Y','Y','N','N','N','N','N','Y','N','N','N','Y','N','N','N','N','Y','Import-Zeitpunkt',0,20,TO_TIMESTAMP('2023-02-02 15:31:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-02T13:31:11.809Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585748 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-02T13:31:11.811Z
/* DDL */  select update_Column_Translation_From_AD_Element(581560) 
;

-- Column: I_DeliveryPlanning.IsActive
-- 2023-02-02T13:31:12.270Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585749,348,0,20,542290,'IsActive',TO_TIMESTAMP('2023-02-02 15:31:12','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2023-02-02 15:31:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-02T13:31:12.271Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585749 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-02T13:31:12.273Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: I_DeliveryPlanning.IsMatchAmounts
-- 2023-02-02T13:31:12.851Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585750,581588,0,20,542290,'IsMatchAmounts',TO_TIMESTAMP('2023-02-02 15:31:12','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','Wenn diese Option gesetzt ist, berücksichtigt der Importprozess bei der Suche nach entsprechenden Rechnungen auch den offenen Rechnungsbetrag.','D',0,1,'Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','Beträge abgleichen',0,0,TO_TIMESTAMP('2023-02-02 15:31:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-02T13:31:12.853Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585750 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-02T13:31:12.855Z
/* DDL */  select update_Column_Translation_From_AD_Element(581588) 
;

-- Column: I_DeliveryPlanning.Processed
-- 2023-02-02T13:31:13.339Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585751,1047,0,20,542290,'Processed',TO_TIMESTAMP('2023-02-02 15:31:13','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','D',0,1,'Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','Verarbeitet',0,0,TO_TIMESTAMP('2023-02-02 15:31:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-02T13:31:13.340Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585751 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-02T13:31:13.342Z
/* DDL */  select update_Column_Translation_From_AD_Element(1047) 
;

-- Column: I_DeliveryPlanning.Updated
-- 2023-02-02T13:31:13.828Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585752,607,0,16,542290,'Updated',TO_TIMESTAMP('2023-02-02 15:31:13','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2023-02-02 15:31:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-02T13:31:13.830Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585752 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-02T13:31:13.832Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: I_DeliveryPlanning.UpdatedBy
-- 2023-02-02T13:31:14.427Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585753,608,0,18,110,542290,'UpdatedBy',TO_TIMESTAMP('2023-02-02 15:31:14','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2023-02-02 15:31:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-02T13:31:14.428Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585753 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-02T13:31:14.430Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- Column: I_DeliveryPlanning.IsMatchAmounts
-- 2023-02-02T13:31:25.080Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=585750
;

-- 2023-02-02T13:31:25.084Z
DELETE FROM AD_Column WHERE AD_Column_ID=585750
;

-- Column: I_DeliveryPlanning.M_SectionCode_ID
-- 2023-02-02T13:32:05.282Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585754,581238,0,19,542290,'M_SectionCode_ID',TO_TIMESTAMP('2023-02-02 15:32:05','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Sektionskennung',0,0,TO_TIMESTAMP('2023-02-02 15:32:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-02T13:32:05.284Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585754 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-02T13:32:05.286Z
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;


-- Table: I_DeliveryPlanning_Data
-- 2023-02-02T20:38:51.595Z
UPDATE AD_Table SET TableName='I_DeliveryPlanning_Data',Updated=TO_TIMESTAMP('2023-02-02 22:38:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542290
;

-- 2023-02-02T20:38:51.615Z
UPDATE AD_Sequence SET Name='I_DeliveryPlanning_Data',Updated=TO_TIMESTAMP('2023-02-02 22:38:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=556203
;

-- 2023-02-02T20:38:51.619Z
ALTER SEQUENCE I_DeliveryPlanning_SEQ RENAME TO I_DeliveryPlanning_Data_SEQ
;

-- 2023-02-02T20:39:19.482Z
UPDATE AD_Element SET ColumnName='I_DeliveryPlanning_Data_ID',Updated=TO_TIMESTAMP('2023-02-02 22:39:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581998
;

-- 2023-02-02T20:39:19.483Z
UPDATE AD_Column SET ColumnName='I_DeliveryPlanning_Data_ID' WHERE AD_Element_ID=581998
;

-- 2023-02-02T20:39:19.484Z
UPDATE AD_Process_Para SET ColumnName='I_DeliveryPlanning_Data_ID' WHERE AD_Element_ID=581998
;

-- 2023-02-02T20:39:19.511Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581998,'de_DE') 
;

-- 2023-02-02T20:39:28.182Z
/* DDL */ CREATE TABLE public.I_DeliveryPlanning_Data (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, FileName VARCHAR(1024), I_DeliveryPlanning_Data_ID NUMERIC(10) NOT NULL, Imported TIMESTAMP WITH TIME ZONE, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_SectionCode_ID NUMERIC(10) NOT NULL, Processed CHAR(1) DEFAULT 'N' CHECK (Processed IN ('Y','N')), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT I_DeliveryPlanning_Data_Key PRIMARY KEY (I_DeliveryPlanning_Data_ID), CONSTRAINT MSectionCode_IDeliveryPlanningData FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED)
;

-- 2023-02-02T20:52:59.649Z
UPDATE AD_Table_Trl SET Name='Lieferplanung Datenimport',Updated=TO_TIMESTAMP('2023-02-02 22:52:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=542290
;

-- 2023-02-02T20:53:00.866Z
UPDATE AD_Table_Trl SET Name='Lieferplanung Datenimport',Updated=TO_TIMESTAMP('2023-02-02 22:53:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Table_ID=542290
;

-- 2023-02-02T20:53:02.578Z
UPDATE AD_Table_Trl SET Name='Lieferplanung Datenimport',Updated=TO_TIMESTAMP('2023-02-02 22:53:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=542290
;

-- 2023-02-02T20:53:02.579Z
UPDATE AD_Table SET Name='Lieferplanung Datenimport' WHERE AD_Table_ID=542290
;

-- 2023-02-02T20:53:09.724Z
UPDATE AD_Table_Trl SET Name='Lieferplanung Datenimport',Updated=TO_TIMESTAMP('2023-02-02 22:53:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Table_ID=542290
;

-- 2023-02-02T20:53:12.716Z
UPDATE AD_Table_Trl SET Name='Delivery Planning Data Import',Updated=TO_TIMESTAMP('2023-02-02 22:53:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=542290
;

-- Column: I_DeliveryPlanning_Data.M_SectionCode_ID
-- 2023-02-03T13:07:14.561Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2023-02-03 15:07:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585754
;

-- 2023-02-03T13:07:17.767Z
INSERT INTO t_alter_column values('i_deliveryplanning_data','M_SectionCode_ID',null,'NULL',null)
;

-- Column: I_DeliveryPlanning_Data.IsReadyForProcessing
-- 2023-02-03T13:48:05.902Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585842,541909,0,20,542290,'IsReadyForProcessing',TO_TIMESTAMP('2023-02-03 15:48:05','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Zeigt an, ob das Zusammentstellen eines Arbeitspakets abgeschlossen ist.','de.metas.async',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Bereit zur Verarbeitung',0,0,TO_TIMESTAMP('2023-02-03 15:48:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-03T13:48:05.903Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585842 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-03T13:48:05.905Z
/* DDL */  select update_Column_Translation_From_AD_Element(541909) 
;

-- 2023-02-03T13:48:11.812Z
/* DDL */ SELECT public.db_alter_table('I_DeliveryPlanning_Data','ALTER TABLE public.I_DeliveryPlanning_Data ADD COLUMN IsReadyForProcessing CHAR(1) DEFAULT ''N'' CHECK (IsReadyForProcessing IN (''Y'',''N'')) NOT NULL')
;

-- 2023-02-03T13:51:47.004Z
INSERT INTO t_alter_column values('i_deliveryplanning_data','IsReadyForProcessing','CHAR(1)',null,'N')
;

-- 2023-02-03T13:51:47.009Z
UPDATE I_DeliveryPlanning_Data SET IsReadyForProcessing='N' WHERE IsReadyForProcessing IS NULL
;

-- Column: I_DeliveryPlanning_Data.IsReadyForProcessing
-- 2023-02-03T13:53:17.906Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2023-02-03 15:53:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585842
;
