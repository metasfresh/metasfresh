/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.serviceprovider.github;

import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.serviceprovider.issue.IssueEntity;
import de.metas.serviceprovider.issue.IssueId;
import de.metas.serviceprovider.issue.IssueRepository;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

public class GithubImportSingleSelection extends GithubImportProcess implements IProcessPrecondition
{
	private final IssueRepository issueRepository = SpringContextHolder.instance.getBean(IssueRepository.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.getSelectionSize().isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		else if (context.getSelectionSize().isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final IssueEntity issueEntity = issueRepository.getById(IssueId.ofRepoId(getRecord_ID()));

		if (issueEntity.getExternalProjectReferenceId() == null
				|| issueEntity.getExternalIssueNo() == null
				|| issueEntity.getExternalIssueNo().signum() == 0)
		{
			throw new AdempiereException("The selected issue was not imported from Github!")
					.appendParametersToMessage()
					.setParameter("IssueEntity", issueEntity);
		}

		setExternalProjectReferenceId(issueEntity.getExternalProjectReferenceId());
		setIssueNumbers(issueEntity.getExternalIssueNo().toString());

		return super.doIt();
	}
}
