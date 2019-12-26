-- 2019-12-05T08:04:59.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,ColumnDisplayLength,IncludedTabHeight,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,Name,AD_Org_ID,EntityType) VALUES (330,'Y',10,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-12-05 10:04:59','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,TO_TIMESTAMP('2019-12-05 10:04:59','YYYY-MM-DD HH24:MI:SS'),100,593483,'Y',660,660,1,1,569691,'Externe Auftragsnummer',0,'D')
;

-- 2019-12-05T08:04:59.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=593483 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-12-05T08:04:59.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577406) 
;

-- 2019-12-05T08:04:59.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=593483
;

-- 2019-12-05T08:04:59.527Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(593483)
;

-- 2019-12-05T08:05:43.870Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Client_ID,CreatedBy,SeqNo,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Org_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,IsAllowFiltering,MultiLine_LinesCount,IsMultiLine,Name,AD_Field_ID,AD_Tab_ID,Created,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,Updated) VALUES (100,564582,0,100,630,0,'N',0,0,540060,'F','N',0,'N','Externe Auftragsnummer',593483,330,TO_TIMESTAMP('2019-12-05 10:05:43','YYYY-MM-DD HH24:MI:SS'),'Y','Y','Y','N',TO_TIMESTAMP('2019-12-05 10:05:43','YYYY-MM-DD HH24:MI:SS'))
;

-- 2019-12-05T08:07:21.311Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,DisplayLength,SortNo,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,Created,CreatedBy,IsReadOnly,ColumnDisplayLength,IncludedTabHeight,Updated,UpdatedBy,AD_Field_ID,IsDisplayedGrid,SeqNo,SeqNoGrid,SpanX,SpanY,AD_Column_ID,Name,AD_Org_ID,EntityType) VALUES (186,'Y',10,0,'N','N','N','N',0,'Y',TO_TIMESTAMP('2019-12-05 10:07:21','YYYY-MM-DD HH24:MI:SS'),100,'N',0,0,TO_TIMESTAMP('2019-12-05 10:07:21','YYYY-MM-DD HH24:MI:SS'),100,593484,'Y',690,670,1,1,569692,'External ID',0,'D')
;

-- 2019-12-05T08:07:21.314Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=593484 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-12-05T08:07:21.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543939) 
;

-- 2019-12-05T08:07:21.322Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=593484
;

-- 2019-12-05T08:07:21.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(593484)
;

-- 2019-12-05T08:07:44.223Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Client_ID,CreatedBy,SeqNo,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Org_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,IsAllowFiltering,MultiLine_LinesCount,IsMultiLine,Name,AD_Field_ID,AD_Tab_ID,Created,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,Updated) VALUES (100,564583,0,100,420,0,'N',0,0,540499,'F','N',0,'N','External ID',593484,186,TO_TIMESTAMP('2019-12-05 10:07:44','YYYY-MM-DD HH24:MI:SS'),'Y','Y','Y','N',TO_TIMESTAMP('2019-12-05 10:07:44','YYYY-MM-DD HH24:MI:SS'))
;


update AD_SysConfig set configurationlevel = 'O' WHERE Name='de.metas.payment.autoAssignToSalesOrderByExternalOrderId.enabled';
