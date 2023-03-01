package de.metas.document.dimension;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_OrderLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderLineDimensionFactoryTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void test()
	{
		final OrderLineDimensionFactory dimensionFactory = new OrderLineDimensionFactory();

		final I_C_OrderLine record = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class);
		final Dimension dimension = DimensionTest.newFullyPopulatedDimension().toBuilder()
				.userElement1Id(0)
				.userElement2Id(0)
				.build();

		dimensionFactory.updateRecord(record, dimension);
		final Dimension dimensionFromRecord = dimensionFactory.getFromRecord(record);
		assertThat(dimensionFromRecord).usingRecursiveComparison().isEqualTo(dimension);
		assertThat(dimensionFromRecord).isEqualTo(dimension);
	}
}