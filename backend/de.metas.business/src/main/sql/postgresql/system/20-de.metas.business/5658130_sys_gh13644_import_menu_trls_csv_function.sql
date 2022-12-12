DO
$$
    BEGIN
        UPDATE ad_element_trl
        SET name                    = data.name_trl
          , printname               = data.name_trl
          , description             = data.description_trl
          , webui_namenew           = data.webui_namenew_trl
          , webui_namebrowse        = data.webui_namebrowse_trl
          , webui_namenewbreadcrumb = data.webui_namenewbreadcrumb_trl
        FROM migration_data."AD_Menu_Trad" data
                 INNER JOIN ad_menu m ON data.ad_menu_id = m.ad_menu_id
        WHERE ad_element_trl.ad_element_id = m.ad_element_id
          AND ad_language IN ('fr_CH', 'fr_FR');


        update ad_element_trl set name = 'Partenaires commerciaux Dist-Orgs' where ad_element_id = 574339 and ad_language = 'fr_CH'
        ;

        /* DDL */  perform update_TRL_Tables_On_AD_Element_TRL_Update(574339,'fr_CH')
        ;

        update ad_element_trl set name = 'Planification des livraisons - Révision des exportations' where ad_element_id = 577793 and ad_language = 'fr_CH'
        ;


        /* DDL */  perform update_TRL_Tables_On_AD_Element_TRL_Update(577793,'fr_CH')
        ;

        UPDATE AD_Element_Trl SET name='Retrait de matériel - LEGACY',Updated=TO_TIMESTAMP('2022-09-30 10:13:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574498 AND AD_Language='fr_CH'
        ;

        /* DDL */  perform update_TRL_Tables_On_AD_Element_TRL_Update(574498,'fr_CH')
        ;

        UPDATE AD_Element_Trl SET name='Projet - LEGACY',Updated=TO_TIMESTAMP('2022-09-30 10:13:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573947 AND AD_Language='fr_CH'
        ;

        /* DDL */  perform update_TRL_Tables_On_AD_Element_TRL_Update(573947,'fr_CH')
        ;

        UPDATE AD_Element_Trl SET name='Packvorschrift Nachweis',Updated=TO_TIMESTAMP('2022-09-30 10:13:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573713 AND AD_Language='fr_CH'
        ;

        /* DDL */  perform update_TRL_Tables_On_AD_Element_TRL_Update(573713,'fr_CH')
        ;

        UPDATE AD_Element_Trl SET name='Configuration de la liste des unités Version',Updated=TO_TIMESTAMP('2022-09-30 10:13:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574286 AND AD_Language='fr_CH'
        ;

        /* DDL */  perform update_TRL_Tables_On_AD_Element_TRL_Update(574286,'fr_CH')
        ;

        UPDATE AD_Element_Trl SET name='Paquet - LEGACY',Updated=TO_TIMESTAMP('2022-09-30 10:13:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574167 AND AD_Language='fr_CH'
        ;

        /* DDL */  perform update_TRL_Tables_On_AD_Element_TRL_Update(574167,'fr_CH')
        ;

        UPDATE AD_Element_Trl SET name='Verpackung',Updated=TO_TIMESTAMP('2022-09-30 10:13:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574257 AND AD_Language='fr_CH'
        ;

        /* DDL */  perform update_TRL_Tables_On_AD_Element_TRL_Update(574257,'fr_CH')
        ;
        UPDATE AD_Element_Trl SET name='Mahndisposition',Updated=TO_TIMESTAMP('2022-09-30 10:13:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574026 AND AD_Language='fr_CH'
        ;
        /* DDL */  perform update_TRL_Tables_On_AD_Element_TRL_Update(574026,'fr_CH')
        ;

        UPDATE AD_Element_Trl SET name='Bestelldisposition Neu',Updated=TO_TIMESTAMP('2022-09-30 10:13:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574315 AND AD_Language='fr_CH'
        ;

        /* DDL */  perform update_TRL_Tables_On_AD_Element_TRL_Update(574315,'fr_CH')
        ;


        UPDATE AD_Element_Trl SET name='Vertragslaufzeit',Updated=TO_TIMESTAMP('2022-09-30 10:13:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574261 AND AD_Language='fr_CH'
        ;

        /* DDL */  perform update_TRL_Tables_On_AD_Element_TRL_Update(574261,'fr_CH')
        ;

        UPDATE AD_Element_Trl SET name='Provisionsart',Updated=TO_TIMESTAMP('2022-09-30 10:13:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=574241 AND AD_Language='fr_CH'
        ;

        /* DDL */  perform update_TRL_Tables_On_AD_Element_TRL_Update(574241,'fr_CH')
        ;

        PERFORM update_trl_tables_on_ad_element_trl_update(NULL, NULL);
    END;
$$
;
