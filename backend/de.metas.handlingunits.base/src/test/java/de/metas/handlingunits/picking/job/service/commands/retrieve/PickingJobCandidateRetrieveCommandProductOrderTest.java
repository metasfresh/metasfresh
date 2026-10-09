package de.metas.handlingunits.picking.job.service.commands.retrieve;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.picking.config.mobileui.MobileUIPickingUserProfileService;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.handlingunits.picking.job.model.PickingJobCandidate;
import de.metas.handlingunits.picking.job.model.PickingJobQuery;
import de.metas.handlingunits.picking.job.repository.MockedPickingJobLoaderSupportingServices;
import de.metas.handlingunits.picking.job.service.external.shipmentschedule.PickingJobShipmentScheduleService;
import de.metas.handlingunits.picking.job_schedule.service.PickingJobScheduleService;
import de.metas.i18n.TranslatableStrings;
import de.metas.inout.ShipmentScheduleId;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.InstantAndOrgId;
import de.metas.organization.OrgId;
import de.metas.picking.api.IPackagingDAO;
import de.metas.picking.api.Packageable;
import de.metas.product.ProductId;
import de.metas.product.ProductValueAndName;
import de.metas.quantity.Quantity;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The launcher caption of a NOT-yet-started picking job lists the product names of its {@link PickingJobCandidate}.
 * The candidate is built from the packageable rows in the order the packageable query streams them, and that
 * query has no ORDER BY key that separates the lines of one sales order — so without an explicit sort the
 * caption's product order is whatever the database returns.
 * <p>
 * The candidate must order its products the same way an already-started job does (see
 * {@code PickingJobLoaderAndSaverProductOrderTest}): by the sales-order line (C_OrderLine.Line), tie-broken by
 * the order-line id. Then a job shows the same caption before and after it is started.
 */
class PickingJobCandidateRetrieveCommandProductOrderTest
{
	private static final OrgId ORG_ID = OrgId.ofRepoId(1);
	private static final BPartnerId CUSTOMER_ID = BPartnerId.ofRepoId(700);
	private static final OrderId SALES_ORDER_ID = OrderId.ofRepoId(500);
	private static final OrderId SALES_ORDER2_ID = OrderId.ofRepoId(600);

	private I_C_UOM uomEach;
	private IPackagingDAO packagingDAO;
	private CountingLoaderSupportingServices loadingSupportingServices;
	private PickingJobAggregationType aggregationType;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		uomEach = BusinessTestHelper.createUomEach();

		packagingDAO = Mockito.mock(IPackagingDAO.class);
		Services.registerService(IPackagingDAO.class, packagingDAO);

		loadingSupportingServices = new CountingLoaderSupportingServices();
		aggregationType = PickingJobAggregationType.SALES_ORDER;
	}

	@Test
	void productNames_followSalesOrderLineOrder_whateverOrderThePackageablesArriveIn()
	{
		// Three axes deliberately disagree, so only a sort on the sales-order line SeqNo (C_OrderLine.Line)
		// yields the expected caption:
		//   arrival order (what the query streams) : P3, P2, P1
		//   order-line id order                    : P2(5031), P3(5032), P1(5033)
		//   sales-order line SeqNo                 : P1(10), P2(20), P3(30)
		final ImmutableList<Packageable> packageablesInArrivalOrder = ImmutableList.of(
				packageable(103, 203, 5032, 30),
				packageable(102, 202, 5031, 20),
				packageable(101, 201, 5033, 10));
		Mockito.when(packagingDAO.stream(Mockito.any())).thenAnswer(invocation -> packageablesInArrivalOrder.stream());

		final ImmutableList<PickingJobCandidate> candidates = newCommand().execute().stream().collect(ImmutableList.toImmutableList());

		assertThat(candidates).hasSize(1);
		assertThat(candidates.get(0).getProducts().getProductNamesJoined(", ").getDefaultValue())
				.isEqualTo("productName-101, productName-102, productName-103");

		// the SeqNo lookup is batched: ONE warm-up for the whole launcher-list load, covering every order line
		assertThat(loadingSupportingServices.warmUpCalls)
				.containsExactly(ImmutableSet.of(
						OrderAndLineId.ofRepoIds(SALES_ORDER_ID.getRepoId(), 5031),
						OrderAndLineId.ofRepoIds(SALES_ORDER_ID.getRepoId(), 5032),
						OrderAndLineId.ofRepoIds(SALES_ORDER_ID.getRepoId(), 5033)));
	}

	@Test
	void sameSalesOrderLineSeqNo_isTieBrokenByOrderLineId()
	{
		final ImmutableList<Packageable> packageablesInArrivalOrder = ImmutableList.of(
				packageable(103, 203, 5033, 10),
				packageable(101, 201, 5031, 10),
				packageable(102, 202, 5032, 10));
		Mockito.when(packagingDAO.stream(Mockito.any())).thenAnswer(invocation -> packageablesInArrivalOrder.stream());

		final ImmutableList<PickingJobCandidate> candidates = newCommand().execute().stream().collect(ImmutableList.toImmutableList());

		assertThat(candidates).hasSize(1);
		assertThat(candidates.get(0).getProducts().getProductNamesJoined(", ").getDefaultValue())
				.isEqualTo("productName-101, productName-102, productName-103");
	}

	@Test
	void productOnSeveralLines_isPlacedByItsFirstLine_andProductWithoutOrderLine_comesLast()
	{
		// P104 has no sales order line -> last. P103 is on lines 30 AND 5 -> placed by line 5, i.e. first.
		final ImmutableList<Packageable> packageablesInArrivalOrder = ImmutableList.of(
				packageable(104, 204, null, 0),
				packageable(102, 202, 5032, 20),
				packageable(103, 203, 5033, 30),
				packageable(101, 201, 5031, 10),
				packageable(103, 205, 5035, 5));
		Mockito.when(packagingDAO.stream(Mockito.any())).thenAnswer(invocation -> packageablesInArrivalOrder.stream());

		final ImmutableList<PickingJobCandidate> candidates = newCommand().execute().stream().collect(ImmutableList.toImmutableList());

		assertThat(candidates).hasSize(1);
		assertThat(candidates.get(0).getProducts().getProductNamesJoined(", ").getDefaultValue())
				.isEqualTo("productName-103, productName-101, productName-102, productName-104");
	}

	@Test
	void twoSalesOrdersInOneLoad_eachCandidateIsOrdered_andAllLinesAreWarmedUpInOneBatch()
	{
		final ImmutableList<Packageable> packageablesInArrivalOrder = ImmutableList.of(
				packageable(SALES_ORDER2_ID, 202, 302, 6032, 20),
				packageable(SALES_ORDER_ID, 103, 203, 5033, 30),
				packageable(SALES_ORDER2_ID, 201, 301, 6031, 10),
				packageable(SALES_ORDER_ID, 101, 201, 5031, 10));
		Mockito.when(packagingDAO.stream(Mockito.any())).thenAnswer(invocation -> packageablesInArrivalOrder.stream());

		final ImmutableList<PickingJobCandidate> candidates = newCommand().execute().stream().collect(ImmutableList.toImmutableList());

		assertThat(candidates)
				.extracting(candidate -> candidate.getProducts().getProductNamesJoined(", ").getDefaultValue())
				.containsExactly(
						"productName-201, productName-202", // SO 600 arrives first
						"productName-101, productName-103");
		assertThat(loadingSupportingServices.warmUpCalls)
				.containsExactly(ImmutableSet.of(
						OrderAndLineId.ofRepoIds(SALES_ORDER_ID.getRepoId(), 5031),
						OrderAndLineId.ofRepoIds(SALES_ORDER_ID.getRepoId(), 5033),
						OrderAndLineId.ofRepoIds(SALES_ORDER2_ID.getRepoId(), 6031),
						OrderAndLineId.ofRepoIds(SALES_ORDER2_ID.getRepoId(), 6032)));
	}

	@Test
	void deliveryLocationBasedAggregation_ordersProductsBySalesOrderLine()
	{
		aggregationType = PickingJobAggregationType.DELIVERY_LOCATION;

		// one delivery location, two sales orders: ordered by Line, then by order line id
		final ImmutableList<Packageable> packageablesInArrivalOrder = ImmutableList.of(
				packageable(SALES_ORDER2_ID, 202, 302, 6032, 20),
				packageable(SALES_ORDER_ID, 101, 201, 5031, 20),
				packageable(SALES_ORDER2_ID, 201, 301, 6031, 10));
		Mockito.when(packagingDAO.stream(Mockito.any())).thenAnswer(invocation -> packageablesInArrivalOrder.stream());

		final ImmutableList<PickingJobCandidate> candidates = newCommand().execute().stream().collect(ImmutableList.toImmutableList());

		assertThat(candidates).hasSize(1);
		assertThat(candidates.get(0).getAggregationType()).isEqualTo(PickingJobAggregationType.DELIVERY_LOCATION);
		assertThat(candidates.get(0).getProducts().getProductNamesJoined(", ").getDefaultValue())
				.isEqualTo("productName-201, productName-101, productName-202");
		assertThat(loadingSupportingServices.warmUpCalls).hasSize(1);
	}

	private PickingJobCandidateRetrieveCommand newCommand()
	{
		final MobileUIPickingUserProfileService configService = Mockito.mock(MobileUIPickingUserProfileService.class);
		Mockito.when(configService.getAggregationType(Mockito.any())).thenReturn(aggregationType);

		return PickingJobCandidateRetrieveCommand.builder()
				.shipmentScheduleService(PickingJobShipmentScheduleService.newInstanceForUnitTesting())
				.configService(configService)
				.pickingJobScheduleService(PickingJobScheduleService.newInstanceForUnitTesting())
				.loadingSupportingServices(loadingSupportingServices)
				.query(PickingJobQuery.builder().userId(UserId.ofRepoId(1)).build())
				.build();
	}

	private Packageable packageable(final int productRepoId, final int shipmentScheduleRepoId, @Nullable final Integer salesOrderLineRepoId, final int salesOrderLineSeqNo)
	{
		return packageable(SALES_ORDER_ID, productRepoId, shipmentScheduleRepoId, salesOrderLineRepoId, salesOrderLineSeqNo);
	}

	private Packageable packageable(
			@NonNull final OrderId salesOrderId,
			final int productRepoId,
			final int shipmentScheduleRepoId,
			@Nullable final Integer salesOrderLineRepoId,
			final int salesOrderLineSeqNo)
	{
		if (salesOrderLineRepoId != null)
		{
			loadingSupportingServices.setSalesOrderLineSeqNo(OrderAndLineId.ofRepoIds(salesOrderId.getRepoId(), salesOrderLineRepoId), salesOrderLineSeqNo);
		}

		final Quantity one = Quantity.of("1", uomEach);
		final Quantity zero = Quantity.zero(uomEach);
		final BPartnerLocationId customerLocationId = BPartnerLocationId.ofRepoId(CUSTOMER_ID, 701);
		final InstantAndOrgId preparationDate = InstantAndOrgId.ofInstant(Instant.parse("2026-10-09T08:00:00Z"), ORG_ID);
		return Packageable.builder()
				.orgId(ORG_ID)
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(shipmentScheduleRepoId))
				.qtyOrdered(one)
				.qtyToDeliver(one)
				.qtyDelivered(zero)
				.qtyPickedAndDelivered(zero)
				.qtyPickedNotDelivered(zero)
				.qtyPickedPlanned(zero)
				.customerId(CUSTOMER_ID)
				.customerName("Customer")
				.customerLocationId(customerLocationId)
				.handoverLocationId(customerLocationId)
				.warehouseId(WarehouseId.ofRepoId(1))
				.productId(ProductId.ofRepoId(productRepoId))
				.productValueAndName(ProductValueAndName.of("productValue-" + productRepoId, TranslatableStrings.anyLanguage("productName-" + productRepoId)))
				.asiId(AttributeSetInstanceId.NONE)
				.salesOrderId(salesOrderId)
				.salesOrderDocumentNo("SO-" + salesOrderId.getRepoId())
				.salesOrderLineIdOrNull(salesOrderLineRepoId != null ? OrderLineId.ofRepoId(salesOrderLineRepoId) : null)
				.preparationDate(preparationDate)
				.deliveryDate(preparationDate)
				.build();
	}

	private static class CountingLoaderSupportingServices extends MockedPickingJobLoaderSupportingServices
	{
		private final List<Set<OrderAndLineId>> warmUpCalls = new ArrayList<>();
		private final Set<OrderAndLineId> warmedUp = new HashSet<>();

		@Override
		public void warmUpSalesOrderLineSeqNosCache(@NonNull final Set<OrderAndLineId> orderAndLineIds)
		{
			warmUpCalls.add(ImmutableSet.copyOf(orderAndLineIds));
			warmedUp.addAll(orderAndLineIds);
		}

		/**
		 * Fails on a line that was not warmed up, because in production that would be one extra query per line.
		 */
		@Override
		public int getSalesOrderLineSeqNo(@NonNull final OrderAndLineId orderAndLineId)
		{
			if (!warmedUp.contains(orderAndLineId))
			{
				throw new AssertionError("Sales order line SeqNo read without a batched warm-up: " + orderAndLineId);
			}
			return super.getSalesOrderLineSeqNo(orderAndLineId);
		}
	}
}
