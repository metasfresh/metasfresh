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

import ch.qos.logback.classic.Level;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.OrgMappingId;
import de.metas.bpartner.composite.BPartnerBankAccount;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.BPartnerContactType;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.composite.BPartnerLocationType;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.contracts.FlatrateTerm;
import de.metas.contracts.IContractChangeBL;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.bpartner.repository.OrgChangeRepository;
import de.metas.contracts.bpartner.repository.OrgMappingRepository;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.request.RequestTypeId;
import de.metas.request.api.IRequestBL;
import de.metas.request.api.IRequestTypeDAO;
import de.metas.request.api.RequestCandidate;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_OrgChange_History;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_R_Request;
import org.compiere.model.X_R_Request;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OrgChangeService
{

	private static final Logger logger = LogManager.getLogger(OrgChangeService.class);
	private final ILoggable loggable = Loggables.withLogger(logger, Level.INFO);

	private final AdMessageKey MSG_OrgChangeSummary = AdMessageKey.of("R_Request_OrgChange_Summary");

	private final OrgChangeRepository orgChangeRepo;
	private final BPartnerCompositeRepository bpCompositeRepo;
	private final OrgMappingRepository orgMappingRepo;

	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IContractChangeBL contractChangeBL = Services.get(IContractChangeBL.class);

	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IRequestTypeDAO requestTypeDAO = Services.get(IRequestTypeDAO.class);

	private final IRequestBL requestBL = Services.get(IRequestBL.class);

	public OrgChangeService(@NonNull final OrgChangeRepository orgChangeRepo,
			@NonNull final BPartnerCompositeRepository bpCompositeRepo,
			@NonNull final OrgMappingRepository orgMappingRepo)
	{
		this.orgChangeRepo = orgChangeRepo;
		this.bpCompositeRepo = bpCompositeRepo;
		this.orgMappingRepo = orgMappingRepo;
	}

	public void moveToNewOrg(@NonNull final OrgChangeRequest orgChangeRequest)
	{
		final OrgChangeCommand orgChangeCommand = OrgChangeCommand.builder()
				.orgChangeService(this)
				.orgChangeRepo(orgChangeRepo)
				.bPartnerCompositeRepo(bpCompositeRepo)
				.orgChangeRequest(orgChangeRequest)
				.build();

		orgChangeCommand.execute();
	}

	public void createFlatrateTerms(@NonNull final OrgChangeBPartnerComposite orgChangeBPartnerComposite,
			@NonNull final BPartnerComposite destinationBPartnerComposite,
			@NonNull final OrgChangeRequest orgChangeRequest)
	{
		createMembershipFlatrateTerms(orgChangeBPartnerComposite,
									  destinationBPartnerComposite,
									  orgChangeRequest);

		createNonMembershipFlatrateTerms(orgChangeBPartnerComposite,
										 destinationBPartnerComposite,
										 orgChangeRequest);
	}

	public void cancelSubscriptionsFor(final OrgChangeBPartnerComposite orgChangeBPartnerComposite, final OrgChangeRequest orgChangeRequest)
	{
		final IContractChangeBL.ContractChangeParameters contractChangeParameters = IContractChangeBL.ContractChangeParameters.builder()
				.changeDate(Objects.requireNonNull(TimeUtil.asTimestamp(orgChangeRequest.getStartDate())))
				.isCloseInvoiceCandidate(true)
				.terminationReason(X_C_Flatrate_Term.TERMINATIONREASON_OrgChange)
				.isCreditOpenInvoices(false)
				.action(IContractChangeBL.ChangeTerm_ACTION_Cancel)
				.build();

		orgChangeBPartnerComposite.getMembershipSubscriptions()
				.stream()
				.map(FlatrateTerm::getFlatrateTermId)
				.map(flatrateDAO::getById)
				.forEach(currentTerm -> contractChangeBL.cancelContract(currentTerm, contractChangeParameters));

		orgChangeBPartnerComposite.getNonMembershipSubscriptions()
				.stream()
				.map(FlatrateTerm::getFlatrateTermId)
				.map(flatrateDAO::getById)
				.forEach(currentTerm -> contractChangeBL.cancelContract(currentTerm, contractChangeParameters));

	}

	private void createNonMembershipFlatrateTerms(
			@NonNull final OrgChangeBPartnerComposite orgChangeBPartnerComposite,
			@NonNull final BPartnerComposite destinationBPartnerComposite,
			@NonNull final OrgChangeRequest orgChangeRequest)
	{
		orgChangeRepo.createNonMembershipSubscriptionTerm(orgChangeBPartnerComposite, destinationBPartnerComposite, orgChangeRequest);

	}

	private void createMembershipFlatrateTerms(
			@NonNull final OrgChangeBPartnerComposite orgChangeBPartnerComposite,
			@NonNull final BPartnerComposite destinationBPartnerComposite,
			@NonNull final OrgChangeRequest orgChangeRequest)
	{
		orgChangeRepo.createMembershipSubscriptionTerm(orgChangeBPartnerComposite, destinationBPartnerComposite, orgChangeRequest);
	}

	public List<BPartnerLocation> getOrCreateLocations(
			@NonNull final OrgChangeBPartnerComposite orgChangeBPartnerComposite,
			@NonNull final BPartnerComposite destinationBPartnerComposite)
	{
		final List<BPartnerLocation> locationsInSourcePartner = orgChangeBPartnerComposite
				.getBPartnerComposite()
				.getLocations();

		final DefaultLocations sourceDefaultLocations = orgChangeBPartnerComposite.getDefaultLocations();

		unmarkDefaultLocationsFromDestination(sourceDefaultLocations, destinationBPartnerComposite);

		final List<BPartnerLocation> existingLocationsInDestinationPartner = destinationBPartnerComposite.getLocations();
		final List<BPartnerLocation> destinationLocations = new ArrayList<>();

		for (final BPartnerLocation sourceLocation : locationsInSourcePartner)
		{
			final OrgMappingId locationOrgMappingId = orgMappingRepo.getCreateOrgMappingId(sourceLocation);
			sourceLocation.setOrgMappingId(locationOrgMappingId);

			final BPartnerLocation matchingLocation = existingLocationsInDestinationPartner.stream()
					.filter(location -> OrgMappingId.equals(locationOrgMappingId, location.getOrgMappingId()))
					.findFirst()
					.orElse(null);

			if (matchingLocation != null)
			{
				final BPartnerLocationType newLocationType = updateMatchingLocationType(
						sourceDefaultLocations,
						sourceLocation,
						matchingLocation);

				matchingLocation.setLocationType(newLocationType);
				matchingLocation.setActive(true);

				loggable.addLog("Location {} from the existing partner {} was preserved.",
								matchingLocation,
								destinationBPartnerComposite.getBpartner());
			}
			else
			{
				final BPartnerLocation newLocation = sourceLocation.toBuilder()
						.id(null)
						.active(true)
						.build();
				destinationLocations.add(newLocation);

				loggable.addLog("Location {} was created for the destination partner {}.",
								newLocation,
								destinationBPartnerComposite.getBpartner());
			}
		}

		return destinationLocations;
	}

	public List<BPartnerContact> getOrCreateContacts(
			final OrgChangeBPartnerComposite orgChangeBPartnerComposite,
			final BPartnerComposite destinationBPartnerComposite)
	{
		final List<BPartnerContact> contactsInSourcePartner = orgChangeBPartnerComposite
				.getBPartnerComposite()
				.getContacts();

		final DefaultContacts sourceDefaultContacts = getDefaultContacts(orgChangeBPartnerComposite);
		unmarkDefaultContactsFromDestination(sourceDefaultContacts, destinationBPartnerComposite);

		final List<BPartnerContact> existingContactsInDestinationPartner = destinationBPartnerComposite.getContacts();

		final List<BPartnerContact> newContacts = new ArrayList<>();
		for (final BPartnerContact sourceContact : contactsInSourcePartner)
		{
			final OrgMappingId contactOrgMappingId = orgMappingRepo.getCreateOrgMappingId(sourceContact);
			sourceContact.setOrgMappingId(contactOrgMappingId);

			final BPartnerContact matchingContact = existingContactsInDestinationPartner.stream()
					.filter(contact -> contactOrgMappingId.equals(contact.getOrgMappingId()))
					.findFirst()
					.orElse(null);

			if (matchingContact != null)
			{
				final BPartnerContactType newContactType = updateMatchingContactType(
						sourceDefaultContacts,
						sourceContact,
						matchingContact);

				matchingContact.setContactType(newContactType);
				matchingContact.setActive(true);

				loggable.addLog("Contact {} from the existing partner {} was preserved.",
								matchingContact,
								destinationBPartnerComposite.getBpartner());
			}
			else
			{
				final BPartnerContact newContact = sourceContact.toBuilder()
						.id(null)
						.active(true)
						.build();

				newContacts.add(newContact);

				loggable.addLog("Contact {} was created for the destination partner {}.",
								newContact,
								destinationBPartnerComposite.getBpartner());
			}
		}

		return newContacts;
	}

	public List<BPartnerBankAccount> getOrCreateBPBankAccounts(
			@NonNull final OrgChangeBPartnerComposite orgChangeBPartnerComposite,
			@NonNull final BPartnerComposite destinationBPartnerComposite)
	{
		final List<BPartnerBankAccount> sourceBankAccounts = orgChangeBPartnerComposite
				.getBPartnerComposite()
				.getBankAccounts();

		final List<BPartnerBankAccount> existingBankAccountsInDestinationPartner =
				destinationBPartnerComposite
						.getBankAccounts();

		final List<BPartnerBankAccount> newBankAccounts = new ArrayList<>();

		for (final BPartnerBankAccount sourceBankAccount : sourceBankAccounts)
		{
			final OrgMappingId bankAccountOrgMappingId = orgMappingRepo.getCreateOrgMappingId(sourceBankAccount);

			sourceBankAccount.setOrgMappingId(bankAccountOrgMappingId);

			final BPartnerBankAccount matchingBankAccount = existingBankAccountsInDestinationPartner.stream()
					.filter(bankAccount -> OrgMappingId.equals(bankAccountOrgMappingId, bankAccount.getOrgMappingId()))
					.findFirst()
					.orElse(null);

			if (matchingBankAccount != null)
			{
				matchingBankAccount.setActive(true);

				loggable.addLog("Bank Account {} from the existing partner {} was preserved.",
								matchingBankAccount,
								destinationBPartnerComposite.getBpartner());
			}
			else
			{
				final BPartnerBankAccount newBankAccount = sourceBankAccount.toBuilder()
						.id(null)
						.active(true)
						.build();

				newBankAccounts.add(newBankAccount);

				loggable.addLog("Bank Account {} was created for the destination partner {}.",
								newBankAccount,
								destinationBPartnerComposite.getBpartner());
			}
		}
		return newBankAccounts;
	}

	private BPartnerContactType updateMatchingContactType(
			@NonNull final DefaultContacts sourceDefaultContacts,
			@NonNull final BPartnerContact sourceContact,
			@NonNull final BPartnerContact matchingContact)
	{
		final BPartnerContactType existingContactType = sourceContact.getContactType();

		final BPartnerContactType newContactType = existingContactType.deepCopy();

		if (!sourceDefaultContacts.isFoundDefaultContact())
		{
			newContactType.setDefaultContact(matchingContact.getContactType().getIsDefaultContactOr(false));
		}
		if (!sourceDefaultContacts.isFoundBillToDefaultContact())
		{
			newContactType.setBillToDefault(matchingContact.getContactType().getIsBillToDefaultOr(false));
		}
		if (!sourceDefaultContacts.isFoundShipToDefaultContact())
		{
			newContactType.setShipToDefault(matchingContact.getContactType().getIsShipToDefaultOr(false));
		}
		if (!sourceDefaultContacts.isFoundSalesDefaultContact())
		{
			newContactType.setSalesDefault(matchingContact.getContactType().getIsSalesDefaultOr(false));
		}
		if (!sourceDefaultContacts.isFoundPurchaseDefaultContact())
		{
			newContactType.setPurchaseDefault(matchingContact.getContactType().getIsPurchaseDefaultOr(false));
		}
		return newContactType;
	}

	private BPartnerLocationType updateMatchingLocationType(
			@NonNull final DefaultLocations sourceDefaultLocations,
			@NonNull final BPartnerLocation sourceLocation,
			@NonNull final BPartnerLocation matchingLocation)
	{
		final BPartnerLocationType existingLocationType = sourceLocation.getLocationType();

		final BPartnerLocationType newLocationType = existingLocationType.deepCopy();

		if (!sourceDefaultLocations.isFoundBillToDefaultLocation())
		{
			newLocationType.setBillToDefault(matchingLocation.getLocationType().getIsBillToDefaultOr(false));
		}
		if (!sourceDefaultLocations.isFoundShipToDefaultLocation())
		{
			newLocationType.setShipToDefault(matchingLocation.getLocationType().getIsShipToDefaultOr(false));
		}

		return newLocationType;
	}

	private void unmarkDefaultContactsFromDestination(@NonNull final DefaultContacts sourceDefaultContacts,
			@NonNull final BPartnerComposite destinationBPartnerComposite)
	{
		final List<BPartnerContact> destinationContacts = destinationBPartnerComposite.getContacts();

		if (sourceDefaultContacts.isFoundDefaultContact())
		{
			unmarkDefaultContacts(destinationContacts);
			loggable.addLog("The default contact will be the counterpart of the initial contact {}."
									+ " ->  Mark all the remaining contacts in the destination partner {} as non-default",
							sourceDefaultContacts.getDefaultContact(), destinationBPartnerComposite.getBpartner());
		}

		if (sourceDefaultContacts.isFoundBillToDefaultContact())
		{
			unmarkBillToDefaultContacts(destinationContacts);
			loggable.addLog("The billToDefault contact will be the counterpart of the initial contact {}."
									+ " ->  Mark all the remaining contacts in the destination partner {} as non-billToDefault",
							sourceDefaultContacts.getBillToDefaultContact(), destinationBPartnerComposite.getBpartner());
		}

		if (sourceDefaultContacts.isFoundShipToDefaultContact())
		{
			unmarkShipToDefaultContacts(destinationContacts);
			loggable.addLog("The shipToDefault contact will be the counterpart of the initial contact {}."
									+ " ->  Mark all the remaining contacts in the destination partner {} as non-shipToDefault",
							sourceDefaultContacts.getShipToDefaultContact(), destinationBPartnerComposite.getBpartner());
		}

		if (sourceDefaultContacts.isFoundSalesDefaultContact())
		{
			unmarkSalesDefaultContacts(destinationContacts);
			loggable.addLog("The salesDefault contact will be the counterpart of the initial contact {}."
									+ " -> Mark all the remaining contacts in the destination partner {} as non-salesDefault",
							sourceDefaultContacts.getSalesDefaultContact(), destinationBPartnerComposite.getBpartner());
		}

		if (sourceDefaultContacts.isFoundPurchaseDefaultContact())
		{
			unmarkPurchaseDefaultContacts(destinationContacts);
			loggable.addLog("The purchaseDefault contact will be the counterpart of the initial contact {}."
									+ " -> Mark all the remaining contacts in the destination partner {} as non-purchaseDefault",
							sourceDefaultContacts.getPurchaseDefaultContact(), destinationBPartnerComposite.getBpartner());
		}
	}

	private DefaultContacts getDefaultContacts(final OrgChangeBPartnerComposite orgChangebpartnerComposite)
	{
		final BPartnerContact defaultContact = orgChangebpartnerComposite.getDefaultContactOrNull();
		final BPartnerContact billToDefaultContact = orgChangebpartnerComposite.getBillToDefaultContactOrNull();
		final BPartnerContact shipToDefaultContact = orgChangebpartnerComposite.getShipToDefaultContactOrNull();
		final BPartnerContact salesDefaultContact = orgChangebpartnerComposite.getSalesDefaultContactOrNull();
		final BPartnerContact purchaseDefaultContact = orgChangebpartnerComposite.getPurchaseDefaultContactOrNull();

		return DefaultContacts.builder()
				.defaultContact(defaultContact)
				.foundDefaultContact(defaultContact != null)

				.shipToDefaultContact(shipToDefaultContact)
				.foundShipToDefaultContact(shipToDefaultContact != null)

				.billToDefaultContact(billToDefaultContact)
				.foundBillToDefaultContact(billToDefaultContact != null)

				.salesDefaultContact(salesDefaultContact)
				.foundSalesDefaultContact(salesDefaultContact != null)

				.purchaseDefaultContact(purchaseDefaultContact)
				.foundPurchaseDefaultContact(purchaseDefaultContact != null)
				.build();
	}

	private void unmarkDefaultLocationsFromDestination(@NonNull final DefaultLocations sourceDefaultLocations,
			@NonNull final BPartnerComposite destinationBPartnerComposite)
	{
		final List<BPartnerLocation> destinationLocations = destinationBPartnerComposite.getLocations();

		if (sourceDefaultLocations.isFoundBillToDefaultLocation())
		{
			unmarkBillToDefaultLocations(destinationLocations);
			loggable.addLog("The billToDefault location will be the counterpart of the initial location {}."
									+ " -> Mark all the remaining locations in the destination partner {} as non-billToDefault",
							sourceDefaultLocations.getBillToDefaultLocation(), destinationBPartnerComposite.getBpartner());
		}

		if (sourceDefaultLocations.isFoundShipToDefaultLocation())
		{
			unmarkShipToDefaultLocations(destinationLocations);
			loggable.addLog("The shipToDefault location will be the counterpart of the initial location {}."
									+ " -> Mark all the remaining locations in the destination partner {} as non-shipToDefault",
							sourceDefaultLocations.getShipToDefaultLocation(), destinationBPartnerComposite.getBpartner());
		}
	}

	public void unmarkBillToDefaultLocations(final List<BPartnerLocation> locations)
	{
		for (final BPartnerLocation location : locations)
		{
			final BPartnerLocationType type = location.getLocationType();
			type.setBillToDefault(false);
		}
	}

	public void unmarkShipToDefaultLocations(final List<BPartnerLocation> locations)
	{
		for (final BPartnerLocation location : locations)
		{
			final BPartnerLocationType type = location.getLocationType();
			type.setShipToDefault(false);
		}
	}

	public void unmarkDefaultContacts(final List<BPartnerContact> contacts)
	{
		for (final BPartnerContact contact : contacts)
		{
			final BPartnerContactType contactType = contact.getContactType();
			contactType.setDefaultContact(false);
		}
	}

	public boolean hasAnyMembershipProduct(final OrgId orgId)
	{
		return orgChangeRepo.hasAnyMembershipProduct(orgId);
	}

	public void saveOrgChangeBPartnerComposite(@NonNull final OrgChangeBPartnerComposite orgChangeBPartnerComposite)
	{
		final BPartnerComposite bPartnerComposite = orgChangeBPartnerComposite.getBPartnerComposite();

		bPartnerComposite.getBpartner().setOrgMappingId(orgChangeBPartnerComposite.getBPartnerOrgMappingId());

		bpCompositeRepo.save(bPartnerComposite);
	}

	public I_R_Request createOrgSwitchRequest(@NonNull OrgChangeHistoryId orgChangeHistoryId)
	{
		final RequestTypeId requestTypeId = requestTypeDAO.retrieveOrgChangeRequestTypeId();
		final I_AD_OrgChange_History orgChangeHistoryRecord = orgChangeRepo.getOrgChangeHistoryById(orgChangeHistoryId);

		final OrgId orgId = OrgId.ofRepoId(orgChangeHistoryRecord.getAD_OrgTo_ID());

		final ZoneId timeZone = orgDAO.getTimeZone(orgId);
		final ZonedDateTime requestDate = TimeUtil.asZonedDateTime(orgChangeHistoryRecord.getDate_OrgChange(), timeZone);

		final BPartnerId bPartnerId = BPartnerId.ofRepoId(orgChangeHistoryRecord.getC_BPartner_To_ID());

		final String summary = msgBL.getMsg(Env.getCtx(), MSG_OrgChangeSummary, new Object[] {
				orgDAO.getById(orgChangeHistoryRecord.getAD_Org_From_ID()).getName(),
				orgDAO.getById(orgChangeHistoryRecord.getAD_OrgTo_ID()).getName(),
				TimeUtil.asDate(requestDate) });

		final RequestCandidate requestCandidate = RequestCandidate.builder()
				.summary(summary)
				.confidentialType(X_R_Request.CONFIDENTIALTYPE_PartnerConfidential)
				.orgId(orgId)
				.recordRef(TableRecordReference.of(I_C_BPartner.Table_Name, bPartnerId))
				.requestTypeId(requestTypeId)
				.partnerId(bPartnerId)
				.dateDelivered(requestDate)

				.build();

		return requestBL.createRequest(requestCandidate);

	}

	public void unmarkBillToDefaultContacts(final List<BPartnerContact> contacts)
	{
		for (final BPartnerContact contact : contacts)
		{
			final BPartnerContactType contactType = contact.getContactType();
			contactType.setBillToDefault(false);
		}
	}

	public void unmarkPurchaseDefaultContacts(final List<BPartnerContact> contacts)
	{
		for (final BPartnerContact contact : contacts)
		{
			final BPartnerContactType contactType = contact.getContactType();
			contactType.setPurchaseDefault(false);
		}
	}

	public void unmarkSalesDefaultContacts(final List<BPartnerContact> contacts)
	{
		for (final BPartnerContact contact : contacts)
		{
			final BPartnerContactType contactType = contact.getContactType();
			contactType.setSalesDefault(false);
		}
	}

	public void unmarkShipToDefaultContacts(final List<BPartnerContact> contacts)
	{
		for (final BPartnerContact contact : contacts)
		{
			final BPartnerContactType contactType = contact.getContactType();
			contactType.setShipToDefault(false);
		}
	}

	public OrgChangeBPartnerComposite getByIdAndOrgChangeDate(final BPartnerId partnerId, final Instant orgChangeDate)
	{

		return orgChangeRepo.getByIdAndOrgChangeDate(partnerId, orgChangeDate);
	}
}
