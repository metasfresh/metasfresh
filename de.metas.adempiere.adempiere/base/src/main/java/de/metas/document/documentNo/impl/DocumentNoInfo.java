package de.metas.document.documentNo.impl;

import com.google.common.base.MoreObjects;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/* package */final class DocumentNoInfo implements IDocumentNoInfo
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final String docBaseType;
	private final String docSubType;
	private final boolean soTrx;
	private final boolean hasChanges;
	private final boolean docNoControlled;
	private final String documentNo;

	private DocumentNoInfo(final Builder builder)
	{
		super();
		this.documentNo = builder.documentNo;
		this.docBaseType = builder.docBaseType;
		this.docSubType = builder.docSubType;
		this.soTrx = builder.soTrx;
		this.hasChanges = builder.hasChanges;
		this.docNoControlled = builder.docNoControlled;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("documentNo", documentNo)
				.add("docBaseType", docBaseType)
				.add("docSubType", docSubType)
				.add("IsSOTrx", soTrx)
				.add("hasChanges", hasChanges)
				.add("docNoControlled", docNoControlled)
				.toString();
	}

	@Override
	public String getDocBaseType()
	{
		return docBaseType;
	}

	@Override
	public String getDocSubType()
	{
		return docSubType;
	}

	@Override
	public boolean isSOTrx()
	{
		return soTrx;
	}

	@Override
	public boolean isHasChanges()
	{
		return hasChanges;
	}

	@Override
	public boolean isDocNoControlled()
	{
		return docNoControlled;
	}

	@Override
	public String getDocumentNo()
	{
		return documentNo;
	}

	public static final class Builder
	{
		private String docBaseType;
		private String docSubType;
		private boolean soTrx;
		private boolean hasChanges;
		private boolean docNoControlled;
		private String documentNo;

		private Builder()
		{
			super();
		}

		public DocumentNoInfo build()
		{
			return new DocumentNoInfo(this);
		}

		public Builder setDocumentNo(final String documentNo)
		{
			this.documentNo = documentNo;
			return this;
		}

		public Builder setDocBaseType(final String docBaseType)
		{
			this.docBaseType = docBaseType;
			return this;
		}

		public Builder setDocSubType(final String docSubType)
		{
			this.docSubType = docSubType;
			return this;
		}

		public Builder setHasChanges(final boolean hasChanges)
		{
			this.hasChanges = hasChanges;
			return this;
		}

		public Builder setDocNoControlled(final boolean docNoControlled)
		{
			this.docNoControlled = docNoControlled;
			return this;
		}

		public Builder setIsSOTrx(final boolean isSOTrx)
		{
			soTrx = isSOTrx;
			return this;
		}
	}
}
