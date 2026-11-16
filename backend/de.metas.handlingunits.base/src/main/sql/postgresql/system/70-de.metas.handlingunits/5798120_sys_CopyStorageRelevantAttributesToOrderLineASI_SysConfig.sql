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

-- SysConfig Name: de.metas.handlingunits.order.CopyStorageRelevantAttributesToOrderLineASI
-- SysConfig Value: N
-- When set to Y, common storage-relevant HU attributes of all reserved VHUs are extracted and copied to the order line ASI.
-- 2026-04-15T00:00:00.000Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541803 /*From ID Server*/,'S',TO_TIMESTAMP('2026-04-15 00:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',0,'If Y, the common storage-relevant attributes of all reserved VHUs are extracted and copied to the order line ASI. Otherwise, only the common project ID is copied.','de.metas.handlingunits','Y','de.metas.handlingunits.order.CopyStorageRelevantAttributesToOrderLineASI',TO_TIMESTAMP('2026-04-15 00:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',0,'N')
;
