/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.boilerplate;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.ToString;
import org.compiere.model.I_AD_User;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import static org.adempiere.model.InterfaceWrapperHelper.create;

@ToString
public static final class BoilerPlateContext
{
	public static final BoilerPlateContext EMPTY = new BoilerPlateContext(ImmutableMap.of());
	private static final String VAR_WindowNo = "WindowNo";
	private static final String VAR_SalesRep = "SalesRep";
	private static final String VAR_SalesRep_ID = "SalesRep_ID";
	private static final String VAR_EMail = "EMail";
	private static final String VAR_AD_User_ID = "AD_User_ID";
	private static final String VAR_AD_User = "AD_User";
	private static final String VAR_C_BPartner_ID = "C_BPartner_ID";
	private static final String VAR_C_BPartner_Location_ID = "C_BPartner_Location_ID";
	private static final String VAR_AD_Org_ID = "AD_Org_ID";
	private static final String VAR_AD_Language = "AD_Language";
	/**
	 * Source document. Usually it's of type {@link SourceDocument}
	 */
	private static final String VAR_SourceDocument = SourceDocument.NAME;
	private final Map<String, Object> attributes;

	private BoilerPlateContext(@NonNull final Map<String, Object> attributes)
	{
		this.attributes = attributes;
	}

	public static Builder builder()
	{
		return new Builder(ImmutableMap.of());
	}

	public Builder toBuilder()
	{
		return new Builder(attributes);
	}

	public boolean containsKey(final String attributeName)
	{
		return attributes.containsKey(attributeName);
	}

	public Object get(final String attributeName)
	{
		return attributes.get(attributeName);
	}

	@Nullable
	public Integer getSalesRep_ID()
	{
		return (Integer)get(VAR_SalesRep_ID);
	}

	public I_AD_User getSalesRepUser()
	{
		return create(get(VAR_SalesRep), I_AD_User.class);
	}

	public String getEMail()
	{
		return (String)get(VAR_EMail);
	}

	@Nullable
	public Integer getC_BPartner_ID()
	{
		return (Integer)get(VAR_C_BPartner_ID);
	}

	@Nullable
	public Integer getC_BPartner_ID(final Integer defaultValue)
	{
		final Integer bpartnerId = getC_BPartner_ID();
		return bpartnerId != null ? bpartnerId : defaultValue;
	}

	@Nullable
	public Integer getC_BPartner_Location_ID()
	{
		return (Integer)get(VAR_C_BPartner_Location_ID);
	}

	@Nullable
	public Integer getC_BPartner_Location_ID(final Integer defaultValue)
	{
		final Integer bpartnerLocationId = getC_BPartner_Location_ID();
		return bpartnerLocationId != null ? bpartnerLocationId : defaultValue;
	}

	@Nullable
	public Integer getAD_User_ID()
	{
		return (Integer)get(VAR_AD_User_ID);
	}

	@Nullable
	public Integer getAD_User_ID(final Integer defaultValue)
	{
		final Integer adUserId = getAD_User_ID();
		return adUserId != null ? adUserId : defaultValue;
	}

	@Nullable
	public Integer getAD_Org_ID(final Integer defaultValue)
	{
		final Integer adOrgId = (Integer)get(VAR_AD_Org_ID);
		return adOrgId != null ? adOrgId : defaultValue;
	}

	@Nullable
	public String getAD_Language()
	{
		final Object adLanguageObj = get(VAR_AD_Language);
		return adLanguageObj != null ? adLanguageObj.toString() : null;
	}

	public int getWindowNo()
	{
		final Object windowNoObj = get(VAR_WindowNo);
		if (windowNoObj instanceof Number)
		{
			return ((Number)windowNoObj).intValue();
		}
		else
		{
			return Env.WINDOW_MAIN;
		}

	}

	public SourceDocument getSourceDocumentOrNull()
	{
		return SourceDocument.toSourceDocumentOrNull(get(VAR_SourceDocument));
	}

	public static class Builder
	{
		private final Map<String, Object> attributes;

		private Builder(final Map<String, Object> attributes)
		{
			this.attributes = attributes != null && !attributes.isEmpty() ? new HashMap<>(attributes) : new HashMap<>();
		}

		public BoilerPlateContext build()
		{
			if (attributes.isEmpty())
			{
				return EMPTY;
			}

			return new BoilerPlateContext(ImmutableMap.copyOf(attributes));
		}

		private void setAttribute(final String attributeName, final Object value)
		{
			if (value == null)
			{
				attributes.remove(attributeName);
			}
			else
			{
				attributes.put(attributeName, value);
			}
		}

		public Builder setWindowNo(final int windowNo)
		{
			setAttribute(VAR_WindowNo, windowNo);
			return this;
		}

		public Builder setSourceDocument(final SourceDocument sourceDocument)
		{
			setAttribute(VAR_SourceDocument, sourceDocument);
			return this;
		}

		public Builder setAD_Language(final String adLanguage)
		{
			setAttribute(VAR_AD_Language, adLanguage);
			return this;
		}

		public Builder setSourceDocumentFromObject(final Object sourceDocumentObj)
		{
			final SourceDocument sourceDocument = SourceDocument.toSourceDocumentOrNull(sourceDocumentObj);
			setSourceDocument(sourceDocument);
			return this;
		}

		public Builder setSalesRep(final I_AD_User salesRepUser)
		{
			setAttribute(VAR_SalesRep, salesRepUser);
			setAttribute(VAR_SalesRep_ID, salesRepUser != null ? salesRepUser.getAD_User_ID() : null);
			return this;
		}

		public Builder setC_BPartner_ID(final int bpartnerId)
		{
			setAttribute(VAR_C_BPartner_ID, bpartnerId);
			return this;
		}

		public Builder setC_BPartner_Location_ID(final int bpartnerLocationId)
		{
			setAttribute(VAR_C_BPartner_Location_ID, bpartnerLocationId);
			return this;
		}

		public Builder setUser(final I_AD_User user)
		{
			setAttribute(VAR_AD_User, user);
			setAttribute(VAR_AD_User_ID, user != null ? user.getAD_User_ID() : null);
			return this;
		}

		public Builder setEmail(final String email)
		{
			setAttribute(VAR_EMail, email);
			return this;
		}

		public Builder setAD_Org_ID(final int adOrgId)
		{
			setAttribute(VAR_AD_Org_ID, adOrgId);
			return this;
		}

		public Builder setCustomAttribute(final String attributeName, final Object value)
		{
			setAttribute(attributeName, value);
			return this;
		}
	}
}