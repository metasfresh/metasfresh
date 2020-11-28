package de.metas.printing.api.impl;

/*
 * #%L
 * de.metas.printing.base
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


import java.util.ArrayList;
import java.util.List;

import org.adempiere.test.TestClientUI.IAskCallback;
import org.junit.Assert;

public class FixedAskCallback implements IAskCallback
{
	private class Response
	{
		final String expectedAD_Message;
		final boolean response;

		public Response(String expectedAD_Message, boolean response)
		{
			super();
			this.expectedAD_Message = expectedAD_Message;
			this.response = response;
		}

		public String getExpectedAD_Message()
		{
			return expectedAD_Message;
		}

		public boolean isResponse()
		{
			return response;
		}
	}

	private String defaultExpectedAD_Message = null;
	private final List<Response> responses = new ArrayList<FixedAskCallback.Response>();
	private int subscribedResponses = 0;
	private int answeredQuestions = 0;

	public FixedAskCallback()
	{
	}

	public FixedAskCallback setDefaultExpectedAD_Message(final String defaultExpectedAD_Message)
	{
		this.defaultExpectedAD_Message = defaultExpectedAD_Message;
		return this;
	}

	public FixedAskCallback addResponse(final String expectedAD_Message, boolean response)
	{
		responses.add(new Response(expectedAD_Message, response));
		subscribedResponses++;
		return this;
	}

	public FixedAskCallback addResponse(boolean response)
	{
		return addResponse(defaultExpectedAD_Message, response);
	}

	@Override
	public boolean getYesNoAnswer(int WindowNo, String AD_Message, String message)
	{
		Assert.assertFalse("No more predefined responses specified (answeredQuestions=" + answeredQuestions + ")", responses.isEmpty());

		final Response response = responses.remove(0);
		answeredQuestions++;

		if (response.getExpectedAD_Message() != null)
		{
			Assert.assertEquals("Invalid AD_Message for YesNo question",
					response.getExpectedAD_Message(), AD_Message);
		}

		return response.isResponse();
	}

	public int getSubscribedResponses()
	{
		return subscribedResponses;
	}

	public int getRemainingResponsesCount()
	{
		return responses.size();
	}

	public void assertNoQuestionsAsked()
	{
		Assert.assertEquals("Questions were asked",
				getSubscribedResponses(), getRemainingResponsesCount());
	}
}
