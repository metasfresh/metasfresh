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

-- SysConfig Name: de.metas.handlingunits.inout.SplitShipmentPackingMaterialLinesByProject
-- SysConfig Value: N
-- When set to Y, shipment packing-material lines are split per C_Project_ID (Positions Nr.), so each packing line and its invoice candidate carry the project.
-- 2026-09-28T00:00:00.000Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541858 /*From ID Server*/,'S',TO_TIMESTAMP('2026-09-28 00:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',0,'When set to Y, shipment packing-material lines are split per C_Project_ID (Positions Nr.), so each packing line and its invoice candidate carry the project.','de.metas.handlingunits','Y','de.metas.handlingunits.inout.SplitShipmentPackingMaterialLinesByProject',TO_TIMESTAMP('2026-09-28 00:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',0,'N')
;
