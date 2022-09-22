/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

-- Column: AD_Element_Trl.Name
-- 2022-09-22T08:33:48.367Z
UPDATE AD_Column SET FieldLength=120,Updated=TO_TIMESTAMP('2022-09-22 09:33:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2646
;

-- 2022-09-22T08:33:57.134Z
INSERT INTO t_alter_column values('ad_element_trl','Name','VARCHAR(120)',null,null)
;

-- Column: AD_Element_Trl.PrintName
-- 2022-09-22T08:34:08.626Z
UPDATE AD_Column SET FieldLength=120,Updated=TO_TIMESTAMP('2022-09-22 09:34:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=4300
;

-- 2022-09-22T08:34:10.574Z
INSERT INTO t_alter_column values('ad_element_trl','PrintName','VARCHAR(120)',null,null)
;

-- Column: AD_Element_Trl.PO_Name
-- 2022-09-22T08:34:40.234Z
UPDATE AD_Column SET FieldLength=120,Updated=TO_TIMESTAMP('2022-09-22 09:34:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6450
;

-- Column: AD_Element_Trl.PO_PrintName
-- 2022-09-22T08:34:50.932Z
UPDATE AD_Column SET FieldLength=120,Updated=TO_TIMESTAMP('2022-09-22 09:34:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=6451
;

-- 2022-09-22T08:34:53.009Z
INSERT INTO t_alter_column values('ad_element_trl','PO_PrintName','VARCHAR(120)',null,null)
;
