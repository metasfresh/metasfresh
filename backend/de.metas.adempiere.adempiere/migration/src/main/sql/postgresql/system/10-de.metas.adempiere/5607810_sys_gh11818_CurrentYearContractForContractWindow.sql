-- 2021-10-04T13:59:00.943Z
-- URL zum Konzept
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Template_Tab_ID,Updated,UpdatedBy) VALUES (0,541447,0,544605,540320,540112,'Y',TO_TIMESTAMP('2021-10-04 15:59:00','YYYY-MM-DD HH24:MI:SS'),100,'U','N','N','C_Flatrate_Term','Y','N','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Pauschale - Vertragsperiode','N',60,0,540340,TO_TIMESTAMP('2021-10-04 15:59:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-04T13:59:00.945Z
-- URL zum Konzept
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=544605 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2021-10-04T13:59:00.967Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(541447) 
;

-- 2021-10-04T13:59:00.972Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(544605)
;

-- 2021-10-04T13:59:47.330Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579971,0,TO_TIMESTAMP('2021-10-04 15:59:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Vetragsperiode akt. Jahr','Vetragsperiode akt. Jahr',TO_TIMESTAMP('2021-10-04 15:59:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-04T13:59:47.332Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579971 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-10-04T13:59:52.664Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-10-04 15:59:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579971 AND AD_Language='de_CH'
;

-- 2021-10-04T13:59:52.667Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579971,'de_CH') 
;

-- 2021-10-04T13:59:56.110Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-10-04 15:59:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579971 AND AD_Language='de_DE'
;

-- 2021-10-04T13:59:56.111Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579971,'de_DE') 
;

-- 2021-10-04T13:59:56.119Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579971,'de_DE') 
;

-- 2021-10-04T14:00:00.832Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-10-04 16:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579971 AND AD_Language='nl_NL'
;

-- 2021-10-04T14:00:00.836Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579971,'nl_NL') 
;

-- 2021-10-04T14:00:22.483Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Contract period curr. year', PrintName='Contract period curr. year',Updated=TO_TIMESTAMP('2021-10-04 16:00:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579971 AND AD_Language='en_US'
;

-- 2021-10-04T14:00:22.484Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579971,'en_US') 
;

-- 2021-10-04T14:00:51.861Z
-- URL zum Konzept
UPDATE AD_Tab SET AD_Element_ID=579971, CommitWarning=NULL, Description=NULL, Help=NULL, Name='Vetragsperiode akt. Jahr',Updated=TO_TIMESTAMP('2021-10-04 16:00:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544605
;

-- 2021-10-04T14:00:51.862Z
-- URL zum Konzept
/* DDL */  select update_tab_translation_from_ad_element(579971) 
;

-- 2021-10-04T14:00:51.864Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Tab(544605)
;

-- 2021-10-04T14:02:13.706Z
-- URL zum Konzept
UPDATE AD_Tab SET WhereClause='date_trunc(''YEAR'', @#Date@) = date_trunc(''YEAR'',C_Flatrate_Term.StartDate)',Updated=TO_TIMESTAMP('2021-10-04 16:02:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544605
;

-- 2021-10-04T14:02:21.229Z
-- URL zum Konzept
UPDATE AD_Tab SET IsActive='N',Updated=TO_TIMESTAMP('2021-10-04 16:02:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540340
;

-- 2021-10-04T14:03:07.332Z
-- URL zum Konzept
UPDATE AD_Tab SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-04 16:03:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540340
;

-- 2021-10-04T14:03:23.398Z
-- URL zum Konzept
UPDATE AD_Tab SET WhereClause='date_trunc(''YEAR'', now()) = date_trunc(''YEAR'',C_Flatrate_Term.StartDate)',Updated=TO_TIMESTAMP('2021-10-04 16:03:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544605
;

-- 2021-10-04T14:04:44.489Z
-- URL zum Konzept
UPDATE AD_Tab SET AD_Column_ID=545803, TabLevel=1,Updated=TO_TIMESTAMP('2021-10-04 16:04:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544605
;

-- 2021-10-04T14:06:25.716Z
-- URL zum Konzept
UPDATE AD_Tab SET IsInsertRecord='N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-10-04 16:06:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544605
;

-- 2021-10-04T14:06:34.315Z
-- URL zum Konzept
UPDATE AD_Tab SET IsReadOnly='N',Updated=TO_TIMESTAMP('2021-10-04 16:06:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544605
;

-- 2021-10-04T14:09:49.137Z
-- URL zum Konzept
UPDATE AD_Tab SET WhereClause='date_trunc(''YEAR'', @Date@) = date_trunc(''YEAR'',C_Flatrate_Term.StartDate)',Updated=TO_TIMESTAMP('2021-10-04 16:09:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544605
;

-- 2021-10-04T14:10:23.606Z
-- URL zum Konzept
UPDATE AD_Tab SET WhereClause='date_trunc(''YEAR'', @#Date@) = date_trunc(''YEAR'',C_Flatrate_Term.StartDate)',Updated=TO_TIMESTAMP('2021-10-04 16:10:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544605
;

-- 2021-10-04T14:11:42.126Z
-- URL zum Konzept
UPDATE AD_Tab SET WhereClause='date_trunc(''YEAR'', now()) = date_trunc(''YEAR'',C_Flatrate_Term.StartDate)',Updated=TO_TIMESTAMP('2021-10-04 16:11:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544605
;

-- 2021-10-04T14:12:15.397Z
-- URL zum Konzept
UPDATE AD_Tab SET IsActive='N',Updated=TO_TIMESTAMP('2021-10-04 16:12:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540340
;

-- 2021-10-04T14:13:01.185Z
-- URL zum Konzept
UPDATE AD_Tab SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-04 16:13:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540340
;

-- 2021-10-04T14:13:54.523Z
-- URL zum Konzept
UPDATE AD_Tab SET Template_Tab_ID=540340,Updated=TO_TIMESTAMP('2021-10-04 16:13:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544605
;


-- 2021-10-04T14:14:25.780Z
-- URL zum Konzept
UPDATE AD_Tab SET IsActive='N',Updated=TO_TIMESTAMP('2021-10-04 16:14:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540340
;

-- 2021-10-05T06:13:25.733Z
-- URL zum Konzept
UPDATE AD_Tab SET WhereClause='date_trunc(''YEAR'', TO_TIMESTAMP(''@#Date@'', ''YYYY-MM-DD'')) = date_trunc(''YEAR'',C_Flatrate_Term.StartDate)',Updated=TO_TIMESTAMP('2021-10-05 08:13:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544605
;

-- 2021-10-05T11:17:54.052Z
-- URL zum Konzept
UPDATE AD_Tab SET IsActive='Y',Updated=TO_TIMESTAMP('2021-10-05 13:17:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540340
;
