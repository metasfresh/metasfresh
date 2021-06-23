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
import de.metas.bpartner.service.CloneBPartnerRequest;
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
import de.metas.contracts.bpartner.service.OrgChangeRequest;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.lang.SOTrx;
import de.metas.location.ICountryDAO;
import de.metas.logging.LogManager;
import de.metas.order.DeliveryRule;
import de.metas.order.DeliveryViaRule;
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
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductAndCategoryId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.product.model.I_M_Product;
import de.metas.quantity.Quantity;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.model.PlainContextAware;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
	private final GroupTemplateRepository groupTemplateRepo;

	public OrgChangeRepository(@NonNull final BPartnerCompositeRepository bPartnerCompositeRepo,
			@NonNull final OrgMappingRepository orgMappingRepo,
			@NonNull final GroupTemplateRepository groupTemplateRepo)
	{
		this.bPartnerCompositeRepo = bPartnerCompositeRepo;
		this.orgMappingRepo = orgMappingRepo;
		this.groupTemplateRepo = groupTemplateRepo;
	}

	public OrgChangeBPartnerComposite getByIdAndOrgChangeDate(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final Instant orgChangeDate)
	{
		final OrgMappingId orgMappingId = orgMappingRepo.getCreateOrgMappingId(bpartnerId);
		final BPartnerComposite bPartnerComposite = bPartnerCompositeRepo.getById(bpartnerId);

		return OrgChangeBPartnerComposite.builder()
				.bPartnerComposite(bPartnerComposite)
				.bPartnerOrgMappingId(orgMappingId)
				.membershipSubscriptions(getMembershipSubscriptions(bpartnerId, orgChangeDate, bPartnerComposite.getOrgId()))
				.allRunningSubscriptions(getAllRunningSubscriptions(bpartnerId, orgChangeDate, bPartnerComposite.getOrgId()))
				.groupCategoryId(getGroupCategoryId(bpartnerId, orgChangeDate, bPartnerComposite.getOrgId()).orElse(null))
				.build();
	}

	private Optional<GroupCategoryId> getGroupCategoryId(@NonNull final BPartnerId bpartnerId, final Instant orgChangeDate, final OrgId orgId)
	{
		return queryMembershipRunningSubscription(bpartnerId, orgChangeDate, orgId)
				.stream()
				.map(term -> productDAO.getById(term.getM_Product_ID()))
				.filter(product -> product.getC_CompensationGroup_Schema_Category_ID() > 0)
				.map(product -> GroupCategoryId.ofRepoId(product.getC_CompensationGroup_Schema_Category_ID()))
				.findFirst();

	}

	private Collection<FlatrateTerm> getAllRunningSubscriptions(@NonNull final BPartnerId bpartnerId,
			@NonNull final Instant orgChangeDate,
			@NonNull final OrgId orgId)
	{
		final Set<FlatrateTermId> flatrateTermIds = retrieveAllRunningSubscriptionIds(bpartnerId, orgChangeDate, orgId);
		return flatrateTermIds.stream()
				.map(this::createFlatrateTerm)
				.collect(ImmutableList.toImmutableList());
	}

	private List<FlatrateTerm> getMembershipSubscriptions(final BPartnerId bpartnerId, final Instant orgChangeDate, final OrgId orgId)
	{
		final Set<FlatrateTermId> membershipFlatrateTermIds = retrieveMembershipSubscriptionIds(bpartnerId, orgChangeDate, orgId);
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
		return queryMembershipProducts(orgId).anyMatch();
	}

	private IQuery<I_M_Product> queryMembershipProducts(@NonNull final OrgId orgId)
	{
		return queryBL.createQueryBuilder(I_M_Product.class)
				.addEqualsFilter(I_M_Product.COLUMNNAME_AD_Org_ID, orgId)
				.addNotEqualsFilter(I_M_Product.COLUMNNAME_C_CompensationGroup_Schema_ID, null)
				.create();
	}

	private IQuery<I_C_Flatrate_Term> queryMembershipRunningSubscription(
			@NonNull final BPartnerId bPartnerId,
			@NonNull final Instant orgChangeDate,
			@NonNull final OrgId orgId)
	{
		final IQuery<I_M_Product> membershipProductQuery = queryMembershipProducts(orgId);

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

	public BPartnerId getOrCreateCounterpartBPartner(@NonNull final OrgChangeRequest orgChangeRequest, @NonNull final OrgMappingId orgMappingId)
	{
		final OrgId targetOrgId = orgChangeRequest.getOrgToId();
		return bpartnerDAO.getCounterpartBPartnerId(orgMappingId, targetOrgId)
				.orElseGet(() -> bpartnerDAO.cloneBPartnerRecord(CloneBPartnerRequest.builder()
																		 .fromBPartnerId(orgChangeRequest.getBpartnerId())
																		 .orgId(targetOrgId)
																		 .orgMappingId(orgMappingId)
																		 .build()));
	}

	private Set<FlatrateTermId> retrieveMembershipSubscriptionIds(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final Instant orgChangeDate,
			@NonNull final OrgId orgId)
	{
		return queryMembershipRunningSubscription(bpartnerId, orgChangeDate, orgId)
				.listIds(FlatrateTermId::ofRepoId);
	}

	private Set<FlatrateTermId> retrieveAllRunningSubscriptionIds(
			@NonNull final BPartnerId bPartnerId,
			@NonNull final Instant orgChangeDate,
			@NonNull final OrgId orgId)
	{
		return queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_AD_Org_ID, orgId)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID, bPartnerId)
				.addNotEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_ContractStatus, FlatrateTermStatus.Quit.getCode())
				.addNotEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_ContractStatus, FlatrateTermStatus.Voided.getCode())
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_EndDate, CompareQueryFilter.Operator.GREATER, orgChangeDate)
				.create()
				.listIds(FlatrateTermId::ofRepoId);
	}

	public void createMembershipSubscriptionTerm(
			final OrgChangeBPartnerComposite orgChangeBPartnerComposite,
			final BPartnerComposite destinationBPartnerComposite,
			final OrgChangeRequest orgChangeRequest)
	{
		final GroupCategoryId groupCategoryId = orgChangeRequest.getGroupCategoryId();
		if (groupCategoryId == null)
		{
			loggable.addLog("No membership subscription will be created for partner {} because there was no group category Id "
									+ "selected as paramteres", destinationBPartnerComposite.getBpartner());
			return;
		}

		final I_M_Product newOrgMembershipProduct = productDAO.getProductOfGroupCategory(groupCategoryId, destinationBPartnerComposite.getOrgId()).orElse(null);
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
		createTerms(destinationBPartnerComposite, orgChangeRequest, groupTemplateId, sourceMembershipSubscription);
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

		final I_AD_User user = userDAO.getCounterpartUser(sourceSubscription.getUserInChargeId(), destinationBPartnerComposite.getOrgId()).orElse(null);

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

			term.setDropShip_Location_ID(shipBPartnerLocation.getId().getRepoId());

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
