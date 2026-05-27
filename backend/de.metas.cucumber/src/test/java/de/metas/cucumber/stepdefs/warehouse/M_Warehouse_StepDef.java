/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cucumber.stepdefs.warehouse;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Locator_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.ValueAndName;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.distribution.DD_NetworkDistribution_StepDefData;
import de.metas.cucumber.stepdefs.resource.S_Resource_StepDefData;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.product.ResourceId;
import de.metas.util.OptionalBoolean;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_Locator;

import static org.adempiere.model.InterfaceWrapperHelper.COLUMNNAME_IsActive;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.compiere.model.I_M_Warehouse.COLUMNNAME_IsIssueWarehouse;
import static org.compiere.model.I_M_Warehouse.COLUMNNAME_M_Warehouse_ID;
import static org.compiere.model.I_M_Warehouse.COLUMNNAME_Value;

@RequiredArgsConstructor
public class M_Warehouse_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);

	@NonNull private final M_Warehouse_StepDefData warehouseTable;
	@NonNull private final M_Locator_StepDefData locatorTable;
	@NonNull private final C_BPartner_StepDefData bpartnerTable;
	@NonNull private final C_BPartner_Location_StepDefData bpartnerLocationTable;
	@NonNull private final S_Resource_StepDefData resourceTable;
	@NonNull private final DD_NetworkDistribution_StepDefData ddNetworkTable;
	@NonNull private final TestContext restTestContext;

	@And("load M_Warehouse:")
	public void load_M_Warehouse(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(COLUMNNAME_M_Warehouse_ID)
				.forEach(row -> {
					final String value = row.getAsString(COLUMNNAME_Value);

					final I_M_Warehouse warehouseRecord = queryBL.createQueryBuilder(I_M_Warehouse.class)
							.addEqualsFilter(COLUMNNAME_Value, value)
							.create()
							.firstOnlyNotNull(I_M_Warehouse.class);

					row.getAsIdentifier().put(warehouseTable, warehouseRecord);
				});
	}

	/**
	 * Upserts {@code M_Warehouse} records by {@code Value} (suggested from the row's identifier when no explicit
	 * {@code Value} is given), then assigns a default locator and stores the warehouse in the StepDefData table.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code M_Warehouse_ID.Identifier} (or {@code M_Warehouse_ID}) — the identifier used to reference the
	 *       warehouse from later step rows; the {@code Value} / {@code Name} are derived from it via
	 *       {@link de.metas.cucumber.stepdefs.DataTableRow#suggestValueAndName()}.</li>
	 * </ul>
	 *
	 * <p>Optional columns (any missing column leaves the column at its default / unset value):
	 * <ul>
	 *   <li>{@code IsIssueWarehouse} — {@code Y} / {@code N}. When {@code Y}, all other warehouses are first
	 *       updated to {@code IsIssueWarehouse=N} (only one issue warehouse is allowed at a time).</li>
	 *   <li>{@code IsDropShipWarehouse} — {@code Y} / {@code N}.</li>
	 *   <li>{@code C_BPartner_ID} / {@code C_BPartner_Location_ID} — identifiers of a previously loaded
	 *       BPartner / location; default to the metasfresh-AG defaults when absent.</li>
	 *   <li>{@code IsInTransit}, {@code IsQuarantineWarehouse}, {@code IsQualityReturnWarehouse} — boolean flags.</li>
	 *   <li>{@code MRP_Exclude} — Yes/No reference stored on the warehouse record. Accepts {@code "Y"} (warehouse is
	 *       excluded from material disposition; no {@code MD_Candidate} rows are created for events on this
	 *       warehouse), {@code "N"} (explicitly included; overrides legacy {@code IsDropShipWarehouse}), or blank /
	 *       omitted (column stays unset; {@code IsDropShipWarehouse} acts as the fallback). See
	 *       {@code WarehouseBL.isIgnoreInMaterialDispo} for the precedence rule.</li>
	 *   <li>{@code PP_Plant_ID} — identifier of a previously loaded {@code S_Resource}.</li>
	 *   <li>{@code M_Locator_ID.Identifier} — when present, the default locator created for this warehouse is
	 *       stored under this identifier in the locator StepDefData table.</li>
	 * </ul>
	 *
	 * <p>Gherkin usage:
	 * <pre>
	 * And metasfresh contains M_Warehouse:
	 *   | M_Warehouse_ID | MRP_Exclude | IsDropShipWarehouse |
	 *   | wh_ok          |             |                     |
	 *   | wh_excl        | Y           |                     |
	 *   | wh_dropship    |             | Y                   |
	 * </pre>
	 */
	@And("metasfresh contains M_Warehouse:")
	public void create_M_Warehouse(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(COLUMNNAME_M_Warehouse_ID)
				.forEach((row) -> {
					final ValueAndName valueAndName = row.suggestValueAndName();

					final I_M_Warehouse warehouseRecord = CoalesceUtil.coalesceSuppliers(
							() -> queryBL.createQueryBuilder(I_M_Warehouse.class)
									.addEqualsFilter(COLUMNNAME_Value, valueAndName.getValue())
									.create()
									.firstOnlyOrNull(I_M_Warehouse.class),
							() -> InterfaceWrapperHelper.newInstance(I_M_Warehouse.class));

					assertThat(warehouseRecord).isNotNull();

					final boolean isIssueWarehouse = row.getAsOptionalBoolean(COLUMNNAME_IsIssueWarehouse).orElse(false);
					if (isIssueWarehouse)
					{ // we can have just one issue-warehouse, so make sure that all other WHs are not issue-warehouses
						final ICompositeQueryUpdater<I_M_Warehouse> updater = queryBL.createCompositeQueryUpdater(I_M_Warehouse.class).addSetColumnValue(COLUMNNAME_IsIssueWarehouse, false);
						queryBL.createQueryBuilder(I_M_Warehouse.class).addEqualsFilter(COLUMNNAME_IsIssueWarehouse, true).addEqualsFilter(COLUMNNAME_IsActive, true).create().updateDirectly(updater);
					}

					final boolean isDropShipWarehouse = row.getAsOptionalBoolean(I_M_Warehouse.COLUMNNAME_IsDropShipWarehouse).orElse(false);
					warehouseRecord.setIsDropShipWarehouse(isDropShipWarehouse);

					final BPartnerId bpartnerId = row.getAsOptionalIdentifier(I_M_Warehouse.COLUMNNAME_C_BPartner_ID)
							.map(bpartnerTable::getId)
							.orElse(StepDefConstants.METASFRESH_AG_BPARTNER_ID);

					final int bpartnerLocationRepoId = row.getAsOptionalIdentifier(I_M_Warehouse.COLUMNNAME_C_BPartner_Location_ID)
							.map(bpartnerLocationTable::get)
							.map(I_C_BPartner_Location::getC_BPartner_Location_ID)
							.orElse(-1);

					final BPartnerLocationId bpartnerLocationId = bpartnerLocationRepoId > 0
							? BPartnerLocationId.ofRepoIdOrNull(bpartnerId, bpartnerLocationRepoId)
							: StepDefConstants.METASFRESH_AG_BPARTNER_LOCATION_ID;

					final boolean isInTransit = row.getAsOptionalBoolean(I_M_Warehouse.COLUMNNAME_IsInTransit).orElse(false);
					final boolean isQuarantineWarehouse = row.getAsOptionalBoolean(I_M_Warehouse.COLUMNNAME_IsQuarantineWarehouse).orElse(false);
					final boolean isQualityReturnWarehouse = row.getAsOptionalBoolean(I_M_Warehouse.COLUMNNAME_IsQualityReturnWarehouse).orElse(false);

					warehouseRecord.setValue(valueAndName.getValue());
					warehouseRecord.setName(valueAndName.getName());
					warehouseRecord.setSeparator("*");
					warehouseRecord.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
					warehouseRecord.setC_BPartner_Location_ID(BPartnerLocationId.toRepoId(bpartnerLocationId));
					warehouseRecord.setIsIssueWarehouse(isIssueWarehouse);
					warehouseRecord.setIsInTransit(isInTransit);
					warehouseRecord.setIsQuarantineWarehouse(isQuarantineWarehouse);
					warehouseRecord.setIsQualityReturnWarehouse(isQualityReturnWarehouse);

					// MRP_Exclude is a Yes/No reference stored as "Y" / "N" / null on the warehouse record.
					// "Y" → warehouse is excluded from material disposition (no MD_Candidate rows are created
					// for events on this warehouse); "N" → explicitly included (overrides IsDropShipWarehouse);
					// null / omitted → fall back to IsDropShipWarehouse semantics. See WarehouseBL.isIgnoreInMaterialDispo.
					row.getAsOptionalString(I_M_Warehouse.COLUMNNAME_MRP_Exclude)
							.map(de.metas.util.StringUtils::trimBlankToNull)
							.filter(java.util.Objects::nonNull)
							.ifPresent(warehouseRecord::setMRP_Exclude);

					row.getAsOptionalIdentifier(I_M_Warehouse.COLUMNNAME_PP_Plant_ID)
							.map(identifier -> resourceTable.getIdOptional(identifier).orElseGet(() -> identifier.getAsId(ResourceId.class)))
							.ifPresent(resourceId -> warehouseRecord.setPP_Plant_ID(resourceId.getRepoId()));

					// IsPackingWarehouse marks a warehouse as a "packing warehouse" for which the dedicated DD_Order
					// reconciliation flow runs (combined with MRP_Exclude=Y to keep material-dispo out). When Y,
					// DD_NetworkDistribution_ID is mandatory and is validated by the M_Warehouse interceptor on save.
					final OptionalBoolean isPackingWarehouse = row.getAsOptionalBoolean(I_M_Warehouse.COLUMNNAME_IsPackingWarehouse);
					if (isPackingWarehouse.isPresent())
					{
						warehouseRecord.setIsPackingWarehouse(isPackingWarehouse.isTrue());
					}

					// DD_NetworkDistribution_ID resolves the source warehouse for the packing warehouse via the
					// distribution network (target -> source). Referenced by identifier from a previously created
					// DD_NetworkDistribution.
					row.getAsOptionalIdentifier(I_M_Warehouse.COLUMNNAME_DD_NetworkDistribution_ID)
							.map(identifier -> identifier.lookupIdIn(ddNetworkTable))
							.ifPresent(networkId -> warehouseRecord.setDD_NetworkDistribution_ID(networkId.getRepoId()));

					saveRecord(warehouseRecord);

					final I_M_Locator locator = warehouseBL.getOrCreateDefaultLocator(WarehouseId.ofRepoId(warehouseRecord.getM_Warehouse_ID()));
					row.getAsOptionalIdentifier(I_M_Locator.COLUMNNAME_M_Locator_ID)
							.ifPresent(locatorIdentifier -> locatorTable.put(locatorIdentifier, locator));

					row.getAsIdentifier().put(warehouseTable, warehouseRecord);

					row.getAsOptionalIdentifier("REST.Context.Value")
							.ifPresent(id -> restTestContext.setVariable(id.getAsString(), warehouseRecord.getValue()));
				});
	}

	/**
	 * Attempts to save an {@code M_Warehouse} and asserts that the save is rejected by a model interceptor.
	 *
	 * <p>Used to verify the "DD_NetworkDistribution_ID is mandatory when IsPackingWarehouse=Y" rule
	 * (enforced by {@code M_Warehouse_DDOrderPickingInterceptor}): setting {@code IsPackingWarehouse=Y}
	 * without a {@code DD_NetworkDistribution_ID} must throw on save.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code M_Warehouse_ID.Identifier} (or {@code M_Warehouse_ID}) — identifier; {@code Value} / {@code Name}
	 *       are derived from it.</li>
	 * </ul>
	 * Optional columns:
	 * <ul>
	 *   <li>{@code IsPackingWarehouse} — {@code Y} / {@code N}.</li>
	 *   <li>{@code DD_NetworkDistribution_ID} — identifier of a previously created DD_NetworkDistribution.</li>
	 * </ul>
	 *
	 * <p>Gherkin usage:
	 * <pre>
	 * Then saving M_Warehouse is rejected:
	 *   | M_Warehouse_ID | IsPackingWarehouse |
	 *   | wh_pack        | Y                  |
	 * </pre>
	 */
	@And("saving M_Warehouse is rejected:")
	public void saving_M_Warehouse_is_rejected(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(COLUMNNAME_M_Warehouse_ID)
				.forEach(row -> {
					final ValueAndName valueAndName = row.suggestValueAndName();

					final I_M_Warehouse warehouseRecord = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class);
					warehouseRecord.setValue(valueAndName.getValue());
					warehouseRecord.setName(valueAndName.getName());
					warehouseRecord.setSeparator("*");
					warehouseRecord.setC_BPartner_ID(BPartnerId.toRepoId(StepDefConstants.METASFRESH_AG_BPARTNER_ID));
					warehouseRecord.setC_BPartner_Location_ID(BPartnerLocationId.toRepoId(StepDefConstants.METASFRESH_AG_BPARTNER_LOCATION_ID));

					final OptionalBoolean isPackingWarehouse = row.getAsOptionalBoolean(I_M_Warehouse.COLUMNNAME_IsPackingWarehouse);
					if (isPackingWarehouse.isPresent())
					{
						warehouseRecord.setIsPackingWarehouse(isPackingWarehouse.isTrue());
					}

					row.getAsOptionalIdentifier(I_M_Warehouse.COLUMNNAME_DD_NetworkDistribution_ID)
							.map(identifier -> identifier.lookupIdIn(ddNetworkTable))
							.ifPresent(networkId -> warehouseRecord.setDD_NetworkDistribution_ID(networkId.getRepoId()));

					assertThatThrownBy(() -> saveRecord(warehouseRecord))
							.as("Saving M_Warehouse %s with IsPackingWarehouse=%s and no DD_NetworkDistribution_ID must be rejected",
									valueAndName.getValue(), isPackingWarehouse.toBooleanString())
							.isInstanceOf(Throwable.class);
				});
	}
}
