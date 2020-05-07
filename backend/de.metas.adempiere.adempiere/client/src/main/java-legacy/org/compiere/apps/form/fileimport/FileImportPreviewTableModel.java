package org.compiere.apps.form.fileimport;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.impexp.CellErrorMessage;
import org.compiere.impexp.ImpDataLine;
import org.compiere.impexp.ImpFormat;
import org.compiere.util.Env;

import de.metas.i18n.IMsgBL;

public class FileImportPreviewTableModel extends AbstractTableModel
{
	private static final long serialVersionUID = 7464707521144987420L;
	
	public static final FileImportPreviewTableModel castOrNull(final TableModel tableModel)
	{
		if (tableModel instanceof FileImportPreviewTableModel)
		{
			return (FileImportPreviewTableModel)tableModel;
		}
		
		return null;
	}

	// services
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	private ImpFormat impFormat;
	private boolean impFormatSet = false;
	private List<TableColumnModel> columnModels = new ArrayList<>();
	private List<ImpDataLine> impDataLines = new ArrayList<>();

	public FileImportPreviewTableModel()
	{
		super();
		setImpFormat(null);
	}

	public void setImpFormat(final ImpFormat impFormat)
	{
		if (impFormatSet && Objects.equals(this.impFormat, impFormat))
		{
			return;
		}

		this.impFormat = impFormat;

		//
		// Create table column models
		final List<TableColumnModel> columnModels = new ArrayList<>();
		if (impFormat == null)
		{
			columnModels.add(new RawDataTableColumnModel(0));
		}
		else
		{
			int columnIndex = 0;
			columnModels.add(new ToImportTableColumnModel(columnIndex++));
			columnModels.add(new LineNoTableColumnModel(columnIndex++));
			for (int rowIndex = 0; rowIndex < impFormat.getRowCount(); rowIndex++)
			{
				columnModels.add(new ImpDataLineRowTableColumnModel(impFormat.getRow(rowIndex), rowIndex, columnIndex++));
			}
			columnModels.add(new ImportStatusTableColumnModel(columnIndex++));
			columnModels.add(new ImportErrorTableColumnModel(columnIndex++));
		}
		this.columnModels = columnModels;

		//
		// Rebuild the lines
		for (final ImpDataLine impDataLine : impDataLines)
		{
			impDataLine.setImpFormat(impFormat);
		}

		//
		// Notify that everything has changed
		fireTableStructureChanged();
		fireTableDataChanged();
	}

	public final ImpFormat getImpFormat()
	{
		return impFormat;
	}

	public void setDataLines(final List<String> lines)
	{
		final List<ImpDataLine> impDataLines = new ArrayList<>(lines.size());
		for (int i = 0; i < lines.size(); i++)
		{
			final ImpDataLine impDataLine = ImpDataLine.builder()
					.impFormat(getImpFormat())
					.fileLineNo(i + 1) // start from 1
					.lineStr(lines.get(i))
					.build();
			impDataLines.add(impDataLine);
		}

		setImpDataLines(impDataLines);
	}

	private final void setImpDataLines(final List<ImpDataLine> impDataLines)
	{
		Check.assumeNotNull(impDataLines, "impDataLines not null");
		this.impDataLines = impDataLines;
		fireTableDataChanged();
	}

	public TableColumnModel getColumnModel(final int columnIndex)
	{
		return columnModels.get(columnIndex);
	}

	@Override
	public String getColumnName(final int columnIndex)
	{
		return getColumnModel(columnIndex).getColumnDisplayName();
	}

	@Override
	public Class<?> getColumnClass(final int columnIndex)
	{
		return getColumnModel(columnIndex).getColumnClass();
	}

	@Override
	public int getColumnCount()
	{
		return columnModels.size();
	}

	@Override
	public int getRowCount()
	{
		return impDataLines.size();
	}

	public final List<ImpDataLine> getImpDataLines()
	{
		return new ArrayList<>(impDataLines);
	}

	private final ImpDataLine getImpDataLine(final int rowIndex)
	{
		return impDataLines.get(rowIndex);
	}

	@Override
	public boolean isCellEditable(final int rowIndex, final int columnIndex)
	{
		final ImpDataLine dataLine = getImpDataLine(rowIndex);
		return getColumnModel(columnIndex).isCellEditable(dataLine);
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex)
	{
		final ImpDataLine dataLine = getImpDataLine(rowIndex);
		return getColumnModel(columnIndex).getValue(dataLine);
	}

	@Override
	public void setValueAt(final Object value, final int rowIndex, final int columnIndex)
	{
		final ImpDataLine dataLine = getImpDataLine(rowIndex);
		getColumnModel(columnIndex).setValue(dataLine, value);

		// Because in some cases, the table models are also updating others columns, it's safe to notify that entire row was changed.
		fireTableRowsUpdated(rowIndex, rowIndex);
	}
	
	public CellErrorMessage getCellErrorMessage(final int rowIndex, final int columnIndex)
	{
		final ImpDataLine dataLine = getImpDataLine(rowIndex);
		if (dataLine == null)
		{
			return null;
		}
		return getColumnModel(columnIndex).getCellErrorMessage(dataLine);
	}

	public String getSummary()
	{
		final int linesCount = getRowCount();
		if (linesCount <= 0)
		{
			return "       ";
		}

		// consider that data has header line if there is more then one record
		final boolean hasHeaderLine = linesCount > 1;
		final int firstDataLineIdx = hasHeaderLine ? 1 : 0;
		final int firstDataLineLength = getImpDataLine(firstDataLineIdx).getLineString().length();

		return msgBL.getMsg(Env.getCtx(), "Records") + ": " + linesCount + ", " + msgBL.getMsg(Env.getCtx(), "Length") + ": " + firstDataLineLength + "   ";
	}
}
