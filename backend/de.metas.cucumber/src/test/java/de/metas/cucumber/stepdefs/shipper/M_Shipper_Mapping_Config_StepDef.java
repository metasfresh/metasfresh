/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.cucumber.stepdefs.shipper;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.util.Check;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import javax.annotation.Nullable;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Shipper_Mapping_Config;

@RequiredArgsConstructor
public class M_Shipper_Mapping_Config_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final M_Shipper_StepDefData shipperTable;

	/**
	 * Creates or updates {@code M_Shipper_Mapping_Config} records. Upsert by {@code (M_Shipper_ID, SeqNo)}: if a record
	 * with that combination already exists it is updated, otherwise a new row is inserted. Idempotent on reruns.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Shipper_ID</b>          — (required) Cucumber identifier for the shipper.<br>
	 *   <b>SeqNo</b>                 — (required) Sequence number; used together with M_Shipper_ID as the upsert key.<br>
	 *   <b>MappingAttributeType</b>  — (required) e.g. {@code Reference}, {@code LineDetailGroup}, {@code SenderAttention}.<br>
	 *   <b>MappingAttributeValue</b> — (required) e.g. {@code CountryOfOrigin}, {@code SenderCountryCode}.<br>
	 *   <b>MappingAttributeKey</b>   — (optional) Detail kind or reference kind ID (e.g. {@code 4}, {@code 108}).<br>
	 *   <b>MappingGroupKey</b>       — (optional) Detail group key (e.g. {@code 1} for customs article).<br>
	 *   <b>MappingRule</b>           — (optional) Conditional rule name (e.g. {@code ReceiverCountryCode}).<br>
	 *   <b>MappingRuleValue</b>      — (optional) Value for the rule condition (e.g. {@code DE}, {@code RO}).
	 * @cucumber.depends StepDefData: M_Shipper_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains M_Shipper_Mapping_Configs:
	 *   | M_Shipper_ID    | SeqNo | MappingAttributeType | MappingGroupKey | MappingAttributeKey | MappingAttributeValue | MappingRule         | MappingRuleValue |
	 *   | nShift_coo_test | 10    | Reference            |                 | 108                 | PickupDateAndTimeStart |                    |                  |
	 *   | nShift_coo_test | 190   | LineDetailGroup      | 1               | 4                   | CountryOfOrigin        |                    |                  |
	 *   | nShift_coo_test | 200   | LineDetailGroup      | 1               | 4                   | SenderCountryCode      | ReceiverCountryCode | DE               |
	 * </pre>
	 */
	@And("metasfresh contains M_Shipper_Mapping_Configs:")
	public void add_M_Shipper_Mapping_Configs(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createOrUpdateMappingConfig);
	}

	private void createOrUpdateMappingConfig(@NonNull final DataTableRow row)
	{
		final ShipperId shipperId = row.getAsIdentifier(I_M_Shipper_Mapping_Config.COLUMNNAME_M_Shipper_ID).lookupNotNullIdIn(shipperTable);
		final int seqNo = row.getAsInt(I_M_Shipper_Mapping_Config.COLUMNNAME_SeqNo);

		@Nullable final I_M_Shipper_Mapping_Config existingConfig = queryBL.createQueryBuilder(I_M_Shipper_Mapping_Config.class)
				.addEqualsFilter(I_M_Shipper_Mapping_Config.COLUMNNAME_M_Shipper_ID, shipperId)
				.addEqualsFilter(I_M_Shipper_Mapping_Config.COLUMNNAME_SeqNo, seqNo)
				.create()
				.firstOnly();

		final I_M_Shipper_Mapping_Config config = existingConfig != null
				? existingConfig
				: InterfaceWrapperHelper.newInstance(I_M_Shipper_Mapping_Config.class);

		if (existingConfig == null)
		{
			config.setM_Shipper_ID(shipperId.getRepoId());
			config.setSeqNo(seqNo);
		}

		final String mappingAttributeType = row.getAsOptionalString(I_M_Shipper_Mapping_Config.COLUMNNAME_MappingAttributeType)
				.orElse(existingConfig != null ? existingConfig.getMappingAttributeType() : null);
		Check.assumeNotNull(mappingAttributeType,
				"MappingAttributeType is required when creating a new M_Shipper_Mapping_Config (M_Shipper_ID={}, SeqNo={})",
				shipperId, seqNo);
		config.setMappingAttributeType(mappingAttributeType);

		final String mappingAttributeValue = row.getAsOptionalString(I_M_Shipper_Mapping_Config.COLUMNNAME_MappingAttributeValue)
				.orElse(existingConfig != null ? existingConfig.getMappingAttributeValue() : null);
		Check.assumeNotNull(mappingAttributeValue,
				"MappingAttributeValue is required when creating a new M_Shipper_Mapping_Config (M_Shipper_ID={}, SeqNo={})",
				shipperId, seqNo);
		config.setMappingAttributeValue(mappingAttributeValue);
		// Nullable fields: absent column leaves existing value unchanged (null on a new record).
		row.getAsOptionalString(I_M_Shipper_Mapping_Config.COLUMNNAME_MappingAttributeKey)
				.ifPresent(config::setMappingAttributeKey);
		row.getAsOptionalString(I_M_Shipper_Mapping_Config.COLUMNNAME_MappingGroupKey)
				.ifPresent(config::setMappingGroupKey);
		row.getAsOptionalString(I_M_Shipper_Mapping_Config.COLUMNNAME_MappingRule)
				.ifPresent(config::setMappingRule);
		row.getAsOptionalString(I_M_Shipper_Mapping_Config.COLUMNNAME_MappingRuleValue)
				.ifPresent(config::setMappingRuleValue);

		InterfaceWrapperHelper.saveRecord(config);
	}
}
