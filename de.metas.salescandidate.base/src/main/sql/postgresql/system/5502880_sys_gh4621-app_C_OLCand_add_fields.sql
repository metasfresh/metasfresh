-- 2018-10-09T09:01:00.557
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,Help,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Description,AD_Org_ID,Name,EntityType) VALUES (540282,'N',10,'N','N','N','N',0,'Y',TO_TIMESTAMP('2018-10-09 09:01:00','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2018-10-09 09:01:00','YYYY-MM-DD HH24:MI:SS'),100,'Die Steuerkategorie hilft, ähnliche Steuern zu gruppieren. Z.B. Verkaufssteuer oder Mehrwertsteuer.
',569303,'N',563249,'Steuerkategorie',0,'Steuerkategorie','de.metas.ordercandidate')
;

-- 2018-10-09T09:01:00.561
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=569303 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-10-09T09:01:00.645
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,AD_Column_ID,Description,AD_Org_ID,Name,EntityType) VALUES (540282,'N',1,'N','N','N','N',0,'Y',TO_TIMESTAMP('2018-10-09 09:01:00','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2018-10-09 09:01:00','YYYY-MM-DD HH24:MI:SS'),100,569304,'N',563250,'Zeigt an, ob das Zusammentstellen eines Datensatzes abgeschlossen ist.',0,'Bereit zur Verarbeitung','de.metas.ordercandidate')
;

-- 2018-10-09T09:01:00.646
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=569304 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-10-09T09:01:36.104
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=569304
;

-- 2018-10-09T09:01:36.113
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Field WHERE AD_Field_ID=569304
;

-- 2018-10-09T09:01:40.620
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=563250
;

-- 2018-10-09T09:01:40.626
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=563250
;

-- 2018-10-09T09:01:52.826
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2018-10-09 09:01:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550544
;

-- 2018-10-09T09:01:55.890
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2018-10-09 09:01:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550545
;

-- 2018-10-09T09:01:57.493
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2018-10-09 09:01:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550546
;

-- 2018-10-09T09:01:58.927
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2018-10-09 09:01:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554409
;

-- 2018-10-09T09:02:09.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2018-10-09 09:02:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554414
;

-- 2018-10-09T09:02:10.382
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2018-10-09 09:02:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554412
;

-- 2018-10-09T09:02:11.044
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2018-10-09 09:02:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551514
;

-- 2018-10-09T09:02:13.737
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2018-10-09 09:02:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551510
;

-- 2018-10-09T09:02:25.217
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsLazyLoading='Y',Updated=TO_TIMESTAMP('2018-10-09 09:02:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551517
;

-- 2018-10-09T09:18:55.817
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,AD_UI_ElementGroup_ID,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,AD_UI_ElementType,Name,IsAllowFiltering,IsMultiLine,MultiLine_LinesCount) VALUES (100,553910,569301,'N',0,TO_TIMESTAMP('2018-10-09 09:18:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',540,540962,TO_TIMESTAMP('2018-10-09 09:18:55','YYYY-MM-DD HH24:MI:SS'),0,'Y','N',0,'N',0,540282,'F','Rechnungsdatum','N','N',0)
;

-- 2018-10-09T09:19:00.621
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2018-10-09 09:19:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553910
;

-- 2018-10-09T09:19:16.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,AD_UI_ElementGroup_ID,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,AD_UI_ElementType,Name,IsAllowFiltering,IsMultiLine,MultiLine_LinesCount) VALUES (100,553911,569302,'Y',0,TO_TIMESTAMP('2018-10-09 09:19:16','YYYY-MM-DD HH24:MI:SS'),100,'Y',550,540962,TO_TIMESTAMP('2018-10-09 09:19:16','YYYY-MM-DD HH24:MI:SS'),0,'Y','N',0,'N',0,540282,'F','Rechnungs-Belegart','N','N',0)
;

-- 2018-10-09T09:20:20.959
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,AD_UI_ElementGroup_ID,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,AD_UI_ElementType,Name,IsAllowFiltering,IsMultiLine,MultiLine_LinesCount) VALUES (100,553912,569303,'Y',0,TO_TIMESTAMP('2018-10-09 09:20:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',560,540962,TO_TIMESTAMP('2018-10-09 09:20:20','YYYY-MM-DD HH24:MI:SS'),0,'Y','N',0,'N',0,540282,'F','Steuerkategorie','N','N',0)
;

-- 2018-10-09T09:20:42.860
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,AD_UI_ElementGroup_ID,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,AD_UI_ElementType,Name,IsAllowFiltering,IsMultiLine,MultiLine_LinesCount) VALUES (100,553913,549595,'Y',0,TO_TIMESTAMP('2018-10-09 09:20:42','YYYY-MM-DD HH24:MI:SS'),100,'Y',570,540962,TO_TIMESTAMP('2018-10-09 09:20:42','YYYY-MM-DD HH24:MI:SS'),0,'Y','N',0,'N',0,540282,'F','Fehlermeldung','N','N',0)
;

