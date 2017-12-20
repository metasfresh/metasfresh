package org.adempiere.ad.table.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Table;
import org.compiere.model.M_Element;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * Producer class used to create table and columns from a given input stream).
 * Input stream shall be like:<br>
 * * ColumName<br>
 * * AD_Reference_ID<br>
 * * length<br>
 */
public class CreateColumnsProducer
{
	public static CreateColumnsProducer newInstance()
	{
		return new CreateColumnsProducer();
	}

	private ILoggable logger;
	private String targetTableName;
	private String entityType = null;
	private byte[] fileContent;

	private CreateColumnsProducer()
	{
	}

	private void addLog(final String message)
	{
		if (logger == null)
		{
			return;
		}

		logger.addLog(message);
	}

	public CreateColumnsProducer setLogger(final ILoggable logger)
	{
		this.logger = logger;
		return this;
	}

	private String getTargetTableName()
	{
		Check.assumeNotNull(targetTableName, "targetTable not null");
		return targetTableName;
	}

	public CreateColumnsProducer setTargetTable(@NonNull final String targetTable)
	{
		targetTableName = targetTable;
		return this;
	}

	private byte[] getFileContent()
	{
		Check.assumeNotNull(fileContent, "fileContent not null");
		return fileContent;
	}

	public CreateColumnsProducer setFileContent(@NonNull final byte[] fileContent)
	{
		this.fileContent = fileContent;
		return this;
	}

	public CreateColumnsProducer setEntityType(final String entityType)
	{
		this.entityType = entityType;
		return this;
	}

	private String getEntityType()
	{
		return entityType;
	}

	public int create()
	{
		final I_AD_Table targetTable = createTargetTable();

		final List<ColumnSource> sourceColumns = createSourceColumns();

		final AtomicInteger countCreated = new AtomicInteger(0);
		sourceColumns.forEach(sourceColumn -> {

			createColumn(sourceColumn, targetTable);
			countCreated.incrementAndGet();
		});

		return countCreated.get();
	}

	@Builder
	@Getter
	public static class ColumnSource
	{
		final private int length;
		final private String name;
		final private int type;
	}

	private List<ColumnSource> createSourceColumns()
	{
		final ByteArrayInputStream input = new ByteArrayInputStream(getFileContent());
		final InputStreamReader inputStreamReader = new InputStreamReader(input);

		final List<ColumnSource> columns = new ArrayList<>();

		try (final BufferedReader reader = new BufferedReader(inputStreamReader))
		{
			String currentTextLine;
			while ((currentTextLine = reader.readLine()) != null)
			{
				if (Check.isEmpty(currentTextLine, true))
				{
					continue;
				}

				final String trimmedtextLine = currentTextLine.trim();
				final ColumnSource column = builColumnSource(trimmedtextLine);
				columns.add(column);
			}

		}
		catch (final IOException e)
		{
			throw new AdempiereException(e.getLocalizedMessage(), e);
		}

		return ImmutableList.copyOf(columns);
	}

	private ColumnSource builColumnSource(@NonNull final String esrImportLineText)
	{
		final String[] fields = esrImportLineText.split(",");
		return ColumnSource.builder()
				.name(fields[0])
				.type(Integer.valueOf(fields[1]))
				.length(Integer.valueOf(fields[2]))
				.build();
	}

	private I_AD_Table createTargetTable()
	{
		final I_AD_Table targetTable = InterfaceWrapperHelper.newInstance(I_AD_Table.class);
		targetTable.setName(getTargetTableName());
		targetTable.setTableName(getTargetTableName());
		targetTable.setEntityType(getEntityType());
		save(targetTable);
		return targetTable;
	}

	private I_AD_Column createColumn(final ColumnSource sourceColumn, final I_AD_Table targetTable)
	{

		final I_AD_Column colTarget = InterfaceWrapperHelper.newInstance(I_AD_Column.class);
		colTarget.setAD_Org_ID(0);
		colTarget.setAD_Table_ID(targetTable.getAD_Table_ID());
		colTarget.setEntityType(getEntityType());

		final Properties ctx = Env.getCtx();
		M_Element element = M_Element.get(ctx, sourceColumn.getName());
		if (element == null)
		{
			element = new M_Element(ctx, sourceColumn.getName(), targetTable.getEntityType(), ITrx.TRXNAME_ThreadInherited);
			element.setColumnName(sourceColumn.getName());
			element.setName(sourceColumn.getName());
			element.setPrintName(sourceColumn.getName());
			element.saveEx(ITrx.TRXNAME_ThreadInherited);
			addLog("@AD_Element_ID@ " + element.getColumnName() + ": @Created@"); // metas
		}
		colTarget.setAD_Element_ID(element.getAD_Element_ID());
		colTarget.setName(targetTable.getName());
		colTarget.setIsAllowLogging(false);

		colTarget.setFieldLength(sourceColumn.getLength());
		colTarget.setAD_Reference_ID(sourceColumn.getType());
		colTarget.setIsActive(true);
		colTarget.setIsUpdateable(true);
		save(colTarget);
		addLog("@AD_Column_ID@ " + targetTable.getTableName() + "." + colTarget.getColumnName() + ": @Created@");

		return colTarget;
	}

}
