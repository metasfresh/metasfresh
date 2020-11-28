-- 2020-03-02T11:02:58.779Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,ColumnDisplayLength,IncludedTabHeight,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,Name,AD_Org_ID,EntityType) VALUES (507,'Y',0,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2020-03-02 13:02:58','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,TO_TIMESTAMP('2020-03-02 13:02:58','YYYY-MM-DD HH24:MI:SS'),100,598436,'Y',580,580,1,1,570076,'Auszugsbetrag Haben',0,'D')
;

-- 2020-03-02T11:02:58.787Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=598436 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-03-02T11:02:58.819Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577575) 
;

-- 2020-03-02T11:02:58.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=598436
;

-- 2020-03-02T11:02:58.842Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(598436)
;

-- 2020-03-02T11:03:05.372Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,ColumnDisplayLength,IncludedTabHeight,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,Name,AD_Org_ID,EntityType) VALUES (507,'Y',0,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2020-03-02 13:03:05','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,TO_TIMESTAMP('2020-03-02 13:03:05','YYYY-MM-DD HH24:MI:SS'),100,598437,'Y',590,590,1,1,570075,'Auszugsbetrag Soll',0,'D')
;

-- 2020-03-02T11:03:05.374Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=598437 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-03-02T11:03:05.375Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577574) 
;

-- 2020-03-02T11:03:05.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=598437
;

-- 2020-03-02T11:03:05.390Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(598437)
;

-- 2020-03-02T11:10:24.603Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Client_ID,CreatedBy,SeqNo,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Org_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,IsAllowFiltering,MultiLine_LinesCount,IsMultiLine,Name,AD_Field_ID,AD_Tab_ID,Created,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,Updated) VALUES (100,566562,0,100,580,0,'N',0,0,542669,'F','N',0,'N','Auszugsbetrag Soll',598437,507,TO_TIMESTAMP('2020-03-02 13:10:24','YYYY-MM-DD HH24:MI:SS'),'Y','Y','Y','N',TO_TIMESTAMP('2020-03-02 13:10:24','YYYY-MM-DD HH24:MI:SS'))
;

-- 2020-03-02T11:11:06.099Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Client_ID,CreatedBy,SeqNo,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Org_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,IsAllowFiltering,MultiLine_LinesCount,IsMultiLine,Name,AD_Field_ID,AD_Tab_ID,Created,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,Updated) VALUES (100,566563,0,100,590,0,'N',0,0,542669,'F','N',0,'N','Auszugsbetrag Haben',598436,507,TO_TIMESTAMP('2020-03-02 13:11:05','YYYY-MM-DD HH24:MI:SS'),'Y','Y','Y','N',TO_TIMESTAMP('2020-03-02 13:11:05','YYYY-MM-DD HH24:MI:SS'))
;

