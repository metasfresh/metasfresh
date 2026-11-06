package de.metas.handlingunits;

import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CreateHUFromInventoryRequestTest
{
	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
	}

	private static CreateHUFromInventoryRequest.CreateHUFromInventoryRequestBuilder requestWithoutDate()
	{
		final I_C_UOM uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		return CreateHUFromInventoryRequest.builder()
				.productId(ProductId.ofRepoId(1))
				.qty(Quantity.of(1, uom))
				.locatorId(LocatorId.ofRepoId(WarehouseId.ofRepoId(1), 1));
	}

	/** Failure scenario: a caller that forgets the date must not silently get "now" (the caller owns the time source). */
	@Test
	void withoutDate_isRefused()
	{
		assertThatThrownBy(() -> requestWithoutDate().build())
				.isInstanceOf(NullPointerException.class)
				.hasMessageContaining("date");
	}

	@Test
	void withDate_keepsIt()
	{
		final ZonedDateTime date = ZonedDateTime.of(2026, 1, 15, 10, 30, 0, 0, ZoneId.of("Europe/Berlin"));
		assertThat(requestWithoutDate().date(date).build().getDate()).isEqualTo(date);
	}
}
