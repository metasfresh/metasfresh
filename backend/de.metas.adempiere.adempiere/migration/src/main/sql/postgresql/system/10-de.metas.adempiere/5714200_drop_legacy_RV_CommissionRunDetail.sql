

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

-- Run mode: SWING_CLIENT

-- Name: Kommissions-Lauf - Details
-- Action Type: R
-- Report: RV_CommissionRunDetail
-- 2023-12-20T19:29:56.359Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=513
;

-- 2023-12-20T19:29:56.369Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=513
;

-- 2023-12-20T19:29:56.372Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=513 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Value: RV_CommissionRunDetail
-- 2023-12-20T19:30:01.092Z
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=320
;

-- 2023-12-20T19:30:01.104Z
DELETE FROM AD_Process WHERE AD_Process_ID=320
;

