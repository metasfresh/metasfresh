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

-- Run mode: WEBUI

-- 2026-06-30T08:32:31.400Z
-- old: M_HU_PI_Attribute_ID=540006, PropagationType='BOTU', AD_Org_ID=1000000, AD_Client_ID=1000000
-- new: M_HU_PI_Attribute_ID=540006, PropagationType='TOPD', AD_Org_ID=0, AD_Client_ID=0
UPDATE M_HU_PI_Attribute SET PropagationType='TOPD',AD_Org_ID=0,AD_Client_ID=0,Updated=TO_TIMESTAMP('2026-06-30 08:32:31.400000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE M_HU_PI_Attribute_ID=540006
;
