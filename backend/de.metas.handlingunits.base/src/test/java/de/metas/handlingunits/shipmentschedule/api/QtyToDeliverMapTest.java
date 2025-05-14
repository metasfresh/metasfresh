package de.metas.handlingunits.shipmentschedule.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import de.metas.business.BusinessTestHelper;
import de.metas.inout.ShipmentScheduleId;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

class QtyToDeliverMapTest
{
	private I_C_UOM uomKg;
	private ProductId productId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final I_C_UOM uomEach = BusinessTestHelper.createUomEach();
		uomKg = BusinessTestHelper.createUomKg();
		productId = BusinessTestHelper.createProductId("product", uomEach);
	}

	private static void testSerializeDeserialize(final QtyToDeliverMap obj) throws JsonProcessingException
	{
		System.out.println("obj             : " + obj);

		final ObjectMapper jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		final String jsonStr = jsonObjectMapper.writeValueAsString(obj);
		System.out.println("json string: " + jsonStr);

		final QtyToDeliverMap qtyToDeliverMap2 = jsonObjectMapper.readValue(jsonStr, QtyToDeliverMap.class);
		System.out.println("obj deserialized: " + qtyToDeliverMap2);

		assertThat(qtyToDeliverMap2).usingRecursiveComparison().isEqualTo(obj);
		assertThat(qtyToDeliverMap2).isEqualTo(obj);
	}

	private StockQtyAndUOMQty zero()
	{
		return StockQtyAndUOMQtys.createZero(productId, UomId.ofRepoId(uomKg.getC_UOM_ID()));
	}

	@SuppressWarnings("SameParameterValue")
	private StockQtyAndUOMQty stockAndUOMQty(final String qtyInStockUOM, final String qtyInUOM)
	{
		return StockQtyAndUOMQtys.create(new BigDecimal(qtyInStockUOM), productId, new BigDecimal(qtyInUOM), UomId.ofRepoId(uomKg.getC_UOM_ID()));
	}

	@SuppressWarnings("SameParameterValue")
	private StockQtyAndUOMQty stockQty(final String qtyInStockUOM)
	{
		return StockQtyAndUOMQtys.ofQtyInStockUOM(new BigDecimal(qtyInStockUOM), productId);
	}

	private static QtyToDeliverMap toMap(final StockQtyAndUOMQty... qtys)
	{
		final HashMap<ShipmentScheduleId, StockQtyAndUOMQty> map = new HashMap<>();
		for (int i = 1; i <= qtys.length; i++)
		{
			map.put(ShipmentScheduleId.ofRepoId(i), qtys[i - 1]);
		}

		return QtyToDeliverMap.ofMap(map);
	}

	@Test
	void testSerializeDeserialize() throws JsonProcessingException
	{
		testSerializeDeserialize(toMap(
				zero(),
				stockQty("123.45"),
				stockAndUOMQty("123.45", "678.98")
		));
	}
}