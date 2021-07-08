package de.metas.handlingunits.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import de.metas.common.util.time.SystemTime;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.assertj.core.api.OptionalAssert;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.Builder;

@ExtendWith(AdempiereTestWatcher.class)
public class HUPIItemProductDAOTest
{
	private HUPIItemProductDAO dao;
	private ProductId product1;
	private ProductId product2;

	private ProductId packagingProduct1;
	private ProductId packagingProduct2;

	private BPartnerId bpartner1;
	private BPartnerId bpartner2;
	private BPartnerId bpartner3;

	/** Watches current test and dumps the database to console in case of failure */

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		dao = (HUPIItemProductDAO)Services.get(IHUPIItemProductDAO.class);

		product1 = createProduct("p1");
		product2 = createProduct("p2");

		packagingProduct1 = createProduct("pp1");
		packagingProduct2 = createProduct("pp2");

		bpartner1 = BPartnerId.ofRepoId(createBPartner("bp1").getC_BPartner_ID());
		bpartner2 = BPartnerId.ofRepoId(createBPartner("bp2").getC_BPartner_ID());
		bpartner3 = BPartnerId.ofRepoId(createBPartner("bp3").getC_BPartner_ID());
	}

	@Test
	public void test_createQueryOrderByBuilder()
	{
		final Comparator<Object> orderBy = dao.createQueryOrderByBuilder(null)
				.createQueryOrderBy()
				.getComparator();

		final List<I_M_HU_PI_Item_Product> itemProducts = Arrays.asList(
				/* 0 */ huPIItemProduct().productId(product1).bpartnerId(null).validFrom("2011-10-01").huUnitType(X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit).build(),
				/* 1 */ huPIItemProduct().productId(product1).bpartnerId(bpartner1).validFrom("2011-10-01").huUnitType(X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit).build());

		final List<I_M_HU_PI_Item_Product> itemProductsOrdered = new ArrayList<>(itemProducts);
		Collections.sort(itemProductsOrdered, orderBy);

		assertEqualsByDescription("Invalid item at index=1", itemProducts.get(1), itemProductsOrdered.get(0));
		assertEqualsByDescription("Invalid item at index=2", itemProducts.get(0), itemProductsOrdered.get(1));
	}

	@Test
	public void test_retrieveMaterialItemProduct_product_anyBPartner_date()
	{
		final I_M_HU_PI_Item_Product[] itemProducts = new I_M_HU_PI_Item_Product[] {
				/* 0 */ huPIItemProduct().productId(product1).bpartnerId(null).validFrom("2011-10-01").huUnitType(X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit).build(),
				/* 1 */ huPIItemProduct().productId(product1).bpartnerId(bpartner1).validFrom("2013-10-01").huUnitType(X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit).build()
		};

		test_retrieveMaterialItemProduct_product_bpartner_date(itemProducts[0], product1, bpartner1, date("2011-10-01"), X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		test_retrieveMaterialItemProduct_product_bpartner_date(itemProducts[0], product1, bpartner1, date("2012-10-01"), X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		test_retrieveMaterialItemProduct_product_bpartner_date(itemProducts[1], product1, bpartner1, date("2013-10-01"), X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);

		test_retrieveMaterialItemProduct_product_bpartner_date(itemProducts[0], product1, bpartner2, date("2011-10-01"), X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		test_retrieveMaterialItemProduct_product_bpartner_date(itemProducts[0], product1, bpartner2, date("2012-10-01"), X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		test_retrieveMaterialItemProduct_product_bpartner_date(itemProducts[0], product1, bpartner2, date("2013-10-01"), X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
	}

	@Test
	public void test_retrieveMaterialItemProduct_anyProduct_bpartner_date()
	{
		final I_M_HU_PI_Item_Product[] itemProducts = new I_M_HU_PI_Item_Product[] {
				/* 0 */ huPIItemProduct().productId(product1).bpartnerId(null).validFrom("2011-10-01").huUnitType(X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit).build(),
				/* 1 */ huPIItemProduct().productId(null).bpartnerId(null).validFrom("2013-10-01").huUnitType(X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit).build()
		};

		test_retrieveMaterialItemProduct_product_bpartner_date(itemProducts[0], product1, bpartner1, date("2011-10-01"), X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		test_retrieveMaterialItemProduct_product_bpartner_date(itemProducts[0], product1, bpartner1, date("2012-10-01"), X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		test_retrieveMaterialItemProduct_product_bpartner_date(itemProducts[0], product1, bpartner1, date("2013-10-01"), X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);

		test_retrieveMaterialItemProduct_product_bpartner_date(null, product2, bpartner1, date("2011-10-01"), X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		test_retrieveMaterialItemProduct_product_bpartner_date(null, product2, bpartner1, date("2012-10-01"), X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		test_retrieveMaterialItemProduct_product_bpartner_date(null, product2, bpartner1, date("2013-10-01"), X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit); // 06566: don't pull the "anyProduct"
		// I_M_HU_PI_Item_Product, because product2 has no
		// assignment
		test_retrieveMaterialItemProduct_product_bpartner_date(null, product2, bpartner1, date("2014-10-01"), X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit); // 06566: don't pull the "anyProduct"
	}

	/**
	 * Tests {@link HUPIItemProductDAO#retrieveMaterialItemProduct(org.compiere.model.I_M_Product, I_C_BPartner, Date, String, boolean, org.compiere.model.I_M_Product)}, i.e that one that also takes the packagacking product into account.
	 *
	 * @task https://metasfresh.atlassian.net/browse/FRESH-386
	 */
	@Test
	public void test_retrieveMaterialItemProductbyPackagingProduct()
	{
		final I_M_HU_PI_Item_Product piip1 = huPIItemProduct().productId(product1).bpartnerId(bpartner1).validFrom("2011-10-01").huUnitType(X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit).build();
		assertThat(piip1.getM_HU_PI_Item().getItemType()).isEqualTo(X_M_HU_PI_Item.ITEMTYPE_Material); // guard
		addPackingmaterialToItem(packagingProduct1, piip1.getM_HU_PI_Item());
		POJOWrapper.setInstanceName(piip1, "piip1");

		final I_M_HU_PI_Item_Product piip2 = huPIItemProduct().productId(product1).bpartnerId(bpartner1).validFrom("2011-10-01").huUnitType(X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit).build();
		assertThat(piip2.getM_HU_PI_Item().getItemType()).isEqualTo(X_M_HU_PI_Item.ITEMTYPE_Material); // guard
		addPackingmaterialToItem(packagingProduct2, piip2.getM_HU_PI_Item());
		POJOWrapper.setInstanceName(piip2, "piip2");

		final boolean allowInfiniteCapacity = true;

		assertThat(dao.retrieveMaterialItemProduct(product1, bpartner1, date("2011-10-01"), X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit, allowInfiniteCapacity, packagingProduct1))
				.isEqualTo(piip1);
		assertThat(dao.retrieveMaterialItemProduct(product1, bpartner1, date("2011-10-01"), X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit, allowInfiniteCapacity, packagingProduct2))
				.isEqualTo(piip2);
	}

	private void test_retrieveMaterialItemProduct_product_bpartner_date(
			final I_M_HU_PI_Item_Product expected,
			final ProductId productId,
			final BPartnerId bpartnerId,
			final ZonedDateTime date,
			final String huUnitType)
	{
		final boolean allowInfiniteCapacity = true;
		final I_M_HU_PI_Item_Product actual = dao.retrieveMaterialItemProduct(productId, bpartnerId, date, huUnitType, allowInfiniteCapacity);
		final String message = "Invalid for product=" + productId + ", bpartner=" + bpartnerId + ", date=" + date;
		assertEqualsByDescription(message, expected, actual);
	}

	private void assertEqualsByDescription(final String message, final I_M_HU_PI_Item_Product expected, final I_M_HU_PI_Item_Product actual)
	{
		final String expectedDescription = expected == null ? null : expected.getDescription();
		final String actualDescription = actual == null ? null : actual.getDescription();
		assertThat(actualDescription).as(message).isEqualTo(expectedDescription);
	}

	private static ZonedDateTime date(final String localDateStr)
	{
		return LocalDate.parse(localDateStr).atStartOfDay(SystemTime.zoneId());
	}

	private ProductId createProduct(final String value)
	{
		final I_M_Product p = newInstance(I_M_Product.class);
		p.setValue(value);
		p.setName(value);
		saveRecord(p);

		return ProductId.ofRepoId(p.getM_Product_ID());
	}

	private I_C_BPartner createBPartner(final String value)
	{
		final I_C_BPartner bp = newInstance(I_C_BPartner.class);
		bp.setValue(value);
		bp.setName(value);
		saveRecord(bp);

		return bp;
	}

	private I_M_HU_PI_Version createM_HU_PI_Version(final String huUnitType)
	{
		final I_M_HU_PI pi = newInstance(I_M_HU_PI.class);
		saveRecord(pi);

		final I_M_HU_PI_Version piVersion = newInstance(I_M_HU_PI_Version.class);
		piVersion.setM_HU_PI(pi);
		piVersion.setHU_UnitType(huUnitType);
		piVersion.setIsCurrent(true);
		piVersion.setName("M_HU_PI_ID=" + pi.getM_HU_PI_ID() + "_Current");
		saveRecord(piVersion);
		return piVersion;
	}

	private I_M_HU_PI_Item createMaterialM_HU_PI_Item(final String huUnitType)
	{
		final I_M_HU_PI_Version piVersion = createM_HU_PI_Version(huUnitType);

		final I_M_HU_PI_Item piItem = newInstance(I_M_HU_PI_Item.class);
		piItem.setM_HU_PI_Version(piVersion);
		piItem.setItemType(X_M_HU_PI_Item.ITEMTYPE_Material);
		saveRecord(piItem);
		return piItem;
	}

	private void addPackingmaterialToItem(final ProductId packingProductId, final I_M_HU_PI_Item materialPiItem)
	{
		final I_M_HU_PackingMaterial packingMaterial = newInstance(I_M_HU_PackingMaterial.class);
		packingMaterial.setM_Product_ID(packingProductId.getRepoId());
		saveRecord(packingMaterial);

		final I_M_HU_PI_Item packingMaterialPiItem = newInstance(I_M_HU_PI_Item.class);
		packingMaterialPiItem.setM_HU_PI_Version(materialPiItem.getM_HU_PI_Version());

		packingMaterialPiItem.setItemType(X_M_HU_PI_Item.ITEMTYPE_PackingMaterial);
		packingMaterialPiItem.setM_HU_PackingMaterial(packingMaterial);
		packingMaterialPiItem.setQty(BigDecimal.ONE);

		saveRecord(packingMaterialPiItem);
	}

	@Builder(builderMethodName = "huPIItemProduct", builderClassName = "HUPIItemProductBuilder")
	private I_M_HU_PI_Item_Product createM_HU_PI_Item_Product(
			final String instanceName,
			final ProductId productId,
			final BPartnerId bpartnerId,
			final String validFrom,
			final String huUnitType,
			final Boolean defaultForProduct)
	{
		final I_M_HU_PI_Item_Product piItemProduct = newInstance(I_M_HU_PI_Item_Product.class);
		if (instanceName != null)
		{
			POJOWrapper.setInstanceName(piItemProduct, instanceName);
		}

		piItemProduct.setM_Product_ID(ProductId.toRepoId(productId));
		if (productId == null)
		{
			piItemProduct.setIsAllowAnyProduct(true);
		}

		piItemProduct.setIsInfiniteCapacity(false);

		final I_M_HU_PI_Item huPiItem = createMaterialM_HU_PI_Item(huUnitType);
		piItemProduct.setM_HU_PI_Item(huPiItem);

		piItemProduct.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
		piItemProduct.setValidFrom(TimeUtil.asTimestamp(date(validFrom)));

		if (defaultForProduct != null)
		{
			piItemProduct.setIsDefaultForProduct(defaultForProduct);
		}

		saveRecord(piItemProduct);

		piItemProduct.setDescription(toString(piItemProduct));
		saveRecord(piItemProduct);

		return piItemProduct;
	}

	private String toString(final I_M_HU_PI_Item_Product piItemProduct)
	{
		final StringBuilder sb = new StringBuilder();

		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(piItemProduct.getC_BPartner_ID());
		if (bpartnerId != null)
		{
			if (sb.length() > 0)
			{
				sb.append(", ");
			}

			final I_C_BPartner bpartner = Services.get(IBPartnerDAO.class).getById(bpartnerId);
			sb.append("BP=").append(bpartner.getValue());
		}

		final ProductId productId = ProductId.ofRepoIdOrNull(piItemProduct.getM_Product_ID());
		if (productId != null)
		{
			if (sb.length() > 0)
			{
				sb.append(", ");
			}

			final String productValue = Services.get(IProductDAO.class).retrieveProductValueByProductId(productId);
			sb.append("Product=").append(productValue);
		}

		if (piItemProduct.isAllowAnyProduct())
		{
			if (sb.length() > 0)
			{
				sb.append(", ");
			}
			sb.append("IsAllowAnyProduct");
		}
		if (piItemProduct.getValidFrom() != null)
		{
			if (sb.length() > 0)
			{
				sb.append(", ");
			}
			sb.append("ValidFrom=").append(piItemProduct.getValidFrom());
		}

		final I_M_HU_PI_Item piItem = piItemProduct.getM_HU_PI_Item();
		final I_M_HU_PI_Version piVersion = piItem == null ? null : piItem.getM_HU_PI_Version();
		if (piVersion != null && piVersion.getHU_UnitType() != null)
		{
			if (sb.length() > 0)
			{
				sb.append(", ");
			}
			sb.append("huUnitType=").append(piVersion.getHU_UnitType());
		}

		final String instanceName = sb.toString();
		POJOWrapper.setInstanceName(piItemProduct, instanceName);

		return instanceName;
	}

	/**
	 * @see http://dewiki908/mediawiki/index.php/08868_Wareneingang_POS_does_not_suggest_customer_defined_TU_%28109729999780%29
	 */
	@Test
	public void test_retrieveTUs_case08868()
	{
		final I_M_HU_PI_Item_Product pip1 = huPIItemProduct().productId(product1).bpartnerId(null).validFrom("2011-10-01").huUnitType(X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit).build();
		final I_M_HU_PI_Item_Product pip2 = huPIItemProduct().productId(product1).bpartnerId(bpartner1).validFrom("2011-10-01").huUnitType(X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit).build();
		@SuppressWarnings("unused")
		final I_M_HU_PI_Item_Product pip3 = huPIItemProduct().productId(product1).bpartnerId(bpartner2).validFrom("2011-10-01").huUnitType(X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit).build();
		final I_M_HU_PI_Item_Product pip4 = huPIItemProduct().productId(product1).bpartnerId(bpartner1).validFrom("2011-10-01").huUnitType(X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit).build();

		assertThat(dao.retrieveTUs(Env.getCtx(), product1, bpartner1))
				.containsExactlyInAnyOrder(pip1, pip2, pip4);
	}

	@Nested
	public class retrieveDefaultForProduct
	{
		private OptionalAssert<I_M_HU_PI_Item_Product> assertDefaultForProduct(final ProductId productId, final BPartnerId bpartnerId, final String date)
		{
			return assertThat(dao.retrieveDefaultForProduct(product1, bpartnerId, date(date)));
		}

		@Test
		public void noDefaults()
		{
			huPIItemProduct().productId(product1).bpartnerId(null).validFrom("2011-10-01").huUnitType(X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit).build();

			assertDefaultForProduct(product1, bpartner1, "2011-10-01").isEmpty();
		}

		@Test
		public void withDefaults()
		{
			final HUPIItemProductBuilder pipBuilder = huPIItemProduct().productId(product1).huUnitType(X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit).defaultForProduct(true);

			final I_M_HU_PI_Item_Product pip1 = pipBuilder.instanceName("pip1").bpartnerId(bpartner1).validFrom("2012-10-01").build();
			final I_M_HU_PI_Item_Product pip2 = pipBuilder.instanceName("pip2").bpartnerId(bpartner2).validFrom("2012-10-01").build();
			final I_M_HU_PI_Item_Product pip3 = pipBuilder.instanceName("pip3").bpartnerId(null).validFrom("2011-10-01").build();

			assertDefaultForProduct(product1, bpartner1, "2010-10-01").isEmpty();
			assertDefaultForProduct(product1, bpartner1, "2011-10-01").contains(pip3);
			assertDefaultForProduct(product1, bpartner1, "2012-10-01").contains(pip1);

			assertDefaultForProduct(product1, bpartner2, "2010-10-01").isEmpty();
			assertDefaultForProduct(product1, bpartner2, "2011-10-01").contains(pip3);
			assertDefaultForProduct(product1, bpartner2, "2012-10-01").contains(pip2);

			assertDefaultForProduct(product1, bpartner3, "2010-10-01").isEmpty();
			assertDefaultForProduct(product1, bpartner3, "2011-10-01").contains(pip3);
			assertDefaultForProduct(product1, bpartner3, "2012-10-01").contains(pip3);
		}
	}
}
