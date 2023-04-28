package de.metas.impexp.parser.xls;

import de.metas.impexp.parser.ErrorMessage;
import de.metas.impexp.parser.ImpDataCell;
import de.metas.impexp.parser.ImpDataLine;
import de.metas.impexp.parser.ImpDataParser;
import de.metas.util.NumberUtils;
import lombok.Builder;
import org.adempiere.exceptions.AdempiereException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Stream;

public class Excel97ImpDataParser implements ImpDataParser
{
	private final int skipFirstNRows;

	@Builder
	private Excel97ImpDataParser(
			final int skipFirstNRows
	)
	{
		this.skipFirstNRows = Math.max(skipFirstNRows, 0);
	}

	@Override
	public Stream<ImpDataLine> streamDataLines(final Resource resource)
	{
		final HSSFWorkbook workbook;
		try
		{
			workbook = new HSSFWorkbook(resource.getInputStream());
		}
		catch (IOException ex)
		{
			throw new AdempiereException("Failed opening " + resource, ex);
		}
		final HSSFSheet sheet = workbook.getSheetAt(0);

		final int firstRowNum = sheet.getFirstRowNum();
		final int lastRowNum = sheet.getLastRowNum();
		final ArrayList<ImpDataLine> lines = new ArrayList<>(Math.max(lastRowNum, 1));
		for (int rowIndex = firstRowNum + skipFirstNRows; rowIndex <= lastRowNum; rowIndex++)
		{
			final HSSFRow row = sheet.getRow(rowIndex);
			final ImpDataLine line = toImpDataLine(row, rowIndex);
			lines.add(line);
		}

		return lines.stream();
	}

	private ImpDataLine toImpDataLine(final HSSFRow row, int rowIndex)
	{
		try
		{
			final short firstCellNum = row.getFirstCellNum();
			final short lastCellNum = row.getLastCellNum();
			final ArrayList<ImpDataCell> cells = new ArrayList<>(Math.max(lastCellNum, 0));
			for (short cellnum = firstCellNum; cellnum < lastCellNum; cellnum++)
			{
				final ImpDataCell cell = toImpDataCell(row.getCell(cellnum));
				cells.add(cell);
			}

			return ImpDataLine.builder().fileLineNo(rowIndex).cells(cells).build();
		}
		catch (Exception ex)
		{
			return ImpDataLine.builder().fileLineNo(rowIndex).parseError(ErrorMessage.of(ex)).build();
		}
	}

	private static ImpDataCell toImpDataCell(final HSSFCell cell)
	{
		if (cell == null)
		{
			return ImpDataCell.value(null);
		}
		try
		{
			switch (cell.getCellTypeEnum())
			{
				case NUMERIC:
					if (HSSFDateUtil.isCellDateFormatted(cell))
					{
						final Date value = cell.getDateCellValue();
						return ImpDataCell.value(value);
					}
					else
					{
						final BigDecimal value = getValueAsBigDecimal(cell);
						return ImpDataCell.value(value);
					}
				case STRING:
					return ImpDataCell.value(cell.getStringCellValue());
				case BOOLEAN:
					return ImpDataCell.value(cell.getBooleanCellValue());
				case _NONE:
				case FORMULA:// not supported
				case BLANK:
				case ERROR:
				default:
					return ImpDataCell.value(null);
			}
		}
		catch (Exception ex)
		{
			return ImpDataCell.error(ErrorMessage.of(ex));
		}
	}

	private static BigDecimal getValueAsBigDecimal(final HSSFCell cell)
	{
		BigDecimal value = BigDecimal.valueOf(cell.getNumericCellValue());
		value = NumberUtils.stripTrailingDecimalZeros(value);
		return value;
	}
}
