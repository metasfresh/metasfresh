-- 2022-09-15T13:07:20.573Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584279,707307,0,543052,0,TO_TIMESTAMP('2022-09-15 16:07:19','YYYY-MM-DD HH24:MI:SS'),100,'Rechnungskandidaten, die nicht in Kraft sind, kommen für die Rechnungsstellung nicht in Frage. Normalerweise wird ein Rechnungskandidat außer Kraft gesetzt, wenn der Quellbeleg erneut geöffnet wird.',0,'D',0,'Y','Y','Y','N','N','N','Y','N','In Kraft',0,530,0,1,1,TO_TIMESTAMP('2022-09-15 16:07:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T13:07:20.579Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707307 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-09-15T13:07:20.606Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581435) 
;

-- 2022-09-15T13:07:20.615Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707307
;

-- 2022-09-15T13:07:20.620Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(707307)
;

-- 2022-09-15T13:07:44.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,707307,0,543052,613009,544365,'F',TO_TIMESTAMP('2022-09-15 16:07:44','YYYY-MM-DD HH24:MI:SS'),100,'Rechnungskandidaten, die nicht in Kraft sind, kommen für die Rechnungsstellung nicht in Frage. Normalerweise wird ein Rechnungskandidat außer Kraft gesetzt, wenn der Quellbeleg erneut geöffnet wird.','Y','N','N','Y','N','N','N',0,'In Kraft',60,0,0,TO_TIMESTAMP('2022-09-15 16:07:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-15T13:08:16.095Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=36,Updated=TO_TIMESTAMP('2022-09-15 16:08:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=613009
;

-- 2022-09-15T13:08:22.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=37,Updated=TO_TIMESTAMP('2022-09-15 16:08:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=573405
;

