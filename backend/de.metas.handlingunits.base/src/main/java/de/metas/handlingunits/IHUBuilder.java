package de.metas.handlingunits;

import java.time.ZonedDateTime;

import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.warehouse.LocatorId;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.storage.IHUStorageFactory;

import javax.annotation.Nullable;

/**
 * Handling Unit Builder Helper
 *
 * @author tsa
 *
 */
public interface IHUBuilder extends IHUIterator
{
	/**
	 * This instance stores the {@code huPIVersion} parameter with which the {@link #createInstanceRecursively(I_M_HU_PI_Version, I_M_HU_Item)} method was called with.<br>
	 * Background: if the parent item is a {@link X_M_HU_Item#ITEMTYPE_HUAggregate} item, then a virtual HU is created, no matter what the given {@code huPIVersion} sais.<br>
	 * However, below that virtual HU, the given {@code huPIVersion}'s material and packaging items shall be created none the less.
	 */
	ModelDynAttributeAccessor<I_M_HU, I_M_HU_PI_Version> BUILDER_INVOCATION_HU_PI_VERSION = new ModelDynAttributeAccessor<>("invocationParamPIVersion", I_M_HU_PI_Version.class);

	/**
	 * Create handling unit by calling {@link #create(I_M_HU_PI_Version)} with the current version of the given <code>pi</code>.
	 *
	 * @return created handling unit; never return null
	 */
	I_M_HU create(I_M_HU_PI pi);

	/**
	 * Create handling unit using the given <code>piVersion</code> and (as parent item) the {@link I_M_HU_Item} that was previously set via {@link #setM_HU_Item_Parent(I_M_HU_Item)}.
	 * <p>
	 * Also check out {@link #setM_HU_Item_Parent(I_M_HU_Item)}.
	 *
	 * @param piVersion
	 * @return created handling unit; never return null
	 */
	I_M_HU create(I_M_HU_PI_Version piVersion);

	@Override
	ZonedDateTime getDate();

	@Override
	void setStorageFactory(IHUStorageFactory storageFactory);

	/**
	 * If the type of the given {@code parentItem} is {@link X_M_HU_Item#ITEMTYPE_HUAggregate}, then {@link #create(I_M_HU_PI_Version)} will create an aggregate VHU
	 * which will contain packing material {@link I_M_HU_Item} according to the {@link I_M_HU_PI_Version} that was given as parameter to the {@code create} method.
	 * <p>
	 * Otherwise, if the item type of the given {@code parentItem} is {@link X_M_HU_Item#ITEMTYPE_HandlingUnit} (or if the given parent item is {@code null}),
	 * then {@link #create(I_M_HU_PI_Version)} shall create a "normal" HU.
	 *
	 * @param parentItem
	 */
	IHUBuilder setM_HU_Item_Parent(@Nullable I_M_HU_Item parentItem);

	IHUBuilder setM_HU_PI_Item_Product(I_M_HU_PI_Item_Product piip);

	IHUBuilder setBPartnerId(BPartnerId bpartnerId);

	IHUBuilder setC_BPartner_Location_ID(int bpartnerLocationId);

	/**
	 * Sets M_Locator to be set on newly create HU.
	 *
	 * In case HU Item Parent (see {@link #setM_HU_Item_Parent(I_M_HU_Item)}) is set then locator from parent will be used and this one will be ignored.
	 */
	IHUBuilder setLocatorId(LocatorId locatorId);

	/**
	 * Sets {@link I_M_HU#COLUMN_HUStatus} to be used when creating a new HU.
	 *
	 * In case HU Item Parent (see {@link #setM_HU_Item_Parent(I_M_HU_Item)}) is set then HUStatus from parent will be used and this one will be ignored.
	 *
	 * @param huStatus HU Status (see X_M_HU.HUSTATUS_*); null is not accepted
	 */
	IHUBuilder setHUStatus(String huStatus);

	IHUBuilder setM_HU_LUTU_Configuration(I_M_HU_LUTU_Configuration lutuConfiguration);

	/**
	 * For material receipts, flag that the HU's PM owner will be "us". See {@link I_M_HU#isHUPlanningReceiptOwnerPM()}
	 */
	IHUBuilder setHUPlanningReceiptOwnerPM(boolean huPlanningReceiptOwnerPM);

	/**
	 * @return true if the HU's owner is "us". See {@link I_M_HU#isHUPlanningReceiptOwnerPM()}
	 */
	boolean isHUPlanningReceiptOwnerPM();
}
