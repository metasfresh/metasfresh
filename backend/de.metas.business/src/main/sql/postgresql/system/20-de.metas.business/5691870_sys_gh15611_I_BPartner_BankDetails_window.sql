-- Field: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> Bankverbindung
-- Column: I_BPartner.BankDetails
-- 2023-06-15T13:07:06.996181800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586817,716369,0,441,TO_TIMESTAMP('2023-06-15 16:07:06.754','YYYY-MM-DD HH24:MI:SS.US'),100,255,'D','Y','N','N','N','N','N','N','N','Bankverbindung',TO_TIMESTAMP('2023-06-15 16:07:06.754','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-15T13:07:06.999165300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716369 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-15T13:07:07.004684400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582440) 
;

-- 2023-06-15T13:07:07.016680200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716369
;

-- 2023-06-15T13:07:07.022226600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716369)
;

-- Field: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> QR IBAN
-- Column: I_BPartner.QR_IBAN
-- 2023-06-15T13:07:33.736901200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586818,716370,0,441,TO_TIMESTAMP('2023-06-15 16:07:33.58','YYYY-MM-DD HH24:MI:SS.US'),100,'International Bank Account Number',34,'D','For payments with a structured QR reference, the QR-IBAN must be used to indicate the account to be credited. The formal structure of the QR-IBAN corresponds to the rules stipulated in ISO 13616 standard for IBAN.
IBAN/QR-IBAN from the Swiss QR Code. Printed in blocks of 4 characters (5x4-character groups, the last character separate).','Y','N','N','N','N','N','N','N','QR IBAN',TO_TIMESTAMP('2023-06-15 16:07:33.58','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-15T13:07:33.740003200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716370 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-15T13:07:33.744984900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577768) 
;

-- 2023-06-15T13:07:33.753783500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716370
;

-- 2023-06-15T13:07:33.758783700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716370)
;


-- UI Element: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> main -> 20 -> div.Bankverbindung
-- Column: I_BPartner.BankDetails
-- 2023-06-15T13:08:43.193656600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716369,0,441,618011,541267,'F',TO_TIMESTAMP('2023-06-15 16:08:43.03','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Bankverbindung',25,0,0,TO_TIMESTAMP('2023-06-15 16:08:43.03','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> main -> 20 -> div.QR IBAN
-- Column: I_BPartner.QR_IBAN
-- 2023-06-15T13:09:03.057869900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,716370,0,441,618012,541267,'F',TO_TIMESTAMP('2023-06-15 16:09:02.903','YYYY-MM-DD HH24:MI:SS.US'),100,'International Bank Account Number','For payments with a structured QR reference, the QR-IBAN must be used to indicate the account to be credited. The formal structure of the QR-IBAN corresponds to the rules stipulated in ISO 13616 standard for IBAN.
IBAN/QR-IBAN from the Swiss QR Code. Printed in blocks of 4 characters (5x4-character groups, the last character separate).','Y','N','N','Y','N','N','N',0,'QR IBAN',35,0,0,TO_TIMESTAMP('2023-06-15 16:09:02.903','YYYY-MM-DD HH24:MI:SS.US'),100)
;

