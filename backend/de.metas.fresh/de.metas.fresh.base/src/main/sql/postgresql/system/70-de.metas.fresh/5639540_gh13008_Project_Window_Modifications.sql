-- 2022-05-18T13:35:12.559Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,582993,693828,0,541830,0,TO_TIMESTAMP('2022-05-18 14:35:10','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Externe Projektreferenz',0,360,0,1,1,TO_TIMESTAMP('2022-05-18 14:35:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-18T13:35:12.647Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=693828 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-18T13:35:12.752Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580859) 
;

-- 2022-05-18T13:35:12.844Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=693828
;

-- 2022-05-18T13:35:12.928Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(693828)
;

-- 2022-05-18T13:35:47.263Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,582994,693829,0,541830,0,TO_TIMESTAMP('2022-05-18 14:35:45','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Elternprojekt',0,370,0,1,1,TO_TIMESTAMP('2022-05-18 14:35:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-18T13:35:47.351Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=693829 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-05-18T13:35:47.445Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580860) 
;

-- 2022-05-18T13:35:47.531Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=693829
;

-- 2022-05-18T13:35:47.913Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(693829)
;

-- 2022-05-18T13:36:55.323Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,693828,0,541830,542695,606412,'F',TO_TIMESTAMP('2022-05-18 14:36:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Externe Projektreferenz',35,0,0,TO_TIMESTAMP('2022-05-18 14:36:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-18T13:37:25.793Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,693829,0,541830,542695,606413,'F',TO_TIMESTAMP('2022-05-18 14:37:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Elternprojekt',45,0,0,TO_TIMESTAMP('2022-05-18 14:37:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-18T13:40:23.532Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-05-18 14:40:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560249
;

-- 2022-05-18T13:40:23.876Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-05-18 14:40:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=606412
;

-- 2022-05-18T14:01:36.491Z
-- URL zum Konzept
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540581,'C_Project.C_Project_ID NOT IN (SELECT DISTINCT pp.C_Project_ID FROM c_project pp WHERE pp.C_Project_Parent_ID = @C_Project_ID@) AND C_Project.C_Project_ID != @C_Project_ID@',TO_TIMESTAMP('2022-05-18 15:01:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Parent Project','S',TO_TIMESTAMP('2022-05-18 15:01:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-05-18T14:02:50.918Z
-- URL zum Konzept
UPDATE AD_Column SET AD_Val_Rule_ID=540581,Updated=TO_TIMESTAMP('2022-05-18 15:02:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582994
;

