package de.metas.edi.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.uom.IUOMDAO;

/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2019 metas GmbH
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

class DesadvBLTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void setQty_isUOMForTUs()
	{
		final I_C_UOM uomRecord = newInstance(I_C_UOM.class);
		uomRecord.setX12DE355(IUOMDAO.X12DE355_COLI);
		saveRecord(uomRecord);

		final I_EDI_DesadvLine desadvLineRecord = newInstance(I_EDI_DesadvLine.class);
		desadvLineRecord.setQtyItemCapacity(new BigDecimal("9"));
		desadvLineRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
		desadvLineRecord.setQtyEntered(new BigDecimal("99"));
		saveRecord(desadvLineRecord);

		// invoke the method under test
		new DesadvBL().setQty(desadvLineRecord, new BigDecimal("20.5"));

		assertThat(desadvLineRecord)
				.extracting(
						"QtyEntered",
						"MovementQty",
						"QtyDeliveredInUOM")
				.containsExactly(
						new BigDecimal("99"),
						new BigDecimal("20.5"),
						new BigDecimal("3"));
	}

}
