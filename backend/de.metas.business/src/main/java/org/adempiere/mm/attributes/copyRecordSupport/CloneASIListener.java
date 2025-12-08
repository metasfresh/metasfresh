package org.adempiere.mm.attributes.copyRecordSupport;

import de.metas.copy_with_details.OnRecordCopiedListener;
import de.metas.copy_with_details.template.CopyTemplate;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.compiere.model.PO;
import org.compiere.model.copy.ValueToCopyType;

/*
 * #%L
 * de.metas.document.engine
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * This listener makes sure that when a PO with an ASI is copied, then the ASI is cloned.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class CloneASIListener implements OnRecordCopiedListener
{
	private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private static final String COLUMNNAME_M_AttributeSetInstance_ID = "M_AttributeSetInstance_ID";

	@Override
	public void onRecordCopied(PO to, PO from, @NonNull final CopyTemplate template)
	{
		final ValueToCopyType type = template.getValueToCopyType(COLUMNNAME_M_AttributeSetInstance_ID).orElse(ValueToCopyType.SKIP);
		if (type.isDirectCopy())
		{
			attributeSetInstanceBL.cloneASI(to, from);
		}
	}
}
