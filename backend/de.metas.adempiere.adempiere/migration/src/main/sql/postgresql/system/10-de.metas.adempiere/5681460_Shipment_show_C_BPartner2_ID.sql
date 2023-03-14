-- Field: Shipment (Customer)(169,D) -> Shipment Line(258,D) -> Business Partner (2)
-- Column: M_InOutLine.C_BPartner2_ID
-- 2023-03-10T12:32:25.238Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586298,712834,0,258,TO_TIMESTAMP('2023-03-10 14:32:25','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Business Partner (2)',TO_TIMESTAMP('2023-03-10 14:32:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-10T12:32:25.240Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712834 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-10T12:32:25.242Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582129) 
;

-- 2023-03-10T12:32:25.245Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712834
;

-- 2023-03-10T12:32:25.246Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712834)
;

-- UI Element: Shipment (Customer)(169,D) -> Shipment Line(258,D) -> main -> 10 -> default.Business Partner (2)
-- Column: M_InOutLine.C_BPartner2_ID
-- 2023-03-10T12:32:48.703Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712834,0,258,540976,616009,'F',TO_TIMESTAMP('2023-03-10 14:32:48','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Business Partner (2)',180,0,0,TO_TIMESTAMP('2023-03-10 14:32:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Shipment (Customer)(169,D) -> Shipment Line(258,D) -> main -> 10 -> default.Business Partner (2)
-- Column: M_InOutLine.C_BPartner2_ID
-- 2023-03-10T12:32:58.704Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2023-03-10 14:32:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616009
;

