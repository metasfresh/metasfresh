-- Field: Distributionsdisposition -> Distributionsdisposition -> Forward Manufacturing Order BOM Line
-- Column: DD_Order_Candidate.Forward_PP_Order_BOMLine_ID
-- Field: Distributionsdisposition(541807,EE01) -> Distributionsdisposition(547559,EE01) -> Forward Manufacturing Order BOM Line
-- Column: DD_Order_Candidate.Forward_PP_Order_BOMLine_ID
-- 2024-09-12T08:27:33.753Z
UPDATE AD_Field SET DisplayLogic='@Forward_PP_Order_BOMLine_ID/0@>0',Updated=TO_TIMESTAMP('2024-09-12 11:27:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729090
;

