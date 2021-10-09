package de.metas.ui.web.handlingunits.process;

import org.springframework.context.annotation.Profile;

import de.metas.Profiles;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL.CreateReceiptsParameters.CreateReceiptsParametersBuilder;
import de.metas.process.IProcessPrecondition;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
@Profile(Profiles.PROFILE_Webui)
public class WEBUI_M_HU_CreateReceipt_NoParams
		extends WEBUI_M_HU_CreateReceipt_Base
		implements IProcessPrecondition
{

	/** does nothing */
	@Override
	protected void customizeParametersBuilder(@NonNull final CreateReceiptsParametersBuilder parametersBuilder)
	{
		// nothing to be done with the existing parameters
	}
}
