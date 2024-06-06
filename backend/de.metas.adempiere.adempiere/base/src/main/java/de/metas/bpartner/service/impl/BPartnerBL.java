package de.metas.bpartner.service.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.bpartner.name.NameAndGreeting;
import de.metas.bpartner.name.strategy.BPartnerNameAndGreetingStrategies;
import de.metas.bpartner.name.strategy.BPartnerNameAndGreetingStrategyId;
import de.metas.bpartner.name.strategy.ComputeNameAndGreetingRequest;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.bpartner.service.BPartnerPrintFormatMap;
import de.metas.bpartner.service.IBPGroupDAO;
import de.metas.bpartner.service.IBPartnerAware;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerBL.RetrieveContactRequest.ContactType;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.greeting.GreetingId;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.Language;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.location.ILocationBL;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.location.impl.AddressBuilder;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.user.User;
import de.metas.user.UserId;
import de.metas.user.UserRepository;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import static de.metas.bpartner.service.IBPartnerDAO.BPartnerLocationQuery.Type.BILL_TO_DEFAULT;
import static de.metas.bpartner.service.IBPartnerDAO.BPartnerLocationQuery.Type.SHIP_TO_DEFAULT;

@Service
public class BPartnerBL implements IBPartnerBL
{
	/* package */static final String SYSCONFIG_C_BPartner_SOTrx_AllowConsolidateInOut_Override = "C_BPartner.SOTrx_AllowConsolidateInOut_Override";
	private static final AdMessageKey MSG_SALES_REP_EQUALS_BPARTNER = AdMessageKey.of("SALES_REP_EQUALS_BPARTNER");
	private static final Logger logger = LogManager.getLogger(IBPartnerBL.class);

	private final ILocationDAO locationDAO;
	private final IBPartnerDAO bpartnersRepo;
	private final UserRepository userRepository;
	private final IBPGroupDAO bpGroupDAO;

	public BPartnerBL()
	{
		this.bpartnersRepo = Services.get(IBPartnerDAO.class);
		this.userRepository = new UserRepository();
		this.bpGroupDAO = Services.get(IBPGroupDAO.class);
		this.locationDAO = Services.get(ILocationDAO.class);
	}

	public BPartnerBL(@NonNull final UserRepository userRepository)
	{
		this.bpartnersRepo = Services.get(IBPartnerDAO.class);
		this.userRepository = userRepository;
		this.bpGroupDAO = Services.get(IBPGroupDAO.class);
		this.locationDAO = Services.get(ILocationDAO.class);
	}

	@Override
	public I_C_BPartner getById(@NonNull final BPartnerId bpartnerId)
	{
		return bpartnersRepo.getById(bpartnerId);
	}

	@Override
	public String getBPartnerValue(final BPartnerId bpartnerId)
	{
		return toBPartnerDisplayName(bpartnerId, I_C_BPartner::getValue);
	}

	@Override
	public String getBPartnerName(@Nullable final BPartnerId bpartnerId)
	{
		return toBPartnerDisplayName(bpartnerId, I_C_BPartner::getName);
	}

	@Override
	public String getBPartnerValueAndName(final BPartnerId bpartnerId)
	{
		return toBPartnerDisplayName(bpartnerId, bpartner -> bpartner.getValue() + "_" + bpartner.getName());
	}

	private String toBPartnerDisplayName(@Nullable final BPartnerId bpartnerId, final Function<I_C_BPartner, String> toString)
	{
		if (bpartnerId == null)
		{
			return "?";
		}

		final I_C_BPartner bpartner = getById(bpartnerId);
		if (bpartner == null)
		{
			return unknownBPName(bpartnerId);
		}

		return toString.apply(bpartner);
	}

	@NonNull
	private static String unknownBPName(final @NonNull BPartnerId bpartnerId)
	{
		return "<" + bpartnerId.getRepoId() + ">";
	}

	@Override
	public Map<BPartnerId, String> getBPartnerNames(@NonNull final Set<BPartnerId> bpartnerIds)
	{
		if (bpartnerIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableMap<BPartnerId, I_C_BPartner> bpartners = Maps.uniqueIndex(bpartnersRepo.getByIds(bpartnerIds), bpartner -> BPartnerId.ofRepoId(bpartner.getC_BPartner_ID()));

		final ImmutableMap.Builder<BPartnerId, String> result = ImmutableMap.builder();
		for (final BPartnerId bpartnerId : bpartnerIds)
		{
			final I_C_BPartner bpartner = bpartners.get(bpartnerId);
			String name = bpartner != null ? bpartner.getName() : unknownBPName(bpartnerId);
			result.put(bpartnerId, name);
		}

		return result.build();
	}

	@Override
	public String mkFullAddress(
			@NonNull final org.compiere.model.I_C_BPartner bpartner,
			@Nullable final I_C_BPartner_Location bpLocation,
			@Nullable final LocationId locationId,
			@Nullable final I_AD_User bpContact)
	{
		final AddressBuilder addressBuilder = AddressBuilder.builder()
				.orgId(OrgId.ofRepoId(bpartner.getAD_Org_ID()))
				.adLanguage(bpartner.getAD_Language())
				.build();
		return addressBuilder.buildBPartnerFullAddressString(bpartner, bpLocation, locationId, bpContact);
	}

	@Override
	public I_AD_User retrieveShipContact(final Properties ctx, final int bPartnerId, final String trxName)
	{
		final I_C_BPartner_Location loc = bpartnersRepo.retrieveShipToLocation(ctx, bPartnerId, trxName);

		final int bPartnerLocationId = loc == null ? -1 : loc.getC_BPartner_Location_ID();
		return retrieveUserForLoc(ctx, bPartnerId, bPartnerLocationId, trxName);
	}

	@Override
	public I_AD_User retrieveShipContact(final org.compiere.model.I_C_BPartner bpartner)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(bpartner);
		final int bPartnerId = bpartner.getC_BPartner_ID();
		final String trxName = InterfaceWrapperHelper.getTrxName(bpartner);
		final org.compiere.model.I_AD_User userPO = retrieveShipContact(ctx, bPartnerId, trxName);
		return InterfaceWrapperHelper.create(userPO, I_AD_User.class);
	}

	@Override
	public I_AD_User createDraftContact(final org.compiere.model.I_C_BPartner bpartner)
	{
		final I_AD_User contact = InterfaceWrapperHelper.newInstance(I_AD_User.class, bpartner);
		contact.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		contact.setName(bpartner.getName());
		return contact;
	}

	@Nullable
	@Override
	public User retrieveContactOrNull(@NonNull final RetrieveContactRequest request)
	{
		final List<I_AD_User> contactRecords = bpartnersRepo.retrieveContacts(
				Env.getCtx(),
				request.getBpartnerId().getRepoId(),
				ITrx.TRXNAME_None);

		final boolean ifNotFoundReturnNull = RetrieveContactRequest.IfNotFound.RETURN_NULL.equals(request.getIfNotFound());
		final boolean onlyActiveContacts = request.isOnlyActive();

		// we will collect the candidates for our return value into these variables
		final Set<User> contactsAtLocation = new TreeSet<>(request.getComparator());
		final Set<User> contactsAtOtherLocations = new TreeSet<>(request.getComparator());
		User defaultContactOfType = null;
		User defaultContact = null;

		for (final I_AD_User contactRecord : contactRecords)
		{
			if (onlyActiveContacts && !contactRecord.isActive())
			{
				continue;
			}

			final User contact = userRepository.ofRecord(contactRecord);
			if (!request.getFilter().test(contact))
			{
				continue;
			}

			final boolean contactHasMatchingBPartnerLocation = request.getBPartnerLocationId() != null
					&& contactRecord.getC_BPartner_Location_ID() == request.getBPartnerLocationId().getRepoId();
			if (contactHasMatchingBPartnerLocation)
			{
				contactsAtLocation.add(contact);
			}
			else
			{
				contactsAtOtherLocations.add(contact);
			}

			if (contactRecord.isDefaultContact())
			{
				defaultContact = contact;
			}
			if (recordMatchesType(contactRecord, request.getContactType()))
			{
				defaultContactOfType = contact;

				if (ifNotFoundReturnNull)
				{
					return defaultContactOfType;
				}
			}
		}

		if (ifNotFoundReturnNull)
		{
			// no user of the given type was found
			return null;
		}

		if (!contactsAtLocation.isEmpty())
		{
			return findBestMatch(contactsAtLocation, defaultContactOfType, defaultContact);
		}
		else if (!contactsAtOtherLocations.isEmpty())
		{
			return findBestMatch(contactsAtOtherLocations, defaultContactOfType, defaultContact);
		}
		return null;
	}

	private boolean recordMatchesType(@NonNull final I_AD_User contactRecord, @Nullable final ContactType contactType)
	{
		if (contactType == null)
		{
			return true;
		}
		switch (contactType)
		{
			case BILL_TO_DEFAULT:
				return contactRecord.isBillToContact_Default();
			case SALES_DEFAULT:
				return contactRecord.isSalesContact_Default();
			case SHIP_TO_DEFAULT:
				return contactRecord.isShipToContact_Default();
			case SUBJECT_MATTER:
				return contactRecord.isSubjectMatterContact();
			default:
				throw new AdempiereException("Unsupporded contactType=" + contactType);
		}
	}

	private User findBestMatch(
			@NonNull final Set<User> contacts,
			@Nullable final User defaultContactOfType,
			@Nullable final User defaultContact)
	{
		Check.assumeNotEmpty(contacts, "Parameter contacts needs to be non-empty");

		if (defaultContactOfType != null && contacts.contains(defaultContactOfType))
		{
			return defaultContactOfType;
		}
		else if (defaultContact != null && contacts.contains(defaultContact))
		{
			return defaultContact;
		}

		return contacts.iterator().next();
	}

	@Override
	public I_AD_User retrieveUserForLoc(final org.compiere.model.I_C_BPartner_Location loc)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(loc);
		final int bPartnerId = loc.getC_BPartner_ID();
		final int bPartnerLocationId = loc.getC_BPartner_Location_ID();
		final String trxName = InterfaceWrapperHelper.getTrxName(loc);

		return retrieveUserForLoc(ctx, bPartnerId, bPartnerLocationId, trxName);
	}

	@Nullable
	private I_AD_User retrieveUserForLoc(final Properties ctx, final int bPartnerId, final int bPartnerLocationId, final String trxName)
	{
		final List<I_AD_User> users = bpartnersRepo.retrieveContacts(ctx, bPartnerId, trxName);

		if (bPartnerLocationId > 0)
		{
			for (final I_AD_User user : users)
			{
				if (user.getC_BPartner_Location_ID() == bPartnerLocationId)
				{
					return user;
				}
			}
		}

		return getDefaultBPContact(users);
	}

	/**
	 * Selects the default contact from a list of BPartner users. Returns first user with IsDefaultContact=Y found or first contact.
	 */
	@Nullable
	private I_AD_User getDefaultBPContact(final List<I_AD_User> users)
	{
		if (users == null || users.isEmpty())
		{
			return null;
		}

		for (final I_AD_User user : users)
		{
			if (user.isDefaultContact())
			{
				return user;
			}
		}
		return users.get(0);
	}

	@Override
	public void updateAllAddresses(@NonNull final I_C_BPartner bpartner)
	{
		final List<I_C_BPartner_Location> bpLocations = bpartnersRepo.retrieveBPartnerLocations(bpartner);
		for (final I_C_BPartner_Location bpLocation : bpLocations)
		{
			updateAddressNoSave(bpLocation, bpartner);
			bpartnersRepo.save(bpLocation);
		}
	}

	@Override
	public void updateMemo(final @NonNull BPartnerId bpartnerId, @NonNull final String memo)
	{
		final I_C_BPartner bpartner = bpartnersRepo.getById(bpartnerId);
		bpartner.setMemo(memo);
		bpartnersRepo.save(bpartner);
	}

	@Override
	public void setAddress(@NonNull final I_C_BPartner_Location bpLocation)
	{
		final I_C_BPartner bpartner = bpartnersRepo.getById(bpLocation.getC_BPartner_ID());
		updateAddressNoSave(bpLocation, bpartner);
	}

	private void updateAddressNoSave(final I_C_BPartner_Location bpLocation, final I_C_BPartner bpartner)
	{
		final ILocationBL locationBL = Services.get(ILocationBL.class);

		final String address = locationBL.mkAddress(
				locationBL.getRecordById(LocationId.ofRepoId(bpLocation.getC_Location_ID())),
				bpartner,
				"",  // bPartnerBlock
				"" // userBlock
		);

		bpLocation.setAddress(address);
	}

	@Override
	public boolean isAllowConsolidateInOutEffective(@NonNull final BPartnerId bpartnerId, @NonNull final SOTrx soTrx)
	{
		final I_C_BPartner bpartner = getById(bpartnerId);
		return isAllowConsolidateInOutEffective(bpartner, soTrx);
	}

	@Override
	public boolean isAllowConsolidateInOutEffective(
			@NonNull final org.compiere.model.I_C_BPartner partner,
			@NonNull final SOTrx soTrx)
	{
		final I_C_BPartner partnerToUse = InterfaceWrapperHelper.create(partner, de.metas.interfaces.I_C_BPartner.class);
		final boolean partnerAllowConsolidateInOut = partnerToUse.isAllowConsolidateInOut();
		if (partnerAllowConsolidateInOut)
		{
			return true;
		}

		//
		// 07973: Attempt to override SO shipment consolidation if configured
		if (soTrx.isSales())
		{
			final boolean allowConsolidateInOutOverrideDefault = false; // default=false (preserve existing logic)
			return Services.get(ISysConfigBL.class).getBooleanValue(
					SYSCONFIG_C_BPartner_SOTrx_AllowConsolidateInOut_Override,
					allowConsolidateInOutOverrideDefault);
		}
		else
		{
			return false;
		}
	}

	@Override
	public Optional<Language> getLanguage(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_BPartner bpartner = bpartnersRepo.getById(bpartnerId);
		return bpartner != null
				? getLanguage(bpartner)
				: Optional.empty();
	}

	@Override
	public Optional<BPartnerId> getBPartnerIdForModel(@Nullable final Object model)
	{
		final IBPartnerAware bpartnerAware = InterfaceWrapperHelper.asColumnReferenceAwareOrNull(model, IBPartnerAware.class);
		return bpartnerAware != null
				? BPartnerId.optionalOfRepoId(bpartnerAware.getC_BPartner_ID())
				: Optional.empty();
	}

	@Override
	public Optional<Language> getLanguageForModel(@Nullable final Object model)
	{
		// 09527 get the most suitable language:
		return getBPartnerIdForModel(model)
				.map(bpartnersRepo::getById)
				.flatMap(this::getLanguage);
	}

	@Override
	public Optional<Language> getLanguage(@NonNull final I_C_BPartner bpartner)
	{
		final String adLanguage = bpartner.getAD_Language();
		return Check.isNotBlank(adLanguage)
				? Optional.ofNullable(Language.getLanguage(adLanguage))
				: Optional.empty();
	}

	@Override
	public int getDiscountSchemaId(@NonNull final BPartnerId bpartnerId, final SOTrx soTrx)
	{
		final I_C_BPartner bpartner = getById(bpartnerId);

		return getDiscountSchemaId(bpartner, soTrx);
	}

	@Override
	public int getDiscountSchemaId(@NonNull final I_C_BPartner bpartner, final SOTrx soTrx)
	{
		{
			final int discountSchemaId;
			if (soTrx.isSales())
			{
				discountSchemaId = bpartner.getM_DiscountSchema_ID();
			}
			else
			{
				discountSchemaId = bpartner.getPO_DiscountSchema_ID();
			}
			if (discountSchemaId > 0)
			{
				return discountSchemaId; // we are done
			}
		}

		// didn't get the schema yet; now we try to get the discount schema from the C_BPartner's C_BP_Group
		final I_C_BP_Group bpGroup = bpartner.getC_BP_Group();
		if (bpGroup != null && bpGroup.getC_BP_Group_ID() > 0)
		{
			final int groupDiscountSchemaId;
			if (soTrx.isSales())
			{
				groupDiscountSchemaId = bpGroup.getM_DiscountSchema_ID();
			}
			else
			{
				groupDiscountSchemaId = bpGroup.getPO_DiscountSchema_ID();
			}
			if (groupDiscountSchemaId > 0)
			{
				return groupDiscountSchemaId; // we are done
			}
		}

		return -1;
	}

	@Override
	public String getAddressStringByBPartnerLocationId(final BPartnerLocationId bpartnerLocationId)
	{
		if (bpartnerLocationId == null)
		{
			return "?";
		}

		final I_C_BPartner_Location bpLocation = bpartnersRepo.getBPartnerLocationByIdEvenInactive(bpartnerLocationId);
		return bpLocation != null ? bpLocation.getAddress() : "<" + bpartnerLocationId.getRepoId() + ">";
	}

	@Override
	@Nullable
	public UserId getSalesRepIdOrNull(final BPartnerId bpartnerId)
	{
		final I_C_BPartner bpartnerRecord = getById(bpartnerId);
		if (InterfaceWrapperHelper.isNull(bpartnerRecord, I_C_BPartner.COLUMNNAME_SalesRep_ID))
		{
			return null;
		}

		final int salesRepRecordId = bpartnerRecord.getSalesRep_ID();
		return UserId.ofRepoIdOrNull(salesRepRecordId);
	}

	@Override
	@Nullable
	public BPartnerId getBPartnerSalesRepId(final BPartnerId bPartnerId)
	{
		final int salesRepRecordId = getById(bPartnerId).getC_BPartner_SalesRep_ID();

		return BPartnerId.ofRepoIdOrNull(salesRepRecordId);
	}

	@Override
	public void setBPartnerSalesRepIdOutOfTrx(@NonNull final BPartnerId bPartnerId, @Nullable final BPartnerId salesRepBPartnerId)
	{
		if (bPartnerId.equals(salesRepBPartnerId))
		{
			return;
		}
		final I_C_BPartner bPartnerRecord = bpartnersRepo.getByIdOutOfTrx(bPartnerId);

		final int salesRepBPartnerIdInt = salesRepBPartnerId != null ? salesRepBPartnerId.getRepoId() : -1;

		bPartnerRecord.setC_BPartner_SalesRep_ID(salesRepBPartnerIdInt);

		bpartnersRepo.saveOutOfTrx(bPartnerRecord);
	}

	@Override
	@Nullable
	public UserId setSalesRepId(
			@NonNull final BPartnerId bpartnerId,
			@Nullable final UserId salesRepId)
	{
		final I_C_BPartner bpartnerRecord = bpartnersRepo.getById(bpartnerId);

		final UserId oldSalesRepId = UserId.ofRepoIdOrNull(bpartnerRecord.getSalesRep_ID());
		bpartnerRecord.setSalesRep_ID(UserId.toRepoId(salesRepId));
		bpartnersRepo.save(bpartnerRecord);

		return oldSalesRepId;
	}

	@Override
	public CountryId getCountryId(@NonNull final BPartnerLocationAndCaptureId bpartnerLocationAndCaptureId)
	{
		final LocationId locationId = getLocationId(bpartnerLocationAndCaptureId);
		return locationDAO.getCountryIdByLocationId(locationId);
	}

	@Override
	public CountryId getCountryId(@NonNull final BPartnerInfo bpartnerInfo)
	{
		if (bpartnerInfo.getLocationId() != null)
		{
			return locationDAO.getCountryIdByLocationId(bpartnerInfo.getLocationId());
		}
		else
		{
			return bpartnersRepo.getCountryId(bpartnerInfo.getBpartnerLocationId());
		}
	}

	@Override
	public LocationId getLocationId(@NonNull final BPartnerLocationAndCaptureId bpartnerLocationAndCaptureId)
	{
		if (bpartnerLocationAndCaptureId.getLocationCaptureId() != null)
		{
			return bpartnerLocationAndCaptureId.getLocationCaptureId();
		}
		else
		{
			return bpartnersRepo.getLocationId(bpartnerLocationAndCaptureId.getBpartnerLocationId());
		}
	}

	@Override
	public int getFreightCostIdByBPartnerId(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_BPartner bpartner = bpartnersRepo.getById(bpartnerId);
		int freightCostId = bpartner.getM_FreightCost_ID();
		if (freightCostId > 0)
		{
			return freightCostId;
		}

		final IBPGroupDAO bpGroupsRepo = Services.get(IBPGroupDAO.class);
		final I_C_BP_Group bpGroup = bpGroupsRepo.getByBPartnerId(bpartnerId);
		freightCostId = bpGroup.getM_FreightCost_ID();
		return freightCostId;
	}

	@Override
	public ShipmentAllocationBestBeforePolicy getBestBeforePolicy(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_BPartner bpartner = getById(bpartnerId);
		final ShipmentAllocationBestBeforePolicy bestBeforePolicy = ShipmentAllocationBestBeforePolicy.ofNullableCode(bpartner.getShipmentAllocation_BestBefore_Policy());
		return bestBeforePolicy != null ? bestBeforePolicy : ShipmentAllocationBestBeforePolicy.Expiring_First;
	}

	@Override
	public Optional<PaymentTermId> getPaymentTermIdForBPartner(@NonNull final BPartnerId bpartnerId, @NonNull final SOTrx soTrx)
	{
		final I_C_BPartner bpartner = bpartnersRepo.getById(bpartnerId);

		return soTrx.isSales()
				? PaymentTermId.optionalOfRepoId(bpartner.getC_PaymentTerm_ID())
				: PaymentTermId.optionalOfRepoId(bpartner.getPO_PaymentTerm_ID());
	}

	@Override
	public Optional<PaymentRule> getPaymentRuleForBPartner(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_BPartner bpartner = bpartnersRepo.getById(bpartnerId);
		final Optional<PaymentRule> bpartnerPaymentRule = PaymentRule.optionalOfCode(bpartner.getPaymentRule());
		if (bpartnerPaymentRule.isPresent())
		{
			return bpartnerPaymentRule;
		}
		//
		// No payment rule in BP. Fallback to group.
		final BPGroupId bpGroupId = BPGroupId.ofRepoId(bpartner.getC_BP_Group_ID());
		final IBPGroupDAO bpGroupDAO = Services.get(IBPGroupDAO.class);
		final I_C_BP_Group bpGroup = bpGroupDAO.getById(bpGroupId);
		return PaymentRule.optionalOfCode(bpGroup.getPaymentRule());
	}

	@Override
	public BPartnerPrintFormatMap getPrintFormats(final @NonNull BPartnerId bpartnerId)
	{
		return bpartnersRepo.getPrintFormats(bpartnerId);
	}

	@Override
	public boolean isSalesRep(@NonNull final BPartnerId bpartnerId)
	{
		return getById(bpartnerId).isSalesRep();
	}

	@Override
	public void validateSalesRep(@NonNull final BPartnerId bPartnerId, @Nullable final BPartnerId salesRepId)
	{
		if (bPartnerId.equals(salesRepId))
		{
			throw new AdempiereException(MSG_SALES_REP_EQUALS_BPARTNER);
		}
	}

	@Override
	public void updateNameAndGreetingFromContacts(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_BPartner bpartner = bpartnersRepo.getByIdInTrx(bpartnerId);
		if (bpartner.isCompany())
		{
			return;
		}

		final BPGroupId bpGroupId = BPGroupId.ofRepoId(bpartner.getC_BP_Group_ID());
		final BPartnerNameAndGreetingStrategyId strategyId = bpGroupDAO.getBPartnerNameAndGreetingStrategyId(bpGroupId);

		final BPartnerNameAndGreetingStrategies partnerNameAndGreetingStrategies = SpringContextHolder.instance.getBean(BPartnerNameAndGreetingStrategies.class);

		final NameAndGreeting nameAndGreeting = partnerNameAndGreetingStrategies.compute(
						strategyId,
						ComputeNameAndGreetingRequest.builder()
								.adLanguage(bpartner.getAD_Language())
								.contacts(bpartnersRepo.retrieveContacts(bpartner)
										.stream()
										.map(contact -> ComputeNameAndGreetingRequest.Contact.builder()
												.greetingId(GreetingId.ofRepoIdOrNull(contact.getC_Greeting_ID()))
												.firstName(contact.getFirstname())
												.lastName(contact.getLastname())
												.seqNo(contact.getAD_User_ID()) // TODO: introduce AD_User.SeqNo
												.isDefaultContact(contact.isDefaultContact())
												.isMembershipContact(contact.isMembershipContact())
												.build())
										.collect(ImmutableList.toImmutableList()))
								.build())
				.orElse(null);

		if (nameAndGreeting == null)
		{
			return;
		}

		bpartner.setName(nameAndGreeting.getName());
		bpartner.setC_Greeting_ID(GreetingId.toRepoId(nameAndGreeting.getGreetingId()));
		bpartnersRepo.save(bpartner);
	}

	@Override
	public void setPreviousIdIfPossible(@NonNull final I_C_BPartner_Location location)
	{
		final List<I_C_BPartner_Location> locations = bpartnersRepo.retrieveBPartnerLocations(BPartnerId.ofRepoId(location.getC_BPartner_ID()));
		final Set<Integer> oldLocations = locations.stream()
				.map(I_C_BPartner_Location::getPrevious_ID)
				.collect(Collectors.toSet());
		final List<I_C_BPartner_Location> locationCandidates = locations.stream()
				.filter(loc -> !oldLocations.contains(loc.getC_BPartner_Location_ID()) && loc.getC_BPartner_Location_ID() != location.getC_BPartner_Location_ID())
				.collect(Collectors.toList());
		if (locationCandidates.size() == 1)
		{
			location.setPrevious_ID(locationCandidates.stream().findFirst().get().getC_BPartner_Location_ID());
			bpartnersRepo.save(location);
		}
	}

	@Override
	public void updateFromPreviousLocation(final I_C_BPartner_Location bpLocation)
	{
		updateFromPreviousLocationNoSave(bpLocation);
		bpartnersRepo.save(bpLocation);
	}

	@Override
	public void updateFromPreviousLocationNoSave(final I_C_BPartner_Location bpLocation)
	{
		final int previousId = bpLocation.getPrevious_ID();
		if (previousId <= 0)
		{
			return;
		}

		final Timestamp validFrom = bpLocation.getValidFrom();
		if (validFrom == null)
		{
			return;
		}

		final I_C_BPartner_Location previousLocation = bpartnersRepo.getBPartnerLocationByIdEvenInactive(BPartnerLocationId.ofRepoId(bpLocation.getC_BPartner_ID(), previousId));
		if (previousLocation == null)
		{
			return;
		}
		// Don't update the defaults if the current location is still valid.
		if (validFrom.before(Env.getDate()))
		{
			bpLocation.setIsBillToDefault(previousLocation.isBillToDefault());
			bpLocation.setIsShipToDefault(previousLocation.isShipToDefault());

			previousLocation.setIsBillToDefault(false);
			previousLocation.setIsShipToDefault(false);
			bpartnersRepo.save(previousLocation);
		}

		bpLocation.setIsBillTo(previousLocation.isBillTo());
		bpLocation.setIsShipTo(previousLocation.isShipTo());
	}

	@Override
	public I_C_BPartner_Location extractShipToLocation(@NonNull final org.compiere.model.I_C_BPartner bp)
	{
		I_C_BPartner_Location bPartnerLocation = null;

		final List<I_C_BPartner_Location> locations = bpartnersRepo.retrieveBPartnerLocations(bp);

		// Set Locations
		final List<I_C_BPartner_Location> shipLocations = new ArrayList<>();
		boolean foundLoc = false;
		for (final I_C_BPartner_Location loc : locations)
		{
			if (loc.isShipTo() && loc.isActive())
			{
				shipLocations.add(loc);
			}

			final org.compiere.model.I_C_BPartner_Location bpLoc = InterfaceWrapperHelper.create(loc, org.compiere.model.I_C_BPartner_Location.class);
			if (bpLoc.isShipToDefault())
			{
				bPartnerLocation = bpLoc;
				foundLoc = true;
			}
		}

		// set first ship location if is not set
		if (!foundLoc)
		{
			if (!shipLocations.isEmpty())
			{
				bPartnerLocation = shipLocations.get(0);
			}
			//No longer setting any location when no shipping location exists for the bpartner
		}

		if (!foundLoc)
		{
			logger.error("MOrder.setBPartner - Has no Ship To Address: {}", bp);
		}

		return bPartnerLocation;
	}

	@NonNull
	@Override
	public Optional<String> getVATTaxId(@NonNull final BPartnerLocationId bpartnerLocationId)
	{
		final I_C_BPartner_Location bpartnerLocation = bpartnersRepo.getBPartnerLocationByIdEvenInactive(bpartnerLocationId);
		if (bpartnerLocation != null && Check.isNotBlank(bpartnerLocation.getVATaxID()))
		{
			return Optional.of(bpartnerLocation.getVATaxID());
		}

		final I_C_BPartner bPartner = getById(bpartnerLocationId.getBpartnerId());
		if (bPartner != null && Check.isNotBlank(bPartner.getVATaxID()))
		{
			return Optional.of(bPartner.getVATaxID());
		}

		return Optional.empty();
	}

	@NonNull
	@Override
	public Optional<UserId> getDefaultDunningContact(@NonNull final BPartnerId bPartnerId)
	{
		return userRepository.getDefaultDunningContact(bPartnerId);
	}

	@NonNull
	@Override
	public Optional<I_C_BPartner_Location> retrieveShipToDefaultLocation(@NonNull final BPartnerId bPartnerId)
	{
		return retrieveBPartnerLocation(bPartnerId, SHIP_TO_DEFAULT);
	}

	@NonNull
	@Override
	public Optional<I_C_BPartner_Location> retrieveBillToDefaultLocation(@NonNull final BPartnerId bPartnerId)
	{
		return retrieveBPartnerLocation(bPartnerId, BILL_TO_DEFAULT);

	}

	@NonNull
	private Optional<I_C_BPartner_Location> retrieveBPartnerLocation(
			@NonNull final BPartnerId bPartnerId,
			@NonNull final IBPartnerDAO.BPartnerLocationQuery.Type type)
	{
		return Optional.ofNullable(bpartnersRepo.retrieveBPartnerLocation(IBPartnerDAO.BPartnerLocationQuery
																				  .builder()
																				  .type(type)
																				  .bpartnerId(bPartnerId)
																				  .build()));
	}
}
