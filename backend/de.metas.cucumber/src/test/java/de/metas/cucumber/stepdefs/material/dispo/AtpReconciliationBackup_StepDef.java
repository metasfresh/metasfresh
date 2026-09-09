package de.metas.cucumber.stepdefs.material.dispo;

/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.common.util.time.SystemTime;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.material.cockpit.stock.StockDataRecordIdentifier;
import de.metas.material.dispo.model.I_MD_ATP_Reconciliation_Backup;
import de.metas.material.dispo.reconcile.AtpReconciliationCommand;
import de.metas.material.dispo.reconcile.AtpReconciliationRunLog;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import de.metas.util.Services;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions invoking {@link AtpReconciliationCommand#reconcileAndLog} directly (there is no
 * {@code AD_Process} for it yet - that is a separate, later deliverable) and asserting the durable backup this
 * run persists is recoverable from {@code MD_ATP_Reconciliation_Backup} - never from the in-process
 * {@link AtpReconciliationRunLog} return value, which does not outlive the JVM that produced it.
 */
@RequiredArgsConstructor
public class AtpReconciliationBackup_StepDef
{
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final M_Warehouse_StepDefData warehouseTable;

	/** Maps a scenario-local alias (e.g. {@code run_a}) to the {@code ReconciliationRunUUID} it produced. */
	private final Map<String, String> runUuidByAlias = new HashMap<>();

	/**
	 * Invokes {@link AtpReconciliationCommand#reconcileAndLog} directly for the given product/warehouse key (there
	 * is no {@code AD_Process} for it yet - see the class Javadoc), establishing a real, non-dry-run correction and
	 * storing its {@code ReconciliationRunUUID} under {@code runIdAlias} so a later step can look up the durable
	 * backup it persisted (see {@link #assertBackupRow}).
	 */
	@When("the ATP reconciliation is run for M_Product_ID {string} and M_Warehouse_ID {string}, storing the run id as {string}")
	public void runReconciliation(
			@NonNull final String productIdentifier,
			@NonNull final String warehouseIdentifier,
			@NonNull final String runIdAlias)
	{
		final ProductId productId = productTable.getId(productIdentifier);
		final WarehouseId warehouseId = warehouseTable.getId(warehouseIdentifier);

		final StockDataRecordIdentifier key = StockDataRecordIdentifier.builder()
				.clientId(StepDefConstants.CLIENT_ID)
				.orgId(StepDefConstants.ORG_ID)
				.warehouseId(warehouseId)
				.productId(productId)
				.storageAttributesKey(AttributesKey.NONE)
				.build();

		final AtpReconciliationCommand atpReconciliationCommand = SpringContextHolder.instance.getBean(AtpReconciliationCommand.class);
		final AtpReconciliationRunLog runLog = atpReconciliationCommand.reconcileAndLog(key, SystemTime.asInstant(), false);

		assertThat(runLog.getRunUuid())
				.as("expected an actual correction (a persisted run id) for M_Product_ID=%s M_Warehouse_ID=%s - "
						+ "check the scenario really set up a divergence for this key", productIdentifier, warehouseIdentifier)
				.isNotNull();

		runUuidByAlias.put(runIdAlias, runLog.getRunUuid());
	}

	/**
	 * Reads {@code MD_ATP_Reconciliation_Backup} fresh from the database by the persisted run id - proving the
	 * pre-change value and the after value both survive independently of the {@link AtpReconciliationRunLog}
	 * object the triggering step already discarded.
	 */
	@Then("the persisted ATP reconciliation backup for the run id {string} contains a row with QtyBefore {string} and QtyAfter {string}")
	public void assertBackupRow(
			@NonNull final String runIdAlias,
			@NonNull final String qtyBeforeText,
			@NonNull final String qtyAfterText)
	{
		final String runUuid = runUuidByAlias.get(runIdAlias);
		assertThat(runUuid).as("no run id was stored for alias '%s' - the triggering step must run first", runIdAlias).isNotNull();

		final List<I_MD_ATP_Reconciliation_Backup> backedUpRows = Services.get(IQueryBL.class)
				.createQueryBuilder(I_MD_ATP_Reconciliation_Backup.class)
				.addEqualsFilter(I_MD_ATP_Reconciliation_Backup.COLUMNNAME_ReconciliationRunUUID, runUuid)
				.create()
				.list();

		final BigDecimal expectedQtyAfter = new BigDecimal(qtyAfterText);
		final boolean expectNoQtyBefore = "null".equalsIgnoreCase(qtyBeforeText);

		final boolean matchFound = backedUpRows.stream().anyMatch(row ->
		{
			final boolean qtyAfterMatches = row.getQtyAfter().compareTo(expectedQtyAfter) == 0;
			final boolean qtyBeforeMatches = expectNoQtyBefore
					? InterfaceWrapperHelper.isNull(row, I_MD_ATP_Reconciliation_Backup.COLUMNNAME_QtyBefore)
					: row.getQtyBefore().compareTo(new BigDecimal(qtyBeforeText)) == 0;
			return qtyAfterMatches && qtyBeforeMatches;
		});

		assertThat(matchFound)
				.as("no persisted MD_ATP_Reconciliation_Backup row for run '%s' with QtyBefore=%s QtyAfter=%s among %s",
						runUuid, qtyBeforeText, qtyAfterText, backedUpRows)
				.isTrue();
	}
}
