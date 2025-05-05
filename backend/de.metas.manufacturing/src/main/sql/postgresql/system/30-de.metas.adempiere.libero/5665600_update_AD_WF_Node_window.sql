-- Field: Produktionsaktivität(541047,D) -> Produktionsaktivität(543532,D) -> Manufacturing Activity Type
-- Column: AD_WF_Node.PP_Activity_Type
-- 2022-11-22T10:00:14.415Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,578131,708149,0,543532,TO_TIMESTAMP('2022-11-22 12:00:13','YYYY-MM-DD HH24:MI:SS'),100,40,'D','Y','N','N','N','N','N','N','N','Manufacturing Activity Type',TO_TIMESTAMP('2022-11-22 12:00:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-22T10:00:14.416Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708149 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-22T10:00:14.418Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580165) 
;

-- 2022-11-22T10:00:14.420Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708149
;

-- 2022-11-22T10:00:14.421Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708149)
;

-- Field: Produktionsaktivität(541047,D) -> Produktionsaktivität(543532,D) -> Always available to user
-- Column: AD_WF_Node.PP_AlwaysAvailableToUser
-- 2022-11-22T10:00:27.205Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584679,708150,0,543532,TO_TIMESTAMP('2022-11-22 12:00:27','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Always available to user',TO_TIMESTAMP('2022-11-22 12:00:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-22T10:00:27.207Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708150 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-22T10:00:27.209Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581540) 
;

-- 2022-11-22T10:00:27.215Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708150
;

-- 2022-11-22T10:00:27.216Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708150)
;

-- Field: Produktionsaktivität(541047,D) -> Produktionsaktivität(543532,D) -> User Instructions
-- Column: AD_WF_Node.PP_UserInstructions
-- 2022-11-22T10:00:34.029Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585110,708151,0,543532,TO_TIMESTAMP('2022-11-22 12:00:33','YYYY-MM-DD HH24:MI:SS'),100,2000,'D','Y','N','N','N','N','N','N','N','User Instructions',TO_TIMESTAMP('2022-11-22 12:00:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-22T10:00:34.032Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=708151 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-22T10:00:34.036Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581715) 
;

-- 2022-11-22T10:00:34.040Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708151
;

-- 2022-11-22T10:00:34.041Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708151)
;

-- UI Column: Produktionsaktivität(541047,D) -> Produktionsaktivität(543532,D) -> main -> 20
-- UI Element Group: mobile UI
-- 2022-11-22T11:02:59.251Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,543405,550043,TO_TIMESTAMP('2022-11-22 13:02:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','mobile UI',40,TO_TIMESTAMP('2022-11-22 13:02:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Produktionsaktivität(541047,D) -> Produktionsaktivität(543532,D) -> main -> 20
-- UI Element Group: mobile UI
-- 2022-11-22T11:03:09.148Z
UPDATE AD_UI_ElementGroup SET SeqNo=25,Updated=TO_TIMESTAMP('2022-11-22 13:03:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550043
;

-- UI Element: Produktionsaktivität(541047,D) -> Produktionsaktivität(543532,D) -> main -> 20 -> mobile UI.Manufacturing Activity Type
-- Column: AD_WF_Node.PP_Activity_Type
-- 2022-11-22T11:03:29.452Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708149,0,543532,550043,613542,'F',TO_TIMESTAMP('2022-11-22 13:03:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Manufacturing Activity Type',10,0,0,TO_TIMESTAMP('2022-11-22 13:03:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produktionsaktivität(541047,D) -> Produktionsaktivität(543532,D) -> main -> 20 -> mobile UI.Always available to user
-- Column: AD_WF_Node.PP_AlwaysAvailableToUser
-- 2022-11-22T11:03:37.632Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708150,0,543532,550043,613543,'F',TO_TIMESTAMP('2022-11-22 13:03:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Always available to user',20,0,0,TO_TIMESTAMP('2022-11-22 13:03:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produktionsaktivität(541047,D) -> Produktionsaktivität(543532,D) -> main -> 20 -> mobile UI.User Instructions
-- Column: AD_WF_Node.PP_UserInstructions
-- 2022-11-22T11:03:46.665Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,708151,0,543532,550043,613544,'F',TO_TIMESTAMP('2022-11-22 13:03:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','User Instructions',30,0,0,TO_TIMESTAMP('2022-11-22 13:03:46','YYYY-MM-DD HH24:MI:SS'),100)
;

