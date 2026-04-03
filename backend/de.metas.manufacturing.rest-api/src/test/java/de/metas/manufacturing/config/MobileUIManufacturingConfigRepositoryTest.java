package de.metas.manufacturing.config;

import de.metas.user.UserId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_MobileUI_MFG_Config;
import org.compiere.model.I_MobileUI_UserProfile_MFG;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MobileUIManufacturingConfigRepositoryTest
{
	private MobileUIManufacturingConfigRepository repo;
	private ClientId clientId;

	private static final UserId USER_ID = UserId.ofRepoId(540000);

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		repo = new MobileUIManufacturingConfigRepository();
		clientId = ClientId.ofRepoId(Env.getAD_Client_ID(Env.getCtx()));
	}

	private void createGlobalConfig(final ReceiveUnitType receiveUnitType)
	{
		final I_MobileUI_MFG_Config record = InterfaceWrapperHelper.newInstance(I_MobileUI_MFG_Config.class);
		record.setIsActive(true);
		record.setIsScanResourceRequired(false);
		record.setIsAllowIssuingAnyHU(false);
		record.setReceiveUnitType(receiveUnitType != null ? receiveUnitType.getCode() : null);
		InterfaceWrapperHelper.save(record);
	}

	private void createUserConfig(final UserId userId, final ReceiveUnitType receiveUnitType)
	{
		final I_MobileUI_UserProfile_MFG record = InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_MFG.class);
		record.setAD_User_ID(userId.getRepoId());
		record.setIsActive(true);
		record.setIsScanResourceRequired(null);
		record.setIsAllowIssuingAnyHU(null);
		record.setReceiveUnitType(receiveUnitType != null ? receiveUnitType.getCode() : null);
		InterfaceWrapperHelper.save(record);
	}

	@Nested
	class getReceiveUnitTypeEffective
	{
		@Test
		void noConfigAtAll_defaultsToCU()
		{
			final MobileUIManufacturingConfig config = repo.getConfig(USER_ID, clientId);
			assertThat(config.getReceiveUnitTypeEffective()).isEqualTo(ReceiveUnitType.CU);
		}

		@Test
		void noConfigAtAll_nullUserId_defaultsToCU()
		{
			final MobileUIManufacturingConfig config = repo.getConfig(null, clientId);
			assertThat(config.getReceiveUnitTypeEffective()).isEqualTo(ReceiveUnitType.CU);
		}

		@Test
		void globalConfigWithTU()
		{
			createGlobalConfig(ReceiveUnitType.TU);

			final MobileUIManufacturingConfig config = repo.getConfig(USER_ID, clientId);
			assertThat(config.getReceiveUnitTypeEffective()).isEqualTo(ReceiveUnitType.TU);
		}

		@Test
		void globalConfigWithCU()
		{
			createGlobalConfig(ReceiveUnitType.CU);

			final MobileUIManufacturingConfig config = repo.getConfig(USER_ID, clientId);
			assertThat(config.getReceiveUnitTypeEffective()).isEqualTo(ReceiveUnitType.CU);
		}

		@Test
		void userConfigWithTU_noGlobalConfig()
		{
			createUserConfig(USER_ID, ReceiveUnitType.TU);

			final MobileUIManufacturingConfig config = repo.getConfig(USER_ID, clientId);
			assertThat(config.getReceiveUnitTypeEffective()).isEqualTo(ReceiveUnitType.TU);
		}

		@Test
		void userConfigOverridesGlobalConfig()
		{
			createGlobalConfig(ReceiveUnitType.CU);
			createUserConfig(USER_ID, ReceiveUnitType.TU);

			final MobileUIManufacturingConfig config = repo.getConfig(USER_ID, clientId);
			assertThat(config.getReceiveUnitTypeEffective()).isEqualTo(ReceiveUnitType.TU);
		}

		@Test
		void userConfigWithNoReceiveUnitType_fallsBackToGlobal()
		{
			createGlobalConfig(ReceiveUnitType.TU);
			createUserConfig(USER_ID, null);

			final MobileUIManufacturingConfig config = repo.getConfig(USER_ID, clientId);
			assertThat(config.getReceiveUnitTypeEffective()).isEqualTo(ReceiveUnitType.TU);
		}

		@Test
		void nullUserId_usesOnlyGlobalConfig()
		{
			createGlobalConfig(ReceiveUnitType.TU);
			createUserConfig(USER_ID, ReceiveUnitType.CU);

			final MobileUIManufacturingConfig config = repo.getConfig(null, clientId);
			assertThat(config.getReceiveUnitTypeEffective()).isEqualTo(ReceiveUnitType.TU);
		}
	}
}
