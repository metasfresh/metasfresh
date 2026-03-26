package de.metas.handlingunits.inout.returns.vendor;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.snapshot.IHUSnapshotDAO;
import de.metas.handlingunits.snapshot.ISnapshotProducer;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.inout.IInOutBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_InOut;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles HU split/transform when a vendor return document (created by {@code M_InOut_GenerateVendorReturn}) is completed.
 * <p>
 * For each vendor return line that has a {@code Return_Origin_InOutLine_ID}:
 * 1. Finds the receipt HUs assigned to the origin receipt line
 * 2. Splits the return quantity from those HUs using {@link HUTransformService}
 * 3. Assigns the split-off HUs to the vendor return
 * 4. Creates HU snapshots for reversal support
 */
@Builder
public class VendorReturnFromReceiptHUHandler
{
	private static final String SYSCONFIG_PickAvailableHUsOnTheFly = "de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHUService.PickAvailableHUsOnTheFly";
	private static final String SYSCONFIG_VendorReturnsInOut_FailIfNoHUsAssigned = "VendorReturnsInOut.FailIfNoHUsAssigned";

	@NonNull private final I_M_InOut vendorReturn;

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final IHUAssignmentDAO huAssignmentDAO = Services.get(IHUAssignmentDAO.class);
	private final IHUAssignmentBL huAssignmentBL = Services.get(IHUAssignmentBL.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUSnapshotDAO huSnapshotDAO = Services.get(IHUSnapshotDAO.class);
	private final IHUInOutBL huInOutBL = Services.get(IHUInOutBL.class);

	public void execute()
	{
		final de.metas.handlingunits.model.I_M_InOut huVendorReturn = InterfaceWrapperHelper.create(vendorReturn, de.metas.handlingunits.model.I_M_InOut.class);

		final IContextAware context = InterfaceWrapperHelper.getContextAware(vendorReturn);
		final ISnapshotProducer<I_M_HU> snapshotProducer = huSnapshotDAO.createSnapshot().setContext(context);
		final List<I_M_HU> allSplitHUs = new ArrayList<>();

		final List<I_M_InOutLine> returnLines = InterfaceWrapperHelper.createList(
				inOutBL.getLines(vendorReturn),
				I_M_InOutLine.class);

		final IHUStorageFactory storageFactory = handlingUnitsBL
				.createMutableHUContext(context)
				.getHUStorageFactory();

		for (final I_M_InOutLine returnLine : returnLines)
		{
			if (returnLine.isDescription() || returnLine.isPackagingMaterial())
			{
				continue;
			}

			final int originInOutLineId = returnLine.getReturn_Origin_InOutLine_ID();
			if (originInOutLineId <= 0)
			{
				continue;
			}

			final BigDecimal returnQty = returnLine.getQtyEntered();
			if (returnQty == null || returnQty.signum() <= 0)
			{
				continue;
			}

			final List<I_M_HU> splitHUs = splitHUsForReturnLine(returnLine, originInOutLineId, returnQty, storageFactory);
			if (splitHUs.isEmpty())
			{
				continue;
			}

			// Assign split HUs to the return line
			huAssignmentBL.assignHUs(returnLine, splitHUs, org.compiere.util.Trx.TRXNAME_ThreadInherited);

			final TableRecordReference originalInOutLineRef = TableRecordReference.of(I_M_InOutLine.Table_Name, originInOutLineId);
			huAssignmentBL.unassignHUs(originalInOutLineRef, splitHUs.stream()
					.map(I_M_HU::getM_HU_ID)
					.map(HuId::ofRepoId)
					.collect(Collectors.toList()));

			// Mark new HUs as Shipped and queue for snapshot
			for (final I_M_HU hu : splitHUs)
			{
				hu.setHUStatus(X_M_HU.HUSTATUS_Shipped);
				InterfaceWrapperHelper.save(hu);
				snapshotProducer.addModel(hu);
			}
			allSplitHUs.addAll(splitHUs);
		}

		if (allSplitHUs.isEmpty())
		{
			if (isFailIfNoHUsAssigned())
			{
				throw new AdempiereException("No HUs could be split for vendor return. Check receipt HU assignments.");
			}
			return;
		}

		// Also assign all HUs to the header
		huInOutBL.setAssignedHandlingUnits(vendorReturn, allSplitHUs);

		// Create snapshots and store the UUID
		snapshotProducer.createSnapshots();
		huVendorReturn.setSnapshot_UUID(snapshotProducer.getSnapshotId());
		InterfaceWrapperHelper.save(huVendorReturn);
	}

	private List<I_M_HU> splitHUsForReturnLine(
			@NonNull final I_M_InOutLine returnLine,
			final int originInOutLineId,
			@NonNull final BigDecimal returnQty,
			@NonNull final IHUStorageFactory storageFactory)
	{
		// 1. Find HUs assigned to the origin receipt line
		final org.compiere.model.I_M_InOutLine originLine = InterfaceWrapperHelper.load(originInOutLineId, org.compiere.model.I_M_InOutLine.class);
		final List<I_M_HU_Assignment> assignments = huAssignmentDAO.retrieveTopLevelHUAssignmentsForModel(originLine);

		if (assignments.isEmpty())
		{
			if (isPickAvailableHUsOnTheFly())
			{
				// Customer doesn't care about HUs — skip HU handling for this line
				// Stock will be adjusted via M_Transaction
				return ImmutableList.of();
			}
			else
			{
				throw new AdempiereException("No HUs assigned to origin receipt line (M_InOutLine_ID=" + originInOutLineId + "). "
						+ "Cannot process vendor return. Please check receipt HU assignments.");
			}
		}

		// 2. Collect unique HUs from assignments (use VHU if available, else top-level)
		final List<I_M_HU> sourceHUs = new ArrayList<>();
		for (final I_M_HU_Assignment assignment : assignments)
		{
			final I_M_HU hu;
			if (assignment.getVHU_ID() > 0)
			{
				hu = InterfaceWrapperHelper.load(assignment.getVHU_ID(), I_M_HU.class);
			}
			else if (assignment.getM_TU_HU_ID() > 0)
			{
				hu = InterfaceWrapperHelper.load(assignment.getM_TU_HU_ID(), I_M_HU.class);
			}
			else
			{
				hu = assignment.getM_HU();
			}

			if (hu != null && hu.isActive())
			{
				sourceHUs.add(hu);
			}
		}

		if (sourceHUs.isEmpty())
		{
			if (isPickAvailableHUsOnTheFly())
			{
				return ImmutableList.of();
			}
			else
			{
				throw new AdempiereException("No active HUs found for origin receipt line (M_InOutLine_ID=" + originInOutLineId + ").");
			}
		}

		// 3. Split the return quantity from the source HUs
		return splitQuantityFromHUs(sourceHUs, returnLine, returnQty, storageFactory);
	}

	private List<I_M_HU> splitQuantityFromHUs(
			@NonNull final List<I_M_HU> sourceHUs,
			@NonNull final I_M_InOutLine returnLine,
			@NonNull final BigDecimal returnQty,
			@NonNull final IHUStorageFactory storageFactory)
	{
		final ProductId productId = ProductId.ofRepoId(returnLine.getM_Product_ID());
		final HUTransformService huTransformService = HUTransformService.newInstance();
		final List<I_M_HU> allSplitHUs = new ArrayList<>();
		BigDecimal remainingQty = returnQty;

		for (final I_M_HU sourceHU : sourceHUs)
		{
			if (remainingQty.signum() <= 0)
			{
				break;
			}

			// Navigate to the VHU level if this is a TU
			final I_M_HU cuHU = findCUForProductOrNull(sourceHU, productId, storageFactory);
			if (cuHU == null)
			{
				continue;
			}

			// Determine how much we can split from this HU
			final IHUStorage huStorage = storageFactory.getStorage(cuHU);
			final IHUProductStorage productStorage = huStorage.getProductStorageOrNull(productId);
			if (productStorage == null || productStorage.getQty().signum() <= 0)
			{
				continue;
			}

			final BigDecimal availableQty = productStorage.getQty().toBigDecimal();
			final BigDecimal splitQty = remainingQty.min(availableQty);

			final Quantity splitQuantity = Quantity.of(splitQty, productStorage.getC_UOM());

			final List<I_M_HU> splitResult = huTransformService.cuToNewCU(cuHU, splitQuantity);

			allSplitHUs.addAll(splitResult);

			remainingQty = remainingQty.subtract(splitQty);

			// Check if the source HU was fully consumed and mark it as Destroyed
			final IHUStorage updatedStorage = storageFactory.getStorage(cuHU);
			if (updatedStorage.isEmpty())
			{
				cuHU.setHUStatus(X_M_HU.HUSTATUS_Destroyed);
				InterfaceWrapperHelper.save(cuHU);
			}
		}

		if (remainingQty.signum() > 0 && !isPickAvailableHUsOnTheFly())
		{
			throw new AdempiereException("Insufficient HU stock for return quantity. "
					+ "Remaining qty=" + remainingQty + " for product " + productId);
		}

		return allSplitHUs;
	}

	@Nullable
	private I_M_HU findCUForProductOrNull(
			@NonNull final I_M_HU hu,
			@NonNull final ProductId productId,
			@NonNull final IHUStorageFactory storageFactory)
	{
		// If it's already a VHU (CU), return it directly
		if (handlingUnitsBL.isVirtual(hu))
		{
			return hu;
		}

		// If it's an aggregate HU, it can be used directly with cuToNewCU
		if (handlingUnitsBL.isAggregateHU(hu))
		{
			return hu;
		}

		// If it's a TU, find the VHU inside
		final IHUStorage huStorage = storageFactory.getStorage(hu);
		final IHUProductStorage productStorage = huStorage.getProductStorageOrNull(productId);
		if (productStorage != null && productStorage.getQty().signum() > 0)
		{
			// This HU has the product — find the VHU child that holds it
			final List<I_M_HU> childHUs = handlingUnitsBL.retrieveIncludedHUs(hu);
			for (final I_M_HU childHU : childHUs)
			{
				if (handlingUnitsBL.isVirtual(childHU))
				{
					final IHUStorage childStorage = storageFactory.getStorage(childHU);
					final IHUProductStorage childProductStorage = childStorage.getProductStorageOrNull(productId);
					if (childProductStorage != null && childProductStorage.getQty().signum() > 0)
					{
						return childHU;
					}
				}
				// Recurse for nested TUs
				final I_M_HU found = findCUForProductOrNull(childHU, productId, storageFactory);
				if (found != null)
				{
					return found;
				}
			}
		}

		return null;
	}

	private boolean isPickAvailableHUsOnTheFly()
	{
		return sysConfigBL.getBooleanValue(SYSCONFIG_PickAvailableHUsOnTheFly, true);
	}

	private boolean isFailIfNoHUsAssigned()
	{
		return sysConfigBL.getBooleanValue(SYSCONFIG_VendorReturnsInOut_FailIfNoHUsAssigned, true);
	}
}
