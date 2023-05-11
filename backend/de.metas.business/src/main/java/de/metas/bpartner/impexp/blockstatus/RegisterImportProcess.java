/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.bpartner.impexp.blockstatus;

import de.metas.impexp.processing.IImportProcessFactory;
import de.metas.util.Services;
import org.compiere.model.I_I_BPartner_BlockStatus;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RegisterImportProcess
{
	private final IImportProcessFactory importProcessesFactory = Services.get(IImportProcessFactory.class);

	public RegisterImportProcess()
	{
		registerImportProcess();
	}

	private void registerImportProcess()
	{
		importProcessesFactory.registerImportProcess(I_I_BPartner_BlockStatus.class, BPartnerBlockStatusImportProcess.class);
	}
}