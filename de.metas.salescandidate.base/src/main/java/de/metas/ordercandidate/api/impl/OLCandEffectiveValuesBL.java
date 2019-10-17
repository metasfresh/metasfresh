package de.metas.ordercandidate.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.time.ZonedDateTime;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerDAO.BPartnerLocationQuery;
import de.metas.bpartner.service.IBPartnerDAO.BPartnerLocationQuery.Type;
import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.lang.CoalesceUtil;
import lombok.NonNull;

public class OLCandEffectiveValuesBL implements IOLCandEffectiveValuesBL
{

	private IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

	private int getC_BPartner_Effective_ID(final I_C_OLCand olCand)
	{
		final Integer bPartnerOverride = InterfaceWrapperHelper.getValueOverrideOrValue(olCand, I_C_OLCand.COLUMNNAME_C_BPartner_ID);
		final int bPartnerID = bPartnerOverride == null ? 0 : bPartnerOverride;

		if (bPartnerID <= 0)
		{
			return olCand.getC_BPartner_ID();
		}
		return bPartnerID;
	}

	@Override
	public I_C_BPartner getC_BPartner_Effective(@NonNull final I_C_OLCand olCand)
	{
		return InterfaceWrapperHelper.create(
				InterfaceWrapperHelper.getCtx(olCand),
				getC_BPartner_Effective_ID(olCand),
				I_C_BPartner.class,
				InterfaceWrapperHelper.getTrxName(olCand));
	}

	@Override
	public I_C_BPartner_Location getC_BP_Location_Effective(final I_C_OLCand olCand)
	{
		return InterfaceWrapperHelper.create(
				InterfaceWrapperHelper.getCtx(olCand),
				getC_BP_Location_Effective_ID(olCand, Type.SHIP_TO),
				I_C_BPartner_Location.class,
				InterfaceWrapperHelper.getTrxName(olCand));
	}

	private int getAD_User_Effective_ID(final I_C_OLCand olCand)
	{
		return olCand.getAD_User_ID();
	}

	@Override
	public ZonedDateTime getDatePromised_Effective(@NonNull final I_C_OLCand olCand)
	{
		return CoalesceUtil.coalesceSuppliers(
				() -> TimeUtil.asZonedDateTime(olCand.getDatePromised_Override()),
				() -> TimeUtil.asZonedDateTime(olCand.getDatePromised()),
				() -> TimeUtil.asZonedDateTime(olCand.getDateOrdered()),
				() -> TimeUtil.asZonedDateTime(olCand.getDateCandidate()));
	}

	private int getBill_BPartner_Effective_ID(final I_C_OLCand olCand)
	{
		return olCand.getBill_BPartner_ID() > 0
				? olCand.getBill_BPartner_ID()
				: getC_BPartner_Effective_ID(olCand);
	}

	@Override
	public <T extends I_C_BPartner> T getBill_BPartner_Effective(final I_C_OLCand olCand, final Class<T> clazz)
	{
		return InterfaceWrapperHelper.create(
				InterfaceWrapperHelper.getCtx(olCand),
				getBill_BPartner_Effective_ID(olCand),
				clazz,
				InterfaceWrapperHelper.getTrxName(olCand));
	}

	private int getBill_Location_Effective_ID(final I_C_OLCand olCand)
	{
		return olCand.getBill_Location_ID() > 0
				? olCand.getBill_Location_ID()
				: getC_BP_Location_Effective_ID(olCand, Type.BILL_TO);
	}

	@Override
	public I_C_BPartner_Location getBill_Location_Effective(final I_C_OLCand olCand)
	{
		return InterfaceWrapperHelper.create(
				InterfaceWrapperHelper.getCtx(olCand),
				getBill_Location_Effective_ID(olCand),
				I_C_BPartner_Location.class,
				InterfaceWrapperHelper.getTrxName(olCand));
	}

	private int getBill_User_Effective_ID(final I_C_OLCand olCand)
	{
		return olCand.getBill_User_ID() > 0
				? olCand.getBill_User_ID()
				: getAD_User_Effective_ID(olCand);
	}

	private int getDropShip_BPartner_Effective_ID(final I_C_OLCand olCand)
	{
		final Integer dropShipBPartnerOverride = InterfaceWrapperHelper.getValueOverrideOrValue(olCand, I_C_OLCand.COLUMNNAME_DropShip_BPartner_ID);
		final int dropShipBPartnerID = dropShipBPartnerOverride == null ? 0 : dropShipBPartnerOverride;

		final int bpartnerID = getC_BPartner_Effective_ID(olCand);

		if (dropShipBPartnerID > 0)
		{
			// the dropship partner was set
			return dropShipBPartnerID;
		}

		// fall-back to bpartner
		return bpartnerID;
	}

	private int getDropShip_Location_Effective_ID(final I_C_OLCand olCand)
	{
		if (olCand.getDropShip_Location_Override_ID() > 0)
		{
			return olCand.getDropShip_Location_Override_ID();
		}

		if (olCand.getDropShip_Location_ID() > 0)
		{
			return olCand.getDropShip_Location_ID();
		}

		// fallback to the bp location
		final int bpLocationId = getC_BP_Location_Effective_ID(olCand, Type.SHIP_TO);
		return bpLocationId;
	}

	@Override
	public I_C_BPartner_Location getDropShip_Location_Effective(final I_C_OLCand olCand)
	{
		return InterfaceWrapperHelper.create(
				InterfaceWrapperHelper.getCtx(olCand),
				getDropShip_Location_Effective_ID(olCand),
				I_C_BPartner_Location.class,
				InterfaceWrapperHelper.getTrxName(olCand));
	}

	private int getDropShip_User_Effective_ID(final I_C_OLCand olCand)
	{
		return getAD_User_Effective_ID(olCand);
	}

	@Override
	public ProductId getM_Product_Effective_ID(@NonNull final I_C_OLCand olCand)
	{
		final int productRepoId = olCand.getM_Product_Override_ID() > 0
				? olCand.getM_Product_Override_ID()
				: olCand.getM_Product_ID();
		return ProductId.ofRepoId(productRepoId);
	}

	@Override
	public I_M_Product getM_Product_Effective(@NonNull final I_C_OLCand olCand)
	{
		return load(
				getM_Product_Effective_ID(olCand),
				I_M_Product.class);
	}

	@Override
	public int getC_UOM_Effective_ID(@NonNull final I_C_OLCand olCandRecord)
	{
		return olCandRecord.isManualPrice()
				? getRecordOrStockUOMId(olCandRecord).getRepoId()
				: olCandRecord.getC_UOM_Internal_ID();
	}

	@Override
	public UomId getRecordOrStockUOMId(@NonNull final I_C_OLCand olCandRecord)
	{
		if (olCandRecord.getC_UOM_ID() > 0)
		{
			return UomId.ofRepoId(olCandRecord.getC_UOM_ID());
		}
		final IProductBL productBL = Services.get(IProductBL.class);
		final UomId stockUOMId = productBL.getStockUOMId(ProductId.ofRepoId(olCandRecord.getM_Product_ID()));
		return stockUOMId;
	}

	@Override
	public I_C_UOM getC_UOM_Effective(final I_C_OLCand olCand)
	{
		return InterfaceWrapperHelper.create(
				InterfaceWrapperHelper.getCtx(olCand),
				getC_UOM_Effective_ID(olCand),
				I_C_UOM.class,
				InterfaceWrapperHelper.getTrxName(olCand));
	}

	private int getHandOver_Partner_Effective_ID(final I_C_OLCand olCand)
	{
		final Integer handOverPartnerOverride = InterfaceWrapperHelper.getValueOverrideOrValue(olCand, I_C_OLCand.COLUMNNAME_HandOver_Partner_ID);
		final int handOverPartnerID = handOverPartnerOverride == null ? 0 : handOverPartnerOverride;

		final int bpartnerId = getC_BPartner_Effective_ID(olCand);

		if (handOverPartnerID > 0)
		{
			// the handover partner was set

			return handOverPartnerID;
		}

		// fall-back to C_BPartner
		return bpartnerId;

	}

	@Override
	public I_C_BPartner getHandOver_Partner_Effective(final I_C_OLCand olCand)
	{
		return InterfaceWrapperHelper.create(
				InterfaceWrapperHelper.getCtx(olCand),
				getHandOver_Partner_Effective_ID(olCand),
				I_C_BPartner.class,
				InterfaceWrapperHelper.getTrxName(olCand));
	}

	private int getHandOver_Location_Effective_ID(final I_C_OLCand olCand)
	{
		final Integer handOverLocationOverride = InterfaceWrapperHelper.getValueOverrideOrValue(olCand, I_C_OLCand.COLUMNNAME_HandOver_Location_ID);
		final int handOverLocationID = handOverLocationOverride == null ? 0 : handOverLocationOverride;

		if (handOverLocationID > 0)
		{
			// the handover location was set
			return handOverLocationID;
		}

		// fall-back to C_BPartner_Location
		final int bpLocationId = getC_BP_Location_Effective_ID(olCand, Type.SHIP_TO);
		return bpLocationId;
	}

	@Override
	public I_C_BPartner_Location getHandOver_Location_Effective(final I_C_OLCand olCand)
	{
		return InterfaceWrapperHelper.create(
				InterfaceWrapperHelper.getCtx(olCand),
				getHandOver_Location_Effective_ID(olCand),
				I_C_BPartner_Location.class,
				InterfaceWrapperHelper.getTrxName(olCand));
	}

	@Override
	public BPartnerId getBPartnerEffectiveId(@NonNull final I_C_OLCand olCand)
	{
		return BPartnerId.ofRepoIdOrNull(getC_BPartner_Effective_ID(olCand));
	}

	@Override
	public BPartnerLocationId getLocationEffectiveId(@NonNull final I_C_OLCand olCand)
	{
		return BPartnerLocationId.ofRepoIdOrNull(
				getC_BPartner_Effective_ID(olCand),
				getC_BP_Location_Effective_ID(olCand, Type.SHIP_TO));
	}

	/**
	 * @param if falling back to the {@code C_BPartner_Location} masterdata, then prefer this type.
	 */
	private int getC_BP_Location_Effective_ID(@NonNull final I_C_OLCand olCandRecord, @NonNull final Type type)
	{
		if (olCandRecord.getC_BP_Location_Override_ID() > 0)
		{
			return olCandRecord.getC_BP_Location_Override_ID();
		}
		if (olCandRecord.getC_BPartner_Location_ID() > 0)
		{
			return olCandRecord.getC_BPartner_Location_ID();
		}

		final BPartnerLocationId bpartnerLocationId = bPartnerDAO
				.retrieveBPartnerLocationId(BPartnerLocationQuery.builder()
						.bpartnerId(BPartnerId.ofRepoId(getC_BPartner_Effective_ID(olCandRecord)))
						.type(type)
						.applyTypeStrictly(false)
						.build());
		if (bpartnerLocationId == null)
		{
			throw new AdempiereException("Given olCandRecord has no C_BP_Location_Override_ID nor C_BPartner_Location_ID and the effective C_BPartner also has no C_BPartner_Location")
					.appendParametersToMessage()
					.setParameter("olCandRecord", olCandRecord);
		}
		return bpartnerLocationId.getRepoId();
	}

	@Override
	public BPartnerContactId getContactEffectiveId(@NonNull final I_C_OLCand olCand)
	{
		return BPartnerContactId.ofRepoIdOrNull(
				getC_BPartner_Effective_ID(olCand),
				getAD_User_Effective_ID(olCand));
	}

	@Override
	public BPartnerId getBillBPartnerEffectiveId(@NonNull final I_C_OLCand olCand)
	{
		return BPartnerId.ofRepoIdOrNull(getBill_BPartner_Effective_ID(olCand));
	}

	@Override
	public BPartnerLocationId getBillLocationEffectiveId(@NonNull final I_C_OLCand olCand)
	{
		return BPartnerLocationId.ofRepoIdOrNull(
				getBill_BPartner_Effective_ID(olCand),
				getBill_Location_Effective_ID(olCand));
	}

	@Override
	public BPartnerContactId getBillContactEffectiveId(@NonNull final I_C_OLCand olCand)
	{
		return BPartnerContactId.ofRepoIdOrNull(
				getBill_BPartner_Effective_ID(olCand),
				getBill_User_Effective_ID(olCand));
	}

	@Override
	public BPartnerId getDropShipBPartnerEffectiveId(@NonNull final I_C_OLCand olCand)
	{
		return BPartnerId.ofRepoIdOrNull(getDropShip_BPartner_Effective_ID(olCand));
	}

	@Override
	public BPartnerLocationId getDropShipLocationEffectiveId(@NonNull final I_C_OLCand olCand)
	{
		return BPartnerLocationId.ofRepoIdOrNull(
				getDropShip_BPartner_Effective_ID(olCand),
				getDropShip_Location_Effective_ID(olCand));
	}

	@Override
	public BPartnerContactId getDropShipContactEffectiveId(@NonNull final I_C_OLCand olCand)
	{
		return BPartnerContactId.ofRepoIdOrNull(
				getDropShip_BPartner_Effective_ID(olCand),
				getDropShip_User_Effective_ID(olCand));
	}

	@Override
	public BPartnerId getHandOverPartnerEffectiveId(@NonNull final I_C_OLCand olCand)
	{
		return BPartnerId.ofRepoIdOrNull(getHandOver_Partner_Effective_ID(olCand));
	}

	@Override
	public BPartnerLocationId getHandOverLocationEffectiveId(@NonNull final I_C_OLCand olCand)
	{
		return BPartnerLocationId.ofRepoIdOrNull(
				getHandOver_Partner_Effective_ID(olCand),
				getHandOver_Location_Effective_ID(olCand));
	}
}
