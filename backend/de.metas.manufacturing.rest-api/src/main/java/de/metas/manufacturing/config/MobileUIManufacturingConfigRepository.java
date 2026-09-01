package de.metas.manufacturing.config;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.user.UserId;
import de.metas.util.OptionalBoolean;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_MobileUI_MFG_Config;
import org.compiere.model.I_MobileUI_MFG_Config_Attribute;
import org.compiere.model.I_MobileUI_UserProfile_MFG;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

/**
 * Repository Tables: MobileUI_MFG_Config, MobileUI_UserProfile_MFG, MobileUI_MFG_Config_Attribute
 * Repository Cluster: MobileUIManufacturingConfigRepository
 */
@Repository
public class MobileUIManufacturingConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

	private static final MobileUIManufacturingConfig DEFAULT_CONFIG = MobileUIManufacturingConfig.builder()
			.isScanResourceRequired(OptionalBoolean.FALSE)
			.isAllowIssuingAnyHU(OptionalBoolean.FALSE)
			.receiveUnitType(ReceiveUnitType.CU)
			.editableAttributeCodesInOrder(ImmutableList.of())
			.isAllowFinishedGoodsReceiveToLU(OptionalBoolean.TRUE)
			.isAllowFinishedGoodsReceiveToTU(OptionalBoolean.TRUE)
			.isSkipFinishedGoodsReceiveTargetStep(OptionalBoolean.FALSE)
			.isCaptureCatchWeightAtReceipt(OptionalBoolean.TRUE)
			.isAllowReceiveWithoutPackingItem(OptionalBoolean.FALSE)
			.build();

	private final CCache<UserId, Optional<MobileUIManufacturingConfig>> userConfigsCache = CCache.<UserId, Optional<MobileUIManufacturingConfig>>builder()
			.tableName(I_MobileUI_UserProfile_MFG.Table_Name)
			.build();

	private final CCache<ClientId, Optional<MobileUIManufacturingConfig>> globalConfigsCache = CCache.<ClientId, Optional<MobileUIManufacturingConfig>>builder()
			.tableName(I_MobileUI_MFG_Config.Table_Name)
			.additionalTableNamesToResetFor(ImmutableList.of(I_MobileUI_MFG_Config_Attribute.Table_Name))
			.build();

	@NonNull
	public MobileUIManufacturingConfig getConfig(@Nullable final UserId userId, @NonNull final ClientId clientId)
	{
		return MobileUIManufacturingConfig.merge(
						userId != null ? getUserConfig(userId) : null,
						getGlobalConfig(clientId),
						DEFAULT_CONFIG
				)
				.orElse(DEFAULT_CONFIG);
	}

	private MobileUIManufacturingConfig getUserConfig(@NonNull final UserId userId)
	{
		//noinspection DataFlowIssue
		return userConfigsCache.getOrLoad(userId, this::retrieveUserConfig).orElse(null);
	}

	public MobileUIManufacturingConfig getGlobalConfig(@NonNull final ClientId clientId)
	{
		//noinspection DataFlowIssue
		return globalConfigsCache.getOrLoad(clientId, this::retrieveGlobalConfig).orElse(null);
	}

	private Optional<MobileUIManufacturingConfig> retrieveUserConfig(@NonNull final UserId userId)
	{
		return retrieveUserConfigRecord(userId)
				.filter(I_MobileUI_UserProfile_MFG::isActive)
				.map(MobileUIManufacturingConfigRepository::fromRecord);
	}

	private Optional<I_MobileUI_UserProfile_MFG> retrieveUserConfigRecord(final @NonNull UserId userId)
	{
		// No active-records filter on purpose: this method is shared by the read path
		// (retrieveUserConfig filters IsActive='N' out in Java, treating it as "no config")
		// and the save path (saveUserConfig reactivates an existing inactive row instead of
		// inserting a duplicate). Filtering here would break that save-path reactivation.
		return queryBL.createQueryBuilder(I_MobileUI_UserProfile_MFG.class)
				.addEqualsFilter(I_MobileUI_UserProfile_MFG.COLUMNNAME_AD_User_ID, userId)
				.create()
				.firstOnlyOptional(I_MobileUI_UserProfile_MFG.class);
	}

	private static MobileUIManufacturingConfig fromRecord(@NonNull final I_MobileUI_UserProfile_MFG record)
	{
		return MobileUIManufacturingConfig.builder()
				.isScanResourceRequired(OptionalBoolean.ofNullableString(record.getIsScanResourceRequired()))
				.isAllowIssuingAnyHU(OptionalBoolean.ofNullableString(record.getIsAllowIssuingAnyHU()))
				.receiveUnitType(ReceiveUnitType.ofNullableCode(record.getReceiveUnitType()))
				// no per-user editable-attribute override in v1 (global-only) - always empty, so the merge
				// (see MobileUIManufacturingConfig#fallbackTo) falls through to the global config's list.
				.editableAttributeCodesInOrder(ImmutableList.of())
				.isAllowFinishedGoodsReceiveToLU(OptionalBoolean.ofNullableString(record.getIsAllowFinishedGoodsReceiveToLU()))
				.isAllowFinishedGoodsReceiveToTU(OptionalBoolean.ofNullableString(record.getIsAllowFinishedGoodsReceiveToTU()))
				.isSkipFinishedGoodsReceiveTargetStep(OptionalBoolean.ofNullableString(record.getIsSkipFinishedGoodsReceiveTargetStep()))
				.isCaptureCatchWeightAtReceipt(OptionalBoolean.ofNullableString(record.getIsCaptureCatchWeightAtReceipt()))
				.isAllowReceiveWithoutPackingItem(OptionalBoolean.ofNullableString(record.getIsAllowReceiveWithoutPackingItem()))
				.build();
	}

	private static void updateRecord(@NonNull final I_MobileUI_UserProfile_MFG record, @NonNull final MobileUIManufacturingConfig from)
	{
		record.setIsScanResourceRequired(from.getIsScanResourceRequired().toBooleanString());
		record.setIsAllowIssuingAnyHU(from.getIsAllowIssuingAnyHU().toBooleanString());
		record.setReceiveUnitType(from.getReceiveUnitType() != null ? from.getReceiveUnitType().getCode() : null);
		record.setIsAllowFinishedGoodsReceiveToLU(from.getIsAllowFinishedGoodsReceiveToLU().toBooleanString());
		record.setIsAllowFinishedGoodsReceiveToTU(from.getIsAllowFinishedGoodsReceiveToTU().toBooleanString());
		record.setIsSkipFinishedGoodsReceiveTargetStep(from.getIsSkipFinishedGoodsReceiveTargetStep().toBooleanString());
		record.setIsCaptureCatchWeightAtReceipt(from.getIsCaptureCatchWeightAtReceipt().toBooleanString());
		record.setIsAllowReceiveWithoutPackingItem(from.getIsAllowReceiveWithoutPackingItem().toBooleanString());
	}

	private Optional<MobileUIManufacturingConfig> retrieveGlobalConfig(@NonNull final ClientId clientId)
	{
		return queryBL.createQueryBuilder(I_MobileUI_MFG_Config.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MobileUI_MFG_Config.COLUMNNAME_AD_Client_ID, clientId)
				.create()
				.firstOnlyOptional(I_MobileUI_MFG_Config.class)
				.map(this::fromRecord);
	}

	private MobileUIManufacturingConfig fromRecord(@NonNull final I_MobileUI_MFG_Config record)
	{
		return MobileUIManufacturingConfig.builder()
				.isScanResourceRequired(OptionalBoolean.ofBoolean(record.isScanResourceRequired()))
				.isAllowIssuingAnyHU(OptionalBoolean.ofBoolean(record.isAllowIssuingAnyHU()))
				.receiveUnitType(ReceiveUnitType.ofNullableCode(record.getReceiveUnitType()))
				.editableAttributeCodesInOrder(retrieveEditableAttributeCodesInOrder(record.getMobileUI_MFG_Config_ID()))
				.isAllowFinishedGoodsReceiveToLU(OptionalBoolean.ofBoolean(record.isAllowFinishedGoodsReceiveToLU()))
				.isAllowFinishedGoodsReceiveToTU(OptionalBoolean.ofBoolean(record.isAllowFinishedGoodsReceiveToTU()))
				.isSkipFinishedGoodsReceiveTargetStep(OptionalBoolean.ofBoolean(record.isSkipFinishedGoodsReceiveTargetStep()))
				.isCaptureCatchWeightAtReceipt(OptionalBoolean.ofBoolean(record.isCaptureCatchWeightAtReceipt()))
				.isAllowReceiveWithoutPackingItem(OptionalBoolean.ofBoolean(record.isAllowReceiveWithoutPackingItem()))
				.build();
	}

	/**
	 * Reads the {@code MobileUI_MFG_Config_Attribute} child rows of the given config (active rows, ascending
	 * {@code SeqNo}) and resolves them to their {@link AttributeCode}s in that order.
	 * Pattern reference: {@code HUManagerProfileRepository#retrieveDisplayedAttributeIdsInOrder}.
	 */
	@NonNull
	private ImmutableList<AttributeCode> retrieveEditableAttributeCodesInOrder(final int mobileUIMFGConfigId)
	{
		final List<AttributeId> orderedAttributeIds = queryBL.createQueryBuilder(I_MobileUI_MFG_Config_Attribute.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MobileUI_MFG_Config_Attribute.COLUMNNAME_MobileUI_MFG_Config_ID, mobileUIMFGConfigId)
				.orderBy(I_MobileUI_MFG_Config_Attribute.COLUMNNAME_SeqNo)
				.orderBy(I_MobileUI_MFG_Config_Attribute.COLUMNNAME_MobileUI_MFG_Config_Attribute_ID)
				.create()
				.stream()
				.map(record -> AttributeId.ofRepoId(record.getM_Attribute_ID()))
				.collect(ImmutableList.toImmutableList());

		return attributeDAO.getOrderedAttributeCodesByIds(orderedAttributeIds);
	}

	public void saveUserConfig(@NonNull final MobileUIManufacturingConfig newConfig, @NonNull final UserId userId)
	{
		final I_MobileUI_UserProfile_MFG record = retrieveUserConfigRecord(userId).orElseGet(() -> InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_MFG.class));
		record.setIsActive(true);
		record.setAD_User_ID(userId.getRepoId());
		updateRecord(record, newConfig);
		InterfaceWrapperHelper.save(record);
	}

}
