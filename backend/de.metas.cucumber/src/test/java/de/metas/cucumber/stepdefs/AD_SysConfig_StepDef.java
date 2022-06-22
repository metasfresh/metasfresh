/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs;

import de.metas.util.Services;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;

public class AD_SysConfig_StepDef
{
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@And("^set sys config (String|boolean|int) value (.*) for sys config (.*)$")
	public void enable_sys_config(@NonNull final String sysconfigType, @NonNull final String sysconfigValue, @NonNull final String sysConfigName)
	{
		switch (sysconfigType)
		{
			case "String":
				sysConfigBL.setValue(sysConfigName, sysconfigValue, ClientId.SYSTEM, StepDefConstants.ORG_ID_SYSTEM);
				break;
			case "boolean":
				final boolean booleanValue = Boolean.parseBoolean(sysconfigValue);
				sysConfigBL.setValue(sysConfigName, booleanValue, ClientId.SYSTEM, StepDefConstants.ORG_ID_SYSTEM);
				break;
			case "int":
				final int intValue = Integer.parseInt(sysconfigValue);
				setSysConfigIntValue(sysConfigName, intValue);
				break;
			default:
				throw new AdempiereException("Unhandled sysConfig type")
						.appendParametersToMessage()
						.setParameter("type:", sysconfigType);
		}
	}

	private void setSysConfigIntValue(@NonNull final String name, final int value)
	{
		sysConfigBL.setValue(name, value, ClientId.SYSTEM, StepDefConstants.ORG_ID_SYSTEM);
	}
}
