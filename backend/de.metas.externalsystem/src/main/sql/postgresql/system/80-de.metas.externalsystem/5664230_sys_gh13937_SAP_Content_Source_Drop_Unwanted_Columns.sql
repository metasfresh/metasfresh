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

-- 2022-11-16T11:50:21.927Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE ExternalSystem_Config_SAP DROP COLUMN IF EXISTS SFTP_BPartner_FileName_Pattern')
;

-- 2022-11-16T11:50:43.844Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE ExternalSystem_Config_SAP DROP COLUMN IF EXISTS SFTP_BPartner_TargetDirectory')
;

-- 2022-11-16T11:50:51.637Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE ExternalSystem_Config_SAP DROP COLUMN IF EXISTS SFTP_Product_FileName_Pattern')
;

-- 2022-11-16T11:50:51.637Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE ExternalSystem_Config_SAP DROP COLUMN IF EXISTS SFTP_Product_TargetDirectory')
;

-- 2022-11-16T18:20:51.303Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE ExternalSystem_Config_SAP DROP COLUMN IF EXISTS ExternalSystem_Config_SAP_SFTP_ID')
;

-- 2022-11-16T18:20:26.594Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP','ALTER TABLE ExternalSystem_Config_SAP DROP COLUMN IF EXISTS ExternalSystem_Config_SAP_LocalFile_ID')
;

-- 2022-11-17T11:48:49.823Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE ExternalSystem_Config_SAP_LocalFile DROP COLUMN IF EXISTS SFTP_HostName')
;

-- 2022-11-17T11:48:40.570Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE ExternalSystem_Config_SAP_LocalFile DROP COLUMN IF EXISTS SFTP_Password')
;

-- 2022-11-17T11:48:31.304Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE ExternalSystem_Config_SAP_LocalFile DROP COLUMN IF EXISTS SFTP_Port')
;

-- 2022-11-17T11:48:07.379Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_SAP_LocalFile','ALTER TABLE ExternalSystem_Config_SAP_LocalFile DROP COLUMN IF EXISTS SFTP_Username')
;