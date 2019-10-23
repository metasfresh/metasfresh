package de.metas.attachments.automaticlinksharing;

import lombok.NonNull;
import lombok.ToString;

import java.util.Collection;
import java.util.List;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.collect.ImmutableSet;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.automaticlinksharing.RecordToReferenceProviderService.ExpandResult;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

/**
 * Generic base class to expand using {@code AD_Table_ID} and {@code Record_ID}; can be extended for specific tables.
 * It is assumed that those tables have these two columns.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 * @param <T>
 */
@ToString
public abstract class TableRecordRefProvider<T> implements ReferenceableRecordsProvider
{
	private final Class<T> modelClass;
	private final String tableName;

	public TableRecordRefProvider(@NonNull final Class<T> modelClass)
	{
		this.modelClass = modelClass;
		this.tableName = InterfaceWrapperHelper.getTableName(modelClass);
	}

	/**
	 * For the given {@code recordRefs}, this method returns other records that refer to those given references via their own {@code AD_Table_ID} and {@code Record_ID}
	 */
	@Override
	public final ExpandResult expand(
			@NonNull final AttachmentEntry attachmentEntry,
			@NonNull final Collection<? extends ITableRecordReference> recordRefs)
	{
		if (recordRefs.isEmpty())
		{
			return ExpandResult.EMPTY;
		}
		if (!isExpandOnAttachmentEntry(attachmentEntry))
		{
			return ExpandResult.EMPTY;
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<T> refereningIcFilters = queryBL
				.createQueryBuilder(modelClass)
				.setJoinOr()
				.setOption(IQueryBuilder.OPTION_Explode_OR_Joins_To_SQL_Unions, true);

		for (final ITableRecordReference recordRef : recordRefs)
		{
			final ICompositeQueryFilter<T> referencingIcFilter = queryBL
					.createCompositeQueryFilter(modelClass)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(TableRecordReference.COLUMNNAME_AD_Table_ID, recordRef.getAD_Table_ID())
					.addEqualsFilter(TableRecordReference.COLUMNNAME_Record_ID, recordRef.getRecord_ID())
					.addFilter(getAdditionalFilter());

			refereningIcFilters.filter(referencingIcFilter);
		}

		final List<Integer> idsToExpand = refereningIcFilters
				.create()
				.listIds();

		final ImmutableSet<ITableRecordReference> set = idsToExpand
				.stream()
				.map(modelId -> TableRecordReference.of(tableName, modelId))
				.collect(ImmutableSet.toImmutableSet());

		return ExpandResult
				.builder()
				.additionalReferences(set)
				.build();
	}

	protected boolean isExpandOnAttachmentEntry(@NonNull final AttachmentEntry attachmentEntry)
	{
		return true;
	}

	protected IQueryFilter<T> getAdditionalFilter()
	{
		return ConstantQueryFilter.of(true);
	}

}
