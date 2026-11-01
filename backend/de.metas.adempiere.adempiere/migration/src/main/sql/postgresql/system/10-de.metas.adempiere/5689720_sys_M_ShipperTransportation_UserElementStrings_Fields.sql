-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> Assignment
-- Column: M_ShipperTransportation.UserElementString1
-- 2023-05-29T16:14:15.364Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586728,716061,0,546732,TO_TIMESTAMP('2023-05-29 19:14:14','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','Assignment',TO_TIMESTAMP('2023-05-29 19:14:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T16:14:15.403Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716061 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T16:14:15.442Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578653) 
;

-- 2023-05-29T16:14:15.508Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716061
;

-- 2023-05-29T16:14:15.546Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716061)
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> UserElementString2
-- Column: M_ShipperTransportation.UserElementString2
-- 2023-05-29T16:14:16.158Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586729,716062,0,546732,TO_TIMESTAMP('2023-05-29 19:14:15','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString2',TO_TIMESTAMP('2023-05-29 19:14:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T16:14:16.195Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716062 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T16:14:16.232Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578654) 
;

-- 2023-05-29T16:14:16.275Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716062
;

-- 2023-05-29T16:14:16.311Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716062)
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> UserElementString3
-- Column: M_ShipperTransportation.UserElementString3
-- 2023-05-29T16:14:16.967Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586730,716063,0,546732,TO_TIMESTAMP('2023-05-29 19:14:16','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString3',TO_TIMESTAMP('2023-05-29 19:14:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T16:14:17.004Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716063 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T16:14:17.041Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578655) 
;

-- 2023-05-29T16:14:17.082Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716063
;

-- 2023-05-29T16:14:17.122Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716063)
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> UserElementString4
-- Column: M_ShipperTransportation.UserElementString4
-- 2023-05-29T16:14:17.775Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586731,716064,0,546732,TO_TIMESTAMP('2023-05-29 19:14:17','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString4',TO_TIMESTAMP('2023-05-29 19:14:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T16:14:17.812Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716064 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T16:14:17.848Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578656) 
;

-- 2023-05-29T16:14:17.889Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716064
;

-- 2023-05-29T16:14:17.925Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716064)
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> UserElementString5
-- Column: M_ShipperTransportation.UserElementString5
-- 2023-05-29T16:14:18.570Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586732,716065,0,546732,TO_TIMESTAMP('2023-05-29 19:14:18','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString5',TO_TIMESTAMP('2023-05-29 19:14:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T16:14:18.606Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716065 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T16:14:18.643Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578657) 
;

-- 2023-05-29T16:14:18.688Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716065
;

-- 2023-05-29T16:14:18.725Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716065)
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> UserElementString6
-- Column: M_ShipperTransportation.UserElementString6
-- 2023-05-29T16:14:19.353Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586733,716066,0,546732,TO_TIMESTAMP('2023-05-29 19:14:18','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString6',TO_TIMESTAMP('2023-05-29 19:14:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T16:14:19.389Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716066 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T16:14:19.426Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578658) 
;

-- 2023-05-29T16:14:19.471Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716066
;

-- 2023-05-29T16:14:19.549Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716066)
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> UserElementString7
-- Column: M_ShipperTransportation.UserElementString7
-- 2023-05-29T16:14:20.173Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586734,716067,0,546732,TO_TIMESTAMP('2023-05-29 19:14:19','YYYY-MM-DD HH24:MI:SS'),100,1000,'D','Y','Y','N','N','N','N','N','UserElementString7',TO_TIMESTAMP('2023-05-29 19:14:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-29T16:14:20.210Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716067 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-29T16:14:20.247Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578659) 
;

-- 2023-05-29T16:14:20.288Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716067
;

-- 2023-05-29T16:14:20.324Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716067)
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> Assignment
-- Column: M_ShipperTransportation.UserElementString1
-- 2023-05-29T16:17:27.301Z
UPDATE AD_Field SET DisplayLogic='@$Element_S1/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 19:17:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716061
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> UserElementString2
-- Column: M_ShipperTransportation.UserElementString2
-- 2023-05-29T16:17:34.912Z
UPDATE AD_Field SET DisplayLogic='@$Element_S2/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 19:17:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716062
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> UserElementString3
-- Column: M_ShipperTransportation.UserElementString3
-- 2023-05-29T16:17:43.710Z
UPDATE AD_Field SET DisplayLogic='@$Element_S3/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 19:17:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716063
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> UserElementString4
-- Column: M_ShipperTransportation.UserElementString4
-- 2023-05-29T16:17:51.036Z
UPDATE AD_Field SET DisplayLogic='@$Element_S4/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 19:17:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716064
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> UserElementString5
-- Column: M_ShipperTransportation.UserElementString5
-- 2023-05-29T16:17:57.066Z
UPDATE AD_Field SET DisplayLogic='@$Element_S5/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 19:17:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716065
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> UserElementString6
-- Column: M_ShipperTransportation.UserElementString6
-- 2023-05-29T16:18:08.754Z
UPDATE AD_Field SET DisplayLogic='@$Element_S6/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 19:18:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716066
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> UserElementString7
-- Column: M_ShipperTransportation.UserElementString7
-- 2023-05-29T16:18:16.359Z
UPDATE AD_Field SET DisplayLogic='@$Element_S7/X@=Y',Updated=TO_TIMESTAMP('2023-05-29 19:18:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716067
;

-- UI Column: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 10
-- UI Element Group: user elements
-- 2023-05-29T16:22:08.048Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546539,550767,TO_TIMESTAMP('2023-05-29 19:22:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','user elements',25,TO_TIMESTAMP('2023-05-29 19:22:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 10 -> user elements.UserElementString2
-- Column: M_ShipperTransportation.UserElementString2
-- 2023-05-29T16:22:35.693Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716062,0,546732,550767,617909,'F',TO_TIMESTAMP('2023-05-29 19:22:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'UserElementString2',10,0,0,TO_TIMESTAMP('2023-05-29 19:22:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction(546732,D) -> main -> 10 -> user elements.UserElementString3
-- Column: M_ShipperTransportation.UserElementString3
-- 2023-05-29T16:22:55.851Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716063,0,546732,550767,617910,'F',TO_TIMESTAMP('2023-05-29 19:22:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'UserElementString3',20,0,0,TO_TIMESTAMP('2023-05-29 19:22:55','YYYY-MM-DD HH24:MI:SS'),100)
;

