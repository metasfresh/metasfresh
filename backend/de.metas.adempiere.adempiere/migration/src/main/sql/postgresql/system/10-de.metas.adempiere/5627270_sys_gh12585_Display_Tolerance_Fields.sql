-- 2022-02-22T17:46:17.200Z
-- URL zum Konzept
UPDATE AD_Field SET DisplayLogic='@IsEnforceTolerance/N@=Y',Updated=TO_TIMESTAMP('2022-02-22 19:46:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=680614
;

-- 2022-02-22T17:52:10.175Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,680613,0,53029,540444,601389,'F',TO_TIMESTAMP('2022-02-22 19:52:09','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Enforce Tolerance',230,0,0,TO_TIMESTAMP('2022-02-22 19:52:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-22T17:52:37.131Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,680614,0,53029,540444,601390,'F',TO_TIMESTAMP('2022-02-22 19:52:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Tolerance %',240,0,0,TO_TIMESTAMP('2022-02-22 19:52:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-22T17:53:50.220Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=95,Updated=TO_TIMESTAMP('2022-02-22 19:53:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=601389
;

-- 2022-02-22T17:54:00.505Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=96,Updated=TO_TIMESTAMP('2022-02-22 19:54:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=601390
;

-- 2022-02-22T17:57:02.727Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,579283,680656,0,53039,0,TO_TIMESTAMP('2022-02-22 19:57:01','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Enforce Tolerance',0,400,0,1,1,TO_TIMESTAMP('2022-02-22 19:57:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-22T17:57:02.822Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=680656 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-02-22T17:57:02.925Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580594) 
;

-- 2022-02-22T17:57:03.027Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=680656
;

-- 2022-02-22T17:57:03.097Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(680656)
;

-- 2022-02-22T17:57:34.487Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,579284,680657,0,53039,0,TO_TIMESTAMP('2022-02-22 19:57:33','YYYY-MM-DD HH24:MI:SS'),100,0,'@IsEnforceTolerance/N@=Y','D',0,'Y','Y','Y','N','N','N','N','N','Tolerance %',0,410,0,1,1,TO_TIMESTAMP('2022-02-22 19:57:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-22T17:57:34.579Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=680657 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-02-22T17:57:34.666Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580593) 
;

-- 2022-02-22T17:57:34.760Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=680657
;

-- 2022-02-22T17:57:34.851Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(680657)
;












-- 2022-02-22T17:59:50.572Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,680656,0,53039,540244,601391,'F',TO_TIMESTAMP('2022-02-22 19:59:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Enforce Tolerance',95,0,0,TO_TIMESTAMP('2022-02-22 19:59:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-02-22T18:00:15.381Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,680657,0,53039,540244,601392,'F',TO_TIMESTAMP('2022-02-22 20:00:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Tolerance %',96,0,0,TO_TIMESTAMP('2022-02-22 20:00:14','YYYY-MM-DD HH24:MI:SS'),100)
;




-- 2022-02-22T18:10:49.994Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=105,Updated=TO_TIMESTAMP('2022-02-22 20:10:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=601391
;

-- 2022-02-22T18:10:58.476Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=106,Updated=TO_TIMESTAMP('2022-02-22 20:10:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=601392
;

