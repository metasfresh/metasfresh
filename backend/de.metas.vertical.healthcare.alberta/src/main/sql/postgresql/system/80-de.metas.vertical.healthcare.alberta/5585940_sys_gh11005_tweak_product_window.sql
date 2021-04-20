-- 2021-04-20T05:12:15.524Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-20 07:12:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578835 AND AD_Language='de_CH'
;

-- 2021-04-20T05:12:15.657Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578835,'de_CH')
;

-- 2021-04-20T05:12:19.421Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-04-20 07:12:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578835 AND AD_Language='de_DE'
;

-- 2021-04-20T05:12:19.445Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578835,'de_DE')
;

-- 2021-04-20T05:12:19.524Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(578835,'de_DE')
;

-- 2021-04-20T05:12:54.639Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N',Updated=TO_TIMESTAMP('2021-04-20 07:12:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=635887
;

-- 2021-04-20T05:12:59.250Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N',Updated=TO_TIMESTAMP('2021-04-20 07:12:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=635886
;

-- 2021-04-20T05:13:02.812Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N',Updated=TO_TIMESTAMP('2021-04-20 07:13:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=635898
;

-- 2021-04-20T05:13:19.735Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2021-04-20 07:13:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=579781
;

-- 2021-04-20T05:13:22.098Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2021-04-20 07:13:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=579780
;

-- 2021-04-20T05:13:26.163Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2021-04-20 07:13:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=579791
;

-- 2021-04-20T05:13:36.525Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=150,Updated=TO_TIMESTAMP('2021-04-20 07:13:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=579782
;

-- 2021-04-20T05:14:00.373Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2021-04-20 07:14:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=579759
;

-- 2021-04-20T05:14:04.645Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2021-04-20 07:14:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=579758
;

-- 2021-04-20T05:16:50.530Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,635635,0,543562,545146,583547,'F',TO_TIMESTAMP('2021-04-20 07:16:50','YYYY-MM-DD HH24:MI:SS'),100,'Hersteller des Produktes','Y','N','N','Y','N','N','N',0,'Hersteller',20,0,0,TO_TIMESTAMP('2021-04-20 07:16:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-20T05:17:23.825Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,635666,0,543562,545146,583548,'F',TO_TIMESTAMP('2021-04-20 07:17:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Hersteller-Nr.',30,0,0,TO_TIMESTAMP('2021-04-20 07:17:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-04-20T05:18:48.627Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2021-04-20 07:18:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=635589
;

-- 2021-04-20T05:19:36.992Z
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET Name='Therapy and Manufacturer',Updated=TO_TIMESTAMP('2021-04-20 07:19:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=545172
;

-- 2021-04-20T05:19:53.191Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=545172, SeqNo=30,Updated=TO_TIMESTAMP('2021-04-20 07:19:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=579566
;

-- 2021-04-20T05:20:25.711Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=545146, SeqNo=40,Updated=TO_TIMESTAMP('2021-04-20 07:20:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=579566
;

-- 2021-04-20T05:20:43.907Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=545172, SeqNo=30,Updated=TO_TIMESTAMP('2021-04-20 07:20:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=583547
;

-- 2021-04-20T05:20:55.985Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=545172, SeqNo=40,Updated=TO_TIMESTAMP('2021-04-20 07:20:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=583548
;




-- 2021-04-20T05:35:07.037Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2021-04-20 07:35:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=579773
;

-- 2021-04-20T05:35:12.898Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2021-04-20 07:35:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=579777
;

-- 2021-04-20T05:35:16.206Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2021-04-20 07:35:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=583547
;

-- 2021-04-20T05:35:25.301Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2021-04-20 07:35:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=583548
;

-- 2021-04-20T05:36:34.720Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=545175, SeqNo=20,Updated=TO_TIMESTAMP('2021-04-20 07:36:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=583547
;

-- 2021-04-20T05:36:54.367Z
-- URL zum Konzept
UPDATE AD_UI_ElementGroup SET Name='Alberta-Article-Data',Updated=TO_TIMESTAMP('2021-04-20 07:36:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=545175
;

-- 2021-04-20T05:37:08.895Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=545144, SeqNo=60,Updated=TO_TIMESTAMP('2021-04-20 07:37:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=583547
;

-- 2021-04-20T05:39:40.173Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=545172, SeqNo=70,Updated=TO_TIMESTAMP('2021-04-20 07:39:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=583547
;

-- 2021-04-20T05:40:08.194Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2021-04-20 07:40:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=583547
;

-- 2021-04-20T06:32:48.579Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=545147, SeqNo=60,Updated=TO_TIMESTAMP('2021-04-20 08:32:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=583548
;

-- 2021-04-20T06:37:33.507Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2021-04-20 08:37:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558031
;

-- 2021-04-20T08:02:17.710Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=579597
;

-- 2021-04-20T08:02:22.203Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=579598
;

-- 2021-04-20T08:06:13.841Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=545172, SeqNo=70,Updated=TO_TIMESTAMP('2021-04-20 10:06:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=583548
;

-- 2021-04-20T08:06:53.455Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2021-04-20 10:06:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=583548
;

