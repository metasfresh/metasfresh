package org.adempiere.mmovement.api.impl;

import de.metas.product.ProductId;
import org.adempiere.mmovement.MovementLineQuery;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
	public void getLineByQuery_withNoFilterAtAll_isRejected()
	{
		final MovementLineQuery query = MovementLineQuery.builder().build();
		assertThat(query.isEmpty()).isTrue();

		// else the query would answer "yes, such a line exists" with an arbitrary line of the system
		assertThatThrownBy(() -> movementDAO.getLineByQuery(query))
				.hasMessageContaining("At least one filter is required");
	}

	@Test
	public void getLineByQuery_withOneFilter_isAccepted()
	{
		final MovementLineQuery query = MovementLineQuery.builder().productId(ProductId.ofRepoId(1000000)).build();
		assertThat(query.isEmpty()).isFalse();

		assertThat(movementDAO.getLineByQuery(query)).isEmpty();
	}
}
