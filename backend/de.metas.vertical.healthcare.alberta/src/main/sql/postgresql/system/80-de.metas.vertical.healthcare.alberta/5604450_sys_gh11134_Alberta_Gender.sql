

DO $$
    BEGIN


        -- 2021-09-14T08:39:34.687Z
        -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
        INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,579814,0,'Alberta_Gender',TO_TIMESTAMP('2021-09-14 11:39:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Geschlecht','Geschlecht',TO_TIMESTAMP('2021-09-14 11:39:34','YYYY-MM-DD HH24:MI:SS'),100)
        ;

        -- 2021-09-14T08:39:34.693Z
        -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
        INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=579814 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
        ;


    EXCEPTION
        WHEN UNIQUE_VIOLATION THEN RAISE NOTICE 'AD_Element 579814 already exists';

    END;

$$;


-- 2021-09-14T08:45:25.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=579814, ColumnName='Alberta_Gender', Description=NULL, Help=NULL, Name='Geschlecht',Updated=TO_TIMESTAMP('2021-09-14 11:45:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=573946
;

-- 2021-09-14T08:45:25.710Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(579814)
;

