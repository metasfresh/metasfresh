

-- 2018-09-26T11:19:44.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,ColumnDisplayLength,IncludedTabHeight,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,Description,AD_Org_ID,Name,EntityType) VALUES (294,'N',0,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2018-09-26 11:19:44','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,TO_TIMESTAMP('2018-09-26 11:19:44','YYYY-MM-DD HH24:MI:SS'),100,569148,'Y',550,180,1,1,560875,'Memo Text',0,'C_BPartner_Memo','U')
;

-- 2018-09-26T11:19:44.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=569148 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-09-26T11:21:23.420
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,ColumnDisplayLength,IncludedTabHeight,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,AD_Org_ID,Name,EntityType) VALUES (294,'Y',0,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2018-09-26 11:21:23','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,TO_TIMESTAMP('2018-09-26 11:21:23','YYYY-MM-DD HH24:MI:SS'),100,569149,'Y',560,190,1,1,560876,0,'Rechnungspartner-Memo','D')
;

-- 2018-09-26T11:21:23.421
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=569149 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-09-26T11:21:39.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2018-09-26 11:21:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=569148
;

-- 2018-09-26T11:22:13.671
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,ColumnDisplayLength,IncludedTabHeight,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,AD_Org_ID,Name,EntityType) VALUES (294,'N',0,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2018-09-26 11:22:13','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,TO_TIMESTAMP('2018-09-26 11:22:13','YYYY-MM-DD HH24:MI:SS'),100,569150,'N',570,200,1,1,560877,0,'Lieferempfänger-Memo','D')
;

-- 2018-09-26T11:22:13.673
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=569150 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-09-26T11:22:38.436
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2018-09-26 11:22:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=569148
;

-- 2018-09-26T11:22:50.893
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2018-09-26 11:22:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=569149
;

-- 2018-09-26T11:23:29.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (Updated,AD_Client_ID,UpdatedBy,Created,CreatedBy,IsActive,AD_UI_ElementField_ID,AD_UI_Element_ID,AD_Field_ID,SeqNo,AD_Org_ID,Type,TooltipIconName) VALUES (TO_TIMESTAMP('2018-09-26 11:23:28','YYYY-MM-DD HH24:MI:SS'),0,100,TO_TIMESTAMP('2018-09-26 11:23:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',540287,541279,569148,30,0,'tooltip','text')
;

-- 2018-09-26T11:24:09.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (Updated,AD_Client_ID,UpdatedBy,Created,CreatedBy,IsActive,AD_UI_ElementField_ID,AD_UI_Element_ID,AD_Field_ID,SeqNo,AD_Org_ID,Type,TooltipIconName) VALUES (TO_TIMESTAMP('2018-09-26 11:24:09','YYYY-MM-DD HH24:MI:SS'),0,100,TO_TIMESTAMP('2018-09-26 11:24:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',540288,541280,569149,30,0,'tooltip','text')
;

-- 2018-09-26T11:24:39.177
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (Updated,AD_Client_ID,UpdatedBy,Created,CreatedBy,IsActive,AD_UI_ElementField_ID,AD_UI_Element_ID,AD_Field_ID,SeqNo,AD_Org_ID,Type,TooltipIconName) VALUES (TO_TIMESTAMP('2018-09-26 11:24:39','YYYY-MM-DD HH24:MI:SS'),0,100,TO_TIMESTAMP('2018-09-26 11:24:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',540289,547190,569150,30,0,'tooltip','text')
;

