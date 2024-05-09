/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2024 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

DROP FUNCTION IF EXISTS Update_DE_Element_Names()
;

CREATE OR REPLACE FUNCTION Update_DE_Element_Names()
    RETURNS VOID
AS
$$
DECLARE
    element_record RECORD;
BEGIN
    FOR element_record IN
        SELECT ad_element_id, de_name
        FROM migration_data.DE_AD_Element_Trl
        LOOP
            -- de_DE Translations
            UPDATE AD_Element_Trl
            SET IsTranslated='Y',
                Name=element_record.de_name,
                PrintName=element_record.de_name,
                Updated=NOW(),
                UpdatedBy=100
            WHERE AD_Element_ID = element_record.ad_element_id
              AND AD_Language = 'de_DE';

            UPDATE AD_Element
            SET Name=element_record.de_name,
                PrintName=element_record.de_name,
                Updated=NOW()
            WHERE AD_Element_ID = element_record.ad_element_id;

            PERFORM update_ad_element_on_ad_element_trl_update(element_record.ad_element_id, 'de_DE');

            PERFORM update_TRL_Tables_On_AD_Element_TRL_Update(element_record.ad_element_id, 'de_DE');

            -- de_CH Translations
            UPDATE AD_Element_Trl
            SET IsTranslated='Y',
                Name=element_record.de_name,
                PrintName=element_record.de_name,
                Updated=NOW(),
                UpdatedBy=100
            WHERE AD_Element_ID = element_record.ad_element_id
              AND AD_Language = 'de_CH';

            UPDATE AD_Element
            SET Name=element_record.de_name,
                PrintName=element_record.de_name,
                Updated=NOW()
            WHERE AD_Element_ID = element_record.ad_element_id;

            PERFORM update_ad_element_on_ad_element_trl_update(element_record.ad_element_id, 'de_CH');

            PERFORM update_TRL_Tables_On_AD_Element_TRL_Update(element_record.ad_element_id, 'de_CH');

            RAISE NOTICE 'Updated element_id % to name %', element_record.ad_element_id, element_record.de_name;

        END LOOP;
END;
$$
    LANGUAGE plpgsql
;

