package de.metas.handlingunits.report.labels;

import de.metas.bpartner.BPartnerId;
import de.metas.i18n.ExplainedOptional;
import de.metas.logging.LogManager;
import de.metas.process.AdProcessId;
import de.metas.report.PrintCopies;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Properties;

/**
 * Legacy HULabelConfig provider based on AD_SysConfig(s)
 */
class HULabelConfigFromSysConfigProvider
{
	private static final Logger logger = LogManager.getLogger(HULabelConfigFromSysConfigProvider.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	//
	// Material Receipt
	private static final String SYSCONFIG_RECEIPT_LABEL_PROCESS_ID = "de.metas.handlingunits.MaterialReceiptLabel.AD_Process_ID";
	private static final String SYSCONFIG_RECEIPT_LABEL_AUTO_PRINT_ENABLED = "de.metas.handlingunits.MaterialReceiptLabel.AutoPrint.Enabled";
	private static final String SYSCONFIG_RECEIPT_LABEL_AUTO_PRINT_ENABLED_C_BPARTNER_ID = SYSCONFIG_RECEIPT_LABEL_AUTO_PRINT_ENABLED + ".C_BPartner_ID_";
	private static final String SYSCONFIG_RECEIPT_LABEL_AUTO_PRINT_COPIES = "de.metas.handlingunits.MaterialReceiptLabel.AutoPrint.Copies";

	//
	// Picking
	private static final String SYSCONFIG_PICKING_LABEL_AUTO_PRINT_ENABLED = "de.metas.ui.web.picking.PickingLabel.AutoPrint.Enabled";
	private static final String SYSCONFIG_PICKING_LABEL_PROCESS_ID = "de.metas.ui.web.picking.PickingLabel.AD_Process_ID";
	private static final String SYSCONFIG_PICKING_LABEL_AUTO_PRINT_COPIES = "de.metas.ui.web.picking.PickingLabel.AutoPrint.Copies";

	//
	// Manufacturing
	public static final String SYSCONFIG_FINISHEDGOODS_LABEL_PROCESS_ID = "de.metas.handlingunits.FinishedGoodsLabel.AD_Process_ID";

	//

	public ExplainedOptional<HULabelConfig> getFirstMatching(@NonNull final HULabelConfigQuery query)
	{
		final HULabelSourceDocType sourceDocType = query.getSourceDocType();
		if (sourceDocType == HULabelSourceDocType.MaterialReceipt)
		{
			return getReceiptLabelFromSysConfig(query.getBpartnerId());
		}
		else if (sourceDocType == HULabelSourceDocType.Picking)
		{
			return getPickingLabelFromSysConfig();
		}
		else if (sourceDocType == HULabelSourceDocType.Manufacturing)
		{
			return getManufacturingFinishedGoodsLabelFromSysConfig();
		}
		else
		{
			return ExplainedOptional.emptyBecause("Source document type is not handled by legacy sysconfig configuration");
		}
	}

	private ExplainedOptional<HULabelConfig> getReceiptLabelFromSysConfig(@Nullable final BPartnerId vendorId)
	{
		final AdProcessId printFormatProcessId = retrieveProcessIdBySysConfig(SYSCONFIG_RECEIPT_LABEL_PROCESS_ID);
		if (printFormatProcessId == null)
		{
			return ExplainedOptional.emptyBecause("No process configured via SysConfig `" + SYSCONFIG_RECEIPT_LABEL_PROCESS_ID + "`");
		}

		final boolean autoPrint = isReceiptLabelAutoPrintEnabled(vendorId);
		final PrintCopies autoPrintCopies = autoPrint
				? getAutoPrintCopyCountForSysConfig(SYSCONFIG_RECEIPT_LABEL_AUTO_PRINT_COPIES)
				: PrintCopies.ZERO;

		return ExplainedOptional.of(HULabelConfig.builder()
				.printFormatProcessId(printFormatProcessId)
				.autoPrint(autoPrint)
				.autoPrintCopies(autoPrintCopies)
				.build());
	}

	/**
	 * Checks the sysconfig and returns true or if receipt label auto printing is enabled in general or for the given HU's C_BPartner_ID.
	 *
	 * @param vendorBPartnerId the original vendor. By now, might not be the same as M_HU.C_BPartner_ID anymore
	 */
	private boolean isReceiptLabelAutoPrintEnabled(@Nullable final BPartnerId vendorBPartnerId)
	{
		final Properties ctx = Env.getCtx();
		final int adClientId = Env.getAD_Client_ID(ctx);
		final int adOrgId = Env.getAD_Org_ID(ctx);

		// NOTE: we have to check for C_BPartner_ID "-1" in case there is no vendor because production systems have a sysconfig like that defined
		final String vendorSysconfigName = SYSCONFIG_RECEIPT_LABEL_AUTO_PRINT_ENABLED_C_BPARTNER_ID
				+ (vendorBPartnerId != null ? vendorBPartnerId.getRepoId() : -1);
		final String valueForBPartner = sysConfigBL.getValue(vendorSysconfigName, "NOT_SET", adClientId, adOrgId);
		logger.debug("SysConfig {}={};", vendorSysconfigName, valueForBPartner);

		if (!"NOT_SET".equals(valueForBPartner))
		{
			return StringUtils.toBoolean(valueForBPartner);
		}

		final String genericValue = sysConfigBL.getValue(SYSCONFIG_RECEIPT_LABEL_AUTO_PRINT_ENABLED, "N", adClientId, adOrgId);
		logger.debug("SysConfig {}={};", SYSCONFIG_RECEIPT_LABEL_AUTO_PRINT_ENABLED, genericValue);
		return StringUtils.toBoolean(genericValue);
	}

	private ExplainedOptional<HULabelConfig> getPickingLabelFromSysConfig()
	{
		final AdProcessId printFormatProcessId = retrieveProcessIdBySysConfig(SYSCONFIG_PICKING_LABEL_PROCESS_ID);
		if (printFormatProcessId == null)
		{
			return ExplainedOptional.emptyBecause("No process configured via SysConfig `" + SYSCONFIG_PICKING_LABEL_PROCESS_ID + "`");
		}

		return ExplainedOptional.of(HULabelConfig.builder()
				.printFormatProcessId(printFormatProcessId)
				.autoPrint(isPickingLabelAutoPrintEnabled())
				.autoPrintCopies(getAutoPrintCopyCountForSysConfig(SYSCONFIG_PICKING_LABEL_AUTO_PRINT_COPIES))
				.build());
	}

	private boolean isPickingLabelAutoPrintEnabled()
	{
		final Properties ctx = Env.getCtx();

		final String genericValue = sysConfigBL.getValue(SYSCONFIG_PICKING_LABEL_AUTO_PRINT_ENABLED, "N", Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx));
		logger.debug("SysConfig {}={};", SYSCONFIG_PICKING_LABEL_AUTO_PRINT_ENABLED, genericValue);

		return StringUtils.toBoolean(genericValue);
	}

	private ExplainedOptional<HULabelConfig> getManufacturingFinishedGoodsLabelFromSysConfig()
	{
		final AdProcessId printFormatProcessId = retrieveProcessIdBySysConfig(SYSCONFIG_FINISHEDGOODS_LABEL_PROCESS_ID);
		if (printFormatProcessId == null)
		{
			return ExplainedOptional.emptyBecause("No process configured via SysConfig `" + SYSCONFIG_FINISHEDGOODS_LABEL_PROCESS_ID + "`");
		}

		return ExplainedOptional.of(HULabelConfig.builder()
				.printFormatProcessId(printFormatProcessId)
				.autoPrint(false)
				.autoPrintCopies(PrintCopies.ZERO)
				.build());
	}

	private AdProcessId retrieveProcessIdBySysConfig(final String sysConfigName)
	{
		final Properties ctx = Env.getCtx();
		final int reportProcessId = sysConfigBL.getIntValue(sysConfigName, -1, Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx));
		return AdProcessId.ofRepoIdOrNull(reportProcessId);
	}

	private PrintCopies getAutoPrintCopyCountForSysConfig(final String sysConfigName)
	{
		final Properties ctx = Env.getCtx();
		return PrintCopies.ofIntOrOne(sysConfigBL.getIntValue(sysConfigName, 1, Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx)));
	}

}
