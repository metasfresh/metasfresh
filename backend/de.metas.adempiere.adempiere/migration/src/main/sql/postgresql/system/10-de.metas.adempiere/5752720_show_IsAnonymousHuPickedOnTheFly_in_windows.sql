-- Run mode: SWING_CLIENT
-- select migrationscript_ignore('10-de.metas.adempiere/5752720_show_IsAnonymousHuPickedOnTheFly_in_windows.sql');

-- Field: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> Anonymous HU Picked On the Fly
-- Column: MobileUI_UserProfile_Picking.IsAnonymousHuPickedOnTheFly
-- 2025-04-24T13:32:11.680Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589910,741984,0,547258,TO_TIMESTAMP('2025-04-24 13:32:11.237000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Anonymous HU Picked On the Fly',TO_TIMESTAMP('2025-04-24 13:32:11.237000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-24T13:32:11.765Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741984 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-24T13:32:11.933Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577441)
;

-- 2025-04-24T13:32:11.958Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741984
;

-- 2025-04-24T13:32:11.964Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741984)
;

-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 20 -> flags.Anonymous HU Picked On the Fly
-- Column: MobileUI_UserProfile_Picking.IsAnonymousHuPickedOnTheFly
-- 2025-04-24T13:32:44.713Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741984,0,547258,551252,631379,'F',TO_TIMESTAMP('2025-04-24 13:32:44.470000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Anonymous HU Picked On the Fly',130,0,0,TO_TIMESTAMP('2025-04-24 13:32:44.470000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Mobile UI Kommissionieraufgabe Optionen(541862,D) -> Mobile UI Kommissionieraufgabe Optionen(547823,D) -> Anonymous HU Picked On the Fly
-- Column: MobileUI_UserProfile_Picking_Job.IsAnonymousHuPickedOnTheFly
-- 2025-04-24T13:35:13.520Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589909,741985,0,547823,TO_TIMESTAMP('2025-04-24 13:35:13.332000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Anonymous HU Picked On the Fly',TO_TIMESTAMP('2025-04-24 13:35:13.332000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-24T13:35:13.527Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741985 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-24T13:35:13.530Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577441)
;

-- 2025-04-24T13:35:13.534Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741985
;

-- 2025-04-24T13:35:13.535Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741985)
;

-- UI Element: Mobile UI Kommissionieraufgabe Optionen(541862,D) -> Mobile UI Kommissionieraufgabe Optionen(547823,D) -> main -> 20 -> flags.Anonymous HU Picked On the Fly
-- Column: MobileUI_UserProfile_Picking_Job.IsAnonymousHuPickedOnTheFly
-- 2025-04-24T13:35:40.215Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741985,0,547823,552482,631380,'F',TO_TIMESTAMP('2025-04-24 13:35:40.036000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Anonymous HU Picked On the Fly',120,0,0,TO_TIMESTAMP('2025-04-24 13:35:40.036000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

