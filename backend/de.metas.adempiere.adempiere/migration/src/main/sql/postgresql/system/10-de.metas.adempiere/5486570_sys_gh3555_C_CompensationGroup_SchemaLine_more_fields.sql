-- 2018-02-23T16:35:17.629
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,IsActive,CreatedBy,EntityType,IsOrderByValue,AD_Reference_ID,ValidationType,AD_Org_ID,Name,UpdatedBy,Created,Updated) VALUES (0,'Y',100,'de.metas.order','N',540836,'L',0,'C_CompensationGroup_SchemaLine_Type',100,TO_TIMESTAMP('2018-02-23 16:35:17','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-23 16:35:17','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-23T16:35:17.646
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540836 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-02-23T16:36:02.349
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Reference_ID,AD_Client_ID,IsActive,Created,CreatedBy,Value,EntityType,AD_Ref_List_ID,ValueName,AD_Org_ID,Name,UpdatedBy,Updated) VALUES (540836,0,'Y',TO_TIMESTAMP('2018-02-23 16:36:02','YYYY-MM-DD HH24:MI:SS'),100,'R','de.metas.order',541593,'Revenue',0,'Revenue',100,TO_TIMESTAMP('2018-02-23 16:36:02','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-23T16:36:02.358
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Ref_List_ID=541593 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2018-02-23T16:36:24.759
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsRangeFilter,IsAutocomplete,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,Help,AD_Reference_Value_ID,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,AD_Org_ID,UpdatedBy,Name,EntityType,IsShowFilterIncrementButtons,FieldLength,Version,SeqNo,Created,SelectionColumnSeqNo,Updated) VALUES (17,'N','N','N','N',0,'Y',100,600,'Y','N','N','N','N','N','N','Y','N','N','N',540941,'N','N','The Type indicates the type of validation that will occur.  This can be SQL, Java Script or Java Language.',540836,'Type','N',559461,'N','N','N','N','N','N','Type of Validation (SQL, Java Script, Java Language)',0,100,'Art','de.metas.order','N',1,0,0,TO_TIMESTAMP('2018-02-23 16:36:24','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-02-23 16:36:24','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-23T16:36:24.770
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559461 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-23T16:37:02.950
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsRangeFilter,IsAutocomplete,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,Help,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,AD_Org_ID,UpdatedBy,Name,EntityType,IsShowFilterIncrementButtons,FieldLength,Version,SeqNo,Created,SelectionColumnSeqNo,Updated) VALUES (12,'N','N','N','N',0,'Y',100,1708,'Y','N','N','N','N','N','N','Y','N','N','N',540941,'N','N','Starting Quantity or Amount Value for break level','BreakValue','N',559462,'N','N','N','N','N','N','Low Value of trade discount break level',0,100,'Break Value','de.metas.order','N',10,0,0,TO_TIMESTAMP('2018-02-23 16:37:02','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-02-23 16:37:02','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-23T16:37:02.953
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559462 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-23T16:37:29.543
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsRangeFilter,IsAutocomplete,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,AD_Org_ID,UpdatedBy,Name,EntityType,IsShowFilterIncrementButtons,FieldLength,Version,SeqNo,Created,SelectionColumnSeqNo,Updated) VALUES (22,'N','N','N','N',0,'Y',100,501672,'Y','N','N','N','N','N','N','Y','N','N','N',540941,'N','N','CompleteOrderDiscount','N',559463,'N','N','N','N','N','N',0,100,'Gesamtauftragsrabatt %','de.metas.order','N',10,0,0,TO_TIMESTAMP('2018-02-23 16:37:29','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-02-23 16:37:29','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-23T16:37:29.548
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559463 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-23T16:40:18.824
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_CompensationGroup_SchemaLine','ALTER TABLE public.C_CompensationGroup_SchemaLine ADD COLUMN CompleteOrderDiscount NUMERIC')
;

-- 2018-02-23T16:40:29.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_CompensationGroup_SchemaLine','ALTER TABLE public.C_CompensationGroup_SchemaLine ADD COLUMN Type CHAR(1)')
;

-- 2018-02-23T16:40:43.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_CompensationGroup_SchemaLine','ALTER TABLE public.C_CompensationGroup_SchemaLine ADD COLUMN BreakValue NUMERIC')
;

-- 2018-02-23T17:11:26.153
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsRangeFilter,IsAutocomplete,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,Help,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,AD_Org_ID,UpdatedBy,Name,EntityType,IsShowFilterIncrementButtons,FieldLength,Version,SeqNo,Created,SelectionColumnSeqNo,Updated) VALUES (11,'N','N','N','N',0,'Y',100,566,'Y','N','N','N','N','N','N','Y','N','N','N',540941,'N','N','"Reihenfolge" bestimmt die Reihenfolge der Einträge','SeqNo','N',559464,'N','Y','N','N','N','N','Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',0,100,'Reihenfolge','de.metas.order','N',10,0,0,TO_TIMESTAMP('2018-02-23 17:11:26','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-02-23 17:11:26','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-23T17:11:26.158
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559464 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-23T17:11:40.318
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2018-02-23 17:11:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559464
;

-- 2018-02-23T17:11:42.416
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_CompensationGroup_SchemaLine','ALTER TABLE public.C_CompensationGroup_SchemaLine ADD COLUMN SeqNo NUMERIC(10)')
;

-- 2018-02-23T17:46:11.040
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,EntityType,Help,AD_Field_ID,AD_Column_ID,Description,AD_Org_ID,Name,UpdatedBy,DisplayLength,Created,Updated) VALUES (541042,'Y','N','N','N','N',0,'Y',100,'N','de.metas.order','The Type indicates the type of validation that will occur.  This can be SQL, Java Script or Java Language.',563021,559461,'Type of Validation (SQL, Java Script, Java Language)',0,'Art',100,1,TO_TIMESTAMP('2018-02-23 17:46:10','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-23 17:46:10','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-23T17:46:11.046
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=563021 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-02-23T17:46:11.128
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,EntityType,Help,AD_Field_ID,AD_Column_ID,Description,AD_Org_ID,Name,UpdatedBy,DisplayLength,Created,Updated) VALUES (541042,'Y','N','N','N','N',0,'Y',100,'N','de.metas.order','Starting Quantity or Amount Value for break level',563022,559462,'Low Value of trade discount break level',0,'Break Value',100,10,TO_TIMESTAMP('2018-02-23 17:46:11','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-23 17:46:11','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-23T17:46:11.130
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=563022 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-02-23T17:46:11.208
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,EntityType,AD_Field_ID,AD_Column_ID,AD_Org_ID,Name,UpdatedBy,DisplayLength,Created,Updated) VALUES (541042,'Y','N','N','N','N',0,'Y',100,'N','de.metas.order',563023,559463,0,'Gesamtauftragsrabatt %',100,10,TO_TIMESTAMP('2018-02-23 17:46:11','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-23 17:46:11','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-23T17:46:11.209
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=563023 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-02-23T17:46:11.314
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,EntityType,Help,AD_Field_ID,AD_Column_ID,Description,AD_Org_ID,Name,UpdatedBy,DisplayLength,Created,Updated) VALUES (541042,'Y','N','N','N','N',0,'Y',100,'N','de.metas.order','"Reihenfolge" bestimmt die Reihenfolge der Einträge',563024,559464,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',0,'Reihenfolge',100,10,TO_TIMESTAMP('2018-02-23 17:46:11','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-23 17:46:11','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-23T17:46:11.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=563024 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-02-23T17:47:37.620
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (IsAllowFiltering,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,UpdatedBy,AD_Client_ID,CreatedBy,IsActive,AD_UI_ElementGroup_ID,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,AD_Org_ID,Name,AD_UI_ElementType,Created,SeqNo,SeqNoGrid,Updated) VALUES ('N',551149,563024,'N',100,0,100,'Y',541468,'Y','N','N',0,541042,0,'Reihenfolge','F',TO_TIMESTAMP('2018-02-23 17:47:37','YYYY-MM-DD HH24:MI:SS'),50,0,TO_TIMESTAMP('2018-02-23 17:47:37','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-23T17:47:58.790
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (IsAllowFiltering,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,UpdatedBy,AD_Client_ID,CreatedBy,IsActive,AD_UI_ElementGroup_ID,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,AD_Org_ID,Name,AD_UI_ElementType,Created,SeqNo,SeqNoGrid,Updated) VALUES ('N',551150,563023,'N',100,0,100,'Y',541468,'Y','N','N',0,541042,0,'Gesamtauftragsrabatt %','F',TO_TIMESTAMP('2018-02-23 17:47:58','YYYY-MM-DD HH24:MI:SS'),60,0,TO_TIMESTAMP('2018-02-23 17:47:58','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-23T17:48:13.230
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (IsAllowFiltering,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,UpdatedBy,AD_Client_ID,CreatedBy,IsActive,AD_UI_ElementGroup_ID,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,AD_Org_ID,Name,AD_UI_ElementType,Created,SeqNo,SeqNoGrid,Updated) VALUES ('N',551151,563021,'N',100,0,100,'Y',541468,'Y','N','N',0,541042,0,'Art','F',TO_TIMESTAMP('2018-02-23 17:48:13','YYYY-MM-DD HH24:MI:SS'),70,0,TO_TIMESTAMP('2018-02-23 17:48:13','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-23T17:48:37.589
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (IsAllowFiltering,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,UpdatedBy,AD_Client_ID,CreatedBy,IsActive,AD_UI_ElementGroup_ID,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,AD_Org_ID,Name,AD_UI_ElementType,Created,SeqNo,SeqNoGrid,Updated) VALUES ('N',551152,563022,'N',100,0,100,'Y',541468,'Y','N','N',0,541042,0,'Break Value','F',TO_TIMESTAMP('2018-02-23 17:48:37','YYYY-MM-DD HH24:MI:SS'),80,0,TO_TIMESTAMP('2018-02-23 17:48:37','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-23T17:49:54.223
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=5,Updated=TO_TIMESTAMP('2018-02-23 17:49:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551149
;

-- 2018-02-23T17:51:22.477
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2018-02-23 17:51:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551003
;

-- 2018-02-23T17:51:22.481
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2018-02-23 17:51:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551149
;

-- 2018-02-23T17:51:22.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2018-02-23 17:51:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=550998
;

-- 2018-02-23T17:51:22.489
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2018-02-23 17:51:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551002
;

-- 2018-02-23T17:51:22.493
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2018-02-23 17:51:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551150
;

-- 2018-02-23T17:51:22.496
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2018-02-23 17:51:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551151
;

-- 2018-02-23T17:51:22.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2018-02-23 17:51:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551152
;

-- 2018-02-23T19:34:08.070
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type@=R',Updated=TO_TIMESTAMP('2018-02-23 19:34:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563022
;

-- 2018-02-23T19:34:15.597
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@Type/-@=R',Updated=TO_TIMESTAMP('2018-02-23 19:34:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563022
;

