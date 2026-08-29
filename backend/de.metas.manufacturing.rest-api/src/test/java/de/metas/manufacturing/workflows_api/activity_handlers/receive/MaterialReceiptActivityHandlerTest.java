package de.metas.manufacturing.workflows_api.activity_handlers.receive;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.manufacturing.config.MobileUIManufacturingConfigRepository;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonNewLUTargetsList;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonNewTUTarget;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonNewTUTargetList;
import de.metas.product.ProductId;
import de.metas.product.allergen.AllergenRepository;
import de.metas.product.allergen.ProductAllergensRepository;
import de.metas.product.allergen.ProductAllergensService;
import de.metas.product.hazard_symbol.HazardSymbolRepository;
import de.metas.product.hazard_symbol.ProductHazardSymbolRepository;
import de.metas.product.hazard_symbol.ProductHazardSymbolService;
import de.metas.scannable_code.format.service.ScannableCodeFormatService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers the receive screen's target OFFERING only — the side that decides which Gebinde the operator is shown.
 * Choosing a target and actually receiving is exercised end-to-end by the mobile Playwright suite.
 */
class MaterialReceiptActivityHandlerTest
{
	private static final String AD_LANGUAGE = "en_US";

	private MaterialReceiptActivityHandler handler;
	private ProductId productId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		// Real collaborators rather than mocks: the module's surefire runs on Java 17 while the pinned
		// ByteBuddy cannot inject subclasses there, and these services are cheap to construct anyway.
		// The target-offering paths under test reach their own collaborators via Services.get(...) instead.
		handler = new MaterialReceiptActivityHandler(
				HUQRCodesService.newInstanceForUnitTesting(),
				new ProductHazardSymbolService(new ProductHazardSymbolRepository(), new HazardSymbolRepository()),
				new ProductAllergensService(new ProductAllergensRepository(), new AllergenRepository()),
				ScannableCodeFormatService.newInstanceForUnitTesting(),
				new MobileUIManufacturingConfigRepository());

		productId = createProduct();
		createVirtualPIItemProduct();
	}

	private static ProductId createProduct()
	{
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setValue("1000007");
		product.setName("Feldahorn H 4xv mDb");
		InterfaceWrapperHelper.save(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	/** The one packing instruction the mobile receive query can never return, because it is HU_UnitType='V'. */
	private static void createVirtualPIItemProduct()
	{
		final I_M_HU_PI_Item_Product piip = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class);
		piip.setM_HU_PI_Item_Product_ID(HUPIItemProductId.VIRTUAL_HU.getRepoId());
		piip.setName("No Packing Item");
		InterfaceWrapperHelper.save(piip);
	}

	/** No physical TU packing exists for the product — the reported Baumschule case. */
	private static List<I_M_HU_PI_Item_Product> noPhysicalTUs()
	{
		return ImmutableList.of();
	}

	@Nested
	class getNewTUTargets
	{
		@Test
		void flagOff_noPhysicalTU_offersNothingAndExplainsWhy()
		{
			final JsonNewTUTargetList result = handler.getNewTUTargets(noPhysicalTUs(), false, productId, AD_LANGUAGE);

			assertThat(result.getValues()).isEmpty();
			assertThat(result.getEmptyReason()).isNotBlank();
		}

		@Test
		void flagOn_noPhysicalTU_offersTheVirtualPackingInstruction()
		{
			final JsonNewTUTargetList result = handler.getNewTUTargets(noPhysicalTUs(), true, productId, AD_LANGUAGE);

			assertThat(result.getValues())
					.extracting(JsonNewTUTarget::getTuPIItemProductId)
					.containsExactly(HUPIItemProductId.VIRTUAL_HU);
		}

		@Test
		void flagOn_noPhysicalTU_carriesNoEmptyReason()
		{
			// The guidance tells the operator to fix master data; with a target on screen that would be misleading.
			final JsonNewTUTargetList result = handler.getNewTUTargets(noPhysicalTUs(), true, productId, AD_LANGUAGE);

			assertThat(result.getEmptyReason()).isNull();
		}
	}

	@Nested
	class getNewLUTargets
	{
		@Test
		void flagOff_noPhysicalTU_explainsThatNoGebindeCanBeOffered()
		{
			final JsonNewLUTargetsList result = handler.getNewLUTargets(noPhysicalTUs(), false, productId, null, AD_LANGUAGE);

			assertThat(result.getValues()).isEmpty();
			assertThat(result.getEmptyReason()).isNotBlank();
		}

		@Test
		void flagOn_noPhysicalTU_staysSilentBecauseTheTUListOffersATarget()
		{
			// The virtual packing instruction has no LU parent items, so the LU list is legitimately empty — but a
			// target DOES exist, and the backend's invariant is that the guidance only ever accompanies "no target at all".
			final JsonNewLUTargetsList result = handler.getNewLUTargets(noPhysicalTUs(), true, productId, null, AD_LANGUAGE);

			assertThat(result.getValues()).isEmpty();
			assertThat(result.getEmptyReason()).isNull();
		}
	}
}
