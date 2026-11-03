package de.metas.handlingunits.rest_api.createfromlabel;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.rest_api.JsonHUAttributeConverters;
import de.metas.handlingunits.rest_api.createfromlabel.JsonHUProposal.JsonAttribute;
import de.metas.handlingunits.rest_api.createfromlabel.JsonHUProposal.JsonProduct;
import de.metas.handlingunits.rest_api.createfromlabel.JsonHUProposal.JsonRejection;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.scannable_code.ScannedCode;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.compiere.model.I_M_Product;

import static de.metas.handlingunits.rest_api.createfromlabel.CreateFromLabelMessages.WEIGHT_NOT_USED;

/**
 * The proposal for the HU Manager "New HU" preview: what the scanned product label would create. Reads only, writes nothing
 * (the preview can be cancelled and every repeat scan proposes again).
 * <p>
 * Quantity and weight (a design default): the preview proposes 1 in the product's stock unit; a product stocked in kg
 * takes the label weight as the quantity; a catch-weight product keeps quantity 1 and the weight becomes the CU's net weight; for any
 * other product the label weight is not used and the preview says so.
 */
public class ProposeHUFromScannedCodeCommand
{
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

	@NonNull private final LabelProductResolver labelProductResolver;
	@NonNull private final LabelTargetLocatorResolver labelTargetLocatorResolver;
	@NonNull private final UserId userId;
	@NonNull private final OrgId orgId;
	@NonNull private final ScannedCode scannedCode;
	@NonNull private final String adLanguage;

	@Builder
	private ProposeHUFromScannedCodeCommand(
			@NonNull final HUQRCodesService huQRCodesService,
			@NonNull final LabelTargetLocatorResolver labelTargetLocatorResolver,
			@NonNull final ClientId clientId,
			@NonNull final UserId userId,
			@NonNull final OrgId orgId,
			@NonNull final ScannedCode scannedCode,
			@NonNull final String adLanguage)
	{
		this.labelProductResolver = LabelProductResolver.builder().huQRCodesService(huQRCodesService).clientId(clientId).build();
		this.labelTargetLocatorResolver = labelTargetLocatorResolver;
		this.userId = userId;
		this.orgId = orgId;
		this.scannedCode = scannedCode;
		this.adLanguage = adLanguage;
	}

	public JsonHUProposal execute()
	{
		try
		{
			return propose();
		}
		catch (final LabelRejectionException rejection)
		{
			return JsonHUProposal.ofRejection(toJsonRejection(rejection));
		}
	}

	private JsonHUProposal propose()
	{
		final LabelProduct labelProduct = labelProductResolver.resolve(scannedCode);
		final ProductId productId = labelProduct.getProductId();
		final LabelValues labelValues = LabelValues.of(labelProduct.getCode());

		final I_M_Product product = productBL.getById(productId);
		final LabelQtyAndWeight qtyAndWeight = LabelQtyAndWeight.of(productId, labelValues.getWeightKg());

		final LocatorQRCode locatorQRCode = labelTargetLocatorResolver.resolve(userId, orgId)
				.map(locatorId -> LocatorQRCode.ofLocator(warehouseDAO.getLocatorById(locatorId)))
				.orElse(null);

		return JsonHUProposal.builder()
				.product(JsonProduct.builder()
						.productId(productId.getRepoId())
						.value(product.getValue())
						.name(product.getName())
						.build())
				.qty(qtyAndWeight.getQty())
				.uom(qtyAndWeight.getStockUom().getCode())
				.attributes(toJsonAttributes(labelValues))
				.weightKg(qtyAndWeight.getWeightKgForCU())
				.locatorQRCode(locatorQRCode != null ? locatorQRCode.toGlobalQRCodeJsonString() : null)
				.locatorCaption(locatorQRCode != null ? locatorQRCode.getCaption() : null)
				.weightNote(qtyAndWeight.isWeightNotUsed() ? TranslatableStrings.adMessage(WEIGHT_NOT_USED).translate(adLanguage) : null)
				.build();
	}

	private ImmutableList<JsonAttribute> toJsonAttributes(@NonNull final LabelValues labelValues)
	{
		final ImmutableList.Builder<JsonAttribute> result = ImmutableList.builder();
		// only attributes this installation defines: the same ones the booking stores
		labelValues.getAttributeValuesDefinedHere().forEach((attributeCode, value) -> {
			final ITranslatableString caption = attributeDAO.getAttributeDisplayNameByValue(attributeCode)
					.orElseThrow(() -> new AdempiereException("Attribute not found: " + attributeCode));

			result.add(JsonAttribute.builder()
					.code(attributeCode.getCode())
					.caption(caption.translate(adLanguage))
					.valueDisplay(String.valueOf(JsonHUAttributeConverters.toDisplayValue(value, adLanguage)))
					.build());
		});
		return result.build();
	}

	private JsonRejection toJsonRejection(@NonNull final LabelRejectionException rejection)
	{
		return JsonRejection.builder()
				.errorCode(rejection.getErrorCode())
				.message(TranslatableStrings.adMessage(rejection.getMessageKey(), rejection.getMessageParams()).translate(adLanguage))
				.build();
	}
}
