package de.metas.invoicecandidate.api;

/*
 * #%L
 * de.metas.swat.base
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


import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;

import de.metas.adempiere.model.I_C_InvoiceLine;

/**
 * Invoice line product attribute: Attribute/Value to be included in {@link I_M_AttributeSetInstance} of the resulting {@link I_C_InvoiceLine}.
 * 
 * NOTE:
 * <ul>
 * <li>{@link #hashCode()} and {@link #equals(Object)} methods needs to be implemented because they are used to check if an {@link IInvoiceLineAttribute} is duplicate.
 * </ul>
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/08642_ASI_on_shipment%2C_but_not_in_Invoice_%28109350210928%29
 */
public interface IInvoiceLineAttribute
{
	/** @return aggregation key, a unique string identifier which contains the attribute name and it's value */
	String toAggregationKey();

	/** @return template {@link I_M_AttributeInstance} to be used as a template, when creating a new attribute instance. */
	I_M_AttributeInstance getAttributeInstanceTemplate();

	@Override
	public int hashCode();

	@Override
	public boolean equals(Object obj);
}
