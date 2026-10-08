package de.metas.order.compensationGroup;

import lombok.NonNull;
import lombok.Value;

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

/** Where a compensation order line takes its base from: its base and whether that base is stored on the order line itself. */
@Value
public class CompensationLineOrigin
{
	public static final CompensationLineOrigin NONE = new CompensationLineOrigin(GroupCompensationBase.NONE, false);

	/** {@link GroupCompensationBase#NONE} = computed on the whole group's regular lines */
	@NonNull GroupCompensationBase base;
	/** see {@link GroupCompensationLine#hasOwnBase()} */
	boolean ownBase;
}
