/*
 * #%L
 * de.metas.document.archive.base
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

-- Column: AD_Archive.POReference
-- Column: AD_Archive.POReference
-- 2024-09-06T15:36:48.527Z
UPDATE AD_Column SET AD_Reference_ID=14, FieldLength=1024,Updated=TO_TIMESTAMP('2024-09-06 15:36:48.527000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=588944
;

-- 2024-09-06T15:36:56.077Z
INSERT INTO t_alter_column values('ad_archive','POReference','VARCHAR(1024)',null,null)
;
