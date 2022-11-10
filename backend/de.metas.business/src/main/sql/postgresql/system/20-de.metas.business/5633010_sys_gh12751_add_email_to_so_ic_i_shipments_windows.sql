-- 2022-04-01T15:19:21.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580746,0,TO_TIMESTAMP('2022-04-01 16:19:20','YYYY-MM-DD HH24:MI:SS'),100,'The email address will be propagated to shipment, invoices and doc outbound logs, regardless the ones from contact master.','D','The email address will be propagated to shipment, invoices and doc outbound logs, regardless the ones from contact master.','Y','eMail','eMail',TO_TIMESTAMP('2022-04-01 16:19:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-01T15:19:21.128Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580746 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-04-01T15:19:56.011Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Die E-Mail-Adresse wird in die Liefer-, Rechnungs- und Ausgehende-Belege übernommen, unabhängig von denen aus dem Kontakt-Stamm.', Help='Die E-Mail-Adresse wird in die Liefer-, Rechnungs- und Ausgehende-Belege übernommen, unabhängig von denen aus dem Kontakt-Stamm.',Updated=TO_TIMESTAMP('2022-04-01 16:19:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580746 AND AD_Language='de_DE'
;

-- 2022-04-01T15:19:56.054Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580746,'de_DE') 
;

-- 2022-04-01T15:19:56.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(580746,'de_DE') 
;

-- 2022-04-01T15:19:56.087Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='eMail', Description='Die E-Mail-Adresse wird in die Liefer-, Rechnungs- und Ausgehende-Belege übernommen, unabhängig von denen aus dem Kontakt-Stamm.', Help='Die E-Mail-Adresse wird in die Liefer-, Rechnungs- und Ausgehende-Belege übernommen, unabhängig von denen aus dem Kontakt-Stamm.' WHERE AD_Element_ID=580746
;

-- 2022-04-01T15:19:56.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='eMail', Description='Die E-Mail-Adresse wird in die Liefer-, Rechnungs- und Ausgehende-Belege übernommen, unabhängig von denen aus dem Kontakt-Stamm.', Help='Die E-Mail-Adresse wird in die Liefer-, Rechnungs- und Ausgehende-Belege übernommen, unabhängig von denen aus dem Kontakt-Stamm.' WHERE AD_Element_ID=580746 AND IsCentrallyMaintained='Y'
;

-- 2022-04-01T15:19:56.090Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='eMail', Description='Die E-Mail-Adresse wird in die Liefer-, Rechnungs- und Ausgehende-Belege übernommen, unabhängig von denen aus dem Kontakt-Stamm.', Help='Die E-Mail-Adresse wird in die Liefer-, Rechnungs- und Ausgehende-Belege übernommen, unabhängig von denen aus dem Kontakt-Stamm.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580746) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580746)
;

-- 2022-04-01T15:19:56.107Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='eMail', Description='Die E-Mail-Adresse wird in die Liefer-, Rechnungs- und Ausgehende-Belege übernommen, unabhängig von denen aus dem Kontakt-Stamm.', Help='Die E-Mail-Adresse wird in die Liefer-, Rechnungs- und Ausgehende-Belege übernommen, unabhängig von denen aus dem Kontakt-Stamm.', CommitWarning = NULL WHERE AD_Element_ID = 580746
;

-- 2022-04-01T15:19:56.110Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='eMail', Description='Die E-Mail-Adresse wird in die Liefer-, Rechnungs- und Ausgehende-Belege übernommen, unabhängig von denen aus dem Kontakt-Stamm.', Help='Die E-Mail-Adresse wird in die Liefer-, Rechnungs- und Ausgehende-Belege übernommen, unabhängig von denen aus dem Kontakt-Stamm.' WHERE AD_Element_ID = 580746
;

-- 2022-04-01T15:19:56.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'eMail', Description = 'Die E-Mail-Adresse wird in die Liefer-, Rechnungs- und Ausgehende-Belege übernommen, unabhängig von denen aus dem Kontakt-Stamm.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580746
;

-- 2022-04-01T15:20:10.349Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Die E-Mail-Adresse wird in die Liefer-, Rechnungs- und Ausgehende-Belege übernommen, unabhängig von denen aus dem Kontakt-Stamm.', Help='Die E-Mail-Adresse wird in die Liefer-, Rechnungs- und Ausgehende-Belege übernommen, unabhängig von denen aus dem Kontakt-Stamm.',Updated=TO_TIMESTAMP('2022-04-01 16:20:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580746 AND AD_Language='nl_NL'
;

-- 2022-04-01T15:20:10.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580746,'nl_NL') 
;

-- 2022-04-01T15:20:18.510Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Die E-Mail-Adresse wird in die Liefer-, Rechnungs- und Ausgehende-Belege übernommen, unabhängig von denen aus dem Kontakt-Stamm.', Help='Die E-Mail-Adresse wird in die Liefer-, Rechnungs- und Ausgehende-Belege übernommen, unabhängig von denen aus dem Kontakt-Stamm.',Updated=TO_TIMESTAMP('2022-04-01 16:20:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580746 AND AD_Language='de_CH'
;

-- 2022-04-01T15:20:18.513Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580746,'de_CH') 
;

-- 2022-04-01T15:22:07.827Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,579252,691601,580746,0,540279,0,TO_TIMESTAMP('2022-04-01 16:22:07','YYYY-MM-DD HH24:MI:SS'),100,'Die E-Mail-Adresse wird in die Liefer-, Rechnungs- und Ausgehende-Belege übernommen, unabhängig von denen aus dem Kontakt-Stamm.',0,'D','Die E-Mail-Adresse wird in die Liefer-, Rechnungs- und Ausgehende-Belege übernommen, unabhängig von denen aus dem Kontakt-Stamm.',0,'Y','Y','Y','N','N','N','N','N','eMail',0,550,0,1,1,TO_TIMESTAMP('2022-04-01 16:22:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-01T15:22:07.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691601 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-01T15:22:07.834Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580746) 
;

-- 2022-04-01T15:22:07.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691601
;

-- 2022-04-01T15:22:07.850Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(691601)
;

-- 2022-04-01T15:22:13.153Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2022-04-01 16:22:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691601
;

-- 2022-04-01T15:23:29.406Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,691601,0,540279,540056,605283,'F',TO_TIMESTAMP('2022-04-01 16:23:29','YYYY-MM-DD HH24:MI:SS'),100,'Die E-Mail-Adresse wird in die Liefer-, Rechnungs- und Ausgehende-Belege übernommen, unabhängig von denen aus dem Kontakt-Stamm.','Die E-Mail-Adresse wird in die Liefer-, Rechnungs- und Ausgehende-Belege übernommen, unabhängig von denen aus dem Kontakt-Stamm.','Y','Y','N','Y','N','N','N',0,'eMail',1040,0,0,TO_TIMESTAMP('2022-04-01 16:23:29','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2022-04-01T15:36:23.814Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,579210,691602,580746,0,263,0,TO_TIMESTAMP('2022-04-01 16:36:23','YYYY-MM-DD HH24:MI:SS'),100,'Die E-Mail-Adresse wird in die Liefer-, Rechnungs- und Ausgehende-Belege übernommen, unabhängig von denen aus dem Kontakt-Stamm.',0,'D','Die E-Mail-Adresse wird in die Liefer-, Rechnungs- und Ausgehende-Belege übernommen, unabhängig von denen aus dem Kontakt-Stamm.',0,'Y','Y','N','N','N','N','N','N','eMail',0,500,0,1,1,TO_TIMESTAMP('2022-04-01 16:36:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-01T15:36:23.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691602 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-01T15:36:23.820Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580746) 
;

-- 2022-04-01T15:36:23.827Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691602
;

-- 2022-04-01T15:36:23.829Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(691602)
;

-- 2022-04-01T15:37:02.701Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,691602,0,263,541214,605284,'F',TO_TIMESTAMP('2022-04-01 16:37:02','YYYY-MM-DD HH24:MI:SS'),100,'Die E-Mail-Adresse wird in die Liefer-, Rechnungs- und Ausgehende-Belege übernommen, unabhängig von denen aus dem Kontakt-Stamm.','Die E-Mail-Adresse wird in die Liefer-, Rechnungs- und Ausgehende-Belege übernommen, unabhängig von denen aus dem Kontakt-Stamm.','Y','Y','N','Y','N','N','N',0,'eMail',90,0,0,TO_TIMESTAMP('2022-04-01 16:37:02','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2022-04-01T15:42:39.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLength=20,Updated=TO_TIMESTAMP('2022-04-01 16:42:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691602
;

-- 2022-04-01T15:42:42.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLength=40,Updated=TO_TIMESTAMP('2022-04-01 16:42:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691602
;

-- 2022-04-01T15:47:32.326Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,579212,691603,580746,0,257,0,TO_TIMESTAMP('2022-04-01 16:47:32','YYYY-MM-DD HH24:MI:SS'),100,'Die E-Mail-Adresse wird in die Liefer-, Rechnungs- und Ausgehende-Belege übernommen, unabhängig von denen aus dem Kontakt-Stamm.',0,'D','Die E-Mail-Adresse wird in die Liefer-, Rechnungs- und Ausgehende-Belege übernommen, unabhängig von denen aus dem Kontakt-Stamm.',0,'Y','Y','N','N','N','N','N','N','eMail',0,520,0,1,1,TO_TIMESTAMP('2022-04-01 16:47:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-01T15:47:32.330Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691603 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-01T15:47:32.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580746) 
;

-- 2022-04-01T15:47:32.340Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691603
;

-- 2022-04-01T15:47:32.342Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(691603)
;

-- 2022-04-01T15:47:36.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLength=40,Updated=TO_TIMESTAMP('2022-04-01 16:47:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691603
;

-- 2022-04-01T15:48:25.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,691603,0,257,540128,605285,'F',TO_TIMESTAMP('2022-04-01 16:48:24','YYYY-MM-DD HH24:MI:SS'),100,'Die E-Mail-Adresse wird in die Liefer-, Rechnungs- und Ausgehende-Belege übernommen, unabhängig von denen aus dem Kontakt-Stamm.','Die E-Mail-Adresse wird in die Liefer-, Rechnungs- und Ausgehende-Belege übernommen, unabhängig von denen aus dem Kontakt-Stamm.','Y','Y','N','Y','N','N','N',0,'eMail',110,0,0,TO_TIMESTAMP('2022-04-01 16:48:24','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

