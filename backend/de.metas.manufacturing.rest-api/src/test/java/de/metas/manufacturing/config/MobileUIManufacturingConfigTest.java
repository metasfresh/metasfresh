package de.metas.manufacturing.config;

import de.metas.user.UserId;
import de.metas.util.OptionalBoolean;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_MobileUI_MFG_Config;
import org.compiere.model.I_MobileUI_UserProfile_MFG;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThat;

class MobileUIManufacturingConfigTest
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

	private static MobileUIManufacturingConfig.MobileUIManufacturingConfigBuilder configBuilder()
	{
		return MobileUIManufacturingConfig.builder()
				.isScanResourceRequired(OptionalBoolean.UNKNOWN)
				.isAllowIssuingAnyHU(OptionalBoolean.UNKNOWN)
				.isBestBeforeDateEditable(OptionalBoolean.UNKNOWN)
				.isLotNumberEditable(OptionalBoolean.UNKNOWN)
				.isAllowReceiveToLU(OptionalBoolean.UNKNOWN)
				.isAllowReceiveToTU(OptionalBoolean.UNKNOWN)
				.isSkipReceiveTargetStep(OptionalBoolean.UNKNOWN)
				.isCaptureCatchWeightAtReceipt(OptionalBoolean.UNKNOWN);
	}

	private void createGlobalConfig(
			final boolean isAllowReceiveToLU,
			final boolean isAllowReceiveToTU,
			final boolean isSkipReceiveTargetStep,
			final boolean isCaptureCatchWeightAtReceipt)
	{
		final I_MobileUI_MFG_Config record = InterfaceWrapperHelper.newInstance(I_MobileUI_MFG_Config.class);
		record.setIsActive(true);
		record.setIsScanResourceRequired(false);
		record.setIsAllowIssuingAnyHU(false);
		record.setIsBestBeforeDateEditable(true);
		record.setIsLotNumberEditable(true);
		record.setIsAllowReceiveToLU(isAllowReceiveToLU);
		record.setIsAllowReceiveToTU(isAllowReceiveToTU);
		record.setIsSkipReceiveTargetStep(isSkipReceiveTargetStep);
		record.setIsCaptureCatchWeightAtReceipt(isCaptureCatchWeightAtReceipt);
		InterfaceWrapperHelper.save(record);
	}

	private void createUserConfig(
			@Nullable final String isAllowReceiveToLU,
			@Nullable final String isAllowReceiveToTU,
			@Nullable final String isSkipReceiveTargetStep,
			@Nullable final String isCaptureCatchWeightAtReceipt)
	{
		final I_MobileUI_UserProfile_MFG record = InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_MFG.class);
		record.setAD_User_ID(USER_ID.getRepoId());
		record.setIsActive(true);
		record.setIsAllowReceiveToLU(isAllowReceiveToLU);
		record.setIsAllowReceiveToTU(isAllowReceiveToTU);
		record.setIsSkipReceiveTargetStep(isSkipReceiveTargetStep);
		record.setIsCaptureCatchWeightAtReceipt(isCaptureCatchWeightAtReceipt);
		InterfaceWrapperHelper.save(record);
	}

	@Nested
	class getIsAllowReceiveToLUEffective
	{
		@Test
		void noConfigAtAll_defaultsToTrue()
		{
			assertThat(repo.getConfig(USER_ID, clientId).getIsAllowReceiveToLUEffective()).isTrue();
		}

		@Test
		void userValueOverridesGlobal()
		{
			final MobileUIManufacturingConfig user = configBuilder().isAllowReceiveToLU(OptionalBoolean.FALSE).build();
			final MobileUIManufacturingConfig global = configBuilder().isAllowReceiveToLU(OptionalBoolean.TRUE).build();

			assertThat(user.fallbackTo(global).getIsAllowReceiveToLUEffective()).isFalse();
		}

		@Test
		void userUnknownInheritsGlobal()
		{
			final MobileUIManufacturingConfig user = configBuilder().build();
			final MobileUIManufacturingConfig global = configBuilder().isAllowReceiveToLU(OptionalBoolean.FALSE).build();

			assertThat(user.fallbackTo(global).getIsAllowReceiveToLUEffective()).isFalse();
		}

		@Test
		void userBlankInheritsGlobalRecord()
		{
			createGlobalConfig(false, true, false, true);
			createUserConfig("", "", "", "");

			assertThat(repo.getConfig(USER_ID, clientId).getIsAllowReceiveToLUEffective()).isFalse();
		}

		@Test
		void userNullInheritsGlobalRecord()
		{
			createGlobalConfig(false, true, false, true);
			createUserConfig(null, null, null, null);

			assertThat(repo.getConfig(USER_ID, clientId).getIsAllowReceiveToLUEffective()).isFalse();
		}

		@Test
		void userRecordValueOverridesGlobalRecord()
		{
			createGlobalConfig(true, true, false, true);
			createUserConfig("N", null, null, null);

			assertThat(repo.getConfig(USER_ID, clientId).getIsAllowReceiveToLUEffective()).isFalse();
		}

		@Test
		void saveUserConfigPersistsTheFlag()
		{
			createGlobalConfig(true, true, false, true);
			repo.saveUserConfig(configBuilder().isAllowReceiveToLU(OptionalBoolean.FALSE).build(), USER_ID);

			assertThat(repo.getConfig(USER_ID, clientId).getIsAllowReceiveToLUEffective()).isFalse();
		}
	}

	@Nested
	class getIsAllowReceiveToTUEffective
	{
		@Test
		void noConfigAtAll_defaultsToTrue()
		{
			assertThat(repo.getConfig(USER_ID, clientId).getIsAllowReceiveToTUEffective()).isTrue();
		}

		@Test
		void userValueOverridesGlobal()
		{
			final MobileUIManufacturingConfig user = configBuilder().isAllowReceiveToTU(OptionalBoolean.FALSE).build();
			final MobileUIManufacturingConfig global = configBuilder().isAllowReceiveToTU(OptionalBoolean.TRUE).build();

			assertThat(user.fallbackTo(global).getIsAllowReceiveToTUEffective()).isFalse();
		}

		@Test
		void userUnknownInheritsGlobal()
		{
			final MobileUIManufacturingConfig user = configBuilder().build();
			final MobileUIManufacturingConfig global = configBuilder().isAllowReceiveToTU(OptionalBoolean.FALSE).build();

			assertThat(user.fallbackTo(global).getIsAllowReceiveToTUEffective()).isFalse();
		}

		@Test
		void userBlankInheritsGlobalRecord()
		{
			createGlobalConfig(true, false, false, true);
			createUserConfig("", "", "", "");

			assertThat(repo.getConfig(USER_ID, clientId).getIsAllowReceiveToTUEffective()).isFalse();
		}

		@Test
		void userRecordValueOverridesGlobalRecord()
		{
			createGlobalConfig(true, true, false, true);
			createUserConfig(null, "N", null, null);

			assertThat(repo.getConfig(USER_ID, clientId).getIsAllowReceiveToTUEffective()).isFalse();
		}

		@Test
		void saveUserConfigPersistsTheFlag()
		{
			createGlobalConfig(true, true, false, true);
			repo.saveUserConfig(configBuilder().isAllowReceiveToTU(OptionalBoolean.FALSE).build(), USER_ID);

			assertThat(repo.getConfig(USER_ID, clientId).getIsAllowReceiveToTUEffective()).isFalse();
		}
	}

	@Nested
	class getIsSkipReceiveTargetStepEffective
	{
		@Test
		void noConfigAtAll_defaultsToFalse()
		{
			assertThat(repo.getConfig(USER_ID, clientId).getIsSkipReceiveTargetStepEffective()).isFalse();
		}

		@Test
		void userValueOverridesGlobal()
		{
			final MobileUIManufacturingConfig user = configBuilder().isSkipReceiveTargetStep(OptionalBoolean.TRUE).build();
			final MobileUIManufacturingConfig global = configBuilder().isSkipReceiveTargetStep(OptionalBoolean.FALSE).build();

			assertThat(user.fallbackTo(global).getIsSkipReceiveTargetStepEffective()).isTrue();
		}

		@Test
		void userUnknownInheritsGlobal()
		{
			final MobileUIManufacturingConfig user = configBuilder().build();
			final MobileUIManufacturingConfig global = configBuilder().isSkipReceiveTargetStep(OptionalBoolean.TRUE).build();

			assertThat(user.fallbackTo(global).getIsSkipReceiveTargetStepEffective()).isTrue();
		}

		@Test
		void userBlankInheritsGlobalRecord()
		{
			createGlobalConfig(true, true, true, true);
			createUserConfig("", "", "", "");

			assertThat(repo.getConfig(USER_ID, clientId).getIsSkipReceiveTargetStepEffective()).isTrue();
		}

		@Test
		void userRecordValueOverridesGlobalRecord()
		{
			createGlobalConfig(true, true, false, true);
			createUserConfig(null, null, "Y", null);

			assertThat(repo.getConfig(USER_ID, clientId).getIsSkipReceiveTargetStepEffective()).isTrue();
		}

		@Test
		void saveUserConfigPersistsTheFlag()
		{
			createGlobalConfig(true, true, false, true);
			repo.saveUserConfig(configBuilder().isSkipReceiveTargetStep(OptionalBoolean.TRUE).build(), USER_ID);

			assertThat(repo.getConfig(USER_ID, clientId).getIsSkipReceiveTargetStepEffective()).isTrue();
		}
	}

	@Nested
	class getIsCaptureCatchWeightAtReceiptEffective
	{
		@Test
		void noConfigAtAll_defaultsToTrue()
		{
			assertThat(repo.getConfig(USER_ID, clientId).getIsCaptureCatchWeightAtReceiptEffective()).isTrue();
		}

		@Test
		void userValueOverridesGlobal()
		{
			final MobileUIManufacturingConfig user = configBuilder().isCaptureCatchWeightAtReceipt(OptionalBoolean.FALSE).build();
			final MobileUIManufacturingConfig global = configBuilder().isCaptureCatchWeightAtReceipt(OptionalBoolean.TRUE).build();

			assertThat(user.fallbackTo(global).getIsCaptureCatchWeightAtReceiptEffective()).isFalse();
		}

		@Test
		void userUnknownInheritsGlobal()
		{
			final MobileUIManufacturingConfig user = configBuilder().build();
			final MobileUIManufacturingConfig global = configBuilder().isCaptureCatchWeightAtReceipt(OptionalBoolean.FALSE).build();

			assertThat(user.fallbackTo(global).getIsCaptureCatchWeightAtReceiptEffective()).isFalse();
		}

		@Test
		void userBlankInheritsGlobalRecord()
		{
			createGlobalConfig(true, true, false, false);
			createUserConfig("", "", "", "");

			assertThat(repo.getConfig(USER_ID, clientId).getIsCaptureCatchWeightAtReceiptEffective()).isFalse();
		}

		@Test
		void userRecordValueOverridesGlobalRecord()
		{
			createGlobalConfig(true, true, false, true);
			createUserConfig(null, null, null, "N");

			assertThat(repo.getConfig(USER_ID, clientId).getIsCaptureCatchWeightAtReceiptEffective()).isFalse();
		}

		@Test
		void saveUserConfigPersistsTheFlag()
		{
			createGlobalConfig(true, true, false, true);
			repo.saveUserConfig(configBuilder().isCaptureCatchWeightAtReceipt(OptionalBoolean.FALSE).build(), USER_ID);

			assertThat(repo.getConfig(USER_ID, clientId).getIsCaptureCatchWeightAtReceiptEffective()).isFalse();
		}
	}
}
