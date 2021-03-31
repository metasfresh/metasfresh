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

package de.metas.contracts.bpartner.repository;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.OrgMappingId;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.contracts.ConditionsId;
import de.metas.contracts.CreateFlatrateTermRequest;
import de.metas.contracts.FlatrateTerm;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.FlatrateTermPricing;
import de.metas.contracts.FlatrateTermStatus;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.bpartner.service.OrgChangeBPartnerComposite;
import de.metas.contracts.bpartner.service.OrgChangeHistoryId;
import de.metas.contracts.bpartner.service.OrgChangeRequest;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.location.ICountryDAO;
import de.metas.logging.LogManager;
import de.metas.order.DeliveryRule;
import de.metas.order.DeliveryViaRule;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.service.IPricingBL;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductAndCategoryId;
import de.metas.product.ProductId;
import de.metas.product.model.I_M_Product;
import de.metas.quantity.Quantity;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.model.PlainContextAware;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_OrgChange_History;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product_Category;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.copy;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class OrgChangeRepository
{
	private static final Logger logger = LogManager.getLogger(OrgChangeRepository.class);
	private final ILoggable loggable = Loggables.withLogger(logger, Level.INFO);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IPricingBL pricingBL = Services.get(IPricingBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IUserDAO userDAO = Services.get(IUserDAO.class);
	private final ICountryDAO countryDAO = Services.get(ICountryDAO.class);

	private final BPartnerCompositeRepository bPartnerCompositeRepo;
	private final OrgMappingRepository orgMappingRepo;

	public OrgChangeRepository(@NonNull final BPartnerCompositeRepository bPartnerCompositeRepo,
			@NonNull final OrgMappingRepository orgMappingRepo)
	{
		this.bPartnerCompositeRepo = bPartnerCompositeRepo;
		this.orgMappingRepo = orgMappingRepo;
	}

	public OrgChangeBPartnerComposite getByIdAndOrgChangeDate(@NonNull final BPartnerId bpartnerId,
			@NonNull final Instant orgChangeDate)
	{
		final OrgMappingId orgMappingId = orgMappingRepo.getCreateOrgMappingId(bpartnerId);
		final BPartnerComposite bPartnerComposite = bPartnerCompositeRepo.getById(bpartnerId);

		return OrgChangeBPartnerComposite.builder()
				.bPartnerComposite(bPartnerComposite)
				.bPartnerOrgMappingId(orgMappingId)
				.membershipSubscriptions(getMembershipSubscriptions(bpartnerId, orgChangeDate, bPartnerComposite.getOrgId()))
				.nonMembershipSubscriptions(getNonMembershipSubscriptions(bpartnerId, orgChangeDate, bPartnerComposite.getOrgId()))
				.build();
	}

	private Collection<? extends FlatrateTerm> getNonMembershipSubscriptions(@NonNull final BPartnerId bpartnerId,
			@NonNull final Instant orgChangeDate,
			@NonNull final OrgId orgId)
	{
		final Set<FlatrateTermId> flatrateTermIds = retrieveNonMembershipSubscriptions(bpartnerId, orgChangeDate, orgId);
		return flatrateTermIds.stream()
				.map(this::createFlatrateTerm)
				.collect(ImmutableList.toImmutableList());
	}

	private List<FlatrateTerm> getMembershipSubscriptions(final BPartnerId bpartnerId, final Instant orgChangeDate, final OrgId orgId)
	{
		final Set<FlatrateTermId> membershipFlatrateTermIds = retrieveMembershipSubscriptions(bpartnerId, orgChangeDate, orgId);
		return membershipFlatrateTermIds.stream()
				.map(this::createFlatrateTerm)
				.collect(ImmutableList.toImmutableList());
	}

	private FlatrateTerm createFlatrateTerm(@NonNull final FlatrateTermId flatrateTermId)
	{
		final I_C_Flatrate_Term term = flatrateDAO.getById(flatrateTermId);

		final OrgId orgId = OrgId.ofRepoId(term.getAD_Org_ID());

		final ProductId productId = ProductId.ofRepoId(term.getM_Product_ID());
		final I_C_UOM termUom = uomDAO.getById(CoalesceUtil.coalesce(UomId.ofRepoIdOrNull(term.getC_UOM_ID()), productBL.getStockUOMId(productId)));

		return FlatrateTerm.builder()
				.flatrateTermId(flatrateTermId)
				.orgId(orgId)
				.billPartnerID(BPartnerId.ofRepoId(term.getBill_BPartner_ID()))
				.billLocationId(BPartnerLocationId.ofRepoIdOrNull(term.getBill_BPartner_ID(), term.getBill_Location_ID()))
				.shipToBPartnerId(BPartnerId.ofRepoIdOrNull(term.getDropShip_BPartner_ID()))
				.shipToLocationId(BPartnerLocationId.ofRepoIdOrNull(term.getDropShip_BPartner_ID(), term.getDropShip_Location_ID()))
				.productId(productId)
				.flatrateConditionsId(ConditionsId.ofRepoId(term.getC_Flatrate_Conditions_ID()))
				.isSimulation(term.isSimulation())
				.status(FlatrateTermStatus.ofNullableCode(term.getContractStatus()))
				.userInChargeId(UserId.ofRepoIdOrNull(term.getAD_User_InCharge_ID()))
				.startDate(TimeUtil.asInstant(term.getStartDate()))
				.endDate(TimeUtil.asInstant(term.getEndDate()))
				.masterStartDate(TimeUtil.asInstant(term.getMasterStartDate()))
				.masterEndDate(TimeUtil.asInstant(term.getMasterEndDate()))
				.plannedQtyPerUnit(Quantity.of(term.getPlannedQtyPerUnit(), termUom))
				.deliveryRule(DeliveryRule.ofNullableCode(term.getDeliveryRule()))
				.deliveryViaRule(DeliveryViaRule.ofNullableCode(term.getDeliveryViaRule()))

				.build();
	}

	public boolean hasAnyMembershipProduct(@NonNull final OrgId orgId)
	{
		return createMembershipProductQuery(orgId).anyMatch();
	}

	private IQuery<I_M_Product> createMembershipProductQuery(@NonNull final OrgId orgId)
	{
		final IQuery<I_M_Product_Category> membershipProductCategoryQuery = getMembershipProductCategoryQuery();
		return queryBL.createQueryBuilder(I_M_Product.class)

				.addEqualsFilter(I_M_Product.COLUMNNAME_AD_Org_ID, orgId)
				.addInSubQueryFilter(I_M_Product.COLUMNNAME_M_Product_Category_ID,
									 I_M_Product_Category.COLUMNNAME_M_Product_Category_ID,
									 membershipProductCategoryQuery)
				.create();
	}

	@Nullable
	public I_M_Product getNewOrgProductForMappingOrNull(@NonNull final ProductId productId, @NonNull final OrgId orgId)
	{
		final I_M_Product productRecord = productDAO.getById(productId, I_M_Product.class);

		return queryBL.createQueryBuilder(I_M_Product.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Product.COLUMNNAME_AD_Org_ID, orgId)
				.addEqualsFilter(I_M_Product.COLUMNNAME_M_Product_Mapping_ID, productRecord.getM_Product_Mapping_ID())
				.orderByDescending(I_M_Product.COLUMNNAME_M_Product_ID)
				.create()
				.first();

	}

	private IQuery<I_M_Product_Category> getMembershipProductCategoryQuery()
	{
		return queryBL.createQueryBuilder(I_M_Product_Category.class)
				.addInArrayFilter(I_M_Product_Category.COLUMNNAME_Value, "Membership")
				.addEqualsFilter(I_M_Product_Category.COLUMNNAME_AD_Org_ID, 0)
				.create();

	}

	private IQuery<I_C_Flatrate_Term> createMembershipRunningSubscriptionQuery(
			@NonNull final BPartnerId bPartnerId,
			@NonNull final Instant orgChangeDate,
			@NonNull final OrgId orgId)
	{
		final IQuery<I_M_Product> membershipProductQuery = createMembershipProductQuery(orgId);

		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID, bPartnerId)
				.addInSubQueryFilter(I_C_Flatrate_Term.COLUMNNAME_M_Product_ID,
									 I_M_Product.COLUMNNAME_M_Product_ID,
									 membershipProductQuery)
				.addNotEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_ContractStatus, FlatrateTermStatus.Quit.getCode())
				.addNotEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_ContractStatus, FlatrateTermStatus.Voided.getCode())
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_EndDate, CompareQueryFilter.Operator.GREATER, orgChangeDate)
				.create();
	}

	public OrgChangeHistoryId createOrgChangeHistory(
			@NonNull final OrgChangeRequest orgChangeRequest,
			@NonNull final OrgMappingId orgMappingId,
			@NonNull final BPartnerComposite bPartnerToComposite)
	{
		final I_AD_OrgChange_History orgChangeHistory = newInstance(I_AD_OrgChange_History.class);

		orgChangeHistory.setAD_Org_From_ID(orgChangeRequest.getOrgFromId().getRepoId());
		orgChangeHistory.setAD_OrgTo_ID(orgChangeRequest.getOrgToId().getRepoId());
		orgChangeHistory.setAD_Org_Mapping_ID(orgMappingId.getRepoId());
		orgChangeHistory.setC_BPartner_From_ID(orgChangeRequest.getBpartnerId().getRepoId());
		orgChangeHistory.setC_BPartner_To_ID(bPartnerToComposite.getBpartner().getId().getRepoId());
		orgChangeHistory.setDate_OrgChange(TimeUtil.asTimestamp(orgChangeRequest.getStartDate()));

		save(orgChangeHistory);

		return OrgChangeHistoryId.ofRepoId(orgChangeHistory.getAD_OrgChange_History_ID());
	}

	public BPartnerId getOrCreateCounterpartBPartner(@NonNull final OrgChangeRequest orgChangeRequest, @NonNull final OrgMappingId orgMappingId)
	{
		BPartnerId bPartnerId = queryBL.createQueryBuilder(I_C_BPartner.class)
				.addEqualsFilter(I_C_BPartner.COLUMN_AD_Org_Mapping_ID, orgMappingId)
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_AD_Org_ID, orgChangeRequest.getOrgToId())
				.create()
				.firstId(BPartnerId::ofRepoIdOrNull);

		if (bPartnerId == null)
		{
			bPartnerId = cloneBPartner(orgChangeRequest, orgMappingId);
		}

		return bPartnerId;
	}

	private BPartnerId cloneBPartner(final @NonNull OrgChangeRequest orgChangeRequest, @NonNull final OrgMappingId orgMappingId)
	{
		final I_C_BPartner oldBpartner = bpartnerDAO.getById(orgChangeRequest.getBpartnerId());

		final I_C_BPartner newBPartner = copy()
				.setFrom(oldBpartner)
				.addTargetColumnNameToSkip(I_C_BPartner.COLUMNNAME_AD_Org_ID)
				.copyToNew(I_C_BPartner.class);

		newBPartner.setAD_Org_ID(orgChangeRequest.getOrgToId().getRepoId());
		newBPartner.setAD_Org_Mapping_ID(orgMappingId.getRepoId());
		save(newBPartner);

		return BPartnerId.ofRepoId(newBPartner.getC_BPartner_ID());
	}

	private Set<FlatrateTermId> retrieveMembershipSubscriptions(@NonNull final BPartnerId bpartnerId,
			@NonNull final Instant orgChangeDate,
			@NonNull final OrgId orgId)
	{
		return createMembershipRunningSubscriptionQuery(bpartnerId, orgChangeDate, orgId)
				.listIds(FlatrateTermId::ofRepoId);
	}

	private Set<FlatrateTermId> retrieveNonMembershipSubscriptions(@NonNull final BPartnerId bPartnerId,
			@NonNull final Instant orgChangeDate,
			@NonNull final OrgId orgId)
	{
		final IQuery<I_M_Product> membershipProductQuery = createMembershipProductQuery(orgId);

		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID, bPartnerId)
				.addNotInSubQueryFilter(I_C_Flatrate_Term.COLUMNNAME_M_Product_ID,
										I_M_Product.COLUMNNAME_M_Product_ID,
										membershipProductQuery)
				.addNotEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_ContractStatus, FlatrateTermStatus.Quit.getCode())
				.addNotEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_ContractStatus, FlatrateTermStatus.Voided.getCode())
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_EndDate, CompareQueryFilter.Operator.GREATER, orgChangeDate)
				.create()
				.listIds(FlatrateTermId::ofRepoId);
	}

	public void createMembershipSubscriptionTerm(final OrgChangeBPartnerComposite orgChangeBPartnerComposite,
			final BPartnerComposite destinationBPartnerComposite,
			final OrgChangeRequest orgChangeRequest)
	{
		final ProductId membershipProductId = orgChangeRequest.getMembershipProductId();

		if (membershipProductId == null)
		{
			loggable.addLog("No membership subscription will be created for partner {} because there was no membership product ID "
									+ "selected as paramteres", destinationBPartnerComposite.getBpartner());
			return;
		}

		final I_M_Product newOrgMembershipProduct = getNewOrgProductForMappingOrNull(membershipProductId, destinationBPartnerComposite.getOrgId());

		if (newOrgMembershipProduct == null)
		{
			loggable.addLog("No counterpart membership product for {} was found in org {}",
							membershipProductId,
							destinationBPartnerComposite.getOrgId());

			return;
		}
		final FlatrateTerm sourceMembershipSubscription = orgChangeBPartnerComposite.getMembershipSubscriptions().get(0);

		if (sourceMembershipSubscription == null)
		{
			loggable.addLog("No membership subscription will be created for partner {} because there is no running membership "
									+ "subscription in the initial partner {}",
							destinationBPartnerComposite.getBpartner(),
							orgChangeBPartnerComposite.getBPartnerComposite().getBpartner());

			return;
		}

		createTerm(destinationBPartnerComposite, orgChangeRequest, newOrgMembershipProduct, sourceMembershipSubscription);

	}

	public void createNonMembershipSubscriptionTerm(final OrgChangeBPartnerComposite orgChangeBPartnerComposite,
			final BPartnerComposite destinationBPartnerComposite,
			final OrgChangeRequest orgChangeRequest)
	{
		final List<FlatrateTerm> nonMembershipSubscriptions = orgChangeBPartnerComposite.getNonMembershipSubscriptions();

		if (Check.isEmpty(nonMembershipSubscriptions))
		{
			loggable.addLog("No subscription for non-membership products will be created for the partner {} "
									+ "because no such subscriptions were found in the initial partner {}.",
							destinationBPartnerComposite.getBpartner(),
							orgChangeBPartnerComposite.getBPartnerComposite().getBpartner());
			return;
		}

		for (final FlatrateTerm subscription : nonMembershipSubscriptions)
		{
			final I_M_Product counterpartProduct = retrieveCounterpartProductOrNull(
					subscription.getProductId(),
					orgChangeRequest.getOrgToId());

			if (counterpartProduct == null)
			{
				loggable.addLog("No counterpart product was found for the product {}. This means no counterpart subscription of {} will be"
										+ "created for the parnter {}",
								subscription.getProductId(),
								subscription,
								destinationBPartnerComposite.getBpartner());
			}
			else
			{
				createTerm(destinationBPartnerComposite,
						   orgChangeRequest,
						   counterpartProduct,
						   subscription);
			}
		}
	}

	private void createTerm(final BPartnerComposite destinationBPartnerComposite,
			@NonNull final OrgChangeRequest orgChangeRequest,
			@NonNull final I_M_Product newProduct,
			@NonNull final FlatrateTerm sourceSubscription)
	{
		final I_C_BPartner partner = bpartnerDAO.getById(destinationBPartnerComposite.getBpartner().getId());

		final BPartnerLocation billBPartnerLocation = destinationBPartnerComposite.extractBillToLocation().orElseGet(null);

		if (billBPartnerLocation == null)
		{
			loggable.addLog("No BillTo Location found in partner {} -> no flatrate term will be created", partner);
			return;
		}

		final BPartnerLocation shipBPartnerLocation = destinationBPartnerComposite.extractShipToLocation().orElseGet(null);

		if (shipBPartnerLocation == null)
		{
			loggable.addLog("No shipTo Location found in partner {} -> no flatrate term will be created", partner);
			return;
		}

		final I_AD_User user = retrieveCounterpartUserOrNull(sourceSubscription.getUserInChargeId(), destinationBPartnerComposite.getOrgId());

		final Timestamp startDate = TimeUtil.asTimestamp(orgChangeRequest.getStartDate());

		final CreateFlatrateTermRequest flatrateTermRequest = CreateFlatrateTermRequest.builder()
				.context(PlainContextAware.newWithThreadInheritedTrx(Env.getCtx()))
				.orgId(orgChangeRequest.getOrgToId())
				.bPartner(partner)
				.startDate(startDate)
				.isSimulation(false)
				.conditions(flatrateDAO.getConditionsById(sourceSubscription.getFlatrateConditionsId()))
				.productAndCategoryId(ProductAndCategoryId.of(newProduct.getM_Product_ID(), newProduct.getM_Product_Category_ID()))
				.userInCharge(user)
				.build();

		final I_C_Flatrate_Term term = flatrateBL.createTerm(flatrateTermRequest);

		final Quantity plannedQtyPerUnit = sourceSubscription.getPlannedQtyPerUnit();

		term.setPlannedQtyPerUnit(plannedQtyPerUnit == null ? BigDecimal.ZERO : plannedQtyPerUnit.toBigDecimal());
		term.setC_UOM_ID(plannedQtyPerUnit == null ? -1 : plannedQtyPerUnit.getUomId().getRepoId());

		term.setDropShip_Location_ID(shipBPartnerLocation.getId().getRepoId());

		term.setDeliveryRule(sourceSubscription.getDeliveryRule() == null ? null : sourceSubscription.getDeliveryRule().getCode());
		term.setDeliveryViaRule(sourceSubscription.getDeliveryViaRule() == null ? null : sourceSubscription.getDeliveryViaRule().getCode());

		final IEditablePricingContext initialContext = pricingBL
				.createInitialContext(orgChangeRequest.getOrgToId(),
									  ProductId.ofRepoId(newProduct.getM_Product_ID()),
									  destinationBPartnerComposite.getBpartner().getId(),
									  plannedQtyPerUnit,
									  SOTrx.SALES);

		initialContext.setPriceDate(TimeUtil.asLocalDate(orgChangeRequest.getStartDate()));

		final CountryId countryId = countryDAO.getCountryIdByCountryCode(billBPartnerLocation.getCountryCode());

		initialContext.setCountryId(countryId);

		final IPricingResult pricingResult = pricingBL.calculatePrice(initialContext);

		term.setPriceActual(pricingResult.getPriceStd());

		term.setM_PricingSystem_ID(pricingResult.getPricingSystemId() == null ? -1 : pricingResult.getPricingSystemId().getRepoId());
		term.setC_Currency_ID(pricingResult.getCurrencyRepoId());

		final IPricingResult flatratePrice = calculateFlatrateTermPrice(term);

		term.setC_TaxCategory_ID(TaxCategoryId.toRepoId(flatratePrice.getTaxCategoryId()));
		term.setIsTaxIncluded(flatratePrice.isTaxIncluded());

		saveRecord(term);

		flatrateBL.complete(term);
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

	@Nullable
	private I_AD_User retrieveCounterpartUserOrNull(
			@NonNull final UserId sourceUserId,
			@NonNull final OrgId orgId)
	{
		final I_AD_User sourceUserRecord = userDAO.getById(sourceUserId);

		return queryBL.createQueryBuilder(I_AD_User.class)
				.addEqualsFilter(I_AD_User.COLUMNNAME_AD_Org_Mapping_ID, sourceUserRecord.getAD_Org_Mapping_ID())
				.addEqualsFilter(I_AD_User.COLUMNNAME_AD_Org_ID, orgId)
				.orderByDescending(I_AD_User.COLUMNNAME_AD_User_ID)
				.create()
				.first(I_AD_User.class);
	}

	@Nullable
	private I_M_Product retrieveCounterpartProductOrNull(final ProductId sourceProductId, final OrgId orgId)
	{
		final I_M_Product sourceProductRecord = productDAO.getById(sourceProductId, de.metas.product.model.I_M_Product.class);

		final I_M_Product counterpartProduct = queryBL.createQueryBuilder(I_M_Product.class)
				.addEqualsFilter(I_M_Product.COLUMNNAME_M_Product_Mapping_ID, sourceProductRecord.getM_Product_Mapping_ID())
				.addEqualsFilter(I_M_Product.COLUMNNAME_AD_Org_ID, orgId)
				.orderByDescending(I_M_Product.COLUMNNAME_M_Product_ID)
				.create()
				.first(I_M_Product.class);

		if (counterpartProduct == null)
		{
			loggable.addLog("No counterpart product was found for {}.", sourceProductRecord);
		}
		else
		{
			loggable.addLog("The counterpart of the product {} is the product {} ", sourceProductRecord, counterpartProduct);
		}

		return counterpartProduct;
	}

	public I_AD_OrgChange_History getOrgChangeHistoryById(@NonNull final OrgChangeHistoryId orgChangeHistoryId)
	{
		return load(orgChangeHistoryId, I_AD_OrgChange_History.class);
	}

}
