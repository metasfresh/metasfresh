-- 2018-02-03T08:25:10.401
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540982,540630,TO_TIMESTAMP('2018-02-03 08:25:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-02-03 08:25:10','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-02-03T08:25:10.405
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540630 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-02-03T08:25:10.469
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540846,540630,TO_TIMESTAMP('2018-02-03 08:25:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-02-03 08:25:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-03T08:25:10.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540846,541429,TO_TIMESTAMP('2018-02-03 08:25:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-02-03 08:25:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-03T08:25:10.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,561328,0,540982,541429,550555,TO_TIMESTAMP('2018-02-03 08:25:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','GO URL',0,10,0,TO_TIMESTAMP('2018-02-03 08:25:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-03T08:25:10.638
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,561330,0,540982,541429,550556,TO_TIMESTAMP('2018-02-03 08:25:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Auth Password',0,20,0,TO_TIMESTAMP('2018-02-03 08:25:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-03T08:25:10.676
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,561329,0,540982,541429,550557,TO_TIMESTAMP('2018-02-03 08:25:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Auth Username',0,30,0,TO_TIMESTAMP('2018-02-03 08:25:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-03T08:25:10.710
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,561331,0,540982,541429,550558,TO_TIMESTAMP('2018-02-03 08:25:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Request Username',0,40,0,TO_TIMESTAMP('2018-02-03 08:25:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-02-03T08:25:10.744
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,561332,0,540982,541429,550559,TO_TIMESTAMP('2018-02-03 08:25:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Request Sender ID',0,50,0,TO_TIMESTAMP('2018-02-03 08:25:10','YYYY-MM-DD HH24:MI:SS'),100)
;

