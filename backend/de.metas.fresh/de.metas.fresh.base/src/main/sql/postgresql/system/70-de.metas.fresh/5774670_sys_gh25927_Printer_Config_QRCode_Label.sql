/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

DROP FUNCTION IF EXISTS report.Printer_Config_QRCode_Label(p_ad_printer_config_ids TEXT,
                                                           p_view_id               TEXT,
                                                           p_record_id             numeric)
;


CREATE OR REPLACE FUNCTION report.Printer_Config_QRCode_Label(
    p_ad_printer_config_ids TEXT,
    p_view_id               TEXT,
    p_record_id             numeric
)
    RETURNS TABLE
            (
                ad_printer_config_id numeric,
                name                 VARCHAR,
                qrcode_data          TEXT
            )
    LANGUAGE plpgsql
AS
$$
BEGIN
    RETURN QUERY
        SELECT pc.AD_Printer_Config_ID,
               pc.Name,
               'PRINTER_CFG#' || pc.AD_Printer_Config_ID || '#' ||
               JSON_BUILD_OBJECT(
                       'printerConfigId', pc.AD_Printer_Config_ID,
                       'caption', pc.Name
               )::text AS QRCodeData
        FROM AD_Printer_Config pc
        WHERE (p_ad_printer_config_ids IS NOT NULL
            AND (
                   (p_ad_printer_config_ids = 'all' AND pc.AD_Printer_Config_ID IN (SELECT vs.IntKey1
                                                                                    FROM T_WEBUI_ViewSelection vs
                                                                                    WHERE vs.UUID = p_view_id))
                       OR (p_ad_printer_config_ids <> 'all' AND
                           pc.AD_Printer_Config_ID = ANY (
                               REGEXP_SPLIT_TO_ARRAY(p_ad_printer_config_ids, ',')::INT[]
                               ))
                   )
            )

           OR (p_ad_printer_config_ids IS NULL
            AND pc.AD_Printer_Config_ID = p_record_id
            )
        ORDER BY pc.AD_Printer_Config_ID;
END;
$$
;
