-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Assignment
-- Column: M_Delivery_Planning.UserElementString1
-- 2023-05-29T15:15:05.376Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586721,716052,0,546674,TO_TIMESTAMP('2023-05-29 18:15:04','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','Assignment',TO_TIMESTAMP('2023-05-29 18:15:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T15:15:05.413Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716052 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T15:15:05.453Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578653) 
;

-- 2023-05-29T15:15:05.501Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716052
;

-- 2023-05-29T15:15:05.539Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716052)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> UserElementString2
-- Column: M_Delivery_Planning.UserElementString2
-- 2023-05-29T15:15:06.170Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586722,716053,0,546674,TO_TIMESTAMP('2023-05-29 18:15:05','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString2',TO_TIMESTAMP('2023-05-29 18:15:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T15:15:06.206Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716053 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T15:15:06.287Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578654) 
;

-- 2023-05-29T15:15:06.329Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716053
;

-- 2023-05-29T15:15:06.365Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716053)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> UserElementString3
-- Column: M_Delivery_Planning.UserElementString3
-- 2023-05-29T15:15:06.974Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586723,716054,0,546674,TO_TIMESTAMP('2023-05-29 18:15:06','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString3',TO_TIMESTAMP('2023-05-29 18:15:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T15:15:07.011Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716054 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T15:15:07.048Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578655) 
;

-- 2023-05-29T15:15:07.091Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716054
;

-- 2023-05-29T15:15:07.127Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716054)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> UserElementString4
-- Column: M_Delivery_Planning.UserElementString4
-- 2023-05-29T15:15:07.743Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586724,716055,0,546674,TO_TIMESTAMP('2023-05-29 18:15:07','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString4',TO_TIMESTAMP('2023-05-29 18:15:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T15:15:07.788Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716055 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T15:15:07.825Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578656) 
;

-- 2023-05-29T15:15:07.868Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716055
;

-- 2023-05-29T15:15:07.903Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716055)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> UserElementString5
-- Column: M_Delivery_Planning.UserElementString5
-- 2023-05-29T15:15:08.548Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586725,716056,0,546674,TO_TIMESTAMP('2023-05-29 18:15:07','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString5',TO_TIMESTAMP('2023-05-29 18:15:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T15:15:08.585Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716056 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T15:15:08.627Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578657) 
;

-- 2023-05-29T15:15:08.669Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716056
;

-- 2023-05-29T15:15:08.705Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716056)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> UserElementString6
-- Column: M_Delivery_Planning.UserElementString6
-- 2023-05-29T15:15:09.374Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586726,716057,0,546674,TO_TIMESTAMP('2023-05-29 18:15:08','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString6',TO_TIMESTAMP('2023-05-29 18:15:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T15:15:09.412Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716057 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T15:15:09.448Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578658) 
;

-- 2023-05-29T15:15:09.497Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716057
;

-- 2023-05-29T15:15:09.534Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716057)
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> UserElementString7
-- Column: M_Delivery_Planning.UserElementString7
-- 2023-05-29T15:15:10.150Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586727,716058,0,546674,TO_TIMESTAMP('2023-05-29 18:15:09','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString7',TO_TIMESTAMP('2023-05-29 18:15:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T15:15:10.188Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716058 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T15:15:10.226Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578659) 
;

-- 2023-05-29T15:15:10.313Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716058
;

-- 2023-05-29T15:15:10.359Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716058)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.UserElementString2
-- Column: M_Delivery_Planning.UserElementString2
-- 2023-05-29T15:16:04.222Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716053,0,546674,550028,617906,'F',TO_TIMESTAMP('2023-05-29 18:16:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'UserElementString2',380,0,0,TO_TIMESTAMP('2023-05-29 18:16:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.UserElementString3
-- Column: M_Delivery_Planning.UserElementString3
-- 2023-05-29T15:16:17.411Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716054,0,546674,550028,617907,'F',TO_TIMESTAMP('2023-05-29 18:16:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'UserElementString3',390,0,0,TO_TIMESTAMP('2023-05-29 18:16:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.UserElementString3
-- Column: M_Delivery_Planning.UserElementString3
-- 2023-05-29T15:16:24.591Z
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2023-05-29 18:16:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=617907
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.UserElementString2
-- Column: M_Delivery_Planning.UserElementString2
-- 2023-05-29T15:16:30.703Z
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2023-05-29 18:16:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=617906
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.UserElementString2
-- Column: M_Delivery_Planning.UserElementString2
-- 2023-05-29T15:17:15.885Z
UPDATE AD_UI_Element SET SeqNo=161,Updated=TO_TIMESTAMP('2023-05-29 18:17:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=617906
;

-- UI Element: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> main -> 10 -> default.UserElementString3
-- Column: M_Delivery_Planning.UserElementString3
-- 2023-05-29T15:17:21.409Z
UPDATE AD_UI_Element SET SeqNo=162,Updated=TO_TIMESTAMP('2023-05-29 18:17:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=617907
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> Assignment
-- Column: M_Delivery_Planning.UserElementString1
-- 2023-05-29T15:24:58.627Z
UPDATE AD_Field SET DisplayLogic='@$Element_S1/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 18:24:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716052
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> UserElementString2
-- Column: M_Delivery_Planning.UserElementString2
-- 2023-05-29T15:25:04.392Z
UPDATE AD_Field SET DisplayLogic='@$Element_S2/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 18:25:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716053
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> UserElementString3
-- Column: M_Delivery_Planning.UserElementString3
-- 2023-05-29T15:25:11Z
UPDATE AD_Field SET DisplayLogic='@$Element_S3/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 18:25:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716054
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> UserElementString4
-- Column: M_Delivery_Planning.UserElementString4
-- 2023-05-29T15:25:17.639Z
UPDATE AD_Field SET DisplayLogic='@$Element_S4/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 18:25:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716055
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> UserElementString5
-- Column: M_Delivery_Planning.UserElementString5
-- 2023-05-29T15:25:24.531Z
UPDATE AD_Field SET DisplayLogic='@$Element_S5/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 18:25:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716056
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> UserElementString6
-- Column: M_Delivery_Planning.UserElementString6
-- 2023-05-29T15:25:31.005Z
UPDATE AD_Field SET DisplayLogic='@$Element_S6/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 18:25:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716057
;

-- Field: Delivery Planning(541632,D) -> Delivery Planning(546674,D) -> UserElementString7
-- Column: M_Delivery_Planning.UserElementString7
-- 2023-05-29T15:25:38.706Z
UPDATE AD_Field SET DisplayLogic='@$Element_S7/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 18:25:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716058
;

