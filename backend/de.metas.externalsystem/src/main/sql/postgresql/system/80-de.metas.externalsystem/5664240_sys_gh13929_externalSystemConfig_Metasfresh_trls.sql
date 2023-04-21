-- 2022-11-14T13:35:12.566Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581670,0,TO_TIMESTAMP('2022-11-14 15:35:12','YYYY-MM-DD HH24:MI:SS'),100,'','de.metas.externalsystem','Y','User Authentication Token','User Authentication Token',TO_TIMESTAMP('2022-11-14 15:35:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-14T13:35:12.576Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581670 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2022-11-14T13:35:18.254Z
UPDATE AD_Element_Trl SET Description='Authentifizierungs-Token, der im ''X-Auth-Token'' Header bei der Verbindung zur ''camel/metasfresh'' API zu verwenden ist.',Updated=TO_TIMESTAMP('2022-11-14 15:35:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581670 AND AD_Language='de_CH'
;

-- 2022-11-14T13:35:18.297Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581670,'de_CH') 
;

-- Element: null
-- 2022-11-14T13:35:20.259Z
UPDATE AD_Element_Trl SET Description='Authentifizierungs-Token, der im ''X-Auth-Token'' Header bei der Verbindung zur ''camel/metasfresh'' API zu verwenden ist.',Updated=TO_TIMESTAMP('2022-11-14 15:35:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581670 AND AD_Language='de_DE'
;

-- 2022-11-14T13:35:20.261Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581670,'de_DE') 
;

-- 2022-11-14T13:35:20.266Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581670,'de_DE') 
;

-- Element: null
-- 2022-11-14T13:35:25.266Z
UPDATE AD_Element_Trl SET Description='Authentication token to be used in ''X-Auth-Token'' Header when connecting to ''camel/metasfresh'' API.',Updated=TO_TIMESTAMP('2022-11-14 15:35:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581670 AND AD_Language='en_US'
;

-- 2022-11-14T13:35:25.270Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581670,'en_US') 
;

-- Element: null
-- 2022-11-14T13:35:29.683Z
UPDATE AD_Element_Trl SET Description='Authentifizierungs-Token, der im ''X-Auth-Token'' Header bei der Verbindung zur ''camel/metasfresh'' API zu verwenden ist.',Updated=TO_TIMESTAMP('2022-11-14 15:35:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581670 AND AD_Language='fr_CH'
;

-- 2022-11-14T13:35:29.686Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581670,'fr_CH') 
;

-- Element: null
-- 2022-11-14T13:35:31.086Z
UPDATE AD_Element_Trl SET Description='Authentifizierungs-Token, der im ''X-Auth-Token'' Header bei der Verbindung zur ''camel/metasfresh'' API zu verwenden ist.',Updated=TO_TIMESTAMP('2022-11-14 15:35:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581670 AND AD_Language='nl_NL'
;

-- 2022-11-14T13:35:31.089Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581670,'nl_NL') 
;

-- Element: null
-- 2022-11-14T13:35:44.004Z
UPDATE AD_Element_Trl SET Name='Token zur Benutzerauthentifizierung', PrintName='Token zur Benutzerauthentifizierung',Updated=TO_TIMESTAMP('2022-11-14 15:35:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581670 AND AD_Language='de_CH'
;

-- 2022-11-14T13:35:44.006Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581670,'de_CH') 
;

-- Element: null
-- 2022-11-14T13:35:46.155Z
UPDATE AD_Element_Trl SET Name='Token zur Benutzerauthentifizierung', PrintName='Token zur Benutzerauthentifizierung',Updated=TO_TIMESTAMP('2022-11-14 15:35:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581670 AND AD_Language='de_DE'
;

-- 2022-11-14T13:35:46.158Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581670,'de_DE') 
;

-- 2022-11-14T13:35:46.159Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581670,'de_DE') 
;

-- Element: null
-- 2022-11-14T13:35:49.051Z
UPDATE AD_Element_Trl SET Name='Token zur Benutzerauthentifizierung', PrintName='Token zur Benutzerauthentifizierung',Updated=TO_TIMESTAMP('2022-11-14 15:35:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581670 AND AD_Language='fr_CH'
;

-- 2022-11-14T13:35:49.054Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581670,'fr_CH') 
;

-- Element: null
-- 2022-11-14T13:35:51.083Z
UPDATE AD_Element_Trl SET Name='Token zur Benutzerauthentifizierung', PrintName='Token zur Benutzerauthentifizierung',Updated=TO_TIMESTAMP('2022-11-14 15:35:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581670 AND AD_Language='nl_NL'
;

-- 2022-11-14T13:35:51.085Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581670,'nl_NL') 
;

-- Field: Externes System(541024,de.metas.externalsystem) -> Metasfresh(546666,de.metas.externalsystem) -> Token zur Benutzerauthentifizierung
-- Column: ExternalSystem_Config_Metasfresh.CamelHttpResourceAuthKey
-- 2022-11-14T13:36:16.004Z
UPDATE AD_Field SET AD_Name_ID=581670, Description='Authentifizierungs-Token, der im ''X-Auth-Token'' Header bei der Verbindung zur ''camel/metasfresh'' API zu verwenden ist.', Help=NULL, Name='Token zur Benutzerauthentifizierung',Updated=TO_TIMESTAMP('2022-11-14 15:36:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707978
;

-- 2022-11-14T13:36:16.009Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581670) 
;

-- 2022-11-14T13:36:16.027Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707978
;

-- 2022-11-14T13:36:16.033Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707978)
;

-- Element: FeedbackResourceURL
-- 2022-11-14T13:37:01.444Z
UPDATE AD_Element_Trl SET Name='Feedback-URL', PrintName='Feedback-URL',Updated=TO_TIMESTAMP('2022-11-14 15:37:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581655 AND AD_Language='de_CH'
;

-- 2022-11-14T13:37:01.447Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581655,'de_CH') 
;

-- Element: FeedbackResourceURL
-- 2022-11-14T13:37:03.518Z
UPDATE AD_Element_Trl SET Name='Feedback-URL', PrintName='Feedback-URL',Updated=TO_TIMESTAMP('2022-11-14 15:37:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581655 AND AD_Language='de_DE'
;

-- 2022-11-14T13:37:03.520Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581655,'de_DE') 
;

-- 2022-11-14T13:37:03.523Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581655,'de_DE') 
;

-- Element: FeedbackResourceURL
-- 2022-11-14T13:37:07.426Z
UPDATE AD_Element_Trl SET Name='Feedback-URL', PrintName='Feedback-URL',Updated=TO_TIMESTAMP('2022-11-14 15:37:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581655 AND AD_Language='fr_CH'
;

-- 2022-11-14T13:37:07.427Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581655,'fr_CH') 
;

-- Element: FeedbackResourceURL
-- 2022-11-14T13:37:09.730Z
UPDATE AD_Element_Trl SET Name='Feedback-URL', PrintName='Feedback-URL',Updated=TO_TIMESTAMP('2022-11-14 15:37:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581655 AND AD_Language='nl_NL'
;

-- 2022-11-14T13:37:09.731Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581655,'nl_NL') 
;

-- Element: FeedbackResourceURL
-- 2022-11-14T13:37:20.267Z
UPDATE AD_Element_Trl SET Description='URL der externen Ressource, an die Metasfresh eine Rückmeldung über die hochgeladenen Daten senden kann.',Updated=TO_TIMESTAMP('2022-11-14 15:37:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581655 AND AD_Language='de_CH'
;

-- 2022-11-14T13:37:20.270Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581655,'de_CH') 
;

-- Element: FeedbackResourceURL
-- 2022-11-14T13:37:22.162Z
UPDATE AD_Element_Trl SET Description='URL der externen Ressource, an die Metasfresh eine Rückmeldung über die hochgeladenen Daten senden kann.',Updated=TO_TIMESTAMP('2022-11-14 15:37:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581655 AND AD_Language='de_DE'
;

-- 2022-11-14T13:37:22.163Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581655,'de_DE') 
;

-- 2022-11-14T13:37:22.164Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581655,'de_DE') 
;

-- Element: FeedbackResourceURL
-- 2022-11-14T13:37:24.316Z
UPDATE AD_Element_Trl SET Description='URL der externen Ressource, an die Metasfresh eine Rückmeldung über die hochgeladenen Daten senden kann.',Updated=TO_TIMESTAMP('2022-11-14 15:37:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581655 AND AD_Language='fr_CH'
;

-- 2022-11-14T13:37:24.318Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581655,'fr_CH') 
;

-- Element: FeedbackResourceURL
-- 2022-11-14T13:37:26.453Z
UPDATE AD_Element_Trl SET Description='URL der externen Ressource, an die Metasfresh eine Rückmeldung über die hochgeladenen Daten senden kann.',Updated=TO_TIMESTAMP('2022-11-14 15:37:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581655 AND AD_Language='nl_NL'
;

-- 2022-11-14T13:37:26.456Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581655,'nl_NL') 
;

-- Element: FeedbackResourceURL
-- 2022-11-14T13:37:32.399Z
UPDATE AD_Element_Trl SET Description='External resource URL where Metasfresh can send feedback about the processed data.',Updated=TO_TIMESTAMP('2022-11-14 15:37:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581655 AND AD_Language='en_US'
;

-- 2022-11-14T13:37:32.401Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581655,'en_US') 
;

-- Element: FeedbackResourceAuthToken
-- 2022-11-14T13:37:55.401Z
UPDATE AD_Element_Trl SET Name='Feedback-Authentifizierungs-Token', PrintName='Feedback-Authentifizierungs-Token',Updated=TO_TIMESTAMP('2022-11-14 15:37:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581654 AND AD_Language='de_CH'
;

-- 2022-11-14T13:37:55.403Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581654,'de_CH') 
;

-- Element: FeedbackResourceAuthToken
-- 2022-11-14T13:37:57.854Z
UPDATE AD_Element_Trl SET Name='Feedback-Authentifizierungs-Token', PrintName='Feedback-Authentifizierungs-Token',Updated=TO_TIMESTAMP('2022-11-14 15:37:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581654 AND AD_Language='de_DE'
;

-- 2022-11-14T13:37:57.856Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581654,'de_DE') 
;

-- 2022-11-14T13:37:57.858Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581654,'de_DE') 
;

-- Element: FeedbackResourceAuthToken
-- 2022-11-14T13:38:00.677Z
UPDATE AD_Element_Trl SET Name='Feedback-Authentifizierungs-Token', PrintName='Feedback-Authentifizierungs-Token',Updated=TO_TIMESTAMP('2022-11-14 15:38:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581654 AND AD_Language='fr_CH'
;

-- 2022-11-14T13:38:00.680Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581654,'fr_CH') 
;

-- Element: FeedbackResourceAuthToken
-- 2022-11-14T13:38:02.757Z
UPDATE AD_Element_Trl SET Name='Feedback-Authentifizierungs-Token', PrintName='Feedback-Authentifizierungs-Token',Updated=TO_TIMESTAMP('2022-11-14 15:38:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581654 AND AD_Language='nl_NL'
;

-- 2022-11-14T13:38:02.759Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581654,'nl_NL') 
;

-- Element: FeedbackResourceAuthToken
-- 2022-11-14T13:38:08.539Z
UPDATE AD_Element_Trl SET Description='Authentifizierungs-Token, das im ''Authorization'' Header bei der Verbindung zu einer externen Ressourcen-URL zu verwenden ist.',Updated=TO_TIMESTAMP('2022-11-14 15:38:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581654 AND AD_Language='de_CH'
;

-- 2022-11-14T13:38:08.541Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581654,'de_CH') 
;

-- Element: FeedbackResourceAuthToken
-- 2022-11-14T13:38:10.134Z
UPDATE AD_Element_Trl SET Description='Authentifizierungs-Token, das im ''Authorization'' Header bei der Verbindung zu einer externen Ressourcen-URL zu verwenden ist.',Updated=TO_TIMESTAMP('2022-11-14 15:38:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581654 AND AD_Language='de_DE'
;

-- 2022-11-14T13:38:10.136Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581654,'de_DE') 
;

-- 2022-11-14T13:38:10.138Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581654,'de_DE') 
;

-- Element: FeedbackResourceAuthToken
-- 2022-11-14T13:38:12.621Z
UPDATE AD_Element_Trl SET Description='Authentifizierungs-Token, das im ''Authorization'' Header bei der Verbindung zu einer externen Ressourcen-URL zu verwenden ist.',Updated=TO_TIMESTAMP('2022-11-14 15:38:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581654 AND AD_Language='fr_CH'
;

-- 2022-11-14T13:38:12.623Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581654,'fr_CH') 
;

-- Element: FeedbackResourceAuthToken
-- 2022-11-14T13:38:14.454Z
UPDATE AD_Element_Trl SET Description='Authentifizierungs-Token, das im ''Authorization'' Header bei der Verbindung zu einer externen Ressourcen-URL zu verwenden ist.',Updated=TO_TIMESTAMP('2022-11-14 15:38:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581654 AND AD_Language='nl_NL'
;

-- 2022-11-14T13:38:14.456Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581654,'nl_NL') 
;

-- Element: FeedbackResourceAuthToken
-- 2022-11-14T13:38:21.394Z
UPDATE AD_Element_Trl SET Description='Token to be used in ''Authorization'' Header when connecting to the configured Feedback resource.',Updated=TO_TIMESTAMP('2022-11-14 15:38:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581654 AND AD_Language='en_US'
;

-- 2022-11-14T13:38:21.395Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581654,'en_US') 
;

