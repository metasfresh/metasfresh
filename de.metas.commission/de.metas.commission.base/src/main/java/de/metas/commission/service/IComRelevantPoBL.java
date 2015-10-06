package de.metas.commission.service;

/*
 * #%L
 * de.metas.commission.base
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
import org.compiere.model.I_C_Invoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.PO;

import de.metas.commission.custom.type.ICommissionType;
import de.metas.commission.exception.InvokeCommissionTypeException;
import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.MCAdvComRelevantPOType;
import de.metas.commission.model.MCAdvCommissionFactCand;

public interface IComRelevantPoBL extends ISingletonService
{
	void recordIncident(MCAdvCommissionFactCand candidate);

	/**
	 * 
	 * @param candidate the relevant PO type to process has been set using {@link MCAdvCommissionFactCand#setCurrentRelPOType(MCAdvComRelevantPOType)}.
	 * @param documentError if <code>true</code> and a Throwable is caught during processing, this method calls
	 *            {@link #handleCandidateEvalThrowable(MCAdvCommissionFactCand, InvokeCommissionTypeException)}. If <code>false</code>, only a <code>InvokeCommissionTypeException</code> is thrown.
	 * @param adPInstanceID
	 * @throws InvokeCommissionTypeException if a throwable is caught during processing and if <code>!documentError</code>.
	 */
	void invokeCommissionType(MCAdvCommissionFactCand candidate, boolean documentError, int adPInstanceID);

	boolean hasCommissionPoints(PO ilOrOl);

	boolean isRelevantInvoiceLine(I_C_Invoice invoice, MInvoiceLine il, ICommissionType type);

	/**
	 * Finds out if the given candidate needs to be evaluated in the context of the given comSystemType. If the candidate's BPartner (see {@link MCAdvCommissionFactCand#retrieveBPartner()}) has a
	 * sponsor node that is not assigned to the given comSystemType's commission system (at the given candidate's dateAcct) or if the BPartner has no customer relation to the sponsor hierarchy at all,
	 * then the method returns <code>false</code>.
	 * 
	 * @param comSystemType
	 * @param candidate
	 * @param throwEx
	 * @return
	 */
	boolean isSameComSystem(I_C_AdvComSystem_Type comSystemType, MCAdvCommissionFactCand candidate, boolean throwEx);

	/**
	 * This method validates a given po (order, invoice etc) and decides what the commission system shall do with it. The method shall be called by a model validator when the given {@code po} is new
	 * or changed.
	 * 
	 * @param po
	 * @return
	 */
	boolean validatePOAfterNewOrChange(PO po);

	/**
	 * This method validates a given po (order, invoice etc) and decides if it can be deleted or is still needed by the commission system.
	 * 
	 * @param po
	 * @throws de.metas.commission.exception.CommissionException with a user-friendly message if the given po is still needed.
	 */
	void validatePOBeforeDelete(PO po);
}
