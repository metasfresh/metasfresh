/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.inout.invoicecandidate.approvedforinvoice;

import com.google.common.collect.ImmutableList;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.approvedforinvoice.IApprovedForInvoicingFinder;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_InOut;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InOutDependentFinder implements IApprovedForInvoicingFinder
{
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

	@Override
	@NonNull
	public String getTableName()
	{
		return I_M_InOut.Table_Name;
	}

	@Override
	@NonNull
	public List<I_C_Invoice_Candidate> findApprovedForReference(final @NonNull TableRecordReference recordReference)
	{
		final InOutId inOutId = recordReference.getIdAssumingTableName(I_M_InOut.Table_Name, InOutId::ofRepoId);

		return inOutDAO.retrieveLinesForInOutId(inOutId)
				.stream()
				.map(InOutAndLineId::getInOutLineId)
<<<<<<< HEAD
				.map(inOutDAO::getLineByIdInTrx)
=======
				.map(inOutDAO::getLineById)
>>>>>>> 01acf328a21 (Revert "Revert "Merge remote-tracking branch 'origin/mad_orange_uat' into mad_orange_hotfix"" (#15192))
				.map(invoiceCandDAO::retrieveInvoiceCandidatesForInOutLine)
				.flatMap(List::stream)
				.filter(I_C_Invoice_Candidate::isApprovalForInvoicing)
				.collect(ImmutableList.toImmutableList());
	}
}
