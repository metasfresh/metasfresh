-- 2022-05-27T08:08:32.466Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=12,Updated=TO_TIMESTAMP('2022-05-27 11:08:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541785
;

-- 2022-05-27T08:09:49.867Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540124, SeqNo=50,Updated=TO_TIMESTAMP('2022-05-27 11:09:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541793
;

-- 2022-05-27T08:10:20.049Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=13,Updated=TO_TIMESTAMP('2022-05-27 11:10:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541793
;

-- 2022-05-27T08:17:25.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Legt die Reihenfolge der Preise fest, z.B. bei der Ausgabe aller Preise zu einer bestimmten Preislistenversion',Updated=TO_TIMESTAMP('2022-05-27 11:17:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1001870
;

-- 2022-05-27T08:17:25.769Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Reihenfolge', Description='Legt die Reihenfolge der Preise fest, z.B. bei der Ausgabe aller Preise zu einer bestimmten Preislistenversion', Help='"Reihenfolge" bestimmt die Reihenfolge der Einträge' WHERE AD_Element_ID=1001870
;

-- 2022-05-27T08:17:25.770Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Reihenfolge', Description='Legt die Reihenfolge der Preise fest, z.B. bei der Ausgabe aller Preise zu einer bestimmten Preislistenversion', Help='"Reihenfolge" bestimmt die Reihenfolge der Einträge' WHERE AD_Element_ID=1001870 AND IsCentrallyMaintained='Y'
;

-- 2022-05-27T08:17:25.772Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Reihenfolge', Description='Legt die Reihenfolge der Preise fest, z.B. bei der Ausgabe aller Preise zu einer bestimmten Preislistenversion', Help='"Reihenfolge" bestimmt die Reihenfolge der Einträge' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1001870) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1001870)
;

-- 2022-05-27T08:17:25.799Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Reihenfolge', Description='Legt die Reihenfolge der Preise fest, z.B. bei der Ausgabe aller Preise zu einer bestimmten Preislistenversion', Help='"Reihenfolge" bestimmt die Reihenfolge der Einträge', CommitWarning = NULL WHERE AD_Element_ID = 1001870
;

-- 2022-05-27T08:17:25.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Reihenfolge', Description='Legt die Reihenfolge der Preise fest, z.B. bei der Ausgabe aller Preise zu einer bestimmten Preislistenversion', Help='"Reihenfolge" bestimmt die Reihenfolge der Einträge' WHERE AD_Element_ID = 1001870
;

-- 2022-05-27T08:17:25.800Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Reihenfolge', Description = 'Legt die Reihenfolge der Preise fest, z.B. bei der Ausgabe aller Preise zu einer bestimmten Preislistenversion', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1001870
;

-- 2022-05-27T08:17:39.332Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt die Reihenfolge der Preise fest, z.B. bei der Ausgabe aller Preise zu einer bestimmten Preislistenversion',Updated=TO_TIMESTAMP('2022-05-27 11:17:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1001870 AND AD_Language='de_DE'
;

-- 2022-05-27T08:17:39.371Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1001870,'de_DE') 
;

-- 2022-05-27T08:17:39.403Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1001870,'de_DE') 
;

-- 2022-05-27T08:17:39.404Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Reihenfolge', Description='Legt die Reihenfolge der Preise fest, z.B. bei der Ausgabe aller Preise zu einer bestimmten Preislistenversion', Help='"Reihenfolge" bestimmt die Reihenfolge der Einträge' WHERE AD_Element_ID=1001870
;

-- 2022-05-27T08:17:39.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Reihenfolge', Description='Legt die Reihenfolge der Preise fest, z.B. bei der Ausgabe aller Preise zu einer bestimmten Preislistenversion', Help='"Reihenfolge" bestimmt die Reihenfolge der Einträge' WHERE AD_Element_ID=1001870 AND IsCentrallyMaintained='Y'
;

-- 2022-05-27T08:17:39.405Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Reihenfolge', Description='Legt die Reihenfolge der Preise fest, z.B. bei der Ausgabe aller Preise zu einer bestimmten Preislistenversion', Help='"Reihenfolge" bestimmt die Reihenfolge der Einträge' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1001870) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1001870)
;

-- 2022-05-27T08:17:39.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Reihenfolge', Description='Legt die Reihenfolge der Preise fest, z.B. bei der Ausgabe aller Preise zu einer bestimmten Preislistenversion', Help='"Reihenfolge" bestimmt die Reihenfolge der Einträge', CommitWarning = NULL WHERE AD_Element_ID = 1001870
;

-- 2022-05-27T08:17:39.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Reihenfolge', Description='Legt die Reihenfolge der Preise fest, z.B. bei der Ausgabe aller Preise zu einer bestimmten Preislistenversion', Help='"Reihenfolge" bestimmt die Reihenfolge der Einträge' WHERE AD_Element_ID = 1001870
;

-- 2022-05-27T08:17:39.417Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Reihenfolge', Description = 'Legt die Reihenfolge der Preise fest, z.B. bei der Ausgabe aller Preise zu einer bestimmten Preislistenversion', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1001870
;

-- 2022-05-27T08:17:42.448Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt die Reihenfolge der Preise fest, z.B. bei der Ausgabe aller Preise zu einer bestimmten Preislistenversion',Updated=TO_TIMESTAMP('2022-05-27 11:17:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1001870 AND AD_Language='de_CH'
;

-- 2022-05-27T08:17:42.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1001870,'de_CH') 
;

-- 2022-05-27T08:17:45.143Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Legt die Reihenfolge der Preise fest, z.B. bei der Ausgabe aller Preise zu einer bestimmten Preislistenversion',Updated=TO_TIMESTAMP('2022-05-27 11:17:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1001870 AND AD_Language='nl_NL'
;

-- 2022-05-27T08:17:45.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1001870,'nl_NL') 
;

-- 2022-05-27T08:20:07.526Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Wenn es mehrere passende Preise zu einer Preislistenversion-/Produktkombination gibt, wird der Preis mit dem kleinsten Wert herangezogen.',Updated=TO_TIMESTAMP('2022-05-27 11:20:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543277
;

-- 2022-05-27T08:20:07.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='MatchSeqNo', Name='Matching order', Description='Wenn es mehrere passende Preise zu einer Preislistenversion-/Produktkombination gibt, wird der Preis mit dem kleinsten Wert herangezogen.', Help=NULL WHERE AD_Element_ID=543277
;

-- 2022-05-27T08:20:07.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MatchSeqNo', Name='Matching order', Description='Wenn es mehrere passende Preise zu einer Preislistenversion-/Produktkombination gibt, wird der Preis mit dem kleinsten Wert herangezogen.', Help=NULL, AD_Element_ID=543277 WHERE UPPER(ColumnName)='MATCHSEQNO' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-05-27T08:20:07.554Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MatchSeqNo', Name='Matching order', Description='Wenn es mehrere passende Preise zu einer Preislistenversion-/Produktkombination gibt, wird der Preis mit dem kleinsten Wert herangezogen.', Help=NULL WHERE AD_Element_ID=543277 AND IsCentrallyMaintained='Y'
;

-- 2022-05-27T08:20:07.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Matching order', Description='Wenn es mehrere passende Preise zu einer Preislistenversion-/Produktkombination gibt, wird der Preis mit dem kleinsten Wert herangezogen.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543277) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543277)
;

-- 2022-05-27T08:20:07.580Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Matching order', Description='Wenn es mehrere passende Preise zu einer Preislistenversion-/Produktkombination gibt, wird der Preis mit dem kleinsten Wert herangezogen.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543277
;

-- 2022-05-27T08:20:07.581Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Matching order', Description='Wenn es mehrere passende Preise zu einer Preislistenversion-/Produktkombination gibt, wird der Preis mit dem kleinsten Wert herangezogen.', Help=NULL WHERE AD_Element_ID = 543277
;

-- 2022-05-27T08:20:07.583Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Matching order', Description = 'Wenn es mehrere passende Preise zu einer Preislistenversion-/Produktkombination gibt, wird der Preis mit dem kleinsten Wert herangezogen.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543277
;

-- 2022-05-27T08:20:14.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn es mehrere passende Preise zu einer Preislistenversion-/Produktkombination gibt, wird der Preis mit dem kleinsten Wert herangezogen.',Updated=TO_TIMESTAMP('2022-05-27 11:20:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543277 AND AD_Language='de_CH'
;

-- 2022-05-27T08:20:14.783Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543277,'de_CH') 
;

-- 2022-05-27T08:20:23.116Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn es mehrere passende Preise zu einer Preislistenversion-/Produktkombination gibt, wird der Preis mit dem kleinsten Wert herangezogen.',Updated=TO_TIMESTAMP('2022-05-27 11:20:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543277 AND AD_Language='nl_NL'
;

-- 2022-05-27T08:20:23.118Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543277,'nl_NL') 
;

-- 2022-05-27T08:20:25.532Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn es mehrere passende Preise zu einer Preislistenversion-/Produktkombination gibt, wird der Preis mit dem kleinsten Wert herangezogen.',Updated=TO_TIMESTAMP('2022-05-27 11:20:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543277 AND AD_Language='de_DE'
;

-- 2022-05-27T08:20:25.534Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543277,'de_DE') 
;

-- 2022-05-27T08:20:25.554Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(543277,'de_DE') 
;

-- 2022-05-27T08:20:25.556Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='MatchSeqNo', Name='Matching order', Description='Wenn es mehrere passende Preise zu einer Preislistenversion-/Produktkombination gibt, wird der Preis mit dem kleinsten Wert herangezogen.', Help=NULL WHERE AD_Element_ID=543277
;

-- 2022-05-27T08:20:25.557Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MatchSeqNo', Name='Matching order', Description='Wenn es mehrere passende Preise zu einer Preislistenversion-/Produktkombination gibt, wird der Preis mit dem kleinsten Wert herangezogen.', Help=NULL, AD_Element_ID=543277 WHERE UPPER(ColumnName)='MATCHSEQNO' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-05-27T08:20:25.558Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MatchSeqNo', Name='Matching order', Description='Wenn es mehrere passende Preise zu einer Preislistenversion-/Produktkombination gibt, wird der Preis mit dem kleinsten Wert herangezogen.', Help=NULL WHERE AD_Element_ID=543277 AND IsCentrallyMaintained='Y'
;

-- 2022-05-27T08:20:25.559Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Matching order', Description='Wenn es mehrere passende Preise zu einer Preislistenversion-/Produktkombination gibt, wird der Preis mit dem kleinsten Wert herangezogen.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543277) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543277)
;

-- 2022-05-27T08:20:25.576Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Matching order', Description='Wenn es mehrere passende Preise zu einer Preislistenversion-/Produktkombination gibt, wird der Preis mit dem kleinsten Wert herangezogen.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543277
;

-- 2022-05-27T08:20:25.577Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Matching order', Description='Wenn es mehrere passende Preise zu einer Preislistenversion-/Produktkombination gibt, wird der Preis mit dem kleinsten Wert herangezogen.', Help=NULL WHERE AD_Element_ID = 543277
;

-- 2022-05-27T08:20:25.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Matching order', Description = 'Wenn es mehrere passende Preise zu einer Preislistenversion-/Produktkombination gibt, wird der Preis mit dem kleinsten Wert herangezogen.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543277
;

-- 2022-05-27T08:20:28.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Wenn es mehrere passende Preise zu einer Preislistenversion-/Produktkombination gibt, wird der Preis mit dem kleinsten Wert herangezogen.',Updated=TO_TIMESTAMP('2022-05-27 11:20:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543277 AND AD_Language='en_US'
;

-- 2022-05-27T08:20:28.458Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543277,'en_US') 
;

-- 2022-05-27T08:22:11.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580944,0,TO_TIMESTAMP('2022-05-27 11:22:11','YYYY-MM-DD HH24:MI:SS'),100,'Wenn gesetzt und der Datensatz (z.B. Auftragszeile), zu dem ein Preis bestimmt wird hat eine Packvorschrift oder Merkmale, dann wird dieser Preis nur bei einer Übereinstimmung herangezogen. Wenn der Datensatz kein Merkmal bzw keine Packvorschrift hat, dann werden diese jeweils in den Datensatz eingesetzt.','D','Y','Merkmal und Packvorschrift','Merkmal und Packvorschrift',TO_TIMESTAMP('2022-05-27 11:22:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-27T08:22:11.239Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580944 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-05-27T08:22:29.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=580944, Description='Wenn gesetzt und der Datensatz (z.B. Auftragszeile), zu dem ein Preis bestimmt wird hat eine Packvorschrift oder Merkmale, dann wird dieser Preis nur bei einer Übereinstimmung herangezogen. Wenn der Datensatz kein Merkmal bzw keine Packvorschrift hat, dann werden diese jeweils in den Datensatz eingesetzt.', Help=NULL, Name='Merkmal und Packvorschrift',Updated=TO_TIMESTAMP('2022-05-27 11:22:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557830
;

-- 2022-05-27T08:22:29.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580944) 
;

-- 2022-05-27T08:22:29.773Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=557830
;

-- 2022-05-27T08:22:29.786Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(557830)
;

-- 2022-05-27T08:26:34.998Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Description='Bei einer TU-Maßeinheit wird die Gebindekapazität dieser Packvorschrift zur Umrechnung benutzt.',Updated=TO_TIMESTAMP('2022-05-27 11:26:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1001085
;

-- 2022-05-27T08:26:35.007Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Packvorschrift', Description='Bei einer TU-Maßeinheit wird die Gebindekapazität dieser Packvorschrift zur Umrechnung benutzt.', Help=NULL WHERE AD_Element_ID=1001085
;

-- 2022-05-27T08:26:35.007Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Packvorschrift', Description='Bei einer TU-Maßeinheit wird die Gebindekapazität dieser Packvorschrift zur Umrechnung benutzt.', Help=NULL WHERE AD_Element_ID=1001085 AND IsCentrallyMaintained='Y'
;

-- 2022-05-27T08:26:35.011Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Packvorschrift', Description='Bei einer TU-Maßeinheit wird die Gebindekapazität dieser Packvorschrift zur Umrechnung benutzt.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1001085) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1001085)
;

-- 2022-05-27T08:26:35.027Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Packvorschrift', Description='Bei einer TU-Maßeinheit wird die Gebindekapazität dieser Packvorschrift zur Umrechnung benutzt.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 1001085
;

-- 2022-05-27T08:26:35.029Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Packvorschrift', Description='Bei einer TU-Maßeinheit wird die Gebindekapazität dieser Packvorschrift zur Umrechnung benutzt.', Help=NULL WHERE AD_Element_ID = 1001085
;

-- 2022-05-27T08:26:35.029Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Packvorschrift', Description = 'Bei einer TU-Maßeinheit wird die Gebindekapazität dieser Packvorschrift zur Umrechnung benutzt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1001085
;

-- 2022-05-27T08:26:43.131Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Bei einer TU-Maßeinheit wird die Gebindekapazität dieser Packvorschrift zur Umrechnung benutzt.',Updated=TO_TIMESTAMP('2022-05-27 11:26:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1001085 AND AD_Language='en_US'
;

-- 2022-05-27T08:26:43.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1001085,'en_US') 
;

-- 2022-05-27T08:26:45.494Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Bei einer TU-Maßeinheit wird die Gebindekapazität dieser Packvorschrift zur Umrechnung benutzt.',Updated=TO_TIMESTAMP('2022-05-27 11:26:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1001085 AND AD_Language='de_DE'
;

-- 2022-05-27T08:26:45.495Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1001085,'de_DE') 
;

-- 2022-05-27T08:26:45.504Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1001085,'de_DE') 
;

-- 2022-05-27T08:26:45.505Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Packvorschrift', Description='Bei einer TU-Maßeinheit wird die Gebindekapazität dieser Packvorschrift zur Umrechnung benutzt.', Help=NULL WHERE AD_Element_ID=1001085
;

-- 2022-05-27T08:26:45.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Packvorschrift', Description='Bei einer TU-Maßeinheit wird die Gebindekapazität dieser Packvorschrift zur Umrechnung benutzt.', Help=NULL WHERE AD_Element_ID=1001085 AND IsCentrallyMaintained='Y'
;

-- 2022-05-27T08:26:45.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Packvorschrift', Description='Bei einer TU-Maßeinheit wird die Gebindekapazität dieser Packvorschrift zur Umrechnung benutzt.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1001085) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1001085)
;

-- 2022-05-27T08:26:45.520Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Packvorschrift', Description='Bei einer TU-Maßeinheit wird die Gebindekapazität dieser Packvorschrift zur Umrechnung benutzt.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 1001085
;

-- 2022-05-27T08:26:45.521Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Packvorschrift', Description='Bei einer TU-Maßeinheit wird die Gebindekapazität dieser Packvorschrift zur Umrechnung benutzt.', Help=NULL WHERE AD_Element_ID = 1001085
;

-- 2022-05-27T08:26:45.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Packvorschrift', Description = 'Bei einer TU-Maßeinheit wird die Gebindekapazität dieser Packvorschrift zur Umrechnung benutzt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1001085
;

-- 2022-05-27T08:26:47.883Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Bei einer TU-Maßeinheit wird die Gebindekapazität dieser Packvorschrift zur Umrechnung benutzt.',Updated=TO_TIMESTAMP('2022-05-27 11:26:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1001085 AND AD_Language='de_CH'
;

-- 2022-05-27T08:26:47.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1001085,'de_CH') 
;

-- 2022-05-27T08:26:50.504Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Description='Bei einer TU-Maßeinheit wird die Gebindekapazität dieser Packvorschrift zur Umrechnung benutzt.',Updated=TO_TIMESTAMP('2022-05-27 11:26:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1001085 AND AD_Language='nl_NL'
;

-- 2022-05-27T08:26:50.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1001085,'nl_NL') 
;

-- 2022-05-27T08:28:25.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580945,0,TO_TIMESTAMP('2022-05-27 11:28:25','YYYY-MM-DD HH24:MI:SS'),100,'Gibt an, ob die Preiseinheit eine TU-Maßeinheit ist','D','Y','Preis pro TU','Preis pro TU',TO_TIMESTAMP('2022-05-27 11:28:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-27T08:28:25.571Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580945 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-05-27T08:28:34.329Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=580945, Description='Gibt an, ob die Preiseinheit eine TU-Maßeinheit ist', Help=NULL, Name='Preis pro TU',Updated=TO_TIMESTAMP('2022-05-27 11:28:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557836
;

-- 2022-05-27T08:28:34.331Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580945) 
;

-- 2022-05-27T08:28:34.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=557836
;


-- 2022-05-27T08:28:34.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(557836)
;

-- 2022-05-27T08:30:01.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2022-05-27 11:30:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556080
;

-- 2022-05-27T08:30:04.798Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_productprice','IsHUPrice','CHAR(1)',null,'N')
;

-- 2022-05-27T08:30:05.284Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE M_ProductPrice SET IsHUPrice='N' WHERE IsHUPrice IS NULL
;

