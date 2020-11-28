-- 2019-08-02T18:54:21.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=541086, SeqNo=140,Updated=TO_TIMESTAMP('2019-08-02 20:54:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548111
;

-- 2019-08-02T18:54:59.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540057, SeqNo=60,Updated=TO_TIMESTAMP('2019-08-02 20:54:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548112
;

-- 2019-08-02T18:55:27.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=560326
;

-- 2019-08-02T18:55:49.338Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='Y',Updated=TO_TIMESTAMP('2019-08-02 20:55:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560337
;

-- 2019-08-02T18:57:11.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2019-08-02 20:57:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560336
;

-- 2019-08-02T18:57:16.662Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2019-08-02 20:57:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560337
;

-- 2019-08-02T18:58:40.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=150,Updated=TO_TIMESTAMP('2019-08-02 20:58:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548111
;

-- 2019-08-02T18:59:32.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,548190,0,540279,541086,560377,'F',TO_TIMESTAMP('2019-08-02 20:59:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'QtyToInvoice_Override',140,0,0,TO_TIMESTAMP('2019-08-02 20:59:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T19:00:20.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Abzurechnen abw. (Lagereinheit)', PrintName='Abzurechnen abw. (Lagereinheit)',Updated=TO_TIMESTAMP('2019-08-02 21:00:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1002360 AND AD_Language='de_DE'
;

-- 2019-08-02T19:00:20.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1002360,'de_DE') 
;

-- 2019-08-02T19:00:20.292Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(1002360,'de_DE') 
;

-- 2019-08-02T19:00:20.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName=NULL, Name='Abzurechnen abw. (Lagereinheit)', Description='Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.', Help='Für einen Rechnungslauf ist immer die "Zu berechn. Menge" maßgeblich. ggf. muss also nach einer Änderung dieses Wertes die nächste Aktualisierung abgewartet werden.' WHERE AD_Element_ID=1002360
;

-- 2019-08-02T19:00:20.295Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Abzurechnen abw. (Lagereinheit)', Description='Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.', Help='Für einen Rechnungslauf ist immer die "Zu berechn. Menge" maßgeblich. ggf. muss also nach einer Änderung dieses Wertes die nächste Aktualisierung abgewartet werden.' WHERE AD_Element_ID=1002360 AND IsCentrallyMaintained='Y'
;

-- 2019-08-02T19:00:20.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Abzurechnen abw. (Lagereinheit)', Description='Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.', Help='Für einen Rechnungslauf ist immer die "Zu berechn. Menge" maßgeblich. ggf. muss also nach einer Änderung dieses Wertes die nächste Aktualisierung abgewartet werden.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=1002360) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 1002360)
;

-- 2019-08-02T19:00:20.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Abzurechnen abw. (Lagereinheit)', Name='Abzurechnen abw. (Lagereinheit)' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=1002360)
;

-- 2019-08-02T19:00:20.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Abzurechnen abw. (Lagereinheit)', Description='Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.', Help='Für einen Rechnungslauf ist immer die "Zu berechn. Menge" maßgeblich. ggf. muss also nach einer Änderung dieses Wertes die nächste Aktualisierung abgewartet werden.', CommitWarning = NULL WHERE AD_Element_ID = 1002360
;

-- 2019-08-02T19:00:20.310Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Abzurechnen abw. (Lagereinheit)', Description='Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.', Help='Für einen Rechnungslauf ist immer die "Zu berechn. Menge" maßgeblich. ggf. muss also nach einer Änderung dieses Wertes die nächste Aktualisierung abgewartet werden.' WHERE AD_Element_ID = 1002360
;

-- 2019-08-02T19:00:20.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Abzurechnen abw. (Lagereinheit)', Description = 'Der Benutzer kann eine abweichende zu berechnende Menge angeben. Diese wird bei der nächsten Aktualisierung des Rechnungskandidaten berücksichtigt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 1002360
;

-- 2019-08-02T19:00:26.285Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Abzurechnen abw. (Lagereinheit)', PrintName='Abzurechnen abw. (Lagereinheit)',Updated=TO_TIMESTAMP('2019-08-02 21:00:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1002360 AND AD_Language='de_CH'
;

-- 2019-08-02T19:00:26.286Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1002360,'de_CH') 
;

-- 2019-08-02T19:02:17.145Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-08-02 21:02:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576974 AND AD_Language='de_CH'
;

-- 2019-08-02T19:02:17.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576974,'de_CH') 
;

-- 2019-08-02T19:02:19.737Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-08-02 21:02:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576974 AND AD_Language='de_DE'
;

-- 2019-08-02T19:02:19.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576974,'de_DE') 
;

-- 2019-08-02T19:02:19.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576974,'de_DE') 
;

-- 2019-08-02T19:03:06.155Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-08-02 21:03:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1251 AND AD_Language='de_CH'
;

-- 2019-08-02T19:03:06.156Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1251,'de_CH') 
;

-- 2019-08-02T19:03:16.754Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Qty to Invoice eff.( stock unit)', PrintName='Qty to invoice eff. (stock unit)',Updated=TO_TIMESTAMP('2019-08-02 21:03:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1251 AND AD_Language='en_US'
;

-- 2019-08-02T19:03:16.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1251,'en_US') 
;

-- 2019-08-02T19:05:00.759Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Stock unit', PrintName='Stock unit',Updated=TO_TIMESTAMP('2019-08-02 21:05:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576969 AND AD_Language='en_US'
;

-- 2019-08-02T19:05:00.760Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576969,'en_US') 
;

-- 2019-08-02T19:06:24.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Quantity (stock unit)', PrintName='Quantity (stock unit)',Updated=TO_TIMESTAMP('2019-08-02 21:06:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542270 AND AD_Language='en_US'
;

-- 2019-08-02T19:06:24.746Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542270,'en_US') 
;

-- 2019-08-02T19:07:14.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Invoiced (stock unit)', PrintName='Invoiced (stock unit)',Updated=TO_TIMESTAMP('2019-08-02 21:07:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576972 AND AD_Language='en_US'
;

-- 2019-08-02T19:07:14.695Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576972,'en_US') 
;

-- 2019-08-02T19:07:17.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-08-02 21:07:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576972 AND AD_Language='de_DE'
;

-- 2019-08-02T19:07:17.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576972,'de_DE') 
;

-- 2019-08-02T19:07:17.659Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576972,'de_DE') 
;

-- 2019-08-02T19:07:20.379Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-08-02 21:07:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576972 AND AD_Language='de_CH'
;

-- 2019-08-02T19:07:20.380Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576972,'de_CH') 
;

-- 2019-08-02T19:07:26.826Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-08-02 21:07:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576973 AND AD_Language='de_CH'
;

-- 2019-08-02T19:07:26.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576973,'de_CH') 
;

-- 2019-08-02T19:07:29.774Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-08-02 21:07:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576973 AND AD_Language='de_DE'
;

-- 2019-08-02T19:07:29.776Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576973,'de_DE') 
;

-- 2019-08-02T19:07:29.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(576973,'de_DE') 
;

-- 2019-08-02T19:07:46.273Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Delivered (stock unit)', PrintName='Delivered (stock unit)',Updated=TO_TIMESTAMP('2019-08-02 21:07:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576973 AND AD_Language='en_US'
;

-- 2019-08-02T19:07:46.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576973,'en_US') 
;

-- 2019-08-02T19:11:56.490Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='QtyDeliveredInUOM',Updated=TO_TIMESTAMP('2019-08-02 21:11:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560329
;

-- 2019-08-02T19:12:06.150Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='QtyToInvoiceInUOM_Calc',Updated=TO_TIMESTAMP('2019-08-02 21:12:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560337
;

-- 2019-08-02T19:14:53.909Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568541,582550,0,540279,TO_TIMESTAMP('2019-08-02 21:14:53','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.invoicecandidate','Y','N','N','N','N','N','N','N','Abzurechnen eff.',TO_TIMESTAMP('2019-08-02 21:14:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T19:14:53.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582550 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-08-02T19:14:53.917Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576983) 
;

-- 2019-08-02T19:14:53.921Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582550
;

-- 2019-08-02T19:14:53.924Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(582550)
;

-- 2019-08-02T19:15:32.486Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_Field_ID=582550,Updated=TO_TIMESTAMP('2019-08-02 21:15:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560337
;

-- 2019-08-02T19:15:47.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y', IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-08-02 21:15:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582550
;

-- 2019-08-02T19:22:58.973Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='To invoice override (stock unit)', PrintName='To invoice override (stock unit)',Updated=TO_TIMESTAMP('2019-08-02 21:22:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1002360 AND AD_Language='en_US'
;

-- 2019-08-02T19:22:58.975Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1002360,'en_US') 
;

-- 2019-08-02T19:24:29.190Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='QtyToInvoice',Updated=TO_TIMESTAMP('2019-08-02 21:24:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=548111
;

-- 2019-08-02T19:25:34.846Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='To invoice eff.( stock unit)', PrintName='To invoice eff. (stock unit)',Updated=TO_TIMESTAMP('2019-08-02 21:25:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=1251 AND AD_Language='en_US'
;

-- 2019-08-02T19:25:34.848Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(1251,'en_US') 
;

-- 2019-08-02T19:26:49.984Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Invoiced', PrintName='Invoiced',Updated=TO_TIMESTAMP('2019-08-02 21:26:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=576971 AND AD_Language='en_US'
;

-- 2019-08-02T19:26:49.986Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(576971,'en_US') 
;

-- 2019-08-02T19:28:01.574Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=548112
;

-- 2019-08-02T19:32:38.268Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,546898,0,540399,541081,TO_TIMESTAMP('2019-08-02 21:32:38','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,'widget',TO_TIMESTAMP('2019-08-02 21:32:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-02T19:33:50.345Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementField WHERE AD_UI_ElementField_ID=540399
;

-- 2019-08-02T19:39:05.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsReadOnly='N',Updated=TO_TIMESTAMP('2019-08-02 21:39:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=540665
;

-- 2019-08-02T19:39:32.292Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-08-02 21:39:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555482
;

-- 2019-08-02T19:39:35.463Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-08-02 21:39:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555483
;

-- 2019-08-02T19:39:37.167Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-08-02 21:39:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555484
;

-- 2019-08-02T19:39:38.914Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-08-02 21:39:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555485
;

-- 2019-08-02T19:39:41.341Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-08-02 21:39:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555488
;

-- 2019-08-02T19:39:42.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-08-02 21:39:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555489
;

-- 2019-08-02T19:39:45.443Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-08-02 21:39:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582504
;

-- 2019-08-02T19:39:50.703Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-08-02 21:39:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582505
;

-- 2019-08-02T19:39:53.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-08-02 21:39:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582506
;

-- 2019-08-02T19:39:56.589Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-08-02 21:39:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582508
;

-- 2019-08-02T19:40:01.210Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-08-02 21:40:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582499
;

-- 2019-08-02T19:40:02.778Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-08-02 21:40:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582500
;

-- 2019-08-02T19:40:05.580Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-08-02 21:40:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582501
;

-- 2019-08-02T19:40:08.106Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-08-02 21:40:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582502
;

-- 2019-08-02T19:40:09.991Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-08-02 21:40:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582503
;

