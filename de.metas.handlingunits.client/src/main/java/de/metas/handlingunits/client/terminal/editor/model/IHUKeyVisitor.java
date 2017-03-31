package de.metas.handlingunits.client.terminal.editor.model;

import de.metas.handlingunits.IHUIteratorListener;

/*
 * #%L
 * de.metas.handlingunits.client
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

public interface IHUKeyVisitor
{
	enum VisitResult
	{
		/**
		 * Continue navigation
		 */
		CONTINUE,
		/**
		 * Stop navigation right on this node
		 */
		STOP,
		/**
		 * Skip downstream navigation and continue with siblings and other nodes.
		 *
		 * This result can be returned only from before* methods.
		 */
		SKIP_DOWNSTREAM;

		public static VisitResult of(final IHUIteratorListener.Result result)
		{
			switch (result)
			{
				case CONTINUE:
					return VisitResult.CONTINUE;
				case STOP:
					return VisitResult.STOP;
				case SKIP_DOWNSTREAM:
					return VisitResult.SKIP_DOWNSTREAM;
				default:
					throw new IllegalArgumentException("Cannot convert " + result + " to " + VisitResult.class);
			}
		}
	}

	/**
	 * Called before visiting key's children.
	 *
	 * @param key
	 * @return any {@link VisitResult} value
	 */
	VisitResult beforeVisit(IHUKey key);

	/**
	 * Called after visiting key's children.
	 *
	 * @param key
	 * @return {@link VisitResult#CONTINUE}, {@link VisitResult#STOP}; {@link VisitResult#SKIP_DOWNSTREAM} is not allowed because makes no sense
	 */
	VisitResult afterVisit(IHUKey key);
}
