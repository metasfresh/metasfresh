package de.metas.order.compensationGroup;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.compensationGroup.contract.ContractSettingsTakeOverId;
import de.metas.currency.CurrencyPrecision;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Order_CompensationGroup;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * A take-over record id carried by a {@link GroupTemplateCompensationLine} must reach the created compensation
 * {@code C_OrderLine.C_CompensationGroup_ContractSettings_TakeOver_ID}; a line without it keeps 0.
 */
public class OrderGroupRepository_TakeOver_Test
{
	private static final ContractSettingsTakeOverId TAKE_OVER_ID = ContractSettingsTakeOverId.ofRepoId(540123);

	private ProductId productId;
	private I_C_Order order;
	private GroupId groupId;
	private OrderGroupRepository repo;
	private GroupCompensationLineCreateRequestFactory requestFactory;
	private IQueryBL queryBL;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		queryBL = Services.get(IQueryBL.class);

		final I_C_UOM uom = newInstance(I_C_UOM.class);
		saveRecord(uom);

		final I_M_Product product = newInstance(I_M_Product.class);
		product.setC_UOM_ID(uom.getC_UOM_ID());
		saveRecord(product);
		productId = ProductId.ofRepoId(product.getM_Product_ID());

		order = newInstance(I_C_Order.class);
		order.setC_BPartner_ID(1);
		saveRecord(order);

		final I_C_Order_CompensationGroup groupHeader = newInstance(I_C_Order_CompensationGroup.class);
		groupHeader.setC_Order_ID(order.getC_Order_ID());
		saveRecord(groupHeader);
		groupId = OrderGroupRepository.createGroupId(OrderId.ofRepoId(order.getC_Order_ID()), groupHeader.getC_Order_CompensationGroup_ID());

		Services.registerService(IOrderLineBL.class, new OrderGroupRepositoryTest.StubOrderLineBL(order));

		requestFactory = new GroupCompensationLineCreateRequestFactory();
		repo = new OrderGroupRepository(Mockito.mock(GroupCompensationLineCreateRequestFactory.class), Optional.empty(), GroupTemplateRepository.newInstanceForUnitTesting(), Optional.empty());
	}

	@Test
	void lineWithTakeOverId_carriesIdToOrderLine()
	{
		final I_C_OrderLine orderLine = createAndSaveCompensationLine(
				GroupTemplateCompensationLine.builder().productId(productId).percentage(Percent.of(10)).takeOverId(TAKE_OVER_ID).build());

		assertThat(orderLine.getC_CompensationGroup_ContractSettings_TakeOver_ID()).isEqualTo(TAKE_OVER_ID.getRepoId());
	}

	@Test
	void lineWithoutTakeOverId_keepsZero()
	{
		final I_C_OrderLine orderLine = createAndSaveCompensationLine(
				GroupTemplateCompensationLine.builder().productId(productId).percentage(Percent.of(10)).build());

		assertThat(orderLine.getC_CompensationGroup_ContractSettings_TakeOver_ID()).isZero();
	}

	private I_C_OrderLine createAndSaveCompensationLine(final GroupTemplateCompensationLine templateLine)
	{
		final Group group = Group.builder()
				.groupId(groupId)
				.pricePrecision(CurrencyPrecision.TWO)
				.amountPrecision(CurrencyPrecision.TWO)
				.bpartnerId(BPartnerId.ofRepoId(order.getC_BPartner_ID()))
				.soTrx(SOTrx.SALES)
				.regularLine(GroupRegularLine.builder().lineNetAmt(new BigDecimal("100")).build())
				.build();
		group.addNewCompensationLine(requestFactory.createGroupCompensationLineCreateRequest(templateLine, group));

		final OrderLinesStorage storage = OrderLinesStorage.builder()
				.groupId(groupId)
				.performDatabaseChanges(true)
				.build();
		repo.saveGroup(group, storage);

		return queryBL.createQueryBuilder(I_C_OrderLine.class)
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_IsGroupCompensationLine, true)
				.create()
				.firstOnlyNotNull(I_C_OrderLine.class);
	}
}
