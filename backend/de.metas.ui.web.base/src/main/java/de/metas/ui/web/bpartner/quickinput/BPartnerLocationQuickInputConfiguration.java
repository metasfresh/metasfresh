package de.metas.ui.web.bpartner.quickinput;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.quick_input.service.BPartnerLocationQuickInputService;
import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.NewRecordDescriptor;
import de.metas.ui.web.window.descriptor.factory.NewRecordDescriptorsProvider;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentCollection;
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
		final BPartnerLocationId bpLocationId = bpartnerLocationQuickInputService.createBPartnerLocationFromTemplate(template, bpartnerId);
		return bpLocationId.getRepoId();
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
		return triggeringDocument.getFieldView(bpartnerFieldName)
				.getValueAsId(BPartnerId.class)
				.orElseThrow(() -> new AdempiereException("No bpartner ID found"));
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