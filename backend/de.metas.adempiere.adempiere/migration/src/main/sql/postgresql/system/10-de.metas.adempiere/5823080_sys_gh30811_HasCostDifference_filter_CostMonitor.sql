-- Put PP_Order.HasCostDifference (AD_Column 593510) on the "Kostenüberwachung Fertigung" tab
-- (AD_Window 542175 / AD_Tab 549352) as its cost-difference filter, and retire the unusable filter
-- on the decimal PP_Order.CostDifference.
--
-- This must stay the ONLY tab with an AD_Field for column 593510: that is what keeps the column-level
-- filter attributes of 593510 from reaching any other PP_Order window.
--
-- The field is shown in the grid (a filter column must be visible in the grid view) but not in the
-- single-record form, where the CostDifference amount already states the same fact more precisely.

INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,IsFilterField,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy)
VALUES (0,593510,784959 /*From ID Server*/,0,549352,
        TO_TIMESTAMP('2026-09-08 15:10:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Y','N','N','N','N','N','Y',
        'Kostendifferenz vorhanden',0,37,
        TO_TIMESTAMP('2026-09-08 15:10:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=784959
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

SELECT update_FieldTranslation_From_AD_Name_Element(585435);

DELETE FROM AD_Element_Link WHERE AD_Field_ID=784959;
SELECT AD_Element_Link_Create_Missing_Field(784959);

INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784959,0,549352,555514,654725 /*From ID Server*/,'F',
        TO_TIMESTAMP('2026-09-08 15:10:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N',
        'Kostendifferenz vorhanden',0,37,0,
        TO_TIMESTAMP('2026-09-08 15:10:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- CostDifference (AD_Field 781753) leaves the filter bar; it stays a grid column.
UPDATE AD_Field SET IsFilterField='N',
       Updated=TO_TIMESTAMP('2026-09-08 15:10:20','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=781753
;
