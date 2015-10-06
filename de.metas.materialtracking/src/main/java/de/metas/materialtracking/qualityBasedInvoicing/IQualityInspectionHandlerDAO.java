package de.metas.materialtracking.qualityBasedInvoicing;

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


import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.util.ISingletonService;

import de.metas.flatrate.model.I_C_Invoice_Clearing_Alloc;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_PP_Order;

/**
 * 
 * Retrieve different records that are related to creating/updating invoice candidates. Used by {@link de.metas.materialtracking.qualityBasedInvoicing.ic.spi.impl.QualityInspectionHandler} and
 * {@link de.metas.materialtracking.model.validator.C_Invoice_Candidate}.
 *
 */
public interface IQualityInspectionHandlerDAO extends ISingletonService
{
	I_C_Invoice_Clearing_Alloc retrieveInitialInvoiceClearingAlloc(I_C_Invoice_Candidate invoiceCandidate);

	/**
	 * Return Quality Inspection orders which are CLosed and which don't already have an {@link I_C_Invoice_Candidate} linked to them.
	 *
	 * @param ctx
	 * @param limit
	 * @param trxName
	 * @return
	 */
	Iterator<I_PP_Order> retrievePPOrdersWithMissingICs(Properties ctx, int limit, String trxName);

	IQueryFilter<org.eevolution.model.I_PP_Order> getInvoiceableOrderFilter();

	boolean isInvoiceable(org.eevolution.model.I_PP_Order ppOrder);

	<T extends I_C_Invoice_Candidate> List<T> retrieveOriginalInvoiceCandidates(I_M_Material_Tracking materialTracking, Class<T> clazz);

	/**
	 * 
	 * @param ic
	 * @param referencedObject the record referenced by the given <code>ic</code>, or <code>null</code>
	 */
	void updateICFromMaterialTracking(I_C_Invoice_Candidate ic, Object referencedObject);
}
