/**
 * 
 */
package org.adempiere.util.test;

/*
 * #%L
 * de.metas.util
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


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * @author cg
 *         Credit to: http://www.codeaffine.com/2013/04/10/running-junit-tests-repeatedly-without-loops/
 *
 */
public class RepeatRule implements TestRule
{

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ java.lang.annotation.ElementType.METHOD })

	public @interface Repeat
	{
		public abstract int times();
	}

	private static class RepeatStatement extends Statement
	{
		private final int times;
		private final Statement statement;

		private RepeatStatement(final int times, final Statement statement)
		{
			this.times = times;
			this.statement = statement;
		}

		@Override
		public void evaluate() throws Throwable
		{
			for (int i = 0; i < times; i++)
			{
				statement.evaluate();
			}
		}
	}

	@Override
	public Statement apply(final Statement statement, final Description description)
	{
		Statement result = statement;
		Repeat repeat = description.getAnnotation(Repeat.class);
		if (repeat != null)
		{
			final int times = repeat.times();
			result = new RepeatStatement(times, statement);
		}
		return result;
	}
}
