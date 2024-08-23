package de.metas.ad_reference;

import com.google.common.base.MoreObjects;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.common.util.TryAndWaitUtil;
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
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class AdRefListRepositoryOverJdbc implements AdRefListRepository
{
	private static final Logger logger = LogManager.getLogger(AdRefListRepositoryOverJdbc.class);
	public static final int SINGLE_CACHE_KEY = 0;

	/**
	 * When calling createRefListItem + getById + createRefListItem for the same ref-list value fast (in cucumber),
	 * then sometimes getById does not contain the previously created ref-list and then the 2nd createRefListItem call fails with a DBUniqueConstraintException.
	 * The only reasons i can think of are
	 * <li>that cache.clear() doesn't cause the cache to be really empty **before** it returns</li>
	 * <li>that there is some other element of concurrency between reading and writing</li>
	 */
	private final transient ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

	private final CCache<Integer, AdRefListsMap> cache = CCache.<Integer, AdRefListsMap>builder()
			.tableName(I_AD_Ref_List.Table_Name)
			.additionalTableNameToResetFor(I_AD_Reference.Table_Name)
			.initialCapacity(1)
			.expireMinutes(CCache.EXPIREMINUTES_Never)
			.build();

	@Override
	public void createRefListItem(@NonNull final ADRefListItemCreateRequest request)
	{
		readWriteLock.writeLock().lock();
		try
		{
			final I_AD_Ref_List record = newInstanceOutOfTrx(I_AD_Ref_List.class);

			record.setAD_Reference_ID(request.getReferenceId().getRepoId());

			record.setName(request.getName().getDefaultValue());
			record.setValue(request.getValue());
			try
			{
				saveRecord(record);
			}
			catch (final AdempiereException e)
			{
				throw AdempiereException.wrapIfNeeded(e)
						.appendParametersToMessage()
						.setParameter("Request", request);
			}
			cache.reset();

			// make sure that the cached value is really gone!
			TryAndWaitUtil.tryAndWait(3, 100, () -> cache.get(SINGLE_CACHE_KEY) == null, () -> logger.warn("AdRefListRepositoryOverJdbc - cache not cleared after 3seconds!"));
		}
		catch (final InterruptedException e)
		{
			throw AdempiereException.wrapIfNeeded(e).appendParametersToMessage().appendParametersToMessage().setParameter("Request", request);
		}
		finally
		{
			readWriteLock.writeLock().unlock();
		}
	}

	@Override
	public ADRefList getById(final ReferenceId adReferenceId)
	{
		readWriteLock.readLock().lock();
		try
		{
			return getMap().getById(adReferenceId);
		}
		finally
		{
			readWriteLock.readLock().unlock();
		}
	}

	private AdRefListsMap getMap()
	{
		return cache.getOrLoad(SINGLE_CACHE_KEY, this::retrieveMap);
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

		@NonNull
		final ImmutableList<ADRefList> lists = DB.retrieveRows("SELECT "
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
