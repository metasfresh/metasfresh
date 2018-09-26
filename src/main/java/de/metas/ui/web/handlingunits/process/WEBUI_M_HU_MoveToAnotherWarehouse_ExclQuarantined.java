package de.metas.ui.web.handlingunits.process;

import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.quarantine.HULotNumberQuarantineService;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorRowFilter.Select;

/*
 * #%L
 * metasfresh-webui-api
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

public class WEBUI_M_HU_MoveToAnotherWarehouse_ExclQuarantined extends WEBUI_M_HU_MoveToAnotherWarehouse_Helper
{
	public static final String MSG_WEBUI_HUs_IN_Quarantine = "WEBUI_HUs_IN_Quarantine";

	private final transient HULotNumberQuarantineService lotNumberQuarantineService = Adempiere.getBean(HULotNumberQuarantineService.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final boolean quarantineHUs = isQuarantineHUs(streamSelectedHUs(Select.ONLY_TOPLEVEL));

		if (quarantineHUs)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_HUs_IN_Quarantine));
		}

		return super.checkPreconditionsApplicable();
	}

	@Override
	public void assertHUsEligible()
	{
		if (isQuarantineHUs(streamSelectedHUs(Select.ONLY_TOPLEVEL)))
		{
			throw new AdempiereException(msgBL.getTranslatableMsgText(MSG_WEBUI_HUs_IN_Quarantine));
		}

	}

	private boolean isQuarantineHUs(Stream<I_M_HU> streamSelectedHUs)
	{
		return streamSelectedHUs
				.filter(huRecord -> lotNumberQuarantineService.isQuarantineHU(huRecord))
				.findAny()
				.isPresent();
	}
}
