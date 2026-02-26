package de.metas.ui.web.window.model.sql.save;

import com.google.common.collect.ImmutableMap;
import de.metas.ui.web.window.model.Document;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.GridTabVO;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class CompositeSaveHandlers
{
	@NonNull private final ImmutableMap<String, SaveHandler> byTableName;
	@NonNull private final DefaultSaveHandler defaultHandler;

	CompositeSaveHandlers(@NonNull final Optional<List<SaveHandler>> optionalHandlers)
	{
		this.defaultHandler = new DefaultSaveHandler();
		this.byTableName = indexByTableName(optionalHandlers);
	}

	@NotNull
	private static ImmutableMap<String, SaveHandler> indexByTableName(@NotNull final Optional<List<SaveHandler>> optionalHandlers)
	{
		final ImmutableMap.Builder<String, SaveHandler> builder = ImmutableMap.builder();
		optionalHandlers.ifPresent(handlers -> {
			for (SaveHandler handler : handlers)
			{
				final Set<String> handledTableNames = handler.getHandledTableName();
				if (handledTableNames.isEmpty())
				{
					throw new AdempiereException("SaveHandler " + handler + " has no handled table names");
				}

				handledTableNames.forEach(handledTableName -> builder.put(handledTableName, handler));
			}
		});
		return builder.build();
	}

	private SaveHandler getHandler(@NonNull final String tableName) {return byTableName.getOrDefault(tableName, defaultHandler);}

	public boolean isReadonly(@NonNull final GridTabVO gridTabVO)
	{
		final String tableName = gridTabVO.getTableName();
		return getHandler(tableName).isReadonly(gridTabVO);
	}

	public SaveHandler.SaveResult save(@NonNull final Document document)
	{
		final String tableName = document.getEntityDescriptor().getTableName();
		return getHandler(tableName).save(document);
	}

	public void delete(final Document document)
	{
		final String tableName = document.getEntityDescriptor().getTableName();
		getHandler(tableName).delete(document);
	}
}
