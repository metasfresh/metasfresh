package de.metas.payment.sepa.process;

import de.metas.banking.PaySelectionId;
import de.metas.banking.payment.IPaySelectionDAO;
import de.metas.common.util.time.SystemTime;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.AdMessageKey;
import de.metas.payment.sepa.api.ISEPADocumentBL;
import de.metas.payment.sepa.api.SEPACreditTransferXML;
import de.metas.payment.sepa.api.SEPAExportContext;
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
import org.springframework.core.io.ByteArrayResource;

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

	//
	// services
	private final ISEPADocumentBL sepaDocumentBL = Services.get(ISEPADocumentBL.class);
	private final IPaySelectionDAO paySelectionDAO = Services.get(IPaySelectionDAO.class);

	private final static String REFERENCE_AS_END_TO_END_ID = "ReferenceAsEndToEndId";
	@Param(parameterName = REFERENCE_AS_END_TO_END_ID)
	private boolean referenceAsEndToEndId;

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
		final I_SEPA_Export sepaExport = sepaDocumentBL.createSEPAExportFromPaySelection(paySelection);

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

		return MSG_OK;
	}
}
