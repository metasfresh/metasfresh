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
 * Unit tests for {@link DDOrderCandidateProcessCommand} — see each test method for its case.
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
	 * Header locator aggregation is OFF by default ({@code DDOrderAggregation.header.byLocatorTo=false}):
	 * two candidates that differ only by {@code targetLocatorId} collapse into ONE DD_Order, yet still get a
	 * separate line each because the line aggregation key always carries the locator (line-level is unconditional).
	 */
	@Test
	void twoGrounds_byLocatorToDisabled_singleDDOrderWithTwoLines()
	{
		processTwoGroundCandidates(false);

		final List<I_DD_Order> orders = queryOrders();
		assertThat(orders).hasSize(1);
		assertThat(queryLines(orders.get(0))).hasSize(2);
	}

	/**
	 * With {@code DDOrderAggregation.header.byLocatorTo=true}: the same two candidates (differing only by
	 * {@code targetLocatorId}, i.e. different grounds) produce TWO DD_Orders, each with a single line.
	 */
	@Test
	void twoGrounds_byLocatorToEnabled_twoSingleLineDDOrders()
	{
		processTwoGroundCandidates(true);

		final List<I_DD_Order> orders = queryOrders();
		assertThat(orders).hasSize(2);
		assertThat(orders).allSatisfy(order -> assertThat(queryLines(order)).hasSize(1));
	}

	/**
	 * Header locator aggregation is OFF by default ({@code DDOrderAggregation.header.byLocatorFrom=false}):
	 * two candidates that differ only by {@code sourceLocatorId} collapse into ONE DD_Order with a separate
	 * line each (line key always carries the locator).
	 */
	@Test
	void twoSourceGrounds_byLocatorFromDisabled_singleDDOrderWithTwoLines()
	{
		processTwoSourceGroundCandidates(false);

		final List<I_DD_Order> orders = queryOrders();
		assertThat(orders).hasSize(1);
		assertThat(queryLines(orders.get(0))).hasSize(2);
	}

	/**
	 * With {@code DDOrderAggregation.header.byLocatorFrom=true}: the same two candidates (differing only by
	 * {@code sourceLocatorId}) produce TWO DD_Orders, each with a single line.
	 */
	@Test
	void twoSourceGrounds_byLocatorFromEnabled_twoSingleLineDDOrders()
	{
		processTwoSourceGroundCandidates(true);

		final List<I_DD_Order> orders = queryOrders();
		assertThat(orders).hasSize(2);
		assertThat(orders).allSatisfy(order -> assertThat(queryLines(order)).hasSize(1));
	}

	/**
	 * Byte-for-byte control for existing (null-locator) customers: two candidates that have no
	 * source/target locator at all must still collapse into ONE DD_Order with a single aggregated line
	 * (both locators are {@code null} in the aggregation key, so they compare equal), and the generated
	 * line falls back -- per side -- to the warehouse's get-create default locator (stubbed here to repoId
	 * 540003 for every warehouse).
	 */
	@Test
	void nullLocators_defaultAndAggregated()
	{
		processTwoNullLocatorCandidates();

		final List<I_DD_Order> orders = queryOrders();
		assertThat(orders).hasSize(1);

		final List<I_DD_OrderLine> lines = queryLines(orders.get(0));
		assertThat(lines).hasSize(1);
		assertThat(lines.get(0).getM_Locator_ID()).isEqualTo(540003);
		assertThat(lines.get(0).getM_LocatorTo_ID()).isEqualTo(540003);
	}

	/**
	 * With header locator aggregation ENABLED ({@code byLocatorFrom=byLocatorTo=true}): a candidate that carries
	 * explicit locators must NOT collapse with an otherwise-identical candidate that has none. The explicit-locator
	 * candidate's header key holds its concrete locators (1001/1002); the null-locator candidate's key holds the
	 * warehouse-default locators (540003/540003), so the two headers compare unequal and each gets its own DD_Order.
	 * The null-locator line still falls back to the warehouse default (540003).
	 */
	@Test
	void locatorPresentAndAbsent_byLocatorEnabled_notCollapsed()
	{
		final DDOrderCandidate base = DDOrderCandidateRepositoryTest.newFullyFilled();
		final DDOrderCandidate withLocators = base.toBuilder()
				.sourceLocatorId(LocatorId.ofRepoId(60, 1001))
				.targetLocatorId(LocatorId.ofRepoId(70, 1002))
				.build();
		final DDOrderCandidate withoutLocators = base.toBuilder().build();
		ddOrderCandidateRepository.save(withLocators);
		ddOrderCandidateRepository.save(withoutLocators);

		commandBuilder
				.aggregationConfig(DDOrderCandidateProcessCommand.AggregationConfig.builder()
						.aggregateBySalesOrderId(true)
						.aggregateByPPOrderRef(true)
						.aggregateBySalesOrderLineId(true)
						.aggregateByLocatorFrom(true)
						.aggregateByLocatorTo(true)
						.build())
				.request(DDOrderCandidateProcessRequest.builder()
						.userId(UserId.METASFRESH)
						.candidates(ImmutableList.of(withLocators, withoutLocators))
						.build())
				.build()
				.execute();

		final List<I_DD_Order> orders = queryOrders();
		assertThat(orders).hasSize(2);
		assertThat(orders).allSatisfy(order -> assertThat(queryLines(order)).hasSize(1));
	}

	/**
	 * Saves two candidates that differ only by their {@code targetLocatorId} (two grounds under the same target
	 * warehouse) and runs the command.
	 * <p>
	 * Both are derived from a single {@code base} candidate so they share the same UOM instance (each
	 * {@code newFullyFilled()} call creates a fresh UOM with a distinct id, which would otherwise split the
	 * line aggregation by UOM and defeat the "differ only by locator" intent).
	 */
	private void processTwoGroundCandidates(final boolean aggregateByLocatorTo)
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
						.aggregateByLocatorTo(aggregateByLocatorTo)
						.build())
				.request(DDOrderCandidateProcessRequest.builder()
						.userId(UserId.METASFRESH)
						.candidates(ImmutableList.of(move1, move2))
						.build())
				.build()
				.execute();
	}

	/**
	 * Like {@link #processTwoGroundCandidates(boolean)} but the two candidates differ only by their
	 * {@code sourceLocatorId} (two source grounds under the same source warehouse); runs with the given
	 * {@code aggregateByLocatorFrom} header setting.
	 */
	private void processTwoSourceGroundCandidates(final boolean aggregateByLocatorFrom)
	{
		final DDOrderCandidate base = DDOrderCandidateRepositoryTest.newFullyFilled();
		final DDOrderCandidate move1 = base.toBuilder()
				.sourceLocatorId(LocatorId.ofRepoId(60, 1001))
				.targetLocatorId(LocatorId.ofRepoId(70, 1002))
				.build();
		final DDOrderCandidate move2 = base.toBuilder()
				.sourceLocatorId(LocatorId.ofRepoId(60, 1004))
				.targetLocatorId(LocatorId.ofRepoId(70, 1002))
				.build();
		ddOrderCandidateRepository.save(move1);
		ddOrderCandidateRepository.save(move2);

		commandBuilder
				.aggregationConfig(DDOrderCandidateProcessCommand.AggregationConfig.builder()
						.aggregateBySalesOrderId(true)
						.aggregateByPPOrderRef(true)
						.aggregateBySalesOrderLineId(true)
						.aggregateByLocatorFrom(aggregateByLocatorFrom)
						.build())
				.request(DDOrderCandidateProcessRequest.builder()
						.userId(UserId.METASFRESH)
						.candidates(ImmutableList.of(move1, move2))
						.build())
				.build()
				.execute();
	}

	/**
	 * Saves two candidates with no source/target locator at all (both {@code null}), sharing the same UOM
	 * instance (see {@link #processTwoGroundCandidates()}), and runs the command.
	 */
	private void processTwoNullLocatorCandidates()
	{
		final DDOrderCandidate base = DDOrderCandidateRepositoryTest.newFullyFilled();
		final DDOrderCandidate move1 = base.toBuilder().build();
		final DDOrderCandidate move2 = base.toBuilder().build();
		ddOrderCandidateRepository.save(move1);
		ddOrderCandidateRepository.save(move2);

		commandBuilder
				.aggregationConfig(DDOrderCandidateProcessCommand.AggregationConfig.builder()
						.aggregateBySalesOrderId(true)
						.aggregateByPPOrderRef(true)
						.aggregateBySalesOrderLineId(true)
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
