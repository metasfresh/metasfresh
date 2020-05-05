-- 2019-09-18T11:18:32.606Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568786,586116,0,220,TO_TIMESTAMP('2019-09-18 14:18:32','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Zuordnung Mindesthaltbarkeit',TO_TIMESTAMP('2019-09-18 14:18:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-18T11:18:32.631Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=586116 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-18T11:18:32.896Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577094) 
;

-- 2019-09-18T11:18:32.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=586116
;

-- 2019-09-18T11:18:32.987Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(586116)
;

-- 2019-09-18T11:19:12.231Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,586116,0,220,561825,540671,TO_TIMESTAMP('2019-09-18 14:19:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Zuordnung Mindesthaltbarkeit',120,0,0,TO_TIMESTAMP('2019-09-18 14:19:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-18T13:19:32.254Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2019-09-18 16:19:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561825
;

