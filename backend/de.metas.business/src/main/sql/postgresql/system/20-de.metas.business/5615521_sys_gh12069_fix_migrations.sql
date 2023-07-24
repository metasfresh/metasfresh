DO
$$
    BEGIN

    IF EXISTS(select 1 from ad_column where ad_column_id = 578725 and columnname ilike 'Content') THEN
        RETURN;
    END IF;

    -- 2021-11-26T09:25:58.222Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,578725,580321,0,14,208,'Content',TO_TIMESTAMP('2021-11-26 10:25:58','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Content',0,0,TO_TIMESTAMP('2021-11-26 10:25:58','YYYY-MM-DD HH24:MI:SS'),100,0)
    ;

    -- 2021-11-26T09:25:58.227Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=578725 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
    ;

    -- 2021-11-26T09:25:58.239Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    /* DDL */  perform update_Column_Translation_From_AD_Element(580321)
    ;

    -- 2021-11-26T09:27:20.280Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    UPDATE AD_Column SET FieldLength=4000,Updated=TO_TIMESTAMP('2021-11-26 10:27:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=578725
    ;

    -- 2021-11-26T09:27:25.758Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    /* DDL */ perform public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN Content TEXT')
    ;

    -- 2021-11-26T09:28:24.645Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580322,0,'ManufacturingMethod',TO_TIMESTAMP('2021-11-26 10:28:24','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Art der Herstellung','Art der Herstellung',TO_TIMESTAMP('2021-11-26 10:28:24','YYYY-MM-DD HH24:MI:SS'),100)
    ;

    -- 2021-11-26T09:28:24.649Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580322 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
    ;

    -- 2021-11-26T09:28:42.828Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    UPDATE AD_Element_Trl SET Name='Manufacturing Method', PrintName='Manufacturing Method',Updated=TO_TIMESTAMP('2021-11-26 10:28:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580322 AND AD_Language='en_US'
    ;

    -- 2021-11-26T09:28:42.833Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    /* DDL */  perform update_TRL_Tables_On_AD_Element_TRL_Update(580322,'en_US')
    ;

    -- 2021-11-26T09:29:47.618Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541515,TO_TIMESTAMP('2021-11-26 10:29:47','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','ManufacturingMethod_Reference',TO_TIMESTAMP('2021-11-26 10:29:47','YYYY-MM-DD HH24:MI:SS'),100,'L')
    ;

    -- 2021-11-26T09:29:47.622Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541515 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
    ;

    -- 2021-11-26T09:30:38.549Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541515,543064,TO_TIMESTAMP('2021-11-26 10:30:38','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Presslingherstellung',TO_TIMESTAMP('2021-11-26 10:30:38','YYYY-MM-DD HH24:MI:SS'),100,'Presslingherstellung','Presslingherstellung')
    ;

    -- 2021-11-26T09:30:38.555Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543064 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
    ;

    -- 2021-11-26T09:31:26.749Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    UPDATE AD_Ref_List SET Value='PR',Updated=TO_TIMESTAMP('2021-11-26 10:31:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543064
    ;

    -- 2021-11-26T09:31:44.382Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,578726,580322,0,17,541515,208,'ManufacturingMethod',TO_TIMESTAMP('2021-11-26 10:31:44','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,2,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Art der Herstellung',0,0,TO_TIMESTAMP('2021-11-26 10:31:44','YYYY-MM-DD HH24:MI:SS'),100,0)
    ;

    -- 2021-11-26T09:31:44.387Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=578726 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
    ;

    -- 2021-11-26T09:31:44.396Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    /* DDL */  perform update_Column_Translation_From_AD_Element(580322)
    ;

    -- 2021-11-26T09:31:53.004Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    /* DDL */ perform public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN ManufacturingMethod VARCHAR(2)')
    ;

    -- 2021-11-26T09:35:46.620Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580323,0,'BioControlNumber',TO_TIMESTAMP('2021-11-26 10:35:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Bio-Kontrollnummer','Bio-Kontrollnummer',TO_TIMESTAMP('2021-11-26 10:35:46','YYYY-MM-DD HH24:MI:SS'),100)
    ;

    -- 2021-11-26T09:35:46.625Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580323 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
    ;

    -- 2021-11-26T09:35:59.201Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    UPDATE AD_Element_Trl SET Name='BioControlNumber', PrintName='BioControlNumber',Updated=TO_TIMESTAMP('2021-11-26 10:35:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580323 AND AD_Language='en_US'
    ;

    -- 2021-11-26T09:35:59.205Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    /* DDL */  perform update_TRL_Tables_On_AD_Element_TRL_Update(580323,'en_US')
    ;

    -- 2021-11-26T09:37:15.736Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541516,TO_TIMESTAMP('2021-11-26 10:37:15','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','BioControlNumber_Reference',TO_TIMESTAMP('2021-11-26 10:37:15','YYYY-MM-DD HH24:MI:SS'),100,'L')
    ;

    -- 2021-11-26T09:37:15.740Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541516 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
    ;

    -- 2021-11-26T09:38:10.600Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541516,543065,TO_TIMESTAMP('2021-11-26 10:38:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Bio Control Number Test',TO_TIMESTAMP('2021-11-26 10:38:10','YYYY-MM-DD HH24:MI:SS'),100,'BCN','Bio Control Number Test')
    ;

    -- 2021-11-26T09:38:10.603Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543065 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
    ;

    -- 2021-11-26T09:38:37.541Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,578727,580323,0,17,541516,208,'BioControlNumber',TO_TIMESTAMP('2021-11-26 10:38:37','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,3,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Bio-Kontrollnummer',0,0,TO_TIMESTAMP('2021-11-26 10:38:37','YYYY-MM-DD HH24:MI:SS'),100,0)
    ;

    -- 2021-11-26T09:38:37.546Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=578727 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
    ;

    -- 2021-11-26T09:38:37.558Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    /* DDL */  perform update_Column_Translation_From_AD_Element(580323)
    ;

    -- 2021-11-26T09:38:40.195Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    /* DDL */ perform public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN BioControlNumber VARCHAR(3)')
    ;

    -- 2021-11-26T09:39:31.964Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580324,0,'PostControl',TO_TIMESTAMP('2021-11-26 10:39:31','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kontrollstelle','Kontrollstelle',TO_TIMESTAMP('2021-11-26 10:39:31','YYYY-MM-DD HH24:MI:SS'),100)
    ;

    -- 2021-11-26T09:39:31.968Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580324 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
    ;

    -- 2021-11-26T09:39:43.607Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    UPDATE AD_Element_Trl SET Name='PostControl', PrintName='PostControl',Updated=TO_TIMESTAMP('2021-11-26 10:39:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580324 AND AD_Language='en_US'
    ;

    -- 2021-11-26T09:39:43.611Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    /* DDL */  perform update_TRL_Tables_On_AD_Element_TRL_Update(580324,'en_US')
    ;

    -- 2021-11-26T09:41:24.681Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541517,TO_TIMESTAMP('2021-11-26 10:41:24','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','PostControl_Reference',TO_TIMESTAMP('2021-11-26 10:41:24','YYYY-MM-DD HH24:MI:SS'),100,'L')
    ;

    -- 2021-11-26T09:41:24.686Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541517 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
    ;

    -- 2021-11-26T09:41:48.933Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541517,543066,TO_TIMESTAMP('2021-11-26 10:41:48','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Post Control Test',TO_TIMESTAMP('2021-11-26 10:41:48','YYYY-MM-DD HH24:MI:SS'),100,'PC','Post Control Test')
    ;

    -- 2021-11-26T09:41:48.936Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543066 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
    ;

    -- 2021-11-26T09:42:03.128Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,578728,580324,0,17,541517,208,'PostControl',TO_TIMESTAMP('2021-11-26 10:42:02','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,2,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Kontrollstelle',0,0,TO_TIMESTAMP('2021-11-26 10:42:02','YYYY-MM-DD HH24:MI:SS'),100,0)
    ;

    -- 2021-11-26T09:42:03.134Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=578728 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
    ;

    -- 2021-11-26T09:42:03.143Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    /* DDL */  perform update_Column_Translation_From_AD_Element(580324)
    ;

    -- 2021-11-26T09:42:08.506Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    /* DDL */ perform public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN PostControl VARCHAR(2)')
    ;

    -- 2021-11-26T09:43:23.818Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580325,0,'ReferenceText',TO_TIMESTAMP('2021-11-26 10:43:23','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Hinweistext','Hinweistext',TO_TIMESTAMP('2021-11-26 10:43:23','YYYY-MM-DD HH24:MI:SS'),100)
    ;

    -- 2021-11-26T09:43:23.823Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580325 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
    ;

    -- 2021-11-26T09:43:59.254Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    UPDATE AD_Element_Trl SET Name='Reference Text', PrintName='Reference Text',Updated=TO_TIMESTAMP('2021-11-26 10:43:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580325 AND AD_Language='en_US'
    ;

    -- 2021-11-26T09:43:59.258Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    /* DDL */  perform update_TRL_Tables_On_AD_Element_TRL_Update(580325,'en_US')
    ;

    -- 2021-11-26T09:47:34.957Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541518,TO_TIMESTAMP('2021-11-26 10:47:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','ReferenceText_Reference',TO_TIMESTAMP('2021-11-26 10:47:34','YYYY-MM-DD HH24:MI:SS'),100,'L')
    ;

    -- 2021-11-26T09:47:34.960Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541518 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
    ;

    -- 2021-11-26T09:48:05.577Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541518,543067,TO_TIMESTAMP('2021-11-26 10:48:03','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Reference Text Test',TO_TIMESTAMP('2021-11-26 10:48:03','YYYY-MM-DD HH24:MI:SS'),100,'RT','Reference Text Test')
    ;

    -- 2021-11-26T09:48:05.580Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543067 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
    ;

    -- 2021-11-26T09:48:13.338Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    UPDATE AD_Ref_List SET EntityType='D',Updated=TO_TIMESTAMP('2021-11-26 10:48:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543067
    ;

    -- 2021-11-26T09:48:28.566Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,578729,580325,0,17,541518,208,'ReferenceText',TO_TIMESTAMP('2021-11-26 10:48:28','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,2,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Hinweistext',0,0,TO_TIMESTAMP('2021-11-26 10:48:28','YYYY-MM-DD HH24:MI:SS'),100,0)
    ;

    -- 2021-11-26T09:48:28.571Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=578729 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
    ;

    -- 2021-11-26T09:48:28.581Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    /* DDL */  perform update_Column_Translation_From_AD_Element(580325)
    ;

    -- 2021-11-26T09:48:30.817Z
    -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
    --/* DDL */ perform public.db_alter_table('M_Product','ALTER TABLE public.M_Product ADD COLUMN ReferenceText VARCHAR(2)')
    --;
    ALTER TABLE public.M_Product ADD COLUMN ReferenceText VARCHAR(2);

    END;
$$
;