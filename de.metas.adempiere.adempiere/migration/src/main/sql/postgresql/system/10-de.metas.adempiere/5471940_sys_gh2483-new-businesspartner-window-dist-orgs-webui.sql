-- 2017-09-17T09:52:31.613
-- Adjusted Insert to make sure that the column is not already created in other repositories
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) 
SELECT 0,557178,225,0,10,291,'N','City','',TO_TIMESTAMP('2017-09-17 09:52:31','YYYY-MM-DD HH24:MI:SS'),100,'N','Name des Ortes','D',60,'Bezeichnet einen einzelnen Ort in diesem Land oder dieser Region.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Ort',0,0,TO_TIMESTAMP('2017-09-17 09:52:31','YYYY-MM-DD HH24:MI:SS'),100,0
WHERE NOT EXISTS (SELECT 1 FROM AD_Column WHERE AD_TABLE_ID = 291 AND ColumnName = 'City')
;

-- 2017-09-17T09:52:31.622
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557178 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-17T09:54:04.235
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='select city from c_bpartner_location bpl
join c_location l on bpl.c_location_id = l.c_location_id 
where isbilltodefault = ''''Y'''' and bpl.c_bpartner_id = C_BPartner.C_BPartner_ID', IsUpdateable='N',Updated=TO_TIMESTAMP('2017-09-17 09:54:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557178
;

-- 2017-09-17T09:56:32.029
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='select postal from c_bpartner_location bpl
join c_location l on bpl.c_location_id = l.c_location_id 
where isbilltodefault = ''''Y'''' and bpl.c_bpartner_id = C_BPartner.C_BPartner_ID', IsUpdateable='N',Updated=TO_TIMESTAMP('2017-09-17 09:56:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=822
;

-- 2017-09-17T09:58:02.187
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='',Updated=TO_TIMESTAMP('2017-09-17 09:58:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=822
;

-- 2017-09-17T09:58:25.526
-- Adjusted Insert to make sure that the column is not already created in other repositories
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) 
SELECT 0,557179,512,0,10,291,'N','Postal','select postal from c_bpartner_location bpl
join c_location l on bpl.c_location_id = l.c_location_id 
where isbilltodefault = ''''Y'''' and bpl.c_bpartner_id = C_BPartner.C_BPartner_ID',TO_TIMESTAMP('2017-09-17 09:58:25','YYYY-MM-DD HH24:MI:SS'),100,'N','Postleitzahl','D',10,'"PLZ" bezeichnet die Postleitzahl für diese Adresse oder dieses Postfach.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','PLZ',0,0,TO_TIMESTAMP('2017-09-17 09:58:25','YYYY-MM-DD HH24:MI:SS'),100,0
WHERE NOT EXISTS (SELECT 1 FROM AD_Column WHERE AD_TABLE_ID = 291 AND ColumnName = 'Postal')
;

-- 2017-09-17T09:58:25.527
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557179 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-17T09:58:51.596
-- Adjusted Insert to make sure that the column is not already created in other repositories
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) 
SELECT 0,557180,156,0,10,291,'N','Address1','select address1 from c_bpartner_location bpl
join c_location l on bpl.c_location_id = l.c_location_id 
where isbilltodefault = ''''Y'''' and bpl.c_bpartner_id = C_BPartner.C_BPartner_ID',TO_TIMESTAMP('2017-09-17 09:58:51','YYYY-MM-DD HH24:MI:SS'),100,'N','Adresszeile 1 für diesen Standort','D',10,'"Adresszeile 1" gibt die Anschrift für diesen Standort an.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Straße und Nr.',0,0,TO_TIMESTAMP('2017-09-17 09:58:51','YYYY-MM-DD HH24:MI:SS'),100,0
WHERE NOT EXISTS (SELECT 1 FROM AD_Column WHERE AD_TABLE_ID = 291 AND ColumnName = 'Address1')
;

-- 2017-09-17T09:58:51.600
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557180 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-17T09:58:57.547
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=100,Updated=TO_TIMESTAMP('2017-09-17 09:58:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557180
;

-- 2017-09-17T10:01:06.679
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='select address1 from c_bpartner_location bpl join c_location l on bpl.c_location_id = l.c_location_id  where isbilltodefault = ''''Y'''' and bpl.c_bpartner_id = C_BPartner.C_BPartner_ID',Updated=TO_TIMESTAMP('2017-09-17 10:01:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557180
;

-- 2017-09-17T10:01:13.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(select city from c_bpartner_location bpl join c_location l on bpl.c_location_id = l.c_location_id  where isbilltodefault = ''''Y'''' and bpl.c_bpartner_id = C_BPartner.C_BPartner_ID)',Updated=TO_TIMESTAMP('2017-09-17 10:01:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557178
;

-- 2017-09-17T10:01:32.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(select address1 from c_bpartner_location bpl join c_location l on bpl.c_location_id = l.c_location_id  where isbilltodefault = ''''Y'''' and bpl.c_bpartner_id = C_BPartner.C_BPartner_ID)',Updated=TO_TIMESTAMP('2017-09-17 10:01:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557180
;

-- 2017-09-17T10:01:38.134
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(select postal from c_bpartner_location bpl join c_location l on bpl.c_location_id = l.c_location_id  where isbilltodefault = ''''Y'''' and bpl.c_bpartner_id = C_BPartner.C_BPartner_ID)',Updated=TO_TIMESTAMP('2017-09-17 10:01:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557179
;

-- 2017-09-17T10:04:28.097
-- Adjusted Insert to make sure that the column is not already created in other repositories
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) 
SELECT 0,557180,560290,0,540871,0,TO_TIMESTAMP('2017-09-17 10:04:28','YYYY-MM-DD HH24:MI:SS'),100,'Adresszeile 1 für diesen Standort',60,'D','"Adresszeile 1" gibt die Anschrift für diesen Standort an.',0,'Y','Y','Y','Y','N','N','N','N','N','Straße und Nr.',290,320,0,1,1,TO_TIMESTAMP('2017-09-17 10:04:28','YYYY-MM-DD HH24:MI:SS'),100 
WHERE NOT EXISTS (SELECT 1 FROM AD_Field JOIN AD_Column ON AD_Field.AD_Column_ID = AD_Column.AD_Column_ID WHERE AD_Column.AD_TABLE_ID = 291 AND AD_Column.ColumnName = 'Address1')
;

-- 2017-09-17T10:04:28.100
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560290 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-17T10:04:52.016
-- Adjusted Insert to make sure that the column is not already created in other repositories
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) 
SELECT 0,557179,560291,0,540871,0,TO_TIMESTAMP('2017-09-17 10:04:51','YYYY-MM-DD HH24:MI:SS'),100,'Postleitzahl',60,'D','"PLZ" bezeichnet die Postleitzahl für diese Adresse oder dieses Postfach.',0,'Y','Y','Y','Y','N','N','N','N','N','PLZ',300,330,0,1,1,TO_TIMESTAMP('2017-09-17 10:04:51','YYYY-MM-DD HH24:MI:SS'),100 
WHERE NOT EXISTS (SELECT 1 FROM AD_Field JOIN AD_Column ON AD_Field.AD_Column_ID = AD_Column.AD_Column_ID WHERE AD_Column.AD_TABLE_ID = 291 AND AD_Column.ColumnName = 'Postal')
;

-- 2017-09-17T10:04:52.018
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560291 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-17T10:05:13.844
-- Adjusted Insert to make sure that the column is not already created in other repositories
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) 
SELECT 0,557178,560292,0,540871,0,TO_TIMESTAMP('2017-09-17 10:05:13','YYYY-MM-DD HH24:MI:SS'),100,'Name des Ortes',60,'D','Bezeichnet einen einzelnen Ort in diesem Land oder dieser Region.',0,'Y','Y','Y','Y','N','N','N','N','N','Ort',310,340,0,1,1,TO_TIMESTAMP('2017-09-17 10:05:13','YYYY-MM-DD HH24:MI:SS'),100 
WHERE NOT EXISTS (SELECT 1 FROM AD_Field JOIN AD_Column ON AD_Field.AD_Column_ID = AD_Column.AD_Column_ID WHERE AD_Column.AD_TABLE_ID = 291 AND AD_Column.ColumnName = 'City')
;

-- 2017-09-17T10:05:13.848
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560292 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-17T10:05:49.999
-- Adjusted Insert to make sure that the column is not already created in other repositories
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) 
SELECT 0,560290,0,540871,541151,548670,TO_TIMESTAMP('2017-09-17 10:05:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Strasse und Nr.',60,0,0,TO_TIMESTAMP('2017-09-17 10:05:49','YYYY-MM-DD HH24:MI:SS'),100 
WHERE EXISTS (SELECT 1 FROM AD_Field where AD_Field.AD_Field_ID = 560290) AND NOT EXISTS (SELECT 1 FROM AD_UI_Element where AD_Field_ID = 560290)
;

-- 2017-09-17T10:06:01.340
-- Adjusted Insert to make sure that the column is not already created in other repositories
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) 
SELECT 0,560291,0,540871,541151,548671,TO_TIMESTAMP('2017-09-17 10:06:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','PLZ',70,0,0,TO_TIMESTAMP('2017-09-17 10:06:01','YYYY-MM-DD HH24:MI:SS'),100 
WHERE EXISTS (SELECT 1 FROM AD_Field where AD_Field.AD_Field_ID = 560291) AND NOT EXISTS (SELECT 1 FROM AD_UI_Element where AD_Field_ID = 560291)
;

-- 2017-09-17T10:06:12.465
-- Adjusted Insert to make sure that the column is not already created in other repositories
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) 
SELECT 0,560292,0,540871,541151,548672,TO_TIMESTAMP('2017-09-17 10:06:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Ort',80,0,0,TO_TIMESTAMP('2017-09-17 10:06:12','YYYY-MM-DD HH24:MI:SS'),100 
WHERE EXISTS (SELECT 1 FROM AD_Field where AD_Field.AD_Field_ID = 560292) AND NOT EXISTS (SELECT 1 FROM AD_UI_Element where AD_Field_ID = 560292)
;

-- 2017-09-17T10:08:14.609
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(select address1 from c_bpartner_location bpl join c_location l on bpl.c_location_id = l.c_location_id  where isbilltodefault = ''Y'' and bpl.c_bpartner_id = C_BPartner.C_BPartner_ID)',Updated=TO_TIMESTAMP('2017-09-17 10:08:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557180
;

-- 2017-09-17T10:08:42.380
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(select city from c_bpartner_location bpl join c_location l on bpl.c_location_id = l.c_location_id  where isbilltodefault = ''Y''
 and bpl.c_bpartner_id = C_BPartner.C_BPartner_ID)',Updated=TO_TIMESTAMP('2017-09-17 10:08:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557178
;

-- 2017-09-17T10:08:57.323
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(select postal from c_bpartner_location bpl join c_location l on bpl.c_location_id = l.c_location_id  where isbilltodefault = ''Y'' and bpl.c_bpartner_id = C_BPartner.C_BPartner_ID)',Updated=TO_TIMESTAMP('2017-09-17 10:08:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557179
;

-- 2017-09-17T10:11:10.233
-- Adjusted Insert to make sure that the column is not already created in other repositories
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) 
SELECT 0,557181,881,0,10,291,'N','EMail',TO_TIMESTAMP('2017-09-17 10:11:10','YYYY-MM-DD HH24:MI:SS'),100,'N','EMail-Adresse','D',200,'The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','eMail',0,0,TO_TIMESTAMP('2017-09-17 10:11:10','YYYY-MM-DD HH24:MI:SS'),100,0 
WHERE NOT EXISTS (SELECT 1 FROM AD_Column WHERE AD_TABLE_ID = 291 AND ColumnName = 'EMail')
;

-- 2017-09-17T10:11:10.237
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=557181 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-09-17T10:12:17.828
-- Adjusted Insert to make sure that the column is not already created in other repositories
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsCentrallyMaintained,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) 
SELECT 0,557181,560293,0,540871,0,TO_TIMESTAMP('2017-09-17 10:12:17','YYYY-MM-DD HH24:MI:SS'),100,'EMail-Adresse',0,'D','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.',0,'Y','Y','Y','Y','N','N','N','N','N','eMail',320,350,0,1,1,TO_TIMESTAMP('2017-09-17 10:12:17','YYYY-MM-DD HH24:MI:SS'),100 
WHERE NOT EXISTS (SELECT 1 FROM AD_Field JOIN AD_Column ON AD_Field.AD_Column_ID = AD_Column.AD_Column_ID WHERE AD_Column.AD_TABLE_ID = 291 AND AD_Column.ColumnName = 'EMail')
;

-- 2017-09-17T10:12:17.831
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=560293 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2017-09-17T10:12:32.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) 
SELECT 0,560293,0,540871,541151,548673,TO_TIMESTAMP('2017-09-17 10:12:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','eMail',90,0,0,TO_TIMESTAMP('2017-09-17 10:12:32','YYYY-MM-DD HH24:MI:SS'),100
WHERE EXISTS (SELECT 1 FROM AD_Field where AD_Field.AD_Field_ID = 560293) AND NOT EXISTS (SELECT 1 FROM AD_UI_Element where AD_Field_ID = 560293)
;

-- 2017-09-17T10:12:46.854
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='',Updated=TO_TIMESTAMP('2017-09-17 10:12:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557180
;

-- 2017-09-17T10:12:56.623
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='',Updated=TO_TIMESTAMP('2017-09-17 10:12:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557178
;

-- 2017-09-17T10:13:05.762
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='',Updated=TO_TIMESTAMP('2017-09-17 10:13:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557179
;

-- 2017-09-17T10:13:29.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('c_bpartner','ALTER TABLE public.C_BPartner ADD COLUMN Postal VARCHAR(10)')
;

-- 2017-09-17T10:13:40.539
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('c_bpartner','ALTER TABLE public.C_BPartner ADD COLUMN EMail VARCHAR(200)')
;

-- 2017-09-17T10:14:05.103
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('c_bpartner','ALTER TABLE public.C_BPartner ADD COLUMN City VARCHAR(60)')
;

-- 2017-09-17T10:14:13.892
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('c_bpartner','ALTER TABLE public.C_BPartner ADD COLUMN Address1 VARCHAR(100)')
;

-- 2017-09-17T10:15:54.595
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(select city from c_bpartner_location bpl
join c_location l on bpl.c_location_id = l.c_location_id 
where isbilltodefault = ''''Y'''' and bpl.c_bpartner_id = C_BPartner.C_BPartner_ID )',Updated=TO_TIMESTAMP('2017-09-17 10:15:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557178
;

-- 2017-09-17T10:17:28.171
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(select city from c_bpartner_location bpl
join c_location l on bpl.c_location_id = l.c_location_id 
where isbilltodefault = ''Y'' and bpl.c_bpartner_id = C_BPartner.C_BPartner_ID )',Updated=TO_TIMESTAMP('2017-09-17 10:17:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557178
;

-- 2017-09-17T10:18:41.339
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(select postal from c_bpartner_location bpl
join c_location l on bpl.c_location_id = l.c_location_id 
where isbilltodefault = ''''Y'''' and bpl.c_bpartner_id = C_BPartner.C_BPartner_ID )',Updated=TO_TIMESTAMP('2017-09-17 10:18:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557179
;

-- 2017-09-17T10:18:50.832
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(select postal from c_bpartner_location bpl
join c_location l on bpl.c_location_id = l.c_location_id 
where isbilltodefault = ''Y'' and bpl.c_bpartner_id = C_BPartner.C_BPartner_ID )',Updated=TO_TIMESTAMP('2017-09-17 10:18:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557179
;

-- 2017-09-17T10:19:25.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(select address1 from c_bpartner_location bpl join c_location l on bpl.c_location_id = l.c_location_id  where isbilltodefault = ''Y'' and bpl.c_bpartner_id = C_BPartner.C_BPartner_ID )',Updated=TO_TIMESTAMP('2017-09-17 10:19:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557180
;

-- 2017-09-17T11:06:33.454
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(SELECT Email FROM AD_User u WHERE u.C_BPartner_ID=C_BPartner.C_BPartner_ID AND u.IsActive=''Y'' AND u.IsDefaultContact=''Y'')', IsUpdateable='N',Updated=TO_TIMESTAMP('2017-09-17 11:06:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=557181
;

