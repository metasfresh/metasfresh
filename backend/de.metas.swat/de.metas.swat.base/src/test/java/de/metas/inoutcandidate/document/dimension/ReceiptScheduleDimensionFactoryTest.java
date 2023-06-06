package de.metas.inoutcandidate.document.dimension;

import de.metas.document.dimension.Dimension;
import de.metas.document.dimension.DimensionTest;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.product.ProductId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReceiptScheduleDimensionFactoryTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void set_and_get()
	{
		final ReceiptScheduleDimensionFactory dimensionFactory = new ReceiptScheduleDimensionFactory();

		final I_M_ReceiptSchedule record = InterfaceWrapperHelper.newInstance(I_M_ReceiptSchedule.class);
		record.setM_Product_ID(900002);
		final Dimension dimension = DimensionTest.newFullyPopulatedDimension();
		dimensionFactory.updateRecord(record, dimension);
		dimensionFactory.updateRecordUserElements(record, dimension);

		final Dimension dimensionFromRecord = dimensionFactory.getFromRecord(record);
		final Dimension dimensionFromRecordExpected = dimension.toBuilder()
				.productId(ProductId.ofRepoId(900002))
				.user1_ID(0)
				.user2_ID(0)
				.userElement1Id(0)
				.userElement2Id(0)
				.build();
		assertThat(dimensionFromRecord).usingRecursiveComparison().isEqualTo(dimensionFromRecordExpected);
		assertThat(dimensionFromRecord).isEqualTo(dimensionFromRecordExpected);
	}

	@Test
	void fieldsThatShallNotBeOverridden()
	{
		final ReceiptScheduleDimensionFactory dimensionFactory = new ReceiptScheduleDimensionFactory();

		final I_M_ReceiptSchedule record = InterfaceWrapperHelper.newInstance(I_M_ReceiptSchedule.class);
		record.setC_Order_ID(900001);
		record.setM_Product_ID(900002);

		dimensionFactory.updateRecord(record, DimensionTest.newFullyPopulatedDimension());
		dimensionFactory.updateRecordUserElements(record, DimensionTest.newFullyPopulatedDimension());

		assertThat(record.getC_Order_ID()).isEqualTo(900001);
		assertThat(record.getM_Product_ID()).isEqualTo(900002);
	}
}
