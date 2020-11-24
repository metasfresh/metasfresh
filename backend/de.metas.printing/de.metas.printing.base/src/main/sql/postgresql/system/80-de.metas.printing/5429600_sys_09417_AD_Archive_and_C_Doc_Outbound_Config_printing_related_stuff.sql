
--
-- this also updates sone items' AD_EntityType, which formerly belonged to de.metas.document.archive
--

-- 08.10.2015 06:25
-- URL zum Konzept
UPDATE AD_Element SET Description='Entscheidet, ob beim erstellen des Druckstücks (Archiv) automatisch eine Druck-Warteschlange-Datensatz erstellt werden soll', Name='In Druck-Warteschlange', PrintName='In Druck-Warteschlange',Updated=TO_TIMESTAMP('2015-10-08 06:25:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542117
;

-- 08.10.2015 06:25
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542117
;

-- 08.10.2015 06:25
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsDirectEnqueue', Name='In Druck-Warteschlange', Description='Entscheidet, ob beim erstellen des Druckstücks (Archiv) automatisch eine Druck-Warteschlange-Datensatz erstellt werden soll', Help=NULL WHERE AD_Element_ID=542117
;

-- 08.10.2015 06:25
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsDirectEnqueue', Name='In Druck-Warteschlange', Description='Entscheidet, ob beim erstellen des Druckstücks (Archiv) automatisch eine Druck-Warteschlange-Datensatz erstellt werden soll', Help=NULL, AD_Element_ID=542117 WHERE UPPER(ColumnName)='ISDIRECTENQUEUE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 08.10.2015 06:25
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsDirectEnqueue', Name='In Druck-Warteschlange', Description='Entscheidet, ob beim erstellen des Druckstücks (Archiv) automatisch eine Druck-Warteschlange-Datensatz erstellt werden soll', Help=NULL WHERE AD_Element_ID=542117 AND IsCentrallyMaintained='Y'
;

-- 08.10.2015 06:25
-- URL zum Konzept
UPDATE AD_Field SET Name='In Druck-Warteschlange', Description='Entscheidet, ob beim erstellen des Druckstücks (Archiv) automatisch eine Druck-Warteschlange-Datensatz erstellt werden soll', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542117) AND IsCentrallyMaintained='Y'
;

-- 08.10.2015 06:25
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='In Druck-Warteschlange', Name='In Druck-Warteschlange' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542117)
;

-- 08.10.2015 06:26
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552771,542655,0,20,540434,'N','IsCreatePrintJob',TO_TIMESTAMP('2015-10-08 06:26:30','YYYY-MM-DD HH24:MI:SS'),100,'N','N','If yes, creates print job directly. Else this item will just be enqueued.','de.metas.document.archive',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Create Print Job',0,TO_TIMESTAMP('2015-10-08 06:26:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 08.10.2015 06:26
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552771 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 08.10.2015 06:28
-- URL zum Konzept
UPDATE AD_Element SET Description='Entscheidet, ob beim Erstellen eines Druck-Warteschlange Eintrags auch sofort ein einzelner Druckjob erstellt wird', Name='Sofort Druckjob erstellen', PrintName='Sofort Druckjob erstellen',Updated=TO_TIMESTAMP('2015-10-08 06:28:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542655
;

-- 08.10.2015 06:28
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=542655
;

-- 08.10.2015 06:28
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsCreatePrintJob', Name='Sofort Druckjob erstellen', Description='Entscheidet, ob beim Erstellen eines Druck-Warteschlange Eintrags auch sofort ein einzelner Druckjob erstellt wird', Help=NULL WHERE AD_Element_ID=542655
;

-- 08.10.2015 06:28
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsCreatePrintJob', Name='Sofort Druckjob erstellen', Description='Entscheidet, ob beim Erstellen eines Druck-Warteschlange Eintrags auch sofort ein einzelner Druckjob erstellt wird', Help=NULL, AD_Element_ID=542655 WHERE UPPER(ColumnName)='ISCREATEPRINTJOB' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 08.10.2015 06:28
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsCreatePrintJob', Name='Sofort Druckjob erstellen', Description='Entscheidet, ob beim Erstellen eines Druck-Warteschlange Eintrags auch sofort ein einzelner Druckjob erstellt wird', Help=NULL WHERE AD_Element_ID=542655 AND IsCentrallyMaintained='Y'
;

-- 08.10.2015 06:28
-- URL zum Konzept
UPDATE AD_Field SET Name='Sofort Druckjob erstellen', Description='Entscheidet, ob beim Erstellen eines Druck-Warteschlange Eintrags auch sofort ein einzelner Druckjob erstellt wird', Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542655) AND IsCentrallyMaintained='Y'
;

-- 08.10.2015 06:28
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Sofort Druckjob erstellen', Name='Sofort Druckjob erstellen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542655)
;

-- 08.10.2015 06:29
-- URL zum Konzept
INSERT INTO t_alter_column values('c_doc_outbound_config','IsDirectEnqueue','CHAR(1)',null,'Y')
;

-- 08.10.2015 06:29
-- URL zum Konzept
UPDATE C_Doc_Outbound_Config SET IsDirectEnqueue='Y' WHERE IsDirectEnqueue IS NULL
;

-- 08.10.2015 06:30
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,552771,556352,0,540477,0,TO_TIMESTAMP('2015-10-08 06:30:21','YYYY-MM-DD HH24:MI:SS'),100,'Entscheidet, ob beim Erstellen eines Druck-Warteschlange Eintrags auch sofort ein einzelner Druckjob erstellt wird',0,'de.metas.printing',0,'Y','Y','Y','Y','N','N','N','N','Y','Sofort Druckjob erstellen',80,80,0,TO_TIMESTAMP('2015-10-08 06:30:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 08.10.2015 06:30
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556352 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 08.10.2015 06:30
-- URL zum Konzept
UPDATE AD_Field SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2015-10-08 06:30:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552024
;

-- 08.10.2015 06:31
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2015-10-08 06:31:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549041
;

-- 08.10.2015 06:31
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2015-10-08 06:31:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552771
;

-- 08.10.2015 06:32
-- URL zum Konzept
UPDATE AD_Element SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2015-10-08 06:32:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542655
;

-- 08.10.2015 06:32
-- URL zum Konzept
UPDATE AD_Element SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2015-10-08 06:32:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542117
;

-- 08.10.2015 09:44
-- URL zum Konzept
UPDATE AD_Column SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2015-10-08 09:44:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551544
;

-- 08.10.2015 09:45
-- URL zum Konzept
UPDATE AD_Field SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2015-10-08 09:45:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551545
;

-- 08.10.2015 09:45
-- URL zum Konzept
UPDATE AD_Field SET EntityType='de.metas.printing',Updated=TO_TIMESTAMP('2015-10-08 09:45:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555208
;



COMMIT;

-- 08.10.2015 08:26
-- URL zum Konzept
ALTER TABLE C_Doc_Outbound_Config ADD IsCreatePrintJob CHAR(1) DEFAULT 'N' CHECK (IsCreatePrintJob IN ('Y','N')) NOT NULL
;

