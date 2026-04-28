package de.metas.ui.web.bpartner.quickinput;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.quick_input.service.BPartnerLocationQuickInputService;
import de.metas.i18n.AdMessageKey;
import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.NewRecordDescriptor;
import de.metas.ui.web.window.descriptor.factory.NewRecordDescriptorsProvider;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.IDocumentFieldView;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_BPartner_Location_QuickInput;
import org.compiere.model.I_C_BPartner_QuickInput;
import org.compiere.model.I_C_Order;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
public class BPartnerLocationQuickInputConfiguration
{
	private static final AdMessageKey MSG_NO_BUSINESS_PARTNER_SELECTED = AdMessageKey.of("de.metas.ui.web.bpartner.quickinput.MSG_NO_BUSINESS_PARTNER_SELECTED");
	@NonNull private static final Logger logger = LogManager.getLogger(BPartnerLocationQuickInputConfiguration.class);
	@NonNull private final NewRecordDescriptorsProvider newRecordDescriptorsProvider;
	@NonNull private final DocumentCollection documentCollection;
	@NonNull private final BPartnerLocationQuickInputService bpartnerLocationQuickInputService;

	@PostConstruct
	public void postConstruct()
	{
		final AdWindowId quickInputAdWindowId = bpartnerLocationQuickInputService.getNewBPartnerLocationWindowId().orElse(null);
		if (quickInputAdWindowId != null)
		{
			newRecordDescriptorsProvider.addNewRecordDescriptor(
					NewRecordDescriptor.of(
							I_C_BPartner_Location.Table_Name,
							WindowId.of(quickInputAdWindowId),
							this::handleNewBPartnerLocationRequest
					)
			);
		}
		else
		{
			logger.warn("No window found for " + I_C_BPartner_QuickInput.Table_Name);
		}
	}

	private int handleNewBPartnerLocationRequest(final NewRecordDescriptor.ProcessNewRecordDocumentRequest request)
	{
		@NonNull final BPartnerId bpartnerId = getBPartnerId(request);

		final I_C_BPartner_Location_QuickInput template = InterfaceWrapperHelper.getPO(request.getDocument());
		updateTemplateForTriggeringField(template, request.getTriggeringField());
		final BPartnerLocationId bpLocationId = bpartnerLocationQuickInputService.createBPartnerLocationFromTemplate(template, bpartnerId);
		return bpLocationId.getRepoId();
	}

	private void updateTemplateForTriggeringField(@NonNull final I_C_BPartner_Location_QuickInput template, @NonNull final String triggeringField)
	{
		switch (triggeringField)
		{
			case I_C_Order.COLUMNNAME_C_BPartner_Location_ID:
			case I_C_Order.COLUMNNAME_DropShip_Location_ID:
				template.setIsShipTo(true);
				return;
			case I_C_Order.COLUMNNAME_Bill_Location_ID:
				template.setIsBillTo(true);
				return;
			case I_C_Order.COLUMNNAME_HandOver_Location_ID:
				template.setIsHandOverLocation(true);
		}
	}

	@NonNull
	private BPartnerId getBPartnerId(final NewRecordDescriptor.ProcessNewRecordDocumentRequest request)
	{
		if (request.getTriggeringDocumentPath() == null)
		{
			throw new AdempiereException("Unknown triggering path");
		}
		if (request.getTriggeringField() == null)
		{
			throw new AdempiereException("Unknown triggering field");
		}

		final String bpartnerFieldName = getBPartnerFieldName(request.getTriggeringField());
		final Document triggeringDocument = documentCollection.getDocumentReadonly(request.getTriggeringDocumentPath());

		// Try the specific BPartner field first (e.g., DropShip_BPartner_ID)
		BPartnerId bpartnerId = triggeringDocument.getFieldView(bpartnerFieldName)
				.getValueAsId(BPartnerId.class)
				.orElse(null);

		// Fallback to main C_BPartner_ID when the specific partner field is empty.
		// This covers DropShip_Location_ID, HandOver_Location_ID, and Bill_Location_ID —
		// all cases where the user triggers "New" on a location field without first setting
		// the corresponding BPartner field.
		// NOTE: C_BPartner_Location_ID maps directly to C_BPartner_ID, so this block is skipped for it.
		// NOTE: For Bill_Location_ID, the fallback assumes Bill_BPartner_ID == C_BPartner_ID.
		// If a separate billing partner is needed, the user must set Bill_BPartner_ID first.
		if (bpartnerId == null && !I_C_Order.COLUMNNAME_C_BPartner_ID.equals(bpartnerFieldName))
		{
			final IDocumentFieldView cbpField = triggeringDocument.getFieldViewOrNull(I_C_Order.COLUMNNAME_C_BPartner_ID);
			if (cbpField != null)
			{
				bpartnerId = cbpField.getValueAsId(BPartnerId.class).orElse(null);
			}
		}

		if (bpartnerId == null)
		{
			throw new AdempiereException(MSG_NO_BUSINESS_PARTNER_SELECTED);
		}
		return bpartnerId;
	}

	@NonNull
	private static String getBPartnerFieldName(@NonNull final String triggeringField)
	{
		switch (triggeringField)
		{
			case I_C_Order.COLUMNNAME_C_BPartner_Location_ID:
				return I_C_Order.COLUMNNAME_C_BPartner_ID;
			case I_C_Order.COLUMNNAME_Bill_Location_ID:
				return I_C_Order.COLUMNNAME_Bill_BPartner_ID;
			case I_C_Order.COLUMNNAME_HandOver_Location_ID:
				return I_C_Order.COLUMNNAME_HandOver_Partner_ID;
			case I_C_Order.COLUMNNAME_DropShip_Location_ID:
				return I_C_Order.COLUMNNAME_DropShip_BPartner_ID;
			default:
				throw new AdempiereException("Unknown triggering field: " + triggeringField);
		}
	}
}