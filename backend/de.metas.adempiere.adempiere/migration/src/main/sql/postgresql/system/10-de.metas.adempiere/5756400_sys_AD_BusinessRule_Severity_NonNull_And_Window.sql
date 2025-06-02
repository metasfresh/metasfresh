-- Column: AD_BusinessRule.Severity
-- 2025-06-02T08:21:46.472Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2025-06-02 08:21:46.471000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590135
;

-- 2025-06-02T08:21:49.704Z
INSERT INTO t_alter_column values('ad_businessrule','Severity','VARCHAR(250)',null,'N')
;

-- 2025-06-02T08:21:49.728Z
UPDATE AD_BusinessRule SET Severity='N' WHERE Severity IS NULL
;

-- 2025-06-02T08:21:49.733Z
INSERT INTO t_alter_column values('ad_businessrule','Severity',null,'NOT NULL',null)
;



-- Field: Business Rule(541837,D) -> Business Rule(547699,D) -> Schweregrad
-- Column: AD_BusinessRule.Severity
-- 2025-06-02T08:23:43.761Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590135,747476,0,547699,TO_TIMESTAMP('2025-06-02 08:23:43.344000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,250,'D','Y','N','N','N','N','N','N','N','Schweregrad',TO_TIMESTAMP('2025-06-02 08:23:43.344000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-02T08:23:43.764Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747476 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-02T08:23:43.766Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583678)
;

-- 2025-06-02T08:23:43.784Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747476
;

-- 2025-06-02T08:23:43.789Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747476)
;

-- UI Column: Business Rule(541837,D) -> Business Rule(547699,D) -> main -> 20
-- UI Element Group: severity
-- 2025-06-02T08:25:26.644Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547680,553086,TO_TIMESTAMP('2025-06-02 08:25:26.440000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','severity',20,TO_TIMESTAMP('2025-06-02 08:25:26.440000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Business Rule(541837,D) -> Business Rule(547699,D) -> main -> 20
-- UI Element Group: org&client
-- 2025-06-02T08:25:31.145Z
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2025-06-02 08:25:31.144000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=552196
;

-- UI Element: Business Rule(541837,D) -> Business Rule(547699,D) -> main -> 20 -> severity.Schweregrad
-- Column: AD_BusinessRule.Severity
-- 2025-06-02T08:25:45.318Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747476,0,547699,553086,633890,'F',TO_TIMESTAMP('2025-06-02 08:25:45.168000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Schweregrad',10,0,0,TO_TIMESTAMP('2025-06-02 08:25:45.168000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

