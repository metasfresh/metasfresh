package de.metas.handlingunits.impl;

import com.google.common.collect.ImmutableList;
import de.metas.adempiere.model.I_M_Product;
import de.metas.bpartner.BPartnerId;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleRepository;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.age.AgeAttributesService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.handlingunits.model.I_M_HU_Reservation;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.reservation.HUReservationDocRef;
import de.metas.handlingunits.reservation.HUReservationRepository;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.AttributesTestHelper;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.text.ExtendedReflectionToStringBuilder;
import org.adempiere.util.text.RecursiveIndentedMultilineToStringStyle;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.X_M_Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

public class HUQueryBuilderTest
{
	private I_M_Warehouse wh;

	private I_M_Product product;
	private List<I_M_HU> hus;

	private HUQueryBuilder huQueryBuilder;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		wh = newInstance(I_M_Warehouse.class);
		save(wh);

		final I_M_Locator locator = newInstance(I_M_Locator.class);
		locator.setM_Warehouse_ID(wh.getM_Warehouse_ID());
		save(locator);

		final I_M_Warehouse otherWh = newInstance(I_M_Warehouse.class);
		save(otherWh);

		final I_M_Locator otherLocator = newInstance(I_M_Locator.class);
		otherLocator.setM_Warehouse_ID(otherWh.getM_Warehouse_ID());
		save(otherLocator);

		product = newInstance(I_M_Product.class);
		save(product);

		final I_M_Product otherProduct = newInstance(I_M_Product.class);
		save(otherProduct);

		hus = ImmutableList.of(
				createHU("locator-product", locator, product),
				createHU("locator-product-nosourceHU", locator, product),
				createHU("locator-otherProduct", locator, otherProduct),
				createHU("otherLocator-product", otherLocator, product),
				createHU("otherLocator-otherProduct", otherLocator, otherProduct));

		huQueryBuilder = new HUQueryBuilder(
				new HUReservationRepository(),
				new AgeAttributesService(),
				new DDOrderMoveScheduleRepository());
	}

	private static I_M_HU createHU(
			final String instanceName,
			final I_M_Locator locator,
			final I_M_Product product)
	{
		final I_M_HU hu = newInstance(I_M_HU.class);
		hu.setHUStatus(X_M_HU.HUSTATUS_Active);
		hu.setM_Locator_ID(locator != null ? locator.getM_Locator_ID() : -1);
		save(hu);
		POJOWrapper.setInstanceName(hu, instanceName);

		final I_M_HU_Storage hu_storage = newInstance(I_M_HU_Storage.class);
		hu_storage.setM_HU(hu);
		hu_storage.setM_Product_ID(product.getM_Product_ID());
		save(hu_storage);

		return hu;
	}

	/**
	 * Just makes sure that {@link HUQueryBuilder#copy()} is not failing.
	 */
	@Test
	public void copy_NotFails()
	{
		final HUQueryBuilder husQueryCopy = huQueryBuilder.copy();
		assertThat(husQueryCopy).as("copy shall not be null").isNotNull();
		assertThat(husQueryCopy).as("original and copy shall not be the same").isNotSameAs(huQueryBuilder);
		assertSameStringRepresentation(huQueryBuilder, husQueryCopy);
	}

	private void assertSameStringRepresentation(final Object expected, final Object actual)
	{
		final String expectedStr = toString(expected);
		final String actualStr = toString(actual);

		final String message = "String representations shall be the same"
				+ "\nExpected: " + expectedStr
				+ "\nActual: " + actualStr;
		assertThat(actualStr).as(message).isEqualTo(expectedStr);
	}

	private String toString(final Object obj)
	{
		return new ExtendedReflectionToStringBuilder(obj, RecursiveIndentedMultilineToStringStyle.instance)
				.toString();
	}

	@Test
	public void createQueryFilter_by_product_and_warehouse()
	{
		huQueryBuilder
				.addOnlyWithProduct(product)
				.addOnlyInWarehouseId(WarehouseId.ofRepoId(wh.getM_Warehouse_ID()));

		// invoke the method under test
		final IQueryFilter<I_M_HU> huFilters = huQueryBuilder.createQueryFilter();

		assertThat(huFilters.accept(hus.get(0))).isTrue();
		assertThat(huFilters.accept(hus.get(1))).isTrue();
		assertThat(huFilters.accept(hus.get(2))).isFalse();
		assertThat(huFilters.accept(hus.get(3))).isFalse();
		assertThat(huFilters.accept(hus.get(4))).isFalse();
	}

	@Test
	public void createQueryFilter_select_all()
	{

		// invoke the method under test
		final IQueryFilter<I_M_HU> huFilters = huQueryBuilder.createQueryFilter();

		assertThat(hus).allMatch(huFilters::accept);
	}

	@Test
	public void createQueryFilter_exclude_reserved()
	{

		final OrderLineId orderLineId = OrderLineId.ofRepoId(10);
		createReservationRecord(orderLineId, hus.get(0));

		final OrderLineId otherOrderLineId = OrderLineId.ofRepoId(20);
		createReservationRecord(otherOrderLineId, hus.get(1));

		huQueryBuilder.setExcludeReservedToOtherThan(HUReservationDocRef.ofSalesOrderLineId(orderLineId));

		// invoke the method under test
		final IQueryFilter<I_M_HU> huFilters = huQueryBuilder.createQueryFilter();

		assertThat(huFilters.accept(hus.get(0))).isTrue(); // because it's reserved for "orderLineId"
		assertThat(huFilters.accept(hus.get(1))).isFalse(); // because it's reserved for a different order line
		assertThat(huFilters.accept(hus.get(2))).isTrue(); // because they are not reserved at all
		assertThat(huFilters.accept(hus.get(3))).isTrue();
		assertThat(huFilters.accept(hus.get(4))).isTrue();
	}

	private void createReservationRecord(final OrderLineId orderLineId, final I_M_HU hu)
	{
		hu.setIsReserved(true);
		saveRecord(hu);
		final I_M_HU_Reservation huReservationRecord = newInstance(I_M_HU_Reservation.class);
		huReservationRecord.setVHU_ID(hu.getM_HU_ID());
		huReservationRecord.setC_OrderLineSO_ID(orderLineId.getRepoId());
		saveRecord(huReservationRecord);
	}

	/**
	 * Regression guard:
	 * An UNSET numeric attribute on a sales-order line's ASI must not be coerced to 0 and applied as a hard
	 * HU-attribute filter. The fix lives in {@code AttributeSetInstanceBL.extractAttributeInstanceValue}: an
	 * unset {@code ValueNumber} (SQL-NULL) is now preserved as {@code null} so the HU-filter loop skips it,
	 * while a genuinely-set value (including 0) still filters.
	 *
	 * <p>AC1 – unset order-line attribute → HU with MonthsUntilExpiry=7 IS returned (no filter applied).
	 * <p>AC2 – attribute set to 7 → HU returned; attribute set to 0 → HU excluded.
	 */
	@Test
	public void unsetNumericOrderLineAttribute_doesNotFilterOutMatchingHUs()
	{
		// -----------------------------------------------------------------------
		// Set up the M_Attribute "MonthsUntilExpiry" (NUMBER type)
		// -----------------------------------------------------------------------
		final AttributesTestHelper attributesTestHelper = new AttributesTestHelper();
		final I_M_Attribute attr = attributesTestHelper.createM_Attribute(
				"MonthsUntilExpiry",
				X_M_Attribute.ATTRIBUTEVALUETYPE_Number,
				true /* isInstanceAttribute */);

		// Register the attribute for the VIRTUAL packing-instruction version so that
		// HUQueryBuilder_Attributes.huRelevantAttributeIds contains it (otherwise the
		// per-attribute filter is silently skipped and all HUs pass regardless of the fix).
		final I_M_HU_PI_Attribute piAttr = newInstance(I_M_HU_PI_Attribute.class);
		piAttr.setM_Attribute_ID(attr.getM_Attribute_ID());
		piAttr.setM_HU_PI_Version_ID(HuPackingInstructionsVersionId.VIRTUAL.getRepoId());
		piAttr.setIsActive(true);
		save(piAttr);

		// -----------------------------------------------------------------------
		// Create an Active HU with MonthsUntilExpiry = 7
		// -----------------------------------------------------------------------
		final I_M_Locator locator = newInstance(I_M_Locator.class);
		locator.setM_Warehouse_ID(wh.getM_Warehouse_ID());
		save(locator);

		final I_M_HU hu = newInstance(I_M_HU.class);
		hu.setHUStatus(X_M_HU.HUSTATUS_Active);
		hu.setM_Locator_ID(locator.getM_Locator_ID());
		save(hu);
		POJOWrapper.setInstanceName(hu, "hu-MonthsUntilExpiry=7");

		final I_M_HU_Storage huStorage = newInstance(I_M_HU_Storage.class);
		huStorage.setM_HU(hu);
		huStorage.setM_Product_ID(product.getM_Product_ID());
		save(huStorage);

		// The HU-level attribute record (MonthsUntilExpiry = 7).
		// This is what the HU filter query reads (I_M_HU_Attribute).
		final I_M_HU_Attribute huAttr = newInstance(I_M_HU_Attribute.class);
		huAttr.setM_HU_ID(hu.getM_HU_ID());
		huAttr.setM_Attribute_ID(attr.getM_Attribute_ID());
		huAttr.setValueNumber(BigDecimal.valueOf(7));
		huAttr.setIsActive(true);
		save(huAttr);

		// -----------------------------------------------------------------------
		// Create supporting records for the method under test
		// -----------------------------------------------------------------------
		final I_C_BPartner bp = newInstance(I_C_BPartner.class);
		save(bp);
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(bp.getC_BPartner_ID());
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());

		// -----------------------------------------------------------------------
		// AC1: order-line ASI has MonthsUntilExpiry UNSET (SQL-NULL ValueNumber).
		// Expected: HU IS returned — the unset attribute must not be turned into a =0 filter.
		// -----------------------------------------------------------------------
		final ImmutableAttributeSet asiSetUnset = loadAsiSet_withNumericAttribute_unset(attr);

		final IQueryFilter<I_M_HU> filterForUnset = newHuQueryBuilder()
				.addOnlyWithAttributeValuesMatchingPartnerAndProduct(bpartnerId, productId, asiSetUnset)
				.createQueryFilter();

		assertThat(filterForUnset.accept(hu))
				.as("AC1: HU with MonthsUntilExpiry=7 must be returned when order-line ASI has the attribute UNSET (null)")
				.isTrue();

		// -----------------------------------------------------------------------
		// AC2a: order-line ASI has MonthsUntilExpiry = 7  → HU matches, IS returned.
		// -----------------------------------------------------------------------
		final ImmutableAttributeSet asiSet7 = loadAsiSet_withNumericAttribute_set(attr, BigDecimal.valueOf(7));

		final IQueryFilter<I_M_HU> filterFor7 = newHuQueryBuilder()
				.addOnlyWithAttributeValuesMatchingPartnerAndProduct(bpartnerId, productId, asiSet7)
				.createQueryFilter();

		assertThat(filterFor7.accept(hu))
				.as("AC2a: HU with MonthsUntilExpiry=7 must be returned when order-line ASI also has MonthsUntilExpiry=7")
				.isTrue();

		// -----------------------------------------------------------------------
		// AC2b: order-line ASI has MonthsUntilExpiry = 0.
		//   - the HU with MonthsUntilExpiry=7 must NOT match (excluded);
		//   - a second HU with MonthsUntilExpiry=0 MUST match (returned) — this proves the
		//     "=0" filter is well-formed and not merely rejecting everything.
		// -----------------------------------------------------------------------
		final I_M_HU huWithZero = newInstance(I_M_HU.class);
		huWithZero.setHUStatus(X_M_HU.HUSTATUS_Active);
		huWithZero.setM_Locator_ID(locator.getM_Locator_ID());
		save(huWithZero);
		POJOWrapper.setInstanceName(huWithZero, "hu-MonthsUntilExpiry=0");

		final I_M_HU_Storage huStorageZero = newInstance(I_M_HU_Storage.class);
		huStorageZero.setM_HU(huWithZero);
		huStorageZero.setM_Product_ID(product.getM_Product_ID());
		save(huStorageZero);

		final I_M_HU_Attribute huAttrZero = newInstance(I_M_HU_Attribute.class);
		huAttrZero.setM_HU_ID(huWithZero.getM_HU_ID());
		huAttrZero.setM_Attribute_ID(attr.getM_Attribute_ID());
		huAttrZero.setValueNumber(BigDecimal.ZERO);
		huAttrZero.setIsActive(true);
		save(huAttrZero);

		final ImmutableAttributeSet asiSet0 = loadAsiSet_withNumericAttribute_set(attr, BigDecimal.ZERO);

		final IQueryFilter<I_M_HU> filterFor0 = newHuQueryBuilder()
				.addOnlyWithAttributeValuesMatchingPartnerAndProduct(bpartnerId, productId, asiSet0)
				.createQueryFilter();

		assertThat(filterFor0.accept(hu))
				.as("AC2b: HU with MonthsUntilExpiry=7 must NOT be returned when order-line ASI has MonthsUntilExpiry=0")
				.isFalse();

		assertThat(filterFor0.accept(huWithZero))
				.as("AC2b: HU with MonthsUntilExpiry=0 MUST be returned when order-line ASI has MonthsUntilExpiry=0 (the =0 filter is well-formed)")
				.isTrue();
	}

	/**
	 * Builds an {@link ImmutableAttributeSet} by going through the real ASI load path
	 * ({@code IAttributeSetInstanceBL.getImmutableAttributeSetById}), which routes through
	 * {@code AttributeSetInstanceBL.extractAttributeInstanceValue}.
	 *
	 * <p>The attribute instance is saved WITHOUT calling {@code setValueNumber}, leaving
	 * {@code ValueNumber} SQL-NULL — the pre-fix code coerces that null to 0 and applies a
	 * hard {@code = 0} filter; the fix preserves null so the filter loop skips it.
	 */
	private static ImmutableAttributeSet loadAsiSet_withNumericAttribute_unset(final I_M_Attribute attr)
	{
		final I_M_AttributeSetInstance asi = newInstance(I_M_AttributeSetInstance.class);
		save(asi);

		final I_M_AttributeInstance ai = newInstance(I_M_AttributeInstance.class);
		ai.setM_Attribute_ID(attr.getM_Attribute_ID());
		ai.setM_AttributeSetInstance_ID(asi.getM_AttributeSetInstance_ID());
		// intentionally do NOT call ai.setValueNumber(…) — ValueNumber stays SQL-NULL
		save(ai);

		return Services.get(IAttributeSetInstanceBL.class)
				.getImmutableAttributeSetById(AttributeSetInstanceId.ofRepoId(asi.getM_AttributeSetInstance_ID()));
	}

	/**
	 * Same real-load path as {@link #loadAsiSet_withNumericAttribute_unset}, but with an explicit
	 * numeric value set on the {@code I_M_AttributeInstance}.
	 */
	private static ImmutableAttributeSet loadAsiSet_withNumericAttribute_set(
			final I_M_Attribute attr,
			final BigDecimal value)
	{
		final I_M_AttributeSetInstance asi = newInstance(I_M_AttributeSetInstance.class);
		save(asi);

		final I_M_AttributeInstance ai = newInstance(I_M_AttributeInstance.class);
		ai.setM_Attribute_ID(attr.getM_Attribute_ID());
		ai.setM_AttributeSetInstance_ID(asi.getM_AttributeSetInstance_ID());
		ai.setValueNumber(value);
		save(ai);

		return Services.get(IAttributeSetInstanceBL.class)
				.getImmutableAttributeSetById(AttributeSetInstanceId.ofRepoId(asi.getM_AttributeSetInstance_ID()));
	}

	private HUQueryBuilder newHuQueryBuilder()
	{
		return new HUQueryBuilder(
				new HUReservationRepository(),
				new AgeAttributesService(),
				new DDOrderMoveScheduleRepository());
	}

}
