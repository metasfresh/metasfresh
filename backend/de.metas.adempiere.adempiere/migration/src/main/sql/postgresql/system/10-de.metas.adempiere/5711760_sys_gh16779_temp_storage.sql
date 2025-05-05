/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2023 metas GmbH
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

-- 2023-11-27T10:35:29.117Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541658,'C',TO_TIMESTAMP('2023-11-27 12:35:28','YYYY-MM-DD HH24:MI:SS'),100,'If true, it enables the "Add to Funnel"/"Add to temporary storage" feature of the GetQuantityDialog.jsx component.','EE01','Y','mobileui.frontend.qtyInput.allowTempQtyStorage',TO_TIMESTAMP('2023-11-27 12:35:28','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

