-- 2020-05-04T09:21:43.371Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,AD_Org_ID,Updated,UpdatedBy,AD_Element_ID,ColumnName,PrintName,Name,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2020-05-04 12:21:43','YYYY-MM-DD HH24:MI:SS'),100,0,TO_TIMESTAMP('2020-05-04 12:21:43','YYYY-MM-DD HH24:MI:SS'),100,577699,'StatementLine','Statement Line','Statement Line','D')
;

-- 2020-05-04T09:21:43.374Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_Description,PO_Help,PO_Name,PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Help,PrintName,WEBUI_NameNewBreadcrumb,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Help,t.PrintName,t.WEBUI_NameNewBreadcrumb,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577699 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-05-04T09:23:34.613Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2020-05-04 12:23:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563160
;

-- 2020-05-04T09:23:50.624Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNoGrid=0, IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2020-05-04 12:23:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563160
;

-- 2020-05-04T09:23:50.629Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNoGrid=40, IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2020-05-04 12:23:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563161
;

-- 2020-05-04T09:23:50.633Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNoGrid=50, IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2020-05-04 12:23:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563162
;

-- 2020-05-04T09:23:50.638Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNoGrid=60, IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2020-05-04 12:23:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563163
;

-- 2020-05-04T09:23:50.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNoGrid=70, IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2020-05-04 12:23:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563164
;

-- 2020-05-04T09:23:50.647Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNoGrid=80, IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2020-05-04 12:23:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563165
;

-- 2020-05-04T09:23:50.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNoGrid=90, IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2020-05-04 12:23:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563166
;

-- 2020-05-04T09:23:50.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNoGrid=100, IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2020-05-04 12:23:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563167
;

-- 2020-05-04T09:23:50.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNoGrid=110, IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2020-05-04 12:23:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563178
;

-- 2020-05-04T09:24:44.153Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,ColumnDisplayLength,IncludedTabHeight,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,Name,AD_Org_ID,Description,EntityType) VALUES (542037,'Y',0,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2020-05-04 12:24:43','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,TO_TIMESTAMP('2020-05-04 12:24:43','YYYY-MM-DD HH24:MI:SS'),100,606034,'Y',450,330,1,1,570150,'Abgeglichen',0,'Zeigt an ob eine Zahlung bereits mit einem Kontoauszug abgeglichen wurde','D')
;

-- 2020-05-04T09:24:44.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=606034 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-05-04T09:24:44.190Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1105) 
;

-- 2020-05-04T09:24:44.211Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=606034
;

-- 2020-05-04T09:24:44.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(606034)
;

-- 2020-05-04T09:27:32.671Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Client_ID,CreatedBy,SeqNo,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Org_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,IsAllowFiltering,MultiLine_LinesCount,IsMultiLine,Name,AD_Field_ID,AD_Tab_ID,Created,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,Updated) VALUES (100,568192,0,100,75,0,'N',0,0,543060,'F','N',0,'N','IsReconciled',606034,542037,TO_TIMESTAMP('2020-05-04 12:27:32','YYYY-MM-DD HH24:MI:SS'),'','Y','N','Y','N',TO_TIMESTAMP('2020-05-04 12:27:32','YYYY-MM-DD HH24:MI:SS'))
;

-- 2020-05-04T09:28:41.871Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNoGrid=70, IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2020-05-04 12:28:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=568192
;

-- 2020-05-04T09:28:41.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNoGrid=80, IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2020-05-04 12:28:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563164
;

-- 2020-05-04T09:28:41.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNoGrid=90, IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2020-05-04 12:28:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563165
;

-- 2020-05-04T09:28:41.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNoGrid=100, IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2020-05-04 12:28:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563166
;

-- 2020-05-04T09:28:41.887Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNoGrid=110, IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2020-05-04 12:28:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563167
;

-- 2020-05-04T09:28:41.890Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNoGrid=120, IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2020-05-04 12:28:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563178
;

-- 2020-05-04T09:31:54.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Auszugsposition', IsTranslated='Y', Name='Auszugsposition',Updated=TO_TIMESTAMP('2020-05-04 12:31:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577699
;

-- 2020-05-04T09:31:54.029Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577699,'de_CH') 
;

-- 2020-05-04T09:31:57.716Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Auszugsposition', IsTranslated='Y', Name='Auszugsposition',Updated=TO_TIMESTAMP('2020-05-04 12:31:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577699
;

-- 2020-05-04T09:31:57.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577699,'de_DE') 
;

-- 2020-05-04T09:31:57.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577699,'de_DE') 
;

-- 2020-05-04T09:31:57.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='StatementLine', Name='Auszugsposition', Description=NULL, Help=NULL WHERE AD_Element_ID=577699
;

-- 2020-05-04T09:31:57.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='StatementLine', Name='Auszugsposition', Description=NULL, Help=NULL, AD_Element_ID=577699 WHERE UPPER(ColumnName)='STATEMENTLINE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-05-04T09:31:57.741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='StatementLine', Name='Auszugsposition', Description=NULL, Help=NULL WHERE AD_Element_ID=577699 AND IsCentrallyMaintained='Y'
;

-- 2020-05-04T09:31:57.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Auszugsposition', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577699) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577699)
;

-- 2020-05-04T09:31:57.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Auszugsposition', Name='Auszugsposition' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577699)
;

-- 2020-05-04T09:31:57.769Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Auszugsposition', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577699
;

-- 2020-05-04T09:31:57.771Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Auszugsposition', Description=NULL, Help=NULL WHERE AD_Element_ID = 577699
;

-- 2020-05-04T09:31:57.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Auszugsposition', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577699
;

-- 2020-05-04T09:31:59.669Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2020-05-04 12:31:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577699
;

-- 2020-05-04T09:31:59.671Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577699,'en_US') 
;

-- 2020-05-04T09:37:06.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET CommitWarning=NULL, Help=NULL, Name='Auszugsposition', Description=NULL, AD_Element_ID=577699,Updated=TO_TIMESTAMP('2020-05-04 12:37:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540707
;

-- 2020-05-04T09:37:06.296Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_tab_translation_from_ad_element(577699) 
;

-- 2020-05-04T09:37:06.298Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Tab(540707)
;

-- 2020-05-04T09:39:05.328Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Auszugsposition', IsTranslated='Y', Name='Auszugsposition',Updated=TO_TIMESTAMP('2020-05-04 12:39:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Element_ID=1382
;

-- 2020-05-04T09:39:05.330Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1382,'fr_CH') 
;

-- 2020-05-04T09:39:18.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Statement Line', IsTranslated='Y', Name='Statement Line',Updated=TO_TIMESTAMP('2020-05-04 12:39:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_GB' AND AD_Element_ID=1382
;

-- 2020-05-04T09:39:18.999Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1382,'en_GB') 
;

-- 2020-05-04T09:39:23.037Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Auszugsposition', IsTranslated='Y', Name='Auszugsposition',Updated=TO_TIMESTAMP('2020-05-04 12:39:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=1382
;

-- 2020-05-04T09:39:23.038Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1382,'de_CH') 
;

-- 2020-05-04T09:39:29.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Statement line',Updated=TO_TIMESTAMP('2020-05-04 12:39:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=1382
;

-- 2020-05-04T09:39:29.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1382,'en_US') 
;

-- 2020-05-04T09:39:36.229Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Auszugsposition', IsTranslated='Y', Name='Auszugsposition',Updated=TO_TIMESTAMP('2020-05-04 12:39:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=1382
;

-- 2020-05-04T09:39:36.230Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1382,'de_DE') 
;

-- 2020-05-04T09:39:36.260Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1382,'de_DE') 
;

-- 2020-05-04T09:39:36.262Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='C_BankStatementLine_ID', Name='Auszugsposition', Description='Position auf einem Bankauszug zu dieser Bank', Help='Die "Auszugs-Position" bezeichnet eine eindeutige Transaktion (Einzahlung, Auszahlung, Auslage/Gebühr) für den definierten Zeitraum bei dieser Bank.' WHERE AD_Element_ID=1382
;

-- 2020-05-04T09:39:36.264Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BankStatementLine_ID', Name='Auszugsposition', Description='Position auf einem Bankauszug zu dieser Bank', Help='Die "Auszugs-Position" bezeichnet eine eindeutige Transaktion (Einzahlung, Auszahlung, Auslage/Gebühr) für den definierten Zeitraum bei dieser Bank.', AD_Element_ID=1382 WHERE UPPER(ColumnName)='C_BANKSTATEMENTLINE_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-05-04T09:39:36.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='C_BankStatementLine_ID', Name='Auszugsposition', Description='Position auf einem Bankauszug zu dieser Bank', Help='Die "Auszugs-Position" bezeichnet eine eindeutige Transaktion (Einzahlung, Auszahlung, Auslage/Gebühr) für den definierten Zeitraum bei dieser Bank.' WHERE AD_Element_ID=1382 AND IsCentrallyMaintained='Y'
;

-- 2020-05-04T09:39:36.267Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Auszugsposition', Description='Position auf einem Bankauszug zu dieser Bank', Help='Die "Auszugs-Position" bezeichnet eine eindeutige Transaktion (Einzahlung, Auszahlung, Auslage/Gebühr) für den definierten Zeitraum bei dieser Bank.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1382) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1382)
;

-- 2020-05-04T09:39:36.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Auszugsposition', Name='Auszugsposition' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1382)
;

-- 2020-05-04T09:39:36.299Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Auszugsposition', Description='Position auf einem Bankauszug zu dieser Bank', Help='Die "Auszugs-Position" bezeichnet eine eindeutige Transaktion (Einzahlung, Auszahlung, Auslage/Gebühr) für den definierten Zeitraum bei dieser Bank.', CommitWarning = NULL WHERE AD_Element_ID = 1382
;

-- 2020-05-04T09:39:36.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Auszugsposition', Description='Position auf einem Bankauszug zu dieser Bank', Help='Die "Auszugs-Position" bezeichnet eine eindeutige Transaktion (Einzahlung, Auszahlung, Auslage/Gebühr) für den definierten Zeitraum bei dieser Bank.' WHERE AD_Element_ID = 1382
;

-- 2020-05-04T09:39:36.302Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Auszugsposition', Description = 'Position auf einem Bankauszug zu dieser Bank', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1382
;

-- 2020-05-04T09:39:47.614Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Statement Line', IsTranslated='N', Name='Statement Line',Updated=TO_TIMESTAMP('2020-05-04 12:39:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Element_ID=1382
;

-- 2020-05-04T09:39:47.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1382,'fr_CH') 
;

-- 2020-05-04T09:39:57.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Statement Line',Updated=TO_TIMESTAMP('2020-05-04 12:39:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=1382
;

-- 2020-05-04T09:39:57.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1382,'en_US') 
;

