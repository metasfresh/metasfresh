package de.metas.impexp.excel;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.Nullable;

import org.adempiere.exceptions.DBException;
import org.compiere.util.Env;

import de.metas.impexp.excel.service.DataConsumer;
import de.metas.impexp.excel.service.ExcelExporterService;
import de.metas.util.lang.CoalesceUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * DataConsumer that consumes a jdbc-{@link ResultSet} towards an excel file.
 * What it does is providing an {@link AbstractExcelExporter} that can be "filled" by {@link ExcelExporterService} from an open {@link ResultSet}.
 */
public class JdbcExcelExporter
		extends AbstractExcelExporter
		implements DataConsumer<ResultSet>
{
	private final Properties m_ctx;
	private ResultSet m_resultSet;
	private List<String> m_columnHeaders;
	private final boolean translateHeaders;

	@Getter
	private File resultFile;

	@Getter
	private boolean noDataAddedYet;

	@Builder
	private JdbcExcelExporter(
			@Nullable final ExcelFormat excelFormat,
			@Nullable final ExcelExportConstants constants,
			@Nullable final Properties ctx,
			@Nullable final File resultFile,
			@Nullable final List<String> columnHeaders,
			@Nullable final Boolean translateHeaders)
	{
		super(excelFormat, constants);
		m_columnHeaders = columnHeaders;

		this.m_ctx = ctx != null ? ctx : Env.getCtx();

		this.translateHeaders = CoalesceUtil.coalesce(translateHeaders, true);
		this.resultFile = resultFile;
		this.noDataAddedYet = true;
	}

	@Override
	public void putHeader(@NonNull final List<String> header)
	{
		m_columnHeaders = header;
	}

	@Override
	public void putResult(@NonNull final ResultSet result)
	{
		m_resultSet = result;
		if (resultFile != null)
		{
			exportToFile(resultFile);
		}
		else
		{
			resultFile = exportToTempFile();
		}
	}

	@Override
	protected Properties getCtx()
	{
		return m_ctx;
	}

	@Override
	public int getColumnCount()
	{
		try
		{
			return m_resultSet.getMetaData().getColumnCount();
		}
		catch (SQLException e)
		{
			throw DBException.wrapIfNeeded(e);
		}
	}

	@Override
	public int getDisplayType(final int IGNORED, final int col)
	{
		final Object value = getValue(col);
		return CellValues.extractDisplayTypeFromValue(value);
	}

	@Override
	public String getHeaderName(final int col)
	{
		String headerName;
		if (m_columnHeaders == null || m_columnHeaders.isEmpty())
		{
			final Object headerNameObj = getValue(col);
			headerName = headerNameObj != null ? headerNameObj.toString() : null;
		}
		else
		{
			headerName = m_columnHeaders.get(col);
		}

		if (translateHeaders)
		{
			final String adLanguage = getLanguage().getAD_Language();
			return msgBL.translatable(headerName).translate(adLanguage);
		}
		else
		{
			return headerName;
		}
	}

	private Object getValue(final int col)
	{
		try
		{
			return m_resultSet.getObject(col + 1);
		}
		catch (SQLException e)
		{
			throw DBException.wrapIfNeeded(e);
		}
	}

	@Override
	public int getRowCount()
	{
		return -1; // TODO remove
	}

	@Override
	public boolean isColumnPrinted(final int col)
	{
		return true;
	}

	@Override
	public boolean isFunctionRow(final int row)
	{
		return false;
	}

	@Override
	public boolean isPageBreak(final int row, final int col)
	{
		return false;
	}

	@Override
	protected List<CellValue> getNextRow()
	{
		try
		{
			return getNextRow0();
		}
		catch (SQLException e)
		{
			throw DBException.wrapIfNeeded(e);
		}
	}

	private List<CellValue> getNextRow0() throws SQLException
	{
		final List<CellValue> row = new ArrayList<>();
		for (int col = 1; col <= getColumnCount(); col++)
		{
			final Object o = m_resultSet.getObject(col);
			row.add(CellValues.toCellValue(o));
		}
		noDataAddedYet = false;
		return row;
	}

	@Override
	protected boolean hasNextRow()
	{
		try
		{
			return m_resultSet.next();
		}
		catch (SQLException e)
		{
			throw DBException.wrapIfNeeded(e);
		}
	}

}
