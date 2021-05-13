-- 2021-05-12T14:44:11.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573839,645460,0,543837,TO_TIMESTAMP('2021-05-12 17:44:11','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, was passieren soll, wenn ein Shopware-Kunde schon als Geschäftspartner in metasfresh angelegt ist.',255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Wenn Gesch.-Partner ex.',TO_TIMESTAMP('2021-05-12 17:44:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-12T14:44:11.934Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=645460 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-12T14:44:11.942Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579145) 
;

-- 2021-05-12T14:44:11.947Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=645460
;

-- 2021-05-12T14:44:11.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(645460)
;

-- 2021-05-12T14:44:12.074Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573840,645461,0,543837,TO_TIMESTAMP('2021-05-12 17:44:11','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, was passieren soll, wenn ein Shopware-Kunde noch nicht als Geschäftspartner in metasfresh angelegt ist.',255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Wenn Gesch.-Partner nicht ex.',TO_TIMESTAMP('2021-05-12 17:44:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-12T14:44:12.075Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=645461 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-12T14:44:12.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579146) 
;

-- 2021-05-12T14:44:12.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=645461
;

-- 2021-05-12T14:44:12.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(645461)
;

-- 2021-05-12T14:44:12.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573841,645462,0,543837,TO_TIMESTAMP('2021-05-12 17:44:12','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden schon in metasfresh angelegt ist.',255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Wenn Adresse ex.',TO_TIMESTAMP('2021-05-12 17:44:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-12T14:44:12.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=645462 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-12T14:44:12.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579147) 
;

-- 2021-05-12T14:44:12.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=645462
;

-- 2021-05-12T14:44:12.204Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(645462)
;

-- 2021-05-12T14:44:12.356Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573842,645463,0,543837,TO_TIMESTAMP('2021-05-12 17:44:12','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden noch nicht in metasfresh angelegt ist.',255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Wenn Adr. nicht ex.',TO_TIMESTAMP('2021-05-12 17:44:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-12T14:44:12.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=645463 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-12T14:44:12.358Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579148) 
;

-- 2021-05-12T14:44:12.360Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=645463
;

-- 2021-05-12T14:44:12.361Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(645463)
;

-- 2021-05-12T14:44:44.516Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543773,545819,TO_TIMESTAMP('2021-05-12 17:44:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','sync advise',30,TO_TIMESTAMP('2021-05-12 17:44:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-12T14:44:50.073Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2021-05-12 17:44:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=545682
;

-- 2021-05-12T14:44:54.650Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET SeqNo=20,Updated=TO_TIMESTAMP('2021-05-12 17:44:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=545819
;

-- 2021-05-12T14:45:28.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy,WidgetSize) VALUES (0,645460,0,543837,584752,545819,'F',TO_TIMESTAMP('2021-05-12 17:45:27','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, was passieren soll, wenn ein Shopware-Kunde schon als Geschäftspartner in metasfresh angelegt ist.','Y','N','N','Y','N','N','N',0,'Wenn Gesch.-Partner ex.',10,0,0,TO_TIMESTAMP('2021-05-12 17:45:27','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- 2021-05-12T14:46:43.056Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,645461,0,543837,584753,545819,'F',TO_TIMESTAMP('2021-05-12 17:46:42','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, was passieren soll, wenn ein Shopware-Kunde noch nicht als Geschäftspartner in metasfresh angelegt ist.','Y','N','N','Y','N','N','N',0,'Wenn Gesch.-Partner nicht ex.',20,0,0,TO_TIMESTAMP('2021-05-12 17:46:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-12T14:46:49.353Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,645462,0,543837,584754,545819,'F',TO_TIMESTAMP('2021-05-12 17:46:49','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden schon in metasfresh angelegt ist.','Y','N','N','Y','N','N','N',0,'Wenn Adresse ex.',30,0,0,TO_TIMESTAMP('2021-05-12 17:46:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-12T14:46:55.158Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,645463,0,543837,584755,545819,'F',TO_TIMESTAMP('2021-05-12 17:46:54','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden noch nicht in metasfresh angelegt ist.','Y','N','N','Y','N','N','N',0,'Wenn Adr. nicht ex.',40,0,0,TO_TIMESTAMP('2021-05-12 17:46:54','YYYY-MM-DD HH24:MI:SS'),100)
;

