package org.adempiere.mmovement.api.impl;

import de.metas.product.ProductId;
import org.adempiere.mmovement.MovementLineQuery;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_MovementLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MovementDAOTest
{
	private MovementDAO movementDAO;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		movementDAO = new MovementDAO();
	}

	@Test
	public void retrieveLinesByQuery_withNoFilterAtAll_isRejected()
	{
		final MovementLineQuery query = MovementLineQuery.builder().build();
		assertThat(query.isEmpty()).isTrue();

		// else the query would answer "yes, such a line exists" with an arbitrary line of the system
		assertThatThrownBy(() -> movementDAO.retrieveLinesByQuery(query))
				.hasMessageContaining("At least one filter is required");
	}

	@Test
	public void retrieveLinesByQuery_withOneFilter_returnsAllMatchingLines()
	{
		final ProductId productId = ProductId.ofRepoId(1000000);

		final I_M_MovementLine line1 = createMovementLine(productId);
		final I_M_MovementLine line2 = createMovementLine(productId);
		// an unrelated line for a different product must not be returned
		createMovementLine(ProductId.ofRepoId(1000001));

		final MovementLineQuery query = MovementLineQuery.builder().productId(productId).build();
		assertThat(query.isEmpty()).isFalse();

		final List<I_M_MovementLine> result = movementDAO.retrieveLinesByQuery(query);
		assertThat(result)
				.extracting(I_M_MovementLine::getM_MovementLine_ID)
				.containsExactlyInAnyOrder(line1.getM_MovementLine_ID(), line2.getM_MovementLine_ID());
	}

	private I_M_MovementLine createMovementLine(final ProductId productId)
	{
		final I_AD_Org org = InterfaceWrapperHelper.newInstance(I_AD_Org.class);
		InterfaceWrapperHelper.save(org);

		final I_M_MovementLine movementLine = InterfaceWrapperHelper.newInstance(I_M_MovementLine.class);
		movementLine.setM_Product_ID(productId.getRepoId());
		movementLine.setAD_Org(org);
		InterfaceWrapperHelper.save(movementLine);

		return movementLine;
	}
}
