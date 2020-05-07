package de.metas.adempiere.report.jasper.server;

import org.apache.poi.ss.SpreadsheetVersion;

import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.export.Cut;
import net.sf.jasperreports.engine.export.CutsInfo;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.XlsReportConfiguration;

/**
 * Extension of {@link JRXlsExporter} which implements our custom features. Mostly that's implementing {@link #PROPERTY_COLUMN_HIDDEN}.<br>
 * We do this by firstly, "registering" out own {@link net.sf.jasperreports.engine.export.JRXlsExporterNature} subclass,<br>
 * and by secondly setting individual columns to be hidden, when the sheet is created.
 *
 *
 * Note that we also set some excel parameters to the way we need them.
 *
 * @author tsa
 *
 */
public class MetasJRXlsExporter extends JRXlsExporter
{
	/**
	 * This property indicates whether the column content is hidden.
	 *
	 * The property could be set to any element and in case at least one element has this property enabled, the entire column will be hidden.
	 */
	public static final String PROPERTY_COLUMN_HIDDEN = JRPropertiesUtil.PROPERTY_PREFIX + "export.xls.column.hidden";

	public MetasJRXlsExporter()
	{
		super();
		setStandardParameters();
	}

	/**
	 * Configure standard parameters.
	 * Mainly our aim is to get a clean Excel report.
	 */
	private final void setStandardParameters()
	{
		// TODO: use the modern API, but note that we can't mix calls to this deprecated API with calls to the new one.

		// Set a password to protect the locked cells.
		// NOTE: if no password is set, the cells which are marked as locked won't be locked.
		// NOTE2: instead of using a random password, it's better to have a well known one because we are setting the password only to protect the cells from writting and NOT to secure the document.
		// setParameter(JRXlsAbstractExporterParameter.PASSWORD, UUID.randomUUID().toString());
		setParameter(JRXlsAbstractExporterParameter.PASSWORD, "unlock");

		// Take out as much graphics & formatting as possible.
		// Our aim is to get a clean Excel file.
		setParameter(JRXlsAbstractExporterParameter.IS_IGNORE_GRAPHICS, true);
		setParameter(JRXlsAbstractExporterParameter.IS_IGNORE_CELL_BACKGROUND, true);
		setParameter(JRXlsAbstractExporterParameter.IS_IGNORE_CELL_BORDER, true);
		setParameter(JRXlsAbstractExporterParameter.IS_COLLAPSE_ROW_SPAN, true);
		setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, true);
		setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, true);
		setParameter(JRXlsAbstractExporterParameter.MAXIMUM_ROWS_PER_SHEET, SpreadsheetVersion.EXCEL97.getLastRowIndex());
	}

	@Override
	protected void initReport()
	{
		super.initReport();

		final XlsReportConfiguration configuration = getCurrentItemConfiguration();

		// Exporter shall use our "nature" in order to have all properties set to Cuts.
		nature = new MetasJRXlsExporterNature(jasperReportsContext, filter,
				configuration.isIgnoreGraphics(),
				configuration.isIgnorePageMargins());
	}

	/**
	 * First calls the super class's method.<br>
	 * Then sets the columns to be hidden whose original jasper elements were annotated to be hidden with the {@link MetasJRXlsExporter#PROPERTY_COLUMN_HIDDEN} property.
	 * <p>
	 * See {@link MetasJRXlsExporterNature} for how that property gets from the jasper elements to the given <code>xCuts</code> instance.
	 */
	@Override
	protected void createSheet(CutsInfo xCuts, SheetInfo sheetInfo)
	{
		super.createSheet(xCuts, sheetInfo);

		//
		// Iterate all xCuts (i.e. columns) and hide those which were marked to be hidden
		for (int col = xCuts.size() - 1; col >= 0; col--)
		{
			final Cut xCut = xCuts.getCut(col);
			if (isHideColumn(xCut))
			{
				sheet.setColumnHidden(col, true);
			}
		}
	}

	/**
	 * @param xCut
	 * @return true if the column shall be hidden in the resulting excel
	 */
	private boolean isHideColumn(final Cut xCut)
	{
		final boolean hidden = xCut.hasProperty(PROPERTY_COLUMN_HIDDEN)
				&& (Boolean)xCut.getProperty(PROPERTY_COLUMN_HIDDEN);
		return hidden;
	}
}
