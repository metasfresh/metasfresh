-- 2022-08-18T11:57:34.220Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=541568,Updated=TO_TIMESTAMP('2022-08-18 14:57:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542191
;

-- 2022-08-18T11:58:35.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584074,705306,0,546465,TO_TIMESTAMP('2022-08-18 14:58:34','YYYY-MM-DD HH24:MI:SS'),100,'Die unterste Stufe um Kosteninformation zu kumulieren',1,'D','Wenn Sie verschiedene Kosten pro Organisation (Lager) oder pro Charge/Los verwalten möchten dann stellen Sie sicher, dass Sie diese für jede Organistaion und jede Charge bzw. jedes Los definieren. Die Kostenrechnungsstufe ist pro Kontenrahmens definiert und kann durch die Produktkategorie oder  den Kontenrahmen überschrieben werden.','Y','N','N','N','N','N','N','N','Kostenrechnungsstufe',TO_TIMESTAMP('2022-08-18 14:58:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-18T11:58:35.082Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705306 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-18T11:58:35.089Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2816) 
;

-- 2022-08-18T11:58:35.112Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705306
;

-- 2022-08-18T11:58:35.120Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(705306)
;

-- 2022-08-18T11:59:43.188Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705306,0,546465,612108,549559,'F',TO_TIMESTAMP('2022-08-18 14:59:43','YYYY-MM-DD HH24:MI:SS'),100,'Die unterste Stufe um Kosteninformation zu kumulieren','Wenn Sie verschiedene Kosten pro Organisation (Lager) oder pro Charge/Los verwalten möchten dann stellen Sie sicher, dass Sie diese für jede Organistaion und jede Charge bzw. jedes Los definieren. Die Kostenrechnungsstufe ist pro Kontenrahmens definiert und kann durch die Produktkategorie oder  den Kontenrahmen überschrieben werden.','Y','N','Y','N','N','Kostenrechnungsstufe',40,0,0,TO_TIMESTAMP('2022-08-18 14:59:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-18T11:59:58.821Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-08-18 14:59:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=702160
;

-- 2022-08-18T12:00:01.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-08-18 15:00:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=702161
;

-- 2022-08-18T12:00:04.481Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-08-18 15:00:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=702162
;

-- 2022-08-18T12:00:06.037Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-08-18 15:00:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=702163
;

-- 2022-08-18T12:00:08.246Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-08-18 15:00:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=705306
;

-- 2022-08-18T12:00:10.011Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-08-18 15:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=702839
;

-- 2022-08-18T12:00:11.396Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-08-18 15:00:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=702840
;

-- 2022-08-18T12:00:13.936Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-08-18 15:00:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=702156
;

