package de.metas.copy_with_details;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.copy_with_details.template.CopyTemplate;
import de.metas.copy_with_details.template.CopyTemplateService;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.compiere.model.PO;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

public class GeneralCopyRecordSupport implements CopyRecordSupport
{
	private static final Logger log = LogManager.getLogger(GeneralCopyRecordSupport.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final CopyTemplateService copyTemplateService = SpringContextHolder.instance.getBean(CopyTemplateService.class);

	@Nullable private String parentLinkColumnName = null;
	@Nullable private PO parentPO = null;
	@Nullable private AdWindowId adWindowId = null;

	private final ArrayList<OnRecordCopiedListener> recordCopiedListeners = new ArrayList<>();
	private final ArrayList<OnRecordCopiedListener> childRecordCopiedListeners = new ArrayList<>();

	@Override
	public final Optional<PO> copyToNew(@NonNull PO fromPO)
	{
		if (!isCopyRecord(fromPO))
		{
			return Optional.empty();
		}

		final CopyTemplate template = copyTemplateService.getCopyTemplate(fromPO.getPOInfo());

		final PO toPO = TableModelLoader.instance.newPO(fromPO.get_TableName());
		toPO.setCopying(true); // needed in case the is some before/after save logic which relies on this
		PO.copyValues(
				fromPO,
				toPO,
				context -> template.getValueToCopy(context
						.withParentPO(parentPO)
						.withAdWindowId(adWindowId)));

		//
		toPO.setCopiedFromRecordId(fromPO.get_ID()); // need this for changelog

		// Parent link:
		final int parentId;
		if (parentLinkColumnName != null && (parentId = getParentID()) > 0)
		{
			toPO.set_CustomColumn(parentLinkColumnName, parentId);
		}

		// Notify listeners
		fireOnRecordCopied(toPO, fromPO);

		toPO.saveEx(ITrx.TRXNAME_ThreadInherited);
		toPO.setCopying(false);

		copyChildren(toPO, fromPO, template);

		return Optional.of(toPO);
	}

	@Override
	public final void copyChildren(@NonNull final PO toPO, @NonNull final PO fromPO)
	{
		final CopyTemplate template = copyTemplateService.getCopyTemplate(toPO.getPOInfo());
		copyChildren(toPO, fromPO, template);
	}

	private void copyChildren(@NonNull final PO toPO, @NonNull final PO fromPO, @NonNull CopyTemplate template)
	{
		if (!isCopyRecord(fromPO))
		{
			return;
		}

		for (final CopyTemplate childTemplate : copyTemplateService.getChildTemplates(template))
		{
			final String parentLinkColumnName = Check.assumeNotNull(childTemplate.getLinkColumnName(), "linkColumnName is set: {}", childTemplate);

			for (final Iterator<Object> it = retrieveChildPOsForParent(childTemplate, fromPO); it.hasNext(); )
			{
				final PO childPO = InterfaceWrapperHelper.getPO(it.next());
				CopyRecordFactory.getCopyRecordSupport(childTemplate.getTableName())
						.setParentLink(toPO, parentLinkColumnName)
						.setAdWindowId(adWindowId)
						.onRecordCopied(childRecordCopiedListeners)
						.oChildRecordCopied(childRecordCopiedListeners)
						.copyToNew(childPO);
				log.debug("Copied {}", childPO);
			}
		}

		fireOnRecordAndChildrenCopied(toPO, fromPO);
	}

	/**
	 * Called after the record was copied, right before saving it (and before it's children are copied).
	 *
	 * @param to   the copy
	 * @param from the source
	 */
	private void fireOnRecordCopied(final PO to, final PO from)
	{
		onRecordCopied(to, from);
		recordCopiedListeners.forEach(listener -> listener.onRecordCopied(to, from));
	}

	protected void onRecordCopied(final PO to, final PO from)
	{
		// nothing on this level
	}

	private void fireOnRecordAndChildrenCopied(final PO to, final PO from)
	{
		onRecordAndChildrenCopied(to, from);
	}

	protected void onRecordAndChildrenCopied(final PO to, final PO from)
	{
		// nothing on this level
	}

	private Iterator<Object> retrieveChildPOsForParent(final CopyTemplate childTemplate, final PO parentPO)
	{
		final IQueryBuilder<Object> queryBuilder = queryBL.createQueryBuilder(childTemplate.getTableName())
				.addEqualsFilter(childTemplate.getLinkColumnName(), parentPO.get_ID());

		childTemplate.getOrderByColumnNames().forEach(queryBuilder::orderBy);

		return queryBuilder
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, false)
				.create()
				.iterate(Object.class);
	}

	/**
	 * @return true if the record shall be copied
	 */
	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	protected boolean isCopyRecord(final PO fromPO)
	{
		return true;
	}

	@Override
	public final GeneralCopyRecordSupport setParentLink(@NonNull final PO parentPO, @NonNull final String parentLinkColumnName)
	{
		this.parentPO = parentPO;
		this.parentLinkColumnName = parentLinkColumnName;
		return this;
	}

	@Override
	public final GeneralCopyRecordSupport setAdWindowId(final @Nullable AdWindowId adWindowId)
	{
		this.adWindowId = adWindowId;
		return this;
	}

	@SuppressWarnings("SameParameterValue")
	@Nullable
	protected final <T> T getParentModel(final Class<T> modelType)
	{
		return parentPO != null ? InterfaceWrapperHelper.create(parentPO, modelType) : null;
	}

	private int getParentID()
	{
		return parentPO != null ? parentPO.get_ID() : -1;
	}

	/**
	 * Allows other modules to install customer code to be executed each time a record was copied.
	 */
	@Override
	public final GeneralCopyRecordSupport onRecordCopied(@NonNull final OnRecordCopiedListener listener)
	{
		if (!recordCopiedListeners.contains(listener))
		{
			recordCopiedListeners.add(listener);
		}

		return this;
	}

	@Override
	public CopyRecordSupport onChildRecordCopied(@NonNull final OnRecordCopiedListener listener)
	{
		if (!childRecordCopiedListeners.contains(listener))
		{
			childRecordCopiedListeners.add(listener);
		}

		return this;
	}
}
