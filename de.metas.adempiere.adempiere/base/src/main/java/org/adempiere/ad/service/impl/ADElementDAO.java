/**
 *
 */
package org.adempiere.ad.service.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.UpperCaseQueryFilterModifier;
import org.adempiere.ad.element.api.AdElementId;
import org.adempiere.ad.element.api.CreateADElementRequest;
import org.adempiere.ad.service.IADElementDAO;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.util.LegacyAdapters;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_Menu;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_Window;
import org.compiere.model.MColumn;

import de.metas.util.Services;
import lombok.NonNull;

/**
 * @author cg
 *
 */
// TODO: merge into ElementDAO
public class ADElementDAO implements IADElementDAO
{

	@Override
	public List<I_AD_Column> retrieveColumns(final int elementId)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_Column.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Column.COLUMN_AD_Element_ID, elementId)
				.orderBy().addColumn(I_AD_Column.COLUMNNAME_ColumnName).endOrderBy()
				.create()
				.list(I_AD_Column.class);
	}

	@Override
	public I_AD_Element getADElement(@NonNull final String columnName)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_Element.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Element.COLUMNNAME_ColumnName, columnName, UpperCaseQueryFilterModifier.instance)
				.create()
				.firstOnly(I_AD_Element.class);
	}

	@Override
	public List<I_AD_Field> retrieveFields(@NonNull final String columnName)
	{
		final List<I_AD_Field> fields = new ArrayList<>();

		final I_AD_Element element = getADElement(columnName);
		if (element != null)
		{
			final List<I_AD_Column> columns = retrieveColumns(element.getAD_Element_ID());

			for (final I_AD_Column c : columns)
			{
				final List<I_AD_Field> list = Services.get(IQueryBL.class).createQueryBuilder(I_AD_Field.class)
						.addOnlyActiveRecordsFilter()
						.addEqualsFilter(I_AD_Field.COLUMN_AD_Column_ID, c.getAD_Column_ID())
						.orderBy().addColumn(I_AD_Field.COLUMNNAME_Name).endOrderBy()
						.create()
						.list(I_AD_Field.class);
				fields.addAll(list);
			}
		}
		return fields;
	}

	@Override
	public void makeElementMandatoryInApplicationDictionaryTables()
	{
		make_AD_Element_Mandatory_In_AD_Tab();
		make_AD_Element_Mandatory_In_AD_Window();
		make_AD_Element_Mandatory_In_AD_Menu();
	}

	private void make_AD_Element_Mandatory_In_AD_Menu()
	{
		final I_AD_Column elementIdColumn = Services.get(IADTableDAO.class).retrieveColumn(I_AD_Menu.Table_Name, I_AD_Menu.COLUMNNAME_AD_Element_ID);

		makeElementColumnMandatory(elementIdColumn);
	}

	private void make_AD_Element_Mandatory_In_AD_Window()
	{
		final I_AD_Column elementIdColumn = Services.get(IADTableDAO.class).retrieveColumn(I_AD_Window.Table_Name, I_AD_Window.COLUMNNAME_AD_Element_ID);

		makeElementColumnMandatory(elementIdColumn);
	}

	private void make_AD_Element_Mandatory_In_AD_Tab()
	{
		final I_AD_Column elementIdColumn = Services.get(IADTableDAO.class).retrieveColumn(I_AD_Tab.Table_Name, I_AD_Tab.COLUMNNAME_AD_Element_ID);

		makeElementColumnMandatory(elementIdColumn);
	}

	private void makeElementColumnMandatory(final I_AD_Column elementIdColumn)
	{
		elementIdColumn.setIsMandatory(true);
		save(elementIdColumn);

		final MColumn columnPO = LegacyAdapters.convertToPO(elementIdColumn);
		columnPO.syncDatabase();
	}

	@Override
	public I_AD_Element getById(final int elementId)
	{
		return loadOutOfTrx(elementId, I_AD_Element.class);
	}

	@Override
	public AdElementId createNewElement(@NonNull final CreateADElementRequest request)
	{
		final I_AD_Element record = newInstance(I_AD_Element.class);
		record.setName(request.getName());
		record.setPrintName(request.getPrintName());
		record.setDescription(request.getDescription());
		record.setHelp(request.getHelp());
		record.setCommitWarning(request.getTabCommitWarning());
		saveRecord(record);

		return AdElementId.ofRepoId(record.getAD_Element_ID());
	}
}
