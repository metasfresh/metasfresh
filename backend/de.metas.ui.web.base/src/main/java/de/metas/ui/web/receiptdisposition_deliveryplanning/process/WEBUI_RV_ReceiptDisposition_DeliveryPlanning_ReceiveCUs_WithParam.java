/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.receiptdisposition_deliveryplanning.process;

import de.metas.Profiles;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.Param;
import org.adempiere.exceptions.FillMandatoryException;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;

/**
 * "CUs annehmen mit Menge" on the receipt-disposition delivery-planning window: the operator types how much arrived.
 * <p>
 * Differs from its parent in the quantity only, so an exhausted line still offers it - the counterpart of
 * {@code WEBUI_M_ReceiptSchedule_ReceiveCUs_WithParam}'s {@code setAllowNoQuantityAvailable(true)}.
 */
@Profile(Profiles.PROFILE_Webui)
public class WEBUI_RV_ReceiptDisposition_DeliveryPlanning_ReceiveCUs_WithParam extends WEBUI_RV_ReceiptDisposition_DeliveryPlanning_ReceiveCUs
		implements IProcessDefaultParametersProvider
{
	private static final String PARAM_QtyCUsPerTU = "QtyCUsPerTU";
	@Param(parameterName = PARAM_QtyCUsPerTU, mandatory = true)
	private BigDecimal p_QtyCUsPerTU;

	@Override
	protected boolean isQtyToReceiveKnownUpfront()
	{
		return false;
	}

	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		// Pre-filled with what pressing the quantity-less variant would receive - the ROW's own figure, so the
		// operator of a split planned row is offered its share rather than the whole order line's remainder.
		return PARAM_QtyCUsPerTU.equals(parameter.getColumnName())
				? getQtyToReceive().toBigDecimal()
				: DEFAULT_VALUE_NOTAVAILABLE;
	}

	@Override
	protected BigDecimal getQtyToReceiveOverrideOrNull()
	{
		if (p_QtyCUsPerTU == null || p_QtyCUsPerTU.signum() <= 0)
		{
			throw new FillMandatoryException(PARAM_QtyCUsPerTU);
		}
		return p_QtyCUsPerTU;
	}
}
