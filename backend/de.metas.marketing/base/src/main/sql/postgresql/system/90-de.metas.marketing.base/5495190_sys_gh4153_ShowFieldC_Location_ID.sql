-- 2018-06-05T23:19:46.041
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560253,564465,0,541097,0,TO_TIMESTAMP('2018-06-05 23:19:45','YYYY-MM-DD HH24:MI:SS'),100,'Adresse oder Anschrift',0,'de.metas.marketing.base','Das Feld "Adresse" definiert die Adressangaben eines Standortes.',0,'Y','Y','Y','N','N','N','N','N','Anschrift',140,140,0,1,1,TO_TIMESTAMP('2018-06-05 23:19:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-06-05T23:19:46.057
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=564465 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-06-05T23:20:32.864
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,564465,0,541097,552157,541597,'F',TO_TIMESTAMP('2018-06-05 23:20:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','Anschrift',50,0,0,TO_TIMESTAMP('2018-06-05 23:20:32','YYYY-MM-DD HH24:MI:SS'),100)
;

