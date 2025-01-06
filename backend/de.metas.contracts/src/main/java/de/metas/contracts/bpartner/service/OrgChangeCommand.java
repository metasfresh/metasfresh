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
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.OrgMappingId;
import de.metas.bpartner.composite.BPartnerBankAccount;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.BPartnerContactType;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.composite.BPartnerLocationType;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.bpartner.creditLimit.BPartnerCreditLimit;
import de.metas.bpartner.service.CloneBPartnerRequest;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.FlatrateTerm;
import de.metas.contracts.FlatrateTermPricing;
import de.metas.contracts.FlatrateTermRequest.CreateFlatrateTermRequest;
import de.metas.contracts.IContractChangeBL;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.bpartner.repository.OrgChangeRepository;
import de.metas.contracts.bpartner.repository.OrgMappingRepository;
import de.metas.contracts.location.adapter.ContractDocumentLocationAdapterFactory;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.lang.SOTrx;
import de.metas.location.ICountryDAO;
import de.metas.location.LocationId;
import de.metas.logging.LogManager;
import de.metas.order.compensationGroup.GroupCategoryId;
import de.metas.order.compensationGroup.GroupTemplate;
import de.metas.order.compensationGroup.GroupTemplateId;
import de.metas.order.compensationGroup.GroupTemplateRegularLine;
import de.metas.order.compensationGroup.GroupTemplateRepository;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPricingBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductAndCategoryId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.product.model.I_M_Product;
import de.metas.quantity.Quantity;
import de.metas.request.RequestTypeId;
import de.metas.request.api.IRequestBL;
import de.metas.request.api.IRequestTypeDAO;
import de.metas.request.api.RequestCandidate;
import de.metas.tax.api.TaxCategoryId;
import de.metas.user.api.IUserDAO;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_OrgChange_History;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.X_R_Request;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrgChangeCommand
{
	private static final Logger logger = LogManager.getLogger(OrgChangeCommand.class);
	private final ILoggable loggable = Loggables.withLogger(logger, Level.INFO);

	private final AdMessageKey MSG_OrgChangeSummary = AdMessageKey.of("R_Request_OrgChange_Summary");

	@NonNull
	private final BPartnerCompositeRepository bpCompositeRepo;
	@NonNull
	private final OrgMappingRepository orgMappingRepo;
	@NonNull
	private final OrgChangeRepository orgChangeRepo;
	@NonNull
	private final OrgChangeHistoryRepository orgChangeHistoryRepo;
	@NonNull
	private final GroupTemplateRepository groupTemplateRepo;

	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IContractChangeBL contractChangeBL = Services.get(IContractChangeBL.class);

	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IRequestTypeDAO requestTypeDAO = Services.get(IRequestTypeDAO.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
	private final IUserDAO userDAO = Services.get(IUserDAO.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IPricingBL pricingBL = Services.get(IPricingBL.class);
	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);

	private final IRequestBL requestBL = Services.get(IRequestBL.class);

	@NonNull
	private final OrgChangeRequest request;

	@Builder
	private OrgChangeCommand(
			final @NonNull BPartnerCompositeRepository bpCompositeRepo,
			final @NonNull OrgChangeRepository orgChangeRepo,
			final @NonNull OrgChangeHistoryRepository orgChangeHistoryRepo,
			final @NonNull OrgMappingRepository orgMappingRepo,
			final @NonNull GroupTemplateRepository groupTemplateRepo,
			//
			final @NonNull OrgChangeRequest request)
	{
		this.bpCompositeRepo = bpCompositeRepo;
		this.orgMappingRepo = orgMappingRepo;
		this.orgChangeRepo = orgChangeRepo;
		this.orgChangeHistoryRepo = orgChangeHistoryRepo;
		this.groupTemplateRepo = groupTemplateRepo;
		this.request = request;
	}

	public void execute()
	{
		final OrgChangeBPartnerComposite bpartnerAndSubscriptions = orgChangeRepo.getByIdAndOrgChangeDate(
				request.getBpartnerId(),
				request.getStartDate());

		final OrgMappingId orgMappingId = bpartnerAndSubscriptions.getBPartnerOrgMappingId();

		final BPartnerId newBPartnerId = getOrCreateCounterpartBPartner(request, orgMappingId);

		// gets the partner with all the active and inactive locations, users, bank accounts & credit limits
		BPartnerComposite destinationBPartnerComposite = bpCompositeRepo.getById(newBPartnerId);
		{
			destinationBPartnerComposite.getBpartner().setActive(true);

			final List<BPartnerLocation> newLocations = getOrCreateLocations(bpartnerAndSubscriptions, destinationBPartnerComposite);
			final List<BPartnerContact> newContacts = getOrCreateContacts(bpartnerAndSubscriptions, destinationBPartnerComposite);
			final List<BPartnerBankAccount> newBPBankAccounts = getOrCreateBPBankAccounts(bpartnerAndSubscriptions, destinationBPartnerComposite);
			final List<BPartnerCreditLimit> newBPCreditLimits = getOrCreateBPCreditLimits(bpartnerAndSubscriptions, destinationBPartnerComposite);

			destinationBPartnerComposite = destinationBPartnerComposite.deepCopy()
					.toBuilder()
					.locations(newLocations)
					.contacts(newContacts)
					.bankAccounts(newBPBankAccounts)
					.creditLimits(newBPCreditLimits)
					.build();
			bpCompositeRepo.save(destinationBPartnerComposite, false);
		}

		bpartnerBL.updateNameAndGreetingFromContacts(newBPartnerId);
		saveOrgChangeBPartnerComposite(bpartnerAndSubscriptions);

		createNewSubscriptions(bpartnerAndSubscriptions, destinationBPartnerComposite);

		cancelCurrentSubscriptions(bpartnerAndSubscriptions);

		final OrgChangeHistoryId orgChangeHistoryId = orgChangeHistoryRepo.createOrgChangeHistory(request, orgMappingId, destinationBPartnerComposite);

		createOrgSwitchRequests(orgChangeHistoryId);
	}

	private BPartnerId getOrCreateCounterpartBPartner(@NonNull final OrgChangeRequest orgChangeRequest, @NonNull final OrgMappingId orgMappingId)
	{
		final OrgId targetOrgId = orgChangeRequest.getOrgToId();
		return bpartnerDAO.getCounterpartBPartnerId(orgMappingId, targetOrgId)
				.orElseGet(() -> bpartnerDAO.cloneBPartnerRecord(CloneBPartnerRequest.builder()
																		 .fromBPartnerId(orgChangeRequest.getBpartnerId())
																		 .orgId(targetOrgId)
																		 .orgMappingId(orgMappingId)
																		 .build()));
	}
	private void createNewSubscriptions(
			@NonNull final OrgChangeBPartnerComposite bpartnerAndSubscriptions,
			@NonNull final BPartnerComposite destinationBPartnerComposite)
	{
		createMembershipSubscriptionTerm(bpartnerAndSubscriptions, destinationBPartnerComposite);
	}

	private void cancelCurrentSubscriptions(final OrgChangeBPartnerComposite bpartnerAndSubscriptions)
	{
		final IContractChangeBL.ContractChangeParameters contractChangeParameters = IContractChangeBL.ContractChangeParameters.builder()
				.changeDate(Objects.requireNonNull(TimeUtil.asTimestamp(request.getStartDate())))
				.isCloseInvoiceCandidate(request.isCloseInvoiceCandidate())
				.terminationReason(X_C_Flatrate_Term.TERMINATIONREASON_OrgChange)
				.isCreditOpenInvoices(false)
				.action(IContractChangeBL.ChangeTerm_ACTION_Cancel)
				.build();

		bpartnerAndSubscriptions.getAllRunningSubscriptions()
				.stream()
				.map(FlatrateTerm::getId)
				.map(flatrateDAO::getById)
				.forEach(currentTerm -> contractChangeBL.cancelContract(currentTerm, contractChangeParameters));

	}

	private void createMembershipSubscriptionTerm(
			@NonNull final OrgChangeBPartnerComposite orgChangeBPartnerComposite,
			@NonNull final BPartnerComposite destinationBPartnerComposite)
	{

		final GroupCategoryId groupCategoryId = request.getGroupCategoryId();
		if (groupCategoryId == null)
		{
			loggable.addLog("No membership subscription will be created for partner {} because there was no group category Id "
									+ "selected as paramteres", destinationBPartnerComposite.getBpartner());
			return;
		}

		final I_M_Product newOrgMembershipProduct = productDAO.getProductOfGroupCategory(groupCategoryId, request.getOrgToId()).orElse(null);
		if (newOrgMembershipProduct == null)
		{
			loggable.addLog("No product for the group category {} was found in org {}",
							groupCategoryId,
							destinationBPartnerComposite.getOrgId());
			return;
		}

		if (newOrgMembershipProduct.getC_CompensationGroup_Schema_ID() <= 0)
		{
			loggable.addLog("The product {} doesn't have a group template",
							newOrgMembershipProduct);
			return;
		}

		if (!orgChangeBPartnerComposite.hasMembershipSubscriptions())
		{
			loggable.addLog("No membership subscription will be created for partner {} because there is no running membership "
									+ "subscription in the initial partner {}",
							destinationBPartnerComposite.getBpartner(),
							orgChangeBPartnerComposite.getBpartner());
			return;
		}
		final FlatrateTerm sourceMembershipSubscription = orgChangeBPartnerComposite.getMembershipSubscriptions().get(0);

		final GroupTemplateId groupTemplateId = GroupTemplateId.ofRepoId(newOrgMembershipProduct.getC_CompensationGroup_Schema_ID());
		createTerms(destinationBPartnerComposite, request, groupTemplateId, sourceMembershipSubscription);
	}

	private void createTerms(
			@NonNull final BPartnerComposite destinationBPartnerComposite,
			@NonNull final OrgChangeRequest orgChangeRequest,
			@NonNull final GroupTemplateId groupTemplateId,
			@NonNull final FlatrateTerm sourceSubscription)
	{
		final I_C_BPartner partner = bpartnerDAO.getById(destinationBPartnerComposite.getBpartner().getId());

		final BPartnerLocation billBPartnerLocation = destinationBPartnerComposite.extractBillToLocation().orElse(null);
		if (billBPartnerLocation == null)
		{
			loggable.addLog("No BillTo Location found in partner {} -> no flatrate term will be created", partner);
			return;
		}

		final BPartnerLocation shipBPartnerLocation = destinationBPartnerComposite.extractShipToLocation().orElse(null);
		if (shipBPartnerLocation == null)
		{
			loggable.addLog("No shipTo Location found in partner {} -> no flatrate term will be created", partner);
			return;
		}

		final I_AD_User user = sourceSubscription.getUserInChargeId() == null ? null:
				userDAO.getCounterpartUser(sourceSubscription.getUserInChargeId(), orgChangeRequest.getOrgToId()).orElse(null);

		final Timestamp startDate = TimeUtil.asTimestamp(orgChangeRequest.getStartDate());

		final GroupTemplate groupTemplate = groupTemplateRepo.getById(groupTemplateId);

		final ImmutableList<GroupTemplateRegularLine> regularLinesToAdd = groupTemplate.getRegularLinesToAdd();

		final I_C_Flatrate_Conditions fallbackConditions = flatrateDAO.getConditionsById(sourceSubscription.getFlatrateConditionsId());

		for (final GroupTemplateRegularLine line : regularLinesToAdd)
		{
			final ProductId productId = line.getProductId();
			final ProductCategoryId productCategoryId = productDAO.retrieveProductCategoryByProductId(productId);

			final ProductAndCategoryId productAndCategoryId = ProductAndCategoryId.of(productId, productCategoryId);

			final ConditionsId contractConditionsId = line.getContractConditionsId();
			final I_C_Flatrate_Conditions conditionsToUse;
			if (contractConditionsId != null)
			{
				conditionsToUse = flatrateDAO.getConditionsById(contractConditionsId);
			}
			else
			{
				conditionsToUse = fallbackConditions;
			}

			final CreateFlatrateTermRequest flatrateTermRequest = CreateFlatrateTermRequest.builder()
					.context(PlainContextAware.newWithThreadInheritedTrx(Env.getCtx()))
					.orgId(orgChangeRequest.getOrgToId())
					.bPartner(partner)
					.startDate(startDate)
					.isSimulation(false)
					.conditions(conditionsToUse)
					.productAndCategoryId(productAndCategoryId)
					.userInCharge(user)
					.build();

			final I_C_Flatrate_Term term = flatrateBL.createTerm(flatrateTermRequest);

			final Quantity plannedQtyPerUnit = sourceSubscription.getPlannedQtyPerUnit();

			term.setPlannedQtyPerUnit(plannedQtyPerUnit == null ? BigDecimal.ZERO : plannedQtyPerUnit.toBigDecimal());
			term.setC_UOM_ID(plannedQtyPerUnit == null ? -1 : plannedQtyPerUnit.getUomId().getRepoId());

			final BPartnerLocationAndCaptureId dropshipLocationId = BPartnerLocationAndCaptureId.ofRepoIdOrNull(
					BPartnerId.toRepoId(shipBPartnerLocation.getId().getBpartnerId()),
					BPartnerLocationId.toRepoId(shipBPartnerLocation.getId()),
					LocationId.toRepoId(shipBPartnerLocation.getExistingLocationId()));

			ContractDocumentLocationAdapterFactory.dropShipLocationAdapter(term)
					.setFrom(dropshipLocationId);

			term.setDeliveryRule(sourceSubscription.getDeliveryRule() == null ? null : sourceSubscription.getDeliveryRule().getCode());
			term.setDeliveryViaRule(sourceSubscription.getDeliveryViaRule() == null ? null : sourceSubscription.getDeliveryViaRule().getCode());

			final IEditablePricingContext initialContext = pricingBL
					.createInitialContext(orgChangeRequest.getOrgToId(),
										  productId,
										  destinationBPartnerComposite.getBpartner().getId(),
										  plannedQtyPerUnit,
										  SOTrx.SALES);
			initialContext.setPriceDate(TimeUtil.asLocalDate(orgChangeRequest.getStartDate()));
			initialContext.setCountryId(countryDAO.getCountryIdByCountryCode(billBPartnerLocation.getCountryCode()));
			final IPricingResult pricingResult = pricingBL.calculatePrice(initialContext);
			term.setPriceActual(pricingResult.getPriceStd());
			term.setM_PricingSystem_ID(PricingSystemId.toRepoId(pricingResult.getPricingSystemId()));
			term.setC_Currency_ID(pricingResult.getCurrencyRepoId());

			final IPricingResult flatratePrice = calculateFlatrateTermPrice(term);
			term.setC_TaxCategory_ID(TaxCategoryId.toRepoId(flatratePrice.getTaxCategoryId()));
			term.setIsTaxIncluded(flatratePrice.isTaxIncluded());

			flatrateDAO.save(term);

			flatrateBL.complete(term);
		}
	}

	private List<BPartnerLocation> getOrCreateLocations(
			@NonNull final OrgChangeBPartnerComposite orgChangeBPartnerComposite,
			@NonNull final BPartnerComposite destinationBPartnerComposite)
	{
		final List<BPartnerLocation> locationsInSourcePartner = orgChangeBPartnerComposite.getLocations();

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

	private List<BPartnerContact> getOrCreateContacts(
			final OrgChangeBPartnerComposite orgChangeBPartnerComposite,
			final BPartnerComposite destinationBPartnerComposite)
	{
		final List<BPartnerContact> contactsInSourcePartner = orgChangeBPartnerComposite.getContacts();

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
				matchingContact.setGreetingId(sourceContact.getGreetingId());
				matchingContact.setTitleId(sourceContact.getTitleId());
				matchingContact.setFirstName(sourceContact.getFirstName());
				matchingContact.setLastName(sourceContact.getLastName());
				matchingContact.setMembershipContact(sourceContact.isMembershipContact());
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

	private List<BPartnerBankAccount> getOrCreateBPBankAccounts(
			@NonNull final OrgChangeBPartnerComposite orgChangeBPartnerComposite,
			@NonNull final BPartnerComposite destinationBPartnerComposite)
	{
		final List<BPartnerBankAccount> sourceBankAccounts = orgChangeBPartnerComposite.getBankAccounts();

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

	@Nullable
	private BPartnerContactType updateMatchingContactType(
			@NonNull final DefaultContacts sourceDefaultContacts,
			@NonNull final BPartnerContact sourceContact,
			@NonNull final BPartnerContact matchingContact)
	{
		final BPartnerContactType existingContactType = sourceContact.getContactType();

		if (existingContactType == null)
		{
			return null;
		}
		final BPartnerContactType newContactType = existingContactType.deepCopy();

		final BPartnerContactType matchingContactType = matchingContact.getContactType();

		if (matchingContactType == null)
		{
			return newContactType;
		}

		if (!sourceDefaultContacts.isFoundDefaultContact())
		{
			newContactType.setDefaultContact(matchingContactType.getIsDefaultContactOr(false));
		}
		if (!sourceDefaultContacts.isFoundBillToDefaultContact())
		{
			newContactType.setBillToDefault(matchingContactType.getIsBillToDefaultOr(false));
		}
		if (!sourceDefaultContacts.isFoundShipToDefaultContact())
		{
			newContactType.setShipToDefault(matchingContactType.getIsShipToDefaultOr(false));
		}
		if (!sourceDefaultContacts.isFoundSalesDefaultContact())
		{
			newContactType.setSalesDefault(matchingContactType.getIsSalesDefaultOr(false));
		}
		if (!sourceDefaultContacts.isFoundPurchaseDefaultContact())
		{
			newContactType.setPurchaseDefault(matchingContactType.getIsPurchaseDefaultOr(false));
		}
		return newContactType;
	}

	private BPartnerLocationType updateMatchingLocationType(
			@NonNull final DefaultLocations sourceDefaultLocations,
			@NonNull final BPartnerLocation sourceLocation,
			@NonNull final BPartnerLocation matchingLocation)
	{
		final BPartnerLocationType sourceLocationType = sourceLocation.getLocationType();
		final BPartnerLocationType matchingLocationType = matchingLocation.getLocationType();

		if (CoalesceUtil.coalesce(sourceLocationType, matchingLocationType) == null)
		{
			return null;
		}

		if (sourceLocationType == null)
		{
			return matchingLocationType;
		}
		if (matchingLocationType == null)
		{
			return sourceLocationType;
		}

		final BPartnerLocationType destinationLocationType = sourceLocationType.deepCopy();

		if (!sourceDefaultLocations.isFoundBillToDefaultLocation())
		{
			destinationLocationType.setBillToDefault(matchingLocationType.getIsBillToDefaultOr(false));
		}
		if (!sourceDefaultLocations.isFoundShipToDefaultLocation())
		{
			destinationLocationType.setShipToDefault(matchingLocationType.getIsShipToDefaultOr(false));
		}

		return destinationLocationType;
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

	@NonNull
	private List<BPartnerCreditLimit> getOrCreateBPCreditLimits(
			@NonNull final OrgChangeBPartnerComposite orgChangeBPartnerComposite,
			@NonNull final BPartnerComposite destinationBPartnerComposite)
	{
		final List<BPartnerCreditLimit> sourceCreditLimits = orgChangeBPartnerComposite.getCreditLimits();

		final List<BPartnerCreditLimit> existingCreditLimitsInDestinationPartner = destinationBPartnerComposite.getCreditLimits();

		final List<BPartnerCreditLimit> updatedDestinationCreditLimits = new ArrayList<>();

		for (final BPartnerCreditLimit sourceCreditLimit : sourceCreditLimits)
		{
			final OrgMappingId creditLimitOrgMappingId = orgMappingRepo.getCreateOrgMappingId(sourceCreditLimit);

			sourceCreditLimit.setOrgMappingId(creditLimitOrgMappingId);

			final BPartnerCreditLimit matchingCreditLimit_Updated = existingCreditLimitsInDestinationPartner.stream()
					.filter(bpartnerCreditLimit -> OrgMappingId.equals(creditLimitOrgMappingId, bpartnerCreditLimit.getOrgMappingId()))
					.findFirst()
					.map(matchingCreditLimit -> matchingCreditLimit.toBuilder()
							.amount(sourceCreditLimit.getAmount())
							.creditLimitTypeId(sourceCreditLimit.getCreditLimitTypeId())
							.dateFrom(sourceCreditLimit.getDateFrom())
							.processed(sourceCreditLimit.isProcessed())
							.active(true)
							.build())
					.orElse(null);

			if (matchingCreditLimit_Updated != null)
			{
				loggable.addLog("Credit Limit {} from the existing partner {} was preserved.",
								matchingCreditLimit_Updated,
								destinationBPartnerComposite.getBpartner());

				updatedDestinationCreditLimits.add(matchingCreditLimit_Updated);
			}
			else
			{
				final BPartnerCreditLimit newCreditLimit = sourceCreditLimit.toBuilder()
						.id(null)
						.active(true)
						.build();

				updatedDestinationCreditLimits.add(newCreditLimit);

				loggable.addLog("Credit Limit {} was created for the destination partner {}.",
								newCreditLimit,
								destinationBPartnerComposite.getBpartner());
			}
		}
		return updatedDestinationCreditLimits;
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

	private void unmarkBillToDefaultLocations(final List<BPartnerLocation> locations)
	{
		for (final BPartnerLocation location : locations)
		{
			final BPartnerLocationType type = location.getLocationType();
			if (type == null)
			{
				continue;
			}
			type.setBillToDefault(false);
		}
	}

	private void unmarkShipToDefaultLocations(final List<BPartnerLocation> locations)
	{
		for (final BPartnerLocation location : locations)
		{
			final BPartnerLocationType type = location.getLocationType();

			if (type == null)
			{
				continue;
			}
			type.setShipToDefault(false);
		}
	}

	private void unmarkDefaultContacts(final List<BPartnerContact> contacts)
	{
		for (final BPartnerContact contact : contacts)
		{
			final BPartnerContactType contactType = contact.getContactType();

			if (contactType == null)
			{
				continue;
			}
			contactType.setDefaultContact(false);
		}
	}

	private void saveOrgChangeBPartnerComposite(@NonNull final OrgChangeBPartnerComposite bpartnerAndSubscriptions)
	{
		final BPartnerComposite bPartnerComposite = bpartnerAndSubscriptions.getBPartnerComposite();
		bPartnerComposite.getBpartner().setOrgMappingId(bpartnerAndSubscriptions.getBPartnerOrgMappingId());
		bpCompositeRepo.save(bPartnerComposite, false);
	}

	private void createOrgSwitchRequests(@NonNull final OrgChangeHistoryId orgChangeHistoryId)
	{
		final I_AD_OrgChange_History orgChangeHistoryRecord = orgChangeHistoryRepo.getOrgChangeHistoryById(orgChangeHistoryId);

		final OrgId orgToId = OrgId.ofRepoId(orgChangeHistoryRecord.getAD_OrgTo_ID());
		final BPartnerId orgToBPartnerId = BPartnerId.ofRepoId(orgChangeHistoryRecord.getC_BPartner_To_ID());
		createOrgSwitchRequest(orgChangeHistoryRecord, orgToId, orgToBPartnerId);

		final OrgId orgFromId = OrgId.ofRepoId(orgChangeHistoryRecord.getAD_Org_From_ID());
		final BPartnerId orgFromBPartnerId = BPartnerId.ofRepoId(orgChangeHistoryRecord.getC_BPartner_From_ID());
		createOrgSwitchRequest(orgChangeHistoryRecord, orgFromId, orgFromBPartnerId);
	}

	private void createOrgSwitchRequest(@NonNull final I_AD_OrgChange_History orgChangeHistoryRecord, @NonNull final OrgId orgId, @NonNull final BPartnerId bPartnerId)
	{
		final RequestTypeId requestTypeId = requestTypeDAO.retrieveOrgChangeRequestTypeId();

		final ZoneId orgTimeZone = orgDAO.getTimeZone(orgId);
		final ZonedDateTime orgRequestDate = TimeUtil.asZonedDateTime(orgChangeHistoryRecord.getDate_OrgChange(), orgTimeZone);
		final String orgSummary = getSummaryForOrg(orgChangeHistoryRecord, orgRequestDate);

		final RequestCandidate orgRequestCandidate = RequestCandidate.builder()
				.summary(orgSummary)
				.confidentialType(X_R_Request.CONFIDENTIALTYPE_PartnerConfidential)
				.orgId(orgId)
				.recordRef(TableRecordReference.of(I_C_BPartner.Table_Name, bPartnerId))
				.requestTypeId(requestTypeId)
				.partnerId(bPartnerId)
				.dateDelivered(orgRequestDate)
				.build();

		requestBL.createRequest(orgRequestCandidate);
	}

	private String getSummaryForOrg(
			@NonNull final I_AD_OrgChange_History orgChangeHistoryRecord,
			@NonNull final ZonedDateTime zonedDateTime)
	{
		return msgBL.getMsg(Env.getCtx(), MSG_OrgChangeSummary, new Object[] {
				orgDAO.getById(orgChangeHistoryRecord.getAD_Org_From_ID()).getName(),
				orgDAO.getById(orgChangeHistoryRecord.getAD_OrgTo_ID()).getName(),
				TimeUtil.asDate(zonedDateTime) });
	}

	private void unmarkBillToDefaultContacts(final List<BPartnerContact> contacts)
	{
		for (final BPartnerContact contact : contacts)
		{
			final BPartnerContactType contactType = contact.getContactType();

			if (contactType == null)
			{
				continue;
			}
			contactType.setBillToDefault(false);
		}
	}

	private void unmarkPurchaseDefaultContacts(final List<BPartnerContact> contacts)
	{
		for (final BPartnerContact contact : contacts)
		{
			final BPartnerContactType contactType = contact.getContactType();

			if (contactType == null)
			{
				continue;
			}
			contactType.setPurchaseDefault(false);
		}
	}

	private void unmarkSalesDefaultContacts(final List<BPartnerContact> contacts)
	{
		for (final BPartnerContact contact : contacts)
		{
			final BPartnerContactType contactType = contact.getContactType();

			if (contactType == null)
			{
				continue;
			}
			contactType.setSalesDefault(false);
		}
	}

	private void unmarkShipToDefaultContacts(final List<BPartnerContact> contacts)
	{
		for (final BPartnerContact contact : contacts)
		{
			final BPartnerContactType contactType = contact.getContactType();

			if (contactType == null)
			{
				continue;
			}
			contactType.setShipToDefault(false);
		}
	}

	private IPricingResult calculateFlatrateTermPrice(@NonNull final I_C_Flatrate_Term newTerm)
	{
		final ZoneId timeZone = orgDAO.getTimeZone(OrgId.ofRepoId(newTerm.getAD_Org_ID()));

		return FlatrateTermPricing.builder()
				.termRelatedProductId(ProductId.ofRepoId(newTerm.getM_Product_ID()))
				.qty(newTerm.getPlannedQtyPerUnit())
				.term(newTerm)
				.priceDate(TimeUtil.asLocalDate(newTerm.getStartDate(), timeZone))
				.build()
				.computeOrThrowEx();
	}
}
