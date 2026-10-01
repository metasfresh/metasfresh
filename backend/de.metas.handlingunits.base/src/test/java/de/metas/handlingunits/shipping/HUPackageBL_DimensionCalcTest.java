package de.metas.handlingunits.shipping;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestinationTestSupport;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.shipping.CreatePackageForHURequest;
import de.metas.organization.OrgId;
import de.metas.product.PackageDimensions;
import de.metas.product.ProductRepository;
import de.metas.quantity.Quantity;
import de.metas.shipping.ShipperId;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_M_Package;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link IHUPackageBL#getPackageDimensions(I_M_HU)}:
 * <ol>
 *   <li>Multi-product TU with mode=Strapping → aggregated dims (not UNSPECIFIED).</li>
 *   <li>Non-self-packed single product WITH dims → dims returned (IsSelfPacked is not checked).</li>
 *   <li>Item without dims inside a multi-product TU → UNSPECIFIED, no exception thrown.</li>
 * </ol>
 */
@ExtendWith(AdempiereTestWatcher.class)
public class HUPackageBL_DimensionCalcTest
{
	private LUTUProducerDestinationTestSupport data;
	private IHUPackageBL huPackageBL;
	/** A TU PI with NO packing material — so getPackageDimensions falls into the product-based branch. */
	private I_M_HU_PI piTU_NoPackMat;

	@BeforeEach
	public void init()
	{
		data = new LUTUProducerDestinationTestSupport();
		SpringContextHolder.registerJUnitBean(ProductRepository.newInstanceForUnitTesting());
		huPackageBL = Services.get(IHUPackageBL.class);

		// The packing material DAO resolves dimensions via a CM UOM lookup (X12DE355=CM).
		// Register the UOM and assign it to the IFCO packing material so the packing-material path
		// in getPackageDimensions doesn't throw "No UOM found for X12DE355=CM".
		final I_C_UOM uomCm = newInstance(I_C_UOM.class);
		uomCm.setName("cm");
		uomCm.setUOMSymbol("cm");
		uomCm.setX12DE355(X12DE355.CENTIMETRE.getCode());
		uomCm.setStdPrecision(2);
		saveRecord(uomCm);

		// Set IFCO packing material dimensions (used by test 2 / single-product CU path).
		final I_M_HU_PackingMaterial pmIFCO = data.helper.pmIFCO;
		pmIFCO.setC_UOM_Dimension_ID(uomCm.getC_UOM_ID());
		pmIFCO.setLength(new BigDecimal("60"));
		pmIFCO.setWidth(new BigDecimal("40"));
		pmIFCO.setHeight(new BigDecimal("30"));
		save(pmIFCO);

		// Create a lightweight TU PI with NO packing material item.
		// getPackageDimensions skips the packing-material branch and enters the product-based branch.
		piTU_NoPackMat = data.helper.createHUDefinition("TU_NoPackMat", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item item = data.helper.createHU_PI_Item_Material(piTU_NoPackMat);
		data.helper.assignProduct(item, data.helper.pTomatoProductId, new BigDecimal("40"), data.helper.uomKg);
		data.helper.assignProduct(item, data.helper.pSaladProductId, new BigDecimal("10"), data.helper.uomEach);

		// Give both test products package dimensions so tests can rely on them having dims.
		// Dimensions are stored on the I_M_Product record and read by ProductRepository#ofProductRecord.
		data.helper.pTomato.setLengthInCm(30);
		data.helper.pTomato.setWidthInCm(20);
		data.helper.pTomato.setHeightInCm(10);
		save(data.helper.pTomato);

		data.helper.pSalad.setLengthInCm(25);
		data.helper.pSalad.setWidthInCm(15);
		data.helper.pSalad.setHeightInCm(12);
		save(data.helper.pSalad);
	}

	/**
	 * (b) Non-self-packed single product WITH dims → dims returned (IsSelfPacked is not checked).
	 */
	@Test
	public void singleNonSelfPackedProduct_withDims_returnsDims()
	{
		// pTomato is NOT self-packed (default); it has dims set in @BeforeEach.
		assertThat(data.helper.pTomato.isSelfPacked()).isFalse();

		// Create a standalone virtual HU (CU) with qty=1 of tomato
		final HUProducerDestination producer = HUProducerDestination.ofVirtualPI();
		producer.setLocatorId(data.defaultLocatorId);
		data.helper.load(producer, data.helper.pTomatoProductId, BigDecimal.ONE, data.helper.uomKg);

		final List<I_M_HU> createdCUs = producer.getCreatedHUs();
		final I_M_HU cu = CollectionUtils.singleElement(createdCUs);

		// Act
		final PackageDimensions result = huPackageBL.getPackageDimensions(cu);

		// Assert: dims returned (NOT UNSPECIFIED).
		// VHU, single product, qty=1 → ofProductDimensionsAndQty(30×20×10, 1):
		//   sorted [10,20,30], stacking=10×1=10, mid=20, max=30 → length=10, height=20, width=30
		assertThat(result.isUnspecified()).isFalse();
		assertThat(result.getLengthInCM()).isEqualTo(10);
		assertThat(result.getHeightInCM()).isEqualTo(20);
		assertThat(result.getWidthInCM()).isEqualTo(30);
	}

	/**
	 * SysConfig gate: when {@code de.metas.handlingunits.PackageDimensions.CheckIsSelfPacked=Y},
	 * a non-self-packed single product WITH dims must return {@link PackageDimensions#UNSPECIFIED}.
	 * When the SysConfig is absent / 'N' (the default), dims are returned unchanged.
	 */
	@Nested
	class IsSelfPackedGate
	{
		private static final String SYSCONFIG_CHECK_IS_SELF_PACKED
				= "de.metas.handlingunits.PackageDimensions.CheckIsSelfPacked";

		@Test
		public void whenSysConfigY_nonSelfPacked_withDims_returnsUnspecified()
		{
			// Arrange: set SysConfig to Y (legacy gate active)
			Services.get(ISysConfigBL.class).setValue(SYSCONFIG_CHECK_IS_SELF_PACKED, true, ClientId.SYSTEM, OrgId.ANY);

			// pTomato is NOT self-packed; it has dims set in @BeforeEach.
			assertThat(data.helper.pTomato.isSelfPacked()).isFalse();

			final HUProducerDestination producer = HUProducerDestination.ofVirtualPI();
			producer.setLocatorId(data.defaultLocatorId);
			data.helper.load(producer, data.helper.pTomatoProductId, BigDecimal.ONE, data.helper.uomKg);

			final I_M_HU cu = CollectionUtils.singleElement(producer.getCreatedHUs());

			// Act
			final PackageDimensions result = huPackageBL.getPackageDimensions(cu);

			// Assert: gate active → UNSPECIFIED (IsSelfPacked=false blocks the product dims)
			assertThat(result.isUnspecified()).isTrue();
		}

		@Test
		public void whenSysConfigN_nonSelfPacked_withDims_returnsDims()
		{
			// Arrange: SysConfig absent / N → default OFF (new behaviour)
			Services.get(ISysConfigBL.class).setValue(SYSCONFIG_CHECK_IS_SELF_PACKED, false, ClientId.SYSTEM, OrgId.ANY);

			assertThat(data.helper.pTomato.isSelfPacked()).isFalse();

			final HUProducerDestination producer = HUProducerDestination.ofVirtualPI();
			producer.setLocatorId(data.defaultLocatorId);
			data.helper.load(producer, data.helper.pTomatoProductId, BigDecimal.ONE, data.helper.uomKg);

			final I_M_HU cu = CollectionUtils.singleElement(producer.getCreatedHUs());

			// Act
			final PackageDimensions result = huPackageBL.getPackageDimensions(cu);

			// Assert: gate inactive → dims returned
			assertThat(result.isUnspecified()).isFalse();
			assertThat(result.getLengthInCM()).isEqualTo(10);
			assertThat(result.getHeightInCM()).isEqualTo(20);
			assertThat(result.getWidthInCM()).isEqualTo(30);
		}

		/**
		 * Exercises {@code resolveSingleUnitDimensions} (the multi-parcel split path in
		 * {@code createM_Packages}): a non-self-packed VHU with integer qty=2 is split into 2 parcels;
		 * when SysConfig=Y, each parcel must carry UNSPECIFIED dims.
		 */
		@Test
		public void whenSysConfigY_nonSelfPacked_multiParcelSplit_eachParcelUnspecified()
		{
			// Arrange: gate active
			Services.get(ISysConfigBL.class).setValue(SYSCONFIG_CHECK_IS_SELF_PACKED, true, ClientId.SYSTEM, OrgId.ANY);

			// pTomato is NOT self-packed; it has dims (30×20×10) set in @BeforeEach.
			assertThat(data.helper.pTomato.isSelfPacked()).isFalse();

			// Create a loose VHU with integer qty=2 — triggers the multi-parcel split in createM_Packages.
			final HUProducerDestination producer = HUProducerDestination.ofVirtualPI();
			producer.setLocatorId(data.defaultLocatorId);
			data.helper.load(producer, data.helper.pTomatoProductId, new BigDecimal("2"), data.helper.uomKg);
			final I_M_HU cu = CollectionUtils.singleElement(producer.getCreatedHUs());

			// createM_Package requires C_BPartner_ID and C_BPartner_Location_ID > 0
			cu.setC_BPartner_ID(1);
			cu.setC_BPartner_Location_ID(1);
			save(cu);

			final CreatePackageForHURequest request = CreatePackageForHURequest.builder()
					.hu(cu)
					.shipperId(ShipperId.ofRepoId(1))
					.build();

			// Act: qty=2 → 2 parcels, each via resolveSingleUnitDimensions
			final List<I_M_Package> packages = huPackageBL.createM_Packages(request);

			// Assert: 2 parcels, each with UNSPECIFIED dims (-1)
			assertThat(packages).hasSize(2);
			for (final I_M_Package pkg : packages)
			{
				assertThat(pkg.getLengthInCm()).isEqualTo(-1);
				assertThat(pkg.getWidthInCm()).isEqualTo(-1);
				assertThat(pkg.getHeightInCm()).isEqualTo(-1);
			}
		}
	}

	@Nested
	class MultiProductTU
	{
		/**
		 * (a) Multi-product TU with TU pi-version mode=Strapping → aggregated dims, NOT UNSPECIFIED.
		 *
		 * <p>Strapping: stacking-axis = sum of (min_edge × qty); other two = max of larger edges across products.</p>
		 * <p>Tomato (30×20×10): sorted [10,20,30], qty=3. Salad (25×15×12): sorted [12,15,25], qty=2.</p>
		 * <p>Stacking axis (lengthInCM) = 3×10 + 2×12 = 30+24 = 54.</p>
		 * <p>heightInCM (mid max) = max(20, 15) = 20.</p>
		 * <p>widthInCM  (max max) = max(30, 25) = 30.</p>
		 */
		@Test
		public void withStrappingMode_returnsAggregatedDims()
		{
			// Set mode=Strapping on the no-packing-material TU PI version.
			// Using piTU_NoPackMat so the HU has no packing material IDs → getPackageDimensions
			// skips the packing-material branch and enters the product-based multi-product dispatch.
			final I_M_HU_PI_Version piVersion = Services.get(IHandlingUnitsDAO.class).retrievePICurrentVersion(piTU_NoPackMat);
			piVersion.setPackageDimensionCalcMethod("S"); // Strapping code
			save(piVersion);

			// Step 1: create a standalone TU (no LU) with 3kg Tomatoes via LUTUProducerDestination.
			final LUTUProducerDestination producer = new LUTUProducerDestination();
			producer.setLocatorId(data.defaultLocatorId);
			producer.setNoLU();
			producer.setTUPI(piTU_NoPackMat);
			data.helper.load(producer, data.helper.pTomatoProductId, new BigDecimal("3"), data.helper.uomKg);

			final List<I_M_HU> createdTUs = producer.getCreatedHUs();
			assertThat(createdTUs).isNotEmpty();
			final I_M_HU tu = createdTUs.get(0);

			// Step 2: create a standalone salad CU with 2 each, then move it into the TU.
			// LUTUProducerDestination uses a per-product HU cursor, so two separate loads create two TUs.
			// We must use cuToExistingTU to build a genuine multi-product TU.
			final HUProducerDestination saladProducer = HUProducerDestination.ofVirtualPI();
			saladProducer.setLocatorId(data.defaultLocatorId);
			data.helper.load(saladProducer, data.helper.pSaladProductId, new BigDecimal("2"), data.helper.uomEach);
			final I_M_HU saladCU = CollectionUtils.singleElement(saladProducer.getCreatedHUs());

			HUTransformService.newInstance(data.helper.getHUContext())
					.cuToExistingTU(saladCU, Quantity.of(new BigDecimal("2"), data.helper.uomEach), tu);

			// Act
			final PackageDimensions result = huPackageBL.getPackageDimensions(tu);

			// Assert: aggregated dims must NOT be UNSPECIFIED
			assertThat(result.isUnspecified()).isFalse();
			// Stacking axis (lengthInCM): 3×10 + 2×12 = 54
			assertThat(result.getLengthInCM()).isEqualTo(54);
			// heightInCM = max mid-edge: max(20,15) = 20
			assertThat(result.getHeightInCM()).isEqualTo(20);
			// widthInCM = max max-edge: max(30,25) = 30
			assertThat(result.getWidthInCM()).isEqualTo(30);
		}

		/**
		 * (c) When one or more items in a multi-product TU have no dims, the result is UNSPECIFIED without error.
		 */
		@Test
		public void itemWithoutDims_returnsUnspecifiedNoException()
		{
			// Remove dims from pSalad: set to -1 which maps to PackageDimensions.UNSPECIFIED (-1,-1,-1).
			data.helper.pSalad.setLengthInCm(-1);
			data.helper.pSalad.setWidthInCm(-1);
			data.helper.pSalad.setHeightInCm(-1);
			save(data.helper.pSalad);

			// Set a calc mode so we enter the multi-product dispatch path.
			// Using piTU_NoPackMat so the HU has no packing material IDs → enters the product-based branch.
			final I_M_HU_PI_Version piVersion = Services.get(IHandlingUnitsDAO.class).retrievePICurrentVersion(piTU_NoPackMat);
			piVersion.setPackageDimensionCalcMethod("S"); // Strapping
			save(piVersion);

			// Step 1: create a standalone TU with 3kg Tomatoes (has dims).
			final LUTUProducerDestination producer = new LUTUProducerDestination();
			producer.setLocatorId(data.defaultLocatorId);
			producer.setNoLU();
			producer.setTUPI(piTU_NoPackMat);
			data.helper.load(producer, data.helper.pTomatoProductId, new BigDecimal("3"), data.helper.uomKg);

			final List<I_M_HU> createdTUs = producer.getCreatedHUs();
			assertThat(createdTUs).isNotEmpty();
			final I_M_HU tu = createdTUs.get(0);

			// Step 2: create a salad CU (no dims) and move it into the TU.
			final HUProducerDestination saladProducer = HUProducerDestination.ofVirtualPI();
			saladProducer.setLocatorId(data.defaultLocatorId);
			data.helper.load(saladProducer, data.helper.pSaladProductId, new BigDecimal("2"), data.helper.uomEach);
			final I_M_HU saladCU = CollectionUtils.singleElement(saladProducer.getCreatedHUs());

			HUTransformService.newInstance(data.helper.getHUContext())
					.cuToExistingTU(saladCU, Quantity.of(new BigDecimal("2"), data.helper.uomEach), tu);

			// Act: salad has no dims → ofItems returns UNSPECIFIED; no exception thrown
			final PackageDimensions result = huPackageBL.getPackageDimensions(tu);
			assertThat(result.isUnspecified()).isTrue();
		}
	}
}
