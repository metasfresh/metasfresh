/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.pporder.api.impl;

import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_PP_Order;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;

public class PPOrderDocumentLUTUConfigurationHandlerTestHelper
{
	public static I_M_HU_LUTU_Configuration createNewLUTUConfiguration(@NonNull final I_PP_Order ppOrder)
	{
		return PPOrderDocumentLUTUConfigurationHandler.instance.createNewLUTUConfiguration(ppOrder);
	}

	public static I_M_HU_LUTU_Configuration createNewLUTUConfiguration(@NonNull final org.eevolution.model.I_PP_Order_BOMLine ppOrderBOMLine)
	{
		final de.metas.handlingunits.model.I_PP_Order_BOMLine huPPOrderBOMLine = InterfaceWrapperHelper.create(ppOrderBOMLine, de.metas.handlingunits.model.I_PP_Order_BOMLine.class);
		return PPOrderBOMLineDocumentLUTUConfigurationHandler.instance.createNewLUTUConfiguration(huPPOrderBOMLine);
	}
}
