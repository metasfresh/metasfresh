package de.metas.document.archive.model;

/*
 * #%L
 * de.metas.document.archive.base
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

public interface I_C_BPartner extends de.metas.order.model.I_C_BPartner
{
	//@formatter:off
	// task 724 ; gh #443
	public static final String COLUMNNAME_IsInvoiceEmailEnabled = "IsInvoiceEmailEnabled";
	/**
	 * Get IsInvoiceEmailEnabled.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getIsInvoiceEmailEnabled();
	/**
	 * Set IsInvoiceEmailEnabled.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsInvoiceEmailEnabled (java.lang.String IsInvoiceEmailEnabled);
	//@formatter:on
}
