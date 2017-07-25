-- 2017-07-07T22:15:19.234
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557009,454,0,30,540832,'N','M_Product_ID',TO_TIMESTAMP('2017-07-07 22:15:19','YYYY-MM-DD HH24:MI:SS'),100,'N','Produkt, Leistung, Artikel','de.metas.handlingunits',10,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Produkt',0,TO_TIMESTAMP('2017-07-07 22:15:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-07-07T22:15:19.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557009 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-07-07T22:15:25.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2017-07-07 22:15:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557009
;


-- 2017-07-07T22:15:59.081
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,557010,526,0,29,540832,'N','Qty',TO_TIMESTAMP('2017-07-07 22:15:58','YYYY-MM-DD HH24:MI:SS'),100,'N','Menge','de.metas.handlingunits',10,'Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Menge',0,TO_TIMESTAMP('2017-07-07 22:15:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-07-07T22:15:59.082
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557010 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-07-07T22:16:11.043
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2017-07-07 22:16:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557010
;

-- 2017-07-07T22:16:52.786
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2017-07-07 22:16:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557009
;



-- 2017-07-08T00:00:43.978
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557009,558810,0,540842,TO_TIMESTAMP('2017-07-08 00:00:43','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',10,'de.metas.handlingunits','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','Y','Y','N','N','N','N','N','Produkt',TO_TIMESTAMP('2017-07-08 00:00:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-08T00:00:43.982
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558810 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-07-08T00:00:44.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557010,558811,0,540842,TO_TIMESTAMP('2017-07-08 00:00:43','YYYY-MM-DD HH24:MI:SS'),100,'Menge',10,'de.metas.handlingunits','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','Y','Y','N','N','N','N','N','Menge',TO_TIMESTAMP('2017-07-08 00:00:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-07-08T00:00:44.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=558811 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

