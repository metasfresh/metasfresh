package de.metas.business_rule.trigger;

import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BusinessRuleFireTriggersCommandTest
{
	private IUserDAO userDAO;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		userDAO = Services.get(IUserDAO.class);
	}

	private static I_M_Product productUpdatedBy(final int updatedBy)
	{
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		InterfaceWrapperHelper.saveRecord(product);
		InterfaceWrapperHelper.setValue(product, "UpdatedBy", updatedBy);
		return product;
	}

	@Test
	void existingUser_isKept()
	{
		final I_AD_User user = InterfaceWrapperHelper.newInstance(I_AD_User.class);
		InterfaceWrapperHelper.saveRecord(user);

		assertThat(BusinessRuleFireTriggersCommand.extractTriggeringUserId(userDAO, productUpdatedBy(user.getAD_User_ID())))
				.isEqualTo(UserId.ofRepoId(user.getAD_User_ID()));
	}

	@Test
	void minusOne_becomesSystem()
	{
		assertThat(BusinessRuleFireTriggersCommand.extractTriggeringUserId(userDAO, productUpdatedBy(-1))).isEqualTo(UserId.SYSTEM);
	}

	@Test
	void missingUser_becomesSystem()
	{
		assertThat(BusinessRuleFireTriggersCommand.extractTriggeringUserId(userDAO, productUpdatedBy(987654))).isEqualTo(UserId.SYSTEM);
	}

	@Test
	void systemUser_staysSystem()
	{
		assertThat(BusinessRuleFireTriggersCommand.extractTriggeringUserId(userDAO, productUpdatedBy(UserId.SYSTEM.getRepoId()))).isEqualTo(UserId.SYSTEM);
	}
}
