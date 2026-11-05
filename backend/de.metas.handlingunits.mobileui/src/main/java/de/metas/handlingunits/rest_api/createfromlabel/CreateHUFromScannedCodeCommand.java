package de.metas.handlingunits.rest_api.createfromlabel;

import de.metas.handlingunits.CreateHUFromInventoryRequest;
import de.metas.handlingunits.CreatedHU;
import de.metas.handlingunits.impl.HUQtyService;
import de.metas.i18n.TranslatableStrings;
import de.metas.quantity.Quantitys;
import de.metas.scannable_code.ScannedCode;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Locator;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * OK on the HU Manager "New HU" preview: re-reads the scanned label on the server and books ONE single-HU inventory that creates ONE CU.
 * <p>
 * The client supplies only the two values the user may edit: the quantity and the location. The product, the label values and the
 * weight rule always come from the re-parse of the scanned code (never trusted from the client); a re-parse that yields several
 * products is refused, e.g. when a second product got the GTIN between the preview and OK.
 * <p>
 * {@link #execute()} runs in the thread's transaction (a new one if none is running): all of it commits or none of it.
 */
public class CreateHUFromScannedCodeCommand
{
	// services
	@NonNull private final ITrxManager trxManager;
	@NonNull private final IWarehouseDAO warehouseDAO;
	@NonNull private final IAttributeDAO attributeDAO;
	@NonNull private final HUQtyService huQtyService;
	@NonNull private final LabelValuesService labelValuesService;

	// params
	@NonNull private final ClientId clientId;
	@NonNull private final String adLanguage;
	@NonNull private final ScannedCode scannedCode;
	@NonNull private final BigDecimal qty;
	@NonNull private final LocatorId locatorId;
	@NonNull private final ZonedDateTime date;

	@Builder
	private CreateHUFromScannedCodeCommand(
			@NonNull final ITrxManager trxManager,
			@NonNull final IWarehouseDAO warehouseDAO,
			@NonNull final IAttributeDAO attributeDAO,
			@NonNull final HUQtyService huQtyService,
			@NonNull final LabelValuesService labelValuesService,
			@NonNull final ClientId clientId,
			@NonNull final String adLanguage,
			@NonNull final ScannedCode scannedCode,
			@NonNull final BigDecimal qty,
			@NonNull final LocatorId locatorId,
			@NonNull final ZonedDateTime date)
	{
		this.trxManager = trxManager;
		this.warehouseDAO = warehouseDAO;
		this.attributeDAO = attributeDAO;
		this.huQtyService = huQtyService;
		this.labelValuesService = labelValuesService;
		this.clientId = clientId;
		this.adLanguage = adLanguage;
		this.scannedCode = scannedCode;
		this.qty = qty;
		this.locatorId = locatorId;
		this.date = date;
	}

	@NonNull
	public CreatedHU execute()
	{
		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	@NonNull
	private CreatedHU executeInTrx()
	{
		if (qty.signum() <= 0)
		{
			throw new AdempiereException(CreateFromLabelMessages.QTY_MUST_BE_POSITIVE, qty);
		}
		final LocatorId verifiedLocatorId = getVerifiedLocatorId();

		final LabelValues labelValues = labelValuesService.resolve(scannedCode, clientId);

		return huQtyService.createHUFromInventory(CreateHUFromInventoryRequest.builder()
				.date(date)
				.productId(labelValues.getProductId())
				.qty(Quantitys.of(qty, labelValues.getStockUomId()))
				.locatorId(verifiedLocatorId)
				.attributes(toInventoryAttributes(labelValues))
				// origin marker on the inventory document, in the language of the creating user; "Change Qty" books the same inventory sub-type
				.description(TranslatableStrings.adMessage(CreateFromLabelMessages.INVENTORY_DESCRIPTION).translate(adLanguage))
				.weightInKg(labelValues.getWeightKgForCU())
				// picking finds the booked HU by this label string (HU_QRCode attribute), as the manufacturing receipt stores it
				.huQRCodeAttributeValue(scannedCode)
				.build());
	}

	/**
	 * the location comes from the client (proposed or scanned): it must be an active location of the client, and the warehouse that came
	 * with it must be the warehouse of the stored location. The create step never makes a location, and books with the stored id pair only.
	 */
	@NonNull
	private LocatorId getVerifiedLocatorId()
	{
		final I_M_Locator locator = warehouseDAO.getLocatorById(locatorId);
		if (!LabelLocatorRules.isUsable(locator, locatorId, clientId))
		{
			throw new AdempiereException(CreateFromLabelMessages.NOT_AN_ACTIVE_LOCATOR, locatorId.getRepoId());
		}
		return LocatorId.ofRecord(locator);
	}

	/**
	 * The lot, best-before date and production date of the label as the inventory line's attributes: the inventory document and the
	 * stock carry them, and the new CU inherits them when the inventory completes. Same route as "Change Qty" of an HU without an HU.
	 * An attribute that is not active here is skipped, like in the preview.
	 */
	private ImmutableAttributeSet toInventoryAttributes(@NonNull final LabelValues labelValues)
	{
		final ImmutableAttributeSet.Builder result = ImmutableAttributeSet.builder();
		labelValues.getAttributeValues(attributeDAO::isActive).forEach(result::attributeValue);
		return result.build();
	}
}
