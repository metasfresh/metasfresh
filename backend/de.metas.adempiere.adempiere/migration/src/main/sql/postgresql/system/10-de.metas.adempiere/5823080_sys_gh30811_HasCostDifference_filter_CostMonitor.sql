-- Put PP_Order.HasCostDifference (AD_Column 593510) on the "Kostenüberwachung Fertigung" tab
-- (AD_Window 542175 / AD_Tab 549352) as its cost-difference filter, and retire the unusable filter
-- on the decimal PP_Order.CostDifference.
--
-- Tab 549352 is on IncludeFiltersStrategy='E' (Explicit), so its filter set is exactly the AD_Fields
-- carrying IsFilterField='Y'. HasCostDifference joins it; CostDifference leaves it -- an EQUALS box
-- on an amount is an exact-match search on a computed decimal, which no controller can type into.
-- CostDifference stays a grid column (SeqNoGrid 35): the Yes/No flag says which orders to work,
-- the amount says how much.
--
-- The tab is the ONLY tab with an AD_Field for column 593510, which is what keeps the column-level
-- FilterOperator='E' / FilterDefaultValue='Y' set in 5823070 from reaching any other window --
-- Produktionsauftrag (AD_Window 53009 / AD_Tab 53054) included.
--
-- The field is shown in the grid (rule: a filter column must be visible in the grid view) but not in
-- the single-record form, where the CostDifference amount already states the same fact with more
-- precision and a derived read-only checkbox would only repeat it. SeqNoGrid=37 places it directly
-- after CostDifference and is free on both grid layers (AD_Field and AD_UI_Element).
--
-- IDs allocated from idserver.metas.de: AD_Field 784959, AD_UI_Element 654725

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

-- CostDifference (AD_Field 781753) leaves the filter bar. It stays a grid column.
-- On a database built from this branch the field never became a filter field in the first place
-- (5823050 does not list it), so this UPDATE is a no-op there; it is written explicitly so that a
-- dev stack which applied an earlier draft of 5823050 converges to the same state.
UPDATE AD_Field SET IsFilterField='N',
       Updated=TO_TIMESTAMP('2026-09-08 15:10:20','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=781753
;
