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

import java.util.List;

import org.adempiere.util.ISingletonService;

import de.metas.contracts.model.I_C_Invoice_Clearing_Alloc;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.validator.C_Invoice_Candidate;
import de.metas.materialtracking.qualityBasedInvoicing.ic.spi.impl.PP_Order_MaterialTracking_Handler;

/**
 * Retrieve different records which are related to creating/updating invoice candidates. Used by {@link PP_Order_MaterialTracking_Handler}
 * and {@link C_Invoice_Candidate}.
 *
 */
public interface IQualityInspectionHandlerDAO extends ISingletonService
{
	I_C_Invoice_Clearing_Alloc retrieveInitialInvoiceClearingAlloc(I_C_Invoice_Candidate invoiceCandidate);

	/**
	 * Gets a list of {@link I_C_Invoice_Candidate}s of original purchase order(s).
	 */
	<T extends I_C_Invoice_Candidate> List<T> retrieveOriginalInvoiceCandidates(I_M_Material_Tracking materialTracking, Class<T> clazz);

	/**
	 * Updates the <code>M_Material_Tracking_ID</code>, <code>C_DocTypeInvoice_ID</code>, <code>M_PricingSystem_ID</code> and in case of an inOutLine also the <code>M_PriceList_Version_ID</code> if
	 * the given <code>ic</code>.
	 * <p>
	 * This method is intended to update ICs that belong to a material tracking, but do not reference a quality inspection. The only know cases for this are
	 * <ul>
	 * <li>C_OrderLines
	 * <li>packing material M_InOutLines
	 * </ul>
	 *
	 * @param ic
	 * @param referencedObject can be <code>null</code>, in which case nothing is done.
	 */
	void updateICFromMaterialTracking(I_C_Invoice_Candidate ic, Object referencedObject);

	List<I_C_Invoice_Candidate> retrieveRelatedICs(Object model);

}
