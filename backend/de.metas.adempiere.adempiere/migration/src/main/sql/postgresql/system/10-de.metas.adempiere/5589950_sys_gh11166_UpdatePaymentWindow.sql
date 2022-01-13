-- 2021-05-25T12:59:30.538Z
-- URL zum Konzept
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540050,545907,TO_TIMESTAMP('2021-05-25 15:59:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','autoAlloc',27,TO_TIMESTAMP('2021-05-25 15:59:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-25T13:00:24.882Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,551885,0,330,545907,585277,'F',TO_TIMESTAMP('2021-05-25 16:00:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'isAutoAllocateAvailableAmt',10,0,0,TO_TIMESTAMP('2021-05-25 16:00:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-25T13:13:17.734Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', PrintName='Auto Allocate Available Amt',Updated=TO_TIMESTAMP('2021-05-25 16:13:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542096 AND AD_Language='en_US'
;

-- 2021-05-25T13:13:17.968Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542096,'en_US') 
;

-- 2021-05-25T13:14:47.150Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Auto Allocate Available Amt',Updated=TO_TIMESTAMP('2021-05-25 16:14:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=542096 AND AD_Language='en_US'
;

-- 2021-05-25T13:14:47.194Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(542096,'en_US') 
;
