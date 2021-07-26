-- create system element IsBranchOffice
-- 2021-07-06T14:43:26.797Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579464,0,'IsBranchOffice',TO_TIMESTAMP('2021-07-06 17:43:25','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Branch office','Branch office',TO_TIMESTAMP('2021-07-06 17:43:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-06T14:43:27.302Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579464 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-07-06T14:43:43.431Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zweitstelle', PrintName='Zweitstelle',Updated=TO_TIMESTAMP('2021-07-06 17:43:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579464 AND AD_Language='de_CH'
;

-- 2021-07-06T14:43:43.514Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579464,'de_CH')
;

-- 2021-07-06T14:43:49.687Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zweitstelle', PrintName='Zweitstelle',Updated=TO_TIMESTAMP('2021-07-06 17:43:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579464 AND AD_Language='de_DE'
;

-- 2021-07-06T14:43:49.727Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579464,'de_DE')
;

-- 2021-07-06T14:43:49.810Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579464,'de_DE')
;

-- 2021-07-06T14:43:49.851Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsBranchOffice', Name='Zweitstelle', Description=NULL, Help=NULL WHERE AD_Element_ID=579464
;

-- 2021-07-06T14:43:49.890Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsBranchOffice', Name='Zweitstelle', Description=NULL, Help=NULL, AD_Element_ID=579464 WHERE UPPER(ColumnName)='ISBRANCHOFFICE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-07-06T14:43:49.932Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsBranchOffice', Name='Zweitstelle', Description=NULL, Help=NULL WHERE AD_Element_ID=579464 AND IsCentrallyMaintained='Y'
;

-- 2021-07-06T14:43:49.972Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Zweitstelle', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579464) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579464)
;

-- 2021-07-06T14:43:50.033Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Zweitstelle', Name='Zweitstelle' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579464)
;

-- 2021-07-06T14:43:50.073Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Zweitstelle', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579464
;

-- 2021-07-06T14:43:50.113Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Zweitstelle', Description=NULL, Help=NULL WHERE AD_Element_ID = 579464
;

-- 2021-07-06T14:43:50.155Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Zweitstelle', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579464
;

-- 2021-07-06T14:43:54.718Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-07-06 17:43:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579464 AND AD_Language='en_US'
;

-- 2021-07-06T14:43:54.757Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579464,'en_US')
;

-- 2021-07-06T14:44:00.444Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Zweitstelle', PrintName='Zweitstelle',Updated=TO_TIMESTAMP('2021-07-06 17:44:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579464 AND AD_Language='nl_NL'
;

-- 2021-07-06T14:44:00.483Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579464,'nl_NL')
;

-- create columns IsBranchOffice
-- 2021-07-06T14:45:29.376Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,574970,579464,0,20,678,'IsBranchOffice',TO_TIMESTAMP('2021-07-06 17:45:28','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Zweitstelle',0,0,TO_TIMESTAMP('2021-07-06 17:45:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-07-06T14:45:29.496Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=574970 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-07-06T14:45:29.575Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579464)
;



--Create the field in window
-- 2021-07-06T14:49:42.745Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,574970,650109,0,612,0,TO_TIMESTAMP('2021-07-06 17:49:42','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Zweitstelle',170,180,0,1,1,TO_TIMESTAMP('2021-07-06 17:49:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-07-06T14:49:42.944Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=650109 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-07-06T14:49:42.993Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579464)
;

-- 2021-07-06T14:49:43.044Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=650109
;

-- 2021-07-06T14:49:43.084Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(650109)
;

--Display in window
-- 2021-07-06T14:51:01.996Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,650109,0,612,540824,587010,'F',TO_TIMESTAMP('2021-07-06 17:51:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Zweitstelle',80,0,0,TO_TIMESTAMP('2021-07-06 17:51:01','YYYY-MM-DD HH24:MI:SS'),100)
;

--fix window name

-- 2021-07-06T14:57:54.198Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Partner Verknüpfungen', PrintName='Partner Verknüpfungen',Updated=TO_TIMESTAMP('2021-07-06 17:57:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2372 AND AD_Language='de_DE'
;

-- 2021-07-06T14:57:54.249Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2372,'de_DE')
;

-- 2021-07-06T14:57:54.378Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(2372,'de_DE')
;

-- 2021-07-06T14:57:54.420Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='C_BP_Relation_ID', Name='Partner Verknüpfungen', Description='Business Partner Relation', Help='"Beziehungen Geschäftspartner" erlaubt die Verwaltung von Beziehungen der Geschäftspartner untereinander: wer empfängt die Rechnungen für Lieferungen oder wer zahlt die Rechnungen.' WHERE AD_Element_ID=2372
;

-- 2021-07-06T14:57:54.460Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_BP_Relation_ID', Name='Partner Verknüpfungen', Description='Business Partner Relation', Help='"Beziehungen Geschäftspartner" erlaubt die Verwaltung von Beziehungen der Geschäftspartner untereinander: wer empfängt die Rechnungen für Lieferungen oder wer zahlt die Rechnungen.', AD_Element_ID=2372 WHERE UPPER(ColumnName)='C_BP_RELATION_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-07-06T14:57:54.510Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='C_BP_Relation_ID', Name='Partner Verknüpfungen', Description='Business Partner Relation', Help='"Beziehungen Geschäftspartner" erlaubt die Verwaltung von Beziehungen der Geschäftspartner untereinander: wer empfängt die Rechnungen für Lieferungen oder wer zahlt die Rechnungen.' WHERE AD_Element_ID=2372 AND IsCentrallyMaintained='Y'
;

-- 2021-07-06T14:57:54.548Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Partner Verknüpfungen', Description='Business Partner Relation', Help='"Beziehungen Geschäftspartner" erlaubt die Verwaltung von Beziehungen der Geschäftspartner untereinander: wer empfängt die Rechnungen für Lieferungen oder wer zahlt die Rechnungen.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=2372) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 2372)
;

-- 2021-07-06T14:57:54.624Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Partner Verknüpfungen', Name='Partner Verknüpfungen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=2372)
;

-- 2021-07-06T14:57:54.671Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Partner Verknüpfungen', Description='Business Partner Relation', Help='"Beziehungen Geschäftspartner" erlaubt die Verwaltung von Beziehungen der Geschäftspartner untereinander: wer empfängt die Rechnungen für Lieferungen oder wer zahlt die Rechnungen.', CommitWarning = NULL WHERE AD_Element_ID = 2372
;

-- 2021-07-06T14:57:54.711Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Partner Verknüpfungen', Description='Business Partner Relation', Help='"Beziehungen Geschäftspartner" erlaubt die Verwaltung von Beziehungen der Geschäftspartner untereinander: wer empfängt die Rechnungen für Lieferungen oder wer zahlt die Rechnungen.' WHERE AD_Element_ID = 2372
;

-- 2021-07-06T14:57:54.751Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Partner Verknüpfungen', Description = 'Business Partner Relation', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 2372
;

-- 2021-07-06T14:58:33.597Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Partner Verknüpfungen', PrintName='Partner Verknüpfungen',Updated=TO_TIMESTAMP('2021-07-06 17:58:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2372 AND AD_Language='nl_NL'
;

-- 2021-07-06T14:58:33.638Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2372,'nl_NL')
;

-- 2021-07-06T14:58:47.838Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Partner Verknüpfungen', PrintName='Partner Verknüpfungen',Updated=TO_TIMESTAMP('2021-07-06 17:58:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2372 AND AD_Language='de_CH'
;

-- 2021-07-06T14:58:47.886Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2372,'de_CH')
;

-- 2021-07-06T14:59:10.914Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Partner Relation', PrintName='Partner Relation',Updated=TO_TIMESTAMP('2021-07-06 17:59:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2372 AND AD_Language='en_GB'
;

-- 2021-07-06T14:59:11.207Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2372,'en_GB')
;

-- 2021-07-06T14:59:25.875Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Partner Verknüpfungen', PrintName='Partner Verknüpfungen',Updated=TO_TIMESTAMP('2021-07-06 17:59:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2372 AND AD_Language='it_CH'
;

-- 2021-07-06T14:59:25.921Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2372,'it_CH')
;

-- 2021-07-06T14:59:31.810Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Partner Verknüpfungen', PrintName='Partner Verknüpfungen',Updated=TO_TIMESTAMP('2021-07-06 17:59:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=2372 AND AD_Language='fr_CH'
;

-- 2021-07-06T14:59:31.848Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2372,'fr_CH')
;

-- 2021-07-06T15:13:08.747Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='IsBranchOffice', PrintName='IsBranchOffice',Updated=TO_TIMESTAMP('2021-07-06 18:13:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579464 AND AD_Language='en_US'
;

-- 2021-07-06T15:13:08.786Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579464,'en_US')
;