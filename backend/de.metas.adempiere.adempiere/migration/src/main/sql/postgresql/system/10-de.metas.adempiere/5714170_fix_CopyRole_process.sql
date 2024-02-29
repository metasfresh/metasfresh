/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
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

-- remove duplicate parameter

-- Run mode: SWING_CLIENT

-- Process: CopyRole(org.compiere.process.CopyRole)
-- ParameterName: AD_Role_ID
-- 2023-12-20T19:24:36.109Z
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=50001
;

-- 2023-12-20T19:24:36.114Z
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=50001
;

