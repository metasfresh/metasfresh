-- 2025-02-14T17:23:24.677Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583487,0,'Warning_Message_ID',TO_TIMESTAMP('2025-02-14 17:23:24.495000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Warning Message ID','Warning Message ID',TO_TIMESTAMP('2025-02-14 17:23:24.495000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-14T17:23:24.681Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583487 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: AD_BusinessRule.Warning_Message_ID
-- 2025-02-14T17:24:40.504Z
UPDATE AD_Column SET AD_Element_ID=583487, ColumnName='Warning_Message_ID', Description=NULL, Help=NULL, Name='Warning Message ID',Updated=TO_TIMESTAMP('2025-02-14 17:24:40.503000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589667
;

-- 2025-02-14T17:24:40.505Z
UPDATE AD_Column_Trl trl SET Name='Warning Message ID' WHERE AD_Column_ID=589667 AND AD_Language='de_DE'
;

-- 2025-02-14T17:24:40.506Z
UPDATE AD_Field SET Name='Warning Message ID', Description=NULL, Help=NULL WHERE AD_Column_ID=589667
;

-- 2025-02-14T17:24:40.663Z
/* DDL */  select update_Column_Translation_From_AD_Element(583487)
;

-- Column: AD_BusinessRule.Warning_Message_ID
-- 2025-02-14T17:25:23.939Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2025-02-14 17:25:23.939000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589667
;

-- 2025-02-14T17:25:26.717Z
/* DDL */ SELECT public.db_alter_table('AD_BusinessRule','ALTER TABLE public.AD_BusinessRule ADD COLUMN Warning_Message_ID NUMERIC(10)')
;

-- 2025-02-14T17:25:26.724Z
ALTER TABLE AD_BusinessRule ADD CONSTRAINT WarningMessage_ADBusinessRule FOREIGN KEY (Warning_Message_ID) REFERENCES public.AD_Message DEFERRABLE INITIALLY DEFERRED
;

UPDATE AD_BusinessRule SET Warning_Message_id=ad_message_id
;
