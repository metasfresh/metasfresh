-- 2018-07-31T20:13:29.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,501661,565243,0,294,0,TO_TIMESTAMP('2018-07-31 20:13:29','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Schlusstext',540,170,0,1,1,TO_TIMESTAMP('2018-07-31 20:13:29','YYYY-MM-DD HH24:MI:SS'),100)
;

UPDATE  ad_field set isdisplayed='Y' where (ad_tab_id, ad_column_id)=(294, 501661);

-- 2018-07-31T20:13:29.555
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=565243 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
--;

-- 2018-07-31T20:14:53.464
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,
(select AD_Field_ID from ad_field where (ad_tab_id, ad_column_id)=(294, 501661)),
0,294,552492,540961,'F',TO_TIMESTAMP('2018-07-31 20:14:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','Schlusstext',300,0,0,TO_TIMESTAMP('2018-07-31 20:14:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-31T20:15:20.021
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,3429,0,294,552493,540961,'F',TO_TIMESTAMP('2018-07-31 20:15:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','Beschreibung',290,0,0,TO_TIMESTAMP('2018-07-31 20:15:19','YYYY-MM-DD HH24:MI:SS'),100)
;

