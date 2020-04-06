-- 11.09.2015 18:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,552718,542424,0,17,319,208,'N','IsManufactured',TO_TIMESTAMP('2015-09-11 18:31:30','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N','Manufactured',0,TO_TIMESTAMP('2015-09-11 18:31:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 11.09.2015 18:31
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=552718 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 11.09.2015 18:34
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_ID=20, AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2015-09-11 18:34:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552718
;

-- 11.09.2015 18:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL=' CASE WHEN ( EXISTS( SELECT 1 from PP_Product_Planning pp where pp.M_Product_ID = @M_Product_ID@ AND pp.isManufactured = ''Y'')) THEN ''Y'' ELSE ''N'' END  ', IsUpdateable='N',Updated=TO_TIMESTAMP('2015-09-11 18:36:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550558
;

-- 11.09.2015 18:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='  (CASE WHEN ( EXISTS( SELECT 1 from PP_Product_Planning pp where pp.M_Product_ID = @M_Product_ID@ AND pp.isManufactured = ''Y'')) THEN ''Y'' ELSE ''N'' END )  ',Updated=TO_TIMESTAMP('2015-09-11 18:36:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550558
;

-- 11.09.2015 18:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL=NULL,Updated=TO_TIMESTAMP('2015-09-11 18:38:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550558
;

-- 11.09.2015 18:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='  (CASE WHEN ( EXISTS( SELECT 1 from PP_Product_Planning pp where pp.M_Product_ID = @M_Product_ID@ AND pp.isManufactured = ''Y'')) THEN ''Y'' ELSE ''N'' END )', IsMandatory='N', IsUpdateable='N',Updated=TO_TIMESTAMP('2015-09-11 18:39:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552718
;

-- 11.09.2015 18:41
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='  (CASE WHEN ( EXISTS( SELECT 1 from PP_Product_Planning pp where pp.M_Product_ID = M_Product.M_Product_ID AND pp.isManufactured = ''Y'')) THEN ''Y'' ELSE ''N'' END )',Updated=TO_TIMESTAMP('2015-09-11 18:41:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=552718
;

-- 11.09.2015 18:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,552718,556296,0,180,77,TO_TIMESTAMP('2015-09-11 18:43:47','YYYY-MM-DD HH24:MI:SS'),100,1,NULL,'EE01',0,'Y','Y','Y','Y','N','N','N','N','N','Manufactured',141,100,TO_TIMESTAMP('2015-09-11 18:43:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 11.09.2015 18:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556296 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 11.09.2015 18:43
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsSameLine='Y',Updated=TO_TIMESTAMP('2015-09-11 18:43:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556296
;

