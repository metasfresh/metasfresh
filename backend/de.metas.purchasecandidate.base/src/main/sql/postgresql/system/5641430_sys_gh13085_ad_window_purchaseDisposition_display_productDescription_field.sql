
-- 2022-05-31T07:02:28.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583196,697945,0,540894,TO_TIMESTAMP('2022-05-31 10:02:28','YYYY-MM-DD HH24:MI:SS'),100,'Produktbeschreibung',255,'de.metas.purchasecandidate','Y','N','N','N','N','N','N','N','Produktbeschreibung',TO_TIMESTAMP('2022-05-31 10:02:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:02:28.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=697945 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-31T07:02:28.693Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2651) 
;

-- 2022-05-31T07:02:28.738Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=697945
;

-- 2022-05-31T07:02:28.749Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(697945)
;

-- 2022-05-31T07:03:33.321Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540719,549184,TO_TIMESTAMP('2022-05-31 10:03:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','product',50,TO_TIMESTAMP('2022-05-31 10:03:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:03:54.946Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,697945,0,540894,608280,549184,'F',TO_TIMESTAMP('2022-05-31 10:03:54','YYYY-MM-DD HH24:MI:SS'),100,'Produktbeschreibung','Y','Y','N','Y','N','N','N',0,'Produktbeschreibung',10,0,0,TO_TIMESTAMP('2022-05-31 10:03:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-31T07:05:51.774Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='N',Updated=TO_TIMESTAMP('2022-05-31 10:05:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=608280
;

