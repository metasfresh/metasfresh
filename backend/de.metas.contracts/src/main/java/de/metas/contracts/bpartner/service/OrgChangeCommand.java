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

package de.metas.contracts.bpartner.service;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.OrgMappingId;
import de.metas.bpartner.composite.BPartner;
import de.metas.bpartner.composite.BPartnerBankAccount;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.contracts.bpartner.repository.OrgChangeRepository;
import lombok.Builder;
import lombok.NonNull;

import java.util.List;

public class OrgChangeCommand
{
	@NonNull
	private final OrgChangeService orgChangeService;
	@NonNull
	private final OrgChangeRepository orgChangeRepo;
	@NonNull
	private final OrgChangeRequest orgChangeRequest;
	@NonNull
	private final BPartnerCompositeRepository bPartnerCompositeRepo;

	@Builder
	private OrgChangeCommand(
			@NonNull OrgChangeService orgChangeService,
			@NonNull OrgChangeRepository orgChangeRepo,
			@NonNull OrgChangeRequest orgChangeRequest,
			@NonNull BPartnerCompositeRepository bPartnerCompositeRepo)
	{
		this.orgChangeService = orgChangeService;
		this.orgChangeRepo = orgChangeRepo;
		this.orgChangeRequest = orgChangeRequest;
		this.bPartnerCompositeRepo = bPartnerCompositeRepo;
	}

	public void execute()
	{
		moveToNewOrg(orgChangeRequest);
	}

	private void moveToNewOrg(
			@NonNull final OrgChangeRequest orgChangeRequest)
	{
		final OrgChangeBPartnerComposite orgChangeBPartnerComposite = orgChangeRepo.getByIdAndOrgChangeDate(orgChangeRequest.getBpartnerId()
				, orgChangeRequest.getStartDate());

		final OrgMappingId orgMappingId = orgChangeBPartnerComposite.getBPartnerOrgMappingId();

		final BPartnerId newBPartnerId = orgChangeRepo.getOrCreateCounterpartBPartner(orgChangeRequest, orgMappingId);

		// gets the partner with all the active and inactive locations, users and bank accounts
		BPartnerComposite destinationBPartnerComposite = bPartnerCompositeRepo.getById(newBPartnerId);

		final BPartner destinationPartner = destinationBPartnerComposite.getBpartner();

		destinationPartner.setActive(true);

		final List<BPartnerLocation> newLocations = orgChangeService.getOrCreateLocations(orgChangeBPartnerComposite, destinationBPartnerComposite);
		final List<BPartnerContact> newContacts = orgChangeService.getOrCreateContacts(orgChangeBPartnerComposite, destinationBPartnerComposite);
		final List<BPartnerBankAccount> newBPBankAccounts = orgChangeService.getOrCreateBPBankAccounts(orgChangeBPartnerComposite, destinationBPartnerComposite);

		destinationBPartnerComposite = destinationBPartnerComposite.deepCopy()
				.toBuilder()

				.locations(newLocations)
				.contacts(newContacts)
				.bankAccounts(newBPBankAccounts)
				.build();

		bPartnerCompositeRepo.save(destinationBPartnerComposite);

		orgChangeService.saveOrgChangeBPartnerComposite(orgChangeBPartnerComposite);

		orgChangeService.createFlatrateTerms(orgChangeBPartnerComposite, destinationBPartnerComposite, orgChangeRequest);

		orgChangeService.cancelSubscriptionsFor(orgChangeBPartnerComposite, orgChangeRequest);

		final OrgChangeHistoryId orgChangeHistoryId = orgChangeRepo.createOrgChangeHistory(orgChangeRequest, orgMappingId, destinationBPartnerComposite);

		orgChangeService.createOrgSwitchRequest(orgChangeHistoryId);
	}
}
