package de.metas.acct.process;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.acct.accounts.ValidCombinationService;
import de.metas.process.JavaProcess;
import org.compiere.SpringContextHolder;

public class C_ValidCombination_UpdateDescriptionForAll extends JavaProcess
{
	private final ValidCombinationService validCombinationService = SpringContextHolder.instance.getBean(ValidCombinationService.class);

	@Override
	protected String doIt()
	{
		validCombinationService.updateValueDescriptionByClientId(getClientId());
		return MSG_OK;
	}
}
