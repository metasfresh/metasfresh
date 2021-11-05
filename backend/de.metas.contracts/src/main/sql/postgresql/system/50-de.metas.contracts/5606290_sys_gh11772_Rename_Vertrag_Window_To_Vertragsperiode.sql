-- 2021-09-23T14:32:15.811Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579915,0,TO_TIMESTAMP('2021-09-23 17:32:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Vertragsperiode','Vertragsperiode',TO_TIMESTAMP('2021-09-23 17:32:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-23T14:32:15.890Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579915 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-09-23T14:33:14.421Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Contract', PrintName='Contract',Updated=TO_TIMESTAMP('2021-09-23 17:33:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579915 AND AD_Language='en_US'
;

-- 2021-09-23T14:33:14.490Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579915,'en_US') 
;

-- 2021-09-23T14:34:05.637Z
-- URL zum Konzept
UPDATE AD_Window SET AD_Element_ID=579915, Description=NULL, Help=NULL, Name='Vertragsperiode',Updated=TO_TIMESTAMP('2021-09-23 17:34:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540359
;

-- 2021-09-23T14:34:05.890Z
-- URL zum Konzept
UPDATE AD_Menu SET Description=NULL, IsActive='Y', Name='Vertragsperiode',Updated=TO_TIMESTAMP('2021-09-23 17:34:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540951
;

-- 2021-09-23T14:34:06.195Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(579915) 
;

-- 2021-09-23T14:34:06.275Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540359
;

-- 2021-09-23T14:34:06.367Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(540359)
;





-- 2021-09-28T15:40:46.235Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-28 18:40:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579915 AND AD_Language='de_CH'
;

-- 2021-09-28T15:40:46.340Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579915,'de_CH') 
;

-- 2021-09-28T15:40:52.862Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-09-28 18:40:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579915 AND AD_Language='de_DE'
;

-- 2021-09-28T15:40:52.941Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579915,'de_DE') 
;

-- 2021-09-28T15:40:53.083Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(579915,'de_DE') 
;

-- 2021-09-28T15:41:10.679Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Vertragsperiode1', PrintName='Vertragsperiode1',Updated=TO_TIMESTAMP('2021-09-28 18:41:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579915 AND AD_Language='de_CH'
;

-- 2021-09-28T15:41:10.792Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579915,'de_CH') 
;

-- 2021-09-28T15:41:16.056Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Vertragsperiode', PrintName='Vertragsperiode',Updated=TO_TIMESTAMP('2021-09-28 18:41:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579915 AND AD_Language='de_CH'
;

-- 2021-09-28T15:41:16.135Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579915,'de_CH') 
;

-- 2021-09-28T15:43:13.705Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Vertragsperiode', PrintName='Vertragsperiode',Updated=TO_TIMESTAMP('2021-09-28 18:43:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574563 AND AD_Language='de_CH'
;

-- 2021-09-28T15:43:13.768Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574563,'de_CH') 
;

-- 2021-09-28T15:43:22.719Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Vertragsperiode', PrintName='Vertragsperiode',Updated=TO_TIMESTAMP('2021-09-28 18:43:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574563 AND AD_Language='de_DE'
;

-- 2021-09-28T15:43:22.798Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574563,'de_DE') 
;

-- 2021-09-28T15:43:22.953Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(574563,'de_DE') 
;

-- 2021-09-28T15:43:23.047Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Vertragsperiode', Description=NULL, Help=NULL WHERE AD_Element_ID=574563
;

-- 2021-09-28T15:43:23.125Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Vertragsperiode', Description=NULL, Help=NULL WHERE AD_Element_ID=574563 AND IsCentrallyMaintained='Y'
;

-- 2021-09-28T15:43:23.188Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Vertragsperiode', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=574563) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 574563)
;

-- 2021-09-28T15:43:23.298Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Vertragsperiode', Name='Vertragsperiode' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=574563)
;

-- 2021-09-28T15:43:23.376Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Vertragsperiode', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 574563
;

-- 2021-09-28T15:43:23.437Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Vertragsperiode', Description=NULL, Help=NULL WHERE AD_Element_ID = 574563
;

-- 2021-09-28T15:43:23.529Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Vertragsperiode', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 574563
;

-- 2021-09-28T16:21:22.433Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,550488,0,540340,540796,591810,'F',TO_TIMESTAMP('2021-09-28 19:21:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Vertrags-Status',430,0,0,TO_TIMESTAMP('2021-09-28 19:21:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-28T16:22:49.765Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2021-09-28 19:22:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=591810
;

-- 2021-09-28T16:23:24.062Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2021-09-28 19:23:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=591810
;

-- 2021-09-28T16:23:24.390Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2021-09-28 19:23:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546379
;

-- 2021-09-28T16:23:24.674Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2021-09-28 19:23:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546375
;

-- 2021-09-28T16:23:25.002Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2021-09-28 19:23:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546393
;

-- 2021-09-28T16:23:25.300Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2021-09-28 19:23:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=546403
;

-- 2021-09-28T17:06:22.082Z
-- URL zum Konzept
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2021-09-28 20:06:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=544582
;







-- 2021-09-29T15:20:58.681Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Contract Period', PrintName='Contract Period',Updated=TO_TIMESTAMP('2021-09-29 18:20:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=579915 AND AD_Language='en_US'
;

-- 2021-09-29T15:20:58.761Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(579915,'en_US') 
;

-- 2021-09-29T15:22:27.973Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Contract Period', PrintName='Contract Period', WEBUI_NameBrowse='Contract Period',Updated=TO_TIMESTAMP('2021-09-29 18:22:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574563 AND AD_Language='en_US'
;

-- 2021-09-29T15:22:28.084Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574563,'en_US') 
;

-- 2021-09-29T15:22:38.425Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Vertragsperiode',Updated=TO_TIMESTAMP('2021-09-29 18:22:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574563 AND AD_Language='de_CH'
;

-- 2021-09-29T15:22:38.517Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574563,'de_CH') 
;

-- 2021-09-29T15:22:47.321Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET WEBUI_NameBrowse='Vertragsperiode',Updated=TO_TIMESTAMP('2021-09-29 18:22:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574563 AND AD_Language='de_DE'
;

-- 2021-09-29T15:22:47.414Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(574563,'de_DE') 
;

-- 2021-09-29T15:22:47.600Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(574563,'de_DE') 
;

-- 2021-09-29T15:22:47.693Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Vertragsperiode', Description = NULL, WEBUI_NameBrowse = 'Vertragsperiode', WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 574563
;

