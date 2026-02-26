package de.metas.invoicecandidate.spi.impl.aggregator.standard;

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

import de.metas.invoicecandidate.api.IInvoiceLineAttribute;
import lombok.NonNull;

import java.util.List;
import java.util.Set;

/**
 * Implementations of this interface are responsible for aggregating sets of {@link IInvoiceLineAttribute}s
 * and retain only those {@link IInvoiceLineAttribute} which are in common.
 * 
 * @author tsa
 *
 */
public interface IInvoiceLineAttributeAggregator
{
	/** @return aggregated invoice line attributes; never returns null */
	List<IInvoiceLineAttribute> aggregate();

	/**
	 * From all attributes added via this method, only common attributes are retained.
	 * <p>
	 * I.e. if an {@link IInvoiceLineAttribute} is added
	 * and we already have another {@link IInvoiceLineAttribute} with same attribute name but different attribute value,
	 * then that attribute won't be part of the aggregation result because it's considered a duplicate.
	 */
	void addToIntersection(@NonNull Set<IInvoiceLineAttribute> invoiceLineAttributesToAdd);

	/**
	 * All attribute added via this method will for sure be added to the invoice line.
	 */
	void addToUnion(@NonNull List<IInvoiceLineAttribute> attributesFromIC);
}
