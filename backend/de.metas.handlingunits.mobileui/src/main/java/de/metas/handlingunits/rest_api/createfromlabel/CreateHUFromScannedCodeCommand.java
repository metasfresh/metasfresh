package de.metas.handlingunits.rest_api.createfromlabel;

import de.metas.handlingunits.CreateHUFromInventoryRequest;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.impl.HUQtyService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.picking.candidate.commands.PackedHUWeightNetUpdater;
import de.metas.i18n.TranslatableStrings;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.scannable_code.ScannedCode;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.X12DE355;
import lombok.Builder;
import lombok.NonNull;
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
 * Must run in a transaction (the caller's): the weight is set on the completed CU, and all of it commits or none of it.
 */
public class CreateHUFromScannedCodeCommand
{
	// services
	@NonNull private final IHandlingUnitsBL handlingUnitsBL;
	@NonNull private final IUOMConversionBL uomConversionBL;
	@NonNull private final IWarehouseDAO warehouseDAO;
	@NonNull private final IAttributeDAO attributeDAO;
	@NonNull private final HUQtyService huQtyService;
	@NonNull private final HUQRCodesService huQRCodesService;
	@NonNull private final LabelProductResolver labelProductResolver;
	@NonNull private final LabelQtyAndWeightService labelQtyAndWeightService;

	// params
	@NonNull private final ClientId clientId;
	@NonNull private final String adLanguage;
	@NonNull private final ScannedCode scannedCode;
	@NonNull private final BigDecimal qty;
	@NonNull private final LocatorId locatorId;
	@NonNull private final ZonedDateTime date;

	@Builder
	private CreateHUFromScannedCodeCommand(
			@NonNull final IHandlingUnitsBL handlingUnitsBL,
			@NonNull final IUOMConversionBL uomConversionBL,
			@NonNull final IWarehouseDAO warehouseDAO,
			@NonNull final IAttributeDAO attributeDAO,
			@NonNull final HUQtyService huQtyService,
			@NonNull final HUQRCodesService huQRCodesService,
			@NonNull final LabelProductResolver labelProductResolver,
			@NonNull final LabelQtyAndWeightService labelQtyAndWeightService,
			@NonNull final ClientId clientId,
			@NonNull final String adLanguage,
			@NonNull final ScannedCode scannedCode,
			@NonNull final BigDecimal qty,
			@NonNull final LocatorId locatorId,
			@NonNull final ZonedDateTime date)
	{
		this.handlingUnitsBL = handlingUnitsBL;
		this.uomConversionBL = uomConversionBL;
		this.warehouseDAO = warehouseDAO;
		this.attributeDAO = attributeDAO;
		this.huQtyService = huQtyService;
		this.huQRCodesService = huQRCodesService;
		this.labelProductResolver = labelProductResolver;
		this.labelQtyAndWeightService = labelQtyAndWeightService;
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
		if (qty.signum() <= 0)
		{
			throw new AdempiereException(CreateFromLabelMessages.QTY_MUST_BE_POSITIVE, qty);
		}
		assertActiveLocatorOfClient();

		final LabelProduct labelProduct = labelProductResolver.resolve(scannedCode, clientId);
		final ProductId productId = labelProduct.getProductId();
		final LabelValues labelValues = LabelValues.of(labelProduct.getCode());
		final LabelQtyAndWeight qtyAndWeight = labelQtyAndWeightService.compute(productId, labelValues.getWeightKg());

		final HuId huId = huQtyService.createHUFromInventory(CreateHUFromInventoryRequest.builder()
				.date(date)
				.productId(productId)
				.qty(Quantitys.of(qty, qtyAndWeight.getStockUomId()))
				.locatorId(locatorId)
				.attributes(toInventoryAttributes(labelValues))
				// origin marker on the inventory document, in the language of the creating user; "Change Qty" books the same inventory sub-type
				.description(TranslatableStrings.adMessage(CreateFromLabelMessages.INVENTORY_DESCRIPTION).translate(adLanguage))
				.build());

		// the weight cannot go through the inventory (its attribute key drops weight attributes): set it on the completed CU
		if (qtyAndWeight.getWeightKgForCU() != null)
		{
			setWeight(handlingUnitsBL.getById(huId), productId, qtyAndWeight);
		}
		final HUQRCode qrCode = huQRCodesService.getQRCodeByHuId(huId); // creates the QR code if missing, like the HU lookup by value does
		return new CreatedHU(huId, qrCode);
	}

	/** the location comes from the client (proposed or scanned): it must be an active location of the client, and the create step never makes one */
	private void assertActiveLocatorOfClient()
	{
		final I_M_Locator locator = warehouseDAO.getLocatorById(locatorId);
		if (locator == null || !locator.isActive() || locator.getAD_Client_ID() != clientId.getRepoId())
		{
			throw new AdempiereException(CreateFromLabelMessages.NOT_AN_ACTIVE_LOCATOR, locatorId.getRepoId());
		}
	}

	/**
	 * The lot, best-before date and production date of the label as the inventory line's attributes: the inventory document and the
	 * stock carry them, and the new CU inherits them when the inventory completes. Same route as "Change Qty" of an HU without an HU.
	 * An attribute this installation does not define is skipped, like in the preview.
	 */
	private ImmutableAttributeSet toInventoryAttributes(@NonNull final LabelValues labelValues)
	{
		final ImmutableAttributeSet.Builder result = ImmutableAttributeSet.builder();
		labelValues.getAttributeValuesDefinedHere(attributeDAO).forEach((attributeCode, value) -> result.attributeValue(attributeCode, value));
		return result.build();
	}

	private void setWeight(@NonNull final I_M_HU cu, @NonNull final ProductId productId, @NonNull final LabelQtyAndWeight qtyAndWeight)
	{
		final BigDecimal weightKg = qtyAndWeight.getWeightKgForCU();
		if (weightKg == null)
		{
			return;
		}

		final Quantity weight = Quantitys.of(weightKg, X12DE355.KILOGRAM);
		new PackedHUWeightNetUpdater(uomConversionBL, handlingUnitsBL.createMutableHUContextForProcessing(), productId, weight)
				.updatePackToHU(cu);
	}
}
