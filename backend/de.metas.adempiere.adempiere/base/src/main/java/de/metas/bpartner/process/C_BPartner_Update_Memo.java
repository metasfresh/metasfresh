/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.bpartner.process;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.util.Services;

public class C_BPartner_Update_Memo extends JavaProcess
{
	private final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);

	@Param(parameterName = "Memo")
	private String p_Memo;

	@Override
	protected String doIt() throws Exception
	{
		bpartnerBL.updateMemo(BPartnerId.ofRepoId(getRecord_ID()),p_Memo);
		return "@Success@";
	}
}
