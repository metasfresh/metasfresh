package de.metas.materialtracking.qualityBasedInvoicing.impl;

/*
 * #%L
 * de.metas.materialtracking
 * %%
 * Copyright (C) 2015 metas GmbH
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


import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionOrder;

/**
 * Factory used to create {@link IQualityInspectionOrder} instances because {@link QualityInspectionOrder} is accessible only on package level.
 * 
 * @author tsa
 *
 */
public class QualityInspectionOrderFactory
{
	public static IQualityInspectionOrder createQualityInspectionOrder(
			final org.eevolution.model.I_PP_Order ppOrder,
			final I_M_Material_Tracking materialTracking)
	{
		return new QualityInspectionOrder(ppOrder, materialTracking);
	}
}
