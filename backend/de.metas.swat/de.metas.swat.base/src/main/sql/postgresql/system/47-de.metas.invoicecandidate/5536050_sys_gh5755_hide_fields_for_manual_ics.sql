-- 2019-11-11T11:04:36.685Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='',Updated=TO_TIMESTAMP('2019-11-11 12:04:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=544966
;

-- 2019-11-11T13:00:53.506Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2019-11-11 14:00:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541010
;

-- 2019-11-11T13:01:46.608Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569216,591336,0,540279,0,TO_TIMESTAMP('2019-11-11 14:01:46','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','N','Externe Datensatz-Kopf-ID',1140,500,0,1,1,TO_TIMESTAMP('2019-11-11 14:01:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-11T13:01:46.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591336 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-11T13:01:46.643Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(575915) 
;

-- 2019-11-11T13:01:46.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591336
;

-- 2019-11-11T13:01:46.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591336)
;

-- 2019-11-11T13:02:03.634Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,569217,591337,0,540279,0,TO_TIMESTAMP('2019-11-11 14:02:03','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','N','Externe Datensatz-Zeilen-ID',1150,510,0,1,1,TO_TIMESTAMP('2019-11-11 14:02:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-11T13:02:03.635Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=591337 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-11-11T13:02:03.637Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(575914) 
;

-- 2019-11-11T13:02:03.640Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=591337
;

-- 2019-11-11T13:02:03.641Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(591337)
;

-- 2019-11-11T13:02:25.772Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET EntityType='de.metas.invoicecandidate',Updated=TO_TIMESTAMP('2019-11-11 14:02:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555715
;

-- 2019-11-11T13:03:02.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,591336,0,540279,540056,563853,'F',TO_TIMESTAMP('2019-11-11 14:03:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Externe Datensatz-Kopf-ID',22,0,0,TO_TIMESTAMP('2019-11-11 14:03:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-11T13:03:18.314Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,591337,0,540279,540056,563854,'F',TO_TIMESTAMP('2019-11-11 14:03:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'ExternalLineId',241030,0,0,TO_TIMESTAMP('2019-11-11 14:03:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-11T13:03:23.853Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET Name='ExternalHeaderId',Updated=TO_TIMESTAMP('2019-11-11 14:03:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563853
;

-- 2019-11-11T13:03:37.565Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-11-11 14:03:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=591336
;

-- 2019-11-11T13:03:38.917Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-11-11 14:03:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=591337
;

-- 2019-11-11T13:05:25.485Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsManual/''N''@=''N''',Updated=TO_TIMESTAMP('2019-11-11 14:05:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555481
;

-- 2019-11-11T13:05:43.161Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsManual/''N''@=''N''',Updated=TO_TIMESTAMP('2019-11-11 14:05:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548114
;

-- 2019-11-11T13:06:05.741Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsManual/''N''@=''N''',Updated=TO_TIMESTAMP('2019-11-11 14:06:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563184
;

-- 2019-11-11T13:06:13.950Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsManual/''N''@=''N''',Updated=TO_TIMESTAMP('2019-11-11 14:06:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582480
;

-- 2019-11-11T13:07:28.778Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsManual/''N''@=''N''',Updated=TO_TIMESTAMP('2019-11-11 14:07:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=546904
;

-- 2019-11-11T13:07:33.750Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsManual/''N''@=''N''',Updated=TO_TIMESTAMP('2019-11-11 14:07:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=546898
;

-- 2019-11-11T13:07:44.375Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsManual/''N''@=''N''',Updated=TO_TIMESTAMP('2019-11-11 14:07:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560421
;

-- 2019-11-11T13:08:09.911Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsManual/''N''@=''N''',Updated=TO_TIMESTAMP('2019-11-11 14:08:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555715
;

-- 2019-11-11T13:08:36.735Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsManual/''N''@=''N''',Updated=TO_TIMESTAMP('2019-11-11 14:08:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555409
;

-- 2019-11-11T13:09:00.286Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/Y@=N & @IsManual/''N''@=''N''',Updated=TO_TIMESTAMP('2019-11-11 14:09:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553212
;

-- 2019-11-11T13:09:21.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/''Y''@=''N'' & @IsManual/''N''@=''N''',Updated=TO_TIMESTAMP('2019-11-11 14:09:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554061
;

-- 2019-11-11T13:09:28.293Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/''Y''@=''N'' & @IsManual/''N''@=''N''',Updated=TO_TIMESTAMP('2019-11-11 14:09:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554060
;

-- 2019-11-11T13:09:38.219Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/''Y''@=''N'' & @IsManual/''N''@=''N''',Updated=TO_TIMESTAMP('2019-11-11 14:09:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553211
;

-- 2019-11-11T13:09:58.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/Y@=N & @IsManual/''N''@=''N''',Updated=TO_TIMESTAMP('2019-11-11 14:09:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553926
;

-- 2019-11-11T13:10:11.502Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsManual/''N''@=''N''',Updated=TO_TIMESTAMP('2019-11-11 14:10:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554865
;

-- 2019-11-11T13:10:20.898Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsManual/''N''@=''N''',Updated=TO_TIMESTAMP('2019-11-11 14:10:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555407
;

-- 2019-11-11T13:10:55.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsManual/''N''@=''N''',Updated=TO_TIMESTAMP('2019-11-11 14:10:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554131
;

-- 2019-11-11T13:13:05.783Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,591337,0,540279,540056,563855,'F',TO_TIMESTAMP('2019-11-11 14:13:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'ExternalLineId',24,0,0,TO_TIMESTAMP('2019-11-11 14:13:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-11-11T13:13:42.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-11-11 14:13:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551089
;

-- 2019-11-11T13:14:25.749Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/Y@=N & @IsManual/''N''@=''N''',Updated=TO_TIMESTAMP('2019-11-11 14:14:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554062
;

-- 2019-11-11T13:14:35.294Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/''Y''@=''N'' & @IsManual/''N''@=''N''',Updated=TO_TIMESTAMP('2019-11-11 14:14:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554062
;

-- 2019-11-11T13:15:11.970Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=563854
;

-- 2019-11-11T13:15:39.324Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/Y@=''N'' & @IsManual/''N''@=''N''',Updated=TO_TIMESTAMP('2019-11-11 14:15:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554132
;

-- 2019-11-11T13:15:43.593Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/''Y''@=''N'' & @IsManual/''N''@=''N''',Updated=TO_TIMESTAMP('2019-11-11 14:15:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553926
;

-- 2019-11-11T13:16:11.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/''Y''@=''N & @IsManual/''N''@=''N''',Updated=TO_TIMESTAMP('2019-11-11 14:16:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553212
;

-- 2019-11-11T13:16:14.913Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/''Y''@=''N'' & @IsManual/''N''@=''N''',Updated=TO_TIMESTAMP('2019-11-11 14:16:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=553212
;

-- 2019-11-11T13:16:19.422Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/''Y''@=''N''',Updated=TO_TIMESTAMP('2019-11-11 14:16:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555542
;

-- 2019-11-11T13:16:25.899Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/''Y''@=''N''',Updated=TO_TIMESTAMP('2019-11-11 14:16:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555544
;

-- 2019-11-11T13:16:29.670Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/''Y''@=''N'' & @IsManual/''N''@=''N''',Updated=TO_TIMESTAMP('2019-11-11 14:16:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=554132
;

-- 2019-11-11T13:16:35.654Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/''N''@=''Y''',Updated=TO_TIMESTAMP('2019-11-11 14:16:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582483
;

-- 2019-11-11T13:16:41.132Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsManual/''N''@=''N''',Updated=TO_TIMESTAMP('2019-11-11 14:16:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548720
;

-- 2019-11-11T13:16:42.217Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsManual/''N''@=''N''',Updated=TO_TIMESTAMP('2019-11-11 14:16:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548719
;

-- 2019-11-11T13:16:55.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsManual/''N''@=''Y''|@Description/@!''''',Updated=TO_TIMESTAMP('2019-11-11 14:16:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=551170
;

-- 2019-11-11T13:17:04.304Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/''Y''@=''N'' & @IsManual/''N''@=''N''',Updated=TO_TIMESTAMP('2019-11-11 14:17:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555544
;

-- 2019-11-11T13:17:07.734Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsSOTrx/''Y''@=''N'' & @IsManual/''N''@=''N''',Updated=TO_TIMESTAMP('2019-11-11 14:17:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555542
;

-- 2019-11-11T13:18:27.531Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2019-11-11 14:18:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563853
;

-- 2019-11-11T13:18:30.197Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2019-11-11 14:18:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=563855
;

