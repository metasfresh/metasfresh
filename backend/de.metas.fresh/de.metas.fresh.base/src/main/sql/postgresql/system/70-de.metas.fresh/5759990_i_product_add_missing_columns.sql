-- Add missing columns because of commenting it out in 5619530_sys_gh12149_Extend_I_product.sql and 5618380_sys_gh12149_Extend_I_Product_table.sql

DO
$$
    BEGIN

        -- 2021-12-20T12:21:59.244801500Z
        -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
        INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, EntityType, IsActive, Name, PrintName, Updated, UpdatedBy)
        VALUES (0, 580408, 0, 'Weight_UOM_ID', TO_TIMESTAMP('2021-12-20 14:21:59', 'YYYY-MM-DD HH24:MI:SS'), 100, 'U', 'Y', 'Darreichungsform-Einheit ', 'Darreichungsform-Einheit ', TO_TIMESTAMP('2021-12-20 14:21:59', 'YYYY-MM-DD HH24:MI:SS'), 100);

        -- 2021-12-20T12:21:59.246360800Z
        -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
        INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
        SELECT l.AD_Language,
               t.AD_Element_ID,
               t.CommitWarning,
               t.Description,
               t.Help,
               t.Name,
               t.PO_Description,
               t.PO_Help,
               t.PO_Name,
               t.PO_PrintName,
               t.PrintName,
               t.WEBUI_NameBrowse,
               t.WEBUI_NameNew,
               t.WEBUI_NameNewBreadcrumb,
               'N',
               t.AD_Client_ID,
               t.AD_Org_ID,
               t.Created,
               t.Createdby,
               t.Updated,
               t.UpdatedBy,
               'Y'
        FROM AD_Language l,
             AD_Element t
        WHERE l.IsActive = 'Y'
          AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
          AND t.AD_Element_ID = 580408
          AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID);

        -- 2021-12-20T12:23:14.654222Z
        -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
        UPDATE AD_Element SET Name='Bruttogewicht-Maßeinheit ', PrintName='Bruttogewicht-Maßeinheit ', Updated=TO_TIMESTAMP('2021-12-20 14:23:14', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID = 580408;

        -- 2021-12-20T12:23:21.361375600Z
        -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
        UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Bruttogewicht-Maßeinheit ', PrintName='Bruttogewicht-Maßeinheit ', Updated=TO_TIMESTAMP('2021-12-20 14:23:21', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID = 580408 AND AD_Language = 'de_CH';

        -- 2021-12-20T12:23:21.362414100Z
        -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
        /* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(580408, 'de_CH');

        -- 2021-12-20T12:23:28.909672800Z
        -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
        UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Bruttogewicht-Maßeinheit', PrintName='Bruttogewicht-Maßeinheit', Updated=TO_TIMESTAMP('2021-12-20 14:23:28', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID = 580408 AND AD_Language = 'de_DE';

        -- 2021-12-20T12:23:28.919567Z
        -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
        UPDATE AD_Column SET ColumnName='Weight_UOM_ID', Name='Bruttogewicht-Maßeinheit', Description=NULL, Help=NULL WHERE AD_Element_ID = 580408;

        -- 2021-12-20T12:23:28.910712300Z
        -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
        /* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(580408, 'de_DE');


        -- 2021-12-20T12:31:45.267341100Z
        -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
        INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary, IsEncrypted,
                               IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name, SelectionColumnSeqNo,
                               SeqNo, Updated, UpdatedBy, Version)
        VALUES (0, 578975, 580408, 0, 18, 114, 532, 'Weight_UOM_ID', TO_TIMESTAMP('2021-12-20 14:31:45', 'YYYY-MM-DD HH24:MI:SS'), 100, 'N', 'U', 0, 10, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Bruttogewicht-Maßeinheit', 0, 0, TO_TIMESTAMP('2021-12-20 14:31:45', 'YYYY-MM-DD HH24:MI:SS'), 100, 0);

        -- 2021-12-20T12:31:45.268378600Z
        -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
        INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
        SELECT l.AD_Language,
               t.AD_Column_ID,
               t.Name,
               'N',
               t.AD_Client_ID,
               t.AD_Org_ID,
               t.Created,
               t.Createdby,
               t.Updated,
               t.UpdatedBy,
               'Y'
        FROM AD_Language l,
             AD_Column t
        WHERE l.IsActive = 'Y'
          AND (l.IsSystemLanguage = 'Y')
          AND t.AD_Column_ID = 578975
          AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

        -- 2021-12-20T12:31:45.270449300Z
        -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
        /* DDL */
        PERFORM update_Column_Translation_From_AD_Element(580408);

        -- 2021-12-20T12:31:49.111393500Z
        -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
        UPDATE AD_Column SET EntityType='D', Updated=TO_TIMESTAMP('2021-12-20 14:31:49', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID = 578975;

        -- 2021-12-20T12:31:50.126558300Z
        -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
        /* DDL */ PERFORM public.db_alter_table('I_Product', 'ALTER TABLE public.I_Product ADD COLUMN Weight_UOM_ID NUMERIC(10)');

        -- 2021-12-20T12:31:50.133367400Z
        -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
        ALTER TABLE I_Product
            ADD CONSTRAINT WeightUOM_IProduct FOREIGN KEY (Weight_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED;

    EXCEPTION
        WHEN unique_violation THEN
            RAISE NOTICE 'I_Product column Weight_UOM_ID already exist';
    END
$$
;

DO
$$
    BEGIN

        -- 2021-12-10T17:42:44.954486800Z
        -- URL zum Konzept
        INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, ColumnName, Created, CreatedBy, DDL_NoForeignKey, DefaultValue, Description, EntityType, FacetFilterSeqNo, FieldLength, Help, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension, IsDLMPartitionBoundary,
                               IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsSelectionColumn, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated, IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name,
                               SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
        VALUES (0, 578919, 403, 0, 20, 532, 'IsPurchased', TO_TIMESTAMP('2021-12-10 19:42:44', 'YYYY-MM-DD HH24:MI:SS'), 100, 'N', 'N', '', 'D', 0, 1, '', 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Wird Eingekauft', 0, 0, TO_TIMESTAMP('2021-12-10 19:42:44', 'YYYY-MM-DD HH24:MI:SS'), 100, 0);

        -- 2021-12-10T17:42:44.986843Z
        -- URL zum Konzept
        INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
        SELECT l.AD_Language,
               t.AD_Column_ID,
               t.Name,
               'N',
               t.AD_Client_ID,
               t.AD_Org_ID,
               t.Created,
               t.Createdby,
               t.Updated,
               t.UpdatedBy,
               'Y'
        FROM AD_Language l,
             AD_Column t
        WHERE l.IsActive = 'Y'
          AND (l.IsSystemLanguage = 'Y')
          AND t.AD_Column_ID = 578919
          AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID);

        -- 2021-12-10T17:42:45.051893700Z
        -- URL zum Konzept
        /* DDL */
        PERFORM update_Column_Translation_From_AD_Element(403);

        -- 2021-12-10T17:42:49.987446400Z
        -- URL zum Konzept
        /* DDL */ SELECT public.db_alter_table('I_Product', 'ALTER TABLE public.I_Product ADD COLUMN IsPurchased CHAR(1) DEFAULT ''N'' CHECK (IsPurchased IN (''Y'',''N'')) NOT NULL');

    EXCEPTION
        WHEN unique_violation THEN
            RAISE NOTICE 'I_Product column IsPurchased already exist';
    END
$$
;