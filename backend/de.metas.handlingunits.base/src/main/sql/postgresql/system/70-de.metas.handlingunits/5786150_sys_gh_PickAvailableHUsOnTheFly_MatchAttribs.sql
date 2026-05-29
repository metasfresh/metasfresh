/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

-- SysConfig Name: de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PickAvailableHUsOnTheFly.MatchAttributes
-- SysConfig Value: N
-- 2026-01-30T22:00:31.244Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541793,'S',TO_TIMESTAMP('2026-01-30 22:00:31.241000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PickAvailableHUsOnTheFly.MatchAttributes',TO_TIMESTAMP('2026-01-30 22:00:31.241000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N')
;

-- SysConfig Name: de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PickAvailableHUsOnTheFly.MatchAttributes
-- SysConfig Value: N
-- 2026-01-30T22:00:47.750Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', EntityType='D',Updated=TO_TIMESTAMP('2026-01-30 22:00:47.750000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_SysConfig_ID=541793
;

-- SysConfig Name: de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PickAvailableHUsOnTheFly.MatchAttributes
-- SysConfig Value: N
-- 2026-01-30T22:00:50.650Z
UPDATE AD_SysConfig SET ConfigurationLevel='O',Updated=TO_TIMESTAMP('2026-01-30 22:00:50.650000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_SysConfig_ID=541793
;

-- SysConfig Name: de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PickAvailableHUsOnTheFly.MatchAttributes
-- SysConfig Value: N
-- 2026-01-30T22:00:56.241Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='If',Updated=TO_TIMESTAMP('2026-01-30 22:00:56.241000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_SysConfig_ID=541793
;

-- SysConfig Name: de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PickAvailableHUsOnTheFly.MatchAttributes
-- SysConfig Value: N
-- 2026-01-30T22:02:09.375Z
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='If de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PickAvailableHUsOnTheFly is set to Y, then this property decides whether only HUs whose attributes match the respective M_ShipmentSchedule''s ASI are picked on the fly.',Updated=TO_TIMESTAMP('2026-01-30 22:02:09.374000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_SysConfig_ID=541793
;
