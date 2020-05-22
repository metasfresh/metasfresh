package de.metas.error;

import org.adempiere.ad.element.api.AdWindowId;

import de.metas.util.ISingletonService;

/**
 * System Error Manager. This subsystem is responsible with error logging, AD_Issue creation etc.
 *
 * @author tsa
 *
 */
public interface IErrorManager extends ISingletonService
{

	AdWindowId AD_ISSUE_WINDOW_ID = AdWindowId.ofRepoId(363);

	/**
	 * Creates, saves and returns an {@link AdIssueId} based on given {@link Throwable} object.
	 */
	AdIssueId createIssue(Throwable t);

	AdIssueId createIssue(IssueCreateRequest request);
}
