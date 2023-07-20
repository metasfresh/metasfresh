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

-- 2022-08-18T12:09:25.606Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541471,'S',TO_TIMESTAMP('2022-08-18 15:09:25','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','de.metas.externalsystem.externalservice.authorization.AD_User_ID',TO_TIMESTAMP('2022-08-18 15:09:25','YYYY-MM-DD HH24:MI:SS'),100,'100')
;

-- 2022-08-18T12:09:54.781Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Description='Specifies the userId used by the External Systems when connecting to Metasfresh.',Updated=TO_TIMESTAMP('2022-08-18 15:09:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541471
;

-- 2022-08-18T12:10:25.424Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', EntityType='D',Updated=TO_TIMESTAMP('2022-08-18 15:10:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541471
;

-- 2022-08-18T12:11:04.508Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541472,'S',TO_TIMESTAMP('2022-08-18 15:11:04','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','de.metas.externalsystem.externalservice.authorization.AD_Role_ID',TO_TIMESTAMP('2022-08-18 15:11:04','YYYY-MM-DD HH24:MI:SS'),100,'Webapi')
;

-- 2022-08-18T12:11:29.830Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Description='Specifies which role used by the External Systems when connecting to Metasfresh.',Updated=TO_TIMESTAMP('2022-08-18 15:11:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541472
;

-- 2022-08-18T12:13:11.832Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Description='Specifies which role is used by the External Systems when connecting to Metasfresh.',Updated=TO_TIMESTAMP('2022-08-18 15:13:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541472
;

-- 2022-08-18T12:13:53.249Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', EntityType='D',Updated=TO_TIMESTAMP('2022-08-18 15:13:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541472
;

-- 2022-08-18T13:46:23.590Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Value='540024',Updated=TO_TIMESTAMP('2022-08-18 16:46:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541472
;

-- 2022-08-19T12:16:56.377Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Description='Specifies the AD_User_ID used by the External Systems when connecting to Metasfresh.',Updated=TO_TIMESTAMP('2022-08-19 15:16:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541471
;

-- 2022-08-19T12:17:12.453Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Description='Specifies the AD_Role_ID used by the External Systems when connecting to Metasfresh.',Updated=TO_TIMESTAMP('2022-08-19 15:17:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541472
;


