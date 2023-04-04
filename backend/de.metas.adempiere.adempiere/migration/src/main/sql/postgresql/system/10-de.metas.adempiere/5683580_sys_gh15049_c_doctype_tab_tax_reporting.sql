-- Name: Tax Reporting Rate Base
-- 2023-03-31T14:45:29.616Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541724,TO_TIMESTAMP('2023-03-31 16:45:29','YYYY-MM-DD HH24:MI:SS'),100,'Contains rules which determine which date is used to select the currency rate displayed in sales invoices. ','D','Y','N','Tax Reporting Rate Base',TO_TIMESTAMP('2023-03-31 16:45:29','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2023-03-31T14:45:29.618Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541724 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2023-03-31T14:45:52.821Z
UPDATE AD_Reference_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-03-31 16:45:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=541724
;

-- 2023-03-31T14:49:25.054Z
UPDATE AD_Reference_Trl SET Description='Enthält Regeln, die festlegen, welches Datum für die Auswahl des in Ausgangsrechnungen angezeigten Währungskurses verwendet wird. ', IsTranslated='Y', Name='Steuerbericht Rate Basis',Updated=TO_TIMESTAMP('2023-03-31 16:49:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541724
;

-- 2023-03-31T14:50:32.651Z
UPDATE AD_Reference_Trl SET Description='Enthält Regeln, die festlegen, welches Datum für die Auswahl des in Ausgangsrechnungen angezeigten Währungskurses verwendet wird. ', IsTranslated='Y', Name='Steuerbericht Rate Basis',Updated=TO_TIMESTAMP('2023-03-31 16:50:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=541724
;

-- 2023-03-31T14:50:32.652Z
UPDATE AD_Reference SET Description='Enthält Regeln, die festlegen, welches Datum für die Auswahl des in Ausgangsrechnungen angezeigten Währungskurses verwendet wird. ', Name='Steuerbericht Rate Basis' WHERE AD_Reference_ID=541724
;

-- Reference: Steuerbericht Rate Basis
-- Value: Goods issue / shipment date
-- ValueName: Goods issue / shipment date
-- 2023-03-31T14:59:19.890Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541724,543424,TO_TIMESTAMP('2023-03-31 16:59:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Goods issue / shipment date',TO_TIMESTAMP('2023-03-31 16:59:19','YYYY-MM-DD HH24:MI:SS'),100,'S','S')
;

-- 2023-03-31T14:59:19.893Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543424 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Steuerbericht Rate Basis
-- Value: 1 business day before invoice date
-- ValueName: 1 business day before invoice date
-- 2023-03-31T14:59:52.439Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541724,543425,TO_TIMESTAMP('2023-03-31 16:59:52','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','1 business day before invoice date',TO_TIMESTAMP('2023-03-31 16:59:52','YYYY-MM-DD HH24:MI:SS'),100,'BI','BI')
;

-- 2023-03-31T14:59:52.440Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543425 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Steuerbericht Rate Basis
-- Value: Invoice date
-- ValueName: Invoice date
-- 2023-03-31T15:00:18.087Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541724,543426,TO_TIMESTAMP('2023-03-31 17:00:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Invoice date',TO_TIMESTAMP('2023-03-31 17:00:17','YYYY-MM-DD HH24:MI:SS'),100,'I','I')
;

-- 2023-03-31T15:00:18.088Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543426 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Steuerbericht Rate Basis -> Goods issue date_Goods issue date
-- 2023-03-31T15:05:49.995Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Warenausgangs- / Lieferdatum',Updated=TO_TIMESTAMP('2023-03-31 17:05:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543424
;

-- Reference Item: Steuerbericht Rate Basis -> Goods issue date_goods issue date
-- 2023-03-31T15:06:08.392Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Warenausgangs- / Lieferdatum',Updated=TO_TIMESTAMP('2023-03-31 17:06:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543424
;

-- 2023-03-31T15:06:08.393Z
UPDATE AD_Ref_List SET Name='Warenausgangs- / Lieferdatum' WHERE AD_Ref_List_ID=543424
;

-- Reference Item: Steuerbericht Rate Basis -> Goods issue date_Goods Issue Date
-- 2023-03-31T15:06:13.887Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-03-31 17:06:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543424
;

-- Reference Item: Steuerbericht Rate Basis -> I_Invoice date
-- 2023-03-31T15:13:20.500Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-03-31 17:13:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543426
;

-- Reference Item: Steuerbericht Rate Basis -> I_Invoice date
-- 2023-03-31T15:13:37.052Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Rechnungsdatum',Updated=TO_TIMESTAMP('2023-03-31 17:13:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543426
;

-- Reference Item: Steuerbericht Rate Basis -> I_Invoice date
-- 2023-03-31T15:13:49.169Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Rechnungsdatum',Updated=TO_TIMESTAMP('2023-03-31 17:13:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543426
;

-- 2023-03-31T15:13:49.170Z
UPDATE AD_Ref_List SET Name='Rechnungsdatum' WHERE AD_Ref_List_ID=543426
;

-- Reference Item: Steuerbericht Rate Basis -> BI_1 business day before invoice date
-- 2023-03-31T15:14:22.252Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-03-31 17:14:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543425
;

-- Reference Item: Steuerbericht Rate Basis -> BI_1 business day before invoice date
-- 2023-03-31T15:14:49.734Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='1 Arbeitstag vor Rechnungsdatum',Updated=TO_TIMESTAMP('2023-03-31 17:14:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543425
;

-- Reference Item: Steuerbericht Rate Basis -> BI_1 business day before invoice date
-- 2023-03-31T15:14:57.126Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='1 Arbeitstag vor Rechnungsdatum',Updated=TO_TIMESTAMP('2023-03-31 17:14:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543425
;

-- 2023-03-31T15:14:57.128Z
UPDATE AD_Ref_List SET Name='1 Arbeitstag vor Rechnungsdatum' WHERE AD_Ref_List_ID=543425
;

-- 2023-04-03T13:18:39.371Z
INSERT INTO C_ConversionType (AD_Client_ID,AD_Org_ID,C_ConversionType_ID,Created,CreatedBy,Description,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,540004,TO_TIMESTAMP('2023-04-03 15:18:39','YYYY-MM-DD HH24:MI:SS'),100,'For local currency conversion (CZ, HU, PL, RO)','Y','Tax Reporting Rate',TO_TIMESTAMP('2023-04-03 15:18:39','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- Table: C_DocType_TaxReporting
-- 2023-04-03T12:26:26.650Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,Description,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TechnicalNote,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542321,'N',TO_TIMESTAMP('2023-04-03 14:26:26','YYYY-MM-DD HH24:MI:SS'),100,'Configuration of tax reports with local currency conversion','D','N','Y','N','N','Y','N','N','N','N','N',0,'Tax Reporting','NP','L','C_DocType_TaxReporting','Configuration of tax reports with local currency conversion used in sales invoice reports','DTI',TO_TIMESTAMP('2023-04-03 14:26:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-03T12:26:26.654Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542321 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-04-03T12:26:27.255Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556256,TO_TIMESTAMP('2023-04-03 14:26:27','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table C_DocType_TaxReporting',1,'Y','N','Y','Y','C_DocType_TaxReporting','N',1000000,TO_TIMESTAMP('2023-04-03 14:26:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-03T12:26:27.274Z
CREATE SEQUENCE C_DocType_TAXREPORTING_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Column: C_DocType_TaxReporting.AD_Client_ID
-- 2023-04-03T12:26:50.899Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586384,102,0,19,542321,'AD_Client_ID',TO_TIMESTAMP('2023-04-03 14:26:50','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2023-04-03 14:26:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-03T12:26:50.902Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586384 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-03T12:26:51.377Z
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- Column: C_DocType_TaxReporting.AD_Org_ID
-- 2023-04-03T12:26:52.355Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586385,113,0,30,542321,'AD_Org_ID',TO_TIMESTAMP('2023-04-03 14:26:52','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Sektion',10,0,TO_TIMESTAMP('2023-04-03 14:26:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-03T12:26:52.357Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586385 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-03T12:26:52.845Z
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- Column: C_DocType_TaxReporting.Created
-- 2023-04-03T12:26:53.490Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586386,245,0,16,542321,'Created',TO_TIMESTAMP('2023-04-03 14:26:53','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2023-04-03 14:26:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-03T12:26:53.492Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586386 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-03T12:26:53.936Z
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- Column: C_DocType_TaxReporting.CreatedBy
-- 2023-04-03T12:26:54.562Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586387,246,0,18,110,542321,'CreatedBy',TO_TIMESTAMP('2023-04-03 14:26:54','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2023-04-03 14:26:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-03T12:26:54.564Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586387 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-03T12:26:55.019Z
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- Column: C_DocType_TaxReporting.IsActive
-- 2023-04-03T12:26:55.647Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586388,348,0,20,542321,'IsActive',TO_TIMESTAMP('2023-04-03 14:26:55','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2023-04-03 14:26:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-03T12:26:55.649Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586388 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-03T12:26:56.160Z
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- Column: C_DocType_TaxReporting.Updated
-- 2023-04-03T12:26:56.791Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586389,607,0,16,542321,'Updated',TO_TIMESTAMP('2023-04-03 14:26:56','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2023-04-03 14:26:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-03T12:26:56.793Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586389 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-03T12:26:57.290Z
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- Column: C_DocType_TaxReporting.UpdatedBy
-- 2023-04-03T12:26:57.925Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586390,608,0,18,110,542321,'UpdatedBy',TO_TIMESTAMP('2023-04-03 14:26:57','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2023-04-03 14:26:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-03T12:26:57.927Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586390 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-03T12:26:58.382Z
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2023-04-03T12:26:58.849Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582203,0,'C_DocType_TaxReporting_ID',TO_TIMESTAMP('2023-04-03 14:26:58','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Tax Reporting','Tax Reporting',TO_TIMESTAMP('2023-04-03 14:26:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-03T12:26:58.853Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582203 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: C_DocType_TaxReporting_ID
-- 2023-04-03T14:11:02.325Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Steuerbericht', PrintName='Steuerbericht',Updated=TO_TIMESTAMP('2023-04-03 16:11:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582203 AND AD_Language='de_CH'
;

-- 2023-04-03T14:11:02.348Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582203,'de_CH')
;

-- Element: C_DocType_TaxReporting_ID
-- 2023-04-03T14:11:03.669Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-04-03 16:11:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582203 AND AD_Language='en_US'
;

-- 2023-04-03T14:11:03.671Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582203,'en_US')
;

-- Element: C_DocType_TaxReporting_ID
-- 2023-04-03T14:11:10.731Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Steuerbericht', PrintName='Steuerbericht',Updated=TO_TIMESTAMP('2023-04-03 16:11:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582203 AND AD_Language='de_DE'
;

-- 2023-04-03T14:11:10.732Z
UPDATE AD_Element SET Name='Steuerbericht', PrintName='Steuerbericht' WHERE AD_Element_ID=582203
;

-- 2023-04-03T14:11:15.200Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582203,'de_DE')
;

-- 2023-04-03T14:11:15.202Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582203,'de_DE')
;

-- Element: C_DocType_TaxReporting_ID
-- 2023-04-03T14:20:27.508Z
UPDATE AD_Element_Trl SET Description='Configuration of tax reports with local currency conversion',Updated=TO_TIMESTAMP('2023-04-03 16:20:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582203 AND AD_Language='en_US'
;

-- 2023-04-03T14:20:27.510Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582203,'en_US')
;

-- Element: C_DocType_TaxReporting_ID
-- 2023-04-03T14:20:32.063Z
UPDATE AD_Element_Trl SET Description='Konfiguration von Steuerberichten mit lokaler Währungsumrechnung',Updated=TO_TIMESTAMP('2023-04-03 16:20:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582203 AND AD_Language='de_DE'
;

-- 2023-04-03T14:20:32.064Z
UPDATE AD_Element SET Description='Konfiguration von Steuerberichten mit lokaler Währungsumrechnung' WHERE AD_Element_ID=582203
;

-- 2023-04-03T14:20:39.826Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582203,'de_DE')
;

-- 2023-04-03T14:20:39.827Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582203,'de_DE')
;

-- Element: C_DocType_TaxReporting_ID
-- 2023-04-03T14:21:03.464Z
UPDATE AD_Element_Trl SET Description='Konfiguration von Steuerberichten mit lokaler Währungsumrechnung',Updated=TO_TIMESTAMP('2023-04-03 16:21:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582203 AND AD_Language='de_CH'
;

-- 2023-04-03T14:21:03.466Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582203,'de_CH')
;

-- Column: C_DocType_TaxReporting.C_DocType_TaxReporting_ID
-- 2023-04-03T12:26:59.410Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586391,582203,0,13,542321,'C_DocType_TaxReporting_ID',TO_TIMESTAMP('2023-04-03 14:26:58','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','N','N','N','N','N','Y','Y','N','N','N','N','Y','N','N','Tax Reporting',0,0,TO_TIMESTAMP('2023-04-03 14:26:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-03T12:26:59.412Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586391 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-03T12:26:59.858Z
/* DDL */  select update_Column_Translation_From_AD_Element(582203)
;

-- Column: C_DocType_TaxReporting.C_DocType_ID
-- 2023-04-03T12:33:34.754Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586392,196,0,30,542321,'C_DocType_ID',TO_TIMESTAMP('2023-04-03 14:33:34','YYYY-MM-DD HH24:MI:SS'),100,'N','Belegart oder Verarbeitungsvorgaben','D',0,10,'Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'Belegart',0,0,TO_TIMESTAMP('2023-04-03 14:33:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-03T12:33:34.757Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586392 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-03T12:33:35.249Z
/* DDL */  select update_Column_Translation_From_AD_Element(196)
;

-- Column: C_DocType_TaxReporting.C_Country_ID
-- 2023-04-03T12:35:59.437Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586393,192,0,19,542321,'C_Country_ID',TO_TIMESTAMP('2023-04-03 14:35:59','YYYY-MM-DD HH24:MI:SS'),100,'N','Land','D',0,10,'"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Land',0,0,TO_TIMESTAMP('2023-04-03 14:35:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-03T12:35:59.439Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586393 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-03T12:35:59.916Z
/* DDL */  select update_Column_Translation_From_AD_Element(192)
;

-- Column: C_DocType_TaxReporting.C_Calendar_ID
-- 2023-04-03T12:47:37.850Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586408,190,0,19,542321,'C_Calendar_ID',TO_TIMESTAMP('2023-04-03 14:47:37','YYYY-MM-DD HH24:MI:SS'),100,'N','Bezeichnung des Buchführungs-Kalenders','D',0,10,'"Kalender" bezeichnet einen eindeutigen Kalender für die Buchhaltung. Es können mehrere Kalender verwendet werden. Z.B. können Sie einen Standardkalender definieren, der vom 1. Jan. bis zum 31. Dez. läuft und einen  fiskalischen, der vom 1. Jul. bis zum 30. Jun. läuft.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Kalender',0,0,TO_TIMESTAMP('2023-04-03 14:47:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-03T12:47:37.852Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586408 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-03T12:47:38.312Z
/* DDL */  select update_Column_Translation_From_AD_Element(190)
;

-- 2023-04-03T12:53:33.682Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582204,0,'TaxReportingRateBase',TO_TIMESTAMP('2023-04-03 14:53:33','YYYY-MM-DD HH24:MI:SS'),100,'Determines which date is used to select the currency rate.','D','Y','Tax Reporting Rate Base','Tax Reporting Rate Base',TO_TIMESTAMP('2023-04-03 14:53:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-03T12:53:33.685Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582204 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: TaxReportingRateBase
-- 2023-04-03T12:54:22.382Z
UPDATE AD_Element_Trl SET Description='Legt fest, welches Datum für die Auswahl des Währungskurses verwendet wird.', IsTranslated='Y', Name='Steuerbericht Rate Basis', PrintName='Steuerbericht Rate Basis',Updated=TO_TIMESTAMP('2023-04-03 14:54:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582204 AND AD_Language='de_CH'
;

-- 2023-04-03T12:54:22.385Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582204,'de_CH')
;

-- Element: TaxReportingRateBase
-- 2023-04-03T12:54:27.763Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-04-03 14:54:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582204 AND AD_Language='en_US'
;

-- 2023-04-03T12:54:27.765Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582204,'en_US')
;

-- Element: TaxReportingRateBase
-- 2023-04-03T12:54:46.944Z
UPDATE AD_Element_Trl SET Description='Legt fest, welches Datum für die Auswahl des Währungskurses verwendet wird.', IsTranslated='Y', Name='Steuerbericht Rate Basis', PrintName='Steuerbericht Rate Basis',Updated=TO_TIMESTAMP('2023-04-03 14:54:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582204 AND AD_Language='de_DE'
;

-- 2023-04-03T12:54:46.945Z
UPDATE AD_Element SET Description='Legt fest, welches Datum für die Auswahl des Währungskurses verwendet wird.', Name='Steuerbericht Rate Basis', PrintName='Steuerbericht Rate Basis' WHERE AD_Element_ID=582204
;

-- 2023-04-03T12:54:47.384Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582204,'de_DE')
;

-- 2023-04-03T12:54:47.385Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582204,'de_DE')
;

-- Column: C_DocType_TaxReporting.TaxReportingRateBase
-- 2023-04-03T12:59:11.187Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586409,582204,0,17,541724,542321,'TaxReportingRateBase',TO_TIMESTAMP('2023-04-03 14:59:11','YYYY-MM-DD HH24:MI:SS'),100,'N','Legt fest, welches Datum für die Auswahl des Währungskurses verwendet wird.','D',0,2,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Steuerbericht Rate Basis',0,0,TO_TIMESTAMP('2023-04-03 14:59:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-03T12:59:11.188Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586409 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-03T12:59:11.638Z
/* DDL */  select update_Column_Translation_From_AD_Element(582204)
;

-- Column: C_DocType_TaxReporting.C_ConversionType_ID
-- 2023-04-03T13:21:23.080Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586410,2278,0,19,542321,'C_ConversionType_ID',TO_TIMESTAMP('2023-04-03 15:21:22','YYYY-MM-DD HH24:MI:SS'),100,'N','540004','Kursart','D',0,10,'Dieses Fenster ermöglicht Ihnen, die verschiedenen Kursarten anzulegen wie z.B. Spot, Firmenrate und/oder Kauf-/Verkaufrate.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Kursart',0,0,TO_TIMESTAMP('2023-04-03 15:21:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-03T13:21:23.085Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586410 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-03T13:21:24.057Z
/* DDL */  select update_Column_Translation_From_AD_Element(2278)
;

-- 2023-04-03T13:24:23.662Z
UPDATE AD_Table_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-04-03 15:24:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=542321
;

-- 2023-04-03T13:24:47.941Z
UPDATE AD_Table_Trl SET IsTranslated='Y', Name='Steuerbericht',Updated=TO_TIMESTAMP('2023-04-03 15:24:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=542321
;

-- 2023-04-03T13:25:01.387Z
UPDATE AD_Table_Trl SET IsTranslated='Y', Name='Steuerbericht',Updated=TO_TIMESTAMP('2023-04-03 15:25:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=542321
;

-- 2023-04-03T13:25:01.388Z
UPDATE AD_Table SET Name='Steuerbericht' WHERE AD_Table_ID=542321
;

-- 2023-04-03T13:27:00.106Z
/* DDL */ CREATE TABLE public.C_DocType_TaxReporting (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_Calendar_ID NUMERIC(10) NOT NULL, C_ConversionType_ID NUMERIC(10) DEFAULT 540004 NOT NULL, C_Country_ID NUMERIC(10) NOT NULL, C_DocType_TaxReporting_ID NUMERIC(10) NOT NULL, C_DocType_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, TaxReportingRateBase VARCHAR(2) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT CCalendar_CDoctTypeTaxReporting FOREIGN KEY (C_Calendar_ID) REFERENCES public.C_Calendar DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CConversionType_CDoctTypeTaxReporting FOREIGN KEY (C_ConversionType_ID) REFERENCES public.C_ConversionType DEFERRABLE INITIALLY DEFERRED, CONSTRAINT CCountry_CDoctTypeTaxReporting FOREIGN KEY (C_Country_ID) REFERENCES public.C_Country DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_DocType_TaxReporting_Key PRIMARY KEY (C_DocType_TaxReporting_ID), CONSTRAINT CDocType_CDoctTypeTaxReporting FOREIGN KEY (C_DocType_ID) REFERENCES public.C_DocType DEFERRABLE INITIALLY DEFERRED)
;

CREATE UNIQUE INDEX C_DocType_TaxReporting_uq ON C_DocType_TaxReporting (c_doctype_id, c_country_id, ad_org_id, ad_client_id)
;

-- Tab: Belegart(135,D) -> Steuerbericht
-- Table: C_DocType_TaxReporting
-- 2023-04-03T14:24:06.144Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,586392,582203,0,546891,542321,135,'Y',TO_TIMESTAMP('2023-04-03 16:24:06','YYYY-MM-DD HH24:MI:SS'),100,'Konfiguration von Steuerberichten mit lokaler Währungsumrechnung','D','N','N','A','C_DocType_TaxReporting','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Steuerbericht',1501,'N',50,1,TO_TIMESTAMP('2023-04-03 16:24:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-03T14:24:06.148Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546891 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-04-03T14:24:06.151Z
/* DDL */  select update_tab_translation_from_ad_element(582203)
;

-- 2023-04-03T14:24:06.162Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546891)
;

-- Field: Belegart(135,D) -> Steuerbericht(546891,D) -> Mandant
-- Column: C_DocType_TaxReporting.AD_Client_ID
-- 2023-04-03T14:25:46.822Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586384,713590,0,546891,TO_TIMESTAMP('2023-04-03 16:25:46','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-04-03 16:25:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-03T14:25:46.825Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=713590 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-04-03T14:25:46.828Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2023-04-03T14:25:48.206Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713590
;

-- 2023-04-03T14:25:48.207Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713590)
;

-- Field: Belegart(135,D) -> Steuerbericht(546891,D) -> Sektion
-- Column: C_DocType_TaxReporting.AD_Org_ID
-- 2023-04-03T14:25:48.294Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586385,713591,0,546891,TO_TIMESTAMP('2023-04-03 16:25:48','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','Sektion',TO_TIMESTAMP('2023-04-03 16:25:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-03T14:25:48.296Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=713591 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-04-03T14:25:48.297Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2023-04-03T14:25:48.684Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713591
;

-- 2023-04-03T14:25:48.685Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713591)
;

-- Field: Belegart(135,D) -> Steuerbericht(546891,D) -> Aktiv
-- Column: C_DocType_TaxReporting.IsActive
-- 2023-04-03T14:25:48.793Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586388,713592,0,546891,TO_TIMESTAMP('2023-04-03 16:25:48','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-04-03 16:25:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-03T14:25:48.795Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=713592 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-04-03T14:25:48.796Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2023-04-03T14:25:48.907Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713592
;

-- 2023-04-03T14:25:48.908Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713592)
;

-- Field: Belegart(135,D) -> Steuerbericht(546891,D) -> Steuerbericht
-- Column: C_DocType_TaxReporting.C_DocType_TaxReporting_ID
-- 2023-04-03T14:25:49.012Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586391,713593,0,546891,TO_TIMESTAMP('2023-04-03 16:25:48','YYYY-MM-DD HH24:MI:SS'),100,'Konfiguration von Steuerberichten mit lokaler Währungsumrechnung',10,'D','Y','N','N','N','N','N','N','N','Steuerbericht',TO_TIMESTAMP('2023-04-03 16:25:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-03T14:25:49.014Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=713593 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-04-03T14:25:49.015Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582203)
;

-- 2023-04-03T14:25:49.019Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713593
;

-- 2023-04-03T14:25:49.020Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713593)
;

-- Field: Belegart(135,D) -> Steuerbericht(546891,D) -> Belegart
-- Column: C_DocType_TaxReporting.C_DocType_ID
-- 2023-04-03T14:25:49.121Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586392,713594,0,546891,TO_TIMESTAMP('2023-04-03 16:25:49','YYYY-MM-DD HH24:MI:SS'),100,'Belegart oder Verarbeitungsvorgaben',10,'D','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','Y','N','N','N','N','N','Belegart',TO_TIMESTAMP('2023-04-03 16:25:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-03T14:25:49.122Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=713594 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-04-03T14:25:49.124Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(196)
;

-- 2023-04-03T14:25:49.195Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713594
;

-- 2023-04-03T14:25:49.196Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713594)
;

-- Field: Belegart(135,D) -> Steuerbericht(546891,D) -> Land
-- Column: C_DocType_TaxReporting.C_Country_ID
-- 2023-04-03T14:25:49.291Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586393,713595,0,546891,TO_TIMESTAMP('2023-04-03 16:25:49','YYYY-MM-DD HH24:MI:SS'),100,'Land',10,'D','"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','Y','N','N','N','N','N','Land',TO_TIMESTAMP('2023-04-03 16:25:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-03T14:25:49.293Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=713595 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-04-03T14:25:49.294Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(192)
;

-- 2023-04-03T14:25:49.306Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713595
;

-- 2023-04-03T14:25:49.308Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713595)
;

-- Field: Belegart(135,D) -> Steuerbericht(546891,D) -> Kalender
-- Column: C_DocType_TaxReporting.C_Calendar_ID
-- 2023-04-03T14:25:49.405Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586408,713596,0,546891,TO_TIMESTAMP('2023-04-03 16:25:49','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnung des Buchführungs-Kalenders',10,'D','"Kalender" bezeichnet einen eindeutigen Kalender für die Buchhaltung. Es können mehrere Kalender verwendet werden. Z.B. können Sie einen Standardkalender definieren, der vom 1. Jan. bis zum 31. Dez. läuft und einen  fiskalischen, der vom 1. Jul. bis zum 30. Jun. läuft.','Y','Y','N','N','N','N','N','Kalender',TO_TIMESTAMP('2023-04-03 16:25:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-03T14:25:49.406Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=713596 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-04-03T14:25:49.408Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(190)
;

-- 2023-04-03T14:25:49.415Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713596
;

-- 2023-04-03T14:25:49.416Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713596)
;

-- Field: Belegart(135,D) -> Steuerbericht(546891,D) -> Steuerbericht Rate Basis
-- Column: C_DocType_TaxReporting.TaxReportingRateBase
-- 2023-04-03T14:25:49.510Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586409,713597,0,546891,TO_TIMESTAMP('2023-04-03 16:25:49','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, welches Datum für die Auswahl des Währungskurses verwendet wird.',2,'D','Y','Y','N','N','N','N','N','Steuerbericht Rate Basis',TO_TIMESTAMP('2023-04-03 16:25:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-03T14:25:49.511Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=713597 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-04-03T14:25:49.512Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582204)
;

-- 2023-04-03T14:25:49.516Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713597
;

-- 2023-04-03T14:25:49.517Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713597)
;

-- Field: Belegart(135,D) -> Steuerbericht(546891,D) -> Kursart
-- Column: C_DocType_TaxReporting.C_ConversionType_ID
-- 2023-04-03T14:25:49.622Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586410,713598,0,546891,TO_TIMESTAMP('2023-04-03 16:25:49','YYYY-MM-DD HH24:MI:SS'),100,'Kursart',10,'D','Dieses Fenster ermöglicht Ihnen, die verschiedenen Kursarten anzulegen wie z.B. Spot, Firmenrate und/oder Kauf-/Verkaufrate.','Y','Y','N','N','N','N','N','Kursart',TO_TIMESTAMP('2023-04-03 16:25:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-03T14:25:49.623Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=713598 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-04-03T14:25:49.624Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2278)
;

-- 2023-04-03T14:25:49.635Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713598
;

-- 2023-04-03T14:25:49.635Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713598)
;

-- Tab: Belegart(135,D) -> Steuerbericht(546891,D)
-- UI Section: main
-- 2023-04-03T14:27:51.586Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546891,545505,TO_TIMESTAMP('2023-04-03 16:27:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-04-03 16:27:51','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-04-03T14:27:51.588Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545505 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Belegart(135,D) -> Steuerbericht(546891,D) -> main
-- UI Column: 10
-- 2023-04-03T14:27:51.736Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546722,545505,TO_TIMESTAMP('2023-04-03 16:27:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-04-03 16:27:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Belegart(135,D) -> Steuerbericht(546891,D) -> main -> 10
-- UI Element Group: default
-- 2023-04-03T14:27:51.872Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546722,550517,TO_TIMESTAMP('2023-04-03 16:27:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2023-04-03 16:27:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Belegart(135,D) -> Steuerbericht(546891,D) -> main -> 10 -> default.Mandant
-- Column: C_DocType_TaxReporting.AD_Client_ID
-- 2023-04-03T14:27:52.019Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,713590,0,546891,550517,616499,'F',TO_TIMESTAMP('2023-04-03 16:27:51','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','Mandant',0,10,0,TO_TIMESTAMP('2023-04-03 16:27:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Belegart(135,D) -> Steuerbericht(546891,D) -> main -> 10 -> default.Sektion
-- Column: C_DocType_TaxReporting.AD_Org_ID
-- 2023-04-03T14:27:52.131Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,713591,0,546891,550517,616500,'F',TO_TIMESTAMP('2023-04-03 16:27:52','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2023-04-03 16:27:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Belegart(135,D) -> Steuerbericht(546891,D) -> main -> 10 -> default.Aktiv
-- Column: C_DocType_TaxReporting.IsActive
-- 2023-04-03T14:27:52.228Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,713592,0,546891,550517,616501,'F',TO_TIMESTAMP('2023-04-03 16:27:52','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','Aktiv',0,30,0,TO_TIMESTAMP('2023-04-03 16:27:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Belegart(135,D) -> Steuerbericht(546891,D) -> main -> 10 -> default.Belegart
-- Column: C_DocType_TaxReporting.C_DocType_ID
-- 2023-04-03T14:27:52.340Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,713594,0,546891,550517,616502,'F',TO_TIMESTAMP('2023-04-03 16:27:52','YYYY-MM-DD HH24:MI:SS'),100,'Belegart oder Verarbeitungsvorgaben','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','N','Y','N','Belegart',0,40,0,TO_TIMESTAMP('2023-04-03 16:27:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Belegart(135,D) -> Steuerbericht(546891,D) -> main -> 10 -> default.Land
-- Column: C_DocType_TaxReporting.C_Country_ID
-- 2023-04-03T14:27:52.438Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,713595,0,546891,550517,616503,'F',TO_TIMESTAMP('2023-04-03 16:27:52','YYYY-MM-DD HH24:MI:SS'),100,'Land','"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','N','N','Y','N','Land',0,50,0,TO_TIMESTAMP('2023-04-03 16:27:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Belegart(135,D) -> Steuerbericht(546891,D) -> main -> 10 -> default.Kalender
-- Column: C_DocType_TaxReporting.C_Calendar_ID
-- 2023-04-03T14:27:52.533Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,713596,0,546891,550517,616504,'F',TO_TIMESTAMP('2023-04-03 16:27:52','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnung des Buchführungs-Kalenders','"Kalender" bezeichnet einen eindeutigen Kalender für die Buchhaltung. Es können mehrere Kalender verwendet werden. Z.B. können Sie einen Standardkalender definieren, der vom 1. Jan. bis zum 31. Dez. läuft und einen  fiskalischen, der vom 1. Jul. bis zum 30. Jun. läuft.','Y','N','N','Y','N','Kalender',0,60,0,TO_TIMESTAMP('2023-04-03 16:27:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Belegart(135,D) -> Steuerbericht(546891,D) -> main -> 10 -> default.Steuerbericht Rate Basis
-- Column: C_DocType_TaxReporting.TaxReportingRateBase
-- 2023-04-03T14:27:52.629Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,713597,0,546891,550517,616505,'F',TO_TIMESTAMP('2023-04-03 16:27:52','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, welches Datum für die Auswahl des Währungskurses verwendet wird.','Y','N','N','Y','N','Steuerbericht Rate Basis',0,70,0,TO_TIMESTAMP('2023-04-03 16:27:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Belegart(135,D) -> Steuerbericht(546891,D) -> main -> 10 -> default.Kursart
-- Column: C_DocType_TaxReporting.C_ConversionType_ID
-- 2023-04-03T14:27:52.733Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,713598,0,546891,550517,616506,'F',TO_TIMESTAMP('2023-04-03 16:27:52','YYYY-MM-DD HH24:MI:SS'),100,'Kursart','Dieses Fenster ermöglicht Ihnen, die verschiedenen Kursarten anzulegen wie z.B. Spot, Firmenrate und/oder Kauf-/Verkaufrate.','Y','N','N','Y','N','Kursart',0,80,0,TO_TIMESTAMP('2023-04-03 16:27:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Belegart(135,D) -> Steuerbericht(546891,D) -> main -> 10 -> default.Mandant
-- Column: C_DocType_TaxReporting.AD_Client_ID
-- 2023-04-03T14:35:01.144Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-04-03 16:35:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616499
;

-- UI Element: Belegart(135,D) -> Steuerbericht(546891,D) -> main -> 10 -> default.Belegart
-- Column: C_DocType_TaxReporting.C_DocType_ID
-- 2023-04-03T14:35:01.152Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-04-03 16:35:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616502
;

-- UI Element: Belegart(135,D) -> Steuerbericht(546891,D) -> main -> 10 -> default.Land
-- Column: C_DocType_TaxReporting.C_Country_ID
-- 2023-04-03T14:35:01.156Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-04-03 16:35:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616503
;

-- UI Element: Belegart(135,D) -> Steuerbericht(546891,D) -> main -> 10 -> default.Kalender
-- Column: C_DocType_TaxReporting.C_Calendar_ID
-- 2023-04-03T14:35:01.160Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-04-03 16:35:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616504
;

-- UI Element: Belegart(135,D) -> Steuerbericht(546891,D) -> main -> 10 -> default.Steuerbericht Rate Basis
-- Column: C_DocType_TaxReporting.TaxReportingRateBase
-- 2023-04-03T14:35:01.164Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-04-03 16:35:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616505
;

-- UI Element: Belegart(135,D) -> Steuerbericht(546891,D) -> main -> 10 -> default.Kursart
-- Column: C_DocType_TaxReporting.C_ConversionType_ID
-- 2023-04-03T14:35:01.168Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-04-03 16:35:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616506
;

-- UI Element: Belegart(135,D) -> Steuerbericht(546891,D) -> main -> 10 -> default.Aktiv
-- Column: C_DocType_TaxReporting.IsActive
-- 2023-04-03T14:35:01.171Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-04-03 16:35:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616501
;

-- UI Element: Belegart(135,D) -> Steuerbericht(546891,D) -> main -> 10 -> default.Sektion
-- Column: C_DocType_TaxReporting.AD_Org_ID
-- 2023-04-03T14:35:01.176Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-04-03 16:35:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616500
;

