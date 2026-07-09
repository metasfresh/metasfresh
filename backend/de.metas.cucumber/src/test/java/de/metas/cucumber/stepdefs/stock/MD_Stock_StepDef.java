/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.cucumber.stepdefs.stock;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSetInstance_StepDefData;
import de.metas.cucumber.stepdefs.warehouse.M_Warehouse_StepDefData;
import de.metas.logging.LogManager;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs;
import de.metas.material.event.commons.AttributesKey;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessInfo;
import org.adempiere.mm.attributes.keys.AttributesKeys;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.*;

public class MD_Stock_StepDef
{
	private final static transient Logger logger = LogManager.getLogger(MD_Stock_StepDef.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	private final M_Product_StepDefData productTable;
	private final M_Warehouse_StepDefData warehouseTable;
	private final M_AttributeSetInstance_StepDefData asiTable;

	public MD_Stock_StepDef(
			final M_Product_StepDefData productTable,
			final M_Warehouse_StepDefData warehouseTable,
			final M_AttributeSetInstance_StepDefData asiTable)
	{
		this.productTable = productTable;
		this.warehouseTable = warehouseTable;
		this.asiTable = asiTable;
	}

	/**
	 * Runs {@link MD_Stock_Update_From_M_HUs}, which resets {@code MD_Stock.QtyOnHand} to the
	 * {@code M_HU_Storage}-derived truth for every product/warehouse/attributes-key row where the
	 * two diverge.
	 *
	 * <p>Stands in for the periodic {@code AD_Scheduler} (CronPattern {@code * /15 * * * *}) that
	 * reconciles MD_Stock from HU data in production — this step invokes the same process
	 * synchronously so the test can assert on its result without waiting for the scheduler.
	 *
	 * <p>Takes no DataTable; the process itself finds and corrects every diverging row.
	 *
	 * <p>Example:
	 * <pre>
	 * When the MD_Stock reconciliation process is run
	 * </pre>
	 */
	@When("the MD_Stock reconciliation process is run")
	public void run_MD_Stock_reconciliation_process()
	{
		final AdProcessId processId = adProcessDAO.retrieveProcessIdByClass(MD_Stock_Update_From_M_HUs.class);

		ProcessInfo.builder()
				.setAD_Process_ID(processId.getRepoId())
				.buildAndPrepareExecution()
				.onErrorThrowException()
				.executeSync();
	}

	/**
	 * Waits up to {@code timeoutSeconds} for all rows in the DataTable to match MD_Stock records,
	 * then validates each row exactly.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code M_Product_ID.Identifier} — product identifier registered in the step-def data store</li>
	 *   <li>{@code QtyOnHand} — expected quantity on hand</li>
	 * </ul>
	 * Optional columns:
	 * <ul>
	 *   <li>{@code OPT.M_Warehouse_ID.Identifier} — narrows the filter to a specific warehouse</li>
	 *   <li>{@code OPT.M_AttributeSetInstance_ID.Identifier} — ASI identifier whose storage-relevant
	 *       attributes are used to compute the {@link AttributesKey} filter applied on
	 *       {@code MD_Stock.AttributesKey}; resolves via
	 *       {@link AttributesKeys#createAttributesKeyFromASIStorageAttributes}</li>
	 * </ul>
	 *
	 * <p>Example:
	 * <pre>
	 * And after not more than 30 seconds metasfresh has MD_Stock data
	 *   | M_Product_ID.Identifier | QtyOnHand | OPT.M_Warehouse_ID.Identifier | OPT.M_AttributeSetInstance_ID.Identifier |
	 *   | product                 | 10        | warehouseStd                  | asiA                                     |
	 *   | product                 | 0         | warehouseStd                  | asiB                                     |
	 * </pre>
	 */
	@And("after not more than {int} seconds metasfresh has MD_Stock data")
	public void verify_MD_Stock_Data(final int timeoutSeconds, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> rows = dataTable.asMaps();

		final Supplier<Boolean> supplier = () -> rows.stream().allMatch(this::waitForStock);

		StepDefUtil.tryAndWait(timeoutSeconds, 500, supplier);

		for (final Map<String, String> row : rows)
		{
			validateMD_Stock(row);
		}
	}

	private boolean waitForStock(@NonNull final Map<String, String> row)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, "M_Product_ID.Identifier");
		final int productId = productTable.get(productIdentifier).getM_Product_ID();

		final BigDecimal qtyOnHand = DataTableUtil.extractBigDecimalForColumnName(row, "QtyOnHand");

		final I_MD_Stock mdStock = buildStockQuery(productId, row).create().firstOnly(I_MD_Stock.class);
		return mdStock != null && mdStock.getQtyOnHand().compareTo(qtyOnHand) == 0;
	}

	private void validateMD_Stock(@NonNull final Map<String, String> row)
	{
		final String productIdentifier = DataTableUtil.extractStringForColumnName(row, "M_Product_ID.Identifier");
		final BigDecimal qtyOnHand = DataTableUtil.extractBigDecimalForColumnName(row, "QtyOnHand");

		final I_M_Product product = productTable.get(productIdentifier);

		final I_MD_Stock mdStock = buildStockQuery(product.getM_Product_ID(), row).create().firstOnly(I_MD_Stock.class);
		assertThat(mdStock).isNotNull();
		assertThat(mdStock.getQtyOnHand()).isEqualTo(qtyOnHand);
	}

	/**
	 * Builds a query for MD_Stock, applying a mandatory product filter plus optional warehouse
	 * and {@code AttributesKey} filters derived from the DataTable row.
	 */
	@NonNull
	private IQueryBuilder<I_MD_Stock> buildStockQuery(final int productId, @NonNull final Map<String, String> row)
	{
		final IQueryBuilder<I_MD_Stock> builder = queryBL.createQueryBuilder(I_MD_Stock.class)
				.addEqualsFilter(I_MD_Stock.COLUMNNAME_M_Product_ID, productId);

		final String warehouseIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_MD_Stock.COLUMNNAME_M_Warehouse_ID + ".Identifier");
		if (warehouseIdentifier != null)
		{
			final I_M_Warehouse warehouse = warehouseTable.get(warehouseIdentifier);
			assertThat(warehouse).isNotNull();
			builder.addEqualsFilter(I_MD_Stock.COLUMNNAME_M_Warehouse_ID, warehouse.getM_Warehouse_ID());
		}

		final String asiIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT.M_AttributeSetInstance_ID.Identifier");
		if (asiIdentifier != null)
		{
			final AttributesKey attributesKey = resolveAttributesKey(asiIdentifier);
			builder.addEqualsFilter(I_MD_Stock.COLUMNNAME_AttributesKey, attributesKey.getAsString());
		}

		return builder;
	}

	@NonNull
	private AttributesKey resolveAttributesKey(@Nullable final String asiIdentifier)
	{
		if (asiIdentifier == null)
		{
			return AttributesKey.NONE;
		}
		final I_M_AttributeSetInstance asi = asiTable.get(asiIdentifier);
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(asi.getM_AttributeSetInstance_ID());
		return AttributesKeys.createAttributesKeyFromASIStorageAttributes(asiId).orElse(AttributesKey.NONE);
	}
}
