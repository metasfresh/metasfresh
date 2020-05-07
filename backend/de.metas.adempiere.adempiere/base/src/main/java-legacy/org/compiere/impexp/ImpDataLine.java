package org.compiere.impexp;

import java.util.List;
import java.util.Objects;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnableAdapter;

import com.google.common.collect.ImmutableList;

import lombok.Builder;
import lombok.Getter;

/**
 * A line from import file, which needs to be imported.
 * 
 * @author tsa
 *
 */
public class ImpDataLine
{
	private final String lineStr;
	private final int fileLineNo;
	@Getter
	private final int dataImportId;
	
	private ImpFormat _impFormat;
	private List<ImpDataCell> _values;

	//
	// Import status
	private ImportStatus importStatus = ImportStatus.New;
	private Throwable importError = null;
	private ITableRecordReference importRecordRef;
	private boolean toImport = true;

	@Builder
	private ImpDataLine(
			final ImpFormat impFormat,
			final int fileLineNo,
			final String lineStr,
			final int dataImportId)
	{
		this.fileLineNo = fileLineNo;
		this.lineStr = lineStr;
		this.dataImportId = dataImportId;


		this.setImpFormat(impFormat);
	}

	public int getFileLineNo()
	{
		return fileLineNo;
	}

	public String getLineString()
	{
		return lineStr;
	}

	public boolean isEmpty()
	{
		return Check.isEmpty(lineStr, true) || getValues().isEmpty();
	}

	public synchronized void setImpFormat(final ImpFormat impFormat)
	{
		if (Objects.equals(this._impFormat, impFormat))
		{
			return;
		}

		this._impFormat = impFormat;
		resetStatus();
	}

	private ImpFormat getImpFormat()
	{
		return _impFormat;
	}

	private final void resetStatus()
	{
		_values = null;
		setImportStatus_New();
	}

	public synchronized List<ImpDataCell> getValues()
	{
		if (_values == null)
		{
			final ImpFormat impFormat = getImpFormat();
			if (impFormat == null)
			{
				final ImpFormatRow impFormatRow = null; // N/A
				final ImpDataCell cell = new ImpDataCell(impFormatRow);
				cell.setValue(lineStr); // whole line
				_values = ImmutableList.of(cell);
			}
			else
			{
				_values = impFormat.parseDataCells(lineStr);
			}

			// Unset "To import" if at least one cell has errors
			if (hasCellErrors())
			{
				setToImport(false);
			}
		}
		return _values;
	}

	public String getValueOrNull(int index)
	{
		if (index < 0)
		{
			return null;
		}
		final List<ImpDataCell> values = getValues();
		if (index >= values.size())
		{
			return null;
		}
		return values.get(index).getValueAsString();
	}

	public boolean isValidIndex(final int index)
	{
		if (index < 0)
		{
			return false;
		}
		final List<ImpDataCell> values = getValues();
		return index < values.size();
	}

	/**
	 * Sets cell value
	 * 
	 * @param columnIndex cell's index
	 * @param value value to set
	 * @param flagToImport true if the line shall be flagged as "To import" if the line has no cell errors
	 */
	public void setValue(final int columnIndex, final Object value, final boolean flagToImport)
	{
		final List<ImpDataCell> values = getValues();
		final ImpDataCell impDataCell = values.get(columnIndex);
		impDataCell.setValue(value);

		if (hasCellErrors())
		{
			setToImport(false);
		}
		else if (flagToImport)
		{
			setToImport(true);
		}
	}

	public CellErrorMessage getCellErrorMessage(final int columnIndex)
	{
		if (columnIndex < 0)
		{
			return null;
		}
		final List<ImpDataCell> values = getValues();
		if (columnIndex >= values.size())
		{
			return null;
		}
		return values.get(columnIndex).getCellErrorMessage();
	}

	public boolean isToImport()
	{
		getValues(); // make sure the values are loaded

		return toImport;
	}

	public void setToImport(final boolean toImport)
	{
		this.toImport = toImport;
	}

	private void setImportStatus_New()
	{
		this.importStatus = ImportStatus.New;
		this.importError = null;
		this.importRecordRef = null;
		this.toImport = getImpFormat() != null;
	}

	private void setImportStatus_ImportPrepared(final ITableRecordReference importRecordRef)
	{
		this.importStatus = ImportStatus.ImportPrepared;
		this.importError = null;
		this.importRecordRef = importRecordRef;
		this.toImport = false;
	}

	private void setImportStatus_Error(final Throwable error)
	{
		this.importStatus = ImportStatus.Error;
		this.importError = error;
		// this.importRecordRef // don't touch it, we keep it as it is
	}

	public void setImportStatus_Scheduled()
	{
		this.importStatus = ImportStatus.ImportScheduled;
		this.toImport = false;
	}

	/** @return true if this row can be imported */
	public boolean isImportable()
	{
		if (getImpFormat() == null)
		{
			return false;
		}
		if (importStatus == ImportStatus.ImportScheduled)
		{
			return false;
		}

		if (hasCellErrors())
		{
			return false;
		}

		return true;
	}

	/** @return true if at least one cell has errors */
	private boolean hasCellErrors()
	{
		final List<ImpDataCell> values = getValues();
		for (final ImpDataCell value : values)
		{
			if (value.isCellError())
			{
				return true;
			}
		}

		return false;
	}

	/** @return the import status of this line; never return null */
	public ImportStatus getImportStatus()
	{
		return importStatus;
	}

	public String getImportErrorMessage()
	{
		if (importError == null)
		{
			return null;
		}
		return importError.getLocalizedMessage();
	}

	public ITableRecordReference getImportRecordRef()
	{
		return importRecordRef;
	}

	public void importToDB()
	{
		final ImpFormat impFormat = getImpFormat();
		Check.assumeNotNull(impFormat, "impFormat not null");
		Services.get(ITrxManager.class).run(ITrx.TRXNAME_ThreadInherited, new TrxRunnableAdapter()
		{
			private Throwable error = null;
			private ITableRecordReference importRecordRef;

			@Override
			public void run(String localTrxName) throws Exception
			{
				importRecordRef = impFormat.updateDB(Env.getCtx(), ImpDataLine.this, localTrxName);
			}

			@Override
			public boolean doCatch(Throwable e) throws Throwable
			{
				error = e;
				return true;
			}

			@Override
			public void doFinally()
			{
				if (error == null)
				{
					setImportStatus_ImportPrepared(importRecordRef);
				}
				else
				{
					setImportStatus_Error(error);
				}
			}
		});
	}
}
