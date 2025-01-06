package de.metas.document.dimension;

import de.metas.product.ProductId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_InOutLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class InOutLineDimensionFactoryTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void get_and_set()
	{
		final InOutLineDimensionFactory dimensionFactory = new InOutLineDimensionFactory();

		final I_M_InOutLine record = InterfaceWrapperHelper.newInstance(I_M_InOutLine.class);
		record.setM_Product_ID(9000002);

		final Dimension dimension = DimensionTest.newFullyPopulatedDimension();
		dimensionFactory.updateRecord(record, dimension);

		final Dimension dimensionFromRecord = dimensionFactory.getFromRecord(record);
		final Dimension dimensionFromRecordExpected = dimension.toBuilder()
				.productId(ProductId.ofRepoId(9000002))
				.userElement1Id(0)
				.userElement2Id(0)
				.build();
		assertThat(dimensionFromRecord).usingRecursiveComparison().isEqualTo(dimensionFromRecordExpected);
		assertThat(dimensionFromRecord).isEqualTo(dimensionFromRecordExpected);
	}

	@Test
	void fieldsThatShallNotBeOverridden()
	{
		final InOutLineDimensionFactory dimensionFactory = new InOutLineDimensionFactory();

		final I_M_InOutLine record = InterfaceWrapperHelper.newInstance(I_M_InOutLine.class);
		record.setC_Order_ID(9000001);
		record.setM_Product_ID(9000002);

		dimensionFactory.updateRecord(record, DimensionTest.newFullyPopulatedDimension());

		assertThat(record.getC_Order_ID()).isEqualTo(9000001);
		assertThat(record.getM_Product_ID()).isEqualTo(9000002);
	}
}