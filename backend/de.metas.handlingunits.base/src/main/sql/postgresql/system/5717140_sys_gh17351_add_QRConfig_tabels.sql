-- Table: M_HU_QRCode_Assignmnet
-- Table: M_HU_QRCode_Assignmnet
-- 2024-02-14T13:11:10.239Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542392,'N',TO_TIMESTAMP('2024-02-14 15:11:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','N','Y','N','N','Y','N','N','N','N','N',0,'HU QR Code Assignment','NP','L','M_HU_QRCode_Assignmnet','DTI',TO_TIMESTAMP('2024-02-14 15:11:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:11:10.243Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542392 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-02-14T13:11:10.424Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556328,TO_TIMESTAMP('2024-02-14 15:11:10','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table M_HU_QRCode_Assignmnet',1,'Y','N','Y','Y','M_HU_QRCode_Assignmnet','N',1000000,TO_TIMESTAMP('2024-02-14 15:11:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-14T13:11:10.445Z
CREATE SEQUENCE M_HU_QRCODE_ASSIGNMNET_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: M_HU_QRCode_Assignmnet.AD_Client_ID
-- Column: M_HU_QRCode_Assignmnet.AD_Client_ID
-- 2024-02-14T13:11:32.963Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587905,102,0,19,542392,'AD_Client_ID',TO_TIMESTAMP('2024-02-14 15:11:32','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','de.metas.handlingunits',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-02-14 15:11:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:11:32.968Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587905 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-14T13:11:32.974Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: M_HU_QRCode_Assignmnet.AD_Org_ID
-- Column: M_HU_QRCode_Assignmnet.AD_Org_ID
-- 2024-02-14T13:11:34.472Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587906,113,0,30,542392,'AD_Org_ID',TO_TIMESTAMP('2024-02-14 15:11:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','de.metas.handlingunits',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2024-02-14 15:11:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:11:34.474Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587906 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-14T13:11:34.476Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: M_HU_QRCode_Assignmnet.Created
-- Column: M_HU_QRCode_Assignmnet.Created
-- 2024-02-14T13:11:35.385Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587907,245,0,16,542392,'Created',TO_TIMESTAMP('2024-02-14 15:11:35','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.handlingunits',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-02-14 15:11:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:11:35.387Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587907 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-14T13:11:35.389Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: M_HU_QRCode_Assignmnet.CreatedBy
-- Column: M_HU_QRCode_Assignmnet.CreatedBy
-- 2024-02-14T13:11:36.422Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587908,246,0,18,110,542392,'CreatedBy',TO_TIMESTAMP('2024-02-14 15:11:36','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.handlingunits',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-02-14 15:11:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:11:36.423Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587908 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-14T13:11:36.426Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: M_HU_QRCode_Assignmnet.IsActive
-- Column: M_HU_QRCode_Assignmnet.IsActive
-- 2024-02-14T13:11:37.506Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587909,348,0,20,542392,'IsActive',TO_TIMESTAMP('2024-02-14 15:11:37','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','de.metas.handlingunits',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-02-14 15:11:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:11:37.508Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587909 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-14T13:11:37.510Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: M_HU_QRCode_Assignmnet.Updated
-- Column: M_HU_QRCode_Assignmnet.Updated
-- 2024-02-14T13:11:38.499Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587910,607,0,16,542392,'Updated',TO_TIMESTAMP('2024-02-14 15:11:38','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.handlingunits',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-02-14 15:11:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:11:38.501Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587910 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-14T13:11:38.503Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: M_HU_QRCode_Assignmnet.UpdatedBy
-- Column: M_HU_QRCode_Assignmnet.UpdatedBy
-- 2024-02-14T13:11:39.469Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587911,608,0,18,110,542392,'UpdatedBy',TO_TIMESTAMP('2024-02-14 15:11:39','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.handlingunits',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-02-14 15:11:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:11:39.470Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587911 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-14T13:11:39.472Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2024-02-14T13:11:40.090Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582964,0,'M_HU_QRCode_Assignmnet_ID',TO_TIMESTAMP('2024-02-14 15:11:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','HU QR Code Assignment','HU QR Code Assignment',TO_TIMESTAMP('2024-02-14 15:11:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-14T13:11:40.095Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582964 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_HU_QRCode_Assignmnet.M_HU_QRCode_Assignmnet_ID
-- Column: M_HU_QRCode_Assignmnet.M_HU_QRCode_Assignmnet_ID
-- 2024-02-14T13:11:40.784Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587912,582964,0,13,542392,'M_HU_QRCode_Assignmnet_ID',TO_TIMESTAMP('2024-02-14 15:11:40','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','HU QR Code Assignment',0,0,TO_TIMESTAMP('2024-02-14 15:11:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:11:40.787Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587912 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-14T13:11:40.789Z
/* DDL */  select update_Column_Translation_From_AD_Element(582964) 
;

-- Column: M_HU_QRCode_Assignmnet.M_HU_QRCode_ID
-- Column: M_HU_QRCode_Assignmnet.M_HU_QRCode_ID
-- 2024-02-14T13:12:21.006Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587913,580520,0,19,542392,'M_HU_QRCode_ID',TO_TIMESTAMP('2024-02-14 15:12:20','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'HU QR Code',0,0,TO_TIMESTAMP('2024-02-14 15:12:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:12:21.010Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587913 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-14T13:12:21.015Z
/* DDL */  select update_Column_Translation_From_AD_Element(580520) 
;

-- Column: M_HU_QRCode_Assignmnet.M_HU_ID
-- Column: M_HU_QRCode_Assignmnet.M_HU_ID
-- 2024-02-14T13:13:01.652Z
UPDATE AD_Column SET AD_Element_ID=542140, ColumnName='M_HU_ID', Description=NULL, Help=NULL, Name='Handling Unit',Updated=TO_TIMESTAMP('2024-02-14 15:13:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587913
;

-- 2024-02-14T13:13:01.662Z
UPDATE AD_Field SET Name='Handling Unit', Description=NULL, Help=NULL WHERE AD_Column_ID=587913
;

-- 2024-02-14T13:13:01.666Z
/* DDL */  select update_Column_Translation_From_AD_Element(542140) 
;

-- 2024-02-14T13:13:11.311Z
/* DDL */ CREATE TABLE public.M_HU_QRCode_Assignmnet (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_HU_ID NUMERIC(10) NOT NULL, M_HU_QRCode_Assignmnet_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT MHU_MHUQRCodeAssignmnet FOREIGN KEY (M_HU_ID) REFERENCES public.M_HU DEFERRABLE INITIALLY DEFERRED, CONSTRAINT M_HU_QRCode_Assignmnet_Key PRIMARY KEY (M_HU_QRCode_Assignmnet_ID))
;

-- Table: QRCode_Configuration
-- Table: QRCode_Configuration
-- 2024-02-14T13:14:14.906Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542393,'N',TO_TIMESTAMP('2024-02-14 15:14:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','N','Y','N','N','Y','N','N','N','N','N',0,'QR Code Configuration','NP','L','QRCode_Configuration','DTI',TO_TIMESTAMP('2024-02-14 15:14:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:14:14.914Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542393 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-02-14T13:14:15.022Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556329,TO_TIMESTAMP('2024-02-14 15:14:14','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table QRCode_Configuration',1,'Y','N','Y','Y','QRCode_Configuration','N',1000000,TO_TIMESTAMP('2024-02-14 15:14:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-14T13:14:15.048Z
CREATE SEQUENCE QRCODE_CONFIGURATION_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: QRCode_Configuration.AD_Client_ID
-- Column: QRCode_Configuration.AD_Client_ID
-- 2024-02-14T13:15:03.524Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587914,102,0,19,542393,'AD_Client_ID',TO_TIMESTAMP('2024-02-14 15:15:03','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','de.metas.handlingunits',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-02-14 15:15:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:15:03.525Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587914 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-14T13:15:03.527Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: QRCode_Configuration.AD_Org_ID
-- Column: QRCode_Configuration.AD_Org_ID
-- 2024-02-14T13:15:04.298Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587915,113,0,30,542393,'AD_Org_ID',TO_TIMESTAMP('2024-02-14 15:15:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','de.metas.handlingunits',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2024-02-14 15:15:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:15:04.301Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587915 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-14T13:15:04.307Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: QRCode_Configuration.Created
-- Column: QRCode_Configuration.Created
-- 2024-02-14T13:15:05.088Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587916,245,0,16,542393,'Created',TO_TIMESTAMP('2024-02-14 15:15:04','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.handlingunits',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-02-14 15:15:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:15:05.091Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587916 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-14T13:15:05.095Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: QRCode_Configuration.CreatedBy
-- Column: QRCode_Configuration.CreatedBy
-- 2024-02-14T13:15:05.889Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587917,246,0,18,110,542393,'CreatedBy',TO_TIMESTAMP('2024-02-14 15:15:05','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.handlingunits',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-02-14 15:15:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:15:05.891Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587917 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-14T13:15:05.895Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: QRCode_Configuration.IsActive
-- Column: QRCode_Configuration.IsActive
-- 2024-02-14T13:15:06.637Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587918,348,0,20,542393,'IsActive',TO_TIMESTAMP('2024-02-14 15:15:06','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','de.metas.handlingunits',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-02-14 15:15:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:15:06.641Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587918 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-14T13:15:06.647Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: QRCode_Configuration.Updated
-- Column: QRCode_Configuration.Updated
-- 2024-02-14T13:15:07.521Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587919,607,0,16,542393,'Updated',TO_TIMESTAMP('2024-02-14 15:15:07','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.handlingunits',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-02-14 15:15:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:15:07.525Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587919 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-14T13:15:07.531Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: QRCode_Configuration.UpdatedBy
-- Column: QRCode_Configuration.UpdatedBy
-- 2024-02-14T13:15:08.283Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587920,608,0,18,110,542393,'UpdatedBy',TO_TIMESTAMP('2024-02-14 15:15:08','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.handlingunits',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-02-14 15:15:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:15:08.285Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587920 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-14T13:15:08.289Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2024-02-14T13:15:08.878Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582965,0,'QRCode_Configuration_ID',TO_TIMESTAMP('2024-02-14 15:15:08','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','QR Code Configuration','QR Code Configuration',TO_TIMESTAMP('2024-02-14 15:15:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-14T13:15:08.881Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582965 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: QRCode_Configuration.QRCode_Configuration_ID
-- Column: QRCode_Configuration.QRCode_Configuration_ID
-- 2024-02-14T13:15:09.551Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587921,582965,0,13,542393,'QRCode_Configuration_ID',TO_TIMESTAMP('2024-02-14 15:15:08','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','QR Code Configuration',0,0,TO_TIMESTAMP('2024-02-14 15:15:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:15:09.555Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587921 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-14T13:15:09.560Z
/* DDL */  select update_Column_Translation_From_AD_Element(582965) 
;

-- 2024-02-14T13:16:46.412Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582966,0,'IsOneQRCodeForAggregatedHUs',TO_TIMESTAMP('2024-02-14 15:16:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','One QR-Code for aggregated HUs','One QR-Code for aggregated HUs',TO_TIMESTAMP('2024-02-14 15:16:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-14T13:16:46.423Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582966 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-02-14T13:17:22.767Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582967,0,'IsOneQRCodeForMatchingAttributes',TO_TIMESTAMP('2024-02-14 15:17:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','One QR-Code for matching attributes','One QR-Code for matching attributes',TO_TIMESTAMP('2024-02-14 15:17:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-14T13:17:22.769Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582967 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: QRCode_Configuration.IsOneQRCodeForAggregatedHUs
-- Column: QRCode_Configuration.IsOneQRCodeForAggregatedHUs
-- 2024-02-14T13:17:50.872Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587922,582966,0,20,542393,'IsOneQRCodeForAggregatedHUs',TO_TIMESTAMP('2024-02-14 15:17:50','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.handlingunits',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'One QR-Code for aggregated HUs',0,0,TO_TIMESTAMP('2024-02-14 15:17:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:17:50.882Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587922 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-14T13:17:50.890Z
/* DDL */  select update_Column_Translation_From_AD_Element(582966) 
;

-- Column: QRCode_Configuration.IsOneQRCodeForMatchingAttributes
-- Column: QRCode_Configuration.IsOneQRCodeForMatchingAttributes
-- 2024-02-14T13:18:01.750Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587923,582967,0,20,542393,'IsOneQRCodeForMatchingAttributes',TO_TIMESTAMP('2024-02-14 15:18:01','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.handlingunits',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'One QR-Code for matching attributes',0,0,TO_TIMESTAMP('2024-02-14 15:18:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:18:01.763Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587923 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-14T13:18:01.773Z
/* DDL */  select update_Column_Translation_From_AD_Element(582967) 
;

-- 2024-02-14T13:18:09.366Z
/* DDL */ CREATE TABLE public.QRCode_Configuration (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, IsOneQRCodeForAggregatedHUs CHAR(1) DEFAULT 'N' CHECK (IsOneQRCodeForAggregatedHUs IN ('Y','N')) NOT NULL, IsOneQRCodeForMatchingAttributes CHAR(1) DEFAULT 'N' CHECK (IsOneQRCodeForMatchingAttributes IN ('Y','N')) NOT NULL, QRCode_Configuration_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT QRCode_Configuration_Key PRIMARY KEY (QRCode_Configuration_ID))
;

-- Table: QRCode_Attribute_Config
-- Table: QRCode_Attribute_Config
-- 2024-02-14T13:18:57.398Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542394,'N',TO_TIMESTAMP('2024-02-14 15:18:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','N','Y','N','N','Y','N','N','N','N','N',0,'QR-Code Attribute Config','NP','L','QRCode_Attribute_Config','DTI',TO_TIMESTAMP('2024-02-14 15:18:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:18:57.401Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=542394 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2024-02-14T13:18:57.624Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556330,TO_TIMESTAMP('2024-02-14 15:18:57','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table QRCode_Attribute_Config',1,'Y','N','Y','Y','QRCode_Attribute_Config','N',1000000,TO_TIMESTAMP('2024-02-14 15:18:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-14T13:18:57.638Z
CREATE SEQUENCE QRCODE_ATTRIBUTE_CONFIG_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: QRCode_Attribute_Config.AD_Client_ID
-- Column: QRCode_Attribute_Config.AD_Client_ID
-- 2024-02-14T13:19:10.517Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587924,102,0,19,542394,'AD_Client_ID',TO_TIMESTAMP('2024-02-14 15:19:10','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','de.metas.handlingunits',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2024-02-14 15:19:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:19:10.519Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587924 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-14T13:19:10.522Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: QRCode_Attribute_Config.AD_Org_ID
-- Column: QRCode_Attribute_Config.AD_Org_ID
-- 2024-02-14T13:19:11.276Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587925,113,0,30,542394,'AD_Org_ID',TO_TIMESTAMP('2024-02-14 15:19:11','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','de.metas.handlingunits',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2024-02-14 15:19:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:19:11.278Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587925 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-14T13:19:11.283Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: QRCode_Attribute_Config.Created
-- Column: QRCode_Attribute_Config.Created
-- 2024-02-14T13:19:12.064Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587926,245,0,16,542394,'Created',TO_TIMESTAMP('2024-02-14 15:19:11','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.handlingunits',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2024-02-14 15:19:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:19:12.069Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587926 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-14T13:19:12.077Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: QRCode_Attribute_Config.CreatedBy
-- Column: QRCode_Attribute_Config.CreatedBy
-- 2024-02-14T13:19:12.899Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587927,246,0,18,110,542394,'CreatedBy',TO_TIMESTAMP('2024-02-14 15:19:12','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.handlingunits',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2024-02-14 15:19:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:19:12.901Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587927 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-14T13:19:12.904Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: QRCode_Attribute_Config.IsActive
-- Column: QRCode_Attribute_Config.IsActive
-- 2024-02-14T13:19:13.628Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587928,348,0,20,542394,'IsActive',TO_TIMESTAMP('2024-02-14 15:19:13','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','de.metas.handlingunits',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2024-02-14 15:19:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:19:13.631Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587928 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-14T13:19:13.635Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: QRCode_Attribute_Config.Updated
-- Column: QRCode_Attribute_Config.Updated
-- 2024-02-14T13:19:14.408Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587929,607,0,16,542394,'Updated',TO_TIMESTAMP('2024-02-14 15:19:14','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.handlingunits',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2024-02-14 15:19:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:19:14.411Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587929 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-14T13:19:14.415Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: QRCode_Attribute_Config.UpdatedBy
-- Column: QRCode_Attribute_Config.UpdatedBy
-- 2024-02-14T13:19:15.173Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587930,608,0,18,110,542394,'UpdatedBy',TO_TIMESTAMP('2024-02-14 15:19:14','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.handlingunits',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2024-02-14 15:19:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:19:15.174Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587930 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-14T13:19:15.176Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2024-02-14T13:19:15.844Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582968,0,'QRCode_Attribute_Config_ID',TO_TIMESTAMP('2024-02-14 15:19:15','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','QR-Code Attribute Config','QR-Code Attribute Config',TO_TIMESTAMP('2024-02-14 15:19:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-14T13:19:15.846Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582968 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: QRCode_Attribute_Config.QRCode_Attribute_Config_ID
-- Column: QRCode_Attribute_Config.QRCode_Attribute_Config_ID
-- 2024-02-14T13:19:16.752Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587931,582968,0,13,542394,'QRCode_Attribute_Config_ID',TO_TIMESTAMP('2024-02-14 15:19:15','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','QR-Code Attribute Config',0,0,TO_TIMESTAMP('2024-02-14 15:19:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:19:16.754Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587931 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-14T13:19:16.758Z
/* DDL */  select update_Column_Translation_From_AD_Element(582968) 
;

-- 2024-02-14T13:21:17.851Z
INSERT INTO t_alter_column values('qrcode_configuration','QRCode_Configuration_ID','NUMERIC(10)',null,null)
;

-- Name: QR Code Configuration
-- 2024-02-14T13:22:22.685Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541853,TO_TIMESTAMP('2024-02-14 15:22:22','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','QR Code Configuration',TO_TIMESTAMP('2024-02-14 15:22:22','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2024-02-14T13:22:22.694Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541853 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: QR Code Configuration
-- Table: QRCode_Configuration
-- Key: QRCode_Configuration.QRCode_Configuration_ID
-- 2024-02-14T13:22:46.086Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,587921,0,541853,542393,TO_TIMESTAMP('2024-02-14 15:22:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','N',TO_TIMESTAMP('2024-02-14 15:22:46','YYYY-MM-DD HH24:MI:SS'),100,'')
;

-- Column: QRCode_Configuration.QRCode_Configuration_ID
-- Column: QRCode_Configuration.QRCode_Configuration_ID
-- 2024-02-14T13:24:23.270Z
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-02-14 15:24:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587921
;

-- Column: QRCode_Attribute_Config.QRCode_Configuration_ID
-- Column: QRCode_Attribute_Config.QRCode_Configuration_ID
-- 2024-02-14T13:24:29.234Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587932,582965,0,30,541853,542394,'QRCode_Configuration_ID',TO_TIMESTAMP('2024-02-14 15:24:28','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'QR Code Configuration',0,0,TO_TIMESTAMP('2024-02-14 15:24:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:24:29.245Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587932 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-14T13:24:29.255Z
/* DDL */  select update_Column_Translation_From_AD_Element(582965) 
;

-- Column: QRCode_Attribute_Config.QRCode_Attribute_Config_ID
-- Column: QRCode_Attribute_Config.QRCode_Attribute_Config_ID
-- 2024-02-14T13:24:52.483Z
UPDATE AD_Column SET IsIdentifier='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2024-02-14 15:24:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587931
;

-- Column: QRCode_Attribute_Config.AD_Attribute_ID
-- Column: QRCode_Attribute_Config.AD_Attribute_ID
-- 2024-02-14T13:26:41.198Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587933,1458,0,30,540880,542394,'AD_Attribute_ID',TO_TIMESTAMP('2024-02-14 15:26:40','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'System Attribute',0,0,TO_TIMESTAMP('2024-02-14 15:26:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:26:41.203Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587933 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-14T13:26:41.210Z
/* DDL */  select update_Column_Translation_From_AD_Element(1458) 
;

-- 2024-02-14T13:27:03.611Z
/* DDL */ CREATE TABLE public.QRCode_Attribute_Config (AD_Attribute_ID NUMERIC(10) NOT NULL, AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, QRCode_Attribute_Config_ID NUMERIC(10) NOT NULL, QRCode_Configuration_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT ADAttribute_QRCodeAttributeConfig FOREIGN KEY (AD_Attribute_ID) REFERENCES public.M_Attribute DEFERRABLE INITIALLY DEFERRED, CONSTRAINT QRCode_Attribute_Config_Key PRIMARY KEY (QRCode_Attribute_Config_ID), CONSTRAINT QRCodeConfiguration_QRCodeAttributeConfig FOREIGN KEY (QRCode_Configuration_ID) REFERENCES public.QRCode_Configuration DEFERRABLE INITIALLY DEFERRED)
;

-- Window: QR Code Configuration, InternalName=null
-- Window: QR Code Configuration, InternalName=null
-- 2024-02-14T13:28:11.754Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,582965,0,541770,TO_TIMESTAMP('2024-02-14 15:28:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','N','N','N','N','N','Y','QR Code Configuration','N',TO_TIMESTAMP('2024-02-14 15:28:11','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2024-02-14T13:28:11.758Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541770 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2024-02-14T13:28:11.764Z
/* DDL */  select update_window_translation_from_ad_element(582965) 
;

-- 2024-02-14T13:28:11.781Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541770
;

-- 2024-02-14T13:28:11.792Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541770)
;

-- Tab: QR Code Configuration -> QR Code Configuration
-- Table: QRCode_Configuration
-- Tab: QR Code Configuration(541770,de.metas.handlingunits) -> QR Code Configuration
-- Table: QRCode_Configuration
-- 2024-02-14T13:29:19.486Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582965,0,547411,542393,541770,'Y',TO_TIMESTAMP('2024-02-14 15:29:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','N','N','A','QRCode_Configuration','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'QR Code Configuration','N',10,0,TO_TIMESTAMP('2024-02-14 15:29:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-14T13:29:19.499Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547411 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-02-14T13:29:19.506Z
/* DDL */  select update_tab_translation_from_ad_element(582965) 
;

-- 2024-02-14T13:29:19.515Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547411)
;

-- Tab: QR Code Configuration -> QR-Code Attribute Config
-- Table: QRCode_Attribute_Config
-- Tab: QR Code Configuration(541770,de.metas.handlingunits) -> QR-Code Attribute Config
-- Table: QRCode_Attribute_Config
-- 2024-02-14T13:30:36.005Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,587932,582968,0,547412,542394,541770,'Y',TO_TIMESTAMP('2024-02-14 15:30:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','N','N','A','QRCode_Attribute_Config','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'QR-Code Attribute Config',587921,'N',20,1,TO_TIMESTAMP('2024-02-14 15:30:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-14T13:30:36.014Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547412 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-02-14T13:30:36.026Z
/* DDL */  select update_tab_translation_from_ad_element(582968) 
;

-- 2024-02-14T13:30:36.036Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547412)
;

-- Name: QR Code Configuration
-- Action Type: W
-- Window: QR Code Configuration(541770,de.metas.handlingunits)
-- 2024-02-14T13:33:03.098Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,582965,542139,0,541770,TO_TIMESTAMP('2024-02-14 15:33:02','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','QR Code Configuration_541770','Y','N','N','N','N','QR Code Configuration',TO_TIMESTAMP('2024-02-14 15:33:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-14T13:33:03.106Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542139 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2024-02-14T13:33:03.113Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542139, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542139)
;

-- 2024-02-14T13:33:03.132Z
/* DDL */  select update_menu_translation_from_ad_element(582965) 
;

-- Reordering children of `Product Attributes`
-- Node name: `Lot Control (M_LotCtl)`
-- 2024-02-14T13:33:03.710Z
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=355 AND AD_Tree_ID=10
;

-- Node name: `Serial No Control (M_SerNoCtl)`
-- 2024-02-14T13:33:03.712Z
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=354 AND AD_Tree_ID=10
;

-- Node name: `Attribute (M_Attribute)`
-- 2024-02-14T13:33:03.712Z
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=359 AND AD_Tree_ID=10
;

-- Node name: `Attribute Set (M_AttributeSet)`
-- 2024-02-14T13:33:03.713Z
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=353 AND AD_Tree_ID=10
;

-- Node name: `Lot (M_Lot)`
-- 2024-02-14T13:33:03.713Z
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=356 AND AD_Tree_ID=10
;

-- Node name: `Product Attribute Grid (org.compiere.apps.form.VAttributeGrid)`
-- 2024-02-14T13:33:03.714Z
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=565 AND AD_Tree_ID=10
;

-- Node name: `Attribute Search (M_AttributeSearch)`
-- 2024-02-14T13:33:03.714Z
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=358 AND AD_Tree_ID=10
;

-- Node name: `Quality Note (M_QualityNote)`
-- 2024-02-14T13:33:03.714Z
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540734 AND AD_Tree_ID=10
;

-- Node name: `QR Code Configuration`
-- 2024-02-14T13:33:03.714Z
UPDATE AD_TreeNodeMM SET Parent_ID=357, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542139 AND AD_Tree_ID=10
;

-- Reordering children of `Logistics`
-- Node name: `QR Code Configuration`
-- 2024-02-14T13:33:16.553Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542139 AND AD_Tree_ID=10
;

-- Node name: `Tour (M_Tour)`
-- 2024-02-14T13:33:16.553Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- Node name: `Tourversion (M_TourVersion)`
-- 2024-02-14T13:33:16.554Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- Node name: `Delivery Days (M_DeliveryDay)`
-- 2024-02-14T13:33:16.555Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- Node name: `Distribution Order (DD_Order)`
-- 2024-02-14T13:33:16.555Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- Node name: `Distributions Editor (DD_OrderLine)`
-- 2024-02-14T13:33:16.555Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction (M_HU_PI)`
-- 2024-02-14T13:33:16.556Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction Version (M_HU_PI_Version)`
-- 2024-02-14T13:33:16.556Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- Node name: `CU-TU Allocation (M_HU_PI_Item_Product)`
-- 2024-02-14T13:33:16.557Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541375 AND AD_Tree_ID=10
;

-- Node name: `Packing Material (M_HU_PackingMaterial)`
-- 2024-02-14T13:33:16.557Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit (M_HU)`
-- 2024-02-14T13:33:16.557Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- Node name: `Packaging code (M_HU_PackagingCode)`
-- 2024-02-14T13:33:16.558Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541384 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Transaction (M_HU_Trx_Line)`
-- 2024-02-14T13:33:16.558Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Trace (M_HU_Trace)`
-- 2024-02-14T13:33:16.558Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- Node name: `Transport Disposition (M_Tour_Instance)`
-- 2024-02-14T13:33:16.559Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- Node name: `Transport Delivery (M_DeliveryDay_Alloc)`
-- 2024-02-14T13:33:16.559Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- Node name: `Material Transactions (M_Transaction)`
-- 2024-02-14T13:33:16.559Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- Node name: `Transportation Order (M_ShipperTransportation)`
-- 2024-02-14T13:33:16.560Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- Node name: `Package (M_Package)`
-- 2024-02-14T13:33:16.560Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- Node name: `Internal Use (M_Inventory)`
-- 2024-02-14T13:33:16.560Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- Node name: `GO! Delivery Orders (GO_DeliveryOrder)`
-- 2024-02-14T13:33:16.561Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- Node name: `Der Kurier Delivery Orders (DerKurier_DeliveryOrder)`
-- 2024-02-14T13:33:16.561Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541083 AND AD_Tree_ID=10
;

-- Node name: `DHL Delivery Order (DHL_ShipmentOrder)`
-- 2024-02-14T13:33:16.562Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541388 AND AD_Tree_ID=10
;

-- Node name: `DPD Delivery Order (DPD_StoreOrder)`
-- 2024-02-14T13:33:16.562Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541394 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2024-02-14T13:33:16.562Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2024-02-14T13:33:16.562Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2024-02-14T13:33:16.563Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- Node name: `HU Reservierung (M_HU_Reservation)`
-- 2024-02-14T13:33:16.563Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541117 AND AD_Tree_ID=10
;

-- Node name: `Service Handling Units (M_HU)`
-- 2024-02-14T13:33:16.563Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541572 AND AD_Tree_ID=10
;

-- Node name: `HU QR Code (M_HU_QRCode)`
-- 2024-02-14T13:33:16.564Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541905 AND AD_Tree_ID=10
;

-- Tab: QR Code Configuration(541770,de.metas.handlingunits) -> QR Code Configuration(547411,de.metas.handlingunits)
-- UI Section: main
-- 2024-02-14T13:34:24.988Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547411,545988,TO_TIMESTAMP('2024-02-14 15:34:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-02-14 15:34:24','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2024-02-14T13:34:24.995Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545988 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: QR Code Configuration(541770,de.metas.handlingunits) -> QR Code Configuration(547411,de.metas.handlingunits) -> main
-- UI Column: 10
-- 2024-02-14T13:34:25.187Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547305,545988,TO_TIMESTAMP('2024-02-14 15:34:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-02-14 15:34:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: QR Code Configuration(541770,de.metas.handlingunits) -> QR Code Configuration(547411,de.metas.handlingunits) -> main
-- UI Column: 20
-- 2024-02-14T13:34:25.287Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547306,545988,TO_TIMESTAMP('2024-02-14 15:34:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2024-02-14 15:34:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: QR Code Configuration(541770,de.metas.handlingunits) -> QR Code Configuration(547411,de.metas.handlingunits) -> main -> 10
-- UI Element Group: default
-- 2024-02-14T13:34:25.470Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547305,551535,TO_TIMESTAMP('2024-02-14 15:34:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2024-02-14 15:34:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: QR Code Configuration(541770,de.metas.handlingunits) -> QR-Code Attribute Config(547412,de.metas.handlingunits)
-- UI Section: main
-- 2024-02-14T13:34:25.598Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547412,545989,TO_TIMESTAMP('2024-02-14 15:34:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-02-14 15:34:25','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2024-02-14T13:34:25.599Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545989 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: QR Code Configuration(541770,de.metas.handlingunits) -> QR-Code Attribute Config(547412,de.metas.handlingunits) -> main
-- UI Column: 10
-- 2024-02-14T13:34:25.691Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547307,545989,TO_TIMESTAMP('2024-02-14 15:34:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-02-14 15:34:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: QR Code Configuration(541770,de.metas.handlingunits) -> QR-Code Attribute Config(547412,de.metas.handlingunits) -> main -> 10
-- UI Element Group: default
-- 2024-02-14T13:34:25.787Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547307,551536,TO_TIMESTAMP('2024-02-14 15:34:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2024-02-14 15:34:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: QR Code Configuration -> QR Code Configuration -> Mandant
-- Column: QRCode_Configuration.AD_Client_ID
-- Field: QR Code Configuration(541770,de.metas.handlingunits) -> QR Code Configuration(547411,de.metas.handlingunits) -> Mandant
-- Column: QRCode_Configuration.AD_Client_ID
-- 2024-02-14T13:34:47.086Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587914,725164,0,547411,TO_TIMESTAMP('2024-02-14 15:34:46','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.handlingunits','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-02-14 15:34:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-14T13:34:47.095Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=725164 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-14T13:34:47.104Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-02-14T13:34:48.017Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725164
;

-- 2024-02-14T13:34:48.018Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725164)
;

-- Field: QR Code Configuration -> QR Code Configuration -> Sektion
-- Column: QRCode_Configuration.AD_Org_ID
-- Field: QR Code Configuration(541770,de.metas.handlingunits) -> QR Code Configuration(547411,de.metas.handlingunits) -> Sektion
-- Column: QRCode_Configuration.AD_Org_ID
-- 2024-02-14T13:34:48.119Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587915,725165,0,547411,TO_TIMESTAMP('2024-02-14 15:34:48','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.handlingunits','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2024-02-14 15:34:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-14T13:34:48.122Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=725165 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-14T13:34:48.125Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-02-14T13:34:48.678Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725165
;

-- 2024-02-14T13:34:48.678Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725165)
;

-- Field: QR Code Configuration -> QR Code Configuration -> Aktiv
-- Column: QRCode_Configuration.IsActive
-- Field: QR Code Configuration(541770,de.metas.handlingunits) -> QR Code Configuration(547411,de.metas.handlingunits) -> Aktiv
-- Column: QRCode_Configuration.IsActive
-- 2024-02-14T13:34:48.783Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587918,725166,0,547411,TO_TIMESTAMP('2024-02-14 15:34:48','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.handlingunits','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-02-14 15:34:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-14T13:34:48.787Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=725166 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-14T13:34:48.790Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-02-14T13:34:49.210Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725166
;

-- 2024-02-14T13:34:49.211Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725166)
;

-- Field: QR Code Configuration -> QR Code Configuration -> QR Code Configuration
-- Column: QRCode_Configuration.QRCode_Configuration_ID
-- Field: QR Code Configuration(541770,de.metas.handlingunits) -> QR Code Configuration(547411,de.metas.handlingunits) -> QR Code Configuration
-- Column: QRCode_Configuration.QRCode_Configuration_ID
-- 2024-02-14T13:34:49.296Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587921,725167,0,547411,TO_TIMESTAMP('2024-02-14 15:34:49','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','QR Code Configuration',TO_TIMESTAMP('2024-02-14 15:34:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-14T13:34:49.297Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=725167 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-14T13:34:49.298Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582965) 
;

-- 2024-02-14T13:34:49.304Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725167
;

-- 2024-02-14T13:34:49.304Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725167)
;

-- Field: QR Code Configuration -> QR Code Configuration -> One QR-Code for aggregated HUs
-- Column: QRCode_Configuration.IsOneQRCodeForAggregatedHUs
-- Field: QR Code Configuration(541770,de.metas.handlingunits) -> QR Code Configuration(547411,de.metas.handlingunits) -> One QR-Code for aggregated HUs
-- Column: QRCode_Configuration.IsOneQRCodeForAggregatedHUs
-- 2024-02-14T13:34:49.393Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587922,725168,0,547411,TO_TIMESTAMP('2024-02-14 15:34:49','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','One QR-Code for aggregated HUs',TO_TIMESTAMP('2024-02-14 15:34:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-14T13:34:49.395Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=725168 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-14T13:34:49.397Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582966) 
;

-- 2024-02-14T13:34:49.401Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725168
;

-- 2024-02-14T13:34:49.402Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725168)
;

-- Field: QR Code Configuration -> QR Code Configuration -> One QR-Code for matching attributes
-- Column: QRCode_Configuration.IsOneQRCodeForMatchingAttributes
-- Field: QR Code Configuration(541770,de.metas.handlingunits) -> QR Code Configuration(547411,de.metas.handlingunits) -> One QR-Code for matching attributes
-- Column: QRCode_Configuration.IsOneQRCodeForMatchingAttributes
-- 2024-02-14T13:34:49.485Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587923,725169,0,547411,TO_TIMESTAMP('2024-02-14 15:34:49','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','One QR-Code for matching attributes',TO_TIMESTAMP('2024-02-14 15:34:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-14T13:34:49.486Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=725169 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-14T13:34:49.487Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582967) 
;

-- 2024-02-14T13:34:49.498Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725169
;

-- 2024-02-14T13:34:49.498Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725169)
;

-- Field: QR Code Configuration -> QR-Code Attribute Config -> Mandant
-- Column: QRCode_Attribute_Config.AD_Client_ID
-- Field: QR Code Configuration(541770,de.metas.handlingunits) -> QR-Code Attribute Config(547412,de.metas.handlingunits) -> Mandant
-- Column: QRCode_Attribute_Config.AD_Client_ID
-- 2024-02-14T13:34:57.855Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587924,725170,0,547412,TO_TIMESTAMP('2024-02-14 15:34:57','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.handlingunits','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-02-14 15:34:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-14T13:34:57.866Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=725170 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-14T13:34:57.874Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2024-02-14T13:34:58.033Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725170
;

-- 2024-02-14T13:34:58.049Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725170)
;

-- Field: QR Code Configuration -> QR-Code Attribute Config -> Sektion
-- Column: QRCode_Attribute_Config.AD_Org_ID
-- Field: QR Code Configuration(541770,de.metas.handlingunits) -> QR-Code Attribute Config(547412,de.metas.handlingunits) -> Sektion
-- Column: QRCode_Attribute_Config.AD_Org_ID
-- 2024-02-14T13:34:58.149Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587925,725171,0,547412,TO_TIMESTAMP('2024-02-14 15:34:58','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.handlingunits','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2024-02-14 15:34:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-14T13:34:58.152Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=725171 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-14T13:34:58.156Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2024-02-14T13:34:58.319Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725171
;

-- 2024-02-14T13:34:58.320Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725171)
;

-- Field: QR Code Configuration -> QR-Code Attribute Config -> Aktiv
-- Column: QRCode_Attribute_Config.IsActive
-- Field: QR Code Configuration(541770,de.metas.handlingunits) -> QR-Code Attribute Config(547412,de.metas.handlingunits) -> Aktiv
-- Column: QRCode_Attribute_Config.IsActive
-- 2024-02-14T13:34:58.411Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587928,725172,0,547412,TO_TIMESTAMP('2024-02-14 15:34:58','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.handlingunits','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-02-14 15:34:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-14T13:34:58.413Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=725172 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-14T13:34:58.416Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2024-02-14T13:34:58.544Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725172
;

-- 2024-02-14T13:34:58.545Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725172)
;

-- Field: QR Code Configuration -> QR-Code Attribute Config -> QR-Code Attribute Config
-- Column: QRCode_Attribute_Config.QRCode_Attribute_Config_ID
-- Field: QR Code Configuration(541770,de.metas.handlingunits) -> QR-Code Attribute Config(547412,de.metas.handlingunits) -> QR-Code Attribute Config
-- Column: QRCode_Attribute_Config.QRCode_Attribute_Config_ID
-- 2024-02-14T13:34:58.642Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587931,725173,0,547412,TO_TIMESTAMP('2024-02-14 15:34:58','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','QR-Code Attribute Config',TO_TIMESTAMP('2024-02-14 15:34:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-14T13:34:58.645Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=725173 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-14T13:34:58.648Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582968) 
;

-- 2024-02-14T13:34:58.661Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725173
;

-- 2024-02-14T13:34:58.662Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725173)
;

-- Field: QR Code Configuration -> QR-Code Attribute Config -> QR Code Configuration
-- Column: QRCode_Attribute_Config.QRCode_Configuration_ID
-- Field: QR Code Configuration(541770,de.metas.handlingunits) -> QR-Code Attribute Config(547412,de.metas.handlingunits) -> QR Code Configuration
-- Column: QRCode_Attribute_Config.QRCode_Configuration_ID
-- 2024-02-14T13:34:58.762Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587932,725174,0,547412,TO_TIMESTAMP('2024-02-14 15:34:58','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','QR Code Configuration',TO_TIMESTAMP('2024-02-14 15:34:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-14T13:34:58.764Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=725174 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-14T13:34:58.767Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582965) 
;

-- 2024-02-14T13:34:58.776Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725174
;

-- 2024-02-14T13:34:58.777Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725174)
;

-- Field: QR Code Configuration -> QR-Code Attribute Config -> System Attribute
-- Column: QRCode_Attribute_Config.AD_Attribute_ID
-- Field: QR Code Configuration(541770,de.metas.handlingunits) -> QR-Code Attribute Config(547412,de.metas.handlingunits) -> System Attribute
-- Column: QRCode_Attribute_Config.AD_Attribute_ID
-- 2024-02-14T13:34:58.864Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587933,725175,0,547412,TO_TIMESTAMP('2024-02-14 15:34:58','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.handlingunits','Y','N','N','N','N','N','N','N','System Attribute',TO_TIMESTAMP('2024-02-14 15:34:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-14T13:34:58.865Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=725175 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-14T13:34:58.867Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1458) 
;

-- 2024-02-14T13:34:58.887Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725175
;

-- 2024-02-14T13:34:58.888Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725175)
;

-- Column: QRCode_Configuration.Name
-- Column: QRCode_Configuration.Name
-- 2024-02-14T13:36:05.716Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587934,469,0,10,542393,'Name',TO_TIMESTAMP('2024-02-14 15:36:05','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.handlingunits',0,40,'E','','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Y','N','N','Y','N','N','N','N','N','Y','N',0,'Name',0,1,TO_TIMESTAMP('2024-02-14 15:36:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:36:05.723Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587934 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-14T13:36:05.731Z
/* DDL */  select update_Column_Translation_From_AD_Element(469) 
;

-- 2024-02-14T13:36:08.483Z
/* DDL */ SELECT public.db_alter_table('QRCode_Configuration','ALTER TABLE public.QRCode_Configuration ADD COLUMN Name VARCHAR(40) NOT NULL')
;

-- Field: QR Code Configuration -> QR Code Configuration -> Name
-- Column: QRCode_Configuration.Name
-- Field: QR Code Configuration(541770,de.metas.handlingunits) -> QR Code Configuration(547411,de.metas.handlingunits) -> Name
-- Column: QRCode_Configuration.Name
-- 2024-02-14T13:36:23.234Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587934,725176,0,547411,TO_TIMESTAMP('2024-02-14 15:36:23','YYYY-MM-DD HH24:MI:SS'),100,'',40,'de.metas.handlingunits','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2024-02-14 15:36:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-02-14T13:36:23.236Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=725176 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-02-14T13:36:23.254Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469) 
;

-- 2024-02-14T13:36:23.376Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725176
;

-- 2024-02-14T13:36:23.377Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(725176)
;

-- UI Element: QR Code Configuration -> QR Code Configuration.Name
-- Column: QRCode_Configuration.Name
-- UI Element: QR Code Configuration(541770,de.metas.handlingunits) -> QR Code Configuration(547411,de.metas.handlingunits) -> main -> 10 -> default.Name
-- Column: QRCode_Configuration.Name
-- 2024-02-14T13:36:55.144Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,725176,0,547411,622926,551535,'F',TO_TIMESTAMP('2024-02-14 15:36:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Name',10,0,0,TO_TIMESTAMP('2024-02-14 15:36:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: QR Code Configuration(541770,de.metas.handlingunits) -> QR Code Configuration(547411,de.metas.handlingunits) -> main -> 20
-- UI Element Group: flags
-- 2024-02-14T13:37:07.252Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547306,551537,TO_TIMESTAMP('2024-02-14 15:37:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2024-02-14 15:37:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: QR Code Configuration -> QR Code Configuration.One QR-Code for aggregated HUs
-- Column: QRCode_Configuration.IsOneQRCodeForAggregatedHUs
-- UI Element: QR Code Configuration(541770,de.metas.handlingunits) -> QR Code Configuration(547411,de.metas.handlingunits) -> main -> 20 -> flags.One QR-Code for aggregated HUs
-- Column: QRCode_Configuration.IsOneQRCodeForAggregatedHUs
-- 2024-02-14T13:37:20.315Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,725168,0,547411,622927,551537,'F',TO_TIMESTAMP('2024-02-14 15:37:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'One QR-Code for aggregated HUs',10,0,0,TO_TIMESTAMP('2024-02-14 15:37:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: QR Code Configuration -> QR Code Configuration.One QR-Code for matching attributes
-- Column: QRCode_Configuration.IsOneQRCodeForMatchingAttributes
-- UI Element: QR Code Configuration(541770,de.metas.handlingunits) -> QR Code Configuration(547411,de.metas.handlingunits) -> main -> 20 -> flags.One QR-Code for matching attributes
-- Column: QRCode_Configuration.IsOneQRCodeForMatchingAttributes
-- 2024-02-14T13:37:26.928Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,725169,0,547411,622928,551537,'F',TO_TIMESTAMP('2024-02-14 15:37:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'One QR-Code for matching attributes',20,0,0,TO_TIMESTAMP('2024-02-14 15:37:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: QR Code Configuration -> QR Code Configuration.Aktiv
-- Column: QRCode_Configuration.IsActive
-- UI Element: QR Code Configuration(541770,de.metas.handlingunits) -> QR Code Configuration(547411,de.metas.handlingunits) -> main -> 20 -> flags.Aktiv
-- Column: QRCode_Configuration.IsActive
-- 2024-02-14T13:37:34.292Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,725166,0,547411,622929,551537,'F',TO_TIMESTAMP('2024-02-14 15:37:34','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',30,0,0,TO_TIMESTAMP('2024-02-14 15:37:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: QR Code Configuration(541770,de.metas.handlingunits) -> QR Code Configuration(547411,de.metas.handlingunits) -> main -> 20
-- UI Element Group: org+client
-- 2024-02-14T13:37:55.148Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547306,551538,TO_TIMESTAMP('2024-02-14 15:37:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','org+client',20,TO_TIMESTAMP('2024-02-14 15:37:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: QR Code Configuration -> QR Code Configuration.Sektion
-- Column: QRCode_Configuration.AD_Org_ID
-- UI Element: QR Code Configuration(541770,de.metas.handlingunits) -> QR Code Configuration(547411,de.metas.handlingunits) -> main -> 20 -> org+client.Sektion
-- Column: QRCode_Configuration.AD_Org_ID
-- 2024-02-14T13:38:06.035Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,725165,0,547411,622930,551538,'F',TO_TIMESTAMP('2024-02-14 15:38:05','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2024-02-14 15:38:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: QR Code Configuration -> QR Code Configuration.Mandant
-- Column: QRCode_Configuration.AD_Client_ID
-- UI Element: QR Code Configuration(541770,de.metas.handlingunits) -> QR Code Configuration(547411,de.metas.handlingunits) -> main -> 20 -> org+client.Mandant
-- Column: QRCode_Configuration.AD_Client_ID
-- 2024-02-14T13:38:14.566Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,725164,0,547411,622931,551538,'F',TO_TIMESTAMP('2024-02-14 15:38:14','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2024-02-14 15:38:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: QR Code Configuration -> QR-Code Attribute Config.System Attribute
-- Column: QRCode_Attribute_Config.AD_Attribute_ID
-- UI Element: QR Code Configuration(541770,de.metas.handlingunits) -> QR-Code Attribute Config(547412,de.metas.handlingunits) -> main -> 10 -> default.System Attribute
-- Column: QRCode_Attribute_Config.AD_Attribute_ID
-- 2024-02-14T13:38:57.837Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,725175,0,547412,622932,551536,'F',TO_TIMESTAMP('2024-02-14 15:38:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'System Attribute',10,0,0,TO_TIMESTAMP('2024-02-14 15:38:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: QR Code Configuration(541770,de.metas.handlingunits) -> QR-Code Attribute Config(547412,de.metas.handlingunits) -> main
-- UI Column: 20
-- 2024-02-14T13:39:13.227Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547308,545989,TO_TIMESTAMP('2024-02-14 15:39:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2024-02-14 15:39:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: QR Code Configuration(541770,de.metas.handlingunits) -> QR-Code Attribute Config(547412,de.metas.handlingunits) -> main -> 20
-- UI Element Group: flags
-- 2024-02-14T13:39:22.658Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547308,551539,TO_TIMESTAMP('2024-02-14 15:39:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2024-02-14 15:39:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: QR Code Configuration -> QR-Code Attribute Config.Aktiv
-- Column: QRCode_Attribute_Config.IsActive
-- UI Element: QR Code Configuration(541770,de.metas.handlingunits) -> QR-Code Attribute Config(547412,de.metas.handlingunits) -> main -> 20 -> flags.Aktiv
-- Column: QRCode_Attribute_Config.IsActive
-- 2024-02-14T13:39:32.770Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,725172,0,547412,622933,551539,'F',TO_TIMESTAMP('2024-02-14 15:39:32','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2024-02-14 15:39:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: QR Code Configuration(541770,de.metas.handlingunits) -> QR-Code Attribute Config(547412,de.metas.handlingunits) -> main -> 20
-- UI Element Group: org+client
-- 2024-02-14T13:39:57.569Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547308,551540,TO_TIMESTAMP('2024-02-14 15:39:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','org+client',20,TO_TIMESTAMP('2024-02-14 15:39:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: QR Code Configuration -> QR-Code Attribute Config.Sektion
-- Column: QRCode_Attribute_Config.AD_Org_ID
-- UI Element: QR Code Configuration(541770,de.metas.handlingunits) -> QR-Code Attribute Config(547412,de.metas.handlingunits) -> main -> 20 -> org+client.Sektion
-- Column: QRCode_Attribute_Config.AD_Org_ID
-- 2024-02-14T13:40:09.943Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,725171,0,547412,622934,551540,'F',TO_TIMESTAMP('2024-02-14 15:40:09','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2024-02-14 15:40:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: QR Code Configuration -> QR-Code Attribute Config.Mandant
-- Column: QRCode_Attribute_Config.AD_Client_ID
-- UI Element: QR Code Configuration(541770,de.metas.handlingunits) -> QR-Code Attribute Config(547412,de.metas.handlingunits) -> main -> 20 -> org+client.Mandant
-- Column: QRCode_Attribute_Config.AD_Client_ID
-- 2024-02-14T13:40:22.648Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,725170,0,547412,622935,551540,'F',TO_TIMESTAMP('2024-02-14 15:40:22','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2024-02-14 15:40:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: M_HU_QRCode_Assignmnet.M_HU_QRCode_ID
-- Column: M_HU_QRCode_Assignmnet.M_HU_QRCode_ID
-- 2024-02-14T13:51:54.170Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587935,580520,0,19,542392,'M_HU_QRCode_ID',TO_TIMESTAMP('2024-02-14 15:51:54','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'HU QR Code',0,0,TO_TIMESTAMP('2024-02-14 15:51:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-02-14T13:51:54.172Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587935 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-02-14T13:51:54.180Z
/* DDL */  select update_Column_Translation_From_AD_Element(580520) 
;

-- 2024-02-14T13:51:56.733Z
/* DDL */ SELECT public.db_alter_table('M_HU_QRCode_Assignmnet','ALTER TABLE public.M_HU_QRCode_Assignmnet ADD COLUMN M_HU_QRCode_ID NUMERIC(10) NOT NULL')
;

-- 2024-02-14T13:51:56.749Z
ALTER TABLE M_HU_QRCode_Assignmnet ADD CONSTRAINT MHUQRCode_MHUQRCodeAssignmnet FOREIGN KEY (M_HU_QRCode_ID) REFERENCES public.M_HU_QRCode DEFERRABLE INITIALLY DEFERRED
;

delete from ad_columncallout where ad_table_id=542392
;
delete from ad_searchdefinition where ad_table_id=542392
;
delete from ad_ui_element where ad_tab_id in (select ad_tab_id from ad_tab where ad_table_id=542392)
;
delete
from ad_ui_elementgroup
where ad_ui_column_id in
      (select ad_ui_column_id
       from ad_ui_column
       where ad_ui_section_id in
             (select ad_ui_section_id
              from ad_ui_section
              where ad_tab_id in
                    (select ad_tab_id from ad_tab where ad_table_id = 542392)))
;
delete
from ad_ui_column
where ad_ui_section_id in
      (select ad_ui_section_id
       from ad_ui_section
       where ad_tab_id in
             (select ad_tab_id from ad_tab where ad_table_id = 542392))
;
delete from ad_ui_section_trl
where ad_ui_section_id in
      (select ad_ui_section_id
       from ad_ui_section
       where ad_tab_id in
             (select ad_tab_id from ad_tab where ad_table_id = 542392))
;
delete from ad_ui_section where ad_tab_id in (select ad_tab_id from ad_tab where ad_table_id=542392)
;
delete from ad_tab where ad_table_id=542392
;
delete from ad_table where tablename = 'M_HU_QRCode_Assignmnet'
;

