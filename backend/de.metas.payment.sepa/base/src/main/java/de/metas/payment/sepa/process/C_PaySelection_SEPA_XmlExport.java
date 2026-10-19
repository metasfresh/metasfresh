package de.metas.payment.sepa.process;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.banking.PaySelectionId;
import de.metas.banking.payment.IPaySelectionDAO;
import de.metas.common.util.time.SystemTime;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.AdMessageKey;
import de.metas.payment.sepa.api.ISEPADocumentBL;
import de.metas.payment.sepa.api.SEPACreditTransferXML;
import de.metas.payment.sepa.api.SEPAExportContext;
import de.metas.payment.sepa.api.SEPAProtocol;
import de.metas.payment.sepa.api.SepaUtils;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.report.ReportResultData;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_PaySelection;
import org.compiere.util.Env;
import org.springframework.core.io.ByteArrayResource;

import java.util.List;
import java.util.Optional;

/**
 * Process that creates SEPA xmls in 3 steps:
 * <p>
 * Creates SEPA_Export/SEPA_Export_Lines from C_PaySelection/C_PaySelectionLines Create SEPADocument/lines from the export Marshals the lines into an XML
 *
 * @author ad
 */
public class C_PaySelection_SEPA_XmlExport
		extends JavaProcess
		implements IProcessPrecondition
{
	private static final AdMessageKey MSG_NO_SELECTION = AdMessageKey.of("de.metas.payment.sepa.noPaySelection");

	private static final String WARNINGS_SEPARATOR = " · "; // middle dot

	//
	// services
	private final ISEPADocumentBL sepaDocumentBL = Services.get(ISEPADocumentBL.class);
	private final IPaySelectionDAO paySelectionDAO = Services.get(IPaySelectionDAO.class);

	private final static String REFERENCE_AS_END_TO_END_ID = "ReferenceAsEndToEndId";
	@Param(parameterName = REFERENCE_AS_END_TO_END_ID)
	private boolean referenceAsEndToEndId;

	@Param(parameterName = "IsGroupTransactions")
	private boolean isGroupTransactions;

	private final static String PARAM_SEPA_PROTOCOL = "SEPA_Protocol";
	@Param(parameterName = PARAM_SEPA_PROTOCOL)
	private String sepaProtocolCode;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		final String tableName = context.getTableName();
		if (!I_C_PaySelection.Table_Name.equals(tableName))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Process " + C_PaySelection_SEPA_XmlExport.class + " only works for C_PaySelection");
		}

		final PaySelectionId paySelectionId = PaySelectionId.ofRepoIdOrNull(context.getSingleSelectedRecordId());
		final Optional<org.compiere.model.I_C_PaySelection> paySelectionHeader = paySelectionDAO.getById(paySelectionId);
		if (!paySelectionHeader.isPresent())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(paySelectionHeader.get().getDocStatus());
		if (!docStatus.isCompletedOrClosed())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason(
					"Process " + C_PaySelection_SEPA_XmlExport.class + " only works for completed or closed C_PaySelections; C_PaySelection_ID=" + paySelectionHeader.get().getC_PaySelection_ID());
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final int recordId = getRecord_ID();
		if (recordId <= 0)
		{
			throw new AdempiereException(MSG_NO_SELECTION);
		}

		final I_C_PaySelection paySelection = paySelectionDAO.getById(PaySelectionId.ofRepoId(recordId))
				.orElseThrow(() -> new AdempiereException("@NotFound@ @C_PaySelection_ID@ (ID=" + recordId + ")"));

		//
		// First, generate the SEPA export as an intermediary step, to use the old framework.
		final I_SEPA_Export sepaExport = sepaDocumentBL.createSEPAExportFromPaySelection(paySelection, isGroupTransactions, SEPAProtocol.ofNullableCode(sepaProtocolCode));

		final SEPAExportContext exportContext = SEPAExportContext.builder()
				.referenceAsEndToEndId(referenceAsEndToEndId)
				.build();
		//
		// After the export header and lines have been created, marshal the document.
		final SEPACreditTransferXML xml = sepaDocumentBL.exportCreditTransferXML(sepaExport, exportContext);

		paySelection.setLastSepaExport(SystemTime.asTimestamp());
		paySelection.setLastSepaExportBy_ID(getAD_User_ID());
		InterfaceWrapperHelper.saveRecord(paySelection);

		getResult().setReportData(ReportResultData.builder()
				.reportFilename(xml.getFilename())
				.reportContentType(xml.getContentType())
				.reportData(new ByteArrayResource(xml.getContent()))
				.build());

		// the WebUI shows the summary next to the download; the warnings are also in the process log
		return createSummary(xml, Env.getADLanguageOrBaseLanguage(getCtx()));
	}

	/**
	 * @return {@link #MSG_OK} if the export has no warnings; otherwise its warnings translated to the given language, on one line: a single warning as it is,
	 * several numbered as {@code 1) … · 2) …}; every "@" is escaped as "@@", i.e. the text is meant to be passed through {@code IMsgBL.parseTranslation}
	 */
	@VisibleForTesting
	static String createSummary(@NonNull final SEPACreditTransferXML xml, @NonNull final String adLanguage)
	{
		// one line, because the WebUI collapses line breaks in the notification; escaped, because the warnings contain partner, location and bank account names
		final List<String> warnings = xml.getWarnings()
				.stream()
				.map(warning -> SepaUtils.escapeAtSignsForParseTranslation(warning.translate(adLanguage)))
				.collect(ImmutableList.toImmutableList());
		if (warnings.isEmpty())
		{
			return MSG_OK;
		}
		else if (warnings.size() == 1)
		{
			return warnings.get(0);
		}

		final StringBuilder summary = new StringBuilder();
		for (int i = 0; i < warnings.size(); i++)
		{
			if (i > 0)
			{
				summary.append(WARNINGS_SEPARATOR);
			}
			summary.append(i + 1).append(") ").append(warnings.get(i));
		}
		return summary.toString();
	}
}
