package de.metas.handlingunits.shipmentschedule.api;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.document.dimension.Dimension;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHUPackageBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocBL;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.document.dimension.ShipmentScheduleDimensionFactory;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.ShipmentScheduleHandler;
import de.metas.inoutcandidate.split.ShipmentScheduleSplit;
import de.metas.inoutcandidate.split.ShipmentScheduleSplitRepository;
import de.metas.inoutcandidate.split.ShipmentScheduleSplitService;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_InOut;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * Supporting services needed by {@link ShipmentScheduleWithHU}
 */
@Service
@RequiredArgsConstructor
public class ShipmentScheduleWithHUSupportingServices
{
	@NonNull private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private IHUShipmentScheduleBL _huShipmentScheduleBL = null; // lazy to avoid cyclic dependency
	@NonNull private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
	@NonNull private final IShipmentScheduleHandlerBL shipmentScheduleHandlerBL = Services.get(IShipmentScheduleHandlerBL.class);
	@NonNull private final IShipmentScheduleAllocBL shipmentScheduleAllocBL = Services.get(IShipmentScheduleAllocBL.class);
	@NonNull private final IHUPackageBL huPackageBL = Services.get(IHUPackageBL.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	@NonNull private final IHUPIItemProductDAO hupiItemProductDAO = Services.get(IHUPIItemProductDAO.class);

	@NonNull private final ShipmentScheduleDimensionFactory shipmentScheduleDimensionFactory;
	@NonNull private final ShipmentScheduleSplitService shipmentScheduleSplitService;

	public static ShipmentScheduleWithHUSupportingServices getInstance()
	{
		return Adempiere.isUnitTestMode()
				? newInstanceForUnitTesting()
				: SpringContextHolder.instance.getBean(ShipmentScheduleWithHUSupportingServices.class);
	}

	public static ShipmentScheduleWithHUSupportingServices newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();

		return new ShipmentScheduleWithHUSupportingServices(
				new ShipmentScheduleDimensionFactory(),
				new ShipmentScheduleSplitService(new ShipmentScheduleSplitRepository())
		);
	}

	private IHUShipmentScheduleBL huShipmentScheduleBL()
	{
		IHUShipmentScheduleBL huShipmentScheduleBL = this._huShipmentScheduleBL;
		if (huShipmentScheduleBL == null)
		{
			huShipmentScheduleBL = this._huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
		}
		return huShipmentScheduleBL;
	}

	public ShipmentScheduleHandler getShipmentScheduleHandler(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		return shipmentScheduleHandlerBL.getHandlerFor(shipmentSchedule);
	}

	public Dimension getDimension(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		return shipmentScheduleDimensionFactory.getFromRecord(shipmentSchedule);
	}

	public void save(final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked)
	{
		InterfaceWrapperHelper.saveRecord(shipmentScheduleQtyPicked);
	}

	public void save(@NonNull final ShipmentScheduleSplit split)
	{
		shipmentScheduleSplitService.save(split);
	}

	public I_M_AttributeSetInstance getASI(@NonNull final AttributeSetInstanceId asiId)
	{
		asiId.assertRegular();
		return InterfaceWrapperHelper.load(asiId, I_M_AttributeSetInstance.class);
	}

	public void assignShipmentToPackages(final I_M_HU topLevelHU, final I_M_InOut inout, final String trxName)
	{
		huPackageBL.assignShipmentToPackages(topLevelHU, inout, trxName);
	}

	public I_M_ShipmentSchedule_QtyPicked createNewQtyPickedRecord(@NonNull final I_M_ShipmentSchedule shipmentSchedule, @NonNull final StockQtyAndUOMQty stockQtyAndCatchQty)
	{
		return InterfaceWrapperHelper.create(
				shipmentScheduleAllocBL.createNewQtyPickedRecord(shipmentSchedule, stockQtyAndCatchQty),
				I_M_ShipmentSchedule_QtyPicked.class);

	}

	public void resetCatchQtyOverride(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		shipmentScheduleBL.resetCatchQtyOverride(shipmentSchedule);
	}

	public BigDecimal extractTuQty(@NonNull final I_M_HU tuHU)
	{
		if (handlingUnitsBL.isAggregateHU(tuHU))
		{
			handlingUnitsDAO.retrieveParentItem(tuHU);
			return tuHU.getM_HU_Item_Parent().getQty();
		}
		else
		{
			return BigDecimal.ONE;
		}
	}

	public ImmutableList<I_M_HU_Item> getMaterialHUItems(@NonNull final I_M_HU topLevelHU)
	{
		return handlingUnitsDAO.retrieveItems(topLevelHU).stream()
				.filter(item -> X_M_HU_Item.ITEMTYPE_Material.equals(item.getItemType()))
				.collect(ImmutableList.toImmutableList());
	}

	public BPartnerId getBPartnerId(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		return shipmentScheduleEffectiveBL.getBPartnerId(shipmentSchedule);
	}

	public ZonedDateTime getPreparationDate(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		return shipmentScheduleEffectiveBL.getPreparationDate(shipmentSchedule);
	}

	public I_M_HU_PI_Item_Product getVirtualPIMaterialItemProduct()
	{
		return hupiItemProductDAO.retrieveVirtualPIMaterialItemProduct(Env.getCtx());
	}

	public I_M_HU_PI_Item_Product retrievePIMaterialItemProduct(
			@NonNull final I_M_HU_PI_Item huPIItem,
			@Nullable final BPartnerId bpartnerId,
			@NonNull final ProductId productId,
			@Nullable final ZonedDateTime date)
	{
		return hupiItemProductDAO.retrievePIMaterialItemProduct(huPIItem, bpartnerId, productId, date);
	}

	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product_IgnoringPickedHUs(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		return huShipmentScheduleBL().getM_HU_PI_Item_Product_IgnoringPickedHUs(shipmentSchedule);
	}

	public I_M_HU_PI_Item getPIItem(final I_M_HU_Item huMaterialItem)
	{
		return handlingUnitsBL.getPIItem(huMaterialItem);
	}
}
