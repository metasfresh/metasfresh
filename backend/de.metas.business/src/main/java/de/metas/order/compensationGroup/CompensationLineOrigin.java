package de.metas.order.compensationGroup;

import de.metas.product.ProductCategoryId;
import lombok.Value;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of

/** Where a compensation order line takes its base from: its applies-to product category and whether that category is stored on the order line itself. */
@Value
public class CompensationLineOrigin
{
	public static final CompensationLineOrigin NONE = new CompensationLineOrigin(null, false);

	/** {@code null} = computed on the whole group's regular lines */
	@Nullable ProductCategoryId appliesToProductCategoryId;
	/** see {@link GroupCompensationLine#hasOwnBase()} */
	boolean ownBase;
}
