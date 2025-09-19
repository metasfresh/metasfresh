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

SELECT backup_table('ExternalSystem_Service', '_ref_list_drop')
;

UPDATE ExternalSystem_Service ess
SET externalsystem_id = e.externalsystem_id
FROM ExternalSystem_Service ess_source
         JOIN externalsystem e ON ((e.value = 'ALBERTA' AND ess_source.type = 'A')
    OR (e.value = 'GRSSignum' AND ess_source.type = 'GRS')
    OR (e.value = 'LeichUndMehl' AND ess_source.type = 'LM')
    OR (e.value = 'Other' AND ess_source.type = 'Other')
    OR (e.value = 'PrintingClient' AND ess_source.type = 'PC')
    OR (e.value = 'ProCareManagement' AND ess_source.type = 'PCM')
    OR (e.value = 'RabbitMQRESTAPI' AND ess_source.type = 'RabbitMQ')
    OR (e.value = 'Shopware6' AND ess_source.type = 'S6')
    OR (e.value = 'WOOCommerce' AND ess_source.type = 'WOO'))
WHERE ess.ExternalSystem_Service_id = ess_source.ExternalSystem_Service_id
;
