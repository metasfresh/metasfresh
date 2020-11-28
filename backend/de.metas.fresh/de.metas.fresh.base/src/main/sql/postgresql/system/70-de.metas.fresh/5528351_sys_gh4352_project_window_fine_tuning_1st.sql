
-- 2019-07-29T12:05:27.897Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582282,0,541830,542695,560300,'F',TO_TIMESTAMP('2019-07-29 14:05:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Projektart',5,0,0,TO_TIMESTAMP('2019-07-29 14:05:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T20:35:29.448Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541784,542707,TO_TIMESTAMP('2019-07-29 22:35:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','active',30,TO_TIMESTAMP('2019-07-29 22:35:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T20:35:33.107Z
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET SeqNo=5,Updated=TO_TIMESTAMP('2019-07-29 22:35:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542707
;

-- 2019-07-29T20:35:52.713Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,541783,542708,TO_TIMESTAMP('2019-07-29 22:35:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','description',20,TO_TIMESTAMP('2019-07-29 22:35:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T20:36:14.053Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542707, SeqNo=10,Updated=TO_TIMESTAMP('2019-07-29 22:36:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560252
;

-- 2019-07-29T20:36:32.193Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=542708, SeqNo=10,Updated=TO_TIMESTAMP('2019-07-29 22:36:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560251
;

-- 2019-07-29T20:37:15.125Z
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='',Updated=TO_TIMESTAMP('2019-07-29 22:37:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582282
;

-- 2019-07-29T20:38:38.465Z
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2019-07-29 22:38:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=542695
;
update ad_field set name = 'Geschäftspartner' where ad_field_id = 582484;
update ad_field_trl set name = 'Geschäftspartner' where ad_field_id = 582484 and ad_language = 'de_DE';