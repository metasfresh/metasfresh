package de.metas.materialtracking.spi;

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


import org.compiere.model.I_C_Invoice_Detail;

import de.metas.materialtracking.IHandlingUnitsInfo;
import de.metas.materialtracking.IHandlingUnitsInfoWritableQty;
import de.metas.util.ISingletonService;

/**
 * SPI factory for handling units aspect.
 *
 * @author tsa
 *
 */
public interface IHandlingUnitsInfoFactory extends ISingletonService
{
	/**
	 * Gets the handling units information from given model.
	 */
	IHandlingUnitsInfo createFromModel(Object model);

	/**
	 * Returns an instance whose qty can be set, with preset values from the given <code>template</code>.
	 * TODO: i believe we don't need a mutable IHandlingUnitsInfoWritable..check if we can just create an immutable one, giving the tu-qty right here
	 */
	IHandlingUnitsInfoWritableQty createHUInfoWritableQty(IHandlingUnitsInfo template);

	/**
	 * Sets Handling Units Informations to given invoice detail.
	 */
	void updateInvoiceDetail(I_C_Invoice_Detail invoiceDetail, IHandlingUnitsInfo handlingUnitsInfo);

}
