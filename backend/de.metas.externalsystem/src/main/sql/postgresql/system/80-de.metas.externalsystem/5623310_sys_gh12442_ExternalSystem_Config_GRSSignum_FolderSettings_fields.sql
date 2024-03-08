-- 2022-01-27T12:51:57.036Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,579146,677949,0,544601,0,TO_TIMESTAMP('2022-01-27 14:51:56','YYYY-MM-DD HH24:MI:SS'),100,'Komma-getrennte Ordner-Strukturen, die innerhalb des Geschäftspartner-Ordners angelegt werden sollen. Beispiel: "Zertifikate, Korrespondenz/Allgemein, Korrespondenz/Entwicklungen".',0,'de.metas.externalsystem',0,'Y','Y','Y','N','N','N','N','N','Geschäftspartner-Unterordner',0,30,0,1,1,TO_TIMESTAMP('2022-01-27 14:51:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-27T12:51:57.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677949 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-27T12:51:57.048Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580525) 
;

-- 2022-01-27T12:51:57.059Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677949
;

-- 2022-01-27T12:51:57.065Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(677949)
;

-- 2022-01-27T12:52:45.451Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,579148,677951,0,544601,0,TO_TIMESTAMP('2022-01-27 14:52:45','YYYY-MM-DD HH24:MI:SS'),100,'Wenn angehakt, dann wird beim Export eines Geschäftspartners im Dateisystem ein Ordner mit Unterordnern für Dateianlagen zu diesem Geschäftspartner angelegt.',0,'de.metas.externalsystem',0,'Y','Y','Y','N','N','N','N','N','Partner-Ordner erst',0,40,0,1,1,TO_TIMESTAMP('2022-01-27 14:52:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-27T12:52:45.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677951 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-27T12:52:45.455Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580526) 
;

-- 2022-01-27T12:52:45.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677951
;

-- 2022-01-27T12:52:45.457Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(677951)
;

-- 2022-01-27T12:53:09.144Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,579145,677952,0,544601,0,TO_TIMESTAMP('2022-01-27 14:53:08','YYYY-MM-DD HH24:MI:SS'),100,'Gibt den Verzeichnispfad an, in dem alle Geschäftspartner-Ordner erstellt werden.',0,'de.metas.externalsystem',0,'Y','Y','Y','N','N','N','N','N','Basis-Pfad',0,50,0,1,1,TO_TIMESTAMP('2022-01-27 14:53:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-27T12:53:09.146Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677952 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-27T12:53:09.148Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580524) 
;

-- 2022-01-27T12:53:09.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677952
;

-- 2022-01-27T12:53:09.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(677952)
;

-- 2022-01-27T12:53:34.973Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,544512,547970,TO_TIMESTAMP('2022-01-27 14:53:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','dir',30,TO_TIMESTAMP('2022-01-27 14:53:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-27T12:55:24.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,677949,0,544601,600092,547970,'F',TO_TIMESTAMP('2022-01-27 14:55:24','YYYY-MM-DD HH24:MI:SS'),100,'Komma-getrennte Ordner-Strukturen, die innerhalb des Geschäftspartner-Ordners angelegt werden sollen. Beispiel: "Zertifikate, Korrespondenz/Allgemein, Korrespondenz/Entwicklungen".','Y','N','N','Y','N','N','N',0,'Geschäftspartner-Unterordner',10,0,0,TO_TIMESTAMP('2022-01-27 14:55:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-27T12:55:40.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,677952,0,544601,600093,547970,'F',TO_TIMESTAMP('2022-01-27 14:55:40','YYYY-MM-DD HH24:MI:SS'),100,'Gibt den Verzeichnispfad an, in dem alle Geschäftspartner-Ordner erstellt werden.','Y','N','N','Y','N','N','N',0,'Basis-Pfad',20,0,0,TO_TIMESTAMP('2022-01-27 14:55:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-27T12:56:07.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,677951,0,544601,600094,547724,'F',TO_TIMESTAMP('2022-01-27 14:56:07','YYYY-MM-DD HH24:MI:SS'),100,'Wenn angehakt, dann wird beim Export eines Geschäftspartners im Dateisystem ein Ordner mit Unterordnern für Dateianlagen zu diesem Geschäftspartner angelegt.','Y','N','N','Y','N','N','N',0,'Partner-Ordner erst',50,0,0,TO_TIMESTAMP('2022-01-27 14:56:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-27T13:51:35.953Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@BPartnerExportDirectories@!null',Updated=TO_TIMESTAMP('2022-01-27 15:51:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579145
;

-- 2022-01-27T14:08:44.184Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@IsCreateBPartnerFolders@=Y',Updated=TO_TIMESTAMP('2022-01-27 16:08:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579145
;

-- 2022-01-27T14:08:54.236Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@IsCreateBPartnerFolders@=Y',Updated=TO_TIMESTAMP('2022-01-27 16:08:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579146
;

-- 2022-01-27T14:13:56.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('externalsystem_config_grssignum','BPartnerExportDirectories','VARCHAR(255)',null,null)
;

-- 2022-01-27T14:14:06.483Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('externalsystem_config_grssignum','BasePathForExportDirectories','VARCHAR(255)',null,null)
;

