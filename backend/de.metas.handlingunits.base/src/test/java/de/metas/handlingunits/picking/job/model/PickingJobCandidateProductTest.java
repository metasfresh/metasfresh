package de.metas.handlingunits.picking.job.model;

import de.metas.business.BusinessTestHelper;
import de.metas.i18n.TranslatableStrings;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThat;

class PickingJobCandidateProductTest
{
	private I_C_UOM each;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		each = BusinessTestHelper.createUomEach();
	}

	Quantity qty(@Nullable String qtyStr) {return qtyStr != null ? Quantity.of(qtyStr, each) : null;}

	@Nested
	class getQtyAvailableStatus
	{
		PickingJobCandidateProduct product(String qtyToDeliver, String qtyAvailable)
		{
			return PickingJobCandidateProduct.builder()
					.productId(ProductId.ofRepoId(1))
					.productName(TranslatableStrings.anyLanguage("product"))
					.qtyToDeliver(qty(qtyToDeliver))
					.qtyAvailableToPick(qty(qtyAvailable))
					.build();
		}

		@Test
		void unknown()
		{
			assertThat(product(null, null).getQtyAvailableStatus()).isEmpty();
			assertThat(product("10", null).getQtyAvailableStatus()).isEmpty();
		}

		@Test
		void not_available()
		{
			assertThat(product("10", "0").getQtyAvailableStatus()).contains(QtyAvailableStatus.NOT_AVAILABLE);
		}

		@Test
		void partially_available()
		{
			assertThat(product("10", "4").getQtyAvailableStatus()).contains(QtyAvailableStatus.PARTIALLY_AVAILABLE);
		}

		@Test
		void fully_available()
		{
			assertThat(product("10", "10").getQtyAvailableStatus()).contains(QtyAvailableStatus.FULLY_AVAILABLE);
			assertThat(product("0", "0").getQtyAvailableStatus()).contains(QtyAvailableStatus.FULLY_AVAILABLE); // shall happen
		}

	}
}