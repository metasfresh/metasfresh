-- gh26253 follow-up: correct AD metadata shipped with the Cost Revaluation "CopyFromCostElement" feature.
-- (1) Make CopyFrom_M_CostElement_ID a zoom target, matching its sibling M_CostElement_ID on the same tab,
--     so a Cost Element record can navigate to the Cost Revaluations that copy FROM it (the sibling already
--     lets it see the ones that target it).
-- (2) The en_US ref-list description of the CopyFromCostElement value referenced the label
--     "Copy from cost element", but the actual en_US field label is "Source Cost Element".
-- (3) The new AD_Element/AD_Field de_DE/de_CH translation rows were left IsTranslated='Y'; the metasfresh
--     convention is that the German base-language rows carry IsTranslated='N' (en_US carries the translation).
--     The companion AD_Ref_List_Trl rows already follow the convention.

-- (1) Zoom target parity with the sibling M_CostElement_ID column (583869, already IsExcludeFromZoomTargets='N')
UPDATE AD_Column SET IsExcludeFromZoomTargets='N', Updated=TO_TIMESTAMP('2026-07-16 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=592962
;

-- (2) en_US ref-list description referenced the wrong label
UPDATE AD_Ref_List_Trl SET Description='Cost is copied unchanged from the cost element selected under "Source Cost Element".', Updated=TO_TIMESTAMP('2026-07-16 10:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Ref_List_ID=544318 AND AD_Language='en_US'
;

-- (3) de_DE/de_CH base-language rows => IsTranslated='N' (RevaluationSource=585099, CopyFrom_M_CostElement_ID=585100)
UPDATE AD_Element_Trl SET IsTranslated='N', Updated=TO_TIMESTAMP('2026-07-16 10:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID IN (585099,585100) AND AD_Language IN ('de_DE','de_CH')
;
UPDATE AD_Field_Trl SET IsTranslated='N', Updated=TO_TIMESTAMP('2026-07-16 10:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID IN (781420,781421) AND AD_Language IN ('de_DE','de_CH')
;
