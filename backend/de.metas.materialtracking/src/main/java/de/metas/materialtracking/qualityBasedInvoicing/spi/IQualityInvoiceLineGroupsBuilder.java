package de.metas.materialtracking.qualityBasedInvoicing.spi;

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


import java.util.List;

import de.metas.materialtracking.qualityBasedInvoicing.IVendorReceipt;
import de.metas.materialtracking.qualityBasedInvoicing.invoicing.IQualityInvoiceLineGroup;
import de.metas.pricing.IPricingContext;

public interface IQualityInvoiceLineGroupsBuilder
{

	List<IQualityInvoiceLineGroup> getCreatedInvoiceLineGroups();

	IQualityInvoiceLineGroupsBuilder setVendorReceipt(IVendorReceipt<?> receiptFromVendor);

	IQualityInvoiceLineGroupsBuilder setPricingContext(IPricingContext pricingContext);

	IQualityInvoiceLineGroupsBuilder create();

}
