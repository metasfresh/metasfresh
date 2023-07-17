
/*
 * #%L
 * de.metas.externalsystem
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

-- Column: ExternalSystem_Config_Ebay.RefreshToken
-- 2022-07-15T07:19:58.801Z
UPDATE AD_Column SET DefaultValue='NOT-YET-SET', IsMandatory='Y',Updated=TO_TIMESTAMP('2022-07-15 09:19:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582767
;

-- 2022-07-15T07:19:59.778Z
INSERT INTO t_alter_column values('externalsystem_config_ebay','RefreshToken','VARCHAR(1000)',null,'NOT-YET-SET')
;

-- 2022-07-15T07:19:59.787Z
UPDATE ExternalSystem_Config_Ebay SET RefreshToken='NOT-YET-SET' WHERE RefreshToken IS NULL
;

-- 2022-07-15T07:19:59.790Z
INSERT INTO t_alter_column values('externalsystem_config_ebay','RefreshToken',null,'NOT NULL',null)
;

-- Column: ExternalSystem_Config_Ebay.RefreshToken
-- 2022-07-15T07:20:33.396Z
UPDATE AD_Column SET DefaultValue='NOT YET SET',Updated=TO_TIMESTAMP('2022-07-15 09:20:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582767
;

-- Column: ExternalSystem_Config_Ebay.RefreshToken
-- 2022-07-15T07:20:51.159Z
UPDATE AD_Column SET DefaultValue='NOT_YET_SET',Updated=TO_TIMESTAMP('2022-07-15 09:20:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582767
;

-- 2022-07-15T07:20:52.494Z
INSERT INTO t_alter_column values('externalsystem_config_ebay','RefreshToken','VARCHAR(1000)',null,'NOT_YET_SET')
;

-- 2022-07-15T07:20:52.500Z
UPDATE ExternalSystem_Config_Ebay SET RefreshToken='NOT_YET_SET' WHERE RefreshToken IS NULL
;
