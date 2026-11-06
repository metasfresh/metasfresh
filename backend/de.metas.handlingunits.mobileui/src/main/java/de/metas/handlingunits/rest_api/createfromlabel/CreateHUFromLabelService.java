package de.metas.handlingunits.rest_api.createfromlabel;

import de.metas.handlingunits.impl.HUQtyService;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.springframework.stereotype.Service;

/** HU Manager "create HU from a scanned label": the preview (writes nothing) and OK (one single-HU inventory, one CU). */
@Service
@RequiredArgsConstructor
public class CreateHUFromLabelService
{
	// services of the commands (resolved once here and handed to each command)
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	@NonNull private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	@NonNull private final HUQtyService huQtyService;
	@NonNull private final LabelValuesService labelValuesService;
	@NonNull private final LabelTargetLocatorResolver labelTargetLocatorResolver;

	/** @return the command with the services set; the caller sets the scanned code, client, org, user and language, then {@code build().execute()} */
	@NonNull
	public ProposeHUFromScannedCodeCommand.ProposeHUFromScannedCodeCommandBuilder propose()
	{
		return ProposeHUFromScannedCodeCommand.builder()
				.attributeDAO(attributeDAO)
				.warehouseDAO(warehouseDAO)
				.uomDAO(uomDAO)
				.labelValuesService(labelValuesService)
				.labelTargetLocatorResolver(labelTargetLocatorResolver);
	}

	/** @return the command with the services set; the caller sets the scanned code, qty, locator, client, language and date, then {@code build().execute()} (which returns the new HU with its HU QR code, created if missing, so the standard actions, Move etc., work on it) */
	@NonNull
	public CreateHUFromScannedCodeCommand.CreateHUFromScannedCodeCommandBuilder create()
	{
		return CreateHUFromScannedCodeCommand.builder()
				.trxManager(trxManager)
				.warehouseDAO(warehouseDAO)
				.attributeDAO(attributeDAO)
				.huQtyService(huQtyService)
				.labelValuesService(labelValuesService);
	}
}
