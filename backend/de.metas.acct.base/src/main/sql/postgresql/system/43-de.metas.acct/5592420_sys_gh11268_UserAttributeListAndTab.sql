--Create Reference List
-- 2021-06-11T12:28:46.918Z
-- URL zum Konzept
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541332,TO_TIMESTAMP('2021-06-11 14:28:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','AD_User_Attribute',TO_TIMESTAMP('2021-06-11 14:28:46','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2021-06-11T12:28:46.919Z
-- URL zum Konzept
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541332 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Create element for column


-- 2021-06-11T12:37:06.956Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579332,0,'AD_User_Attribute',TO_TIMESTAMP('2021-06-11 14:37:06','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','User Attribute','User Attribute',TO_TIMESTAMP('2021-06-11 14:37:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-11T12:37:06.958Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579332 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-06-11T12:37:28.772Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Benutzerattribut', PrintName='Benutzerattribut',Updated=TO_TIMESTAMP('2021-06-11 14:37:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579332 AND AD_Language='de_CH'
;

-- 2021-06-11T12:37:28.792Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579332,'de_CH')
;

-- 2021-06-11T12:37:36.348Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Benutzerattribut', PrintName='Benutzerattribut',Updated=TO_TIMESTAMP('2021-06-11 14:37:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579332 AND AD_Language='de_DE'
;

-- 2021-06-11T12:37:36.350Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579332,'de_DE')
;

-- 2021-06-11T12:37:36.379Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579332,'de_DE')
;

-- 2021-06-11T12:37:36.384Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='AD_User_Attribute', Name='Benutzerattribut', Description=NULL, Help=NULL WHERE AD_Element_ID=579332
;

-- 2021-06-11T12:37:36.385Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_User_Attribute', Name='Benutzerattribut', Description=NULL, Help=NULL, AD_Element_ID=579332 WHERE UPPER(ColumnName)='AD_USER_ATTRIBUTE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-06-11T12:37:36.389Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='AD_User_Attribute', Name='Benutzerattribut', Description=NULL, Help=NULL WHERE AD_Element_ID=579332 AND IsCentrallyMaintained='Y'
;

-- 2021-06-11T12:37:36.389Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Benutzerattribut', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579332) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579332)
;

-- 2021-06-11T12:37:36.412Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Benutzerattribut', Name='Benutzerattribut' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579332)
;

-- 2021-06-11T12:37:36.414Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Benutzerattribut', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579332
;

-- 2021-06-11T12:37:36.416Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Benutzerattribut', Description=NULL, Help=NULL WHERE AD_Element_ID = 579332
;

-- 2021-06-11T12:37:36.417Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Benutzerattribut', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579332
;

-- 2021-06-11T12:37:40.325Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-06-11 14:37:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579332 AND AD_Language='en_US'
;

-- 2021-06-11T12:37:40.325Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579332,'en_US')
;

-- 2021-06-11T12:37:46.381Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Benutzerattribut', PrintName='Benutzerattribut',Updated=TO_TIMESTAMP('2021-06-11 14:37:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579332 AND AD_Language='nl_NL'
;

-- 2021-06-11T12:37:46.384Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579332,'nl_NL')
;

--
-- Delete previously added AD_User_Attribute tab, table, element, sequence
drop table if exists tmp_to_delete;
create temporary table tmp_to_delete as
SELECT tt.ad_tab_id, uis.ad_ui_section_id, uic.ad_ui_column_id, uig.ad_ui_elementgroup_id, uie.ad_ui_element_id
from ad_tab tt
left outer join ad_ui_section uis on tt.ad_tab_id = uis.ad_tab_id
left outer join ad_ui_column uic on uic.ad_ui_section_id = uis.ad_ui_section_id
left outer join ad_ui_elementgroup uig on uig.ad_ui_column_id = uic.ad_ui_column_id
left outer join ad_ui_element uie on uie.ad_ui_elementgroup_id = uig.ad_ui_elementgroup_id
where tt.ad_table_id=(select ad_table_id from ad_table where tablename='AD_User_Attribute');
--
delete from ad_ui_element where ad_ui_element_id in (select ad_ui_element_id from tmp_to_delete);
delete from ad_ui_element where ad_ui_elementtype='L' and labels_tab_id in (select ad_tab_id from tmp_to_delete);
delete from ad_ui_elementgroup where ad_ui_elementgroup_id in (select ad_ui_elementgroup_id from tmp_to_delete);
delete from ad_ui_column where ad_ui_column_id=(select ad_ui_column_id from tmp_to_delete);
delete from ad_ui_section_trl where ad_ui_section_id=(select ad_ui_section_id from tmp_to_delete);
delete from ad_ui_section where ad_ui_section_id=(select ad_ui_section_id from tmp_to_delete);
delete from ad_tab where ad_tab_id in (select ad_tab_id from tmp_to_delete);
--
delete from ad_column c where c.ad_table_id=(select t.ad_table_id from ad_table t where t.tablename='AD_User_Attribute');
delete from ad_table where tablename='AD_User_Attribute';
delete from ad_element where columnname='AD_User_Attribute_ID';
delete from ad_sequence where name='AD_User_Attribute' and istableid='Y';
drop sequence if exists ad_user_attribute_seq;


-- Create Table

-- 2021-06-11T12:39:49.115Z
-- URL zum Konzept
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy) VALUES ('3',0,0,0,541699,'N',TO_TIMESTAMP('2021-06-11 14:39:49','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','Y','N','N','N','N','N',0,'User Attribute','NP','L','AD_User_Attribute','DTI',TO_TIMESTAMP('2021-06-11 14:39:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-11T12:39:49.118Z
-- URL zum Konzept
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Table_ID=541699 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2021-06-11T12:39:49.202Z
-- URL zum Konzept
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,555430,TO_TIMESTAMP('2021-06-11 14:39:49','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table AD_User_Attribute',1,'Y','N','Y','Y','AD_User_Attribute','N',1000000,TO_TIMESTAMP('2021-06-11 14:39:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-11T12:39:49.221Z
-- URL zum Konzept
CREATE SEQUENCE AD_USER_ATTRIBUTE_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2021-06-11T12:41:08.871Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574333,102,0,19,541699,'AD_Client_ID',TO_TIMESTAMP('2021-06-11 14:41:08','YYYY-MM-DD HH24:MI:SS'),100,'N','Mandant für diese Installation.','D',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','N','Mandant',0,TO_TIMESTAMP('2021-06-11 14:41:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-11T12:41:08.872Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574333 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-11T12:41:08.874Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(102)
;

-- 2021-06-11T12:41:09.535Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574334,113,0,30,541699,'AD_Org_ID',TO_TIMESTAMP('2021-06-11 14:41:09','YYYY-MM-DD HH24:MI:SS'),100,'N','Organisatorische Einheit des Mandanten','D',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','Y','N','Y','Y','N','N','Sektion',0,TO_TIMESTAMP('2021-06-11 14:41:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-11T12:41:09.536Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574334 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-11T12:41:09.537Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(113)
;

-- 2021-06-11T12:41:09.992Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574335,245,0,16,541699,'Created',TO_TIMESTAMP('2021-06-11 14:41:09','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag erstellt wurde','D',29,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt',0,TO_TIMESTAMP('2021-06-11 14:41:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-11T12:41:09.995Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574335 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-11T12:41:09.997Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(245)
;

-- 2021-06-11T12:41:10.419Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574336,246,0,18,110,541699,'CreatedBy',TO_TIMESTAMP('2021-06-11 14:41:10','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag erstellt hat','D',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Erstellt durch',0,TO_TIMESTAMP('2021-06-11 14:41:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-11T12:41:10.423Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574336 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-11T12:41:10.426Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(246)
;

-- 2021-06-11T12:41:10.838Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574337,348,0,20,541699,'IsActive',TO_TIMESTAMP('2021-06-11 14:41:10','YYYY-MM-DD HH24:MI:SS'),100,'N','Der Eintrag ist im System aktiv','D',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','Y','N','N','Y','N','Y','Aktiv',0,TO_TIMESTAMP('2021-06-11 14:41:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-11T12:41:10.839Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574337 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-11T12:41:10.841Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(348)
;

-- 2021-06-11T12:41:11.305Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574338,607,0,16,541699,'Updated',TO_TIMESTAMP('2021-06-11 14:41:11','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem dieser Eintrag aktualisiert wurde','D',29,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert',0,TO_TIMESTAMP('2021-06-11 14:41:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-11T12:41:11.306Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574338 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-11T12:41:11.307Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(607)
;

-- 2021-06-11T12:41:11.698Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574339,608,0,18,110,541699,'UpdatedBy',TO_TIMESTAMP('2021-06-11 14:41:11','YYYY-MM-DD HH24:MI:SS'),100,'N','Nutzer, der diesen Eintrag aktualisiert hat','D',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Y','N','N','Y','N','N','Aktualisiert durch',0,TO_TIMESTAMP('2021-06-11 14:41:11','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-11T12:41:11.699Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574339 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-11T12:41:11.700Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(608)
;

-- 2021-06-11T12:41:12.071Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579333,0,'AD_User_Attribute_ID',TO_TIMESTAMP('2021-06-11 14:41:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','User Attribute','User Attribute',TO_TIMESTAMP('2021-06-11 14:41:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-11T12:41:12.073Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579333 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-06-11T12:41:12.390Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsCalculated,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsSyncDatabase,IsTranslated,IsUpdateable,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574340,579333,0,13,541699,'AD_User_Attribute_ID',TO_TIMESTAMP('2021-06-11 14:41:12','YYYY-MM-DD HH24:MI:SS'),100,'N','D',10,'Y','N','N','N','N','N','Y','Y','N','N','Y','N','N','User Attribute',0,TO_TIMESTAMP('2021-06-11 14:41:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-11T12:41:12.391Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574340 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-11T12:41:12.392Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579333)
;

-- 2021-06-11T12:43:10.333Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574341,138,0,30,541699,'AD_User_ID',TO_TIMESTAMP('2021-06-11 14:43:10','YYYY-MM-DD HH24:MI:SS'),100,'N','User within the system - Internal or Business Partner Contact','D',0,10,'The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N',0,'Ansprechpartner',0,0,TO_TIMESTAMP('2021-06-11 14:43:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-11T12:43:10.337Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574341 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-11T12:43:10.341Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(138)
;

-- 2021-06-11T12:44:32.633Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574342,167,0,17,541332,541699,'Attribute',TO_TIMESTAMP('2021-06-11 14:44:32','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Merkmal',0,10,TO_TIMESTAMP('2021-06-11 14:44:32','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-11T12:44:32.634Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574342 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-11T12:44:32.635Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(167)
;

-- 2021-06-11T12:58:35.856Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574343,187,0,19,541699,'C_BPartner_ID',TO_TIMESTAMP('2021-06-11 14:58:35','YYYY-MM-DD HH24:MI:SS'),100,'N','Bezeichnet einen Geschäftspartner','D',0,10,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Geschäftspartner',0,0,TO_TIMESTAMP('2021-06-11 14:58:35','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-06-11T12:58:35.857Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574343 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-06-11T12:58:35.858Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(187)
;


-- 2021-06-11T12:58:36.275Z
-- URL zum Konzept
--ALTER TABLE AD_User_Attribute ADD CONSTRAINT CBPartner_ADUserAttribute FOREIGN KEY (C_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
--;

-- Tab


-- 2021-06-11T13:01:33.453Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,574343,579333,0,544008,541699,123,'Y',TO_TIMESTAMP('2021-06-11 15:01:33','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','AD_User_Attribute','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'User Attribute','N',270,1,TO_TIMESTAMP('2021-06-11 15:01:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-11T13:01:33.454Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=544008 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2021-06-11T13:01:33.455Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(579333)
;

-- 2021-06-11T13:01:33.459Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(544008)
;

-- 2021-06-11T13:13:09.873Z
-- URL zum Konzept
UPDATE AD_Tab SET AD_Column_ID=574341, TabLevel=2,Updated=TO_TIMESTAMP('2021-06-11 15:13:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544008
;

-- 2021-06-11T13:14:39.798Z
-- URL zum Konzept
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=574343
;

-- 2021-06-11T13:14:39.801Z
-- URL zum Konzept
DELETE FROM AD_Column WHERE AD_Column_ID=574343
;

-- 2021-06-11T13:24:07.225Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,574342,647506,0,544008,0,TO_TIMESTAMP('2021-06-11 15:24:07','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Merkmal',10,10,0,1,1,TO_TIMESTAMP('2021-06-11 15:24:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-11T13:24:07.226Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647506 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-11T13:24:07.227Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(167)
;

-- 2021-06-11T13:24:07.230Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647506
;

-- 2021-06-11T13:24:07.231Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(647506)
;

-- 2021-06-11T13:24:32.818Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,574333,647507,0,544008,0,TO_TIMESTAMP('2021-06-11 15:24:32','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',0,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','Y','Y','N','N','N','N','N','Mandant',20,20,0,1,1,TO_TIMESTAMP('2021-06-11 15:24:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-11T13:24:32.820Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647507 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-11T13:24:32.823Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2021-06-11T13:24:33.146Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647507
;

-- 2021-06-11T13:24:33.147Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(647507)
;

-- 2021-06-11T13:24:40.440Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,574334,647508,0,544008,0,TO_TIMESTAMP('2021-06-11 15:24:40','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',0,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','Y','N','N','N','N','N','Sektion',30,30,0,1,1,TO_TIMESTAMP('2021-06-11 15:24:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-11T13:24:40.443Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647508 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-11T13:24:40.446Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2021-06-11T13:24:40.567Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647508
;

-- 2021-06-11T13:24:40.567Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(647508)
;

-- 2021-06-11T13:25:18.617Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,574337,647509,0,544008,0,TO_TIMESTAMP('2021-06-11 15:25:18','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',0,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','Y','N','N','N','N','N','Aktiv',40,40,0,1,1,TO_TIMESTAMP('2021-06-11 15:25:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-11T13:25:18.618Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647509 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-11T13:25:18.620Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2021-06-11T13:25:18.808Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647509
;

-- 2021-06-11T13:25:18.808Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(647509)
;

-- 2021-06-11T13:25:31.772Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,574341,647510,0,544008,0,TO_TIMESTAMP('2021-06-11 15:25:31','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact',0,'D','The User identifies a unique user in the system. This could be an internal user or a business partner contact',0,'Y','Y','Y','N','N','N','N','N','Ansprechpartner',50,50,0,1,1,TO_TIMESTAMP('2021-06-11 15:25:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-11T13:25:31.773Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647510 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-11T13:25:31.774Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(138)
;

-- 2021-06-11T13:25:31.781Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647510
;

-- 2021-06-11T13:25:31.781Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(647510)
;

-- 2021-06-11T13:25:49.177Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,574340,647511,0,544008,0,TO_TIMESTAMP('2021-06-11 15:25:49','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','User Attribute',60,60,0,1,1,TO_TIMESTAMP('2021-06-11 15:25:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-11T13:25:49.180Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=647511 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-06-11T13:25:49.184Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579333)
;

-- 2021-06-11T13:25:49.192Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=647511
;

-- 2021-06-11T13:25:49.193Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(647511)
;

-- 2021-06-11T13:26:33.419Z
-- URL zum Konzept
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,544008,543156,TO_TIMESTAMP('2021-06-11 15:26:33','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-06-11 15:26:33','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2021-06-11T13:26:33.420Z
-- URL zum Konzept
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=543156 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2021-06-11T13:26:38.181Z
-- URL zum Konzept
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,543966,543156,TO_TIMESTAMP('2021-06-11 15:26:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2021-06-11 15:26:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-11T13:26:44.708Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543966,545981,TO_TIMESTAMP('2021-06-11 15:26:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,TO_TIMESTAMP('2021-06-11 15:26:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-06-11T13:27:00.406Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,647506,0,544008,545981,585704,'F',TO_TIMESTAMP('2021-06-11 15:27:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Merkmal',10,0,0,TO_TIMESTAMP('2021-06-11 15:27:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Link the User Attribute to Contact Tab

-- 2021-06-11T13:30:08.067Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Labels_Selector_Field_ID,Labels_Tab_ID,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,0,496,540578,585705,'L',TO_TIMESTAMP('2021-06-11 15:30:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',647506,544008,0,'Attribute',250,0,0,TO_TIMESTAMP('2021-06-11 15:30:08','YYYY-MM-DD HH24:MI:SS'),100)
;
