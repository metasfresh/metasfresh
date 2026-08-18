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
				.isAllowFinishedGoodsReceiveToLU(OptionalBoolean.UNKNOWN)
				.isAllowFinishedGoodsReceiveToTU(OptionalBoolean.UNKNOWN)
				.isSkipFinishedGoodsReceiveTargetStep(OptionalBoolean.UNKNOWN)
				.isCaptureFinishedGoodsCatchWeightAtReceipt(OptionalBoolean.UNKNOWN);
	}

	private void createGlobalConfig(
			final boolean isAllowFinishedGoodsReceiveToLU,
			final boolean isAllowFinishedGoodsReceiveToTU,
			final boolean isSkipFinishedGoodsReceiveTargetStep,
			final boolean isCaptureFinishedGoodsCatchWeightAtReceipt)
	{
		final I_MobileUI_MFG_Config record = InterfaceWrapperHelper.newInstance(I_MobileUI_MFG_Config.class);
		record.setIsActive(true);
		record.setIsScanResourceRequired(false);
		record.setIsAllowIssuingAnyHU(false);
		record.setIsBestBeforeDateEditable(true);
		record.setIsLotNumberEditable(true);
		record.setIsAllowFinishedGoodsReceiveToLU(isAllowFinishedGoodsReceiveToLU);
		record.setIsAllowFinishedGoodsReceiveToTU(isAllowFinishedGoodsReceiveToTU);
		record.setIsSkipFinishedGoodsReceiveTargetStep(isSkipFinishedGoodsReceiveTargetStep);
		record.setIsCaptureFinishedGoodsCatchWeightAtReceipt(isCaptureFinishedGoodsCatchWeightAtReceipt);
		InterfaceWrapperHelper.save(record);
	}

	private void createUserConfig(
			@Nullable final String isAllowFinishedGoodsReceiveToLU,
			@Nullable final String isAllowFinishedGoodsReceiveToTU,
			@Nullable final String isSkipFinishedGoodsReceiveTargetStep,
			@Nullable final String isCaptureFinishedGoodsCatchWeightAtReceipt)
	{
		final I_MobileUI_UserProfile_MFG record = InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_MFG.class);
		record.setAD_User_ID(USER_ID.getRepoId());
		record.setIsActive(true);
		record.setIsAllowFinishedGoodsReceiveToLU(isAllowFinishedGoodsReceiveToLU);
		record.setIsAllowFinishedGoodsReceiveToTU(isAllowFinishedGoodsReceiveToTU);
		record.setIsSkipFinishedGoodsReceiveTargetStep(isSkipFinishedGoodsReceiveTargetStep);
		record.setIsCaptureFinishedGoodsCatchWeightAtReceipt(isCaptureFinishedGoodsCatchWeightAtReceipt);
		InterfaceWrapperHelper.save(record);
	}

	@Nested
	class getIsAllowFinishedGoodsReceiveToLUEffective
	{
		@Test
		void noConfigAtAll_defaultsToTrue()
		{
			assertThat(repo.getConfig(USER_ID, clientId).getIsAllowFinishedGoodsReceiveToLUEffective()).isTrue();
		}

		@Test
		void userValueOverridesGlobal()
		{
			final MobileUIManufacturingConfig user = configBuilder().isAllowFinishedGoodsReceiveToLU(OptionalBoolean.FALSE).build();
			final MobileUIManufacturingConfig global = configBuilder().isAllowFinishedGoodsReceiveToLU(OptionalBoolean.TRUE).build();

			assertThat(user.fallbackTo(global).getIsAllowFinishedGoodsReceiveToLUEffective()).isFalse();
		}

		@Test
		void userUnknownInheritsGlobal()
		{
			final MobileUIManufacturingConfig user = configBuilder().build();
			final MobileUIManufacturingConfig global = configBuilder().isAllowFinishedGoodsReceiveToLU(OptionalBoolean.FALSE).build();

			assertThat(user.fallbackTo(global).getIsAllowFinishedGoodsReceiveToLUEffective()).isFalse();
		}

		@Test
		void userBlankInheritsGlobalRecord()
		{
			createGlobalConfig(false, true, false, true);
			createUserConfig("", "", "", "");

			assertThat(repo.getConfig(USER_ID, clientId).getIsAllowFinishedGoodsReceiveToLUEffective()).isFalse();
		}

		@Test
		void userNullInheritsGlobalRecord()
		{
			createGlobalConfig(false, true, false, true);
			createUserConfig(null, null, null, null);

			assertThat(repo.getConfig(USER_ID, clientId).getIsAllowFinishedGoodsReceiveToLUEffective()).isFalse();
		}

		@Test
		void userRecordValueOverridesGlobalRecord()
		{
			createGlobalConfig(true, true, false, true);
			createUserConfig("N", null, null, null);

			assertThat(repo.getConfig(USER_ID, clientId).getIsAllowFinishedGoodsReceiveToLUEffective()).isFalse();
		}

		@Test
		void saveUserConfigPersistsTheFlag()
		{
			createGlobalConfig(true, true, false, true);
			repo.saveUserConfig(configBuilder().isAllowFinishedGoodsReceiveToLU(OptionalBoolean.FALSE).build(), USER_ID);

			assertThat(repo.getConfig(USER_ID, clientId).getIsAllowFinishedGoodsReceiveToLUEffective()).isFalse();
		}
	}

	@Nested
	class getIsAllowFinishedGoodsReceiveToTUEffective
	{
		@Test
		void noConfigAtAll_defaultsToTrue()
		{
			assertThat(repo.getConfig(USER_ID, clientId).getIsAllowFinishedGoodsReceiveToTUEffective()).isTrue();
		}

		@Test
		void userValueOverridesGlobal()
		{
			final MobileUIManufacturingConfig user = configBuilder().isAllowFinishedGoodsReceiveToTU(OptionalBoolean.FALSE).build();
			final MobileUIManufacturingConfig global = configBuilder().isAllowFinishedGoodsReceiveToTU(OptionalBoolean.TRUE).build();

			assertThat(user.fallbackTo(global).getIsAllowFinishedGoodsReceiveToTUEffective()).isFalse();
		}

		@Test
		void userUnknownInheritsGlobal()
		{
			final MobileUIManufacturingConfig user = configBuilder().build();
			final MobileUIManufacturingConfig global = configBuilder().isAllowFinishedGoodsReceiveToTU(OptionalBoolean.FALSE).build();

			assertThat(user.fallbackTo(global).getIsAllowFinishedGoodsReceiveToTUEffective()).isFalse();
		}

		@Test
		void userBlankInheritsGlobalRecord()
		{
			createGlobalConfig(true, false, false, true);
			createUserConfig("", "", "", "");

			assertThat(repo.getConfig(USER_ID, clientId).getIsAllowFinishedGoodsReceiveToTUEffective()).isFalse();
		}

		@Test
		void userRecordValueOverridesGlobalRecord()
		{
			createGlobalConfig(true, true, false, true);
			createUserConfig(null, "N", null, null);

			assertThat(repo.getConfig(USER_ID, clientId).getIsAllowFinishedGoodsReceiveToTUEffective()).isFalse();
		}

		@Test
		void saveUserConfigPersistsTheFlag()
		{
			createGlobalConfig(true, true, false, true);
			repo.saveUserConfig(configBuilder().isAllowFinishedGoodsReceiveToTU(OptionalBoolean.FALSE).build(), USER_ID);

			assertThat(repo.getConfig(USER_ID, clientId).getIsAllowFinishedGoodsReceiveToTUEffective()).isFalse();
		}
	}

	@Nested
	class getIsSkipFinishedGoodsReceiveTargetStepEffective
	{
		@Test
		void noConfigAtAll_defaultsToFalse()
		{
			assertThat(repo.getConfig(USER_ID, clientId).getIsSkipFinishedGoodsReceiveTargetStepEffective()).isFalse();
		}

		@Test
		void userValueOverridesGlobal()
		{
			final MobileUIManufacturingConfig user = configBuilder().isSkipFinishedGoodsReceiveTargetStep(OptionalBoolean.TRUE).build();
			final MobileUIManufacturingConfig global = configBuilder().isSkipFinishedGoodsReceiveTargetStep(OptionalBoolean.FALSE).build();

			assertThat(user.fallbackTo(global).getIsSkipFinishedGoodsReceiveTargetStepEffective()).isTrue();
		}

		@Test
		void userUnknownInheritsGlobal()
		{
			final MobileUIManufacturingConfig user = configBuilder().build();
			final MobileUIManufacturingConfig global = configBuilder().isSkipFinishedGoodsReceiveTargetStep(OptionalBoolean.TRUE).build();

			assertThat(user.fallbackTo(global).getIsSkipFinishedGoodsReceiveTargetStepEffective()).isTrue();
		}

		@Test
		void userBlankInheritsGlobalRecord()
		{
			createGlobalConfig(true, true, true, true);
			createUserConfig("", "", "", "");

			assertThat(repo.getConfig(USER_ID, clientId).getIsSkipFinishedGoodsReceiveTargetStepEffective()).isTrue();
		}

		@Test
		void userRecordValueOverridesGlobalRecord()
		{
			createGlobalConfig(true, true, false, true);
			createUserConfig(null, null, "Y", null);

			assertThat(repo.getConfig(USER_ID, clientId).getIsSkipFinishedGoodsReceiveTargetStepEffective()).isTrue();
		}

		@Test
		void saveUserConfigPersistsTheFlag()
		{
			createGlobalConfig(true, true, false, true);
			repo.saveUserConfig(configBuilder().isSkipFinishedGoodsReceiveTargetStep(OptionalBoolean.TRUE).build(), USER_ID);

			assertThat(repo.getConfig(USER_ID, clientId).getIsSkipFinishedGoodsReceiveTargetStepEffective()).isTrue();
		}
	}

	@Nested
	class getIsCaptureFinishedGoodsCatchWeightAtReceiptEffective
	{
		@Test
		void noConfigAtAll_defaultsToTrue()
		{
			assertThat(repo.getConfig(USER_ID, clientId).getIsCaptureFinishedGoodsCatchWeightAtReceiptEffective()).isTrue();
		}

		@Test
		void userValueOverridesGlobal()
		{
			final MobileUIManufacturingConfig user = configBuilder().isCaptureFinishedGoodsCatchWeightAtReceipt(OptionalBoolean.FALSE).build();
			final MobileUIManufacturingConfig global = configBuilder().isCaptureFinishedGoodsCatchWeightAtReceipt(OptionalBoolean.TRUE).build();

			assertThat(user.fallbackTo(global).getIsCaptureFinishedGoodsCatchWeightAtReceiptEffective()).isFalse();
		}

		@Test
		void userUnknownInheritsGlobal()
		{
			final MobileUIManufacturingConfig user = configBuilder().build();
			final MobileUIManufacturingConfig global = configBuilder().isCaptureFinishedGoodsCatchWeightAtReceipt(OptionalBoolean.FALSE).build();

			assertThat(user.fallbackTo(global).getIsCaptureFinishedGoodsCatchWeightAtReceiptEffective()).isFalse();
		}

		@Test
		void userBlankInheritsGlobalRecord()
		{
			createGlobalConfig(true, true, false, false);
			createUserConfig("", "", "", "");

			assertThat(repo.getConfig(USER_ID, clientId).getIsCaptureFinishedGoodsCatchWeightAtReceiptEffective()).isFalse();
		}

		@Test
		void userRecordValueOverridesGlobalRecord()
		{
			createGlobalConfig(true, true, false, true);
			createUserConfig(null, null, null, "N");

			assertThat(repo.getConfig(USER_ID, clientId).getIsCaptureFinishedGoodsCatchWeightAtReceiptEffective()).isFalse();
		}

		@Test
		void saveUserConfigPersistsTheFlag()
		{
			createGlobalConfig(true, true, false, true);
			repo.saveUserConfig(configBuilder().isCaptureFinishedGoodsCatchWeightAtReceipt(OptionalBoolean.FALSE).build(), USER_ID);

			assertThat(repo.getConfig(USER_ID, clientId).getIsCaptureFinishedGoodsCatchWeightAtReceiptEffective()).isFalse();
		}
	}

	@Nested
	class effectiveForReceiveLine
	{
		@Test
		void mainFinishedGood_passesTheConfigThrough()
		{
			final MobileUIManufacturingConfig config = configBuilder()
					.isAllowFinishedGoodsReceiveToLU(OptionalBoolean.FALSE)
					.isAllowFinishedGoodsReceiveToTU(OptionalBoolean.TRUE)
					.isSkipFinishedGoodsReceiveTargetStep(OptionalBoolean.TRUE)
					.isCaptureFinishedGoodsCatchWeightAtReceipt(OptionalBoolean.FALSE)
					.build();

			final FinishedGoodsReceiveLineConfig lineConfig = config.effectiveForReceiveLine(true);

			assertThat(lineConfig.isAllowReceiveToLU()).isFalse();
			assertThat(lineConfig.isAllowReceiveToTU()).isTrue();
			assertThat(lineConfig.isSkipReceiveTargetStep()).isTrue();
			assertThat(lineConfig.isCaptureCatchWeight()).isFalse();
		}

		@Test
		void coProduct_isExemptFromEverySimplification()
		{
			final MobileUIManufacturingConfig config = configBuilder()
					.isAllowFinishedGoodsReceiveToLU(OptionalBoolean.FALSE)
					.isAllowFinishedGoodsReceiveToTU(OptionalBoolean.FALSE)
					.isSkipFinishedGoodsReceiveTargetStep(OptionalBoolean.TRUE)
					.isCaptureFinishedGoodsCatchWeightAtReceipt(OptionalBoolean.FALSE)
					.build();

			final FinishedGoodsReceiveLineConfig lineConfig = config.effectiveForReceiveLine(false);

			assertThat(lineConfig.isAllowReceiveToLU()).isTrue();
			assertThat(lineConfig.isAllowReceiveToTU()).isTrue();
			assertThat(lineConfig.isSkipReceiveTargetStep()).isFalse();
			assertThat(lineConfig.isCaptureCatchWeight()).isTrue();
		}

		@Test
		void coProduct_outcomeDoesNotDependOnTheConfig()
		{
			final MobileUIManufacturingConfig config = configBuilder()
					.isAllowFinishedGoodsReceiveToLU(OptionalBoolean.TRUE)
					.isAllowFinishedGoodsReceiveToTU(OptionalBoolean.TRUE)
					.isSkipFinishedGoodsReceiveTargetStep(OptionalBoolean.FALSE)
					.isCaptureFinishedGoodsCatchWeightAtReceipt(OptionalBoolean.TRUE)
					.build();

			final FinishedGoodsReceiveLineConfig lineConfig = config.effectiveForReceiveLine(false);

			assertThat(lineConfig.isAllowReceiveToLU()).isTrue();
			assertThat(lineConfig.isAllowReceiveToTU()).isTrue();
			assertThat(lineConfig.isSkipReceiveTargetStep()).isFalse();
			assertThat(lineConfig.isCaptureCatchWeight()).isTrue();
		}
	}
}
