-- 2020-02-11T20:54:24.794Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,597925,0,542297,543492,566337,'F',TO_TIMESTAMP('2020-02-11 21:54:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Effort_UOM_ID',30,0,0,TO_TIMESTAMP('2020-02-11 21:54:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-11T20:54:45.519Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2020-02-11 21:54:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566337
;

-- 2020-02-11T20:54:45.523Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2020-02-11 21:54:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566318
;

-- 2020-02-11T20:54:45.527Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2020-02-11 21:54:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566334
;

-- 2020-02-11T20:54:45.530Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2020-02-11 21:54:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566311
;

-- 2020-02-11T20:56:40.064Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET AD_UI_Column_ID=542289, SeqNo=30,Updated=TO_TIMESTAMP('2020-02-11 21:56:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=543492
;

-- 2020-02-11T20:57:09.539Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,542297,541801,TO_TIMESTAMP('2020-02-11 21:57:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2020-02-11 21:57:09','YYYY-MM-DD HH24:MI:SS'),100,'description')
;

-- 2020-02-11T20:57:09.540Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541801 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2020-02-11T20:57:18.169Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542292,541801,TO_TIMESTAMP('2020-02-11 21:57:18','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-02-11 21:57:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-11T20:57:31.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542292,543497,TO_TIMESTAMP('2020-02-11 21:57:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','description',10,TO_TIMESTAMP('2020-02-11 21:57:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-11T20:58:00.085Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,597930,0,542297,543497,566338,'F',TO_TIMESTAMP('2020-02-11 21:57:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Description',10,0,0,TO_TIMESTAMP('2020-02-11 21:57:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-11T20:58:10.705Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2020-02-11 21:58:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569985
;

-- 2020-02-11T20:58:12.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('s_budget_issue','Description','VARCHAR(1024)',null,null)
;

-- 2020-02-11T20:58:12.756Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('s_budget_issue','Description',null,'NULL',null)
;

-- 2020-02-11T20:59:28.214Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=40,Updated=TO_TIMESTAMP('2020-02-11 21:59:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=543490
;

-- 2020-02-11T20:59:32.004Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2020-02-11 21:59:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=543492
;

-- 2020-02-11T21:00:00.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsMultiLine='Y', MultiLine_LinesCount=3,Updated=TO_TIMESTAMP('2020-02-11 22:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566338
;

-- 2020-02-11T21:03:03.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,542295,541802,TO_TIMESTAMP('2020-02-11 22:03:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2020-02-11 22:03:03','YYYY-MM-DD HH24:MI:SS'),100,'descr')
;

-- 2020-02-11T21:03:03.241Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541802 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2020-02-11T21:03:09.276Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542293,541802,TO_TIMESTAMP('2020-02-11 22:03:09','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-02-11 22:03:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-11T21:03:18.439Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542293,543498,TO_TIMESTAMP('2020-02-11 22:03:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','descr',10,TO_TIMESTAMP('2020-02-11 22:03:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-11T21:03:38.423Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543498, SeqNo=10,Updated=TO_TIMESTAMP('2020-02-11 22:03:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566310
;

-- 2020-02-11T21:04:26.302Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543484, SeqNo=110,Updated=TO_TIMESTAMP('2020-02-11 22:04:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566336
;

-- 2020-02-11T21:04:40.491Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,542126,541803,TO_TIMESTAMP('2020-02-11 22:04:40','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2020-02-11 22:04:40','YYYY-MM-DD HH24:MI:SS'),100,'descr')
;

-- 2020-02-11T21:04:40.492Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_UI_Section_ID=541803 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2020-02-11T21:04:47.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,542294,541803,TO_TIMESTAMP('2020-02-11 22:04:47','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2020-02-11 22:04:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-11T21:04:57.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542294,543499,TO_TIMESTAMP('2020-02-11 22:04:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','descr',10,TO_TIMESTAMP('2020-02-11 22:04:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-11T21:05:22.515Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543499, SeqNo=10,Updated=TO_TIMESTAMP('2020-02-11 22:05:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=566301
;

-- 2020-02-11T21:05:26.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=543496
;

-- 2020-02-11T21:07:49.882Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-02-11 22:07:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=597904
;

-- 2020-02-11T21:07:52.832Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-02-11 22:07:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=597939
;

-- 2020-02-11T21:09:50.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2020-02-11 22:09:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=542297
;

