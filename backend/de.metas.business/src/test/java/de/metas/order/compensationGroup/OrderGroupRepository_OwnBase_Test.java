package de.metas.order.compensationGroup;

import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyPrecision;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Order_CompensationGroup;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * A compensation line with its own base stores its applies-to category in {@code C_OrderLine.GroupCompensation_Product_Category_ID}
 * and gets it back from there on reload; a line without own base leaves the column empty.
 */
public class OrderGroupRepository_OwnBase_Test
{
	private ProductId productId;
	private ProductCategoryId categoryId;
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

		final I_M_Product_Category category = newInstance(I_M_Product_Category.class);
		saveRecord(category);
		categoryId = ProductCategoryId.ofRepoId(category.getM_Product_Category_ID());

		final I_M_Product product = newInstance(I_M_Product.class);
		product.setC_UOM_ID(uom.getC_UOM_ID());
		product.setM_Product_Category_ID(categoryId.getRepoId());
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
		repo = OrderGroupRepository.newInstanceForUnitTesting();
	}

	@Test
	void ownBaseLine_storesItsCategoryOnTheOrderLine()
	{
		final I_C_OrderLine orderLine = createAndSaveCompensationLine(
				GroupTemplateCompensationLine.builder().productId(productId).percentage(Percent.of(10)).appliesToProductCategoryId(categoryId).ownBase(true).build());

		assertThat(orderLine.getGroupCompensation_Product_Category_ID()).isEqualTo(categoryId.getRepoId());
	}

	@Test
	void ownBaseLine_reloadedWithTheStoredCategoryAsBase()
	{
		final I_C_OrderLine regularLine = newInstance(I_C_OrderLine.class);
		regularLine.setC_Order_ID(order.getC_Order_ID());
		regularLine.setM_Product_ID(productId.getRepoId());
		regularLine.setC_Order_CompensationGroup_ID(groupId.getOrderCompensationGroupId());
		regularLine.setLineNetAmt(new BigDecimal("100"));
		saveRecord(regularLine);
		createAndSaveCompensationLine(
				GroupTemplateCompensationLine.builder().productId(productId).percentage(Percent.of(10)).appliesToProductCategoryId(categoryId).ownBase(true).build());

		final GroupCompensationLine reloadedLine = repo.retrieveGroup(groupId).getCompensationLines().get(0);

		assertThat(reloadedLine.hasOwnBase()).isTrue();
		assertThat(reloadedLine.getAppliesToProductCategoryId()).isEqualTo(categoryId);
	}

	@Test
	void lineWithoutOwnBase_leavesTheColumnEmpty()
	{
		final I_C_OrderLine orderLine = createAndSaveCompensationLine(
				GroupTemplateCompensationLine.builder().productId(productId).percentage(Percent.of(10)).appliesToProductCategoryId(categoryId).build());

		assertThat(ProductCategoryId.ofRepoIdOrNull(orderLine.getGroupCompensation_Product_Category_ID())).isNull();
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
