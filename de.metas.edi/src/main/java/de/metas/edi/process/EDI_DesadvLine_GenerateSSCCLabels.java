package de.metas.edi.process;

/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.util.Check;

import de.metas.edi.sscc18.DesadvLineSSCC18Generator;
import de.metas.edi.sscc18.PrintableDesadvLineSSCC18Labels;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.esb.edi.model.I_EDI_DesadvLine_SSCC;
import de.metas.process.ProcessInfoParameter;
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

		desadvLineLabelsGenerator = new DesadvLineSSCC18Generator()
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
