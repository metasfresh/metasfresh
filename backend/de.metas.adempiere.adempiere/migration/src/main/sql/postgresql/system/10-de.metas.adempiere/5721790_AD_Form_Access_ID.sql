-- Run mode: SWING_CLIENT

-- Column: AD_Form_Access.AD_Form_Access_ID
-- 2024-04-15T07:16:47.197Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,588184,543336,0,13,378,'AD_Form_Access_ID',TO_TIMESTAMP('2024-04-15 07:16:47.050000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','AD_Form_Access',TO_TIMESTAMP('2024-04-15 07:16:47.050000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1)
;

-- 2024-04-15T07:16:47.200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588184 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-15T07:16:47.353Z
/* DDL */  select update_Column_Translation_From_AD_Element(543336)
;

-- 2024-04-15T07:16:47.473Z
CREATE SEQUENCE AD_FORM_ACCESS_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2024-04-15T07:16:47.484Z
ALTER TABLE AD_Form_Access ADD COLUMN AD_Form_Access_ID numeric(10,0)
;

-- 2024-04-15T07:16:47.498Z
ALTER TABLE AD_Form_Access ALTER COLUMN AD_Form_Access_ID SET DEFAULT nextval('ad_form_access_seq')
;

-- 2024-04-15T07:16:47.515Z
ALTER TABLE AD_Form_Access ALTER COLUMN AD_Form_Access_ID SET NOT NULL
;

-- 2024-04-15T07:16:47.526Z
ALTER TABLE AD_Form_Access DROP CONSTRAINT IF EXISTS ad_form_access_pkey
;

-- 2024-04-15T07:16:47.537Z
ALTER TABLE AD_Form_Access DROP CONSTRAINT IF EXISTS ad_form_access_key
;

-- 2024-04-15T07:16:47.545Z
ALTER TABLE AD_Form_Access ADD CONSTRAINT ad_form_access_pkey PRIMARY KEY (AD_Form_Access_ID)
;

-- Field: Rolle - Verwaltung(111,D) -> Fenster (nicht dynamisch)-Zugriff(306,D) -> AD_Form_Access
-- Column: AD_Form_Access.AD_Form_Access_ID
-- 2024-04-15T07:16:47.810Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588184,728042,0,306,TO_TIMESTAMP('2024-04-15 07:16:47.676000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','AD_Form_Access',TO_TIMESTAMP('2024-04-15 07:16:47.676000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-04-15T07:16:47.812Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728042 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-15T07:16:47.815Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543336)
;

-- 2024-04-15T07:16:47.820Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728042
;

-- 2024-04-15T07:16:47.822Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728042)
;

-- Field: Fenster (nicht dynamisch)(187,D) -> Berechtigung(309,D) -> AD_Form_Access
-- Column: AD_Form_Access.AD_Form_Access_ID
-- 2024-04-15T07:16:47.959Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588184,728043,0,309,TO_TIMESTAMP('2024-04-15 07:16:47.844000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','AD_Form_Access',TO_TIMESTAMP('2024-04-15 07:16:47.844000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-04-15T07:16:47.962Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728043 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-15T07:16:47.964Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543336)
;

-- 2024-04-15T07:16:47.967Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728043
;

-- 2024-04-15T07:16:47.967Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728043)
;

