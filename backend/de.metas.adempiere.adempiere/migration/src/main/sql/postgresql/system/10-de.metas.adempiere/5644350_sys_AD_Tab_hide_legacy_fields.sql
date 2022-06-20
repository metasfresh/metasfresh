-- Field: Fenster Verwaltung -> Register -> Query data on load
-- Column: AD_Tab.IsQueryOnLoad
-- 2022-06-20T12:43:24.032Z
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2022-06-20 15:43:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=542858
;

-- Field: Fenster Verwaltung -> Register -> Grid Mode Only
-- Column: AD_Tab.IsGridModeOnly
-- 2022-06-20T12:43:51.525Z
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2022-06-20 15:43:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=543156
;

-- Field: Fenster Verwaltung -> Register -> Check Parents Changed
-- Column: AD_Tab.IsCheckParentsChanged
-- 2022-06-20T12:43:54.999Z
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2022-06-20 15:43:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=63006
;

-- Field: Fenster Verwaltung -> Register -> Search Active
-- Column: AD_Tab.IsSearchActive
-- 2022-06-20T12:44:47.141Z
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2022-06-20 15:44:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=542629
;

-- Field: Fenster Verwaltung -> Register -> Refresh All On Activate
-- Column: AD_Tab.IsRefreshAllOnActivate
-- 2022-06-20T12:46:59.917Z
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2022-06-20 15:46:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=58083
;

-- Field: Fenster Verwaltung -> Register -> Default Selected Record
-- Column: AD_Tab.DefaultWhereClause
-- 2022-06-20T12:48:03.653Z
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2022-06-20 15:48:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=542630
;

-- Field: Fenster Verwaltung -> Register -> Meldung
-- Column: AD_Tab.AD_Message_ID
-- 2022-06-20T12:52:48.326Z
UPDATE AD_Field SET DisplayLogic='@AD_Message_ID@>0', IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-06-20 15:52:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=545243
;

-- Field: Fenster Verwaltung -> Register -> Has Tree
-- Column: AD_Tab.HasTree
-- 2022-06-20T12:53:12.030Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-06-20 15:53:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=312
;

-- Field: Fenster Verwaltung -> Register -> Bild
-- Column: AD_Tab.AD_Image_ID
-- 2022-06-20T12:53:43.018Z
UPDATE AD_Field SET DisplayLogic='@AD_Image_ID/0@>0', IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-06-20 15:53:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4956
;

-- Field: Fenster Verwaltung -> Register -> Bild
-- Column: AD_Tab.AD_Image_ID
-- 2022-06-20T12:53:48.683Z
UPDATE AD_Field SET IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2022-06-20 15:53:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=4956
;

-- Field: Fenster Verwaltung -> Register -> Has Tree
-- Column: AD_Tab.HasTree
-- 2022-06-20T12:55:10.783Z
UPDATE AD_Field SET DisplayLogic='@HasTree/N@=Y',Updated=TO_TIMESTAMP('2022-06-20 15:55:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=312
;

-- Field: Fenster Verwaltung -> Register -> Advanced Tab
-- Column: AD_Tab.IsAdvancedTab
-- 2022-06-20T12:55:41.926Z
UPDATE AD_Field SET DisplayLogic='@IsAdvancedTab/N@=Y',Updated=TO_TIMESTAMP('2022-06-20 15:55:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=11997
;

-- Field: Fenster Verwaltung -> Register -> Speicherwarnung
-- Column: AD_Tab.CommitWarning
-- 2022-06-20T12:56:15.053Z
UPDATE AD_Field SET DisplayLogic='@CommitWarning@!''''',Updated=TO_TIMESTAMP('2022-06-20 15:56:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=1548
;

-- Field: Fenster Verwaltung -> Register -> Sql ORDER BY
-- Column: AD_Tab.OrderByClause
-- 2022-06-20T12:56:26.999Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-06-20 15:56:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=1549
;

-- Field: Fenster Verwaltung -> Register -> Collapse Search Panel
-- Column: AD_Tab.IsSearchCollapsed
-- 2022-06-20T12:56:54.751Z
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N', IsSameLine='N',Updated=TO_TIMESTAMP('2022-06-20 15:56:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=542857
;

-- Field: Fenster Verwaltung -> Register -> Sql ORDER BY
-- Column: AD_Tab.OrderByClause
-- 2022-06-20T12:57:46.369Z
UPDATE AD_Field SET DisplayLogic='@OrderByClause@!''''',Updated=TO_TIMESTAMP('2022-06-20 15:57:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=1549
;

