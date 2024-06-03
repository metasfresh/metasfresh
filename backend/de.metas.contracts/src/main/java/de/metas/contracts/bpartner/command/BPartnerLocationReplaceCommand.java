/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.bpartner.command;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.location.LocationId;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.util.Services;
import de.metas.util.lang.RepoIdAware;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.Collection;

import static de.metas.common.util.CoalesceUtil.coalesce;

@Builder(toBuilder = true)
public class BPartnerLocationReplaceCommand
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	@NonNull
	private final BPartnerLocationId oldBPLocationId;
	@NonNull
	private final LocationId oldLocationId;
	@NonNull
	private final I_C_BPartner_Location oldLocation;

	@NonNull
	private final BPartnerLocationId newBPLocationId;
	@NonNull
	private final LocationId newLocationId;
	@NonNull
	private final I_C_BPartner_Location newLocation;

	@Builder.Default
	private boolean saveNewLocation = true;

	public BPartnerLocationReplaceCommand(@NonNull final BPartnerLocationId oldBPLocationId,
			@Nullable final LocationId oldLocationId,
			@Nullable final I_C_BPartner_Location oldLocation,
			@NonNull final BPartnerLocationId newBPLocationId,
			@Nullable final LocationId newLocationId,
			@Nullable final I_C_BPartner_Location newLocation,
			@NonNull final Boolean saveNewLocation)
	{
		this.oldBPLocationId = oldBPLocationId;
		this.oldLocation = coalesce(oldLocation, bpartnerDAO.getBPartnerLocationByIdEvenInactive(oldBPLocationId));
		this.oldLocationId = coalesce(oldLocationId, LocationId.ofRepoId(this.oldLocation.getC_Location_ID()));

		this.newBPLocationId = newBPLocationId;
		this.newLocation = coalesce(newLocation, bpartnerDAO.getBPartnerLocationByIdEvenInactive(newBPLocationId));
		this.newLocationId = coalesce(newLocationId, LocationId.ofRepoId(this.newLocation.getC_Location_ID()));
		this.saveNewLocation = saveNewLocation;
	}

	public void execute()
	{
		if (!verify())
		{
			return;
		}
		updateOLCands();
		updateInvoiceCandidates();
		updateFlatrateTerms();
		deactivateOldBPLocation();
		if (saveNewLocation)
		{
			bpartnerDAO.save(newLocation);
		}
	}

	private boolean verify()
	{
		final int previousId = newLocation.getPrevious_ID();
		if (previousId <= 0)
		{
			return false;
		}

		final Timestamp validFrom = newLocation.getValidFrom();
		if (validFrom == null)
		{
			return false;
		}

		// Don't update the defaults if the current location is still valid.
		return !validFrom.after(Env.getDate());
	}

	private void updateOLCands()
	{
		updateOLCandColumn(I_C_OLCand.COLUMNNAME_C_BPartner_Location_ID, oldBPLocationId, newBPLocationId);
		updateOLCandColumn(I_C_OLCand.COLUMNNAME_C_BPartner_Location_Value_ID, oldLocationId, newLocationId);

		updateOLCandColumn(I_C_OLCand.COLUMNNAME_Bill_Location_ID, oldBPLocationId, newBPLocationId);
		updateOLCandColumn(I_C_OLCand.COLUMNNAME_Bill_Location_Value_ID, oldLocationId, newLocationId);

		updateOLCandColumn(I_C_OLCand.COLUMNNAME_DropShip_Location_ID, oldBPLocationId, newBPLocationId);
		updateOLCandColumn(I_C_OLCand.COLUMNNAME_DropShip_Location_Value_ID, oldLocationId, newLocationId);

		updateOLCandColumn(I_C_OLCand.COLUMNNAME_DropShip_Location_Override_ID, oldBPLocationId, newBPLocationId);
		updateOLCandColumn(I_C_OLCand.COLUMNNAME_DropShip_Location_Override_Value_ID, oldLocationId, newLocationId);

		updateOLCandColumn(I_C_OLCand.COLUMNNAME_HandOver_Location_ID, oldBPLocationId, newBPLocationId);
		updateOLCandColumn(I_C_OLCand.COLUMNNAME_HandOver_Location_Value_ID, oldLocationId, newLocationId);

		updateOLCandColumn(I_C_OLCand.COLUMNNAME_HandOver_Location_Override_ID, oldBPLocationId, newBPLocationId);
		updateOLCandColumn(I_C_OLCand.COLUMNNAME_HandOver_Location_Override_Value_ID, oldLocationId, newLocationId);

		updateOLCandColumn(I_C_OLCand.COLUMNNAME_C_BP_Location_Override_ID, oldBPLocationId, newBPLocationId);
		updateOLCandColumn(I_C_OLCand.COLUMNNAME_C_BP_Location_Override_Value_ID, oldLocationId, newLocationId);
	}

	private void updateOLCandColumn(final String columnName, final RepoIdAware oldLocationId, final RepoIdAware newLocationId)
	{
		final ICompositeQueryUpdater<I_C_OLCand> queryUpdater = queryBL.createCompositeQueryUpdater(I_C_OLCand.class).addSetColumnValue(columnName, newLocationId);
		queryBL.createQueryBuilder(I_C_OLCand.class).addEqualsFilter(columnName, oldLocationId).addEqualsFilter(I_C_OLCand.COLUMNNAME_Processed, false).create().update(queryUpdater);
	}

	private void updateInvoiceCandidates()
	{
		updateInvoiceCandidateColumn(I_C_Invoice_Candidate.COLUMNNAME_Bill_Location_ID, oldBPLocationId, newBPLocationId);
		updateInvoiceCandidateColumn(I_C_Invoice_Candidate.COLUMNNAME_Bill_Location_Value_ID, oldLocationId, newLocationId);

		updateInvoiceCandidateColumn(I_C_Invoice_Candidate.COLUMNNAME_Bill_Location_Override_ID, oldBPLocationId, newBPLocationId);
		updateInvoiceCandidateColumn(I_C_Invoice_Candidate.COLUMNNAME_Bill_Location_Override_Value_ID, oldLocationId, newLocationId);

		updateInvoiceCandidateColumn(I_C_Invoice_Candidate.COLUMNNAME_First_Ship_BPLocation_ID, oldLocationId, newLocationId);
	}

	private void updateInvoiceCandidateColumn(final String columnName, final RepoIdAware oldLocationId, final RepoIdAware newLocationId)
	{
		final ICompositeQueryUpdater<I_C_Invoice_Candidate> queryUpdater = queryBL.createCompositeQueryUpdater(I_C_Invoice_Candidate.class)
				.addSetColumnValue(columnName, newLocationId);
		queryBL
				.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addEqualsFilter(columnName, oldLocationId)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_Processed, false)
				.create()
				.update(queryUpdater);
	}

	private void updateFlatrateTerms()
	{
		updateFlatrateTermsColumn(I_C_Flatrate_Term.COLUMNNAME_Bill_Location_ID, oldBPLocationId, newBPLocationId);
		updateFlatrateTermsColumn(I_C_Flatrate_Term.COLUMNNAME_DropShip_Location_ID, oldBPLocationId, newBPLocationId);
	}

	private void updateFlatrateTermsColumn(final String columnName, final RepoIdAware oldLocationId, final RepoIdAware newLocationId)
	{
		final Collection<String> disallowedFlatrateStatuses = ImmutableSet.of(X_C_Flatrate_Term.CONTRACTSTATUS_Voided, X_C_Flatrate_Term.CONTRACTSTATUS_Quit);
		final ICompositeQueryUpdater<I_C_Flatrate_Term> queryUpdater = queryBL.createCompositeQueryUpdater(I_C_Flatrate_Term.class)
				.addSetColumnValue(columnName, newLocationId);
		queryBL
				.createQueryBuilder(I_C_Flatrate_Term.class)
				.addEqualsFilter(columnName, oldLocationId)
				.addNotInArrayFilter(I_C_Flatrate_Term.COLUMN_ContractStatus,disallowedFlatrateStatuses)
				.addCompareFilter(I_C_Flatrate_Term.COLUMN_EndDate, CompareQueryFilter.Operator.GREATER_OR_EQUAL, Env.getDate())
				// .addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Processed, false) in this case contracts are already processed
				.create()
				.update(queryUpdater);
	}

	private void deactivateOldBPLocation()
	{
		newLocation.setIsBillToDefault(newLocation.isBillToDefault() || oldLocation.isBillToDefault());
		newLocation.setIsShipToDefault(newLocation.isShipToDefault() || oldLocation.isShipToDefault());
		newLocation.setIsBillTo(newLocation.isBillTo() || oldLocation.isBillTo());
		newLocation.setIsShipTo(newLocation.isShipTo() || oldLocation.isShipTo());

		oldLocation.setIsBillToDefault(false);
		oldLocation.setIsShipToDefault(false);
		oldLocation.setIsActive(false);
		bpartnerDAO.save(oldLocation);
	}
}
