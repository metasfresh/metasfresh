-- Run mode: SWING_CLIENT

-- Column: Fact_Acct_Transactions_View.AccountConceptualName
-- 2025-04-18T11:02:25.768Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,589918,582046,0,10,541485,'AccountConceptualName',TO_TIMESTAMP('2025-04-18 11:02:25.578000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',255,'Y','Y','N','N','N','N','N','N','N','N','Y','Buchungsschlüssel',TO_TIMESTAMP('2025-04-18 11:02:25.578000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-04-18T11:02:25.773Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589918 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-04-18T11:02:25.780Z
/* DDL */  select update_Column_Translation_From_AD_Element(582046)
;

-- Field: Buchführungs-Details(162,D) -> Buchführung(242,D) -> Buchungsschlüssel
-- Column: Fact_Acct_Transactions_View.AccountConceptualName
-- 2025-04-18T11:04:08.120Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589918,741981,0,242,TO_TIMESTAMP('2025-04-18 11:04:07.950000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Buchungsschlüssel',TO_TIMESTAMP('2025-04-18 11:04:07.950000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-18T11:04:08.122Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741981 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-18T11:04:08.125Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582046)
;

-- 2025-04-18T11:04:08.129Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741981
;

-- 2025-04-18T11:04:08.133Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741981)
;

-- UI Element: Buchführungs-Details(162,D) -> Buchführung(242,D) -> main -> 10 -> default.Buchungsschlüssel
-- Column: Fact_Acct_Transactions_View.AccountConceptualName
-- 2025-04-18T11:04:34.797Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741981,0,242,540304,631376,'F',TO_TIMESTAMP('2025-04-18 11:04:34.624000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Buchungsschlüssel',80,0,0,TO_TIMESTAMP('2025-04-18 11:04:34.624000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Buchführungs-Details(162,D) -> Buchführung(242,D) -> main -> 10 -> default.Buchungsschlüssel
-- Column: Fact_Acct_Transactions_View.AccountConceptualName
-- 2025-04-18T11:04:43.496Z
UPDATE AD_UI_Element SET SeqNo=25,Updated=TO_TIMESTAMP('2025-04-18 11:04:43.496000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631376
;

