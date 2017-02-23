-- 22.02.2017 12:02
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=1000002,Updated=TO_TIMESTAMP('2017-02-22 12:02:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2703
;

-- 22.02.2017 12:02
-- URL zum Konzept
UPDATE AD_Field SET AD_FieldGroup_ID=1000002, IsDisplayed='Y',Updated=TO_TIMESTAMP('2017-02-22 12:02:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=501685
;

-- 22.02.2017 12:03
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=500,Updated=TO_TIMESTAMP('2017-02-22 12:03:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2703
;

-- 22.02.2017 12:03
-- URL zum Konzept
UPDATE AD_Field SET SeqNo=510,Updated=TO_TIMESTAMP('2017-02-22 12:03:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=501685
;

-- 22.02.2017 12:05
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,1000014,540128,TO_TIMESTAMP('2017-02-22 12:05:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','Text',30,'primary',TO_TIMESTAMP('2017-02-22 12:05:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 22.02.2017 12:05
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,2703,0,257,540128,541803,TO_TIMESTAMP('2017-02-22 12:05:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','description',10,0,0,TO_TIMESTAMP('2017-02-22 12:05:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 22.02.2017 12:06
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,501685,0,257,540128,541804,TO_TIMESTAMP('2017-02-22 12:06:02','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','description bottom',20,0,0,TO_TIMESTAMP('2017-02-22 12:06:02','YYYY-MM-DD HH24:MI:SS'),100)
;

