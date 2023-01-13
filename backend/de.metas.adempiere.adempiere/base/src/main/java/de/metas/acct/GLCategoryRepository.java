package de.metas.acct;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.service.ClientId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_GL_Category;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class GLCategoryRepository
{
	public static GLCategoryRepository get() {return SpringContextHolder.instance.getBean(GLCategoryRepository.class);}

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private static final CCache<ClientId, DefaultGLCategories> defaultsCache = CCache.<ClientId, DefaultGLCategories>builder()
			.tableName(I_GL_Category.Table_Name)
			.build();

	public Optional<GLCategoryId> getDefaultId(@NonNull final ClientId clientId, @NonNull final GLCategoryType categoryType)
	{
		return getDefaults(clientId).getByCategoryType(categoryType);
	}

	public Optional<GLCategoryId> getDefaultId(@NonNull final ClientId clientId)
	{
		return getDefaults(clientId).getDefaultId();
	}

	private DefaultGLCategories getDefaults(final ClientId clientId)
	{
		return defaultsCache.getOrLoad(clientId, this::retrieveDefaults);
	}

	private DefaultGLCategories retrieveDefaults(final ClientId clientId)
	{
		final ImmutableList<GLCategory> list = queryBL.createQueryBuilder(I_GL_Category.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_GL_Category.COLUMNNAME_AD_Client_ID, clientId)
				.addEqualsFilter(I_GL_Category.COLUMNNAME_IsDefault, true)
				.orderBy(I_GL_Category.COLUMNNAME_GL_Category_ID) // just to have a predictable order
				.create()
				.stream()
				.map(GLCategoryRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return new DefaultGLCategories(list);
	}

	private static GLCategory fromRecord(@NonNull final I_GL_Category record)
	{
		return GLCategory.builder()
				.id(GLCategoryId.ofRepoId(record.getGL_Category_ID()))
				.name(record.getName())
				.categoryType(GLCategoryType.ofCode(record.getCategoryType()))
				.isDefault(record.isDefault())
				.clientId(ClientId.ofRepoId(record.getAD_Client_ID()))
				.build();
	}

	private static class DefaultGLCategories
	{
		private final ImmutableMap<GLCategoryType, GLCategory> byCategoryType;
		private final ImmutableList<GLCategory> list;

		DefaultGLCategories(final ImmutableList<GLCategory> list)
		{
			this.byCategoryType = Maps.uniqueIndex(list, GLCategory::getCategoryType);
			this.list = list;
		}

		public Optional<GLCategoryId> getByCategoryType(@NonNull final GLCategoryType categoryType)
		{
			final GLCategory category = byCategoryType.get(categoryType);
			if (category != null)
			{
				return Optional.of(category.getId());
			}

			return getDefaultId();
		}

		public Optional<GLCategoryId> getDefaultId()
		{
			return !list.isEmpty()
					? Optional.of(list.get(0).getId())
					: Optional.empty();
		}
	}
}
