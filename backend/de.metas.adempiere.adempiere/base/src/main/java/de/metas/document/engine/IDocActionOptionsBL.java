package de.metas.document.engine;

import de.metas.util.ISingletonService;

import javax.annotation.Nullable;
import java.util.Set;

/**
 * DocAction options retrieval and mapping, using {@link DocActionOptionsContext}
 *
 * @author al
 */
public interface IDocActionOptionsBL extends ISingletonService
{
	Set<String> getRequiredParameters(@Nullable String contextTableName);

	void updateDocActions(DocActionOptionsContext optionsCtx);
}
