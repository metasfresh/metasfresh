package org.adempiere.ad.service.impl;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.Language;
import de.metas.i18n.TranslatableStrings;
import de.metas.reflist.RefListId;
import de.metas.reflist.ReferenceId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.service.IADReferenceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Ref_List;
import org.compiere.model.I_AD_Reference;
import org.compiere.util.Env;

import java.util.Collection;
import java.util.Properties;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class ADReferenceDAO implements IADReferenceDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<ReferenceId, ADRefList> cache = CCache.<ReferenceId, ADRefList>builder()
			.tableName(I_AD_Ref_List.Table_Name)
			.build();

	@Override
	public Collection<ADRefListItem> retrieveListItems(final int adReferenceId)
	{
		return getRefListById(ReferenceId.ofRepoId(adReferenceId)).getItems();
	}

	@Override
	public Set<String> retrieveListValues(final int adReferenceId)
	{
		return getRefListById(ReferenceId.ofRepoId(adReferenceId)).getValues();
	}

	@Override
	public ADRefList getRefListById(final ReferenceId adReferenceId)
	{
		return cache.getOrLoad(adReferenceId, this::retrieveADRefList);
	}

	@Override
	public ADRefListItem retrieveListItemOrNull(final int adReferenceId, final String value)
	{
		return getRefListById(ReferenceId.ofRepoId(adReferenceId)).getItemByValue(value).orElse(null);
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

		String adLanguage = Env.getAD_Language(ctx);
		if (adLanguage == null)
		{
			adLanguage = Language.getBaseAD_Language();
		}
		return item.getName().translate(adLanguage);
	}

	@Override
	public ITranslatableString retrieveListNameTranslatableString(final int adReferenceId, final String value)
	{
		Check.assume(adReferenceId > 0, "adReferenceId > 0");
		Check.assumeNotEmpty(value, "value is not empty");

		// NOTE: we are wrapping everything in a forwarding translatable string,
		// because we want this code to be called each time, just in case of a cache reset,
		// or the translation changes.
		return TranslatableStrings.forwardingTo(() -> {
			final ADRefListItem item = retrieveListItemOrNull(adReferenceId, value);
			return item != null ? item.getName() : TranslatableStrings.constant(value);
		});
	}

	public void saveRefList(@NonNull final IADReferenceDAO.ADRefListItemCreateRequest refListItemCreateRequest)
	{
		final I_AD_Ref_List record = newInstanceOutOfTrx(I_AD_Ref_List.class);

		record.setAD_Reference_ID(refListItemCreateRequest.getReferenceId().getRepoId());

		record.setName(refListItemCreateRequest.getName().getDefaultValue());
		record.setValue(refListItemCreateRequest.getValue());

		saveRecord(record);
	}

	public I_AD_Reference getReferenceByID(@NonNull final ReferenceId referenceId)
	{
		return load(referenceId, I_AD_Reference.class);
	}

	private ADRefList retrieveADRefList(@NonNull final ReferenceId referenceId)
	{
		final ImmutableList<ADRefListItem> items = queryBL
				.createQueryBuilderOutOfTrx(I_AD_Ref_List.class)
				.addEqualsFilter(I_AD_Ref_List.COLUMNNAME_AD_Reference_ID, referenceId.getRepoId())
				.addOnlyActiveRecordsFilter()
				.orderBy(I_AD_Ref_List.COLUMNNAME_AD_Ref_List_ID)
				.create()
				.stream(I_AD_Ref_List.class)
				.map(this::toADRefListItem)
				.collect(ImmutableList.toImmutableList());

		return ADRefList.builder()
				.referenceId(referenceId)
				.items(items)
				.build();
	}

	private ADRefListItem toADRefListItem(final I_AD_Ref_List item)
	{
		final IModelTranslationMap itemTrl = InterfaceWrapperHelper.getModelTranslationMap(item);

		return ADRefListItem.builder()
				.referenceId(ReferenceId.ofRepoId(item.getAD_Reference_ID()))
				.value(item.getValue())
				.valueName(item.getValueName())
				.name(itemTrl.getColumnTrl(I_AD_Ref_List.COLUMNNAME_Name, item.getName()))
				.description(itemTrl.getColumnTrl(I_AD_Ref_List.COLUMNNAME_Description, item.getDescription()))
				.refListId(RefListId.ofRepoId(item.getAD_Ref_List_ID()))
				.build();
	}
}
