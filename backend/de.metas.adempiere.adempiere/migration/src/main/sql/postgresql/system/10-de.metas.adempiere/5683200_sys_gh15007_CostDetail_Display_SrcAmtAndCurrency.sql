-- Element: SourceAmt
-- 2023-03-29T17:23:39.251Z
UPDATE AD_Element_Trl SET Name='Betrag in Belegwährung', PrintName='Betrag in Belegwährung',Updated=TO_TIMESTAMP('2023-03-29 20:23:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582177 AND AD_Language='de_CH'
;

-- 2023-03-29T17:23:39.320Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582177,'de_CH') 
;

-- Element: SourceAmt
-- 2023-03-29T17:23:41.430Z
UPDATE AD_Element_Trl SET Name='Betrag in Belegwährung', PrintName='Betrag in Belegwährung',Updated=TO_TIMESTAMP('2023-03-29 20:23:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582177 AND AD_Language='de_DE'
;

-- 2023-03-29T17:23:41.431Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582177,'de_DE') 
;

-- Element: SourceAmt
-- 2023-03-29T17:23:44.705Z
UPDATE AD_Element_Trl SET Name='Betrag in Belegwährung', PrintName='Betrag in Belegwährung',Updated=TO_TIMESTAMP('2023-03-29 20:23:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582177 AND AD_Language='fr_CH'
;

-- 2023-03-29T17:23:44.707Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582177,'fr_CH') 
;

-- Element: SourceAmt
-- 2023-03-29T17:23:46.768Z
UPDATE AD_Element_Trl SET Name='Betrag in Belegwährung', PrintName='Betrag in Belegwährung',Updated=TO_TIMESTAMP('2023-03-29 20:23:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582177 AND AD_Language='nl_NL'
;

-- 2023-03-29T17:23:46.770Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582177,'nl_NL') 
;

-- Element: SourceAmt
-- 2023-03-29T17:23:54.125Z
UPDATE AD_Element_Trl SET Name='Amount in document currency', PrintName='Amount in document currency',Updated=TO_TIMESTAMP('2023-03-29 20:23:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582177 AND AD_Language='en_US'
;

-- 2023-03-29T17:23:54.126Z
UPDATE AD_Element SET Name='Amount in document currency', PrintName='Amount in document currency' WHERE AD_Element_ID=582177
;

-- 2023-03-29T17:23:54.592Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582177,'en_US') 
;

-- 2023-03-29T17:23:54.593Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582177,'en_US') 
;

-- Element: Source_Currency_ID
-- 2023-03-29T17:24:14.046Z
UPDATE AD_Element_Trl SET Name='Belegwährung', PrintName='Belegwährung',Updated=TO_TIMESTAMP('2023-03-29 20:24:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578756 AND AD_Language='de_CH'
;

-- 2023-03-29T17:24:14.049Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578756,'de_CH') 
;

-- Element: Source_Currency_ID
-- 2023-03-29T17:24:16.192Z
UPDATE AD_Element_Trl SET Name='Belegwährung', PrintName='Belegwährung',Updated=TO_TIMESTAMP('2023-03-29 20:24:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578756 AND AD_Language='de_DE'
;

-- 2023-03-29T17:24:16.195Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578756,'de_DE') 
;

-- Element: Source_Currency_ID
-- 2023-03-29T17:24:20.086Z
UPDATE AD_Element_Trl SET Name='Belegwährung', PrintName='Belegwährung',Updated=TO_TIMESTAMP('2023-03-29 20:24:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578756 AND AD_Language='nl_NL'
;

-- 2023-03-29T17:24:20.087Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578756,'nl_NL') 
;

-- Element: Source_Currency_ID
-- 2023-03-29T17:24:22.112Z
UPDATE AD_Element_Trl SET Name='Belegwährung', PrintName='Belegwährung',Updated=TO_TIMESTAMP('2023-03-29 20:24:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578756 AND AD_Language='fr_CH'
;

-- 2023-03-29T17:24:22.115Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578756,'fr_CH') 
;

-- Element: Source_Currency_ID
-- 2023-03-29T17:24:27.616Z
UPDATE AD_Element_Trl SET Name='Document currency', PrintName='Document currency',Updated=TO_TIMESTAMP('2023-03-29 20:24:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578756 AND AD_Language='en_US'
;

-- 2023-03-29T17:24:27.617Z
UPDATE AD_Element SET Name='Document currency', PrintName='Document currency' WHERE AD_Element_ID=578756
;

-- 2023-03-29T17:24:28.023Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(578756,'en_US') 
;

-- 2023-03-29T17:24:28.024Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578756,'en_US') 
;

-- Field: Product Cost(344,D) -> Cost Details(748,D) -> Amount in document currency
-- Column: M_CostDetail.SourceAmt
-- 2023-03-29T17:26:47.538Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586369,713451,0,748,TO_TIMESTAMP('2023-03-29 20:26:47','YYYY-MM-DD HH24:MI:SS'),100,22,'D','Y','N','N','N','N','N','N','N','Amount in document currency',TO_TIMESTAMP('2023-03-29 20:26:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T17:26:47.540Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=713451 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T17:26:47.542Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582177) 
;

-- 2023-03-29T17:26:47.557Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713451
;

-- 2023-03-29T17:26:47.561Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713451)
;

-- Field: Product Cost(344,D) -> Cost Details(748,D) -> Document currency
-- Column: M_CostDetail.Source_Currency_ID
-- 2023-03-29T17:26:57.245Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586370,713452,0,748,TO_TIMESTAMP('2023-03-29 20:26:57','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Document currency',TO_TIMESTAMP('2023-03-29 20:26:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-29T17:26:57.247Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=713452 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-29T17:26:57.249Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578756) 
;

-- 2023-03-29T17:26:57.258Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713452
;

-- 2023-03-29T17:26:57.267Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713452)
;

-- UI Element: Product Cost(344,D) -> Cost Details(748,D) -> main -> 10 -> Amount & Qty.Amount in document currency
-- Column: M_CostDetail.SourceAmt
-- 2023-03-29T17:27:20.848Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,713451,0,748,616417,544602,'F',TO_TIMESTAMP('2023-03-29 20:27:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Amount in document currency',60,0,0,TO_TIMESTAMP('2023-03-29 20:27:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Product Cost(344,D) -> Cost Details(748,D) -> main -> 10 -> Amount & Qty.Document currency
-- Column: M_CostDetail.Source_Currency_ID
-- 2023-03-29T17:27:28.657Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,713452,0,748,616418,544602,'F',TO_TIMESTAMP('2023-03-29 20:27:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Document currency',70,0,0,TO_TIMESTAMP('2023-03-29 20:27:28','YYYY-MM-DD HH24:MI:SS'),100)
;