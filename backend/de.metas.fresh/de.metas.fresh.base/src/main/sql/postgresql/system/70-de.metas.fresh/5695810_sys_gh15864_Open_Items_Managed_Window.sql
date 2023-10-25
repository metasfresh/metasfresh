-- Column: Open_Items_Managed_V.BPartnerValue
-- 2023-07-14T08:49:52.474Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,587116,2094,0,10,542349,'BPartnerValue',TO_TIMESTAMP('2023-07-14 09:49:52','YYYY-MM-DD HH24:MI:SS'),100,'Key of the Business Partner','D',40,'Y','Y','N','N','N','N','N','N','N','N','N','Geschäftspartner-Schlüssel',TO_TIMESTAMP('2023-07-14 09:49:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-14T08:49:52.476Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587116 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-14T08:49:52.926Z
/* DDL */  select update_Column_Translation_From_AD_Element(2094)
;

-- Column: Open_Items_Managed_V.BPartnerName
-- 2023-07-14T08:49:53.427Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,587117,543350,0,10,542349,'BPartnerName',TO_TIMESTAMP('2023-07-14 09:49:53','YYYY-MM-DD HH24:MI:SS'),100,'D',100,'Y','Y','N','N','N','N','N','N','N','N','N','Name Geschäftspartner',TO_TIMESTAMP('2023-07-14 09:49:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-14T08:49:53.428Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587117 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-14T08:49:53.832Z
/* DDL */  select update_Column_Translation_From_AD_Element(543350)
;

-- Field: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> Geschäftspartner-Schlüssel
-- Column: Open_Items_Managed_V.BPartnerValue
-- 2023-07-14T08:52:04.457Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587116,716671,0,547024,TO_TIMESTAMP('2023-07-14 09:52:04','YYYY-MM-DD HH24:MI:SS'),100,'Key of the Business Partner',40,'D','Y','N','N','N','N','N','N','N','Geschäftspartner-Schlüssel',TO_TIMESTAMP('2023-07-14 09:52:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-14T08:52:04.460Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716671 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-14T08:52:04.462Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2094)
;

-- 2023-07-14T08:52:04.472Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716671
;

-- 2023-07-14T08:52:04.473Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716671)
;

-- Field: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> Name Geschäftspartner
-- Column: Open_Items_Managed_V.BPartnerName
-- 2023-07-14T08:52:04.651Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587117,716672,0,547024,TO_TIMESTAMP('2023-07-14 09:52:04','YYYY-MM-DD HH24:MI:SS'),100,100,'D','Y','N','N','N','N','N','N','N','Name Geschäftspartner',TO_TIMESTAMP('2023-07-14 09:52:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-14T08:52:04.653Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716672 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-14T08:52:04.654Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543350)
;

-- 2023-07-14T08:52:04.657Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716672
;

-- 2023-07-14T08:52:04.658Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716672)
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> default.Geschäftspartner
-- Column: Open_Items_Managed_V.C_BPartner_ID
-- 2023-07-14T08:52:32.297Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2023-07-14 09:52:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618241
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> default.Geschäftspartner
-- Column: Open_Items_Managed_V.C_BPartner_ID
-- 2023-07-14T08:52:42.124Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=618241
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> default.Geschäftspartner-Schlüssel
-- Column: Open_Items_Managed_V.BPartnerValue
-- 2023-07-14T08:53:25.565Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716671,0,547024,550819,618253,'F',TO_TIMESTAMP('2023-07-14 09:53:25','YYYY-MM-DD HH24:MI:SS'),100,'Key of the Business Partner','Y','N','N','Y','N','N','N',0,'Geschäftspartner-Schlüssel',10,0,0,TO_TIMESTAMP('2023-07-14 09:53:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> default.Name Geschäftspartner
-- Column: Open_Items_Managed_V.BPartnerName
-- 2023-07-14T08:53:47.861Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716672,0,547024,550819,618254,'F',TO_TIMESTAMP('2023-07-14 09:53:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Name Geschäftspartner',15,0,0,TO_TIMESTAMP('2023-07-14 09:53:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> flags.Offener Posten Verwaltet
-- Column: Open_Items_Managed_V.IsOpenItem
-- 2023-07-14T08:59:24.333Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2023-07-14 09:59:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618233
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> org.Mandant
-- Column: Open_Items_Managed_V.AD_Client_ID
-- 2023-07-14T09:01:59.904Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-07-14 10:01:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618240
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> org.Organisation
-- Column: Open_Items_Managed_V.AD_Org_ID
-- 2023-07-14T09:01:59.911Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-07-14 10:01:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618239
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> flags.Offener Posten Verwaltet
-- Column: Open_Items_Managed_V.IsOpenItem
-- 2023-07-14T09:01:59.917Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-07-14 10:01:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618233
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> default.Geschäftspartner-Schlüssel
-- Column: Open_Items_Managed_V.BPartnerValue
-- 2023-07-14T09:01:59.923Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-07-14 10:01:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618253
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> default.Name Geschäftspartner
-- Column: Open_Items_Managed_V.BPartnerName
-- 2023-07-14T09:01:59.929Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-07-14 10:01:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618254
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> dates.Buchungsdatum
-- Column: Open_Items_Managed_V.DateAcct
-- 2023-07-14T09:01:59.935Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-07-14 10:01:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618234
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> org.Department
-- Column: Open_Items_Managed_V.M_Department_ID
-- 2023-07-14T09:01:59.940Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-07-14 10:01:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618238
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> org.Sektionskennung
-- Column: Open_Items_Managed_V.M_SectionCode_ID
-- 2023-07-14T09:01:59.944Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-07-14 10:01:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618237
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> org.UserElementString1
-- Column: Open_Items_Managed_V.UserElementString1
-- 2023-07-14T09:01:59.949Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-07-14 10:01:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618236
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 20 -> dates.Document date
-- Column: Open_Items_Managed_V.DocumentDate
-- 2023-07-14T09:01:59.954Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-07-14 10:01:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618235
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> default.Nr.
-- Column: Open_Items_Managed_V.DocumentNo
-- 2023-07-14T09:01:59.960Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-07-14 10:01:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618225
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> default.Referenz
-- Column: Open_Items_Managed_V.POReference
-- 2023-07-14T09:01:59.964Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-07-14 10:01:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618227
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> Description.Beschreibung
-- Column: Open_Items_Managed_V.Description
-- 2023-07-14T09:01:59.969Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-07-14 10:01:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618224
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> Amount.Document currency
-- Column: Open_Items_Managed_V.Source_Currency_ID
-- 2023-07-14T09:01:59.974Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-07-14 10:01:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618228
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> Amount.Amount (FC)
-- Column: Open_Items_Managed_V.Amount_FC
-- 2023-07-14T09:01:59.979Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-07-14 10:01:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618229
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> Amount.Accounting Currency
-- Column: Open_Items_Managed_V.Acct_Currency_ID
-- 2023-07-14T09:01:59.984Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2023-07-14 10:01:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618230
;

-- UI Element: Offene Posten (verwaltet)(541716,D) -> Offene Posten (verwaltet)(547024,D) -> main -> 10 -> Amount.Amount (LC)
-- Column: Open_Items_Managed_V.Amount_LC
-- 2023-07-14T09:02:00.115Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2023-07-14 10:01:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618231
;
