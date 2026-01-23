package de.metas.handlingunits.picking.job.model;

import com.google.common.collect.ImmutableList;
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

class PickingJobCandidateProductsTest
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
		private final ProductId p1 = ProductId.ofRepoId(1);
		private final ProductId p2 = ProductId.ofRepoId(2);

		@SuppressWarnings("SameParameterValue")
		PickingJobCandidateProduct product(ProductId productId, String qtyToDeliver, String qtyAvailable)
		{
			return PickingJobCandidateProduct.builder()
					.productId(productId)
					.productName(TranslatableStrings.anyLanguage("productId=" + productId.getRepoId()))
					.qtyToDeliver(qty(qtyToDeliver))
					.qtyAvailableToPick(qty(qtyAvailable))
					.build();
		}

		PickingJobCandidateProducts products(PickingJobCandidateProduct... products)
		{
			return PickingJobCandidateProducts.ofList(ImmutableList.copyOf(products));
		}

		@Test
		void unknown()
		{
			assertThat(products(
					product(p1, "10", null)
			).getQtyAvailableStatus()).isEmpty();
			assertThat(products(
					product(p1, "10", null),
					product(p2, "10", "10")
			).getQtyAvailableStatus()).isEmpty();
		}

		@Test
		void not_available()
		{
			assertThat(products(
					product(p1, "10", "0"),
					product(p2, "10", "0")
			).getQtyAvailableStatus()).contains(QtyAvailableStatus.NOT_AVAILABLE);
		}

		@Test
		void partially_available()
		{
			assertThat(products(
					product(p1, "10", "10"),
					product(p2, "10", "0")
			).getQtyAvailableStatus()).contains(QtyAvailableStatus.PARTIALLY_AVAILABLE);

			assertThat(products(
					product(p1, "10", "10"),
					product(p2, "10", "9")
			).getQtyAvailableStatus()).contains(QtyAvailableStatus.PARTIALLY_AVAILABLE);

			assertThat(products(
					product(p1, "10", "8"),
					product(p2, "10", "9")
			).getQtyAvailableStatus()).contains(QtyAvailableStatus.PARTIALLY_AVAILABLE);
		}

		@Test
		void fully_available()
		{
			assertThat(products(
					product(p1, "10", "10"),
					product(p2, "10", "10")
			).getQtyAvailableStatus()).contains(QtyAvailableStatus.FULLY_AVAILABLE);
		}

	}

}