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

-- Column: S_ExternalProjectReference.ExternalSystem_ID
-- 2025-09-18T07:57:36.922Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2025-09-18 07:57:36.922000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590938
;

-- 2025-09-18T07:57:38.716Z
INSERT INTO t_alter_column values('s_externalprojectreference','ExternalSystem_ID','NUMERIC(10)',null,null)
;

-- 2025-09-18T07:57:38.717Z
INSERT INTO t_alter_column values('s_externalprojectreference','ExternalSystem_ID',null,'NOT NULL',null)
;
