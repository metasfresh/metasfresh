package de.metas.material.planning.pporder.impl;

import de.metas.event.log.EventLogService;
import de.metas.event.log.EventLogsRepository;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.OrderBOMLineQuantities;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.UomId;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMVersions;
import org.eevolution.mrp.api.impl.MRPTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * This class tests {@link IPPOrderBOMBL} in convert with {@link IPPOrderBOMDAO}.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class PPOrderBOMBLTest
{
	private MRPTestHelper helper;

	/**
	 * Service under test
	 */
	private PPOrderBOMBL ppOrderBOMBL;

	// Other services
	private IPPOrderBOMDAO ppOrderBOMDAO;

	@BeforeEach
	public void init()
	{
		// NOTE: after this, model validators will be also registered
		helper = new MRPTestHelper();

		SpringContextHolder.registerJUnitBean(new EventLogService(mock(EventLogsRepository.class)));

		ppOrderBOMDAO = Services.get(IPPOrderBOMDAO.class);
		ppOrderBOMBL = (PPOrderBOMBL)Services.get(IPPOrderBOMBL.class);
	}

	@Test
	//@Disabled("gh #523: test doesn't work right now, and we might drop it in future")
	public void qualityMultiplierTest()
	{
		// Mocking the AB Alicesalat 250g case from db

		// Defining the needed UOM
		final I_C_UOM uomKillogram = createUOM("Killogram", 2, 0);
		final I_C_UOM uomStuck = createUOM("Stück", 0, 0);
		final I_C_UOM uomMillimeter = createUOM("Millimeter", 2, 0);
		final I_C_UOM uomRolle = createUOM("Rolle", 2, 0);

		// Defining products

		final I_M_Product pSalad = helper.createProduct("Salad", uomStuck); // AB Alicesalat 250g - the big product bom

		// Conversion Salad
		helper.createUOMConversion(CreateUOMConversionRequest.builder()
				.productId(ProductId.ofRepoId(pSalad.getM_Product_ID()))
				.fromUomId(UomId.ofRepoId(uomStuck.getC_UOM_ID()))
				.toUomId(UomId.ofRepoId(uomKillogram.getC_UOM_ID()))
				.fromToMultiplier(new BigDecimal("0.25"))
				.build());

		// Components
		final I_M_Product pCarrot = helper.createProduct("Carrot", uomKillogram); // Karotten Julienne 3.2 mm Halbfabrikat
		final I_M_Product pFrisee = helper.createProduct("Frisée", uomKillogram); // Frisée Industrie
		final I_M_Product pRadiesli = helper.createProduct("Radiesli", uomKillogram); // P001697_Radiesli Julienne 3.2 mm Halbfabrikat
		// Packing material
		final I_M_Product pFolie = helper.createProduct("Folie", uomRolle); // Folie AB Alicesalat (1000 lm)

		//
		// Conversion for Folie: 1 rolle = 1_500_000 mm
		helper.createUOMConversion(CreateUOMConversionRequest.builder()
				.productId(ProductId.ofRepoId(pFolie.getM_Product_ID()))
				.fromUomId(UomId.ofRepoId(uomRolle.getC_UOM_ID()))
				.toUomId(UomId.ofRepoId(uomMillimeter.getC_UOM_ID()))
				.fromToMultiplier(new BigDecimal("1500000"))
				.build());

		final I_PP_Product_BOMVersions bomVersions = helper.createBOMVersions(ProductId.ofRepoId(pSalad.getM_Product_ID()));
		//
		// Define BOM
		//@formatter:off
		final I_PP_Product_BOM saladProductBom = helper.newProductBOM()
				.product(pSalad).uom(uomStuck)
				.bomVersions(bomVersions)
				// Carrot
				.newBOMLine()
					.product(pCarrot).uom(uomKillogram)
					.setIsQtyPercentage(true)
					.setQtyBatch(new BigDecimal(44))
					.endLine()
				// Frisee
				.newBOMLine()
					.product(pFrisee).uom(uomKillogram)
					.setIsQtyPercentage(true)
					.setQtyBatch(new BigDecimal(36))
					.endLine()
				// Radisli
				.newBOMLine()
					.product(pRadiesli).uom(uomKillogram)
					.setIsQtyPercentage(true)
					.setQtyBatch(new BigDecimal(20))
					.endLine()
				// Folie
				.newBOMLine()
					.product(pFolie).uom(uomMillimeter)
					.setIsQtyPercentage(false)
					.setQtyBOM(new BigDecimal(260))
					.endLine()
				//
				.build();
		//@formatter:off

		//
		// Create and save PP Order
		final I_PP_Order ppOrder = helper.createPP_Order(saladProductBom, "100", uomStuck);

		//
		// Test: Carrot
		{
			final I_PP_Order_BOMLine ppOrderBOMLine_Carrot = ppOrderBOMDAO.retrieveOrderBOMLine(ppOrder, pCarrot);
			assertUOM(uomKillogram, ppOrderBOMLine_Carrot);

			final Quantity qtyRequiredForOneFinishedGood = ppOrderBOMBL.toQtyCalculationsBOMLine(ppOrder, ppOrderBOMLine_Carrot).getQtyRequiredForOneFinishedGood(); // lineCarrot
			
			// qty ordered = 100, qty batch = 44 (percentaje) -> 0,44 per one stuck
			// one stuck = 0,25 kg -> the qty shall be 0,44 * 0,25 = 0,11
			assertThat(qtyRequiredForOneFinishedGood).isEqualTo(Quantity.of("0.11", uomKillogram));
		}

		//
		// Test: Frisee
		{
			final I_PP_Order_BOMLine ppOrderBOMLine_Frisee = ppOrderBOMDAO.retrieveOrderBOMLine(ppOrder, pFrisee);
			assertUOM(uomKillogram, ppOrderBOMLine_Frisee);

			final Quantity qtyRequiredForOneFinishedGood = ppOrderBOMBL.toQtyCalculationsBOMLine(ppOrder, ppOrderBOMLine_Frisee).getQtyRequiredForOneFinishedGood();
			assertThat(qtyRequiredForOneFinishedGood).isEqualTo(Quantity.of("0.09", uomKillogram));
		}

		//
		// Test: Radisli
		{
			final I_PP_Order_BOMLine ppOrderBOMLine_Radiesli = ppOrderBOMDAO.retrieveOrderBOMLine(ppOrder, pRadiesli);
			assertUOM(uomKillogram, ppOrderBOMLine_Radiesli);

			final Quantity qtyRequiredForOneFinishedGood = ppOrderBOMBL.toQtyCalculationsBOMLine(ppOrder, ppOrderBOMLine_Radiesli).getQtyRequiredForOneFinishedGood();
			assertThat(qtyRequiredForOneFinishedGood).isEqualByComparingTo(Quantity.of("0.05", uomKillogram));
		}

		//
		// Test: Folie for 100 salads
		{
			final I_PP_Order_BOMLine ppOrderBOMLine_Folie = ppOrderBOMDAO.retrieveOrderBOMLine(ppOrder, pFolie);
			assertUOM(uomMillimeter, ppOrderBOMLine_Folie);

			final Quantity qtyRequiredForOneFinishedGood = ppOrderBOMBL.toQtyCalculationsBOMLine(ppOrder, ppOrderBOMLine_Folie).getQtyRequiredForOneFinishedGood();
			assertThat(qtyRequiredForOneFinishedGood).isEqualByComparingTo(Quantity.of("260", uomMillimeter));
			assertThat(ppOrderBOMLine_Folie.getQtyRequiered()).isEqualByComparingTo("26000");
		}
	}

	private I_C_UOM createUOM(final String name, final int stdPrecision, final int costingPrecision)
	{
		final I_C_UOM uom = helper.createUOM(name);
		uom.setStdPrecision(stdPrecision);
		uom.setCostingPrecision(costingPrecision);
		InterfaceWrapperHelper.save(uom);
		return uom;
	}

	private void assertUOM(final I_C_UOM expectedUOM, final I_PP_Order_BOMLine ppOrderBOMLine)
	{
		assertThat(ppOrderBOMLine.getC_UOM_ID())
			.as("BOM line's UOM: "+ppOrderBOMLine)
			.isEqualTo(expectedUOM.getC_UOM_ID());
	}

	private void testExtractUpdateOrderBOMLineQuantities(final OrderBOMLineQuantities qtys)
	{
		final I_PP_Order_BOMLine record = InterfaceWrapperHelper.newInstance(I_PP_Order_BOMLine.class);
		PPOrderBOMBL.updateRecord(record, qtys);

		final OrderBOMLineQuantities qtysActual = ppOrderBOMBL.getQuantities(record);
		assertThat(qtysActual).usingRecursiveComparison().isEqualTo(qtys);
	}

	@Test
	public void testExtractUpdateOrderBOMLineQuantities()
	{
		final I_C_UOM uomKg = helper.uomKg;
		testExtractUpdateOrderBOMLineQuantities(OrderBOMLineQuantities.builder()
				.qtyRequired(Quantity.of("1", uomKg))
				.qtyRequiredBeforeClose(Quantity.of("2", uomKg))
				.qtyIssuedOrReceived(Quantity.of("3", uomKg))
				.qtyIssuedOrReceivedActual((Quantity.of("4", uomKg)))
				.qtyReject(Quantity.of("5", uomKg))
				.qtyScrap(Quantity.of("6", uomKg))
				.qtyUsageVariance(Quantity.of("7", uomKg))
				.qtyPost(Quantity.of("8", uomKg))
				.qtyReserved(Quantity.of("9", uomKg))

		.build());
	}

}
