package de.metas.ad_reference;

import com.google.common.base.MoreObjects;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.i18n.ImmutableTranslatableString;
import de.metas.logging.LogManager;
import de.metas.util.ColorId;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Ref_List;
import org.compiere.model.I_AD_Reference;
import org.compiere.model.X_AD_Reference;
import org.compiere.util.DB;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class AdRefListRepositoryOverJdbc implements AdRefListRepository
{
	private static final Logger logger = LogManager.getLogger(AdRefListRepositoryOverJdbc.class);

	private final CCache<Integer, AdRefListsMap> cache = CCache.<Integer, AdRefListsMap>builder()
			.tableName(I_AD_Ref_List.Table_Name)
			.additionalTableNameToResetFor(I_AD_Reference.Table_Name)
			.initialCapacity(1)
			.expireMinutes(CCache.EXPIREMINUTES_Never)
			.build();

	@Override
	public void createRefListItem(@NonNull final ADRefListItemCreateRequest request)
	{
		final I_AD_Ref_List record = newInstanceOutOfTrx(I_AD_Ref_List.class);

		record.setAD_Reference_ID(request.getReferenceId().getRepoId());

		record.setName(request.getName().getDefaultValue());
		record.setValue(request.getValue());

		saveRecord(record);

		cache.reset();
	}

	@Override
	public ADRefList getById(final ReferenceId adReferenceId)
	{
		return getMap().getById(adReferenceId);
	}

	private AdRefListsMap getMap()
	{
		return cache.getOrLoad(0, this::retrieveMap);
	}

	private AdRefListsMap retrieveMap()
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		final ImmutableListMultimap<ReferenceId, ADRefListItem> itemsByReferenceId = DB.retrieveRows(
						"SELECT "
								+ " rl." + I_AD_Ref_List.COLUMNNAME_AD_Reference_ID
								+ " , rl." + I_AD_Ref_List.COLUMNNAME_AD_Ref_List_ID
								+ " , rl." + I_AD_Ref_List.COLUMNNAME_Value
								+ " , rl." + I_AD_Ref_List.COLUMNNAME_ValueName
								+ " , rl." + I_AD_Ref_List.COLUMNNAME_Name
								+ " , rl." + I_AD_Ref_List.COLUMNNAME_Description
								+ " , rl." + I_AD_Ref_List.COLUMNNAME_AD_Color_ID
								+ ", (SELECT array_agg(ARRAY[trl.ad_language, trl.name, trl.description]) FROM ad_ref_list_trl trl WHERE trl.ad_ref_list_id = rl.ad_ref_list_id) AS trls"
								+ " FROM " + I_AD_Ref_List.Table_Name + " rl "
								+ " WHERE rl." + I_AD_Ref_List.COLUMNNAME_IsActive + "=?"
								+ " ORDER BY rl.AD_Reference_ID, rl.AD_Ref_List_ID",
						Collections.singletonList(true),
						AdRefListRepositoryOverJdbc::retrieveADRefListItem)
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(ADRefListItem::getReferenceId, item -> item));

		@NonNull final ImmutableList<ADRefList> lists = DB.retrieveRows("SELECT "
						+ " r." + I_AD_Reference.COLUMNNAME_AD_Reference_ID
						+ " , r." + I_AD_Reference.COLUMNNAME_Name
						+ " , r." + I_AD_Reference.COLUMNNAME_IsOrderByValue
						+ " FROM " + I_AD_Reference.Table_Name + " r"
						+ " WHERE "
						+ " r." + I_AD_Reference.COLUMNNAME_ValidationType + "=?"
						// NOTE fetch all, even if not active
						+ " ORDER BY r.AD_Reference_ID",
				ImmutableList.of(X_AD_Reference.VALIDATIONTYPE_ListValidation),
				rs -> retrieveADRefList(rs, itemsByReferenceId));

		final AdRefListsMap result = new AdRefListsMap(lists);
		logger.info("Loaded {} in {}", result, stopwatch.stop());
		return result;
	}

	private static ADRefListItem retrieveADRefListItem(final ResultSet rs) throws SQLException
	{
		final ImmutableTranslatableString.ImmutableTranslatableStringBuilder name = ImmutableTranslatableString.builder()
				.defaultValue(rs.getString(I_AD_Ref_List.COLUMNNAME_Name));

		final ImmutableTranslatableString.ImmutableTranslatableStringBuilder description = ImmutableTranslatableString.builder()
				.defaultValue(rs.getString(I_AD_Ref_List.COLUMNNAME_Description));

		final Array sqlArray = rs.getArray("trls");
		if (sqlArray != null)
		{
			final String[][] trls = (String[][])sqlArray.getArray();
			for (final String[] trl : trls)
			{
				final String adLanguage = trl[0];
				name.trl(adLanguage, trl[1]);
				description.trl(adLanguage, trl[2]);
			}
		}

		return ADRefListItem.builder()
				.referenceId(ReferenceId.ofRepoId(rs.getInt(I_AD_Ref_List.COLUMNNAME_AD_Reference_ID)))
				.refListId(ADRefListId.ofRepoId(rs.getInt(I_AD_Ref_List.COLUMNNAME_AD_Ref_List_ID)))
				.value(rs.getString(I_AD_Ref_List.COLUMNNAME_Value))
				.valueName(rs.getString(I_AD_Ref_List.COLUMNNAME_ValueName))
				.name(name.build())
				.description(description.build())
				.colorId(ColorId.ofRepoIdOrNull(rs.getInt(I_AD_Ref_List.COLUMNNAME_AD_Color_ID)))
				.build();
	}

	private static ADRefList retrieveADRefList(final ResultSet rs, final ImmutableListMultimap<ReferenceId, ADRefListItem> itemsByReferenceId) throws SQLException
	{
		final ReferenceId referenceId = ReferenceId.ofRepoId(rs.getInt(I_AD_Reference.COLUMNNAME_AD_Reference_ID));
		final ImmutableList<ADRefListItem> items = itemsByReferenceId.get(referenceId);

		return ADRefList.builder()
				.referenceId(referenceId)
				.name(rs.getString(I_AD_Reference.COLUMNNAME_Name))
				.isOrderByValue(StringUtils.toBoolean(rs.getString(I_AD_Reference.COLUMNNAME_IsOrderByValue)))
				.items(items)
				.build();
	}

	//
	//
	//

	private static final class AdRefListsMap
	{
		private final ImmutableMap<ReferenceId, ADRefList> byId;

		private AdRefListsMap(@NonNull final List<ADRefList> lists)
		{
			this.byId = Maps.uniqueIndex(lists, ADRefList::getReferenceId);
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("size", byId.size())
					.toString();
		}

		@NonNull
		public ADRefList getById(@NonNull final ReferenceId referenceId)
		{
			final ADRefList adRefList = byId.get(referenceId);
			if (adRefList == null)
			{
				throw new AdempiereException("No List Reference found for " + referenceId);
			}
			return adRefList;
		}
	}
}
