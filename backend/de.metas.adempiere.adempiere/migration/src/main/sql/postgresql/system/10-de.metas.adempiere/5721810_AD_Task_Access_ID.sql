-- Run mode: SWING_CLIENT

-- Column: AD_Task_Access.AD_Task_Access_ID
-- 2024-04-15T07:17:51.658Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,588186,543338,0,13,199,'AD_Task_Access_ID',TO_TIMESTAMP('2024-04-15 07:17:50.492000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','AD_Task_Access',TO_TIMESTAMP('2024-04-15 07:17:50.492000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1)
;

-- 2024-04-15T07:17:51.660Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588186 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-15T07:17:51.775Z
/* DDL */  select update_Column_Translation_From_AD_Element(543338)
;

-- 2024-04-15T07:17:51.886Z
CREATE SEQUENCE AD_TASK_ACCESS_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2024-04-15T07:17:51.897Z
ALTER TABLE AD_Task_Access ADD COLUMN AD_Task_Access_ID numeric(10,0)
;

-- 2024-04-15T07:17:51.910Z
ALTER TABLE AD_Task_Access ALTER COLUMN AD_Task_Access_ID SET DEFAULT nextval('ad_task_access_seq')
;

-- 2024-04-15T07:17:51.920Z
ALTER TABLE AD_Task_Access ALTER COLUMN AD_Task_Access_ID SET NOT NULL
;

-- 2024-04-15T07:17:51.929Z
ALTER TABLE AD_Task_Access DROP CONSTRAINT IF EXISTS ad_task_access_pkey
;

-- 2024-04-15T07:17:51.940Z
ALTER TABLE AD_Task_Access DROP CONSTRAINT IF EXISTS ad_task_access_key
;

-- 2024-04-15T07:17:51.949Z
ALTER TABLE AD_Task_Access ADD CONSTRAINT ad_task_access_pkey PRIMARY KEY (AD_Task_Access_ID)
;

-- Field: Rolle - Verwaltung(111,D) -> Aufgaben-Zugriff(313,D) -> AD_Task_Access
-- Column: AD_Task_Access.AD_Task_Access_ID
-- 2024-04-15T07:17:52.384Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588186,728047,0,313,TO_TIMESTAMP('2024-04-15 07:17:52.265000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','AD_Task_Access',TO_TIMESTAMP('2024-04-15 07:17:52.265000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-04-15T07:17:52.387Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728047 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-15T07:17:52.389Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543338)
;

-- 2024-04-15T07:17:52.391Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728047
;

-- 2024-04-15T07:17:52.392Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728047)
;

-- Field: Aufgabe(114,D) -> Berechtigung(310,D) -> AD_Task_Access
-- Column: AD_Task_Access.AD_Task_Access_ID
-- 2024-04-15T07:17:52.521Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588186,728048,0,310,TO_TIMESTAMP('2024-04-15 07:17:52.406000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','AD_Task_Access',TO_TIMESTAMP('2024-04-15 07:17:52.406000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-04-15T07:17:52.523Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728048 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-15T07:17:52.525Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543338)
;

-- 2024-04-15T07:17:52.530Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728048
;

-- 2024-04-15T07:17:52.531Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728048)
;

