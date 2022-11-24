-- 2022-01-26T15:10:01.401Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540010,547969,TO_TIMESTAMP('2022-01-26 17:10:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','salesRep',30,TO_TIMESTAMP('2022-01-26 17:10:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-26T15:10:30.128Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,1098,0,186,547969,600087,'F',TO_TIMESTAMP('2022-01-26 17:10:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Kundenbetreuer',10,0,0,TO_TIMESTAMP('2022-01-26 17:10:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-26T15:14:35.192Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,3524,0,223,540672,600088,'F',TO_TIMESTAMP('2022-01-26 17:14:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Kundenbetreuer',265,0,0,TO_TIMESTAMP('2022-01-26 17:14:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-01-26T15:15:59.714Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2022-01-26 17:15:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=3524
;

