package de.metas.inoutcandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

import de.metas.inoutcandidate.model.I_M_IolCandHandler;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ShipmentScheduleHandlerBL_retrieveHandlerRecord_Test
{
	private static final String CLASSNAME = "de.metas.test.SomeHandler";

	private ShipmentScheduleHandlerBL shipmentScheduleHandlerBL;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		shipmentScheduleHandlerBL = new ShipmentScheduleHandlerBL();
	}

	@Test
	void activeRecord_isReturned()
	{
		final I_M_IolCandHandler record = newInstance(I_M_IolCandHandler.class);
		record.setClassname(CLASSNAME);
		saveRecord(record);

		assertThat(shipmentScheduleHandlerBL.retrieveHandlerRecord(CLASSNAME).getM_IolCandHandler_ID())
				.isEqualTo(record.getM_IolCandHandler_ID());
	}

	@Test
	void noRecord_throws()
	{
		assertThatThrownBy(() -> shipmentScheduleHandlerBL.retrieveHandlerRecord(CLASSNAME))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(CLASSNAME);
	}
}
