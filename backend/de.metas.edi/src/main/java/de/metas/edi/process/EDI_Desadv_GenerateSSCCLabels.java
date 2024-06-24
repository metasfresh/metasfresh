package de.metas.edi.process;

import de.metas.edi.api.IDesadvBL;
import de.metas.edi.api.impl.pack.EDIDesadvPackId;
import de.metas.edi.api.impl.pack.EDIDesadvPackRepository;
import de.metas.edi.sscc18.DesadvLineSSCC18Generator;
import de.metas.edi.sscc18.PrintableDesadvLineSSCC18Labels;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.handlingunits.attributes.sscc18.impl.SSCC18CodeBL;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.report.ReportResultData;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;

import javax.annotation.Nullable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Creates as many {@link de.metas.esb.edi.model.I_EDI_Desadv_Pack} records as asked and then print them
 *
 * @author tsa
 * Task 08910
 */
public class EDI_Desadv_GenerateSSCCLabels extends JavaProcess implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private final IDesadvBL desadvBL = SpringContextHolder.instance.getBean(IDesadvBL.class);
	private final SSCC18CodeBL sscc18CodeService = SpringContextHolder.instance.getBean(SSCC18CodeBL.class);
	private final EDIDesadvPackRepository EDIDesadvPackRepository = SpringContextHolder.instance.getBean(EDIDesadvPackRepository.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private static final String PARAM_IsDefault = "IsDefault";
	@Param(parameterName = PARAM_IsDefault)
	private boolean p_IsDefault = true;

	private static final String PARAM_Counter = "Counter";
	@Param(parameterName = PARAM_Counter)
	private int p_Counter = 0;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		final Set<TableRecordReference> selectedLineRefs = context.getSelectedIncludedRecords();
		if (selectedLineRefs.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Nullable
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (PARAM_Counter.equals(parameter.getColumnName()))
		{
			if (getSelectedLineIds().size() == 1)
			{
				final I_EDI_DesadvLine desadvLine = getSingleSelectedLine();
				return PrintableDesadvLineSSCC18Labels.builder()
						.setEDI_DesadvLine(desadvLine)
						.getRequiredSSCC18Count();
			}
		}

		return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		final Set<EDIDesadvPackId> lineSSCCIdsToPrint = generateLabels();

		if (!lineSSCCIdsToPrint.isEmpty())
		{
			final ReportResultData report = desadvBL.printSSCC18_Labels(getCtx(), lineSSCCIdsToPrint);
			getResult().setReportData(report);
		}

		return MSG_OK;
	}

	private Set<EDIDesadvPackId> generateLabels()
	{
		return trxManager.callInThreadInheritedTrx(this::generateLabelsInTrx);
	}

	private Set<EDIDesadvPackId> generateLabelsInTrx()
	{
		if(!p_IsDefault && p_Counter <= 0)
		{
			throw new FillMandatoryException(PARAM_Counter);
		}

		final List<I_EDI_DesadvLine> desadvLines = getSelectedLines();
		if (desadvLines.isEmpty())
		{
			throw new AdempiereException("@NoSelection@");
		}

		final DesadvLineSSCC18Generator desadvLineLabelsGenerator = DesadvLineSSCC18Generator.builder()
				.sscc18CodeService(sscc18CodeService)
				.desadvBL(desadvBL)
				.ediDesadvPackRepository(EDIDesadvPackRepository)
				.printExistingLabels(true)
				.build();

		final LinkedHashSet<EDIDesadvPackId> lineSSCCIdsToPrint = new LinkedHashSet<>();

		for (final I_EDI_DesadvLine desadvLine : desadvLines)
		{
			final PrintableDesadvLineSSCC18Labels desadvLineLabels = PrintableDesadvLineSSCC18Labels.builder()
					.setEDI_DesadvLine(desadvLine)
					.setRequiredSSCC18Count(!p_IsDefault ? p_Counter : null)
					.build();

			desadvLineLabelsGenerator.generateAndEnqueuePrinting(desadvLineLabels);

			lineSSCCIdsToPrint.addAll(desadvLineLabelsGenerator.getLineSSCCIdsToPrint());
		}

		return lineSSCCIdsToPrint;
	}

	private List<I_EDI_DesadvLine> getSelectedLines()
	{
		final Set<Integer> lineIds = getSelectedLineIds();
		return desadvBL.retrieveLinesByIds(lineIds);
	}

	private I_EDI_DesadvLine getSingleSelectedLine()
	{
		return CollectionUtils.singleElement(getSelectedLines());
	}

	private Set<Integer> getSelectedLineIds()
	{
		return getSelectedIncludedRecordIds(I_EDI_DesadvLine.class);
	}
}
