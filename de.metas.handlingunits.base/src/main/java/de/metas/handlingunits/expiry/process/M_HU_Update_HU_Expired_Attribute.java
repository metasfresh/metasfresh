package de.metas.handlingunits.expiry.process;

import java.time.LocalDate;

import org.compiere.Adempiere;

import de.metas.handlingunits.expiry.HUWithExpiryDatesService;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class M_HU_Update_HU_Expired_Attribute extends JavaProcess
{
	private final HUWithExpiryDatesService huWithExpiryDatesService = Adempiere.getBean(HUWithExpiryDatesService.class);

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		huWithExpiryDatesService.markExpiredWhereWarnDateExceeded(LocalDate.now().atStartOfDay());
		return MSG_OK;
	}
}
