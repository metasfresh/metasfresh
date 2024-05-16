-- 2021-09-23T08:43:04.894Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,570890,660510,0,540812,0,TO_TIMESTAMP('2021-09-23 11:43:01','YYYY-MM-DD HH24:MI:SS'),100,'International Bank Account Number',0,'D','For payments with a structured QR reference, the QR-IBAN must be used to indicate the account to be credited. The formal structure of the QR-IBAN corresponds to the rules stipulated in ISO 13616 standard for IBAN.
IBAN/QR-IBAN from the Swiss QR Code. Printed in blocks of 4 characters (5x4-character groups, the last character separate).',0,'Y','Y','Y','N','N','N','N','N','QR IBAN',0,330,0,1,1,TO_TIMESTAMP('2021-09-23 11:43:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-23T08:43:05.205Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=660510 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-23T08:43:05.334Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577768) 
;

-- 2021-09-23T08:43:05.426Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=660510
;

-- 2021-09-23T08:43:05.497Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(660510)
;

-- 2021-09-23T08:46:40.275Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,660510,0,540812,540335,591506,'F',TO_TIMESTAMP('2021-09-23 11:46:39','YYYY-MM-DD HH24:MI:SS'),100,'International Bank Account Number','For payments with a structured QR reference, the QR-IBAN must be used to indicate the account to be credited. The formal structure of the QR-IBAN corresponds to the rules stipulated in ISO 13616 standard for IBAN.
IBAN/QR-IBAN from the Swiss QR Code. Printed in blocks of 4 characters (5x4-character groups, the last character separate).','Y','N','N','Y','N','N','N',0,'QR IBAN',35,0,0,TO_TIMESTAMP('2021-09-23 11:46:39','YYYY-MM-DD HH24:MI:SS'),100)
;

