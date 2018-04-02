package de.metas.handlingunits.ddorder.api;

import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.product.model.I_M_Product_LotNumber_Lock;
import lombok.NonNull;
import lombok.Value;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


@Value
public final class QuarantineInOutLine
{
	@NonNull
	I_M_InOutLine inOutLine;

	@NonNull
	I_M_Product_LotNumber_Lock lockLotNo;

	public int getBpartnerId()
	{
		return inOutLine.getM_InOut().getC_BPartner_ID();
	}

	public int getBpartnerLocationId()
	{
		return inOutLine.getM_InOut().getC_BPartner_Location_ID();
	}

	public int getReceiptLineId()
	{
		return inOutLine.getM_InOutLine_ID();
	}
}