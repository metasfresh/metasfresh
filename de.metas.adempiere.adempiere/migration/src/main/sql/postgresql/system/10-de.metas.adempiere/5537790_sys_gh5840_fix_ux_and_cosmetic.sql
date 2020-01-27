-- 2019-12-04T06:34:08.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-04 08:34:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=576112
;

-- 2019-12-04T06:34:08.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576112,'de_CH') 
;

-- 2019-12-04T06:34:10.973Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-12-04 08:34:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=576112
;

-- 2019-12-04T06:34:10.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576112,'de_DE') 
;

-- 2019-12-04T06:34:10.987Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576112,'de_DE') 
;

-- 2019-12-04T06:34:28.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Phonecall Date ', Name='Phonecall Date ',Updated=TO_TIMESTAMP('2019-12-04 08:34:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=576112
;

-- 2019-12-04T06:34:28.221Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576112,'en_US') 
;

-- 2019-12-04T06:34:51.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Phonecall Date', Name='Phonecall Date',Updated=TO_TIMESTAMP('2019-12-04 08:34:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=576112
;

-- 2019-12-04T06:34:51.527Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576112,'en_US') 
;

-- 2019-12-04T06:43:28.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=540325
;

-- 2019-12-04T06:43:59.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Client_ID,CreatedBy,SeqNo,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Org_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,IsAllowFiltering,MultiLine_LinesCount,IsMultiLine,Name,AD_Field_ID,AD_Tab_ID,Created,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,Updated) VALUES (100,564564,0,100,22,0,'N',0,0,542365,'F','N',0,'N','Contact',577773,541673,TO_TIMESTAMP('2019-12-04 08:43:59','YYYY-MM-DD HH24:MI:SS'),'Y','N','Y','N',TO_TIMESTAMP('2019-12-04 08:43:59','YYYY-MM-DD HH24:MI:SS'))
;

-- 2019-12-04T06:49:14.696Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2019-12-04 08:49:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564553
;

-- 2019-12-04T06:49:20.390Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2019-12-04 08:49:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564553
;

-- 2019-12-04T06:51:03.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=540324
;

-- 2019-12-04T06:51:38.091Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,Help,AD_Client_ID,CreatedBy,SeqNo,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Org_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,IsAllowFiltering,MultiLine_LinesCount,IsMultiLine,Name,AD_Field_ID,AD_Tab_ID,Created,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,Updated) VALUES (100,564565,'Identifiziert die Adresse des Geschäftspartners',0,100,21,0,'N',0,0,542365,'F','N',0,'N','Standort',577772,541673,TO_TIMESTAMP('2019-12-04 08:51:37','YYYY-MM-DD HH24:MI:SS'),'Identifiziert die (Liefer-) Adresse des Geschäftspartners','Y','N','Y','N',TO_TIMESTAMP('2019-12-04 08:51:37','YYYY-MM-DD HH24:MI:SS'))
;

-- 2019-12-04T06:54:23.487Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Ordered ', IsTranslated='Y', Name='Ordered  ',Updated=TO_TIMESTAMP('2019-12-04 08:54:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=576252
;

-- 2019-12-04T06:54:23.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576252,'en_US') 
;

-- 2019-12-04T06:54:28.590Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Ordered', Name='Ordered',Updated=TO_TIMESTAMP('2019-12-04 08:54:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=576252
;

-- 2019-12-04T06:54:28.592Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576252,'en_US') 
;

-- 2019-12-04T07:10:01.411Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,IsActive,Created,CreatedBy,IsOrderByValue,Updated,UpdatedBy,AD_Reference_ID,ValidationType,Name,AD_Org_ID,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2019-12-04 09:10:01','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-12-04 09:10:01','YYYY-MM-DD HH24:MI:SS'),100,541085,'T','C_PhonecallSchedule -> C_Phonecall_Schema',0,'D')
;

-- 2019-12-04T07:10:01.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541085 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-12-04T07:11:07.117Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Reference_ID,AD_Key,AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsValueDisplayed,AD_Window_ID,UpdatedBy,AD_Table_ID,AD_Org_ID,EntityType) VALUES (541085,564182,0,'Y',TO_TIMESTAMP('2019-12-04 09:11:07','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-12-04 09:11:07','YYYY-MM-DD HH24:MI:SS'),'N',540583,100,541176,0,'D')
;

-- 2019-12-04T07:13:37.596Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Key=564183, WhereClause='C_Phonecall_Schedule_ID = @C_Phonecall_Schedule_ID/-1@',Updated=TO_TIMESTAMP('2019-12-04 09:13:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541085
;

-- 2019-12-04T07:13:55.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=541085,Updated=TO_TIMESTAMP('2019-12-04 09:13:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564143
;

-- 2019-12-04T07:18:46.202Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Name='C_Phonecall_Schedule -> C_Phonecall_Schema',Updated=TO_TIMESTAMP('2019-12-04 09:18:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541085
;

-- 2019-12-04T07:19:53.514Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2019-12-04 09:19:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564143
;

-- 2019-12-04T07:19:58.859Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=541085
;

-- 2019-12-04T07:19:58.868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=541085
;

-- 2019-12-04T07:20:23.194Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,IsActive,Created,CreatedBy,IsOrderByValue,Updated,UpdatedBy,AD_Reference_ID,ValidationType,Name,AD_Org_ID,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2019-12-04 09:20:23','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-12-04 09:20:23','YYYY-MM-DD HH24:MI:SS'),100,541086,'T','C_Phonecall_Schedule -> C_Phonecall_Schema_Version',0,'D')
;

-- 2019-12-04T07:20:23.196Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541086 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-12-04T07:21:14.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Reference_ID,AD_Key,WhereClause,AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsValueDisplayed,AD_Window_ID,UpdatedBy,AD_Table_ID,AD_Org_ID,EntityType) VALUES (541086,564176,'C_Phonecall_Schedule_ID = @C_Phonecall_Schedule_ID/-`@',0,'Y',TO_TIMESTAMP('2019-12-04 09:21:14','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-12-04 09:21:14','YYYY-MM-DD HH24:MI:SS'),'N',540584,100,541178,0,'D')
;

-- 2019-12-04T07:21:29.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=541086,Updated=TO_TIMESTAMP('2019-12-04 09:21:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564143
;

-- 2019-12-04T07:23:54.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,IsActive,Created,CreatedBy,IsOrderByValue,Updated,UpdatedBy,AD_Reference_ID,ValidationType,Name,AD_Org_ID,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2019-12-04 09:23:54','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-12-04 09:23:54','YYYY-MM-DD HH24:MI:SS'),100,541087,'T','C_Phonecall_Schedule -> C_Phonecall_Schema_Version_Line',0,'D')
;

-- 2019-12-04T07:23:54.387Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541087 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-12-04T07:24:22.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='C_Phonecall_Schedule_ID = @C_Phonecall_Schedule_ID/-1`@',Updated=TO_TIMESTAMP('2019-12-04 09:24:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541086
;

-- 2019-12-04T07:24:35.327Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Reference_ID,AD_Key,WhereClause,AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsValueDisplayed,AD_Window_ID,UpdatedBy,AD_Table_ID,AD_Org_ID,EntityType) VALUES (541087,564174,'C_Phonecall_Schedule_ID = @C_Phonecall_Schedule_ID/-1`@',0,'Y',TO_TIMESTAMP('2019-12-04 09:24:35','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-12-04 09:24:35','YYYY-MM-DD HH24:MI:SS'),'N',540584,100,541178,0,'D')
;

-- 2019-12-04T07:24:42.271Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Key=564176,Updated=TO_TIMESTAMP('2019-12-04 09:24:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541087
;

-- 2019-12-04T07:25:39.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=541087,Updated=TO_TIMESTAMP('2019-12-04 09:25:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=564184
;

-- 2019-12-04T07:29:16.316Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2019-12-04 09:29:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541208
;

-- 2019-12-04T07:29:25.302Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Contact1', Name='Contact1',Updated=TO_TIMESTAMP('2019-12-04 09:29:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Element_ID=541208
;

-- 2019-12-04T07:29:25.305Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541208,'fr_CH') 
;

-- 2019-12-04T07:29:31.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Contact1', IsTranslated='Y', Name='Contact1',Updated=TO_TIMESTAMP('2019-12-04 09:29:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Element_ID=541208
;

-- 2019-12-04T07:29:31.163Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541208,'en_GB') 
;

-- 2019-12-04T07:29:35.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Contact1', IsTranslated='Y', Name='Contact1',Updated=TO_TIMESTAMP('2019-12-04 09:29:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=541208
;

-- 2019-12-04T07:29:35.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541208,'de_CH') 
;

-- 2019-12-04T07:29:39.216Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Contact1', IsTranslated='Y', Name='Contact1',Updated=TO_TIMESTAMP('2019-12-04 09:29:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=541208
;

-- 2019-12-04T07:29:39.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541208,'en_US') 
;

-- 2019-12-04T07:29:45.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Contact1', Name='Contact1',Updated=TO_TIMESTAMP('2019-12-04 09:29:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=541208
;

-- 2019-12-04T07:29:45.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541208,'de_DE') 
;

-- 2019-12-04T07:29:45.135Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(541208,'de_DE') 
;

-- 2019-12-04T07:29:45.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_BP_Contact_ID', Name='Contact1', Description=NULL, Help=NULL WHERE AD_Element_ID=541208
;

-- 2019-12-04T07:29:45.140Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BP_Contact_ID', Name='Contact1', Description=NULL, Help=NULL, AD_Element_ID=541208 WHERE UPPER(ColumnName)='C_BP_CONTACT_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-12-04T07:29:45.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BP_Contact_ID', Name='Contact1', Description=NULL, Help=NULL WHERE AD_Element_ID=541208 AND IsCentrallyMaintained='Y'
;

-- 2019-12-04T07:29:45.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Contact1', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541208) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 541208)
;

-- 2019-12-04T07:29:45.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Contact1', Name='Contact1' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541208)
;

-- 2019-12-04T07:29:45.163Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Contact1', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 541208
;

-- 2019-12-04T07:29:45.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Contact1', Description=NULL, Help=NULL WHERE AD_Element_ID = 541208
;

-- 2019-12-04T07:29:45.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Contact1', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 541208
;

-- 2019-12-04T07:31:11.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Nutzer / Kontakt', IsTranslated='Y', Name='Nutzer / Kontakt',Updated=TO_TIMESTAMP('2019-12-04 09:31:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=541208
;

-- 2019-12-04T07:31:11.785Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541208,'de_DE') 
;

-- 2019-12-04T07:31:11.794Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(541208,'de_DE') 
;

-- 2019-12-04T07:31:11.803Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_BP_Contact_ID', Name='Nutzer / Kontakt', Description=NULL, Help=NULL WHERE AD_Element_ID=541208
;

-- 2019-12-04T07:31:11.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BP_Contact_ID', Name='Nutzer / Kontakt', Description=NULL, Help=NULL, AD_Element_ID=541208 WHERE UPPER(ColumnName)='C_BP_CONTACT_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-12-04T07:31:11.807Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BP_Contact_ID', Name='Nutzer / Kontakt', Description=NULL, Help=NULL WHERE AD_Element_ID=541208 AND IsCentrallyMaintained='Y'
;

-- 2019-12-04T07:31:11.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Nutzer / Kontakt', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541208) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 541208)
;

-- 2019-12-04T07:31:11.822Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Nutzer / Kontakt', Name='Nutzer / Kontakt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541208)
;

-- 2019-12-04T07:31:11.826Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Nutzer / Kontakt', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 541208
;

-- 2019-12-04T07:31:11.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Nutzer / Kontakt', Description=NULL, Help=NULL WHERE AD_Element_ID = 541208
;

-- 2019-12-04T07:31:11.829Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Nutzer / Kontakt', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 541208
;

-- 2019-12-04T07:31:25.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='User / Contact', Name='User / Contact',Updated=TO_TIMESTAMP('2019-12-04 09:31:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=541208
;

-- 2019-12-04T07:31:25.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541208,'en_US') 
;

-- 2019-12-04T07:31:29.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Nutzer / Kontakt', Name='Nutzer / Kontakt',Updated=TO_TIMESTAMP('2019-12-04 09:31:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=541208
;

-- 2019-12-04T07:31:29.436Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541208,'de_CH') 
;

-- 2019-12-04T07:31:32.270Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N',Updated=TO_TIMESTAMP('2019-12-04 09:31:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Element_ID=541208
;

-- 2019-12-04T07:31:32.272Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541208,'en_GB') 
;

-- 2019-12-04T07:37:57.066Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Kontakt', Name='Kontakt',Updated=TO_TIMESTAMP('2019-12-04 09:37:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=541208
;

-- 2019-12-04T07:37:57.068Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541208,'de_DE') 
;

-- 2019-12-04T07:37:57.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(541208,'de_DE') 
;

-- 2019-12-04T07:37:57.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_BP_Contact_ID', Name='Kontakt', Description=NULL, Help=NULL WHERE AD_Element_ID=541208
;

-- 2019-12-04T07:37:57.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BP_Contact_ID', Name='Kontakt', Description=NULL, Help=NULL, AD_Element_ID=541208 WHERE UPPER(ColumnName)='C_BP_CONTACT_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-12-04T07:37:57.083Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BP_Contact_ID', Name='Kontakt', Description=NULL, Help=NULL WHERE AD_Element_ID=541208 AND IsCentrallyMaintained='Y'
;

-- 2019-12-04T07:37:57.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Kontakt', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=541208) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 541208)
;

-- 2019-12-04T07:37:57.101Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Kontakt', Name='Kontakt' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=541208)
;

-- 2019-12-04T07:37:57.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Kontakt', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 541208
;

-- 2019-12-04T07:37:57.108Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Kontakt', Description=NULL, Help=NULL WHERE AD_Element_ID = 541208
;

-- 2019-12-04T07:37:57.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Kontakt', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 541208
;

-- 2019-12-04T07:38:12.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Kontakt', Name='Kontakt',Updated=TO_TIMESTAMP('2019-12-04 09:38:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=541208
;

-- 2019-12-04T07:38:12.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541208,'de_CH') 
;

-- 2019-12-04T07:38:25.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Contact1', Name='Contact1',Updated=TO_TIMESTAMP('2019-12-04 09:38:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='it_CH' AND AD_Element_ID=541208
;

-- 2019-12-04T07:38:25.199Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541208,'it_CH') 
;

-- 2019-12-04T07:38:34.017Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Contact', Name='Contact',Updated=TO_TIMESTAMP('2019-12-04 09:38:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=541208
;

-- 2019-12-04T07:38:34.019Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541208,'en_US') 
;

