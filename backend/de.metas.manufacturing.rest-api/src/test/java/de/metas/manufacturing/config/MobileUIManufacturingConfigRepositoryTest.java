package de.metas.manufacturing.config;

import de.metas.user.UserId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_MobileUI_MFG_Config;
import org.compiere.model.I_MobileUI_MFG_Config_Attribute;
import org.compiere.model.I_MobileUI_UserProfile_MFG;
import org.compiere.model.I_M_Attribute;
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

	private I_MobileUI_MFG_Config createGlobalConfig(final ReceiveUnitType receiveUnitType)
	{
		final I_MobileUI_MFG_Config record = InterfaceWrapperHelper.newInstance(I_MobileUI_MFG_Config.class);
		record.setIsActive(true);
		record.setIsScanResourceRequired(false);
		record.setIsAllowIssuingAnyHU(false);
		record.setReceiveUnitType(receiveUnitType != null ? receiveUnitType.getCode() : null);
		InterfaceWrapperHelper.save(record);
		return record;
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

	private I_M_Attribute createAttribute(final String code)
	{
		final I_M_Attribute record = InterfaceWrapperHelper.newInstance(I_M_Attribute.class);
		record.setValue(code);
		record.setName(code);
		record.setAttributeValueType(AttributeValueType.STRING.getCode());
		InterfaceWrapperHelper.saveRecord(record);
		return record;
	}

	private void createConfigAttribute(
			final I_MobileUI_MFG_Config config,
			final I_M_Attribute attribute,
			final int seqNo,
			final boolean isActive)
	{
		final I_MobileUI_MFG_Config_Attribute record = InterfaceWrapperHelper.newInstance(I_MobileUI_MFG_Config_Attribute.class);
		record.setMobileUI_MFG_Config_ID(config.getMobileUI_MFG_Config_ID());
		record.setM_Attribute_ID(attribute.getM_Attribute_ID());
		record.setSeqNo(seqNo);
		record.setIsActive(isActive);
		InterfaceWrapperHelper.saveRecord(record);
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

	@Nested
	class getEditableAttributeCodesInOrder
	{
		@Test
		void noConfigAtAll_emptyList()
		{
			final MobileUIManufacturingConfig config = repo.getConfig(USER_ID, clientId);
			assertThat(config.getEditableAttributeCodesInOrder()).isEmpty();
		}

		@Test
		void noActiveChildRows_emptyList()
		{
			createGlobalConfig(ReceiveUnitType.CU);

			final MobileUIManufacturingConfig config = repo.getConfig(USER_ID, clientId);
			assertThat(config.getEditableAttributeCodesInOrder()).isEmpty();
		}

		@Test
		void activeRowsOrderedBySeqNo_evenWhenInsertedOutOfOrder()
		{
			final I_MobileUI_MFG_Config globalConfig = createGlobalConfig(ReceiveUnitType.CU);
			final I_M_Attribute attr1 = createAttribute("Attr1");
			final I_M_Attribute attr2 = createAttribute("Attr2");

			// insert out of SeqNo order on purpose - the effective list must still come back ordered
			createConfigAttribute(globalConfig, attr2, 20, true);
			createConfigAttribute(globalConfig, attr1, 10, true);

			final MobileUIManufacturingConfig config = repo.getConfig(USER_ID, clientId);
			assertThat(config.getEditableAttributeCodesInOrder())
					.containsExactly(AttributeCode.ofString("Attr1"), AttributeCode.ofString("Attr2"));
		}

		@Test
		void inactiveChildRow_excludedFromEffectiveList()
		{
			final I_MobileUI_MFG_Config globalConfig = createGlobalConfig(ReceiveUnitType.CU);
			final I_M_Attribute attr1 = createAttribute("Attr1");
			final I_M_Attribute attr2 = createAttribute("Attr2");

			createConfigAttribute(globalConfig, attr1, 10, true);
			createConfigAttribute(globalConfig, attr2, 20, false);

			final MobileUIManufacturingConfig config = repo.getConfig(USER_ID, clientId);
			assertThat(config.getEditableAttributeCodesInOrder())
					.containsExactly(AttributeCode.ofString("Attr1"));
		}
	}
}
