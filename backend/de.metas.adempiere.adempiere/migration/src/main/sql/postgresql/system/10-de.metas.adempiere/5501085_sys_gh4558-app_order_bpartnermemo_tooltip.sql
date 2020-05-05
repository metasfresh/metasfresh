/*
-- 2018-09-07T14:56:14.253
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544237,0,'C_BPartner_Memo',TO_TIMESTAMP('2018-09-07 14:56:14','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Geschäftspartner-Memo','Geschäftspartner-Memo',TO_TIMESTAMP('2018-09-07 14:56:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-07T14:56:14.256
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544237 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
*/
-- 2018-09-07T14:56:17.255
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-07 14:56:17','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=544250 AND AD_Language='de_CH'
;

-- 2018-09-07T14:56:17.259
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544250,'de_CH') 
;

-- 2018-09-07T14:56:33.272
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-07 14:56:33','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Businesspartner-Memo',PrintName='Businesspartner-Memo' WHERE AD_Element_ID=544250 AND AD_Language='en_US'
;

-- 2018-09-07T14:56:33.275
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544250,'en_US') 
;

-- 2018-09-07T14:59:25.709
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=9999999, TechnicalNote='This column is used as SQL column in C_Order',Updated=TO_TIMESTAMP('2018-09-07 14:59:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=542502
;

-- 2018-09-07T14:59:43.775
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=999999,Updated=TO_TIMESTAMP('2018-09-07 14:59:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=542502
;

-- 2018-09-07T14:59:45.777
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bpartner','Memo','TEXT',null,null)
;

-- 2018-09-07T15:00:02.136
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,560875,544250,0,14,259,'C_BPartner_Memo','( select bp.Memo from C_BPartner bp where bp.C_BPartner_ID = C_Order.C_BPartner_ID )',TO_TIMESTAMP('2018-09-07 15:00:01','YYYY-MM-DD HH24:MI:SS'),100,'N','D',999999,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Geschäftspartner-Memo',0,0,TO_TIMESTAMP('2018-09-07 15:00:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-09-07T15:00:02.137
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560875 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-07T15:03:55.880
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544238,0,'Bill_BPartner_Memo',TO_TIMESTAMP('2018-09-07 15:03:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Rechnungspartner-Memo','Rechnungspartner-Memo',TO_TIMESTAMP('2018-09-07 15:03:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-07T15:03:55.883
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544238 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-09-07T15:04:29.674
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,560876,544238,0,14,259,'Bill_BPartner_Memo','( select bp.Memo from C_BPartner bp where bp.C_BPartner_ID = C_Order.Bill_BPartner_ID )',TO_TIMESTAMP('2018-09-07 15:04:29','YYYY-MM-DD HH24:MI:SS'),100,'N','D',999999,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Rechnungspartner-Memo',0,0,TO_TIMESTAMP('2018-09-07 15:04:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-09-07T15:04:29.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560876 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-07T15:06:07.256
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,544239,0,'DropShip_BPartner_Memo',TO_TIMESTAMP('2018-09-07 15:06:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Lieferempfänger-Memo','Lieferempfänger-Memo',TO_TIMESTAMP('2018-09-07 15:06:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-07T15:06:07.257
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Element_ID, t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Element_ID=544239 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2018-09-07T15:06:27.725
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,560877,544239,0,14,259,'DropShip_BPartner_Memo','( select bp.Memo from C_BPartner bp where bp.C_BPartner_ID = C_Order.DropShip_BPartner_ID )',TO_TIMESTAMP('2018-09-07 15:06:27','YYYY-MM-DD HH24:MI:SS'),100,'N','D',999999,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Lieferempfänger-Memo',0,0,TO_TIMESTAMP('2018-09-07 15:06:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2018-09-07T15:06:27.727
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=560877 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2018-09-07T15:06:48.675
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=53458 AND AD_Language='fr_CH'
;

-- 2018-09-07T15:06:50.007
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=53458 AND AD_Language='it_CH'
;

-- 2018-09-07T15:06:51.554
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=53458 AND AD_Language='en_GB'
;

-- 2018-09-07T15:06:54.120
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-07 15:06:54','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=53458 AND AD_Language='de_CH'
;

-- 2018-09-07T15:06:54.123
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(53458,'de_CH') 
;

-- 2018-09-07T15:07:09.921
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-07 15:07:09','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=544239 AND AD_Language='de_CH'
;

-- 2018-09-07T15:07:09.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544239,'de_CH') 
;

-- 2018-09-07T15:07:21.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-07 15:07:21','YYYY-MM-DD HH24:MI:SS'),Name='Drop Shipment Partner Memo',PrintName='Drop Shipment Partner Memo' WHERE AD_Element_ID=544239 AND AD_Language='nl_NL'
;

-- 2018-09-07T15:07:21.916
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544239,'nl_NL') 
;

-- 2018-09-07T15:07:25.867
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-07 15:07:25','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Drop Shipment Partner Memo',PrintName='Drop Shipment Partner Memo' WHERE AD_Element_ID=544239 AND AD_Language='en_US'
;

-- 2018-09-07T15:07:25.876
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544239,'en_US') 
;

-- 2018-09-07T15:08:07.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-07 15:08:07','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=544238 AND AD_Language='de_CH'
;

-- 2018-09-07T15:08:07.463
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544238,'de_CH') 
;

-- 2018-09-07T15:08:26.128
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=2039 AND AD_Language='fr_CH'
;

-- 2018-09-07T15:08:27.550
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=2039 AND AD_Language='it_CH'
;

-- 2018-09-07T15:08:28.653
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=2039 AND AD_Language='en_GB'
;

-- 2018-09-07T15:08:33.328
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-07 15:08:33','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=2039 AND AD_Language='de_CH'
;

-- 2018-09-07T15:08:33.332
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(2039,'de_CH') 
;

-- 2018-09-07T15:08:49.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-07 15:08:49','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Invoice Partner Memo',PrintName='Invoice Partner Memo' WHERE AD_Element_ID=544238 AND AD_Language='en_US'
;

-- 2018-09-07T15:08:49.141
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(544238,'en_US') 
;

-- 2018-09-07T15:09:19.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=187 AND AD_Language='fr_CH'
;

-- 2018-09-07T15:09:20.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=187 AND AD_Language='it_CH'
;

-- 2018-09-07T15:09:22.024
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Trl WHERE AD_Element_ID=187 AND AD_Language='en_GB'
;

-- 2018-09-07T15:09:24.529
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2018-09-07 15:09:24','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y' WHERE AD_Element_ID=187 AND AD_Language='de_CH'
;

-- 2018-09-07T15:09:24.532
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(187,'de_CH') 
;

-- 2018-09-07T15:13:20.745
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560875,568869,0,186,0,TO_TIMESTAMP('2018-09-07 15:13:20','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','N','N','N','N','N','N','N','Geschäftspartner-Memo',630,610,0,1,1,TO_TIMESTAMP('2018-09-07 15:13:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-07T15:13:20.746
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=568869 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-09-07T15:13:28.812
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560876,568870,0,186,0,TO_TIMESTAMP('2018-09-07 15:13:28','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','N','N','N','N','N','N','N','Rechnungspartner-Memo',640,620,0,1,1,TO_TIMESTAMP('2018-09-07 15:13:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-07T15:13:28.813
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=568870 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-09-07T15:13:39.320
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560877,568871,0,186,0,TO_TIMESTAMP('2018-09-07 15:13:39','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','N','N','N','N','N','N','N','Lieferempfänger-Memo',650,630,0,1,1,TO_TIMESTAMP('2018-09-07 15:13:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-07T15:13:39.322
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=568871 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-09-07T15:14:21.907
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColumnDisplayLength=273, IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2018-09-07 15:14:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557176
;

-- 2018-09-07T15:14:21.911
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColumnDisplayLength=82, IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2018-09-07 15:14:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557207
;

-- 2018-09-07T15:14:21.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColumnDisplayLength=314, IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2018-09-07 15:14:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557175
;

-- 2018-09-07T15:14:21.917
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColumnDisplayLength=106, IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2018-09-07 15:14:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=557173
;

-- 2018-09-07T15:14:21.920
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColumnDisplayLength=69, IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2018-09-07 15:14:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=568717
;

-- 2018-09-07T15:14:21.923
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET ColumnDisplayLength=118, IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2018-09-07 15:14:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=568718
;

-- 2018-09-07T15:14:55.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,TooltipIconName,Type,Updated,UpdatedBy) VALUES (0,568869,0,540283,1000000,TO_TIMESTAMP('2018-09-07 15:14:55','YYYY-MM-DD HH24:MI:SS'),100,'Y',30,'text','tooltip',TO_TIMESTAMP('2018-09-07 15:14:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-07T15:15:24.751
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,TooltipIconName,Type,Updated,UpdatedBy) VALUES (0,568870,0,540284,1000013,TO_TIMESTAMP('2018-09-07 15:15:24','YYYY-MM-DD HH24:MI:SS'),100,'Y',30,'text','tooltip',TO_TIMESTAMP('2018-09-07 15:15:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-07T15:17:00.634
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,TooltipIconName,Type,Updated,UpdatedBy) VALUES (0,568871,0,540285,544810,TO_TIMESTAMP('2018-09-07 15:17:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',30,'text','tooltip',TO_TIMESTAMP('2018-09-07 15:17:00','YYYY-MM-DD HH24:MI:SS'),100)
;

