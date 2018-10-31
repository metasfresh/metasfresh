package de.metas.document.engine;

import de.metas.util.ISingletonService;

/**
 * DocAction options retrieval and mapping, using {@link IDocActionOptionsContext}
 *
 * @author al
 */
public interface IDocActionOptionsBL extends ISingletonService
{
	void updateDocActions(DocActionOptionsContext optionsCtx);
}
