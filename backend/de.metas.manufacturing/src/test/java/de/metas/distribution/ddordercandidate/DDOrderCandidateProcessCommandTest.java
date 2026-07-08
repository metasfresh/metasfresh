package de.metas.distribution.ddordercandidate;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelService;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocumentBL;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.notification.INotificationBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.uom.IUOMConversionBL;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.util.Env;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.X_DD_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Regression test for the NPE that occurred while processing a {@link DDOrderCandidate} whose
 * {@code productPlanningId} is {@code null} (no product planning).
 * <p>
 * The header-record creation used to dereference {@code productPlanningDAO.getById(null)} unconditionally;
 * with no product planning this threw a {@link NullPointerException}. The fix guards the lookup and clears
 * {@code PP_Product_Planning_ID} / {@code AD_User_ID} / {@code SalesRep_ID} with the {@code -1} sentinel.
 * <p>
 * Also covers the optional {@code aggregateByProductId} header-aggregation dimension: with the flag off,
 * candidates of different products share one DD_Order (one line each); with it on, each product gets its
 * own DD_Order.
 * <p>
 * Also covers locator handling: the candidate's {@code sourceLocatorId}/{@code targetLocatorId} must be
 * carried onto the generated line, and the optional {@code aggregateByLocatorId} dimension must give each
 * distinct ground (locator) its own DD_Order when on, while candidates keep collapsing into one DD_Order
 * when off.
 */
class DDOrderCandidateProcessCommandTest
{
	private static final ClientId CLIENT_ID = ClientId.ofRepoId(9);

	private IProductPlanningDAO productPlanningDAO;
	private DDOrderCandidateRepository ddOrderCandidateRepository;

	private DDOrderCandidateProcessCommand.DDOrderCandidateProcessCommandBuilder commandBuilder;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		Env.setClientId(Env.getCtx(), CLIENT_ID);
		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_User_ID, UserId.METASFRESH.getRepoId());

		// The notification producer is created internally by the command and grabs INotificationBL from Services.
		Services.registerService(INotificationBL.class, mock(INotificationBL.class));

		this.ddOrderCandidateRepository = new DDOrderCandidateRepository();

		// Collaborators are mocked at the command's seam so the test stays a pure unit test (no DB masterdata).
		// Note: productPlanningDAO is mocked and never stubbed for getById -> on the *old* (buggy) code,
		// getById(null) returns null and the subsequent getPlannerId() throws NPE; the fix avoids that call.
		this.productPlanningDAO = mock(IProductPlanningDAO.class);

		// The real low-level service is used so the in-memory persistence assigns DD_Order(_Line) PKs,
		// letting createLine() run to completion (mocking save() would leave DD_Order_ID=0 and break it).
		final DDOrderLowLevelService ddOrderLowLevelService = new DDOrderLowLevelService(new DDOrderLowLevelDAO());

		final IOrgDAO orgDAO = mock(IOrgDAO.class);
		when(orgDAO.getClientIdByOrgId(any())).thenReturn(CLIENT_ID);

		final IDocTypeDAO docTypeDAO = mock(IDocTypeDAO.class);
		when(docTypeDAO.getDocTypeId(any(DocTypeQuery.class))).thenReturn(DocTypeId.ofRepoId(540000));

		final IWarehouseBL warehouseBL = mock(IWarehouseBL.class);
		when(warehouseBL.getInTransitWarehouseId(any())).thenReturn(WarehouseId.ofRepoId(540001));
		when(warehouseBL.getOrCreateDefaultLocatorId(any())).thenReturn(LocatorId.ofRepoId(540002, 540003));

		final IUOMConversionBL uomConversionBL = mock(IUOMConversionBL.class);
		when(uomConversionBL.convertToProductUOM(any(), any(ProductId.class)))
				.thenAnswer(invocation -> invocation.getArgument(0));

		final IOrderLineBL orderLineBL = mock(IOrderLineBL.class);
		when(orderLineBL.getBPartnerId(any(OrderAndLineId.class))).thenReturn(Optional.<BPartnerId>empty());

		final IBPartnerOrgBL bpartnerOrgBL = mock(IBPartnerOrgBL.class);
		when(bpartnerOrgBL.retrieveOrgBPLocationId(any(OrgId.class))).thenReturn((BPartnerLocationId)null);

		this.commandBuilder = DDOrderCandidateProcessCommand.builder()
				.ddOrderLowLevelService(ddOrderLowLevelService)
				.ddOrderCandidateService(mock(DDOrderCandidateService.class))
				.orgDAO(orgDAO)
				.docTypeDAO(docTypeDAO)
				.documentBL(mock(IDocumentBL.class))
				.productPlanningDAO(productPlanningDAO)
				.bpartnerOrgBL(bpartnerOrgBL)
				.warehouseBL(warehouseBL)
				.uomConversionBL(uomConversionBL)
				.orderLineBL(orderLineBL)
				.aggregationConfig(DDOrderCandidateProcessCommand.AggregationConfig.builder()
						.aggregateBySalesOrderId(true)
						.aggregateByPPOrderRef(true)
						.aggregateBySalesOrderLineId(true)
						.build());
	}

	@Test
	void process_candidateWithoutProductPlanning_doesNotThrowAndClearsProductPlanning()
	{
		final DDOrderCandidate candidate = DDOrderCandidateRepositoryTest.newFullyFilled().toBuilder()
				.productPlanningId(null)
				.build();
		ddOrderCandidateRepository.save(candidate); // assigns the id required by getIdNotNull()

		final DDOrderCandidateProcessCommand command = commandBuilder
				.request(DDOrderCandidateProcessRequest.builder()
						.userId(UserId.METASFRESH)
						.candidates(ImmutableList.of(candidate))
						.build())
				.build();

		assertThatCode(command::execute).doesNotThrowAnyException();

		// productPlanningDAO must not be queried at all when there is no product planning
		verify(productPlanningDAO, never()).getById(any());

		final I_DD_Order header = Services.get(IQueryBL.class)
				.createQueryBuilder(I_DD_Order.class)
				.create()
				.firstOnlyNotNull(I_DD_Order.class);
		assertThat(header.getPP_Product_Planning_ID()).isEqualTo(-1);
		assertThat(header.getAD_User_ID()).isEqualTo(-1);
		assertThat(header.getSalesRep_ID()).isEqualTo(-1);
		assertThat(header.getDocStatus()).isEqualTo(X_DD_Order.DOCSTATUS_Drafted);
	}

	/**
	 * Control: with {@code aggregateByProductId=false} (today's behaviour), two candidates that differ only by
	 * product collapse into ONE DD_Order carrying one line per product.
	 */
	@Test
	void process_twoProducts_byProductIdDisabled_singleDDOrderWithTwoLines()
	{
		processTwoProductCandidates(false);

		final List<I_DD_Order> orders = queryOrders();
		assertThat(orders).hasSize(1);
		assertThat(queryLines(orders.get(0))).hasSize(2);
	}

	/**
	 * Target: with {@code aggregateByProductId=true}, the same two candidates produce TWO DD_Orders, each with a
	 * single line (one product — and therefore one UOM — per order).
	 */
	@Test
	void process_twoProducts_byProductIdEnabled_twoSingleLineDDOrders()
	{
		processTwoProductCandidates(true);

		final List<I_DD_Order> orders = queryOrders();
		assertThat(orders).hasSize(2);
		assertThat(orders).allSatisfy(order -> assertThat(queryLines(order)).hasSize(1));
	}

	/**
	 * A candidate that carries its own {@code sourceLocatorId}/{@code targetLocatorId} must have those locators
	 * carried onto the generated DD_Order line, rather than falling back to the warehouse default locator
	 * (which the harness stubs to locator repoId 540003 for every warehouse).
	 */
	@Test
	void locatorsCarriedOntoLine()
	{
		final LocatorId sourceLocatorId = LocatorId.ofRepoId(60, 1001);
		final LocatorId targetLocatorId = LocatorId.ofRepoId(70, 1002);

		final DDOrderCandidate candidate = DDOrderCandidateRepositoryTest.newFullyFilled().toBuilder()
				.sourceLocatorId(sourceLocatorId)
				.targetLocatorId(targetLocatorId)
				.build();
		ddOrderCandidateRepository.save(candidate);

		commandBuilder
				.aggregationConfig(DDOrderCandidateProcessCommand.AggregationConfig.builder()
						.aggregateBySalesOrderId(true)
						.aggregateByPPOrderRef(true)
						.aggregateBySalesOrderLineId(true)
						.aggregateByLocatorId(true)
						.build())
				.request(DDOrderCandidateProcessRequest.builder()
						.userId(UserId.METASFRESH)
						.candidates(ImmutableList.of(candidate))
						.build())
				.build()
				.execute();

		final List<I_DD_Order> orders = queryOrders();
		assertThat(orders).hasSize(1);

		final List<I_DD_OrderLine> lines = queryLines(orders.get(0));
		assertThat(lines).hasSize(1);
		assertThat(lines.get(0).getM_Locator_ID()).isEqualTo(sourceLocatorId.getRepoId());
		assertThat(lines.get(0).getM_LocatorTo_ID()).isEqualTo(targetLocatorId.getRepoId());
	}

	/**
	 * With {@code aggregateByLocatorId=true}, two candidates that differ only by {@code targetLocatorId}
	 * (i.e. different grounds) must each get their own DD_Order with a single line, rather than collapsing
	 * into one DD_Order with a single aggregated line.
	 */
	@Test
	void oneOrderOneLinePerMove()
	{
		processTwoGroundCandidates(true);

		final List<I_DD_Order> orders = queryOrders();
		assertThat(orders).hasSize(2);
		assertThat(orders).allSatisfy(order -> assertThat(queryLines(order)).hasSize(1));
	}

	/**
	 * Control for {@link #oneOrderOneLinePerMove()}: with {@code aggregateByLocatorId=false} the two per-ground
	 * candidates collapse into ONE DD_Order with a single aggregated line (locators are not part of the
	 * aggregation key when the flag is off).
	 */
	@Test
	void flagOff_unchanged()
	{
		processTwoGroundCandidates(false);

		final List<I_DD_Order> orders = queryOrders();
		assertThat(orders).hasSize(1);

		final List<I_DD_OrderLine> lines = queryLines(orders.get(0));
		assertThat(lines).hasSize(1);
		// AC4 byte-for-byte: with the knob off the candidate's own locators (1001/1002/1003) are ignored
		// and the warehouse default locator (stubbed to repoId 540003 for every warehouse) is used — the
		// exact prior behaviour of createLine before this change.
		assertThat(lines.get(0).getM_Locator_ID()).isEqualTo(540003);
		assertThat(lines.get(0).getM_LocatorTo_ID()).isEqualTo(540003);
	}

	/**
	 * Saves two candidates that differ only by their {@code targetLocatorId} (two grounds under the same target
	 * warehouse) and runs the command with the given {@code aggregateByLocatorId} setting.
	 * <p>
	 * Both are derived from a single {@code base} candidate so they share the same UOM instance (each
	 * {@code newFullyFilled()} call creates a fresh UOM with a distinct id, which would otherwise split the
	 * line aggregation by UOM and defeat the "differ only by locator" intent).
	 */
	private void processTwoGroundCandidates(final boolean aggregateByLocatorId)
	{
		final DDOrderCandidate base = DDOrderCandidateRepositoryTest.newFullyFilled();
		final DDOrderCandidate move1 = base.toBuilder()
				.sourceLocatorId(LocatorId.ofRepoId(60, 1001))
				.targetLocatorId(LocatorId.ofRepoId(70, 1002))
				.build();
		final DDOrderCandidate move2 = base.toBuilder()
				.sourceLocatorId(LocatorId.ofRepoId(60, 1001))
				.targetLocatorId(LocatorId.ofRepoId(70, 1003))
				.build();
		ddOrderCandidateRepository.save(move1);
		ddOrderCandidateRepository.save(move2);

		commandBuilder
				.aggregationConfig(DDOrderCandidateProcessCommand.AggregationConfig.builder()
						.aggregateBySalesOrderId(true)
						.aggregateByPPOrderRef(true)
						.aggregateBySalesOrderLineId(true)
						.aggregateByLocatorId(aggregateByLocatorId)
						.build())
				.request(DDOrderCandidateProcessRequest.builder()
						.userId(UserId.METASFRESH)
						.candidates(ImmutableList.of(move1, move2))
						.build())
				.build()
				.execute();
	}

	private void processTwoProductCandidates(final boolean aggregateByProductId)
	{
		final DDOrderCandidate product20 = DDOrderCandidateRepositoryTest.newFullyFilled().toBuilder()
				.productId(ProductId.ofRepoId(20))
				.build();
		final DDOrderCandidate product21 = DDOrderCandidateRepositoryTest.newFullyFilled().toBuilder()
				.productId(ProductId.ofRepoId(21))
				.build();
		ddOrderCandidateRepository.save(product20);
		ddOrderCandidateRepository.save(product21);

		commandBuilder
				.aggregationConfig(DDOrderCandidateProcessCommand.AggregationConfig.builder()
						.aggregateBySalesOrderId(true)
						.aggregateByPPOrderRef(true)
						.aggregateBySalesOrderLineId(true)
						.aggregateByProductId(aggregateByProductId)
						.build())
				.request(DDOrderCandidateProcessRequest.builder()
						.userId(UserId.METASFRESH)
						.candidates(ImmutableList.of(product20, product21))
						.build())
				.build()
				.execute();
	}

	private static List<I_DD_Order> queryOrders()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_DD_Order.class)
				.create()
				.list(I_DD_Order.class);
	}

	private static List<I_DD_OrderLine> queryLines(final I_DD_Order order)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_DD_OrderLine.class)
				.addEqualsFilter(I_DD_OrderLine.COLUMNNAME_DD_Order_ID, order.getDD_Order_ID())
				.create()
				.list(I_DD_OrderLine.class);
	}
}
