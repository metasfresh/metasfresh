-- 2023-09-06T13:55:47.794524100Z
INSERT INTO AD_EntityType (AD_Client_ID,AD_EntityType_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,IsDisplayed,ModelPackage,Name,Processing,Updated,UpdatedBy) VALUES (0,540304,0,TO_TIMESTAMP('2023-09-06 16:55:47.674','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.shippingnotification','Y','Y','de.metas.shippingnotification.model','de.metas.shippingnotification','N',TO_TIMESTAMP('2023-09-06 16:55:47.674','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:55:58.263261300Z
UPDATE AD_EntityType SET Name='Shipping notification',Updated=TO_TIMESTAMP('2023-09-06 16:55:58.261','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_EntityType_ID=540304
;



-- Table: M_Shipping_Notification
-- 2023-09-05T13:48:41.228259800Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('4',0,0,0,542365,'N',TO_TIMESTAMP('2023-09-05 16:48:41.01','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.shippingnotification','N','Y','N','N','Y','N','N','N','N','N',0,'Shipping Notification','NP','L','M_Shipping_Notification','DTI',TO_TIMESTAMP('2023-09-05 16:48:41.01','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T13:48:41.252517500Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542365 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-09-05T13:48:41.370159900Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556302,TO_TIMESTAMP('2023-09-05 16:48:41.286','YYYY-MM-DD HH24:MI:SS.US'),100,1000000,50000,'Table M_Shipping_Notification',1,'Y','N','Y','Y','M_Shipping_Notification','N',1000000,TO_TIMESTAMP('2023-09-05 16:48:41.286','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-05T13:48:41.381042Z
CREATE SEQUENCE M_SHIPPING_NOTIFICATION_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: M_Shipping_Notification.AD_Client_ID
-- 2023-09-05T13:49:02.062751600Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587373,102,0,19,542365,'AD_Client_ID',TO_TIMESTAMP('2023-09-05 16:49:01.927','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Mandant für diese Installation.','de.metas.shippingnotification',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2023-09-05 16:49:01.927','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T13:49:02.065854800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587373 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T13:49:02.089471400Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: M_Shipping_Notification.AD_Org_ID
-- 2023-09-05T13:49:03.255453300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587374,113,0,30,542365,'AD_Org_ID',TO_TIMESTAMP('2023-09-05 16:49:02.983','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Organisatorische Einheit des Mandanten','de.metas.shippingnotification',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2023-09-05 16:49:02.983','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T13:49:03.257563Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587374 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T13:49:03.260684500Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: M_Shipping_Notification.Created
-- 2023-09-05T13:49:03.789180100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587375,245,0,16,542365,'Created',TO_TIMESTAMP('2023-09-05 16:49:03.593','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.shippingnotification',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2023-09-05 16:49:03.593','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T13:49:03.791250400Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587375 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T13:49:03.793835400Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: M_Shipping_Notification.CreatedBy
-- 2023-09-05T13:49:04.335586600Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587376,246,0,18,110,542365,'CreatedBy',TO_TIMESTAMP('2023-09-05 16:49:04.117','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.shippingnotification',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2023-09-05 16:49:04.117','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T13:49:04.343156200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587376 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T13:49:04.351355500Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: M_Shipping_Notification.IsActive
-- 2023-09-05T13:49:04.884448200Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587377,348,0,20,542365,'IsActive',TO_TIMESTAMP('2023-09-05 16:49:04.681','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Der Eintrag ist im System aktiv','de.metas.shippingnotification',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2023-09-05 16:49:04.681','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T13:49:04.886533700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587377 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T13:49:04.890152200Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: M_Shipping_Notification.Updated
-- 2023-09-05T13:49:05.410254100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587378,607,0,16,542365,'Updated',TO_TIMESTAMP('2023-09-05 16:49:05.201','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.shippingnotification',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2023-09-05 16:49:05.201','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T13:49:05.412862900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587378 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T13:49:05.415969Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: M_Shipping_Notification.UpdatedBy
-- 2023-09-05T13:49:06.227764400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587379,608,0,18,110,542365,'UpdatedBy',TO_TIMESTAMP('2023-09-05 16:49:05.993','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.shippingnotification',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2023-09-05 16:49:05.993','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T13:49:06.235728Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587379 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T13:49:06.248541Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2023-09-05T13:49:06.704858100Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582695,0,'M_Shipping_Notification_ID',TO_TIMESTAMP('2023-09-05 16:49:06.623','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.shippingnotification','Y','Shipping Notification','Shipping Notification',TO_TIMESTAMP('2023-09-05 16:49:06.623','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-05T13:49:06.707994900Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582695 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Shipping_Notification.M_Shipping_Notification_ID
-- 2023-09-05T13:49:07.158511400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587380,582695,0,13,542365,'M_Shipping_Notification_ID',TO_TIMESTAMP('2023-09-05 16:49:06.62','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.shippingnotification',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Shipping Notification',0,0,TO_TIMESTAMP('2023-09-05 16:49:06.62','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T13:49:07.160584500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587380 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T13:49:07.163690700Z
/* DDL */  select update_Column_Translation_From_AD_Element(582695) 
;

-- Column: M_Shipping_Notification.DocAction
-- 2023-09-05T13:49:24.689251900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587381,287,0,28,135,542365,'DocAction',TO_TIMESTAMP('2023-09-05 16:49:24.572','YYYY-MM-DD HH24:MI:SS.US'),100,'N','CO','Der zukünftige Status des Belegs','de.metas.shippingnotification',0,2,'You find the current status in the Document Status field. The options are listed in a popup','Y','Y','N','N','Y','N','N','N','N','Y','N','N','N','N','N','N','Y','Belegverarbeitung',0,0,TO_TIMESTAMP('2023-09-05 16:49:24.572','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T13:49:24.700327100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587381 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T13:49:24.731864300Z
/* DDL */  select update_Column_Translation_From_AD_Element(287) 
;

-- Column: M_Shipping_Notification.DocStatus
-- 2023-09-05T13:49:25.303398Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587382,289,0,17,131,542365,'DocStatus',TO_TIMESTAMP('2023-09-05 16:49:25.051','YYYY-MM-DD HH24:MI:SS.US'),100,'N','DR','The current status of the document','de.metas.shippingnotification',0,2,'The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','Y','N','N','Y','N','N','N','N','Y','N','N','N','N','N','N','Y','Belegstatus',0,0,TO_TIMESTAMP('2023-09-05 16:49:25.051','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T13:49:25.305472800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587382 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T13:49:25.308576300Z
/* DDL */  select update_Column_Translation_From_AD_Element(289) 
;

-- Column: M_Shipping_Notification.Processing
-- 2023-09-05T13:49:25.746900800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587383,524,0,28,542365,'Processing',TO_TIMESTAMP('2023-09-05 16:49:25.556','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.shippingnotification',0,1,'Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','Process Now',0,0,TO_TIMESTAMP('2023-09-05 16:49:25.556','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T13:49:25.751238500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587383 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T13:49:25.757159900Z
/* DDL */  select update_Column_Translation_From_AD_Element(524) 
;

-- Column: M_Shipping_Notification.Processed
-- 2023-09-05T13:49:26.228411900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587384,1047,0,20,542365,'Processed',TO_TIMESTAMP('2023-09-05 16:49:26.031','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','de.metas.shippingnotification',0,1,'Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','Y','N','N','Y','N','N','N','N','Y','N','N','N','N','N','N','N','Verarbeitet',0,0,TO_TIMESTAMP('2023-09-05 16:49:26.031','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T13:49:26.230485500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587384 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T13:49:26.233066800Z
/* DDL */  select update_Column_Translation_From_AD_Element(1047) 
;


-- 2023-09-05T13:49:26.717691100Z
INSERT INTO AD_Workflow (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_Workflow_ID,Author,Cost,Created,CreatedBy,DocumentNo,Duration,DurationUnit,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsValid,Name,PublishStatus,Updated,UpdatedBy,Value,Version,WaitingTime,WorkflowType,WorkingTime) VALUES ('1',0,0,542365,540123,'metasfresh ERP',0,TO_TIMESTAMP('2023-09-05 16:49:26.555','YYYY-MM-DD HH24:MI:SS.US'),100,'10000000',1,'D','D','Y','N','N','N','Process_M_Shipping_Notification','R',TO_TIMESTAMP('2023-09-05 16:49:26.555','YYYY-MM-DD HH24:MI:SS.US'),100,'Process_M_Shipping_Notification',0,0,'P',0)
;

-- 2023-09-05T13:49:26.727248Z
INSERT INTO AD_Workflow_Trl (AD_Language,AD_Workflow_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Workflow_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Workflow t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Workflow_ID=540123 AND NOT EXISTS (SELECT 1 FROM AD_Workflow_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Workflow_ID=t.AD_Workflow_ID)
;

-- 2023-09-05T13:49:26.878331300Z
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,Description,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,JoinElement,Name,SplitElement,Updated,UpdatedBy,Value,WaitingTime,XPosition,YPosition) VALUES ('Z',0,0,540305,540123,0,TO_TIMESTAMP('2023-09-05 16:49:26.799','YYYY-MM-DD HH24:MI:SS.US'),100,'(Standard Node)',0,0,'D','Y','Y','X','(Start)','X',TO_TIMESTAMP('2023-09-05 16:49:26.799','YYYY-MM-DD HH24:MI:SS.US'),100,'(Start)',0,0,0)
;

-- 2023-09-05T13:49:26.893689400Z
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_WF_Node_ID=540305 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2023-09-05T13:49:26.906355800Z
UPDATE AD_Workflow SET AD_WF_Node_ID=540305, IsValid='Y',Updated=TO_TIMESTAMP('2023-09-05 16:49:26.905','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Workflow_ID=540123
;

-- 2023-09-05T13:49:26.936516700Z
UPDATE AD_Workflow SET AD_WF_Node_ID=540305, IsValid='Y',Updated=TO_TIMESTAMP('2023-09-05 16:49:26.935','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Workflow_ID=540123
;

-- 2023-09-05T13:49:27.033139800Z
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,Description,DocAction,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,JoinElement,Name,SplitElement,Updated,UpdatedBy,Value,WaitingTime,XPosition,YPosition) VALUES ('D',0,0,540306,540123,0,TO_TIMESTAMP('2023-09-05 16:49:26.944','YYYY-MM-DD HH24:MI:SS.US'),100,'(Standard Node)','--',0,0,'D','Y','Y','X','(DocAuto)','X',TO_TIMESTAMP('2023-09-05 16:49:26.944','YYYY-MM-DD HH24:MI:SS.US'),100,'(DocAuto)',0,0,0)
;

-- 2023-09-05T13:49:27.034697400Z
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_WF_Node_ID=540306 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2023-09-05T13:49:27.115094400Z
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,Description,DocAction,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,JoinElement,Name,SplitElement,Updated,UpdatedBy,Value,WaitingTime,XPosition,YPosition) VALUES ('D',0,0,540307,540123,0,TO_TIMESTAMP('2023-09-05 16:49:27.04','YYYY-MM-DD HH24:MI:SS.US'),100,'(Standard Node)','PR',0,0,'D','Y','Y','X','(DocPrepare)','X',TO_TIMESTAMP('2023-09-05 16:49:27.04','YYYY-MM-DD HH24:MI:SS.US'),100,'(DocPrepare)',0,0,0)
;

-- 2023-09-05T13:49:27.116649100Z
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_WF_Node_ID=540307 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2023-09-05T13:49:27.194746800Z
INSERT INTO AD_WF_Node (Action,AD_Client_ID,AD_Org_ID,AD_WF_Node_ID,AD_Workflow_ID,Cost,Created,CreatedBy,Description,DocAction,Duration,DurationLimit,EntityType,IsActive,IsCentrallyMaintained,JoinElement,Name,SplitElement,Updated,UpdatedBy,Value,WaitingTime,XPosition,YPosition) VALUES ('D',0,0,540308,540123,0,TO_TIMESTAMP('2023-09-05 16:49:27.121','YYYY-MM-DD HH24:MI:SS.US'),100,'(Standard Node)','CO',0,0,'D','Y','Y','X','(DocComplete)','X',TO_TIMESTAMP('2023-09-05 16:49:27.121','YYYY-MM-DD HH24:MI:SS.US'),100,'(DocComplete)',0,0,0)
;

-- 2023-09-05T13:49:27.195769700Z
INSERT INTO AD_WF_Node_Trl (AD_Language,AD_WF_Node_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_WF_Node_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_WF_Node t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_WF_Node_ID=540308 AND NOT EXISTS (SELECT 1 FROM AD_WF_Node_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_WF_Node_ID=t.AD_WF_Node_ID)
;

-- 2023-09-05T13:49:27.312666400Z
INSERT INTO AD_WF_NodeNext (AD_Client_ID,AD_Org_ID,AD_WF_Next_ID,AD_WF_Node_ID,AD_WF_NodeNext_ID,Created,CreatedBy,Description,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540307,540305,540193,TO_TIMESTAMP('2023-09-05 16:49:27.231','YYYY-MM-DD HH24:MI:SS.US'),100,'Standard Transition','D','Y',10,TO_TIMESTAMP('2023-09-05 16:49:27.231','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-05T13:49:27.323768900Z
UPDATE AD_WF_NodeNext SET Description='(Standard Approval)', IsStdUserWorkflow='Y',Updated=TO_TIMESTAMP('2023-09-05 16:49:27.323','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_WF_NodeNext_ID=540193
;

-- 2023-09-05T13:49:27.409588900Z
INSERT INTO AD_WF_NodeNext (AD_Client_ID,AD_Org_ID,AD_WF_Next_ID,AD_WF_Node_ID,AD_WF_NodeNext_ID,Created,CreatedBy,Description,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540306,540305,540194,TO_TIMESTAMP('2023-09-05 16:49:27.327','YYYY-MM-DD HH24:MI:SS.US'),100,'Standard Transition','D','Y',100,TO_TIMESTAMP('2023-09-05 16:49:27.327','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-05T13:49:27.499930900Z
INSERT INTO AD_WF_NodeNext (AD_Client_ID,AD_Org_ID,AD_WF_Next_ID,AD_WF_Node_ID,AD_WF_NodeNext_ID,Created,CreatedBy,Description,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540308,540307,540195,TO_TIMESTAMP('2023-09-05 16:49:27.416','YYYY-MM-DD HH24:MI:SS.US'),100,'Standard Transition','D','Y',100,TO_TIMESTAMP('2023-09-05 16:49:27.416','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Value: Process_M_Shipping_Notification
-- 2023-09-05T13:49:27.609297700Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Workflow_ID,Created,CreatedBy,EntityType,IsActive,IsReport,IsUseBPartnerLanguage,Name,Type,Updated,UpdatedBy,Value) VALUES ('1',0,0,585313,540123,TO_TIMESTAMP('2023-09-05 16:49:27.506','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','Y','Process_M_Shipping_Notification','Java',TO_TIMESTAMP('2023-09-05 16:49:27.506','YYYY-MM-DD HH24:MI:SS.US'),100,'Process_M_Shipping_Notification')
;

-- 2023-09-05T13:49:27.663690500Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585313 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Column: M_Shipping_Notification.Processing
-- 2023-09-05T13:49:27.718289Z
UPDATE AD_Column SET AD_Process_ID=585313,Updated=TO_TIMESTAMP('2023-09-05 16:49:27.717','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587383
;

-- Column: M_Shipping_Notification.DocAction
-- 2023-09-05T13:49:28.212906400Z
UPDATE AD_Column SET AD_Process_ID=585313,Updated=TO_TIMESTAMP('2023-09-05 16:49:28.212','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587381
;

-- Table: M_Shipping_Notification
-- 2023-09-05T13:49:32.902694400Z
UPDATE AD_Table SET AccessLevel='3',Updated=TO_TIMESTAMP('2023-09-05 16:49:32.9','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=542365
;

-- 2023-09-05T13:49:43.359825200Z
/* DDL */ CREATE TABLE public.M_Shipping_Notification (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, DocAction CHAR(2) DEFAULT 'CO' NOT NULL, DocStatus VARCHAR(2) DEFAULT 'DR' NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, M_Shipping_Notification_ID NUMERIC(10) NOT NULL, Processed CHAR(1) DEFAULT 'N' CHECK (Processed IN ('Y','N')) NOT NULL, Processing CHAR(1), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT M_Shipping_Notification_Key PRIMARY KEY (M_Shipping_Notification_ID))
;

-- Column: M_Shipping_Notification.POReference
-- 2023-09-05T14:02:41.754057800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587385,952,0,10,542365,'POReference',TO_TIMESTAMP('2023-09-05 17:02:41.611','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Referenz-Nummer des Kunden','de.metas.shippingnotification',0,40,'The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Referenz',0,0,TO_TIMESTAMP('2023-09-05 17:02:41.611','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:02:41.756646500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587385 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T14:02:41.759230500Z
/* DDL */  select update_Column_Translation_From_AD_Element(952) 
;

-- 2023-09-05T14:02:43.029176400Z
/* DDL */ SELECT public.db_alter_table('M_Shipping_Notification','ALTER TABLE public.M_Shipping_Notification ADD COLUMN POReference VARCHAR(40)')
;

-- Column: M_Shipping_Notification.C_BPartner_ID
-- 2023-09-05T14:02:53.985464400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587386,187,0,19,542365,'C_BPartner_ID',TO_TIMESTAMP('2023-09-05 17:02:53.865','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Bezeichnet einen Geschäftspartner','de.metas.shippingnotification',0,10,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Geschäftspartner',0,0,TO_TIMESTAMP('2023-09-05 17:02:53.865','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:02:53.987554800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587386 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T14:02:53.990663100Z
/* DDL */  select update_Column_Translation_From_AD_Element(187) 
;

-- 2023-09-05T14:02:55.665251Z
/* DDL */ SELECT public.db_alter_table('M_Shipping_Notification','ALTER TABLE public.M_Shipping_Notification ADD COLUMN C_BPartner_ID NUMERIC(10)')
;

-- 2023-09-05T14:02:55.670989800Z
ALTER TABLE M_Shipping_Notification ADD CONSTRAINT CBPartner_MShippingNotification FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_Shipping_Notification.C_BPartner_Location_ID
-- 2023-09-05T14:03:07.958433200Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587387,189,0,19,542365,'C_BPartner_Location_ID',TO_TIMESTAMP('2023-09-05 17:03:07.826','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Identifiziert die (Liefer-) Adresse des Geschäftspartners','de.metas.shippingnotification',0,10,'Identifiziert die Adresse des Geschäftspartners','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Standort',0,0,TO_TIMESTAMP('2023-09-05 17:03:07.826','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:03:07.960514300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587387 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T14:03:07.963637300Z
/* DDL */  select update_Column_Translation_From_AD_Element(189) 
;

-- 2023-09-05T14:03:09.113021Z
/* DDL */ SELECT public.db_alter_table('M_Shipping_Notification','ALTER TABLE public.M_Shipping_Notification ADD COLUMN C_BPartner_Location_ID NUMERIC(10)')
;

-- 2023-09-05T14:03:09.117709900Z
ALTER TABLE M_Shipping_Notification ADD CONSTRAINT CBPartnerLocation_MShippingNotification FOREIGN KEY (C_BPartner_Location_ID) REFERENCES public.C_BPartner_Location DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_Shipping_Notification.BPartnerAddress
-- 2023-09-05T14:03:26.974487500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587388,505103,0,36,542365,'BPartnerAddress',TO_TIMESTAMP('2023-09-05 17:03:26.868','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.shippingnotification',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Anschrift-Text',0,0,TO_TIMESTAMP('2023-09-05 17:03:26.868','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:03:26.976569700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587388 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T14:03:26.979160900Z
/* DDL */  select update_Column_Translation_From_AD_Element(505103) 
;

-- 2023-09-05T14:03:28.792630800Z
/* DDL */ SELECT public.db_alter_table('M_Shipping_Notification','ALTER TABLE public.M_Shipping_Notification ADD COLUMN BPartnerAddress TEXT')
;

-- Column: M_Shipping_Notification.AD_User_ID
-- 2023-09-05T14:03:41.327277500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587389,138,0,19,542365,'AD_User_ID',TO_TIMESTAMP('2023-09-05 17:03:41.212','YYYY-MM-DD HH24:MI:SS.US'),100,'N','User within the system - Internal or Business Partner Contact','de.metas.shippingnotification',0,10,'The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Ansprechpartner',0,0,TO_TIMESTAMP('2023-09-05 17:03:41.212','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:03:41.329366500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587389 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T14:03:41.331971500Z
/* DDL */  select update_Column_Translation_From_AD_Element(138) 
;

-- 2023-09-05T14:03:43.091354300Z
/* DDL */ SELECT public.db_alter_table('M_Shipping_Notification','ALTER TABLE public.M_Shipping_Notification ADD COLUMN AD_User_ID NUMERIC(10)')
;

-- 2023-09-05T14:03:43.097600800Z
ALTER TABLE M_Shipping_Notification ADD CONSTRAINT ADUser_MShippingNotification FOREIGN KEY (AD_User_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_Shipping_Notification.M_Warehouse_ID
-- 2023-09-05T14:03:53.876327Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587390,459,0,19,542365,'M_Warehouse_ID',TO_TIMESTAMP('2023-09-05 17:03:53.754','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Lager oder Ort für Dienstleistung','de.metas.shippingnotification',0,10,'Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lager',0,0,TO_TIMESTAMP('2023-09-05 17:03:53.754','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:03:53.879510900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587390 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T14:03:53.883222300Z
/* DDL */  select update_Column_Translation_From_AD_Element(459) 
;

-- 2023-09-05T14:03:55.798231200Z
/* DDL */ SELECT public.db_alter_table('M_Shipping_Notification','ALTER TABLE public.M_Shipping_Notification ADD COLUMN M_Warehouse_ID NUMERIC(10)')
;

-- 2023-09-05T14:03:55.802876300Z
ALTER TABLE M_Shipping_Notification ADD CONSTRAINT MWarehouse_MShippingNotification FOREIGN KEY (M_Warehouse_ID) REFERENCES public.M_Warehouse DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_Shipping_Notification.M_Locator_ID
-- 2023-09-05T14:04:09.235903700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587391,448,0,19,542365,'M_Locator_ID',TO_TIMESTAMP('2023-09-05 17:04:09.097','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Lagerort im Lager','de.metas.shippingnotification',0,10,'"Lagerort" bezeichnet, wo im Lager ein Produkt aufzufinden ist.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lagerort',0,0,TO_TIMESTAMP('2023-09-05 17:04:09.097','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:04:09.239108100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587391 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T14:04:09.243353200Z
/* DDL */  select update_Column_Translation_From_AD_Element(448) 
;

-- Column: M_Shipping_Notification.M_Locator_ID
-- 2023-09-05T14:04:20.561227200Z
UPDATE AD_Column SET AD_Val_Rule_ID=127,Updated=TO_TIMESTAMP('2023-09-05 17:04:20.56','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587391
;

-- 2023-09-05T14:04:22.758087Z
/* DDL */ SELECT public.db_alter_table('M_Shipping_Notification','ALTER TABLE public.M_Shipping_Notification ADD COLUMN M_Locator_ID NUMERIC(10)')
;

-- 2023-09-05T14:04:22.763750300Z
ALTER TABLE M_Shipping_Notification ADD CONSTRAINT MLocator_MShippingNotification FOREIGN KEY (M_Locator_ID) REFERENCES public.M_Locator DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_Shipping_Notification.C_Harvesting_Calendar_ID
-- 2023-09-05T14:04:44.565833700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587392,581157,0,18,540260,542365,'C_Harvesting_Calendar_ID',TO_TIMESTAMP('2023-09-05 17:04:44.441','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.shippingnotification',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Erntekalender',0,0,TO_TIMESTAMP('2023-09-05 17:04:44.441','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:04:44.567911900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587392 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T14:04:44.570496900Z
/* DDL */  select update_Column_Translation_From_AD_Element(581157) 
;

-- 2023-09-05T14:04:46.570486600Z
/* DDL */ SELECT public.db_alter_table('M_Shipping_Notification','ALTER TABLE public.M_Shipping_Notification ADD COLUMN C_Harvesting_Calendar_ID NUMERIC(10)')
;

-- 2023-09-05T14:04:46.574626300Z
ALTER TABLE M_Shipping_Notification ADD CONSTRAINT CHarvestingCalendar_MShippingNotification FOREIGN KEY (C_Harvesting_Calendar_ID) REFERENCES public.C_Calendar DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_Shipping_Notification.Harvesting_Year_ID
-- 2023-09-05T14:05:09.725197300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587393,582471,0,18,540133,542365,540655,'Harvesting_Year_ID',TO_TIMESTAMP('2023-09-05 17:05:09.271','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.shippingnotification',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Erntejahr',0,0,TO_TIMESTAMP('2023-09-05 17:05:09.271','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:05:09.726754500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587393 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T14:05:09.729345700Z
/* DDL */  select update_Column_Translation_From_AD_Element(582471) 
;

-- 2023-09-05T14:05:13.198989500Z
/* DDL */ SELECT public.db_alter_table('M_Shipping_Notification','ALTER TABLE public.M_Shipping_Notification ADD COLUMN Harvesting_Year_ID NUMERIC(10)')
;

-- 2023-09-05T14:05:13.204158600Z
ALTER TABLE M_Shipping_Notification ADD CONSTRAINT HarvestingYear_MShippingNotification FOREIGN KEY (Harvesting_Year_ID) REFERENCES public.C_Year DEFERRABLE INITIALLY DEFERRED
;

-- 2023-09-05T14:06:24.004126100Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582696,0,'PhysicalClearanceDate',TO_TIMESTAMP('2023-09-05 17:06:23.87','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.shippingnotification','Y','Physical Clearance Date','Physical Clearance Date',TO_TIMESTAMP('2023-09-05 17:06:23.87','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-05T14:06:24.006205100Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582696 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Shipping_Notification.PhysicalClearanceDate
-- 2023-09-05T14:06:45.313590Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587394,582696,0,15,542365,'PhysicalClearanceDate',TO_TIMESTAMP('2023-09-05 17:06:45.209','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.shippingnotification',0,7,'B','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Physical Clearance Date',0,0,TO_TIMESTAMP('2023-09-05 17:06:45.209','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:06:45.316767600Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587394 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T14:06:45.320472300Z
/* DDL */  select update_Column_Translation_From_AD_Element(582696) 
;

-- 2023-09-05T14:07:03.601910Z
/* DDL */ SELECT public.db_alter_table('M_Shipping_Notification','ALTER TABLE public.M_Shipping_Notification ADD COLUMN PhysicalClearanceDate TIMESTAMP WITHOUT TIME ZONE')
;

-- Column: M_Shipping_Notification.Description
-- 2023-09-05T14:07:28.795921900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587395,275,0,36,542365,'Description',TO_TIMESTAMP('2023-09-05 17:07:28.648','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.shippingnotification',0,2000,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Beschreibung',0,0,TO_TIMESTAMP('2023-09-05 17:07:28.648','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:07:28.798030300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587395 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T14:07:28.800621200Z
/* DDL */  select update_Column_Translation_From_AD_Element(275) 
;

-- Column: M_Shipping_Notification.DocumentNo
-- 2023-09-05T14:08:02.759879400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587396,290,0,10,542365,'DocumentNo',TO_TIMESTAMP('2023-09-05 17:08:02.645','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Document sequence number of the document','de.metas.shippingnotification',0,60,'E','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','Y','N','N','N','N','N','Y','N','N','N','N','N','Y','Y',0,'Nr.',0,1,TO_TIMESTAMP('2023-09-05 17:08:02.645','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:08:02.761959700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587396 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T14:08:02.764547200Z
/* DDL */  select update_Column_Translation_From_AD_Element(290) 
;

-- 2023-09-05T14:08:04.622989500Z
/* DDL */ SELECT public.db_alter_table('M_Shipping_Notification','ALTER TABLE public.M_Shipping_Notification ADD COLUMN DocumentNo VARCHAR(60)')
;

-- Column: M_Shipping_Notification.C_DocType_ID
-- 2023-09-05T14:08:20.132915Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587397,196,0,19,542365,'C_DocType_ID',TO_TIMESTAMP('2023-09-05 17:08:20.017','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Belegart oder Verarbeitungsvorgaben','de.metas.shippingnotification',0,10,'Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Belegart',0,0,TO_TIMESTAMP('2023-09-05 17:08:20.017','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:08:20.135515600Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587397 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T14:08:20.137590400Z
/* DDL */  select update_Column_Translation_From_AD_Element(196) 
;

-- 2023-09-05T14:08:22.963909900Z
/* DDL */ SELECT public.db_alter_table('M_Shipping_Notification','ALTER TABLE public.M_Shipping_Notification ADD COLUMN C_DocType_ID NUMERIC(10) NOT NULL')
;

-- 2023-09-05T14:08:22.968040700Z
ALTER TABLE M_Shipping_Notification ADD CONSTRAINT CDocType_MShippingNotification FOREIGN KEY (C_DocType_ID) REFERENCES public.C_DocType DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_Shipping_Notification.Posted
-- 2023-09-05T14:08:42.952841600Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587398,1308,0,17,234,542365,'Posted',TO_TIMESTAMP('2023-09-05 17:08:42.768','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Buchungsstatus','de.metas.shippingnotification',0,1,'Zeigt den Verbuchungsstatus der Hauptbuchpositionen an.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Buchungsstatus',0,0,TO_TIMESTAMP('2023-09-05 17:08:42.768','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:08:42.954400100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587398 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T14:08:42.956987800Z
/* DDL */  select update_Column_Translation_From_AD_Element(1308) 
;

-- 2023-09-05T14:08:44.856127500Z
/* DDL */ SELECT public.db_alter_table('M_Shipping_Notification','ALTER TABLE public.M_Shipping_Notification ADD COLUMN Posted CHAR(1)')
;

-- Column: M_Shipping_Notification.C_Auction_ID
-- 2023-09-05T14:09:13.583907800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587399,582634,0,19,542365,'C_Auction_ID',TO_TIMESTAMP('2023-09-05 17:09:13.442','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.shippingnotification',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Auktion',0,0,TO_TIMESTAMP('2023-09-05 17:09:13.442','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:09:13.587101400Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587399 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T14:09:13.591326200Z
/* DDL */  select update_Column_Translation_From_AD_Element(582634) 
;

-- 2023-09-05T14:09:15.272514Z
/* DDL */ SELECT public.db_alter_table('M_Shipping_Notification','ALTER TABLE public.M_Shipping_Notification ADD COLUMN C_Auction_ID NUMERIC(10)')
;

-- 2023-09-05T14:09:15.276707900Z
ALTER TABLE M_Shipping_Notification ADD CONSTRAINT CAuction_MShippingNotification FOREIGN KEY (C_Auction_ID) REFERENCES public.C_Auction DEFERRABLE INITIALLY DEFERRED
;

-- 2023-09-05T14:11:40.327873900Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582697,0,'ShipFromAddress',TO_TIMESTAMP('2023-09-05 17:11:40.198','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.shippingnotification','Y','ShipFromAddress','Ship From Address',TO_TIMESTAMP('2023-09-05 17:11:40.198','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-05T14:11:40.339100400Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582697 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Shipping_Notification.ShipFromAddress
-- 2023-09-05T14:12:03.228453400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587400,582697,0,36,542365,'ShipFromAddress',TO_TIMESTAMP('2023-09-05 17:12:03.104','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.shippingnotification',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'ShipFromAddress',0,0,TO_TIMESTAMP('2023-09-05 17:12:03.104','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:12:03.230534200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587400 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T14:12:03.233157400Z
/* DDL */  select update_Column_Translation_From_AD_Element(582697) 
;

-- 2023-09-05T14:12:05.041590200Z
/* DDL */ SELECT public.db_alter_table('M_Shipping_Notification','ALTER TABLE public.M_Shipping_Notification ADD COLUMN ShipFromAddress TEXT')
;

-- Column: M_Shipping_Notification.BPartnerAddress
-- 2023-09-05T14:13:53.451097400Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-09-05 17:13:53.45','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587388
;

-- 2023-09-05T14:13:54.724675700Z
INSERT INTO t_alter_column values('m_shipping_notification','BPartnerAddress','TEXT',null,null)
;

-- 2023-09-05T14:13:54.728305700Z
INSERT INTO t_alter_column values('m_shipping_notification','BPartnerAddress',null,'NOT NULL',null)
;

-- Column: M_Shipping_Notification.C_BPartner_ID
-- 2023-09-05T14:14:04.209690500Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-09-05 17:14:04.209','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587386
;

-- 2023-09-05T14:14:05.597311100Z
INSERT INTO t_alter_column values('m_shipping_notification','C_BPartner_ID','NUMERIC(10)',null,null)
;

-- 2023-09-05T14:14:05.599427Z
INSERT INTO t_alter_column values('m_shipping_notification','C_BPartner_ID',null,'NOT NULL',null)
;

-- Column: M_Shipping_Notification.C_BPartner_Location_ID
-- 2023-09-05T14:14:13.995971800Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-09-05 17:14:13.995','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587387
;

-- 2023-09-05T14:14:17.748783900Z
INSERT INTO t_alter_column values('m_shipping_notification','C_BPartner_Location_ID','NUMERIC(10)',null,null)
;

-- 2023-09-05T14:14:17.750364300Z
INSERT INTO t_alter_column values('m_shipping_notification','C_BPartner_Location_ID',null,'NOT NULL',null)
;

-- Column: M_Shipping_Notification.C_Harvesting_Calendar_ID
-- 2023-09-05T14:14:45.619373500Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-09-05 17:14:45.618','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587392
;

-- 2023-09-05T14:14:47.361439Z
INSERT INTO t_alter_column values('m_shipping_notification','C_Harvesting_Calendar_ID','NUMERIC(10)',null,null)
;

-- 2023-09-05T14:14:47.363572700Z
INSERT INTO t_alter_column values('m_shipping_notification','C_Harvesting_Calendar_ID',null,'NOT NULL',null)
;

-- Column: M_Shipping_Notification.Harvesting_Year_ID
-- 2023-09-05T14:14:55.474315200Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-09-05 17:14:55.474','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587393
;

-- 2023-09-05T14:14:59.024807100Z
INSERT INTO t_alter_column values('m_shipping_notification','Harvesting_Year_ID','NUMERIC(10)',null,null)
;

-- 2023-09-05T14:14:59.025847800Z
INSERT INTO t_alter_column values('m_shipping_notification','Harvesting_Year_ID',null,'NOT NULL',null)
;

-- Column: M_Shipping_Notification.M_Warehouse_ID
-- 2023-09-05T14:15:14.641315800Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-09-05 17:15:14.64','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587390
;

-- 2023-09-05T14:15:15.920587400Z
INSERT INTO t_alter_column values('m_shipping_notification','M_Warehouse_ID','NUMERIC(10)',null,null)
;

-- 2023-09-05T14:15:15.922134500Z
INSERT INTO t_alter_column values('m_shipping_notification','M_Warehouse_ID',null,'NOT NULL',null)
;

-- Column: M_Shipping_Notification.M_Locator_ID
-- 2023-09-05T14:15:24.089095600Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-09-05 17:15:24.088','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587391
;

-- 2023-09-05T14:15:25.353079500Z
INSERT INTO t_alter_column values('m_shipping_notification','M_Locator_ID','NUMERIC(10)',null,null)
;

-- 2023-09-05T14:15:25.354117800Z
INSERT INTO t_alter_column values('m_shipping_notification','M_Locator_ID',null,'NOT NULL',null)
;

-- Column: M_Shipping_Notification.PhysicalClearanceDate
-- 2023-09-05T14:15:36.019515100Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-09-05 17:15:36.018','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587394
;

-- 2023-09-05T14:15:37.642189300Z
INSERT INTO t_alter_column values('m_shipping_notification','PhysicalClearanceDate','TIMESTAMP WITHOUT TIME ZONE',null,null)
;

-- 2023-09-05T14:15:37.643227Z
INSERT INTO t_alter_column values('m_shipping_notification','PhysicalClearanceDate',null,'NOT NULL',null)
;

-- Column: M_Shipping_Notification.DocumentNo
-- 2023-09-05T14:15:54.130369100Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-09-05 17:15:54.13','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587396
;

-- 2023-09-05T14:15:56.006536400Z
INSERT INTO t_alter_column values('m_shipping_notification','DocumentNo','VARCHAR(60)',null,null)
;

-- 2023-09-05T14:15:56.008088Z
INSERT INTO t_alter_column values('m_shipping_notification','DocumentNo',null,'NOT NULL',null)
;

-- Column: M_Shipping_Notification.Harvesting_Year_ID
-- 2023-09-05T14:16:42.657272Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-09-05 17:16:42.656','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587393
;

-- Column: M_Shipping_Notification.C_BPartner_ID
-- 2023-09-05T14:17:01.200671200Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-09-05 17:17:01.2','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587386
;

-- Column: M_Shipping_Notification.M_Warehouse_ID
-- 2023-09-05T14:17:07.970405600Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-09-05 17:17:07.969','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587390
;

-- Table: M_Shipping_NotificationLine
-- 2023-09-05T14:56:10.948991300Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542366,'N',TO_TIMESTAMP('2023-09-05 17:56:10.687','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.shippingnotification','N','Y','N','N','Y','N','N','N','N','N',0,'Shipping Notification Line','NP','L','M_Shipping_NotificationLine','DTI',TO_TIMESTAMP('2023-09-05 17:56:10.687','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:56:10.951574500Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542366 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-09-05T14:56:11.038706600Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556303,TO_TIMESTAMP('2023-09-05 17:56:10.958','YYYY-MM-DD HH24:MI:SS.US'),100,1000000,50000,'Table M_Shipping_NotificationLine',1,'Y','N','Y','Y','M_Shipping_NotificationLine','N',1000000,TO_TIMESTAMP('2023-09-05 17:56:10.958','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-05T14:56:11.044395400Z
CREATE SEQUENCE M_SHIPPING_NOTIFICATIONLINE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: M_Shipping_NotificationLine.AD_Client_ID
-- 2023-09-05T14:56:25.201760900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587404,102,0,19,542366,'AD_Client_ID',TO_TIMESTAMP('2023-09-05 17:56:25.089','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Mandant für diese Installation.','de.metas.shippingnotification',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2023-09-05 17:56:25.089','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:56:25.205479100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587404 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T14:56:25.210263300Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Column: M_Shipping_NotificationLine.AD_Org_ID
-- 2023-09-05T14:56:26.208394800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587405,113,0,30,542366,'AD_Org_ID',TO_TIMESTAMP('2023-09-05 17:56:25.935','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Organisatorische Einheit des Mandanten','de.metas.shippingnotification',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2023-09-05 17:56:25.935','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:56:26.210992800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587405 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T14:56:26.213579200Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: M_Shipping_NotificationLine.Created
-- 2023-09-05T14:56:26.827435700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587406,245,0,16,542366,'Created',TO_TIMESTAMP('2023-09-05 17:56:26.615','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','de.metas.shippingnotification',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2023-09-05 17:56:26.615','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:56:26.829510100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587406 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T14:56:26.832139400Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: M_Shipping_NotificationLine.CreatedBy
-- 2023-09-05T14:56:27.371685700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587407,246,0,18,110,542366,'CreatedBy',TO_TIMESTAMP('2023-09-05 17:56:27.139','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag erstellt hat','de.metas.shippingnotification',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2023-09-05 17:56:27.139','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:56:27.373777300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587407 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T14:56:27.376907300Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: M_Shipping_NotificationLine.IsActive
-- 2023-09-05T14:56:27.906995600Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587408,348,0,20,542366,'IsActive',TO_TIMESTAMP('2023-09-05 17:56:27.672','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Der Eintrag ist im System aktiv','de.metas.shippingnotification',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2023-09-05 17:56:27.672','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:56:27.909091800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587408 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T14:56:27.912231700Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: M_Shipping_NotificationLine.Updated
-- 2023-09-05T14:56:28.454599700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587409,607,0,16,542366,'Updated',TO_TIMESTAMP('2023-09-05 17:56:28.229','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','de.metas.shippingnotification',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2023-09-05 17:56:28.229','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:56:28.456675100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587409 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T14:56:28.458759400Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: M_Shipping_NotificationLine.UpdatedBy
-- 2023-09-05T14:56:29.018600Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587410,608,0,18,110,542366,'UpdatedBy',TO_TIMESTAMP('2023-09-05 17:56:28.78','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','de.metas.shippingnotification',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2023-09-05 17:56:28.78','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:56:29.020679500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587410 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T14:56:29.022753300Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- 2023-09-05T14:56:29.444140900Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582701,0,'M_Shipping_NotificationLine_ID',TO_TIMESTAMP('2023-09-05 17:56:29.365','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.shippingnotification','Y','Shipping Notification Line','Shipping Notification Line',TO_TIMESTAMP('2023-09-05 17:56:29.365','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-05T14:56:29.446831400Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582701 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_Shipping_NotificationLine.M_Shipping_NotificationLine_ID
-- 2023-09-05T14:56:29.937364400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587411,582701,0,13,542366,'M_Shipping_NotificationLine_ID',TO_TIMESTAMP('2023-09-05 17:56:29.361','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.shippingnotification',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Shipping Notification Line',0,0,TO_TIMESTAMP('2023-09-05 17:56:29.361','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:56:29.939429800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587411 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T14:56:29.942580700Z
/* DDL */  select update_Column_Translation_From_AD_Element(582701) 
;

-- Column: M_Shipping_NotificationLine.M_Shipping_Notification_ID
-- 2023-09-05T14:56:51.191078400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587412,582695,0,19,542366,'M_Shipping_Notification_ID',TO_TIMESTAMP('2023-09-05 17:56:51.064','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.shippingnotification',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Shipping Notification',0,0,TO_TIMESTAMP('2023-09-05 17:56:51.064','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:56:51.194197Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587412 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T14:56:51.198844400Z
/* DDL */  select update_Column_Translation_From_AD_Element(582695) 
;

-- Column: M_Shipping_NotificationLine.Line
-- 2023-09-05T14:59:07.545903Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587413,439,0,11,542366,'Line',TO_TIMESTAMP('2023-09-05 17:59:07.443','YYYY-MM-DD HH24:MI:SS.US'),100,'N','@SQL=SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM M_Shipping_NotificationLine WHERE M_Shipping_Notification_ID=@M_Shipping_Notification_ID@','Einzelne Zeile in dem Dokument','de.metas.shippingnotification',0,22,'Indicates the unique line for a document.  It will also control the display order of the lines within a document.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Zeile Nr.',0,0,TO_TIMESTAMP('2023-09-05 17:59:07.443','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:59:07.548479900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587413 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T14:59:07.551079700Z
/* DDL */  select update_Column_Translation_From_AD_Element(439) 
;

-- Column: M_Shipping_NotificationLine.M_Product_ID
-- 2023-09-05T14:59:20.329380800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587414,454,0,19,542366,'M_Product_ID',TO_TIMESTAMP('2023-09-05 17:59:20.2','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Produkt, Leistung, Artikel','de.metas.shippingnotification',0,10,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Produkt',0,0,TO_TIMESTAMP('2023-09-05 17:59:20.2','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:59:20.331967700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587414 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T14:59:20.335075700Z
/* DDL */  select update_Column_Translation_From_AD_Element(454) 
;

-- Column: M_Shipping_NotificationLine.MovementQty
-- 2023-09-05T14:59:28.807292400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587415,1038,0,29,542366,'MovementQty',TO_TIMESTAMP('2023-09-05 17:59:28.68','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Menge eines bewegten Produktes.','de.metas.shippingnotification',0,10,'Die "Bewegungs-Menge" bezeichnet die Menge einer Ware, die bewegt wurde.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Bewegungs-Menge',0,0,TO_TIMESTAMP('2023-09-05 17:59:28.68','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:59:28.810479200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587415 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T14:59:28.814206800Z
/* DDL */  select update_Column_Translation_From_AD_Element(1038) 
;

-- Column: M_Shipping_NotificationLine.C_UOM_ID
-- 2023-09-05T14:59:34.332226500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587416,215,0,19,542366,'C_UOM_ID',TO_TIMESTAMP('2023-09-05 17:59:34.22','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Maßeinheit','de.metas.shippingnotification',0,10,'Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Maßeinheit',0,0,TO_TIMESTAMP('2023-09-05 17:59:34.22','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:59:34.334303200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587416 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T14:59:34.336892100Z
/* DDL */  select update_Column_Translation_From_AD_Element(215) 
;

-- Column: M_Shipping_NotificationLine.C_OrderLine_ID
-- 2023-09-05T14:59:46.430499800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587417,561,0,19,542366,'C_OrderLine_ID',TO_TIMESTAMP('2023-09-05 17:59:46.303','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Auftragsposition','de.metas.shippingnotification',0,10,'"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Auftragsposition',0,0,TO_TIMESTAMP('2023-09-05 17:59:46.303','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:59:46.432052800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587417 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T14:59:46.434641300Z
/* DDL */  select update_Column_Translation_From_AD_Element(561) 
;

-- Column: M_Shipping_NotificationLine.C_OrderLine_ID
-- 2023-09-05T14:59:49.546112900Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-09-05 17:59:49.546','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587417
;

-- Column: M_Shipping_NotificationLine.M_ShipmentSchedule_ID
-- 2023-09-05T14:59:58.871546300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587418,500221,0,19,542366,'M_ShipmentSchedule_ID',TO_TIMESTAMP('2023-09-05 17:59:58.746','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.shippingnotification',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Lieferdisposition',0,0,TO_TIMESTAMP('2023-09-05 17:59:58.746','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T14:59:58.873614300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587418 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T14:59:58.875690Z
/* DDL */  select update_Column_Translation_From_AD_Element(500221) 
;

-- Column: M_Shipping_NotificationLine.M_AttributeSetInstance_ID
-- 2023-09-05T15:00:06.714406700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587419,2019,0,35,542366,'M_AttributeSetInstance_ID',TO_TIMESTAMP('2023-09-05 18:00:06.611','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Merkmals Ausprägungen zum Produkt','de.metas.shippingnotification',0,10,'The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Merkmale',0,0,TO_TIMESTAMP('2023-09-05 18:00:06.611','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-05T15:00:06.716485500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587419 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-05T15:00:06.719074100Z
/* DDL */  select update_Column_Translation_From_AD_Element(2019) 
;

-- Column: M_Shipping_NotificationLine.M_Product_ID
-- 2023-09-05T15:00:16.136114100Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-09-05 18:00:16.135','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587414
;

-- Column: M_Shipping_NotificationLine.MovementQty
-- 2023-09-05T15:00:27.024997Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-09-05 18:00:27.024','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587415
;

-- 2023-09-05T15:00:30.506124600Z
/* DDL */ CREATE TABLE public.M_Shipping_NotificationLine (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_OrderLine_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, C_UOM_ID NUMERIC(10), IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, Line NUMERIC(10) NOT NULL, M_AttributeSetInstance_ID NUMERIC(10), MovementQty NUMERIC NOT NULL, M_Product_ID NUMERIC(10) NOT NULL, M_ShipmentSchedule_ID NUMERIC(10) NOT NULL, M_Shipping_Notification_ID NUMERIC(10) NOT NULL, M_Shipping_NotificationLine_ID NUMERIC(10) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT COrderLine_MShippingNotificationLine FOREIGN KEY (C_OrderLine_ID) REFERENCES public.C_OrderLine DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CUOM_MShippingNotificationLine FOREIGN KEY (C_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MAttributeSetInstance_MShippingNotificationLine FOREIGN KEY (M_AttributeSetInstance_ID) REFERENCES public.M_AttributeSetInstance DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MProduct_MShippingNotificationLine FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MShipmentSchedule_MShippingNotificationLine FOREIGN KEY (M_ShipmentSchedule_ID) REFERENCES public.M_ShipmentSchedule DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MShippingNotification_MShippingNotificationLine FOREIGN KEY (M_Shipping_Notification_ID) REFERENCES public.M_Shipping_Notification DEFERRABLE INITIALLY DEFERRED, CONSTRAINT M_Shipping_NotificationLine_Key PRIMARY KEY (M_Shipping_NotificationLine_ID))
;

-- 2023-09-06T13:09:33.883084900Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582713,0,TO_TIMESTAMP('2023-09-06 16:09:33.682','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.shippingnotification','Y','Shipping Notification','Shipping Notification',TO_TIMESTAMP('2023-09-06 16:09:33.682','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:09:33.888242200Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582713 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-09-06T13:10:19.814458400Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferavis', PrintName='Lieferavis',Updated=TO_TIMESTAMP('2023-09-06 16:10:19.813','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582713 AND AD_Language='de_CH'
;

-- 2023-09-06T13:10:19.819118Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582713,'de_CH') 
;

-- Element: null
-- 2023-09-06T13:10:24.792826500Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferavis', PrintName='Lieferavis',Updated=TO_TIMESTAMP('2023-09-06 16:10:24.792','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582713 AND AD_Language='de_DE'
;

-- 2023-09-06T13:10:24.797045400Z
UPDATE AD_Element SET Name='Lieferavis', PrintName='Lieferavis' WHERE AD_Element_ID=582713
;

-- 2023-09-06T13:10:25.026833900Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582713,'de_DE') 
;

-- 2023-09-06T13:10:25.029998900Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582713,'de_DE') 
;

-- Element: null
-- 2023-09-06T13:10:47.384564400Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-09-06 16:10:47.384','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582713 AND AD_Language='en_US'
;

-- 2023-09-06T13:10:47.387683200Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582713,'en_US') 
;

-- Window: Lieferavis, InternalName=null
-- 2023-09-06T13:11:07.911132900Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,582713,0,541734,TO_TIMESTAMP('2023-09-06 16:11:07.793','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.shippingnotification','Y','N','N','N','N','N','N','Y','Lieferavis','N',TO_TIMESTAMP('2023-09-06 16:11:07.793','YYYY-MM-DD HH24:MI:SS.US'),100,'M',0,0,100)
;

-- 2023-09-06T13:11:07.913729700Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541734 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-09-06T13:11:07.916830900Z
/* DDL */  select update_window_translation_from_ad_element(582713) 
;

-- 2023-09-06T13:11:07.925643700Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541734
;

-- 2023-09-06T13:11:07.929326Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541734)
;

-- Tab: Lieferavis(541734,de.metas.inout) -> Shipping Notification
-- Table: M_Shipping_Notification
-- 2023-09-06T13:11:42.426252Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582695,0,547218,542365,541734,'Y',TO_TIMESTAMP('2023-09-06 16:11:42.292','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.shippingnotification','N','N','A','M_Shipping_Notification','Y','N','Y','Y','N','N','N','Y','Y','Y','N','N','N','Y','Y','N','N','N',0,'Shipping Notification','N',10,0,TO_TIMESTAMP('2023-09-06 16:11:42.292','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:11:42.429399Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547218 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-09-06T13:11:42.431470600Z
/* DDL */  select update_tab_translation_from_ad_element(582695) 
;

-- 2023-09-06T13:11:42.434055400Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547218)
;

-- Tab: Lieferavis(541734,de.metas.inout) -> Shipping Notification
-- Table: M_Shipping_Notification
-- 2023-09-06T13:11:48.346135Z
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-09-06 16:11:48.345','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547218
;

-- Tab: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout)
-- UI Section: main
-- 2023-09-06T13:11:58.236661800Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547218,545811,TO_TIMESTAMP('2023-09-06 16:11:58.097','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-09-06 16:11:58.097','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2023-09-06T13:11:58.238739800Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545811 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main
-- UI Column: 10
-- 2023-09-06T13:11:58.369910300Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547076,545811,TO_TIMESTAMP('2023-09-06 16:11:58.266','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-09-06 16:11:58.266','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Section: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main
-- UI Column: 20
-- 2023-09-06T13:11:58.457110Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547077,545811,TO_TIMESTAMP('2023-09-06 16:11:58.375','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',20,TO_TIMESTAMP('2023-09-06 16:11:58.375','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 10
-- UI Element Group: default
-- 2023-09-06T13:11:58.594743100Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547076,551131,TO_TIMESTAMP('2023-09-06 16:11:58.481','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','default',10,'primary',TO_TIMESTAMP('2023-09-06 16:11:58.481','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Tab: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line
-- Table: M_Shipping_NotificationLine
-- 2023-09-06T13:12:32.614175200Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582701,0,547219,542366,541734,'Y',TO_TIMESTAMP('2023-09-06 16:12:32.468','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.shippingnotification','N','N','A','M_Shipping_NotificationLine','Y','N','Y','Y','N','N','N','N','Y','Y','Y','N','N','Y','Y','N','N','N',0,'Shipping Notification Line',587380,'N',20,1,TO_TIMESTAMP('2023-09-06 16:12:32.468','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:12:32.616824200Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547219 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-09-06T13:12:32.619491800Z
/* DDL */  select update_tab_translation_from_ad_element(582701) 
;

-- 2023-09-06T13:12:32.623175600Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547219)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Mandant
-- Column: M_Shipping_Notification.AD_Client_ID
-- 2023-09-06T13:12:53.824302700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587373,720405,0,547218,TO_TIMESTAMP('2023-09-06 16:12:53.699','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'de.metas.shippingnotification','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-09-06 16:12:53.699','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:12:53.837840500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720405 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:12:53.850158Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-09-06T13:12:54.403668Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720405
;

-- 2023-09-06T13:12:54.406027600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720405)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Organisation
-- Column: M_Shipping_Notification.AD_Org_ID
-- 2023-09-06T13:12:54.495474400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587374,720406,0,547218,TO_TIMESTAMP('2023-09-06 16:12:54.41','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.shippingnotification','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-09-06 16:12:54.41','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:12:54.497030900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720406 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:12:54.499101500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-09-06T13:12:54.689591Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720406
;

-- 2023-09-06T13:12:54.691191Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720406)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Aktiv
-- Column: M_Shipping_Notification.IsActive
-- 2023-09-06T13:12:54.776284800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587377,720407,0,547218,TO_TIMESTAMP('2023-09-06 16:12:54.694','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'de.metas.shippingnotification','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-09-06 16:12:54.694','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:12:54.777923600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720407 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:12:54.779480800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-09-06T13:12:55.008408800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720407
;

-- 2023-09-06T13:12:55.010055600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720407)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Shipping Notification
-- Column: M_Shipping_Notification.M_Shipping_Notification_ID
-- 2023-09-06T13:12:55.107942Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587380,720408,0,547218,TO_TIMESTAMP('2023-09-06 16:12:55.013','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.shippingnotification','Y','N','N','N','N','N','N','N','Shipping Notification',TO_TIMESTAMP('2023-09-06 16:12:55.013','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:12:55.110031500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720408 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:12:55.111591900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582695) 
;

-- 2023-09-06T13:12:55.113684Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720408
;

-- 2023-09-06T13:12:55.114203900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720408)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Belegverarbeitung
-- Column: M_Shipping_Notification.DocAction
-- 2023-09-06T13:12:55.192654500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587381,720409,0,547218,TO_TIMESTAMP('2023-09-06 16:12:55.116','YYYY-MM-DD HH24:MI:SS.US'),100,'Der zukünftige Status des Belegs',2,'de.metas.shippingnotification','You find the current status in the Document Status field. The options are listed in a popup','Y','N','N','N','N','N','N','N','Belegverarbeitung',TO_TIMESTAMP('2023-09-06 16:12:55.116','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:12:55.194204800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720409 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:12:55.195764200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(287) 
;

-- 2023-09-06T13:12:55.206627700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720409
;

-- 2023-09-06T13:12:55.207699300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720409)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Belegstatus
-- Column: M_Shipping_Notification.DocStatus
-- 2023-09-06T13:12:55.295134900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587382,720410,0,547218,TO_TIMESTAMP('2023-09-06 16:12:55.21','YYYY-MM-DD HH24:MI:SS.US'),100,'The current status of the document',2,'de.metas.shippingnotification','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','N','N','N','N','N','N','N','Belegstatus',TO_TIMESTAMP('2023-09-06 16:12:55.21','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:12:55.296691200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720410 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:12:55.298759Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(289) 
;

-- 2023-09-06T13:12:55.312139700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720410
;

-- 2023-09-06T13:12:55.313194400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720410)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Process Now
-- Column: M_Shipping_Notification.Processing
-- 2023-09-06T13:12:55.397405600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587383,720411,0,547218,TO_TIMESTAMP('2023-09-06 16:12:55.316','YYYY-MM-DD HH24:MI:SS.US'),100,1,'de.metas.shippingnotification','Y','N','N','N','N','N','N','N','Process Now',TO_TIMESTAMP('2023-09-06 16:12:55.316','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:12:55.399569200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720411 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:12:55.401667600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(524) 
;

-- 2023-09-06T13:12:55.438891100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720411
;

-- 2023-09-06T13:12:55.440538200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720411)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Verarbeitet
-- Column: M_Shipping_Notification.Processed
-- 2023-09-06T13:12:55.519501100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587384,720412,0,547218,TO_TIMESTAMP('2023-09-06 16:12:55.443','YYYY-MM-DD HH24:MI:SS.US'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'de.metas.shippingnotification','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2023-09-06 16:12:55.443','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:12:55.521095400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720412 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:12:55.522650500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047) 
;

-- 2023-09-06T13:12:55.545386700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720412
;

-- 2023-09-06T13:12:55.546442500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720412)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Referenz
-- Column: M_Shipping_Notification.POReference
-- 2023-09-06T13:12:55.636574700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587385,720413,0,547218,TO_TIMESTAMP('2023-09-06 16:12:55.55','YYYY-MM-DD HH24:MI:SS.US'),100,'Referenz-Nummer des Kunden',40,'de.metas.shippingnotification','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','N','N','N','N','N','N','Referenz',TO_TIMESTAMP('2023-09-06 16:12:55.55','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:12:55.643154200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720413 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:12:55.649549500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(952) 
;

-- 2023-09-06T13:12:55.680110200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720413
;

-- 2023-09-06T13:12:55.682310400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720413)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Geschäftspartner
-- Column: M_Shipping_Notification.C_BPartner_ID
-- 2023-09-06T13:12:55.802078200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587386,720414,0,547218,TO_TIMESTAMP('2023-09-06 16:12:55.685','YYYY-MM-DD HH24:MI:SS.US'),100,'Bezeichnet einen Geschäftspartner',10,'de.metas.shippingnotification','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2023-09-06 16:12:55.685','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:12:55.806697200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720414 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:12:55.810990300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2023-09-06T13:12:55.840835300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720414
;

-- 2023-09-06T13:12:55.842620300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720414)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Standort
-- Column: M_Shipping_Notification.C_BPartner_Location_ID
-- 2023-09-06T13:12:55.924909700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587387,720415,0,547218,TO_TIMESTAMP('2023-09-06 16:12:55.845','YYYY-MM-DD HH24:MI:SS.US'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',10,'de.metas.shippingnotification','Identifiziert die Adresse des Geschäftspartners','Y','N','N','N','N','N','N','N','Standort',TO_TIMESTAMP('2023-09-06 16:12:55.845','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:12:55.926485300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720415 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:12:55.928054300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(189) 
;

-- 2023-09-06T13:12:55.936818500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720415
;

-- 2023-09-06T13:12:55.937859700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720415)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Anschrift-Text
-- Column: M_Shipping_Notification.BPartnerAddress
-- 2023-09-06T13:12:56.022223Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587388,720416,0,547218,TO_TIMESTAMP('2023-09-06 16:12:55.94','YYYY-MM-DD HH24:MI:SS.US'),100,2000,'de.metas.shippingnotification','Y','N','N','N','N','N','N','N','Anschrift-Text',TO_TIMESTAMP('2023-09-06 16:12:55.94','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:12:56.023821600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720416 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:12:56.025379400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(505103) 
;

-- 2023-09-06T13:12:56.030076100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720416
;

-- 2023-09-06T13:12:56.031114500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720416)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Ansprechpartner
-- Column: M_Shipping_Notification.AD_User_ID
-- 2023-09-06T13:12:56.120192500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587389,720417,0,547218,TO_TIMESTAMP('2023-09-06 16:12:56.033','YYYY-MM-DD HH24:MI:SS.US'),100,'User within the system - Internal or Business Partner Contact',10,'de.metas.shippingnotification','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','N','N','N','N','N','Ansprechpartner',TO_TIMESTAMP('2023-09-06 16:12:56.033','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:12:56.122261800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720417 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:12:56.123294500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(138) 
;

-- 2023-09-06T13:12:56.136101100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720417
;

-- 2023-09-06T13:12:56.136696200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720417)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Lager
-- Column: M_Shipping_Notification.M_Warehouse_ID
-- 2023-09-06T13:12:56.228405100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587390,720418,0,547218,TO_TIMESTAMP('2023-09-06 16:12:56.139','YYYY-MM-DD HH24:MI:SS.US'),100,'Lager oder Ort für Dienstleistung',10,'de.metas.shippingnotification','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','N','N','N','N','N','N','Lager',TO_TIMESTAMP('2023-09-06 16:12:56.139','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:12:56.230482500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720418 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:12:56.232041100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(459) 
;

-- 2023-09-06T13:12:56.242387600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720418
;

-- 2023-09-06T13:12:56.243426400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720418)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Lagerort
-- Column: M_Shipping_Notification.M_Locator_ID
-- 2023-09-06T13:12:56.330046500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587391,720419,0,547218,TO_TIMESTAMP('2023-09-06 16:12:56.246','YYYY-MM-DD HH24:MI:SS.US'),100,'Lagerort im Lager',10,'de.metas.shippingnotification','"Lagerort" bezeichnet, wo im Lager ein Produkt aufzufinden ist.','Y','N','N','N','N','N','N','N','Lagerort',TO_TIMESTAMP('2023-09-06 16:12:56.246','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:12:56.331601500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720419 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:12:56.333158700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(448) 
;

-- 2023-09-06T13:12:56.346548700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720419
;

-- 2023-09-06T13:12:56.347633300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720419)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Erntekalender
-- Column: M_Shipping_Notification.C_Harvesting_Calendar_ID
-- 2023-09-06T13:12:56.443063200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587392,720420,0,547218,TO_TIMESTAMP('2023-09-06 16:12:56.35','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.shippingnotification','Y','N','N','N','N','N','N','N','Erntekalender',TO_TIMESTAMP('2023-09-06 16:12:56.35','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:12:56.445140100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720420 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:12:56.447212300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581157) 
;

-- 2023-09-06T13:12:56.450826Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720420
;

-- 2023-09-06T13:12:56.451343600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720420)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Erntejahr
-- Column: M_Shipping_Notification.Harvesting_Year_ID
-- 2023-09-06T13:12:56.534404100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587393,720421,0,547218,TO_TIMESTAMP('2023-09-06 16:12:56.453','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.shippingnotification','Y','N','N','N','N','N','N','N','Erntejahr',TO_TIMESTAMP('2023-09-06 16:12:56.453','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:12:56.535957700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720421 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:12:56.538026400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582471) 
;

-- 2023-09-06T13:12:56.541121100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720421
;

-- 2023-09-06T13:12:56.541638500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720421)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Physical Clearance Date
-- Column: M_Shipping_Notification.PhysicalClearanceDate
-- 2023-09-06T13:12:56.633215700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587394,720422,0,547218,TO_TIMESTAMP('2023-09-06 16:12:56.543','YYYY-MM-DD HH24:MI:SS.US'),100,7,'de.metas.shippingnotification','Y','N','N','N','N','N','N','N','Physical Clearance Date',TO_TIMESTAMP('2023-09-06 16:12:56.543','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:12:56.635287Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720422 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:12:56.636837900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582696) 
;

-- 2023-09-06T13:12:56.639433600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720422
;

-- 2023-09-06T13:12:56.639951100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720422)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Beschreibung
-- Column: M_Shipping_Notification.Description
-- 2023-09-06T13:12:56.723712600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587395,720423,0,547218,TO_TIMESTAMP('2023-09-06 16:12:56.642','YYYY-MM-DD HH24:MI:SS.US'),100,2000,'de.metas.shippingnotification','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2023-09-06 16:12:56.642','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:12:56.725780600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720423 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:12:56.727845700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2023-09-06T13:12:56.809089200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720423
;

-- 2023-09-06T13:12:56.810512700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720423)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Nr.
-- Column: M_Shipping_Notification.DocumentNo
-- 2023-09-06T13:12:56.895373900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587396,720424,0,547218,TO_TIMESTAMP('2023-09-06 16:12:56.813','YYYY-MM-DD HH24:MI:SS.US'),100,'Document sequence number of the document',60,'de.metas.shippingnotification','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','N','N','N','N','N','Nr.',TO_TIMESTAMP('2023-09-06 16:12:56.813','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:12:56.896960900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720424 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:12:56.899027700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(290) 
;

-- 2023-09-06T13:12:56.906777600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720424
;

-- 2023-09-06T13:12:56.907297100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720424)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Belegart
-- Column: M_Shipping_Notification.C_DocType_ID
-- 2023-09-06T13:12:56.984456800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587397,720425,0,547218,TO_TIMESTAMP('2023-09-06 16:12:56.91','YYYY-MM-DD HH24:MI:SS.US'),100,'Belegart oder Verarbeitungsvorgaben',10,'de.metas.shippingnotification','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','N','N','N','N','N','N','Belegart',TO_TIMESTAMP('2023-09-06 16:12:56.91','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:12:56.986528900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720425 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:12:56.987565200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(196) 
;

-- 2023-09-06T13:12:57.003691Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720425
;

-- 2023-09-06T13:12:57.004734800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720425)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Buchungsstatus
-- Column: M_Shipping_Notification.Posted
-- 2023-09-06T13:12:57.093996200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587398,720426,0,547218,TO_TIMESTAMP('2023-09-06 16:12:57.007','YYYY-MM-DD HH24:MI:SS.US'),100,'Buchungsstatus',1,'de.metas.shippingnotification','Zeigt den Verbuchungsstatus der Hauptbuchpositionen an.','Y','N','N','N','N','N','N','N','Buchungsstatus',TO_TIMESTAMP('2023-09-06 16:12:57.007','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:12:57.095547600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720426 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:12:57.097105Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1308) 
;

-- 2023-09-06T13:12:57.104866400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720426
;

-- 2023-09-06T13:12:57.105385Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720426)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Auktion
-- Column: M_Shipping_Notification.C_Auction_ID
-- 2023-09-06T13:12:57.195737600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587399,720427,0,547218,TO_TIMESTAMP('2023-09-06 16:12:57.108','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.shippingnotification','Y','N','N','N','N','N','N','N','Auktion',TO_TIMESTAMP('2023-09-06 16:12:57.108','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:12:57.197816Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720427 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:12:57.199378900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582634) 
;

-- 2023-09-06T13:12:57.201965100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720427
;

-- 2023-09-06T13:12:57.202482500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720427)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> ShipFromAddress
-- Column: M_Shipping_Notification.ShipFromAddress
-- 2023-09-06T13:12:57.295418700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587400,720428,0,547218,TO_TIMESTAMP('2023-09-06 16:12:57.204','YYYY-MM-DD HH24:MI:SS.US'),100,2000,'de.metas.shippingnotification','Y','N','N','N','N','N','N','N','ShipFromAddress',TO_TIMESTAMP('2023-09-06 16:12:57.204','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:12:57.297495400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720428 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:12:57.300091800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582697) 
;

-- 2023-09-06T13:12:57.302677500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720428
;

-- 2023-09-06T13:12:57.303195200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720428)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> Mandant
-- Column: M_Shipping_NotificationLine.AD_Client_ID
-- 2023-09-06T13:13:17.723869900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587404,720429,0,547219,TO_TIMESTAMP('2023-09-06 16:13:17.609','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'de.metas.shippingnotification','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-09-06 16:13:17.609','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:13:17.725438600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720429 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:13:17.726991300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-09-06T13:13:17.809440200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720429
;

-- 2023-09-06T13:13:17.811252800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720429)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> Organisation
-- Column: M_Shipping_NotificationLine.AD_Org_ID
-- 2023-09-06T13:13:17.896580500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587405,720430,0,547219,TO_TIMESTAMP('2023-09-06 16:13:17.814','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.shippingnotification','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-09-06 16:13:17.814','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:13:17.898134Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720430 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:13:17.899684900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-09-06T13:13:17.980493500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720430
;

-- 2023-09-06T13:13:17.981585500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720430)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> Aktiv
-- Column: M_Shipping_NotificationLine.IsActive
-- 2023-09-06T13:13:18.067570500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587408,720431,0,547219,TO_TIMESTAMP('2023-09-06 16:13:17.985','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'de.metas.shippingnotification','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-09-06 16:13:17.985','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:13:18.069644Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720431 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:13:18.071197200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-09-06T13:13:18.143642800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720431
;

-- 2023-09-06T13:13:18.145220600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720431)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> Shipping Notification Line
-- Column: M_Shipping_NotificationLine.M_Shipping_NotificationLine_ID
-- 2023-09-06T13:13:18.221805400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587411,720432,0,547219,TO_TIMESTAMP('2023-09-06 16:13:18.148','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.shippingnotification','Y','N','N','N','N','N','N','N','Shipping Notification Line',TO_TIMESTAMP('2023-09-06 16:13:18.148','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:13:18.223359300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720432 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:13:18.224912700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582701) 
;

-- 2023-09-06T13:13:18.226979600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720432
;

-- 2023-09-06T13:13:18.228013800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720432)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> Shipping Notification
-- Column: M_Shipping_NotificationLine.M_Shipping_Notification_ID
-- 2023-09-06T13:13:18.309452400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587412,720433,0,547219,TO_TIMESTAMP('2023-09-06 16:13:18.23','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.shippingnotification','Y','N','N','N','N','N','N','N','Shipping Notification',TO_TIMESTAMP('2023-09-06 16:13:18.23','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:13:18.311519800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720433 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:13:18.312561400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582695) 
;

-- 2023-09-06T13:13:18.314641Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720433
;

-- 2023-09-06T13:13:18.315671600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720433)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> Zeile Nr.
-- Column: M_Shipping_NotificationLine.Line
-- 2023-09-06T13:13:18.402281500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587413,720434,0,547219,TO_TIMESTAMP('2023-09-06 16:13:18.318','YYYY-MM-DD HH24:MI:SS.US'),100,'Einzelne Zeile in dem Dokument',22,'de.metas.shippingnotification','Indicates the unique line for a document.  It will also control the display order of the lines within a document.','Y','N','N','N','N','N','N','N','Zeile Nr.',TO_TIMESTAMP('2023-09-06 16:13:18.318','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:13:18.404357900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720434 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:13:18.405911300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(439) 
;

-- 2023-09-06T13:13:18.411065700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720434
;

-- 2023-09-06T13:13:18.412099300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720434)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> Produkt
-- Column: M_Shipping_NotificationLine.M_Product_ID
-- 2023-09-06T13:13:18.492211200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587414,720435,0,547219,TO_TIMESTAMP('2023-09-06 16:13:18.414','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt, Leistung, Artikel',10,'de.metas.shippingnotification','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','N','N','N','N','N','Produkt',TO_TIMESTAMP('2023-09-06 16:13:18.414','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:13:18.493778100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720435 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:13:18.495342900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2023-09-06T13:13:18.565289500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720435
;

-- 2023-09-06T13:13:18.566671Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720435)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> Bewegungs-Menge
-- Column: M_Shipping_NotificationLine.MovementQty
-- 2023-09-06T13:13:18.654186800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587415,720436,0,547219,TO_TIMESTAMP('2023-09-06 16:13:18.569','YYYY-MM-DD HH24:MI:SS.US'),100,'Menge eines bewegten Produktes.',10,'de.metas.shippingnotification','Die "Bewegungs-Menge" bezeichnet die Menge einer Ware, die bewegt wurde.','Y','N','N','N','N','N','N','N','Bewegungs-Menge',TO_TIMESTAMP('2023-09-06 16:13:18.569','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:13:18.658588100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720436 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:13:18.662429Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1038) 
;

-- 2023-09-06T13:13:18.676954900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720436
;

-- 2023-09-06T13:13:18.679684200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720436)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> Maßeinheit
-- Column: M_Shipping_NotificationLine.C_UOM_ID
-- 2023-09-06T13:13:18.776327200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587416,720437,0,547219,TO_TIMESTAMP('2023-09-06 16:13:18.687','YYYY-MM-DD HH24:MI:SS.US'),100,'Maßeinheit',10,'de.metas.shippingnotification','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','N','N','N','N','N','Maßeinheit',TO_TIMESTAMP('2023-09-06 16:13:18.687','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:13:18.777884800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720437 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:13:18.779439100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215) 
;

-- 2023-09-06T13:13:18.793712600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720437
;

-- 2023-09-06T13:13:18.794757200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720437)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> Auftragsposition
-- Column: M_Shipping_NotificationLine.C_OrderLine_ID
-- 2023-09-06T13:13:18.885909900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587417,720438,0,547219,TO_TIMESTAMP('2023-09-06 16:13:18.798','YYYY-MM-DD HH24:MI:SS.US'),100,'Auftragsposition',10,'de.metas.shippingnotification','"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.','Y','N','N','N','N','N','N','N','Auftragsposition',TO_TIMESTAMP('2023-09-06 16:13:18.798','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:13:18.887990400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720438 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:13:18.889029Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(561) 
;

-- 2023-09-06T13:13:18.895282800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720438
;

-- 2023-09-06T13:13:18.896317300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720438)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> Lieferdisposition
-- Column: M_Shipping_NotificationLine.M_ShipmentSchedule_ID
-- 2023-09-06T13:13:18.989284700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587418,720439,0,547219,TO_TIMESTAMP('2023-09-06 16:13:18.898','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.shippingnotification','Y','N','N','N','N','N','N','N','Lieferdisposition',TO_TIMESTAMP('2023-09-06 16:13:18.898','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:13:18.990843300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720439 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:13:18.992913400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(500221) 
;

-- 2023-09-06T13:13:18.997038200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720439
;

-- 2023-09-06T13:13:18.997556700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720439)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> Merkmale
-- Column: M_Shipping_NotificationLine.M_AttributeSetInstance_ID
-- 2023-09-06T13:13:19.087759400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587419,720440,0,547219,TO_TIMESTAMP('2023-09-06 16:13:19.0','YYYY-MM-DD HH24:MI:SS.US'),100,'Merkmals Ausprägungen zum Produkt',10,'de.metas.shippingnotification','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','N','N','N','N','N','N','Merkmale',TO_TIMESTAMP('2023-09-06 16:13:19.0','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:13:19.089316100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720440 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:13:19.090871200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2019) 
;

-- 2023-09-06T13:13:19.104831100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720440
;

-- 2023-09-06T13:13:19.106370500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720440)
;

-- Tab: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout)
-- UI Section: main
-- 2023-09-06T13:13:26.713053600Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547219,545812,TO_TIMESTAMP('2023-09-06 16:13:26.607','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-09-06 16:13:26.607','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2023-09-06T13:13:26.715142600Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545812 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> main
-- UI Column: 10
-- 2023-09-06T13:13:26.788943Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547078,545812,TO_TIMESTAMP('2023-09-06 16:13:26.719','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-09-06 16:13:26.719','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> main -> 10
-- UI Element Group: default
-- 2023-09-06T13:13:26.870492100Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547078,551132,TO_TIMESTAMP('2023-09-06 16:13:26.792','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','default',10,'primary',TO_TIMESTAMP('2023-09-06 16:13:26.792','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:13:31.009394Z
/* DDL */ select AD_Element_Link_Create_Missing()
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 10 -> default.Geschäftspartner
-- Column: M_Shipping_Notification.C_BPartner_ID
-- 2023-09-06T13:14:37.093778700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720414,0,547218,551131,620436,'F',TO_TIMESTAMP('2023-09-06 16:14:36.968','YYYY-MM-DD HH24:MI:SS.US'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','Geschäftspartner',10,0,0,TO_TIMESTAMP('2023-09-06 16:14:36.968','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:14:55.493024900Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,720417,0,541728,620436,TO_TIMESTAMP('2023-09-06 16:14:55.354','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,'widget',TO_TIMESTAMP('2023-09-06 16:14:55.354','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:15:04.058811900Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,720415,0,541729,620436,TO_TIMESTAMP('2023-09-06 16:15:03.924','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',20,'widget',TO_TIMESTAMP('2023-09-06 16:15:03.924','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 10 -> default.Erntekalender
-- Column: M_Shipping_Notification.C_Harvesting_Calendar_ID
-- 2023-09-06T13:15:42.132617300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720420,0,547218,551131,620437,'F',TO_TIMESTAMP('2023-09-06 16:15:42.018','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Erntekalender',20,0,0,TO_TIMESTAMP('2023-09-06 16:15:42.018','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 10 -> default.Erntejahr
-- Column: M_Shipping_Notification.Harvesting_Year_ID
-- 2023-09-06T13:15:55.656059800Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720421,0,547218,551131,620438,'F',TO_TIMESTAMP('2023-09-06 16:15:55.545','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Erntejahr',30,0,0,TO_TIMESTAMP('2023-09-06 16:15:55.545','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 10 -> default.Lager
-- Column: M_Shipping_Notification.M_Warehouse_ID
-- 2023-09-06T13:16:31.237812200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720418,0,547218,551131,620439,'F',TO_TIMESTAMP('2023-09-06 16:16:31.084','YYYY-MM-DD HH24:MI:SS.US'),100,'Lager oder Ort für Dienstleistung','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','Y','N','N','Lager',40,0,0,TO_TIMESTAMP('2023-09-06 16:16:31.084','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 10 -> default.Erntejahr
-- Column: M_Shipping_Notification.Harvesting_Year_ID
-- 2023-09-06T13:16:45.041116900Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=620438
;

-- 2023-09-06T13:16:52.142532600Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,720421,0,541730,620437,TO_TIMESTAMP('2023-09-06 16:16:52.035','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,'widget',TO_TIMESTAMP('2023-09-06 16:16:52.035','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:16:57.895154200Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,720419,0,541731,620439,TO_TIMESTAMP('2023-09-06 16:16:57.787','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,'widget',TO_TIMESTAMP('2023-09-06 16:16:57.787','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 10 -> default.Auktion
-- Column: M_Shipping_Notification.C_Auction_ID
-- 2023-09-06T13:17:47.165635100Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720427,0,547218,551131,620440,'F',TO_TIMESTAMP('2023-09-06 16:17:47.045','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Auktion',50,0,0,TO_TIMESTAMP('2023-09-06 16:17:47.045','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 20
-- UI Element Group: primary
-- 2023-09-06T13:18:01.485999400Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547077,551133,TO_TIMESTAMP('2023-09-06 16:18:01.377','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','primary',10,TO_TIMESTAMP('2023-09-06 16:18:01.377','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 20 -> primary.Belegart
-- Column: M_Shipping_Notification.C_DocType_ID
-- 2023-09-06T13:19:02.072350600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720425,0,547218,551133,620441,'F',TO_TIMESTAMP('2023-09-06 16:19:01.917','YYYY-MM-DD HH24:MI:SS.US'),100,'Belegart oder Verarbeitungsvorgaben','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','Y','N','N','Belegart',10,0,0,TO_TIMESTAMP('2023-09-06 16:19:01.917','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 20 -> primary.Nr.
-- Column: M_Shipping_Notification.DocumentNo
-- 2023-09-06T13:19:14.919082500Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720424,0,547218,551133,620442,'F',TO_TIMESTAMP('2023-09-06 16:19:14.784','YYYY-MM-DD HH24:MI:SS.US'),100,'Document sequence number of the document','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','N','N','Nr.',20,0,0,TO_TIMESTAMP('2023-09-06 16:19:14.784','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 20 -> primary.Physical Clearance Date
-- Column: M_Shipping_Notification.PhysicalClearanceDate
-- 2023-09-06T13:19:34.687657300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720422,0,547218,551133,620443,'F',TO_TIMESTAMP('2023-09-06 16:19:34.559','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Physical Clearance Date',30,0,0,TO_TIMESTAMP('2023-09-06 16:19:34.559','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 20 -> primary.Referenz
-- Column: M_Shipping_Notification.POReference
-- 2023-09-06T13:19:45.577623900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720413,0,547218,551133,620444,'F',TO_TIMESTAMP('2023-09-06 16:19:45.452','YYYY-MM-DD HH24:MI:SS.US'),100,'Referenz-Nummer des Kunden','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','Y','N','N','Referenz',40,0,0,TO_TIMESTAMP('2023-09-06 16:19:45.452','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 20 -> primary.Organisation
-- Column: M_Shipping_Notification.AD_Org_ID
-- 2023-09-06T13:20:21.755430800Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720406,0,547218,551133,620445,'F',TO_TIMESTAMP('2023-09-06 16:20:21.636','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Organisation',50,0,0,TO_TIMESTAMP('2023-09-06 16:20:21.636','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 20 -> primary.Organisation
-- Column: M_Shipping_Notification.AD_Org_ID
-- 2023-09-06T13:20:24.823959100Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551133, IsDisplayed='Y', SeqNo=60,Updated=TO_TIMESTAMP('2023-09-06 16:20:24.823','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620445
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 20 -> primary.Mandant
-- Column: M_Shipping_Notification.AD_Client_ID
-- 2023-09-06T13:20:33.230344100Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720405,0,547218,551133,620446,'F',TO_TIMESTAMP('2023-09-06 16:20:33.084','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',70,0,0,TO_TIMESTAMP('2023-09-06 16:20:33.084','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 20
-- UI Element Group: dates
-- 2023-09-06T13:21:03.302335300Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547077,551134,TO_TIMESTAMP('2023-09-06 16:21:03.185','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','dates',20,TO_TIMESTAMP('2023-09-06 16:21:03.185','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 20 -> dates.Physical Clearance Date
-- Column: M_Shipping_Notification.PhysicalClearanceDate
-- 2023-09-06T13:21:42.554802700Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551134, SeqNo=10,Updated=TO_TIMESTAMP('2023-09-06 16:21:42.554','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620443
;

-- UI Column: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 20
-- UI Element Group: org
-- 2023-09-06T13:21:55.188530600Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547077,551135,TO_TIMESTAMP('2023-09-06 16:21:55.083','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','org',30,TO_TIMESTAMP('2023-09-06 16:21:55.083','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 20 -> org.Organisation
-- Column: M_Shipping_Notification.AD_Org_ID
-- 2023-09-06T13:22:45.264204200Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551135, SeqNo=10,Updated=TO_TIMESTAMP('2023-09-06 16:22:45.263','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620445
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 20 -> org.Mandant
-- Column: M_Shipping_Notification.AD_Client_ID
-- 2023-09-06T13:22:54.946794800Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551135, SeqNo=20,Updated=TO_TIMESTAMP('2023-09-06 16:22:54.946','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620446
;

-- UI Column: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 20
-- UI Element Group: org
-- 2023-09-06T13:23:22.092347300Z
UPDATE AD_UI_ElementGroup SET SeqNo=40,Updated=TO_TIMESTAMP('2023-09-06 16:23:22.092','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551135
;

-- UI Column: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 20
-- UI Element Group: harv
-- 2023-09-06T13:23:32.671079700Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547077,551136,TO_TIMESTAMP('2023-09-06 16:23:32.536','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','harv',30,TO_TIMESTAMP('2023-09-06 16:23:32.536','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 20
-- UI Element Group: harv
-- 2023-09-06T13:24:03.310084300Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=551136
;

-- UI Column: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 20
-- UI Element Group: org
-- 2023-09-06T13:24:06.793495800Z
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2023-09-06 16:24:06.792','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551135
;

-- Column: M_Shipping_Notification.DateAcct
-- 2023-09-06T13:24:36.869804500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587454,263,0,15,542365,'DateAcct',TO_TIMESTAMP('2023-09-06 16:24:36.739','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Accounting Date','de.metas.shippingnotification',0,7,'The Accounting Date indicates the date to be used on the General Ledger account entries generated from this document. It is also used for any currency conversion.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Buchungsdatum',0,0,TO_TIMESTAMP('2023-09-06 16:24:36.739','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-06T13:24:36.872392100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587454 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-06T13:24:36.875497900Z
/* DDL */  select update_Column_Translation_From_AD_Element(263) 
;

-- Column: M_Shipping_Notification.DateAcct
-- 2023-09-06T13:25:25.589743100Z
UPDATE AD_Column SET DefaultValue='@#Date@', IsMandatory='Y',Updated=TO_TIMESTAMP('2023-09-06 16:25:25.589','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587454
;

-- 2023-09-06T13:25:27.104662600Z
/* DDL */ SELECT public.db_alter_table('M_Shipping_Notification','ALTER TABLE public.M_Shipping_Notification ADD COLUMN DateAcct TIMESTAMP WITHOUT TIME ZONE NOT NULL')
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Buchungsdatum
-- Column: M_Shipping_Notification.DateAcct
-- 2023-09-06T13:25:33.980279700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587454,720441,0,547218,TO_TIMESTAMP('2023-09-06 16:25:33.846','YYYY-MM-DD HH24:MI:SS.US'),100,'Accounting Date',7,'de.metas.shippingnotification','The Accounting Date indicates the date to be used on the General Ledger account entries generated from this document. It is also used for any currency conversion.','Y','N','N','N','N','N','N','N','Buchungsdatum',TO_TIMESTAMP('2023-09-06 16:25:33.846','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:25:33.982364Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720441 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T13:25:33.985495200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(263) 
;

-- 2023-09-06T13:25:33.992282400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720441
;

-- 2023-09-06T13:25:33.998656700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720441)
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 20 -> dates.Buchungsdatum
-- Column: M_Shipping_Notification.DateAcct
-- 2023-09-06T13:27:15.706401800Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720441,0,547218,551134,620447,'F',TO_TIMESTAMP('2023-09-06 16:27:15.506','YYYY-MM-DD HH24:MI:SS.US'),100,'Accounting Date','The Accounting Date indicates the date to be used on the General Ledger account entries generated from this document. It is also used for any currency conversion.','Y','N','Y','N','N','Buchungsdatum',20,0,0,TO_TIMESTAMP('2023-09-06 16:27:15.506','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Belegart
-- Column: M_Shipping_Notification.C_DocType_ID
-- 2023-09-06T13:38:29.101290400Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-09-06 16:38:29.1','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720425
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Nr.
-- Column: M_Shipping_Notification.DocumentNo
-- 2023-09-06T13:38:29.111242900Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-09-06 16:38:29.111','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720424
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Physical Clearance Date
-- Column: M_Shipping_Notification.PhysicalClearanceDate
-- 2023-09-06T13:38:29.118499500Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-09-06 16:38:29.117','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720422
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Geschäftspartner
-- Column: M_Shipping_Notification.C_BPartner_ID
-- 2023-09-06T13:38:29.125348300Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-09-06 16:38:29.125','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720414
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Lager
-- Column: M_Shipping_Notification.M_Warehouse_ID
-- 2023-09-06T13:38:29.132550900Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-09-06 16:38:29.132','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720418
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Erntejahr
-- Column: M_Shipping_Notification.Harvesting_Year_ID
-- 2023-09-06T13:38:29.139195900Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-09-06 16:38:29.139','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720421
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Auktion
-- Column: M_Shipping_Notification.C_Auction_ID
-- 2023-09-06T13:38:29.146481400Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-09-06 16:38:29.146','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720427
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Organisation
-- Column: M_Shipping_Notification.AD_Org_ID
-- 2023-09-06T13:38:29.155903Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-09-06 16:38:29.155','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720406
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> Zeile Nr.
-- Column: M_Shipping_NotificationLine.Line
-- 2023-09-06T13:39:13.101892300Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-09-06 16:39:13.101','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720434
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> Produkt
-- Column: M_Shipping_NotificationLine.M_Product_ID
-- 2023-09-06T13:39:13.108693400Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-09-06 16:39:13.108','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720435
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> Merkmale
-- Column: M_Shipping_NotificationLine.M_AttributeSetInstance_ID
-- 2023-09-06T13:39:13.115952100Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-09-06 16:39:13.115','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720440
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> Bewegungs-Menge
-- Column: M_Shipping_NotificationLine.MovementQty
-- 2023-09-06T13:39:13.124813200Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-09-06 16:39:13.124','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720436
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> Maßeinheit
-- Column: M_Shipping_NotificationLine.C_UOM_ID
-- 2023-09-06T13:39:13.131031400Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-09-06 16:39:13.131','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720437
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> Auftragsposition
-- Column: M_Shipping_NotificationLine.C_OrderLine_ID
-- 2023-09-06T13:39:13.137259300Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-09-06 16:39:13.137','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720438
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> Lieferdisposition
-- Column: M_Shipping_NotificationLine.M_ShipmentSchedule_ID
-- 2023-09-06T13:39:13.143984900Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-09-06 16:39:13.143','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720439
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> Organisation
-- Column: M_Shipping_NotificationLine.AD_Org_ID
-- 2023-09-06T13:39:13.150194300Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-09-06 16:39:13.15','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720430
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 20 -> primary.Belegart
-- Column: M_Shipping_Notification.C_DocType_ID
-- 2023-09-06T13:40:09.542400600Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-09-06 16:40:09.541','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620441
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 20 -> primary.Nr.
-- Column: M_Shipping_Notification.DocumentNo
-- 2023-09-06T13:40:09.550735200Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-09-06 16:40:09.55','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620442
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 20 -> dates.Physical Clearance Date
-- Column: M_Shipping_Notification.PhysicalClearanceDate
-- 2023-09-06T13:40:09.560339700Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-09-06 16:40:09.559','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620443
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 10 -> default.Geschäftspartner
-- Column: M_Shipping_Notification.C_BPartner_ID
-- 2023-09-06T13:40:09.569734700Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-09-06 16:40:09.569','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620436
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 10 -> default.Lager
-- Column: M_Shipping_Notification.M_Warehouse_ID
-- 2023-09-06T13:40:09.578005200Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-09-06 16:40:09.577','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620439
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 10 -> default.Auktion
-- Column: M_Shipping_Notification.C_Auction_ID
-- 2023-09-06T13:40:09.586314900Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-09-06 16:40:09.585','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620440
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 20 -> org.Organisation
-- Column: M_Shipping_Notification.AD_Org_ID
-- 2023-09-06T13:40:09.593561700Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-09-06 16:40:09.593','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620445
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Erntekalender
-- Column: M_Shipping_Notification.C_Harvesting_Calendar_ID
-- 2023-09-06T13:40:21.736859100Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-09-06 16:40:21.736','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720420
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Erntejahr
-- Column: M_Shipping_Notification.Harvesting_Year_ID
-- 2023-09-06T13:40:21.743595400Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-09-06 16:40:21.743','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720421
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Auktion
-- Column: M_Shipping_Notification.C_Auction_ID
-- 2023-09-06T13:40:21.753060100Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-09-06 16:40:21.752','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720427
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Organisation
-- Column: M_Shipping_Notification.AD_Org_ID
-- 2023-09-06T13:40:21.759278700Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-09-06 16:40:21.759','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720406
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> Erntejahr
-- Column: M_Shipping_Notification.Harvesting_Year_ID
-- 2023-09-06T13:40:42.400521800Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-09-06 16:40:42.399','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720421
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 10 -> default.Erntekalender
-- Column: M_Shipping_Notification.C_Harvesting_Calendar_ID
-- 2023-09-06T13:41:17.919837800Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-09-06 16:41:17.919','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620437
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 10 -> default.Lager
-- Column: M_Shipping_Notification.M_Warehouse_ID
-- 2023-09-06T13:41:17.929279300Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-09-06 16:41:17.929','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620439
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 10 -> default.Auktion
-- Column: M_Shipping_Notification.C_Auction_ID
-- 2023-09-06T13:41:17.939849600Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-09-06 16:41:17.939','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620440
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 20 -> org.Organisation
-- Column: M_Shipping_Notification.AD_Org_ID
-- 2023-09-06T13:41:17.946572200Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-09-06 16:41:17.946','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620445
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 10 -> default.Lager
-- Column: M_Shipping_Notification.M_Warehouse_ID
-- 2023-09-06T13:41:36.262111500Z
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2023-09-06 16:41:36.262','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620439
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 10 -> default.Auktion
-- Column: M_Shipping_Notification.C_Auction_ID
-- 2023-09-06T13:41:36.271618300Z
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2023-09-06 16:41:36.271','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620440
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.inout) -> main -> 10 -> default.Geschäftspartner
-- Column: M_Shipping_Notification.C_BPartner_ID
-- 2023-09-06T13:41:36.278433500Z
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2023-09-06 16:41:36.278','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620436
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> main -> 10 -> default.Zeile Nr.
-- Column: M_Shipping_NotificationLine.Line
-- 2023-09-06T13:42:30.007496100Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720434,0,547219,551132,620448,'F',TO_TIMESTAMP('2023-09-06 16:42:29.853','YYYY-MM-DD HH24:MI:SS.US'),100,'Einzelne Zeile in dem Dokument','Indicates the unique line for a document.  It will also control the display order of the lines within a document.','Y','N','Y','N','N','Zeile Nr.',10,0,0,TO_TIMESTAMP('2023-09-06 16:42:29.853','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> main -> 10 -> default.Produkt
-- Column: M_Shipping_NotificationLine.M_Product_ID
-- 2023-09-06T13:42:39.843345200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720435,0,547219,551132,620449,'F',TO_TIMESTAMP('2023-09-06 16:42:39.707','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','Produkt',20,0,0,TO_TIMESTAMP('2023-09-06 16:42:39.707','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> main -> 10 -> default.Merkmale
-- Column: M_Shipping_NotificationLine.M_AttributeSetInstance_ID
-- 2023-09-06T13:42:58.454409700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720440,0,547219,551132,620450,'F',TO_TIMESTAMP('2023-09-06 16:42:58.328','YYYY-MM-DD HH24:MI:SS.US'),100,'Merkmals Ausprägungen zum Produkt','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','Y','N','N','Merkmale',30,0,0,TO_TIMESTAMP('2023-09-06 16:42:58.328','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> main -> 10 -> default.Bewegungs-Menge
-- Column: M_Shipping_NotificationLine.MovementQty
-- 2023-09-06T13:43:15.993763500Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720436,0,547219,551132,620451,'F',TO_TIMESTAMP('2023-09-06 16:43:15.867','YYYY-MM-DD HH24:MI:SS.US'),100,'Menge eines bewegten Produktes.','Die "Bewegungs-Menge" bezeichnet die Menge einer Ware, die bewegt wurde.','Y','N','Y','N','N','Bewegungs-Menge',40,0,0,TO_TIMESTAMP('2023-09-06 16:43:15.867','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> main -> 10 -> default.Maßeinheit
-- Column: M_Shipping_NotificationLine.C_UOM_ID
-- 2023-09-06T13:43:27.338896400Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720437,0,547219,551132,620452,'F',TO_TIMESTAMP('2023-09-06 16:43:27.19','YYYY-MM-DD HH24:MI:SS.US'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','N','Maßeinheit',50,0,0,TO_TIMESTAMP('2023-09-06 16:43:27.19','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> main -> 10 -> default.Auftragsposition
-- Column: M_Shipping_NotificationLine.C_OrderLine_ID
-- 2023-09-06T13:43:38.050387700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720438,0,547219,551132,620453,'F',TO_TIMESTAMP('2023-09-06 16:43:37.946','YYYY-MM-DD HH24:MI:SS.US'),100,'Auftragsposition','"Auftragsposition" bezeichnet eine einzelne Position in einem Auftrag.','Y','N','Y','N','N','Auftragsposition',60,0,0,TO_TIMESTAMP('2023-09-06 16:43:37.946','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> main -> 10 -> default.Lieferdisposition
-- Column: M_Shipping_NotificationLine.M_ShipmentSchedule_ID
-- 2023-09-06T13:43:48.837376300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720439,0,547219,551132,620454,'F',TO_TIMESTAMP('2023-09-06 16:43:48.715','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Lieferdisposition',70,0,0,TO_TIMESTAMP('2023-09-06 16:43:48.715','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> main -> 10 -> default.Organisation
-- Column: M_Shipping_NotificationLine.AD_Org_ID
-- 2023-09-06T13:43:53.997056700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720430,0,547219,551132,620455,'F',TO_TIMESTAMP('2023-09-06 16:43:53.894','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Organisation',80,0,0,TO_TIMESTAMP('2023-09-06 16:43:53.894','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> main -> 10 -> default.Zeile Nr.
-- Column: M_Shipping_NotificationLine.Line
-- 2023-09-06T13:44:20.094326600Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-09-06 16:44:20.093','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620448
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> main -> 10 -> default.Produkt
-- Column: M_Shipping_NotificationLine.M_Product_ID
-- 2023-09-06T13:44:20.104780Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-09-06 16:44:20.104','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620449
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> main -> 10 -> default.Merkmale
-- Column: M_Shipping_NotificationLine.M_AttributeSetInstance_ID
-- 2023-09-06T13:44:20.114164300Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-09-06 16:44:20.114','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620450
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> main -> 10 -> default.Bewegungs-Menge
-- Column: M_Shipping_NotificationLine.MovementQty
-- 2023-09-06T13:44:20.121943900Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-09-06 16:44:20.121','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620451
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> main -> 10 -> default.Maßeinheit
-- Column: M_Shipping_NotificationLine.C_UOM_ID
-- 2023-09-06T13:44:20.129734500Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-09-06 16:44:20.129','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620452
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> main -> 10 -> default.Auftragsposition
-- Column: M_Shipping_NotificationLine.C_OrderLine_ID
-- 2023-09-06T13:44:20.136475100Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-09-06 16:44:20.136','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620453
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> main -> 10 -> default.Lieferdisposition
-- Column: M_Shipping_NotificationLine.M_ShipmentSchedule_ID
-- 2023-09-06T13:44:20.143256300Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-09-06 16:44:20.143','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620454
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> main -> 10 -> default.Organisation
-- Column: M_Shipping_NotificationLine.AD_Org_ID
-- 2023-09-06T13:44:20.152141500Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-09-06 16:44:20.152','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620455
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> main -> 10 -> default.Auftragsposition
-- Column: M_Shipping_NotificationLine.C_OrderLine_ID
-- 2023-09-06T13:44:40.625421700Z
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2023-09-06 16:44:40.624','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620453
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.inout) -> main -> 10 -> default.Lieferdisposition
-- Column: M_Shipping_NotificationLine.M_ShipmentSchedule_ID
-- 2023-09-06T13:44:40.632201100Z
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2023-09-06 16:44:40.631','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620454
;

-- Table: M_Shipping_NotificationLine
-- 2023-09-06T13:45:29.887205300Z
UPDATE AD_Table SET AD_Window_ID=541734,Updated=TO_TIMESTAMP('2023-09-06 16:45:29.885','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=542366
;

-- Table: M_Shipping_Notification
-- 2023-09-06T13:45:48.105422800Z
UPDATE AD_Table SET AD_Window_ID=541734,Updated=TO_TIMESTAMP('2023-09-06 16:45:48.102','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=542365
;

-- Name: Lieferavis
-- Action Type: W
-- Window: Lieferavis(541734,de.metas.inout)
-- 2023-09-06T13:47:21.758361700Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,582713,542113,0,541734,TO_TIMESTAMP('2023-09-06 16:47:21.645','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.shippingnotification','Shipping Notification','Y','N','N','N','N','Lieferavis',TO_TIMESTAMP('2023-09-06 16:47:21.645','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T13:47:21.760437Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542113 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-09-06T13:47:21.763014500Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542113, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542113)
;

-- 2023-09-06T13:47:21.770801900Z
/* DDL */  select update_menu_translation_from_ad_element(582713) 
;

-- Reordering children of `Shipment`
-- Node name: `Shipment (M_InOut)`
-- 2023-09-06T13:47:22.411547900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000087 AND AD_Tree_ID=10
;

-- Node name: `Shipment Restrictions (M_Shipment_Constraint)`
-- 2023-09-06T13:47:22.412591400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540970 AND AD_Tree_ID=10
;

-- Node name: `Shipment Disposition (M_ShipmentSchedule)`
-- 2023-09-06T13:47:22.413624100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540728 AND AD_Tree_ID=10
;

-- Node name: `Shipment disposition export revision (M_ShipmentSchedule_ExportAudit_Item)`
-- 2023-09-06T13:47:22.414661300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541483 AND AD_Tree_ID=10
;

-- Node name: `Empties Return (M_InOut)`
-- 2023-09-06T13:47:22.415698800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540783 AND AD_Tree_ID=10
;

-- Node name: `Shipment Declaration Config (M_Shipment_Declaration_Config)`
-- 2023-09-06T13:47:22.416738Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541251 AND AD_Tree_ID=10
;

-- Node name: `Shipment Declaration (M_Shipment_Declaration)`
-- 2023-09-06T13:47:22.417774200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541250 AND AD_Tree_ID=10
;

-- Node name: `Customer Return (M_InOut)`
-- 2023-09-06T13:47:22.418810900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540841 AND AD_Tree_ID=10
;

-- Node name: `Service/Repair Customer Return (M_InOut)`
-- 2023-09-06T13:47:22.419329600Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53243 AND AD_Tree_ID=10
;

-- Node name: `Picking Terminal (M_Packageable_V)`
-- 2023-09-06T13:47:22.420368400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540868 AND AD_Tree_ID=10
;

-- Node name: `Picking Terminal (v2)`
-- 2023-09-06T13:47:22.421404900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541138 AND AD_Tree_ID=10
;

-- Node name: `Picking Tray Clearing (Prototype)`
-- 2023-09-06T13:47:22.422475500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540971 AND AD_Tree_ID=10
;

-- Node name: `Picking Tray (M_PickingSlot)`
-- 2023-09-06T13:47:22.423503200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540949 AND AD_Tree_ID=10
;

-- Node name: `Picking Slot Trx (M_PickingSlot_Trx)`
-- 2023-09-06T13:47:22.424542900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540950 AND AD_Tree_ID=10
;

-- Node name: `EDI DESADV (EDI_Desadv)`
-- 2023-09-06T13:47:22.425580900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540976 AND AD_Tree_ID=10
;

-- Node name: `Picking candidate (M_Picking_Candidate)`
-- 2023-09-06T13:47:22.426619500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541395 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-09-06T13:47:22.427661400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000060 AND AD_Tree_ID=10
;

-- Node name: `EDI_Desadv_Pack (EDI_Desadv_Pack)`
-- 2023-09-06T13:47:22.428698800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541406 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-09-06T13:47:22.429733300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000068 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2023-09-06T13:47:22.430766100Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000079 AND AD_Tree_ID=10
;

-- Node name: `Picking`
-- 2023-09-06T13:47:22.431804500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541856 AND AD_Tree_ID=10
;

-- Node name: `Lieferavis`
-- 2023-09-06T13:47:22.433354Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542113 AND AD_Tree_ID=10
;

-- Reordering children of `Shipment`
-- Node name: `Lieferavis`
-- 2023-09-06T13:47:28.740087900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542113 AND AD_Tree_ID=10
;

-- Node name: `Shipment (M_InOut)`
-- 2023-09-06T13:47:28.741131300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000087 AND AD_Tree_ID=10
;

-- Node name: `Shipment Restrictions (M_Shipment_Constraint)`
-- 2023-09-06T13:47:28.742723500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540970 AND AD_Tree_ID=10
;

-- Node name: `Shipment Disposition (M_ShipmentSchedule)`
-- 2023-09-06T13:47:28.744811900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540728 AND AD_Tree_ID=10
;

-- Node name: `Shipment disposition export revision (M_ShipmentSchedule_ExportAudit_Item)`
-- 2023-09-06T13:47:28.745859800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541483 AND AD_Tree_ID=10
;

-- Node name: `Empties Return (M_InOut)`
-- 2023-09-06T13:47:28.746906500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540783 AND AD_Tree_ID=10
;

-- Node name: `Shipment Declaration Config (M_Shipment_Declaration_Config)`
-- 2023-09-06T13:47:28.747948500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541251 AND AD_Tree_ID=10
;

-- Node name: `Shipment Declaration (M_Shipment_Declaration)`
-- 2023-09-06T13:47:28.748470700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541250 AND AD_Tree_ID=10
;

-- Node name: `Customer Return (M_InOut)`
-- 2023-09-06T13:47:28.749511900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540841 AND AD_Tree_ID=10
;

-- Node name: `Service/Repair Customer Return (M_InOut)`
-- 2023-09-06T13:47:28.750544400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53243 AND AD_Tree_ID=10
;

-- Node name: `Picking Terminal (M_Packageable_V)`
-- 2023-09-06T13:47:28.751591500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540868 AND AD_Tree_ID=10
;

-- Node name: `Picking Terminal (v2)`
-- 2023-09-06T13:47:28.752633400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541138 AND AD_Tree_ID=10
;

-- Node name: `Picking Tray Clearing (Prototype)`
-- 2023-09-06T13:47:28.753668400Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540971 AND AD_Tree_ID=10
;

-- Node name: `Picking Tray (M_PickingSlot)`
-- 2023-09-06T13:47:28.754710700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540949 AND AD_Tree_ID=10
;

-- Node name: `Picking Slot Trx (M_PickingSlot_Trx)`
-- 2023-09-06T13:47:28.755752300Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540950 AND AD_Tree_ID=10
;

-- Node name: `EDI DESADV (EDI_Desadv)`
-- 2023-09-06T13:47:28.756849500Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540976 AND AD_Tree_ID=10
;

-- Node name: `Picking candidate (M_Picking_Candidate)`
-- 2023-09-06T13:47:28.758396700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541395 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-09-06T13:47:28.759440800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000060 AND AD_Tree_ID=10
;

-- Node name: `EDI_Desadv_Pack (EDI_Desadv_Pack)`
-- 2023-09-06T13:47:28.760484900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541406 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-09-06T13:47:28.761007Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000068 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2023-09-06T13:47:28.762044900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000079 AND AD_Tree_ID=10
;

-- Node name: `Picking`
-- 2023-09-06T13:47:28.763083200Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541856 AND AD_Tree_ID=10
;

-- Name: Lieferavis
-- Action Type: W
-- Window: Lieferavis(541734,de.metas.inout)
-- 2023-09-06T13:59:03.567958500Z
UPDATE AD_Menu SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 16:59:03.567','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Menu_ID=542113
;

-- Tab: Lieferavis(541734,de.metas.inout) -> Shipping Notification
-- Table: M_Shipping_Notification
-- 2023-09-06T14:00:03.693712600Z
UPDATE AD_Tab SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:00:03.693','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547218
;

-- Tab: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line
-- Table: M_Shipping_NotificationLine
-- 2023-09-06T14:00:07.375870400Z
UPDATE AD_Tab SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:00:07.375','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547219
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.shippingnotification) -> Erntejahr
-- Column: M_Shipping_Notification.Harvesting_Year_ID
-- 2023-09-06T14:00:38.416901Z
UPDATE AD_Field SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:00:38.416','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720421
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.shippingnotification) -> Organisation
-- Column: M_Shipping_Notification.AD_Org_ID
-- 2023-09-06T14:00:40.711532500Z
UPDATE AD_Field SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:00:40.711','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720406
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.shippingnotification) -> Aktiv
-- Column: M_Shipping_Notification.IsActive
-- 2023-09-06T14:00:43.030568Z
UPDATE AD_Field SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:00:43.03','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720407
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.shippingnotification) -> Shipping Notification
-- Column: M_Shipping_Notification.M_Shipping_Notification_ID
-- 2023-09-06T14:00:45.641879900Z
UPDATE AD_Field SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:00:45.641','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720408
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.shippingnotification) -> Belegverarbeitung
-- Column: M_Shipping_Notification.DocAction
-- 2023-09-06T14:00:47.919503300Z
UPDATE AD_Field SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:00:47.918','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720409
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.shippingnotification) -> Belegstatus
-- Column: M_Shipping_Notification.DocStatus
-- 2023-09-06T14:00:50.333484600Z
UPDATE AD_Field SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:00:50.332','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720410
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.shippingnotification) -> Process Now
-- Column: M_Shipping_Notification.Processing
-- 2023-09-06T14:00:52.341342900Z
UPDATE AD_Field SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:00:52.341','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720411
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.shippingnotification) -> Verarbeitet
-- Column: M_Shipping_Notification.Processed
-- 2023-09-06T14:00:54.294790500Z
UPDATE AD_Field SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:00:54.294','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720412
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.shippingnotification) -> Referenz
-- Column: M_Shipping_Notification.POReference
-- 2023-09-06T14:00:57.577527600Z
UPDATE AD_Field SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:00:57.577','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720413
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.shippingnotification) -> Geschäftspartner
-- Column: M_Shipping_Notification.C_BPartner_ID
-- 2023-09-06T14:00:59.400112500Z
UPDATE AD_Field SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:00:59.4','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720414
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.shippingnotification) -> Standort
-- Column: M_Shipping_Notification.C_BPartner_Location_ID
-- 2023-09-06T14:01:01.936161700Z
UPDATE AD_Field SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:01:01.935','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720415
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.shippingnotification) -> Anschrift-Text
-- Column: M_Shipping_Notification.BPartnerAddress
-- 2023-09-06T14:01:05.349238900Z
UPDATE AD_Field SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:01:05.349','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720416
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.shippingnotification) -> Ansprechpartner
-- Column: M_Shipping_Notification.AD_User_ID
-- 2023-09-06T14:01:10.880355800Z
UPDATE AD_Field SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:01:10.88','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720417
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.shippingnotification) -> Lager
-- Column: M_Shipping_Notification.M_Warehouse_ID
-- 2023-09-06T14:01:13.063123500Z
UPDATE AD_Field SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:01:13.062','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720418
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.shippingnotification) -> Lagerort
-- Column: M_Shipping_Notification.M_Locator_ID
-- 2023-09-06T14:01:15.453607100Z
UPDATE AD_Field SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:01:15.453','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720419
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.shippingnotification) -> Erntekalender
-- Column: M_Shipping_Notification.C_Harvesting_Calendar_ID
-- 2023-09-06T14:01:17.686215800Z
UPDATE AD_Field SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:01:17.686','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720420
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.shippingnotification) -> Physical Clearance Date
-- Column: M_Shipping_Notification.PhysicalClearanceDate
-- 2023-09-06T14:01:20.416510600Z
UPDATE AD_Field SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:01:20.416','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720422
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.shippingnotification) -> Beschreibung
-- Column: M_Shipping_Notification.Description
-- 2023-09-06T14:01:22.760863200Z
UPDATE AD_Field SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:01:22.76','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720423
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.shippingnotification) -> Nr.
-- Column: M_Shipping_Notification.DocumentNo
-- 2023-09-06T14:01:25.246980100Z
UPDATE AD_Field SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:01:25.246','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720424
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.shippingnotification) -> Belegart
-- Column: M_Shipping_Notification.C_DocType_ID
-- 2023-09-06T14:01:27.327956Z
UPDATE AD_Field SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:01:27.327','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720425
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.shippingnotification) -> Buchungsstatus
-- Column: M_Shipping_Notification.Posted
-- 2023-09-06T14:01:29.478361500Z
UPDATE AD_Field SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:01:29.478','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720426
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.shippingnotification) -> Auktion
-- Column: M_Shipping_Notification.C_Auction_ID
-- 2023-09-06T14:01:32.049185200Z
UPDATE AD_Field SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:01:32.048','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720427
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.shippingnotification) -> ShipFromAddress
-- Column: M_Shipping_Notification.ShipFromAddress
-- 2023-09-06T14:01:34.119298800Z
UPDATE AD_Field SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:01:34.119','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720428
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.shippingnotification) -> Mandant
-- Column: M_Shipping_Notification.AD_Client_ID
-- 2023-09-06T14:01:36.488123900Z
UPDATE AD_Field SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:01:36.487','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720405
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.shippingnotification) -> Buchungsdatum
-- Column: M_Shipping_Notification.DateAcct
-- 2023-09-06T14:01:40.117488800Z
UPDATE AD_Field SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:01:40.116','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720441
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.shippingnotification) -> Merkmale
-- Column: M_Shipping_NotificationLine.M_AttributeSetInstance_ID
-- 2023-09-06T14:01:48.750186100Z
UPDATE AD_Field SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:01:48.75','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720440
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.shippingnotification) -> Lieferdisposition
-- Column: M_Shipping_NotificationLine.M_ShipmentSchedule_ID
-- 2023-09-06T14:01:50.714724500Z
UPDATE AD_Field SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:01:50.714','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720439
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.shippingnotification) -> Auftragsposition
-- Column: M_Shipping_NotificationLine.C_OrderLine_ID
-- 2023-09-06T14:01:52.805609500Z
UPDATE AD_Field SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:01:52.805','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720438
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.shippingnotification) -> Maßeinheit
-- Column: M_Shipping_NotificationLine.C_UOM_ID
-- 2023-09-06T14:01:54.683805200Z
UPDATE AD_Field SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:01:54.683','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720437
;

-- Field: Lieferavis(541734,de.metas.inout) -> Shipping Notification Line(547219,de.metas.shippingnotification) -> Bewegungs-Menge
-- Column: M_Shipping_NotificationLine.MovementQty
-- 2023-09-06T14:01:56.821444900Z
UPDATE AD_Field SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:01:56.82','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720436
;

-- Table: M_Shipping_NotificationLine
-- 2023-09-06T14:02:14.055164600Z
UPDATE AD_Table SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:02:14.053','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=542366
;

-- Column: M_Shipping_NotificationLine.AD_Client_ID
-- 2023-09-06T14:02:25.258059100Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:02:25.257','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587404
;

-- Column: M_Shipping_NotificationLine.AD_Org_ID
-- 2023-09-06T14:02:28.168426100Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:02:28.168','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587405
;

-- Column: M_Shipping_NotificationLine.C_OrderLine_ID
-- 2023-09-06T14:02:31.085995600Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:02:31.085','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587417
;

-- Column: M_Shipping_NotificationLine.Created
-- 2023-09-06T14:02:33.955166500Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:02:33.954','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587406
;

-- Column: M_Shipping_NotificationLine.CreatedBy
-- 2023-09-06T14:02:36.223112400Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:02:36.223','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587407
;

-- Column: M_Shipping_NotificationLine.C_UOM_ID
-- 2023-09-06T14:02:40.882245100Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:02:40.882','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587416
;

-- Column: M_Shipping_NotificationLine.IsActive
-- 2023-09-06T14:02:43.455014100Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:02:43.455','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587408
;

-- Column: M_Shipping_NotificationLine.Line
-- 2023-09-06T14:02:46.531115300Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:02:46.531','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587413
;

-- Column: M_Shipping_NotificationLine.M_AttributeSetInstance_ID
-- 2023-09-06T14:03:01.500402800Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:03:01.499','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587419
;

-- Column: M_Shipping_NotificationLine.MovementQty
-- 2023-09-06T14:03:05.289932200Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:03:05.289','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587415
;

-- Column: M_Shipping_NotificationLine.M_Product_ID
-- 2023-09-06T14:03:08.398697200Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:03:08.398','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587414
;

-- Column: M_Shipping_NotificationLine.M_ShipmentSchedule_ID
-- 2023-09-06T14:03:11.064632500Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:03:11.064','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587418
;

-- Column: M_Shipping_NotificationLine.M_Shipping_Notification_ID
-- 2023-09-06T14:03:14.208424Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:03:14.208','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587412
;

-- Column: M_Shipping_NotificationLine.M_Shipping_NotificationLine_ID
-- 2023-09-06T14:03:16.745318600Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-09-06 17:03:16.745','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587411
;

-- Column: M_Shipping_NotificationLine.Updated
-- 2023-09-06T14:03:19.784213900Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:03:19.784','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587409
;

-- Column: M_Shipping_NotificationLine.UpdatedBy
-- 2023-09-06T14:03:22.319042900Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:03:22.319','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587410
;

-- Table: M_Shipping_Notification
-- 2023-09-06T14:03:38.295316300Z
UPDATE AD_Table SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:03:38.293','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=542365
;

-- Column: M_Shipping_Notification.AD_Client_ID
-- 2023-09-06T14:03:46.152886500Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:03:46.152','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587373
;

-- Column: M_Shipping_Notification.AD_Org_ID
-- 2023-09-06T14:03:48.744904Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:03:48.744','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587374
;

-- Column: M_Shipping_Notification.AD_User_ID
-- 2023-09-06T14:03:51.249580200Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:03:51.249','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587389
;

-- Column: M_Shipping_Notification.BPartnerAddress
-- 2023-09-06T14:03:54.570652500Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:03:54.57','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587388
;

-- Column: M_Shipping_Notification.C_Auction_ID
-- 2023-09-06T14:03:59.182250900Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:03:59.181','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587399
;

-- Column: M_Shipping_Notification.C_BPartner_ID
-- 2023-09-06T14:04:02.126139700Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:04:02.125','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587386
;

-- Column: M_Shipping_Notification.C_BPartner_Location_ID
-- 2023-09-06T14:04:04.441825200Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:04:04.441','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587387
;

-- Column: M_Shipping_Notification.C_DocType_ID
-- 2023-09-06T14:04:06.916173900Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:04:06.915','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587397
;

-- Column: M_Shipping_Notification.C_Harvesting_Calendar_ID
-- 2023-09-06T14:04:09.618411900Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:04:09.617','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587392
;

-- Column: M_Shipping_Notification.Created
-- 2023-09-06T14:04:12.535955400Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:04:12.535','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587375
;

-- Column: M_Shipping_Notification.CreatedBy
-- 2023-09-06T14:04:14.808091500Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:04:14.807','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587376
;

-- Column: M_Shipping_Notification.DateAcct
-- 2023-09-06T14:04:28.236440300Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:04:28.235','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587454
;

-- Column: M_Shipping_Notification.Description
-- 2023-09-06T14:04:35.976157300Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:04:35.975','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587395
;

-- Column: M_Shipping_Notification.DocAction
-- 2023-09-06T14:04:38.481947800Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:04:38.481','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587381
;

-- Column: M_Shipping_Notification.DocStatus
-- 2023-09-06T14:04:41.199706100Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:04:41.199','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587382
;

-- Column: M_Shipping_Notification.DocumentNo
-- 2023-09-06T14:04:45.701756100Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:04:45.701','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587396
;

-- Column: M_Shipping_Notification.Harvesting_Year_ID
-- 2023-09-06T14:04:48.330109Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:04:48.33','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587393
;

-- Column: M_Shipping_Notification.IsActive
-- 2023-09-06T14:07:51.063307600Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:07:51.063','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587377
;

-- Column: M_Shipping_Notification.M_Locator_ID
-- 2023-09-06T14:07:53.911197200Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:07:53.91','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587391
;

-- Column: M_Shipping_Notification.M_Shipping_Notification_ID
-- 2023-09-06T14:07:56.672980600Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-09-06 17:07:56.672','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587380
;

-- Column: M_Shipping_Notification.M_Warehouse_ID
-- 2023-09-06T14:07:59.351813400Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:07:59.351','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587390
;

-- Column: M_Shipping_Notification.PhysicalClearanceDate
-- 2023-09-06T14:08:01.784290500Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:08:01.783','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587394
;

-- Column: M_Shipping_Notification.POReference
-- 2023-09-06T14:08:04.801539900Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:08:04.801','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587385
;

-- Column: M_Shipping_Notification.Posted
-- 2023-09-06T14:08:07.509420300Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:08:07.508','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587398
;

-- Column: M_Shipping_Notification.Processed
-- 2023-09-06T14:08:09.837803400Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:08:09.837','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587384
;

-- Column: M_Shipping_Notification.Processing
-- 2023-09-06T14:08:12.478831600Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:08:12.478','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587383
;

-- Column: M_Shipping_Notification.ShipFromAddress
-- 2023-09-06T14:08:15.040161300Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:08:15.04','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587400
;

-- Column: M_Shipping_Notification.Updated
-- 2023-09-06T14:08:17.498480900Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:08:17.498','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587378
;

-- Column: M_Shipping_Notification.UpdatedBy
-- 2023-09-06T14:08:20.208309200Z
UPDATE AD_Column SET EntityType='de.metas.shippingnotification',Updated=TO_TIMESTAMP('2023-09-06 17:08:20.208','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587379
;

-- Column: C_Order.PhysicalClearanceDate
-- 2023-09-06T14:10:53.232465300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,ReadOnlyLogic,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587455,582696,0,15,259,'PhysicalClearanceDate',TO_TIMESTAMP('2023-09-06 17:10:53.025','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.inout',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Physical Clearance Date','1=2',0,0,TO_TIMESTAMP('2023-09-06 17:10:53.025','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-06T14:10:53.235060900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587455 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-06T14:10:53.240835300Z
/* DDL */  select update_Column_Translation_From_AD_Element(582696) 
;

-- 2023-09-06T14:11:32.060114300Z
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN PhysicalClearanceDate TIMESTAMP WITHOUT TIME ZONE')
;

-- Field: Auftrag(143,D) -> Auftrag(186,D) -> Physical Clearance Date
-- Column: C_Order.PhysicalClearanceDate
-- 2023-09-06T14:13:09.644036500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587455,720442,0,186,0,TO_TIMESTAMP('2023-09-06 17:13:09.532','YYYY-MM-DD HH24:MI:SS.US'),100,0,'de.metas.shippingnotification',0,'Y','Y','Y','N','N','N','N','N','N','Physical Clearance Date',0,760,0,1,1,TO_TIMESTAMP('2023-09-06 17:13:09.532','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-06T14:13:09.647674800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720442 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-06T14:13:09.650258600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582696) 
;

-- 2023-09-06T14:13:09.654915200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720442
;

-- 2023-09-06T14:13:09.656471800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720442)
;

-- UI Element: Auftrag(143,D) -> Auftrag(186,D) -> main view -> 20 -> Dates.Physical Clearance Date
-- Column: C_Order.PhysicalClearanceDate
-- 2023-09-06T14:13:43.363560Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720442,0,186,540920,620456,'F',TO_TIMESTAMP('2023-09-06 17:13:43.244','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Physical Clearance Date',50,0,0,TO_TIMESTAMP('2023-09-06 17:13:43.244','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Auftrag(143,D) -> Auftrag(186,D) -> main view -> 20 -> Dates.Physical Clearance Date
-- Column: C_Order.PhysicalClearanceDate
-- 2023-09-06T14:15:01.614543300Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=620456
;

-- Column: M_ShipmentSchedule.PhysicalClearanceDate
-- 2023-09-06T14:50:23.899471700Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587456,582696,0,15,500221,'PhysicalClearanceDate',TO_TIMESTAMP('2023-09-06 17:50:23.702','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.inoutcandidate',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Physical Clearance Date',0,0,TO_TIMESTAMP('2023-09-06 17:50:23.702','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-06T14:50:23.904758300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587456 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-06T14:50:23.914088700Z
/* DDL */  select update_Column_Translation_From_AD_Element(582696) 
;

-- Column: M_ShipmentSchedule.PhysicalClearanceDate
-- 2023-09-06T14:51:49.676079900Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2023-09-06 17:51:49.676','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587456
;

-- 2023-09-06T14:51:56.176240600Z
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule','ALTER TABLE public.M_ShipmentSchedule ADD COLUMN PhysicalClearanceDate TIMESTAMP WITHOUT TIME ZONE')
;

-- Column: M_ShipmentSchedule.PhysicalClearanceDate
-- 2023-09-06T14:54:07.819748200Z
UPDATE AD_Column SET ReadOnlyLogic='1=2',Updated=TO_TIMESTAMP('2023-09-06 17:54:07.819','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587456
;


--- doc type---



-- Name: C_DocType Shipping Notification
-- 2023-09-06T15:27:51.205443600Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540656,'C_DocType.docbasetype = ''SN''',TO_TIMESTAMP('2023-09-06 18:27:51.028','YYYY-MM-DD HH24:MI:SS.US'),100,'Shipping Notification document type','de.metas.shippingnotification','Y','C_DocType Shipping Notification','S',TO_TIMESTAMP('2023-09-06 18:27:51.028','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: M_Shipping_Notification.C_DocType_ID
-- 2023-09-06T15:28:07.164618Z
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=170, AD_Val_Rule_ID=540656, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2023-09-06 18:28:07.164','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587397
;

-- Reference: C_DocType DocBaseType
-- Value: SN
-- ValueName: Shipping notification
-- 2023-09-07T06:48:31.171799100Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,183,543548,TO_TIMESTAMP('2023-09-07 09:48:30.951','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.shippingnotification','Y','Shipping notification',TO_TIMESTAMP('2023-09-07 09:48:30.951','YYYY-MM-DD HH24:MI:SS.US'),100,'SN','Shipping notification')
;

-- 2023-09-07T06:48:31.200924500Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543548 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: C_DocType DocBaseType -> SN_Shipping notification
-- 2023-09-07T06:48:42.571871200Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Lieferavis',Updated=TO_TIMESTAMP('2023-09-07 09:48:42.571','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543548
;

-- Reference Item: C_DocType DocBaseType -> SN_Shipping notification
-- 2023-09-07T06:48:45.812699300Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-09-07 09:48:45.812','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543548
;

-- Reference Item: C_DocType DocBaseType -> SN_Shipping notification
-- 2023-09-07T06:48:52.812085800Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Lieferavis',Updated=TO_TIMESTAMP('2023-09-07 09:48:52.812','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543548
;

-- 2023-09-07T06:48:52.814180800Z
UPDATE AD_Ref_List SET Name='Lieferavis' WHERE AD_Ref_List_ID=543548
;

-- 2023-09-07T06:59:46.225377100Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewMonth,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556307,TO_TIMESTAMP('2023-09-07 09:59:46.097','YYYY-MM-DD HH24:MI:SS.US'),100,10000000,10000000,'DocumentNo/Value for Table M_ShippingNotification',1,'Y','N','Y','N','DocumentNo_M_ShippingNotification','N','N',10000000,TO_TIMESTAMP('2023-09-07 09:59:46.097','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-07T07:01:27.072434600Z
UPDATE AD_Sequence SET CurrentNext=1000000,Updated=TO_TIMESTAMP('2023-09-07 10:01:27.07','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Sequence_ID=556307
;

---

--
-- Script: /tmp/webui_migration_scripts_2023-09-06_8450706617159403874/5701760_migration_2023-09-07_postgresql.sql
-- User: metasfresh
-- OS user: root
--


-- 2023-09-07T07:14:19.839443049Z
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsExcludeFromCommision,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,1000000,541109,TO_TIMESTAMP('2023-09-07 09:14:19.836','YYYY-MM-DD HH24:MI:SS.US'),100,'MMO',1,'C',0,'N','N','Y','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Shipping Notification','Shipping Notification',TO_TIMESTAMP('2023-09-07 09:14:19.836','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-07T07:14:19.862267022Z
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_DocType_ID=541109 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2023-09-07T07:14:19.866363613Z
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541109 AND rol.IsManual='N')
;

-- 2023-09-07T07:14:32.753712974Z
UPDATE C_DocType_Trl SET Name='Lieferavis',Updated=TO_TIMESTAMP('2023-09-07 09:14:32.753','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_DocType_ID=541109
;

-- 2023-09-07T07:14:34.225755602Z
UPDATE C_DocType_Trl SET PrintName='Lieferavis',Updated=TO_TIMESTAMP('2023-09-07 09:14:34.225','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_DocType_ID=541109
;

-- 2023-09-07T07:14:34.294096336Z
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-09-07 09:14:34.293','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_DocType_ID=541109
;

-- 2023-09-07T07:14:38.027692650Z
UPDATE C_DocType_Trl SET Name='Lieferavis',Updated=TO_TIMESTAMP('2023-09-07 09:14:38.027','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND C_DocType_ID=541109
;

-- 2023-09-07T07:14:38.028502181Z
UPDATE C_DocType SET Name='Lieferavis' WHERE C_DocType_ID=541109
;

-- 2023-09-07T07:14:40.574946451Z
UPDATE C_DocType_Trl SET PrintName='Lieferavis',Updated=TO_TIMESTAMP('2023-09-07 09:14:40.574','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND C_DocType_ID=541109
;

-- 2023-09-07T07:14:40.575528556Z
UPDATE C_DocType SET PrintName='Lieferavis' WHERE C_DocType_ID=541109
;


update c_doctype
set entitytype='de.metas.shippingnotification',
    docnosequence_id=556307,
    docbasetype='SN'
where c_doctype_id=541109;

-- 2023-09-07T08:27:00.888779200Z
/* DDL */ SELECT public.db_alter_table('M_Shipping_Notification','ALTER TABLE public.M_Shipping_Notification ADD COLUMN Description TEXT')
;



-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.shippingnotification) -> main -> 10 -> default.Erntejahr
-- Column: M_Shipping_Notification.Harvesting_Year_ID
-- 2023-09-07T08:41:52.456318600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720421,0,547218,551131,620459,'F',TO_TIMESTAMP('2023-09-07 11:41:52.264','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','Y','N','N','Erntejahr',25,50,0,TO_TIMESTAMP('2023-09-07 11:41:52.264','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-07T08:42:01.907074800Z
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=541730
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.shippingnotification) -> main -> 10 -> default.Geschäftspartner
-- Column: M_Shipping_Notification.C_BPartner_ID
-- 2023-09-07T08:42:19.746327Z
UPDATE AD_UI_Element SET IsMultiLine='Y',Updated=TO_TIMESTAMP('2023-09-07 11:42:19.746','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620436
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.shippingnotification) -> main -> 10 -> default.Geschäftspartner
-- Column: M_Shipping_Notification.C_BPartner_ID
-- 2023-09-07T08:43:47.826482400Z
UPDATE AD_UI_Element SET UIStyle='primary', WidgetSize='L',Updated=TO_TIMESTAMP('2023-09-07 11:43:47.825','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620436
;

-- UI Element: Lieferavis(541734,de.metas.inout) -> Shipping Notification(547218,de.metas.shippingnotification) -> main -> 10 -> default.Geschäftspartner
-- Column: M_Shipping_Notification.C_BPartner_ID
-- 2023-09-07T08:44:10.284823Z
UPDATE AD_UI_Element SET IsMultiLine='N',Updated=TO_TIMESTAMP('2023-09-07 11:44:10.284','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620436
;

-- 2023-09-07T08:45:37.041426700Z
UPDATE AD_UI_ElementField SET SeqNo=10,Updated=TO_TIMESTAMP('2023-09-07 11:45:37.038','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_ElementField_ID=541729
;

-- 2023-09-07T08:45:41.314008700Z
UPDATE AD_UI_ElementField SET SeqNo=20,Updated=TO_TIMESTAMP('2023-09-07 11:45:41.31','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_ElementField_ID=541728
;


-- Column: M_Shipping_NotificationLine.Processed
-- 2023-09-07T11:09:44.058643900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587457,1047,0,20,542366,'Processed',TO_TIMESTAMP('2023-09-07 14:09:43.865','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','de.metas.shippingnotification',0,1,'Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Verarbeitet',0,0,TO_TIMESTAMP('2023-09-07 14:09:43.865','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-07T11:09:44.066446800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587457 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-07T11:09:44.073294500Z
/* DDL */  select update_Column_Translation_From_AD_Element(1047) 
;

-- 2023-09-07T11:09:47.265738800Z
/* DDL */ SELECT public.db_alter_table('M_Shipping_NotificationLine','ALTER TABLE public.M_Shipping_NotificationLine ADD COLUMN Processed CHAR(1) DEFAULT ''N'' CHECK (Processed IN (''Y'',''N'')) NOT NULL')
;

-- Column: M_Shipping_NotificationLine.M_Shipping_Notification_ID
-- 2023-09-08T14:58:52.643444800Z
UPDATE AD_Column SET IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-09-08 17:58:52.643','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587412
;

