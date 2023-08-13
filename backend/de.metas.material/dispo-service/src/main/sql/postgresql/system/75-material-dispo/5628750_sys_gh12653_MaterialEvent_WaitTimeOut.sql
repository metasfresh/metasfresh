/*
 * #%L
 * metasfresh-material-dispo-service
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

-- 2022-03-03T19:11:18.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541450,'S',TO_TIMESTAMP('2022-03-03 21:11:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','de.metas.material.event.MaterialEventObserver.WaitTimeOutMS',TO_TIMESTAMP('2022-03-03 21:11:17','YYYY-MM-DD HH24:MI:SS'),100,'300000')
;

-- 2022-03-03T19:12:31.428Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='S', Description='Timeout to wait Material Events to be processed',Updated=TO_TIMESTAMP('2022-03-03 21:12:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541450
;
