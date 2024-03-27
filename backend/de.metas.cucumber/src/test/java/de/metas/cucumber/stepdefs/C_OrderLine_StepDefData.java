/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs;

import de.metas.order.OrderLineId;
import lombok.NonNull;
import org.compiere.model.I_C_OrderLine;

import javax.annotation.Nullable;

/**
 * Having a dedicated class to help the IOC-framework injecting the right instances, if a step-def needs more than one.
 */
public class C_OrderLine_StepDefData extends StepDefData<I_C_OrderLine>
{
	public C_OrderLine_StepDefData()
	{
		super(I_C_OrderLine.class);
	}

	@Nullable
	public OrderLineId getId(@NonNull final StepDefDataIdentifier identifier)
	{
		if (identifier.isNullPlaceholder())
		{
			return null;
		}
		else
		{
			return OrderLineId.ofRepoId(get(identifier).getC_OrderLine_ID());
		}
	}
}