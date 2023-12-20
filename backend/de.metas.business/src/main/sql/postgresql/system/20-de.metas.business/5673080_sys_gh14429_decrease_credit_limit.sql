-- Column: C_BPartner_CreditLimit.IsActive
-- 2023-01-23T14:58:25.797Z
UPDATE AD_Column SET IsAlwaysUpdateable='Y',Updated=TO_TIMESTAMP('2023-01-23 15:58:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558972
;

-- UI Element: Business Partner_OLD(123,D) -> Credit Limit(540994,D) -> main -> 10 -> default.Active
-- Column: C_BPartner_CreditLimit.IsActive
-- 2023-01-23T15:16:11.024Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,561829,0,540994,541427,614777,'F',TO_TIMESTAMP('2023-01-23 16:16:10','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','Y','N','N','N',0,'Active',50,0,0,TO_TIMESTAMP('2023-01-23 16:16:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Business Partner_OLD(123,D) -> Credit Limit(540994,D) -> main -> 10 -> default.Active
-- Column: C_BPartner_CreditLimit.IsActive
-- 2023-01-23T15:16:18.919Z
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2023-01-23 16:16:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614777
;


-- UI Element: Business Partner_OLD(123,D) -> Credit Limit(540994,D) -> main -> 10 -> default.Active
-- Column: C_BPartner_CreditLimit.IsActive
-- 2023-01-23T17:08:46.433Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-01-23 18:08:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614777
;
