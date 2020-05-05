-- 19.04.2016 18:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,543074,0,'Event_UUID',TO_TIMESTAMP('2016-04-19 18:38:59','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Event UUID','Event UUID',TO_TIMESTAMP('2016-04-19 18:38:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.04.2016 18:38
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=543074 AND NOT EXISTS (SELECT * FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 19.04.2016 18:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554383,543074,0,10,540747,'N','Event_UUID',TO_TIMESTAMP('2016-04-19 18:39:28','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.procurement',60,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Event UUID',0,TO_TIMESTAMP('2016-04-19 18:39:28','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 19.04.2016 18:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554383 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 19.04.2016 18:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE PMM_QtyReport_Event ADD Event_UUID VARCHAR(60) DEFAULT NULL 
;

-- 19.04.2016 18:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,554384,543074,0,10,540752,'N','Event_UUID',TO_TIMESTAMP('2016-04-19 18:39:59','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.procurement',60,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Event UUID',0,TO_TIMESTAMP('2016-04-19 18:39:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 19.04.2016 18:39
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=554384 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 19.04.2016 18:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2016-04-19 18:40:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=554384
;

-- 19.04.2016 18:40
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE PMM_WeekReport_Event ADD Event_UUID VARCHAR(60) DEFAULT NULL 
;

-- 19.04.2016 18:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,554383,556916,0,540727,TO_TIMESTAMP('2016-04-19 18:58:13','YYYY-MM-DD HH24:MI:SS'),100,60,'de.metas.procurement','Y','Y','Y','N','N','N','N','N','Event UUID',TO_TIMESTAMP('2016-04-19 18:58:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.04.2016 18:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556916 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 19.04.2016 18:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=260,Updated=TO_TIMESTAMP('2016-04-19 18:58:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556916
;

-- 19.04.2016 18:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=270,Updated=TO_TIMESTAMP('2016-04-19 18:58:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556677
;

-- 19.04.2016 18:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=280,Updated=TO_TIMESTAMP('2016-04-19 18:58:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556678
;

-- 19.04.2016 18:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=290,Updated=TO_TIMESTAMP('2016-04-19 18:58:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556772
;

-- 19.04.2016 18:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=300,Updated=TO_TIMESTAMP('2016-04-19 18:58:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556679
;

-- 19.04.2016 18:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=260,Updated=TO_TIMESTAMP('2016-04-19 18:58:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556916
;

-- 19.04.2016 18:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=270,Updated=TO_TIMESTAMP('2016-04-19 18:58:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556677
;

-- 19.04.2016 18:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=280,Updated=TO_TIMESTAMP('2016-04-19 18:58:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556678
;

-- 19.04.2016 18:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=290,Updated=TO_TIMESTAMP('2016-04-19 18:58:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556772
;

-- 19.04.2016 18:58
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=300,Updated=TO_TIMESTAMP('2016-04-19 18:58:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556679
;

-- 19.04.2016 18:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsCentrallyMaintained,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,554384,556917,0,540733,TO_TIMESTAMP('2016-04-19 18:59:08','YYYY-MM-DD HH24:MI:SS'),100,60,'de.metas.procurement','Y','Y','Y','N','N','N','N','N','Event UUID',TO_TIMESTAMP('2016-04-19 18:59:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 19.04.2016 18:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=556917 AND NOT EXISTS (SELECT * FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 19.04.2016 18:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2016-04-19 18:59:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556917
;

-- 19.04.2016 18:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=110,Updated=TO_TIMESTAMP('2016-04-19 18:59:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556795
;

-- 19.04.2016 18:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=120,Updated=TO_TIMESTAMP('2016-04-19 18:59:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556796
;

-- 19.04.2016 18:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=130,Updated=TO_TIMESTAMP('2016-04-19 18:59:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556797
;

-- 19.04.2016 18:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2016-04-19 18:59:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556917
;

-- 19.04.2016 18:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2016-04-19 18:59:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556795
;

-- 19.04.2016 18:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2016-04-19 18:59:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556796
;

-- 19.04.2016 18:59
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2016-04-19 18:59:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=556797
;

