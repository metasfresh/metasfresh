package de.metas.report.xls.engine;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatee2;
import org.compiere.util.Evaluatees;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.report.jasper.OutputType;
import de.metas.process.ProcessInfoParameter;
import de.metas.report.engine.AbstractReportEngine;
import de.metas.report.engine.ReportContext;

/*
 * #%L
 * de.metas.report.jasper.server.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class XlsReportEngine extends AbstractReportEngine
{
	public static final Set<String> REPORT_FILE_EXTENSIONS = ImmutableSet.of("xls");

	private static final String PROPERTY_ProcessParameters = "p";

	@Override
	public void report(final ReportContext reportContext, final OutputStream out)
	{
		OutputType outputType = reportContext.getOutputType();
		if (outputType == null)
		{
			outputType = OutputType.XLS;
		}

		final ClassLoader reportClassLoader = createReportClassLoader(reportContext);
		final String templateResourceName = reportContext.getReportTemplatePath();
		Check.assumeNotEmpty(templateResourceName, "templateResourceName not defined in process: {}", reportContext);

		//
		// Pick the right output stream
		final OutputStream xlsOutput;
		final boolean needsConversion;
		if (outputType == OutputType.XLS)
		{
			xlsOutput = out;
			needsConversion = false;
		}
		else if (outputType == OutputType.PDF)
		{
			xlsOutput = new ByteArrayOutputStream();
			needsConversion = true;
		}
		else
		{
			throw new AdempiereException("OutputType " + outputType + " not supported by " + this);
		}

		//
		// Datasource
		final IXlsDataSource xlsDataSource = retrieveDataSource(reportContext);

		//
		// Create the report
		JXlsExporter.newInstance()
				.setContext(reportContext.getCtx())
				.setLoader(reportClassLoader)
				.setProperty(PROPERTY_ProcessParameters, createContextAsMap(reportContext))
				.setTemplateResourceName(templateResourceName)
				.setOutput(xlsOutput)
				.setAD_Language(reportContext.getAD_Language())
				//
				.setDataSource(xlsDataSource)
				.export();

		//
		// Convert to desired OutputType if needed
		if (needsConversion)
		{
			final byte[] xlsData = ((ByteArrayOutputStream)xlsOutput).toByteArray();
			convert(xlsData, outputType, out);
		}
	}

	private void convert(final byte[] xlsData, final OutputType outputType, final OutputStream out)
	{
		if (outputType == OutputType.PDF)
		{
			throw new UnsupportedOperationException("Conversion to PDF is not yet implemented");
		}
		else
		{
			throw new AdempiereException("OutputType " + outputType + " is not supported by " + this);
		}
	}

	private IXlsDataSource retrieveDataSource(final ReportContext reportContext)
	{
		//
		// Get SQL Statement
		final String sql = reportContext.getSQLStatement();
		if (Check.isEmpty(sql, true))
		{
			throw new AdempiereException("SQL Statement is not set in " + reportContext);
		}

		//
		// Parse the SQL Statement
		final IStringExpression sqlExpression = Services.get(IExpressionFactory.class).compile(sql, IStringExpression.class);
		final Evaluatee evalCtx = createEvaluationContext(reportContext);
		String sqlFinal = sqlExpression.evaluate(evalCtx, OnVariableNotFound.Fail);

		//
		// Add debugging query info
		{
			String sqlQueryInfo = "jxls report=" + reportContext.getReportTemplatePath()
					+ ", AD_PInstance_ID=" + reportContext.getAD_PInstance_ID();
			sqlQueryInfo = "/* "
					+ sqlQueryInfo.replace("/*", " ").replace("*/", " ")
					+ " */ ";
			
			sqlFinal = sqlQueryInfo + sqlFinal;
		}

		//
		// Create & return the data source
		return JdbcXlsDataSource.of(sqlFinal);
	}

	private Evaluatee createEvaluationContext(final ReportContext reportContext)
	{
		final Map<String, Object> reportContextAsMap = createContextAsMap(reportContext);
		final Evaluatee2 reportContextAsEvaluatee = Evaluatees.ofMap(reportContextAsMap);

		final Evaluatee ctxEvaluatee = Evaluatees.ofCtx(reportContext.getCtx(), Env.WINDOW_MAIN, false); // onlyWindow=false

		return Evaluatees.compose(reportContextAsEvaluatee, ctxEvaluatee);
	}

	private Map<String, Object> createContextAsMap(final ReportContext reportContext)
	{
		final Collection<ProcessInfoParameter> processInfoParams = reportContext.getProcessInfoParameters();
		if (processInfoParams.isEmpty())
		{
			return ImmutableMap.of();
		}

		final TreeMap<String, Object> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

		map.put("AD_Process_ID", reportContext.getAD_Process_ID());
		map.put("AD_PInstance_ID", reportContext.getAD_PInstance_ID());
		map.put("AD_Table_ID", reportContext.getAD_Table_ID());
		map.put("Record_ID", reportContext.getRecord_ID());
		map.put("AD_Language", reportContext.getAD_Language());
		map.put("OutputType", reportContext.getOutputType());

		for (final ProcessInfoParameter param : processInfoParams)
		{
			final String parameterName = param.getParameterName();
			map.put(parameterName, param.getParameter());
			map.put(parameterName + "_Info", param.getInfo());

			map.put(parameterName + "_From", param.getParameter());
			map.put(parameterName + "_From_Info", param.getInfo());

			map.put(parameterName + "_To", param.getParameter_To());
			map.put(parameterName + "_To_Info", param.getInfo_To());
		}

		return Collections.unmodifiableMap(map);
	}
}
