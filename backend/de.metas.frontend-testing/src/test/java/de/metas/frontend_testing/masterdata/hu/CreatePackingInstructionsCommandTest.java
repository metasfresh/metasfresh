package de.metas.frontend_testing.masterdata.hu;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.attribute.strategy.impl.CopyHUAttributeTransferStrategy;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CreatePackingInstructionsCommandTest
{
	private final ObjectMapper jsonMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();
	private MasterdataContext context;
	private IQueryBL queryBL;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		context = new MasterdataContext();
		queryBL = Services.get(IQueryBL.class);
	}

	private ProductId createProduct(final String identifier)
	{
		final I_C_UOM uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		InterfaceWrapperHelper.saveRecord(uom);
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setValue(identifier);
		product.setName(identifier);
		product.setC_UOM_ID(uom.getC_UOM_ID());
		InterfaceWrapperHelper.saveRecord(product);
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());
		context.putIdentifier(Identifier.ofString(identifier), productId);
		return productId;
	}

	private List<I_M_HU_PI_Item> luItems(final String json) throws Exception
	{
		execute("PI", json);

		return queryBL
				.createQueryBuilder(I_M_HU_PI_Item.class)
				.addNotNull(I_M_HU_PI_Item.COLUMNNAME_Included_HU_PI_ID)
				.orderBy(I_M_HU_PI_Item.COLUMNNAME_M_HU_PI_Item_ID)
				.create()
				.list();
	}

	@Nested
	class Request
	{
		@Test
		public void additionalLUItems_isDeserialized() throws Exception
		{
			final String json = "{\"lu\":\"LU\",\"qtyTUsPerLU\":30,\"tu\":\"TU\",\"product\":\"P1\",\"additionalLUItems\":[{\"product\":\"XY\",\"qtyTUsPerLU\":40}]}";
			final JsonPackingInstructionsRequest request = jsonMapper.readValue(json, JsonPackingInstructionsRequest.class);

			assertThat(request.getAdditionalLUItems()).hasSize(1);
			assertThat(request.getAdditionalLUItems().get(0).getProduct()).isEqualTo(Identifier.ofString("XY"));
			assertThat(request.getAdditionalLUItems().get(0).getQtyTUsPerLU()).isEqualTo(40);
		}

		@Test
		public void withoutAdditionalLUItems_hasEmptyList() throws Exception
		{
			final JsonPackingInstructionsRequest request = jsonMapper.readValue("{\"lu\":\"LU\",\"qtyTUsPerLU\":30,\"tu\":\"TU\",\"product\":\"P1\"}", JsonPackingInstructionsRequest.class);
			assertThat(request.getAdditionalLUItems()).isEmpty();
		}
	}

	@Nested
	class Execute
	{
		@Test
		public void additionalLUItem_createsProductBoundLUItem() throws Exception
		{
			createProduct("P1");
			final ProductId xy = createProduct("XY");

			final List<I_M_HU_PI_Item> items = luItems("{\"lu\":\"LU\",\"qtyTUsPerLU\":30,\"tu\":\"TU\",\"product\":\"P1\",\"qtyCUsPerTU\":4,"
					+ "\"additionalLUItems\":[{\"product\":\"XY\",\"qtyTUsPerLU\":40}]}");

			assertThat(items).hasSize(2);
			// generic item, unchanged
			assertThat(items.get(0).getM_Product_ID()).isLessThanOrEqualTo(0);
			assertThat(items.get(0).getQty()).isEqualByComparingTo(BigDecimal.valueOf(30));
			// product-specific item: same LU version + same included TU PI
			assertThat(items.get(1).getM_Product_ID()).isEqualTo(xy.getRepoId());
			assertThat(items.get(1).getQty()).isEqualByComparingTo(BigDecimal.valueOf(40));
			assertThat(items.get(1).getM_HU_PI_Version_ID()).isEqualTo(items.get(0).getM_HU_PI_Version_ID());
			assertThat(items.get(1).getIncluded_HU_PI_ID()).isEqualTo(items.get(0).getIncluded_HU_PI_ID());
		}

		@Test
		public void withoutAdditionalLUItems_createsSingleGenericLUItem() throws Exception
		{
			createProduct("P1");

			final List<I_M_HU_PI_Item> items = luItems("{\"lu\":\"LU\",\"qtyTUsPerLU\":30,\"tu\":\"TU\",\"product\":\"P1\",\"qtyCUsPerTU\":4}");

			assertThat(items).hasSize(1);
			assertThat(items.get(0).getM_Product_ID()).isLessThanOrEqualTo(0);
			assertThat(items.get(0).getQty()).isEqualByComparingTo(BigDecimal.valueOf(30));
		}

		/**
		 * Several entries on the same TU packing instruction (e.g. one per product) share its one material item, like a real TU packing instruction
		 * that holds several products: code that resolves "the" material item of a TU (e.g. a TU's capacity) requires exactly one.
		 */
		@Test
		public void tuReusedByAnotherEntry_sharesTheTUsMaterialItem() throws Exception
		{
			createProduct("P1");
			createProduct("P2");

			execute("PI_P1", "{\"lu\":\"LU\",\"qtyTUsPerLU\":30,\"tu\":\"TU\",\"product\":\"P1\",\"qtyCUsPerTU\":4}");
			execute("PI_P2", "{\"tu\":\"TU\",\"product\":\"P2\",\"qtyCUsPerTU\":4}");

			final List<I_M_HU_PI_Item> materialItems = queryBL
					.createQueryBuilder(I_M_HU_PI_Item.class)
					.addEqualsFilter(I_M_HU_PI_Item.COLUMNNAME_ItemType, X_M_HU_PI_Item.ITEMTYPE_Material)
					.create()
					.list();
			assertThat(materialItems).hasSize(1);

			final List<I_M_HU_PI_Item_Product> itemProducts = queryBL
					.createQueryBuilder(I_M_HU_PI_Item_Product.class)
					.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_ID, materialItems.get(0).getM_HU_PI_Item_ID())
					.create()
					.list();
			assertThat(itemProducts).hasSize(2);
		}

		@Test
		public void additionalLUItemsWithoutLU_fails()
		{
			createProduct("P1");
			createProduct("XY");

			assertThatThrownBy(() -> luItems("{\"tu\":\"TU\",\"product\":\"P1\",\"qtyCUsPerTU\":4,"
					+ "\"additionalLUItems\":[{\"product\":\"XY\",\"qtyTUsPerLU\":40}]}"))
					.hasMessageContaining("additionalLUItems");
		}

		@Test
		public void luSSCC18_createsSSCC18AttributeOnLUPIVersion() throws Exception
		{
			createProduct("P1");
			final I_M_Attribute sscc18Attribute = InterfaceWrapperHelper.newInstance(I_M_Attribute.class);
			sscc18Attribute.setValue("SSCC18");
			sscc18Attribute.setName("SSCC18");
			sscc18Attribute.setAttributeValueType(X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40);
			InterfaceWrapperHelper.saveRecord(sscc18Attribute);

			final I_AD_JavaClass copyStrategyClass = InterfaceWrapperHelper.newInstance(I_AD_JavaClass.class);
			copyStrategyClass.setClassname(CopyHUAttributeTransferStrategy.class.getName());
			InterfaceWrapperHelper.saveRecord(copyStrategyClass);

			luItems("{\"lu\":\"LU\",\"qtyTUsPerLU\":30,\"tu\":\"TU\",\"product\":\"P1\",\"qtyCUsPerTU\":4,\"luSSCC18\":true}");

			final I_M_HU_PI_Version luVersion = queryBL
					.createQueryBuilder(I_M_HU_PI_Version.class)
					.addEqualsFilter(I_M_HU_PI_Version.COLUMNNAME_HU_UnitType, "LU")
					.create()
					.firstOnlyNotNull(I_M_HU_PI_Version.class);
			final List<I_M_HU_PI_Attribute> luAttributes = queryBL
					.createQueryBuilder(I_M_HU_PI_Attribute.class)
					.addEqualsFilter(I_M_HU_PI_Attribute.COLUMNNAME_M_HU_PI_Version_ID, luVersion.getM_HU_PI_Version_ID())
					.create()
					.list();
			assertThat(luAttributes).hasSize(1);
			assertThat(luAttributes.get(0).getM_Attribute_ID()).isEqualTo(sscc18Attribute.getM_Attribute_ID());
			assertThat(luAttributes.get(0).isActive()).isTrue();
			// mirrors the standard EUR-pallet LU SSCC18 row
			assertThat(luAttributes.get(0).getPropagationType()).isEqualTo(X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation);
			assertThat(luAttributes.get(0).getHU_TansferStrategy_JavaClass_ID()).isEqualTo(copyStrategyClass.getAD_JavaClass_ID());
			assertThat(luAttributes.get(0).isReadOnly()).isTrue();
			assertThat(luAttributes.get(0).isDisplayed()).isTrue();
			assertThat(luAttributes.get(0).isUseInASI()).isTrue();
			assertThat(luAttributes.get(0).isOnlyIfInProductAttributeSet()).isTrue();
		}

		@Test
		public void luSSCC18_withoutLU_throws()
		{
			createProduct("P1");

			assertThatThrownBy(() -> luItems("{\"tu\":\"TU\",\"product\":\"P1\",\"qtyCUsPerTU\":4,\"luSSCC18\":true}"))
					.hasMessageContaining("luSSCC18 requires lu");
		}
	}

	private void execute(final String identifier, final String json) throws Exception
	{
		CreatePackingInstructionsCommand.builder()
				.context(context)
				.request(jsonMapper.readValue(json, JsonPackingInstructionsRequest.class))
				.identifier(Identifier.ofString(identifier))
				.build()
				.execute();
	}

}
