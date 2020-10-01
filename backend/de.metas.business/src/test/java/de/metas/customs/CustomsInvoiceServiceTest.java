package de.metas.customs;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.function.Consumer;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;

import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.currency.CurrencyCode;
import de.metas.currency.ICurrencyDAO;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.customs.process.ShipmentLinesForCustomsInvoiceRepo;
import de.metas.document.DocTypeId;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.interfaces.I_C_BPartner;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.order.OrderLineRepository;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.IUOMConversionDAO;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConstants;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class CustomsInvoiceServiceTest
{
	private CustomsInvoiceService service;
	private CustomsInvoiceRepository customsInvoiceRepo;

	private BPartnerLocationId logisticCompany;
	private UserId logisticUserId;

	private DocTypeId customsInvoiceDocTypeId;

	private CurrencyId chf;
	private CurrencyId euro;
	private final BigDecimal currencyMultiplier = new BigDecimal("1.13");

	private ProductId product1;

	private UomId uom1;
	private UomId uom2;
	private final BigDecimal convertionMultiplier = new BigDecimal("2");

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		Env.setLoggedUserId(Env.getCtx(), UserId.METASFRESH);

		customsInvoiceRepo = new CustomsInvoiceRepository();
		final OrderLineRepository orderLineRepo = new OrderLineRepository();
		final ShipmentLinesForCustomsInvoiceRepo shipmentLinesForCustomsInvoiceRepo = new ShipmentLinesForCustomsInvoiceRepo();
		service = new CustomsInvoiceService(customsInvoiceRepo, orderLineRepo, shipmentLinesForCustomsInvoiceRepo);

		logisticCompany = createBPartnerAndLocation("LogisticCompany", "Logistic Company Address");
		logisticUserId = createUser(logisticCompany.getBpartnerId(), "Logistic company user");

		chf = PlainCurrencyDAO.createCurrencyId(CurrencyCode.CHF);
		euro = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		customsInvoiceDocTypeId = createDocType("Customs Invoice DocType");

		uom1 = createUOM("UomCode1");
		uom2 = createUOM("UomCode2");
		createUOM(UOMConstants.X12_KILOGRAM);

		product1 = createProduct("Product1", uom1);

		final IUOMConversionDAO uomConversionsRepo = Services.get(IUOMConversionDAO.class);
		uomConversionsRepo.createUOMConversion(CreateUOMConversionRequest.builder()
				.productId(product1)
				.fromUomId(uom2)
				.toUomId(uom1)
				.fromToMultiplier(convertionMultiplier)
				.build());

		final PlainCurrencyDAO currencyDAO = (PlainCurrencyDAO)Services.get(ICurrencyDAO.class);
		currencyDAO.setRate(euro, chf, currencyMultiplier);
	}

	@Test
	public void createCustomsInvoice_oneShipmentLine()
	{
		final BPartnerLocationId bpartnerAndLocation = createBPartnerAndLocation("Partner2", "address2");
		final OrderId order = createOrder(bpartnerAndLocation);
		final InOutId shipment = createShipment(bpartnerAndLocation);

		final I_C_OrderLine orderLine1 = createOrderLine(
				order,
				StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1),
				Money.of("10", chf));
		final InOutAndLineId shipmentLine1 = createInOutLine(shipment, orderLine1);

		final CustomsInvoice customsInvoice = service.generateCustomsInvoice(CustomsInvoiceRequest.builder()
				.bpartnerAndLocationId(logisticCompany)
				.bpartnerAddress("Bpartner Address")
				.currencyId(chf)
				.docTypeId(customsInvoiceDocTypeId)
				.documentNo("12345")
				.invoiceDate(LocalDate.of(2019, Month.MAY, 22))
				.userId(logisticUserId)
				.linesToExportMap(ImmutableSetMultimap.<ProductId, InOutAndLineId> builder()
						.put(product1, shipmentLine1)
						.build())
				.build());

		assertThat(customsInvoice).isNotNull();
		assertThat(customsInvoice.getId()).isNotNull();
		assertThat(customsInvoice.getBpartnerAndLocationId()).isEqualTo(logisticCompany);
		assertThat(customsInvoice.getCurrencyId()).isEqualTo(chf);
		assertThat(customsInvoice.getDocTypeId()).isEqualTo(customsInvoiceDocTypeId);
		assertThat(customsInvoice.getInvoiceDate()).isEqualTo(LocalDate.of(2019, Month.MAY, 22));
		assertThat(customsInvoice.getUserId()).isEqualTo(logisticUserId);
		assertThat(customsInvoice.getDocStatus()).isEqualTo(DocStatus.Drafted);

		//
		// Customs Invoice Line:
		{
			final ImmutableList<CustomsInvoiceLine> lines = customsInvoice.getLines();
			assertThat(lines).hasSize(1);
			final CustomsInvoiceLine customsInvoiceLine = lines.get(0);
			assertThat(customsInvoiceLine.getId()).isNotNull();
			assertThat(customsInvoiceLine.getLineNo()).isEqualTo(10);
			assertThat(customsInvoiceLine.getProductId()).isEqualTo(product1);
			assertThat(customsInvoiceLine.getQuantity()).isEqualTo(qty("2", uom1));
			assertThat(customsInvoiceLine.getQuantity().getUomId()).isEqualTo(uom1);
			assertThat(customsInvoiceLine.getLineNetAmt()).isEqualTo(Money.of("20", chf)); // 2uom1 x 10chf

			//
			// Allocation:
			{
				assertThat(customsInvoiceLine.getAllocations()).hasSize(1);
				final CustomsInvoiceLineAlloc alloc = customsInvoiceLine.getAllocations().get(0);
				assertThat(alloc.getInoutAndLineId()).isEqualTo(shipmentLine1);
				assertThat(alloc.getPrice()).isEqualTo(Money.of("10", chf));
				assertThat(alloc.getQuantityInPriceUOM()).isEqualTo(qty("2", uom1));
			}
		}
	}

	@Test
	public void createCustomsInvoice_twoShipmentLines()
	{
		final BPartnerLocationId bpartnerAndLocation = createBPartnerAndLocation("Partner", "address");
		final OrderId order = createOrder(bpartnerAndLocation);
		final InOutId shipment = createShipment(bpartnerAndLocation);

		final I_C_OrderLine orderLine1 = createOrderLine(
				order,
				StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1),
				Money.of("10", chf));
		final InOutAndLineId shipmentLine1 = createInOutLine(shipment, orderLine1);

		final I_C_OrderLine orderLine2 = createOrderLine(
				order,
				StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(5), product1, uom1),
				Money.of("20", chf));
		final InOutAndLineId shipmentLine2 = createInOutLine(shipment, orderLine2);

		final CustomsInvoice customsInvoice = service.generateCustomsInvoice(CustomsInvoiceRequest.builder()
				.bpartnerAndLocationId(logisticCompany)
				.bpartnerAddress("Bpartner Address")
				.currencyId(chf)
				.docTypeId(customsInvoiceDocTypeId)
				.documentNo("12345")
				.invoiceDate(LocalDate.of(2019, Month.MAY, 22))
				.userId(logisticUserId)
				.linesToExportMap(ImmutableSetMultimap.<ProductId, InOutAndLineId> builder()
						.put(product1, shipmentLine1)
						.put(product1, shipmentLine2)
						.build())
				.build());

		assertThat(customsInvoice).isNotNull();
		assertThat(customsInvoice.getId()).isNotNull();
		assertThat(customsInvoice.getBpartnerAndLocationId()).isEqualTo(logisticCompany);
		assertThat(customsInvoice.getCurrencyId()).isEqualTo(chf);
		assertThat(customsInvoice.getDocTypeId()).isEqualTo(customsInvoiceDocTypeId);
		assertThat(customsInvoice.getInvoiceDate()).isEqualTo(LocalDate.of(2019, Month.MAY, 22));
		assertThat(customsInvoice.getUserId()).isEqualTo(logisticUserId);
		assertThat(customsInvoice.getDocStatus()).isEqualTo(DocStatus.Drafted);

		//
		// Customs Invoice Line:
		{
			final ImmutableList<CustomsInvoiceLine> lines = customsInvoice.getLines();
			assertThat(lines).hasSize(1);
			final CustomsInvoiceLine customsInvoiceLine = lines.get(0);
			assertThat(customsInvoiceLine.getId()).isNotNull();
			assertThat(customsInvoiceLine.getLineNo()).isEqualTo(10);
			assertThat(customsInvoiceLine.getProductId()).isEqualTo(product1);
			assertThat(customsInvoiceLine.getQuantity()).isEqualTo(qty("7", uom1));
			assertThat(customsInvoiceLine.getLineNetAmt()).isEqualTo(Money.of("120", chf)); // 2uom1 x 10chf + 5uom1 x 20chf

			//
			// Allocations
			{
				assertThat(customsInvoiceLine.getAllocations()).hasSize(2);

				final CustomsInvoiceLineAlloc alloc1 = customsInvoiceLine.getAllocations().get(0);
				assertThat(alloc1.getInoutAndLineId()).isEqualTo(shipmentLine1);
				assertThat(alloc1.getPrice()).isEqualTo(Money.of("10", chf));
				assertThat(alloc1.getQuantityInPriceUOM()).isEqualTo(qty("2", uom1));

				final CustomsInvoiceLineAlloc alloc2 = customsInvoiceLine.getAllocations().get(1);
				assertThat(alloc2.getInoutAndLineId()).isEqualTo(shipmentLine2);
				assertThat(alloc2.getPrice()).isEqualTo(Money.of("20", chf));
				assertThat(alloc2.getQuantityInPriceUOM()).isEqualTo(qty("5", uom1));
			}
		}
	}

	@Test
	public void createCustomsInvoice_twoShipmentLines_DifferentUOM()
	{
		final BPartnerLocationId bpartnerAndLocation = createBPartnerAndLocation("Partner", "address");
		final OrderId order = createOrder(bpartnerAndLocation);
		final InOutId shipment = createShipment(bpartnerAndLocation);

		final I_C_OrderLine orderLine1 = createOrderLine(
				order,
				StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1),
				Money.of("10", chf));
		final InOutAndLineId shipmentLine1 = createInOutLine(shipment, orderLine1);

		final I_C_OrderLine orderLine2 = createOrderLine(
				order,
				StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(5), product1, uom2), // => product1 is in UOM1, so: uomQty=2.5 uom2, stockQty=5 uom1
				Money.of("20", chf));
		final InOutAndLineId shipmentLine2 = createInOutLine(shipment, orderLine2);

		final CustomsInvoice customsInvoice = service.generateCustomsInvoice(CustomsInvoiceRequest.builder()
				.bpartnerAndLocationId(logisticCompany)
				.bpartnerAddress("Bpartner Address")
				.currencyId(chf)
				.docTypeId(customsInvoiceDocTypeId)
				.documentNo("12345")
				.invoiceDate(LocalDate.of(2019, Month.MAY, 22))
				.userId(logisticUserId)
				.linesToExportMap(ImmutableSetMultimap.<ProductId, InOutAndLineId> builder()
						.put(product1, shipmentLine1)
						.put(product1, shipmentLine2)
						.build())
				.build());

		assertThat(customsInvoice).isNotNull();
		assertThat(customsInvoice.getId()).isNotNull();
		assertThat(customsInvoice.getBpartnerAndLocationId()).isEqualTo(logisticCompany);
		assertThat(customsInvoice.getCurrencyId()).isEqualTo(chf);
		assertThat(customsInvoice.getDocTypeId()).isEqualTo(customsInvoiceDocTypeId);
		assertThat(customsInvoice.getInvoiceDate()).isEqualTo(LocalDate.of(2019, Month.MAY, 22));
		assertThat(customsInvoice.getUserId()).isEqualTo(logisticUserId);
		assertThat(customsInvoice.getDocStatus()).isEqualTo(DocStatus.Drafted);

		//
		// Customs Invoice Line
		{
			final ImmutableList<CustomsInvoiceLine> lines = customsInvoice.getLines();
			assertThat(lines).hasSize(1);
			final CustomsInvoiceLine customsInvoiceLine = lines.get(0);

			assertThat(customsInvoiceLine.getId()).isNotNull();
			assertThat(customsInvoiceLine.getLineNo()).isEqualTo(10);
			assertThat(customsInvoiceLine.getProductId()).isEqualTo(product1);
			assertThat(customsInvoiceLine.getQuantity()).isEqualTo(qty("7", uom1));
			assertThat(customsInvoiceLine.getLineNetAmt()).isEqualTo(Money.of("70", chf));
			// i.e. LineNetAmt:
			// shipmentLine1: 2 UOM1 x 10 CHF = 20 CHF
			// shipmentLine2: + 2.5 UOM2 x 20 CHF = 50 CHF

			//
			// Allocations
			{
				assertThat(customsInvoiceLine.getAllocations()).hasSize(2);

				final CustomsInvoiceLineAlloc alloc1 = customsInvoiceLine.getAllocations().get(0);
				assertThat(alloc1.getInoutAndLineId()).isEqualTo(shipmentLine1);
				assertThat(alloc1.getPrice()).isEqualTo(Money.of(10, chf));
				assertThat(alloc1.getQuantityInPriceUOM()).isEqualTo(qty("2", uom1));

				final CustomsInvoiceLineAlloc alloc2 = customsInvoiceLine.getAllocations().get(1);
				assertThat(alloc2.getInoutAndLineId()).isEqualTo(shipmentLine2);
				assertThat(alloc2.getPrice()).isEqualTo(Money.of(20, chf));
				assertThat(alloc2.getQuantityInPriceUOM()).isEqualTo(qty("2.5", uom2));
			}
		}
	}

	@Test
	public void createCustomsInvoice_twoShipmentLines_DifferentUOM_DifferentCurrency()
	{
		final BPartnerLocationId bpartnerAndLocation = createBPartnerAndLocation("Partner", "address");
		final OrderId order = createOrder(bpartnerAndLocation);
		final InOutId shipment = createShipment(bpartnerAndLocation);

		final I_C_OrderLine orderLine1 = createOrderLine(
				order,
				StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1),
				Money.of("10", chf));
		final InOutAndLineId shipmentLine1 = createInOutLine(shipment, orderLine1);

		final I_C_OrderLine orderLine2 = createOrderLine(
				order,
				StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(5), product1, uom2), // 5 UOM1 = 2.5 UOM2
				Money.of("20", euro)); // 22.6 CHF
		final InOutAndLineId shipmentLine2 = createInOutLine(shipment, orderLine2);

		final CustomsInvoice customsInvoice = service.generateCustomsInvoice(CustomsInvoiceRequest.builder()
				.bpartnerAndLocationId(logisticCompany)
				.bpartnerAddress("Bpartner Address")
				.currencyId(chf)
				.docTypeId(customsInvoiceDocTypeId)
				.documentNo("12345")
				.invoiceDate(LocalDate.of(2019, Month.MAY, 22))
				.userId(logisticUserId)
				.linesToExportMap(ImmutableSetMultimap.<ProductId, InOutAndLineId> builder()
						.put(product1, shipmentLine1)
						.put(product1, shipmentLine2)
						.build())
				.build());

		//
		// Customs Invoice
		assertThat(customsInvoice).isNotNull();
		assertThat(customsInvoice.getId()).isNotNull();
		assertThat(customsInvoice.getBpartnerAndLocationId()).isEqualTo(logisticCompany);
		assertThat(customsInvoice.getCurrencyId()).isEqualTo(chf);
		assertThat(customsInvoice.getDocTypeId()).isEqualTo(customsInvoiceDocTypeId);
		assertThat(customsInvoice.getInvoiceDate()).isEqualTo(LocalDate.of(2019, Month.MAY, 22));
		assertThat(customsInvoice.getUserId()).isEqualTo(logisticUserId);
		assertThat(customsInvoice.getDocStatus()).isEqualTo(DocStatus.Drafted);

		//
		// Customs Invoice Line
		{
			final ImmutableList<CustomsInvoiceLine> lines = customsInvoice.getLines();
			assertThat(lines).hasSize(1);
			final CustomsInvoiceLine customsInvoiceLine = lines.get(0);
			assertThat(customsInvoiceLine.getId()).isNotNull();
			assertThat(customsInvoiceLine.getLineNo()).isEqualTo(10);
			assertThat(customsInvoiceLine.getProductId()).isEqualTo(product1);
			assertThat(customsInvoiceLine.getQuantity()).isEqualTo(qty("7", uom1)); // i.e. 2 uom1 + 5 uom1
			assertThat(customsInvoiceLine.getLineNetAmt()).isEqualTo(Money.of("76.5", chf));
			// i.e. LineNetAmt is
			// 20 CHF (previous)
			// + 56.5 CHF (=20 EUR x 2.5 UOM2 = 22.6 CHF x 2.5 UOM2)

			//
			// Allocations
			{
				assertThat(customsInvoiceLine.getAllocations()).hasSize(2);

				final CustomsInvoiceLineAlloc alloc1 = customsInvoiceLine.getAllocations().get(0);
				assertThat(alloc1.getInoutAndLineId()).isEqualTo(shipmentLine1);
				assertThat(alloc1.getPrice()).isEqualTo(Money.of(10, chf));
				assertThat(alloc1.getQuantityInPriceUOM()).isEqualTo(qty("2", uom1));

				final CustomsInvoiceLineAlloc alloc2 = customsInvoiceLine.getAllocations().get(1);
				assertThat(alloc2.getInoutAndLineId()).isEqualTo(shipmentLine2);
				assertThat(alloc2.getPrice()).isEqualTo(Money.of("22.6", chf)); // 20eur x 1.13
				assertThat(alloc2.getQuantityInPriceUOM()).isEqualTo(qty("2.5", uom2));
			}
		}
	}

	@Test
	public void addShipmentsToCustomsInvoice_AddSameShipmentLine()
	{
		final BPartnerLocationId bpartnerAndLocation = createBPartnerAndLocation("bpartner", "address");
		final OrderId order = createOrder(bpartnerAndLocation);
		final I_C_OrderLine orderLine1 = createOrderLine(
				order,
				StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1), // qty
				Money.of(10, chf) // price
		);

		final InOutId inout1 = createShipment(bpartnerAndLocation);
		final InOutAndLineId shipmentLine1 = createInOutLine(inout1, orderLine1);

		final Consumer<CustomsInvoice> customsInvoiceLineAssertions = customsInvoice -> {
			final ImmutableList<CustomsInvoiceLine> lines = customsInvoice.getLines();
			assertThat(lines).hasSize(1);
			final CustomsInvoiceLine customsInvoiceLine = lines.get(0);

			assertThat(customsInvoiceLine.getId()).isNotNull();
			assertThat(customsInvoiceLine.getLineNo()).isEqualTo(10);
			assertThat(customsInvoiceLine.getProductId()).isEqualTo(product1);
			assertThat(customsInvoiceLine.getQuantity()).isEqualTo(qty("2", uom1));
			assertThat(customsInvoiceLine.getLineNetAmt()).isEqualTo(Money.of(20, chf));

			//
			// Allocations
			assertThat(customsInvoiceLine.getAllocations()).hasSize(1);

			final CustomsInvoiceLineAlloc alloc1 = customsInvoiceLine.getAllocations().get(0);
			assertThat(alloc1.getInoutAndLineId()).isEqualTo(shipmentLine1);
			assertThat(alloc1.getPrice()).isEqualTo(Money.of(10, chf));
			assertThat(alloc1.getQuantityInPriceUOM()).isEqualTo(qty("2", uom1));
		};

		//
		// Create the customs invoice from one shipment line
		final CustomsInvoiceId customsInvoiceId;
		{
			final CustomsInvoice customsInvoice = service.generateCustomsInvoice(CustomsInvoiceRequest.builder()
					.bpartnerAndLocationId(logisticCompany)
					.bpartnerAddress("Bpartner Address")
					.currencyId(chf)
					.docTypeId(customsInvoiceDocTypeId)
					.documentNo("12345")
					.invoiceDate(LocalDate.of(2019, Month.MAY, 22))
					.userId(logisticUserId)
					.linesToExportMap(ImmutableSetMultimap.<ProductId, InOutAndLineId> builder()
							.put(product1, shipmentLine1)
							.build())
					.build());
			customsInvoiceId = customsInvoice.getId();

			customsInvoiceLineAssertions.accept(customsInvoice);
		}

		//
		// Add same shipment line again: expect no change
		{
			service.addShipmentLinesToCustomsInvoice(product1,
					ImmutableSet.of(shipmentLine1),
					customsInvoiceId);

			final CustomsInvoice customsInvoice = customsInvoiceRepo.retrieveById(customsInvoiceId);
			customsInvoiceLineAssertions.accept(customsInvoice);
		}
	}

	@Test
	public void addShipmentsToCustomsInvoice_AddSecondShipmentLine_with_DifferentUOM_DifferentCurrency()
	{
		final BPartnerLocationId bpartnerAndLocation = createBPartnerAndLocation("bpartner", "address");
		final OrderId order = createOrder(bpartnerAndLocation);

		//
		// Create the customs invoice from one shipment line:
		final CustomsInvoiceId customsInvoiceId;
		{
			final I_C_OrderLine orderLine1 = createOrderLine(
					order,
					StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(2), product1, uom1), // qty
					Money.of(10, chf) // price
			);

			final InOutId inout1 = createShipment(bpartnerAndLocation);
			final InOutAndLineId shipmentLine1 = createInOutLine(inout1, orderLine1);

			final CustomsInvoice customsInvoice = service.generateCustomsInvoice(CustomsInvoiceRequest.builder()
					.bpartnerAndLocationId(logisticCompany)
					.bpartnerAddress("Bpartner Address")
					.currencyId(chf)
					.docTypeId(customsInvoiceDocTypeId)
					.documentNo("12345")
					.invoiceDate(LocalDate.of(2019, Month.MAY, 22))
					.userId(logisticUserId)
					.linesToExportMap(ImmutableSetMultimap.<ProductId, InOutAndLineId> builder()
							.put(product1, shipmentLine1)
							.build())
					.build());
			customsInvoiceId = customsInvoice.getId();
			assertThat(customsInvoiceId).isNotNull();
			assertThat(customsInvoice.getBpartnerAndLocationId()).isEqualTo(logisticCompany);
			assertThat(customsInvoice.getCurrencyId()).isEqualTo(chf);
			assertThat(customsInvoice.getDocTypeId()).isEqualTo(customsInvoiceDocTypeId);
			assertThat(customsInvoice.getInvoiceDate()).isEqualTo(LocalDate.of(2019, Month.MAY, 22));
			assertThat(customsInvoice.getUserId()).isEqualTo(logisticUserId);
			assertThat(customsInvoice.getDocStatus()).isEqualTo(DocStatus.Drafted);

			final ImmutableList<CustomsInvoiceLine> lines = customsInvoice.getLines();
			assertThat(lines).hasSize(1);
			final CustomsInvoiceLine customsInvoiceLine = lines.get(0);

			assertThat(customsInvoiceLine.getId()).isNotNull();
			assertThat(customsInvoiceLine.getLineNo()).isEqualTo(10);
			assertThat(customsInvoiceLine.getProductId()).isEqualTo(product1);
			assertThat(customsInvoiceLine.getQuantity()).isEqualTo(qty("2", uom1));
			assertThat(customsInvoiceLine.getLineNetAmt()).isEqualTo(Money.of(20, chf));

			//
			// Allocations
			assertThat(customsInvoiceLine.getAllocations()).hasSize(1);

			final CustomsInvoiceLineAlloc alloc1 = customsInvoiceLine.getAllocations().get(0);
			assertThat(alloc1.getInoutAndLineId()).isEqualTo(shipmentLine1);
			assertThat(alloc1.getPrice()).isEqualTo(Money.of(10, chf));
			assertThat(alloc1.getQuantityInPriceUOM()).isEqualTo(qty("2", uom1));
		}

		//
		// Add another shipment line to existing customs invoice
		{
			final I_C_OrderLine orderLine2 = createOrderLine(
					order,
					StockQtyAndUOMQtys.createConvert(BigDecimal.valueOf(5), product1, uom2), // qty: 5 uom1 = 2.5 uom2
					Money.of(20, euro) // price: 20 EUR = 22.6 CHF
			);
			final InOutId inout2 = createShipment(bpartnerAndLocation);
			final InOutAndLineId shipmentLine2 = createInOutLine(inout2, orderLine2);

			service.addShipmentLinesToCustomsInvoice(product1,
					ImmutableSet.of(shipmentLine2),
					customsInvoiceId);

			final CustomsInvoice customsInvoice = customsInvoiceRepo.retrieveById(customsInvoiceId);

			final ImmutableList<CustomsInvoiceLine> lines = customsInvoice.getLines();
			assertThat(lines).hasSize(1);
			final CustomsInvoiceLine customsInvoiceLine = lines.get(0);
			assertThat(customsInvoiceLine.getLineNo()).isEqualTo(10);
			assertThat(customsInvoiceLine.getProductId()).isEqualTo(product1);
			assertThat(customsInvoiceLine.getQuantity()).isEqualTo(qty("7", uom1)); // i.e. 2 uom1 + 5 uom1
			assertThat(customsInvoiceLine.getLineNetAmt()).isEqualTo(Money.of("76.5", chf));
			// i.e. LineNetAmt is
			// 20 CHF (previous)
			// + 56.5 CHF (=20 EUR x 2.5 UOM2 = 22.6 CHF x 2.5 UOM2)

			//
			// Allocations
			assertThat(customsInvoiceLine.getAllocations()).hasSize(2);

			final CustomsInvoiceLineAlloc alloc2 = customsInvoiceLine.getAllocations().get(1);
			assertThat(alloc2.getInoutAndLineId()).isEqualTo(shipmentLine2);
			assertThat(alloc2.getPrice()).isEqualTo(Money.of("22.6", chf)); // 20 eur
			assertThat(alloc2.getQuantityInPriceUOM()).isEqualTo(qty("2.5", uom2));
		}
	}

	private I_C_OrderLine createOrderLine(
			final OrderId order,
			final StockQtyAndUOMQty stockQtyAndUOMQty,
			final Money priceActual)
	{
		final I_C_OrderLine orderLineRecord = newInstance(I_C_OrderLine.class);

		orderLineRecord.setC_Order_ID(order.getRepoId());

		orderLineRecord.setM_Product_ID(stockQtyAndUOMQty.getProductId().getRepoId());
		orderLineRecord.setQtyOrdered(stockQtyAndUOMQty.getStockQty().toBigDecimal());

		orderLineRecord.setQtyEntered(stockQtyAndUOMQty.getUOMQtyNotNull().toBigDecimal());
		orderLineRecord.setC_UOM_ID(stockQtyAndUOMQty.getUOMQtyNotNull().getUomId().getRepoId());

		orderLineRecord.setPriceActual(priceActual.toBigDecimal());
		orderLineRecord.setC_Currency_ID(priceActual.getCurrencyId().getRepoId());

		save(orderLineRecord);

		return orderLineRecord;
	}

	private InOutAndLineId createInOutLine(@NonNull final InOutId inout1, @NonNull final I_C_OrderLine orderLineRecord)
	{

		final I_M_InOutLine shipmentLineRecord = newInstance(I_M_InOutLine.class);
		shipmentLineRecord.setM_InOut_ID(inout1.getRepoId());

		shipmentLineRecord.setM_Product_ID(orderLineRecord.getM_Product_ID());
		shipmentLineRecord.setMovementQty(orderLineRecord.getQtyOrdered());

		shipmentLineRecord.setC_UOM_ID(orderLineRecord.getC_UOM_ID());
		shipmentLineRecord.setQtyEntered(orderLineRecord.getQtyEntered());

		shipmentLineRecord.setC_OrderLine_ID(orderLineRecord.getC_OrderLine_ID());

		save(shipmentLineRecord);

		// return shipmentLineRecord;
		return InOutAndLineId.ofRepoId(inout1.getRepoId(), shipmentLineRecord.getM_InOutLine_ID());
	}

	private InOutId createShipment(final BPartnerLocationId bpartnerAndLocation)
	{
		final I_M_InOut shipment = newInstance(I_M_InOut.class);

		shipment.setC_BPartner_ID(bpartnerAndLocation.getBpartnerId().getRepoId());
		shipment.setC_BPartner_Location_ID(bpartnerAndLocation.getRepoId());

		shipment.setDocStatus(IDocument.STATUS_Completed);

		save(shipment);

		return InOutId.ofRepoId(shipment.getM_InOut_ID());
	}

	private OrderId createOrder(final BPartnerLocationId bpartnerAndLocation)
	{
		final I_M_Warehouse warehouse = newInstance(I_M_Warehouse.class);
		save(warehouse);

		final I_C_PaymentTerm paymentTerm = newInstance(I_C_PaymentTerm.class);
		save(paymentTerm);

		final I_C_Order order = newInstance(I_C_Order.class);

		order.setC_BPartner_ID(bpartnerAndLocation.getBpartnerId().getRepoId());
		order.setC_BPartner_Location_ID(bpartnerAndLocation.getRepoId());

		order.setDocStatus(DocStatus.Completed.getCode());

		order.setM_Warehouse_ID(warehouse.getM_Warehouse_ID());

		order.setC_PaymentTerm_ID(paymentTerm.getC_PaymentTerm_ID());

		order.setDatePromised(TimeUtil.asTimestamp(LocalDate.of(2019, 6, 6)));

		save(order);

		return OrderId.ofRepoId(order.getC_Order_ID());
	}

	private ProductId createProduct(final String productName, final UomId uomId)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setName(productName);
		product.setC_UOM_ID(uomId.getRepoId());

		save(product);

		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	private UomId createUOM(final String name)
	{
		final I_C_UOM uom = newInstance(I_C_UOM.class);
		uom.setName(name);
		uom.setUOMSymbol(name);
		uom.setStdPrecision(2);
		uom.setX12DE355(name);
		save(uom);

		return UomId.ofRepoId(uom.getC_UOM_ID());
	}

	private UserId createUser(final BPartnerId bpartnerId, final String userName)
	{
		final I_AD_User userRecord = newInstance(I_AD_User.class);
		userRecord.setC_BPartner_ID(bpartnerId.getRepoId());
		userRecord.setName(userName);
		save(userRecord);

		return UserId.ofRepoId(userRecord.getAD_User_ID());
	}

	private DocTypeId createDocType(final String docTypeName)
	{
		final I_C_DocType docTypeRecord = newInstance(I_C_DocType.class);
		docTypeRecord.setName(docTypeName);
		save(docTypeRecord);

		return DocTypeId.ofRepoId(docTypeRecord.getC_DocType_ID());
	}

	private BPartnerLocationId createBPartnerAndLocation(final String partnerName, final String address)
	{
		final I_C_BPartner bpartnerRecord = newInstance(I_C_BPartner.class);
		bpartnerRecord.setName(partnerName);
		save(bpartnerRecord);

		final int bpartnerId = bpartnerRecord.getC_BPartner_ID();

		final I_C_BPartner_Location bpLocationRecord = newInstance(I_C_BPartner_Location.class);
		bpLocationRecord.setC_BPartner_ID(bpartnerId);
		bpLocationRecord.setAddress(address);

		save(bpLocationRecord);

		return BPartnerLocationId.ofRepoId(bpartnerId, bpLocationRecord.getC_BPartner_Location_ID());
	}

	private Quantity qty(final String qtyStr, final UomId uomId)
	{
		final I_C_UOM uom = Services.get(IUOMDAO.class).getById(uomId);
		return Quantity.of(qtyStr, uom);
	}
}
