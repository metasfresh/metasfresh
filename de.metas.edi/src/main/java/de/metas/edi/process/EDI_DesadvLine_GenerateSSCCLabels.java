package de.metas.edi.process;

import org.compiere.SpringContextHolder;

import de.metas.edi.sscc18.DesadvLineSSCC18Generator;
import de.metas.edi.sscc18.PrintableDesadvLineSSCC18Labels;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.esb.edi.model.I_EDI_DesadvLine_SSCC;
import de.metas.handlingunits.attributes.sscc18.impl.SSCC18CodeBL;
import de.metas.process.ProcessInfoParameter;
import de.metas.util.Check;
import de.metas.process.JavaProcess;

/**
 * Creates as many {@link I_EDI_DesadvLine_SSCC} records as asked and then print them
 *
 * @author tsa
 * @task 08910
 */
public class EDI_DesadvLine_GenerateSSCCLabels extends JavaProcess
{
	//
	// Parameters
	private static final String PARAM_Counter = "Counter";
	/** How many {@link I_EDI_DesadvLine_SSCC} are required */
	private int p_Counter = 0;

	private final transient SSCC18CodeBL sscc18CodeService = SpringContextHolder.instance.getBean(SSCC18CodeBL.class);

	//
	// status
	private DesadvLineSSCC18Generator desadvLineLabelsGenerator;

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter param : getParametersAsArray())
		{
			final String parameterName = param.getParameterName();
			if (PARAM_Counter.equals(parameterName))
			{
				p_Counter = param.getParameterAsInt();
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		Check.assume(p_Counter > 0, "@Counter@ > 0");
		final I_EDI_DesadvLine desadvLine = getRecord(I_EDI_DesadvLine.class);

		final PrintableDesadvLineSSCC18Labels desadvLineLabels = PrintableDesadvLineSSCC18Labels.builder()
				.setEDI_DesadvLine(desadvLine)
				.setRequiredSSCC18Count(p_Counter)
				.build();

		desadvLineLabelsGenerator = new DesadvLineSSCC18Generator(sscc18CodeService)
				.setContext(this)
				.setPrintExistingLabels(true)
				.generateAndEnqueuePrinting(desadvLineLabels);

		return MSG_OK;
	}

	/**
	 * Print all enqueued {@link I_EDI_DesadvLine_SSCC} labels.
	 */
	@Override
	protected void postProcess(boolean success)
	{
		if (!success)
		{
			return;
		}

		desadvLineLabelsGenerator.printAll();
	}
}
