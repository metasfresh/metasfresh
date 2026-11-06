package de.metas.handlingunits.rest_api.createfromlabel;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.rest_api.JsonHUAttributeConverters;
import de.metas.handlingunits.rest_api.createfromlabel.JsonHUProposal.JsonAttribute;
import de.metas.handlingunits.rest_api.createfromlabel.JsonHUProposal.JsonProduct;
import de.metas.handlingunits.rest_api.createfromlabel.JsonHUProposal.JsonRejection;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.scannable_code.ScannedCode;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.qrcode.LocatorQRCode;

import static de.metas.handlingunits.rest_api.createfromlabel.CreateFromLabelMessages.WEIGHT_NOT_USED;

/**
 * The proposal for the HU Manager "New HU" preview: what the scanned product label would create. Reads only, writes nothing
 * (the preview can be cancelled and every repeat scan proposes again).
 * <p>
 * Product, label values, quantity and weight: see {@link LabelValuesService}.
 */
public class ProposeHUFromScannedCodeCommand
{
	// services
	@NonNull private final IAttributeDAO attributeDAO;
	@NonNull private final IWarehouseDAO warehouseDAO;
	@NonNull private final LabelValuesService labelValuesService;
	@NonNull private final LabelTargetLocatorResolver labelTargetLocatorResolver;

	// params
	@NonNull private final ClientId clientId;
	@NonNull private final UserId userId;
	@NonNull private final OrgId orgId;
	@NonNull private final ScannedCode scannedCode;
	@NonNull private final String adLanguage;

	@Builder
	private ProposeHUFromScannedCodeCommand(
			@NonNull final IAttributeDAO attributeDAO,
			@NonNull final IWarehouseDAO warehouseDAO,
			@NonNull final LabelValuesService labelValuesService,
			@NonNull final LabelTargetLocatorResolver labelTargetLocatorResolver,
			@NonNull final ClientId clientId,
			@NonNull final UserId userId,
			@NonNull final OrgId orgId,
			@NonNull final ScannedCode scannedCode,
			@NonNull final String adLanguage)
	{
		this.attributeDAO = attributeDAO;
		this.warehouseDAO = warehouseDAO;
		this.labelValuesService = labelValuesService;
		this.labelTargetLocatorResolver = labelTargetLocatorResolver;
		this.clientId = clientId;
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
		final LabelValues labelValues = labelValuesService.resolve(scannedCode, clientId);

		final LocatorQRCode locatorQRCode = labelTargetLocatorResolver.resolve(userId, orgId, clientId)
				.map(locatorId -> LocatorQRCode.ofLocator(warehouseDAO.getLocatorById(locatorId)))
				.orElse(null);

		return JsonHUProposal.builder()
				.product(JsonProduct.builder()
						.productId(labelValues.getProductId().getRepoId())
						.value(labelValues.getProductValue())
						.name(labelValues.getProductName())
						.build())
				.qty(labelValues.getQty() != null ? labelValues.getQty().toBigDecimal() : null)
				.uom(labelValues.getStockUom().getCode())
				.attributes(toJsonAttributes(labelValues))
				.weightKg(labelValues.getWeightKgForCU() != null ? labelValues.getWeightKgForCU().toBigDecimal() : null)
				.locatorQRCode(locatorQRCode != null ? locatorQRCode.toScannedCode() : null)
				.locatorCaption(locatorQRCode != null ? locatorQRCode.getCaption() : null)
				.weightNote(labelValues.isWeightIgnored() ? TranslatableStrings.adMessage(WEIGHT_NOT_USED).translate(adLanguage) : null)
				.build();
	}

	private ImmutableList<JsonAttribute> toJsonAttributes(@NonNull final LabelValues labelValues)
	{
		final ImmutableList.Builder<JsonAttribute> result = ImmutableList.builder();
		// only the active attributes: the same ones the booking stores
		labelValues.getAttributeValues(attributeDAO::isActive).forEach((attributeCode, value) -> {
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
				// AdempiereException.getErrorCode() is @Nullable; the rejection always has its message key
				.errorCode(rejection.getErrorCode() != null ? rejection.getErrorCode() : rejection.getMessageKey().toAD_Message())
				.message(TranslatableStrings.adMessage(rejection.getMessageKey(), rejection.getMessageParams()).translate(adLanguage))
				.build();
	}
}
