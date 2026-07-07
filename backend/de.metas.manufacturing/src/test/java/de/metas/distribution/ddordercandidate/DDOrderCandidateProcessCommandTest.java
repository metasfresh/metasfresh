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
import org.eevolution.model.X_DD_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
