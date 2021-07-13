package de.metas.ui.web.window.model;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.LogicExpressionResult;

import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.exceptions.InvalidDocumentStateException;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@ToString(exclude = { "allowCreateNewLogic", "allowDeleteLogic" })
public final class IncludedDocumentsCollectionActions
{
	private static final LogicExpressionResult DISALLOW_Initially = LogicExpressionResult.namedConstant("initially not allowed", false);
	private static final LogicExpressionResult DISALLOW_ParentDocumentProcessed = LogicExpressionResult.namedConstant("ParentDocumentProcessed", false);
	private static final LogicExpressionResult DISALLOW_ParentDocumentNotActive = LogicExpressionResult.namedConstant("ParentDocumentNotActive", false);
	private static final LogicExpressionResult DISALLOW_ParentDocumentNew = LogicExpressionResult.namedConstant("ParentDocumentNew", false);
	private static final LogicExpressionResult DISALLOW_ParentDocumentInvalid = LogicExpressionResult.namedConstant("ParentDocumentInvalid", false);
	private static final LogicExpressionResult DISALLOW_AnotherNewDocumentAlreadyExists = LogicExpressionResult.namedConstant("A new document already exists", false);
	private static final LogicExpressionResult DISALLOW_UnsavedRowFound = LogicExpressionResult.namedConstant("Unsaved row found", false);

	private final DetailId detailId;
	private final ILogicExpression allowCreateNewLogic;
	private final ILogicExpression allowDeleteLogic;
	private final DocumentPath parentDocumentPath;

	private LogicExpressionResult allowNew = DISALLOW_Initially;
	private LogicExpressionResult allowDelete = DISALLOW_Initially;

	@Builder
	private IncludedDocumentsCollectionActions(
			@NonNull final DetailId detailId,
			@NonNull final ILogicExpression allowCreateNewLogic,
			@NonNull final ILogicExpression allowDeleteLogic,
			@NonNull final DocumentPath parentDocumentPath)
	{
		this.detailId = detailId;
		this.allowCreateNewLogic = allowCreateNewLogic;
		this.allowDeleteLogic = allowDeleteLogic;
		this.parentDocumentPath = parentDocumentPath;
	}

	private IncludedDocumentsCollectionActions(final IncludedDocumentsCollectionActions from)
	{
		detailId = from.detailId;
		allowCreateNewLogic = from.allowCreateNewLogic;
		allowDeleteLogic = from.allowDeleteLogic;
		parentDocumentPath = from.parentDocumentPath;

		allowNew = from.allowNew;
		allowDelete = from.allowDelete;
	}

	public IncludedDocumentsCollectionActions copy()
	{
		return new IncludedDocumentsCollectionActions(this);
	}

	public LogicExpressionResult getAllowCreateNewDocument()
	{
		return allowNew;
	}

	public void updateAndAssertAlowCreateNew(final IncludedDocumentsCollectionActionsContext context)
	{
		final LogicExpressionResult allowCreateNewDocument = updateAndGetAllowCreateNewDocument(context);
		if (allowCreateNewDocument.isFalse())
		{
			throw new InvalidDocumentStateException(parentDocumentPath, "Cannot create included document because it's not allowed.")
					.setParameter("allowCreateNewDocument", allowCreateNewDocument)
					.setParameter("allowCreateNewLogic", allowCreateNewLogic)
					.setParameter("detailId", detailId)
					.setParameter("context", context);
		}

	}

	public LogicExpressionResult updateAndGetAllowCreateNewDocument(final IncludedDocumentsCollectionActionsContext context)
	{
		final LogicExpressionResult allowNew = computeAllowCreateNewDocument(context);
		return setAllowNewAndCollect(allowNew, context);
	}

	private LogicExpressionResult setAllowNewAndCollect(final LogicExpressionResult allowNew, final IncludedDocumentsCollectionActionsContext context)
	{
		final LogicExpressionResult allowNewOld = this.allowNew;
		this.allowNew = allowNew;

		if (!allowNewOld.equalsByNameAndValue(allowNew))
		{
			context.collectAllowNew(parentDocumentPath, detailId, allowNew);
		}

		return allowNew;
	}

	private LogicExpressionResult computeAllowCreateNewDocument(final IncludedDocumentsCollectionActionsContext context)
	{
		// Quickly check if the allowCreateNew logic it's constant and it's false.
		// In that case we can return right away.
		if (allowCreateNewLogic.isConstantFalse())
		{
			return LogicExpressionResult.ofConstantExpression(allowCreateNewLogic);
		}

		if (context.isParentDocumentProcessed())
		{
			return DISALLOW_ParentDocumentProcessed;
		}

		if (!context.isParentDocumentActive())
		{
			return DISALLOW_ParentDocumentNotActive;
		}

		if (context.isParentDocumentNew())
		{
			return DISALLOW_ParentDocumentNew;
		}
		
		if(context.isParentDocumentInvalid())
		{
			return DISALLOW_ParentDocumentInvalid;
		}

		//
		// Check all included documents and don't allow creating new documents if:
		// * if there is a new included document
		// * if one of the included documents were not already saved
		{
			final LogicExpressionResult allowCreateNew = context.getIncludedDocuments()
					.stream()
					.map(includedDocument -> {
						if (includedDocument.isNew())
						{
							return DISALLOW_AnotherNewDocumentAlreadyExists;
						}
						else if (!includedDocument.getSaveStatus().isSavedOrDeleted())
						{
							return DISALLOW_UnsavedRowFound;
						}
						else
						{
							return null;
						}
					})
					.filter(result -> result != null)
					.findFirst().orElse(null);
			if (allowCreateNew != null && allowCreateNew.isFalse())
			{
				return allowCreateNew;
			}
		}

		//
		// Evaluate the allowCreateNew logic expression
		final LogicExpressionResult allowCreateNew = allowCreateNewLogic.evaluateToResult(context.toEvaluatee(), OnVariableNotFound.ReturnNoResult);
		return allowCreateNew;
	}

	public LogicExpressionResult getAllowDeleteDocument()
	{
		return allowDelete;
	}

	public void assertDeleteDocumentAllowed(final IncludedDocumentsCollectionActionsContext context)
	{
		final LogicExpressionResult allowDelete = updateAndGetAllowDeleteDocument(context);
		if (allowDelete.isFalse())
		{
			throw new InvalidDocumentStateException(parentDocumentPath, "Cannot delete included document because it's not allowed: " + allowDelete);
		}
	}

	public LogicExpressionResult updateAndGetAllowDeleteDocument(final IncludedDocumentsCollectionActionsContext context)
	{
		final LogicExpressionResult allowDeleteOld = allowDelete;
		final LogicExpressionResult allowDelete = computeAllowDeleteDocument(context);
		this.allowDelete = allowDelete;

		if (!allowDeleteOld.equalsByNameAndValue(allowDelete))
		{
			context.collectAllowDelete(parentDocumentPath, detailId, allowDelete);
		}

		return allowDelete;
	}

	private LogicExpressionResult computeAllowDeleteDocument(final IncludedDocumentsCollectionActionsContext context)
	{
		// Quickly check if the allowDelete logic it's constant and it's false.
		// In that case we can return right away.
		if (allowDeleteLogic.isConstantFalse())
		{
			return LogicExpressionResult.ofConstantExpression(allowDeleteLogic);
		}

		if (context.isParentDocumentProcessed())
		{
			return DISALLOW_ParentDocumentProcessed;
		}

		if (!context.isParentDocumentActive())
		{
			return DISALLOW_ParentDocumentNotActive;
		}

		final LogicExpressionResult allowDelete = allowDeleteLogic.evaluateToResult(context.toEvaluatee(), OnVariableNotFound.ReturnNoResult);
		return allowDelete;
	}

	public void onNewDocument(final Document document, final IncludedDocumentsCollectionActionsContext context)
	{
		if (document.isNew())
		{
			setAllowNewAndCollect(DISALLOW_AnotherNewDocumentAlreadyExists, context);
		}
	}
}
