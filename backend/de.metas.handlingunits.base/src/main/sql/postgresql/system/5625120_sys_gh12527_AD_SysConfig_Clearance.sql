/*
 * #%L
 * de.metas.handlingunits.base
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

-- 2022-02-09T12:33:03.207Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541449,'S',TO_TIMESTAMP('2022-02-09 14:33:03','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','de.metas.ui.web.handlingunits.field.ClearanceStatus.IsDisplayed',TO_TIMESTAMP('2022-02-09 14:33:03','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

-- 2022-02-09T12:33:08.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='S', EntityType='de.metas.handlingunits',Updated=TO_TIMESTAMP('2022-02-09 14:33:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541449
;

