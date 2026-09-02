/*
 * #%L
 * de.metas.edi
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

-- Column: EDI_Desadv.EDI_ExportStatus
-- 2026-02-04T08:03:19.115Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2026-02-04 08:03:19.115000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=551732
;

-- 2026-02-04T08:03:32.568Z
INSERT INTO t_alter_column values('edi_desadv','EDI_ExportStatus','CHAR(1)',null,'P')
;

-- 2026-02-04T08:03:32.889Z
UPDATE EDI_Desadv SET EDI_ExportStatus='P' WHERE EDI_ExportStatus IS NULL
;

-- 2026-02-04T08:03:32.895Z
INSERT INTO t_alter_column values('edi_desadv','EDI_ExportStatus',null,'NOT NULL',null)
;

