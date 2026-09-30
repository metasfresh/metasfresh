package de.metas.invoicecandidate.spi.impl;

import ch.qos.logback.classic.Level;
import de.metas.acct.api.IProductAcctDAO;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.business.BusinessTestHelper;
import de.metas.document.dimension.DimensionFactory;
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.OrderLineDimensionFactory;
import de.metas.inoutcandidate.document.dimension.ReceiptScheduleDimensionFactory;
import de.metas.invoicecandidate.AbstractICTestSupport;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.document.dimension.InvoiceCandidateDimensionFactory;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lang.SOTrx;
import de.metas.location.LocationId;
import de.metas.logging.LogManager;
import de.metas.order.invoicecandidate.C_OrderLine_Handler;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.swat.base
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

/**
 * A group-compensation order line's invoice candidate is priced with {@code PriceEntered == PriceActual}, which is only correct if
 * the candidate carries no discount and no {@code QualityDiscountPercent_Override}. This covers the override: it must never be set
 * from the partner's pricing conditions for a group-compensation line, even when a breaks-type condition has a break matching the
 * discount product with a quality-discount percentage.
 * <p>
 * Uses a mocked {@link IInvoiceCandBL} to simulate that matching break -- a full DB-backed {@code IPricingConditionsRepository}
 * fixture (discount schema + schema break + a {@code PaymentTermService} Spring bean) is out of reach of this lightweight
 * ({@link AbstractICTestSupport}) test support, so the boundary is exercised at the {@code C_OrderLine_Handler} -> {@link IInvoiceCandBL}
 * call site instead.
 */
public class C_OrderLine_Handler_GroupCompensationTest extends AbstractICTestSupport
{
	private static final BigDecimal SIMULATED_QUALITY_DISCOUNT_PERCENT = new BigDecimal("15");

	private C_OrderLine_Handler orderLineHandler;
	private IInvoiceCandBL mockInvoiceCandBL;

	@BeforeEach
	public void init()
	{
		final Properties ctx = Env.getCtx();
		Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, 1);
		Env.setContext(ctx, Env.CTXNAME_AD_Language, "de_CH");

		final List<DimensionFactory<?>> dimensionFactories = new ArrayList<>();
		dimensionFactories.add(new OrderLineDimensionFactory());
		dimensionFactories.add(new ReceiptScheduleDimensionFactory());
		dimensionFactories.add(new InvoiceCandidateDimensionFactory());

		final DimensionService dimensionService = new DimensionService(dimensionFactories);
		SpringContextHolder.registerJUnitBean(dimensionService);

		// Register the mock BEFORE constructing the handler: C_OrderLine_Handler resolves
		// IInvoiceCandBL via Services.get(...) once, at field-initialization time.
		mockInvoiceCandBL = Mockito.mock(IInvoiceCandBL.class);
		Mockito.doAnswer(invocation -> {
					final I_C_Invoice_Candidate ic = invocation.getArgument(0);
					// simulate a partner whose breaks-type pricing conditions have a break matching the discount product with a quality-discount %
					ic.setQualityDiscountPercent_Override(SIMULATED_QUALITY_DISCOUNT_PERCENT);
					return null;
				})
				.when(mockInvoiceCandBL)
				.setQualityDiscountPercent_Override(ArgumentMatchers.any(I_C_Invoice_Candidate.class), ArgumentMatchers.any(ImmutableAttributeSet.class));
		Services.registerService(IInvoiceCandBL.class, mockInvoiceCandBL);

		orderLineHandler = new C_OrderLine_Handler();

		final I_C_ILCandHandler handler = create(Env.getCtx(), I_C_ILCandHandler.class, org.adempiere.ad.trx.api.ITrx.TRXNAME_None);
		handler.setC_ILCandHandler_ID(540001);
		handler.setClassname(C_OrderLine_Handler.class.getName());
		handler.setName("Auftragszeilen");
		handler.setTableName(I_C_OrderLine.Table_Name);
		save(handler);

		orderLineHandler.setHandlerRecord(handler);

		LogManager.setLevel(Level.DEBUG);

		Services.registerService(IBPartnerBL.class, new BPartnerBL(new UserRepository()));
	}

	private BPartnerLocationAndCaptureId createBPartnerAndLocation()
	{
		final org.compiere.model.I_C_BPartner bpartner = BusinessTestHelper.createBPartner("Test1");

		final I_C_Location location = newInstance(I_C_Location.class);
		saveRecord(location);
		final LocationId locationId = LocationId.ofRepoId(location.getC_Location_ID());

		final I_C_BPartner_Location bpl = newInstance(I_C_BPartner_Location.class, bpartner);
		bpl.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bpl.setIsShipTo(true);
		bpl.setIsBillTo(true);
		bpl.setC_Location_ID(locationId.getRepoId());
		saveRecord(bpl);

		return BPartnerLocationAndCaptureId.ofRecord(bpl);
	}

	private void setUpActivityAndTaxRetrieval(final I_C_Order order1, final I_C_OrderLine oL1)
	{
		final IProductAcctDAO productAcctDAO = Mockito.mock(IProductAcctDAO.class);
		final ITaxBL taxBL = Mockito.mock(ITaxBL.class);

		Services.registerService(IProductAcctDAO.class, productAcctDAO);
		Services.registerService(ITaxBL.class, taxBL);

		Mockito.doReturn(null).when(productAcctDAO).retrieveActivityForAcct(
				ArgumentMatchers.any(ClientId.class),
				ArgumentMatchers.any(OrgId.class),
				ArgumentMatchers.any(ProductId.class));

		Mockito
				.when(taxBL.getTaxNotNull(
						order1,
						(TaxCategoryId)null,
						oL1.getM_Product_ID(),
						order1.getDatePromised(),
						OrgId.ofRepoId(order1.getAD_Org_ID()),
						WarehouseId.ofRepoId(order1.getM_Warehouse_ID()),
						BPartnerLocationAndCaptureId.ofRepoId(order1.getC_BPartner_ID(), order1.getC_BPartner_Location_ID(), order1.getC_BPartner_Location_Value_ID()),
						SOTrx.ofBoolean(order1.isSOTrx())))
				.thenReturn(TaxId.ofRepoId(3));
	}

	private I_C_Order createOrder(final BPartnerLocationAndCaptureId bpartnerAndLocationId, final String docNo)
	{
		final I_C_Order order = order(docNo);
		order.setAD_Org_ID(orgId.getRepoId());
		order.setM_Warehouse_ID(warehouseId.getRepoId());
		order.setC_BPartner_ID(bpartnerAndLocationId.getBpartnerId().getRepoId());
		order.setC_BPartner_Location_ID(bpartnerAndLocationId.getBpartnerLocationId().getRepoId());
		order.setBill_BPartner_ID(bpartnerAndLocationId.getBpartnerId().getRepoId());
		order.setBill_Location_ID(bpartnerAndLocationId.getBpartnerLocationId().getRepoId());
		order.setDatePromised(Timestamp.valueOf("2021-11-30 00:00:00"));
		order.setC_Currency_ID(10);
		order.setM_PricingSystem_ID(20);
		order.setC_PaymentTerm_ID(paymentTermId.getRepoId());
		save(order);
		return order;
	}

	@Test
	public void groupCompensationLine_qualityDiscountPercentOverride_isNull()
	{
		final BPartnerLocationAndCaptureId bpartnerAndLocationId = createBPartnerAndLocation();

		final I_C_Order order = createOrder(bpartnerAndLocationId, "compensation");

		final I_C_OrderLine compensationOrderLine = orderLine("compensation");
		compensationOrderLine.setAD_Org_ID(orgId.getRepoId());
		compensationOrderLine.setC_Order(order);
		compensationOrderLine.setM_Product_ID(productId.getRepoId());
		compensationOrderLine.setIsGroupCompensationLine(true);
		compensationOrderLine.setC_Order_CompensationGroup_ID(1000001);
		save(compensationOrderLine);
		setUpActivityAndTaxRetrieval(order, compensationOrderLine);

		final I_C_Invoice_Candidate ic = orderLineHandler
				.createCandidatesFor(de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest.of(orderLineHandler, compensationOrderLine))
				.getC_Invoice_Candidates()
				.get(0);

		assertThat(ic.isGroupCompensationLine()).isTrue();
		// the generated getter never returns null (falls back to ZERO); the guard sets the underlying value
		// to null explicitly -- verified below by asserting the BL lookup was never even attempted
		assertThat(ic.getQualityDiscountPercent_Override()).isEqualByComparingTo(BigDecimal.ZERO);
		Mockito.verify(mockInvoiceCandBL, Mockito.never())
				.setQualityDiscountPercent_Override(ArgumentMatchers.any(), ArgumentMatchers.any());
	}

	@Test
	public void regularLine_qualityDiscountPercentOverride_isStillAppliedFromPricingConditions()
	{
		final BPartnerLocationAndCaptureId bpartnerAndLocationId = createBPartnerAndLocation();

		final I_C_Order order = createOrder(bpartnerAndLocationId, "regular");

		final I_C_OrderLine regularOrderLine = orderLine("regular");
		regularOrderLine.setAD_Org_ID(orgId.getRepoId());
		regularOrderLine.setC_Order(order);
		regularOrderLine.setM_Product_ID(productId.getRepoId());
		save(regularOrderLine);
		setUpActivityAndTaxRetrieval(order, regularOrderLine);

		final I_C_Invoice_Candidate ic = orderLineHandler
				.createCandidatesFor(de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest.of(orderLineHandler, regularOrderLine))
				.getC_Invoice_Candidates()
				.get(0);

		assertThat(ic.isGroupCompensationLine()).isFalse();
		assertThat(ic.getQualityDiscountPercent_Override()).isEqualByComparingTo(SIMULATED_QUALITY_DISCOUNT_PERCENT);
		Mockito.verify(mockInvoiceCandBL, Mockito.times(1))
				.setQualityDiscountPercent_Override(ArgumentMatchers.eq(ic), ArgumentMatchers.any());
	}
}
