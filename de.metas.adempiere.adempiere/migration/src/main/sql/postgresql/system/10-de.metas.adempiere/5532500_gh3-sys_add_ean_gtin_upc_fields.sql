-- 2019-09-30T11:04:42.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=603, ColumnName='UPC', Description='Produktidentifikation (Barcode) durch Universal Product Code oder European Article Number)', Help='Tragen Sie hier den Barcode für das Produkt in einer der Barcode-Codierungen (Codabar, Code 25, Code 39, Code 93, Code 128, UPC (A), UPC (E), EAN-13, EAN-8, ITF, ITF-14, ISBN, ISSN, JAN-13, JAN-8, POSTNET und FIM, MSI/Plessey, Pharmacode) ein.', Name='UPC/EAN',Updated=TO_TIMESTAMP('2019-09-30 13:04:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549977
;

-- 2019-09-30T11:04:42.842Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='UPC/EAN', Description='Produktidentifikation (Barcode) durch Universal Product Code oder European Article Number)', Help='Tragen Sie hier den Barcode für das Produkt in einer der Barcode-Codierungen (Codabar, Code 25, Code 39, Code 93, Code 128, UPC (A), UPC (E), EAN-13, EAN-8, ITF, ITF-14, ISBN, ISSN, JAN-13, JAN-8, POSTNET und FIM, MSI/Plessey, Pharmacode) ein.' WHERE AD_Column_ID=549977
;

-- 2019-09-30T11:04:42.881Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(603) 
;

-- 2019-09-30T11:05:24.085Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='UPC', PrintName='UPC',Updated=TO_TIMESTAMP('2019-09-30 13:05:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=603 AND AD_Language='de_CH'
;

-- 2019-09-30T11:05:24.098Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(603,'de_CH') 
;

-- 2019-09-30T11:05:29.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='UPC', PrintName='UPC',Updated=TO_TIMESTAMP('2019-09-30 13:05:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=603 AND AD_Language='en_US'
;

-- 2019-09-30T11:05:29.005Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(603,'en_US') 
;

-- 2019-09-30T11:05:33.898Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='UPC', PrintName='UPC',Updated=TO_TIMESTAMP('2019-09-30 13:05:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=603 AND AD_Language='de_DE'
;

-- 2019-09-30T11:05:33.902Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(603,'de_DE') 
;

-- 2019-09-30T11:05:33.908Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(603,'de_DE') 
;

-- 2019-09-30T11:05:33.909Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='UPC', Name='UPC', Description='Produktidentifikation (Barcode) durch Universal Product Code oder European Article Number)', Help='Tragen Sie hier den Barcode für das Produkt in einer der Barcode-Codierungen (Codabar, Code 25, Code 39, Code 93, Code 128, UPC (A), UPC (E), EAN-13, EAN-8, ITF, ITF-14, ISBN, ISSN, JAN-13, JAN-8, POSTNET und FIM, MSI/Plessey, Pharmacode) ein.' WHERE AD_Element_ID=603
;

-- 2019-09-30T11:05:33.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='UPC', Name='UPC', Description='Produktidentifikation (Barcode) durch Universal Product Code oder European Article Number)', Help='Tragen Sie hier den Barcode für das Produkt in einer der Barcode-Codierungen (Codabar, Code 25, Code 39, Code 93, Code 128, UPC (A), UPC (E), EAN-13, EAN-8, ITF, ITF-14, ISBN, ISSN, JAN-13, JAN-8, POSTNET und FIM, MSI/Plessey, Pharmacode) ein.', AD_Element_ID=603 WHERE UPPER(ColumnName)='UPC' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-09-30T11:05:33.912Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='UPC', Name='UPC', Description='Produktidentifikation (Barcode) durch Universal Product Code oder European Article Number)', Help='Tragen Sie hier den Barcode für das Produkt in einer der Barcode-Codierungen (Codabar, Code 25, Code 39, Code 93, Code 128, UPC (A), UPC (E), EAN-13, EAN-8, ITF, ITF-14, ISBN, ISSN, JAN-13, JAN-8, POSTNET und FIM, MSI/Plessey, Pharmacode) ein.' WHERE AD_Element_ID=603 AND IsCentrallyMaintained='Y'
;

-- 2019-09-30T11:05:33.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='UPC', Description='Produktidentifikation (Barcode) durch Universal Product Code oder European Article Number)', Help='Tragen Sie hier den Barcode für das Produkt in einer der Barcode-Codierungen (Codabar, Code 25, Code 39, Code 93, Code 128, UPC (A), UPC (E), EAN-13, EAN-8, ITF, ITF-14, ISBN, ISSN, JAN-13, JAN-8, POSTNET und FIM, MSI/Plessey, Pharmacode) ein.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=603) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 603)
;

-- 2019-09-30T11:05:33.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='UPC', Name='UPC' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=603)
;

-- 2019-09-30T11:05:33.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='UPC', Description='Produktidentifikation (Barcode) durch Universal Product Code oder European Article Number)', Help='Tragen Sie hier den Barcode für das Produkt in einer der Barcode-Codierungen (Codabar, Code 25, Code 39, Code 93, Code 128, UPC (A), UPC (E), EAN-13, EAN-8, ITF, ITF-14, ISBN, ISSN, JAN-13, JAN-8, POSTNET und FIM, MSI/Plessey, Pharmacode) ein.', CommitWarning = NULL WHERE AD_Element_ID = 603
;

-- 2019-09-30T11:05:33.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='UPC', Description='Produktidentifikation (Barcode) durch Universal Product Code oder European Article Number)', Help='Tragen Sie hier den Barcode für das Produkt in einer der Barcode-Codierungen (Codabar, Code 25, Code 39, Code 93, Code 128, UPC (A), UPC (E), EAN-13, EAN-8, ITF, ITF-14, ISBN, ISSN, JAN-13, JAN-8, POSTNET und FIM, MSI/Plessey, Pharmacode) ein.' WHERE AD_Element_ID = 603
;

-- 2019-09-30T11:05:33.930Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'UPC', Description = 'Produktidentifikation (Barcode) durch Universal Product Code oder European Article Number)', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 603
;

-- 2019-09-30T11:06:14.432Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568986,589269,0,540517,TO_TIMESTAMP('2019-09-30 13:06:14','YYYY-MM-DD HH24:MI:SS'),100,50,'de.metas.handlingunits','Y','Y','N','N','N','N','N','TU-EAN',TO_TIMESTAMP('2019-09-30 13:06:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-30T11:06:14.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589269 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-30T11:06:14.447Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577133) 
;

-- 2019-09-30T11:06:14.454Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589269
;

-- 2019-09-30T11:06:14.456Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589269)
;

-- 2019-09-30T11:06:14.534Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568987,589270,0,540517,TO_TIMESTAMP('2019-09-30 13:06:14','YYYY-MM-DD HH24:MI:SS'),100,50,'de.metas.handlingunits','Y','Y','N','N','N','N','N','GTIN',TO_TIMESTAMP('2019-09-30 13:06:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-30T11:06:14.535Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589270 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-30T11:06:14.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577135) 
;

-- 2019-09-30T11:06:14.542Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589270
;

-- 2019-09-30T11:06:14.543Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589270)
;

-- 2019-09-30T11:06:31.984Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=577137, Description=NULL, Help=NULL, Name='TU-UPC',Updated=TO_TIMESTAMP('2019-09-30 13:06:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553744
;

-- 2019-09-30T11:06:31.988Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577137) 
;

-- 2019-09-30T11:06:31.991Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=553744
;

-- 2019-09-30T11:06:31.991Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(553744)
;

-- 2019-09-30T11:08:15.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Description='Number', Name='UPC',Updated=TO_TIMESTAMP('2019-09-30 13:08:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000130
;

-- 2019-09-30T11:09:07.544Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589270,0,540517,1000019,563120,'F',TO_TIMESTAMP('2019-09-30 13:09:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'GTIN',160,0,0,TO_TIMESTAMP('2019-09-30 13:09:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-30T11:09:30.950Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589269,0,540517,1000019,563121,'F',TO_TIMESTAMP('2019-09-30 13:09:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'EAN_TU',18,0,0,TO_TIMESTAMP('2019-09-30 13:09:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-30T11:09:37.997Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=16,Updated=TO_TIMESTAMP('2019-09-30 13:09:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563120
;

-- 2019-09-30T11:10:27.266Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2019-09-30 13:10:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563121
;

-- 2019-09-30T11:10:27.271Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-09-30 13:10:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563120
;

-- 2019-09-30T11:10:27.275Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-09-30 13:10:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000130
;

-- 2019-09-30T11:10:27.282Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-09-30 13:10:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000122
;

-- 2019-09-30T11:10:27.287Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-09-30 13:10:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000124
;

-- 2019-09-30T11:10:27.291Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-09-30 13:10:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000125
;

-- 2019-09-30T11:10:27.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-09-30 13:10:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000126
;

-- 2019-09-30T11:10:27.297Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-09-30 13:10:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000128
;

-- 2019-09-30T11:10:27.300Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2019-09-30 13:10:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000129
;

-- 2019-09-30T11:10:27.304Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2019-09-30 13:10:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000127
;

-- 2019-09-30T11:10:27.308Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2019-09-30 13:10:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000121
;

-- 2019-09-30T11:10:27.312Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2019-09-30 13:10:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000119
;

-- 2019-09-30T11:10:46.105Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568983,589271,0,562,TO_TIMESTAMP('2019-09-30 13:10:45','YYYY-MM-DD HH24:MI:SS'),100,50,'D','Y','N','N','N','N','N','N','N','CU-EAN',TO_TIMESTAMP('2019-09-30 13:10:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-30T11:10:46.113Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589271 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-30T11:10:46.121Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577134) 
;

-- 2019-09-30T11:10:46.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589271
;

-- 2019-09-30T11:10:46.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589271)
;

-- 2019-09-30T11:11:27.987Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Name_ID=577136, Description=NULL, Help=NULL, Name='CU-UPC',Updated=TO_TIMESTAMP('2019-09-30 13:11:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=552669
;

-- 2019-09-30T11:11:27.990Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577136) 
;

-- 2019-09-30T11:11:27.992Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=552669
;

-- 2019-09-30T11:11:27.994Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(552669)
;

-- 2019-09-30T11:11:44.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='UPC_CU',Updated=TO_TIMESTAMP('2019-09-30 13:11:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000150
;

-- 2019-09-30T11:12:06.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589271,0,562,1000021,563122,'F',TO_TIMESTAMP('2019-09-30 13:12:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'EAN_CU',58,0,0,TO_TIMESTAMP('2019-09-30 13:12:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-30T11:12:22.708Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-09-30 13:12:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563122
;

-- 2019-09-30T11:12:22.712Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-09-30 13:12:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000150
;

-- 2019-09-30T11:12:22.717Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2019-09-30 13:12:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000155
;

-- 2019-09-30T11:12:22.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2019-09-30 13:12:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000152
;

-- 2019-09-30T11:12:22.725Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2019-09-30 13:12:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000147
;

-- 2019-09-30T11:12:22.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2019-09-30 13:12:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000161
;

-- 2019-09-30T11:12:22.731Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2019-09-30 13:12:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=1000144
;

-- 2019-09-30T11:13:01.425Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568980,589272,0,540663,TO_TIMESTAMP('2019-09-30 13:13:01','YYYY-MM-DD HH24:MI:SS'),100,50,'de.metas.esb.edi','Y','Y','N','N','N','N','N','TU-EAN',TO_TIMESTAMP('2019-09-30 13:13:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-30T11:13:01.426Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589272 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-30T11:13:01.431Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577133) 
;

-- 2019-09-30T11:13:01.433Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589272
;

-- 2019-09-30T11:13:01.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589272)
;

-- 2019-09-30T11:13:01.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568981,589273,0,540663,TO_TIMESTAMP('2019-09-30 13:13:01','YYYY-MM-DD HH24:MI:SS'),100,50,'de.metas.esb.edi','Y','Y','N','N','N','N','N','GTIN',TO_TIMESTAMP('2019-09-30 13:13:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-30T11:13:01.526Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589273 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-30T11:13:01.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577135) 
;

-- 2019-09-30T11:13:01.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589273
;

-- 2019-09-30T11:13:01.541Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589273)
;

-- 2019-09-30T11:13:01.627Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568984,589274,0,540663,TO_TIMESTAMP('2019-09-30 13:13:01','YYYY-MM-DD HH24:MI:SS'),100,50,'de.metas.esb.edi','Y','Y','N','N','N','N','N','CU-EAN',TO_TIMESTAMP('2019-09-30 13:13:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-30T11:13:01.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589274 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-30T11:13:01.633Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577134) 
;

-- 2019-09-30T11:13:01.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589274
;

-- 2019-09-30T11:13:01.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589274)
;

-- 2019-09-30T11:13:01.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568985,589275,0,540663,TO_TIMESTAMP('2019-09-30 13:13:01','YYYY-MM-DD HH24:MI:SS'),100,50,'de.metas.esb.edi','Y','Y','N','N','N','N','N','TU-UPC',TO_TIMESTAMP('2019-09-30 13:13:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-30T11:13:01.720Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=589275 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-30T11:13:01.725Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577137) 
;

-- 2019-09-30T11:13:01.727Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=589275
;

-- 2019-09-30T11:13:01.728Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(589275)
;

-- 2019-09-30T11:13:46.411Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='UPC_CU',Updated=TO_TIMESTAMP('2019-09-30 13:13:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549236
;

-- 2019-09-30T11:15:00.355Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589274,0,540663,541228,563123,'F',TO_TIMESTAMP('2019-09-30 13:15:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'EAN_CU',38,0,0,TO_TIMESTAMP('2019-09-30 13:15:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-30T11:19:04.755Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589273,0,540663,541228,563124,'F',TO_TIMESTAMP('2019-09-30 13:19:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'GTIN',36,0,0,TO_TIMESTAMP('2019-09-30 13:19:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-30T11:19:41.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589272,0,540663,541228,563125,'F',TO_TIMESTAMP('2019-09-30 13:19:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'EAN_TU',42,0,0,TO_TIMESTAMP('2019-09-30 13:19:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-30T11:20:03.338Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,589275,0,540663,541228,563126,'F',TO_TIMESTAMP('2019-09-30 13:20:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'UPC_TU',44,0,0,TO_TIMESTAMP('2019-09-30 13:20:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-30T11:20:36.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-09-30 13:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563124
;

-- 2019-09-30T11:20:36.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2019-09-30 13:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563123
;

-- 2019-09-30T11:20:36.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2019-09-30 13:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563125
;

-- 2019-09-30T11:20:36.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2019-09-30 13:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549236
;

-- 2019-09-30T11:20:36.876Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-09-30 13:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563126
;

-- 2019-09-30T11:20:36.884Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-09-30 13:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549237
;

-- 2019-09-30T11:20:36.888Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2019-09-30 13:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549238
;

-- 2019-09-30T11:20:36.892Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2019-09-30 13:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549239
;

-- 2019-09-30T11:20:36.896Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2019-09-30 13:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549240
;

-- 2019-09-30T11:20:36.900Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2019-09-30 13:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549241
;

-- 2019-09-30T11:20:36.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2019-09-30 13:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549242
;

-- 2019-09-30T11:20:36.909Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2019-09-30 13:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549243
;

-- 2019-09-30T11:20:36.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2019-09-30 13:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549244
;

-- 2019-09-30T11:20:36.917Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2019-09-30 13:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549245
;

-- 2019-09-30T11:20:36.920Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2019-09-30 13:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549246
;

-- 2019-09-30T11:20:36.924Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2019-09-30 13:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549232
;

-- 2019-09-30T11:20:36.927Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2019-09-30 13:20:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=549247
;

-- 2019-09-30T11:32:07.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2019-09-30 13:32:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563120
;

-- 2019-09-30T11:32:07.747Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-09-30 13:32:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563121
;

-- 2019-09-30T11:34:11.740Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('EDI_DesadvLine','ALTER TABLE public.EDI_DesadvLine ADD COLUMN EAN_CU VARCHAR(50)')
;

