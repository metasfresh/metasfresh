package de.metas.ui.web.handlingunits.process;

import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.Param;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import org.adempiere.exceptions.FillMandatoryException;

import java.math.BigDecimal;
import java.util.stream.Stream;

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

public class WEBUI_M_ReceiptSchedule_ReceiveCUs_WithParam extends WEBUI_M_ReceiptSchedule_ReceiveCUs implements IProcessDefaultParametersProvider
{
	private static final String PARAM_QtyCUsPerTU = "QtyCUsPerTU";
	@Param(parameterName = PARAM_QtyCUsPerTU, mandatory = true)
	private BigDecimal p_QtyCUsPerTU;

	public WEBUI_M_ReceiptSchedule_ReceiveCUs_WithParam()
	{
		// configure defaults
		setDisallowMultipleReceiptsSchedules();
		setAllowNoQuantityAvailable();
	}

	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (PARAM_QtyCUsPerTU.equals(parameter.getColumnName()))
		{
			final I_M_ReceiptSchedule receiptSchedule = getM_ReceiptSchedule();
			return getDefaultAvailableQtyToReceive(receiptSchedule);
		}
		else
		{
			return DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	@Override
	protected Stream<I_M_ReceiptSchedule> streamReceiptSchedulesToReceive()
	{
		return Stream.of(getM_ReceiptSchedule());
	}

	private I_M_ReceiptSchedule getM_ReceiptSchedule()
	{
		return getRecord(I_M_ReceiptSchedule.class);
	}

	@Override
	protected Quantity getEffectiveQtyToReceive(final I_M_ReceiptSchedule rs)
	{
		if (p_QtyCUsPerTU == null || p_QtyCUsPerTU.signum() <= 0)
		{
			throw new FillMandatoryException(PARAM_QtyCUsPerTU);
		}

		final UomId uomId = UomId.ofRepoId(rs.getC_UOM_ID());
		return Quantitys.of(p_QtyCUsPerTU, uomId);
	}
}
