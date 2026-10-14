-- Run mode: SWING_CLIENT
--
-- Ruecknahme Gebinde (return-package) feature, core (EntityType 'D').
--
-- Creates the general table C_Order_ReturnPackage (Name 'Rücknahme Gebinde'), a child of C_Order.
-- Depends on the pallet-type reference list AD_Reference 542107 created in migration 5808290.
--
-- PalletType is mandatory (NOT NULL, IsMandatory='Y'): every row is either EUR or H1; a row with no
-- pallet type is meaningless. The business partner is NOT stored here — it is derived via C_Order_ID.
--
-- Per-row design ("Reading B"): QtyDeliveredLU AND QtyReturnedLU are BOTH stored,
-- user-editable manual quantity columns — neither is virtual / calculated. Users set them at the
-- order stage from the known LU quantities to be shipped / received.
--
-- AUTO-CREATION: two rows per sales order (one PalletType='EUR', one PalletType='H1') are
-- auto-created by a C_Order model interceptor when the order is created — see AD_Table
-- Description / Help below (kept there so troubleshooting is obvious).
--
-- Native PK sequence (C_Order_ReturnPackage_SEQ + AD_Sequence) is intentionally NOT declared here;
-- it is auto-created by the framework for the new table.
--
-- IDs from central ID server:
--   AD_Table   542618
--   AD_Element 585006 (C_Order_ReturnPackage_ID key), 585007 (PalletType), 585008 (QtyDeliveredLU), 585009 (QtyReturnedLU)
--   Reused AD_Element: 558 (C_Order_ID), and the standard-column elements
--   AD_Column  592815..592827 (592823 / C_BPartner_ID intentionally omitted)

-- Table: C_Order_ReturnPackage
-- 2026-06-17T09:00:00.000Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CloningEnabled,CopyColumnsFromTable,Created,CreatedBy,DownlineCloningStrategy,Description,EntityType,Help,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength,WhenChildCloningStrategy) VALUES ('3',0,0,0,542618 /*From ID Server*/,'A','N',TO_TIMESTAMP('2026-06-17 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'A','Rücknahme-Gebinde-Zeilen zu einem Kundenauftrag (C_Order), je Auftrag zwei Zeilen (Palettentyp EUR und H1). Auto-Anlage durch C_Order-Interceptor, sofern SysConfig C_Order.ReturnPackage.AutoCreate aktiv (Standard: aus).','D','Je Kundenauftrag werden zwei Zeilen (Palettentyp EUR und H1) automatisch durch einen C_Order-Model-Interceptor angelegt, sobald der Auftrag angelegt wird - sofern der SysConfig-Schalter C_Order.ReturnPackage.AutoCreate aktiviert ist (Standard: aus). Erscheint keine Zeile, prüfe zuerst diesen SysConfig-Wert, dann den Interceptor bzw. ob der Auftrag korrekt angelegt wurde.','N','Y','N','Y','Y','N','Y','N','N','N',0,'Rücknahme Gebinde','NP','L','C_Order_ReturnPackage','DTI',TO_TIMESTAMP('2026-06-17 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,0,'A')
;

-- 2026-06-17T09:00:01.000Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542618 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- Column: C_Order_ReturnPackage.AD_Client_ID
-- 2026-06-17T09:00:02.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592816 /*From ID Server*/,102,0,19,542618,'AD_Client_ID',TO_TIMESTAMP('2026-06-17 09:00:02','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden.','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','Mandant','NP',0,TO_TIMESTAMP('2026-06-17 09:00:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-17T09:00:03.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592816 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
-- 2026-06-17T09:00:04.000Z
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- Column: C_Order_ReturnPackage.AD_Org_ID
-- 2026-06-17T09:00:05.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592817 /*From ID Server*/,113,0,19,542618,'AD_Org_ID',TO_TIMESTAMP('2026-06-17 09:00:05','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','Y','N','Y','Y','N','N','Sektion','NP',0,TO_TIMESTAMP('2026-06-17 09:00:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-17T09:00:06.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592817 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
-- 2026-06-17T09:00:07.000Z
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- Column: C_Order_ReturnPackage.Created
-- 2026-06-17T09:00:08.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592819 /*From ID Server*/,245,0,16,542618,'Created',TO_TIMESTAMP('2026-06-17 09:00:08','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt','NP',0,TO_TIMESTAMP('2026-06-17 09:00:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-17T09:00:09.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592819 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
-- 2026-06-17T09:00:10.000Z
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- Column: C_Order_ReturnPackage.CreatedBy
-- 2026-06-17T09:00:11.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592820 /*From ID Server*/,246,0,18,110,542618,'CreatedBy',TO_TIMESTAMP('2026-06-17 09:00:11','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch','NP',0,TO_TIMESTAMP('2026-06-17 09:00:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-17T09:00:12.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592820 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
-- 2026-06-17T09:00:13.000Z
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- Column: C_Order_ReturnPackage.Updated
-- 2026-06-17T09:00:14.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592821 /*From ID Server*/,607,0,16,542618,'Updated',TO_TIMESTAMP('2026-06-17 09:00:14','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert','NP',0,TO_TIMESTAMP('2026-06-17 09:00:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-17T09:00:15.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592821 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
-- 2026-06-17T09:00:16.000Z
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- Column: C_Order_ReturnPackage.UpdatedBy
-- 2026-06-17T09:00:17.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592822 /*From ID Server*/,608,0,18,110,542618,'UpdatedBy',TO_TIMESTAMP('2026-06-17 09:00:17','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch','NP',0,TO_TIMESTAMP('2026-06-17 09:00:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-17T09:00:18.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592822 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
-- 2026-06-17T09:00:19.000Z
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- Column: C_Order_ReturnPackage.IsActive
-- 2026-06-17T09:00:20.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592818 /*From ID Server*/,348,0,20,542618,'IsActive',TO_TIMESTAMP('2026-06-17 09:00:20','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten.','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','Y','Aktiv','NP',0,TO_TIMESTAMP('2026-06-17 09:00:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-17T09:00:21.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592818 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
-- 2026-06-17T09:00:22.000Z
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- Element: C_Order_ReturnPackage_ID (key column)
-- 2026-06-17T09:00:23.000Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585006 /*From ID Server*/,0,'C_Order_ReturnPackage_ID',TO_TIMESTAMP('2026-06-17 09:00:23','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Rücknahme Gebinde','Rücknahme Gebinde',TO_TIMESTAMP('2026-06-17 09:00:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-06-17T09:00:24.000Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585006 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
-- 2026-06-17T09:00:25.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Return Package', PrintName='Return Package',Updated=TO_TIMESTAMP('2026-06-17 09:00:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585006 AND AD_Language='en_US'
;
-- 2026-06-17T09:00:25.100Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-17 09:00:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585006 AND AD_Language='de_DE'
;
-- 2026-06-17T09:00:25.200Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-17 09:00:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585006 AND AD_Language='de_CH'
;
-- 2026-06-17T09:00:25.300Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585006,'en_US')
;
-- 2026-06-17T09:00:25.400Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585006,'de_DE')
;
-- 2026-06-17T09:00:25.500Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585006,'de_CH')
;

-- Column: C_Order_ReturnPackage.C_Order_ReturnPackage_ID (key)
-- 2026-06-17T09:00:26.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592815 /*From ID Server*/,585006,0,13,542618,'C_Order_ReturnPackage_ID',TO_TIMESTAMP('2026-06-17 09:00:26','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','N','N','N','N','N','Y','Y','N','N','Y','N','N','Rücknahme Gebinde','NP',0,TO_TIMESTAMP('2026-06-17 09:00:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-17T09:00:27.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592815 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
-- 2026-06-17T09:00:28.000Z
/* DDL */  select update_Column_Translation_From_AD_Element(585006)
;

-- Column: C_Order_ReturnPackage.C_Order_ID (parent link, reuse element 558)
-- 2026-06-17T09:00:29.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592824 /*From ID Server*/,558,0,19,542618,'C_Order_ID',TO_TIMESTAMP('2026-06-17 09:00:29','YYYY-MM-DD HH24:MI:SS'),100,'N','Auftrag','D',10,'Eindeutige Kennung eines Auftrags.','Y','Y','N','N','N','N','N','N','Y','Y','N','Y','N','Y','Auftrag','NP',0,TO_TIMESTAMP('2026-06-17 09:00:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-17T09:00:30.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592824 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
-- 2026-06-17T09:00:31.000Z
/* DDL */  select update_Column_Translation_From_AD_Element(558)
;

-- Element: PalletType
-- 2026-06-17T09:00:35.000Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585007 /*From ID Server*/,0,'PalletType',TO_TIMESTAMP('2026-06-17 09:00:35','YYYY-MM-DD HH24:MI:SS'),100,'Palettentyp des Rücknahme-Gebindes (EUR oder H1).','D','Y','Palette','Palette',TO_TIMESTAMP('2026-06-17 09:00:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-06-17T09:00:36.000Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585007 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
-- 2026-06-17T09:00:37.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Pallet', PrintName='Pallet', Description='Pallet type of the return package (EUR or H1).',Updated=TO_TIMESTAMP('2026-06-17 09:00:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585007 AND AD_Language='en_US'
;
-- 2026-06-17T09:00:37.100Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-17 09:00:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585007 AND AD_Language='de_DE'
;
-- 2026-06-17T09:00:37.200Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-17 09:00:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585007 AND AD_Language='de_CH'
;
-- 2026-06-17T09:00:37.300Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585007,'en_US')
;
-- 2026-06-17T09:00:37.400Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585007,'de_DE')
;
-- 2026-06-17T09:00:37.500Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585007,'de_CH')
;

-- Column: C_Order_ReturnPackage.PalletType (list, AD_Reference_Value_ID=542107)
-- 2026-06-17T09:00:38.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592825 /*From ID Server*/,585007,0,17,542107,542618,'PalletType',TO_TIMESTAMP('2026-06-17 09:00:38','YYYY-MM-DD HH24:MI:SS'),100,'N','Palettentyp des Rücknahme-Gebindes (EUR oder H1).','D',10,NULL,'Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','Y','Palette','NP',0,TO_TIMESTAMP('2026-06-17 09:00:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-17T09:00:39.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592825 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
-- 2026-06-17T09:00:40.000Z
/* DDL */  select update_Column_Translation_From_AD_Element(585007)
;

-- Element: QtyDeliveredLU
-- 2026-06-17T09:00:41.000Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585008 /*From ID Server*/,0,'QtyDeliveredLU',TO_TIMESTAMP('2026-06-17 09:00:41','YYYY-MM-DD HH24:MI:SS'),100,'Gelieferte Menge des Rücknahme-Gebindes.','D','Y','Geliefert','Geliefert',TO_TIMESTAMP('2026-06-17 09:00:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-06-17T09:00:42.000Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585008 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
-- 2026-06-17T09:00:43.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Delivered', PrintName='Delivered', Description='Delivered quantity of the return package.',Updated=TO_TIMESTAMP('2026-06-17 09:00:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585008 AND AD_Language='en_US'
;
-- 2026-06-17T09:00:43.100Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-17 09:00:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585008 AND AD_Language='de_DE'
;
-- 2026-06-17T09:00:43.200Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-17 09:00:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585008 AND AD_Language='de_CH'
;
-- 2026-06-17T09:00:43.300Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585008,'en_US')
;
-- 2026-06-17T09:00:43.400Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585008,'de_DE')
;
-- 2026-06-17T09:00:43.500Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585008,'de_CH')
;

-- Column: C_Order_ReturnPackage.QtyDeliveredLU (Quantity)
-- 2026-06-17T09:00:44.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592826 /*From ID Server*/,585008,0,29,542618,'QtyDeliveredLU',TO_TIMESTAMP('2026-06-17 09:00:44','YYYY-MM-DD HH24:MI:SS'),100,'N','Gelieferte Menge des Rücknahme-Gebindes.','D',0,NULL,'Y','Y','N','N','N','N','N','N','N','N','N','Y','N','Y','Geliefert','NP',0,TO_TIMESTAMP('2026-06-17 09:00:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-17T09:00:45.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592826 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
-- 2026-06-17T09:00:46.000Z
/* DDL */  select update_Column_Translation_From_AD_Element(585008)
;

-- Element: QtyReturnedLU
-- 2026-06-17T09:00:47.000Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585009 /*From ID Server*/,0,'QtyReturnedLU',TO_TIMESTAMP('2026-06-17 09:00:47','YYYY-MM-DD HH24:MI:SS'),100,'Zurückgegebene Menge des Rücknahme-Gebindes.','D','Y','Zurück','Zurück',TO_TIMESTAMP('2026-06-17 09:00:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-06-17T09:00:48.000Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585009 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
-- 2026-06-17T09:00:49.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Returned', PrintName='Returned', Description='Returned quantity of the return package.',Updated=TO_TIMESTAMP('2026-06-17 09:00:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585009 AND AD_Language='en_US'
;
-- 2026-06-17T09:00:49.100Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-17 09:00:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585009 AND AD_Language='de_DE'
;
-- 2026-06-17T09:00:49.200Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-06-17 09:00:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585009 AND AD_Language='de_CH'
;
-- 2026-06-17T09:00:49.300Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585009,'en_US')
;
-- 2026-06-17T09:00:49.400Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585009,'de_DE')
;
-- 2026-06-17T09:00:49.500Z
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(585009,'de_CH')
;

-- Column: C_Order_ReturnPackage.QtyReturnedLU (Quantity)
-- 2026-06-17T09:00:50.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592827 /*From ID Server*/,585009,0,29,542618,'QtyReturnedLU',TO_TIMESTAMP('2026-06-17 09:00:50','YYYY-MM-DD HH24:MI:SS'),100,'N','Zurückgegebene Menge des Rücknahme-Gebindes.','D',0,NULL,'Y','Y','N','N','N','N','N','N','N','N','N','Y','N','Y','Zurück','NP',0,TO_TIMESTAMP('2026-06-17 09:00:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-06-17T09:00:51.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592827 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
-- 2026-06-17T09:00:52.000Z
/* DDL */  select update_Column_Translation_From_AD_Element(585009)
;

-- Physical table
-- 2026-06-17T09:00:53.000Z
/* DDL */ CREATE TABLE public.C_Order_ReturnPackage (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, C_Order_ID NUMERIC(10) NOT NULL, C_Order_ReturnPackage_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, IsActive CHAR(1) DEFAULT 'Y' CHECK (IsActive IN ('Y','N')) NOT NULL, PalletType VARCHAR(10) NOT NULL, QtyDeliveredLU NUMERIC, QtyReturnedLU NUMERIC, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT COrder_COrderReturnPackage FOREIGN KEY (C_Order_ID) REFERENCES public.C_Order DEFERRABLE INITIALLY DEFERRED, CONSTRAINT C_Order_ReturnPackage_Key PRIMARY KEY (C_Order_ReturnPackage_ID))
;

-- 2026-06-17T09:00:54.000Z
/* DDL */ SELECT public.db_alter_table('C_Order_ReturnPackage','ALTER TABLE public.C_Order_ReturnPackage ADD CONSTRAINT PalletType_Check CHECK (PalletType IN (''EUR'',''H1''))')
;
