package de.metas.costing;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_M_CostElement;

/*
 * #%L
 * de.metas.business
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

public enum CostingMethod implements ReferenceListAwareEnum
{
	StandardCosting(X_M_CostElement.COSTINGMETHOD_StandardCosting), //
	AveragePO(X_M_CostElement.COSTINGMETHOD_AveragePO), //
	LIFO(X_M_CostElement.COSTINGMETHOD_Lifo), //
	FIFO(X_M_CostElement.COSTINGMETHOD_Fifo), //
	LastPOPrice(X_M_CostElement.COSTINGMETHOD_LastPOPrice), //
	AverageInvoice(X_M_CostElement.COSTINGMETHOD_AverageInvoice), //
	LastInvoice(X_M_CostElement.COSTINGMETHOD_LastInvoice), //
	UserDefined(X_M_CostElement.COSTINGMETHOD_UserDefined), //
	ExternalProcessing(X_M_CostElement.COSTINGMETHOD__), //
	MovingAverageInvoice(X_M_CostElement.COSTINGMETHOD_MovingAverageInvoice)//

	;

	public static final int AD_REFERENCE_ID = X_M_CostElement.COSTINGMETHOD_AD_Reference_ID;

	@Getter
	private final String code;

	CostingMethod(final String code)
	{
		this.code = code;
	}

	public static CostingMethod ofNullableCode(final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	public static CostingMethod ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	private static final ReferenceListAwareEnums.ValuesIndex<CostingMethod> index = ReferenceListAwareEnums.index(values());
}
