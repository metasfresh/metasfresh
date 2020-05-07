package org.adempiere.ad.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Ref_List;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableMap;

import de.metas.i18n.ForwardingTranslatableString;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.ITranslatableString;

public class ADReferenceDAO implements IADReferenceDAO
{
	@Override
	public Collection<ADRefListItem> retrieveListItems(final int adReferenceId)
	{
		final Map<String, ADRefListItem> itemsMap = retrieveListValuesMap(adReferenceId);
		return itemsMap.values();
	}

	@Override
	public Set<String> retrieveListValues(final int adReferenceId)
	{
		final Map<String, ADRefListItem> itemsMap = retrieveListValuesMap(adReferenceId);
		return itemsMap.keySet();
	}

	@Cached(cacheName = I_AD_Ref_List.Table_Name + "#by#" + I_AD_Ref_List.COLUMNNAME_AD_Reference_ID + "#asMap", expireMinutes = Cached.EXPIREMINUTES_Never)
	@Override
	public Map<String, ADRefListItem> retrieveListValuesMap(final int adReferenceId)
	{

		final IQueryBuilder<I_AD_Ref_List> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Ref_List.class, Env.getCtx(), ITrx.TRXNAME_None);

		queryBuilder.getCompositeFilter()
				.addEqualsFilter(I_AD_Ref_List.COLUMNNAME_AD_Reference_ID, adReferenceId)
				.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_AD_Ref_List.COLUMNNAME_AD_Ref_List_ID);

		final List<I_AD_Ref_List> items = queryBuilder.create().list(I_AD_Ref_List.class);
		if (items.isEmpty())
		{
			return ImmutableMap.of();
		}

		final Map<String, ADRefListItem> itemsMap = new HashMap<>(items.size());
		for (final I_AD_Ref_List item : items)
		{
			final String value = item.getValue();
			final IModelTranslationMap itemTrl = InterfaceWrapperHelper.getModelTranslationMap(item);
			itemsMap.put(value, ADRefListItem.builder()
					.value(value)
					.valueName(item.getValueName())
					.name(itemTrl.getColumnTrl(I_AD_Ref_List.COLUMNNAME_Name, item.getName()))
					.description(itemTrl.getColumnTrl(I_AD_Ref_List.COLUMNNAME_Description, item.getDescription()))
					.refListId(item.getAD_Ref_List_ID())
					.build());
		}

		return ImmutableMap.copyOf(itemsMap);
	}

	@Override
	public ADRefListItem retrieveListItemOrNull(final int adReferenceId, final String value)
	{
		final Map<String, ADRefListItem> itemsMap = retrieveListValuesMap(adReferenceId);
		final ADRefListItem item = itemsMap.get(value);
		return item;
	}

	@Override
	public boolean existListValue(final int adReferenceId, final String value)
	{
		final ADRefListItem item = retrieveListItemOrNull(adReferenceId, value);
		return item != null;
	}

	@Override
	public String retrieveListNameTrl(final Properties ctx, final int adReferenceId, final String value)
	{
		final ADRefListItem item = retrieveListItemOrNull(adReferenceId, value);
		if (item == null)
		{
			return value;
		}

		return item.getName().translate(Env.getAD_Language(ctx));
	}

	@Override
	public ITranslatableString retrieveListNameTranslatableString(final int adReferenceId, final String value)
	{
		Check.assume(adReferenceId > 0, "adReferenceId > 0");
		Check.assumeNotEmpty(value, "value is not empty");

		// NOTE: we are wrapping everything in a forwarding translatable string,
		// because we want this code to be called each time, just in case of a cache reset,
		// or the translation changes.
		return ForwardingTranslatableString.of(() -> {
			final ADRefListItem item = retrieveListItemOrNull(adReferenceId, value);
			return item != null ? item.getName() : ITranslatableString.constant(value);
		});
	}
}
