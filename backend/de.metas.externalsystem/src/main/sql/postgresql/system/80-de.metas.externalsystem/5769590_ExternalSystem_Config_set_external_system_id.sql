/*
 * #%L
 * de.metas.externalsystem
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

SELECT backup_table('ExternalSystem_Config', '_ref_list_drop')
;

UPDATE ExternalSystem_Config esc
SET externalsystem_id = e.externalsystem_id
FROM ExternalSystem_Config esc_source
         JOIN externalsystem e ON ((e.value = 'ALBERTA' AND esc_source.type = 'A')
    OR (e.value = 'GRSSignum' AND esc_source.type = 'GRS')
    OR (e.value = 'LeichUndMehl' AND esc_source.type = 'LM')
    OR (e.value = 'Other' AND esc_source.type = 'Other')
    OR (e.value = 'PrintingClient' AND esc_source.type = 'PC')
    OR (e.value = 'ProCareManagement' AND esc_source.type = 'PCM')
    OR (e.value = 'RabbitMQRESTAPI' AND esc_source.type = 'RabbitMQ')
    OR (e.value = 'Shopware6' AND esc_source.type = 'S6')
    OR (e.value = 'WooCommerce' AND esc_source.type = 'WOO'))
WHERE esc.ExternalSystem_Config_id = esc_source.ExternalSystem_Config_id
;

UPDATE externalsystem_config_alberta
SET externalsystemvalue = 'ALBERTA'
WHERE externalsystemvalue = 'Alberta'
;
