-- Column: AD_Record_Warning.Severity
-- 2025-06-02T08:32:12.444Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2025-06-02 08:32:12.443000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590136
;

-- 2025-06-02T08:32:14.185Z
INSERT INTO t_alter_column values('ad_record_warning','Severity','VARCHAR(250)',null,null)
;

-- 2025-06-02T08:32:14.188Z
INSERT INTO t_alter_column values('ad_record_warning','Severity',null,'NOT NULL',null)
;



-- Field: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> Schweregrad
-- Column: AD_Record_Warning.Severity
-- 2025-06-02T08:32:48.331Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590136,747477,0,547698,TO_TIMESTAMP('2025-06-02 08:32:48.182000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,250,'D','Y','N','N','N','N','N','N','N','Schweregrad',TO_TIMESTAMP('2025-06-02 08:32:48.182000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-02T08:32:48.332Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747477 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-02T08:32:48.334Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583678)
;

-- 2025-06-02T08:32:48.337Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747477
;

-- 2025-06-02T08:32:48.338Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747477)
;

-- UI Column: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 20
-- UI Element Group: severity
-- 2025-06-02T08:33:41.607Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547678,553087,TO_TIMESTAMP('2025-06-02 08:33:41.487000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','severity',30,TO_TIMESTAMP('2025-06-02 08:33:41.487000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 20
-- UI Element Group: org&client
-- 2025-06-02T08:33:44.752Z
UPDATE AD_UI_ElementGroup SET SeqNo=40,Updated=TO_TIMESTAMP('2025-06-02 08:33:44.751000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=552189
;

-- UI Element: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 20 -> severity.Schweregrad
-- Column: AD_Record_Warning.Severity
-- 2025-06-02T08:33:54.582Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747477,0,547698,553087,633891,'F',TO_TIMESTAMP('2025-06-02 08:33:54.394000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Schweregrad',10,0,0,TO_TIMESTAMP('2025-06-02 08:33:54.394000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;



