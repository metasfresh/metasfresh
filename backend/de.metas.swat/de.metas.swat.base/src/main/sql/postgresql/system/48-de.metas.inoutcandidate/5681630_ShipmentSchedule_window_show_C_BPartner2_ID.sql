-- Field: Shipment Disposition(500221,de.metas.inoutcandidate) -> Shipment Disposition(500221,de.metas.inoutcandidate) -> Business Partner (2)
-- Column: M_ShipmentSchedule.C_BPartner2_ID
-- 2023-03-13T15:06:10.746Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586302,712838,0,500221,TO_TIMESTAMP('2023-03-13 17:06:10','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.inoutcandidate','Y','N','N','N','N','N','N','N','Business Partner (2)',TO_TIMESTAMP('2023-03-13 17:06:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-13T15:06:10.750Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712838 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-13T15:06:10.756Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582129) 
;

-- 2023-03-13T15:06:10.764Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712838
;

-- 2023-03-13T15:06:10.767Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712838)
;

-- UI Element: Shipment Disposition(500221,de.metas.inoutcandidate) -> Shipment Disposition(500221,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Business Partner (2)
-- Column: M_ShipmentSchedule.C_BPartner2_ID
-- 2023-03-13T15:06:34.014Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712838,0,500221,540052,616013,'F',TO_TIMESTAMP('2023-03-13 17:06:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Business Partner (2)',340,0,0,TO_TIMESTAMP('2023-03-13 17:06:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Shipment Disposition(500221,de.metas.inoutcandidate) -> Shipment Disposition(500221,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Business Partner (2)
-- Column: M_ShipmentSchedule.C_BPartner2_ID
-- 2023-03-13T15:06:42.238Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2023-03-13 17:06:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616013
;

-- Field: Shipment Disposition(500221,de.metas.inoutcandidate) -> Shipment Disposition(500221,de.metas.inoutcandidate) -> Business Partner (2)
-- Column: M_ShipmentSchedule.C_BPartner2_ID
-- 2023-03-13T15:06:55.470Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-03-13 17:06:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712838
;

