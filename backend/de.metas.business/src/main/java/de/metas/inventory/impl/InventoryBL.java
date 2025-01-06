package de.metas.inventory.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.document.engine.DocStatus;
import de.metas.inventory.IInventoryBL;
import de.metas.inventory.IInventoryDAO;
import de.metas.inventory.InventoryId;
import de.metas.inventory.InventoryLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_InventoryLine;
import org.compiere.util.Env;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.save;

public class InventoryBL implements IInventoryBL
{
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IInventoryDAO inventoryDAO = Services.get(IInventoryDAO.class);

	@VisibleForTesting
	public static final String SYSCONFIG_QuickInput_Charge_ID = "de.metas.adempiere.callout.M_Inventory.QuickInput.C_Charge_ID";

	@Override
	public int getDefaultInternalChargeId()
	{
		final Properties ctx = Env.getCtx();
		final int chargeId = sysConfigBL.getIntValue(
				SYSCONFIG_QuickInput_Charge_ID,
				-1, // defaultValue
				Env.getAD_Client_ID(ctx),
				Env.getAD_Org_ID(ctx));
		if (chargeId <= 0)
		{
			throw new AdempiereException("@NotFound@ @AD_SysConfig_ID@: " + InventoryBL.SYSCONFIG_QuickInput_Charge_ID);
		}

		return chargeId;
	}

	@Override
	public void addDescription(@NonNull final I_M_Inventory inventory, final String descriptionToAdd)
	{
		final String description = inventory.getDescription();
		if (Check.isEmpty(description, true))
		{
			inventory.setDescription(description);
		}
		else
		{
			inventory.setDescription(description + " | " + description);
		}
	}

	@Override
	public void addDescription(@NonNull final I_M_InventoryLine inventoryLine, final String descriptionToAdd)
	{
		final String description = inventoryLine.getDescription();
		if (Check.isEmpty(description, true))
		{
			inventoryLine.setDescription(description);
		}
		else
		{
			inventoryLine.setDescription(description + " | " + description);
		}
	}

	@Override
	public DocStatus getDocStatus(@NonNull final InventoryId inventoryId)
	{
		final I_M_Inventory inventory = inventoryDAO.getById(inventoryId);
		return DocStatus.ofCode(inventory.getDocStatus());
	}

	@Override
	public boolean isComplete(@NonNull final I_M_Inventory inventory)
	{
		final DocStatus docStatus = DocStatus.ofCode(inventory.getDocStatus());
		return docStatus.isCompletedOrClosedReversedOrVoided();
	}

	@Override
	public Quantity getMovementQty(final I_M_InventoryLine inventoryLine)
	{
		final I_C_UOM uom = loadOutOfTrx(inventoryLine.getC_UOM_ID(), I_C_UOM.class);

		if (isInternalUseInventory(inventoryLine))
		{
			final BigDecimal qtyValue = inventoryLine.getQtyInternalUse().negate();
			return Quantity.of(qtyValue, uom);
		}
		else
		{
			BigDecimal qtyValue = inventoryLine.getQtyCount().subtract(inventoryLine.getQtyBook());
			return Quantity.of(qtyValue, uom);
		}
	}

	@Override
	public Quantity getMovementQtyInStockingUOM(final I_M_InventoryLine inventoryLine)
	{
		ProductId productId = ProductId.ofRepoId(inventoryLine.getM_Product_ID());
		final Quantity movementQty = getMovementQty(inventoryLine);
		return Services.get(IUOMConversionBL.class).convertToProductUOM(movementQty, productId);
	}

	@Override
	public boolean isInternalUseInventory(final I_M_InventoryLine inventoryLine)
	{
		/*
		 * TODO: need to add M_Inventory.IsInternalUseInventory flag
		 * see FR [ 1879029 ] Added IsInternalUseInventory flag to M_Inventory table
		 * MInventory parent = getParent();
		 * return parent != null && parent.isInternalUseInventory();
		 */
		return inventoryLine.getQtyInternalUse().signum() != 0;
	}

	@Override
	public boolean isSOTrx(final I_M_InventoryLine inventoryLine)
	{
		return getMovementQty(inventoryLine).signum() < 0;
	}

	@Override
	public void assignToInventoryCounters(final List<I_M_InventoryLine> inventoryLines, final int numberOfCounters)
	{
		final Map<Integer, List<I_M_InventoryLine>> linesToLocators = new HashMap<>();

		GuavaCollectors.groupByAndStream(inventoryLines.stream(), I_M_InventoryLine::getM_Locator_ID)
				.forEach(
						inventoryLinesPerLocator -> linesToLocators.put(inventoryLinesPerLocator.get(0).getM_Locator_ID(),
																		inventoryLinesPerLocator));

		final List<Integer> locatorIds = linesToLocators
				.keySet()
				.stream()
				.sorted()
				.collect(ImmutableList.toImmutableList());

		int i = 0;

		for (int locatorId : locatorIds)
		{
			if (i == numberOfCounters)
			{
				i = 0;
			}
			final char counterIdentifier = (char)('A' + i);

			assignInventoryLinesToCounterIdentifiers(linesToLocators.get(locatorId), counterIdentifier);

			i++;
		}
	}

	private void assignInventoryLinesToCounterIdentifiers(final List<I_M_InventoryLine> list, final char counterIdentifier)
	{
		list.forEach(inventoryLine -> {
			inventoryLine.setAssignedTo(Character.toString(counterIdentifier));
			save(inventoryLine);
		});

	}

	@Override
	public void setDefaultInternalChargeId(final I_M_InventoryLine inventoryLine)
	{
		final int defaultChargeId = getDefaultInternalChargeId();

		inventoryLine.setC_Charge_ID(defaultChargeId);

	}

	@Override
	public void markInventoryLinesAsCounted(@NonNull final InventoryId inventoryId)
	{
		inventoryDAO.retrieveLinesForInventoryId(inventoryId)
				.forEach(this::markInventoryLineAsCounted);

	}

	@NonNull
	@Override
	public I_M_Inventory getById(@NonNull final InventoryId inventoryId)
	{
		return Optional.ofNullable(inventoryDAO.getById(inventoryId))
				.orElseThrow(() -> new AdempiereException("No record found for " + inventoryId));
	}

	private void markInventoryLineAsCounted(final I_M_InventoryLine inventoryLine)
	{
		inventoryLine.setIsCounted(true);
		save(inventoryLine);
	}

	@NonNull
	@Override
	public I_M_InventoryLine getLineById(@NonNull final InventoryLineId inventoryLineId)
	{
		return inventoryDAO.getLineById(inventoryLineId);
	}

	@Override
	public boolean isReversal(@NonNull final I_M_Inventory inventory)
	{
		final InventoryId reversalId = InventoryId.ofRepoIdOrNull(inventory.getReversal_ID());

		return reversalId != null && reversalId.getRepoId() < inventory.getM_Inventory_ID();
	}
}
