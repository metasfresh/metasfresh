-- 2019-04-10T11:35:37.608
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2019-04-10 11:35:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=578538
;

-- 2019-04-10T11:35:37.608
-- URL zum Konzept
DELETE FROM AD_UI_ELEMENT WHERE AD_UI_Element_ID=558269
;

-- 2019-04-10T11:36:30.346
-- URL zum Konzept
INSERT INTO AD_UI_Element (UpdatedBy,AD_UI_Element_ID,AD_Field_ID,IsAdvancedField,AD_UI_ElementGroup_ID,AD_Client_ID,Created,CreatedBy,IsActive,SeqNo,Updated,AD_Org_ID,IsDisplayed,IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_Tab_ID,AD_UI_ElementType,IsAllowFiltering,IsMultiLine,MultiLine_LinesCount,Name) VALUES (100,558357,578538,'N',542370,0,TO_TIMESTAMP('2019-04-10 11:36:30','YYYY-MM-DD HH24:MI:SS'),100,'Y',30,TO_TIMESTAMP('2019-04-10 11:36:30','YYYY-MM-DD HH24:MI:SS'),0,'Y','N',0,'N',0,541674,'F','N','N',0,'Reihenfolge')
;

-- 2019-04-10T11:36:51.969
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2019-04-10 11:36:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558269
;

-- 2019-04-10T11:39:57.582
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2019-04-10 11:39:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=558357
;

-- 2019-04-10T11:39:57.595
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2019-04-10 11:39:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557960
;

-- 2019-04-10T11:39:57.605
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2019-04-10 11:39:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557958
;

-- 2019-04-10T11:39:57.614
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2019-04-10 11:39:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=557959
;
