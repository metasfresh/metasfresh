-- Table: Contract_Module_Log
-- 2023-06-08T19:04:32.564020633Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542338,'N',TO_TIMESTAMP('2023-06-08 20:04:32.12','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'Contract Module Log','NP','L','Contract_Module_Log','DTI',TO_TIMESTAMP('2023-06-08 20:04:32.12','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-08T19:04:32.570416570Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542338 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-06-08T19:04:33.253666887Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556274,TO_TIMESTAMP('2023-06-08 20:04:33.088','YYYY-MM-DD HH24:MI:SS.US'),100,1000000,50000,'Table Contract_Module_Log',1,'Y','N','Y','Y','Contract_Module_Log','N',1000000,TO_TIMESTAMP('2023-06-08 20:04:33.088','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-08T19:04:33.269176157Z
CREATE SEQUENCE CONTRACT_MODULE_LOG_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2023-06-08T19:05:05.918184621Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582413,0,'Contract_Module_Log_ID',TO_TIMESTAMP('2023-06-08 20:05:05.635','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Contract Module Log','Contract Module Log',TO_TIMESTAMP('2023-06-08 20:05:05.635','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-08T19:05:05.920613205Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582413 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: Contract_Module_Log.Contract_Module_Log_ID
-- 2023-06-08T19:05:06.586889937Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,586758,582413,0,13,542338,'Contract_Module_Log_ID',TO_TIMESTAMP('2023-06-08 20:05:05.633','YYYY-MM-DD HH24:MI:SS.US'),100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','Contract Module Log',TO_TIMESTAMP('2023-06-08 20:05:05.633','YYYY-MM-DD HH24:MI:SS.US'),100,1)
;

-- 2023-06-08T19:05:06.588913515Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586758 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-08T19:05:07.439621111Z
/* DDL */  select update_Column_Translation_From_AD_Element(582413)
;

-- 2023-06-08T19:05:07.993700459Z
ALTER SEQUENCE CONTRACT_MODULE_LOG_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 RESTART 1000000
;

-- 2023-06-08T19:05:08.005643794Z
ALTER TABLE Contract_Module_Log ADD COLUMN Contract_Module_Log_ID numeric(10,0)
;

-- 2023-06-08T19:05:27.582121358Z
/* DDL */ CREATE TABLE public.Contract_Module_Log (Contract_Module_Log_ID NUMERIC(10) NOT NULL, CONSTRAINT Contract_Module_Log_Key PRIMARY KEY (Contract_Module_Log_ID))
;

-- Column: Contract_Module_Log.AD_Client_ID
-- 2023-06-08T19:05:54.236996884Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586759,102,0,19,542338,'AD_Client_ID',TO_TIMESTAMP('2023-06-08 20:05:53.945','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Mandant für diese Installation.','D',0,10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Mandant',0,0,TO_TIMESTAMP('2023-06-08 20:05:53.945','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-08T19:05:54.240690844Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586759 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-08T19:05:55.098646307Z
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- Column: Contract_Module_Log.AD_Org_ID
-- 2023-06-08T19:05:56.308917027Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586760,113,0,30,542338,'AD_Org_ID',TO_TIMESTAMP('2023-06-08 20:05:55.752','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Organisatorische Einheit des Mandanten','D',0,10,'E','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','Y','N','Y','N','N','Y','N','N','Organisation',10,0,TO_TIMESTAMP('2023-06-08 20:05:55.752','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-08T19:05:56.310759718Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586760 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-08T19:05:57.149779702Z
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- Column: Contract_Module_Log.Created
-- 2023-06-08T19:05:58.153930726Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586761,245,0,16,542338,'Created',TO_TIMESTAMP('2023-06-08 20:05:57.769','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',0,29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt',0,0,TO_TIMESTAMP('2023-06-08 20:05:57.769','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-08T19:05:58.155741383Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586761 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-08T19:05:58.915623847Z
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- Column: Contract_Module_Log.CreatedBy
-- 2023-06-08T19:05:59.935990537Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586762,246,0,18,110,542338,'CreatedBy',TO_TIMESTAMP('2023-06-08 20:05:59.508','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',0,10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Erstellt durch',0,0,TO_TIMESTAMP('2023-06-08 20:05:59.508','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-08T19:05:59.937881096Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586762 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-08T19:06:00.765384128Z
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- Column: Contract_Module_Log.IsActive
-- 2023-06-08T19:06:01.876387511Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586763,348,0,20,542338,'IsActive',TO_TIMESTAMP('2023-06-08 20:06:01.425','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Der Eintrag ist im System aktiv','D',0,1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','Y','Aktiv',0,0,TO_TIMESTAMP('2023-06-08 20:06:01.425','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-08T19:06:01.877822402Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586763 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-08T19:06:02.665794937Z
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- Column: Contract_Module_Log.Updated
-- 2023-06-08T19:06:03.723336254Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586764,607,0,16,542338,'Updated',TO_TIMESTAMP('2023-06-08 20:06:03.247','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',0,29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert',0,0,TO_TIMESTAMP('2023-06-08 20:06:03.247','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-08T19:06:03.725097096Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586764 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-08T19:06:04.525259806Z
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- Column: Contract_Module_Log.UpdatedBy
-- 2023-06-08T19:06:05.564627101Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsFacetFilter,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586765,608,0,18,110,542338,'UpdatedBy',TO_TIMESTAMP('2023-06-08 20:06:05.136','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',0,10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Aktualisiert durch',0,0,TO_TIMESTAMP('2023-06-08 20:06:05.136','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-08T19:06:05.566649953Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586765 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-08T19:06:06.295957958Z
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2023-06-08T19:06:07.109037794Z
/* DDL */ SELECT public.db_alter_table('Contract_Module_Log','ALTER TABLE public.Contract_Module_Log ADD COLUMN AD_Client_ID NUMERIC(10) NOT NULL')
;

-- 2023-06-08T19:06:07.136740265Z
/* DDL */ SELECT public.db_alter_table('Contract_Module_Log','ALTER TABLE public.Contract_Module_Log ADD COLUMN AD_Org_ID NUMERIC(10) NOT NULL')
;

-- 2023-06-08T19:06:07.154231313Z
/* DDL */ SELECT public.db_alter_table('Contract_Module_Log','ALTER TABLE public.Contract_Module_Log ADD COLUMN Created TIMESTAMP WITH TIME ZONE NOT NULL')
;

-- 2023-06-08T19:06:07.174859467Z
/* DDL */ SELECT public.db_alter_table('Contract_Module_Log','ALTER TABLE public.Contract_Module_Log ADD COLUMN CreatedBy NUMERIC(10) NOT NULL')
;

-- 2023-06-08T19:06:07.190992842Z
/* DDL */ SELECT public.db_alter_table('Contract_Module_Log','ALTER TABLE public.Contract_Module_Log ADD COLUMN IsActive CHAR(1) CHECK (IsActive IN (''Y'',''N'')) NOT NULL')
;

-- 2023-06-08T19:06:07.207967826Z
/* DDL */ SELECT public.db_alter_table('Contract_Module_Log','ALTER TABLE public.Contract_Module_Log ADD COLUMN Updated TIMESTAMP WITH TIME ZONE NOT NULL')
;

-- 2023-06-08T19:06:07.228922967Z
/* DDL */ SELECT public.db_alter_table('Contract_Module_Log','ALTER TABLE public.Contract_Module_Log ADD COLUMN UpdatedBy NUMERIC(10) NOT NULL')
;

-- 2023-06-08T19:06:53.397036214Z
UPDATE AD_Table_Trl SET Name='Vertragsbaustein Log',Updated=TO_TIMESTAMP('2023-06-08 20:06:53.396','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Table_ID=542338
;

-- 2023-06-08T19:07:03.954710872Z
UPDATE AD_Table_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-06-08 20:07:03.953','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Table_ID=542338
;

-- 2023-06-08T19:07:09.236217061Z
UPDATE AD_Table_Trl SET Name='Vertragsbaustein Log',Updated=TO_TIMESTAMP('2023-06-08 20:07:09.235','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Table_ID=542338
;

-- 2023-06-08T19:07:09.237580587Z
UPDATE AD_Table SET Name='Vertragsbaustein Log' WHERE AD_Table_ID=542338
;

-- Column: Contract_Module_Log.M_Product_ID
-- 2023-06-08T19:08:07.561092242Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586766,454,0,19,542338,'M_Product_ID',TO_TIMESTAMP('2023-06-08 20:08:07.276','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Produkt, Leistung, Artikel','D',0,10,'E','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Produkt',0,0,TO_TIMESTAMP('2023-06-08 20:08:07.276','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-08T19:08:07.563174473Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586766 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-08T19:08:08.342855319Z
/* DDL */  select update_Column_Translation_From_AD_Element(454)
;

-- 2023-06-08T19:08:12.103219544Z
/* DDL */ SELECT public.db_alter_table('Contract_Module_Log','ALTER TABLE public.Contract_Module_Log ADD COLUMN M_Product_ID NUMERIC(10)')
;

-- 2023-06-08T19:08:12.110814457Z
ALTER TABLE Contract_Module_Log ADD CONSTRAINT MProduct_ContractModuleLog FOREIGN KEY (M_Product_ID) REFERENCES public.M_Product DEFERRABLE INITIALLY DEFERRED
;

-- Column: Contract_Module_Log.IsSOTrx
-- 2023-06-08T19:08:34.828657375Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586767,1106,0,20,542338,'IsSOTrx',TO_TIMESTAMP('2023-06-08 20:08:34.604','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','Dies ist eine Verkaufstransaktion','D',0,1,'E','The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','N','N','N','Y','N',0,'Verkaufstransaktion',0,0,TO_TIMESTAMP('2023-06-08 20:08:34.604','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-08T19:08:34.830610757Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586767 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-08T19:08:35.574021661Z
/* DDL */  select update_Column_Translation_From_AD_Element(1106)
;

-- 2023-06-08T19:08:52.264031215Z
/* DDL */ SELECT public.db_alter_table('Contract_Module_Log','ALTER TABLE public.Contract_Module_Log ADD COLUMN IsSOTrx CHAR(1) DEFAULT ''N'' CHECK (IsSOTrx IN (''Y'',''N'')) NOT NULL')
;

-- Column: Contract_Module_Log.Processed
-- 2023-06-08T19:09:46.446211790Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586768,1047,0,20,542338,'Processed',TO_TIMESTAMP('2023-06-08 20:09:46.195','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','D',0,1,'Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Verarbeitet',0,0,TO_TIMESTAMP('2023-06-08 20:09:46.195','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-08T19:09:46.448079948Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586768 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-08T19:09:47.238633858Z
/* DDL */  select update_Column_Translation_From_AD_Element(1047)
;

-- 2023-06-08T19:09:49.672240038Z
/* DDL */ SELECT public.db_alter_table('Contract_Module_Log','ALTER TABLE public.Contract_Module_Log ADD COLUMN Processed CHAR(1) DEFAULT ''N'' CHECK (Processed IN (''Y'',''N'')) NOT NULL')
;

-- Column: Contract_Module_Log.AD_Table_ID
-- 2023-06-08T19:10:40.755079947Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586769,126,0,19,542338,'AD_Table_ID',TO_TIMESTAMP('2023-06-08 20:10:40.434','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Database Table information','D',0,10,'E','The Database Table provides the information of the table definition','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'DB-Tabelle',0,0,TO_TIMESTAMP('2023-06-08 20:10:40.434','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-08T19:10:40.756606631Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586769 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-08T19:10:41.505281598Z
/* DDL */  select update_Column_Translation_From_AD_Element(126)
;

-- 2023-06-08T19:10:52.363692455Z
/* DDL */ SELECT public.db_alter_table('Contract_Module_Log','ALTER TABLE public.Contract_Module_Log ADD COLUMN AD_Table_ID NUMERIC(10)')
;

-- 2023-06-08T19:10:52.372455889Z
ALTER TABLE Contract_Module_Log ADD CONSTRAINT ADTable_ContractModuleLog FOREIGN KEY (AD_Table_ID) REFERENCES public.AD_Table DEFERRABLE INITIALLY DEFERRED
;

-- Column: Contract_Module_Log.M_Warehouse_ID
-- 2023-06-08T19:11:13.991692060Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586770,459,0,19,542338,'M_Warehouse_ID',TO_TIMESTAMP('2023-06-08 20:11:13.688','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Lager oder Ort für Dienstleistung','D',0,10,'E','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Lager',0,0,TO_TIMESTAMP('2023-06-08 20:11:13.688','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-08T19:11:13.993213125Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586770 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-08T19:11:14.768181588Z
/* DDL */  select update_Column_Translation_From_AD_Element(459)
;

-- 2023-06-08T19:11:27.415154436Z
/* DDL */ SELECT public.db_alter_table('Contract_Module_Log','ALTER TABLE public.Contract_Module_Log ADD COLUMN M_Warehouse_ID NUMERIC(10)')
;

-- 2023-06-08T19:11:27.422272597Z
ALTER TABLE Contract_Module_Log ADD CONSTRAINT MWarehouse_ContractModuleLog FOREIGN KEY (M_Warehouse_ID) REFERENCES public.M_Warehouse DEFERRABLE INITIALLY DEFERRED
;

-- Column: Contract_Module_Log.Contract_Module_Type_ID
-- 2023-06-08T19:12:26.752413738Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586771,582395,0,19,542338,'Contract_Module_Type_ID',TO_TIMESTAMP('2023-06-08 20:12:26.458','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'E','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Contract Module Type',0,0,TO_TIMESTAMP('2023-06-08 20:12:26.458','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-08T19:12:26.753748296Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586771 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-08T19:12:27.546297752Z
/* DDL */  select update_Column_Translation_From_AD_Element(582395)
;

-- 2023-06-08T19:12:33.646526349Z
/* DDL */ SELECT public.db_alter_table('Contract_Module_Log','ALTER TABLE public.Contract_Module_Log ADD COLUMN Contract_Module_Type_ID NUMERIC(10)')
;

-- 2023-06-08T19:12:33.656898157Z
ALTER TABLE Contract_Module_Log ADD CONSTRAINT ContractModuleType_ContractModuleLog FOREIGN KEY (Contract_Module_Type_ID) REFERENCES public.Contract_Module_Type DEFERRABLE INITIALLY DEFERRED
;

-- Column: Contract_Module_Log.DateTrx
-- 2023-06-08T19:13:27.991931796Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586772,1297,0,15,542338,'DateTrx',TO_TIMESTAMP('2023-06-08 20:13:27.681','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Vorgangsdatum','D',0,7,'B','Das "Vorgangsdatum" bezeichnet das Datum des Vorgangs.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Vorgangsdatum',0,0,TO_TIMESTAMP('2023-06-08 20:13:27.681','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-08T19:13:27.993595462Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586772 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-08T19:13:28.737335160Z
/* DDL */  select update_Column_Translation_From_AD_Element(1297)
;

-- 2023-06-08T19:13:36.939468755Z
/* DDL */ SELECT public.db_alter_table('Contract_Module_Log','ALTER TABLE public.Contract_Module_Log ADD COLUMN DateTrx TIMESTAMP WITHOUT TIME ZONE')
;

-- Column: Contract_Module_Log.C_Invoice_Candidate_ID
-- 2023-06-08T19:33:14.239914914Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586773,541266,0,19,542338,'C_Invoice_Candidate_ID',TO_TIMESTAMP('2023-06-08 20:33:13.899','YYYY-MM-DD HH24:MI:SS.US'),100,'N','','de.metas.invoicecandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Rechnungskandidat',0,0,TO_TIMESTAMP('2023-06-08 20:33:13.899','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-08T19:33:14.241385321Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586773 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-08T19:33:14.934416277Z
/* DDL */  select update_Column_Translation_From_AD_Element(541266)
;

-- Column: Contract_Module_Log.M_Product_ID
-- 2023-06-08T19:34:55.892932520Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2023-06-08 20:34:55.892','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586766
;

-- Column: Contract_Module_Log.M_Product_ID
-- 2023-06-08T19:35:20.041734913Z
UPDATE AD_Column SET AD_Reference_Value_ID=540272, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2023-06-08 20:35:20.041','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586766
;


-- Column: Contract_Module_Log.Record_ID
-- 2023-06-08T19:42:52.618977334Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586774,538,0,28,542338,'Record_ID',TO_TIMESTAMP('2023-06-08 20:42:52.328','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Direct internal record ID','D',0,22,'The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Datensatz-ID',0,0,TO_TIMESTAMP('2023-06-08 20:42:52.328','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-08T19:42:52.627656702Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586774 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-08T19:42:53.437984315Z
/* DDL */  select update_Column_Translation_From_AD_Element(538)
;

-- 2023-06-08T19:43:49.315254899Z
/* DDL */ SELECT public.db_alter_table('Contract_Module_Log','ALTER TABLE public.Contract_Module_Log ADD COLUMN Record_ID NUMERIC(10)')
;

-- Column: Contract_Module_Log.Description
-- 2023-06-08T19:52:06.597794166Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586775,275,0,10,542338,'Description',TO_TIMESTAMP('2023-06-08 20:52:06.059','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,255,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Beschreibung',0,0,TO_TIMESTAMP('2023-06-08 20:52:06.059','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-08T19:52:06.601904919Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586775 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-08T19:52:07.170637239Z
/* DDL */  select update_Column_Translation_From_AD_Element(275)
;

-- 2023-06-08T19:52:29.995756810Z
/* DDL */ SELECT public.db_alter_table('Contract_Module_Log','ALTER TABLE public.Contract_Module_Log ADD COLUMN Description VARCHAR(255)')
;

-- 2023-06-08T20:03:23.311900695Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582414,0,'CollectionPoint_BPartner_ID',TO_TIMESTAMP('2023-06-08 21:03:23.009','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Collection Point','Collection Point',TO_TIMESTAMP('2023-06-08 21:03:23.009','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-08T20:03:23.315370323Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582414 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: CollectionPoint_BPartner_ID
-- 2023-06-08T20:03:54.051845210Z
UPDATE AD_Element_Trl SET Name='Sammelstelle', PrintName='Sammelstelle',Updated=TO_TIMESTAMP('2023-06-08 21:03:54.05','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582414 AND AD_Language='de_CH'
;

-- 2023-06-08T20:03:54.058383180Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582414,'de_CH')
;

-- Element: CollectionPoint_BPartner_ID
-- 2023-06-08T20:03:59.555865590Z
UPDATE AD_Element_Trl SET Name='Sammelstelle', PrintName='Sammelstelle',Updated=TO_TIMESTAMP('2023-06-08 21:03:59.555','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582414 AND AD_Language='de_DE'
;

-- 2023-06-08T20:03:59.557011228Z
UPDATE AD_Element SET Name='Sammelstelle', PrintName='Sammelstelle' WHERE AD_Element_ID=582414
;

-- 2023-06-08T20:03:59.990925864Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582414,'de_DE')
;

-- 2023-06-08T20:03:59.992068484Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582414,'de_DE')
;

-- Element: CollectionPoint_BPartner_ID
-- 2023-06-08T20:04:03.396100352Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-06-08 21:04:03.395','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582414 AND AD_Language='en_US'
;

-- 2023-06-08T20:04:03.401459112Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582414,'en_US')
;

-- Element: CollectionPoint_BPartner_ID
-- 2023-06-08T20:04:20.539644162Z
UPDATE AD_Element_Trl SET Name='Centre de Collection', PrintName='Centre de Collection',Updated=TO_TIMESTAMP('2023-06-08 21:04:20.538','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582414 AND AD_Language='fr_CH'
;

-- 2023-06-08T20:04:20.544571341Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582414,'fr_CH')
;

-- Column: Contract_Module_Log.CollectionPoint_BPartner_ID
-- 2023-06-08T20:04:52.921995699Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586776,582414,0,30,541252,542338,'CollectionPoint_BPartner_ID',TO_TIMESTAMP('2023-06-08 21:04:52.569','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Sammelstelle',0,0,TO_TIMESTAMP('2023-06-08 21:04:52.569','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-08T20:04:52.925838406Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586776 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-08T20:04:53.476753329Z
/* DDL */  select update_Column_Translation_From_AD_Element(582414)
;

-- Column: Contract_Module_Log.CollectionPoint_BPartner_ID
-- 2023-06-08T20:05:20.479319260Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-06-08 21:05:20.479','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586776
;

-- 2023-06-08T20:05:23.733949090Z
/* DDL */ SELECT public.db_alter_table('Contract_Module_Log','ALTER TABLE public.Contract_Module_Log ADD COLUMN CollectionPoint_BPartner_ID NUMERIC(10)')
;

-- 2023-06-08T20:05:23.741276548Z
ALTER TABLE Contract_Module_Log ADD CONSTRAINT CollectionPointBPartner_ContractModuleLog FOREIGN KEY (CollectionPoint_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- 2023-06-08T20:06:03.435595342Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582415,0,'Producer_BPartner_ID',TO_TIMESTAMP('2023-06-08 21:06:03.126','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Producer','Producer',TO_TIMESTAMP('2023-06-08 21:06:03.126','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-08T20:06:03.439580851Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582415 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Producer_BPartner_ID
-- 2023-06-08T20:06:25.690158424Z
UPDATE AD_Element_Trl SET Name='Produzent', PrintName='Produzent',Updated=TO_TIMESTAMP('2023-06-08 21:06:25.689','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582415 AND AD_Language='de_CH'
;

-- 2023-06-08T20:06:25.693161527Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582415,'de_CH')
;

-- Element: Producer_BPartner_ID
-- 2023-06-08T20:06:32.014818658Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Produzent', PrintName='Produzent',Updated=TO_TIMESTAMP('2023-06-08 21:06:32.014','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582415 AND AD_Language='de_DE'
;

-- 2023-06-08T20:06:32.017309173Z
UPDATE AD_Element SET Name='Produzent', PrintName='Produzent' WHERE AD_Element_ID=582415
;

-- 2023-06-08T20:06:32.394591754Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582415,'de_DE')
;

-- 2023-06-08T20:06:32.395520360Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582415,'de_DE')
;

-- Element: Producer_BPartner_ID
-- 2023-06-08T20:06:38.755627828Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-06-08 21:06:38.755','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582415 AND AD_Language='en_US'
;

-- 2023-06-08T20:06:38.757072834Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582415,'en_US')
;

-- Element: Producer_BPartner_ID
-- 2023-06-08T20:06:52.944854418Z
UPDATE AD_Element_Trl SET Name='Producteur', PrintName='Producteur',Updated=TO_TIMESTAMP('2023-06-08 21:06:52.944','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582415 AND AD_Language='fr_CH'
;

-- 2023-06-08T20:06:52.946467004Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582415,'fr_CH')
;

-- Column: Contract_Module_Log.Producer_BPartner_ID
-- 2023-06-08T20:07:17.656757770Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586777,582415,0,30,541252,542338,'Producer_BPartner_ID',TO_TIMESTAMP('2023-06-08 21:07:17.213','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Produzent',0,0,TO_TIMESTAMP('2023-06-08 21:07:17.213','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-08T20:07:17.660726626Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586777 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-08T20:07:18.185240639Z
/* DDL */  select update_Column_Translation_From_AD_Element(582415)
;

-- Column: Contract_Module_Log.Producer_BPartner_ID
-- 2023-06-08T20:07:22.606525221Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-06-08 21:07:22.605','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586777
;

-- 2023-06-08T20:07:26.664390333Z
/* DDL */ SELECT public.db_alter_table('Contract_Module_Log','ALTER TABLE public.Contract_Module_Log ADD COLUMN Producer_BPartner_ID NUMERIC(10)')
;

-- 2023-06-08T20:07:26.670046111Z
ALTER TABLE Contract_Module_Log ADD CONSTRAINT ProducerBPartner_ContractModuleLog FOREIGN KEY (Producer_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- Column: Contract_Module_Log.C_Flatrate_Term_ID
-- 2023-06-08T20:08:17.265430625Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586778,541447,0,30,542338,'C_Flatrate_Term_ID',TO_TIMESTAMP('2023-06-08 21:08:16.485','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Pauschale - Vertragsperiode',0,0,TO_TIMESTAMP('2023-06-08 21:08:16.485','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-08T20:08:17.270416241Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586778 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-08T20:08:17.788251580Z
/* DDL */  select update_Column_Translation_From_AD_Element(541447)
;

-- 2023-06-08T20:09:06.290460736Z
/* DDL */ SELECT public.db_alter_table('Contract_Module_Log','ALTER TABLE public.Contract_Module_Log ADD COLUMN C_Flatrate_Term_ID NUMERIC(10)')
;

-- 2023-06-08T20:09:06.296213676Z
ALTER TABLE Contract_Module_Log ADD CONSTRAINT CFlatrateTerm_ContractModuleLog FOREIGN KEY (C_Flatrate_Term_ID) REFERENCES public.C_Flatrate_Term DEFERRABLE INITIALLY DEFERRED
;


