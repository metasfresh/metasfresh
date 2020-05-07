/**
 *
 */
package org.adempiere.ad.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.UpperCaseQueryFilterModifier;
import org.adempiere.ad.service.IADElementDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Field;

import lombok.NonNull;

/**
 * @author cg
 *
 */
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

}
