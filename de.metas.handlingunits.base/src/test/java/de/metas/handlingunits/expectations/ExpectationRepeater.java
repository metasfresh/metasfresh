package de.metas.handlingunits.expectations;

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


import org.adempiere.util.Check;

public class ExpectationRepeater<ParentExpectationType, ExpectationType extends AbstractHUExpectation<ParentExpectationType>>
{
	private int repeat = -1;
	private IExpectationProducer<ExpectationType> newExpectationProducer;
	/** Template expectation. All expectations that will be created will be a copy of this one */
	private ExpectationType template;

	public ExpectationRepeater()
	{
		super();
	}

	private void createExpectationsFromTemplate()
	{
		Check.assume(repeat > 0, "repeat > 0: {}", repeat);
		Check.assumeNotNull(template, "template not set");

		// NOTE: we subtract "1" from how many expectations to create because "template" also counts
		final int expectationsToCreate = repeat - 1;

		for (int i = 1; i < expectationsToCreate; i++)
		{
			final ExpectationType expectation = newExpectation();
			expectation.copyFromExpectation(template);
			newExpectationProducer.addToParent(expectation); // add it to parent
			expectation.endExpectation(); // call it just in case is there a BL on that
		}
	}

	public ExpectationRepeater<ParentExpectationType, ExpectationType> newInstanceProducer(final IExpectationProducer<ExpectationType> newExpectationProducer)
	{
		this.newExpectationProducer = newExpectationProducer;
		return this;
	}

	private ExpectationType newExpectation()
	{
		Check.assumeNotNull(newExpectationProducer, "newExpectationProducer not null");
		return newExpectationProducer.newExpectation();
	}

	public ExpectationRepeater<ParentExpectationType, ExpectationType> repeat(final int repeat)
	{
		this.repeat = repeat;
		return this;
	}

	public ExpectationType template()
	{
		if (template != null)
		{
			return template;
		}

		template = newExpectation();

		// When "endExpectation" is called on template, we want to create all required expectations based on template
		template.endExpectationCallback(new Runnable()
		{

			@Override
			public void run()
			{
				createExpectationsFromTemplate();
			}
		});

		return template;
	}
}
