-- 2022-11-25T19:09:35.327Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2022-11-25 20:09:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547748
;

-- 2022-11-25T19:09:41.153Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2022-11-25 20:09:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547749
;

-- 2022-11-25T19:09:59.811Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547749
;

-- 2022-11-25T19:10:10.342Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=547748
;

-- 2022-11-25T19:10:36.881Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2022-11-25 20:10:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=543271
;

-- 2022-11-25T19:10:44.017Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2022-11-25 20:10:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=543272
;

-- 2022-11-25T19:11:12.737Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,575174,708212,0,352,0,TO_TIMESTAMP('2022-11-25 20:11:12','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Letzter SEPA Export',0,160,0,1,1,TO_TIMESTAMP('2022-11-25 20:11:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-25T19:11:12.738Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708212 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-25T19:11:12.762Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579515) 
;

-- 2022-11-25T19:11:12.769Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708212
;

-- 2022-11-25T19:11:12.771Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(708212)
;

-- 2022-11-25T19:11:33.200Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,575175,708213,0,352,0,TO_TIMESTAMP('2022-11-25 20:11:33','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Letzter SEPA Export von',0,170,0,1,1,TO_TIMESTAMP('2022-11-25 20:11:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-25T19:11:33.201Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708213 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-25T19:11:33.202Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579516) 
;

-- 2022-11-25T19:11:33.204Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708213
;

-- 2022-11-25T19:11:33.204Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(708213)
;

-- 2022-11-25T19:11:35.991Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-11-25 20:11:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708213
;

-- 2022-11-25T19:11:40.626Z
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-11-25 20:11:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708212
;

-- 2022-11-25T19:12:04.616Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,575176,708214,0,352,0,TO_TIMESTAMP('2022-11-25 20:12:04','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','Y','N','Letzter Revolut Export',0,180,0,1,1,TO_TIMESTAMP('2022-11-25 20:12:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-25T19:12:04.617Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708214 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-25T19:12:04.618Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579517) 
;

-- 2022-11-25T19:12:04.620Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708214
;

-- 2022-11-25T19:12:04.620Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(708214)
;

-- 2022-11-25T19:12:20.774Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,575177,708215,0,352,0,TO_TIMESTAMP('2022-11-25 20:12:20','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','Y','N','Letzter Revolut Export von',0,190,0,1,1,TO_TIMESTAMP('2022-11-25 20:12:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-25T19:12:20.775Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708215 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-25T19:12:20.776Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579518) 
;

-- 2022-11-25T19:12:20.778Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708215
;

-- 2022-11-25T19:12:20.779Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(708215)
;

-- 2022-11-25T19:13:24.144Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,708212,0,352,541028,613588,'F',TO_TIMESTAMP('2022-11-25 20:13:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Letzter SEPA Export',20,0,0,TO_TIMESTAMP('2022-11-25 20:13:24','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2022-11-25T19:13:39.874Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,708213,0,352,541028,613589,'F',TO_TIMESTAMP('2022-11-25 20:13:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Letzter SEPA Export von',30,0,0,TO_TIMESTAMP('2022-11-25 20:13:39','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2022-11-25T19:13:50.198Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,708214,0,352,541028,613590,'F',TO_TIMESTAMP('2022-11-25 20:13:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Letzter Revolut Export',40,0,0,TO_TIMESTAMP('2022-11-25 20:13:50','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2022-11-25T19:14:02.054Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,708215,0,352,541028,613591,'F',TO_TIMESTAMP('2022-11-25 20:14:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Letzter Revolut Export von',50,0,0,TO_TIMESTAMP('2022-11-25 20:14:01','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

