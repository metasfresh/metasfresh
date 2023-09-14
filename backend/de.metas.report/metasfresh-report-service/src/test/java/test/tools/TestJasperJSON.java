package test.tools;

import ch.qos.logback.classic.Level;
import de.metas.logging.LogManager;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.query.JsonQLQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRLoader;
import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.tools.AdempiereToolsHelper;
import org.compiere.util.DB;
import org.junit.Ignore;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/*
 * #%L
 * report-service
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
@Ignore
public class TestJasperJSON
{

	public static void main(String[] args) throws JRException, FileNotFoundException
	{

		//
		// Start ADempiere
		AdempiereToolsHelper.getInstance().startupMinimal();
		LogManager.setLevel(Level.DEBUG);
		MigrationScriptFileLoggerHolder.setEnabled(false); // metas: don't log migration scripts

		//
		// get some value from DB
		final String sql_value = DB.getSQLValueStringEx(ITrx.TRXNAME_ThreadInherited, "select name from M_product where m_product_id=1000000");

		// get some data form JSON
		final InputStream inputStream = TestJasperJSON.class.getResourceAsStream("/product_entry.json");

		//
		// prepare  parameters
		final Map<String, Object> params = new HashMap<>();
		params.put(JsonQLQueryExecuterFactory.JSON_INPUT_STREAM, inputStream);
		params.put("SQL_VALUE", sql_value);



		final JasperReport jasperReport = (JasperReport)JRLoader.loadObjectFromFile(TestJasperJSON.class.getResource("/JSON_POC_Report.jasper").getFile());
		final JasperPrint print = JasperFillManager.fillReport(jasperReport, params);

		final JRPdfExporter exporter = new JRPdfExporter();
		final String pdf_File = "E:/JSON_PDF_File.pdf";
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, pdf_File);
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
		exporter.exportReport();
		System.out.println("File Created: " + pdf_File);
	}

}
