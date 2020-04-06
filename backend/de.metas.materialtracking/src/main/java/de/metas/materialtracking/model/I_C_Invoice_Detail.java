package de.metas.materialtracking.model;



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

import org.adempiere.ad.persistence.ModelDynAttributeAccessor;

import de.metas.materialtracking.qualityBasedInvoicing.invoicing.IQualityInvoiceLine;

public interface I_C_Invoice_Detail extends de.metas.invoicecandidate.model.I_C_Invoice_Detail
{
	// @formatter:off
	 org.adempiere.model.ModelColumn<I_C_Invoice_Detail, I_PP_Order> COLUMN_PP_Order_ID =
			 new org.adempiere.model.ModelColumn<I_C_Invoice_Detail, I_PP_Order>(I_C_Invoice_Detail.class, "PP_Order_ID", I_PP_Order.class);
	 String COLUMNNAME_PP_Order_ID = "PP_Order_ID";

	 void setPP_Order_ID(int PP_Order_ID);
	 int getPP_Order_ID();
	 void setPP_Order(I_PP_Order PP_Order);
	 I_PP_Order getPP_Order();
	// @formatter:on


	ModelDynAttributeAccessor<I_C_Invoice_Detail, IQualityInvoiceLine> DYNATTR_C_Invoice_Detail_IQualityInvoiceLine =
			new ModelDynAttributeAccessor<>(IQualityInvoiceLine.class);
}
