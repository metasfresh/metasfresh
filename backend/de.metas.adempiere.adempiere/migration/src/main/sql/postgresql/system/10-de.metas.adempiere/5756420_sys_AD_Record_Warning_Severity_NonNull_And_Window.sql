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



-- UI Element: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 20 -> severity.Schweregrad
-- Column: AD_Record_Warning.Severity
-- 2025-06-02T12:45:54.138Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-06-02 12:45:54.138000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=633891
;

-- UI Element: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 20 -> flags.Bestätigt
-- Column: AD_Record_Warning.IsAcknowledged
-- 2025-06-02T12:45:54.148Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-06-02 12:45:54.148000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=633172
;

-- UI Element: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 20 -> flags.Aktiv
-- Column: AD_Record_Warning.IsActive
-- 2025-06-02T12:45:54.154Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-06-02 12:45:54.154000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627399
;

-- UI Element: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 20 -> org&client.Sektion
-- Column: AD_Record_Warning.AD_Org_ID
-- 2025-06-02T12:45:54.161Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-06-02 12:45:54.161000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627400
;

-- UI Element: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 20 -> flags.Bestätigt
-- Column: AD_Record_Warning.IsAcknowledged
-- 2025-06-02T12:46:01.170Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-06-02 12:46:01.170000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=633172
;

-- UI Element: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 20 -> severity.Schweregrad
-- Column: AD_Record_Warning.Severity
-- 2025-06-02T12:46:01.176Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-06-02 12:46:01.176000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=633891
;

-- UI Element: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 20 -> flags.Aktiv
-- Column: AD_Record_Warning.IsActive
-- 2025-06-02T12:46:12.449Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2025-06-02 12:46:12.449000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627399
;

-- UI Element: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 20 -> org&client.Sektion
-- Column: AD_Record_Warning.AD_Org_ID
-- 2025-06-02T12:46:12.482Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-06-02 12:46:12.482000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627400
;

-- Column: AD_Record_Warning.Severity
-- 2025-06-02T12:48:32.091Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=80,Updated=TO_TIMESTAMP('2025-06-02 12:48:32.090000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590136
;

-- Column: AD_Record_Warning.IsActive
-- 2025-06-02T12:48:34.891Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=90,Updated=TO_TIMESTAMP('2025-06-02 12:48:34.890000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589486
;

-- Column: AD_Record_Warning.AD_Org_ID
-- 2025-06-02T12:48:36.650Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=100,Updated=TO_TIMESTAMP('2025-06-02 12:48:36.650000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589483
;

-- UI Element: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 20 -> flags.Aktiv
-- Column: AD_Record_Warning.IsActive
-- 2025-06-02T12:49:04.848Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-06-02 12:49:04.848000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627399
;

-- UI Element: Warnhinweise(541836,D) -> Warnhinweise(547698,D) -> main -> 20 -> org&client.Sektion
-- Column: AD_Record_Warning.AD_Org_ID
-- 2025-06-02T12:49:04.853Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-06-02 12:49:04.853000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=627400
;

