-- 2024-09-02T11:05:19.566Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583239,0,'IsPickingWithNewLU',TO_TIMESTAMP('2024-09-02 14:05:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kommissionieren mit LU','Kommissionieren mit LU',TO_TIMESTAMP('2024-09-02 14:05:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-02T11:05:19.583Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583239 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: MobileUI_UserProfile_Picking.IsPickingWithNewLU
-- Column: MobileUI_UserProfile_Picking.IsPickingWithNewLU
-- 2024-09-02T11:05:54.061Z
UPDATE AD_Column SET AD_Element_ID=583239, ColumnName='IsPickingWithNewLU', DefaultValue='N', Description=NULL, Help=NULL, Name='Kommissionieren mit LU',Updated=TO_TIMESTAMP('2024-09-02 14:05:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588864
;

-- 2024-09-02T11:05:54.065Z
UPDATE AD_Field SET Name='Kommissionieren mit LU', Description=NULL, Help=NULL WHERE AD_Column_ID=588864
;

-- 2024-09-02T11:05:54.105Z
/* DDL */  select update_Column_Translation_From_AD_Element(583239) 
;

-- 2024-09-02T11:05:56.800Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking','ALTER TABLE public.MobileUI_UserProfile_Picking ADD COLUMN IsPickingWithNewLU CHAR(1) DEFAULT ''N'' CHECK (IsPickingWithNewLU IN (''Y'',''N'')) NOT NULL')
;



-- Element: IsPickingWithNewLU
-- 2024-09-02T11:12:07.519Z
UPDATE AD_Element_Trl SET Name='Pick with LU', PrintName='Pick with LU',Updated=TO_TIMESTAMP('2024-09-02 14:12:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583239 AND AD_Language='en_US'
;

-- 2024-09-02T11:12:07.523Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583239,'en_US')
;

-- Field: Mobile UI Kommissionierprofil -> Mobile UI Kommissionierprofil -> Kommissionieren mit LU
-- Column: MobileUI_UserProfile_Picking.IsPickingWithNewLU
-- Field: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> Kommissionieren mit LU
-- Column: MobileUI_UserProfile_Picking.IsPickingWithNewLU
-- 2024-09-02T11:28:44.932Z
UPDATE AD_Field SET AD_Name_ID=583239, Description=NULL, Help=NULL, Name='Kommissionieren mit LU',Updated=TO_TIMESTAMP('2024-09-02 14:28:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=729105
;

-- 2024-09-02T11:28:44.935Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583239)
;

-- 2024-09-02T11:28:44.952Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729105
;

-- 2024-09-02T11:28:44.968Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729105)
;

