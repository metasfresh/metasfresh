
-- 2018-09-12T08:46:09.383
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560993,568970,0,146,0,TO_TIMESTAMP('2018-09-12 08:46:09','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','Abw. Sequenznummer-Implementierung',170,170,0,1,1,TO_TIMESTAMP('2018-09-12 08:46:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-12T08:46:09.387
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=568970 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-09-12T08:47:15.536
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsAutoSequence@=Y & @IsTableID@=N', SeqNo=107,Updated=TO_TIMESTAMP('2018-09-12 08:47:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=568970
;

-- 2018-09-12T08:52:49.098
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,568970,0,146,540411,553798,'F',TO_TIMESTAMP('2018-09-12 08:52:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Abw. Sequenznummer-Implementierung',30,0,0,TO_TIMESTAMP('2018-09-12 08:52:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-12T09:10:53.068
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,544777,0,TO_TIMESTAMP('2018-09-12 09:10:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.document','Y','Die abweichende Sequenznummer-Implementierung, die der Nummernfolge {0} zugewiesen ist, ist auf den betreffenden Datensatz nicht anwendbar.','E',TO_TIMESTAMP('2018-09-12 09:10:52','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.document.CustomSequenceNoProviderNoApplicable')
;

-- 2018-09-12T09:10:53.071
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=544777 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2018-09-12T09:10:56.233
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-12 09:10:56','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Message_ID=544777 AND AD_Language='de_CH'
;

-- 2018-09-12T09:47:35.823
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-12 09:47:35','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',MsgText='The custom sequence number provider which is assigned to this sequence can''t be applied to the respective data record.' WHERE AD_Message_ID=544777 AND AD_Language='en_US'
;

-- 2018-09-12T09:48:03.212
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Message_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-12 09:48:03','YYYY-MM-DD HH24:MI:SS'),MsgText='The custom sequence number provider which is assigned to the sequence {0} can''t be applied to the respective data record.' WHERE AD_Message_ID=544777 AND AD_Language='en_US'
;

