package de.metas.document.dimension;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import de.metas.sectionCode.SectionCodeId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DimensionTest
{
	public static Dimension newFullyPopulatedDimension()
	{
		return newFullyPopulatedDimension(0);
	}

	public static Dimension newFullyPopulatedDimension(int idSeed)
	{
		return Dimension.builder()
				.projectId(ProjectId.ofRepoId(idSeed + 1))
				.campaignId(idSeed + 2)
				.activityId(ActivityId.ofRepoId(idSeed + 3))
				.salesOrderId(OrderId.ofRepoId(idSeed + 4))
				.sectionCodeId(SectionCodeId.ofRepoId(idSeed + 5))
				.productId(ProductId.ofRepoId(idSeed + 6))
				.bpartnerId2(BPartnerId.ofRepoId(idSeed + 11))
				.user1_ID(idSeed + 7)
				.user2_ID(idSeed + 8)
				.userElement1Id(idSeed + 9)
				.userElement2Id(idSeed + 10)
				.userElementString1("userElementString1-" + idSeed)
				.userElementString2("userElementString2-" + idSeed)
				.userElementString3("userElementString3-" + idSeed)
				.userElementString4("userElementString4-" + idSeed)
				.userElementString5("userElementString5-" + idSeed)
				.userElementString6("userElementString6-" + idSeed)
				.userElementString7("userElementString7-" + idSeed)
				.build();
	}

	@Nested
	class fallbackTo
	{
		@Test
		void empty_fallbackTo_fullyPopulated()
		{
			final Dimension fullyPopulated = newFullyPopulatedDimension();
			assertThat(Dimension.EMPTY.fallbackTo(fullyPopulated)).isSameAs(fullyPopulated);
		}

		@Test
		void fullyPopulated_fallbackTo_empty()
		{
			final Dimension fullyPopulated = newFullyPopulatedDimension();
			assertThat(fullyPopulated.fallbackTo(Dimension.EMPTY)).isSameAs(fullyPopulated);
		}

		@Test
		void partiallyPopulated_fallbackTo_partiallyPopulated()
		{
			final Dimension dim1 = Dimension.builder().productId(ProductId.ofRepoId(1)).build();
			final Dimension dim2 = Dimension.builder().sectionCodeId(SectionCodeId.ofRepoId(2)).build();

			assertThat(dim1.fallbackTo(dim2))
					.isEqualTo(Dimension.builder()
							.productId(ProductId.ofRepoId(1))
							.sectionCodeId(SectionCodeId.ofRepoId(2))
							.build());
		}
	}

	@Nested
	class extractCommonDimension
	{
		@Test
		void emptyList()
		{
			assertThat(Dimension.extractCommonDimension(ImmutableList.of())).isEqualTo(Dimension.builder().build());
		}

		@Test
		void singleElement()
		{
			final Dimension dimension = newFullyPopulatedDimension();
			assertThat(Dimension.extractCommonDimension(ImmutableList.of(dimension))).isEqualTo(dimension);
		}

		@Test
		void sameElementButDuplicate()
		{
			final Dimension dimension = newFullyPopulatedDimension();
			assertThat(Dimension.extractCommonDimension(ImmutableList.of(dimension, dimension))).isEqualTo(dimension);
		}

		@Test
		void sameElementButDuplicate_and_anEmptyDimension()
		{
			final Dimension dimension = newFullyPopulatedDimension();
			final Dimension empty = Dimension.builder().build();
			assertThat(Dimension.extractCommonDimension(ImmutableList.of(dimension, empty, dimension)))
					.usingRecursiveComparison()
					.isEqualTo(dimension);
		}

		@Test
		void bpartnerId2_1_null_1()
		{
			assertThat(Dimension.extractCommonDimension(ImmutableList.of(
					Dimension.builder().bpartnerId2(BPartnerId.ofRepoId(1)).build(),
					Dimension.builder().bpartnerId2(null).build(),
					Dimension.builder().bpartnerId2(BPartnerId.ofRepoId(1)).build()
			)))
					.isEqualTo(Dimension.builder().bpartnerId2(BPartnerId.ofRepoId(1)).build());
		}

		@Test
		void bpartnerId2_1_null_2()
		{
			assertThat(Dimension.extractCommonDimension(ImmutableList.of(
					Dimension.builder().bpartnerId2(BPartnerId.ofRepoId(1)).build(),
					Dimension.builder().bpartnerId2(null).build(),
					Dimension.builder().bpartnerId2(BPartnerId.ofRepoId(2)).build()
			)))
					.isEqualTo(Dimension.builder().bpartnerId2(null).build());
		}
	}
}