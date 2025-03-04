/*
 * #%L
 * de.metas.edi
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

-- 2024-06-17T08:47:52.167Z
INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel, Created, CreatedBy, Description, EntityType, IsActive, Name, Updated, UpdatedBy, Value)
VALUES (0, 0, 541723, 'S', TO_TIMESTAMP('2024-06-17 08:47:52.056000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'If enabled, the EDI document will be computed for each shipment.', 'de.metas.esb.edi', 'Y', 'de.metas.edi.OneDesadvPerShipment',
        TO_TIMESTAMP('2024-06-17 08:47:52.056000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'N')
;

-- 2024-06-20T10:00:01.933Z
UPDATE AD_SysConfig
SET Description='If enabled, the EDI document will be computed for each shipment. Note: when the sys config is enabled, the ''EXP_M_InOut_Desadv_V'' EXP_Format must be manually activated and the default ''EDI_Exp_Desadv'' inactivated.',
    Updated=TO_TIMESTAMP('2024-06-20 10:00:01.933000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy=100
WHERE AD_SysConfig_ID = 541723
;