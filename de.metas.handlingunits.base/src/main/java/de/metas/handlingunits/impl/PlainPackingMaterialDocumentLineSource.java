package de.metas.handlingunits.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.adempiere.util.Check;

import de.metas.handlingunits.IPackingMaterialDocumentLineSource;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import lombok.Value;

/**
 * Just a plain implementation of {@link IPackingMaterialDocumentLineSource} which wraps an {@link I_M_HU_PackingMaterial}.
 *
 * @author tsa
 *
 */
@Value
public final class PlainPackingMaterialDocumentLineSource implements IPackingMaterialDocumentLineSource
{
	public static final PlainPackingMaterialDocumentLineSource of(final I_M_HU_PackingMaterial packingMaterial, final int qty)
	{
		return new PlainPackingMaterialDocumentLineSource(packingMaterial, qty);
	}
	
	private final List<I_M_HU_PackingMaterial> M_HU_PackingMaterials;
	private final BigDecimal qty;

	private PlainPackingMaterialDocumentLineSource(final I_M_HU_PackingMaterial packingMaterial, final int qty)
	{
		super();
		Check.assumeNotNull(packingMaterial, "packingMaterial not null");
		M_HU_PackingMaterials = Collections.singletonList(packingMaterial);

		this.qty = BigDecimal.valueOf(qty);
	}
}
