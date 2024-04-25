package de.metas.ordercandidate.api.impl;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.BPartnerInfo;
import de.metas.bpartner.service.BPartnerInfo.BPartnerInfoBuilder;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerDAO.BPartnerLocationQuery;
import de.metas.bpartner.service.IBPartnerDAO.BPartnerLocationQuery.Type;
import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;

import static de.metas.common.util.CoalesceUtil.coalesceSuppliers;
import static de.metas.common.util.CoalesceUtil.firstGreaterThanZero;
import static org.adempiere.model.InterfaceWrapperHelper.load;

public class OLCandEffectiveValuesBL implements IOLCandEffectiveValuesBL
{
	private final transient IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final transient IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final transient IProductBL productBL = Services.get(IProductBL.class);

	@Override
	public I_C_BPartner getC_BPartner_Effective(@NonNull final I_C_OLCand olCand)
	{
		final BPartnerId bpartnerId = getBPartnerEffectiveId(olCand);
		return bpartnerId != null ? bPartnerDAO.getByIdInTrx(bpartnerId) : null;
	}

	@Override
	public I_C_BPartner_Location getC_BP_Location_Effective(final I_C_OLCand olCand)
	{
		final BPartnerLocationId bpLocationId = getLocationAndCaptureEffectiveId(olCand, Type.SHIP_TO).getBpartnerLocationId();
		return bPartnerDAO.getBPartnerLocationByIdInTrx(bpLocationId);
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

	@Override
	public <T extends I_C_BPartner> T getBill_BPartner_Effective(final I_C_OLCand olCand, final Class<T> clazz)
	{
		final BPartnerId billBPartnerId = getBillBPartnerEffectiveId(olCand);
		return billBPartnerId != null ? bPartnerDAO.getByIdInTrx(billBPartnerId, clazz) : null;
	}

	@Override
	public I_C_BPartner_Location getBill_Location_Effective(final I_C_OLCand olCand)
	{
		final BPartnerLocationId billLocationEffectiveId = getBillLocationEffectiveId(olCand);
		return bPartnerDAO.getBPartnerLocationByIdInTrx(billLocationEffectiveId);
	}

	private int getBill_User_Effective_ID(final I_C_OLCand olCand)
	{
		return olCand.getBill_User_ID() > 0
				? olCand.getBill_User_ID()
				: getAD_User_Effective_ID(olCand);
	}

	private BPartnerLocationAndCaptureId getDropShipLocationAndCaptureEffectiveId(@NonNull final I_C_OLCand olCand)
	{
		return coalesceSuppliers(
				() -> BPartnerLocationAndCaptureId.ofRepoIdOrNull(olCand.getDropShip_BPartner_Override_ID(), olCand.getDropShip_Location_Override_ID(), olCand.getDropShip_Location_Override_Value_ID()),
				() -> BPartnerLocationAndCaptureId.ofRepoIdOrNull(olCand.getDropShip_BPartner_ID(), olCand.getDropShip_Location_ID(), olCand.getDropShip_Location_Value_ID()),
				() -> getLocationAndCaptureEffectiveId(olCand, Type.SHIP_TO));
	}

	@Override
	public I_C_BPartner_Location getDropShip_Location_Effective(final I_C_OLCand olCand)
	{
		final BPartnerLocationId dropShipLocationEffectiveId = getDropShipLocationAndCaptureEffectiveId(olCand).getBpartnerLocationId();
		return bPartnerDAO.getBPartnerLocationByIdInTrx(dropShipLocationEffectiveId);
	}

	private int getDropShip_User_Effective_ID(@NonNull final I_C_OLCand olCand)
	{
		if (olCand.getDropShip_User_ID() > 0)
		{
			return olCand.getDropShip_User_ID();
		}
		return getAD_User_Effective_ID(olCand);
	}

	@Override
	@Nullable
	public ProductId getM_Product_Effective_ID(@NonNull final I_C_OLCand olCand)
	{
		final int productRepoId = CoalesceUtil.firstGreaterThanZero(olCand.getM_Product_Override_ID(), olCand.getM_Product_ID());
		return ProductId.ofRepoIdOrNull(productRepoId);
	}

	@Override
	public I_M_Product getM_Product_Effective(@NonNull final I_C_OLCand olCand)
	{
		final ProductId productId = getM_Product_Effective_ID(olCand);
		if (productId == null)
		{
			return null;
		}
		return load(productId, I_M_Product.class);
	}

	@Override
	public UomId getEffectiveUomId(@NonNull final I_C_OLCand olCandRecord)
	{
		return olCandRecord.isManualPrice()
				? getRecordOrStockUOMId(olCandRecord)
				: UomId.ofRepoIdOrNull(firstGreaterThanZero(
				olCandRecord.getC_UOM_Internal_ID(),
				olCandRecord.getC_UOM_ID()));
	}

	@Override
	@NonNull
	public UomId getRecordOrStockUOMId(@NonNull final I_C_OLCand olCandRecord)
	{
		if (olCandRecord.getC_UOM_ID() > 0)
		{
			return UomId.ofRepoId(olCandRecord.getC_UOM_ID());
		}

		// If the olCand did not come with a UOM, then we assume that the customer-ordered-quantity-amount is in the customer specific price-UOM rather than in the product's internal stock-UOM
		if (olCandRecord.getPrice_UOM_Internal_ID() > 0)
		{
			return UomId.ofRepoId(olCandRecord.getPrice_UOM_Internal_ID());
		}

		// only if we have nothing else to work with, we go with our internal stock-UOM
		return productBL.getStockUOMId(ProductId.ofRepoId(olCandRecord.getM_Product_ID()));
	}

	@NonNull
	@Override
	public Quantity getQtyItemCapacity_Effective(@NonNull final I_C_OLCand olCandRecord)
	{
		final BigDecimal result = olCandRecord.isManualQtyItemCapacity()
				? olCandRecord.getQtyItemCapacity()
				: olCandRecord.getQtyItemCapacityInternal();
		
		return Quantitys.of(result, ProductId.ofRepoId(olCandRecord.getM_Product_ID()));
	}

	@Override
	public I_C_UOM getC_UOM_Effective(final I_C_OLCand olCand)
	{
		final UomId effectiveUomId = getEffectiveUomId(olCand);
		if (effectiveUomId == null)
		{
			return null;
		}
		return uomDAO.getById(effectiveUomId);
	}

	@Override
	public I_C_BPartner_Location getHandOver_Location_Effective(final I_C_OLCand olCand)
	{
		final BPartnerLocationId handOverLocationEffectiveId = getHandOverLocationEffectiveId(olCand).getBpartnerLocationId();
		return bPartnerDAO.getBPartnerLocationByIdInTrx(handOverLocationEffectiveId);
	}

	private int getHandOver_User_Effective_ID(@NonNull final I_C_OLCand olCandRecord)
	{
		if (olCandRecord.getHandOver_User_ID() > 0)
		{
			return olCandRecord.getHandOver_User_ID();
		}
		return getAD_User_Effective_ID(olCandRecord);
	}

	@Override
	@Nullable
	public BPartnerId getBPartnerEffectiveId(@NonNull final I_C_OLCand olCandRecord)
	{
		return coalesceSuppliers(
				() -> BPartnerId.ofRepoIdOrNull(olCandRecord.getC_BPartner_Override_ID()),
				() -> BPartnerId.ofRepoIdOrNull(olCandRecord.getC_BPartner_ID()));
	}

	@Override
	public BPartnerLocationId getLocationEffectiveId(@NonNull final I_C_OLCand olCand)
	{
		return getLocationAndCaptureEffectiveId(olCand, Type.SHIP_TO).getBpartnerLocationId();
	}

	/**
	 * @param fallbackToType if falling back to the {@code C_BPartner_Location} masterdata, then prefer this type.
	 */
	private BPartnerLocationAndCaptureId getLocationAndCaptureEffectiveId(@NonNull final I_C_OLCand olCandRecord, @NonNull final Type fallbackToType)
	{
		final BPartnerLocationAndCaptureId bpLocationOverrideId = BPartnerLocationAndCaptureId.ofRepoIdOrNull(olCandRecord.getC_BPartner_Override_ID(), olCandRecord.getC_BP_Location_Override_ID(), olCandRecord.getC_BP_Location_Override_Value_ID());
		if (bpLocationOverrideId != null)
		{
			return bpLocationOverrideId;
		}

		final BPartnerLocationAndCaptureId bpLocationId = BPartnerLocationAndCaptureId.ofRepoIdOrNull(olCandRecord.getC_BPartner_ID(), olCandRecord.getC_BPartner_Location_ID(), olCandRecord.getC_BPartner_Location_Value_ID());
		if (bpLocationId != null)
		{
			return bpLocationId;
		}

		final BPartnerId bpartnerId = getBPartnerEffectiveId(olCandRecord);
		assert bpartnerId != null;

		final BPartnerLocationId fallbackBPLocationId = bPartnerDAO.retrieveBPartnerLocationId(
				BPartnerLocationQuery.builder()
						.bpartnerId(bpartnerId)
						.type(fallbackToType)
						.applyTypeStrictly(false)
						.build());
		if (fallbackBPLocationId == null)
		{
			throw new AdempiereException("Given olCandRecord has no C_BP_Location_Override_ID nor C_BPartner_Location_ID and the effective C_BPartner also has no C_BPartner_Location")
					.appendParametersToMessage()
					.setParameter("olCandRecord", olCandRecord);
		}

		final I_C_BPartner_Location fallbackBPLocation = bPartnerDAO.getBPartnerLocationByIdEvenInactive(fallbackBPLocationId);
		assert fallbackBPLocation != null;
		return BPartnerLocationAndCaptureId.ofRepoId(fallbackBPLocation.getC_BPartner_ID(), fallbackBPLocation.getC_BPartner_Location_ID(), fallbackBPLocation.getC_Location_ID());
	}

	@Override
	public BPartnerContactId getContactEffectiveId(@NonNull final I_C_OLCand olCand)
	{
		return BPartnerContactId.ofRepoIdOrNull(
				getBPartnerEffectiveId(olCand),
				getAD_User_Effective_ID(olCand));
	}

	@Override
	public BPartnerId getBillBPartnerEffectiveId(@NonNull final I_C_OLCand olCand)
	{
		return coalesceSuppliers(
				() -> BPartnerId.ofRepoIdOrNull(olCand.getBill_BPartner_ID()),
				() -> getBPartnerEffectiveId(olCand));
	}

	@Override
	public BPartnerLocationId getBillLocationEffectiveId(@NonNull final I_C_OLCand olCand)
	{
		return getBillLocationAndCaptureEffectiveId(olCand).getBpartnerLocationId();
	}

	@Override
	public BPartnerLocationAndCaptureId getBillLocationAndCaptureEffectiveId(final @NonNull I_C_OLCand olCand)
	{
		return coalesceSuppliers(
				() -> BPartnerLocationAndCaptureId.ofRepoIdOrNull(olCand.getBill_BPartner_ID(), olCand.getBill_Location_ID(), olCand.getBill_Location_Value_ID()),
				() -> getLocationAndCaptureEffectiveId(olCand, Type.BILL_TO));
	}

	@Override
	public BPartnerContactId getBillContactEffectiveId(@NonNull final I_C_OLCand olCand)
	{
		return BPartnerContactId.ofRepoIdOrNull(
				getBillBPartnerEffectiveId(olCand),
				getBill_User_Effective_ID(olCand));
	}

	@Override
	public BPartnerInfo getBuyerPartnerInfo(@NonNull final I_C_OLCand olCandRecord)
	{
		final BPartnerLocationAndCaptureId bpLocationId = getLocationAndCaptureEffectiveId(olCandRecord, Type.SHIP_TO);
		final BPartnerId bpartnerId = bpLocationId.getBpartnerId();
		final int contactId = getAD_User_Effective_ID(olCandRecord);

		return BPartnerInfo.builder()
				.bpartnerId(bpartnerId)
				.bpartnerLocationId(bpLocationId.getBpartnerLocationId())
				.locationId(bpLocationId.getLocationCaptureId())
				.contactId(BPartnerContactId.ofRepoIdOrNull(bpartnerId, contactId))
				.build();
	}

	@Override
	public BPartnerInfo getBillToPartnerInfo(@NonNull final I_C_OLCand olCandRecord)
	{
		final BPartnerContactId contactId = getBillContactEffectiveId(olCandRecord);
		final BPartnerLocationAndCaptureId bpLocationId = getBillLocationAndCaptureEffectiveId(olCandRecord);
		if (bpLocationId != null)
		{
			return BPartnerInfo.ofLocationAndContact(bpLocationId, contactId);
		}
		else
		{
			return BPartnerInfo.builder()
					.bpartnerId(getBPartnerEffectiveId(olCandRecord))
					.contactId(contactId)
					.build();
		}
	}

	@Override
	public Optional<BPartnerInfo> getDropShipPartnerInfo(@NonNull final I_C_OLCand olCandRecord)
	{
		final BPartnerLocationAndCaptureId dropShipLocationId = getDropShipLocationAndCaptureEffectiveId(olCandRecord);
		final int dropShipContactId = getDropShip_User_Effective_ID(olCandRecord); // TODO add the column & stuff

		return extractDifferentShipToBPartnerInfo(olCandRecord, dropShipLocationId, dropShipContactId);
	}

	@Override
	public Optional<BPartnerInfo> getHandOverPartnerInfo(@NonNull final I_C_OLCand olCandRecord)
	{
		final BPartnerLocationAndCaptureId dropShipLocationId = getHandOverLocationEffectiveId(olCandRecord);
		final int dropShipContactId = getHandOver_User_Effective_ID(olCandRecord); // TODO add the column & stuff

		return extractDifferentShipToBPartnerInfo(olCandRecord, dropShipLocationId, dropShipContactId);
	}

	@Nullable
	@Override
	public HUPIItemProductId getEffectivePackingInstructions(@NonNull final I_C_OLCand olCand)
	{
		if (olCand.getM_HU_PI_Item_Product_Override_ID() > 0)
		{
			return HUPIItemProductId.ofRepoId(olCand.getM_HU_PI_Item_Product_Override_ID());
		}
		return HUPIItemProductId.ofRepoIdOrNull(olCand.getM_HU_PI_Item_Product_ID());
	}

	private Optional<BPartnerInfo> extractDifferentShipToBPartnerInfo(
			@NonNull final I_C_OLCand olCandRecord,
			final BPartnerLocationAndCaptureId differentShipToBPartnerLocationAndCaptureId,
			final int differentShipToContactId)
	{
		final BPartnerLocationAndCaptureId bpLocationAndCaptureId = getLocationAndCaptureEffectiveId(olCandRecord, Type.SHIP_TO);
		final int contactId = getAD_User_Effective_ID(olCandRecord);

		if (BPartnerLocationAndCaptureId.equals(bpLocationAndCaptureId, differentShipToBPartnerLocationAndCaptureId)
				&& differentShipToContactId == contactId)
		{
			return Optional.empty();
		}

		final BPartnerInfoBuilder result = BPartnerInfo.builder();
		if (!BPartnerId.equals(differentShipToBPartnerLocationAndCaptureId.getBpartnerId(), bpLocationAndCaptureId.getBpartnerId()))
		{
			result.bpartnerId(differentShipToBPartnerLocationAndCaptureId.getBpartnerId());
			if (!BPartnerLocationAndCaptureId.equals(bpLocationAndCaptureId, differentShipToBPartnerLocationAndCaptureId))
			{
				result.bpartnerLocationId(differentShipToBPartnerLocationAndCaptureId.getBpartnerLocationId());
				result.locationId(differentShipToBPartnerLocationAndCaptureId.getLocationCaptureId());
			}
			else // no dropShipLocationId was specified; select one of the dropship-partner's locations from the master data
			{
				final BPartnerLocationQuery query = BPartnerLocationQuery.builder()
						.bpartnerId(differentShipToBPartnerLocationAndCaptureId.getBpartnerId())
						.type(Type.SHIP_TO)
						.applyTypeStrictly(false)
						.build();
				final BPartnerLocationId bpartnerLocationId = bPartnerDAO.retrieveBPartnerLocationId(query);
				if (bpartnerLocationId == null)
				{
					throw new AdempiereException("DropShip-BPartner needs to have a bpartner-location")
							.appendParametersToMessage()
							.setParameter("DropShipBPartnerId", differentShipToBPartnerLocationAndCaptureId.getBpartnerId())
							.setParameter("C_OLCand", olCandRecord);
				}
				result.bpartnerLocationId(bpartnerLocationId);
			}
			if (differentShipToContactId != contactId)
			{
				result.contactId(BPartnerContactId.ofRepoIdOrNull(differentShipToBPartnerLocationAndCaptureId.getBpartnerId(), differentShipToContactId));
			} // note: if no dropship contact was specified, then we leave it like that

		}
		else // dropShipBPartnerId == bPartnerId
		{
			result.bpartnerId(differentShipToBPartnerLocationAndCaptureId.getBpartnerId());
			if (!BPartnerLocationAndCaptureId.equals(bpLocationAndCaptureId, differentShipToBPartnerLocationAndCaptureId))
			{
				result.bpartnerLocationId(differentShipToBPartnerLocationAndCaptureId.getBpartnerLocationId());
				result.locationId(differentShipToBPartnerLocationAndCaptureId.getLocationCaptureId());
			}
			if (differentShipToContactId != contactId)
			{
				result.contactId(BPartnerContactId.ofRepoIdOrNull(differentShipToBPartnerLocationAndCaptureId.getBpartnerId(), differentShipToContactId));
			}
		}
		return Optional.of(result.build());
	}

	private BPartnerLocationAndCaptureId getHandOverLocationEffectiveId(@NonNull final I_C_OLCand olCand)
	{
		return coalesceSuppliers(
				() -> BPartnerLocationAndCaptureId.ofRepoIdOrNull(olCand.getHandOver_Partner_Override_ID(), olCand.getHandOver_Location_Override_ID(), olCand.getHandOver_Location_Override_Value_ID()),
				() -> BPartnerLocationAndCaptureId.ofRepoIdOrNull(olCand.getHandOver_Partner_ID(), olCand.getHandOver_Location_ID(), olCand.getHandOver_Location_Value_ID()),
				() -> getLocationAndCaptureEffectiveId(olCand, Type.SHIP_TO));
	}
}
