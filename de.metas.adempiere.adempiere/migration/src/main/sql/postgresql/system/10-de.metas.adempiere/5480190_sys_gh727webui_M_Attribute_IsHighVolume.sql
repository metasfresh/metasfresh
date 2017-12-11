-- 2017-12-10T13:30:25.270
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,AD_Element_ID,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsRangeFilter,IsAutocomplete,IsAllowLogging,IsEncrypted,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,AllowZoomTo,Help,ColumnName,IsGenericZoomOrigin,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,Description,AD_Org_ID,UpdatedBy,Name,EntityType,IsShowFilterIncrementButtons,FieldLength,Version,SeqNo,Created,SelectionColumnSeqNo,Updated) VALUES (20,'N','N','N','N','N',0,'Y',100,1174,'Y','N','N','N','N','N','N','Y','N','N','N',562,'N','N','The High Volume Checkbox indicates if a search screen will display as opposed to a pick list for selecting records from this table.','IsHighVolume','N',558262,'N','Y','N','N','N','N','Use Search instead of Pick list',0,100,'High Volume','D','N',1,0,0,TO_TIMESTAMP('2017-12-10 13:30:24','YYYY-MM-DD HH24:MI:SS'),0,TO_TIMESTAMP('2017-12-10 13:30:24','YYYY-MM-DD HH24:MI:SS'))
;

-- 2017-12-10T13:30:25.285
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=558262 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-12-10T13:30:32.337
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_Attribute','ALTER TABLE public.M_Attribute ADD COLUMN IsHighVolume CHAR(1) DEFAULT ''N'' CHECK (IsHighVolume IN (''Y'',''N'')) NOT NULL')
;

-- 2017-12-10T13:31:11.908
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Tab_ID,IsDisplayed,IsSameLine,IsHeading,IsFieldOnly,IsEncrypted,AD_Client_ID,IsActive,CreatedBy,IsReadOnly,IsCentrallyMaintained,Help,AD_Field_ID,AD_Column_ID,Description,AD_Org_ID,Name,EntityType,UpdatedBy,DisplayLength,Created,Updated) VALUES (462,'Y','N','N','N','N',0,'Y',100,'N','Y','The High Volume Checkbox indicates if a search screen will display as opposed to a pick list for selecting records from this table.',560803,558262,'Use Search instead of Pick list',0,'High Volume','D',100,1,TO_TIMESTAMP('2017-12-10 13:31:11','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2017-12-10 13:31:11','YYYY-MM-DD HH24:MI:SS'))
;

-- 2017-12-10T13:31:11.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560803 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-12-10T13:33:32.694
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,UpdatedBy,AD_Client_ID,CreatedBy,IsActive,AD_UI_ElementGroup_ID,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,AD_Org_ID,Name,AD_UI_ElementType,Created,SeqNo,SeqNoGrid,Updated) VALUES (549708,560803,'N',100,0,100,'Y',540207,'Y','N','N',0,462,0,'High Volume','F',TO_TIMESTAMP('2017-12-10 13:33:32','YYYY-MM-DD HH24:MI:SS'),40,0,TO_TIMESTAMP('2017-12-10 13:33:32','YYYY-MM-DD HH24:MI:SS'))
;

-- 2017-12-10T13:33:52.244
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@AttributeValueType/-@=L',Updated=TO_TIMESTAMP('2017-12-10 13:33:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=560803
;

