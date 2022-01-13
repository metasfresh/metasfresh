-- 2021-05-04T10:26:56.783Z
-- URL zum Konzept
UPDATE AD_Tab SET IsActive='N',Updated=TO_TIMESTAMP('2021-05-04 13:26:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=640
;

-- 2021-05-04T11:27:11.752Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573764,504410,0,19,261,'AD_BoilerPlate_ID',TO_TIMESTAMP('2021-05-04 14:27:10','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Textbaustein',0,0,TO_TIMESTAMP('2021-05-04 14:27:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-05-04T11:27:12.325Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573764 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-05-04T11:27:12.517Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(504410) 
;

-- 2021-05-04T11:30:57.834Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=540008,Updated=TO_TIMESTAMP('2021-05-04 14:30:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573764
;

-- 2021-05-04T11:31:13.707Z
-- URL zum Konzept
ALTER TABLE C_Tax ADD CONSTRAINT ADBoilerPlate_CTax FOREIGN KEY (AD_BoilerPlate_ID) REFERENCES public.AD_BoilerPlate DEFERRABLE INITIALLY DEFERRED
;

-- 2021-05-04T11:35:59.087Z
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='Y',Updated=TO_TIMESTAMP('2021-05-04 14:35:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=3053
;

-- 2021-05-04T11:36:14.997Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_tax','IsDocumentLevel','CHAR(1)',null,'Y')
;

-- 2021-05-04T11:36:15.956Z
-- URL zum Konzept
UPDATE C_Tax SET IsDocumentLevel='Y' WHERE IsDocumentLevel IS NULL
;

-- 2021-05-04T11:36:45.578Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_tax','IsDocumentLevel','CHAR(1)',null,'Y')
;

-- 2021-05-04T11:36:46.554Z
-- URL zum Konzept
UPDATE C_Tax SET IsDocumentLevel='Y' WHERE IsDocumentLevel IS NULL
;

-- 2021-05-04T11:37:55.280Z
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='N',Updated=TO_TIMESTAMP('2021-05-04 14:37:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=4211
;

-- 2021-05-04T11:38:18.840Z
-- URL zum Konzept
INSERT INTO t_alter_column values('c_tax','IsDefault','CHAR(1)',null,'N')
;

-- 2021-05-04T11:38:19.744Z
-- URL zum Konzept
UPDATE C_Tax SET IsDefault='N' WHERE IsDefault IS NULL
;

-- 2021-05-04T11:40:04.681Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,573764,645189,0,174,0,TO_TIMESTAMP('2021-05-04 14:40:03','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Textbaustein',270,200,0,1,1,TO_TIMESTAMP('2021-05-04 14:40:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-04T11:40:04.914Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=645189 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-04T11:40:04.959Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(504410) 
;

-- 2021-05-04T11:40:05.030Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=645189
;

-- 2021-05-04T11:40:05.074Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(645189)
;

-- 2021-05-04T11:40:14.374Z
-- URL zum Konzept
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2021-05-04 14:40:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=645189
;

-- 2021-05-04T11:43:54.341Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2021-05-04 14:43:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544922
;

-- 2021-05-04T11:44:09.113Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2021-05-04 14:44:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544923
;

-- 2021-05-04T11:45:15.355Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2021-05-04 14:45:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544934
;

-- 2021-05-04T11:46:09.532Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2021-05-04 14:46:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544932
;

-- 2021-05-04T11:46:25.141Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2021-05-04 14:46:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544933
;

-- 2021-05-04T11:47:06.392Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2021-05-04 14:47:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544936
;

-- 2021-05-04T11:47:47.419Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2021-05-04 14:47:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544937
;

-- 2021-05-04T11:51:01.372Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2021-05-04 14:51:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544929
;

-- 2021-05-04T11:51:24.186Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2021-05-04 14:51:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544928
;

-- 2021-05-04T12:05:09.750Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579119,0,TO_TIMESTAMP('2021-05-04 15:05:09','YYYY-MM-DD HH24:MI:SS'),100,'Service provider''s country','D','Service provider''s country','Y','Country of origin','Country of origin',TO_TIMESTAMP('2021-05-04 15:05:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-04T12:05:10.033Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579119 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-04T12:05:45.461Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=579119, Description='Service provider''s country', Help='Service provider''s country', Name='Country of origin',Updated=TO_TIMESTAMP('2021-05-04 15:05:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=974
;

-- 2021-05-04T12:05:45.553Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579119) 
;

-- 2021-05-04T12:05:45.601Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=974
;

-- 2021-05-04T12:05:45.645Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(974)
;

-- 2021-05-04T12:24:18.612Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Land des Leistungserbringers', Help='Land des Leistungserbringers', IsTranslated='Y', Name='Ursprungsland', PrintName='Ursprungsland',Updated=TO_TIMESTAMP('2021-05-04 15:24:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579119 AND AD_Language='de_CH'
;

-- 2021-05-04T12:24:18.857Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579119,'de_CH') 
;

-- 2021-05-04T12:24:36.104Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Land des Leistungserbringers', Help='Land des Leistungserbringers', IsTranslated='Y', Name='Ursprungsland', PrintName='Ursprungsland',Updated=TO_TIMESTAMP('2021-05-04 15:24:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579119 AND AD_Language='de_DE'
;

-- 2021-05-04T12:24:36.158Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579119,'de_DE') 
;

-- 2021-05-04T12:24:36.294Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579119,'de_DE') 
;

-- 2021-05-04T12:24:36.344Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Ursprungsland', Description='Land des Leistungserbringers', Help='Land des Leistungserbringers' WHERE AD_Element_ID=579119
;

-- 2021-05-04T12:24:36.389Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Ursprungsland', Description='Land des Leistungserbringers', Help='Land des Leistungserbringers' WHERE AD_Element_ID=579119 AND IsCentrallyMaintained='Y'
;

-- 2021-05-04T12:24:36.433Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Ursprungsland', Description='Land des Leistungserbringers', Help='Land des Leistungserbringers' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579119) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579119)
;

-- 2021-05-04T12:24:36.497Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Ursprungsland', Name='Ursprungsland' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579119)
;

-- 2021-05-04T12:24:36.546Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Ursprungsland', Description='Land des Leistungserbringers', Help='Land des Leistungserbringers', CommitWarning = NULL WHERE AD_Element_ID = 579119
;

-- 2021-05-04T12:24:36.596Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Ursprungsland', Description='Land des Leistungserbringers', Help='Land des Leistungserbringers' WHERE AD_Element_ID = 579119
;

-- 2021-05-04T12:24:36.638Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Ursprungsland', Description = 'Land des Leistungserbringers', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579119
;

-- 2021-05-04T12:24:42.182Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-04 15:24:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579119 AND AD_Language='en_US'
;

-- 2021-05-04T12:24:42.224Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579119,'en_US') 
;

-- 2021-05-04T12:24:58.241Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Land des Leistungserbringers', Help='Land des Leistungserbringers', Name='Ursprungsland', PrintName='Ursprungsland',Updated=TO_TIMESTAMP('2021-05-04 15:24:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579119 AND AD_Language='nl_NL'
;

-- 2021-05-04T12:24:58.282Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579119,'nl_NL') 
;

-- 2021-05-04T12:25:59.045Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579120,0,TO_TIMESTAMP('2021-05-04 15:25:58','YYYY-MM-DD HH24:MI:SS'),100,'Country in which the service is provided or to which the goods are delivered.','D','Country in which the service is provided or to which the goods are delivered.','Y','Destination country','Destination country',TO_TIMESTAMP('2021-05-04 15:25:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-04T12:25:59.703Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579120 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-04T12:26:46.660Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird', Help='Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird', IsTranslated='Y', Name='Bestimmungsland', PrintName='Bestimmungsland',Updated=TO_TIMESTAMP('2021-05-04 15:26:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579120 AND AD_Language='de_CH'
;

-- 2021-05-04T12:26:46.883Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579120,'de_CH') 
;

-- 2021-05-04T12:27:06.586Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird', Help='Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird', IsTranslated='Y', Name='Bestimmungsland', PrintName='Bestimmungsland',Updated=TO_TIMESTAMP('2021-05-04 15:27:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579120 AND AD_Language='de_DE'
;

-- 2021-05-04T12:27:06.634Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579120,'de_DE') 
;

-- 2021-05-04T12:27:06.744Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579120,'de_DE') 
;

-- 2021-05-04T12:27:06.800Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Bestimmungsland', Description='Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird', Help='Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird' WHERE AD_Element_ID=579120
;

-- 2021-05-04T12:27:06.885Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Bestimmungsland', Description='Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird', Help='Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird' WHERE AD_Element_ID=579120 AND IsCentrallyMaintained='Y'
;

-- 2021-05-04T12:27:06.934Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Bestimmungsland', Description='Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird', Help='Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579120) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579120)
;

-- 2021-05-04T12:27:07.015Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Bestimmungsland', Name='Bestimmungsland' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579120)
;

-- 2021-05-04T12:27:07.062Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Bestimmungsland', Description='Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird', Help='Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird', CommitWarning = NULL WHERE AD_Element_ID = 579120
;

-- 2021-05-04T12:27:07.115Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Bestimmungsland', Description='Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird', Help='Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird' WHERE AD_Element_ID = 579120
;

-- 2021-05-04T12:27:07.162Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Bestimmungsland', Description = 'Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579120
;

-- 2021-05-04T12:27:14.330Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-04 15:27:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579120 AND AD_Language='en_US'
;

-- 2021-05-04T12:27:14.381Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579120,'en_US') 
;

-- 2021-05-04T12:27:45.723Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird', Help='Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird', Name='Bestimmungsland', PrintName='Bestimmungsland',Updated=TO_TIMESTAMP('2021-05-04 15:27:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579120 AND AD_Language='nl_NL'
;

-- 2021-05-04T12:27:45.770Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579120,'nl_NL') 
;

-- 2021-05-04T12:27:59.574Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird.', Help='Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird.',Updated=TO_TIMESTAMP('2021-05-04 15:27:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579120 AND AD_Language='de_DE'
;

-- 2021-05-04T12:27:59.701Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579120,'de_DE') 
;

-- 2021-05-04T12:27:59.813Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579120,'de_DE') 
;

-- 2021-05-04T12:27:59.868Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Bestimmungsland', Description='Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird.', Help='Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird.' WHERE AD_Element_ID=579120
;

-- 2021-05-04T12:27:59.909Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Bestimmungsland', Description='Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird.', Help='Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird.' WHERE AD_Element_ID=579120 AND IsCentrallyMaintained='Y'
;

-- 2021-05-04T12:27:59.975Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Bestimmungsland', Description='Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird.', Help='Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579120) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579120)
;

-- 2021-05-04T12:28:00.066Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Bestimmungsland', Description='Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird.', Help='Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird.', CommitWarning = NULL WHERE AD_Element_ID = 579120
;

-- 2021-05-04T12:28:00.125Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Bestimmungsland', Description='Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird.', Help='Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird.' WHERE AD_Element_ID = 579120
;

-- 2021-05-04T12:28:00.166Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Bestimmungsland', Description = 'Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579120
;

-- 2021-05-04T12:28:15.400Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird.', Help='Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird.',Updated=TO_TIMESTAMP('2021-05-04 15:28:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579120 AND AD_Language='de_CH'
;

-- 2021-05-04T12:28:15.451Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579120,'de_CH') 
;

-- 2021-05-04T12:28:46.565Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=579120, Description='Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird.', Help='Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird.', Name='Bestimmungsland',Updated=TO_TIMESTAMP('2021-05-04 15:28:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=976
;

-- 2021-05-04T12:28:46.706Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579120) 
;

-- 2021-05-04T12:28:46.783Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=976
;

-- 2021-05-04T12:28:46.826Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(976)
;

-- 2021-05-04T12:32:39.871Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579121,0,TO_TIMESTAMP('2021-05-04 15:32:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Partner has a VAT Tax ID','Partner has a VAT Tax ID',TO_TIMESTAMP('2021-05-04 15:32:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-04T12:32:40.033Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579121 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-05-04T12:33:17.694Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Matches only if the corresponding business partner has a VAT-ID.', Help='Matches only if the corresponding business partner has a VAT-ID.', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-04 15:33:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579121 AND AD_Language='en_US'
;

-- 2021-05-04T12:33:17.744Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579121,'en_US') 
;

-- 2021-05-04T12:33:36.389Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Partner hat eine Ust.-ID', PrintName='Partner hat eine Ust.-ID',Updated=TO_TIMESTAMP('2021-05-04 15:33:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579121 AND AD_Language='de_CH'
;

-- 2021-05-04T12:33:36.432Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579121,'de_CH') 
;

-- 2021-05-04T12:33:42.871Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Partner hat eine Ust.-ID', PrintName='Partner hat eine Ust.-ID',Updated=TO_TIMESTAMP('2021-05-04 15:33:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579121 AND AD_Language='de_DE'
;

-- 2021-05-04T12:33:42.946Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579121,'de_DE') 
;

-- 2021-05-04T12:33:43.040Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579121,'de_DE') 
;

-- 2021-05-04T12:33:43.083Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Partner hat eine Ust.-ID', Description=NULL, Help=NULL WHERE AD_Element_ID=579121
;

-- 2021-05-04T12:33:43.130Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Partner hat eine Ust.-ID', Description=NULL, Help=NULL WHERE AD_Element_ID=579121 AND IsCentrallyMaintained='Y'
;

-- 2021-05-04T12:33:43.171Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Partner hat eine Ust.-ID', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579121) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579121)
;

-- 2021-05-04T12:33:43.252Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Partner hat eine Ust.-ID', Name='Partner hat eine Ust.-ID' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579121)
;

-- 2021-05-04T12:33:43.330Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Partner hat eine Ust.-ID', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579121
;

-- 2021-05-04T12:33:43.379Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Partner hat eine Ust.-ID', Description=NULL, Help=NULL WHERE AD_Element_ID = 579121
;

-- 2021-05-04T12:33:43.425Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Partner hat eine Ust.-ID', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579121
;

-- 2021-05-04T12:33:50.991Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Partner hat eine Ust.-ID', PrintName='Partner hat eine Ust.-ID',Updated=TO_TIMESTAMP('2021-05-04 15:33:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579121 AND AD_Language='nl_NL'
;

-- 2021-05-04T12:33:51.043Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579121,'nl_NL') 
;

-- 2021-05-04T12:34:04.281Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Matcht nur, wenn der betreffende Geschäftspartner eine Umsatzsteuer-ID hat.',Updated=TO_TIMESTAMP('2021-05-04 15:34:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579121 AND AD_Language='nl_NL'
;

-- 2021-05-04T12:34:04.324Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579121,'nl_NL') 
;

-- 2021-05-04T12:34:45.227Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Matcht nur, wenn der betreffende Geschäftspartner eine Umsatzsteuer-ID hat.', Help='Matcht nur, wenn der betreffende Geschäftspartner eine Umsatzsteuer-ID hat.',Updated=TO_TIMESTAMP('2021-05-04 15:34:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579121 AND AD_Language='de_DE'
;

-- 2021-05-04T12:34:45.279Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579121,'de_DE') 
;

-- 2021-05-04T12:34:45.361Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579121,'de_DE') 
;

-- 2021-05-04T12:34:45.404Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Partner hat eine Ust.-ID', Description='Matcht nur, wenn der betreffende Geschäftspartner eine Umsatzsteuer-ID hat.', Help='Matcht nur, wenn der betreffende Geschäftspartner eine Umsatzsteuer-ID hat.' WHERE AD_Element_ID=579121
;

-- 2021-05-04T12:34:45.445Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Partner hat eine Ust.-ID', Description='Matcht nur, wenn der betreffende Geschäftspartner eine Umsatzsteuer-ID hat.', Help='Matcht nur, wenn der betreffende Geschäftspartner eine Umsatzsteuer-ID hat.' WHERE AD_Element_ID=579121 AND IsCentrallyMaintained='Y'
;

-- 2021-05-04T12:34:45.500Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Partner hat eine Ust.-ID', Description='Matcht nur, wenn der betreffende Geschäftspartner eine Umsatzsteuer-ID hat.', Help='Matcht nur, wenn der betreffende Geschäftspartner eine Umsatzsteuer-ID hat.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579121) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579121)
;

-- 2021-05-04T12:34:45.545Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Partner hat eine Ust.-ID', Description='Matcht nur, wenn der betreffende Geschäftspartner eine Umsatzsteuer-ID hat.', Help='Matcht nur, wenn der betreffende Geschäftspartner eine Umsatzsteuer-ID hat.', CommitWarning = NULL WHERE AD_Element_ID = 579121
;

-- 2021-05-04T12:34:45.592Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Partner hat eine Ust.-ID', Description='Matcht nur, wenn der betreffende Geschäftspartner eine Umsatzsteuer-ID hat.', Help='Matcht nur, wenn der betreffende Geschäftspartner eine Umsatzsteuer-ID hat.' WHERE AD_Element_ID = 579121
;

-- 2021-05-04T12:34:45.632Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Partner hat eine Ust.-ID', Description = 'Matcht nur, wenn der betreffende Geschäftspartner eine Umsatzsteuer-ID hat.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579121
;

-- 2021-05-04T12:34:58.178Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Matcht nur, wenn der betreffende Geschäftspartner eine Umsatzsteuer-ID hat.', Help='Matcht nur, wenn der betreffende Geschäftspartner eine Umsatzsteuer-ID hat.',Updated=TO_TIMESTAMP('2021-05-04 15:34:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579121 AND AD_Language='de_CH'
;

-- 2021-05-04T12:34:58.221Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579121,'de_CH') 
;

-- 2021-05-04T12:35:30.592Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=579121, Description='Matcht nur, wenn der betreffende Geschäftspartner eine Umsatzsteuer-ID hat.', Help='Matcht nur, wenn der betreffende Geschäftspartner eine Umsatzsteuer-ID hat.', Name='Partner hat eine Ust.-ID',Updated=TO_TIMESTAMP('2021-05-04 15:35:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2872
;

-- 2021-05-04T12:35:30.683Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579121) 
;

-- 2021-05-04T12:35:30.732Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=2872
;

-- 2021-05-04T12:35:30.773Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(2872)
;

-- 2021-05-04T12:39:31.703Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Matches only if the country in which the service is provided lies within the EU.', IsTranslated='Y', Name='IsToEULocation', PrintName='IsToEULocation',Updated=TO_TIMESTAMP('2021-05-04 15:39:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53702 AND AD_Language='en_US'
;

-- 2021-05-04T12:39:31.751Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53702,'en_US') 
;

-- 2021-05-04T12:39:44.882Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Matches only if the country in which the service is provided lies within the EU.', Name='IsToEULocation', PrintName='IsToEULocation',Updated=TO_TIMESTAMP('2021-05-04 15:39:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53702 AND AD_Language='en_GB'
;

-- 2021-05-04T12:39:44.924Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53702,'en_GB') 
;

-- 2021-05-04T12:40:01.752Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Matcht nur, wenn das Land, in dem die Leistung erbracht wird, in der EU liegt.', Help='Matcht nur, wenn das Land, in dem die Leistung erbracht wird, in der EU liegt.', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-04 15:40:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53702 AND AD_Language='de_DE'
;

-- 2021-05-04T12:40:01.802Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53702,'de_DE') 
;

-- 2021-05-04T12:40:01.890Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(53702,'de_DE') 
;

-- 2021-05-04T12:40:01.934Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsToEULocation', Name='Nach EU', Description='Matcht nur, wenn das Land, in dem die Leistung erbracht wird, in der EU liegt.', Help='Matcht nur, wenn das Land, in dem die Leistung erbracht wird, in der EU liegt.' WHERE AD_Element_ID=53702
;

-- 2021-05-04T12:40:01.974Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsToEULocation', Name='Nach EU', Description='Matcht nur, wenn das Land, in dem die Leistung erbracht wird, in der EU liegt.', Help='Matcht nur, wenn das Land, in dem die Leistung erbracht wird, in der EU liegt.', AD_Element_ID=53702 WHERE UPPER(ColumnName)='ISTOEULOCATION' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-04T12:40:02.023Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsToEULocation', Name='Nach EU', Description='Matcht nur, wenn das Land, in dem die Leistung erbracht wird, in der EU liegt.', Help='Matcht nur, wenn das Land, in dem die Leistung erbracht wird, in der EU liegt.' WHERE AD_Element_ID=53702 AND IsCentrallyMaintained='Y'
;

-- 2021-05-04T12:40:02.065Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Nach EU', Description='Matcht nur, wenn das Land, in dem die Leistung erbracht wird, in der EU liegt.', Help='Matcht nur, wenn das Land, in dem die Leistung erbracht wird, in der EU liegt.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=53702) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 53702)
;

-- 2021-05-04T12:40:02.123Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Nach EU', Description='Matcht nur, wenn das Land, in dem die Leistung erbracht wird, in der EU liegt.', Help='Matcht nur, wenn das Land, in dem die Leistung erbracht wird, in der EU liegt.', CommitWarning = NULL WHERE AD_Element_ID = 53702
;

-- 2021-05-04T12:40:02.164Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Nach EU', Description='Matcht nur, wenn das Land, in dem die Leistung erbracht wird, in der EU liegt.', Help='Matcht nur, wenn das Land, in dem die Leistung erbracht wird, in der EU liegt.' WHERE AD_Element_ID = 53702
;

-- 2021-05-04T12:40:02.211Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Nach EU', Description = 'Matcht nur, wenn das Land, in dem die Leistung erbracht wird, in der EU liegt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 53702
;

-- 2021-05-04T12:40:08.557Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Matcht nur, wenn das Land, in dem die Leistung erbracht wird, in der EU liegt.', Help='Matcht nur, wenn das Land, in dem die Leistung erbracht wird, in der EU liegt.',Updated=TO_TIMESTAMP('2021-05-04 15:40:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53702 AND AD_Language='nl_NL'
;

-- 2021-05-04T12:40:08.603Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53702,'nl_NL') 
;

-- 2021-05-04T12:40:17.192Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Matcht nur, wenn das Land, in dem die Leistung erbracht wird, in der EU liegt.', Help='Matcht nur, wenn das Land, in dem die Leistung erbracht wird, in der EU liegt.',Updated=TO_TIMESTAMP('2021-05-04 15:40:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53702 AND AD_Language='de_CH'
;

-- 2021-05-04T12:40:17.239Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53702,'de_CH') 
;

-- 2021-05-04T12:48:25.040Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2021-05-04 15:48:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544926
;

-- 2021-05-04T12:48:36.792Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2021-05-04 15:48:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544927
;

-- 2021-05-04T12:51:14.794Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2021-05-04 15:51:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544925
;

-- 2021-05-04T12:52:09.178Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2021-05-04 15:52:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544935
;

-- 2021-05-04T12:53:37.984Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2021-05-04 15:53:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544929
;

-- 2021-05-04T12:56:58.735Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,6121,0,174,540521,584456,'F',TO_TIMESTAMP('2021-05-04 15:56:58','YYYY-MM-DD HH24:MI:SS'),100,'Steuersatz steuerbefreit','If a business partner is exempt from tax on sales, the exempt tax rate is used. For this, you need to set up a tax rate with a 0% rate and indicate that this is your tax exempt rate.  This is required for tax reporting, so that you can track tax exempt transactions.','Y','N','N','Y','N','N','N',0,'Steuerbefreit',50,0,0,TO_TIMESTAMP('2021-05-04 15:56:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-04T12:58:11.329Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=584456
;

-- 2021-05-04T12:59:29.567Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,74290,0,174,540521,584457,'F',TO_TIMESTAMP('2021-05-04 15:59:29','YYYY-MM-DD HH24:MI:SS'),100,'If this flag is set, in a tax aware document (e.g. Invoice, Order) this tax will absorb the whole amount, leaving 0 for base amount','If this flag is set, in a tax aware document (e.g. Invoice, Order) this tax will absorb the whole amount, leaving 0 for base amount.

Using this feature is useful when you have to create invoices/orders that contain only taxes on a line (e.g. custom authorities invoices).','Y','N','N','Y','N','N','N',0,'Steuer Ausschließlich',50,0,0,TO_TIMESTAMP('2021-05-04 15:59:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-04T13:00:12.512Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2021-05-04 16:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544928
;

-- 2021-05-04T13:01:42.978Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2021-05-04 16:01:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544939
;

-- 2021-05-04T13:02:14.882Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540306,545777,TO_TIMESTAMP('2021-05-04 16:02:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','type',20,TO_TIMESTAMP('2021-05-04 16:02:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-04T13:02:21.082Z
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2021-05-04 16:02:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540522
;

-- 2021-05-04T13:03:12.011Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,8195,0,174,545777,584458,'F',TO_TIMESTAMP('2021-05-04 16:03:11','YYYY-MM-DD HH24:MI:SS'),100,'Steuer für Einkauf und/ oder Verkauf Transaktionen.','Sales Tax: charged when selling - examples: Sales Tax, Output VAT (payable)
Purchase Tax: tax charged when purchasing - examples: Use Tax, Input VAT (receivable)','Y','N','N','Y','N','N','N',0,'VK/ EK Typ',10,0,0,TO_TIMESTAMP('2021-05-04 16:03:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-04T13:04:01.334Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,645189,0,174,545777,584459,'F',TO_TIMESTAMP('2021-05-04 16:04:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Textbaustein',20,0,0,TO_TIMESTAMP('2021-05-04 16:04:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-04T13:06:40.772Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,74290,0,174,545777,584460,'F',TO_TIMESTAMP('2021-05-04 16:06:40','YYYY-MM-DD HH24:MI:SS'),100,'If this flag is set, in a tax aware document (e.g. Invoice, Order) this tax will absorb the whole amount, leaving 0 for base amount','If this flag is set, in a tax aware document (e.g. Invoice, Order) this tax will absorb the whole amount, leaving 0 for base amount.

Using this feature is useful when you have to create invoices/orders that contain only taxes on a line (e.g. custom authorities invoices).','Y','N','N','Y','N','N','N',0,'Steuer Ausschließlich',10,0,0,TO_TIMESTAMP('2021-05-04 16:06:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-04T13:07:29.528Z
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET Name='property',Updated=TO_TIMESTAMP('2021-05-04 16:07:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=545777
;

-- 2021-05-04T13:07:41.641Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540306,545778,TO_TIMESTAMP('2021-05-04 16:07:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','typpe',20,TO_TIMESTAMP('2021-05-04 16:07:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-04T13:07:45.212Z
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2021-05-04 16:07:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=545777
;

-- 2021-05-04T13:07:52.962Z
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET SeqNo=40,Updated=TO_TIMESTAMP('2021-05-04 16:07:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540522
;

-- 2021-05-04T13:08:03.527Z
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET Name='type',Updated=TO_TIMESTAMP('2021-05-04 16:08:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=545778
;

-- 2021-05-04T13:08:46.052Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=584458
;

-- 2021-05-04T13:09:44.213Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,8195,0,174,545778,584461,'F',TO_TIMESTAMP('2021-05-04 16:09:43','YYYY-MM-DD HH24:MI:SS'),100,'Steuer für Einkauf und/ oder Verkauf Transaktionen.','Sales Tax: charged when selling - examples: Sales Tax, Output VAT (payable)
Purchase Tax: tax charged when purchasing - examples: Use Tax, Input VAT (receivable)','Y','N','N','Y','N','N','N',0,'VK/ EK Typ',10,0,0,TO_TIMESTAMP('2021-05-04 16:09:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-04T13:10:10.153Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540310,545779,TO_TIMESTAMP('2021-05-04 16:10:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','description',40,TO_TIMESTAMP('2021-05-04 16:10:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-04T13:10:54.461Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,971,0,174,545779,584462,'F',TO_TIMESTAMP('2021-05-04 16:10:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'Beschreibung',10,0,0,TO_TIMESTAMP('2021-05-04 16:10:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-04T13:11:36.561Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=584462
;

-- 2021-05-04T13:11:47.109Z
-- URL zum Konzept
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=545779
;

-- 2021-05-04T13:12:05.491Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540305,545780,TO_TIMESTAMP('2021-05-04 16:12:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','description',30,TO_TIMESTAMP('2021-05-04 16:12:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-04T13:12:40.282Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,971,0,174,545780,584463,'F',TO_TIMESTAMP('2021-05-04 16:12:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Beschreibung',10,0,0,TO_TIMESTAMP('2021-05-04 16:12:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-04T13:13:34.819Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=584461
;

-- 2021-05-04T13:13:45.977Z
-- URL zum Konzept
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=545778
;

-- 2021-05-04T13:14:37.628Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540305,545781,TO_TIMESTAMP('2021-05-04 16:14:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','type',15,TO_TIMESTAMP('2021-05-04 16:14:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-04T13:15:05.493Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,8195,0,174,545781,584464,'F',TO_TIMESTAMP('2021-05-04 16:15:04','YYYY-MM-DD HH24:MI:SS'),100,'Steuer für Einkauf und/ oder Verkauf Transaktionen.','Sales Tax: charged when selling - examples: Sales Tax, Output VAT (payable)
Purchase Tax: tax charged when purchasing - examples: Use Tax, Input VAT (receivable)','Y','N','N','Y','N','N','N',0,'VK/ EK Typ',10,0,0,TO_TIMESTAMP('2021-05-04 16:15:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-04T13:15:45.491Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=584464
;

-- 2021-05-04T13:15:54.881Z
-- URL zum Konzept
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=545781
;

-- 2021-05-04T13:16:33.684Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,8195,0,174,545780,584465,'F',TO_TIMESTAMP('2021-05-04 16:16:33','YYYY-MM-DD HH24:MI:SS'),100,'Steuer für Einkauf und/ oder Verkauf Transaktionen.','Sales Tax: charged when selling - examples: Sales Tax, Output VAT (payable)
Purchase Tax: tax charged when purchasing - examples: Use Tax, Input VAT (receivable)','Y','N','N','Y','N','N','N',0,'VK/ EK Typ',10,0,0,TO_TIMESTAMP('2021-05-04 16:16:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-04T13:16:47.373Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2021-05-04 16:16:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584463
;

-- 2021-05-04T13:19:07.801Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2021-05-04 16:19:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566596
;

-- 2021-05-04T13:19:08.029Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2021-05-04 16:19:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544929
;

-- 2021-05-04T13:19:08.281Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2021-05-04 16:19:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544927
;

-- 2021-05-04T13:19:08.516Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2021-05-04 16:19:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544925
;

-- 2021-05-04T13:19:08.751Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2021-05-04 16:19:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544930
;

-- 2021-05-04T13:19:27.008Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2021-05-04 16:19:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544924
;

-- 2021-05-04T13:19:27.219Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-05-04 16:19:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544916
;

-- 2021-05-04T13:19:27.436Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-05-04 16:19:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544917
;

-- 2021-05-04T13:19:27.686Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-05-04 16:19:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544918
;

-- 2021-05-04T13:19:27.923Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2021-05-04 16:19:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544919
;

-- 2021-05-04T13:19:28.153Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2021-05-04 16:19:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544920
;

-- 2021-05-04T13:19:28.383Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2021-05-04 16:19:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544921
;

-- 2021-05-04T13:21:11.335Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2021-05-04 16:21:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544939
;

-- 2021-05-04T13:21:11.718Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2021-05-04 16:21:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544927
;

-- 2021-05-04T13:21:11.932Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2021-05-04 16:21:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544925
;

-- 2021-05-04T13:21:12.154Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2021-05-04 16:21:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544930
;

-- 2021-05-04T13:21:51.243Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2021-05-04 16:21:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544939
;

-- 2021-05-04T13:21:51.472Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2021-05-04 16:21:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584465
;

-- 2021-05-04T13:23:01.172Z
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2021-05-04 16:23:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584465
;

-- 2021-05-04T13:23:31.901Z
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2021-05-04 16:23:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584465
;

-- 2021-05-04T13:25:06.588Z
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2021-05-04 16:25:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544924
;

-- 2021-05-04T13:25:11.019Z
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2021-05-04 16:25:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544926
;

-- 2021-05-04T13:25:15.450Z
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2021-05-04 16:25:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544927
;

-- 2021-05-04T13:25:19.327Z
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2021-05-04 16:25:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544925
;

-- 2021-05-04T13:25:24.922Z
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2021-05-04 16:25:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584457
;

-- 2021-05-04T13:26:11.600Z
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2021-05-04 16:26:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544919
;

-- 2021-05-04T13:30:13.920Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2021-05-04 16:30:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544916
;

-- 2021-05-04T13:30:14.144Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2021-05-04 16:30:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544917
;

-- 2021-05-04T13:30:14.382Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2021-05-04 16:30:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544918
;

-- 2021-05-04T13:30:14.599Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-05-04 16:30:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544919
;

-- 2021-05-04T13:30:14.818Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2021-05-04 16:30:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544920
;

-- 2021-05-04T13:30:15.041Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2021-05-04 16:30:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544921
;

-- 2021-05-04T13:30:15.272Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2021-05-04 16:30:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584465
;

-- 2021-05-04T13:30:15.502Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2021-05-04 16:30:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584457
;

-- 2021-05-04T13:30:15.762Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2021-05-04 16:30:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544924
;

-- 2021-05-04T13:30:15.986Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2021-05-04 16:30:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544930
;

-- 2021-05-04T13:32:31.033Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2021-05-04 16:32:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544926
;

-- 2021-05-04T13:32:31.432Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2021-05-04 16:32:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584457
;

-- 2021-05-04T13:32:31.650Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2021-05-04 16:32:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544927
;

-- 2021-05-04T13:32:31.872Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2021-05-04 16:32:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544925
;

-- 2021-05-04T13:32:32.080Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2021-05-04 16:32:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544924
;

-- 2021-05-04T13:32:32.304Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2021-05-04 16:32:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544930
;

-- 2021-05-04T13:34:36.990Z
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2021-05-04 16:34:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544923
;

-- 2021-05-04T13:34:47.459Z
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2021-05-04 16:34:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544922
;

-- 2021-05-05T07:04:21.505Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2021-05-05 10:04:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544938
;

-- 2021-05-05T07:20:47.854Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='EK Steuersatz',Updated=TO_TIMESTAMP('2021-05-05 10:20:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=570
;

-- 2021-05-05T07:20:54.991Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET Name='EK Steuersatz',Updated=TO_TIMESTAMP('2021-05-05 10:20:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=570
;

-- 2021-05-05T07:21:02.175Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET Name='EK Steuersatz',Updated=TO_TIMESTAMP('2021-05-05 10:21:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=570
;

-- 2021-05-05T07:21:31.022Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET Name='VK Steuersatz',Updated=TO_TIMESTAMP('2021-05-05 10:21:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=569
;

-- 2021-05-05T07:21:33.980Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-05 10:21:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=569
;

-- 2021-05-05T07:21:41.232Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET Name='VK Steuersatz',Updated=TO_TIMESTAMP('2021-05-05 10:21:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=569
;

-- 2021-05-05T07:21:47.660Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='VK Steuersatz',Updated=TO_TIMESTAMP('2021-05-05 10:21:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=569
;

-- 2021-05-05T07:25:06.391Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Beide',Updated=TO_TIMESTAMP('2021-05-05 10:25:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=568
;

-- 2021-05-05T07:25:10.602Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET Name='Beide',Updated=TO_TIMESTAMP('2021-05-05 10:25:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=568
;

-- 2021-05-05T07:25:16.198Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Beide',Updated=TO_TIMESTAMP('2021-05-05 10:25:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=568
;

-- 2021-05-05T07:27:03.415Z
-- URL zum Konzept
UPDATE AD_Ref_List SET ValueName='Beide',Updated=TO_TIMESTAMP('2021-05-05 10:27:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=568
;

-- 2021-05-05T07:28:44.534Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Name='Beide',Updated=TO_TIMESTAMP('2021-05-05 10:28:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=568
;

-- 2021-05-05T07:28:59.917Z
-- URL zum Konzept
UPDATE AD_Ref_List SET ValueName='Both ',Updated=TO_TIMESTAMP('2021-05-05 10:28:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=568
;

-- 2021-05-05T07:30:30.992Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Name='EK Steuersatz',Updated=TO_TIMESTAMP('2021-05-05 10:30:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=570
;

-- 2021-05-05T07:31:17.811Z
-- URL zum Konzept
UPDATE AD_Ref_List SET Name='VK Steuersatz',Updated=TO_TIMESTAMP('2021-05-05 10:31:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=569
;

-- 2021-05-05T07:43:31.963Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='IsToEULoc', PrintName='IsToEULoc',Updated=TO_TIMESTAMP('2021-05-05 10:43:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53702 AND AD_Language='en_GB'
;

-- 2021-05-05T07:43:32.115Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53702,'en_GB')
;

-- 2021-05-05T07:43:56.242Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='IsToEULoc', PrintName='IsToEULoc',Updated=TO_TIMESTAMP('2021-05-05 10:43:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=53702 AND AD_Language='en_US'
;

-- 2021-05-05T07:43:56.292Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53702,'en_US')
;

-- 2021-05-05T07:47:18.044Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Partner has a VAT Tax ID', Name='ReqTaxCert.',Updated=TO_TIMESTAMP('2021-05-05 10:47:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579121 AND AD_Language='en_US'
;

-- 2021-05-05T07:47:18.095Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579121,'en_US')
;

-- 2021-05-05T07:48:05.699Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='ReqTaxCert',Updated=TO_TIMESTAMP('2021-05-05 10:48:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579121 AND AD_Language='en_US'
;

-- 2021-05-05T07:48:05.741Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579121,'en_US')
;

-- 2021-05-05T07:49:33.904Z
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2021-05-05 10:49:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544920
;

-- 2021-05-05T07:49:48.967Z
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2021-05-05 10:49:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544921
;

-- 2021-05-05T07:50:12.999Z
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2021-05-05 10:50:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544921
;

-- 2021-05-05T07:50:29.394Z
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2021-05-05 10:50:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544920
;

-- 2021-05-07T10:48:36.764Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='If the flag is set, this tax can be used in documents where an entire line amount is a tax amount. Used, e.g., when a tax charge needs to be paid to a customs office.', Help='If the flag is set, this tax can be used in documents where an entire line amount is a tax amount. Used, e.g., when a tax charge needs to be paid to a customs office.',Updated=TO_TIMESTAMP('2021-05-07 13:48:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=57413 AND AD_Language='en_GB'
;

-- 2021-05-07T10:48:36.981Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(57413,'en_GB')
;

-- 2021-05-07T10:48:50.949Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='If the flag is set, this tax can be used in documents where an entire line amount is a tax amount. Used, e.g., when a tax charge needs to be paid to a customs office.', Help='If the flag is set, this tax can be used in documents where an entire line amount is a tax amount. Used, e.g., when a tax charge needs to be paid to a customs office.',Updated=TO_TIMESTAMP('2021-05-07 13:48:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=57413 AND AD_Language='en_US'
;

-- 2021-05-07T10:48:50.985Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(57413,'en_US')
;

-- 2021-05-07T10:48:53.225Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-07 13:48:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=57413 AND AD_Language='en_US'
;

-- 2021-05-07T10:48:53.260Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(57413,'en_US')
;

-- 2021-05-07T10:49:31.330Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Wenn gewählt, wird die Zeilensumme (z.B. in der Auftragszeile) als Steuerbetrag behandelt und nicht als Basis zur Berechnung der Steuer. Dieser Steuersatz wird z.B. in Belegen verwendet, bei denen ein Steuerbetrag an eine Behörde entrichtet werden soll.', Help='Wenn gewählt, wird die Zeilensumme (z.B. in der Auftragszeile) als Steuerbetrag behandelt und nicht als Basis zur Berechnung der Steuer. Dieser Steuersatz wird z.B. in Belegen verwendet, bei denen ein Steuerbetrag an eine Behörde entrichtet werden soll.', IsTranslated='Y', Name='Steuer Ausschließlich', PrintName='Steuer Ausschließlich',Updated=TO_TIMESTAMP('2021-05-07 13:49:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=57413 AND AD_Language='de_CH'
;

-- 2021-05-07T10:49:31.373Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(57413,'de_CH')
;

-- 2021-05-07T10:49:43.046Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Wenn gewählt, wird die Zeilensumme (z.B. in der Auftragszeile) als Steuerbetrag behandelt und nicht als Basis zur Berechnung der Steuer. Dieser Steuersatz wird z.B. in Belegen verwendet, bei denen ein Steuerbetrag an eine Behörde entrichtet werden soll.', Help='Wenn gewählt, wird die Zeilensumme (z.B. in der Auftragszeile) als Steuerbetrag behandelt und nicht als Basis zur Berechnung der Steuer. Dieser Steuersatz wird z.B. in Belegen verwendet, bei denen ein Steuerbetrag an eine Behörde entrichtet werden soll.',Updated=TO_TIMESTAMP('2021-05-07 13:49:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=57413 AND AD_Language='nl_NL'
;

-- 2021-05-07T10:49:43.082Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(57413,'nl_NL')
;

-- 2021-05-07T10:50:18.926Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Wenn gewählt, wird die Zeilensumme (z.B. in der Auftragszeile) als Steuerbetrag behandelt und nicht als Basis zur Berechnung der Steuer. Dieser Steuersatz wird z.B. in Belegen verwendet, bei denen ein Steuerbetrag an eine Behörde entrichtet werden soll.', Help='Wenn gewählt, wird die Zeilensumme (z.B. in der Auftragszeile) als Steuerbetrag behandelt und nicht als Basis zur Berechnung der Steuer. Dieser Steuersatz wird z.B. in Belegen verwendet, bei denen ein Steuerbetrag an eine Behörde entrichtet werden soll.', IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-07 13:50:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=57413 AND AD_Language='de_DE'
;

-- 2021-05-07T10:50:18.962Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(57413,'de_DE')
;

-- 2021-05-07T10:50:19.048Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(57413,'de_DE')
;

-- 2021-05-07T10:50:19.086Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsWholeTax', Name='Steuer Ausschließlich', Description='Wenn gewählt, wird die Zeilensumme (z.B. in der Auftragszeile) als Steuerbetrag behandelt und nicht als Basis zur Berechnung der Steuer. Dieser Steuersatz wird z.B. in Belegen verwendet, bei denen ein Steuerbetrag an eine Behörde entrichtet werden soll.', Help='Wenn gewählt, wird die Zeilensumme (z.B. in der Auftragszeile) als Steuerbetrag behandelt und nicht als Basis zur Berechnung der Steuer. Dieser Steuersatz wird z.B. in Belegen verwendet, bei denen ein Steuerbetrag an eine Behörde entrichtet werden soll.' WHERE AD_Element_ID=57413
;

-- 2021-05-07T10:50:19.127Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsWholeTax', Name='Steuer Ausschließlich', Description='Wenn gewählt, wird die Zeilensumme (z.B. in der Auftragszeile) als Steuerbetrag behandelt und nicht als Basis zur Berechnung der Steuer. Dieser Steuersatz wird z.B. in Belegen verwendet, bei denen ein Steuerbetrag an eine Behörde entrichtet werden soll.', Help='Wenn gewählt, wird die Zeilensumme (z.B. in der Auftragszeile) als Steuerbetrag behandelt und nicht als Basis zur Berechnung der Steuer. Dieser Steuersatz wird z.B. in Belegen verwendet, bei denen ein Steuerbetrag an eine Behörde entrichtet werden soll.', AD_Element_ID=57413 WHERE UPPER(ColumnName)='ISWHOLETAX' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-07T10:50:19.168Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsWholeTax', Name='Steuer Ausschließlich', Description='Wenn gewählt, wird die Zeilensumme (z.B. in der Auftragszeile) als Steuerbetrag behandelt und nicht als Basis zur Berechnung der Steuer. Dieser Steuersatz wird z.B. in Belegen verwendet, bei denen ein Steuerbetrag an eine Behörde entrichtet werden soll.', Help='Wenn gewählt, wird die Zeilensumme (z.B. in der Auftragszeile) als Steuerbetrag behandelt und nicht als Basis zur Berechnung der Steuer. Dieser Steuersatz wird z.B. in Belegen verwendet, bei denen ein Steuerbetrag an eine Behörde entrichtet werden soll.' WHERE AD_Element_ID=57413 AND IsCentrallyMaintained='Y'
;

-- 2021-05-07T10:50:19.203Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Steuer Ausschließlich', Description='Wenn gewählt, wird die Zeilensumme (z.B. in der Auftragszeile) als Steuerbetrag behandelt und nicht als Basis zur Berechnung der Steuer. Dieser Steuersatz wird z.B. in Belegen verwendet, bei denen ein Steuerbetrag an eine Behörde entrichtet werden soll.', Help='Wenn gewählt, wird die Zeilensumme (z.B. in der Auftragszeile) als Steuerbetrag behandelt und nicht als Basis zur Berechnung der Steuer. Dieser Steuersatz wird z.B. in Belegen verwendet, bei denen ein Steuerbetrag an eine Behörde entrichtet werden soll.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=57413) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 57413)
;

-- 2021-05-07T10:50:19.253Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Steuer Ausschließlich', Description='Wenn gewählt, wird die Zeilensumme (z.B. in der Auftragszeile) als Steuerbetrag behandelt und nicht als Basis zur Berechnung der Steuer. Dieser Steuersatz wird z.B. in Belegen verwendet, bei denen ein Steuerbetrag an eine Behörde entrichtet werden soll.', Help='Wenn gewählt, wird die Zeilensumme (z.B. in der Auftragszeile) als Steuerbetrag behandelt und nicht als Basis zur Berechnung der Steuer. Dieser Steuersatz wird z.B. in Belegen verwendet, bei denen ein Steuerbetrag an eine Behörde entrichtet werden soll.', CommitWarning = NULL WHERE AD_Element_ID = 57413
;

-- 2021-05-07T10:50:19.289Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Steuer Ausschließlich', Description='Wenn gewählt, wird die Zeilensumme (z.B. in der Auftragszeile) als Steuerbetrag behandelt und nicht als Basis zur Berechnung der Steuer. Dieser Steuersatz wird z.B. in Belegen verwendet, bei denen ein Steuerbetrag an eine Behörde entrichtet werden soll.', Help='Wenn gewählt, wird die Zeilensumme (z.B. in der Auftragszeile) als Steuerbetrag behandelt und nicht als Basis zur Berechnung der Steuer. Dieser Steuersatz wird z.B. in Belegen verwendet, bei denen ein Steuerbetrag an eine Behörde entrichtet werden soll.' WHERE AD_Element_ID = 57413
;

-- 2021-05-07T10:50:19.324Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Steuer Ausschließlich', Description = 'Wenn gewählt, wird die Zeilensumme (z.B. in der Auftragszeile) als Steuerbetrag behandelt und nicht als Basis zur Berechnung der Steuer. Dieser Steuersatz wird z.B. in Belegen verwendet, bei denen ein Steuerbetrag an eine Behörde entrichtet werden soll.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 57413
;

-- 2021-05-07T11:33:07.278Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544932
;

-- 2021-05-07T11:33:08.872Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544933
;

-- 2021-05-07T11:33:10.148Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544939
;

-- 2021-05-07T11:33:58.563Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2091,0,174,540523,584672,'F',TO_TIMESTAMP('2021-05-07 14:33:58','YYYY-MM-DD HH24:MI:SS'),100,'Steuer wird dokumentbasiert berechnet (abweichend wäre zeilenweise)','If the tax is calculated on document level, all lines with that tax rate are added before calculating the total tax for the document.
Otherwise the tax is calculated per line and then added.
Due to rounding, the tax amount can differ.','Y','Y','N','Y','N','N','N',0,'Dokumentbasiert',10,0,0,TO_TIMESTAMP('2021-05-07 14:33:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-07T11:35:04.982Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=584465
;

-- 2021-05-07T11:35:54.682Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,8195,0,174,540520,584673,'F',TO_TIMESTAMP('2021-05-07 14:35:54','YYYY-MM-DD HH24:MI:SS'),100,'Steuer für Einkauf und/ oder Verkauf Transaktionen.','Sales Tax: charged when selling - examples: Sales Tax, Output VAT (payable)
Purchase Tax: tax charged when purchasing - examples: Use Tax, Input VAT (receivable)','Y','N','N','Y','N','N','N',0,'VK/ EK Typ',5,0,0,TO_TIMESTAMP('2021-05-07 14:35:54','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2021-05-07T11:36:29.134Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544919
;

-- 2021-05-07T11:36:30.416Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=566596
;

-- 2021-05-07T11:38:14.677Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2871,0,174,545777,584674,'F',TO_TIMESTAMP('2021-05-07 14:38:14','YYYY-MM-DD HH24:MI:SS'),100,'Rate or Tax or Exchange','The Rate indicates the percentage to be multiplied by the source to arrive at the tax or exchange amount.','Y','N','N','Y','N','N','N',0,'Satz',15,0,0,TO_TIMESTAMP('2021-05-07 14:38:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-07T11:39:28.261Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544926
;

-- 2021-05-07T11:39:29.325Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544927
;

-- 2021-05-07T11:39:30.478Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544925
;

-- 2021-05-07T11:39:31.618Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=584457
;

-- 2021-05-07T11:39:32.881Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544929
;

-- 2021-05-07T11:39:34.037Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544928
;

-- 2021-05-07T11:41:13.471Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,6121,0,174,540519,584675,'F',TO_TIMESTAMP('2021-05-07 14:41:13','YYYY-MM-DD HH24:MI:SS'),100,'Steuersatz steuerbefreit','If a business partner is exempt from tax on sales, the exempt tax rate is used. For this, you need to set up a tax rate with a 0% rate and indicate that this is your tax exempt rate.  This is required for tax reporting, so that you can track tax exempt transactions.','Y','N','N','Y','N','N','N',0,'Steuerbefreit',40,0,0,TO_TIMESTAMP('2021-05-07 14:41:13','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2021-05-07T11:41:45.522Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,2872,0,174,540519,584676,'F',TO_TIMESTAMP('2021-05-07 14:41:45','YYYY-MM-DD HH24:MI:SS'),100,'Matcht nur, wenn der betreffende Geschäftspartner eine Umsatzsteuer-ID hat.','Matcht nur, wenn der betreffende Geschäftspartner eine Umsatzsteuer-ID hat.','Y','N','N','Y','N','N','N',0,'Partner hat eine Ust.-ID',50,0,0,TO_TIMESTAMP('2021-05-07 14:41:45','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2021-05-07T11:42:32.167Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,56447,0,174,540519,584677,'F',TO_TIMESTAMP('2021-05-07 14:42:31','YYYY-MM-DD HH24:MI:SS'),100,'Matcht nur, wenn das Land, in dem die Leistung erbracht wird, in der EU liegt.','Matcht nur, wenn das Land, in dem die Leistung erbracht wird, in der EU liegt.','Y','N','N','Y','N','N','N',0,'Nach EU',60,0,0,TO_TIMESTAMP('2021-05-07 14:42:31','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2021-05-07T11:44:33.685Z
-- URL zum Konzept
--INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,598475,0,174,545777,584678,'F',TO_TIMESTAMP('2021-05-07 14:44:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'External ID',17,0,0,TO_TIMESTAMP('2021-05-07 14:44:33','YYYY-MM-DD HH24:MI:SS'),100)
--;

-- 2021-05-07T11:45:28.404Z
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2021-05-07 14:45:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540520
;

-- 2021-05-07T11:46:25.304Z
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET Name='default',Updated=TO_TIMESTAMP('2021-05-07 14:46:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540520
;

-- 2021-05-07T11:48:26.903Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=584673
;

-- 2021-05-07T11:48:27.990Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544920
;

-- 2021-05-07T11:48:29.174Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=544921
;

-- 2021-05-07T11:48:45.406Z
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET Name='country', UIStyle='',Updated=TO_TIMESTAMP('2021-05-07 14:48:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540520
;

-- 2021-05-07T11:49:39.085Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,8195,0,174,540519,584679,'F',TO_TIMESTAMP('2021-05-07 14:49:38','YYYY-MM-DD HH24:MI:SS'),100,'Steuer für Einkauf und/ oder Verkauf Transaktionen.','Sales Tax: charged when selling - examples: Sales Tax, Output VAT (payable)
Purchase Tax: tax charged when purchasing - examples: Use Tax, Input VAT (receivable)','Y','N','N','Y','N','N','N',0,'VK/ EK Typ',70,0,0,TO_TIMESTAMP('2021-05-07 14:49:38','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2021-05-07T11:50:49.410Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,974,0,174,540519,584680,'F',TO_TIMESTAMP('2021-05-07 14:50:48','YYYY-MM-DD HH24:MI:SS'),100,'Land des Leistungserbringers','Land des Leistungserbringers','Y','N','N','Y','N','N','N',0,'Ursprungsland',80,0,0,TO_TIMESTAMP('2021-05-07 14:50:48','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- 2021-05-07T11:51:17.025Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,976,0,174,540519,584681,'F',TO_TIMESTAMP('2021-05-07 14:51:16','YYYY-MM-DD HH24:MI:SS'),100,'Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird.','Bestimmungs-/Leistungsland, d.h. das Land, in dem die Leistung erbracht, bzw. in das die Ware geliefert wirdWare geliefert wird.','Y','N','N','Y','N','N','N',0,'Bestimmungsland',90,0,0,TO_TIMESTAMP('2021-05-07 14:51:16','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- 2021-05-07T11:54:44.214Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2021-05-07 14:54:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584674
;

-- 2021-05-07T11:54:44.505Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2021-05-07 14:54:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584680
;

-- 2021-05-07T11:54:44.685Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2021-05-07 14:54:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584681
;

-- 2021-05-07T11:54:44.867Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2021-05-07 14:54:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544924
;

-- 2021-05-07T11:54:45.089Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2021-05-07 14:54:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544930
;

-- 2021-05-07T11:55:45.041Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2021-05-07 14:55:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584679
;

-- 2021-05-07T11:55:45.223Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2021-05-07 14:55:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584675
;

-- 2021-05-07T11:55:45.407Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2021-05-07 14:55:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584460
;

-- 2021-05-07T11:55:45.586Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2021-05-07 14:55:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584677
;

-- 2021-05-07T11:55:45.770Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2021-05-07 14:55:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544924
;

-- 2021-05-07T11:55:45.945Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2021-05-07 14:55:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544930
;

-- 2021-05-07T12:00:56.280Z
-- URL zum Konzept
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2021-05-07 15:00:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544918
;

-- 2021-05-07T12:02:29.811Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2021-05-07 15:02:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584676
;

-- 2021-05-07T12:02:29.993Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2021-05-07 15:02:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584675
;

-- 2021-05-07T12:02:30.175Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2021-05-07 15:02:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544924
;

-- 2021-05-07T12:02:30.350Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2021-05-07 15:02:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544930
;




