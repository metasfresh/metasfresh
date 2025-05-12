/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts.flatrate.process;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.FlatrateTermRequest.FlatrateTermBillPartnerRequest;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;

import java.util.List;

public abstract class C_Flatrate_Term_Change_BillPartner_Base extends JavaProcess implements IProcessPrecondition
{
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

	@Param(parameterName = I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID, mandatory = true)
	private int p_billBPartnerId;

	@Param(parameterName = I_C_Flatrate_Term.COLUMNNAME_Bill_Location_ID, mandatory = true)
	private int p_billLocationId;

	@Param(parameterName = I_C_Flatrate_Term.COLUMNNAME_Bill_User_ID)
	private int p_billUserId;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		if (context.isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		if(ProcessUtil.isFlatFeeContract(context))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Not supported for FlatFee contracts");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		updateFlatrateTermsPartner();

		return MSG_OK;
	}

	private void updateFlatrateTermsPartner()
	{
		final List<I_C_Flatrate_Term> flatrateTermsToChange = getFlatrateTermsToChange();

		flatrateTermsToChange.forEach(this::updateFlatrateTermPartner);

	}

	private void updateFlatrateTermPartner(final I_C_Flatrate_Term term)
	{
		final ImmutableList<I_C_Flatrate_Term> nextTerms = flatrateBL.retrieveNextFlatrateTerms(term);

		updateFlatrateTermBillBPartner(term);

		nextTerms.forEach(this::updateFlatrateTermBillBPartner);
	}

	private void updateFlatrateTermBillBPartner(final I_C_Flatrate_Term term)
	{
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(p_billBPartnerId);
		final BPartnerLocationId bPartnerLocationId = BPartnerLocationId.ofRepoId(p_billBPartnerId, p_billLocationId);

		final BPartnerContactId bPartnerContactId = BPartnerContactId.ofRepoIdOrNull(p_billBPartnerId, p_billUserId);

		final boolean termHasInvoices = C_Flatrate_Term_Change_ProcessHelper.termHasInvoices(term);

		final FlatrateTermBillPartnerRequest request = FlatrateTermBillPartnerRequest.builder()
				.flatrateTermId(FlatrateTermId.ofRepoId(term.getC_Flatrate_Term_ID()))
				.billBPartnerId(bPartnerId)
				.billLocationId(bPartnerLocationId)
				.billUserId(bPartnerContactId)
				.termHasInvoices(termHasInvoices)
				.build();

		flatrateBL.updateFlatrateTermBillBPartner(request);
	}

	protected abstract ImmutableList<I_C_Flatrate_Term> getFlatrateTermsToChange();
}
