-- 2022-01-19T11:05:00.169Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,579097,541761,0,13,540005,'C_CountryArea_ID',TO_TIMESTAMP('2022-01-19 13:04:59','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.swat',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Country Area',0,0,TO_TIMESTAMP('2022-01-19 13:04:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-01-19T11:05:00.204Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=579097 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-01-19T11:05:00.300Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(541761) 
;

-- 2022-01-19T11:05:59.243Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,579097,677145,0,541820,0,TO_TIMESTAMP('2022-01-19 13:05:58','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Country Area',0,60,0,1,1,TO_TIMESTAMP('2022-01-19 13:05:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T11:05:59.281Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677145 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-19T11:05:59.319Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541761) 
;

-- 2022-01-19T11:05:59.375Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677145
;

-- 2022-01-19T11:05:59.413Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(677145)
;

-- 2022-01-19T11:06:38.302Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,677145,0,541820,542676,599544,'F',TO_TIMESTAMP('2022-01-19 13:06:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Country Area',25,0,0,TO_TIMESTAMP('2022-01-19 13:06:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T11:06:49.016Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-01-19 13:06:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=599544
;

-- 2022-01-19T11:06:49.157Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-01-19 13:06:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560147
;

-- 2022-01-19T11:49:27.254Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=19, IsExcludeFromZoomTargets='N',Updated=TO_TIMESTAMP('2022-01-19 13:49:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579097
;

-- 2022-01-19T12:51:49.013Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_Value_ID=156,Updated=TO_TIMESTAMP('2022-01-19 14:51:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579097
;

-- 2022-01-19T12:53:51.711Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('M_FreightCostDetail','ALTER TABLE public.M_FreightCostDetail ADD COLUMN C_CountryArea_ID NUMERIC(10)')
;

-- 2022-01-19T12:53:51.757Z
-- URL zum Konzept
ALTER TABLE M_FreightCostDetail ADD CONSTRAINT CCountryArea_MFreightCostDetail FOREIGN KEY (C_CountryArea_ID) REFERENCES public.C_CountryArea DEFERRABLE INITIALLY DEFERRED
;

-- Create Saint-Martin French and Dutch sides

-- 2022-01-19T14:19:57.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_AttributeValue (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,M_Attribute_ID,M_AttributeValue_ID,Name,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2022-01-19 16:19:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000001,540045,'Collectivity of Saint Martin',TO_TIMESTAMP('2022-01-19 16:19:57','YYYY-MM-DD HH24:MI:SS'),100,'MF')
;

-- 2022-01-19T14:19:57.568Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Country (AD_Client_ID,AD_Language,AD_Org_ID,AllowCitiesOutOfList,C_Country_ID,C_Currency_ID,CountryCode,CountryCode_3digit,Created,CreatedBy,DisplaySequence,HasPostal_Add,HasRegion,IsActive,IsAddressLinesLocalReverse,IsAddressLinesReverse,Name,RegionName,Updated,UpdatedBy) VALUES (0,'en_US',0,'Y',540005,318,'MF','MAF',TO_TIMESTAMP('2022-01-19 16:19:57','YYYY-MM-DD HH24:MI:SS'),100,'@BP@ @CON@ @A1@ @A2@ @A3@ @A4@ @P@ @C@ @CO@','N','N','Y','N','N','Collectivity of Saint Martin','State',TO_TIMESTAMP('2022-01-19 16:19:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:19:57.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Country_Trl (AD_Language,C_Country_ID, Description,Name,RegionName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Country_ID, t.Description,t.Name,t.RegionName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Country t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Country_ID=540005 AND NOT EXISTS (SELECT 1 FROM C_Country_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Country_ID=t.C_Country_ID)
;

-- 2022-01-19T14:20:12.778Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Country SET Description='Saint Martin French Side',Updated=TO_TIMESTAMP('2022-01-19 16:20:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Country_ID=540005
;

-- 2022-01-19T14:20:12.780Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Country_Trl trl SET Description='Saint Martin French Side', Name='Collectivity of Saint Martin', RegionName='State'  WHERE C_Country_ID=540005 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2022-01-19T14:20:20.442Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Country SET Description='Saint Martin French Part',Updated=TO_TIMESTAMP('2022-01-19 16:20:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Country_ID=540005
;

-- 2022-01-19T14:20:20.443Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Country_Trl trl SET Description='Saint Martin French Part', Name='Collectivity of Saint Martin', RegionName='State'  WHERE C_Country_ID=540005 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2022-01-19T14:20:26.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Country SET CaptureSequence='@A1@ @A2@ @A3@ @A4@ @C@,  @P@ @CO@',Updated=TO_TIMESTAMP('2022-01-19 16:20:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Country_ID=540005
;

-- 2022-01-19T14:20:38.954Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Country SET DisplaySequenceLocal='@BP@ @CON@ @A1@ @A2@ @A3@ @A4@ @P@ @C@',Updated=TO_TIMESTAMP('2022-01-19 16:20:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Country_ID=540005
;

-- 2022-01-19T14:20:52.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Country SET C_Currency_ID=102,Updated=TO_TIMESTAMP('2022-01-19 16:20:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Country_ID=540005
;

-- 2022-01-19T14:49:38.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Country_Trl SET Name='Saint-Martin (Gebietskörperschaft)',Updated=TO_TIMESTAMP('2022-01-19 16:49:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_Country_ID=540005
;

-- 2022-01-19T14:49:41.868Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Country_Trl SET Name='Saint-Martin (Gebietskörperschaft)',Updated=TO_TIMESTAMP('2022-01-19 16:49:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND C_Country_ID=540005
;

-- 2022-01-19T14:49:44.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Country_Trl SET Name='Saint-Martin (Gebietskörperschaft)',Updated=TO_TIMESTAMP('2022-01-19 16:49:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND C_Country_ID=540005
;

-- 2022-01-19T14:49:49.084Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Country_Trl SET Description='St. Martin (Französischer Teil)',Updated=TO_TIMESTAMP('2022-01-19 16:49:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_Country_ID=540005
;

-- 2022-01-19T14:49:52.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Country_Trl SET Description='St. Martin (Französischer Teil)',Updated=TO_TIMESTAMP('2022-01-19 16:49:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND C_Country_ID=540005
;

-- 2022-01-19T14:49:55.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Country_Trl SET Description='St. Martin (Französischer Teil)',Updated=TO_TIMESTAMP('2022-01-19 16:49:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND C_Country_ID=540005
;

-- 2022-01-19T14:50:01.429Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Country_Trl SET Description='Saint Martin (French Part)',Updated=TO_TIMESTAMP('2022-01-19 16:50:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_Country_ID=540005
;

-- 2022-01-19T14:52:28.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_AttributeValue (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,M_Attribute_ID,M_AttributeValue_ID,Name,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2022-01-19 16:52:28','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000001,540046,'Sint Maarten',TO_TIMESTAMP('2022-01-19 16:52:28','YYYY-MM-DD HH24:MI:SS'),100,'SX')
;

-- 2022-01-19T14:52:28.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Country (AD_Client_ID,AD_Language,AD_Org_ID,AllowCitiesOutOfList,C_Country_ID,C_Currency_ID,CountryCode,CountryCode_3digit,Created,CreatedBy,DisplaySequence,HasPostal_Add,HasRegion,IsActive,IsAddressLinesLocalReverse,IsAddressLinesReverse,Name,RegionName,Updated,UpdatedBy) VALUES (0,'en_US',0,'Y',540006,318,'SX','SXM',TO_TIMESTAMP('2022-01-19 16:52:28','YYYY-MM-DD HH24:MI:SS'),100,'@BP@ @CON@ @A1@ @A2@ @A3@ @A4@ @P@ @C@ @CO@','N','N','Y','N','N','Sint Maarten','State',TO_TIMESTAMP('2022-01-19 16:52:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-19T14:52:28.796Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Country_Trl (AD_Language,C_Country_ID, Description,Name,RegionName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Country_ID, t.Description,t.Name,t.RegionName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Country t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Country_ID=540006 AND NOT EXISTS (SELECT 1 FROM C_Country_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Country_ID=t.C_Country_ID)
;

-- 2022-01-19T14:52:32.969Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Country SET CaptureSequence='@A1@ @A2@ @A3@ @A4@ @C@,  @P@ @CO@',Updated=TO_TIMESTAMP('2022-01-19 16:52:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Country_ID=540006
;

-- 2022-01-19T14:52:35.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Country SET DisplaySequenceLocal='@BP@ @CON@ @A1@ @A2@ @A3@ @A4@ @P@ @C@',Updated=TO_TIMESTAMP('2022-01-19 16:52:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Country_ID=540006
;

-- 2022-01-19T15:00:28.078Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Country_Trl SET Description='Saint Martin (Dutch Part)',Updated=TO_TIMESTAMP('2022-01-19 17:00:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_Country_ID=540006
;

-- 2022-01-19T15:00:35.664Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Country_Trl SET Description='St. Martin (Niederländischer Teil)',Updated=TO_TIMESTAMP('2022-01-19 17:00:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_Country_ID=540006
;

-- 2022-01-19T15:00:36.844Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Country_Trl SET Description='St. Martin (Niederländischer Teil)',Updated=TO_TIMESTAMP('2022-01-19 17:00:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND C_Country_ID=540006
;

-- 2022-01-19T15:00:38.269Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Country_Trl SET Description='St. Martin (Niederländischer Teil)',Updated=TO_TIMESTAMP('2022-01-19 17:00:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND C_Country_ID=540006
;

-- 2022-01-19T15:00:54.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Country SET Description='Sint Maarten',Updated=TO_TIMESTAMP('2022-01-19 17:00:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Country_ID=540006
;

-- 2022-01-19T15:00:54.154Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Country_Trl trl SET Description='Sint Maarten', Name='Sint Maarten', RegionName='State'  WHERE C_Country_ID=540006 AND ( trl.isTranslated = 'N'  OR  exists(select 1 from ad_language lang where lang.ad_language = trl.ad_language and lang.isBaseLanguage = 'Y') )
;

-- 2022-01-21T14:25:17.133Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Index_Table_Trl WHERE AD_Index_Table_ID=540247
;

-- 2022-01-21T14:25:17.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Index_Table WHERE AD_Index_Table_ID=540247
;

-- Need to drop index so as to allow Spanish provinces to be added to master data as Countries (to allow setting shipping fees)

DROP INDEX IF EXISTS c_country_countrycode_3digit
;

COMMIT
;

-- 2022-01-21T14:30:17.199Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_AttributeValue (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,M_Attribute_ID,M_AttributeValue_ID,Name,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2022-01-21 16:30:17','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000001,540047,'Kanarische Inseln',TO_TIMESTAMP('2022-01-21 16:30:17','YYYY-MM-DD HH24:MI:SS'),100,'IC')
;

-- 2022-01-21T14:30:16.966Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Country (AccountNumberCharType,AccountNumberLength,AccountNumberSeqNo,AD_Client_ID,AD_Language,AD_Org_ID,AllowCitiesOutOfList,BankCodeCharType,BankCodeLength,BankCodeSeqNo,BranchCodeCharType,BranchCodeLength,BranchCodeSeqNo,CaptureSequence,C_Country_ID,C_Currency_ID,CountryCode,CountryCode_3digit,Created,CreatedBy,Description,DisplaySequence,DisplaySequenceLocal,HasPostal_Add,HasRegion,IsActive,IsAddressLinesLocalReverse,IsAddressLinesReverse,Name,NationalCheckDigitCharType,NationalCheckDigitLength,NationalCheckDigitSeqNo,Updated,UpdatedBy) VALUES ('n','10','40',0,'es_ES',0,'Y','n','4','10','n','4','20','@A1@ @A2@ @A3@ @A4@ E-@P@ @C@ @R@ @CO@',540010,102,'IC','ESP',TO_TIMESTAMP('2022-01-21 16:30:16','YYYY-MM-DD HH24:MI:SS'),100,'Kanarische Inseln','@BP@ @CON@ @A1@ @A2@ @A3@ @A4@ @P@ @C@ @CO@','@BP@ @CON@ @A1@ @A2@ @A3@ @A4@ @P@ @C@','N','Y','Y','N','N','Kanarische Inseln','n','2','30',TO_TIMESTAMP('2022-01-21 16:30:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-21T14:30:16.968Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Country_Trl (AD_Language,C_Country_ID, Description,Name,RegionName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Country_ID, t.Description,t.Name,t.RegionName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Country t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Country_ID=540010 AND NOT EXISTS (SELECT 1 FROM C_Country_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Country_ID=t.C_Country_ID)
;

-- 2022-01-21T14:30:49.962Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO M_AttributeValue (AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,M_Attribute_ID,M_AttributeValue_ID,Name,Updated,UpdatedBy,Value) VALUES (0,0,TO_TIMESTAMP('2022-01-21 16:30:49','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000001,540048,'Ceuta, Melilla',TO_TIMESTAMP('2022-01-21 16:30:49','YYYY-MM-DD HH24:MI:SS'),100,'EA')
;

-- 2022-01-21T14:30:49.812Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Country (AccountNumberCharType,AccountNumberLength,AccountNumberSeqNo,AD_Client_ID,AD_Language,AD_Org_ID,AllowCitiesOutOfList,BankCodeCharType,BankCodeLength,BankCodeSeqNo,BranchCodeCharType,BranchCodeLength,BranchCodeSeqNo,CaptureSequence,C_Country_ID,C_Currency_ID,CountryCode,CountryCode_3digit,Created,CreatedBy,Description,DisplaySequence,DisplaySequenceLocal,HasPostal_Add,HasRegion,IsActive,IsAddressLinesLocalReverse,IsAddressLinesReverse,Name,NationalCheckDigitCharType,NationalCheckDigitLength,NationalCheckDigitSeqNo,Updated,UpdatedBy) VALUES ('n','10','40',0,'es_ES',0,'Y','n','4','10','n','4','20','@A1@ @A2@ @A3@ @A4@ E-@P@ @C@ @R@ @CO@',540011,102,'EA','ESP',TO_TIMESTAMP('2022-01-21 16:30:49','YYYY-MM-DD HH24:MI:SS'),100,'Ceuta, Melilla','@BP@ @CON@ @A1@ @A2@ @A3@ @A4@ @P@ @C@ @CO@','@BP@ @CON@ @A1@ @A2@ @A3@ @A4@ @P@ @C@','N','Y','Y','N','N','Ceuta, Melilla','n','2','30',TO_TIMESTAMP('2022-01-21 16:30:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-21T14:30:49.813Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Country_Trl (AD_Language,C_Country_ID, Description,Name,RegionName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_Country_ID, t.Description,t.Name,t.RegionName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_Country t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.C_Country_ID=540011 AND NOT EXISTS (SELECT 1 FROM C_Country_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_Country_ID=t.C_Country_ID)
;

-- 2022-01-21T14:31:25.476Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Country_Trl SET Description='Canary Islands', Name='Canary Islands',Updated=TO_TIMESTAMP('2022-01-21 16:31:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_Country_ID=540010
;

-- Disable Spanish regions that have been transformed into countries is our masterdata

-- 2022-01-21T14:48:03.061Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Region SET IsActive='N',Updated=TO_TIMESTAMP('2022-01-21 16:48:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Region_ID=248
;

-- 2022-01-21T14:48:05.513Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Region SET IsActive='N',Updated=TO_TIMESTAMP('2022-01-21 16:48:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Region_ID=265
;

-- 2022-01-21T14:49:02.198Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Region SET IsActive='N',Updated=TO_TIMESTAMP('2022-01-21 16:49:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Region_ID=274
;

-- 2022-01-21T14:49:06.213Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Region SET IsActive='N',Updated=TO_TIMESTAMP('2022-01-21 16:49:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Region_ID=270
;


-- 2022-01-24T10:43:43.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,579118,566,0,11,540005,'SeqNo',TO_TIMESTAMP('2022-01-24 12:43:43','YYYY-MM-DD HH24:MI:SS'),100,'N','@SQL=SELECT COALESCE(MAX(seqNo),0)+10 AS DefaultValue FROM M_FreightCostDetail WHERE M_FreightCostDetail_ID=@M_FreightCostDetail_ID/-1@','Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','de.metas.swat',0,22,'"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Reihenfolge',0,0,TO_TIMESTAMP('2022-01-24 12:43:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-01-24T10:43:43.524Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=579118 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-01-24T10:43:43.548Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(566)
;

-- 2022-01-24T10:44:25.923Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ValueMin='0',Updated=TO_TIMESTAMP('2022-01-24 12:44:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579118
;

-- 2022-01-24T10:44:45.053Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2022-01-24 12:44:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=579118
;

-- 2022-01-24T10:44:49.160Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_FreightCostDetail','ALTER TABLE public.M_FreightCostDetail ADD COLUMN SeqNo NUMERIC(10)')
;

-- 2022-01-24T11:14:38.344Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,579118,677939,0,541820,0,TO_TIMESTAMP('2022-01-24 13:14:38','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',0,'D','"Reihenfolge" bestimmt die Reihenfolge der Einträge',0,'Y','Y','Y','N','N','N','N','N','Reihenfolge',0,70,0,1,1,TO_TIMESTAMP('2022-01-24 13:14:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-24T11:14:38.346Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=677939 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-01-24T11:14:38.349Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(566)
;

-- 2022-01-24T11:14:38.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=677939
;

-- 2022-01-24T11:14:38.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(677939)
;

-- 2022-01-24T11:15:29.106Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,677939,0,541820,542676,600073,'F',TO_TIMESTAMP('2022-01-24 13:15:28','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','Y','N','N','N',0,'Reihenfolge',5,0,0,TO_TIMESTAMP('2022-01-24 13:15:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-24T11:15:37.952Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-01-24 13:15:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=600073
;

-- 2022-01-24T11:15:37.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-01-24 13:15:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560129
;

-- 2022-01-24T11:15:37.958Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-01-24 13:15:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560131
;

-- 2022-01-24T11:15:37.962Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-01-24 13:15:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560130
;

-- 2022-01-24T11:15:37.964Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-01-24 13:15:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=599544
;

-- 2022-01-24T11:15:37.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-01-24 13:15:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560147
;
