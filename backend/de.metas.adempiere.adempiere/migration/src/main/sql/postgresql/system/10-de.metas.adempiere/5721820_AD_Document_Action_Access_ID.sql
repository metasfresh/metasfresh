-- Run mode: SWING_CLIENT

-- Column: AD_Document_Action_Access.AD_Document_Action_Access_ID
-- 2024-04-15T07:18:20.868Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,588187,543335,0,13,53012,'AD_Document_Action_Access_ID',TO_TIMESTAMP('2024-04-15 07:18:20.718000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D',10,'Y','Y','N','N','N','Y','Y','N','N','N','N','Document Action Access',TO_TIMESTAMP('2024-04-15 07:18:20.718000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1)
;

-- 2024-04-15T07:18:20.870Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588187 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-15T07:18:20.999Z
/* DDL */  select update_Column_Translation_From_AD_Element(543335)
;

-- 2024-04-15T07:18:21.123Z
CREATE SEQUENCE AD_DOCUMENT_ACTION_ACCESS_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- 2024-04-15T07:18:21.134Z
ALTER TABLE AD_Document_Action_Access ADD COLUMN AD_Document_Action_Access_ID numeric(10,0)
;

-- 2024-04-15T07:18:21.186Z
ALTER TABLE AD_Document_Action_Access ALTER COLUMN AD_Document_Action_Access_ID SET DEFAULT nextval('ad_document_action_access_seq')
;

-- 2024-04-15T07:18:21.196Z
ALTER TABLE AD_Document_Action_Access ALTER COLUMN AD_Document_Action_Access_ID SET NOT NULL
;

-- 2024-04-15T07:18:21.207Z
ALTER TABLE AD_Document_Action_Access DROP CONSTRAINT IF EXISTS ad_document_action_access_pkey
;

-- 2024-04-15T07:18:21.216Z
ALTER TABLE AD_Document_Action_Access DROP CONSTRAINT IF EXISTS ad_document_action_access_key
;

-- 2024-04-15T07:18:21.225Z
ALTER TABLE AD_Document_Action_Access ADD CONSTRAINT ad_document_action_access_pkey PRIMARY KEY (AD_Document_Action_Access_ID)
;

-- Field: Rolle - Verwaltung(111,D) -> Belegaktion-Zugriff(53013,D) -> Document Action Access
-- Column: AD_Document_Action_Access.AD_Document_Action_Access_ID
-- 2024-04-15T07:18:21.426Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588187,728049,0,53013,TO_TIMESTAMP('2024-04-15 07:18:21.306000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Document Action Access',TO_TIMESTAMP('2024-04-15 07:18:21.306000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-04-15T07:18:21.431Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728049 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-15T07:18:21.433Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543335)
;

-- 2024-04-15T07:18:21.437Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728049
;

-- 2024-04-15T07:18:21.438Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728049)
;

