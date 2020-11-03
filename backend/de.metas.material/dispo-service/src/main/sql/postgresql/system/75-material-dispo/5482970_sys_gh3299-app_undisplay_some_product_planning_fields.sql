-- 2018-01-17T06:21:29.686
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2018-01-17 06:21:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53534
;

-- 2018-01-17T06:24:46.671
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2018-01-17 06:24:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554084
;

-- 2018-01-17T06:27:22.189
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543769,0,TO_TIMESTAMP('2018-01-17 06:27:22','YYYY-MM-DD HH24:MI:SS'),100,'This field currently hasn''t any influence on the production','de.metas.material.dispo','Y','Eingekauft (currently not implemented!)','Eingekauft (currently not implemented!)',TO_TIMESTAMP('2018-01-17 06:27:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T06:27:22.192
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543769 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-01-17T06:27:35.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=543769, Description='This field currently hasn''t any influence on the production', Help=NULL, Name='Eingekauft (currently not implemented!)',Updated=TO_TIMESTAMP('2018-01-17 06:27:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554084
;

-- 2018-01-17T06:27:35.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543769,NULL) 
;

-- 2018-01-17T06:28:32.849
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET Help='Falls ja, dann wird bei der Bestellkontrolle für das jeweils hinterlegte Lager zur jeweiligen Auftragsposition ein Eintrag erstellt',Updated=TO_TIMESTAMP('2018-01-17 06:28:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552944
;

-- 2018-01-17T06:28:32.852
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Wird gehandelt', Description='Legt fest, ob mit dem bestreffenden Produkt gehandelt wird. ', Help='Falls ja, dann wird bei der Bestellkontrolle für das jeweils hinterlegte Lager zur jeweiligen Auftragsposition ein Eintrag erstellt' WHERE AD_Column_ID=552944
;

-- 2018-01-17T06:51:17.957
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET Help='Falls ja, dann wird bei der Bestellkontrolle für das jeweils hinterlegte Lager zur jeweiligen Auftragsposition ein Eintrag erstellt.
Statt wird gehandelt kann zu diesem Zweck auch "wird Produziert" = Ja gesetzt werden.', Name='Wird gehandelt (Bestellkontrolle)', PrintName='Wird gehandelt (Bestellkontrolle)',Updated=TO_TIMESTAMP('2018-01-17 06:51:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542934
;

-- 2018-01-17T06:51:17.961
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsTraded', Name='Wird gehandelt (Bestellkontrolle)', Description='Legt fest, ob mit dem bestreffenden Produkt gehandelt wird. ', Help='Falls ja, dann wird bei der Bestellkontrolle für das jeweils hinterlegte Lager zur jeweiligen Auftragsposition ein Eintrag erstellt.
Statt wird gehandelt kann zu diesem Zweck auch "wird Produziert" = Ja gesetzt werden.' WHERE AD_Element_ID=542934
;

-- 2018-01-17T06:51:17.973
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsTraded', Name='Wird gehandelt (Bestellkontrolle)', Description='Legt fest, ob mit dem bestreffenden Produkt gehandelt wird. ', Help='Falls ja, dann wird bei der Bestellkontrolle für das jeweils hinterlegte Lager zur jeweiligen Auftragsposition ein Eintrag erstellt.
Statt wird gehandelt kann zu diesem Zweck auch "wird Produziert" = Ja gesetzt werden.', AD_Element_ID=542934 WHERE UPPER(ColumnName)='ISTRADED' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2018-01-17T06:51:17.975
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsTraded', Name='Wird gehandelt (Bestellkontrolle)', Description='Legt fest, ob mit dem bestreffenden Produkt gehandelt wird. ', Help='Falls ja, dann wird bei der Bestellkontrolle für das jeweils hinterlegte Lager zur jeweiligen Auftragsposition ein Eintrag erstellt.
Statt wird gehandelt kann zu diesem Zweck auch "wird Produziert" = Ja gesetzt werden.' WHERE AD_Element_ID=542934 AND IsCentrallyMaintained='Y'
;

-- 2018-01-17T06:51:17.976
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Wird gehandelt (Bestellkontrolle)', Description='Legt fest, ob mit dem bestreffenden Produkt gehandelt wird. ', Help='Falls ja, dann wird bei der Bestellkontrolle für das jeweils hinterlegte Lager zur jeweiligen Auftragsposition ein Eintrag erstellt.
Statt wird gehandelt kann zu diesem Zweck auch "wird Produziert" = Ja gesetzt werden.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=542934) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 542934)
;

-- 2018-01-17T06:51:17.997
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Wird gehandelt (Bestellkontrolle)', Name='Wird gehandelt (Bestellkontrolle)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=542934)
;

-- 2018-01-17T06:53:37.012
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET IsActive='N',Updated=TO_TIMESTAMP('2018-01-17 06:53:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=53274
;

-- 2018-01-17T06:55:21.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543770,0,TO_TIMESTAMP('2018-01-17 06:55:21','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','Order Policy (not displayed, curently there is just LFL)','Order Policy (not displayed, curently there is just LFL)',TO_TIMESTAMP('2018-01-17 06:55:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-01-17T06:55:21.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543770 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-01-17T06:55:33.238
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=543770, Description=NULL, Help=NULL, IsDisplayed='N', IsDisplayedGrid='N', Name='Order Policy (not displayed, curently there is just LFL)',Updated=TO_TIMESTAMP('2018-01-17 06:55:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=53525
;

-- 2018-01-17T06:55:33.239
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543770,NULL) 
;

