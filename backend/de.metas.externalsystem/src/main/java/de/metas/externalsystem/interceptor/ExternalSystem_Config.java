/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.externalsystem.interceptor;

import de.metas.externalsystem.ExternalSystemConfigRepo;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.IExternalSystemChildConfig;
import de.metas.externalsystem.model.I_ExternalSystem_Config;
import de.metas.i18n.AdMessageKey;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Interceptor(I_ExternalSystem_Config.class)
@Component
public class ExternalSystem_Config
{
	public final ExternalSystemConfigRepo externalSystemConfigRepo;

	private final static AdMessageKey MSG_EXTERNAL_SYS_CONFIG_CANNOT_CHANGE_TYPE = AdMessageKey.of("External_System_Config_Cannot_Change_Type");

	public ExternalSystem_Config(@NonNull final ExternalSystemConfigRepo externalSystemConfigRepo)
	{
		this.externalSystemConfigRepo = externalSystemConfigRepo;
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_ExternalSystem_Config.COLUMNNAME_Type)
	public void check(final I_ExternalSystem_Config config)
	{
		final I_ExternalSystem_Config oldParent = InterfaceWrapperHelper.createOld(config, I_ExternalSystem_Config.class);
		final ExternalSystemType oldParentType = ExternalSystemType.ofCode(oldParent.getType());

		final ExternalSystemParentConfigId parentConfigId = ExternalSystemParentConfigId.ofRepoId(config.getExternalSystem_Config_ID());

		final Optional<IExternalSystemChildConfig> childConfig = externalSystemConfigRepo.getChildByParentIdAndType(parentConfigId, oldParentType);

		childConfig.ifPresent(x -> {
			throw new AdempiereException(MSG_EXTERNAL_SYS_CONFIG_CANNOT_CHANGE_TYPE).markAsUserValidationError();
		});

	}
}
