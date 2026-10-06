package de.metas.invoicecandidate.compensationGroup;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.order.OrderId;
import de.metas.order.compensationGroup.Group;
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequestFactory;
import de.metas.order.compensationGroup.GroupId;
import de.metas.order.compensationGroup.GroupRegularLine;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.order.compensationGroup.PackingMaterialProductCategoryProvider;
import de.metas.order.model.I_C_CompensationGroup_Schema;
import de.metas.order.model.I_C_CompensationGroup_SchemaLine;
import de.metas.product.ProductCategoryId;
import de.metas.uom.UomId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Order_CompensationGroup;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests how {@link InvoiceCandidateGroupRepository} gives the group's regular lines the packing-material categories of
 * their order line's packing instruction, and that it resolves them only when a discount line asks for them.
 */
class InvoiceCandidateGroupRepositoryPackingTest
{
	private static final int PI_ITEM_PRODUCT_ID = 4711;
	/** distinct net amounts identify the regular lines, whatever order the group lists them in */
	private static final BigDecimal PACKED_LINE_NET_AMT = new BigDecimal("100");
	private static final BigDecimal UNPACKED_LINE_NET_AMT = new BigDecimal("200");

	private InvoiceCandidateGroupRepository repo;
	private UomId uomId;
	private CountingProvider provider;
	private ProductCategoryId cartonCategoryId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final I_C_UOM uomRecord = newInstance(I_C_UOM.class);
		saveRecord(uomRecord);
		uomId = UomId.ofRepoId(uomRecord.getC_UOM_ID());

		cartonCategoryId = ProductCategoryId.ofRepoId(1000123);
		provider = new CountingProvider(ImmutableMap.of(HUPIItemProductId.ofRepoId(PI_ITEM_PRODUCT_ID), ImmutableSet.of(cartonCategoryId)));
		SpringContextHolder.registerJUnitBean(PackingMaterialProductCategoryProvider.class, provider);

		repo = new InvoiceCandidateGroupRepository(Mockito.mock(GroupCompensationLineCreateRequestFactory.class));
	}

	@Test
	void regularLines_carryThePackingMaterialCategoriesOfTheirOrderLinesPacking()
	{
		final Group group = retrieveGroup(true);

		assertThat(provider.calls.get()).isEqualTo(1);
		assertThat(group.getRegularLines()).hasSize(2);
		final GroupRegularLine packedLine = findRegularLineByNetAmt(group, PACKED_LINE_NET_AMT);
		final GroupRegularLine unpackedLine = findRegularLineByNetAmt(group, UNPACKED_LINE_NET_AMT);
		assertThat(packedLine.getPackingMaterialProductCategoryIds()).containsExactly(cartonCategoryId);
		assertThat(unpackedLine.getPackingMaterialProductCategoryIds()).isEmpty();
	}

	@Test
	void packingMaterialCategories_notResolvedWhenNoDiscountLineAsksForThem()
	{
		final Group group = retrieveGroup(false);

		assertThat(provider.calls.get()).isZero();
		assertThat(group.getRegularLines()).hasSize(2);
		assertThat(group.getRegularLines()).allSatisfy(line -> assertThat(line.getPackingMaterialProductCategoryIds()).isEmpty());
	}

	private static GroupRegularLine findRegularLineByNetAmt(final Group group, final BigDecimal netAmt)
	{
		return group.getRegularLines().stream()
				.filter(line -> line.getLineNetAmt().compareTo(netAmt) == 0)
				.collect(com.google.common.collect.MoreCollectors.onlyElement());
	}

	/** a group of two regular candidates (the first one's order line has a packing instruction) and one discount candidate */
	private Group retrieveGroup(final boolean discountLineHasPackingCategory)
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setC_BPartner_ID(1);
		saveRecord(order);

		final I_M_Product goodsProduct = newProduct();
		final I_M_Product discountProduct = newProduct();

		final I_C_CompensationGroup_Schema schema = newInstance(I_C_CompensationGroup_Schema.class);
		saveRecord(schema);
		final I_C_CompensationGroup_SchemaLine schemaLine = newInstance(I_C_CompensationGroup_SchemaLine.class);
		schemaLine.setC_CompensationGroup_Schema_ID(schema.getC_CompensationGroup_Schema_ID());
		schemaLine.setM_Product_ID(discountProduct.getM_Product_ID());
		if (discountLineHasPackingCategory)
		{
			schemaLine.setM_Product_Category_PackingMaterial_ID(cartonCategoryId.getRepoId());
		}
		saveRecord(schemaLine);

		final I_C_Order_CompensationGroup groupHeader = newInstance(I_C_Order_CompensationGroup.class);
		groupHeader.setC_Order_ID(order.getC_Order_ID());
		groupHeader.setC_CompensationGroup_Schema_ID(schema.getC_CompensationGroup_Schema_ID());
		saveRecord(groupHeader);
		final int groupRepoId = groupHeader.getC_Order_CompensationGroup_ID();

		final I_C_OrderLine packedOrderLine = newOrderLine(order, goodsProduct, groupRepoId, PI_ITEM_PRODUCT_ID);
		final I_C_OrderLine unpackedOrderLine = newOrderLine(order, goodsProduct, groupRepoId, 0);
		final I_C_OrderLine discountOrderLine = newOrderLine(order, discountProduct, groupRepoId, 0);
		discountOrderLine.setIsGroupCompensationLine(true);
		discountOrderLine.setC_CompensationGroup_SchemaLine_ID(schemaLine.getC_CompensationGroup_SchemaLine_ID());
		saveRecord(discountOrderLine);

		newRegularCandidate(order, goodsProduct, packedOrderLine, groupRepoId, PACKED_LINE_NET_AMT);
		newRegularCandidate(order, goodsProduct, unpackedOrderLine, groupRepoId, UNPACKED_LINE_NET_AMT);

		final I_C_Invoice_Candidate discountIc = newInstance(I_C_Invoice_Candidate.class);
		discountIc.setC_Order_ID(order.getC_Order_ID());
		discountIc.setC_Order_CompensationGroup_ID(groupRepoId);
		discountIc.setC_OrderLine_ID(discountOrderLine.getC_OrderLine_ID());
		discountIc.setM_Product_ID(discountProduct.getM_Product_ID());
		discountIc.setIsGroupCompensationLine(true);
		discountIc.setC_UOM_ID(uomId.getRepoId());
		discountIc.setPrice_UOM_ID(uomId.getRepoId());
		discountIc.setQtyToInvoice(BigDecimal.ONE);
		discountIc.setPriceEntered(BigDecimal.ZERO);
		discountIc.setGroupCompensationType(X_C_Invoice_Candidate.GROUPCOMPENSATIONTYPE_Discount);
		discountIc.setGroupCompensationAmtType(X_C_Invoice_Candidate.GROUPCOMPENSATIONAMTTYPE_Percent);
		discountIc.setGroupCompensationPercentage(BigDecimal.TEN);
		saveRecord(discountIc);

		final GroupId groupId = OrderGroupRepository.createGroupId(OrderId.ofRepoId(order.getC_Order_ID()), groupRepoId);
		return repo.retrieveGroup(groupId);
	}

	private I_M_Product newProduct()
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setC_UOM_ID(uomId.getRepoId());
		final I_M_Product_Category category = newInstance(I_M_Product_Category.class);
		saveRecord(category);
		product.setM_Product_Category_ID(category.getM_Product_Category_ID());
		saveRecord(product);
		return product;
	}

	private I_C_OrderLine newOrderLine(final I_C_Order order, final I_M_Product product, final int groupRepoId, final int piItemProductId)
	{
		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setC_Order_ID(order.getC_Order_ID());
		orderLine.setM_Product_ID(product.getM_Product_ID());
		orderLine.setC_UOM_ID(uomId.getRepoId());
		orderLine.setC_Order_CompensationGroup_ID(groupRepoId);
		InterfaceWrapperHelper.create(orderLine, de.metas.interfaces.I_C_OrderLine.class).setM_HU_PI_Item_Product_ID(piItemProductId);
		saveRecord(orderLine);
		return orderLine;
	}

	private void newRegularCandidate(final I_C_Order order, final I_M_Product product, final I_C_OrderLine orderLine, final int groupRepoId, final BigDecimal netAmt)
	{
		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class);
		ic.setC_Order_ID(order.getC_Order_ID());
		ic.setC_Order_CompensationGroup_ID(groupRepoId);
		ic.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
		ic.setM_Product_ID(product.getM_Product_ID());
		ic.setNetAmtToInvoice(netAmt);
		saveRecord(ic);
	}

	private static class CountingProvider implements PackingMaterialProductCategoryProvider
	{
		private final ImmutableMap<HUPIItemProductId, ImmutableSet<ProductCategoryId>> result;
		final AtomicInteger calls = new AtomicInteger();

		CountingProvider(@NonNull final ImmutableMap<HUPIItemProductId, ImmutableSet<ProductCategoryId>> result)
		{
			this.result = result;
		}

		@Override
		public @NonNull ImmutableMap<HUPIItemProductId, ImmutableSet<ProductCategoryId>> getPackingMaterialProductCategoryIdsAndAncestors(@NonNull final Set<HUPIItemProductId> ids)
		{
			calls.incrementAndGet();
			return result;
		}
	}
}
