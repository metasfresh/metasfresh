-- 2018-04-11T14:06:46.046
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559655,563193,0,406,TO_TIMESTAMP('2018-04-11 14:06:45','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.pricing','Y','Y','N','N','N','N','N','Base_PricingSystem_ID',TO_TIMESTAMP('2018-04-11 14:06:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-11T14:06:46.047
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=563193 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-04-11T14:06:46.130
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559656,563194,0,406,TO_TIMESTAMP('2018-04-11 14:06:46','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.pricing','Y','Y','N','N','N','N','N','IsPriceOverride',TO_TIMESTAMP('2018-04-11 14:06:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-11T14:06:46.133
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=563194 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-04-11T14:06:46.223
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559657,563195,0,406,TO_TIMESTAMP('2018-04-11 14:06:46','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.pricing','Y','Y','N','N','N','N','N','PriceBase',TO_TIMESTAMP('2018-04-11 14:06:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-11T14:06:46.226
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=563195 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-04-11T14:06:46.316
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559658,563196,0,406,TO_TIMESTAMP('2018-04-11 14:06:46','YYYY-MM-DD HH24:MI:SS'),100,'Amount added to a price as a surcharge',10,'de.metas.pricing','The Standard Price Surcharge Amount indicates the amount to be added to the price prior to multiplication.
','Y','Y','N','N','N','N','N','Aufschlag auf Standardpreis',TO_TIMESTAMP('2018-04-11 14:06:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-11T14:06:46.319
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=563196 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-04-11T14:06:46.408
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559659,563197,0,406,TO_TIMESTAMP('2018-04-11 14:06:46','YYYY-MM-DD HH24:MI:SS'),100,'Standardpreis',14,'de.metas.pricing','Preis mit Standardrabattierung gegenber Listenpreis.','Y','Y','N','N','N','N','N','Standardpreis',TO_TIMESTAMP('2018-04-11 14:06:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-11T14:06:46.411
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Field_ID=563197 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2018-04-11T14:18:35.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsPriceOverride@=Y',Updated=TO_TIMESTAMP('2018-04-11 14:18:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563195
;

-- 2018-04-11T14:19:35.310
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsPriceOverride@ = Y & @PriceBase@ = P ',Updated=TO_TIMESTAMP('2018-04-11 14:19:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563193
;

-- 2018-04-11T14:19:57.343
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsPriceOverride@ = Y & @PriceBase@ = P ',Updated=TO_TIMESTAMP('2018-04-11 14:19:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563196
;

-- 2018-04-11T14:20:15.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@IsPriceOverride@ = Y & @PriceBase@ = F',Updated=TO_TIMESTAMP('2018-04-11 14:20:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=563197
;




-- 2018-04-11T17:19:26.944
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,563194,0,406,551409,540609,'F',TO_TIMESTAMP('2018-04-11 17:19:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','IsPriceOverride',140,0,0,TO_TIMESTAMP('2018-04-11 17:19:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-11T17:19:48.572
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,563195,0,406,551410,540609,'F',TO_TIMESTAMP('2018-04-11 17:19:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','PriceBase',150,0,0,TO_TIMESTAMP('2018-04-11 17:19:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-11T17:20:06.556
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,563193,0,406,551411,540609,'F',TO_TIMESTAMP('2018-04-11 17:20:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','Base Pricing System',160,0,0,TO_TIMESTAMP('2018-04-11 17:20:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-11T17:20:46.486
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,563196,0,406,551412,540609,'F',TO_TIMESTAMP('2018-04-11 17:20:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','Std AddAmt',170,0,0,TO_TIMESTAMP('2018-04-11 17:20:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-04-11T17:32:09.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,563197,0,406,551413,540609,'F',TO_TIMESTAMP('2018-04-11 17:32:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','Standardpreis',180,0,0,TO_TIMESTAMP('2018-04-11 17:32:09','YYYY-MM-DD HH24:MI:SS'),100)
;


