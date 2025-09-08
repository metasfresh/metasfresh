--  Element: ProcessedDirectory

DO
$$
    BEGIN
        IF not exists(select 1
                      from AD_Element
                      where ColumnName = 'ProcessedDirectory') THEN

            -- 2022-10-18T15:53:11.582136400Z
            INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581583,0,'ProcessedDirectory',TO_TIMESTAMP('2022-10-18 18:53:11','YYYY-MM-DD HH24:MI:SS'),100,'Defines where files should be moved after being successfully processed.','D','Y','Processed Directory','Processed Directory',TO_TIMESTAMP('2022-10-18 18:53:11','YYYY-MM-DD HH24:MI:SS'),100)
            ;

            -- 2022-10-18T15:53:11.588312400Z
            INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581583 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
            ;

            -- Element: ProcessedDirectory
            -- 2022-10-18T15:54:09.841372400Z
            UPDATE AD_Element_Trl SET Description='Legt fest, wohin Dateien nach erfolgreicher Verarbeitung verschoben werden sollen.', Name='Bearbeitetes Verzeichnis', PrintName='Bearbeitetes Verzeichnis',Updated=TO_TIMESTAMP('2022-10-18 18:54:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581583 AND AD_Language='de_CH'
            ;

            -- 2022-10-18T15:54:09.865853100Z
            /* DDL */  PERFORM update_TRL_Tables_On_AD_Element_TRL_Update(581583,'de_CH')
            ;

            -- Element: ProcessedDirectory
            -- 2022-10-18T15:54:26.290049Z
            UPDATE AD_Element_Trl SET Description='Legt fest, wohin Dateien nach erfolgreicher Verarbeitung verschoben werden sollen.', Name='Bearbeitetes Verzeichnis', PrintName='Bearbeitetes Verzeichnis',Updated=TO_TIMESTAMP('2022-10-18 18:54:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581583 AND AD_Language='de_DE'
            ;

            -- 2022-10-18T15:54:26.291996300Z
            /* DDL */  PERFORM update_ad_element_on_ad_element_trl_update(581583,'de_DE')
            ;

            -- 2022-10-18T15:54:26.302655800Z
            /* DDL */  PERFORM update_TRL_Tables_On_AD_Element_TRL_Update(581583,'de_DE')
            ;

        END IF;
    END
$$;


--  Element: ErroredDirectory


DO
$$
    BEGIN
        IF not exists(select 1
                      from AD_Element
                      where ColumnName = 'ErroredDirectory') THEN

            -- 2022-10-18T15:58:53.651264700Z
            INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581584,0,'ErroredDirectory',TO_TIMESTAMP('2022-10-18 18:58:53','YYYY-MM-DD HH24:MI:SS'),100,'Defines where files should be moved after attempting to process them with error.','D','Y','Errored Directory','Errored Directory',TO_TIMESTAMP('2022-10-18 18:58:53','YYYY-MM-DD HH24:MI:SS'),100)
            ;

            -- 2022-10-18T15:58:53.654298800Z
            INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581584 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
            ;

            -- Element: ErroredDirectory
            -- 2022-10-18T15:59:17.553369700Z
            UPDATE AD_Element_Trl SET Description='Gibt an, wohin die Dateien nach einem fehlerhaften Verarbeitungsversuch verschoben werden sollen.',Updated=TO_TIMESTAMP('2022-10-18 18:59:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581584 AND AD_Language='de_CH'
            ;

            -- 2022-10-18T15:59:17.555368400Z
            /* DDL */  PERFORM update_TRL_Tables_On_AD_Element_TRL_Update(581584,'de_CH')
            ;

            -- Element: ErroredDirectory
            -- 2022-10-18T16:00:16.892907900Z
            UPDATE AD_Element_Trl SET Name='Fehlerhaftes Verzeichnis', PrintName='Fehlerhaftes Verzeichnis',Updated=TO_TIMESTAMP('2022-10-18 19:00:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581584 AND AD_Language='de_CH'
            ;

            -- 2022-10-18T16:00:16.895039600Z
            /* DDL */  PERFORM update_TRL_Tables_On_AD_Element_TRL_Update(581584,'de_CH')
            ;

            -- Element: ErroredDirectory
            -- 2022-10-18T16:01:18.141697700Z
            UPDATE AD_Element_Trl SET Description='Gibt an, wohin die Dateien nach einem fehlerhaften Verarbeitungsversuch verschoben werden sollen.', Name='Fehlerhaftes Verzeichnis', PrintName='Fehlerhaftes Verzeichnis',Updated=TO_TIMESTAMP('2022-10-18 19:01:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581584 AND AD_Language='de_DE'
            ;

            -- 2022-10-18T16:01:18.143733200Z
            /* DDL */  PERFORM update_ad_element_on_ad_element_trl_update(581584,'de_DE')
            ;

            -- 2022-10-18T16:01:18.150549100Z
            /* DDL */  PERFORM update_TRL_Tables_On_AD_Element_TRL_Update(581584,'de_DE')
            ;

        END IF;
    END
$$;