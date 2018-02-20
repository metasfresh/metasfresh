-- 2018-02-19T15:34:41.795
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsRangeFilter,IsAutocomplete,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,AD_Org_ID,UpdatedBy,Name,EntityType,IsShowFilterIncrementButtons,FieldLength,Version,SeqNo,Created,SelectionColumnSeqNo,Updated) VALUES (20,'Y','N','N','N','N',0,'Y',100,543891,'Y','N','N','N','N','N','N','Y','N','N','N',260,'N','N','IsPriceEditable','N',559246,'N','Y','N','N','N','N','Allow user to change the price',0,100,'Price Editable','D','N',1,0,0,TO_TIMESTAMP('2018-02-19 15:34:41','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-02-19 15:34:41','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-19T15:34:41.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559246 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-19T15:34:42.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN IsPriceEditable CHAR(1) DEFAULT ''Y'' CHECK (IsPriceEditable IN (''Y'',''N'')) NOT NULL')
;

-- 2018-02-19T15:35:00.446
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsRangeFilter,IsAutocomplete,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,AD_Org_ID,UpdatedBy,Name,EntityType,IsShowFilterIncrementButtons,FieldLength,Version,SeqNo,Created,SelectionColumnSeqNo,Updated) VALUES (20,'Y','N','N','N','N',0,'Y',100,543892,'Y','N','N','N','N','N','N','Y','N','N','N',260,'N','N','IsDiscountEditable','N',559247,'N','Y','N','N','N','N','Allow user to change the discount',0,100,'Discount Editable','D','N',1,0,0,TO_TIMESTAMP('2018-02-19 15:35:00','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2018-02-19 15:35:00','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-19T15:35:00.449
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=559247 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-02-19T15:35:02.541
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN IsDiscountEditable CHAR(1) DEFAULT ''Y'' CHECK (IsDiscountEditable IN (''Y'',''N'')) NOT NULL')
;

-- 2018-02-19T15:39:17.997
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,EntityType,AD_Field_ID,AD_Column_ID,Description,AD_Org_ID,Name,UpdatedBy,DisplayLength,Created,Updated) VALUES (187,'Y','N','N','N','N',0,'Y',100,'N','D',562804,559246,'Allow user to change the price',0,'Price Editable',100,1,TO_TIMESTAMP('2018-02-19 15:39:17','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-19 15:39:17','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-19T15:39:18
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=562804 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-02-19T15:39:30.307
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,EntityType,AD_Field_ID,AD_Column_ID,Description,AD_Org_ID,Name,UpdatedBy,DisplayLength,Created,Updated) VALUES (187,'Y','N','N','N','N',0,'Y',100,'N','D',562805,559247,'Allow user to change the discount',0,'Discount Editable',100,1,TO_TIMESTAMP('2018-02-19 15:39:30','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2018-02-19 15:39:30','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-19T15:39:30.313
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=562805 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-02-19T15:41:39.709
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@IsPriceEditable@!Y',Updated=TO_TIMESTAMP('2018-02-19 15:41:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=12875
;

-- 2018-02-19T15:42:29.560
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='@IsPriceEditable@!Y',Updated=TO_TIMESTAMP('2018-02-19 15:42:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2232
;

-- 2018-02-19T15:43:07.257
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='(@IsGroupCompensationLine/N@=Y & @GroupCompensationType/-@=D & @GroupCompensationAmtType/-@=P)
& @IsPriceEditable@!Y',Updated=TO_TIMESTAMP('2018-02-19 15:43:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=4031
;

-- 2018-02-19T17:15:01.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (IsAllowFiltering,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,UpdatedBy,AD_Client_ID,CreatedBy,IsActive,AD_UI_ElementGroup_ID,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,AD_Org_ID,Name,AD_UI_ElementType,Created,SeqNo,SeqNoGrid,Updated) VALUES ('N',551005,562802,'N',100,0,100,'Y',540126,'Y','N','N',0,540787,0,'Price Editable','F',TO_TIMESTAMP('2018-02-19 17:15:01','YYYY-MM-DD HH24:MI:SS'),100,0,TO_TIMESTAMP('2018-02-19 17:15:01','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-19T17:15:19.993
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (IsAllowFiltering,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,UpdatedBy,AD_Client_ID,CreatedBy,IsActive,AD_UI_ElementGroup_ID,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,AD_Org_ID,Name,AD_UI_ElementType,Created,SeqNo,SeqNoGrid,Updated) VALUES ('N',551006,562803,'N',100,0,100,'Y',540126,'Y','N','N',0,540787,0,'Discount Editable','F',TO_TIMESTAMP('2018-02-19 17:15:19','YYYY-MM-DD HH24:MI:SS'),110,0,TO_TIMESTAMP('2018-02-19 17:15:19','YYYY-MM-DD HH24:MI:SS'))
;

-- 2018-02-19T17:45:48.404
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ReadOnlyLogic='(@IsGroupCompensationLine/N@=Y & @GroupCompensationType/-@=D & @GroupCompensationAmtType/-@=P)
| @IsPriceEditable@!Y',Updated=TO_TIMESTAMP('2018-02-19 17:45:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=4031
;

