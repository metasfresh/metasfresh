-- 2021-08-05T13:00:48.339Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579540,0,'IsPrintLine',TO_TIMESTAMP('2021-08-05 15:00:48','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Print Line','Print Line',TO_TIMESTAMP('2021-08-05 15:00:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-08-05T13:00:48.340Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579540 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-08-05T13:02:02.758Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Druckzeile', PrintName='Druckzeile',Updated=TO_TIMESTAMP('2021-08-05 15:02:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579540 AND AD_Language='de_CH'
;

-- 2021-08-05T13:02:02.778Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579540,'de_CH') 
;

-- 2021-08-05T13:02:15.948Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Druckzeile', PrintName='Druckzeile',Updated=TO_TIMESTAMP('2021-08-05 15:02:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579540 AND AD_Language='de_DE'
;

-- 2021-08-05T13:02:15.952Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579540,'de_DE') 
;

-- 2021-08-05T13:02:15.978Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579540,'de_DE') 
;

-- 2021-08-05T13:02:15.980Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsPrintLine', Name='Druckzeile', Description=NULL, Help=NULL WHERE AD_Element_ID=579540
;

-- 2021-08-05T13:02:15.982Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsPrintLine', Name='Druckzeile', Description=NULL, Help=NULL, AD_Element_ID=579540 WHERE UPPER(ColumnName)='ISPRINTLINE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-08-05T13:02:15.983Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsPrintLine', Name='Druckzeile', Description=NULL, Help=NULL WHERE AD_Element_ID=579540 AND IsCentrallyMaintained='Y'
;

-- 2021-08-05T13:02:15.984Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Druckzeile', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579540) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579540)
;

-- 2021-08-05T13:02:16.002Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Druckzeile', Name='Druckzeile' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579540)
;

-- 2021-08-05T13:02:16.003Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Druckzeile', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579540
;

-- 2021-08-05T13:02:16.004Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Druckzeile', Description=NULL, Help=NULL WHERE AD_Element_ID = 579540
;

-- 2021-08-05T13:02:16.005Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Druckzeile', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579540
;

-- 2021-08-05T13:02:31.991Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,575258,579540,0,20,260,'IsPrintLine',TO_TIMESTAMP('2021-08-05 15:02:31','YYYY-MM-DD HH24:MI:SS'),100,'N','Y','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Druckzeile',0,0,TO_TIMESTAMP('2021-08-05 15:02:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-08-05T13:02:31.992Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=575258 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-08-05T13:02:31.993Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(579540) 
;

-- 2021-08-05T13:02:37.537Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN IsPrintLine CHAR(1) DEFAULT ''Y'' CHECK (IsPrintLine IN (''Y'',''N'')) NOT NULL')
;








-- 2021-08-06T10:01:08.410Z
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='IsHideWhenPrinting',Updated=TO_TIMESTAMP('2021-08-06 12:01:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579540
;

-- 2021-08-06T10:01:08.414Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsHideWhenPrinting', Name='Druckzeile', Description=NULL, Help=NULL WHERE AD_Element_ID=579540
;

-- 2021-08-06T10:01:08.415Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsHideWhenPrinting', Name='Druckzeile', Description=NULL, Help=NULL, AD_Element_ID=579540 WHERE UPPER(ColumnName)='ISHIDEWHENPRINTING' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-08-06T10:01:08.416Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsHideWhenPrinting', Name='Druckzeile', Description=NULL, Help=NULL WHERE AD_Element_ID=579540 AND IsCentrallyMaintained='Y'
;

-- 2021-08-06T10:01:52.083Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Beim Drucken ausblenden', PrintName='Beim Drucken ausblenden',Updated=TO_TIMESTAMP('2021-08-06 12:01:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579540 AND AD_Language='de_CH'
;

-- 2021-08-06T10:01:52.098Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579540,'de_CH') 
;

-- 2021-08-06T10:02:03.133Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Beim Drucken ausblenden', PrintName='Beim Drucken ausblenden',Updated=TO_TIMESTAMP('2021-08-06 12:02:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579540 AND AD_Language='de_DE'
;

-- 2021-08-06T10:02:03.134Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579540,'de_DE') 
;

-- 2021-08-06T10:02:03.142Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579540,'de_DE') 
;

-- 2021-08-06T10:02:03.143Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='IsHideWhenPrinting', Name='Beim Drucken ausblenden', Description=NULL, Help=NULL WHERE AD_Element_ID=579540
;

-- 2021-08-06T10:02:03.144Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsHideWhenPrinting', Name='Beim Drucken ausblenden', Description=NULL, Help=NULL, AD_Element_ID=579540 WHERE UPPER(ColumnName)='ISHIDEWHENPRINTING' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-08-06T10:02:03.144Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='IsHideWhenPrinting', Name='Beim Drucken ausblenden', Description=NULL, Help=NULL WHERE AD_Element_ID=579540 AND IsCentrallyMaintained='Y'
;

-- 2021-08-06T10:02:03.145Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Beim Drucken ausblenden', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=579540) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 579540)
;

-- 2021-08-06T10:02:03.158Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Beim Drucken ausblenden', Name='Beim Drucken ausblenden' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=579540)
;

-- 2021-08-06T10:02:03.158Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Beim Drucken ausblenden', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 579540
;

-- 2021-08-06T10:02:03.160Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Beim Drucken ausblenden', Description=NULL, Help=NULL WHERE AD_Element_ID = 579540
;

-- 2021-08-06T10:02:03.160Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Beim Drucken ausblenden', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 579540
;

-- 2021-08-06T10:02:31.568Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Hide when printing', PrintName='Hide when printing',Updated=TO_TIMESTAMP('2021-08-06 12:02:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579540 AND AD_Language='en_US'
;

-- 2021-08-06T10:02:31.572Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579540,'en_US') 
;

-- 2021-08-06T10:03:19.263Z
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='N',Updated=TO_TIMESTAMP('2021-08-06 12:03:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=575258
;

-- 2021-08-06T10:03:25.575Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN IsHideWhenPrinting CHAR(1) DEFAULT ''N'' CHECK (IsHideWhenPrinting IN (''Y'',''N'')) NOT NULL')
;


/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine DROP COLUMN IsPrintLine')
;


