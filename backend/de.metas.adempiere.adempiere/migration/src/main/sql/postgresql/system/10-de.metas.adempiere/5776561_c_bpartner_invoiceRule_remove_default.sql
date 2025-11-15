/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
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

-- Run mode: SWING_CLIENT

-- Column: C_BPartner.InvoiceRule
-- 2025-11-12T18:23:25.851Z
UPDATE AD_Column SET DefaultValue='',Updated=TO_TIMESTAMP('2025-11-12 18:23:25.851000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=4429
;

-- 2025-11-15T14:22:09.696Z
INSERT INTO t_alter_column values('c_bpartner','InvoiceRule','CHAR(1)',null,null)
;

alter table c_bpartner alter column InvoiceRule drop default
;
