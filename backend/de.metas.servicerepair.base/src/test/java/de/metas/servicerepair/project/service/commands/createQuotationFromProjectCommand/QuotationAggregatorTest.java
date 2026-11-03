/*
 * #%L
 * de.metas.servicerepair.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.servicerepair.project.service.commands.createQuotationFromProjectCommand;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.DocTypeId;
import de.metas.document.dimension.DimensionService;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.IOrderLineBL;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.order.compensationGroup.calibration.CalibrationMatchKey;
import de.metas.order.compensationGroup.calibration.CompensationGroupCalibrationRuleRepository;
import de.metas.order.impl.OrderBL;
import de.metas.order.impl.OrderLineBL;
import de.metas.order.impl.OrderLineDetailRepository;
import de.metas.order.model.I_C_CompensationGroup_CalibrationRule;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPricingBL;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantity;
import de.metas.servicerepair.customerreturns.WarrantyCase;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollector;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollectorId;
import de.metas.servicerepair.project.model.ServiceRepairProjectCostCollectorType;
import de.metas.servicerepair.project.model.ServiceRepairProjectInfo;
import de.metas.servicerepair.project.model.ServiceRepairProjectTask;
import de.metas.servicerepair.project.model.ServiceRepairProjectTaskId;
import de.metas.servicerepair.project.model.ServiceRepairProjectTaskStatus;
import de.metas.servicerepair.project.model.ServiceRepairProjectTaskType;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

class QuotationAggregatorTest
{
	private static final ZoneId TIME_ZONE = ZoneId.of("Europe/Berlin");
	private static final ProjectId PROJECT_ID = ProjectId.ofRepoId(1);

	private IPricingBL pricingBL;
	private ITrxManager trxManager;
	private IOrderDAO orderDAO;

	private I_C_UOM uom;
	private BPartnerId customerId;
	private BPGroupId customerGroupId;
	private ProductCategoryId productCategoryId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		pricingBL = Services.get(IPricingBL.class);
		trxManager = Services.get(ITrxManager.class);
		orderDAO = Services.get(IOrderDAO.class);

		// The repair quotation lines are priced manually (zero) and their price is not under test: keep the pricing engine out.
		final OrderLineBL orderLineBL = Mockito.spy(new OrderLineBL());
		doNothing().when(orderLineBL).updatePrices(any(I_C_OrderLine.class));
		Services.registerService(IOrderLineBL.class, orderLineBL);
		final OrderBL orderBL = Mockito.spy(new OrderBL());
		doNothing().when(orderBL).setPriceList(any(I_C_Order.class));
		Services.registerService(IOrderBL.class, orderBL);
		SpringContextHolder.registerJUnitBean(new OrderLineDetailRepository());
		SpringContextHolder.registerJUnitBean(new DimensionService(ImmutableList.of()));

		uom = newInstance(I_C_UOM.class);
		uom.setStdPrecision(2);
		saveRecord(uom);

		final I_C_BP_Group bpGroup = newInstance(I_C_BP_Group.class);
		saveRecord(bpGroup);
		customerGroupId = BPGroupId.ofRepoId(bpGroup.getC_BP_Group_ID());

		final I_C_BPartner customer = newInstance(I_C_BPartner.class);
		customer.setC_BP_Group_ID(bpGroup.getC_BP_Group_ID());
		customer.setIsCustomer(true);
		saveRecord(customer);
		customerId = BPartnerId.ofRepoId(customer.getC_BPartner_ID());

		final I_M_Product_Category category = newInstance(I_M_Product_Category.class);
		saveRecord(category);
		productCategoryId = ProductCategoryId.ofRepoId(category.getM_Product_Category_ID());
	}

	private ProductId product()
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setM_Product_Category_ID(productCategoryId.getRepoId());
		product.setC_UOM_ID(uom.getC_UOM_ID());
		saveRecord(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	private void calibrationRule80Percent(final ProductId productId)
	{
		final I_C_CompensationGroup_CalibrationRule rule = newInstance(I_C_CompensationGroup_CalibrationRule.class);
		rule.setAD_Org_ID(OrgId.ANY.getRepoId());
		rule.setSeqNo(10);
		rule.setC_BPartner_ID(customerId.getRepoId());
		rule.setM_Product_ID(productId.getRepoId());
		rule.setGroupCompensationCalibrationFactor(new BigDecimal("80"));
		rule.setIsActive(true);
		saveRecord(rule);
	}

	private Quantity qty(final String qty)
	{
		return Quantity.of(new BigDecimal(qty), uom);
	}

	/**
	 * A matching active calibration rule must not calibrate a service-repair quotation line.
	 */
	@Test
	void repairQuotation_matchingActiveCalibrationRule_isNotCalibrated()
	{
		final ProductId repairedProductId = product();
		calibrationRule80Percent(repairedProductId);

		// precondition: the rule matches this customer + product, i.e. it would calibrate this product in a calibrating flow (order line quick input, order candidates)
		assertThat(CompensationGroupCalibrationRuleRepository.newInstanceForUnitTesting()
				.getActiveRules()
				.findFirstMatching(CalibrationMatchKey.builder()
						.orgId(OrgId.MAIN)
						.bpartnerId(customerId)
						.bpGroupId(customerGroupId)
						.productId(repairedProductId)
						.productCategoryId(productCategoryId)
						.build()))
				.isPresent();

		final ServiceRepairProjectTaskId taskId = ServiceRepairProjectTaskId.ofRepoId(PROJECT_ID, 1);
		final ServiceRepairProjectTask task = ServiceRepairProjectTask.builder()
				.id(taskId)
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(1, OrgId.MAIN.getRepoId()))
				.type(ServiceRepairProjectTaskType.REPAIR_ORDER)
				.status(ServiceRepairProjectTaskStatus.COMPLETED)
				.productId(repairedProductId)
				.asiId(AttributeSetInstanceId.NONE)
				.warrantyCase(WarrantyCase.NO)
				.qtyRequired(qty("3"))
				.qtyReserved(qty("0"))
				.qtyConsumed(qty("3"))
				.isRepairOrderDone(true)
				.build();

		final ServiceRepairProjectCostCollector repairedProductToReturn = ServiceRepairProjectCostCollector.builder()
				.id(ServiceRepairProjectCostCollectorId.ofRepoId(PROJECT_ID, 1))
				.taskId(taskId)
				.type(ServiceRepairProjectCostCollectorType.RepairedProductToReturn)
				.productId(repairedProductId)
				.asiId(AttributeSetInstanceId.NONE)
				.warrantyCase(WarrantyCase.NO)
				.qtyReserved(qty("0"))
				.qtyConsumed(qty("3"))
				.build();

		final CurrencyId currencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);
		final QuotationAggregator aggregator = QuotationAggregator.builder()
				.pricingBL(pricingBL)
				.orderGroupRepository(OrderGroupRepository.newInstanceForUnitTesting())
				.project(ServiceRepairProjectInfo.builder()
						.projectId(PROJECT_ID)
						.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(1, OrgId.MAIN.getRepoId()))
						.bpartnerId(customerId)
						.build())
				.tasks(ImmutableList.of(task))
				.pricingInfo(ProjectQuotationPricingInfo.builder()
						.orgId(OrgId.MAIN)
						.orgTimeZone(TIME_ZONE)
						.shipBPartnerId(customerId)
						.datePromised(SystemTime.asZonedDateTime(TIME_ZONE))
						.pricingSystemId(PricingSystemId.ofRepoId(1))
						.priceListId(PriceListId.ofRepoId(1))
						.priceListVersionId(PriceListVersionId.ofRepoId(1))
						.currencyId(currencyId)
						.countryId(CountryId.ofRepoId(1))
						.build())
				.quotationDocTypeId(DocTypeId.ofRepoId(1))
				.build();

		// in production the quotation is created inside the process transaction
		final I_C_Order quotation = trxManager.callInThreadInheritedTrx(() -> aggregator.addAll(ImmutableList.of(repairedProductToReturn)).createDraft());

		final List<I_C_OrderLine> quotationLines = orderDAO.retrieveOrderLines(quotation, I_C_OrderLine.class);
		assertThat(quotationLines).hasSize(1);
		assertThat(quotationLines).allSatisfy(line -> {
			assertThat(line.getC_CompensationGroup_CalibrationRule_ID()).as("no calibration rule stored").isLessThanOrEqualTo(0);
			assertThat(InterfaceWrapperHelper.<BigDecimal>getValueOrNull(line, I_C_OrderLine.COLUMNNAME_GroupCompensationCalibrationFactor)).as("no calibration factor stored").isNull();
			assertThat(InterfaceWrapperHelper.<BigDecimal>getValueOrNull(line, I_C_OrderLine.COLUMNNAME_GroupCompensationQtyEnteredUncalibrated)).as("no uncalibrated qty stored").isNull();
		});

		final I_C_OrderLine repairedProductLine = quotationLines.get(0);
		assertThat(repairedProductLine.getC_Order_CompensationGroup_ID()).as("the repaired-product line is grouped").isPositive();
		assertThat(repairedProductLine.getM_Product_ID()).isEqualTo(repairedProductId.getRepoId());
		assertThat(repairedProductLine.getQtyEntered()).as("qty not calibrated").isEqualByComparingTo("3");
	}
}
