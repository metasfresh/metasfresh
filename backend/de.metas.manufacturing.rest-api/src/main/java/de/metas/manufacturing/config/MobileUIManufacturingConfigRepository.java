package de.metas.manufacturing.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.cache.CCache;
import de.metas.user.UserId;
import de.metas.util.OptionalBoolean;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_MobileUI_MFG_Config;
import org.compiere.model.I_MobileUI_MFG_Config_Attribute;
import org.compiere.model.I_MobileUI_UserProfile_MFG;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Repository Tables: MobileUI_MFG_Config, MobileUI_UserProfile_MFG, MobileUI_MFG_Config_Attribute
 * Repository Cluster: MobileUIManufacturingConfigRepository
 */
@Repository
public class MobileUIManufacturingConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

	// Lot number + Best-before date are editable BY DEFAULT, so a tenant with no MobileUI_MFG_Config
	// row still gets both editable.
	private static final MobileUIManufacturingConfig DEFAULT_CONFIG = MobileUIManufacturingConfig.builder()
			.isScanResourceRequired(OptionalBoolean.FALSE)
			.isAllowIssuingAnyHU(OptionalBoolean.FALSE)
			.receiveUnitType(ReceiveUnitType.CU)
			.editableAttributeCodesInOrder(ImmutableList.of(AttributeConstants.ATTR_LotNumber, AttributeConstants.ATTR_BestBeforeDate))
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

	private Optional<I_MobileUI_MFG_Config> retrieveGlobalConfigRecord(@NonNull final ClientId clientId)
	{
		// No active-records filter on purpose - mirrors retrieveUserConfigRecord: this is the save-path lookup,
		// which must find (and reactivate) an existing-but-inactive row instead of inserting a duplicate.
		return queryBL.createQueryBuilder(I_MobileUI_MFG_Config.class)
				.addEqualsFilter(I_MobileUI_MFG_Config.COLUMNNAME_AD_Client_ID, clientId)
				.create()
				.firstOnlyOptional(I_MobileUI_MFG_Config.class);
	}

	/**
	 * Test/admin-masterdata write path for the global editable-attribute list (v1 is global-only - see
	 * {@link MobileUIManufacturingConfig#getEditableAttributeCodesInOrder()}). Upserts the {@code MobileUI_MFG_Config}
	 * row for the given client (creating one with column defaults if none exists yet) and replaces its
	 * {@code MobileUI_MFG_Config_Attribute} child rows to match the given ordered list: an attribute already
	 * present is reactivated/reordered in place, a new one is created, and an active row whose attribute is no
	 * longer in the list is deactivated. Deliberately independent of {@link #saveUserConfig} - that path only
	 * persists the per-user flags (this list is not one of them, see {@code fromRecord}'s always-empty read).
	 */
	public void saveGlobalEditableAttributeCodesInOrder(@NonNull final ClientId clientId, @NonNull final List<AttributeCode> attributeCodesInOrder)
	{
		// AD_Client_ID is not exposed via a setter on I_MobileUI_MFG_Config (system column) - a freshly
		// created record picks up the CURRENT session's client, which callers of this masterdata/test path
		// always run as clientId anyway.
		final I_MobileUI_MFG_Config record = retrieveGlobalConfigRecord(clientId)
				.orElseGet(() -> InterfaceWrapperHelper.newInstance(I_MobileUI_MFG_Config.class));
		record.setIsActive(true);
		InterfaceWrapperHelper.save(record);

		saveEditableAttributeChildRows(record.getMobileUI_MFG_Config_ID(), attributeCodesInOrder);
	}

	private void saveEditableAttributeChildRows(final int mobileUIMFGConfigId, @NonNull final List<AttributeCode> attributeCodesInOrder)
	{
		final Map<AttributeId, I_MobileUI_MFG_Config_Attribute> existingByAttributeId = queryBL.createQueryBuilder(I_MobileUI_MFG_Config_Attribute.class)
				.addEqualsFilter(I_MobileUI_MFG_Config_Attribute.COLUMNNAME_MobileUI_MFG_Config_ID, mobileUIMFGConfigId)
				.create()
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						record -> AttributeId.ofRepoId(record.getM_Attribute_ID()),
						record -> record));

		final Set<AttributeId> keptAttributeIds = new HashSet<>();
		int seqNo = 10;
		for (final AttributeCode attributeCode : attributeCodesInOrder)
		{
			final AttributeId attributeId = attributeDAO.getAttributeIdByCode(attributeCode);
			if (!keptAttributeIds.add(attributeId))
			{
				// duplicate attribute code in the same call - keep the first occurrence's row/SeqNo, don't
				// create a second active row for the same attribute (existingByAttributeId is a fixed snapshot
				// and would not see the row just created/saved for the first occurrence).
				continue;
			}

			final I_MobileUI_MFG_Config_Attribute childRecord = existingByAttributeId.getOrDefault(
					attributeId,
					InterfaceWrapperHelper.newInstance(I_MobileUI_MFG_Config_Attribute.class));
			childRecord.setMobileUI_MFG_Config_ID(mobileUIMFGConfigId);
			childRecord.setM_Attribute_ID(attributeId.getRepoId());
			childRecord.setSeqNo(seqNo);
			childRecord.setIsActive(true);
			InterfaceWrapperHelper.save(childRecord);

			seqNo += 10;
		}

		existingByAttributeId.forEach((attributeId, childRecord) -> {
			if (!keptAttributeIds.contains(attributeId) && childRecord.isActive())
			{
				childRecord.setIsActive(false);
				InterfaceWrapperHelper.save(childRecord);
			}
		});
	}

}
