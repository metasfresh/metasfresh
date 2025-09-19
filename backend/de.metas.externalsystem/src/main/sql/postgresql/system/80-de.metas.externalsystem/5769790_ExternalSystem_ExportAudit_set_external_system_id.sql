/*
 * #%L
 * de.metas.serviceprovider.base
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

SELECT backup_table('externalsystem_exportaudit', '_ref_list_drop')
;

UPDATE metasfresh.public.externalsystem_exportaudit esea
SET externalsystem_id = e.externalsystem_id
FROM externalsystem_exportaudit esea_source
         JOIN externalsystem e ON ((e.value = 'ALBERTA' AND esea_source.externalsystemtype = 'A')
    OR (e.value = 'GRSSignum' AND esea_source.externalsystemtype = 'GRS')
    OR (e.value = 'LeichUndMehl' AND esea_source.externalsystemtype = 'LM')
    OR (e.value = 'Other' AND esea_source.externalsystemtype = 'Other')
    OR (e.value = 'PrintingClient' AND esea_source.externalsystemtype = 'PC')
    OR (e.value = 'ProCareManagement' AND esea_source.externalsystemtype = 'PCM')
    OR (e.value = 'RabbitMQRESTAPI' AND esea_source.externalsystemtype = 'RabbitMQ')
    OR (e.value = 'Shopware6' AND esea_source.externalsystemtype = 'S6')
    OR (e.value = 'WOOCommerce' AND esea_source.externalsystemtype = 'WOO'))
WHERE esea.externalsystem_exportaudit_id = esea_source.externalsystem_exportaudit_id
;
