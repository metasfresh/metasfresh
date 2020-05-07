package de.metas.inout.api;

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


import org.adempiere.util.ISingletonService;

import de.metas.inout.model.I_M_InOut;

public interface IInOutInvoiceCandidateBL extends ISingletonService
{

	/**
	 * Check if the inout is ready for invoicing (it is Active, completed or closed);
	 * 
	 * @param inOut
	 * @return true if the inout can be approved for invoicing
	 */
	boolean isApproveInOutForInvoicing(de.metas.inout.model.I_M_InOut inOut);

	/**
	 * This method sets the flag IsInOutApprovedForInvoicing in the invoice candidates that are linked with the inout given as parameter
	 * It also sets the editable flag ApprovedForInvoicing on true in case the IsInOutApprovedForInvoicing is true.
	 * 
	 * In case the invoice candidates are linked with other inouts that are not yet approved for invoicing, the flag will also not be set in the invoice candidates
	 * 
	 * @param inOut
	 */
	void approveForInvoicingLinkedInvoiceCandidates(I_M_InOut inOut);
}
