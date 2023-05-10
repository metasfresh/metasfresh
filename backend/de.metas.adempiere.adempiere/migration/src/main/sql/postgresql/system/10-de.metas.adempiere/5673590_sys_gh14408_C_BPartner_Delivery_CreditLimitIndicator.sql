-- Field: Geschäftspartner(123,D) -> Statistik(540739,D) -> Delivery Credit Status
-- Column: C_BPartner_Stats.Delivery_CreditStatus
-- 2023-01-25T18:40:00.935Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585543,710536,0,540739,TO_TIMESTAMP('2023-01-25 20:40:00','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Delivery Credit Status',TO_TIMESTAMP('2023-01-25 20:40:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:40:00.937Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710536 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:40:00.939Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581938) 
;

-- 2023-01-25T18:40:00.953Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710536
;

-- 2023-01-25T18:40:00.955Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710536)
;

-- Field: Geschäftspartner(123,D) -> Statistik(540739,D) -> Delivery credit limit indicator %
-- Column: C_BPartner_Stats.DeliveryCreditLimitIndicator
-- 2023-01-25T18:40:01.043Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585624,710537,0,540739,TO_TIMESTAMP('2023-01-25 20:40:00','YYYY-MM-DD HH24:MI:SS'),100,'Percent of Credit used from the limit',10,'D','','Y','Y','N','N','N','N','N','Delivery credit limit indicator %',TO_TIMESTAMP('2023-01-25 20:40:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:40:01.045Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710537 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:40:01.047Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581960) 
;

-- 2023-01-25T18:40:01.050Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710537
;

-- 2023-01-25T18:40:01.051Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710537)
;

-- UI Element: Geschäftspartner(123,D) -> Statistik(540739,D) -> main -> 10 -> default.Delivery Credit Status
-- Column: C_BPartner_Stats.Delivery_CreditStatus
-- 2023-01-25T18:41:09.537Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710536,0,540739,1000038,614859,'F',TO_TIMESTAMP('2023-01-25 20:41:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Delivery Credit Status',80,0,0,TO_TIMESTAMP('2023-01-25 20:41:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_BPartner.DeliveryCreditLimitIndicator
-- 2023-01-25T18:45:54.946Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585625,581960,0,10,291,'DeliveryCreditLimitIndicator','(select MAX(DeliveryCreditLimitIndicator) from C_BPartner_Stats s where s.C_BPartner_ID = C_BPartner.C_BPartner_ID)',TO_TIMESTAMP('2023-01-25 20:45:54','YYYY-MM-DD HH24:MI:SS'),100,'N','Percent of Credit used from the limit','D',0,25,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Delivery credit limit indicator %',0,0,TO_TIMESTAMP('2023-01-25 20:45:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-25T18:45:54.948Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585625 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-25T18:45:54.951Z
/* DDL */  select update_Column_Translation_From_AD_Element(581960) 
;




-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> ESR Drucken
-- Column: C_BPartner.Fresh_IsPrintESR
-- 2023-01-25T18:46:56.485Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,65577,710538,0,220,TO_TIMESTAMP('2023-01-25 20:46:56','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','ESR Drucken',TO_TIMESTAMP('2023-01-25 20:46:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:46:56.487Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710538 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:46:56.488Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(55919) 
;

-- 2023-01-25T18:46:56.492Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710538
;

-- 2023-01-25T18:46:56.493Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710538)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Gebinde Saldo an drucken auf LS
-- Column: C_BPartner.Fresh_ContainersBalanceToPrintOnLS
-- 2023-01-25T18:46:56.605Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,65579,710539,0,220,TO_TIMESTAMP('2023-01-25 20:46:56','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','Y','N','N','N','N','N','Gebinde Saldo an drucken auf LS',TO_TIMESTAMP('2023-01-25 20:46:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:46:56.606Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710539 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:46:56.608Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542153) 
;

-- 2023-01-25T18:46:56.611Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710539
;

-- 2023-01-25T18:46:56.612Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710539)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Preis Liste Senden
-- Column: C_BPartner.Fresh_SendPriceList
-- 2023-01-25T18:46:56.716Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,65580,710540,0,220,TO_TIMESTAMP('2023-01-25 20:46:56','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','Y','N','N','N','N','N','Preis Liste Senden',TO_TIMESTAMP('2023-01-25 20:46:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:46:56.718Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710540 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:46:56.720Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542154) 
;

-- 2023-01-25T18:46:56.725Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710540
;

-- 2023-01-25T18:46:56.726Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710540)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> VK Preis auf LS
-- Column: C_BPartner.Fresh_RetailPriceOnLS
-- 2023-01-25T18:46:56.884Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,65581,710541,0,220,TO_TIMESTAMP('2023-01-25 20:46:56','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','Y','N','N','N','N','N','VK Preis auf LS',TO_TIMESTAMP('2023-01-25 20:46:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:46:56.885Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710541 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:46:56.887Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542155) 
;

-- 2023-01-25T18:46:56.893Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710541
;

-- 2023-01-25T18:46:56.893Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710541)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Auszeichnungspreis auf LS
-- Column: C_BPartner.Fresh_AwardPriceOnLS
-- 2023-01-25T18:46:56.998Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,65582,710542,0,220,TO_TIMESTAMP('2023-01-25 20:46:56','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','Y','N','N','N','N','N','Auszeichnungspreis auf LS',TO_TIMESTAMP('2023-01-25 20:46:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:46:56.999Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710542 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:46:57.002Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(55918) 
;

-- 2023-01-25T18:46:57.006Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710542
;

-- 2023-01-25T18:46:57.007Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710542)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Default contact
-- Column: C_BPartner.Default_User_ID
-- 2023-01-25T18:46:57.113Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,546704,710543,0,220,TO_TIMESTAMP('2023-01-25 20:46:57','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Default contact',TO_TIMESTAMP('2023-01-25 20:46:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:46:57.114Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710543 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:46:57.116Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541717) 
;

-- 2023-01-25T18:46:57.119Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710543
;

-- 2023-01-25T18:46:57.120Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710543)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Rechnungsstandort
-- Column: C_BPartner.Default_Bill_Location_ID
-- 2023-01-25T18:46:57.237Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,546705,710544,0,220,TO_TIMESTAMP('2023-01-25 20:46:57','YYYY-MM-DD HH24:MI:SS'),100,'Standort des Geschäftspartners für die Rechnungsstellung',10,'D','Y','Y','N','N','N','N','N','Rechnungsstandort',TO_TIMESTAMP('2023-01-25 20:46:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:46:57.239Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710544 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:46:57.240Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541718) 
;

-- 2023-01-25T18:46:57.243Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710544
;

-- 2023-01-25T18:46:57.244Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710544)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Default ship location
-- Column: C_BPartner.Default_Ship_Location_ID
-- 2023-01-25T18:46:57.361Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,546706,710545,0,220,TO_TIMESTAMP('2023-01-25 20:46:57','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Default ship location',TO_TIMESTAMP('2023-01-25 20:46:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:46:57.362Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710545 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:46:57.364Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541719) 
;

-- 2023-01-25T18:46:57.367Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710545
;

-- 2023-01-25T18:46:57.368Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710545)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Migration_Key
-- Column: C_BPartner.Migration_Key
-- 2023-01-25T18:46:57.473Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,549131,710546,0,220,TO_TIMESTAMP('2023-01-25 20:46:57','YYYY-MM-DD HH24:MI:SS'),100,'When data is imported from a an external datasource, this element can be used to identify the data record',255,'D','Y','Y','N','N','N','N','N','Migration_Key',TO_TIMESTAMP('2023-01-25 20:46:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:46:57.475Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710546 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:46:57.477Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542124) 
;

-- 2023-01-25T18:46:57.480Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710546
;

-- 2023-01-25T18:46:57.482Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710546)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Allow Line Discount
-- Column: C_BPartner.Fresh_AllowLineDiscount
-- 2023-01-25T18:46:57.590Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,549327,710547,0,220,TO_TIMESTAMP('2023-01-25 20:46:57','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Allow Line Discount',TO_TIMESTAMP('2023-01-25 20:46:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:46:57.592Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710547 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:46:57.594Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542151) 
;

-- 2023-01-25T18:46:57.598Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710547
;

-- 2023-01-25T18:46:57.599Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710547)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Anbauplanung
-- Column: C_BPartner.IsPlanning
-- 2023-01-25T18:46:57.718Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,549800,710548,0,220,TO_TIMESTAMP('2023-01-25 20:46:57','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Anbauplanung',TO_TIMESTAMP('2023-01-25 20:46:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:46:57.719Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710548 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:46:57.721Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542243) 
;

-- 2023-01-25T18:46:57.725Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710548
;

-- 2023-01-25T18:46:57.726Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710548)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> ADR-Zertifizierung
-- Column: C_BPartner.Fresh_Certification
-- 2023-01-25T18:46:57.831Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,549801,710549,0,220,TO_TIMESTAMP('2023-01-25 20:46:57','YYYY-MM-DD HH24:MI:SS'),100,16,'D','Y','Y','N','N','N','N','N','ADR-Zertifizierung',TO_TIMESTAMP('2023-01-25 20:46:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:46:57.832Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710549 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:46:57.834Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542244) 
;

-- 2023-01-25T18:46:57.839Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710549
;

-- 2023-01-25T18:46:57.840Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710549)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Flag Urproduzent
-- Column: C_BPartner.Fresh_Urproduzent
-- 2023-01-25T18:46:57.949Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,549845,710550,0,220,TO_TIMESTAMP('2023-01-25 20:46:57','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Flag Urproduzent',TO_TIMESTAMP('2023-01-25 20:46:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:46:57.950Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710550 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:46:57.952Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542256) 
;

-- 2023-01-25T18:46:57.955Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710550
;

-- 2023-01-25T18:46:57.956Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710550)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Flag Produzentenabrechnung
-- Column: C_BPartner.Fresh_Produzentenabrechnung
-- 2023-01-25T18:46:58.068Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,549846,710551,0,220,TO_TIMESTAMP('2023-01-25 20:46:57','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Flag Produzentenabrechnung',TO_TIMESTAMP('2023-01-25 20:46:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:46:58.069Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710551 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:46:58.071Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542257) 
;

-- 2023-01-25T18:46:58.075Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710551
;

-- 2023-01-25T18:46:58.076Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710551)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> AdRRegion
-- Column: C_BPartner.Fresh_AdRRegion
-- 2023-01-25T18:46:58.182Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,549865,710552,0,220,TO_TIMESTAMP('2023-01-25 20:46:58','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','Y','N','N','N','N','N','AdRRegion',TO_TIMESTAMP('2023-01-25 20:46:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:46:58.183Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710552 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:46:58.185Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542261) 
;

-- 2023-01-25T18:46:58.188Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710552
;

-- 2023-01-25T18:46:58.189Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710552)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> ADR Kunde
-- Column: C_BPartner.IsADRCustomer
-- 2023-01-25T18:46:58.288Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550255,710553,0,220,TO_TIMESTAMP('2023-01-25 20:46:58','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','ADR Kunde',TO_TIMESTAMP('2023-01-25 20:46:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:46:58.290Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710553 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:46:58.292Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542343) 
;

-- 2023-01-25T18:46:58.297Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710553
;

-- 2023-01-25T18:46:58.298Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710553)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> ADR Lieferant
-- Column: C_BPartner.IsADRVendor
-- 2023-01-25T18:46:58.407Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550445,710554,0,220,TO_TIMESTAMP('2023-01-25 20:46:58','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','ADR Lieferant',TO_TIMESTAMP('2023-01-25 20:46:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:46:58.408Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710554 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:46:58.409Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542398) 
;

-- 2023-01-25T18:46:58.413Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710554
;

-- 2023-01-25T18:46:58.414Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710554)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> AdRRegionVendor
-- Column: C_BPartner.Fresh_AdRVendorRegion
-- 2023-01-25T18:46:58.530Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550447,710555,0,220,TO_TIMESTAMP('2023-01-25 20:46:58','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','Y','N','N','N','N','N','AdRRegionVendor',TO_TIMESTAMP('2023-01-25 20:46:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:46:58.532Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710555 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:46:58.534Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542399) 
;

-- 2023-01-25T18:46:58.537Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710555
;

-- 2023-01-25T18:46:58.538Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710555)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Lager
-- Column: C_BPartner.M_Warehouse_ID
-- 2023-01-25T18:46:58.642Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550682,710556,0,220,TO_TIMESTAMP('2023-01-25 20:46:58','YYYY-MM-DD HH24:MI:SS'),100,'Lager oder Ort für Dienstleistung',10,'D','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','Y','N','N','N','N','N','Lager',TO_TIMESTAMP('2023-01-25 20:46:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:46:58.643Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710556 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:46:58.644Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(459) 
;

-- 2023-01-25T18:46:58.655Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710556
;

-- 2023-01-25T18:46:58.656Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710556)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Lieferung
-- Column: C_BPartner.PO_DeliveryViaRule
-- 2023-01-25T18:46:58.827Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550687,710557,0,220,TO_TIMESTAMP('2023-01-25 20:46:58','YYYY-MM-DD HH24:MI:SS'),100,'Wie der Auftrag geliefert wird',2,'D','"Lieferung durch" gibt an, auf welche Weise die Produkte geliefert werden sollen. Beispiel: wird abgeholt oder geliefert?','Y','Y','N','N','N','N','N','Lieferung',TO_TIMESTAMP('2023-01-25 20:46:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:46:58.829Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710557 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:46:58.831Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542451) 
;

-- 2023-01-25T18:46:58.835Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710557
;

-- 2023-01-25T18:46:58.837Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710557)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Aggregationsregel für Eingangsrechungen
-- Column: C_BPartner.PO_Invoice_Aggregation_ID
-- 2023-01-25T18:46:58.952Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551863,710558,0,220,TO_TIMESTAMP('2023-01-25 20:46:58','YYYY-MM-DD HH24:MI:SS'),100,'Optionale Konfigurationsregel für das Kopf-Aggregationsmerkmal bei Rechnungskandidaten mit "Verkaufsrechnung = Nein". Wenn nicht gesetzt wird ein Standardwert benutzt.',10,'D','Y','Y','N','N','N','N','N','Aggregationsregel für Eingangsrechungen',TO_TIMESTAMP('2023-01-25 20:46:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:46:58.953Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710558 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:46:58.954Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542718) 
;

-- 2023-01-25T18:46:58.957Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710558
;

-- 2023-01-25T18:46:58.958Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710558)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Aggregationsregel für Ausgangsrechungen
-- Column: C_BPartner.SO_Invoice_Aggregation_ID
-- 2023-01-25T18:46:59.064Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551864,710559,0,220,TO_TIMESTAMP('2023-01-25 20:46:58','YYYY-MM-DD HH24:MI:SS'),100,'Optionale Konfigurationsregel für das Kopf-Aggregationsmerkmal bei Rechnungskandidaten mit "Verkaufsrechnung = Ja". Wenn nicht gesetzt wird ein Standardwert benutzt.',10,'D','Y','Y','N','N','N','N','N','Aggregationsregel für Ausgangsrechungen',TO_TIMESTAMP('2023-01-25 20:46:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:46:59.065Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710559 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:46:59.066Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542719) 
;

-- 2023-01-25T18:46:59.069Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710559
;

-- 2023-01-25T18:46:59.070Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710559)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Gebinde auf Lsch. ausblenden
-- Column: C_BPartner.IsHidePackingMaterialInShipmentPrint
-- 2023-01-25T18:46:59.183Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,552285,710560,0,220,TO_TIMESTAMP('2023-01-25 20:46:59','YYYY-MM-DD HH24:MI:SS'),100,'Steuert ob Gebindezeilen beim Lieferscheindruck verborgen werden',1,'D','Wenn gesetzt, werden Gebindezeilen im ausgedruckten Lieferschein ausgeblendet','Y','Y','N','N','N','N','N','Gebinde auf Lsch. ausblenden',TO_TIMESTAMP('2023-01-25 20:46:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:46:59.184Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710560 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:46:59.185Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542803) 
;

-- 2023-01-25T18:46:59.188Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710560
;

-- 2023-01-25T18:46:59.189Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710560)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Aggregationsregel für Ausgangsrechungen (Rechnungsposition)
-- Column: C_BPartner.SO_InvoiceLine_Aggregation_ID
-- 2023-01-25T18:46:59.303Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,552547,710561,0,220,TO_TIMESTAMP('2023-01-25 20:46:59','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Aggregationsregel für Ausgangsrechungen (Rechnungsposition)',TO_TIMESTAMP('2023-01-25 20:46:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:46:59.304Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710561 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:46:59.306Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542849) 
;

-- 2023-01-25T18:46:59.309Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710561
;

-- 2023-01-25T18:46:59.310Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710561)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Aggregationsregel für Eingangsrechungen (Rechnungsposition)
-- Column: C_BPartner.PO_InvoiceLine_Aggregation_ID
-- 2023-01-25T18:46:59.408Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,552548,710562,0,220,TO_TIMESTAMP('2023-01-25 20:46:59','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Aggregationsregel für Eingangsrechungen (Rechnungsposition)',TO_TIMESTAMP('2023-01-25 20:46:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:46:59.410Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710562 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:46:59.411Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542848) 
;

-- 2023-01-25T18:46:59.415Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710562
;

-- 2023-01-25T18:46:59.415Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710562)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Kundengruppe
-- Column: C_BPartner.Customer_Group_ID
-- 2023-01-25T18:46:59.514Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,552717,710563,0,220,TO_TIMESTAMP('2023-01-25 20:46:59','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Kundengruppe',TO_TIMESTAMP('2023-01-25 20:46:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:46:59.515Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710563 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:46:59.516Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542888) 
;

-- 2023-01-25T18:46:59.520Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710563
;

-- 2023-01-25T18:46:59.520Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710563)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> keine Bestellkontrolle
-- Column: C_BPartner.IsDisableOrderCheckup
-- 2023-01-25T18:46:59.627Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,552778,710564,0,220,TO_TIMESTAMP('2023-01-25 20:46:59','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, dass beim Fertigstellen von Aufträgen zu diesem Kunden keine automatischen Bestellkontroll-Druckstücke erzeugt werden',1,'D','Y','Y','N','N','N','N','N','keine Bestellkontrolle',TO_TIMESTAMP('2023-01-25 20:46:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:46:59.628Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710564 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:46:59.629Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542907) 
;

-- 2023-01-25T18:46:59.632Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710564
;

-- 2023-01-25T18:46:59.633Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710564)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Statistik Gruppe
-- Column: C_BPartner.Salesgroup
-- 2023-01-25T18:46:59.737Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,553014,710565,0,220,TO_TIMESTAMP('2023-01-25 20:46:59','YYYY-MM-DD HH24:MI:SS'),100,250,'D','Y','Y','N','N','N','N','N','Statistik Gruppe',TO_TIMESTAMP('2023-01-25 20:46:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:46:59.738Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710565 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:46:59.740Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542949) 
;

-- 2023-01-25T18:46:59.743Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710565
;

-- 2023-01-25T18:46:59.743Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710565)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Sales Responsible
-- Column: C_BPartner.SalesRepIntern_ID
-- 2023-01-25T18:46:59.851Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557022,710566,0,220,TO_TIMESTAMP('2023-01-25 20:46:59','YYYY-MM-DD HH24:MI:SS'),100,'Sales Responsible Internal',10,'D','','Y','Y','N','N','N','N','N','Sales Responsible',TO_TIMESTAMP('2023-01-25 20:46:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:46:59.852Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710566 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:46:59.853Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543385) 
;

-- 2023-01-25T18:46:59.856Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710566
;

-- 2023-01-25T18:46:59.857Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710566)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Wiedervorlage Datum Innen
-- Column: C_BPartner.ReminderDateIntern
-- 2023-01-25T18:46:59.963Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557023,710567,0,220,TO_TIMESTAMP('2023-01-25 20:46:59','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','Y','N','N','N','N','N','Wiedervorlage Datum Innen',TO_TIMESTAMP('2023-01-25 20:46:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:46:59.965Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710567 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:46:59.966Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543386) 
;

-- 2023-01-25T18:46:59.969Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710567
;

-- 2023-01-25T18:46:59.969Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710567)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Wiedervorlage Datum Aussen
-- Column: C_BPartner.ReminderDateExtern
-- 2023-01-25T18:47:00.072Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557024,710568,0,220,TO_TIMESTAMP('2023-01-25 20:46:59','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','Y','N','N','N','N','N','Wiedervorlage Datum Aussen',TO_TIMESTAMP('2023-01-25 20:46:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:00.073Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710568 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:00.074Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543387) 
;

-- 2023-01-25T18:47:00.077Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710568
;

-- 2023-01-25T18:47:00.078Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710568)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Ort
-- Column: C_BPartner.City
-- 2023-01-25T18:47:00.199Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557178,710569,0,220,TO_TIMESTAMP('2023-01-25 20:47:00','YYYY-MM-DD HH24:MI:SS'),100,'Name des Ortes',60,'D','Bezeichnet einen einzelnen Ort in diesem Land oder dieser Region.','Y','Y','N','N','N','N','N','Ort',TO_TIMESTAMP('2023-01-25 20:47:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:00.200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710569 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:00.201Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(225) 
;

-- 2023-01-25T18:47:00.205Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710569
;

-- 2023-01-25T18:47:00.205Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710569)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> PLZ
-- Column: C_BPartner.Postal
-- 2023-01-25T18:47:00.303Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557179,710570,0,220,TO_TIMESTAMP('2023-01-25 20:47:00','YYYY-MM-DD HH24:MI:SS'),100,'Postleitzahl',10,'D','"PLZ" bezeichnet die Postleitzahl für diese Adresse oder dieses Postfach.','Y','Y','N','N','N','N','N','PLZ',TO_TIMESTAMP('2023-01-25 20:47:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:00.305Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710570 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:00.306Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(512) 
;

-- 2023-01-25T18:47:00.311Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710570
;

-- 2023-01-25T18:47:00.312Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710570)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Straße und Nr.
-- Column: C_BPartner.Address1
-- 2023-01-25T18:47:00.415Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557180,710571,0,220,TO_TIMESTAMP('2023-01-25 20:47:00','YYYY-MM-DD HH24:MI:SS'),100,'Adresszeile 1 für diesen Standort',100,'D','"Adresszeile 1" gibt die Anschrift für diesen Standort an.','Y','Y','N','N','N','N','N','Straße und Nr.',TO_TIMESTAMP('2023-01-25 20:47:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:00.416Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710571 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:00.417Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(156) 
;

-- 2023-01-25T18:47:00.421Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710571
;

-- 2023-01-25T18:47:00.422Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710571)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> eMail
-- Column: C_BPartner.EMail
-- 2023-01-25T18:47:00.510Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557181,710572,0,220,TO_TIMESTAMP('2023-01-25 20:47:00','YYYY-MM-DD HH24:MI:SS'),100,'EMail-Adresse',200,'D','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','Y','N','N','N','N','N','eMail',TO_TIMESTAMP('2023-01-25 20:47:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:00.512Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710572 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:00.514Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(881) 
;

-- 2023-01-25T18:47:00.519Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710572
;

-- 2023-01-25T18:47:00.520Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710572)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Global ID
-- Column: C_BPartner.GlobalId
-- 2023-01-25T18:47:00.631Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,558551,710573,0,220,TO_TIMESTAMP('2023-01-25 20:47:00','YYYY-MM-DD HH24:MI:SS'),100,60,'D','Y','Y','N','N','N','N','N','Global ID',TO_TIMESTAMP('2023-01-25 20:47:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:00.633Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710573 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:00.635Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543753) 
;

-- 2023-01-25T18:47:00.638Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710573
;

-- 2023-01-25T18:47:00.638Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710573)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Großhandelserlaubnis §52a AMG
-- Column: C_BPartner.IsPharmaWholesalePermission
-- 2023-01-25T18:47:00.740Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559720,710574,0,220,TO_TIMESTAMP('2023-01-25 20:47:00','YYYY-MM-DD HH24:MI:SS'),100,'',1,'D','Y','Y','N','N','N','N','N','Großhandelserlaubnis §52a AMG',TO_TIMESTAMP('2023-01-25 20:47:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:00.741Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710574 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:00.743Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543965) 
;

-- 2023-01-25T18:47:00.745Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710574
;

-- 2023-01-25T18:47:00.746Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710574)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Arzneivermittler §52c Abs.1-3 AMG
-- Column: C_BPartner.IsPharmaAgentPermission
-- 2023-01-25T18:47:00.859Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559721,710575,0,220,TO_TIMESTAMP('2023-01-25 20:47:00','YYYY-MM-DD HH24:MI:SS'),100,'',1,'D','Y','Y','N','N','N','N','N','Arzneivermittler §52c Abs.1-3 AMG',TO_TIMESTAMP('2023-01-25 20:47:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:00.861Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710575 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:00.863Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543967) 
;

-- 2023-01-25T18:47:00.866Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710575
;

-- 2023-01-25T18:47:00.867Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710575)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Tierärtzliche Hausapotheke §67 ApoG
-- Column: C_BPartner.IsVeterinaryPharmacyPermission
-- 2023-01-25T18:47:00.973Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559722,710576,0,220,TO_TIMESTAMP('2023-01-25 20:47:00','YYYY-MM-DD HH24:MI:SS'),100,'',1,'D','Y','Y','N','N','N','N','N','Tierärtzliche Hausapotheke §67 ApoG',TO_TIMESTAMP('2023-01-25 20:47:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:00.974Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710576 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:00.975Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543968) 
;

-- 2023-01-25T18:47:00.978Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710576
;

-- 2023-01-25T18:47:00.979Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710576)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Herstellererlaubnis §13 AMG
-- Column: C_BPartner.IsPharmaManufacturerPermission
-- 2023-01-25T18:47:01.076Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559723,710577,0,220,TO_TIMESTAMP('2023-01-25 20:47:00','YYYY-MM-DD HH24:MI:SS'),100,'',1,'D','Y','Y','N','N','N','N','N','Herstellererlaubnis §13 AMG',TO_TIMESTAMP('2023-01-25 20:47:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:01.078Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710577 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:01.079Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543966) 
;

-- 2023-01-25T18:47:01.082Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710577
;

-- 2023-01-25T18:47:01.082Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710577)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Herstellererlaubnis §13 AMG
-- Column: C_BPartner.IsPharmaVendorManufacturerPermission
-- 2023-01-25T18:47:01.177Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559725,710578,0,220,TO_TIMESTAMP('2023-01-25 20:47:01','YYYY-MM-DD HH24:MI:SS'),100,'',1,'D','Y','Y','N','N','N','N','N','Herstellererlaubnis §13 AMG',TO_TIMESTAMP('2023-01-25 20:47:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:01.178Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710578 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:01.180Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543971) 
;

-- 2023-01-25T18:47:01.183Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710578
;

-- 2023-01-25T18:47:01.183Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710578)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Arzneimittelvermittler §52c Abs.1-3 AMG
-- Column: C_BPartner.IsPharmaVendorAgentPermission
-- 2023-01-25T18:47:01.282Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559726,710579,0,220,TO_TIMESTAMP('2023-01-25 20:47:01','YYYY-MM-DD HH24:MI:SS'),100,'',1,'D','Y','Y','N','N','N','N','N','Arzneimittelvermittler §52c Abs.1-3 AMG',TO_TIMESTAMP('2023-01-25 20:47:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:01.283Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710579 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:01.285Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543970) 
;

-- 2023-01-25T18:47:01.287Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710579
;

-- 2023-01-25T18:47:01.288Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710579)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Großhandelserlaubnis §52a AMG
-- Column: C_BPartner.IsPharmaVendorWholesalePermission
-- 2023-01-25T18:47:01.397Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559727,710580,0,220,TO_TIMESTAMP('2023-01-25 20:47:01','YYYY-MM-DD HH24:MI:SS'),100,'',1,'D','Y','Y','N','N','N','N','N','Großhandelserlaubnis §52a AMG',TO_TIMESTAMP('2023-01-25 20:47:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:01.398Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710580 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:01.400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543969) 
;

-- 2023-01-25T18:47:01.402Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710580
;

-- 2023-01-25T18:47:01.403Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710580)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Lieferberechtigung
-- Column: C_BPartner.ShipmentPermissionPharma
-- 2023-01-25T18:47:01.505Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559728,710581,0,220,TO_TIMESTAMP('2023-01-25 20:47:01','YYYY-MM-DD HH24:MI:SS'),100,'',1,'D','Y','Y','N','N','N','N','N','Lieferberechtigung',TO_TIMESTAMP('2023-01-25 20:47:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:01.506Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710581 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:01.507Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543972) 
;

-- 2023-01-25T18:47:01.510Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710581
;

-- 2023-01-25T18:47:01.512Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710581)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Bezugsberechtigung
-- Column: C_BPartner.ReceiptPermissionPharma
-- 2023-01-25T18:47:01.611Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559729,710582,0,220,TO_TIMESTAMP('2023-01-25 20:47:01','YYYY-MM-DD HH24:MI:SS'),100,'',1,'D','Y','Y','N','N','N','N','N','Bezugsberechtigung',TO_TIMESTAMP('2023-01-25 20:47:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:01.612Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710582 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:01.613Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543973) 
;

-- 2023-01-25T18:47:01.616Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710582
;

-- 2023-01-25T18:47:01.617Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710582)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Datum Typ A Erlaubnis
-- Column: C_BPartner.ShipmentPermissionChangeDate
-- 2023-01-25T18:47:01.707Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559730,710583,0,220,TO_TIMESTAMP('2023-01-25 20:47:01','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','Y','N','N','N','N','N','Datum Typ A Erlaubnis',TO_TIMESTAMP('2023-01-25 20:47:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:01.708Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710583 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:01.709Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543974) 
;

-- 2023-01-25T18:47:01.712Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710583
;

-- 2023-01-25T18:47:01.713Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710583)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Datum Typ A Erlaubnis
-- Column: C_BPartner.ReceiptPermissionChangeDate
-- 2023-01-25T18:47:01.849Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559731,710584,0,220,TO_TIMESTAMP('2023-01-25 20:47:01','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','Y','N','N','N','N','N','Datum Typ A Erlaubnis',TO_TIMESTAMP('2023-01-25 20:47:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:01.851Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710584 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:01.852Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543975) 
;

-- 2023-01-25T18:47:01.855Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710584
;

-- 2023-01-25T18:47:01.856Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710584)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Aggregate Purchase Orders
-- Column: C_BPartner.IsAggregatePO
-- 2023-01-25T18:47:01.959Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560060,710585,0,220,TO_TIMESTAMP('2023-01-25 20:47:01','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Aggregate Purchase Orders',TO_TIMESTAMP('2023-01-25 20:47:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:01.960Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710585 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:01.961Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544052) 
;

-- 2023-01-25T18:47:01.964Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710585
;

-- 2023-01-25T18:47:01.965Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710585)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Betreuer Vertrieb
-- Column: C_BPartner.SalesResponsible
-- 2023-01-25T18:47:02.088Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560062,710586,0,220,TO_TIMESTAMP('2023-01-25 20:47:01','YYYY-MM-DD HH24:MI:SS'),100,60,'D','Y','Y','N','N','N','N','N','Betreuer Vertrieb',TO_TIMESTAMP('2023-01-25 20:47:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:02.089Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710586 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:02.091Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544053) 
;

-- 2023-01-25T18:47:02.093Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710586
;

-- 2023-01-25T18:47:02.094Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710586)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Einkaufsgr./Haupt-Apo.
-- Column: C_BPartner.PurchaseGroup
-- 2023-01-25T18:47:02.205Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560064,710587,0,220,TO_TIMESTAMP('2023-01-25 20:47:02','YYYY-MM-DD HH24:MI:SS'),100,60,'D','Y','Y','N','N','N','N','N','Einkaufsgr./Haupt-Apo.',TO_TIMESTAMP('2023-01-25 20:47:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:02.206Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710587 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:02.207Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544060) 
;

-- 2023-01-25T18:47:02.210Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710587
;

-- 2023-01-25T18:47:02.211Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710587)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Verbandszugehörigkeit
-- Column: C_BPartner.AssociationMembership
-- 2023-01-25T18:47:02.307Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560065,710588,0,220,TO_TIMESTAMP('2023-01-25 20:47:02','YYYY-MM-DD HH24:MI:SS'),100,60,'D','Y','Y','N','N','N','N','N','Verbandszugehörigkeit',TO_TIMESTAMP('2023-01-25 20:47:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:02.308Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710588 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:02.309Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544055) 
;

-- 2023-01-25T18:47:02.312Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710588
;

-- 2023-01-25T18:47:02.313Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710588)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Lieferberechtigung(Old)
-- Column: C_BPartner.ShipmentPermissionPharma_Old
-- 2023-01-25T18:47:02.412Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560066,710589,0,220,TO_TIMESTAMP('2023-01-25 20:47:02','YYYY-MM-DD HH24:MI:SS'),100,'Bezugsberechtigung',60,'D','Bezugsberechtigung
','Y','Y','N','N','N','N','N','Lieferberechtigung(Old)',TO_TIMESTAMP('2023-01-25 20:47:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:02.413Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710589 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:02.415Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544056) 
;

-- 2023-01-25T18:47:02.418Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710589
;

-- 2023-01-25T18:47:02.419Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710589)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Hersteller/GH/Apotheke
-- Column: C_BPartner.PermissionPharmaType
-- 2023-01-25T18:47:02.516Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560067,710590,0,220,TO_TIMESTAMP('2023-01-25 20:47:02','YYYY-MM-DD HH24:MI:SS'),100,60,'D','Y','Y','N','N','N','N','N','Hersteller/GH/Apotheke ',TO_TIMESTAMP('2023-01-25 20:47:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:02.518Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710590 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:02.520Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544057) 
;

-- 2023-01-25T18:47:02.525Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710590
;

-- 2023-01-25T18:47:02.526Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710590)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Mindesthaltbarkeit Tage
-- Column: C_BPartner.ShelfLifeMinDays
-- 2023-01-25T18:47:02.629Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560068,710591,0,220,TO_TIMESTAMP('2023-01-25 20:47:02','YYYY-MM-DD HH24:MI:SS'),100,'Mindesthaltbarkeit in Tagen, bezogen auf das Mindesthaltbarkeitsdatum einer Produktinstanz',3,'D','Miminum Shelf Life of products with Guarantee Date instance. If > 0 you cannot select products with a shelf life less than the minum shelf life, unless you select "Show All"','Y','Y','N','N','N','N','N','Mindesthaltbarkeit Tage',TO_TIMESTAMP('2023-01-25 20:47:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:02.630Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710591 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:02.632Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2264) 
;

-- 2023-01-25T18:47:02.636Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710591
;

-- 2023-01-25T18:47:02.637Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710591)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> KV-Gebiet (Info)
-- Column: C_BPartner.Region
-- 2023-01-25T18:47:02.734Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560069,710592,0,220,TO_TIMESTAMP('2023-01-25 20:47:02','YYYY-MM-DD HH24:MI:SS'),100,'',60,'D','','Y','Y','N','N','N','N','N','KV-Gebiet (Info) ',TO_TIMESTAMP('2023-01-25 20:47:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:02.735Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710592 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:02.736Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544061) 
;

-- 2023-01-25T18:47:02.740Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710592
;

-- 2023-01-25T18:47:02.740Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710592)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Samstag Öffnungszeiten
-- Column: C_BPartner.WeekendOpeningTimes
-- 2023-01-25T18:47:02.855Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560070,710593,0,220,TO_TIMESTAMP('2023-01-25 20:47:02','YYYY-MM-DD HH24:MI:SS'),100,60,'D','Y','Y','N','N','N','N','N','Samstag Öffnungszeiten ',TO_TIMESTAMP('2023-01-25 20:47:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:02.858Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710593 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:02.860Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544058) 
;

-- 2023-01-25T18:47:02.863Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710593
;

-- 2023-01-25T18:47:02.864Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710593)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> URL3
-- Column: C_BPartner.URL3
-- 2023-01-25T18:47:02.966Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560188,710594,0,220,TO_TIMESTAMP('2023-01-25 20:47:02','YYYY-MM-DD HH24:MI:SS'),100,'Full URL address - e.g. http://www.adempiere.org',400,'D','The URL defines an fully qualified web address like http://www.adempiere.org','Y','Y','N','N','N','N','N','URL3',TO_TIMESTAMP('2023-01-25 20:47:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:02.968Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710594 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:02.969Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544091) 
;

-- 2023-01-25T18:47:02.972Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710594
;

-- 2023-01-25T18:47:02.972Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710594)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Betreuer Einkauf
-- Column: C_BPartner.VendorResponsible
-- 2023-01-25T18:47:03.084Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560212,710595,0,220,TO_TIMESTAMP('2023-01-25 20:47:02','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','Y','N','N','N','N','N','Betreuer Einkauf ',TO_TIMESTAMP('2023-01-25 20:47:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:03.085Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710595 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:03.086Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544099) 
;

-- 2023-01-25T18:47:03.089Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710595
;

-- 2023-01-25T18:47:03.090Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710595)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Mindestbestellwertinfo
-- Column: C_BPartner.MinimumOrderValue
-- 2023-01-25T18:47:03.200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560213,710596,0,220,TO_TIMESTAMP('2023-01-25 20:47:03','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','Y','N','N','N','N','N','Mindestbestellwertinfo',TO_TIMESTAMP('2023-01-25 20:47:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:03.201Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710596 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:03.202Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544100) 
;

-- 2023-01-25T18:47:03.205Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710596
;

-- 2023-01-25T18:47:03.206Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710596)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Retouren-Telefax
-- Column: C_BPartner.RetourFax
-- 2023-01-25T18:47:03.313Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560214,710597,0,220,TO_TIMESTAMP('2023-01-25 20:47:03','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','Y','N','N','N','N','N','Retouren-Telefax ',TO_TIMESTAMP('2023-01-25 20:47:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:03.315Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710597 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:03.316Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544101) 
;

-- 2023-01-25T18:47:03.319Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710597
;

-- 2023-01-25T18:47:03.320Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710597)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Pharma_Phone
-- Column: C_BPartner.Pharma_Phone
-- 2023-01-25T18:47:03.433Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560215,710598,0,220,TO_TIMESTAMP('2023-01-25 20:47:03','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','Y','N','N','N','N','N','Pharma_Phone',TO_TIMESTAMP('2023-01-25 20:47:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:03.434Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710598 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:03.435Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544102) 
;

-- 2023-01-25T18:47:03.438Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710598
;

-- 2023-01-25T18:47:03.439Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710598)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Contacts
-- Column: C_BPartner.Contacts
-- 2023-01-25T18:47:03.540Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560216,710599,0,220,TO_TIMESTAMP('2023-01-25 20:47:03','YYYY-MM-DD HH24:MI:SS'),100,2000,'D','Y','Y','N','N','N','N','N','Contacts',TO_TIMESTAMP('2023-01-25 20:47:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:03.541Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710599 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:03.542Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544103) 
;

-- 2023-01-25T18:47:03.545Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710599
;

-- 2023-01-25T18:47:03.546Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710599)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Lieferanten Kategorie
-- Column: C_BPartner.VendorCategory
-- 2023-01-25T18:47:03.638Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560219,710600,0,220,TO_TIMESTAMP('2023-01-25 20:47:03','YYYY-MM-DD HH24:MI:SS'),100,'Lieferanten Kategorie',255,'D','','Y','Y','N','N','N','N','N','Lieferanten Kategorie',TO_TIMESTAMP('2023-01-25 20:47:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:03.639Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710600 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:03.640Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(622) 
;

-- 2023-01-25T18:47:03.647Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710600
;

-- 2023-01-25T18:47:03.648Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710600)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Qualification
-- Column: C_BPartner.Qualification
-- 2023-01-25T18:47:03.748Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560220,710601,0,220,TO_TIMESTAMP('2023-01-25 20:47:03','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','Y','N','N','N','N','N','Qualification ',TO_TIMESTAMP('2023-01-25 20:47:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:03.749Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710601 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:03.750Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544098) 
;

-- 2023-01-25T18:47:03.753Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710601
;

-- 2023-01-25T18:47:03.754Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710601)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Eigene-Kd. Nr.
-- Column: C_BPartner.CustomerNoAtVendor
-- 2023-01-25T18:47:03.871Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560221,710602,0,220,TO_TIMESTAMP('2023-01-25 20:47:03','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','Y','N','N','N','N','N','Eigene-Kd. Nr. ',TO_TIMESTAMP('2023-01-25 20:47:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:03.872Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710602 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:03.873Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544104) 
;

-- 2023-01-25T18:47:03.876Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710602
;

-- 2023-01-25T18:47:03.877Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710602)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Pharma_Fax
-- Column: C_BPartner.Pharma_Fax
-- 2023-01-25T18:47:03.975Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560227,710603,0,220,TO_TIMESTAMP('2023-01-25 20:47:03','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','Y','N','N','N','N','N','Pharma_Fax',TO_TIMESTAMP('2023-01-25 20:47:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:03.976Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710603 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:03.978Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544105) 
;

-- 2023-01-25T18:47:03.981Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710603
;

-- 2023-01-25T18:47:03.982Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710603)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Status-Info
-- Column: C_BPartner.StatusInfo
-- 2023-01-25T18:47:04.089Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560228,710604,0,220,TO_TIMESTAMP('2023-01-25 20:47:03','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','Y','N','N','N','N','N','Status-Info',TO_TIMESTAMP('2023-01-25 20:47:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:04.090Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710604 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:04.092Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544106) 
;

-- 2023-01-25T18:47:04.094Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710604
;

-- 2023-01-25T18:47:04.095Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710604)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Manufacturer
-- Column: C_BPartner.IsManufacturer
-- 2023-01-25T18:47:04.192Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560428,710605,0,220,TO_TIMESTAMP('2023-01-25 20:47:04','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Manufacturer',TO_TIMESTAMP('2023-01-25 20:47:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:04.193Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710605 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:04.194Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544129) 
;

-- 2023-01-25T18:47:04.197Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710605
;

-- 2023-01-25T18:47:04.198Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710605)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> GDP Certificate
-- Column: C_BPartner.GDPCertificateCustomer
-- 2023-01-25T18:47:04.294Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560500,710606,0,220,TO_TIMESTAMP('2023-01-25 20:47:04','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','GDP Certificate',TO_TIMESTAMP('2023-01-25 20:47:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:04.296Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710606 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:04.297Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544138) 
;

-- 2023-01-25T18:47:04.300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710606
;

-- 2023-01-25T18:47:04.301Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710606)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> GDP Certificate
-- Column: C_BPartner.GDPCertificateVendor
-- 2023-01-25T18:47:04.400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560501,710607,0,220,TO_TIMESTAMP('2023-01-25 20:47:04','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','GDP Certificate',TO_TIMESTAMP('2023-01-25 20:47:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:04.401Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710607 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:04.402Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544139) 
;

-- 2023-01-25T18:47:04.405Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710607
;

-- 2023-01-25T18:47:04.405Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710607)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> QMS Certificate
-- Column: C_BPartner.QMSCertificateCustomer
-- 2023-01-25T18:47:04.497Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560502,710608,0,220,TO_TIMESTAMP('2023-01-25 20:47:04','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','QMS Certificate',TO_TIMESTAMP('2023-01-25 20:47:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:04.498Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710608 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:04.500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544140) 
;

-- 2023-01-25T18:47:04.503Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710608
;

-- 2023-01-25T18:47:04.504Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710608)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> QMS Certificate
-- Column: C_BPartner.QMSCertificateVendor
-- 2023-01-25T18:47:04.609Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560503,710609,0,220,TO_TIMESTAMP('2023-01-25 20:47:04','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','QMS Certificate',TO_TIMESTAMP('2023-01-25 20:47:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:04.610Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710609 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:04.611Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544141) 
;

-- 2023-01-25T18:47:04.614Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710609
;

-- 2023-01-25T18:47:04.615Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710609)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Self Disclosure
-- Column: C_BPartner.SelfDisclosureCustomer
-- 2023-01-25T18:47:04.719Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560504,710610,0,220,TO_TIMESTAMP('2023-01-25 20:47:04','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Self Disclosure',TO_TIMESTAMP('2023-01-25 20:47:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:04.720Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710610 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:04.721Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544145) 
;

-- 2023-01-25T18:47:04.724Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710610
;

-- 2023-01-25T18:47:04.725Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710610)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Self Disclosure
-- Column: C_BPartner.SelfDisclosureVendor
-- 2023-01-25T18:47:04.881Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560505,710611,0,220,TO_TIMESTAMP('2023-01-25 20:47:04','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Self Disclosure',TO_TIMESTAMP('2023-01-25 20:47:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:04.884Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710611 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:04.885Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544144) 
;

-- 2023-01-25T18:47:04.890Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710611
;

-- 2023-01-25T18:47:04.891Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710611)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Certificate of Registration
-- Column: C_BPartner.CertificateOfRegistrationCustomer
-- 2023-01-25T18:47:05.005Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560506,710612,0,220,TO_TIMESTAMP('2023-01-25 20:47:04','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Certificate of Registration',TO_TIMESTAMP('2023-01-25 20:47:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:05.006Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710612 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:05.008Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544142) 
;

-- 2023-01-25T18:47:05.010Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710612
;

-- 2023-01-25T18:47:05.011Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710612)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Certificate of Registration
-- Column: C_BPartner.CertificateOfRegistrationVendor
-- 2023-01-25T18:47:05.117Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560507,710613,0,220,TO_TIMESTAMP('2023-01-25 20:47:05','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Certificate of Registration',TO_TIMESTAMP('2023-01-25 20:47:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:05.118Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710613 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:05.119Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544143) 
;

-- 2023-01-25T18:47:05.122Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710613
;

-- 2023-01-25T18:47:05.123Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710613)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Contact Status Information
-- Column: C_BPartner.ContactStatusInfoCustomer
-- 2023-01-25T18:47:05.229Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560508,710614,0,220,TO_TIMESTAMP('2023-01-25 20:47:05','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Contact Status Information',TO_TIMESTAMP('2023-01-25 20:47:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:05.230Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710614 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:05.232Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544146) 
;

-- 2023-01-25T18:47:05.234Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710614
;

-- 2023-01-25T18:47:05.235Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710614)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Contact Status Information
-- Column: C_BPartner.ContactStatusInfoVendor
-- 2023-01-25T18:47:05.344Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560509,710615,0,220,TO_TIMESTAMP('2023-01-25 20:47:05','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Contact Status Information',TO_TIMESTAMP('2023-01-25 20:47:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:05.345Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710615 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:05.347Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544147) 
;

-- 2023-01-25T18:47:05.350Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710615
;

-- 2023-01-25T18:47:05.351Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710615)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Externe ID
-- Column: C_BPartner.ExternalId
-- 2023-01-25T18:47:05.458Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563299,710616,0,220,TO_TIMESTAMP('2023-01-25 20:47:05','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','Y','N','N','N','N','N','Externe ID',TO_TIMESTAMP('2023-01-25 20:47:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:05.459Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710616 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:05.461Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543939) 
;

-- 2023-01-25T18:47:05.465Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710616
;

-- 2023-01-25T18:47:05.465Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710616)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Anbieter-Nr.
-- Column: C_BPartner.IFA_Manufacturer
-- 2023-01-25T18:47:05.568Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564554,710617,0,220,TO_TIMESTAMP('2023-01-25 20:47:05','YYYY-MM-DD HH24:MI:SS'),100,5,'D','Y','Y','N','N','N','N','N','Anbieter-Nr.',TO_TIMESTAMP('2023-01-25 20:47:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:05.570Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710617 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:05.571Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576251) 
;

-- 2023-01-25T18:47:05.574Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710617
;

-- 2023-01-25T18:47:05.575Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710617)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Betäubungsmittel
-- Column: C_BPartner.IsPharmaCustomerNarcoticsPermission
-- 2023-01-25T18:47:05.681Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,567353,710618,0,220,TO_TIMESTAMP('2023-01-25 20:47:05','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Betäubungsmittel',TO_TIMESTAMP('2023-01-25 20:47:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:05.682Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710618 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:05.683Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576507) 
;

-- 2023-01-25T18:47:05.686Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710618
;

-- 2023-01-25T18:47:05.686Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710618)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Betäubungsmittel
-- Column: C_BPartner.IsPharmaVendorNarcoticsPermission
-- 2023-01-25T18:47:05.795Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,567354,710619,0,220,TO_TIMESTAMP('2023-01-25 20:47:05','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Betäubungsmittel',TO_TIMESTAMP('2023-01-25 20:47:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:05.797Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710619 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:05.798Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576506) 
;

-- 2023-01-25T18:47:05.801Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710619
;

-- 2023-01-25T18:47:05.801Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710619)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> BTM
-- Column: C_BPartner.BTM
-- 2023-01-25T18:47:05.892Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,567934,710620,0,220,TO_TIMESTAMP('2023-01-25 20:47:05','YYYY-MM-DD HH24:MI:SS'),100,25,'D','Y','Y','N','N','N','N','N','BTM',TO_TIMESTAMP('2023-01-25 20:47:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:05.893Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710620 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:05.894Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576728) 
;

-- 2023-01-25T18:47:05.897Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710620
;

-- 2023-01-25T18:47:05.898Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710620)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Telefon (alternativ)
-- Column: C_BPartner.Phone2
-- 2023-01-25T18:47:06.004Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568274,710621,0,220,TO_TIMESTAMP('2023-01-25 20:47:05','YYYY-MM-DD HH24:MI:SS'),100,'Alternative Telefonnummer',255,'D','','Y','Y','N','N','N','N','N','Telefon (alternativ)',TO_TIMESTAMP('2023-01-25 20:47:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:06.005Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710621 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:06.007Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(506) 
;

-- 2023-01-25T18:47:06.011Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710621
;

-- 2023-01-25T18:47:06.012Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710621)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> URL2
-- Column: C_BPartner.URL2
-- 2023-01-25T18:47:06.278Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568428,710622,0,220,TO_TIMESTAMP('2023-01-25 20:47:06','YYYY-MM-DD HH24:MI:SS'),100,'Vollständige Web-Addresse, z.B. https://metasfresh.com/',512,'D','','Y','Y','N','N','N','N','N','URL2',TO_TIMESTAMP('2023-01-25 20:47:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:06.279Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710622 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:06.282Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576916) 
;

-- 2023-01-25T18:47:06.285Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710622
;

-- 2023-01-25T18:47:06.286Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710622)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Aktionsbezug
-- Column: C_BPartner.IsAllowActionPrice
-- 2023-01-25T18:47:06.386Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568477,710623,0,220,TO_TIMESTAMP('2023-01-25 20:47:06','YYYY-MM-DD HH24:MI:SS'),100,'Wenn auf "ja" gesetzt, dann werden bei der Preisberechnung auch Aktionspreise berücksichtigt.',1,'D','Y','Y','N','N','N','N','N','Aktionsbezug',TO_TIMESTAMP('2023-01-25 20:47:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:06.388Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710623 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:06.389Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576946) 
;

-- 2023-01-25T18:47:06.391Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710623
;

-- 2023-01-25T18:47:06.392Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710623)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> PLV von Basis
-- Column: C_BPartner.IsAllowPriceMutation
-- 2023-01-25T18:47:06.501Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568595,710624,0,220,TO_TIMESTAMP('2023-01-25 20:47:06','YYYY-MM-DD HH24:MI:SS'),100,'Von Basis derivierte PLV erlauben ',1,'D','Y','Y','N','N','N','N','N','PLV von Basis',TO_TIMESTAMP('2023-01-25 20:47:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:06.503Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710624 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:06.504Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577030) 
;

-- 2023-01-25T18:47:06.507Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710624
;

-- 2023-01-25T18:47:06.508Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710624)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Standard Lieferstadt
-- Column: C_BPartner.DefaultShipTo_City
-- 2023-01-25T18:47:06.607Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569793,710625,0,220,TO_TIMESTAMP('2023-01-25 20:47:06','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','Y','N','N','N','N','N','Standard Lieferstadt',TO_TIMESTAMP('2023-01-25 20:47:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:06.608Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710625 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:06.609Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577445) 
;

-- 2023-01-25T18:47:06.612Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710625
;

-- 2023-01-25T18:47:06.612Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710625)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Standard Liefer PLZ
-- Column: C_BPartner.DefaultShipTo_Postal
-- 2023-01-25T18:47:06.716Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569794,710626,0,220,TO_TIMESTAMP('2023-01-25 20:47:06','YYYY-MM-DD HH24:MI:SS'),100,40,'D','Y','Y','N','N','N','N','N','Standard Liefer PLZ',TO_TIMESTAMP('2023-01-25 20:47:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:06.717Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710626 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:06.718Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577446) 
;

-- 2023-01-25T18:47:06.720Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710626
;

-- 2023-01-25T18:47:06.721Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710626)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Datum Haddex Prüfung
-- Column: C_BPartner.DateHaddexCheck
-- 2023-01-25T18:47:06.820Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572467,710627,0,220,TO_TIMESTAMP('2023-01-25 20:47:06','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','Y','N','N','N','N','N','Datum Haddex Prüfung',TO_TIMESTAMP('2023-01-25 20:47:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:06.821Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710627 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:06.822Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578608) 
;

-- 2023-01-25T18:47:06.825Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710627
;

-- 2023-01-25T18:47:06.826Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710627)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Haddex Kontroll Nr.
-- Column: C_BPartner.HaddexControlNr
-- 2023-01-25T18:47:06.916Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572468,710628,0,220,TO_TIMESTAMP('2023-01-25 20:47:06','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','Haddex Kontroll Nr.',TO_TIMESTAMP('2023-01-25 20:47:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:06.918Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710628 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:06.919Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578609) 
;

-- 2023-01-25T18:47:06.921Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710628
;

-- 2023-01-25T18:47:06.922Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710628)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Haddex Prüfung erforderlich
-- Column: C_BPartner.IsHaddexCheck
-- 2023-01-25T18:47:07.018Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572470,710629,0,220,TO_TIMESTAMP('2023-01-25 20:47:06','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Haddex Prüfung erforderlich',TO_TIMESTAMP('2023-01-25 20:47:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:07.019Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710629 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:07.021Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578664) 
;

-- 2023-01-25T18:47:07.024Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710629
;

-- 2023-01-25T18:47:07.025Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710629)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Org Mapping
-- Column: C_BPartner.AD_Org_Mapping_ID
-- 2023-01-25T18:47:07.124Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573097,710630,0,220,TO_TIMESTAMP('2023-01-25 20:47:07','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Org Mapping',TO_TIMESTAMP('2023-01-25 20:47:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:07.125Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710630 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:07.126Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578815) 
;

-- 2023-01-25T18:47:07.129Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710630
;

-- 2023-01-25T18:47:07.130Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710630)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> PO_Incoterm
-- Column: C_BPartner.PO_Incoterm
-- 2023-01-25T18:47:07.226Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573903,710631,0,220,TO_TIMESTAMP('2023-01-25 20:47:07','YYYY-MM-DD HH24:MI:SS'),100,'Internationale Handelsklauseln (engl. International Commercial Terms)',3,'D','Incoterms (International Commercial Terms, deutsch: Internationale Handelsklauseln) sind eine Reihe internationaler Regeln zur Interpretation spezifizierter Handelsbedingungen im Außenhandelsgeschäft.

Die Incoterms wurden von der Internationalen Handelskammer (International Chamber of Commerce, ICC) erstmals 1936 aufgestellt, um eine gemeinsame Basis für den internationalen Handel zu schaffen. Sie regeln vor allem die Art und Weise der Lieferung von Gütern. Die Bestimmungen legen fest, welche Transportkosten der Verkäufer, welche der Käufer zu tragen hat und wer im Falle eines Verlustes der Ware das finanzielle Risiko trägt. Die Incoterms geben jedoch keine Auskunft darüber, wann und wo das Eigentum an der Ware von dem Verkäufer auf den Käufer übergeht. Der Stand der Incoterms wird durch Angabe der Jahreszahl gekennzeichnet. Aktuell gelten die Incoterms 2000 (6. Revision).','Y','Y','N','N','N','N','N','PO_Incoterm',TO_TIMESTAMP('2023-01-25 20:47:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:07.227Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710631 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:07.228Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579178) 
;

-- 2023-01-25T18:47:07.231Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710631
;

-- 2023-01-25T18:47:07.231Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710631)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Archiviert
-- Column: C_BPartner.IsArchived
-- 2023-01-25T18:47:07.331Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573948,710632,0,220,TO_TIMESTAMP('2023-01-25 20:47:07','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Archiviert',TO_TIMESTAMP('2023-01-25 20:47:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:07.332Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710632 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:07.334Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578977) 
;

-- 2023-01-25T18:47:07.336Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710632
;

-- 2023-01-25T18:47:07.337Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710632)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Is Alberta doctor
-- Column: C_BPartner.IsAlbertaDoctor
-- 2023-01-25T18:47:07.434Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573949,710633,0,220,TO_TIMESTAMP('2023-01-25 20:47:07','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Is Alberta doctor',TO_TIMESTAMP('2023-01-25 20:47:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:07.436Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710633 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:07.437Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579162) 
;

-- 2023-01-25T18:47:07.440Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710633
;

-- 2023-01-25T18:47:07.441Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710633)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Titel
-- Column: C_BPartner.AlbertaTitle
-- 2023-01-25T18:47:07.543Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573950,710634,0,220,TO_TIMESTAMP('2023-01-25 20:47:07','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','Y','N','N','N','N','N','Titel',TO_TIMESTAMP('2023-01-25 20:47:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:07.544Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710634 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:07.546Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579194) 
;

-- 2023-01-25T18:47:07.549Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710634
;

-- 2023-01-25T18:47:07.549Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710634)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Kurztitel
-- Column: C_BPartner.TitleShort
-- 2023-01-25T18:47:07.658Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573951,710635,0,220,TO_TIMESTAMP('2023-01-25 20:47:07','YYYY-MM-DD HH24:MI:SS'),100,255,'D','Y','Y','N','N','N','N','N','Kurztitel',TO_TIMESTAMP('2023-01-25 20:47:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:07.659Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710635 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:07.661Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579163) 
;

-- 2023-01-25T18:47:07.663Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710635
;

-- 2023-01-25T18:47:07.664Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710635)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Role
-- Column: C_BPartner.AlbertaRole
-- 2023-01-25T18:47:07.763Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573952,710636,0,220,TO_TIMESTAMP('2023-01-25 20:47:07','YYYY-MM-DD HH24:MI:SS'),100,255,'D','','Y','Y','N','N','N','N','N','Role',TO_TIMESTAMP('2023-01-25 20:47:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:07.765Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710636 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:07.766Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579189) 
;

-- 2023-01-25T18:47:07.769Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710636
;

-- 2023-01-25T18:47:07.769Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710636)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Keine Werbung
-- Column: C_BPartner.ExcludeFromPromotions
-- 2023-01-25T18:47:07.912Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574474,710637,0,220,TO_TIMESTAMP('2023-01-25 20:47:07','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Keine Werbung',TO_TIMESTAMP('2023-01-25 20:47:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:07.914Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710637 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:07.916Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579361) 
;

-- 2023-01-25T18:47:07.919Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710637
;

-- 2023-01-25T18:47:07.919Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710637)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Referrer
-- Column: C_BPartner.Referrer
-- 2023-01-25T18:47:08.051Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574475,710638,0,220,TO_TIMESTAMP('2023-01-25 20:47:07','YYYY-MM-DD HH24:MI:SS'),100,'Referring web address',255,'D','Y','Y','N','N','N','N','N','Referrer',TO_TIMESTAMP('2023-01-25 20:47:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:08.052Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710638 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:08.054Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1429) 
;

-- 2023-01-25T18:47:08.056Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710638
;

-- 2023-01-25T18:47:08.057Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710638)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> MKTG_Campaign
-- Column: C_BPartner.MKTG_Campaign_ID
-- 2023-01-25T18:47:08.155Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574563,710639,0,220,TO_TIMESTAMP('2023-01-25 20:47:08','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','MKTG_Campaign',TO_TIMESTAMP('2023-01-25 20:47:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:08.156Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710639 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:08.157Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544034) 
;

-- 2023-01-25T18:47:08.160Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710639
;

-- 2023-01-25T18:47:08.161Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710639)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Rechnungsstellung (Kreditoren)
-- Column: C_BPartner.PO_InvoiceRule
-- 2023-01-25T18:47:08.247Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,578381,710640,0,220,TO_TIMESTAMP('2023-01-25 20:47:08','YYYY-MM-DD HH24:MI:SS'),100,250,'D','Y','Y','N','N','N','N','N','Rechnungsstellung (Kreditoren)',TO_TIMESTAMP('2023-01-25 20:47:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:08.248Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710640 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:08.249Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580209) 
;

-- 2023-01-25T18:47:08.252Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710640
;

-- 2023-01-25T18:47:08.253Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710640)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Alter Wert
-- Column: C_BPartner.OldValue
-- 2023-01-25T18:47:08.346Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579084,710641,0,220,TO_TIMESTAMP('2023-01-25 20:47:08','YYYY-MM-DD HH24:MI:SS'),100,'The old file data',250,'D','Old data overwritten in the field','Y','Y','N','N','N','N','N','Alter Wert',TO_TIMESTAMP('2023-01-25 20:47:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:08.347Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710641 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:08.349Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2066) 
;

-- 2023-01-25T18:47:08.352Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710641
;

-- 2023-01-25T18:47:08.353Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710641)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Suchschlüssel Alt (Kunde)
-- Column: C_BPartner.Old_Value_Customer
-- 2023-01-25T18:47:08.457Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579093,710642,0,220,TO_TIMESTAMP('2023-01-25 20:47:08','YYYY-MM-DD HH24:MI:SS'),100,250,'D','Y','Y','N','N','N','N','N','Suchschlüssel Alt (Kunde)',TO_TIMESTAMP('2023-01-25 20:47:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:08.459Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710642 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:08.460Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580484) 
;

-- 2023-01-25T18:47:08.463Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710642
;

-- 2023-01-25T18:47:08.464Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710642)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Suchschlüssel Alt (Lieferant)
-- Column: C_BPartner.Old_Value_Vendor
-- 2023-01-25T18:47:08.559Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579094,710643,0,220,TO_TIMESTAMP('2023-01-25 20:47:08','YYYY-MM-DD HH24:MI:SS'),100,250,'D','Y','Y','N','N','N','N','N','Suchschlüssel Alt (Lieferant)',TO_TIMESTAMP('2023-01-25 20:47:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:08.560Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710643 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:08.561Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580485) 
;

-- 2023-01-25T18:47:08.564Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710643
;

-- 2023-01-25T18:47:08.565Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710643)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Incoterms (Lieferant)
-- Column: C_BPartner.C_Incoterms_Vendor_ID
-- 2023-01-25T18:47:08.664Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579095,710644,0,220,TO_TIMESTAMP('2023-01-25 20:47:08','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Incoterms (Lieferant)',TO_TIMESTAMP('2023-01-25 20:47:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:08.665Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710644 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:08.666Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580487) 
;

-- 2023-01-25T18:47:08.669Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710644
;

-- 2023-01-25T18:47:08.669Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710644)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Incoterms (Kunde)
-- Column: C_BPartner.C_Incoterms_Customer_ID
-- 2023-01-25T18:47:08.766Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579096,710645,0,220,TO_TIMESTAMP('2023-01-25 20:47:08','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Incoterms (Kunde)',TO_TIMESTAMP('2023-01-25 20:47:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:08.767Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710645 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:08.769Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580486) 
;

-- 2023-01-25T18:47:08.771Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710645
;

-- 2023-01-25T18:47:08.772Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710645)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Section Group Partner
-- Column: C_BPartner.Section_Group_Partner_ID
-- 2023-01-25T18:47:08.913Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584076,710646,0,220,TO_TIMESTAMP('2023-01-25 20:47:08','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Section Group Partner',TO_TIMESTAMP('2023-01-25 20:47:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:08.925Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710646 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:08.927Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581322) 
;

-- 2023-01-25T18:47:08.930Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710646
;

-- 2023-01-25T18:47:08.931Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710646)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Section Group Partner
-- Column: C_BPartner.IsSectionGroupPartner
-- 2023-01-25T18:47:09.029Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584077,710647,0,220,TO_TIMESTAMP('2023-01-25 20:47:08','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Section Group Partner',TO_TIMESTAMP('2023-01-25 20:47:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:09.030Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710647 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:09.032Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581323) 
;

-- 2023-01-25T18:47:09.035Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710647
;

-- 2023-01-25T18:47:09.036Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710647)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Section Partner
-- Column: C_BPartner.IsSectionPartner
-- 2023-01-25T18:47:09.132Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584081,710648,0,220,TO_TIMESTAMP('2023-01-25 20:47:09','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Section Partner',TO_TIMESTAMP('2023-01-25 20:47:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:09.133Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710648 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:09.134Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581324) 
;

-- 2023-01-25T18:47:09.137Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710648
;

-- 2023-01-25T18:47:09.137Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710648)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Sales Partner
-- Column: C_BPartner.IsSalesPartner
-- 2023-01-25T18:47:09.241Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584082,710649,0,220,TO_TIMESTAMP('2023-01-25 20:47:09','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Sales Partner',TO_TIMESTAMP('2023-01-25 20:47:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:09.242Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710649 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:09.243Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581325) 
;

-- 2023-01-25T18:47:09.246Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710649
;

-- 2023-01-25T18:47:09.246Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710649)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Forwarder
-- Column: C_BPartner.IsForwarder
-- 2023-01-25T18:47:09.345Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584084,710650,0,220,TO_TIMESTAMP('2023-01-25 20:47:09','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Forwarder',TO_TIMESTAMP('2023-01-25 20:47:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:09.346Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710650 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:09.348Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581326) 
;

-- 2023-01-25T18:47:09.350Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710650
;

-- 2023-01-25T18:47:09.351Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710650)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Default Forwarder
-- Column: C_BPartner.IsDefaultForwarder
-- 2023-01-25T18:47:09.450Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584085,710651,0,220,TO_TIMESTAMP('2023-01-25 20:47:09','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Default Forwarder',TO_TIMESTAMP('2023-01-25 20:47:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:09.451Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710651 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:09.452Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581327) 
;

-- 2023-01-25T18:47:09.455Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710651
;

-- 2023-01-25T18:47:09.456Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710651)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Storage/Warehouse
-- Column: C_BPartner.IsStorageWarehouse
-- 2023-01-25T18:47:09.552Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584086,710652,0,220,TO_TIMESTAMP('2023-01-25 20:47:09','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Storage/Warehouse',TO_TIMESTAMP('2023-01-25 20:47:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:09.554Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710652 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:09.555Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581328) 
;

-- 2023-01-25T18:47:09.557Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710652
;

-- 2023-01-25T18:47:09.558Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710652)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Actis Rating
-- Column: C_BPartner.Actis_Rating
-- 2023-01-25T18:47:09.649Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584088,710653,0,220,TO_TIMESTAMP('2023-01-25 20:47:09','YYYY-MM-DD HH24:MI:SS'),100,60,'D','Y','Y','N','N','N','N','N','Actis Rating',TO_TIMESTAMP('2023-01-25 20:47:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:09.651Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710653 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:09.652Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581330) 
;

-- 2023-01-25T18:47:09.654Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710653
;

-- 2023-01-25T18:47:09.655Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710653)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> RIM Category
-- Column: C_BPartner.RIM_Category
-- 2023-01-25T18:47:09.763Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584089,710654,0,220,TO_TIMESTAMP('2023-01-25 20:47:09','YYYY-MM-DD HH24:MI:SS'),100,60,'D','Y','Y','N','N','N','N','N','RIM Category',TO_TIMESTAMP('2023-01-25 20:47:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:09.764Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710654 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:09.765Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581331) 
;

-- 2023-01-25T18:47:09.768Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710654
;

-- 2023-01-25T18:47:09.768Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710654)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Running Contracts
-- Column: C_BPartner.IsRunningContracts
-- 2023-01-25T18:47:09.927Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584094,710655,0,220,TO_TIMESTAMP('2023-01-25 20:47:09','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','Y','N','N','N','N','N','Running Contracts',TO_TIMESTAMP('2023-01-25 20:47:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:09.928Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710655 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:09.930Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581336) 
;

-- 2023-01-25T18:47:09.932Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710655
;

-- 2023-01-25T18:47:09.933Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710655)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Group/Mother Company
-- Column: C_BPartner.Mother_Company_ID
-- 2023-01-25T18:47:10.036Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584095,710656,0,220,TO_TIMESTAMP('2023-01-25 20:47:09','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','N','N','N','N','N','Group/Mother Company',TO_TIMESTAMP('2023-01-25 20:47:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:10.037Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710656 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:10.038Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581337) 
;

-- 2023-01-25T18:47:10.041Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710656
;

-- 2023-01-25T18:47:10.041Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710656)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> SAP BPartner Id
-- Column: C_BPartner.SAP_BPartnerCode
-- 2023-01-25T18:47:10.145Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585527,710657,0,220,TO_TIMESTAMP('2023-01-25 20:47:10','YYYY-MM-DD HH24:MI:SS'),100,40,'D','Y','Y','N','N','N','N','N','SAP BPartner Id',TO_TIMESTAMP('2023-01-25 20:47:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:10.146Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710657 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:10.147Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581932) 
;

-- 2023-01-25T18:47:10.150Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710657
;

-- 2023-01-25T18:47:10.151Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710657)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Delivery credit limit indicator %
-- Column: C_BPartner.DeliveryCreditLimitIndicator
-- 2023-01-25T18:47:10.243Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585625,710658,0,220,TO_TIMESTAMP('2023-01-25 20:47:10','YYYY-MM-DD HH24:MI:SS'),100,'Percent of Credit used from the limit',25,'D','','Y','Y','N','N','N','N','N','Delivery credit limit indicator %',TO_TIMESTAMP('2023-01-25 20:47:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-25T18:47:10.245Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710658 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-25T18:47:10.246Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581960) 
;

-- 2023-01-25T18:47:10.249Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710658
;

-- 2023-01-25T18:47:10.249Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710658)
;

-- UI Element: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> main -> 20 -> creditlimit.Delivery credit limit indicator %
-- Column: C_BPartner.DeliveryCreditLimitIndicator
-- 2023-01-25T18:48:06.737Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710658,0,220,541428,614860,'F',TO_TIMESTAMP('2023-01-25 20:48:06','YYYY-MM-DD HH24:MI:SS'),100,'Percent of Credit used from the limit','Y','N','N','Y','N','N','N',0,'Delivery credit limit indicator %',20,0,0,TO_TIMESTAMP('2023-01-25 20:48:06','YYYY-MM-DD HH24:MI:SS'),100)
;

