package de.metas.material.event;

import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.ProductDescriptor;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;

/*
 * #%L
 * metasfresh-material-event
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

/**
 * Extracts a {@link ProductDescriptor} from a given ASI-aware model.
 * There is a subclass in metasfresh-material-dispo-commons that is injected at runtime and that has a working implementation for {@link #createProductDescriptor(Object)}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface ModelProductDescriptorExtractor
{
	default ProductDescriptor createProductDescriptor(@NonNull final Object asiAwareModel)
	{
		return createProductDescriptor(asiAwareModel, AttributesKey.NONE);
	}

	/**
	 * @param defaultAttributesKey the result will have this key if there is not key to be generated
	 *            for the given {@code asiAwareModel}'s attribute set instance.
	 */
	ProductDescriptor createProductDescriptor(Object asiAwareModel, AttributesKey defaultAttributesKey);

	ProductDescriptor createProductDescriptor(int productId, @NonNull AttributeSetInstanceId asiId);
}
