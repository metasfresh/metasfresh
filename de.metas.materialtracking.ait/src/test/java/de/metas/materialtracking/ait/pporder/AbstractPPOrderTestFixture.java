package de.metas.materialtracking.ait.pporder;

import de.metas.materialtracking.ait.helpers.PPOrderDriver;

/*
 * #%L
 * de.metas.materialtracking.ait
 * %%
 * Copyright (C) 2016 metas GmbH
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
public abstract class AbstractPPOrderTestFixture
{
	public void updatePPOrderQualityFields(final String documentNo)
	{
		PPOrderDriver.updatePPOrderQualityFields(documentNo);
	}

	public void setupPPOrderHeader(
			final String nameDocType,
			final String documentNo,
			final String lotMaterialTracking,
			final String strDate) throws Throwable
	{
		PPOrderDriver.setupPPOrderHeader(nameDocType, documentNo, lotMaterialTracking, strDate);
	}

	public String setupPPOrderItems(final String documentNo,
			final String strType,
			final String valueProduct,
			final String strQty,
			final String nameReceipt)
	{
		return PPOrderDriver.setupPPOrderItems(documentNo, strType, valueProduct, strQty, nameReceipt);
	}
}
