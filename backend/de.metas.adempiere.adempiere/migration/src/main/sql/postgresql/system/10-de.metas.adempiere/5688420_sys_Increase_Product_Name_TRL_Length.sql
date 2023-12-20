--duplicates 5633190_sys_Increase_Product_Name_TRL_Length.sql
--while reversing the effects of 5638600_sys_Increase_Product_Name_TRL_Length_510.sql
-- so that m_product and m_product_trl have the same length

DO
$$
    BEGIN
        IF (SELECT fieldlength < 600 FROM metasfresh.public.ad_column WHERE AD_Column_ID = 3330) THEN
            -- 2023-05-18T12:12:12.081Z
            -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
            -- M_Product_Trl.Name
            UPDATE AD_Column SET FieldLength=600, Updated=TO_TIMESTAMP('2023-05-18 12:12:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID = 3330;

            -- 2022-04-04T12:33:03.192Z
            -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
            INSERT INTO t_alter_column VALUES ('m_product_trl', 'Name', 'VARCHAR(600)', NULL, NULL);

        END IF;

        IF (SELECT fieldlength < 600 FROM metasfresh.public.ad_column WHERE AD_Column_ID = 1410) THEN
            -- 2023-05-18T12:12:12.081Z
            -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
            -- M_Product.Name
            UPDATE AD_Column SET FieldLength=600, Updated=TO_TIMESTAMP('2023-05-18 12:12:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID = 1410;

            -- 2022-04-04T12:33:03.192Z	-- 2022-04-04T12:33:03.192Z
            -- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
            INSERT INTO t_alter_column VALUES ('m_product', 'Name', 'VARCHAR(600)', NULL, NULL);
        END IF;
    END
$$