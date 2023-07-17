-- Field: Budget-Issue(540859,de.metas.serviceprovider) -> Issue(542341,de.metas.serviceprovider) -> Kostenstelle
-- Column: S_Issue.C_Activity_ID
-- 2022-08-22T11:57:07.887Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584051,705455,0,542341,TO_TIMESTAMP('2022-08-22 14:57:07','YYYY-MM-DD HH24:MI:SS'),100,'Kostenstelle',10,'de.metas.serviceprovider','Erfassung der zugehörigen Kostenstelle','Y','N','N','N','N','N','N','N','Kostenstelle',TO_TIMESTAMP('2022-08-22 14:57:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-22T11:57:07.890Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705455 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-22T11:57:07.937Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1005) 
;

-- 2022-08-22T11:57:08.049Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705455
;

-- 2022-08-22T11:57:08.057Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705455)
;

-- UI Element: Budget-Issue(540859,de.metas.serviceprovider) -> Issue(542341,de.metas.serviceprovider) -> main -> 10 -> default.Kostenstelle
-- Column: S_Issue.C_Activity_ID
-- 2022-08-22T11:58:32.716Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705455,0,542341,612157,543568,'F',TO_TIMESTAMP('2022-08-22 14:58:32','YYYY-MM-DD HH24:MI:SS'),100,'Kostenstelle','Erfassung der zugehörigen Kostenstelle','Y','N','N','Y','N','N','N',0,'Kostenstelle',65,0,0,TO_TIMESTAMP('2022-08-22 14:58:32','YYYY-MM-DD HH24:MI:SS'),100)
;

